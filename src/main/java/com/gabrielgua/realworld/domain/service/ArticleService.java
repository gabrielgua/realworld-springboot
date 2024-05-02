package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.exception.ArticleNotFoundException;
import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository repository;

    @Transactional(readOnly = true)
    public List<Article> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Article getBySlug(String slug) {
        return repository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));
    }

    @Transactional
    public Article save(Article article, Profile profile) {
        article.setAuthor(profile);
        return repository.save(article);
    }



}
