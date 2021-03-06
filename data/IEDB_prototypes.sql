-- MAC 0211 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto

-- Prototype changes
-- Naming convention: [u|i|d]_table_attribute

-- Title
INSERT INTO IEDB.Prototype_change
VALUES ('u_title_name', 'IEDB.Title', 'UPDATE', 'id', 'name');
INSERT INTO IEDB.Prototype_change
VALUES ('u_title_description', 'IEDB.Title', 'UPDATE', 'id', 'description');

-- Auditive/Visual/Written
INSERT INTO IEDB.Prototype_change
VALUES ('u_auditive_genre', 'IEDB.Auditive', 'UPDATE', 'id', 'genre');
INSERT INTO IEDB.Prototype_change
VALUES ('u_written_genre', 'IEDB.Written', 'UPDATE', 'id', 'genre');
INSERT INTO IEDB.Prototype_change
VALUES ('u_visual_genre', 'IEDB.Visual', 'UPDATE', 'id', 'genre');
INSERT INTO IEDB.Prototype_change
VALUES ('u_visual_censorship', 'IEDB.Visual', 'UPDATE', 'id', 'censorship');

-- Book
INSERT INTO IEDB.Prototype_change
VALUES ('u_book_name', 'IEDB.Title', 'UPDATE', 'id', 'name');
INSERT INTO IEDB.Prototype_change
VALUES ('u_book_description', 'IEDB.Title', 'UPDATE', 'id', 'description');
INSERT INTO IEDB.Prototype_change
VALUES ('u_book_genre', 'IEDB.Written', 'UPDATE', 'id', 'genre');
INSERT INTO IEDB.Prototype_change
VALUES ('u_book_num_editions', 'IEDB.Book', 'UPDATE', 'id', 'num_editions');

-- Comic
INSERT INTO IEDB.Prototype_change
VALUES ('u_comic_name', 'IEDB.Title', 'UPDATE', 'id', 'name');
INSERT INTO IEDB.Prototype_change
VALUES ('u_comic_description', 'IEDB.Title', 'UPDATE', 'id', 'description');
INSERT INTO IEDB.Prototype_change
VALUES ('u_comic_genre', 'IEDB.Written', 'UPDATE', 'id', 'genre');
INSERT INTO IEDB.Prototype_change
VALUES ('u_comic_num', 'IEDB.Comic', 'UPDATE', 'id', 'num');
INSERT INTO IEDB.Prototype_change
VALUES ('u_comic_arc', 'IEDB.Comic', 'UPDATE', 'id', 'arc');

-- Movie
INSERT INTO IEDB.Prototype_change
VALUES ('u_movie_name', 'IEDB.Title', 'UPDATE', 'id', 'name');
INSERT INTO IEDB.Prototype_change
VALUES ('u_movie_description', 'IEDB.Title', 'UPDATE', 'id', 'description');
INSERT INTO IEDB.Prototype_change
VALUES ('u_movie_genre', 'IEDB.Visual', 'UPDATE', 'id', 'genre');
INSERT INTO IEDB.Prototype_change
VALUES ('u_movie_censorship', 'IEDB.Visual', 'UPDATE', 'id', 'censorship');
INSERT INTO IEDB.Prototype_change
VALUES ('u_movie_duration', 'IEDB.Movie', 'UPDATE', 'id', 'duration');
INSERT INTO IEDB.Prototype_change
VALUES ('u_movie_nationality', 'IEDB.Movie', 'UPDATE', 'id', 'nationality');

-- Music
INSERT INTO IEDB.Prototype_change
VALUES ('u_music_name', 'IEDB.Title', 'UPDATE', 'id', 'name');
INSERT INTO IEDB.Prototype_change
VALUES ('u_music_description', 'IEDB.Title', 'UPDATE', 'id', 'description');
INSERT INTO IEDB.Prototype_change
VALUES ('u_music_genre', 'IEDB.Auditive', 'UPDATE', 'id', 'genre');
INSERT INTO IEDB.Prototype_change
VALUES ('u_music_duration', 'IEDB.Music', 'UPDATE', 'id', 'duration');

-- Series
INSERT INTO IEDB.Prototype_change
VALUES ('u_series_name', 'IEDB.Title', 'UPDATE', 'id', 'name');
INSERT INTO IEDB.Prototype_change
VALUES ('u_series_description', 'IEDB.Title', 'UPDATE', 'id', 'description');
INSERT INTO IEDB.Prototype_change
VALUES ('u_series_genre', 'IEDB.Visual', 'UPDATE', 'id', 'genre');
INSERT INTO IEDB.Prototype_change
VALUES ('u_series_censorship', 'IEDB.Visual', 'UPDATE', 'id', 'censorship');
INSERT INTO IEDB.Prototype_change
VALUES ('u_series_date_init', 'IEDB.Series', 'UPDATE', 'id', 'date_init');
INSERT INTO IEDB.Prototype_change
VALUES ('u_series_date_end', 'IEDB.Series', 'UPDATE', 'id', 'date_end');
INSERT INTO IEDB.Prototype_change
VALUES ('u_series_num_seasons', 'IEDB.Series', 'UPDATE', 'id', 'num_seasons');
