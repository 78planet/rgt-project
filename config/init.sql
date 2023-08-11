CREATE TABLE orders
(
    id           bigint       NOT NULL auto_increment,
    order_id     varchar(255) NOT NULL,
    product_name varchar(255) NOT NULL,
    options      varchar(255) NOT NULL,
    table_no     integer      NOT NULL,
    quantity     bigint       NOT NULL,
    order_date   DATE         NOT NULL,
    order_time   time         NOT NULL,
    robot_status varchar(255) NOT NULL,
    date_time    datetime     NOT NULL,
    seq          varchar(255) NOT NULL,
    dong         varchar(255) NOT NULL,
    ho           varchar(255) NOT NULL,
    orderer_name varchar(255) NOT NULL,
    PRIMARY KEY (id)
) engine = InnoDB
  default charset = utf8mb4;

CREATE TABLE update_product_name_map
(
    id             bigint       NOT NULL auto_increment,
    target_data     varchar(255) NOT NULL,
    will_change_data varchar(255) NOT NULL,
    PRIMARY KEY (id)
) engine = InnoDB
  default charset = utf8mb4;
