package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.Client;

// Views
import views.html.index;
import views.html.add_title;
import views.html.add_music;
import views.html.add_book;
import views.html.add_comic;
import views.html.add_movie;
import views.html.add_series;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

public class Application extends Controller {

    public static Result index(){
        return ok(index.render());
    }

    //@Security.Authenticated(Secured.class)
    public static Result addTitle() {
    	return ok(add_title.render());
    }
}
