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

    public PersonDAO() {
        super("IEDB.Person", DB.getConnection());
    }

    public PersonDAO(Connection connection) {
        super("IEDB.Person", connection);
    }
    
    /*@Override
    public List<? extends Title> getAll() {
        return this.buildCompleteFromTitle(super.getAll());
    }

    @Override
    public List<? extends Title> getByName(final String name) {
        return this.buildCompleteFromTitle(super.getByName(name));
    }*/

    @Override
    protected Person buildFromResultSet(ResultSet rs) throws SQLException {

        Person person = new Person();
        person.setName           (rs.getString ("name"));
        person.setBirthday       (rs.getDate   ("birhday"));
        person.setNationality    (rs.getString ("nationality"));
        return person;
    }
    
    /*private List<? extends Title> 
    buildCompleteFromPerson(List<? extends Title> partial) {
        
        List<Title> complete = new ArrayList<Title>();
        for(Title title: partial)
            complete.addAll(
                this.getByTypeAndName(title.getType(), title.getName())
            );
        return complete;
    }
    
    private ViewerDAO<? extends Title> getDAO(String type) {
        switch(type.toLowerCase()) {
            case "music":  return new MusicDAO  (this.connection);
            case "comic":  return new ComicDAO  (this.connection);
            case "book":   return new BookDAO   (this.connection);
            case "movie":  return new MovieDAO  (this.connection);
            case "series": return new SeriesDAO (this.connection);
            default: throw new RuntimeException("Invalid type " + type);
        }
    }*/
}
