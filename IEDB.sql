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
                           DOMAIN DEFINITION                           
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

CREATE DOMAIN IEDB.TYPE_USERNAME AS VARCHAR(32) NOT NULL
       CHECK (VALUE ~ '^[a-zA-Z][a-zA-Z0-9]*$');

CREATE DOMAIN IEDB.TYPE_EMAIL AS VARCHAR(64) NOT NULL
       CHECK (VALUE ~ '^[a-zA-Z0-9._]+@[a-zA-Z0-9]+(\.\w{3})?(\.\w{2})?$');

CREATE DOMAIN IEDB.TYPE_ID AS INTEGER NOT NULL;

CREATE DOMAIN IEDB.TYPE_NAME AS VARCHAR(128) NOT NULL;

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                            DATA DEFINITION                            
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

CREATE TABLE IF NOT EXISTS IEDB.User
(
    username TYPE_USERNAME PRIMARY KEY,
    email    TYPE_EMAIL    NOT NULL,
    reviewer BOOLEAN       NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS IEDB.Title
(
    id            TYPE_ID   PRIMARY KEY,
    name          TYPE_NAME NOT NULL,
    date_creation DATE      NOT NULL,
    description   TEXT
);

CREATE TABLE IF NOT EXISTS IEDB.Auditive
(
    id TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS IEDB.Written
(
    id TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS IEDB.Visual
(
    id TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS IEDB.Music
(
    id TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES IEDB.Auditive(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS IEDB.HQ
(
    id TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES IEDB.Written(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS IEDB.Book
(
    id TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES IEDB.Written(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS IEDB.Movie
(
    id TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES IEDB.Visual(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS IEDB.Series
(
    id TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id) REFERENCES IEDB.Visual(id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS IEDB.Change
(
    id            TYPE_ID      PRIMARY KEY,
    creation_date DATE         NOT NULL,
    target_table  VARCHAR(32)  NOT NULL,
    operation     CHAR(6)      NOT NULL,
    afected_col   VARCHAR(32)  NOT NULL,
    conversion    CHAR(12),    
    new_text      TEXT         NOT NULL
);

CREATE TABLE IF NOT EXISTS IEDB.Company
(
    id   TYPE_ID   PRIMARY KEY,
    name TYPE_NAME NOT NULL UNIQUE
);

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                    TRIGGERS AND STORED PROCEDURES                      
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

CREATE OR REPLACE FUNCTION change(_id TYPE_ID)
    RETURNS void AS $$
    DECLARE 
        _target_table VARCHAR(32);
        _operation    CHAR(6);
        _afected_col  VARCHAR(32);
        _conversion   CHAR(12);
        _new_text     TEXT;
    BEGIN
        _target_table := (SELECT target_table
                          FROM IEDB.Change WHERE id = _id);
        _operation    := (SELECT operation
                          FROM IEDB.Change WHERE id = _id);
        _afected_col  := (SELECT afected_col
                          FROM IEDB.Change WHERE id = _id);
        _conversion   := (SELECT conversion
                          FROM IEDB.Change WHERE id = _id);
        _new_text     := (SELECT new_text
                          FROM IEDB.Change WHERE id = _id);
        
        IF( _operation = 'UPDATE' ) THEN
            EXECUTE '
                UPDATE ' || _target_table || '
                SET    ' || _afected_col  || '
                =      ' || quote_literal(_new_text);
        END IF;
    END;
$$ LANGUAGE plpgsql;
