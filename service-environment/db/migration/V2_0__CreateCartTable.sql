CREATE TABLE IF NOT EXISTS cart
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    cart_key     VARCHAR(255) UNIQUE NOT NULL,
    product_id   INT UNIQUE          NOT NULL,
    quantity     INT                 NOT NULL,
    user_id      INT                 NOT NULL,
    created      TIMESTAMP           NOT NULL,
    last_updated TIMESTAMP           NOT NULL,
    version      INT                 NOT NULL
)