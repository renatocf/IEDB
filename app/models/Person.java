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
package models;

import java.util.Date;

public class Person
{
    private String name;
    private String nationality;
    private Date birthday;
    private boolean isAuthor;
    private boolean isActor;
    private boolean isDirector;
    private boolean isMusician;

    // Getters
    public String getName() {
        return this.name;
    }
    
    public String getNationality() {
        return this.nationality;
    }
    
    public Date getBirthday() {
        return this.birthday;
    }

    public boolean isAuthor(){
        return this.isAuthor;
    }

    public boolean isActor(){
        return this.isActor;
    }

    public boolean isDirector(){
        return this.isDirector;
    }

    public boolean isMusician(){
        return this.isMusician;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setIsAuthor(boolean isAuthor){
        this.isAuthor = isAuthor;
    }

    public void setIsActor(boolean isActor){
        this.isActor = isActor;
    }

    public void setIsDirector(boolean isDirector){
        this.isDirector = isDirector;
    }

    public void setIsMusician(boolean isMusician){
        this.isMusician = isMusician;
    }
}
