CREATE DATABASE reservation_db;

USE reservation_db;

CREATE TABLE reservations (
    pnr INT PRIMARY KEY,
    name VARCHAR(50),
    train_no INT,
    train_name VARCHAR(50),
    class_type VARCHAR(20),
    journey_date VARCHAR(20),
    source VARCHAR(50),
    destination VARCHAR(50)
);
