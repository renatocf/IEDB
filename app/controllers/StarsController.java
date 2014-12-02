package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.Stars;
import models.StarsDAO;

import javax.swing.JOptionPane;

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
        dao.add(stars);
        JOptionPane.showMessageDialog(null, stars.getRate());
        return redirect(controllers.routes.Application.index());
    }
}