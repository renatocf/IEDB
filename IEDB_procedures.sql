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
                          STORED PROCEDURES                      
-----------------------------------------------------------------------
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
*/

------------------------------------------------------------------------
--                        Create new account                          --
------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION create_client(
    _username TYPE_USERNAME, _email TYPE_EMAIL, _password TYPE_PASSWORD)
RETURNS void AS $$
BEGIN
    INSERT INTO IEDB.Client(username, email, password)
    VALUES(_username, _email, _password);
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                          Change account                            --
------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION change_client_email(
    _username TYPE_USERNAME, _new_email TYPE_EMAIL)
RETURNS void AS $$
BEGIN
    UPDATE IEDB.Client SET email = _new_email
    WHERE  username = _username;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION change_client_password(
    _username TYPE_USERNAME, _password TYPE_PASSWORD)
RETURNS void AS $$
BEGIN
    UPDATE IEDB.Client SET email = _new_email
    WHERE  password = _password;
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                           Create title                             --
------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION create_title(_type varchar(6), _name TYPE_NAME)
RETURNS void AS $$
DECLARE
    _id INTEGER;
BEGIN
    INSERT INTO IEDB.Title(name, date_creation)
    VALUES(_name, current_date) RETURNING id INTO _id;
    
    CASE
        WHEN _type = 'music' THEN
            INSERT INTO IEDB.Auditive (title_id)    VALUES(_id);
            INSERT INTO IEDB.Music    (auditive_id) VALUES(_id);
        WHEN _type = 'book' THEN      
            INSERT INTO IEDB.Written (title_id)   VALUES(_id);
            INSERT INTO IEDB.Book    (written_id) VALUES(_id);
        WHEN _type = 'hq' THEN                    
            INSERT INTO IEDB.Written (title_id)   VALUES(_id);
            INSERT INTO IEDB.HQ      (written_id) VALUES(_id);
        WHEN _type = 'movie' THEN                   
            INSERT INTO IEDB.Visual (title_id)  VALUES(_id);
            INSERT INTO IEDB.Movie  (visual_id) VALUES(_id);
        WHEN _type = 'series' THEN              
            INSERT INTO IEDB.Visual (title_id)  VALUES(_id);
            INSERT INTO IEDB.Series (visual_id) VALUES(_id);
        ELSE
            RAISE EXCEPTION 'Incorrect type --> %', _type
            USING HINT = 'Please check valid title types';
    END CASE;
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                          Calculate rate                            --
------------------------------------------------------------------------

-- Title
CREATE OR REPLACE FUNCTION calculate_rate(_title_id INTEGER)
RETURNS INTEGER AS $$
BEGIN
    RETURN (SELECT count(*) FROM IEDB.rel_stars
            WHERE  title_id = _title_id);
END;
$$ LANGUAGE plpgsql;

-- Adaptation
CREATE OR REPLACE FUNCTION calculate_rate(
    _original_title_id INTEGER, _adaptation_title_id INTEGER)
RETURNS INTEGER AS $$
BEGIN
    RETURN (SELECT count(*) FROM IEDB.rel_rates
            WHERE  original_title_id = _original_title_id
              AND  adaptation_title_id = _adaptation_title_id);
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                           Create change                            --
------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION create_change(
    _submitter_username TYPE_USERNAME,
    _target_table       VARCHAR(32),
    _operation          CHAR(6),
    _afected_col        VARCHAR(32),
    _info               TEXT)
RETURNS void AS $$
BEGIN
    INSERT INTO IEDB.Change(submitter_username, target_table,
                            operation, afected_col, info)
    VALUES(_submitter_username, _target_table, 
           _operation, _afected_col, _info);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION create_change(
    _submitter_username TYPE_USERNAME,
    _prototype_name     TYPE_NAME,
    _info               TEXT)
RETURNS void AS $$
DECLARE
    _target_table VARCHAR(32)  NOT NULL;
    _operation    CHAR(6)      NOT NULL;
    _afected_col  VARCHAR(32)  NOT NULL;
BEGIN
    _target_table := (SELECT target_table FROM IEDB.Prototype_change
                      WHERE name = _prototype_name);
    _operation    := (SELECT operation FROM IEDB.Prototype_change
                      WHERE name = _prototype_name);
    _afected_col  := (SELECT afected_col FROM IEDB.Prototype_change
                      WHERE name = _prototype_name);
    
    INSERT INTO IEDB.Change(submitter_username, target_table,
                            operation, afected_col, info)
    VALUES(_submitter_username, _target_table, 
           _operation, _afected_col, _info);
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------
--                           Allow change                             --
------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION allow_change(_id INTEGER)
RETURNS void AS $$
DECLARE 
    _target_table VARCHAR(32);
    _operation    CHAR(6);
    _afected_col  VARCHAR(32);
    _info         TEXT;
BEGIN
    _target_table := (SELECT target_table
                      FROM IEDB.Change WHERE id = _id);
    _operation    := (SELECT operation
                      FROM IEDB.Change WHERE id = _id);
    _afected_col  := (SELECT afected_col
                      FROM IEDB.Change WHERE id = _id);
    _info         := (SELECT info
                      FROM IEDB.Change WHERE id = _id);
    
    IF( _operation = 'INSERT' ) THEN
        EXECUTE '
            INSERT INTO ' || _target_table || '(' || _afected_col  || ')
            VALUES (' || quote_literal(_info) || ')';
    ELSIF( _operation = 'UPDATE' ) THEN
        EXECUTE '
            UPDATE ' || _target_table || '
            SET    ' || _afected_col  || '
            =      ' || quote_literal(_info);
    ELSIF( _operation = 'DELETE' ) THEN
        EXECUTE '
            DELETE FROM ' || _target_table || '
            WHERE' || _afected_col || '=' || quote_literal(_info);
    END IF;
END;
$$ LANGUAGE plpgsql;
