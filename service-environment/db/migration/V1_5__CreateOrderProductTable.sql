CREATE TABLE IF NOT EXISTS ORDERS_PRODUCT
(
    order_id   INT NOT NULL,
    product_id INT NOT NULL,
    quantity   INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES ORDERS (id),
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id)
)