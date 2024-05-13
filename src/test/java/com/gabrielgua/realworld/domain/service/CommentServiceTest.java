package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.exception.CommentNotFoundException;
import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Comment;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CommentServiceTest {

    @Mock private CommentRepository commentRepository;

    @InjectMocks
    private CommentService service;

    private Comment comment;
    private Article article;
    private Profile author;

    private final List<Comment> comments = new ArrayList<>();


    @BeforeEach
    void setUp() {
        openMocks(this);

        author = Profile.builder()
                .id(1L)
                .username("author")
                .build();

        article = Article.builder()
                .id(1L)
                .title("Hello article")
                .comments(new HashSet<>())
                .build();


        comment = Comment.builder()
                .id(1L)
                .author(author)
                .article(article)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .body("I'm a comment")
                .build();

        var comment2 = Comment.builder()
                .id(2L)
                .author(author)
                .article(article)
                .createdAt(OffsetDateTime.now())
                .body("I'm a comment 2")
                .build();

        comments.add(comment);
        comments.add(comment2);

    }

    @Test
    public void should_return_all_comments_for_an_article() {
        when(commentRepository.findAllByArticle(any(Article.class))).thenReturn(comments);

        article.setComments(new HashSet<>(comments));

        var comments = service.getAllByArticle(article);

        assertThat(comments).isNotNull();
        assertThat(comments.containsAll(article.getComments())).isTrue();

        verify(commentRepository, times(1)).findAllByArticle(any(Article.class));
    }

    @Test
    public void should_return_a_comment_by_id() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(comment));

        var found = service.getById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(comment.getId());
        assertThat(found.getId()).isEqualTo(1L);

        verify(commentRepository, times(1)).findById(anyLong());

    }

    @Test
    public void should_throw_CommentNotFoundException_when_comment_id_not_found() {
        when(commentRepository.findById(anyLong())).thenThrow(CommentNotFoundException.class);

        assertThatExceptionOfType(CommentNotFoundException.class)
                .isThrownBy(() -> service.getById(anyLong()));

        verify(commentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void should_save_a_comment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);


        var newComment = Comment.builder()
                .body("I'm a comment")
                .build();

        var saved = service.save(newComment, article, author);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getAuthor()).isNotNull();
        assertThat(saved.getArticle()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();


        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void should_delete_a_comment() {
        assertAll(() -> service.delete(comment));

        verify(commentRepository, times(1)).delete(any(Comment.class));
    }



}