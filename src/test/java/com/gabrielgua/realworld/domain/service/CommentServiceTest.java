package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Comment;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class CommentServiceTest {

    @Mock private CommentRepository commentRepository;

    @InjectMocks
    private CommentService service;

    private Comment comment;
    private Article article;
    private Profile author;


    @BeforeEach
    void setUp() {
        openMocks(this);

        article = Article.builder()
                .id(1L)
                .title("Hello article")
                .comments(new HashSet<>(Set.of(comment)))
                .build();


        comment = Comment.builder()
                .id(1L)
                .author(author)
                .article(article)
                .createdAt(OffsetDateTime.now())
                .body("I'm a comment")

                .build();
    }

    @Test
    public void should_return_all_comments_for_an_article() {

        var comments = service.getAllByArticle(article);

        assertThat(comments).isNotNull();
    }



}