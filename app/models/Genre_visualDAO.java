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

public class Genre_visualDAO extends DAO<Genre_visual> {
    
    public Genre_visualDAO(Connection connection) {
        super(connection);
    }

	public Genre_visualDAO() {
        super(DB.getConnection());
    }

	public List<Genre_visual> getByGenre() {
        return this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Genre_visual",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                }
            }
        );
    }

    @Override
    protected Genre_visual buildFromResultSet(ResultSet rs) 
        throws SQLException {
        
        Genre_visual genre = new Genre_visual();
        genre.setGenre(rs.getString("name"));
        return genre;
    }
}
