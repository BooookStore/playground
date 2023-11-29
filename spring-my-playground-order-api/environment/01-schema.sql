-- @formatter:off
create database playground;

\connect playground;
-- @formatter:on

CREATE TABLE "order"
(
    id          UUID PRIMARY KEY,
    create_user UUID NOT NULL
);

CREATE TABLE "received_order"
(
    id       UUID      PRIMARY KEY,
    order_id UUID      NOT NULL,
    datetime TIMESTAMP NOT NULL,
    FOREIGN KEY ("order_id") REFERENCES "order" (id)
);

CREATE TABLE "accepted_order"
(
    id                UUID      PRIMARY KEY,
    received_order_id UUID      NOT NULL,
    datetime          TIMESTAMP NOT NULL,
    FOREIGN KEY ("received_order_id") REFERENCES "received_order_id" (id)
);