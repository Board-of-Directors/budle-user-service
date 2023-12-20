create table code
(
    id           bigserial primary key,
    code         varchar,
    phone_number varchar,
    created_at   timestamp default now(),
    type         varchar not null
)
