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

import java.util.List;

public class StarsDAO extends DAO<Stars> {
    
    public StarsDAO() {
        super(DB.getConnection());
    }
    
    public StarsDAO(Connection connection) {
        super(connection);
    }
    
    public void add(final Stars stars) {
        this.persistFromQuery(
            "SELECT IEDB.create_stars(?, ?, ?)",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setString(1, stars.getClientEmail());
                    stmt.setInt(2, stars.getTitle());
                    stmt.setInt(3, stars.getRate());
                }
            }
        );
    }

    @Override
    protected Stars buildFromResultSet(ResultSet rs) throws SQLException {
        Stars stars = new Stars();
        stars.setClientEmail    (rs.getString  ("client_email"));
        stars.setTitle          (rs.getInt     ("title_id"));
        stars.setRate           (rs.getInt     ("rate"));
        return stars;
    }
}