package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.Client;
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
import models.Genre_auditiveDAO;
import models.Genre_auditive;
import models.Genre_visualDAO;
import models.Genre_visual;
import models.Genre_writtenDAO;
import models.Genre_written;

// Views
import views.html.index;
import views.html.add_title;
import views.html.add_music;
import views.html.add_book;
import views.html.add_comic;
import views.html.add_movie;
import views.html.add_series;

import javax.swing.JOptionPane;

// Play
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;

public class RegisterTitles extends Controller {

    static private Genre_auditiveDAO daoAuditive = new Genre_auditiveDAO();
    static private Genre_visualDAO   daoVisual   = new Genre_visualDAO  ();
    static private Genre_writtenDAO  daoWritten  = new Genre_writtenDAO ();

    static private TitleDAO daoTitle = new TitleDAO();

    public static Result addMusic() {
    	return ok(add_music.render(RegisterTitles.daoAuditive.getByGenre()));
    }
    
    public static Result addBook() {
    	return ok(add_book.render(RegisterTitles.daoWritten.getByGenre()));
    }
    
    public static Result addComic() {
    	return ok(add_comic.render(RegisterTitles.daoWritten.getByGenre()));
    }
    
    public static Result addMovie() {
    	return ok(add_movie.render(RegisterTitles.daoVisual.getByGenre()));
    }
    
    public static Result addSeries() {
    	return ok(add_series.render(RegisterTitles.daoVisual.getByGenre()));
    }



    public static Result addNewMusic(){
        Music music = Form.form(Music.class).bindFromRequest().get();
        MusicDAO dao = new MusicDAO();
        if(!daoTitle.thisNameExists(music.getName(), "music")){
            dao.add(music);
            JOptionPane.showMessageDialog(null, "The music '" + music.getName() + "' was added succesfully!");
        }
        else{
            JOptionPane.showMessageDialog(null, "A music named '" + music.getName() + "' already exists!");
        }
        return redirect(controllers.routes.Application.index());
    }

    public static Result addNewBook(){
        Book book = Form.form(Book.class).bindFromRequest().get();
        BookDAO dao = new BookDAO();
        if(!daoTitle.thisNameExists(book.getName(), "book")){
            dao.add(book);
            JOptionPane.showMessageDialog(null, "The book '" + book.getName() + "' was added succesfully!");
        }
        else{
            JOptionPane.showMessageDialog(null, "A book named '" + book.getName() + "' already exists!");
        }
        return redirect(controllers.routes.Application.index());
    }

    public static Result addNewComic(){
        Comic comic = Form.form(Comic.class).bindFromRequest().get();
        ComicDAO dao = new ComicDAO();
        if(!daoTitle.thisNameExists(comic.getName(), "comic")){
            dao.add(comic);
            JOptionPane.showMessageDialog(null, "The comic '" + comic.getName() + "' was added succesfully!");
        }
        else{
            JOptionPane.showMessageDialog(null, "A comic named '" + comic.getName() + "' already exists!");
        }
        return redirect(controllers.routes.Application.index());
    }

    public static Result addNewMovie(){
        Movie movie = Form.form(Movie.class).bindFromRequest().get();
        MovieDAO dao = new MovieDAO();
        if(!daoTitle.thisNameExists(movie.getName(), "movie")){
            dao.add(movie);
            JOptionPane.showMessageDialog(null, "The movie '" + movie.getName() + "' was added succesfully!");
        }
        else{
            JOptionPane.showMessageDialog(null, "A movie named '" + movie.getName() + "' already exists!");
        }
        return redirect(controllers.routes.Application.index());
    }

    public static Result addNewSeries(){
        Series series = Form.form(Series.class).bindFromRequest().get();
        SeriesDAO dao = new SeriesDAO();
        if(!daoTitle.thisNameExists(series.getName(), "series")){
            dao.add(series);
            JOptionPane.showMessageDialog(null, "The series '" + series.getName() + "' was added succesfully!");
        }
        else{
            JOptionPane.showMessageDialog(null, "A series named '" + series.getName() + "' already exists!");
        }  
        return redirect(controllers.routes.Application.index());
    }
}