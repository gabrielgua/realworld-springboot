create table users_articles (
    user_id bigint not null,
    article_id bigint not null,

    primary key(user_id, article_id),
    constraint fk_profiles_articles_users foreign key (user_id) references users (id),
    constraint fk_profiles_articles_article foreign key (article_id) references articles (id)
) engine=InnoDB default charset=UTF8MB4;