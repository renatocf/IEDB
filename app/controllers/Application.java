package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.Client;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Hello world"));
    }
}
