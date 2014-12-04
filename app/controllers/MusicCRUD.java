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
import models.Music;
import models.MusicDAO;
import models.Client;
import models.ClientDAO;

// Views
import views.html.index;
import views.html.title;
import views.html.add_music;

// Play
import play.data.Form;
import play.mvc.Result;
import play.twirl.api.Content;

public class MusicCRUD extends CRUD<Music> {
    
    final private static MusicCRUD self = new MusicCRUD();
    final private static MusicDAO dao = new MusicDAO();
    
    public static MusicCRUD getInstance() { return self; }
    
    protected Music find(String name) {
        return dao.getByName(name).get(0);
    }
    
    protected void storeNew(Form<Music> form) { 
        dao.add(form.get());
    }
    
    protected void storeChanged(Form<Music> form) { 
        dao.update(form.get());
    }
    
    protected Content renderUpdate(Form<Music> form) {
        return add_music.render(form, daoGenre.getAllAuditive());
    }
    
    protected Content renderRead(Form<Music> form) {
        return title.render(form.get());
    }
    
    private MusicCRUD() {
        super(Music.class);
    }
}
