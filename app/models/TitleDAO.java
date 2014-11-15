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

public class TitleDAO {

    Connection connection;
    
    public TitleDAO() {
        this.connection = DB.getConnection();
    }
    
    public List<Title> getAll() {
        
        String sql = "SELECT * FROM IEDB.Title";
        
        try {
            List<Title> titles = new ArrayList<Title>();
            PreparedStatement stmt 
                = this.connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Title title = new Title();
                
                title.setId(rs.getInt("id"));
                /* title.setCameFrom(rs.getInt("came_from")); */
                title.setName(rs.getString("name"));
                title.setDateCreation(rs.getDate("date_creation"));
                title.setDescription(rs.getString("description"));
                
                titles.add(title);
            }
            
            rs.close();
            stmt.close();
            return titles;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Title> getAllReferences(Title title){

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
    }
}
