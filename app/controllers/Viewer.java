package controllers;

// Models
import models.Movie;
import models.MovieDAO;
import models.Title;

// Views
import views.html.movie;
import views.html.search_results;

import java.util.List;
import java.util.ArrayList;

import play.*;
import play.mvc.*;
import play.data.Form;

public class Viewer extends Controller {

    public static Result find() {
        Title title = Form.form(Title.class).bindFromRequest().get();
        
        List<Title> searchResults = new ArrayList<Title>();
        searchResults.addAll(findMovies(title.getName()));
        
        return ok(search_results.render(searchResults));
    }
    
    private static List<Movie> findMovies(String name) {
        MovieDAO dao = new MovieDAO();
        return dao.getByName(name.replace('-',' '));
    }
    
    public static Result showMovie(String name) {
        MovieDAO dao = new MovieDAO();
        List<Movie> movies = dao.getByName(name.replace('-',' '));
        return ok(movie.render(movies.get(0).getName()));
    }

}
