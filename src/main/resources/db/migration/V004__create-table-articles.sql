create table articles (
    id bigint not null auto_increment,
    title varchar(255) not null,
    slug varchar(255) not null,
    description varchar(255) not null,
    body varchar(512) not null,
    favorites_count int not null default 0,
    created_at datetime not null,
    updated_at datetime not null,

    author_id bigint not null,

    primary key (id),
    constraint fk_articles_author foreign key (author_id) references profiles (user_id)

) engine=InnoDB default charset=UTF8MB4;

