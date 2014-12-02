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
package integration;

// Tested class
import play.db.DB;
import javax.sql.DataSource;
import java.sql.Connection;

// JUnit
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

// Fake application
import play.test.*;
import static play.test.Helpers.*;

public class DBTest extends WithApplication {

    @Test
    public void testGetDataSource() {
        DataSource ds = DB.getDataSource();
        assertNotNull(ds);
    }

    @Test
    public void testGetConnection() {
        Connection conn = DB.getConnection();
        assertNotNull(conn);
    }
}
