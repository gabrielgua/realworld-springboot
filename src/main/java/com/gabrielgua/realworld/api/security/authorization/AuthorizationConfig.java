package com.gabrielgua.realworld.api.security.authorization;

import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.service.ArticleService;
import com.gabrielgua.realworld.domain.service.CommentService;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationConfig {

    private final AuthUtils authUtils;
    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;


    public boolean isArticleAuthor(String slug) {
        if (!isAuthenticated()) {
            return false;
        }

        var article = articleService.getBySlug(slug);
        var author = article.getAuthor();

        return authenticatedUserEquals(author);
    }

    public boolean isCommentAuthor(Long commentId) {
        if (!isAuthenticated()) {
            return false;
        }

        var comment = commentService.getById(commentId);
        var author = comment.getAuthor();

        return authenticatedUserEquals(author);
    }

    private boolean authenticatedUserEquals(Profile user) {
        return userService.getCurrentUser().getProfile().equals(user);
    }

    public boolean isAuthenticated() {
        return authUtils.isAuthenticated();
    }








}
