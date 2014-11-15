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

CREATE DOMAIN IEDB.TYPE_USERNAME AS VARCHAR(32)
       CHECK (VALUE ~ '^[a-zA-Z][a-zA-Z0-9]*$');

CREATE DOMAIN IEDB.TYPE_EMAIL AS VARCHAR(64)
       CHECK (VALUE ~ '^[a-zA-Z0-9._]+@[a-zA-Z0-9]+(\.\w{3})?(\.\w{2})?$');

CREATE DOMAIN IEDB.TYPE_PASSWORD AS VARCHAR(128)
       CHECK (char_length(VALUE) >= 8);

CREATE DOMAIN IEDB.TYPE_OPERATION AS CHAR(6)
       CHECK(VALUE = 'INSERT' OR VALUE = 'UPDATE' OR VALUE = 'DELETE');

CREATE DOMAIN IEDB.TYPE_NAME AS VARCHAR(128);

CREATE DOMAIN IEDB.TYPE_NATIONALITY AS VARCHAR(50);

CREATE DOMAIN IEDB.TYPE_RATE AS NUMERIC(1,1) DEFAULT 0
       CHECK (VALUE >= 0 AND VALUE <= 5);

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                         ENTITIES DEFINITION                            
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

-- Client --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS IEDB.Client
(
    username TYPE_USERNAME PRIMARY KEY,
    email    TYPE_EMAIL    NOT NULL,
    password TYPE_PASSWORD NOT NULL,
    active   BOOLEAN DEFAULT true,
    reviewer BOOLEAN DEFAULT false
);

