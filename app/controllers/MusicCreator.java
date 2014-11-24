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

// Views
import views.html.index;
import views.html.add_music;

// Play
import play.data.Form;
import play.mvc.Result;
import play.twirl.api.Content;
import play.data.validation.ValidationError;

// Java Util
import java.util.List;
import java.util.ArrayList;

public class MusicCreator extends Creator<MusicCreator.NewMusic> {
    
    final private static MusicCreator self = new MusicCreator();
    final private static MusicDAO dao = new MusicDAO();
    
    public static Result build() { return self.show(); }
    public static Result store() { return self.add();  }

    public static class NewMusic extends Music {
    	public List<ValidationError> validate() {
            List<ValidationError> errors = new ArrayList<>();
            if (dao.existsName(this.getName())) {
                errors.add(new ValidationError(
                    "name", "Title already exists!"));
            }
            return errors.isEmpty() ? null : errors;
        }
    }
    
    protected void store(Form<NewMusic> form) { 
        dao.add(form.get());
    }
    
    protected Content render(Form<NewMusic> form) {
        return add_music.render(form, daoGenre.getAllAuditive());
    }
    
    private MusicCreator() {
        super(NewMusic.class);
    }
}
