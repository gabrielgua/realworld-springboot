package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.exception.ArticleNotFoundException;
import com.gabrielgua.realworld.domain.exception.ArticleNotUniqueException;
import com.gabrielgua.realworld.domain.model.Article;
import com.gabrielgua.realworld.domain.model.Comment;
import com.gabrielgua.realworld.domain.model.Profile;
import com.gabrielgua.realworld.domain.model.Tag;
import com.gabrielgua.realworld.domain.repository.ArticleRepository;
import com.gabrielgua.realworld.domain.repository.CommentRepository;
import com.gabrielgua.realworld.infra.spec.ArticleSpecification;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ArticleServiceTest {

    @Mock private Slugify slg;
    @Mock private ArticleRepository articleRepository;

    @Mock private ArticleSpecification spec;

    @Mock private CommentRepository commentRepository;

    @InjectMocks
    private ArticleService articleService;

    private Page<Article> articlePage;
    private final List<Article> articles = new ArrayList<>();

    private Article articleOne;

    private Profile profile;

    private Profile author;

    private final List<Tag> tags = new ArrayList<>();

    private final Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));


    @BeforeEach
    void setUp() {
        openMocks(this);

        var dragons = Tag.builder().name("dragons").build();
        var guides = Tag.builder().name("guides").build();

        tags.add(dragons);
        tags.add(guides);

        author = Profile.builder()
                .id(2L)
                .username("author")
                .build();

        articleOne = Article.builder()
                .id(1L)
                .title("Article One")
                .slug("article-one")
                .description("Article number one")
                .body("This is the article number one")
                .author(author)
                .tagList(new HashSet<>(tags))
                .favorites(new HashSet<>())
                .build();

        var articleTwo = Article.builder()
                .id(2L)
                .title("Article Two")
                .author(author)
                .description("Article number two")
                .body("This is the article number two")
                .build();

        articles.add(articleOne);
        articles.add(articleTwo);

        articlePage = new PageImpl<>(articles);

        profile = Profile.builder()
                .id(1L)
                .image("https://example.com/my-image.png")
                .username("user")
                .articles(new HashSet<>(articles))
                .profiles(new HashSet<>())
                .build();

        var profile1 = Profile.builder().id(3L).build();

        var profiles = new HashSet<Profile>();
        profiles.add(author);
        profiles.add(profile1);

        profile.setProfiles(profiles);


    }

    @Test
    public void should_return_all_articles_successfully() {
        when(articleRepository.findAll(any(ArticleSpecification.class), any(Pageable.class))).thenReturn(articlePage);

        var allArticles = articleService.listAll(spec, pageable).getContent();

        assertThat(allArticles).isNotNull();
        assertThat(allArticles.size()).isEqualTo(articles.size());

        verify(articleRepository, times(1)).findAll(any(ArticleSpecification.class), any(Pageable.class));
    }

    @Test
    public void should_return_article_by_slug_successfully() {
        when(articleRepository.findBySlug(anyString())).thenReturn(Optional.ofNullable(articleOne));

        var foundArticle = articleService.getBySlug("article-one");

        assertThat(foundArticle).isNotNull();
        assertThat(foundArticle.getId()).isEqualTo(articleOne.getId());
        assertThat(foundArticle.getSlug()).isEqualTo(articleOne.getSlug());

        verify(articleRepository, times(1)).findBySlug(anyString());
    }

    @Test
    public void should_throw_ArticleNotFoundException_when_slug_not_found() {
        when(articleRepository.findBySlug(anyString())).thenThrow(ArticleNotFoundException.class);

        assertThatExceptionOfType(ArticleNotFoundException.class)
                .isThrownBy(() -> articleService.getBySlug("slug"));

        verify(articleRepository, times(1)).findBySlug(anyString());
    }

    @Test
    public void should_return_the_articles_feed_successfully() {
        when(articleRepository.findAllByAuthorIn(anyList(), any(Pageable.class))).thenReturn(articles);

        var following = profile.getProfiles();
        var feed = articleService.getFeedByUser(profile, pageable);
        var authors = feed.stream().map(Article::getAuthor).collect(Collectors.toSet());

        assertThat(feed).isNotNull();
        assertThat(feed.size()).isEqualTo(articles.size());
        assertThat(following.containsAll(authors)).isTrue();
        assertThat(feed.containsAll(profile.getArticles())).isTrue();

        verify(articleRepository, times(1)).findAllByAuthorIn(anyList(), any(Pageable.class));
    }

    @Test
    public void should_save_an_article_successfully() {
        var newArticle = Article.builder()
                .title("Article One")
                .description("Article number one")
                .body("This is the article number one")
                .build();


        when(articleRepository.save(any(Article.class))).thenReturn(articleOne);

        var saved = articleService.save(newArticle, author, tags);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getSlug()).isEqualTo(articleOne.getSlug());
        assertThat(saved.getAuthor()).isEqualTo(articleOne.getAuthor());
        assertThat(saved.getTagList().containsAll(articleOne.getTagList())).isTrue();
        assertThat(saved.getTagList().size()).isEqualTo(articleOne.getTagList().size());


        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    public void should_throw_ArticleNotUniqueException_when_slug_taken() {
        when(articleRepository.findBySlug(anyString())).thenReturn(Optional.ofNullable(articleOne));
        when(slg.slugify(anyString())).thenReturn("article-one");

        var article = Article.builder().title("Article one").build();
        assertThatExceptionOfType(ArticleNotUniqueException.class)
                .isThrownBy(() -> articleService.save(article))
                .withMessage("There's already an article with this title");

        verify(slg, times(1)).slugify(anyString());
        verify(articleRepository, times(1)).findBySlug(anyString());
    }

    @Test
    public void should_delete_an_article_successfully() {
        assertAll(() -> articleService.delete(articleOne));

        verify(articleRepository, times(1)).delete(any(Article.class));
    }

    @Test
    public void should_add_user_to_favorites_successfully() {
        when(articleRepository.save(any(Article.class))).thenReturn(articleOne);

        var saved = articleService.profileFavorited(profile, articleOne);

        assertThat(saved).isNotNull();
        assertThat(saved.getFavorites().contains(profile)).isTrue();
        assertThat(saved.getFavorites().size()).isEqualTo(articleOne.getFavorites().size());
        assertThat(saved.getFavoritesCount()).isOne();

        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    public void should_remove_user_from_favorites_successfully() {
        when(articleRepository.save(any(Article.class))).thenReturn(articleOne);

        var saved = articleService.profileUnfavorited(profile, articleOne);

        assertThat(saved).isNotNull();
        assertThat(saved.getFavorites().contains(profile)).isFalse();
        assertThat(saved.getFavorites().size()).isEqualTo(articleOne.getFavorites().size());
        assertThat(saved.getFavoritesCount()).isZero();


        verify(articleRepository, times(1)).save(any(Article.class));
    }
}