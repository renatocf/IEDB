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

import java.util.*;

public class TitleDAO extends DAO<Title> {

    public TitleDAO() {
        super(DB.getConnection());
    }
    
    public List<Title> getAll() {
        return this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Title ORDER BY id"
        );
    }

    public List<Title> getAllTitlesWithNameLike(String token) {
        
        List<Title> partial = this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Title WHERE lower(name) LIKE ?"
        );
        
        List<Title> complete = new ArrayList<Title>();
        for(Title title: partial)
            complete.add(
                getTitleByTypeAndName(title.getType(), title.getName())
            );

        return complete;
    }

    public Title getTitleByTypeAndName(String type, String name) {
        
        switch(type.toLowerCase()) {
            case "music":
                MusicDAO musicDAO = new MusicDAO();
                return musicDAO.getByName(name).get(0);
            case "hq":
                HqDAO hqDAO = new HqDAO();
                return hqDAO.getByName(name).get(0);
            case "book":
                BookDAO bookDAO = new BookDAO();
                return bookDAO.getByName(name).get(0);
            case "movie": 
                MovieDAO movieDAO = new MovieDAO();
                return movieDAO.getByName(name).get(0);
            case "series":
                SeriesDAO seriesDAO = new SeriesDAO();
                return seriesDAO.getByName(name).get(0);
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
