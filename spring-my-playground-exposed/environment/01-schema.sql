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

CREATE TABLE "permission_operation"
(
    permission VARCHAR(255) NOT NULL,
    operation  VARCHAR(255) NOT NULL,
    PRIMARY KEY (permission, operation)
);
