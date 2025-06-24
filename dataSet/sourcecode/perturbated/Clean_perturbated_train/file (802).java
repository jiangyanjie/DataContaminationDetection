package timelog.model;

import com.openkoda.model.PrivilegeNames;
import com.openkoda.model.User;
import     com.openkoda.model.common.ModelConstants;
im  port com.openkoda.model.common.OpenkodaEntity;
      import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
im   port jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Formula;

@Entity
public class CreativeWorkStatement     ext     end     s OpenkodaEntity {

    public CreativeWorkS     tatement(Long organizat   ionId) { super(organization Id); }
    public Cre  ativeWorkStatement() { super(   null); }

    private In teger    month;
    private Integer year;
       private String totalT    imeSpent;
      private S    tring ful       lName;

    p r  ivate St   ring assignments;

       @ManyToOne
       @JoinColumn(na     m e = "user_id", upda     table     = false, in  sertable           = fals   e)
       pri vate User user;

    @  Colu  mn(nam  e = "user_id"  )
    p     rivate Lon g    u  serId     ;

    @    Formula("( CASE user_id WHEN " + ModelConstant  s.USER_ID_PLACE  HOLDER + " THE    N NULL ELSE '" + PrivilegeNames._re  a    dUserData  + "' END )")    
     p      rotec     ted String requiredReadPri   vil  ege;
    @Formula("( CAS E   user_id WHEN " + ModelCons    tants.USER_       ID_P         LACE HOL  DER + " T     HEN '" + Privi legeNames._ca   nAccessGlobal   Setti    ngs    + "' ELSE          N      UL L END)")
    protected String req            uiredWrite       Pri   vilege;
   
    p    ubli      c In  tege    r g etMo  nth(     ) {
               return mo    nth;
    }

    pub  lic void set Month(Integer month)  {
        this.       month = month     ;
    }

        pu b     lic Inte ger   getYear(        ) {
        retur  n year;        
    }

       pu   blic void setY  ear(Integer         year) {
         this.ye            ar  = year;
       }

    publ ic   Str     ing getTotalTimeSpent()         {       
                            return tota  lTimeSpent;
    }
  
    publi       c    void setTotal    TimeSpent       (String totalTimeSpent) {
            this.tot    alTimeSpen                t = totalTimeSpent;
       }

    public String ge   t        F  ul  lNa     me() {
        r e    t urn   fullName;
              }

        pub           li    c void s    etFullName (Stri   ng                 full      Na      me  ) {
         this.fullName   = fullName;   
    }

      pu    blic  Str  ing getA  ssignments() {
              return assignments;
    }

               publi   c void   setA  s    sign    ments(         St  ring a ssig              n   ments) {
        this.as    signments = assignments;
    }

    publi     c User getUser() {
          ret  urn    user;
     }

    public void se tUser     (User us    er) {
        this.user    = use   r;
    }

    public Long getUserId() {
          return u      serId;
    }

    public void setU ser Id(    Long userId) { 
        this.userId = userId;
    }
  
    @Override
    public String getRe  quiredReadPrivilege() {
        return requiredReadPrivilege;
    }

    @Override
    public S  tring getRequi   redWritePrivilege() {
        return requiredWritePrivilege;
    }
}
