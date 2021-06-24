INSERT INTO CATALOG.orders (user_id, payment_id, comment, status, created, last_updated, version)
VALUES (1, 1, 'Good stuff!', 'APPROVED', '2021-01-01 00:00:01', '2021-01-01 00:00:01', 1);

INSERT INTO CATALOG.orders (user_id, payment_id, comment, status, created, last_updated, version)
VALUES (1, 2, 'Bought two GPUs! They are great!', 'CANCELLED', '2021-01-01 00:00:01', '2021-01-01 00:00:01', 1);

INSERT INTO CATALOG.orders (user_id, payment_id, comment, status, created, last_updated, version)
VALUES (1, 3, 'Really nice MONITORS from DELL.', 'APPROVED', '2021-01-01 00:00:01', '2021-01-01 00:00:01', 1);


INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (1, 'G PRO Wireless', 'The best mouse of year 2021', 100, 150, 'PERIPHERALS', 'Logitech',
        'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b',
        'product-icon-KSOS.png',
        '2021-01-01 00:00:01',
        '2021-01-01 00:00:01', 1);

INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (2, 'G915 TKL', 'The best keyboard of year 2021', 100, 215, 'PERIPHERALS', 'Logitech',
        'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b', 'product-icon-KSOS.png',
        '2021-01-01 00:00:01',
        '2021-01-01 00:00:01', 1);

INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (3, 'Monitor 195', 'Budget monitor', 100, 250, 'MONITORS', 'LG', 'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b',
        'product-icon-KSOS.png',
        '2021-01-01 00:00:01',
        '2021-01-01 00:00:01',
        1);

INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (4, 'Alienware AW3219', '240hz, 2k, G-Sync', 60, 900, 'MONITORS', 'DELL',
        'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b', 'product-icon-KSOS.png',
        '2021-01-01 00:00:01',
        '2021-01-01 00:00:01', 1);

INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (5, 'DELL UltraSharp', '2k, FreeSync, 60hz', 100, 450, 'MONITORS', 'DELL',
        'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b', 'product-icon-KSOS.png',
        '2021-01-01 00:00:01',
        '2021-01-01 00:00:01', 1);

INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (6, 'DELL Normal Monitor', '1080p', 25, 200, 'MONITORS', 'DELL', 'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b',
        'product-icon-KSOS.png', '2021-01-01 00:00:01',
        '2021-01-01 00:00:01',
        1);

INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (7, 'Intel I9 9900K', 'Probably the best intel CPU', 100, 450, 'HARDWARE', 'INTEL',
        'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b', 'product-icon-KSOS.png',
        '2021-01-01 00:00:01',
        '2021-01-01 00:00:01', 1);

INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (8, 'RTX 3080', 'MID LEVEL GPU FROM NVIDIA', 25, 500, 'HARDWARE', 'NVIDIA',
        'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b', 'product-icon-KSOS.png',
        '2021-01-01 00:00:01',
        '2021-01-01 00:00:01', 1);

INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (9, 'AMD RYZEN 3600X', 'AMD BEST CPU', 100, 450, 'HARDWARE', 'AMD', 'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b',
        'product-icon-KSOS.png',
        '2021-01-01 00:00:01',
        '2021-01-01 00:00:01',
        1);

INSERT INTO CATALOG.product (id, name, description, quantity, price, category, product_line, image_path, image_file_name, created,
                             last_updated,
                             version)
VALUES (10, 'RTX 3090', 'NVIDIA BEST GPU', 25, 1500, 'HARDWARE', 'NVIDIA', 'disertatie-bucket/cc89aca8-ac5e-4e49-8feb-10488dd3167b',
        'product-icon-KSOS.png',
        '2021-01-01 00:00:01',
        '2021-01-01 00:00:01',
        1);

INSERT INTO CATALOG.orders_product (order_id, product_id, quantity)
VALUES (1, 2, 1);
INSERT INTO CATALOG.orders_product (order_id, product_id, quantity)
VALUES (1, 1, 1);
INSERT INTO CATALOG.orders_product (order_id, product_id, quantity)
VALUES (1, 3, 1);

INSERT INTO CATALOG.orders_product (order_id, product_id, quantity)
VALUES (2, 8, 1);
INSERT INTO CATALOG.orders_product (order_id, product_id, quantity)
VALUES (2, 10, 1);

INSERT INTO CATALOG.orders_product (order_id, product_id, quantity)
VALUES (3, 5, 3);
INSERT INTO CATALOG.orders_product (order_id, product_id, quantity)
VALUES (3, 4, 1);
INSERT INTO CATALOG.orders_product (order_id, product_id, quantity)
VALUES (3, 3, 1);
INSERT INTO CATALOG.orders_product (order_id, product_id, quantity)
VALUES (3, 6, 1);