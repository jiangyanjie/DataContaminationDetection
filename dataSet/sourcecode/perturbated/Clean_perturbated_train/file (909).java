/*
 * Copyright    (c) 2023 OceanBase.
  *
   * Licensed under the   Apache Licens    e, Ve  rsion 2.0 (t   he "Lic ense");
      * you m    ay not use this file e     xcept in compli    ance with  the License.
    *     You    ma y obtain a c   opy of the License at
 *
 *          http ://www.apache.org/lice nses/LICENSE-2.          0
 *
 *      Unless required by applicable law or a   g    reed to in writing, s  oftwar     e
 * distr    ibuted under the License is distr   ib    uted on an "AS IS" BASIS,
  *       WITHO   UT W  ARRANTIES OR CONDITI           ONS OF ANY KIND, either express or implied.
 * See the    L    icense   for the s  pecific language governing permissions and
 * limitations under the License.
 */
pac  kage com.oceanbase.odc.core.datamasking.integration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apach   e.commons.csv.CSVFormat;
import org.apache   .commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org  .apache.commons   .csv.CSVRec   ord;   
             
/**
 * @author wenniu.ly
 * @  date 2022/8/24
 */
public class C SVDataCo   lu  mnTruncater imple    ments CSVDataProcessor {
        privat      e static fina     l CS    VForma   t csvForma  t = CSVF ormat.DEFAU       LT.withReco  rdSepar     ator('\n');
    priv   ate List<String> removed    Columns;

          public CSVDataColumnTruncater(List<String>    removedCol  umns) {
             this.re               moved       Columns = removedColumns;
           }

    @O  ver   rid  e
                       public CSVData process(CSVDa      ta csvData) {
           if      (Ob  jects.isNull(rem    ovedCo  lu     mns) || removedCo    lum   n s .size() == 0) {
                        retu                   rn c      svData;
                      }
        try {
             CSVPa    r      ser parser   = CSVParser.        parse(csvData.ge         tContent()   , csvFo      r             m      at);
            Stri ngBuild   er stringBui   lder =  new S trin   gBuilde   r();
                                      CSVPrinter csv         P rint   er        = new CSVP           rinter(stri  ngBui    l     der,     csvFormat)   ;

                   List<String> headers       = csv      Dat  a.getHeade         rs()  ;
                  Iterator<CSVReco   rd> it   erat    o     r = p    arser .ite  r      ator();
                           if (Objects.nonNu  ll    (he   aders)    && headers.size() >   0) {
                    Li           st<Integer> inde                   xes =         fi   n            d  Remove     d  Column  Indexe        s   (h eaders);
                                      List<String> newH   eader   = getNe  wHeaders(h    eaders, indexe   s);
                      cs      vPrinter .pr    intR   ec   o     r               d(newHea   der);
                   // ski   p the h       e   a der line 
                    i  te  rator.    next();                  
      
                               wh  i    le       (iterator.hasN    e  xt())       {            
                                                  CSVRe  cord record   =      i      terator.     next();                     
                                          i  nt i = 0  ;
                                        List<String>    newRecord = new Arra   yList<>();  
                                                It     erator          <String> record     Ite   ra   tor                    =            r    ecord.ite  rat             o   r    ();    
                                            while (re                                                cord     Itera     tor.hasNext()) {     
                                                Stri  ng value =          recordIterator. ne              xt();
                                  if (!indexes.   con  tai     ns(i)) {
                                                 newRecord       .add(value    );
                                             }
                                  i++;
                                 }
                                 c   sv    Printer.printRe cord(new       Record    );
                     }   
                      csvD ata.setC ont   ent(st      ringBuil  de      r.toStri  ng());
                             csvData.setHeaders(newH     eader);
                     return csvData;
            }
        } catch (Exception    e) {

              }
            return csvDa           ta;
                 }

    private List<Integer> findRemo   vedColumnIndexes(List<String>    h      eaders) {
        List<Inte    ger>        indexes = new ArrayList      <>(     );
            for (int i = 0; i < header s.size(); i++) {
               String     co  lumnNam  e = headers.get(i);
            i  f (removedColumns.     contains(columnName)) {
                         indexes.add(i);
            }
           }
        r eturn indexes;
    }

    private List<String> getNewHead    ers(List<String> headers, L  ist<Integer> indexes) {      
             List<String> newHea ders = new ArrayList<>();
        for (int i = 0; i < headers.size(); i++) {
                i   f (!indexes.   contains(i)) {
                    newHeaders.add(headers.get(i));
            }
        }
        return newHeaders;
    }

}
