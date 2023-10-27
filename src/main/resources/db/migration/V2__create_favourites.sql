create table company(
    id bigserial primary key
);


create table favourite_companies(
    company_id bigint references company(id),
    user_id bigint references users(id)
);