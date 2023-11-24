\connect playground;

INSERT INTO "order" (id, create_user, name)
VALUES ('1111', 'fc447159-81df-42ac-91dc-e8a001b44143', '2019 Guatemara'),
       ('1112', '8094c979-cad8-4f6e-8255-5b54a62f956c', '2022 Guatemara'),
       ('1113', '8094c979-cad8-4f6e-8255-5b54a62f956c', '2023 Guatemara');

INSERT INTO "order_status" ("order", datetime, status, "user")
VALUES ('1111', '2023-11-4 11:49:00', 'ACCEPTED', 'fc447159-81df-42ac-91dc-e8a001b44143'),
       ('1112', '2023-11-7 21:11:00', 'ACCEPTED', '8094c979-cad8-4f6e-8255-5b54a62f956c'),
       ('1112', '2023-11-8 22:11:00', 'CANCELED', '8094c979-cad8-4f6e-8255-5b54a62f956c'),
       ('1113', '2023-11-9 20:11:00', 'ACCEPTED', '8094c979-cad8-4f6e-8255-5b54a62f956c');
