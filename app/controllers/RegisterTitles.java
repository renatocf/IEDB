package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.Client;
import models.Music;
import models.MusicDAO;
import models.Genre_auditiveDAO;
import models.Genre_auditive;

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

public class RegisterTitles extends Controller {

    static private Genre_auditiveDAO dao = new Genre_auditiveDAO();

    public static Result addMusic() {
        //return ok(add_music.render());

    	return ok(add_music.render(RegisterTitles.dao.getByGenre()));
    }
    
    public static Result addBook() {
    	return ok(add_book.render());
    }
    
    public static Result addComic() {
    	return ok(add_comic.render());
    }
    
    public static Result addMovie() {
    	return ok(add_movie.render());
    }
    
    public static Result addSeries() {
    	return ok(add_series.render());
    }

    public static Result addNewMusic(){
        Music music = Form.form(Music.class).bindFromRequest().get();
        MusicDAO dao = new MusicDAO();
        dao.add(music);
        return redirect(controllers.routes.Application.index());
    }
}