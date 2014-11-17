package controllers;

// Models
import models.Movie;
import models.MovieDAO;
import models.Title;
import models.TitleDAO;

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
        TitleDAO dao = new TitleDAO();

        List<Title> searchResults = dao.getAllTitlesWithNameLike(title.getName());
        //searchResults.addAll(findMovies(title.getName()));
        
        return ok(search_results.render(searchResults));
    }
    
    public static Result getMovie(String name) {
        List<Movie> titles = findMovies(name);
        if(titles.size() == 0) return notFound(name + "not found");
        else return showTitle(titles.get(0));
    }
    
    public static Result showTitle(Movie m) {
        return ok(title.render(m));
    }

    public static Result showTitle(String type, String name){
        TitleDAO dao = new TitleDAO();
        return ok(title.render(dao.getByTypeAndName(type, name).get(0)));
    }
    
    private static List<Movie> findMovies(String name) {
        MovieDAO dao = new MovieDAO();
        return dao.getByName(name.replace('-',' '));
    }

    /*private static List<Titles> findAllByName(String name){
        List<Title> titles; = new ArrayList<Title>();
        MovieDAO daoMovie = new MovieDAO();
        MovieDAO daoSeries = new SeriesDAO();
        MovieDAO daoMusic = new MusicDAO();
        MovieDAO daoHq = new HqDAO();
        MovieDAO daoBook = new BookDAO();

        titles.addAll(daoMovie.getByName(name));
        titles.addAll(daoSeries.getByName(name));
        titles.addAll(daoMusic.getByName(name));
        titles.addAll(daoHq.getByName(name));
        titles.addAll(daoBook.getByName(name));

        return titles;
    }*/
}
