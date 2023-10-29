\connect playground;

INSERT INTO "operation" (id, name)
VALUES ('OP_D001', 'DISPLAY_ALL_ORDER'),
       ('OP_D002', 'DISPLAY_CREATED_ORDER'),
       ('OP_C001', 'CANCEL_ALL_ORDER'),
       ('OP_C002', 'CANCEL_CREATED_ORDER');

INSERT INTO "permission" (id, name)
VALUES ('PE_001', 'CANCELABLABLE_ALL_ORDER'),
       ('PE_002', 'CANCELABLABLE_CREATED_ORDER');

INSERT INTO "permission_operation" (permission, operation)
VALUES ('PE_001', 'OP_D001'),
       ('PE_001', 'OP_C001'),
       ('PE_002', 'OP_D002'),
       ('PE_002', 'OP_C002');