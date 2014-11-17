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

// Views
import views.html.index;
import views.html.login;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result addTitle(){
    	return ok(addTitle.render());
    }

	public static Result login() {
	    return ok(login.render(Form.form(Login.class)));
	}

	public static Result authenticate() {
        
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        
    	if (loginForm.hasErrors()) {
    	 	return badRequest(login.render(loginForm));
    	} 
    	else {
	      	session().clear();
	        session("email", loginForm.get().email);
	        return redirect(routes.Application.index());
	    }
	}

	public static class Login {
    	public String email;
    	public String password;
    	public String validate() {
            ClientDAO dao = new ClientDAO();
            if (dao.find(email, password) == null)
                return "Invalid user or password";
            return null;
        }
	}
}
