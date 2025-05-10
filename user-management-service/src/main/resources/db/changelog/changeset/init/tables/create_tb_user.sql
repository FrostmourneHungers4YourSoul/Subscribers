CREATE TABLE users
(
    uuid       UUID         DEFAULT gen_random_uuid(),
    username   VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(255) DEFAULT 'USER',
    preference VARCHAR(255),

    CONSTRAINT pk_user PRIMARY KEY (uuid)
);

INSERT INTO users(username, email, password, preference, role)
VALUES ('user', 'user@gmail.com', 'user123', 'FILM', 'USER'),
       ('john', 'john@gmail.com', 'john123', null, 'CONTENT_MAKER'),
       ('alice', 'alice@gmail.com', 'alice123', 'PODCASTS', 'USER');
