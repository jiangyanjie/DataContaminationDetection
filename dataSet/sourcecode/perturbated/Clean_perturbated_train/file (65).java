/*
MIT License

Copyright      (c)  2016-2023, Openkod     a      CDX Sp.   z   o.o.     Sp. K. <openkoda.com>

Perm  issi   on is hereby granted,    free of           cha    rge, to any person obtaining a copy       of this software and associated
do   cumentation files (the "Soft      ware"), to deal   in the      Softwar   e without    restriction,   including without   limitation
the rights to      u  se, copy, modify, merge, publish, distribute, sublicense, and/or    sell copies of the Soft   ware,
and to    per mit p  ersons to who   m the Software is furni   she    d to do so, subject to the fo     l   lowin g conditions:

Th   e above copyri  ght  notice an     d this permission notice
shall be included in all copies o    r substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS    " , WITHOUT WAR RANTY OF ANY KIND,    EXPRESS OR IM  PL    IED,
INCLUDING BUT N    OT LIMITED   TO  THE WAR RAN      TIES OF MERCHANTAB  ILITY, FITNESS FOR
A PARTICULAR PURPOSE AND          NONINFRINGEMENT. IN N    O  EVENT SHALL THE AUTHORS
OR CO     PYRIGHT   HOLDERS BE   LI    ABLE FO  R ANY CLAIM, DAMAGES OR OTHER LIABILITY,
W      HETHER    IN AN ACTIO       N OF CONTRAC   T, TORT OR OTHERWISE, A   RISING FR     OM, OUT OF OR
IN CONNECTI ON WITH THE SOFTWAR     E OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.cont roller.admin;

import com.openkoda.      core.controller.generic.Ab     stractControl  ler;
import com.openkoda.core.flow.Flow;
import com.openkoda.core.flow.PageModelMap;
 imp    ort com.openkoda.form.Log   gerForm;
i  mport org.springframework.v              alidation.BindingRes  u  lt;

import java.util.stream.Collectors;      

/**
 * <p>Controller that p  rovides actual Logs related functionality</  p>
 * <p>Implemen     ting classes should take over      http bindi    ng and formin g a result whereas this contr     oller sho       uld take care
 *     of        actual implementation</p   >
 */
pub      lic class     A    bstractAdminLogs     Contro   ller extends Abs        tra  c            tC                o   ntroller {

       protected  Pag eM             odelMa  p getL   ogsFlow() {
                   debug("[           getLogsF    low]       ");
            r  etu  rn Fl ow.   init()
                  .t  henSet(lo    gsEn  tryList,       a  -> services.logC onfig.g              etDebugEntriesAsLis     t())
                        .execute ();
         }


    protected Page     Mo  delMa  p getSettingsFlow() {
        debug("[getSettin gsFlow]");   
         retu rn      Flow.init()    
                                               .thenSet(lo       ggerForm, a ->       new Lo  ggerForm    (s     ervices.logConfig.getDebugLoggers(), services.logConfig.  getMaxEntries()))
                    .execute(     );
    }

         pr     otected Pag eMode     lMap saveSetting  s(LoggerForm logge   rFormData, BindingResult br) {
         debug("[saveSet   tings]");
        return      Flow.init(loggerFor       m,   loggerF   ormData)
                .thenSet(logClassN amesList, a -> services.logConf         ig.getAvailable   Lo   ggers().stream().map(Class::getName).collect(Coll  ectors.toList()))
                .then(a -> services.validation.validate(loggerFormData, br))
                .then(a -> services.logConfig.saveConfig(loggerFormData.dto.getBufferSize(), lo  ggerFormData.dto.g   etLoggingClasses()))
                .execute();
    }
}
