package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.Client;

import views.html.*;

public class Login extends Controller {

	public static Result login() {
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
