CREATE SEQUENCE ORDER_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE order_entity
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_time  TIMESTAMP,
    order_type VARCHAR(32),
    meal_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) references auth_user (id),
    FOREIGN KEY (meal_id) references meal (id),
    FOREIGN KEY (address_id) references address (id)
);
