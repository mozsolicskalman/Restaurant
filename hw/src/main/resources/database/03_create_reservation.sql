CREATE SEQUENCE RESERVATION_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_time TIMESTAMP,
    table_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) references auth_user(id),
    FOREIGN KEY (table_id) references table(id)
);