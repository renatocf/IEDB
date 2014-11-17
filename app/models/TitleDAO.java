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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class TitleDAO extends DAO<Title> {

    public TitleDAO() {
        super(DB.getConnection());
    }

    public TitleDAO(Connection connection) {
        super(connection);
    }
    
    public List<? extends Title> getAll() {
        return this.buildCompleteFromTitle(this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Title ORDER BY id"
        ));
    }

    public List<? extends Title> getByName(String name) {
        return this.buildCompleteFromTitle(this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Title WHERE lower(name) LIKE ?",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setString(1, "%" + name + "%");
                }
            }
        ));
    }

    public List<? extends Title>
    getByTypeAndName(String type, String name) {
        
        switch(type.toLowerCase()) {
            case "music":
                MusicDAO musicDAO = new MusicDAO(this.connection);
                return musicDAO.getByName(name);
            case "hq":
                HqDAO hqDAO = new HqDAO(this.connection);
                return hqDAO.getByName(name);
            case "book":
                BookDAO bookDAO = new BookDAO(this.connection);
                return bookDAO.getByName(name);
            case "movie": 
                MovieDAO movieDAO = new MovieDAO(this.connection);
                return movieDAO.getByName(name);
            case "series":
                SeriesDAO seriesDAO = new SeriesDAO(this.connection);
                return seriesDAO.getByName(name);
            default:
                throw new RuntimeException("Invalid type " + type);
        }
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
    buildCompleteFromTitle(List<Title> partial) {
        
        List<Title> complete = new ArrayList<Title>();
        for(Title title: partial)
            complete.addAll(
                this.getByTypeAndName(title.getType(), title.getName())
            );
        return complete;
    }
    
    /*
    private List<Title> getAllReferences(Title title) {

        String sql = "SELECT refered_title_id"+
                      "FROM IEDB.rel_references"+
                      "WHERE referencer_title_id = ?";
        
        try {
            List<Title> references = new ArrayList<Title>();
            PreparedStatement stmt 
                = this.connection.prepareStatement(sql);
            stmt.setString(1, title.getId()+"");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Title title_ref = new Title();
                
                title_ref.setId(rs.getInt("id"));
                references.add(title_ref);
            }
            
            rs.close();
            stmt.close();
            return references;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }   
    }*/
}
