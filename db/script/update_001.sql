CREATE TABLE IF NOT EXISTS post
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR,
    description TEXT,
    created     DATE,
    city_id     INT,
    visible     BOOLEAN
);

CREATE TABLE IF NOT EXISTS candidate
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR,
    description TEXT,
    created     DATE,
    city_id     INT,
    photo       BYTEA
);

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR,
    email    VARCHAR UNIQUE,
    password VARCHAR,
    created  DATE
);