CREATE TABLE IF NOT EXISTS CART.cart
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    cart_key     VARCHAR(255) UNIQUE NOT NULL,
    email        VARCHAR(50)         NOT NULL,
    products     VARCHAR(50)         NOT NULL,
    created      TIMESTAMP           NOT NULL,
    last_updated TIMESTAMP           NOT NULL,
    deleted      TIMESTAMP,
    version      INT                 NOT NULL
)