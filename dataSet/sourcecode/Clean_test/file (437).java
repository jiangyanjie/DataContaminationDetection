/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Minh Nguyen
 */
@Named(value = "applicationMB")
@SessionScoped
public class ApplicationMB implements Serializable {

    // Personal Section
    private String personalfirstName;
    private String personalmiddleName;
    private String personallastName;
    private String personalsex;
    private String personalmaritalStatus;
    private String personalcountryOfCitizenship;
    
    
    private String englishrw;
    private String englishsp;
    private String englishrwo;
    
    
    private String highschoolgrad;
    

    public String getHighschoolgrad() {
        return highschoolgrad;
    }

    public void setHighschoolgrad(String highschoolgrad) {
        this.highschoolgrad = highschoolgrad;
    }
    

    public String getPersonalfirstName() {
        return personalfirstName;
    }

    public void setPersonalfirstName(String personalfirstName) {
        this.personalfirstName = personalfirstName;
    }

    public String getPersonalmiddleName() {
        return personalmiddleName;
    }

    public void setPersonalmiddleName(String personalmiddleName) {
        this.personalmiddleName = personalmiddleName;
    }

    public String getPersonallastName() {
        return personallastName;
    }

    public void setPersonallastName(String personallastName) {
        this.personallastName = personallastName;
    }

    public String getPersonalsex() {
        return personalsex;
    }

    public void setPersonalsex(String personalsex) {
        this.personalsex = personalsex;
    }

    public String getPersonalmaritalStatus() {
        return personalmaritalStatus;
    }

    public void setPersonalmaritalStatus(String personalmaritalStatus) {
        this.personalmaritalStatus = personalmaritalStatus;
    }

    public String getPersonalcountryOfCitizenship() {
        return personalcountryOfCitizenship;
    }

    public void setPersonalcountryOfCitizenship(String personalcountryOfCitizenship) {
        this.personalcountryOfCitizenship = personalcountryOfCitizenship;
    }

    public String getEnglishrw() {
        return englishrw;
    }

    public void setEnglishrw(String englishrw) {
        this.englishrw = englishrw;
    }

    public String getEnglishsp() {
        return englishsp;
    }

    public void setEnglishsp(String englishsp) {
        this.englishsp = englishsp;
    }

    public String getEnglishrwo() {
        return englishrwo;
    }

    public void setEnglishrwo(String englishrwo) {
        this.englishrwo = englishrwo;
    }
    
    
    
    
    
   
    /**
     * Creates a new instance of ApplicationMB
     */
    public ApplicationMB() {
    }
}
