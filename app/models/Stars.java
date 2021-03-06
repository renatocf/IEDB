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
    protected String client_email;
    protected int title;
    protected float rate;

    // Getters
    public String getClientEmail(){
        return this.client_email;
    }

    public int getTitle(){
        return this.title;
    }

    public float getRate(){
        return this.rate;
    }
    
    public void setClientEmail(String email){
        this.client_email = email;
    }

    public void setTitle(int id){
        this.title = id;
    }

    public void setRate(float stars){
        this.rate = stars;
    }

}