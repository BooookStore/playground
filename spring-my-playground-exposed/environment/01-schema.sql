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

CREATE TABLE "user"
(
    mail_address VARCHAR(255) PRIMARY KEY,
    password     VARCHAR(255) NOT NULL
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

CREATE TABLE "subject"
(
    mail_address VARCHAR(255) NOT NULL,
    role         VARCHAR(255) NOT NULL,
    PRIMARY KEY (mail_address, role)
);

CREATE TABLE "subject_event"
(
    mail_address VARCHAR(255) NOT NULL,
    role         VARCHAR(255) NOT NULL,
    date_time    TIMESTAMP    NOT NULL,
    event        VARCHAR(255) NOT NULL,
    UNIQUE (date_time, event)
);
