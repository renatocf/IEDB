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
import models.BookDAO;

// JUnit
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

// Fake application
import play.test.*;
import static play.test.Helpers.*;

public class BookDAOTest extends WithApplication{

    private BookDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new BookDAO();
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }

    @Test
    public void getByName() {        
        assertEquals(1, dao.getByName("Potter").size());
        assertEquals("Harry Potter and the Philosopher's Stone", 
            dao.getByName("Potter").get(0).getName());
    }

    @Test
    public void getAll() {
        assertEquals(1, dao.getAll().size());
        assertEquals("Harry Potter and the Philosopher's Stone", 
            dao.getAll().get(0).getName());
    }
}
