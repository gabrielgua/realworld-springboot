create table profiles (
    user_id bigint not null,
    username varchar(255) not null,
    bio varchar(255),
    image varchar(255),

    primary key(user_id)

) engine=InnoDB default charset=UTF8MB4;

alter table profiles add constraint fk_profiles_user
foreign key (user_id) references users (id);