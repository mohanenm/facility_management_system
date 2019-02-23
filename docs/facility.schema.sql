drop table if exists facility_reservation;
drop table if exists room;
drop table if exists building;
drop table if exists facility;

create table facility(
	id SERIAL PRIMARY KEY,
	name varchar(128) not null,
	description varchar(128) not null
);

create table building(
	id SERIAL PRIMARY KEY,
	facility_id INTEGER REFERENCES facility(id),
	street_address varchar(128),
	city varchar(32),
	state varchar(2),
	zip integer
);

create table room(
	id SERIAL PRIMARY KEY,
	building_id INTEGER REFERENCES building(id) NOT NULL,
	room_number INTEGER
);

create table facility_reservation(
	id SERIAL PRIMARY KEY,
	facility_id INTEGER REFERENCES facility(id) NOT NULL,
	start TIMESTAMP,
	finish TIMESTAMP
);

insert into facility (name, description) values('F1', 'Health & Fitness');
insert into facility (name, description) values('F2', 'Human Resources');
insert into facility (name, description) values('F3', 'Information Technology');
insert into facility (name, description) values('F4', 'Electrical and Wiring');
insert into facility (name, description) values('F5', 'Plumbing');
insert into building(facility_id, street_address, city, state, zip) values
(2, '555 North Jefferson Ave', 'Hershey', 'PA', 55555),
(2, '555 West Washington Lane', 'Hershey', 'PA', 55555),
(1, '1035 Ashley Way', 'Sacramento', 'CA', 77777),
(1, '8340 Dingleberry Bvd', 'Sacramento', 'CA', 77777),
(3, '1234 Hummingbird Ln', 'Abequerque', 'NM', 88888);
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
insert into facility_reservation(facility_id, start, finish) values
(1,'2019-01-30 07:00:00', '2019-01-30 08:00:00'),
(1,'2019-01-30 10:00:00', '2019-01-30 11:00:00'),
(1,'2019-01-30 9:00:00', '2019-01-30 09:59:00'),
(1,'2019-01-30 8:30:00', '2019-01-30 9:30:00');

