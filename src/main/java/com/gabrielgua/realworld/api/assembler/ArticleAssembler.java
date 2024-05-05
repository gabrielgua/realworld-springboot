package com.gabrielgua.realworld.api.assembler;

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

    public List<ArticleResponse> toCollectionModel(List<Article> articles) {
        return articles.stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ArticleResponse> toCollectionModel(User user, List<Article> articles) {
        return articles.stream()
                .map(a -> toResponse(user, a))
                .toList();
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
