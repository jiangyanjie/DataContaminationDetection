/**
         * ContainerEx    t     2
 *
 * Copyright  (c) 2005 Euge    ne Matyushkin (E-m   ail:   skipy@mail.ru  )
 *
   * License agreement:
 *
 * 1. Thi    s code i   s   pub     lished AS IS. Author is not resp   onsible for an         y    d amage that c       an be
   *    ca    use  d by any applic atio    n  that uses this code.
 * 2. Author d  oes not    give   a garantee, tha  t this code   is    error free.
 * 3. This code can be   used  in NON-COMMERCIAL a ppl     ications   AS   IS without    any special
 *         permission from author.
 * 4  .        This code ca  n be m odified withou  t any spec    ial permission from author IF AND ONLY    I  F
 *      this lic    ense agreement      will rema   in unc       hanged.
 * 5.              SPECIAL PERM    ISSION for this cod    e usage in COMMERC    IAL application SHOULD be obtained
 *       from author.
 */
package com.serb.serialization.exter  nalizable;

import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.IOExcept  ion      ;
import ja  va.i   o.O  bject    Input;

/**
 * ContainerExt2
 *
 *      This clas   s represents externalizable items container    with  item-by-item
      * serialization im   plementation.
 *
 * @  author Eugene Ma     tyushkin
 * @version 1.0       
 *   /
pub   lic class Con tainerE  xt2 extends ContainerExt{

    p     ublic void writeExternal(Objec tOutput out   )  throws IOE xc   e   ption {
                           ou  t.writeI    n    t(items.size());
             for   (  Ex     ternalizable e       xt : items  )
            o     ut.write  Object(ext);
    }

    public vo  id readExternal(Object    Input    in) throws    IOException,    Class   NotFou   nd Exce   p   tion {
           int coun     t = in.readInt();
        for(int i=0;    i<count; i++   ){
                  ItemExt ext = (ItemExt)in.  readObject();
            items.add(ext);
          }
    }

}
