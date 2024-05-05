package com.gabrielgua.realworld.api.security.authorization;

import com.gabrielgua.realworld.api.security.AuthUtils;
import com.gabrielgua.realworld.domain.model.User;
import com.gabrielgua.realworld.domain.service.ArticleService;
import com.gabrielgua.realworld.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationConfig {

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;


    public boolean isAuthor(String slug) {
        if (!isAuthenticated()) {
            return false;
        }

        var article = articleService.getBySlug(slug);
        var user = article.getAuthor().getUser();

        return authenticatedUserEquals(user);
    }

    private boolean authenticatedUserEquals(User user) {
        return userService.getCurrentUser().equals(user);
    }

    public boolean isAuthenticated() {
        return authUtils.isAuthenticated();
    }







}
