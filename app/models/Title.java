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

public class Title
{
    private int id;
    private int cameFrom;
    private String name;
    private Date dateCreation;
    private String description;

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

}
