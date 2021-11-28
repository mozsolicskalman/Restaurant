CREATE SEQUENCE ORDER_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE "ORDER"
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_time  TIMESTAMP,
    feedback    BIGINT,
    order_type VARCHAR(32),
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) references auth_user (id)
);
