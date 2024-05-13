package com.gabrielgua.realworld.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Profile author;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
