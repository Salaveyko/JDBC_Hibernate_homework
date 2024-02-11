create database if not exists hibernate_db;

use hibernate_db;

create table if not exists books
(
	id bigint primary key auto_increment,
    name varchar(50),
    price double
);