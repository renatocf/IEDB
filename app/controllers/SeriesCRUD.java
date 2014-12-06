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
import models.Series;
import models.SeriesDAO;
import models.Client;
import models.ClientDAO;

// Views
import views.html.index;
import views.html.title;
import views.html.add_series;

// Play
import play.data.Form;
import play.mvc.Result;
import play.twirl.api.Content;

public class SeriesCRUD extends CRUD<Series> {
    
    final private static SeriesCRUD self = new SeriesCRUD();
    final private static SeriesDAO dao = new SeriesDAO();
    
    public static SeriesCRUD getInstance() { return self; }
    
    protected Series find(String name) {
        return dao.getByName(name).get(0);
    }
    
    protected void storeNew(Form<Series> form) { 
        dao.add(form.get());
    }
    
    protected void storeChanged(Form<Series> form) { 
        dao.update(form.get());
    }
    
    protected Content renderUpdate(Form<Series> form, String response) {
        return add_series.render(
            form, response, 
            daoGenre.getAllVisual(), daoCensorship.getAllVisual()
        );
    }
    
    protected Content renderRead(Form<Series> form) {
        return title.render(form.get());
    }
    
    private SeriesCRUD() {
        super(Series.class, "series");
    }
}
