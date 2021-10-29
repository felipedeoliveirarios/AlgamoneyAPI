CREATE TABLE entry
(
    id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    description  VARCHAR(96)    NOT NULL,
    due_date     DATE           NOT NULL,
    payment_date DATE,
    value        DECIMAL(10, 2) NOT NULL,
    note         VARCHAR(128),
    type         VARCHAR(16)    NOT NULL,
    category_id  BIGINT(20)     NOT NULL,
    person_id    BIGINT(20)     NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id),
    FOREIGN KEY (person_id) REFERENCES person (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT
INTO entry (description, due_date, payment_date, value, note, type, category_id, person_id)
VALUES ('Materiais de Limpeza', '2020-05-22', '2020-04-06', 2833.20, 'Cartão de crédito', 'EXPENSE', 1, 1);

INSERT
INTO entry (description, due_date, payment_date, value, note, type, category_id, person_id)
VALUES ('Transporte de Carga', '2020-03-09', '2020-02-04', 22450.00, 'Pedido #02832', 'INCOME', 5, 1);

INSERT
INTO entry (description, due_date, payment_date, value, note, type, category_id, person_id)
VALUES ('IPTU', '2020-04-01', '2020-05-18', 2345.78, 'Pago com atraso', 'EXPENSE', 4, 3);

INSERT
INTO entry (description, due_date, payment_date, value, note, type, category_id, person_id)
VALUES ('Revenda de Produto', '2020-06-19', null, 499.90, null, 'INCOME', 2, 2);

INSERT
INTO entry (description, due_date, payment_date, value, note, type, category_id, person_id)
VALUES ('Comida de Cachorro', '2020-12-21', '2020-12-21', 60.00, null, 'EXPENSE', 6, 2);

INSERT
INTO entry (description, due_date, payment_date, value, note, type, category_id, person_id)
VALUES ('Confraternização', '2020-08-23', '2020-08-23', 337.93, null, 'EXPENSE', 2, 4);

INSERT
INTO entry (description, due_date, payment_date, value, note, type, category_id, person_id)
VALUES ('Kit de Primeiros Socorros', '2020-12-18', '2020-10-23', 129.90, null, 'EXPENSE', 3, 5);
