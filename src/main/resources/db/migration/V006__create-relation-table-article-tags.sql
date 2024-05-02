create table articles_tags(
    article_id bigint not null,
    tag_id bigint not null,

    primary key(article_id, tag_id),
    constraint fk_articles_tags_article foreign key (article_id) references articles (id),
    constraint fk_articles_tags_tags foreign key (tag_id) references tags (id)
) engine=InnoDB default charset=UTF8MB4;

