-- drop database if exists
drop database if exists mybnb;

-- create database
create database mybnb;

-- select database
use mybnb;

-- create acc_occupancy
select "Creating acc_occupancy table" as msg;
create table acc_occupancy(
    acc_id varchar(10) not null,
    vacancy int not null,

    constraint pk_acc_id primary key(acc_id)
);

-- create reservations
select "Creating reservations table" as msg;
create table reservations(
    resv_id varchar(8) not null,
    name char(128) not null,
    email varchar(128) not null,
    acc_id varchar(10) not null,
    arrival_date date not null,
    duration int not null,

    constraint pk_resv_id primary key(resv_id),
    constraint fk_acc_id foreign key(acc_id)
        references acc_occupancy(acc_id)
);

select "Granting privileges to fred..." as msg;
grant all privileges on mybnb.* to 'fred'@'%';
flush privileges;
