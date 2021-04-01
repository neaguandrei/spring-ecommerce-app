CREATE TABLE IF NOT EXISTS PAYMENT
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    order_id       INT,
    amount         DECIMAL      NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    created        TIMESTAMP    NOT NULL,
    last_updated   TIMESTAMP    NOT NULL,
    version        INT          NOT NULL,
    FOREIGN KEY (order_id) REFERENCES ORDERS (id)
)