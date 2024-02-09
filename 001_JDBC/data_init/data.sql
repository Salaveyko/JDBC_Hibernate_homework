use my_db;

insert into employees
(name, phone)
values
("Alehandro", "+380(96)7023810"),
("Kristina", "+380(68)7243342"),
("Sofia", "+380(96)2223343"),
("Andriy", "+380(98)4322353"),
("Zubik", "+380(96)1221122");

insert into positions
(employee_id, position, salary)
values
(1, "Developer", 100000),
(2, "Manager", 50000),
(3, "Manager", 50000),
(4, "Driver", 30000),
(5, "Cleaner", 15000);

insert into info
(employee_id, address, birthdate, married)
values
(1, "Kotliarevskogo", "1993-02-15", true),
(2, "Gukovskogo", "2014-12-23", false),
(3, "Bandery", "2016-01-01", false),
(4, "Soborna", "1999-04-18", true),
(5, "Zamkova", "2001-05-27", false);