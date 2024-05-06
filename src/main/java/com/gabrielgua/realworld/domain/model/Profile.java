package com.gabrielgua.realworld.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String username;
    private String bio;
    private String image;

    @ManyToMany
    @JoinTable(
            name = "profiles_following",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<Profile> profiles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "profiles_articles",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<Article> articles = new HashSet<>();

    public void followProfile(Profile profile) {
        getProfiles().add(profile);
    }

    public void unfollowProfile(Profile profile) {
        getProfiles().remove(profile);
    }

    public void favoriteArticle(Article article) {
        getArticles().add(article);
    }

    public void unfavoriteArticle(Article article) {
        getArticles().remove(article);
    }
}
