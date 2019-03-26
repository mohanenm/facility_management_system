drop table if exists room_maintenance_schedule;
drop table if exists room_reservation;
drop table if exists room_maintenance_request;
drop table if exists facility_maintenance_schedule;
drop table if exists facility_maintenance_request;
drop table if exists maintenance_request;
drop table if exists maintenance_rate;
drop table if exists maintenance_hourly_rate;
--drop table if exists maintenance_type;
drop table if exists facility_inspection;
drop table if exists room;
drop table if exists building;
drop table if exists facility;


create table facility (
	id SERIAL PRIMARY KEY,
	name varchar(128) not null UNIQUE,
	description varchar(128) not null
);

create table building(
	id SERIAL PRIMARY KEY,
	facility_id INTEGER REFERENCES facility(id) ON DELETE CASCADE NOT NULL,
	name varchar(32) NOT NULL,
	street_address varchar(128),
	city varchar(32),
	state varchar(2),
	zip integer
);

create table room (
	id SERIAL PRIMARY KEY,
	building_id INTEGER REFERENCES building(id) ON DELETE CASCADE NOT NULL,
	room_number INTEGER NOT NULL,
	capacity INTEGER default 10
);

create table facility_inspection (
	id SERIAL PRIMARY KEY,
	facility_id INTEGER REFERENCES facility(id) ON DELETE CASCADE NOT NULL,
	completed TIMESTAMP NOT NULL,
	passed BOOLEAN NOT NULL
);

create table maintenance_type (
	id SERIAL PRIMARY KEY,
	description varchar(128) NOT NULL
);

insert into maintenance_type (description) values
('Heating System'),
('Air Conditioning System'),
('Plumbing Issue'),
('Animal Services'),
('Audio Visual'),
('Ghostbuster - Removal'),
('Blown Transformer'),
('Paint Required');

create table maintenance_hourly_rate (
	id SERIAL PRIMARY KEY,
	facility_id INTEGER REFERENCES facility(id) ON DELETE CASCADE NOT NULL,
	maintenance_type_id INTEGER REFERENCES maintenance_type(id) ON DELETE CASCADE NOT NULL,
	hourly_rate DOUBLE PRECISION DEFAULT 15.00
);

create table maintenance_request (
	id SERIAL PRIMARY KEY,
	maintenance_type_id INTEGER REFERENCES maintenance_type(id)  ON DELETE CASCADE NOT NULL,
	description varchar(250)NOT NULL,
	is_vacate_required BOOLEAN DEFAULT false,
	is_routine BOOLEAN DEFAULT true
);

create table facility_maintenance_request (
	id SERIAL PRIMARY KEY,
	maintenance_request_id INTEGER REFERENCES maintenance_request(id) ON DELETE CASCADE NOT NULL,
	facility_id INTEGER REFERENCES facility(id) ON DELETE CASCADE NOT NULL
);

---Refers to facility wide maintenance requests
create table facility_maintenance_schedule (
	id SERIAL PRIMARY KEY,
	facility_maintenance_request_id INTEGER REFERENCES facility_maintenance_request(id) ON DELETE CASCADE NOT NULL,
	start TIMESTAMP NOT NULL,
	finish TIMESTAMP NOT NULL
);

create table room_maintenance_request (
	id SERIAL PRIMARY KEY,
	maintenance_request_id INTEGER REFERENCES maintenance_request(id) ON DELETE CASCADE NOT NULL,
	room_id INTEGER REFERENCES room(id) ON DELETE CASCADE NOT NULL
);

create table room_reservation (
	id SERIAL PRIMARY KEY,
	room_id INTEGER REFERENCES room(id) ON DELETE CASCADE NOT NULL,
	start TIMESTAMP NOT NULL,
	finish TIMESTAMP NOT NULL,
	maintenance_request_id INTEGER REFERENCES maintenance_request(id) ON DELETE CASCADE
);

create table room_maintenance_schedule (
	id SERIAL PRIMARY KEY,
	room_maintenance_request_id INTEGER REFERENCES room_maintenance_request(id) ON DELETE CASCADE NOT NULL,
	start TIMESTAMP NOT NULL,
	finish TIMESTAMP NOT NULL
);
