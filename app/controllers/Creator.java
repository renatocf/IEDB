package controllers;

// Model
import models.TitleDAO;
import models.GenreDAO;
import models.CensorshipDAO;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;
import play.twirl.api.Content;

abstract public class Creator<T> extends Controller {

    protected TitleDAO      daoTitle      = new TitleDAO();
    protected GenreDAO      daoGenre      = new GenreDAO();
    protected CensorshipDAO daoCensorship = new CensorshipDAO();
    
    protected Class<T> clazz;
    
    protected Creator(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    public Result show() {
    	return ok(this.render(Form.form(clazz)));
    }

    public Result add() {
        Form form = Form.form(clazz).bindFromRequest();
        
    	if (form.hasErrors())
    	 	return badRequest(this.render(form));
            
        this.store(form);
        return redirect(routes.Application.index());
    }
    
    abstract protected void store(Form<T> form);
    abstract protected Content render(play.data.Form<T> form);
}
