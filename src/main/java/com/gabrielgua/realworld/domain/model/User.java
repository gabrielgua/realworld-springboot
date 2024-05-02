package com.gabrielgua.realworld.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;
    private String password;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "users_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<Profile> following = new HashSet<>();

//    @ManyToMany
//    @JoinTable(
//            name = "users_favorited",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "article_id")
//    )
//    private Set<Article> favorited = new HashSet<>();

    public void followProfile(Profile profile) {
        getFollowing().add(profile);
    }

    public void unfollowProfile(Profile profile) {
        getFollowing().remove(profile);
    }
}
