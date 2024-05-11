package com.gabrielgua.realworld.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String slug;
    private String description;
    private String body;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "articles_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagList = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Profile author;

    @ManyToMany(mappedBy = "articles", cascade = CascadeType.DETACH)
    private Set<Profile> favorites = new HashSet<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private Set<Comment> comments = new HashSet<>();

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

    public void addFavorite(Profile profile) {
        getFavorites().add(profile);
        updateFavoritedCount();
    }

    public void removeFavorite(Profile profile) {
        getFavorites().remove(profile);
        updateFavoritedCount();
    }
}
