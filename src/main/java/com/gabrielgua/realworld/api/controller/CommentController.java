package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.CommentAssembler;
import com.gabrielgua.realworld.api.model.comment.CommentWrapper;
import com.gabrielgua.realworld.api.model.comment.CommentRegister;
import com.gabrielgua.realworld.api.model.comment.CommentResponse;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.api.security.authorization.CheckSecurity;
import com.gabrielgua.realworld.domain.service.ArticleService;
import com.gabrielgua.realworld.domain.service.CommentService;
import com.gabrielgua.realworld.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}/comments")
public class CommentController {

    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final CommentAssembler commentAssembler;
    private final AuthUtils authUtils;

    @GetMapping
    @CheckSecurity.Public.canRead
    public CommentWrapper listByArticle(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);

        if (authUtils.isAuthenticated()) {
            var user = userService.getCurrentUser();
            return commentAssembler.toCollectionResponse(user, commentService.getAllByArticle(article));
        }


        return commentAssembler.toCollectionResponse(commentService.getAllByArticle(article));
    }

    @PostMapping
    @CheckSecurity.Protected.canManage
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse save(@PathVariable String slug, @Valid @RequestBody CommentRegister register) {
        var comment = commentAssembler.toEntity(register);
        var article = articleService.getBySlug(slug);
        var author = userService.getCurrentUser().getProfile();

        return commentAssembler.toResponse(commentService.save(comment, article, author));
    }

    @DeleteMapping("/{commentId}")
    @CheckSecurity.Comments.canDelete
    public void delete(@PathVariable String slug, @PathVariable Long commentId) {
        var article = articleService.getBySlug(slug);
        var comment = commentService.getById(commentId);

        commentService.delete(comment);
    }

}
