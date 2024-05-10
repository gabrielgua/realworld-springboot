package com.gabrielgua.realworld.domain.repository;

import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Tag;
import com.gabrielgua.realworld.domain.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    Optional<Article> findBySlug(String slug);
    List<Article> findAllByAuthorIn(List<User> users, Pageable pageable);

}
