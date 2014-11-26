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
import views.html.signin;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;
import play.data.validation.ValidationError;

// Java Util
import java.util.List;
import java.util.ArrayList;

public class Login extends Controller {

    final private static ClientDAO dao = new ClientDAO();

	public static Result signin() {
	    return ok(signin.render(Form.form(PossibleClient.class)));
	}

    public static Result signout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.Application.index());
    }

	public static Result authenticate() {
    	Form<PossibleClient> clientForm 
            = Form.form(PossibleClient.class).bindFromRequest();

    	if (clientForm.hasErrors())
    	 	return badRequest(signin.render(clientForm));

        session().clear();
        session("email", clientForm.get().getEmail());
        return redirect(routes.Application.index());
	}

	public static class PossibleClient extends Client {

    	public List<ValidationError> validate() {

            List<ValidationError> errors = new ArrayList<>();

            System.err.println("USERNAME ===> " + this.getUsername());
            System.err.println("PASSWORD ===> " + this.getPassword());
            Client client = Login.dao.getByUsername(this.getUsername());
            if (client == null) {
                errors.add(new ValidationError(
                    "username", "Invalid username!"));
            }
            else if (client.getPassword() != this.getPassword()) {
                errors.add(new ValidationError(
                    "password", "Invalid password!"));
            }
            return errors.isEmpty() ? null : errors;
        }
	}
}
