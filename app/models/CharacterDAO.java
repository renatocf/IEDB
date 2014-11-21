/**********************************************************************/
/* Copyright 2014 IEDB                                                */
/*                                                                    */
/* Licensed under the Apache License, Version 2.0 (the "License");    */
/* you may not use this file except in compliance with the License.   */
/* You may obtain a copy of the License at                            */
/*                                                                    */
/*     http://www.apache.org/licenses/LICENSE-2.0                     */
/*                                                                    */
/* Unless required by applicable law or agreed to in writing,         */
/* software distributed under the License is distributed on an        */
/* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,       */
/* either express or implied.                                         */
/* See the License for the specific language governing permissions    */
/* and limitations under the License.                                 */
/**********************************************************************/
package models;

import play.db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CharacterDAO extends ViewerDAO<Character> {

    public CharacterDAO(Connection connection) {
        super("IEDB.Character", connection);
    }

    public CharacterDAO() {
        this(DB.getConnection());
    }

    public List<Character> getAllCharactersFromTitle(final Title title) {
        List<Character> characters
        = this.retrieveAllFromQuery(
            "SELECT character_name FROM IEDB.rel_has WHERE title_id = ?",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setInt(1, title.getId());
                }
            }
        );
        return characters;
    }
    
    @Override
    protected Character buildFromResultSet(ResultSet rs) 
        throws SQLException {

        Character character = new Character();
        character.setName         (rs.getString ("name"));
        return character;
    }
}
