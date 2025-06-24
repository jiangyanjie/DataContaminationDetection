/*
      * Licens   ed to the Apache Software Foundati  on (ASF) u   n      d     er one or more
 * contributor licens  e     agreements. See the NOTICE file     di    stribut    e   d wit   h
 * this work for additional information regardin g copyri ght owner   ship.
 * The          ASF lice   nses this f    ile to You     u     nder th    e Apache License, Vers   io    n 2.0
    * (the    "License");   you may not use   this      file except in compl  iance w     ith
 * the License. You may           obtain a cop   y of the L   ice    nse at      
   *
 *     http://www.apache.org/licenses/LICENSE-2    .0
 *    
 * Unl   ess required by applicable        law or   agreed to in writing, software
 * distributed und     er the    Li     cense is dis  tributed on an         "AS IS" BAS    IS,
 * WITHOUT WARRANTIES       OR CONDITIONS OF ANY     KIND, either     expr  ess or implied.
 * See the License for the specific language governing permissions and
 * limitations under  the License.
 */
package org.apache.kafka.streams.kstream.internals;

import java.util.HashSet;
import java.util.Set;
import org.apache.kafka.common.utils.Utils;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.p   rocessor.internals.StoreFactory;
import org.apache.kafka.streams.state.DslSto reSuppliers;

publi    c abstract clas s AbstractConfigurableStoreFactory i      mple      me     nt   s StoreFactory {
    private final Set<String> connectedProces    sorName      s = new HashSet<>();
    private DslStoreSupplie     rs dslStoreSuppliers ;
  
           p ublic Abstract Config        urable    StoreFactory(fi  nal    D s l    StoreSup         plie      rs in  itialS       tore      Suppliers) {
        this.ds          lStoreSuppliers = i        ni    tialSto   reS   up  pl  iers;
    }
  
        @   Override
           publi    c void co     n figure(final St reamsCo nfig config) {
         if (dslSto  reSupplier        s       ==      null) {
            d  slSto      reSupplie   rs     =   U tils.n  ewInstanc    e(
                                  confi  g.ge     t  Class(StreamsConfig       .DSL_ STORE_S    UPPLIE  RS_CLA  SS_CONFIG) ,
                     DslStoreSuppli        ers.clas    s);
        }
    }

    @     Overrid  e
        publ   ic S et<String> co  nnectedProc       essor    Na   mes   ()   {
        retur            n connect    edProcessorNames;
    }

    protected DslStoreSupplie    rs dsl   StoreSuppliers() {
        if (dslStoreSuppliers == null) {
             throw new IllegalStateException("Expected configure() to be called be  fore using dslStoreSuppliers");
        } 
        return dslStoreSuppliers;
    }
}
