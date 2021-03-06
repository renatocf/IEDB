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

-- Books
SELECT create_book(_name := 'Harry Potter and the Philosopher''s Stone', _genre := 'Adventure', _num_editions := 1);
SELECT create_book(_name := 'Harry Potter and the Chamber of Secrets', _genre := 'Adventure', _num_editions := 1);
SELECT create_book(_name := 'Harry Potter and the Prisoner of Azkaban', _genre := 'Adventure', _num_editions := 1);
SELECT create_book(_name := 'Harry Potter and the Goblet of Fire', _genre := 'Adventure', _num_editions := 1);
SELECT create_book(_name := 'Harry Potter and the Order of the Phoenix', _genre := 'Adventure', _num_editions := 1);
SELECT create_book(_name := 'Harry Potter and the Half-Blood Prince', _genre := 'Adventure', _num_editions := 1);
SELECT create_book(_name := 'Harry Potter and the Deathly Hallows', _genre := 'Adventure', _num_editions := 1);

-- Comics
SELECT create_comic(_name := 'Superman', _genre := 'Action', _num := 423);

-- Movies
SELECT create_movie('The Lord of the Rings: The Fellowship of the Ring', 'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.', 'Adventure', 178, 'USA', 'G');
SELECT create_movie('The Lord of the Rings: The Two Towers', 'While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron''s new ally, Saruman, and his hordes of Isengard.', 'Adventure', 201, 'USA', 'G');
SELECT create_movie('The Lord of the Rings: The Return of the King', 'Gandalf and Aragorn lead the World of Men against Sauron''s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.', 'Action', 201, 'USA', 'G');
SELECT create_movie('Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', 'Action', 136, 'USA', 'G');

-- Musics
SELECT create_music(_name := 'Lucy in the sky with diamonds', _genre := 'Rock', _duration := 3);
SELECT create_music(_name := 'Penny Lane', _genre := 'Rock', _duration := 3);
SELECT create_music(_name := 'Octavarium', _genre := 'Rock', _duration := 29);
SELECT create_music(_name := 'Mirror Mirror', _genre := 'Rock', _duration := 5);
SELECT create_music(_name := 'The Trooper', _genre := 'Rock', _duration := 4);
SELECT create_music(_name := 'Excalibur', _genre := 'Rock', _duration := 5);
SELECT create_music(_name := 'Applause', _genre := 'Pop', _duration := 4);
SELECT create_music(_name := 'Anaconda', _genre := 'Pop', _duration := 3);
SELECT create_music(_name := 'Harder, Better, Faster, Stronger', _genre := 'Electronic', _duration := 4);
SELECT create_music(_name := 'Get Lucky', _genre := 'Electronic', _duration := 4);

-- Series
SELECT create_series(_name := 'Friends', _genre := 'Comedy', _date_init := '1994-09-22', _date_end := '2004-05-06', _num_seasons := 10);
SELECT create_series(_name := 'Prison Break', _genre := 'Action', _num_seasons := 4);
SELECT create_series(_name := 'Smallville', _genre := 'Action', _num_seasons := 9);
SELECT create_series(_name := 'Breaking Bad', _genre := 'Action', _num_seasons := 5);
SELECT create_series(_name := 'Homeland', _genre := 'Action', _num_seasons := 4);
SELECT create_series(_name := 'Supernatural', _genre := 'Action', _num_seasons := 10);
SELECT create_series(_name := 'House of Cards', _genre := 'Politics', _num_seasons := 2);
SELECT create_series(_name := 'Stargate SG1', _genre := 'Science Fiction', _num_seasons := 10);
SELECT create_series(_name := 'Doctor Who', _genre := 'Adventure', _num_seasons := 8);
SELECT create_series(_name := 'Flash', _genre := 'Action', _num_seasons := 1);
SELECT create_series(_name := 'Gotham', _genre := 'Action', _num_seasons := 1);
SELECT create_series(_name := 'Agents of Shield', _genre := 'Action', _num_seasons := 2);

SELECT create_change('renatocf', 'u_title_description', '1', 
'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.');
SELECT approve_change('ruan0408',1);
