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
import models.Movie;
import models.MovieDAO;
import models.Client;
import models.ClientDAO;

// Views
import views.html.index;
import views.html.title;
import views.html.add_movie;

// Play
import play.data.Form;
import play.mvc.Result;
import play.twirl.api.Content;

public class MovieCRUD extends CRUD<Movie> {
    
    final private static MovieCRUD self = new MovieCRUD();
    final private static MovieDAO dao = new MovieDAO();
    
    public static MovieCRUD getInstance() { return self; }
    
    protected Movie find(String name) {
        return dao.getByName(name).get(0);
    }

    protected void storeNew(Form<Movie> form) { 
        dao.add(form.get());
    }

    protected void storeChanged(Form<Movie> form) { 
        dao.update(form.get());
    }
    
    protected Content renderUpdate(Form<Movie> form) {
        return add_movie.render(
            form, daoGenre.getAllVisual(), daoCensorship.getAllVisual()
        );
    }
    
    protected Content renderRead(Form<Movie> form) {
        return title.render(form.get());
    }
    
    private MovieCRUD() {
        super(Movie.class);
    }
}
