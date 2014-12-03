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

public class PersonDAO extends ViewerDAO<Person> {

    public PersonDAO(Connection connection) {
        super("IEDB.Person", connection);
    }

    public PersonDAO() {
        this(DB.getConnection());
    }
    
    public void add(final Person person) {
        throw new RuntimeException("Not yet implemented");
    }
    
    public void update(final Person person) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    protected Person buildFromResultSet(ResultSet rs) throws SQLException {

        Person person = new Person();
        person.setName           (rs.getString ("name"));
        person.setBirthday       (rs.getDate   ("birhday"));
        person.setNationality    (rs.getString ("nationality"));
        person.setIsAuthor       (rs.getBoolean("is_author"));
        person.setIsActor        (rs.getBoolean("is_actor"));
        person.setIsDirector     (rs.getBoolean("is_director"));
        person.setIsMusician     (rs.getBoolean("is_musician"));
        return person;
    }
}
