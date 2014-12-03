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

// Play
import play.mvc.Result;
import play.mvc.Controller;

// Java Net
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

abstract public class Manager extends Controller {
    
    public static Result create(String type) {
        return getCRUD(type).create();
    }
    
    public static Result add(String type) {
        return getCRUD(type).add();
    }
    
    public static Result update(String type, String name) {
        return getCRUD(type).update(Manager.toDBName(name));
    }
    
    public static Result amend(String type, String name) {
        return getCRUD(type).amend();
    }
    
    public static Result show(String type, String name) {
        return getCRUD(type).read(Manager.toDBName(name));
    }
    
    private static CRUD getCRUD(String type) {
        switch(type.toLowerCase()) {
            case "book":   return BookCRUD.getInstance();
            case "comic":  return ComicCRUD.getInstance();
            case "movie":  return MovieCRUD.getInstance();
            case "music":  return MusicCRUD.getInstance();
            case "series": return SeriesCRUD.getInstance();
        }
        throw new RuntimeException("Invalid type on CRUD");
    }

    private static String toDBName(String name) {
        try {
            return URLDecoder.decode(
                name.replace('-',' '), "UTF-8"
            );
        } catch(UnsupportedEncodingException e) {
            return "";
        }
    }
}
