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
import java.sql.SQLException;

import java.util.List;

abstract class ViewerDAO<T> extends DAO<T> {
    
    protected String defaultTable;
    
    protected ViewerDAO(String defaultTable, Connection connection) {
        super(connection);
        this.defaultTable = defaultTable;
    }
    
    public List<? extends T> getAll() {
        return this.retrieveAllFromQuery(
            "SELECT * FROM " + this.defaultTable + " ORDER BY id"
        );
    }
    
    public List<? extends T> getByName(final String name) {
        return this.retrieveAllFromQuery(
            "SELECT * FROM " + this. defaultTable +
            " WHERE lower(name) LIKE lower(?)",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                    stmt.setString(1, "%" + name + "%");
                }
            }
        );
    }

    abstract public void add    (final T t);
    abstract public void update (final T t);
    
    public boolean existsName(String name) {
        return !this.getByName(name).isEmpty();
    }
}
