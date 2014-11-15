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
                title.setType(rs.getString("type"));
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

    public Title getTitleByTypeAndName(String type, String name){
        String sql = "SELECT * "+
                     "FROM IEDB.Complete_"+type.toLowerCase()+
                     " WHERE lower(name) = ?";
                     
        try {
System.out.println(type);
System.out.println(name);
            Title title = null;
            PreparedStatement stmt 
                = this.connection.prepareStatement(sql);

            stmt.setString(1, name.replace("-", " "));
            ResultSet rs = stmt.executeQuery();
            
            rs.next();// maybe treat the case where its false?
            switch(type.toLowerCase()){
                case "music":
                    Music music = new Music();
                    music.setDuration(rs.getInt("duration"));
                    title = music;
                    break;
                case "hq":
                    Hq hq = new Hq();
                    hq.setArc(rs.getString("arc"));
                    hq.setNum(rs.getInt("num"));
                    title = hq;
                    break;
                case "book":
                    Book book = new Book();
                    book.setNumEditions(rs.getInt("num_editions"));
                    title = book;
                    break;
                case "movie":
                    Movie movie = new Movie();
                    movie.setNationality(rs.getString("nationality"));
                    movie.setDuration(rs.getInt("duration"));
                    //movie.setCensorship(rs.getString("censorship"));
                    title = movie;
                    break;
                case "series":
                    Series series = new Series();
                    series.setDateInit(rs.getDate("date_init"));
                    series.setDateEnd(rs.getDate("date_end"));
                    series.setNumSeasons(rs.getInt("num_seasons"));
                    //series.setCensorship(rs.getString("censorship"));
                    title = series;
                    break;
                default:
                    throw new RuntimeException();
            }        

            title.setId(rs.getInt("id"));
            title.setType(rs.getString("type"));
            title.setName(rs.getString("name"));
            title.setDateCreation(rs.getDate("date_creation"));
            title.setDescription(rs.getString("description"));
            
            rs.close();
            stmt.close();
            return title;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    /*
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
    }*/
}
