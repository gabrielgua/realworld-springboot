package com.gabrielgua.realworld.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String slug;
    private String description;
    private String body;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "articles_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagList = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Profile author;

    @ManyToMany(mappedBy = "favoritedArticles")
    private Set<User> favorites = new HashSet<>();

    private int favoritesCount = 0;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    public void addTag(Tag tag) {
        getTagList().add(tag);
    }

    public void updateFavoritedCount() {
        setFavoritesCount(getFavorites().size());
    }

    public void addFavorite(User user) {
        getFavorites().add(user);
        updateFavoritedCount();
    }

    public void removeFavorite(User user) {
        getFavorites().remove(user);
        updateFavoritedCount();
    }
}
