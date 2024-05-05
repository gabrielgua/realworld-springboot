package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.ArticleListResponse;
import com.gabrielgua.realworld.api.model.ArticleRegister;
import com.gabrielgua.realworld.api.model.ArticleResponse;
import com.gabrielgua.realworld.api.model.ArticleUpdate;
import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Tag;
import com.gabrielgua.realworld.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public void copyToEntity(ArticleUpdate update, Article article) {
        modelMapper.map(update, article);
    }

    public ArticleResponse toResponse(Article article) {
        var response = modelMapper.map(article, ArticleResponse.class);

        response.setTagList(tagsToList(article.getTagList().stream().toList()));
        return response;
    }

    public ArticleResponse toResponse(User user, Article article) {
        var response = modelMapper.map(article, ArticleResponse.class);

        if (user.getFollowing().contains(article.getAuthor())) {
            response.getAuthor().setFollowing(true);
        }

        if (article.getFavorites().contains(user)) {
            response.setFavorited(true);
        }

        response.setTagList(tagsToList(article.getTagList().stream().toList()));
        return response;
    }

    public ArticleListResponse toCollectionModel(List<Article> articles) {

        var content = articles.stream()
                .map(this::toResponse)
                .toList();

        var articlesCount = content.size();

        return ArticleListResponse.builder()
                .articles(content)
                .articlesCount(articlesCount)
                .build();
    }

    public ArticleListResponse toCollectionModel(User user, List<Article> articles) {

        var content = articles.stream()
                .map(a -> toResponse(user, a))
                .toList();

        var articlesCount = content.size();

        return ArticleListResponse.builder()
                .articles(content)
                .articlesCount(articlesCount)
                .build();
    }


    public Article toEntity(ArticleRegister register) {
        return modelMapper.map(register, Article.class);
    }

    private List<String> tagsToList(List<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .toList();
    }












}
