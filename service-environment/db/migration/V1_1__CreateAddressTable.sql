CREATE TABLE IF NOT EXISTS ADDRESS
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    user_id      INT          NOT NULL,
    internal_id  VARCHAR(255) NOT NULL,
    address_one  VARCHAR(255) NOT NULL,
    address_two  VARCHAR(255) NOT NULL,
    city         VARCHAR(255) NOT NULL,
    state        VARCHAR(255) NOT NULL,
    country      VARCHAR(255) NOT NULL,
    postal_code  VARCHAR(255) NOT NULL,
    created      TIMESTAMP    NOT NULL,
    last_updated TIMESTAMP    NOT NULL,
    version      INT          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USER (id)
)