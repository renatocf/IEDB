-- MAC 0439 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto
-- 
-- Lucas Dário                    - 7990940
-- Renato Cordeiro Ferreira       - 7990933
-- Ruan de Menezes Costa          - 7990929

------------------------------------------------------------------------
--                          Data definition                           --
------------------------------------------------------------------------

CREATE SCHEMA IF NOT EXISTS IEDB;
SET search_path TO IEDB;

CREATE TABLE IF NOT EXISTS IEDB.User
(
    username VARCHAR(32),
    email    VARCHAR(64) NOT NULL,
    reviewer BOOLEAN     NOT NULL DEFAULT false,
    PRIMARY KEY(username)
);

CREATE TABLE IF NOT EXISTS IEDB.Title
(
    id            INTEGER,
    name          VARCHAR(128) NOT NULL,
    date_creation DATE NOT NULL,
    description   TEXT,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS IEDB.Auditive
(
    id            INTEGER PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES(IEDB.Title)
);

CREATE TABLE IF NOT EXISTS IEDB.Written
(
    id            INTEGER PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES(IEDB.Title)
);

CREATE TABLE IF NOT EXISTS IEDB.Visual
(
    id            INTEGER PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES(IEDB.Title)
);

CREATE TABLE IF NOT EXISTS IEDB.Music
(
    id            INTEGER PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES(IEDB.Auditive)
);

CREATE TABLE IF NOT EXISTS IEDB.HQ
(
    id            INTEGER PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES(IEDB.Written)
);

CREATE TABLE IF NOT EXISTS IEDB.Book
(
    id            INTEGER PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES(IEDB.Written)
);

CREATE TABLE IF NOT EXISTS IEDB.Movie
(
    id            INTEGER PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES(IEDB.Visual)
);

CREATE TABLE IF NOT EXISTS IEDB.Series
(
    id            INTEGER PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES(IEDB.Visual)
);

CREATE TABLE IF NOT EXISTS IEDB.Change
(
    id            INTEGER,
    creation_date DATE        NOT NULL,
    target_table  VARCHAR(32) NOT NULL,
    operation     CHAR(6)     NOT NULL,
    afected_col   VARCHAR(32) NOT NULL,
    conversion    CHAR(12),
    new_text      TEXT        NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS IEDB.Company
(
    id            INTEGER,
    name          VARCHAR(128)
    PRIMARY KEY(id),
    UNIQUE(name)
);

------------------------------------------------------------------------
--                  Triggers and stored procedures                    --
------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION change_update(
    _target_table varchar(32), _afected_col varchar(32),
    _conversion char(12), _new_text TEXT
)
    RETURNS SETOF BOOLEAN AS $$
    BEGIN
        EXECUTE '
            UPDATE ' || _target_table || '
            SET    ' || _afected_col  || '
            =      ' || quote_literal(_new_text);
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION change(_id integer)
    RETURNS SETOF void AS $$
    DECLARE function_type char(6);
    BEGIN
        function_type := (SELECT operation
                          FROM IEDB.Change WHERE id = _id);
        IF( function_type = 'UPDATE' ) THEN
            PERFORM change_update(
                (SELECT target_table FROM IEDB.Change WHERE id = _id),
                (SELECT afected_col  FROM IEDB.Change WHERE id = _id),
                (SELECT conversion   FROM IEDB.Change WHERE id = _id),
                (SELECT new_text     FROM IEDB.Change WHERE id = _id)
            );
        END IF;
    END;
$$ LANGUAGE plpgsql;
