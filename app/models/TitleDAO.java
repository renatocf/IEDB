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

public class TitleDAO extends ViewerDAO<Title> {

    public TitleDAO() {
        super("IEDB.Title", DB.getConnection());
    }

    public TitleDAO(Connection connection) {
        super("IEDB.Title", connection);
    }

    public Boolean thisNameExists(final String name, final String type) {
    
        List<Title> titles
        = this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Title WHERE (name = ?) and (type = ?)",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setString(1, name);
                    stmt.setString(2, type);
                }
            }
        );
        return titles.size() > 0 ? true : false;
    }
    
    @Override
    public List<? extends Title> getAll() {
        return this.buildCompleteFromTitle(super.getAll());
    }

    @Override
    public List<? extends Title> getByName(final String name) {
        return this.buildCompleteFromTitle(super.getByName(name));
    }

    public List<? extends Title>
    getByTypeAndName(String type, String name) {
        return this.getDAO(type).getByName(name);
    }
    
    @Override
    protected Title buildFromResultSet(ResultSet rs) throws SQLException {

        Title title = new Title();
        title.setId           (rs.getInt    ("id"));
        title.setType         (rs.getString ("type"));
        title.setName         (rs.getString ("name"));
        title.setDateCreation (rs.getDate   ("date_creation"));
        title.setDescription  (rs.getString ("description"));
        return title;
    }
    
    private List<? extends Title> 
    buildCompleteFromTitle(List<? extends Title> partial) {
        
        List<Title> complete = new ArrayList<Title>();
        for(Title title: partial)
            complete.addAll(
                this.getByTypeAndName(title.getType(), title.getName())
            );
        return complete;
    }
    
    private ViewerDAO<? extends Title> getDAO(String type) {
        switch(type.toLowerCase()) {
            case "music":  return new MusicDAO  (this.connection);
            case "comic":  return new ComicDAO  (this.connection);
            case "book":   return new BookDAO   (this.connection);
            case "movie":  return new MovieDAO  (this.connection);
            case "series": return new SeriesDAO (this.connection);
            default: throw new RuntimeException("Invalid type " + type);
        }
    }
     
    public List<Title> getAllReferences(final Title title) {

        List<Title> titles
        = this.retrieveAllFromQuery(
            "SELECT id, type, name, date_creation, description "+
            "FROM   IEDB.rel_references, IEDB.Title "+
            "WHERE  referencer_title_id = ? AND "+
                    "refered_title_id = id",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setInt(1, title.getId());
                }
            }
        );
        return titles;
    }
}
