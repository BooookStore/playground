create table "order"
(
    id uuid primary key,
    order_date_time timestamp not null,
    shop_id varchar(50) not null
);
