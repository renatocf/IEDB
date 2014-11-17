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

public class ClientDAO extends DAO<Client> {
    
    public ClientDAO() {
        super(DB.getConnection());
    }
    
    public ClientDAO(Connection connection) {
        super(connection);
    }
    
    public void addClient(final Client client) {
        this.persistFromQuery(
            "SELECT IEDB.create_account(?, ?, ?)",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setString(1, client.getUsername());
                    stmt.setString(2, client.getEmail());
                    stmt.setString(3, client.getPassword());
                }
            }
        );
    }

    @Override
    protected Client buildFromResultSet(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setUsername   (rs.getString  ("username"));
        client.setEmail      (rs.getString  ("email"));
        client.setPassword   (rs.getString  ("password"));
        client.setIsActive   (rs.getBoolean ("active"));
        client.setIsReviewer (rs.getBoolean ("reviewer"));
        return client;
    }
}
