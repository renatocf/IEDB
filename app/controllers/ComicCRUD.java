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
package controllers;

// Model
import models.Comic;
import models.ComicDAO;

// Views
import views.html.index;
import views.html.title;
import views.html.add_comic;

// Play
import play.data.Form;
import play.mvc.Result;
import play.twirl.api.Content;

public class ComicCRUD extends CRUD<Comic> {
    
    final private static ComicCRUD self = new ComicCRUD();
    final private static ComicDAO dao = new ComicDAO();
    
    public static ComicCRUD getInstance() { return self; }
    
    protected Comic find(String name) {
        return dao.getByName(name).get(0);
    }
    
    protected void storeNew(Form<Comic> form) { 
        dao.add(form.get());
    }
    
    protected void storeChanged(Form<Comic> form) { 
        dao.update(form.get());
    }
    
    protected Content renderUpdate(Form<Comic> form) {
        return add_comic.render(form, daoGenre.getAllWritten());
    }
    
    protected Content renderRead(Form<Comic> form) {
        return title.render(form.get());
    }
    
    private ComicCRUD() {
        super(Comic.class);
    }
}
