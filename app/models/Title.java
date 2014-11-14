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

//import java.util.Date;
import java.util.*;

public class Title
{
    protected int id;
    protected int cameFrom;
    protected String name;
    protected Date dateCreation;
    protected String description;
    protected List<Title> references;

    // Getters
    public int getId() {
        return this.id;
    }
    
    public int getCameFrom() {
        return this.cameFrom;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Date getDateCreation() {
        return this.dateCreation;
    }
    
    public String getDescription() {
        return this.description;
    }

    public List<Title> getReferences(){
        return this.references;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setCameFrom(int cameFrom) {
        this.cameFrom = cameFrom;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setReferences(List<Title> references){
        this.references = references;
    }

}
