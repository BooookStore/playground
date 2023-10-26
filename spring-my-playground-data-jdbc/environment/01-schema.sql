create database playground;

\connect playground;

create table "coffee"
(
    id varchar(255) primary key
);

create table "coffee_bean"
(
    coffee varchar(255) primary key,
    name      varchar(255) not null,
    foreign key (coffee) references coffee (id)
);
