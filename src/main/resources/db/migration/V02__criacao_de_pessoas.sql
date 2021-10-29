CREATE TABLE person(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(96) NOT NULL,
    is_active BOOLEAN NOT NULL,
    address_street VARCHAR(96),
    address_number VARCHAR(8),
    address_complement VARCHAR(32),
    address_neighborhood VARCHAR(32),
    address_postal_code VARCHAR(16),
    address_city VARCHAR(32),
    address_state VARCHAR(2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT
    INTO person (name, address_postal_code, address_street, address_number, address_complement, address_neighborhood, address_city, address_state, is_active)
    VALUES ('Estefânia Basílio Alencar', '78095-143', 'São Paulo', '5164', 'Lote 10', 'Santo Antônio', 'Petrolina', 'PE', true);

INSERT
    INTO person (name, address_postal_code, address_street, address_number, address_complement, address_neighborhood, address_city, address_state, is_active)
    VALUES('Sarai Mangueira Pastana', '69098-939', 'das Flores', '6036', 'Bloco 9', 'Industrial', 'São Luís', 'MA', true);

INSERT
INTO person (name, address_postal_code, address_street, address_number, address_complement, address_neighborhood,
             address_city, address_state, is_active)
VALUES ('Stefania Pires Delgado', '50493-180', 'Quatorze', '7008', 'Fazenda 4', 'Vila Nova', 'Rio Branco', 'AC', true);

INSERT
INTO person (name, address_postal_code, address_street, address_number, address_complement, address_neighborhood,
             address_city, address_state, is_active)
VALUES ('Ionara Caminha Frois', '79698-013', 'Belo Horizonte', '4734', 'Galpão 8', 'Vila Nova', 'Montes Claros', 'MG',
        true);

INSERT
INTO person (name, address_postal_code, address_street, address_number, address_complement, address_neighborhood,
             address_city, address_state, is_active)
VALUES ('Lopo Vilanova Brum', '28820-708', 'Santa Rita', '1465', 'Fazenda 3', 'Centro', 'Águas Lindas de Goiás', 'GO',
        false);