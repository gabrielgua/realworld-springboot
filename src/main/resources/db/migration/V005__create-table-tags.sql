create table tags (
    id bigint not null auto_increment,
    name varchar(255) not null,

    primary key(id)
) engine=InnoDB default charset=UTF8MB4;