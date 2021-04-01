CREATE TABLE IF NOT EXISTS ORDERS
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    user_id      INT          NOT NULL,
    comment      VARCHAR(255),
    status       VARCHAR(255) NOT NULL,
    created      TIMESTAMP    NOT NULL,
    last_updated TIMESTAMP    NOT NULL,
    version      INT          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USER (id)
)