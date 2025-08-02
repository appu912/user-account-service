drop table users;

create table users (
    id serial primary key,
    email_id varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    full_name varchar(255)
);

select * from users;