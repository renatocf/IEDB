-- MAC 0439 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto
-- 
-- Lucas Dário                    - 7990940
-- Renato Cordeiro Ferreira       - 7990933
-- Ruan de Menezes Costa          - 7990929

-- DEPENDENCIES: 
-- Run 'IEDB.sql' and 'IEDB_procedures.sql'
-- before this.

CREATE SCHEMA IF NOT EXISTS IEDB;
SET search_path TO IEDB;

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                                VIEWS
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

-- @view Complete_music
CREATE OR REPLACE VIEW IEDB.Complete_music AS
SELECT *, calculate_rate(id) AS rate
FROM IEDB.Title NATURAL JOIN IEDB.Auditive NATURAL JOIN IEDB.Music;

-- @view Complete_book
CREATE OR REPLACE VIEW IEDB.Complete_book AS
SELECT *, calculate_rate(id) AS rate
FROM IEDB.Title NATURAL JOIN IEDB.Written NATURAL JOIN IEDB.Book;

-- @view Complete_hq
CREATE OR REPLACE VIEW IEDB.Complete_hq AS
SELECT *, calculate_rate(id) AS rate
FROM   IEDB.Title NATURAL JOIN IEDB.Written NATURAL JOIN IEDB.HQ;

-- @view Complete_movie
CREATE OR REPLACE VIEW IEDB.Complete_movie AS
SELECT *, calculate_rate(id) AS rate
FROM   IEDB.Title NATURAL JOIN IEDB.Visual NATURAL JOIN IEDB.Movie;

-- @view Complete_series
CREATE OR REPLACE VIEW IEDB.Complete_series AS
SELECT *, calculate_rate(id) AS rate
FROM IEDB.Title NATURAL JOIN IEDB.Visual NATURAL JOIN IEDB.Series;

-- @view Complete_adaptation
CREATE OR REPLACE VIEW IEDB.Complete_adaptation AS
SELECT original.name AS original_name, adaptation.name AS adaptation_name,
       calculate_rate(original_title_id, adaptation_title_id) AS rate
FROM   IEDB.Title AS original, IEDB.Title AS adaptation, 
       IEDB.rel_adapted_to
WHERE  original.id = original_title_id 
  AND  adaptation.id = adaptation_title_id;

-- @view Active_client
CREATE OR REPLACE VIEW IEDB.Active_client AS
SELECT username FROM IEDB.Client 
WHERE  active = true;

-- @view Active_reviewer
CREATE OR REPLACE VIEW IEDB.Active_reviewer AS
SELECT username FROM IEDB.Client 
WHERE  active = true AND reviewer = true;

-- @view Approved_change
CREATE OR REPLACE VIEW IEDB.Approved_change AS
SELECT * FROM IEDB.Change WHERE approval = true;

-- @view Reproved_change
CREATE OR REPLACE VIEW IEDB.Reproved_change AS
SELECT * FROM IEDB.Change WHERE approval = true;

-- @view Unanalized_change
CREATE OR REPLACE VIEW IEDB.Unanalized_change AS
SELECT * FROM IEDB.Change WHERE approval IS NULL;
