use acme;

create table dept (
    dept_id char(3) not null,
    name varchar(64) not null,

    constraint pk_dept_id primary key(dept_id)
);

create table employee(
    employee_id char(8) not null,
    name varchar(64) not null,
    dept_id char(3) not null,

    constraint pk_emp_id primary key(employee_id),
    constraint fk_dept_id foreign key(dept_id) 
        references dept(dept_id)
);

