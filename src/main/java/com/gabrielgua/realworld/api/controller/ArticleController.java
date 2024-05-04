package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.ArticleAssembler;
import com.gabrielgua.realworld.api.model.ArticleRegister;
import com.gabrielgua.realworld.api.model.ArticleResponse;
import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.service.ArticleService;
import com.gabrielgua.realworld.domain.service.TagService;
import com.gabrielgua.realworld.domain.service.UserService;
import com.gabrielgua.realworld.infra.spec.ArticleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleAssembler assembler;
    private final UserService userService;
    private final TagService tagService;
    private final AuthUtils authUtils;

    private static final String DEFAULT_FILTER_LIMIT = "20";
    private static final String DEFAULT_FILTER_OFFSET = "0";
    private static final Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.DESC, "createdAt");


    private User getCurrentUser() {
        return userService.getByEmail(authUtils.getCurrentUserEmail());
    }

    private boolean hasAuthorizationHeader(WebRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION) != null;
    }

    @GetMapping
    public List<ArticleResponse> getAll(
            WebRequest request,
            ArticleSpecification filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {


        Pageable pageable = PageRequest.of(offset, limit, DEFAULT_FILTER_SORT);
        var articles = articleService.listAll(filter, pageable).getContent();


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


    @PostMapping
    public ArticleResponse save(@RequestBody ArticleRegister register) {
        var user = getCurrentUser();
        var tags = tagService.saveAll(register.getTagList().stream().toList());
        var article = assembler.toEntity(register);

        return assembler.toResponse(user, articleService.save(article, user.getProfile(), tags));
    }
}
