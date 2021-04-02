CREATE TABLE IF NOT EXISTS user
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    email        VARCHAR(255) UNIQUE NOT NULL,
    password     VARCHAR(255)        NOT NULL,
    first_name   VARCHAR(255)        NOT NULL,
    last_name    VARCHAR(255)        NOT NULL,
    phone        VARCHAR(255)        NOT NULL,
    created      TIMESTAMP           NOT NULL,
    last_updated TIMESTAMP           NOT NULL,
    version      INT                 NOT NULL
)