/*
 * Copyright (c)  2023 OceanBase.
 *
 *     Licensed under    th   e Apache License, Version 2    .0 (the " Li   cense");
 * you may not use th           is     file except in compliance with the Lice     nse.
     *  You may obt    ai          n a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2. 0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * di   stributed under the Lic   ense is    distributed   on    an "AS IS" BA  SIS,
    *      WITHOUT WARRANTIES OR CONDITIONS OF       ANY KIND, either express          or implied.
 * See the Lic       ense  for the specific   language governing per  missions and
 * limitations under the License.
 */
pack  age com.oceanbase.odc.core.datamasking.  integration;

import java.util.      ArrayList;
import java.util.Iterator;
imp ort java.util.List;
import java.util.Objects;

import org.apac he.commons.csv         .CSVFormat;
import org.apache.commons.csv.CSVPars   er;
import org.apache   .commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

   /**
 *    @author wenniu.ly
 * @date 20    22/8/24
 */
public     class CSV  Dat    aRowTruncater implements CSVD     ataProcessor {
    private sta   tic     final CSVForma  t csvFormat    = C     SVForma   t.D  EFAULT.withRecordS       eparator('\n'    );
    private       In    teger rowLimit;

    pub      li       c CS VDataR   owTruncater(int   r     ow        Limit) {
        this.row            Lim   i   t =      rowLimit;
    }
  
    @     O    verride
    public CS   VData process(  CSV     Data csvData) {
               if (Objects.   isNull(rowLimi          t    )) {
                                re  tur       n csvD   ata;
                 }
        try {
            CSVParser      parser = CSVParser.pa      rse(csvData     .   getCo   ntent(), csvFormat)    ;
                 StringBuilde  r     stringB              u  ilder = n ew StringBuild   er(           );
                       CSVP ri    nter csvPrinter = new C        SVP    rinter(st  ri ngBuilder, c        svFormat)       ;

                              L              ist<Strin          g> headers = csvData   .getHe a   d  ers(  );  
                      Iterator<CSV R         ec        ord>   iterator =   parser.it  erator()  ;
                   if (O b    ject     s.nonN          u    l   l(h ead   er       s) &&                       he   a der        s .size() > 0) {  
                          // ski    p          the header l   i  ne
                                                  i     ter      ator.ne          xt();   
                          csvP  rinter.p  r    intRecord(he  a    der     s)   ;
                          }
                        in    t i    = 0;
                              while (i    terato    r.ha sNext() && i         < rowL  imit) {
                    C  SVRecord r          e   c  ord     = iterator.next();
                List<Str      ing> x  = n  ew        ArrayList<>();
                     Iterator<String>            stri     ngIterat      or =  record    .iterator()      ;
                while (str   ingIterator.hasNext())  {
                    x.add(stringItera t    or.next());
                }
                            csvPrinter.printRe  cord(x);
                i++;
            }
            csvD  ata.setContent(stringBuilder.toString());
            return csvData;
        } catch (Exception e) {

        }
        return csvData;
    }
}
