package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.exception.ArticleNotFoundException;
import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.model.Tag;
import com.gabrielgua.realworld.domain.repository.ArticleRepository;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository repository;
    private final Slugify slg;

    @Transactional(readOnly = true)
    public List<Article> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Article getBySlug(String slug) {
        return repository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));
    }

    @Transactional
    public Article save(Article article, Profile profile, List<Tag> tags) {
        article.setAuthor(profile);
        article.setSlug(slg.slugify(article.getTitle()));

        addAllTags(article, tags);
        return repository.save(article);
    }

    private void addAllTags(Article article, List<Tag> tags) {
        article.setTagList(new HashSet<>());
        tags.forEach(article::addTag);
    }
}
