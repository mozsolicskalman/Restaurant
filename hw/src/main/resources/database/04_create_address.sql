CREATE SEQUENCE ADDRESS_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE address
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    address  VARCHAR(512),
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) references auth_user (id)
);
