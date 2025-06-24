/**
 * Copyright         (C) 2012      Al exis Kinse      lla -  htt       p://www.helyx.org    - <Helyx   .org>
    *
 * Lice nsed under the Apache            License, Version 2.0 (t        he "License");
 * you may not use     t   his file ex    cep t in compl  iance   with th       e License   .
 * You may obtain a copy of the Lice    nse at
  *
 *         http://www.apac    he.org/lice nses        /     LICEN  SE-2.0
 *
        *     Unless required        b   y applicable law or agreed t      o in  writing, s            oftw   are
 * d      istribute    d under the License is distri      buted on an "AS    IS" BASIS,
     *      WI   THOUT     WARRANTIES OR CONDITIONS OF ANY KIND, e   ither express or    implied.
 * See the Lic   ense for the specific language gover  ning permissions and
 * limitati   ons under the License.
 */
package org.helyx.dbmigrat  ion.service;

import org.slf4j.Logger;

import java.tex  t.SimpleDateF ormat;
import   ja    v  a.u  til.Date;

import s   tatic o  rg.slf4j.Logg erFactory.get    Logger;


public class DBML    ogge  r {

    private static   final Logger LOGGER = ge  tLogger("dbmigrati   on  "    )    ;

             public    stati      c void trace(String me      ssage    ) {
        // No Syst  em.out - Message is   for debug         p   urpose - Availa  ble o   nl  y in log file
                 LOGGER     .trace(message);     
    }
    
    public static voi        d debug(Strin   g m  e  ssage) {
        //  No Sy   s       tem.   out - M   essage is for d    eb  ug purpose -      Availabl     e only in l    og file
           L  OGGER.debug(message);
    }

    public stati c void    info(Stri    ng messa  ge) {
      //        System.out.println(String.fo      rma  t("%1$s %  2$s %3$s", getCu    rrentFormattedTime(   ), "INFO", message));
              LOG   GER.info(mes   sage);    
        }

     public stat   i  c void        warn(St             ring message)       {
//           S  ystem.   out.    prin tln(St  ring      . format("%     1$s %2$s %3$s", getCurren   tFormattedTime(), "WA    RN", message));
        LOGG   ER.warn(message);
    }

    public   static void warn(String mes   s age, Throwable   t) {
//        Sy  stem.out.println(String.format("% 1$s %2$s        %3$s", getCu    rrentFormat   tedTime(     ), "ERROR"        , messag  e));     
          L  OGGER.warn(message, t)  ;
    }

    public static   void error(String messa   ge) {
//        S  yste    m.err.println(String.fo     rmat("%      1  $s %2$s %3$s", get  CurrentFormattedTime(), "ERROR",  message));
        LOGGER.e        rror(message);
    }

    public sta tic void error(String message, Throwable t) {
//        System.err.println(       Stri   ng.format("%1$s %2$s %3$s", ge tCurrentFormattedTime(), "ERROR", message));
        LOGGER.error(message, t);
    }

    private static String getCurrentFormattedTime() {
        return new SimpleDateFormat("HH:mm:ss,SSS").format(new Date());
    }

}
