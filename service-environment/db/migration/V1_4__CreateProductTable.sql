CREATE TABLE IF NOT EXISTS PRODUCT
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    internal_id  VARCHAR(255) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(255) NOT NULL,
    quantity     INT          NOT NULL,
    price        INT          NOT NULL,
    category     VARCHAR(255) NOT NULL,
    product_line VARCHAR(255) NOT NULL,
    created      TIMESTAMP    NOT NULL,
    last_updated TIMESTAMP    NOT NULL,
    version      INT          NOT NULL
)