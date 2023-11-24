-- @formatter:off
create database playground;

\connect playground;
-- @formatter:on

CREATE TABLE "order"
(
    id          VARCHAR(255) PRIMARY KEY,
    create_user UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL
);

CREATE TABLE "order_status"
(
    "order"  VARCHAR(255) NOT NULL,
    datetime TIMESTAMP    NOT NULL,
    status   VARCHAR(255) NOT NULL,
    "user"   UUID         NOT NULL,
    FOREIGN KEY ("order") REFERENCES "order" (id)
);