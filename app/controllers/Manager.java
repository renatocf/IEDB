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

abstract public class Manager extends Controller {
    
    public static Result create(String type) {
        return getCRUD(type).create();
    }
    
    public static Result read(String type, String name) {
        return getCRUD(type).read(name);
    }
    
    public static Result update(String type, String name) {
        return getCRUD(type).update(name);
    }
    
    public static Result add(String type) {
        return getCRUD(type).add();
    }
    
    public static Result amend(String type, String name) {
        return getCRUD(type).add();
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
}
