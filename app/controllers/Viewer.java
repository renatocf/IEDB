package controllers;

// Models
import models.Movie;
import models.MovieDAO;
import models.Title;

// Views
import views.html.movie;
import views.html.search_results;
import views.html.title;

import java.util.List;
import java.util.ArrayList;

import play.*;
import play.mvc.*;
import play.data.Form;

public class Viewer extends Controller {

    public static Result search() {
        Title title = Form.form(Title.class).bindFromRequest().get();
        
        List<Title> searchResults = new ArrayList<Title>();
        searchResults.addAll(findMovies(title.getName()));
        
        return ok(search_results.render(searchResults));
    }
    
    public static Result getMovie(String name) {
        return showTitle(findMovies(name).get(0));
    }
    
    public static Result showTitle(Movie m) {
        return ok(title.render(m));
    }
    
    private static List<Movie> findMovies(String name) {
        MovieDAO dao = new MovieDAO();
        return dao.getByName(name.replace('-',' '));
    }
}
