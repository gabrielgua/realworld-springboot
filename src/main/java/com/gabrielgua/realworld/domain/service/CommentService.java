package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.exception.CommentNotFoundException;
import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Comment;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public List<Comment> getAllByArticle(Article article) {
        return repository.findAllByArticle(article);
    }

    @Transactional
    public Comment getById(Long commentId) {
        return repository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    @Transactional
    public Comment save(Comment comment, Article article, Profile author) {
        comment.setAuthor(author);
        comment.setArticle(article);
        return repository.save(comment);
    }

    @Transactional
    public void delete(Comment comment) {
        repository.delete(comment);
    }


}
