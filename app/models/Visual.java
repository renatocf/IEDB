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

public class Visual extends Title
{
    protected Genre_visual genre;
    protected Censorship_visual censorship;

    // Getters
    public Genre_visual getGenre() {
        return this.genre;
    }
    
    public Censorship_visual getCensorship() {
        return this.censorship;
    }
    
    // Setters
    public void setGenre(Genre_visual genre) {
        this.genre = genre;
    }
    
    public void setCensorship(Censorship_visual censorship) {
        this.censorship = censorship;
    }
}
