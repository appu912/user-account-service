drop table users;

create table users (
    id serial primary key,
    email_id varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    full_name varchar(255)
);

insert into users (email_id, password, username, full_name) values ('user1@gmail.com', 'User1@123456', 'user1', 'User One');
insert into users (email_id, password, username, full_name) values ('user2@gmail.com', 'User2@123456', 'user2', 'User Two');
insert into users (email_id, password, username, full_name) values ('user3@gmail.com', 'User3@123456', 'user3', 'User Three');
insert into users (email_id, password, username, full_name) values ('user4@gmail.com', 'User4@123456', 'user4', 'User Four');
insert into users (email_id, password, username, full_name) values ('user5@gmail.com', 'User5@123456', 'user5', 'User Five');
insert into users (email_id, password, username, full_name) values ('user6@gmail.com', 'User6@123456', 'user6', 'User Six');
insert into users (email_id, password, username, full_name) values ('user7@gmail.com', 'User7@123456', 'user7', 'User Seven');
insert into users (email_id, password, username, full_name) values ('user8@gmail.com', 'User8@123456', 'user8', 'User Eight');
insert into users (email_id, password, username, full_name) values ('user9@gmail.com', 'User9@123456', 'user9', 'User Nine');
insert into users (email_id, password, username, full_name) values ('user10@gmail.com', 'User10@123456', 'user10', 'User Ten');
insert into users (email_id, password, username, full_name) values ('user11@gmail.com', 'User11@123456', 'user11', 'User Eleven');
insert into users (email_id, password, username, full_name) values ('user12@gmail.com', 'User12@123456', 'user12', 'User Twelve');
insert into users (email_id, password, username, full_name) values ('user13@gmail.com', 'User13@123456', 'user13', 'User Thirteen');
insert into users (email_id, password, username, full_name) values ('user14@gmail.com', 'User14@123456', 'user14', 'User Fourteen');
insert into users (email_id, password, username, full_name) values ('user15@gmail.com', 'User15@123456', 'user15', 'User Fifteen');


select * from users;