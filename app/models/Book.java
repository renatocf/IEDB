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

// Play
import play.data.validation.ValidationError;

// Java Util
import java.util.List;
import java.util.ArrayList;

public class Book extends Written {

    protected int numEditions;

    // Getters
    public int getNumEditions() {
        return this.numEditions;
    }
    
    // Setters
    public void setNumEditions(int numEditions) {
        this.numEditions = numEditions;
    }

    // Validation
    public List<ValidationError> validate() {
        BookDAO dao = new BookDAO();
        List<ValidationError> errors = new ArrayList<>();
        if (dao.existsName(this.getName())) {
            errors.add(new ValidationError(
                "name", "Music already exists!"));
        }
        return errors.isEmpty() ? null : errors;
    }
}
