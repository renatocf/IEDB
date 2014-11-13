package controllers;

// Models
import models.Movie;
import models.MovieDAO;
import models.Title;

// Views
import views.html.movie;

import java.util.List;

import play.*;
import play.mvc.*;
import play.data.Form;

public class Viewer extends Controller {

    public static Result find() {
        Title title = Form.form(Title.class).bindFromRequest().get();
        return redirect(routes.Viewer.getMovie(title.getName()));
    }
    
    public static Result getMovie(String name) {
        MovieDAO dao = new MovieDAO();
        List<Movie> movies = dao.getByName(name.replace('-',' '));
        return ok(movie.render(movies.get(0).getName()));
    }

}
