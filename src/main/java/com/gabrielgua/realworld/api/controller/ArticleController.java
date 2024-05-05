package com.gabrielgua.realworld.api.controller;

import com.gabrielgua.realworld.api.assembler.ArticleAssembler;
import com.gabrielgua.realworld.api.model.ArticleListResponse;
import com.gabrielgua.realworld.api.model.ArticleRegister;
import com.gabrielgua.realworld.api.model.ArticleResponse;
import com.gabrielgua.realworld.api.model.ArticleUpdate;
import com.gabrielgua.realworld.api.security.authorization.CheckSecurity;
import com.gabrielgua.realworld.domain.model.Tag;
import com.gabrielgua.realworld.domain.service.ArticleService;
import com.gabrielgua.realworld.domain.service.TagService;
import com.gabrielgua.realworld.domain.service.UserService;
import com.gabrielgua.realworld.infra.spec.ArticleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final TagService tagService;
    private final UserService userService;
    private final ArticleService articleService;
    private final ArticleAssembler articleAssembler;

    private static final String DEFAULT_FILTER_LIMIT = "20";
    private static final String DEFAULT_FILTER_OFFSET = "0";
    private static final Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.DESC, "createdAt");

    private boolean hasAuthorizationHeader(WebRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION) != null;
    }

    @GetMapping
    public ArticleListResponse getAll(
            WebRequest request,
            ArticleSpecification filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset) {


        Pageable pageable = PageRequest.of(offset, limit, DEFAULT_FILTER_SORT);
        var articles = articleService.listAll(filter, pageable).getContent();

        if (!hasAuthorizationHeader(request)) {
            return articleAssembler.toCollectionModel(articles);
        }

        var user = userService.getCurrentUser();
        return articleAssembler.toCollectionModel(user, articles);
    }

    @GetMapping("/feed")
    public ArticleListResponse getFeed(
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset
    ) {

        var user = userService.getCurrentUser();
        Pageable pageable = PageRequest.of(offset, limit, DEFAULT_FILTER_SORT);
        var articles = articleService.getFeedByUser(user, pageable);

        return articleAssembler.toCollectionModel(user, articles);
    }

    @GetMapping("/{slug}")
    public ArticleResponse getBySlug(@PathVariable String slug, WebRequest request) {
        var article = articleService.getBySlug(slug);
        if (!hasAuthorizationHeader(request)) {
            return articleAssembler.toResponse(article);
        }

        var user = userService.getCurrentUser();
        return articleAssembler.toResponse(user, article);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CheckSecurity.Default.canWrite
    public ArticleResponse save(@RequestBody ArticleRegister register) {
        var user = userService.getCurrentUser();

        List<Tag> tags = new ArrayList<>();
        if (register.getTagList() != null) {
            tags = tagService.saveAll(register.getTagList().stream().toList());
        }

        var article = articleAssembler.toEntity(register);
        return articleAssembler.toResponse(user, articleService.save(article, user.getProfile(), tags));
    }

    @PutMapping("/{slug}")
    @CheckSecurity.Articles.canManage
    public ArticleResponse update(@PathVariable String slug, @RequestBody ArticleUpdate update) {
        var article = articleService.getBySlug(slug);
        articleAssembler.copyToEntity(update, article);

        return articleAssembler.toResponse(articleService.save(article));
    }

    @DeleteMapping("/{slug}")
    @CheckSecurity.Articles.canManage
    public void delete(@PathVariable String slug) {
        var article = articleService.getBySlug(slug);
        articleService.delete(article);
    }

}
