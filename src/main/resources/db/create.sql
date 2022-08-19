SET MODE PostgreSQL;

CREATE DATABASE IF NOT EXISTS mpate_daktari;
\c mpate_daktari;

SET AUTOCOMMIT = ON;

CREATE SCHEMA "location";

CREATE SCHEMA "hospitals";

CREATE SCHEMA "doctors";

CREATE TABLE IF NOT EXISTS "location" (
  "id" int,
  "name" varchar
);

CREATE TABLE IF NOT EXISTS "specialty" (
  "id" int,
  "name" varchar
);

CREATE TABLE IF NOT EXISTS "doctors" (
  "id" int,
  "name" varchar,
  "locationID" int,
  "specialtyID" int,
  "qualification" varchar,
  "consultationFee" int,
  "email" varchar,
  "phoneNumber" varchar,
  "rating" int
);

CREATE TABLE IF NOT EXISTS "hospitals" (
  "id" int,
  "name" varchar,
  "operatingHours" varchar,
  "locationID" int,
  "email" varchar,
  "phoneNumber" varchar,
  "rating" int
);

CREATE TABLE IF NOT EXISTS"services" (
  "id" int,
  "name" varchar
);

CREATE TABLE IF NOT EXISTS "payment" (
  "id" int,
  "name" varchar
);

CREATE TABLE  IF NOT EXISTS "location"."specialty" (
  "id" int,
  "specialtyId" int,
  "locationID" int
);

CREATE TABLE IF NOT EXISTS "location"."services" (
  "id" int,
  "servicesId" int,
  "locationID" int
);

CREATE TABLE IF NOT EXISTS "hospitals"."payment" (
  "id" int,
  "hospitalID" int,
  "paymentID" int
);

CREATE TABLE IF NOT EXISTS "hospitals"."services" (
  "id" int,
  "hospitalID" int,
  "servicesID" int
);

CREATE TABLE IF NOT EXISTS "hospitals"."specialty" (
  "id" int,
  "hospitalID" int,
  "specialtyID" int
);

CREATE TABLE IF NOT EXISTS "hospitals"."doctors" (
  "id" int,
  "hospitalID" int,
  "doctorID" int
);

CREATE TABLE IF NOT EXISTS "doctors"."payment" (
  "id" int,
  "doctorID" int,
  "paymentID" int
);

ALTER TABLE "location"."specialty" ADD FOREIGN KEY ("locationID") REFERENCES "location" ("id");

ALTER TABLE "location"."specialty" ADD FOREIGN KEY ("specialtyId") REFERENCES "specialty" ("id");

ALTER TABLE "location"."services" ADD FOREIGN KEY ("locationID") REFERENCES "location" ("id");

ALTER TABLE "hospitals" ADD FOREIGN KEY ("locationID") REFERENCES "location" ("id");

ALTER TABLE "doctors" ADD FOREIGN KEY ("locationID") REFERENCES "location" ("id");

ALTER TABLE "doctors" ADD FOREIGN KEY ("specialtyID") REFERENCES "specialty" ("id");

ALTER TABLE "hospitals"."services" ADD FOREIGN KEY ("servicesID") REFERENCES "services" ("id");

ALTER TABLE "hospitals"."services" ADD FOREIGN KEY ("hospitalID") REFERENCES "hospitals" ("id");

ALTER TABLE "hospitals"."doctors" ADD FOREIGN KEY ("hospitalID") REFERENCES "hospitals" ("id");

ALTER TABLE "hospitals"."doctors" ADD FOREIGN KEY ("doctorID") REFERENCES "doctors" ("id");

ALTER TABLE "doctors"."payment" ADD FOREIGN KEY ("paymentID") REFERENCES "payment" ("id");

ALTER TABLE "doctors"."payment" ADD FOREIGN KEY ("doctorID") REFERENCES "doctors" ("id");

ALTER TABLE "hospitals"."payment" ADD FOREIGN KEY ("hospitalID") REFERENCES "hospitals" ("id");

ALTER TABLE "hospitals"."payment" ADD FOREIGN KEY ("paymentID") REFERENCES "payment" ("id");

ALTER TABLE "hospitals"."specialty" ADD FOREIGN KEY ("hospitalID") REFERENCES "hospitals" ("id");

ALTER TABLE "hospitals"."specialty" ADD FOREIGN KEY ("specialtyID") REFERENCES "specialty" ("id");

ALTER TABLE "services" ADD FOREIGN KEY ("id") REFERENCES "location"."services" ("servicesId");
