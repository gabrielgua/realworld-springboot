create table users (
    id bigint not null auto_increment,
    username varchar(255) not null,
    email varchar(255) not null,
    bio varchar(255),
    password varchar(255) not null,
    token varchar(255),
    image varchar(255),

    primary key (id)

) engine=InnoDB default charset=UTF8MB4;