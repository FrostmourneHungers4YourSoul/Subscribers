CREATE TABLE IF NOT EXISTS content
(
    uuid              UUID      DEFAULT gen_random_uuid(),
    name              VARCHAR(255) NOT NULL,
    type              VARCHAR(255) NOT NULL,
    description       TEXT,
    date_created      TIMESTAMP DEFAULT now(),
    subscription_uuid UUID,

    CONSTRAINT pk_content PRIMARY KEY (uuid),

    CONSTRAINT fk_service FOREIGN KEY (subscription_uuid)
        REFERENCES subscription (uuid)
);