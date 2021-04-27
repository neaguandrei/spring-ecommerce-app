CREATE TABLE IF NOT EXISTS USER.refresh_token
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT         NOT NULL,
    token   VARCHAR(39) NOT NULL,
    created TIMESTAMP   NOT NULL,
    revoked TIMESTAMP,
    version INT         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USER.user (id)
)