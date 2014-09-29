-- MAC 0211 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto
--
-- Data definition

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
