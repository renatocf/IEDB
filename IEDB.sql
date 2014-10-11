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

CREATE DOMAIN IEDB.TYPE_NAME AS VARCHAR(128) NOT NULL;

CREATE DOMAIN IEDB.TYPE_NATIONALITY AS VARCHAR(50) NOT NULL;

CREATE DOMAIN IEDB.TYPE_RATE AS INT NOT NULL
        CHECK (VALUE >= 0 AND VALUE <= 5);

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                         ENTITIES DEFINITION                            
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

-- Artificial ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS IEDB.Change
(
    id            SERIAL       PRIMARY KEY,
    creation_date DATE         NOT NULL,
    target_table  VARCHAR(32)  NOT NULL,
    operation     CHAR(6)      NOT NULL,
    afected_col   VARCHAR(32)  NOT NULL,
    conversion    CHAR(12),    
    new_text      TEXT         NOT NULL
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


-- Client --------------------------------------------------------------
CREATE TABLE IF NOT EXISTS IEDB.Client
(
    username TYPE_USERNAME PRIMARY KEY,
    email    TYPE_EMAIL    NOT NULL
);

CREATE TABLE IF NOT EXISTS IEDB.Reviewer
(
    username TYPE_USERNAME PRIMARY KEY,
    FOREIGN KEY(username) REFERENCES IEDB.Client(username)
        ON UPDATE CASCADE ON DELETE CASCADE
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
    came_from     INTEGER,
    name          TYPE_NAME NOT NULL,
    date_creation DATE      NOT NULL,
    description   TEXT,
    rate          TYPE_RATE,
    FOREIGN KEY(came_from) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Auditive
(
    title_id SERIAL    PRIMARY KEY,
    genre    TYPE_NAME NOT NULL DEFAULT 'Other',
    
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(genre) REFERENCES IEDB.Genre_auditive(name)
        ON UPDATE CASCADE ON DELETE SET DEFAULT
);

CREATE TABLE IF NOT EXISTS IEDB.Written
(
    title_id SERIAL    PRIMARY KEY,
    genre    TYPE_NAME NOT NULL DEFAULT 'Other',
    
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(genre) REFERENCES IEDB.Genre_written(name)
        ON UPDATE CASCADE ON DELETE SET DEFAULT
);

CREATE TABLE IF NOT EXISTS IEDB.Visual
(
    title_id   SERIAL  PRIMARY KEY,
    censorship TYPE_NAME,
    genre      TYPE_NAME NOT NULL DEFAULT 'Other',
    
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(censorship) REFERENCES IEDB.Censorship_visual(rating)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(genre) REFERENCES IEDB.Genre_visual(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Music
(
    auditive_id SERIAL  PRIMARY KEY,
    duration    INTERVAL,
    
    FOREIGN KEY(auditive_id) REFERENCES IEDB.Auditive(title_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Book 
(
    written_id   SERIAL  PRIMARY KEY,
    num_editions INT,
    
    FOREIGN KEY(written_id) REFERENCES IEDB.Written(title_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.HQ
(
    written_id   SERIAL  PRIMARY KEY,
    num_editions INT,
    
    FOREIGN KEY(written_id) REFERENCES IEDB.Written(title_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Movie
(
    visual_id    SERIAL  PRIMARY KEY,
    duration     INTERVAL,
    nationality  TYPE_NATIONALITY,
    
    FOREIGN KEY(visual_id) REFERENCES IEDB.Visual(title_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Series
(
    visual_id   SERIAL   PRIMARY KEY,
    date_init   DATE,
    date_end    DATE,
    num_seasons INT,
    FOREIGN KEY(visual_id) REFERENCES IEDB.Visual(title_id)
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
    company_id  SERIAL,
    title_id    SERIAL,
    date        DATE,
    
    PRIMARY KEY(company_id, title_id),
    FOREIGN KEY(company_id) REFERENCES IEDB.Company(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id) REFERENCES IEDB.Company(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_followed_by
(
    original_title_id  SERIAL,
    sequel_title_id    SERIAL,
    
    PRIMARY KEY(original_title_id, sequel_title_id),
    FOREIGN KEY(original_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(sequel_title_id)   REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_references
(
    referencer_title_id  SERIAL,
    refered_title_id     SERIAL,
    
    PRIMARY KEY(referencer_title_id, refered_title_id),
    FOREIGN KEY(referencer_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(refered_title_id)   REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_plays
(
    auditive_id  SERIAL,
    band_name    SERIAL,
    
    PRIMARY KEY(auditive_id, band_name),
    FOREIGN KEY(auditive_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(band_name)   REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_adapted_to
(
    original_title_id    SERIAL,
    adaptation_title_id  SERIAL,

    PRIMARY KEY(original_title_id, adaptation_title_id),
    FOREIGN KEY(original_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(adaptation_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT

);

CREATE TABLE IF NOT EXISTS IEDB.rel_rates
(
    client_name          TYPE_USERNAME,
    original_title_id    SERIAL,
    adaptation_title_id  SERIAL,
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
    title_id     SERIAL,
    date         DATE,

    FOREIGN KEY(client_name) REFERENCES IEDB.Client(username)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_has
(
    character_name  TYPE_NAME,
    title_id        SERIAL,

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
    title_id     SERIAL,
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
    title_id        SERIAL,

    PRIMARY KEY(actor_name, character_name, title_id),
    FOREIGN KEY(actor_name) REFERENCES IEDB.Actor(person_name)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(character_name) REFERENCES IEDB.Character(name)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_directs
(
    title_id       SERIAL,
    director_name  TYPE_NAME,

    PRIMARY KEY(title_id, director_name),
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(director_name) REFERENCES IEDB.Director(person_name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_performs
(
    title_id       SERIAL,
    musician_name  TYPE_NAME,

    PRIMARY KEY(title_id, musician_name),
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(musician_name) REFERENCES IEDB.Musician(person_name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                    TRIGGERS AND STORED PROCEDURES                      
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

CREATE OR REPLACE FUNCTION change(_id INTEGER)
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
