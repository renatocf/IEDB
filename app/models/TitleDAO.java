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
        
        String sql = "SELECT * FROM IEDB.Title ORDER BY id";
        
        try {
            List<Title> titles = new ArrayList<Title>();
            PreparedStatement stmt 
                = this.connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Title title = new Title();
                
                title.setId(rs.getInt("id"));
                title.setType(rs.getString("type"));
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

    public Title getTitleByTypeAndName(String type, String name){
        String sql = "SELECT * "+
                     "FROM IEDB.Complete_"+type.toLowerCase()+
                     " WHERE lower(name) = ?";
                     
        try {
            Title title = null;
            PreparedStatement stmt 
                = this.connection.prepareStatement(sql);

            stmt.setString(1, name.replace("-", " ").toLowerCase());
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

    public List<Title> getAllTitlesWithNameLike(String token){
        List<Title> titles_id_type = this.getTitlesNameAndTypeWithNameLike(token);
        List<Title> resp = new ArrayList<Title>();
        for(Title title : titles_id_type)
            resp.add(this.getTitleByTypeAndName(title.getType(), title.getName()));

        return resp;
    }

    private List<Title> getTitlesNameAndTypeWithNameLike(String token){
        String sql = " SELECT type, name"+
                     " FROM   IEDB.Title"  +
                     " WHERE  lower(name) LIKE ?";
        List<Title> titles = new ArrayList<Title>();
        Title new_title = null;

        try{
            PreparedStatement st = this.connection.prepareStatement(sql);
            st.setString(1, "%"+token+"%");
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                new_title = new Title();
                new_title.setName(rs.getString("name"));
                new_title.setType(rs.getString("type"));
                titles.add(new_title);
            }
        }catch(SQLException e){
            throw new RuntimeException();
        }
        return titles;
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
