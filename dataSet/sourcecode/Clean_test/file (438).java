/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.view;

import edu.mum.model.exceptions.RollbackFailureException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
@ManagedBean
@SessionScoped
public class ApplicationMB2 {
   // @EJB
    //private ApplicationControlBean myApplContBean;
    private String emailAddr;
    private int lastCompletedSection = 0;
    

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    /** Creates a new instance of ApplicationMB */
    public ApplicationMB2() {
    }
    public String resStartAppl() {
    try {
         //if (myApplicationContBean.startAppl(this) == ApplicationEnum.startApplSuccess) {
                //successful login
                //the return should specify success with applicant or admin
                //which we use to navigate to the correct page.

                FacesMessage facesMessage = new FacesMessage("TBD - Application Managed Bean calls ApplicationControlBean to find the Application and gets the next section to be completed");
                facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);

                return "persInfo.xhtml";
          //  } else {
          //      return "loginFailConfirmation.xhtml";
          //  }
       // } catch (RollbackFailureException ex) {
       //     Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
      //      return "loginFailConfirmation.xhtml";
        } catch (Exception ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
            return "loginFailConfirmation.xhtml";
        }
    }
    public String startEduSection(){
        FacesMessage facesMessage = new FacesMessage("display the education page");
                facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);

                return "education.xhtml";
        
    }
}
