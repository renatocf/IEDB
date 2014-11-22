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
import play.data.validation.ValidationError;

// Java Util
import java.util.List;
import java.util.ArrayList;

public class Register extends Controller {

    final private static ClientDAO dao = new ClientDAO();

    public static Result signup() {
        return ok(signup.render(Form.form(Register.NewClient.class)));
    }

    public static Result addClient() {
        Form<NewClient> clientForm
            = Form.form(Register.NewClient.class).bindFromRequest();
        
    	if (clientForm.hasErrors())
    	 	return badRequest(signup.render(clientForm));
        
        Register.dao.add(clientForm.get());
        return redirect(routes.Application.index());
    }
    
	public static class NewClient extends Client {
        
        private String password_conf;
            
    	public List<ValidationError> validate() {
            
            List<ValidationError> errors = new ArrayList<>();
            if (Register.dao.existsUsername(this.getUsername())) {
                errors.add(new ValidationError(
                    "username", "Username already exists!"));
            }
            if (Register.dao.existsEmail(this.getEmail())) {
                errors.add(new ValidationError(
                    "email", "Email already registered!"));
            }
            if (this.getPassword().equals(this.password_conf)) {
                errors.add(new ValidationError(
                    "password_conf", "Confirmation doesn't match!"));
            }
            return errors.isEmpty() ? null : errors;
        }
	}
}
