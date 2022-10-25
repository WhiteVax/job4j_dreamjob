CREATE TABLE post
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR,
    description TEXT,
    created     DATE,
    city_id     INT
);