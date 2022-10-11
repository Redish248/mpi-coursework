CREATE TABLE admin (
                       uid serial PRIMARY KEY,
                       name VARCHAR(40) NOT NULL,
                       surname VARCHAR(40) NOT NULL,
                       salary INTEGER CHECK (salary >= 0),
                       nick VARCHAR(50) UNIQUE,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE user_role (
                           uid serial PRIMARY KEY,
                           name VARCHAR(50) NOT NULL
);

CREATE TABLE users (
                       uid serial PRIMARY KEY,
                       name VARCHAR(40) NOT NULL,
                       surname VARCHAR(40) NOT NULL,
                       nick VARCHAR(50) UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       birth_date DATE,
                       user_type BIGINT NOT NULL REFERENCES user_role,
                       email VARCHAR(100),
                       phone VARCHAR(20),
                       is_pirate BOOLEAN,
                       is_activated BOOLEAN,
                       is_vip BOOLEAN
);

CREATE TABLE island (
                        uid serial PRIMARY KEY,
                        name VARCHAR(40) NOT NULL,
                        x_coordinate INTEGER NOT NULL,
                        y_coordinate INTEGER NOT NULL,
                        has_pirates BOOLEAN
);

CREATE TABLE crew (
                      uid serial PRIMARY KEY,
                      team_name VARCHAR(50),
                      crew_owner BIGINT NOT NULL REFERENCES users,
                      price_per_day INTEGER NOT NULL,
                      rates_number INTEGER NOT NULL,
                      rates_average FLOAT(8) NOT NULL,
                      description TEXT DEFAULT 'Краткое описание для привлечения внимания клиентов'
);

CREATE TABLE crew_member (
                             uid serial PRIMARY KEY,
                             crew BIGINT NOT NULL REFERENCES crew,
                             member_id BIGINT NOT NULL REFERENCES users
);

CREATE TABLE ship (
                      uid serial PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      owner BIGINT NOT NULL REFERENCES users,
                      description TEXT DEFAULT 'Краткое описание для привлечения внимания клиентов',
                      speed INTEGER CHECK (speed >= 0),
                      capacity INTEGER CHECK (capacity >= 0),
                      fuel_consumption INTEGER,
                      length INTEGER CHECK (length >= 0),
                      width INTEGER CHECK (width >= 0),
                      price_per_day INTEGER NOT NULL,
                      rates_number INTEGER NOT NULL,
                      rates_average FLOAT(8) NOT NULL
);

CREATE TABLE trip_request (
                              uid serial PRIMARY KEY,
                              traveler BIGINT NOT NULL REFERENCES users,
                              date_start DATE,
                              date_end DATE,
                              island_start BIGINT NOT NULL REFERENCES island,
                              island_end BIGINT NOT NULL REFERENCES island,
                              status VARCHAR(20),
                              ship_id BIGINT REFERENCES ship,
                              crew_id BIGINT REFERENCES crew,
                              cost INTEGER CHECK (cost >= 0)
);