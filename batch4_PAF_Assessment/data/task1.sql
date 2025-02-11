-- Write your Task 1 answers in this file

-- drop database if exists
DROP DATABASE IF EXISTS bedandbreakfast;

-- create database
CREATE DATABASE bedandbreakfast;

-- select database
USE bedandbreakfast;

-- create users
SELECT "Creating users table" AS msg;
CREATE TABLE users(
    email VARCHAR(128),
    name VARCHAR(128),

    CONSTRAINT pk_users_email_id PRIMARY KEY(email)
);

-- create bookings
SELECT "Creating bookings table" AS msg;
CREATE TABLE bookings(
    booking_id CHAR(8),
    listing_id VARCHAR(20),
    duration INT,
    email VARCHAR(128),

    CONSTRAINT pk_booking_id PRIMARY KEY(booking_id),
    CONSTRAINT fk_email FOREIGN KEY(email) REFERENCES users(email)
);

-- create reviews
SELECT "Creating reviews table" AS msg;
CREATE TABLE reviews(
    id INT AUTO_INCREMENT,
    date TIMESTAMP,
    listing_id VARCHAR(20),
    reviewer_name VARCHAR(64),
    comments TEXT,

    CONSTRAINT pk_id PRIMARY KEY(id)
);

-- grant privileges to fred
SELECT "Granting privileges to fred..." AS msg;
GRANT ALL PRIVILEGES ON bedandbreakfast.* TO 'fred'@'%';
FLUSH PRIVILEGES;

-- insert values
SELECT "Inserting values to users..." AS msg;
INSERT INTO users (email,name) VALUES 
    ("fred@gmail.com", "Fred Flintstone"),
    ("barney@gmail.com", "Barney Rubble"),
    ("fry@planetexpress.com", "Philip J Fry"),
    ("hlmer@gmail.com", "Homer Simpson");