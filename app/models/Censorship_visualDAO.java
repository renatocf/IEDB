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

public class Censorship_visualDAO extends ViewerDAO<Censorship_visual> {

    public Censorship_visualDAO(Connection connection) {
        super("IEDB.Complete_Censorship_visual", connection);
    }

    public Censorship_visualDAO() {
        this(DB.getConnection());
    }

    public List<? extends Censorship_visual> getByRating() {
    
        List<Censorship_visual> ratings 
        = this.retrieveAllFromQuery(
            "SELECT * FROM IEDB.Censorship_visual",
            new StatementConfigurator() {
                public void configureStatement(PreparedStatement stmt) 
                    throws SQLException {
                }
            }
        );
        return ratings;
    }
    
    @Override
    protected Censorship_visual buildFromResultSet(ResultSet rs) throws SQLException {
        
        Censorship_visual censorship = new Censorship_visual();
        censorship.setCensorship           (rs.getString    ("rating"));
        return censorship;
    }
}
