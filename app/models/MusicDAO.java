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

import java.util.Date;
import java.util.List;

public class MusicDAO extends ViewerDAO<Music> {

    public MusicDAO() {
        super("IEDB.Complete_music", DB.getConnection());
    }

    public MusicDAO(Connection connection) {
        super("IEDB.Complete_music", connection);
    }
    
    @Override
    protected Music buildFromResultSet(ResultSet rs) throws SQLException {
        Music music = new Music();
        music.setId           (rs.getInt    ("id"));
        music.setName         (rs.getString ("name"));
        music.setType         (rs.getString ("type"));
        music.setDateCreation (rs.getDate   ("date_creation"));
        music.setDescription  (rs.getString ("description"));
        /* music.setGenre       (rs.getString  ("genre")); */
        music.setDuration     (rs.getInt    ("duration"));
        /* music.setRate        (rs.getInt     ("rate")); */
        return music;
    }
}
