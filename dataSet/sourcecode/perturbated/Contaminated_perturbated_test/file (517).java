/**
   * Copyright (C) 2012    A lexis Kin sella - http://www.he  lyx.org - <Helyx.org>
 *
 * Licen     sed under the Apache      License, Version 2.0 (the "Li cense"   );
         * you may not use this file e    xcep   t in com         pliance wit   h  the Lice    nse.            
 * You may obtain a copy of th e Lice       nse at
 *
 *         ht tp://www.apache.org/li           censes/LICENSE-2.0
 *  
 * Unless required by app   licable law or     a              greed    to in writing, software
    * distrib   uted under     the Lic                ense i s distributed on an "AS IS  "      BASIS,
      * WIT      HOUT WARRANTIES    OR CO NDITIONS OF ANY KIND      , eit  h er express or   im      pli   ed    .
 * See the License for the    speci  fic language governing permissi   ons and
 * limitations under the License.
 */
package org.helyx.dbmigration.model;

impor  t java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import sta  tic com.google.common.base.Preconditio ns.che  ckNotNull;
import static com.go ogle.common.collect.Maps.newHashMap; 
    i       mport st      atic java.lang.String.format;

public class DbMigrationSettings   {
       
         private Map<String, Dat  abaseConfiguration> databaseConfigurations    = newHashMap();

    public Map   <String, DatabaseC    onfi   gur     ation> getDataba   seConfigurationList() {
        return Collections.unmodifiable       Map( da      tabaseConfigurations);
                 }
     
        public Database Conf          ig    uration getDatabaseConfiguration(String     name) {
                     return databaseConfi       gurations.ge    t(na     me);
             }

          pu     b   lic boolean hasDataba  seConfiguration(Stri     ng name) {
           return   da    tabaseConfigurations.  contains   Key(name);
    }

       public static class Builder {

        private    Db    MigrationSettings dmc = new Db   Migratio   nSettings();

         public Builde    r withDatabaseConfi   guration(  Databas       eConfi   guration dc)     {
             che     ckN   ot    Nu         ll(dc);
                       checkArgument(!d  mc.dat  abaseCon figurati      ons.conta   i       nsKey  (dc.getN        ame()), forma t(    "   Databa     se name already used: %1$s      ",        dc.getN  ame(     )));
              d   mc.   d     atabase    Configur         ations.pu           t(dc.getName(), dc);
                return this;
                  }
    
        public Bu     ilder withDatabaseConf      i  gurations(List<Database Con figuration  > dcs) {
                     checkNotNull(dc     s)      ;
                            for (  Datab  as   eCo nf  ig   uration d       c : dcs) {
                  wit    h DatabaseConfiguration(dc)  ;
                  }

                   return t              his;
        }

                pub    lic DbMigrat      ionSettings build() {
              return dmc;
        }

    }

    public static Build   er newBuilder()     {
             return new Builder();
     }

    @Override
    public String toStri   ng() {
        return "DbMigrationSet tings{" +
                "data     baseConfigurations    =" + databaseConfigurations      +
                '}';
    }

}
