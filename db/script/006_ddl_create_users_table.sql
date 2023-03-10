CREATE TABLE IF NOT EXISTS users
(
    id       serial primary key,
    email    varchar unique not null,
    name     varchar        not null,
    password varchar        not null,
    created  timestamp
);