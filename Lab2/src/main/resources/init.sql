drop table if exists lab_java.cars, lab_java.marks, lab_java.car_users, lab_java.receipts cascade;


create table lab_java.marks
(
    id   bigserial primary key,
    name varchar(200) not null unique
);

insert into lab_java.marks (name) values ('BMW');
insert into lab_java.marks (name) values ('Saturn');
insert into lab_java.marks (name) values ('Toyota');
insert into lab_java.marks (name) values ('Chevrolet');
insert into lab_java.marks (name) values ('Subaru');
insert into lab_java.marks (name) values ('Lexus');

create table lab_java.cars
(
    id            bigserial primary key,
    mark_id       bigint references lab_java.marks (id) on delete cascade,
    quality_class varchar(30),
    name          varchar(200) not null unique,
    base_price    int          not null
);

insert into lab_java.cars (mark_id, quality_class, name, base_price) values (6, 'BUSINESS', 'Tracer', 14040);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (4, 'BASIC', 'F250', 19866);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (3, 'PREMIUM', 'Grand Marquis', 7785);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (3, 'BUSINESS', 'Sportage', 12238);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (2, 'BASIC', 'SRX', 14436);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (3, 'PREMIUM', 'Tercel', 6152);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (6, 'BUSINESS', 'E150', 19165);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (5, 'BASIC', 'S-Class', 6174);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (4, null, 'Sierra 2500', 12622);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (2, null, 'Sonoma Club Coupe', 10947);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (6, 'BASIC', 'Accord', 18474);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (6, 'PREMIUM', 'GL-Class', 5344);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (2, 'BUSINESS', 'H1', 8667);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (5, 'BASIC', 'B-Series', 7668);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (2, null, 'SL-Class', 17260);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (2, 'BUSINESS', 'Konkord', 7658);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (1, 'BASIC', 'Challenger', 13321);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (5, 'PREMIUM', 'Impala', 6953);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (4, 'BUSINESS', 'Express 3500', 14824);
insert into lab_java.cars (mark_id, quality_class, name, base_price) values (1, null, 'Spectra', 5923);

create table lab_java.car_users
(
    id       bigserial primary key,
    username varchar(50) not null unique,
    password varchar(50) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    role varchar(15) not null
);

insert into lab_java.car_users (username, password, first_name, last_name, role)
values ('admin', 'admin', 'admin', 'admin', 'ADMIN'), ('client', 'client', 'client', 'client', 'CLIENT');


create table lab_java.receipts
(
    id bigserial primary key,
    user_id bigint not null references lab_java.car_users(id),
    car_id bigint not null references lab_java.cars(id),
    declined boolean not null default false,
    fulfilled boolean not null default false,
    driver_needed boolean not null ,
    days_number int not null default 1,
    total_price int not null
);