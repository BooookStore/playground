create database playground;

\connect playground;

create table customer
(
    id uuid primary key
);

create table product
(
    id uuid primary key
);

create table order_requested
(
    id uuid primary key,
    datetime timestamp not null
);

create table order_detail
(
    order_requested_id uuid references order_requested,
    product_id uuid references product,
    amount int not null check (amount > 0),
    primary key (order_requested_id, product_id)
);
