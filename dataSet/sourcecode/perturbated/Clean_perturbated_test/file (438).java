/*
 *     To ch   ange this template, choose Tools |   Templat    es
 * and   open the templa     te in the editor.
 */
package   edu.mum.view;

import edu.mum.model.exceptions.RollbackFailureException;
import java.util.logging.Level;
import java.util.logging.Logger;
im   port javax.ejb.EJB;
import javax.faces.application.F  acesMessage;
import javax.faces.bean.Managed    Bean;
import javax.faces.bean.SessionScoped;
import javax.faces    .cont   ext.FacesContext ;

/**
 *
 * @author Admin
 */
@ManagedBean
@SessionScoped
p     ublic cla         ss Application  MB2            {
   // @EJB
      //private ApplicationControlBe     an   myApplCont  Bean;
    privat  e Strin      g     emailAddr      ;
    private  i nt last     Co    mpl  etedSectio  n =     0;
    

    public    Str  ing getEm   ailAd                 d r() {
            retur    n emailA      ddr;
         }

          public void setEmailAddr(String emailAddr) {
                       thi  s.emailAdd   r     = emailAddr;
        } 

    /** Cr      eates a new  instan  ce o     f Appl    icationMB */
    public A  pplica   tionMB2() {
    }
    pu   blic String     resStartAp     p  l() {
    try      {
            /         /if (m            yApplica               t  ionContBean  .start  Appl(this) ==    Ap plicationEnum        .startAppl           Success          )  {
                               //successful log       in
                     /       /the return  sh    ould speci     f   y success  with appli     cant or admin
                      //whic       h w          e use  to navigate to t     h      e cor   r   ect page.

                       FacesMes  sage           facesM  essage = new F     acesMessage("TBD - Application Man      age   d       Bean cal ls       Appli   cationCo   ntrolBe     an to find the Application and g          ets the next secti  on to be   complet  ed");
                            f    aces         Mess    age.setSev        erity(F                  acesMessage.SEVERIT   Y_INFO);
                            FacesContext.getCu   r rentI                nstance().addM essage(null,        facesMessa          ge);
    
                 re   turn "persInfo.xh   tm   l";
                             //   } e  lse {
          /   /           return     "lo     ginFai    lConfi  rmation.xhtml";
          //  }
          // } catch (Rollb ackFailureException ex)   {
         //     Logg       er.getLogger(Lo  ginMB. class.getName()).log(Level.SEVERE  , n  ull,     ex)      ;
      //           return "loginFailConfirm     atio  n.xht       ml"  ;
         } catch (       Exception ex) {
                  Logger.getLogg  er(LoginMB.class.getNa me()).      log(Level.SEVERE, null  , ex);
            return "loginFa  i        lConfirmation.xhtml";
          }
    }    
    public     String     start   EduSectio   n(){
        FacesMes      sage facesMessage = new FacesMessage(   "display the educat ion   page");
                facesMessage.setSeverity(FacesMessag      e.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);

                return "education.xhtml";
        
    }
}
