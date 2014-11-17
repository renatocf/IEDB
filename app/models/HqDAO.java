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

public class HqDAO extends DAO<Hq> {

    public HqDAO() {
        super(DB.getConnection());
    }

    public HqDAO(Connection connection) {
        super(connection);
    }
    
    public List<Hq> getAll() {
        return this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Complete_hq"
        );
    }
    
    public List<Hq> getByName(final String name) {
        return this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Complete_hq" + 
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
    protected Hq buildFromResultSet(ResultSet rs) throws SQLException {
        Hq hq = new Hq();
        hq.setId           (rs.getInt    ("id"));
        hq.setName         (rs.getString ("name"));
        hq.setType         (rs.getString ("type"));
        hq.setDateCreation (rs.getDate   ("date_creation"));
        hq.setDescription  (rs.getString ("description"));
        /* hq.setGenre       (rs.getString  ("genre")); */
        hq.setNum          (rs.getInt    ("num"));
        hq.setArc          (rs.getString ("arc"));
        /* hq.setRate        (rs.getInt     ("rate")); */
        return hq;
    }
}
