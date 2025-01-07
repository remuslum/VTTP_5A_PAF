drop database if exists rsvp;

create database rsvp;

use rsvp;

create table guests (
    guest_id int auto_increment,
    name char(128) not null,
    email varchar(128) not null,
    phone varchar(8) not null,
    confirmation_date varchar(56) not null,
    comments char(128) not null,

    constraint pk_guest_id primary key(guest_id)
);

grant all privileges on rsvp.* to 'fred'@'%';
flush privileges;