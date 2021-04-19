INSERT INTO USER.user (id, email, password, first_name, last_name, phone, created, last_updated, version)
VALUES (1, 'andreineagu.c@gmail.com', '$2y$10$1SaYw/3GraNrE8VABCIyYOQR8Je2ND7WX/b4cSexKJVuJCJQ5Uyii', 'Andrei',
        'Neagu', '0723333434', '2021-01-01 00:00:01', '2021-01-01 00:00:01', 1);


INSERT INTO USER.address (id, user_id, address_one, address_two, city, state, country, postal_code, created,
                     last_updated, version)
VALUES (1, 1, 'Str. Iancului, nr. 10', 'Bloc 114B', 'Bucharest', 'Bucharest', 'Romania', '021992',
        '2021-01-01 00:00:01', '2021-01-01 00:00:01', 1);
