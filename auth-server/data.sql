-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO role(id, role) VALUES ('1e194f5a-3da4-4ef5-bac8-25bd87fda3d6', 'ROLE_ADMIN');
INSERT INTO role(id, role) VALUES ('c1858547-d0a9-4bed-bba2-a3f95f87b123', 'ROLE_VERIFIED');
INSERT INTO role(id, role) VALUES ('b3181129-6a30-4253-9509-8cb1643f404d', 'ROLE_USER');

INSERT INTO users(id, created, user_name, password) VALUES ('268db62f-1d30-4e64-a28f-4e537e696eef', '2017-02-02 21:45:09.836', 'admin', '$2a$10$7Erzl/PmEC9n/x30B0dAZOU8P5FCs2K.QEm8ei5zR/lq4GPucdqiG');
-- passwort : password

INSERT INTO users(id, created, user_name, password) VALUES ('a510284c-7cd4-49a0-9f60-a6128f54754a', '2017-02-02 21:45:09.836', 'shopper', '$2a$10$Kpbh9fdb8bu01rZuNuBViOLBAb0X4svhOvGowgvQZIQJE25cAr.ju');
-- passwort : shopper

-- admin
INSERT INTO users_roles(users_id, roles_id)	VALUES ('268db62f-1d30-4e64-a28f-4e537e696eef', '1e194f5a-3da4-4ef5-bac8-25bd87fda3d6');
INSERT INTO users_roles(users_id, roles_id)	VALUES ('268db62f-1d30-4e64-a28f-4e537e696eef', 'c1858547-d0a9-4bed-bba2-a3f95f87b123');
INSERT INTO users_roles(users_id, roles_id)	VALUES ('268db62f-1d30-4e64-a28f-4e537e696eef', 'b3181129-6a30-4253-9509-8cb1643f404d');

-- shopper
INSERT INTO users_roles(users_id, roles_id)	VALUES ('a510284c-7cd4-49a0-9f60-a6128f54754a', 'c1858547-d0a9-4bed-bba2-a3f95f87b123');
INSERT INTO users_roles(users_id, roles_id)	VALUES ('a510284c-7cd4-49a0-9f60-a6128f54754a', 'b3181129-6a30-4253-9509-8cb1643f404d');
