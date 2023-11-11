\connect playground;

INSERT INTO "user" (id, mail_address, password)
VALUES ('fc447159-81df-42ac-91dc-e8a001b44143', 'bookstore1@playground', 'password'),
       ('8094c979-cad8-4f6e-8255-5b54a62f956c', 'bookstore2@playground', 'password'),
       ('0f2e1115-6064-4903-9bcf-5c9e4e5f2d24', 'bookstore3@playground', 'password');

INSERT INTO "operation" (id, name)
VALUES ('OP_001', 'DISPLAY_ALL_ORDER'),
       ('OP_002', 'DISPLAY_CREATED_ORDER'),
       ('OP_003', 'CANCEL_ALL_ORDER'),
       ('OP_004', 'CANCEL_CREATED_ORDER'),
       ('OP_005', 'CREATE_ORDER');

INSERT INTO "permission" (id, name)
VALUES ('PE_001', 'CANCELABLABLE_ALL_ORDER'),
       ('PE_002', 'CANCELABLABLE_CREATED_ORDER'),
       ('PE_003', 'CREATE_ORDER');

INSERT INTO "permission_operation" (permission, operation)
VALUES ('PE_001', 'OP_001'),
       ('PE_001', 'OP_003'),
       ('PE_002', 'OP_002'),
       ('PE_002', 'OP_004'),
       ('PE_003', 'OP_005');

INSERT INTO "role" (id, name)
VALUES ('RO_001', 'MANAGER'),
       ('RO_002', 'NORMAL');

INSERT INTO "role_permission" (role, permission)
VALUES ('RO_001', 'PE_001'),
       ('RO_002', 'PE_002'),
       ('RO_001', 'PE_003'),
       ('RO_002', 'PE_003');

INSERT INTO "subject" ("user", role)
VALUES ('fc447159-81df-42ac-91dc-e8a001b44143', 'RO_001'),
       ('8094c979-cad8-4f6e-8255-5b54a62f956c', 'RO_002'),
       ('0f2e1115-6064-4903-9bcf-5c9e4e5f2d24', 'RO_002');

INSERT INTO "order" (id, create_user, name)
VALUES ('1111', 'fc447159-81df-42ac-91dc-e8a001b44143', '2019 Guatemara'),
       ('1112', '8094c979-cad8-4f6e-8255-5b54a62f956c', '2020 Guatemara');

INSERT INTO "order_status" ("order", datetime, status, "user")
VALUES ('1111', '2023-11-4 11:49:00', 'ACCEPTED', 'fc447159-81df-42ac-91dc-e8a001b44143'),
       ('1112', '2023-11-7 21:11:00', 'ACCEPTED', '8094c979-cad8-4f6e-8255-5b54a62f956c');