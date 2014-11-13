package controllers;

// Models
import models.Movie;
import models.MovieDAO;

// Views
import views.html.movie;

import java.util.List;

import play.*;
import play.mvc.*;

public class Viewer extends Controller {

    public static Result getMovie(String name) {
        MovieDAO dao = new MovieDAO();
        List<Movie> movies = dao.getByName(name.replace('-',' '));
        return ok(movie.render(movies.get(0).getName()));
    }

}
