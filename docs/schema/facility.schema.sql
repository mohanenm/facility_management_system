drop table if exists room_maintenance_schedule;
drop table if exists room_reservation;
drop table if exists room_maintenance_request;
drop table if exists facility_maintenance_schedule;
drop table if exists facility_maintenance_request;
drop table if exists maintenance_request;
drop table if exists maintenance_rate;
drop table if exists maintenance_type;
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

create table maintenance_rate (
	id SERIAL PRIMARY KEY,
	facility_id INTEGER REFERENCES facility(id) NOT NULL,
	maintenance_type_id INTEGER REFERENCES maintenance_type(id) NOT NULL,
	rate DOUBLE PRECISION DEFAULT 15.00
);

create table maintenance_request (
	id SERIAL PRIMARY KEY,
	maintenance_type_id INTEGER REFERENCES maintenance_type(id) NOT NULL,
	description varchar(250)NOT NULL,
	is_vacate_required BOOLEAN DEFAULT false,
	is_routine BOOLEAN DEFAULT true
);

create table facility_maintenance_request (
	id SERIAL PRIMARY KEY,
	maintenance_request_id INTEGER REFERENCES maintenance_request(id) NOT NULL,
	facility_id INTEGER REFERENCES facility(id)
);

---Refers to facility wide maintenance requests
create table facility_maintenance_schedule (
	id SERIAL PRIMARY KEY,
	facility_maintenence_request_id INTEGER REFERENCES facility_maintenance_request(id) NOT NULL,
	start TIMESTAMP NOT NULL,
	finish TIMESTAMP NOT NULL
);

create table room_maintenance_request (
	id SERIAL PRIMARY KEY,
	maintenance_request_id INTEGER REFERENCES maintenance_type(id) NOT NULL,
	room_id INTEGER REFERENCES room(id)
);

create table room_reservation (
	id SERIAL PRIMARY KEY,
	room_id INTEGER REFERENCES room(id) NOT NULL,
	start TIMESTAMP NOT NULL,
	finish TIMESTAMP NOT NULL,
	maintenance_request_id INTEGER REFERENCES maintenance_request(id)
);

create table room_maintenance_schedule (
	id SERIAL PRIMARY KEY,
	room_maintenance_request_id INTEGER REFERENCES room_maintenance_request(id) NOT NULL,
	start TIMESTAMP NOT NULL,
	finish TIMESTAMP NOT NULL
);

insert into facility (name, description) values('F1', 'Health & Fitness');
insert into facility (name, description) values('F2', 'Human Resources');
insert into facility (name, description) values('F3', 'Information Technology');
insert into facility (name, description) values('F4', 'Electrical and Wiring');
insert into facility (name, description) values('F5', 'Plumbing');
insert into building(facility_id, name, street_address, city, state, zip) values
(2,'b1', '555 North Jefferson Ave', 'Hershey', 'PA', 55555),
(2,'b2', '555 West Washington Lane', 'Hershey', 'PA', 55555),
(1,'b1', '1035 Ashley Way', 'Sacramento', 'CA', 77777),
(1,'b4', '8340 Dingleberry Bvd', 'Sacramento', 'CA', 77777),
(3,'b5', '1234 Hummingbird Dr', 'Abequerque', 'NM', 88888);
insert into room (building_id, room_number) values
(1, 101),
(1, 102),
(1, 103),
(1, 104),
(2, 101),
(2, 102),
(2, 201),
(3, 101),
(3, 201),
(3, 301);

insert into maintenance_request
(maintenance_type_id, description, is_vacate_required, is_routine)
values (1, 'Fix it', true, true);

insert into room_maintenance_request
(maintenance_request_id, room_id)
values (1, 1);


insert into room(building_id, room_number, capacity) values
(3, 201, 15),
(3, 207, 20);

insert into room_reservation(room_id, start, finish) values
(1,'2019-01-30 07:00:00', '2019-01-30 08:00:00'),
(1,'2019-01-30 10:00:00', '2019-01-30 11:00:00'),
(1,'2019-01-30 9:00:00', '2019-01-30 09:59:00'),
(1,'2019-01-30 8:30:00', '2019-01-30 9:30:00');



--- insert facility request
insert into facility_maintenance_request
(maintenance_request_id, facility_id)
values (1, 1);

insert into facility_maintenance_schedule(facility_maintenence_request_id, start, finish) values
(1,'2019-01-30 07:00:00', '2019-01-30 08:00:00'),
(1,'2019-01-30 10:00:00', '2019-01-30 11:00:00'),
(1,'2019-01-30 9:00:00', '2019-01-30 09:59:00'),
(1,'2019-01-30 8:30:00', '2019-01-30 9:30:00');


insert into room_maintenance_schedule (room_maintenance_request_id, start, finish)
 values (1, '2019-02-24 01:00:00', '2019-02-24 02:00:00'),
 (1, '2019-02-24 05:00:00', '2019-02-24 06:00:00'),
 (1, '2019-02-25 01:00:00', '2019-04-25 02:00:00'),
 (1, '2019-02-25 04:00:00', '2019-02-25 06:00:00');


insert into facility_inspection (facility_id, completed, passed) values
(1, '2019-01-30 06:00:00', true),
(2, '2019-01-30 06:00:00', false);




commit;