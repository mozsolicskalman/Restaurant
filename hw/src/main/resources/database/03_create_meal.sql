CREATE SEQUENCE MEAL_SEQ START WITH 5 INCREMENT BY 1;

CREATE TABLE meal
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL DEFAULT '',
    price BIGINT NOT NULL default 100
);

INSERT INTO meal(id, name, price)
VALUES ( 1, 'pencakes', 1000);

INSERT INTO meal(id, name, price)
VALUES ( 2, 'pizza', 2000);

INSERT INTO meal(id, name, price)
VALUES ( 3, 'hamburger', 3000);

INSERT INTO meal(id, name, price)
VALUES ( 4, 'caviar', 5000);
