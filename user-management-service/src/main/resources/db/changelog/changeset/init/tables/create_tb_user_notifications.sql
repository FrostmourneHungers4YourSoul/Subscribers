CREATE TABLE IF NOT EXISTS user_notifications
(
    target       UUID         NOT NULL,
    publisher    VARCHAR(255) NOT NULL,
    message      VARCHAR(255) NOT NULL,
    read         BOOLEAN      NOT NULL,
    date_created TIMESTAMP DEFAULT now(),

    PRIMARY KEY (target, date_created),
    CONSTRAINT fk_user_notification FOREIGN KEY (target) REFERENCES users (uuid)
);