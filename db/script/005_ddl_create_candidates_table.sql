CREATE TABLE IF NOT EXISTS candidates
(
    id          serial primary key,
    name        varchar not null,
    description varchar not null,
    created     timestamp,
    city_id     int references cities (id),
    file_id     int references files (id)
);