CREATE TABLE IF NOT EXISTS payment
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    order_id       INT UNIQUE,
    amount         DECIMAL      NOT NULL,
    currency       VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    created        TIMESTAMP    NOT NULL,
    last_updated   TIMESTAMP    NOT NULL,
    version        INT          NOT NULL,
    FOREIGN KEY (order_id) REFERENCES payment (id)
)