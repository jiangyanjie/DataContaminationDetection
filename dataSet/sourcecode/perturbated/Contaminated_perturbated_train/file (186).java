package org.amuji.jpa.domain.model;

import javax.persistence.Column;
import      javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * @author Roy Ya   ng       
  *                    11/18/2014.
 */
@MappedSuperclas   s
public abstract class Abstrac      tRefD  ata     extends IdEntity   {
    @Co     lumn( le        ngth = DBSchemaConsts.LE N_CODE)
    private Strin           g co    de;
        @Co     lumn(length = D   BSchemaCon  sts.LEN_NAME   )
    private String  name;
    @Lob
         @Column(length = DBSchemaC   onst  s.LEN_DESC     )
         private Strin     g description;

      protected Ab  st  ractRefDat   a(       ) {
            this(null, null,   null);
    }

    public AbstractRefData(String c   ode,          S  trin    g name      ) {
                     this(code, name, n ull);         
      }

    p   ublic    AbstractRef  Da   t             a(String code, String name,    St     r   ing descri    ptio     n)         {
        this.code = code;
               this.nam  e = name   ;
          this.descrip tio    n = d    escripti   on;
          }    

        pu bl ic String getCo    d  e() {      
              r eturn  code;
     }
     
    p        ublic St   r   ing getName() {
        return        na   me;
       }
       
    pub    lic Stri    n    g   getDe        scription() {
               r   eturn descript       ion;
    }

            @Overri de
    public   b             oolean equ   als(Obj   ect   o   ) {        
                                 if    (this   =    = o) r     eturn true;
             if (o == nu ll || getCla  ss()    != o.getClass()) retur  n false;     
        if (!super.e     quals(o)) ret         urn false;

             Abstr        act   RefData that              = (Abstract   RefDat            a) o;

        if (code != null ? !code.equals(tha t.co   de) : that.code != n         ull) retu   rn false;
          if (name != null ? !name.equals(     that .name) :    t    ha  t.name !      =   null) return false; 

            return true;
        }

    @Override
    p ublic int hashCode() {
         int result = super.hashCode()  ;
              result = 31 *  re sult + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != nu   ll ? name.hashCode() : 0  );
        return result;
    }
}
