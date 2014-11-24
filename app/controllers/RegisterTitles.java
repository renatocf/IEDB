package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

// Model
import models.TitleDAO;
import models.Title;
import models.Music;
import models.MusicDAO;
import models.Book;
import models.BookDAO;
import models.Comic;
import models.ComicDAO;
import models.Movie;
import models.MovieDAO;
import models.Series;
import models.SeriesDAO;
import models.GenreDAO;
import models.Censorship;
import models.CensorshipDAO;

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

    static private TitleDAO      daoTitle      = new TitleDAO();
    static private GenreDAO      daoGenre      = new GenreDAO();
    static private CensorshipDAO daoCensorship = new CensorshipDAO();

    // public static Result addMusic() {
    // 	return ok(add_music.render(daoGenre.getAllAuditive()));
    // }

    public static Result addBook() {
    	return ok(add_book.render(daoGenre.getAllWritten()));
    }

    public static Result addComic() {
    	return ok(add_comic.render(daoGenre.getAllWritten()));
    }

    public static Result addMovie() {
    	return ok(add_movie.render(
            daoGenre.getAllVisual(), daoCensorship.getAllVisual())
        );
    }

    public static Result addSeries() {
    	return ok(add_series.render(
            daoGenre.getAllVisual(), daoCensorship.getAllVisual())
        );
    }

    // public static Result addNewMusic() {
    //     Music music = Form.form(Music.class).bindFromRequest().get();

    //     MusicDAO dao = new MusicDAO();
    //     if (!daoTitle.thisNameExists(music.getName(), "music"))
    //         dao.add(music);
    //     return redirect(controllers.routes.Application.index());
    // }

    public static Result addNewBook() {
        Book book = Form.form(Book.class).bindFromRequest().get();
        
        BookDAO dao = new BookDAO();
        if (!daoTitle.thisNameExists(book.getName(), "book"))
            dao.add(book);
        return redirect(controllers.routes.Application.index());
    }

    public static Result addNewComic() {
        Comic comic = Form.form(Comic.class).bindFromRequest().get();
        ComicDAO dao = new ComicDAO();
        if (!daoTitle.thisNameExists(comic.getName(), "comic"))
            dao.add(comic);
        return redirect(controllers.routes.Application.index());
    }

    public static Result addNewMovie() {
        Movie movie = Form.form(Movie.class).bindFromRequest().get();
        MovieDAO dao = new MovieDAO();
        if (!daoTitle.thisNameExists(movie.getName(), "movie"))
            dao.add(movie);
        return redirect(controllers.routes.Application.index());
    }
    
    public static Result addNewSeries() {
        Series series = Form.form(Series.class).bindFromRequest().get();
        SeriesDAO dao = new SeriesDAO();
        if (!daoTitle.thisNameExists(series.getName(), "series"))
            dao.add(series);
        return redirect(controllers.routes.Application.index());
    }
}
