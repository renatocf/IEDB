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

public class SeriesDAO extends ViewerDAO<Series> {

    public SeriesDAO() {
        super("IEDB.Complete_series", DB.getConnection());
    }

    public SeriesDAO(Connection connection) {
        super("IEDB.Complete_series", connection);
    }
    
    @Override
    protected Series buildFromResultSet(ResultSet rs) throws SQLException {
        Series series = new Series();
        series.setId           (rs.getInt    ("id"));
        series.setName         (rs.getString ("name"));
        series.setType         (rs.getString ("type"));
        series.setDateCreation (rs.getDate   ("date_creation"));
        series.setDescription  (rs.getString ("description"));
        /* series.setCensorship  (rs.getString  ("censorship")); */
        /* series.setGenre       (rs.getString  ("genre")); */
        series.setDateInit     (rs.getDate   ("date_init"));
        series.setDateEnd      (rs.getDate   ("date_end"));
        series.setNumSeasons   (rs.getInt    ("num_seasons"));
        /* series.setRate        (rs.getInt     ("rate")); */
        return series;
    }
}
