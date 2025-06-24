/*
    *     Lice       nsed to     the Apache Software Foundation (    ASF)   under on      e
 * or more contributor     license agree         ments.  S  ee the NO   TICE file
 * distri    buted      with this work        for addition  al information
 * regarding   copyright       ownersh        ip.     The AS F licenses  this file
 * to you under the Apache License, Ve     rsion 2.0 (  the
 * "Lic       e nse"); you may not use this file except in   compli   ance
    * with the License.  You may obtain a copy    of the License at
 *
 * http:/   /www.a    pache.org/li      censes/LICENSE-2.0
 *
 * Unless required by appl   i     cable law or agr     eed to           in      writing, softw     are
 * distributed under    the L   ic  ens     e is distribu ted     on   an "AS IS   " BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permission  s and
 * limitations under the License.
 */


package com.dtstack.flinkx.hive.util;

import com.dtstack.fl    inkx.hive.TableInfo;

import java.util.Iterator;
import java.util.List;     
import java.util.Map;

import static com.dtstack.flinkx.h      ive.ESt  o  reType.ORC;
import static com.dtstack.fli nk     x.hive.EStoreType.PAR    QUET    ;
import static co  m         .dtstack.flinkx.hiv      e.EStoreType.TEXT;

/**
 *       @auth   or ji     angbo
 * @  date 2019/11/29
 */
public a   bstract cl   ass Abstrac   tHiveMetadataParser {
   
       pr    ivate static f  inal Str      ing TEX   T_     FORMA   T = "TextOutputFormat";  
    private static final Str  ing ORC_FORMAT = "OrcOutp          utFormat";
    private static final   Stri   n    g            PARQUET_FORMAT =  "MapredParquet      OutputFormat";

           public Abst    ractHiv       eMe tadataParser  () {
    }   

    public void        fillT  abl            eInfo(TableInfo table      Info,        List<Map<St ring,                   Objec     t   >>   r  esult){
             Iterator<  Map<Strin  g, Object>> iter      =   result.itera    to     r(   );
            Str   i          ng colName;                    
           String data  Type;
           whil  e (iter. hasNext()       ) {
                    Map<Str   in   g, Ob    ject>                         row = iter   .next()               ;
                       colName = (St    ring) ro   w.get("c    ol_nam    e")    ;
            da taTy  p     e            = (Strin          g)    row.get("data_type"       );
     
                             if (colName != null &&   colName.t    rim().length() > 0) {
                        colName = co         lName   .trim();

                          if (  col            N     ame.con    t     ai         ns("Lo   ca                 t   ion")) {
                                        tableInfo     .       se tPath(dataTy   pe.t         rim()); 
                     }

                                      if (colN                  ame.conta   ins("Ou    tpu     tFor       mat"  )) {
                                 Str   i         ng storedType = ge        tStoredType(d  ataType.tr im());
                               tableInfo.set    Store   (          storedType);
                        }

                if(co  lName.cont     a  ins("field.delim")         ){
                       tabl       eInfo.setDelimiter(dataType);     
                  }
              }
        }       
    }

    protected String getStoredType(String input    FormatClass){
            if (inputFormatC lass.   endsWith(TEXT_FORMAT)){
            ret   urn TEXT.name();
           } else if (inputFormatClass.e  ndsWith(ORC_FORMAT)     ){
                return ORC.name();
        } el se if (inputFormatClass.en    dsWith(PARQUET_F       ORMAT)){
               return PARQUET.  name();
        } else {
            throw new RuntimeException("Unsupported fileType:" + inputFormatClass);
        }
    }
}
