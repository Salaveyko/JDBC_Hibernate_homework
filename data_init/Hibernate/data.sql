use hibernate_db;

-- lesson 006_Hibernate
insert into cars
(brand, model, produced, isNew)
values
('Mercedes', 'S 580e', '2022-07-05', true),
('Audi', 'A6', '2022-07-05', true),
('Audi', 'A7', '2021-08-05', true),
('Volkswagen', 'Golf', '2016-04-05', false),
('BMW', 'X6', '2020-05-05', true),
('BMW', 'X5', '2016-05-05', false);

insert into lots
(available, price, car_id)
values
(5, 30000, 1),
(6, 27000, 2),
(4, 29000, 3),
(1, 4000, 4),
(3, 31000, 5),
(1, 14000, 6);
