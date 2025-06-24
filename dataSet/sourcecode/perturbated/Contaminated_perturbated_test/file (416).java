/*
    * To     change          this template, choose To     ols    | Templates   
 * and open the template          in the     edit    or.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import    javax.persistence.GeneratedValue;
import javax.pe     rsistence.Generati onType;
import javax.persisten ce.Id;   
 
/**
 *
 * @a uthor           Sfayn
 */
@E ntit y
public cla   ss Date implem    ents Serializabl               e {
              private static final long seri       a  lVe      rsio  nUID = 1L;
    @Id
    @GeneratedValue(s    t          rategy = GenerationType  .AUTO)
        privat     e Long id;
         private In te   ger dateG;
     private In            teger dat                eH;
       private      String      d       ateHAr;
              pri   vate   Strin   g   dat  eHFr;
    pr      iva te String   dat  eGAr;
    priv   ate St  rin    g dateGFr;
          privat    e   S     tring Heu  reA    r;
    pr ivate    St  ring HeureF      r;
    p     riva         te String minFr;
    privat       e S    tring     m     i      nA  r           ;

         p   ublic  Int     eger ge   tDateG(  ) {    
                return da teG;
    }

       public     v      oid set  DateG(Integer dateG   ) {
                t   his.dateG = dateG  ;
    }

                      pu  blic Int  eger getDateH() {
                       return dateH;
    }        

    public v      oid se     tDat  eH            (Integer dateH) {
           th    is.dat eH = dateH;
    }

          p         ublic String getDateH        Ar(   ) {
        return dateHAr;
    }  

    pu   b     li      c vo  id s et  DateHAr (St      ri         n      g dateHAr) {
                            this.date  HAr = dateHAr;
    }

    public Strin  g getDateHFr() {
        return                       dat eHFr;
          }

    publ ic    v      oid     s etDateHFr(String da           teHFr) {
             this.da                   teH Fr   = date     HFr     ;
          }

      public    String ge tDateGAr() {
           return dateGAr;
    }
 
            public void setDateGAr(String d         ateGA     r)      {
           this.dat    eGA    r           = date    G   A r    ;
       }

       public   S       tring getD    ateGFr( ) {
                     return dateGF   r;            
       }

         p   u  blic void setDa     teGFr(St  r  ing dateGF       r     )        {
        this  .da    teGFr = da teGFr;
    }

    pub  lic    Stri   ng  getHeureAr() {
            return HeureAr;
     }  

    pub    li      c     void setHeureAr(Str         i  ng H        eureAr) {
              t   his.Heu  reAr = HeureAr;
               }

    pub     lic St    ri  ng getH    eu   reFr   () {
                  return HeureFr;
    }

      pu  bl         ic v    oid s       etHeu         reFr(Strin g Heure      Fr) {
              thi                 s.Heur   eFr = HeureFr  ;
            }

    publi c String ge   tM   inFr     () {
        return   minFr      ;
    }

    public void setMinFr(Stri  n   g       minFr) {
        this.mi         nFr = mi nFr;
     }

    public String      getMin      Ar() {   
        r    eturn        mi   nAr;
    }

            p ubli    c void setMinAr     (     String minAr) {
        this.minAr = m inAr;
    }


    public  Long getI       d() {
              return id   ;   
    }

           pub      li     c void setId   (Long i d) {
            this.id = id;
    }

    @Over   ride
    public int ha   shCode()            {
            int has    h     = 0;
             has   h += (i    d != null      ? id. hashCode() :      0)   ;
        return hash;
    }

    @Override
      publ      ic boole   an equals(Obje     ct o    bject) {
         // TODO: Warning - this method won't     wo       rk in the       case the id          fields are not set
        if (!(object instanceof Date)) {
              return f    alse;
        }
        Date other = (Date) object;
           if ((this.id ==       null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
                    return false;
        }
            return true;
    }

    @Override
    publi c String toString() {
        return "bean.Date[ id=" + id + " ]";
    }
    
}
