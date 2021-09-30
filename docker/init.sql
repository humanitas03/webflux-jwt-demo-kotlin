CREATE DATABASE IF NOT EXISTS mydb;

CREATE TABLE user (
                       id bigint(20) primary key auto_increment,
                       username varchar(255) not null,
                       password varchar(255) not null,
                       nickname varchar(255) not null,
                       activated boolean null,
                       authority varchar(255) not null
)