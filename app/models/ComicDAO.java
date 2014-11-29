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

public class ComicDAO extends ViewerDAO<Comic> {

    public ComicDAO(Connection connection) {
        super("IEDB.Complete_comic", connection);
    }

    public ComicDAO() {
        this(DB.getConnection());
    }

    public void add(final Comic comic) {
        this.persistFromQuery(
            "SELECT IEDB.create_comic(?,?,?,?,?)",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setString (1, comic.getName());
                    stmt.setString (2, comic.getDescription());
                    stmt.setString (3, comic.getGenre());
                    stmt.setString (4, comic.getArc());
                    stmt.setInt    (5, comic.getNum());
                }
            }
        );
    }
    
    @Override
    protected Comic buildFromResultSet(ResultSet rs) throws SQLException {
        
        Comic comic = new Comic();
        TitleDAO daoTitle = new TitleDAO();
        comic.setId           (rs.getInt    ("id"));
        comic.setName         (rs.getString ("name"));
        comic.setType         (rs.getString ("type"));
        comic.setDateCreation (rs.getDate   ("date_creation"));
        comic.setDescription  (rs.getString ("description"));
        comic.setReferences   (daoTitle.getAllReferences(comic));
        /* comic.setGenre       (rs.getString  ("genre")); */
        comic.setNum          (rs.getInt    ("num"));
        comic.setArc          (rs.getString ("arc"));
        /* comic.setRate        (rs.getInt     ("rate")); */
        return comic;
    }
}
