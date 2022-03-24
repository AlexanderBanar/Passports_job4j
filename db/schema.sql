create database passport;

create table passports(
                          id serial primary key,
                          fullName varchar,
                          expiration timestamp without time zone
);

insert into passports(fullName, expiration) VALUES ('Ivanov Ivan Ivanovich',	'2025-12-03');
insert into passports(fullName, expiration) VALUES ('Petrov Petr Petrovich',	'2022-04-18');
insert into passports(fullName, expiration) VALUES ('Alekseev Aleksey Alekseevich',	'2022-09-07');
insert into passports(fullName, expiration) VALUES ('Sidorova Olga Olegovna',	'2022-05-27');
insert into passports(fullName, expiration) VALUES ('Pavlova Irina Sergeevna',	'2022-06-17');

alter table passports add column serial int;
alter table passports add column number int;

update passports set serial = 1234 where id = 1;
update passports set number = 987654 where id = 1;
update passports set serial = 1234, number = 654895 where id = 2;
update passports set serial = 1235, number = 468955 where id = 3;
update passports set serial = 1235, number = 998741 where id = 4;
update passports set serial = 1235, number = 885001 where id = 5;

insert into passports(fullName, expiration) VALUES ('Antonov Anton Antonovich',	'2021-09-07');
insert into passports(fullName, expiration) VALUES ('Ivanova Oksana Igorevna',	'2022-02-20');
insert into passports(fullName, expiration) VALUES ('Petrova Elena Nikolaevna',	'2022-01-17');

update passports set serial = 1232, number = 440236 where id = 6;
update passports set serial = 1232, number = 618769 where id = 7;
update passports set serial = 1232, number = 853476 where id = 8;