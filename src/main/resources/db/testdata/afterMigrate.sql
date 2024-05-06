set foreign_key_checks = 0;

delete from tags;
delete from users;
delete from profiles;
delete from articles;
delete from comments;
delete from articles_tags;
delete from profiles_articles;
delete from profiles_following;


set foreign_key_checks = 1;

alter table tags auto_increment = 1;
alter table users auto_increment = 1;
alter table articles auto_increment = 1;
alter table comments auto_increment = 1;

