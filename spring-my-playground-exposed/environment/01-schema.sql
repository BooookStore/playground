-- @formatter:off
create database playground;

\connect playground;
-- @formatter:on

CREATE TABLE "operation"
(
    id   VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE "permission"
(
    id   VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE "role"
(
    id   VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE "user"
(
    id           UUID PRIMARY KEY,
    mail_address VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL
);

CREATE TABLE "permission_operation"
(
    permission VARCHAR(255) NOT NULL,
    operation  VARCHAR(255) NOT NULL,
    FOREIGN KEY (permission) REFERENCES "permission" (id),
    FOREIGN KEY (operation) REFERENCES "operation" (id),
    PRIMARY KEY (permission, operation)
);

CREATE TABLE "role_permission"
(
    role       VARCHAR(255) NOT NULL,
    permission VARCHAR(255) NOT NULL,
    FOREIGN KEY (role) REFERENCES "role" (id),
    FOREIGN KEY (permission) REFERENCES "permission" (id),
    PRIMARY KEY (role, permission)
);

CREATE TABLE "subject"
(
    "user" UUID         NOT NULL,
    role   VARCHAR(255) NOT NULL,
    FOREIGN KEY ("user") REFERENCES "user" (id),
    FOREIGN KEY (role) REFERENCES "role" (id),
    PRIMARY KEY ("user", role)
);

CREATE TABLE "order"
(
    id   VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE "order_history"
(
    "order"  VARCHAR(255) NOT NULL,
    datetime TIMESTAMP    NOT NULL,
    status   VARCHAR(255) NOT NULL,
    "user"   UUID         NOT NULL,
    FOREIGN KEY ("order") REFERENCES "order" (id),
    FOREIGN KEY ("user") REFERENCES "user" (id)
);