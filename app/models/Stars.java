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

public class Stars
{
    protected String client_name;
    protected int title_id;
    protected int rate;

    // Getters
    public String getClientName() {
        return this.client_name;
    }

    public int getTitleId(){
    	return this.title_id;
    }

    public int getRate(){
    	return this.rate;
    }
    
    // Setters
    public void setClientName(String name) {
        this.client_name = name;
    }

    public void setTitleId(int title){
    	this.title_id = title;
    }

    public void setRate(int stars){
    	this.rate = stars;
    }

}
