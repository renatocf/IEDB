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
import models.SeriesDAO;

// JUnit
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

// Fake application
import static play.test.Helpers.running;
import static play.test.Helpers.fakeApplication;

public class SeriesDAOTest {
    
    private SeriesDAO dao;

    /*@Before
    public void setUp() throws Exception{
        running(fakeApplication(), new Runnable() {
            public void run() {
                dao = new SeriesDAO();
            }
        });
    }

    @After
    public void tearDown() throws Exception{
        dao = null;
    }*/

    @Test
    public void getByName() {
        running(fakeApplication(), new Runnable(){
            public void run(){
                dao = new SeriesDAO();
                assertEquals(1, dao.getByName("Friends").size());
                assertEquals("Friends", dao.getByName("Friends").get(0).getName());
            }
        });
    }
    
    @Test
    public void getAll() {
        running(fakeApplication(), new Runnable(){
            public void run(){
                dao = new SeriesDAO();
                assertEquals(1, dao.getAll().size());
                assertEquals("Friends", dao.getAll().get(0).getName());
            }
        });
    }
}
