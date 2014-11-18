package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.Client;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(""));
    }
    public static Result addTitle(){
    	return ok(addTitle.render(""));
    }

    public static Result addMusic(){
    	return ok(addMusic.render(""));
    }
    public static Result addBook(){
    	return ok(addBook.render(""));
    }
    public static Result addHQ(){
    	return ok(addHQ.render(""));
    }
    public static Result addMovie(){
    	return ok(addMovie.render(""));
    }
    public static Result addSeries(){
    	return ok(addSeries.render(""));
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
	    if (Client.authenticate(email, password) == null) {
	    	return "Invalid user or password";
	    }
	    return null;
	}

	}
}
