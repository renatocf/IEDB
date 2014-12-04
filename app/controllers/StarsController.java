package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.Stars;
import models.StarsDAO;

// Views
import views.html.index;
import views.html.title;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

public class StarsController extends Controller {

    public static Result addNewStars(){
        Stars stars = Form.form(Stars.class).bindFromRequest().get();
        StarsDAO dao = new StarsDAO();
        System.out.println("AUEHUAEHUAHEUA");
        System.out.println(stars.getRate());
        stars.setClientEmail(session().get("email"));
        dao.add(stars);
        return redirect(controllers.routes.Application.index());
    }
}