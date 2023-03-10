CREATE TABLE IF NOT EXISTS cities
(
    id   serial primary key,
    name varchar not null unique
);