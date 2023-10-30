create database playground;

\connect playground;

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

CREATE TABLE "permission_operation"
(
    permission VARCHAR(255) NOT NULL,
    operation  VARCHAR(255) NOT NULL,
    PRIMARY KEY (permission, operation)
);

CREATE TABLE "role_permission"
(
    role       VARCHAR(255) NOT NULL,
    permission VARCHAR(255) NOT NULL,
    PRIMARY KEY (role, permission)
);