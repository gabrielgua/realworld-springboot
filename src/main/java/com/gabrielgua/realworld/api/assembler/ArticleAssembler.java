package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.ArticleResponse;
import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ArticleResponse toResponse(Article article) {
        return modelMapper.map(article, ArticleResponse.class);
    }

    public ArticleResponse toResponse(User user, Article article) {
        var response = modelMapper.map(article, ArticleResponse.class);

        if (user.getFollowing().contains(article.getAuthor())) {
            response.getAuthor().setFollowing(true);
        }

//        if (user.getFavorited().contains(article)) {
//              response.setFavorited(true);
//        }


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












}
