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
import models.Client;
import models.ClientDAO;

// Views
import views.html.signup;
import views.html.search_results;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

public class Register extends Controller {

    public static Result signup() {
        return ok(signup.render());
    }

    public static Result addClient() {
        Client client = Form.form(Client.class).bindFromRequest().get();

        ClientDAO dao = new ClientDAO();
        dao.add(client);

        return redirect(controllers.routes.Application.index());
    }
}
