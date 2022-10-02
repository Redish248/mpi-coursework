create table fsb_agents
(
    uid      serial PRIMARY KEY,
    name     VARCHAR(40)  NOT NULL,
    surname  VARCHAR(40)  NOT NULL,
    nick     VARCHAR(50) UNIQUE,
    password VARCHAR(100) NOT NULL
)