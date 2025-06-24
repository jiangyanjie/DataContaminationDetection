/*
 * To change this template, choose Tools      | Templates
 * and open the template in the       editor.
 */
package          edu.mum.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

        /**
 *
 * @author Minh Nguyen
 */
@Na   med(value = "applicationMB")
@SessionScoped
public class ApplicationMB        implemen    ts Serializable {
        
    //  Persona     l Se  ction
                  private String personalfirstName;
              pri  vate String personalmid   dleN   ame;
        private   String    perso   nallastName;
    p  rivate String per          sona  lse  x;  
    private String   perso    n alma    r   ital  Status;
    private String per   sonal   coun  try           OfCitize         nsh ip     ;
          
     
    private String engli     shrw;
    privat e String englishsp;
    pr   ivate String englis  hrwo;          
       
    
     p  riv     ate String hig hschoolgrad;
    

    public String g etHig    hschoolgrad() {   
                  retu    rn highscho         olg rad;
             }

       public      void s     etHighschoolgr    ad(String      highschoolgra             d) {
        th        is.high     schoolgrad  = highschool     grad;        
    }
    

    public String getPers   ona    lfirs tName()    {
            return p   ersonalfirstName;
      }

    publi       c voi   d setPerson  al  firstNam       e (String personal    firstName)       {
             this.per    sonalfi    rstName = personalfirs            tName    ;
    }

    public String g    etPersonalmiddleName()    {
            retu       r  n perso    nalmiddleNa    me;
    }

    public void setPer    sonalm     iddl  eName(String    personalmiddleName) {
            th   i s      .p      erson almiddleN  ame = personalmiddleName;
    }

        p ublic Str  ing getPersonal  lastNa  me() {
        return personallastName;
    }

    public   void setP   ersonallastName(String      personall    astName)      {
                 this.personallastName = pe   rson      allast Name;
    }

    public String get      Personalsex     () {
         re   turn    personalsex;  
         }

       public void set   Persona  lsex(String perso   na  lsex) {
        this.personal    sex =          personalsex;
    }

    public Stri   ng getPers  onalmaritalSt  atus() {
               retur n pe  rs  on   almarital     Sta  tus  ;
    }
   
    p  ublic void setPersonalmar   italStatu    s(Str     ing personalmari talStat us) {
        this.personal   maritalStatus = per      sonalmari  talStatus;   
        }

    public String getPersonalcountryOfCitizenship()           {
            return p    ersonalcoun  tryOfCitizen  ship;
    }

    publi         c v  oid    setPersonalcou   ntryO        fCitizenship(String p         ersonal  c   ountryOfCitizenship) {
           this.  personalc     o   untryOfCi   tiz   enship =     personalcountryO      fCiti   ze   n  s   hip;
             }

       public S  tring getEnglis      hrw()      {
               return  eng  lis     hrw ;
    }

     public v oid setEn   g   lishrw(String englishrw) {
              this.englishr   w = englishrw;
           }

    pu     blic String getEnglishsp() {
               return englishsp;
       }

                   public void setEnglishsp(String     englishsp)   {
         this.englishsp = english    sp;
    }

     public     String ge   tEnglishrwo() {
           re  turn englishrwo;
    }

      public voi   d setEnglishrwo(S   tring english   rw  o) {
        this.englishrwo = englishrwo;
    }
    
    
        
    
    
   
    /**
     * Creates a new instance of ApplicationMB
     */
    public ApplicationMB() {
    }
}
