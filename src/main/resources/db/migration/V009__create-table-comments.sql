create table comments (
    id bigint not null auto_increment,
    created_at datetime not null,
    updated_at datetime not null,
    body varchar(255) not null,

    author_id bigint not null,
    article_id bigint not null,

    primary key (id),
    constraint fk_comments_author foreign key (author_id) references profiles (user_id),
    constraint fk_comments_article foreign key (article_id) references articles (id)
) engine=InnoDB default charset=UTF8MB4;