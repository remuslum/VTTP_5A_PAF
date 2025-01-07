-- drop database if exists
drop database if exists acme;

-- create database
create database acme;

-- select database
use acme;

-- create one or more tables
create table employees (
    emp_id char(8) not null, -- this is the PK
    name varchar(128) not null,
    salary decimal(10, 2) default 4500,
    dob date not null,
    dept_id int,

    constraint pk_emp_id primary key (emp_id),
    constraint chk_salary check(salary >= 1500)
);

-- create department table
select "Creating dept table..." as msg;
create table dept (
    dept_id int auto_increment,
    name varchar(64) not null,

    constraint pk_dept_id primary key(dept_id)

);

select "Grant privileges to fred" as msg;
grant all privileges on acme.* to 'fred'@'%';
flush privileges;