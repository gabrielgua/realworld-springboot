package com.gabrielgua.realworld.domain.repository;

import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticle(Article article);
}
