CREATE TABLE post
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50),
    description TEXT,
    created     DATE,
    city_id     INT,
    city_name   VARCHAR(100)
);