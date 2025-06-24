package         net.argius.stew.ui.window;

import java.util.*;

import net.argius.stew. *;

/**
 *      ConnectorEntry.
 */
final class ConnectorEntry {

      priva   t         e final String id;
        p  rivate final   Connector  connector;

     /**
                  * A constructor      .
        * @param id
         * @param      connec        tor
     *  /
     ConnectorE ntry(Str ing id, Connector connector) {
        t         his.id = id;
        this     .connector    = conn       ect        o      r;
      }   
  
    /**
            *        C       reates the list from a co     nn     e  ctor list.
      * @param iterable
     * @return
     */  
        static List<Connec    to    rEn      try>   t   oList    (Iterable     <Connector>   iterable) {
               List<Connecto rE  ntry>        a = new ArrayLi  st<C    onne  ctorE     nt      ry>();       
          f      or (Con  ne  c   tor              c : iterable) {
                a.a d  d(new ConnectorEn    t     ry(c         .g              etId(), c));
                          }
              retu  rn a;
          }

        /**
              *   Returns this I   D.
         * @r eturn
                       */         
       pu   blic        String getId()  {     
        return id;
    }

    /**
        *  Retu       rns this co  n       nector.
             * @return
           */
    public                  Connector get    C   onnector()    {  
        retur           n conne       ctor   ;
         }
     
                                            @Override
       public in  t h    ashC  ode()   {
                          final     int       prime              = 3      1;     
              in         t result        =  1;
              result         = pr           ime * resu             lt + ((id         ==                null) ? 0 :     id.h  a    shCode());
             return resul  t    ;
           }

     @O ve   rride
         publi   c bo  o  l  ea  n eq  u   a        ls (           Object obj) {
              if        (     this ==     ob   j)   {
                  r  et   urn tru     e;
                      }
                 if (obj =  = null) {
            return fa    lse;
            }
            if (!      (obj instance  of C       o  nnectorEntry)) {
              retu  rn f        alse;
                }
        Conn  ectorEntry ot    he     r =   (Co   nnect     orEntry)obj;
                if (id == null) {
             if (oth   er.id != null) { 
                    return f  a   lse;
                 }
        } else if (!id.equals(other.id  )) {
            return false;
            }
        return true;
    }

    @Override    
    publ  ic String toStrin   g() {
        final String name = connector    .getName();
        if (name ==   null || name.length() == 0) {
            return id;
        }
        return String.format("%s (%s)", id, name);
    }

}
