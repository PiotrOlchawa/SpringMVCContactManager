INSERT INTO PUBLIC.USER(ID,PASSWORD, USERNAME) VALUES
(1,'$2a$10$8m3qBL7CGr2xjU6PPPCuAO5UDgoetimhbpCJkJQDPlzIShSjPVlte', 'piotr'),
(2,'$2a$10$MM4z7Nlm/Gwkczrp4TofeelstW0w3ctLdDe3U4Q4eCbW2UNGDxJr6', 'admin');

INSERT INTO PUBLIC.AUTHORITIES(ID, AUTHORITY, USER_ID) VALUES
(1, 'ROLE_USER', 1),
(2, 'ROLE_ADMIN', 2);