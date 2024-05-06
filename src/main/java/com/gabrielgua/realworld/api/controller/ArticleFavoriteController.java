package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.ArticleAssembler;
import com.gabrielgua.realworld.api.model.article.ArticleResponse;
import com.gabrielgua.realworld.api.security.authorization.CheckSecurity;
import com.gabrielgua.realworld.domain.service.ArticleService;
import com.gabrielgua.realworld.domain.service.ProfileService;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}")
public class ArticleFavoriteController {

    private final UserService userService;
    private final ProfileService profileService;
    private final ArticleService articleService;
    private final ArticleAssembler articleAssembler;

    @PostMapping("/favorite")
    @CheckSecurity.Protected.canManage
    public ArticleResponse favoriteArticle(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);
        var profile = userService.getCurrentUser();

        profile = userService.favorite(profile, article);
        article = articleService.userFavorited(profile, article);
        return articleAssembler.toResponse(article);
    }

    @DeleteMapping("/favorite")
    @CheckSecurity.Protected.canManage
    public ArticleResponse unfavoriteArticle(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);
        var user = userService.getCurrentUser();

        user = userService.unfavorite(user, article);
        article = articleService.userUnfavorited(user, article);
        return articleAssembler.toResponse(article);
    }
}