-- Artificial ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS IEDB.Change
(
    id                 SERIAL         PRIMARY KEY,
    submitter_username TYPE_USERNAME  NOT NULL,
    reviewer_username  TYPE_USERNAME  DEFAULT NULL,
    target_table       VARCHAR(32)    NOT NULL,
    operation          TYPE_OPERATION NOT NULL,
    id_col             VARCHAR(32)    DEFAULT NULL,
    id_value           TEXT           DEFAULT NULL,
    affected_col       VARCHAR(32)    DEFAULT NULL,
    info               TEXT           DEFAULT NULL,
    approval           BOOLEAN        DEFAULT NULL,
    
    FOREIGN KEY(submitter_username) REFERENCES IEDB.Client(username)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(reviewer_username) REFERENCES IEDB.Client(username)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Prototype_change
(
    name         TYPE_NAME      PRIMARY KEY,
    target_table VARCHAR(32)    NOT NULL,
    operation    TYPE_OPERATION NOT NULL,
    id_col       VARCHAR(32)    NOT NULL,
    affected_col VARCHAR(32)    NOT NULL
);

CREATE TABLE IF NOT EXISTS IEDB.Genre_auditive
(
    name TYPE_NAME PRIMARY KEY DEFAULT 'Other'
);

CREATE TABLE IF NOT EXISTS IEDB.Genre_written
(
    name TYPE_NAME PRIMARY KEY DEFAULT 'Other'
);

CREATE TABLE IF NOT EXISTS IEDB.Genre_visual
(
    name TYPE_NAME PRIMARY KEY DEFAULT 'Other'
);

CREATE TABLE IF NOT EXISTS IEDB.Censorship_visual
(
    rating TYPE_NAME PRIMARY KEY
);

-- Person --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS IEDB.Person
(
    name        TYPE_NAME PRIMARY KEY,
    birthday    DATE,
    nationality TYPE_NATIONALITY
);

CREATE TABLE IF NOT EXISTS IEDB.Author
(
    person_name TYPE_NAME PRIMARY KEY,
    FOREIGN KEY(person_name) REFERENCES IEDB.Person(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Actor
(
    person_name TYPE_NAME PRIMARY KEY,
    FOREIGN KEY(person_name) REFERENCES IEDB.Person(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Director
(
    person_name TYPE_NAME PRIMARY KEY,
    FOREIGN KEY(person_name) REFERENCES IEDB.Person(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Musician
(
    person_name TYPE_NAME PRIMARY KEY,
    FOREIGN KEY(person_name) REFERENCES IEDB.Person(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- Band ----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS IEDB.Band
(
    name          TYPE_NAME PRIMARY KEY,
    date_creation DATE,
    date_end      DATE
);

-- Character -----------------------------------------------------------
CREATE TABLE IF NOT EXISTS IEDB.Character
(
    name TYPE_NAME PRIMARY KEY
);

-- Company -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS IEDB.Company
(
    id   SERIAL    PRIMARY KEY,
    name TYPE_NAME NOT NULL UNIQUE
);

-- Title ---------------------------------------------------------------
CREATE TABLE IF NOT EXISTS IEDB.Title
(
    id            SERIAL    PRIMARY KEY,
    name          TYPE_NAME NOT NULL,
    type          TYPE_NAME NOT NULL,
    date_creation DATE      NOT NULL,
    description   TEXT,
    UNIQUE(name, type)
);

CREATE TABLE IF NOT EXISTS IEDB.Auditive
(
    id    INTEGER PRIMARY KEY,
    genre TYPE_NAME DEFAULT NULL,
    
    FOREIGN KEY(id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(genre) REFERENCES IEDB.Genre_auditive(name)
        ON UPDATE CASCADE ON DELETE SET DEFAULT
);

CREATE TABLE IF NOT EXISTS IEDB.Written
(
    id    INTEGER PRIMARY KEY,
    genre TYPE_NAME DEFAULT NULL,
    
    FOREIGN KEY(id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(genre) REFERENCES IEDB.Genre_written(name)
        ON UPDATE CASCADE ON DELETE SET DEFAULT
);

CREATE TABLE IF NOT EXISTS IEDB.Visual
(
    id         INTEGER PRIMARY KEY,
    censorship TYPE_NAME DEFAULT NULL,
    genre      TYPE_NAME DEFAULT NULL,
    
    FOREIGN KEY(id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(censorship) REFERENCES IEDB.Censorship_visual(rating)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(genre) REFERENCES IEDB.Genre_visual(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Music
(
    id       INTEGER PRIMARY KEY,
    duration INTEGER,
    
    FOREIGN KEY(id) REFERENCES IEDB.Auditive(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Book 
(
    id           INTEGER PRIMARY KEY,
    num_editions INT,
    
    FOREIGN KEY(id) REFERENCES IEDB.Written(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.HQ
(
    id  INTEGER PRIMARY KEY,
    num INT,
    arc TYPE_NAME,
    
    FOREIGN KEY(id) REFERENCES IEDB.Written(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Movie
(
    id          INTEGER PRIMARY KEY,
    duration    INTEGER,
    nationality TYPE_NATIONALITY,
    
    FOREIGN KEY(id) REFERENCES IEDB.Visual(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Series
(
    id          INTEGER PRIMARY KEY,
    date_init   DATE,
    date_end    DATE,
    num_seasons INT,
    FOREIGN KEY(id) REFERENCES IEDB.Visual(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                            RELATIONSHIPS
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

CREATE TABLE IF NOT EXISTS IEDB.rel_publishes
(
    company_id  INTEGER,
    title_id    INTEGER,
    date        DATE,
    
    PRIMARY KEY(company_id, title_id),
    FOREIGN KEY(company_id) REFERENCES IEDB.Company(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_followed_by
(
    original_title_id  INTEGER,
    sequel_title_id    INTEGER,
    
    PRIMARY KEY(original_title_id, sequel_title_id),
    FOREIGN KEY(original_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(sequel_title_id)   REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_references
(
    referencer_title_id  INTEGER,
    refered_title_id     INTEGER,
    
    PRIMARY KEY(referencer_title_id, refered_title_id),
    FOREIGN KEY(referencer_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(refered_title_id)   REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_plays
(
    auditive_id  INTEGER,
    band_name    TYPE_NAME,

    PRIMARY KEY(auditive_id, band_name),
    FOREIGN KEY(auditive_id) REFERENCES IEDB.Auditive(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(band_name) REFERENCES IEDB.Band(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_adapted_to
(
    original_title_id    INTEGER,
    adaptation_title_id  INTEGER,

    PRIMARY KEY(original_title_id, adaptation_title_id),
    FOREIGN KEY(original_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(adaptation_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_rates
(
    client_name          TYPE_USERNAME,
    original_title_id    INTEGER,
    adaptation_title_id  INTEGER,
    rate                 TYPE_RATE,
    
    PRIMARY KEY(client_name, original_title_id, adaptation_title_id),
    FOREIGN KEY(client_name) REFERENCES IEDB.Client(username)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(original_title_id, adaptation_title_id) 
    REFERENCES IEDB.rel_adapted_to(original_title_id, adaptation_title_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_stars
(
    client_name  TYPE_USERNAME,
    title_id     INTEGER,
    rate         TYPE_RATE,
    
    PRIMARY KEY(client_name, title_id),
    FOREIGN KEY(client_name) REFERENCES IEDB.Client(username)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_has
(
    character_name  TYPE_NAME,
    title_id        INTEGER,

    PRIMARY KEY(character_name, title_id),
    FOREIGN KEY(character_name) REFERENCES IEDB.Character(name)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_is_part_of
(
    musician_name  TYPE_NAME,
    band_name      TYPE_NAME,
    entry_date     DATE,
    exit_date      DATE,
    
    PRIMARY KEY(musician_name, band_name),
    FOREIGN KEY(musician_name) REFERENCES IEDB.Musician(person_name)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(band_name) REFERENCES IEDB.Band(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_creates
(
    title_id     INTEGER,
    author_name  TYPE_NAME,

    PRIMARY KEY(title_id, author_name),
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(author_name) REFERENCES IEDB.Author(person_name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_interps
(
    actor_name      TYPE_NAME,
    character_name  TYPE_NAME,
    visual_id       INTEGER,

    PRIMARY KEY(actor_name, character_name, visual_id),
    FOREIGN KEY(actor_name) REFERENCES IEDB.Actor(person_name)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(character_name) REFERENCES IEDB.Character(name)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(visual_id) REFERENCES IEDB.Visual(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_directs
(
    visual_id      INTEGER,
    director_name  TYPE_NAME,

    PRIMARY KEY(visual_id, director_name),
    FOREIGN KEY(visual_id) REFERENCES IEDB.Visual(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(director_name) REFERENCES IEDB.Director(person_name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_performs
(
    auditive_id    INTEGER,
    musician_name  TYPE_NAME,

    PRIMARY KEY(auditive_id, musician_name),
    FOREIGN KEY(auditive_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(musician_name) REFERENCES IEDB.Musician(person_name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                              TRIGGER
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

-- @procedure validate_reviewer
CREATE OR REPLACE FUNCTION validate_reviewer()
RETURNS TRIGGER AS $$
BEGIN
    IF ( NEW.reviewer_username IS NOT NULL 
    AND  (SELECT reviewer FROM IEDB.Client 
          WHERE username = NEW.reviewer_username) = false) 
    THEN
        RAISE EXCEPTION '% is not a reviewer', _reviewer_username;
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- @trigger AllowChange
DROP TRIGGER IF EXISTS AllowChange ON IEDB.Change;
CREATE TRIGGER AllowChange
BEFORE UPDATE ON IEDB.Change
FOR EACH ROW
    WHEN ( NEW.id IS NOT NULL )
        EXECUTE PROCEDURE validate_reviewer();
