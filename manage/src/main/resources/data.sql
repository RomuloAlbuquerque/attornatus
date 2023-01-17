insert into tb_person(name, birth_date, created_at) values ('Romulo Albuquerque', '1988-12-21', NOW());
insert into tb_person(name, birth_date, created_at) values ('Daiane Soares', '1989-08-17', NOW());
insert into tb_person(name, birth_date, created_at) values ('Ana Beatriz', '2013-12-19', NOW());
insert into tb_person(name, birth_date, created_at) values ('Murillo William', '2009-01-01', NOW());

insert into tb_address(street_address, cep, number, city, main, created_At) values ('Rua Miguel Inácio Faraco', '88705-050', 355, 'Tubarão-SC', false, NOW());
insert into tb_address(street_address, cep, number, city, main, created_At) values ('Passagem Campestre', '67030-168', 47, 'Ananindeua-PA', false, NOW());

INSERT INTO tb_person_address (person_id, address_id) VALUES (1, 1);
INSERT INTO tb_person_address (person_id, address_id) VALUES (1, 2);
INSERT INTO tb_person_address (person_id, address_id) VALUES (2, 1);
INSERT INTO tb_person_address (person_id, address_id) VALUES (2, 2);
INSERT INTO tb_person_address (person_id, address_id) VALUES (3, 1);
INSERT INTO tb_person_address (person_id, address_id) VALUES (3, 2);
INSERT INTO tb_person_address (person_id, address_id) VALUES (4, 1);
INSERT INTO tb_person_address (person_id, address_id) VALUES (4, 2);