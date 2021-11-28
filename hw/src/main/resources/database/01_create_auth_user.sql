CREATE SEQUENCE AUTH_USER_SEQ START WITH 2 INCREMENT BY 1;

CREATE TABLE auth_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(512) NOT NULL UNIQUE,
    password_hash VARCHAR(256) NOT NULL
);
