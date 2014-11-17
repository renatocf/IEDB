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

public class BookDAO extends ViewerDAO<Book> {

    public BookDAO() {
        super("IEDB.Complete_book", DB.getConnection());
    }

    public BookDAO(Connection connection) {
        super("IEDB.Complete_book", connection);
    }

    @Override
    protected Book buildFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId           (rs.getInt    ("id"));
        book.setName         (rs.getString ("name"));
        book.setType         (rs.getString ("type"));
        book.setDateCreation (rs.getDate   ("date_creation"));
        book.setDescription  (rs.getString ("description"));
        /* book.setGenre        (rs.getString ("genre")); */
        book.setNumEditions  (rs.getInt    ("num_editions"));
        /* book.setRate         (rs.getInt    ("rate")); */
        return book;
    }
}
