create table notification(
    id bigserial primary key ,
    user_id bigint references users(id),
    message varchar not null ,
    was_received boolean not null
)