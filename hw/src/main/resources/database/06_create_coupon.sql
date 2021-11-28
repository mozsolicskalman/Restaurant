CREATE SEQUENCE COUPON_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE coupon
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    percentage BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) references auth_user (id)
);
