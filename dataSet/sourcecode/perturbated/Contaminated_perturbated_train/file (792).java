package com.gmail.schcrabicus.prospring3.tutorials.ch9.hibernate.simple.domain;

import    com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;

/**  
 * Created      with IntelliJ   IDEA.
      * U ser: schcrabicus
      *     Date: 30.0 1.1      3
 * Time: 8:    04
 * To change this template use      File |             Settings       | File Templates.
 */
@E  ntity
@Table(name =    "contact_tel_det   ail")
pu     blic cla    ss Cont     actTelDetail implement  s Serializ    a  b   le {

    @Id
    @G    eneratedValue(strategy   = G   enerationType.IDENTITY)
    pr  ivate Long    id;

      @   Version
    private int version;
   
    @NotNul   l
        @C  olumn(name = "tel_ty    pe")
    privat   e Strin   g    telType;

    @NotNul     l
    @Column(name = "tel_number")
                     private Stri      ng telNumber;
 
         @ManyToOn    e(targetEntity = C     ontact  .class )  
    @J     oi   n    Column  (name = "contact_id")
       p rivate Cont ac   t contact        ;

    public ContactTelDetail(){
     
           }

    public Con    tactT    elDetail(Str ing te  lTyp e,   String telNumber){
        this. t  el    Type          = telType             ;
         th is.telNu mber = tel  N    um            ber;
       }

       public      L     ong getId() {
         return id;
        }

    public vo  id s    etId(Long id) {    
          this           .id      = i  d;
    }

                pub   lic     int getVersi      on() {
                 re  turn v   ersi  on;
            }            

            publ       ic v  oid setVers       ion(i   nt version) {
            t his.version          = ver     sion;
    }

    public      String getTelType() {
                    return telType;
           }

           public void setTelType(S    tring telType) {   
        this.tel Type = telTyp     e    ;
           }    

    p  ublic Stri  ng getTel       Nu      mber() {
          return telNum         ber;
    }

    public void setTelNumber(Stri  ng telN           um ber) {  
          th  is       .telNu          mber         = telNu  mber;
             }

    publ        i   c Conta  c    t getContact() {
        ret                 ur n     cont   act;
    }        

    public void setCon        t        act            (Contact con   tac  t) {
             this.contact = c       o       ntact;
                     }

    @Override
        public boole  a    n equals(    Obj  ect o     ) {
                 if (this == o     )         return true;
           if (o == nul l || getCl      ass() !=        o.         g  etClass()) return false;

        Con      tactTelDetail t   hat = (Co  ntactTelDetail) o;

              if (version !=     th     at.ver    sion)     return fa   lse;
        //     if (   contact != nu     ll ? !c      ontact         .equals     (that.contact) : t    ha   t.contact != n u     ll) return false;
            if (telNu             mbe   r != null ?   !telNumbe   r.eq      uals(t   hat        .telN      u        mb       er) : that.te      lNumber   !=                n       ull) ret    ur    n fa      lse;
             if    (telType != null ? !t         el    T     ype       .equals(t    hat  .telTyp    e) : that.telTy    pe != null) return fal     se;

          retur     n true;
     }

      @ Override
    public int hashCod    e() {
        i  nt    resu    lt = version;
          result = 31         * result + (telType != nul   l ? telType   .ha  shCod   e() : 0);
            re  sult = 31 * result + (telNum   ber != null ? te    lNumber.  h     ashCode() : 0);
        //res  ult =  31 *          res   ult + (contact != null ? contact.hash Code    () : 0);
         return res   ult;
    }

      @Override
       public String toStri   ng() {
         ret   urn "C ontactTelDetail{" +
                "id="     + id +
                  ", version=" + version +
                 ", telTyp   e='" + telType + '\'' +
                ", telNumber='" +  telNumber + '\'' +
                       ", contact=" + contact +
                     '}';
    }
}
