drop table if exists cars, marks, car_users cascade;


create table marks
(
    id   bigserial primary key,
    name varchar(200) not null unique
);

insert into marks (name) values ('BMW');
insert into marks (name) values ('Saturn');
insert into marks (name) values ('Toyota');
insert into marks (name) values ('Chevrolet');
insert into marks (name) values ('Subaru');
insert into marks (name) values ('Lexus');

create table cars
(
    id            bigserial primary key,
    mark_id       bigint references marks (id) on delete cascade,
    quality_class varchar(30),
    name          varchar(200) not null unique,
    base_price    int          not null
);

insert into cars (mark_id, quality_class, name, base_price) values (6, 'BUSINESS', 'Tracer', 14040);
insert into cars (mark_id, quality_class, name, base_price) values (4, 'BASIC', 'F250', 19866);
insert into cars (mark_id, quality_class, name, base_price) values (3, 'PREMIUM', 'Grand Marquis', 7785);
insert into cars (mark_id, quality_class, name, base_price) values (3, 'BUSINESS', 'Sportage', 12238);
insert into cars (mark_id, quality_class, name, base_price) values (2, 'BASIC', 'SRX', 14436);
insert into cars (mark_id, quality_class, name, base_price) values (3, 'PREMIUM', 'Tercel', 6152);
insert into cars (mark_id, quality_class, name, base_price) values (6, 'BUSINESS', 'E150', 19165);
insert into cars (mark_id, quality_class, name, base_price) values (5, 'BASIC', 'S-Class', 6174);
insert into cars (mark_id, quality_class, name, base_price) values (4, null, 'Sierra 2500', 12622);
insert into cars (mark_id, quality_class, name, base_price) values (2, null, 'Sonoma Club Coupe', 10947);
insert into cars (mark_id, quality_class, name, base_price) values (6, 'BASIC', 'Accord', 18474);
insert into cars (mark_id, quality_class, name, base_price) values (6, 'PREMIUM', 'GL-Class', 5344);
insert into cars (mark_id, quality_class, name, base_price) values (2, 'BUSINESS', 'H1', 8667);
insert into cars (mark_id, quality_class, name, base_price) values (5, 'BASIC', 'B-Series', 7668);
insert into cars (mark_id, quality_class, name, base_price) values (2, null, 'SL-Class', 17260);
insert into cars (mark_id, quality_class, name, base_price) values (2, 'BUSINESS', 'Konkord', 7658);
insert into cars (mark_id, quality_class, name, base_price) values (1, 'BASIC', 'Challenger', 13321);
insert into cars (mark_id, quality_class, name, base_price) values (5, 'PREMIUM', 'Impala', 6953);
insert into cars (mark_id, quality_class, name, base_price) values (4, 'BUSINESS', 'Express 3500', 14824);
insert into cars (mark_id, quality_class, name, base_price) values (1, null, 'Spectra', 5923);

create table car_users
(
    id       bigserial primary key,
    username varchar(50) not null unique,
    password varchar(50) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    role varchar(15) not null
);

insert into car_users (username, password, first_name, last_name, role)
values ('admin', 'admin', 'admin', 'admin', 'ADMIN');
