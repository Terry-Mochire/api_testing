CREATE DATABASE mpate_daktari;
\c mpate_daktari;

CREATE TABLE location (
id SERIAL PRIMARY KEY,
name VARCHAR,
hospitals INT,
doctors INT,
services INT,
speciality INT
);

CREATE TABLE doctors (
id SERIAL PRIMARY KEY,
name VARCHAR,
speciality VARCHAR,
experience VARCHAR,
hospitalid INT,
locationid INT
);

CREATE TABLE hospitals (
id SERIAL PRIMARY KEY,
locationid INT,
doctors INT,
services INT,
operatinghours BIGINT,
);

CREATE TABLE speciality (
id SERIAL PRIMARY KEY,
type VARCHAR,
doctors INT,
hospitals INT,
locationid INT
);

CREATE TABLE services (
id SERIAL PRIMARY KEY,
service VARCHAR,
locationid INT,
hospitals INT
);
