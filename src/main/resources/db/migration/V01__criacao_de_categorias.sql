CREATE TABLE category
(
    id   BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(48) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO category (name)
values ('Housing');
INSERT INTO category (name)
values ('Food');
INSERT INTO category (name)
values ('Medical');
INSERT INTO category (name)
values ('Taxes');
INSERT INTO category (name)
values ('Transportation');
INSERT INTO category (name)
values ('Pets');