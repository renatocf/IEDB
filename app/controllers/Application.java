package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.Client;

// Views
import views.html.index;
import views.html.addTitle;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

public class Application extends Controller {

    public static Result index(){
        return ok(index.render());
    }
    public static Result addTitle(){
    	return ok(addTitle.render());
    }


    public static Result addMusic(){
    	return ok(addMusic.render());
    }
    public static Result addBook(){
    	return ok(addBook.render());
    }
    public static Result addHQ(){
    	return ok(addHQ.render());
    }
    public static Result addMovie(){
    	return ok(addMovie.render());
    }
    public static Result addSeries(){
    	return ok(addSeries.render());
    }
}
