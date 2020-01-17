insert into procedimento(id, nome, descricao) values(1, 'Nao se aplica', 'Nao se aplica');

insert into grupo (id, nome, descricao) values (1, 'ROLE_ANALISTAS', 'ANALISTAS');
insert into grupo (id, nome, descricao) values (2, 'ROLE_SUPERVISORES', 'SUPERVISORES');
insert into grupo (id, nome, descricao) values (3, 'ROLE_ADMINISTRADORES', 'ADMINISTRADORES');

insert into usuario (id, nome, chave, senha, status, funcao_administrador, funcao_supervisor) values (1, 'Administrador Sistema', 'ADMS', md5('123321'), 'Ativo', 'SIM', 'NAO');

insert into usuario_grupo (usuario_id, grupo_id) values(1, 3);

insert into categoria_id_sequence(next_val) values(0);
insert into chamado_id_sequence(next_val) values(0);
insert into equipe_id_sequence(next_val) values(0);
insert into grupo_id_sequence(next_val) values(4);
insert into usuario_id_sequence(next_val) values(2);
insert into produto_id_sequence(next_val) values(0);
insert into procedimento_id_sequence(next_val) values(2);
insert into regional_id_sequence(next_val) values(0);