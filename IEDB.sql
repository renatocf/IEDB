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

CREATE DOMAIN IEDB.TYPE_NATIONALITY AS VARCHAR(50) NOT NULL;

CREATE DOMAIN IEDB.TYPE_RATE AS INT NOT NULL
		CHECK (VALUE >= 0 AND VALUE <= 5);

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                            DATA DEFINITION                            
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

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

CREATE TABLE IF NOT EXISTS IEDB.Person
(
    name        TYPE_NAME   PRIMARY KEY,
    birthday    DATE,
    nationality TYPE_NATIONALITY
);

CREATE TABLE IF NOT EXISTS IEDB.Actor
(
    name_person TYPE_NAME   PRIMARY KEY,
    FOREIGN KEY(name_person) REFERENCES IEDB.Person(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Author
(
    name_person TYPE_NAME   PRIMARY KEY,
    FOREIGN KEY(name_person) REFERENCES IEDB.Person(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Director
(
    name_person TYPE_NAME   PRIMARY KEY,
    FOREIGN KEY(name_person) REFERENCES IEDB.Person(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Musician
(
    name_person TYPE_NAME   PRIMARY KEY,
    FOREIGN KEY(name_person) REFERENCES IEDB.Person(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Title
(
    id            TYPE_ID   PRIMARY KEY,
	came_from	  TYPE_ID,
    name          TYPE_NAME NOT NULL,
    date_creation DATE      NOT NULL,
	description   TEXT,
	rate		  TYPE_RATE,
	FOREIGN KEY(came_from) REFERENCES IEDB.Title(id)
		ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Character
(
    name        TYPE_NAME   PRIMARY KEY,
);

CREATE TABLE IF NOT EXISTS IEDB.Auditive
(
    id_title TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id_title) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Written
(
    id_title TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id_title) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Visual
(
    id_title TYPE_ID PRIMARY KEY,
    FOREIGN KEY(id_title) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Music
(
    id_auditive     TYPE_ID PRIMARY KEY,
	genre		    TYPE_NAME NOT NULL DEFAULT 'Other',
	duration	    INTERVAL,
	FOREIGN KEY(genre) REFERENCES IEDB.Genre_auditive(name),
		ON UPDATE CASCADE ON DELETE SET DEFAULT,
    FOREIGN KEY(id_auditive) REFERENCES IEDB.Auditive(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.HQ --ADICIONAR MAIS COISAS PARA DIFERENCIAR DE LIVROS
(
    id_written  	TYPE_ID PRIMARY KEY,
	genre			TYPE_NAME NOT NULL DEFAULT 'Other',
	num_editions	INT,
	FOREIGN KEY(genre) REFERENCES IEDB.Genre_written(name),
		ON UPDATE CASCADE ON DELETE SET DEFAULT,
    FOREIGN KEY(id_written) REFERENCES IEDB.Written(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Book 
(
    id_written  	TYPE_ID PRIMARY KEY,
	genre			TYPE_NAME NOT NULL DEFAULT 'Other',
	num_editions	INT,
	FOREIGN KEY(genre) REFERENCES IEDB.Genre_written(name),
		ON UPDATE CASCADE ON DELETE SET DEFAULT,
    FOREIGN KEY(id_written) REFERENCES IEDB.Written(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Movie
(
    id_visual    TYPE_ID PRIMARY KEY,
	genre		 TYPE_NAME NOT NULL DEFAULT 'Other',
	duration	 INTERVAL,
	censorship	 TYPE_NAME,
	nationality  TYPE_NATIONALITY,
    FOREIGN KEY(id_visual) REFERENCES IEDB.Visual(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(censorship) REFERENCES IEDB.Visual_censorship(rating)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(genre) REFERENCES IEDB.Genre_visual(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Series
(
    id TYPE_ID  PRIMARY KEY,
	genre		TYPE_NAME NOT NULL DEFAULT 'Other',
	censorship	TYPE_NAME,
	date_init	DATE,
	date_end	DATE,
	num_seasons INT,
    FOREIGN KEY(id_visual) REFERENCES IEDB.Visual(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(age_classification) REFERENCES IEDB.Visual_censorship(rating)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(genre) REFERENCES IEDB.Genre_visual(name)
        ON UPDATE CASCADE ON DELETE RESTRICT
);
-- DAQUI PRA BAIXO NÃO FOI ATUALIZADO AINDA
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

CREATE TABLE IF NOT EXISTS IEDB.rel_publishes
(
    company_id    TYPE_ID,
    title_id      TYPE_ID,
    date          DATE,
    
    PRIMARY KEY(company_id, title_id),
    FOREIGN KEY(company_id) REFERENCES IEDB.Company(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id)   REFERENCES IEDB.Company(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_followed_by
(
    title_id                   TYPE_ID,
    continuation_title_id      TYPE_ID,
    
    PRIMARY KEY(title_id, continuation_title_id),
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(continuation_title_id)   REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_references
(
    referencer_title_id   TYPE_ID,
    refered_title_id      TYPE_ID,
    
    PRIMARY KEY(referencer_title_id, refered_title_id),
    FOREIGN KEY(referencer_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(refered_title_id)   REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_adapted_to
(
    original_title_id      TYPE_ID,
    adaptation_title_id    TYPE_ID,

    PRIMARY KEY(original_title_id, adaptation_title_id),
    FOREIGN KEY(original_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(adaptation_title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT

);

CREATE TABLE IF NOT EXISTS IEDB.rel_rates --Não pode deletar usuário??
(
    username_client        TYPE_USERNAME,
    original_title_id      TYPE_ID,
    adaptation_title_id    TYPE_ID,
    rate                   TYPE_RATE,
    
    PRIMARY KEY(username_client, original_title_id, adaptation_title_id),
    FOREIGN KEY(username_client) REFERENCES IEDB.Client(username)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(original_title_id, adaptation_title_id) 
        REFERENCES IEDB.rel_adapted_to(original_title_id, adaptation_title_id)
            ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_stars
(
    username_client     TYPE_USERNAME,
    title_id            TYPE_ID,
    date                DATE,

    FOREIGN KEY(username_client) REFERENCES IEDB.Client(username)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_has
(
    character_name  TYPE_NAME,
    title_id        TYPE_ID,

    PRIMARY KEY(character_name, title_id),
    FOREIGN KEY(character_name) REFERENCES IEDB.Character(name)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_interps
(
    name_actor      TYPE_NAME,
    name_character  TYPE_NAME,
    title_id        TYPE_ID,

    PRIMARY KEY(name_actor, name_character, title_id),
    FOREIGN KEY(name_actor) REFERENCES IEDB.Actor(name_person)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(character_name) REFERENCES IEDB.Character(name)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_creates
(
    title_id        TYPE_ID,
    author_name     TYPE_NAME,

    PRIMARY KEY(title_id, author_name),
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(author_name) REFERENCES IEDB.Author(name_person)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_directs
(
    title_id          TYPE_ID,
    director_name     TYPE_NAME,

    PRIMARY KEY(title_id, director_name),
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(director_name) REFERENCES IEDB.Director(name_person)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.rel_performs
(
    title_id          TYPE_ID,
    musician_name     TYPE_NAME,

    PRIMARY KEY(title_id, musician_name),
    FOREIGN KEY(title_id) REFERENCES IEDB.Title(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(author_name) REFERENCES IEDB.Musician(name_person)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS IEDB.Company
(
    id   TYPE_ID   PRIMARY KEY,
    name TYPE_NAME NOT NULL UNIQUE
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

CREATE TABLE IF NOT EXISTS IEDB.Visual_censorship
(
    rating TYPE_NAME PRIMARY KEY
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
