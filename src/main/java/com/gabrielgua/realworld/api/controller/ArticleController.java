package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.ArticleAssembler;
import com.gabrielgua.realworld.api.model.ArticleResponse;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.service.ArticleService;
import com.gabrielgua.realworld.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleAssembler assembler;
    private final UserService userService;
    private final AuthUtils authUtils;


    private User getCurrentUser() {
        return userService.getByEmail(authUtils.getCurrentUserEmail());
    }

    private boolean hasAuthorizationHeader(WebRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION) != null;
    }

    @GetMapping
    public List<ArticleResponse> getAll(WebRequest request) {
        var articles = articleService.listAll();

        if (!hasAuthorizationHeader(request)) {
            return assembler.toCollectionModel(articles);
        }

        var user = getCurrentUser();
        return assembler.toCollectionModel(user, articles);
    }

    @GetMapping("/{slug}")
    public ArticleResponse getBySlug(@PathVariable String slug, WebRequest request) {
        var article = articleService.getBySlug(slug);
        if (!hasAuthorizationHeader(request)) {
            return assembler.toResponse(article);
        }

        var user = getCurrentUser();
        return assembler.toResponse(user, article);
    }





}
