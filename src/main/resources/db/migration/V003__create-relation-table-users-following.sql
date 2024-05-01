create table users_following (
    user_id bigint not null,
    profile_id bigint not null,

    primary key (user_id, profile_id)
) engine=InnoDB default charset=UTF8MB4;

alter table users_following add constraint users_following_users
foreign key (user_id) references users (id);

alter table users_following add constraint users_following_profile
foreign key (profile_id) references profiles (user_id);