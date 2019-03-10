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
values
(1, 'Fix it', true, true),
(2, 'We are burning up in here', true, false);

insert into room_maintenance_request
(maintenance_request_id, room_id)
values
(1, 1),
(2, 2);

insert into room(building_id, room_number, capacity) values
(3, 201, 15),
(3, 207, 20);

insert into room_reservation(room_id, start, finish) values
(1,'2019-01-30 07:00:00', '2019-01-30 08:00:00'),
(1,'2019-01-30 10:00:00', '2019-01-30 11:00:00'),
(1,'2019-01-30 9:00:00', '2019-01-30 09:59:00'),
(1,'2019-01-30 8:30:00', '2019-01-30 9:30:00'),
(2,'2019-01-30 9:00:00', '2019-01-30 09:59:00'),
(2,'2019-01-30 8:30:00', '2019-01-30 9:30:00')

;



--- insert facility request
insert into facility_maintenance_request
(maintenance_request_id, facility_id)
values (1, 1);

insert into facility_maintenance_schedule(facility_maintenance_request_id, start, finish) values
(1,'2019-01-30 07:00:00', '2019-01-30 08:00:00'),
(1,'2019-01-30 10:00:00', '2019-01-30 11:00:00'),
(1,'2019-01-30 9:00:00', '2019-01-30 09:59:00'),
(1,'2019-01-30 8:30:00', '2019-01-30 9:30:00');


insert into room_maintenance_schedule (room_maintenance_request_id, start, finish)
 values (1, '2019-02-24 01:00:00', '2019-02-24 02:00:00'),
 (1, '2019-02-24 05:00:00', '2019-02-24 06:00:00'),
 (1, '2019-02-25 01:00:00', '2019-04-25 02:00:00'),
 (1, '2019-02-25 04:00:00', '2019-02-25 06:00:00'),
 (2, '2019-02-25 01:00:00', '2019-04-25 02:00:00'),
 (2, '2019-02-25 04:00:00', '2019-02-25 06:00:00')
 ;


insert into facility_inspection (facility_id, completed, passed) values
(1, '2019-01-30 06:00:00', true),
(2, '2019-01-30 06:00:00', false);


commit;