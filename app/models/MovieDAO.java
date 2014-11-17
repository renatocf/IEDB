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

public class MovieDAO extends DAO<Movie> {

    public MovieDAO() {
        super(DB.getConnection());
    }

    public MovieDAO(Connection connection) {
        super(connection);
    }
    
    public List<Movie> getAll() {
        return this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Complete_movie"
        );
    }
    
    public List<Movie> getByName(String name) {
        return this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Complete_movie" + 
            " WHERE lower(name) LIKE lower(?)",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setString(1, "%" + name + "%");
                }
            }
        );
    }
    
    @Override
    protected Movie buildFromResultSet(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        movie.setId           (rs.getInt    ("id"));
        movie.setName         (rs.getString ("name"));
        movie.setType         (rs.getString ("type"));
        movie.setDateCreation (rs.getDate   ("date_creation"));
        movie.setDescription  (rs.getString ("description"));
        /* movie.setCensorship   (rs.getString ("censorship")); */
        /* movie.setGenre        (rs.getString ("genre")); */
        movie.setDuration     (rs.getInt    ("duration"));
        movie.setNationality  (rs.getString ("nationality"));
        /* movie.setRate         (rs.getInt    ("rate")); */
        return movie;
    }
}
