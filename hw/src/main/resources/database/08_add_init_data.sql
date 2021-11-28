INSERT INTO auth_user(id, username, password_hash)
VALUES (1, 'admin', '{bcrypt}$2a$10$vVQgOzqgfBgEGNiZ14Wss.zGhRJGmoB4SMnNzkXr7IYsAkaNGYOa2');
INSERT INTO auth_user_roles(auth_user_id, roles)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO desk(id, seats)
VALUES (1, 2);
INSERT INTO desk(id, seats)
VALUES (2, 2);
INSERT INTO desk(id, seats)
VALUES (3, 4);
INSERT INTO desk(id, seats)
VALUES (4, 4);
INSERT INTO desk(id, seats)
VALUES (5, 4);
INSERT INTO desk(id, seats)
VALUES (6, 4);
INSERT INTO desk(id, seats)
VALUES (7, 8);
INSERT INTO desk(id, seats)
VALUES (8, 8);
INSERT INTO desk(id, seats)
VALUES (9, 8);
INSERT INTO desk(id, seats)
VALUES (10, 10);
