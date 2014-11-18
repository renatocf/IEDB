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

public class Login extends Controller {

	public static Result signin() {
	    return ok(signin.render(Form.form(Signin.class)));
	}

	public static Result authenticate() {
    	Form<Signin> signinForm = Form.form(Signin.class).bindFromRequest();
    	if (signinForm.hasErrors()) {
    	 	return badRequest(signin.render(signinForm));
    	} 
    	else {
	      	session().clear();
	        session("email", signinForm.get().email);
	        return redirect(routes.Application.index());
	    }
	}

	public static class Signin {

    	public String email;
    	public String password;

    	public String validate() {
            if (Client.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
	}
}
