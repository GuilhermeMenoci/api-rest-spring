
INSERT INTO eventos.evento(cidade, codigo, [data], nome_evento) VALUES('São Paulo', 1, '2022-04-04', 'Formatura');

INSERT INTO eventos.convidado(cpf, nome, evento_id) VALUES('72490831046', 'Guilherme', 1);

INSERT INTO eventos.usuario([login], senha, evento_id) VALUES('adm', 'adm', 1);
