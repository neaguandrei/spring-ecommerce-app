CREATE TABLE IF NOT EXISTS CART.cart
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    cart_key     VARCHAR(255) UNIQUE NOT NULL,
    email        INT                 NOT NULL,
    products     INT UNIQUE          NOT NULL,
    created      TIMESTAMP           NOT NULL,
    last_updated TIMESTAMP           NOT NULL,
    version      INT                 NOT NULL
)