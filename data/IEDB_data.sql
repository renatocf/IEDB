-- MAC 0211 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto

-- Basic info
INSERT INTO IEDB.Prototype_change
VALUES ('change_description', 'IEDB.Title', 'UPDATE', 'id', 'description');

INSERT INTO IEDB.Genre_auditive VALUES ('Rock');
INSERT INTO IEDB.Genre_written VALUES ('Romance');
INSERT INTO IEDB.Genre_visual VALUES ('Action');

INSERT INTO IEDB.Censorship_visual VALUES ('G');


-- Data sample
SELECT create_account
    ('renatocf', 'renato.cordeiro.ferreira@usp.br', '14159265');
SELECT create_account
    ('ruan0408', 'ruan.costa@usp.br', '35897932');
SELECT create_account
    ('tuiuiu', 'lucas.dario@usp.br', '38462643');
SELECT create_account
    ('karinaawoki', 'karina.suemi.awoki@usp.br', '38327950');

SELECT grant_reviewer_permission('renatocf');
SELECT grant_reviewer_permission('ruan0408');

SELECT create_title('movie', 'The Lord of the Rings: The Fellowship of the Ring');

SELECT create_movie('Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'Action', 136, 'USA', 'G');

SELECT create_change('renatocf', 'change_description', '1', 
'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.');

SELECT approve_change('ruan0408',1);
