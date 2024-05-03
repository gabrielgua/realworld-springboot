package com.gabrielgua.realworld.infra.spec;

import com.gabrielgua.realworld.domain.model.Article;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Join(path = "author", alias = "author")
@Join(path = "tagList", alias = "tags")
@And({
        @Spec(path = "author.username", params = "author", spec = Like.class),
        @Spec(path = "tags.name", params = "tag", spec = Like.class)
})
public interface ArticleSpecification extends Specification<Article> {
}
