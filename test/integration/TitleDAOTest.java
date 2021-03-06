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

// Java Util
import java.util.List;
import java.util.Arrays;

// Tested class
import models.Title;
import models.TitleDAO;

// JUnit
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

// Fake application
import play.test.*;
import static play.test.Helpers.*;

public class TitleDAOTest extends WithApplication{

    private TitleDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new TitleDAO();
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }

    @Test
    public void getAll() {
        List<? extends Title> titles = dao.getAll();
        
        List<String> names = Arrays.asList(
            "The Lord of the Rings: The Fellowship of the Ring", 
            "Matrix",
            "Superman",
            "Lucy in the sky with diamonds",
            "Friends",
            "Harry Potter and the Philosopher's Stone"
        );
        
        assertEquals(6, titles.size());
        for(int i = 0; i < titles.size(); i++)
            assertEquals(titles.get(i).getName(), names.get(i));
    }

    @Test
    public void getByTypeAndName() {
        assertEquals(1, dao.getByTypeAndName("movie", "lord").size());
        assertEquals("The Lord of the Rings: The Fellowship of the Ring", 
            dao.getByTypeAndName("movie", "lord").get(0).getName());
    }

    @Test
    public void getByName() {
        assertEquals(1, dao.getByName("lord").size());
        assertEquals("The Lord of the Rings: The Fellowship of the Ring",
            dao.getByName("lord").get(0).getName());
    }
}
