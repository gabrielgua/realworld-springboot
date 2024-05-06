create table profiles_articles (
    profile_id bigint not null,
    article_id bigint not null,

    primary key (profile_id, article_id),
    constraint fk_profiles_articles_profiles foreign key (profile_id) references profiles (user_id),
    constraint fk_profiles_articles_articles foreign key (article_id) references articles (id)

) engine=InnoDB default charset=UTF8MB4;



