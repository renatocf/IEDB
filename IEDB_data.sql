-- MAC 0211 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto
-- 
-- Data sample

INSERT INTO IEDB.User(username, email, reviewer)
VALUES('renatocf', 'renato.cordeiro.ferreira@usp.br', true);

INSERT INTO IEDB.User(username, email, reviewer)
VALUES('ruan0408', 'ruan.costa@usp.br', true);

INSERT INTO IEDB.User(username, email)
VALUES('tuiuiu', 'lucas.dario@usp.br');

INSERT INTO IEDB.User(username, email)
VALUES('karinaawoki', 'karina.suemi.awoki@usp.br');

INSERT INTO IEDB.Title(id, name, date_creation, description)
VALUES(1, 'The Lord of the Rings: The Fellowship of the Ring',
       '2014/09/28', 'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.');

INSERT INTO IEDB.Change(id, creation_date, target_table, operation, afected_col, conversion, new_text)
VALUES(1, '2014/09/28', 'Title', 'UPDATE', 'description', NULL, 'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron. Based on J.R.R.Tolkien book.');
