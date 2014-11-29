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

// Models
import models.Title;
import models.TitleDAO;

// Views
import views.html.title;
import views.html.search_results;
import views.html.index;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

public class Viewer extends Controller {

    
    static private TitleDAO dao = new TitleDAO();

    public static Result search() {
        Title title = Form.form(Title.class).bindFromRequest().get();
        if(title.getName().equals(""))
            return ok(index.render());

        return ok(search_results.render(
            Viewer.dao.getByName(title.getName())
        ));
    }

    public static Result showTitle(String type, String name) {
        return ok(title.render(
            Viewer.dao.getByTypeAndName(type, name.replace('-',' ')).get(0)
        ));
    }
}
