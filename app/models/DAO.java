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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

abstract class DAO<T> {
    
    protected Connection connection;
    
    protected interface StatementConfigurator {
        public void configureStatement(PreparedStatement stmt) 
            throws SQLException;
    }
    
    protected DAO(Connection connection) {
        this.connection = connection;
    }
    
    abstract protected T buildFromResultSet(ResultSet rs) 
        throws SQLException;
    
    protected List<T> retrieveAllFromQuery(String sql, 
                                           StatementConfigurator scfg) {
        try {
            PreparedStatement stmt
                = this.connection.prepareStatement(sql);

            scfg.configureStatement(stmt);
            List<T> Ts = retrieveAllFromStatement(stmt);

            stmt.close();
            return Ts;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<T> retrieveAllFromQuery(String sql) {
        return this.retrieveAllFromQuery(sql, 
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {}
        });
    }
    
    protected void persistFromQuery(String sql, 
                                    StatementConfigurator scfg) {
        try {
            PreparedStatement stmt
                = this.connection.prepareStatement(sql);

            scfg.configureStatement(stmt);
            this.persistFromStatement(stmt);
            
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected void persistFromQuery(String sql) {
        this.persistFromQuery(sql, 
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {}
        });
    }
    
    protected java.sql.Date toSQLDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    
    private List<T> retrieveAllFromStatement(PreparedStatement stmt) 
        throws SQLException {
        
        List<T> Ts = new ArrayList<T>();

        ResultSet rs = stmt.executeQuery();
        while (rs.next())
            Ts.add(buildFromResultSet(rs));
        rs.close();

        return Ts;
    }
    
    private void persistFromStatement(PreparedStatement stmt) 
        throws SQLException {
        stmt.execute();
    }
}
