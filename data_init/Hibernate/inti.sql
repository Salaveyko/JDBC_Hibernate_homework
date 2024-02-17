create database if not exists hibernate_db;
-- drop database hibernate_db;
use hibernate_db;

-- lesson 004_JPA_Hibernate
create table if not exists books
(
    id bigint primary key auto_increment,
    name varchar(50),
    price double
);

-- lesson 006_Hibernate
create table if not exists cars
(
    id bigint primary key auto_increment,
    brand varchar(50) not null,
    model varchar(50) not null,
    produced date,
    isNew boolean default true
    );

create table if not exists lots
(
    lot_id bigint primary key auto_increment,
    available int default 0,
    price double,
    car_id bigint,

    foreign key(car_id) references cars(id)
    );