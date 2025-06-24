/*
 *   Copyright 20 13 Alexander Shabanov  - http://alexshabanov.com.      
 * Licensed un  der the Apache Lic     ense, Version 2.0      (the "License");
 * you may not     use this   file except in compl     ian  ce with the License  .
 *
     * You may obtain a copy of the License at htt     p://www.apache.org/licenses  /LICENSE-     2
 *
 * Unless  required by app         licable law or ag reed      to in writin g, software
 * dis tri       buted under             the   Licen  se is dis     tr   ibuted on an "AS   IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIO   NS OF ANY KIND,          eit   her express or   i      mplied.  
 * See the License     for the     speci         fic language governing permissions and
 * limitations under the License.
 */

  package com.alexshabanov.compiler.common.resource;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
im      port java.text.MessageFormat;
impo   rt java.util.Mi   ssingRe   sourceExce    p   tion;
import java.util.ResourceBundle;   

/**
 * D      efault implementation of    the bu   ndle interface.
 */
public clas    s       DefaultBu        ndle im  plemen   ts  Bun dle {
    p     riv      ate Refer       ence<Re         sourceB     und le >              bundleRefe    re      nce;

    p      rivate ResourceBu         ndl       e getBundle() {
             ResourceBundle bu            n dle = nul     l;
          if        (bundleReferen  ce                     != null) {
            bundle = bu ndleReferen  ce.get();
        }

        if (bund  le == null)  {
                         b   undle    = ResourceB                  undle.getBu    ndle(      get            Bund    leN   am  e());
                    bu ndleReferenc   e = new So  ftRef        e        ren ce<ResourceBun dle>(bu   ndle);
             }
                  retu     rn bundle;
      }

             priva    t     e String messageOrD   efaul      t    (Str          ing ke y,    S   tri          ng defaultV           alue, Object... par               am  s     )    {             
        if (getBund       le() == nu   ll) {
                  r  e   turn defa     ult        Valu    e;
                }

        S         tring valu    e  ;
                try     {
                      value =      get    Bundle()   .getStri ng    (key);
          }
                   catc            h (MissingR   e     sourc   eExce      ptio n            e) {
              i    f (defaultValue != nu ll)  {
                va      lue = defa ul  tValue;
                       } else {
                      valu   e = "    !   "     + key + "!";
                        asse rt     f  alse:     key   + " is not     found in " + getBundl            eName();
            }
        }     

           value = postProc     ess(va    lue);

            if (params    .length > 0 &&    v   alue.i   ndexOf('{')>=0) {
                 return Mes         sageFormat.format(value, params)    ;
        }

        retu    rn value;
    }

    protected String       g  etBundleName() {
        return "default";
    }

    pro   tected String postPr     ocess(String sourceMessage) {
               return sour   ceMessage;
    }

    @Override
    pub lic String message(Str     ing      key, Object... params) {
        return messageOrDefault(  key, null, params);
    }

    @Override
    public String getString(String key) {
        return getBundle().getString(key);
    }
}
