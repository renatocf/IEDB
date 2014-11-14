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

/* import play.db.DB; */
import play.db.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class MovieDAO {

    Connection connection;
    
    public MovieDAO() {
        /* this.connection = DriverManager.getConnection(); */
        this.connection = DB.getConnection();
    }
    
    public List<Movie> getByName(String name) {
        
        String sql = "SELECT * FROM IEDB.Complete_movie" +
                     " WHERE lower(name) LIKE lower(?)";
        
        try {
            List<Movie> movies = new ArrayList<Movie>();
            
            PreparedStatement stmt
                = this.connection.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Movie movie = new Movie();
                
                movie.setId(rs.getInt("id"));
                movie.setName(rs.getString("name"));
                movie.setDateCreation(rs.getDate("date_creation"));
                movie.setDescription(rs.getString("description"));
                /* movie.setRate(rs.getInt("rate")); */
                /* movie.setGenre(rs.getString("genre")); */
                /* movie.setCensorship(rs.); */
                movie.setDuration(rs.getInt("duration"));
                movie.setNationality(rs.getString("nationality"));
                
                movies.add(movie);
            }
            
            rs.close();
            stmt.close();
            return movies;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Movie> getAll() {
        
        String sql = "SELECT * FROM IEDB.Movie";
        
        try {
            List<Movie> movies = new ArrayList<Movie>();
            PreparedStatement stmt 
                = this.connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Movie movie = new Movie();
                
                movie.setId(rs.getInt("id"));
                movie.setCameFrom(rs.getInt("came_from"));
                movie.setName(rs.getString("name"));
                movie.setDateCreation(rs.getDate("date_creation"));
                movie.setDescription(rs.getString("description"));
                
                movies.add(movie);
            }
            
            rs.close();
            stmt.close();
            return movies;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
