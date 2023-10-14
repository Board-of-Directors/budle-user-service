DROP TABLE IF EXISTS users cascade;

CREATE TABLE users
(
    id           bigserial,
    username     varchar(30) not null unique,
    phone_number varchar(30) not null unique,
    password     varchar     not null,
    primary key (id)

);
