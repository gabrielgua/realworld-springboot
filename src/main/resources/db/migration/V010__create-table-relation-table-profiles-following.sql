create table profiles_following (
    profile_id bigint not null,
    following_id bigint not null,

    primary key (profile_id, following_id),
    constraint fk_profiles_following_profiles foreign key (profile_id) references profiles (user_id),
    constraint fk_profiles_following_following foreign key (following_id) references profiles (user_id)

) engine=InnoDB default charset=UTF8MB4;



