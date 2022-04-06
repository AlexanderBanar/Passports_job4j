create table passports(
                          id serial primary key,
                          fullName varchar,
                          expiration timestamp without time zone,
                          serial int,
                          number int,
                          birthday timestamp without time zone
);