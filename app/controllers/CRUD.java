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
import models.TitleDAO;
import models.GenreDAO;
import models.CensorshipDAO;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;
import play.twirl.api.Content;

abstract public class CRUD<T> extends Controller {

    protected TitleDAO      daoTitle      = new TitleDAO();
    protected GenreDAO      daoGenre      = new GenreDAO();
    protected CensorshipDAO daoCensorship = new CensorshipDAO();

    protected Class<T> clazz;

    protected CRUD(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Result create() {
        return ok(this.renderUpdate(this.newForm()));
    }

    public Result read(String name) {
        return ok(this.renderRead(this.newForm(name)));
    }

    public Result update(String name) {
        return ok(this.renderUpdate(this.newForm(name)));
    }

    public Result add() {
        Form<T> form = Form.form(clazz).bindFromRequest();

        if (form.hasErrors())
            return badRequest(this.renderUpdate(form));

        this.store(form);
        return redirect(routes.Application.index());
    }

    abstract protected T       find         (String name);
    abstract protected void    store        (Form<T> form);
    abstract protected Content renderRead   (Form<T> form);
    abstract protected Content renderUpdate (Form<T> form);
    
    private Form<T> newForm(String name) {
        return Form.form(clazz).fill(this.find(name));
    }
    
    private Form<T> newForm() {
        return Form.form(clazz);
    }
}
