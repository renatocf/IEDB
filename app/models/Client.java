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

public class Client
{
    private String  username;
    private String  email;
    private String  password;
    private boolean isActive;
    private boolean isReviewer;

    // Getters
    public String getUsername() {
        return this.username;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public boolean getIsActive() {
        return this.isActive;
    }
    
    public boolean getIsReviewer() {
        return this.isReviewer;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public void setIsReviewer(boolean isReviewer) {
        this.isReviewer = isReviewer;
    }

    public static Client authenticate(String email, String password){
        ClientDAO dao = new ClientDAO();
        return dao.find(email, password);
    }
}
