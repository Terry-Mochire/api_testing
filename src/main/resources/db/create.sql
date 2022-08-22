SET MODE PostgreSQL;

CREATE DATABASE IF NOT EXISTS mpate_daktari;
\c mpate_daktari;


CREATE SCHEMA "locations";

CREATE SCHEMA "hospitals";

CREATE SCHEMA "doctors";

CREATE TABLE IF NOT EXISTS "locations" (
  "id" serial PRIMARY KEY,
  "name" varchar
);

CREATE TABLE IF NOT EXISTS "specialties" (
  "id" serial PRIMARY KEY,
  "name" varchar
);

CREATE TABLE IF NOT EXISTS "doctors" (
  "id" serial PRIMARY KEY,
  "doc_name" varchar,
  "location_id" int,
  "specialty_id" int,
  "qualification" varchar,
  "consultation_fee" int,
  "email" varchar,
  "phone_number" varchar,
  "rating" real
);

CREATE TABLE IF NOT EXISTS "hospitals" (
  "id" serial PRIMARY KEY,
  "name" varchar,
  "operating_hours" varchar,
  "location_id" int,
  "email" varchar,
  "phone_number" varchar,
  "rating" real
);

CREATE TABLE IF NOT EXISTS"services" (
  "id" serial PRIMARY KEY,
  "name" varchar
);

CREATE TABLE IF NOT EXISTS "payments" (
  "id" serial PRIMARY KEY,
  "name" varchar
);

CREATE TABLE  IF NOT EXISTS "locations"."specialty" (
  "id" serial PRIMARY KEY,
  "specialty_id" int,
  "location_id" int
);

CREATE TABLE IF NOT EXISTS "locations"."services" (
  "id" serial PRIMARY KEY,
  "services_id" int,
  "location_id" int
);

CREATE TABLE IF NOT EXISTS "hospitals"."payment" (
  "id" serial PRIMARY KEY,
  "hospital_id" int,
  "payment_id" int
);

CREATE TABLE IF NOT EXISTS "hospitals"."services" (
  "id" serial PRIMARY KEY,
  "hospital_id" int,
  "services_id" int
);

CREATE TABLE IF NOT EXISTS "hospitals"."specialty" (
  "id" serial PRIMARY KEY,
  "hospital_id" int,
  "specialty_id" int
);

CREATE TABLE IF NOT EXISTS "hospitals"."doctors" (
  "id" serial PRIMARY KEY,
  "hospital_id" int,
  "doctor_id" int
);

CREATE TABLE IF NOT EXISTS "doctors"."payments" (
  "id" serial PRIMARY KEY,
  "doctor_id" int,
  "payment_id" int
);

CREATE TABLE IF NOT EXISTS "doctors"."specialties" (
  "id" serial PRIMARY KEY,
  "doctor_id" int,
  "specialty_id" int
);

ALTER TABLE "locations"."specialties" ADD FOREIGN KEY ("location_id") REFERENCES "locations" ("id");

ALTER TABLE "locations"."specialties" ADD FOREIGN KEY ("specialty_id") REFERENCES "specialties" ("id");

ALTER TABLE "locations"."services" ADD FOREIGN KEY ("location_id") REFERENCES "locations" ("id");

ALTER TABLE "hospitals" ADD FOREIGN KEY ("location_id") REFERENCES "locations" ("id");

ALTER TABLE "doctors" ADD FOREIGN KEY ("location_id") REFERENCES "locations" ("id");

ALTER TABLE "doctors" ADD FOREIGN KEY ("specialty_id") REFERENCES "specialties" ("id");

ALTER TABLE "hospitals"."services" ADD FOREIGN KEY ("services_id") REFERENCES "services" ("id");

ALTER TABLE "hospitals"."services" ADD FOREIGN KEY ("hospital_id") REFERENCES "hospitals" ("id");

ALTER TABLE "hospitals"."doctors" ADD FOREIGN KEY ("hospital_id") REFERENCES "hospitals" ("id");

ALTER TABLE "hospitals"."doctors" ADD FOREIGN KEY ("doctor_id") REFERENCES "doctors" ("id");

ALTER TABLE "doctors"."payments" ADD FOREIGN KEY ("payment_id") REFERENCES "payments" ("id");

ALTER TABLE "doctors"."payments" ADD FOREIGN KEY ("doctor_id") REFERENCES "doctors" ("id");

ALTER TABLE "doctors"."specialties" ADD FOREIGN KEY ("specialty_id") REFERENCES "specialties" ("id");

ALTER TABLE "doctors"."specialties" ADD FOREIGN KEY ("doctor_id") REFERENCES "doctors" ("id");

ALTER TABLE "hospitals"."payments" ADD FOREIGN KEY ("hospital_id") REFERENCES "hospitals" ("id");

ALTER TABLE "hospitals"."payments" ADD FOREIGN KEY ("payment_id") REFERENCES "payments" ("id");

ALTER TABLE "hospitals"."specialties" ADD FOREIGN KEY ("hospital_id") REFERENCES "hospitals" ("id");

ALTER TABLE "hospitals"."specialties" ADD FOREIGN KEY ("specialty_id") REFERENCES "specialties" ("id");

ALTER TABLE "services" ADD FOREIGN KEY ("id") REFERENCES "locations"."services" ("services_id");
