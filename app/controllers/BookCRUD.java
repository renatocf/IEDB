/**********************************************************************/
/* Copyright 2014 IEDB                                                */
/*                                                                    */
/* Licensed under the Apache License, Version 2.0 (the "License");    */
/* you may not use this file except in compliance with the License.   */
/* You may obtain a copy of the License at                            */
/*                                                                    */
/*     http://www.apache.org/licenses/LICENSE-2.0                     */
/*                                                                    */
/* Unless required by applicable law or agreed to in writing,         */
/* software distributed under the License is distributed on an        */
/* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,       */
/* either express or implied.                                         */
/* See the License for the specific language governing permissions    */
/* and limitations under the License.                                 */
/**********************************************************************/
package controllers;

// Model
import models.Book;
import models.BookDAO;
import models.Client;
import models.ClientDAO;

// Views
import views.html.index;
import views.html.title;
import views.html.add_book;

// Play
import play.data.Form;
import play.mvc.Result;
import play.twirl.api.Content;

public class BookCRUD extends CRUD<Book> {
    
    final private static BookCRUD self = new BookCRUD();
    final private static BookDAO dao = new BookDAO();
    
    public static BookCRUD getInstance() { return self; }
    
    protected Book find(String name) {
        return dao.getByName(name).get(0);
    }
    
    protected void storeNew(Form<Book> form) { 
        dao.add(form.get());
    }
    
    protected void storeChanged(Form<Book> form) { 
        dao.update(form.get());
    }
    
    protected Content renderUpdate(Form<Book> form) {
        return add_book.render(form, daoGenre.getAllWritten());
    }
    
    protected Content renderRead(Form<Book> form) {
        ClientDAO client = new ClientDAO();
        String username = client.getByEmail(session().get("email")).getUsername();
        return title.render(form.get(), username);
    }
    
    private BookCRUD() {
        super(Book.class);
    }
}
