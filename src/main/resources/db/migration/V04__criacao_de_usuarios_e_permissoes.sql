CREATE TABLE user
(
    id       BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(64)  NOT NULL,
    email    VARCHAR(64)  NOT NULL,
    password VARCHAR(160) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE permission
(
    id          BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(64) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE user_permission
(
    user_id       BIGINT(20) NOT NULL,
    permission_id BIGINT(20) NOT NULL,
    PRIMARY KEY (user_id, permission_id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (permission_id) REFERENCES permission (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO user (name, email, password)
VALUES ('admin', 'admin@algamoney.com.br', '$2a$10$LwM4rOXrheB5tmKKBHh.Y.n50vxpEHxl3ImbMFQVzkimPQEoc9PKO');
INSERT INTO user (name, email, password)
VALUES ('maria', 'maria@algamoney.com.br', '$2a$10$Pg9xsv7ZdeLYvCIWMmdtYusyCWKGFT5A7F.8W9/aVafFtADX5yTqS');

INSERT INTO permission (description)
VALUES ('ROLE_CATEGORY_CREATE');
INSERT INTO permission (description)
VALUES ('ROLE_CATEGORY_RETRIEVE');
INSERT INTO permission (description)
VALUES ('ROLE_CATEGORY_UPDATE');
INSERT INTO permission (description)
VALUES ('ROLE_CATEGORY_DELETE');

INSERT INTO permission (description)
VALUES ('ROLE_PERSON_CREATE');
INSERT INTO permission (description)
VALUES ('ROLE_PERSON_RETRIEVE');
INSERT INTO permission (description)
VALUES ('ROLE_PERSON_UPDATE');
INSERT INTO permission (description)
VALUES ('ROLE_PERSON_DELETE');

INSERT INTO permission (description)
VALUES ('ROLE_ENTRY_CREATE');
INSERT INTO permission (description)
VALUES ('ROLE_ENTRY_RETRIEVE');
INSERT INTO permission (description)
VALUES ('ROLE_ENTRY_UPDATE');
INSERT INTO permission (description)
VALUES ('ROLE_ENTRY_DELETE');

-- admin
-- CRUD Categoria
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 1);
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 2);
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 3);
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 4);

-- CRUD Pessoa
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 5);
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 6);
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 7);
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 8);

-- CRUD Lançamento
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 9);
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 10);
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 11);
INSERT INTO user_permission (user_id, permission_id)
VALUES (1, 12);

-- maria
-- ROLE_*_RETRIEVE
INSERT INTO user_permission (user_id, permission_id)
VALUES (2, 2);
INSERT INTO user_permission (user_id, permission_id)
VALUES (2, 6);
INSERT INTO user_permission (user_id, permission_id)
VALUES (2, 10);