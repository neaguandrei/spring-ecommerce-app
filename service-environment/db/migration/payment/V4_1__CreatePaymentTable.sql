CREATE TABLE IF NOT EXISTS PAYMENT.payment
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT,
    amount         DECIMAL      NOT NULL,
    currency       VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    description    VARCHAR(255) NOT NULL,
    created        TIMESTAMP    NOT NULL,
    last_updated   TIMESTAMP    NOT NULL,
    version        INT          NOT NULL
)