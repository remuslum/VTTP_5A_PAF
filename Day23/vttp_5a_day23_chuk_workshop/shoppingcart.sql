-- drop database if exists
drop database if exists shoppingcart;

-- create database
create database shoppingcart;

-- select database
use shoppingcart;

-- create checkout
select "Creating checkout table..." as msg;
create table checkout(
    checkout_id int not null,
    name char(128) not null,
    deliveryDate date not null,

    constraint pk_id primary key(checkout_id)
);

-- create person
select "Creating person table..." as msg;
create table person(
    id int auto_increment not null,
    name char(128) not null,
    address varchar(128) not null,
    checkout_id int default 0,

    constraint pk_id primary key(id),
    constraint fk_checkout_id foreign key(checkout_id)
        references checkout(checkout_id)
);

-- create cart
select "Creating cart table..." as msg;
create table cart(
    id int auto_increment,
    name varchar(64) not null,
    quantity varchar(32) not null, 
    unitPrice float(5,2) not null,
    checkout_id int default 0,

    constraint pk_id primary key(id),
    constraint fk_checkout_id_cart foreign key(checkout_id)
        references checkout(checkout_id)
);



select "Granting privileges to fred..." as msg;
grant all privileges on shoppingcart.* to 'fred'@'%';
flush privileges;