drop database if exists bankaccount;

create database bankaccount;

use bankaccount;

create table BankAccount (
    id int not null auto_increment,
    fullname varchar(150) not null,
    isActive boolean,
    balance float default '100.0',
    constraint pk_bankaccount_id primary key (id)
);

insert into BankAccount (fullname, isActive, balance) values
    ('Test Account', true, 300.0);

select "Granting privileges to fred..." as msg;
grant all privileges on bankaccount.* to 'fred'@'%';
flush privileges;