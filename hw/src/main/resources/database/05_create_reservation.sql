CREATE SEQUENCE RESERVATION_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_time TIMESTAMP,
    feedback    BIGINT,
    desk_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) references auth_user(id),
    FOREIGN KEY (desk_id) references desk(id)
);
