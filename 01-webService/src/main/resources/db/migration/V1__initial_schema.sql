create table "tUser"
(
    userid bigserial not null primary key,
    name varchar(50),
    pass varchar(50),
    mail varchar(255)
);

insert into "tUser" (name, pass, mail)
values ('Petr', '123', 'Petr@dot.com');
insert into "tUser" (name, pass, mail)
values ('Basyl', 'ABC', 'XXX@YYY.com');
insert into "tUser" (name, pass, mail)
values ('Root', '@#$', 'admin@factory.com');

