set foreign_key_checks = 0;

delete from users;
delete from profiles;

set foreign_key_checks = 1;

alter table users auto_increment = 1;
