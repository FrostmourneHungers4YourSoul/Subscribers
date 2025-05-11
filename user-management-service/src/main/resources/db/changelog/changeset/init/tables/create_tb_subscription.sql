CREATE TABLE IF NOT EXISTS subscription
(
    uuid  UUID DEFAULT gen_random_uuid(),
    name  VARCHAR(255) NOT NULL UNIQUE,
    price DOUBLE PRECISION DEFAULT 0.00,

    CONSTRAINT pk_service PRIMARY KEY (uuid)
);

CREATE TABLE users_subscriptions
(
    subscription_uuid UUID,
    user_uuid         UUID,
    date_time         TIMESTAMP DEFAULT now(),

    CONSTRAINT pk_subscriptions PRIMARY KEY (user_uuid, subscription_uuid),

    CONSTRAINT fk_usr_ser_on_service FOREIGN KEY (subscription_uuid)
        REFERENCES subscription (uuid),

    CONSTRAINT fk_usr_ser_on_user FOREIGN KEY (user_uuid)
        REFERENCES users (uuid)
);

INSERT INTO subscription(name, price)
VALUES ('YouTube Premium', 13.99),
       ('VK Музыка', 3.00),
       ('Яндекс.Плюс', 4.00),
       ('Netflix', 7.99);
