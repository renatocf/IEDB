-- MAC 0211 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto
-- 
-- Data sample

INSERT INTO IEDB.Client(username, email, password)
VALUES('renatocf', 'renato.cordeiro.ferreira@usp.br', '14159265');

INSERT INTO IEDB.Client(username, email, password)
VALUES('ruan0408', 'ruan.costa@usp.br', '35897932');

INSERT INTO IEDB.Client(username, email, password)
VALUES('tuiuiu', 'lucas.dario@usp.br', '38462643');

INSERT INTO IEDB.Client(username, email, password)
VALUES('karinaawoki', 'karina.suemi.awoki@usp.br', '38327950');

INSERT INTO IEDB.Reviewer(username) VALUES('renatocf');
INSERT INTO IEDB.Reviewer(username) VALUES('ruan0408');

INSERT INTO IEDB.Title(name, date_creation, description)
VALUES('The Lord of the Rings: The Fellowship of the Ring',
       '2014/09/28', 'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.');

INSERT INTO IEDB.Change(creation_date, target_table, 
                        operation, afected_col, info)
VALUES('2014/09/28', 'Title', 'UPDATE', 'description', 'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron. Based on J.R.R.Tolkien book.');

INSERT INTO IEDB.Genre_auditive
VALUES ('Rock');

INSERT INTO IEDB.Genre_written
VALUES ('Romance');

INSERT INTO IEDB.Genre_visual
VALUES ('Action');

INSERT INTO IEDB.Censorship_visual -- Motion picture rating system
VALUES ('G');
