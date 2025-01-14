drop database if exists orders;

create database orders;

use orders;

select "Creating orders table..." as msg;
create table orders(
    order_id int auto_increment,
    order_date date not null,
    customer_name varchar(128) not null,
    ship_address varchar(128) not null,
    notes text not null,
    tax decimal(2,2) default 0.05,

    constraint pk_order_id primary key(order_id)
);

select "Creating order_details table..." as msg;
create table order_details(
    id int auto_increment,
    product varchar(64) not null,
    unit_price decimal(3,2) not null,
    discount decimal(3,2) default 1.0,
    quantity int not null,
    order_id int not null,

    constraint pk_order_details_id primary key(id),
    constraint fk_order_id foreign key(order_id) references orders(order_id)
);

select "Granting fred privileges..." as msg;
grant all privileges on orders.* to 'fred'@'%';
flush privileges;