-- MAC 0439 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto
-- 
-- Lucas Dário                    - 7990940
-- Renato Cordeiro Ferreira       - 7990933
-- Ruan de Menezes Costa          - 7990929

CREATE SCHEMA IF NOT EXISTS IEDB;
SET search_path TO IEDB;

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                                VIEWS
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

CREATE OR REPLACE VIEW IEDB.Complete_music AS
SELECT *, calculate_rate(id) AS rate
FROM IEDB.Title NATURAL JOIN IEDB.Auditive NATURAL JOIN IEDB.Music;

CREATE OR REPLACE VIEW IEDB.Complete_book AS
SELECT *, calculate_rate(id) AS rate
FROM IEDB.Title NATURAL JOIN IEDB.Written NATURAL JOIN IEDB.Book;

CREATE OR REPLACE VIEW IEDB.Complete_HQ AS
SELECT *, calculate_rate(id) AS rate
FROM   IEDB.Title NATURAL JOIN IEDB.Written NATURAL JOIN IEDB.HQ;

CREATE OR REPLACE VIEW IEDB.Complete_movie AS
SELECT *, calculate_rate(id) AS rate
FROM   IEDB.Title NATURAL JOIN IEDB.Visual NATURAL JOIN IEDB.Movie;

CREATE OR REPLACE VIEW IEDB.Complete_series AS
SELECT *, calculate_rate(id) AS rate
FROM IEDB.Title NATURAL JOIN IEDB.Visual NATURAL JOIN IEDB.Series;

CREATE OR REPLACE VIEW IEDB.Complete_adaptation AS
SELECT original.name AS original_name, adaptation.name AS adaptation_name,
       calculate_rate(original_title_id, adaptation_title_id) AS rate
FROM   IEDB.Title AS original, IEDB.Title AS adaptation, 
       IEDB.rel_adapted_to
WHERE  original.id = original_title_id 
  AND  adaptation.id = adaptation_title_id;
