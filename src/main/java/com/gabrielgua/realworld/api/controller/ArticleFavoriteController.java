package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.ArticleAssembler;
import com.gabrielgua.realworld.api.assembler.UserAssembler;
import com.gabrielgua.realworld.api.model.ArticleResponse;
import com.gabrielgua.realworld.api.model.UserResponse;
import com.gabrielgua.realworld.domain.service.ArticleService;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}")
public class ArticleFavoriteController {

    private final UserService userService;
    private final ArticleService articleService;
    private final ArticleAssembler articleAssembler;
    private final UserAssembler userAssembler;

    @PostMapping("/favorite")
    public ArticleResponse favoriteArticle(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);
        var user = userService.getCurrentUser();

        user = userService.favorite(user, article);
        article = articleService.userFavorited(user, article);
        return articleAssembler.toResponse(user, article);
    }

    @DeleteMapping("/favorite")
    public ArticleResponse unfavoriteArticle(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);
        var user = userService.getCurrentUser();

        user = userService.unfavorite(user, article);
        article = articleService.userUnfavorited(user, article);
        return articleAssembler.toResponse(user, article);
    }



    @GetMapping("/favorites")
    public List<UserResponse> getAllFavorites(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);

        return userAssembler.toCollectionResponse(article.getFavorites().stream().toList());
    }


}
