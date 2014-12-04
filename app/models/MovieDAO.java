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

public class MovieDAO extends ViewerDAO<Movie> {

    public MovieDAO(Connection connection) {
        super("IEDB.Complete_movie", connection);
    }

    public MovieDAO() {
        this(DB.getConnection());
    }

    public void add(final Movie movie) {
        this.persistFromQuery(
            "SELECT IEDB.create_movie(?,?,?,?,?,?)",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setString (1, movie.getName());
                    stmt.setString (2, movie.getDescription());
                    stmt.setString (3, movie.getGenre());
                    stmt.setInt    (4, movie.getDuration());
                    stmt.setString (5, movie.getNationality());
                    stmt.setString (6, movie.getCensorship());
                }
            }
        );
    }

    public void update(final Movie movie) {
        this.persistFromQuery(
            "SELECT IEDB.create_movie(?,?,?,?,?,?)",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setString (1, movie.getName());
                    stmt.setString (2, movie.getDescription());
                    stmt.setString (3, movie.getGenre());
                    stmt.setInt    (4, movie.getDuration());
                    stmt.setString (5, movie.getNationality());
                    stmt.setString (6, movie.getCensorship());
                }
            }
        );
    }
    
    @Override
    protected Movie buildFromResultSet(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        TitleDAO daoTitle = new TitleDAO();
        movie.setId           (rs.getInt    ("id"));
        movie.setName         (rs.getString ("name"));
        movie.setType         (rs.getString ("type"));
        movie.setDateCreation (rs.getDate   ("date_creation"));
        movie.setDescription  (rs.getString ("description"));
        movie.setReferences   (daoTitle.getAllReferences(movie));
        /* movie.setCensorship   (rs.getString ("censorship")); */
        /* movie.setGenre        (rs.getString ("genre")); */
        movie.setDuration     (rs.getInt    ("duration"));
        movie.setNationality  (rs.getString ("nationality"));
        movie.setRate         (rs.getFloat  ("rate"));
        return movie;
    }
}
