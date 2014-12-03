-- MAC 0211 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto

-- Data sample
SELECT create_account
    ('renatocf', 'renato.cordeiro.ferreira@usp.br', '14159265');
SELECT create_account
    ('ruan0408', 'ruan.costa@usp.br', 'ruanruan');
SELECT create_account
    ('tuiuiu', 'lucas.dario@usp.br', '38462643');
SELECT create_account
    ('karinaawoki', 'karina.suemi.awoki@usp.br', '38327950');

SELECT grant_reviewer_permission('renatocf');
SELECT grant_reviewer_permission('ruan0408');

-- SELECT create_title('movie', 'The Lord of the Rings: The Fellowship of the Ring');
SELECT create_movie('The Lord of the Rings: The Fellowship of the Ring', 'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.', 'Adventure', 178, 'USA', 'G');
SELECT create_movie('Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'Action', 136, 'USA', 'G');

SELECT create_comic(_name := 'Superman', _genre := 'Action', _num := 423);
SELECT create_music(_name := 'Lucy in the sky with diamonds', _genre := 'Rock', _duration := 3);
SELECT create_series(_name := 'Friends', _genre := 'Comedy', _date_init := '1994-09-22', _date_end := '2004-05-06', _num_seasons := 10);
SELECT create_book(_name := 'Harry Potter and the Philosopher''s Stone', _genre := 'Adventure', _num_editions := 1);

SELECT create_change('renatocf', 'u_title_description', '1', 
'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.');

SELECT approve_change('ruan0408',1);
