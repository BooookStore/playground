\connect playground;

INSERT INTO "operation" (id, name)
VALUES ('OP_001', 'DISPLAY_ALL_ORDER'),
       ('OP_002', 'DISPLAY_CREATED_ORDER'),
       ('OP_003', 'CANCEL_ALL_ORDER'),
       ('OP_004', 'CANCEL_CREATED_ORDER');

INSERT INTO "permission" (id, name)
VALUES ('PE_001', 'CANCELABLABLE_ALL_ORDER'),
       ('PE_002', 'CANCELABLABLE_CREATED_ORDER');

INSERT INTO "permission_operation" (permission, operation)
VALUES ('PE_001', 'OP_001'),
       ('PE_001', 'OP_002'),
       ('PE_002', 'OP_003'),
       ('PE_002', 'OP_004');

INSERT INTO "role" (id, name)
VALUES ('RO_001', 'MANAGER'),
       ('RO_002', 'NORMAL');

INSERT INTO "role_permission" (role, permission)
VALUES ('RO_001', 'PE_001'),
       ('RO_002', 'PE_002');

INSERT INTO "user" (mail_address, password)
VALUES ('bookstore1@playground', 'password'),
       ('bookstore2@playground', 'password');

INSERT INTO "subject" (mail_address, role)
VALUES ('bookstore1@playground', 'RO_001'),
       ('bookstore2@playground', 'RO_002');

INSERT INTO "order" (id, name)
VALUES ('1111', '2019 Guatemara');