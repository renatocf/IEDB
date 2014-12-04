-- MAC 0439 - Laboratório de Banco de Dados
-- IME-USP  - Segundo  Semestre   de   2014
-- Turma 45 - Prof.ª  Kelly  Rosa  Bragheto
-- 
-- Lucas Dário                    - 7990940
-- Renato Cordeiro Ferreira       - 7990933
-- Ruan de Menezes Costa          - 7990929

-- DEPENDENCIES: 
-- Run 'IEDB.sql' before this.

CREATE SCHEMA IF NOT EXISTS IEDB;
SET search_path TO IEDB;

/*
////////////////////////////////////////////////////////////////////////
-----------------------------------------------------------------------
                          STORED PROCEDURES                      
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

------------------------------------------------------------------------
--                       Account administration                       --
------------------------------------------------------------------------

-- @procedure create_account
CREATE OR REPLACE FUNCTION create_account(
    _username TYPE_USERNAME, _email TYPE_EMAIL, _password TYPE_PASSWORD)
RETURNS void AS $$
BEGIN
    INSERT INTO IEDB.Client(username, email, password)
    VALUES(_username, _email, _password);
END;
$$ LANGUAGE plpgsql;

-- @procedure change_account_email
CREATE OR REPLACE FUNCTION change_account_email(
    _username TYPE_USERNAME, _new_email TYPE_EMAIL)
RETURNS void AS $$
BEGIN
    UPDATE IEDB.Client SET email = _new_email
    WHERE  username = _username;
END;
$$ LANGUAGE plpgsql;

-- @procedure change_account_password
CREATE OR REPLACE FUNCTION change_account_password(
    _username TYPE_USERNAME, _password TYPE_PASSWORD)
RETURNS void AS $$
BEGIN
    UPDATE IEDB.Client SET email = _new_email
    WHERE  password = _password;
END;
$$ LANGUAGE plpgsql;

-- @procedure activate_account
CREATE OR REPLACE FUNCTION activate_account(_username TYPE_USERNAME)
RETURNS void AS $$
BEGIN
    UPDATE IEDB.Client SET active = true WHERE username = _username;
END;
$$ LANGUAGE plpgsql;

-- @procedure deactivate_account
CREATE OR REPLACE FUNCTION deactivate_account(_username TYPE_USERNAME)
RETURNS void AS $$
BEGIN
    UPDATE IEDB.Client SET active = false WHERE username = _username;
END;
$$ LANGUAGE plpgsql;

-- @procedure grant_reviewer_privilege
CREATE OR REPLACE FUNCTION grant_reviewer_permission(
    _username TYPE_USERNAME)
RETURNS void AS $$
BEGIN
    UPDATE IEDB.Client SET reviewer = true WHERE username = _username;
END;
$$ LANGUAGE plpgsql;

-- @procedure revoke_reviewer_privilege
CREATE OR REPLACE FUNCTION revoke_reviewer_permission(
    _username TYPE_USERNAME)
RETURNS void AS $$
BEGIN
    UPDATE IEDB.Client SET reviewer = false WHERE username = _username;
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                         Title management                           --
------------------------------------------------------------------------

-- @procedure create_title
CREATE OR REPLACE FUNCTION create_title(_type varchar(6), _name TYPE_NAME)
RETURNS void AS $$
DECLARE
    _id INTEGER;
BEGIN
    INSERT INTO IEDB.Title(name, date_creation)
    VALUES(_name, current_date) RETURNING id INTO _id;
    
    CASE
        WHEN _type = 'music' THEN
            INSERT INTO IEDB.Auditive (id) VALUES(_id);
            INSERT INTO IEDB.Music    (id) VALUES(_id);
        WHEN _type = 'book' THEN      
            INSERT INTO IEDB.Written (id) VALUES(_id);
            INSERT INTO IEDB.Book    (id) VALUES(_id);
        WHEN _type = 'comic' THEN                    
            INSERT INTO IEDB.Written (id) VALUES(_id);
            INSERT INTO IEDB.Comic   (id) VALUES(_id);
        WHEN _type = 'movie' THEN                   
            INSERT INTO IEDB.Visual  (id) VALUES(_id);
            INSERT INTO IEDB.movie   (id)  VALUES(_id);
        WHEN _type = 'series' THEN              
            INSERT INTO IEDB.Visual  (id) VALUES(_id);
            INSERT INTO IEDB.series  (id) VALUES(_id);
        ELSE
            RAISE EXCEPTION 'Incorrect type --> %', _type
            USING HINT = 'Please check valid title types';
    END CASE;
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                         Book management                           --
------------------------------------------------------------------------
-- @procedure create_book
CREATE OR REPLACE FUNCTION create_book(
    _name          TYPE_NAME,
    _description   TEXT                 DEFAULT NULL,
    _genre         varchar(30)          DEFAULT 'Other',
    _num_editions  INT                  DEFAULT NULL)
RETURNS void AS $$
DECLARE
    _id INTEGER;
BEGIN
    INSERT INTO IEDB.Title(name, type, date_creation, description)
    VALUES(_name, 'book', current_date, _description) 
    RETURNING id INTO _id;

    INSERT INTO IEDB.Written(id, genre) 
    VALUES(_id, _genre);
    
    INSERT INTO IEDB.Book(id, num_editions)
    VALUES(_id, _num_editions);
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                         Comic management                           --
------------------------------------------------------------------------
-- @procedure create_comic
CREATE OR REPLACE FUNCTION create_comic(
    _name          TYPE_NAME,
    _description   TEXT                 DEFAULT NULL,
    _genre         varchar(30)          DEFAULT 'Other',
    _arc           TYPE_NAME            DEFAULT NULL,
    _num           INT                  DEFAULT NULL)
RETURNS void AS $$
DECLARE
    _id INTEGER;
BEGIN
    INSERT INTO IEDB.Title(name, type, date_creation, description)
    VALUES(_name, 'comic', current_date, _description) 
    RETURNING id INTO _id;

    INSERT INTO IEDB.Written(id, genre) 
    VALUES(_id, _genre);
    
    INSERT INTO IEDB.Comic(id, arc, num)
    VALUES(_id, _arc, _num);
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                         Movie management                           --
------------------------------------------------------------------------
-- @procedure create_movie
CREATE OR REPLACE FUNCTION create_movie(
    _name           TYPE_NAME,
    _description    TEXT                DEFAULT NULL,
    _genre          varchar(30)         DEFAULT 'Other',
    _duration       INTEGER             DEFAULT NULL,
    _nationality    TYPE_NATIONALITY    DEFAULT NULL,
    _censorship     TYPE_NAME           DEFAULT NULL)
RETURNS void AS $$
DECLARE
    _id INTEGER;
BEGIN
    INSERT INTO IEDB.Title(name, type, date_creation, description)
    VALUES(_name, 'movie', current_date, _description) 
    RETURNING id INTO _id;

    INSERT INTO IEDB.Visual(id, genre, censorship) 
    VALUES(_id, _genre, _censorship);
    
    INSERT INTO IEDB.Movie(id, duration, nationality)
    VALUES(_id, _duration, _nationality);
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                         Music management                           --
------------------------------------------------------------------------
-- @procedure create_music
CREATE OR REPLACE FUNCTION create_music(
    _name           TYPE_NAME,
    _description    TEXT                    DEFAULT NULL,
    _genre          varchar(30)             DEFAULT 'Other',
    _duration       INTEGER                 DEFAULT NULL)
RETURNS void AS $$
DECLARE
    _id INTEGER;
BEGIN
    INSERT INTO IEDB.Title(name, type, date_creation, description)
    VALUES(_name, 'music', current_date, _description) 
    RETURNING id INTO _id;

    INSERT INTO IEDB.Auditive(id, genre) 
    VALUES(_id, _genre);
    
    INSERT INTO IEDB.Music(id, duration)
    VALUES(_id, _duration);
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                         Series management                           --
------------------------------------------------------------------------


-- @procedure create_series
CREATE OR REPLACE FUNCTION create_series(
    _name        TYPE_NAME,
    _description TEXT           DEFAULT NULL,
    _genre       varchar(30)    DEFAULT 'Other',
    _date_init   DATE           DEFAULT NULL,
    _date_end    DATE           DEFAULT NULL,
    _num_seasons INT            DEFAULT NULL,
    _censorship  TYPE_NAME      DEFAULT NULL)
RETURNS void AS $$
DECLARE
    _id INTEGER;
BEGIN
    INSERT INTO IEDB.Title(name, type, date_creation, description)
    VALUES(_name, 'series', current_date, _description) 
    RETURNING id INTO _id;

    INSERT INTO IEDB.Visual(id, genre, censorship) 
    VALUES(_id, _genre, _censorship);
    
    INSERT INTO IEDB.Series(id, date_init, date_end, num_seasons)
    VALUES(_id, _date_init, _date_end, _num_seasons);
END;
$$ LANGUAGE plpgsql;


------------------------------------------------------------------------
--                           References                               --
------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION create_ref_title_title(
    _referencer_title_id     INTEGER,
    _refered_title_id        INTEGER)
RETURNS void AS $$
BEGIN
    INSERT INTO IEDB.rel_references(referencer_title_id, refered_title_id)
    VALUES(_referencer_title_id, _refered_title_id);
END;
$$ LANGUAGE plpgsql;



------------------------------------------------------------------------
--                          Calculate rate                            --
------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION create_stars(
    _client_email      TYPE_EMAIL,
    _title_id          INTEGER,
    _rate              TYPE_RATE
    )
RETURNS void AS $$
BEGIN
    INSERT INTO IEDB.rel_stars(client_email, title_id, rate)
    VALUES(_client_email, _title_id, _rate);
END;
$$ LANGUAGE plpgsql;


-- @procedure calculate_rate
CREATE OR REPLACE FUNCTION calculate_rate(_title_id INTEGER)
RETURNS REAL AS $$
BEGIN   
    RETURN (SELECT avg(rate) FROM IEDB.rel_stars
            WHERE  title_id = _title_id);
END;
$$ LANGUAGE plpgsql;

-- @procedure calculate_rate
CREATE OR REPLACE FUNCTION calculate_rate(
    _original_title_id INTEGER, _adaptation_title_id INTEGER)
RETURNS REAL AS $$
BEGIN
    RETURN (SELECT avg(rate) FROM IEDB.rel_rates
            WHERE  original_title_id = _original_title_id
              AND  adaptation_title_id = _adaptation_title_id);
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                        Change management                           --
------------------------------------------------------------------------

-- @procedure create_change
-- Create change without a prototype
CREATE OR REPLACE FUNCTION create_change(
    _submitter_username TYPE_USERNAME,
    _target_table VARCHAR(32), _operation CHAR(6),
    _id_col       VARCHAR(32), _id_value  TEXT,
    _affected_col VARCHAR(32), _info      TEXT)
RETURNS void AS $$
BEGIN
    INSERT INTO IEDB.Change(submitter_username, target_table, operation,
                            id_col, id_value, affected_col, info)
    VALUES(_submitter_username, _target_table, _operation,
           _id_col, _id_value, _affected_col, _info);
END;
$$ LANGUAGE plpgsql;

-- @procedure create_change
-- Create change using a tuple from Prototype_change
CREATE OR REPLACE FUNCTION create_change(
    _submitter_username TYPE_USERNAME,
    _prototype_name TYPE_NAME, _id_value TEXT, _info TEXT)
RETURNS void AS $$
DECLARE
    _target_table VARCHAR(32);
    _operation    CHAR(6);
    _id_col       VARCHAR(32);
    _affected_col VARCHAR(32);
BEGIN
    _target_table := (SELECT target_table FROM IEDB.Prototype_change
                      WHERE name = _prototype_name);
    _operation    := (SELECT operation FROM IEDB.Prototype_change
                      WHERE name = _prototype_name);
    _id_col       := (SELECT id_col FROM IEDB.Prototype_change
                      WHERE name = _prototype_name);
    _affected_col := (SELECT affected_col FROM IEDB.Prototype_change
                      WHERE name = _prototype_name);
    
    INSERT INTO IEDB.Change(submitter_username, target_table, operation,
                            id_col, id_value, affected_col, info)
    VALUES(_submitter_username, _target_table, _operation,
           _id_col, _id_value, _affected_col, _info);
END;
$$ LANGUAGE plpgsql;

-- @procedure approve_change
CREATE OR REPLACE FUNCTION approve_change(
    _reviewer_username TYPE_USERNAME, _id INTEGER)
RETURNS void AS $$
DECLARE 
    _target_table VARCHAR(32); _operation CHAR(6);
    _id_col       VARCHAR(32); _id_value  TEXT;
    _affected_col VARCHAR(32); _info      TEXT;
BEGIN
    IF ((SELECT approval FROM IEDB.Change 
         WHERE reviewer_username = _reviewer_username) IS NOT NULL)
        THEN RETURN;
    END IF;
    
    UPDATE IEDB.Change
    SET    reviewer_username = _reviewer_username, approval = true 
    WHERE  id = _id;
    
    _target_table := (SELECT target_table FROM IEDB.Change WHERE id = _id);
    _operation    := (SELECT operation    FROM IEDB.Change WHERE id = _id);
    _id_col       := (SELECT id_col       FROM IEDB.Change WHERE id = _id);
    _id_value     := (SELECT id_value     FROM IEDB.Change WHERE id = _id);
    _affected_col := (SELECT affected_col FROM IEDB.Change WHERE id = _id);
    _info         := (SELECT info         FROM IEDB.Change WHERE id = _id);
    
    IF( _operation = 'INSERT' ) THEN
        EXECUTE '
            INSERT INTO ' || _target_table        || '
                       (' || _affected_col        || ')
            VALUES     (' || quote_literal(_info) || ')';
    ELSIF( _operation = 'UPDATE' ) THEN
        EXECUTE '
            UPDATE ' || _target_table        || '
            SET    ' || _affected_col        || '
            =      ' || quote_literal(_info) || '
            WHERE  ' || _id_col              || '
            =      ' || quote_literal(_id_value);
    ELSIF( _operation = 'DELETE' ) THEN
        EXECUTE '
            DELETE FROM ' || _target_table || '
            WHERE       ' || _id_col       || '
            =           ' || quote_literal(_id_value);
    ELSE
        RAISE EXCEPTION '% is not a valid operation', _operation
        USING HINT = 'Operations should be either INSERT/UPDATE/DELETE';
    END IF;
END;
$$ LANGUAGE plpgsql;

-- @procedure reprove_change
CREATE OR REPLACE FUNCTION reprove_change(
    _reviewer_username TYPE_USERNAME, _id INTEGER)
RETURNS void AS $$
BEGIN
    IF ((SELECT approval FROM IEDB.Change 
         WHERE reviewer_username = _reviewer_username) IS NOT NULL)
        THEN RETURN;
    END IF;
    
    UPDATE IEDB.Change
    SET    reviewer_username = _reviewer_username, approval = false
    WHERE  id = _id;
END;
$$ LANGUAGE plpgsql;
