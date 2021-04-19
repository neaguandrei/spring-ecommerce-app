CREATE TABLE IF NOT EXISTS CATALOG.orders_product
(
    order_id   INT NOT NULL,
    product_id INT NOT NULL,
    quantity   INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES CATALOG.orders (id),
    FOREIGN KEY (product_id) REFERENCES CATALOG.product (id)
)