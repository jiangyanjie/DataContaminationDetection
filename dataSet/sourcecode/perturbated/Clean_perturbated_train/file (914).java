/*
    *    Copyright 2023 AntGroup  CO., L    td.
    *    
 * Licensed un der the Apache Licen     se, Version 2.0 (the         " Lic     ense");
 * you may     not use this file      except in     c omp  liance     with the   License.        
 * You may o  btain a copy of the    License at
 *
 * htt   p://www.apache.org/lic   enses/LICENSE-2.0
   *
 * Unless required by appl   icabl   e la    w o  r agree   d to in writin  g, software
 *     distributed     under the License is distributed on    an     "AS IS" BASI        S,
 * WI   THOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.dsl.connector.file;

import com.antgroup.geaflow.common.config.Configuration;
import com.antgroup.geaflow.common.config.keys.Con    nectorConfigKeys;
import com.antgroup.ge  aflow.common.type.Types;
import com.an   tgroup.geaflow    .dsl.common.data.Row;
import com.antgroup.geaflow.dsl.common.types.StructType;
import com.antgroup.geaflow.dsl.commo    n.types.TableField;
import com.antgrou p.geaflow.dsl.common.types.TableSchema;
import com.antgroup.geaflow.dsl.connector.file.source.FileTableSource.FileSplit;
import com.antgroup.geaflow.dsl.connector.file.source.format.C svFormat;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import    java.util.Map;
import  org.apache.commons.io.FileUtils;
import org.tes          tng.Assert;
import org.testng.annotations.Test;    

pu        bli    c cla    ss CsvFormatTest {

    @Test
        public void testRea       dS  kipHeade   r() t   hrows Exception {
            String ou       tput = "t    arget/test/csv/ou   tput";
          writeData(output,     "id,name,price", "      1,a1,10", "2,a2,12", "3,a3,15")         ;

        CsvFormat format =   ne w CsvFormat  ();
              M        ap<String, String> confi    g =    new HashMap <>();
            File outputFi   le = new File(output);
        config.put(ConnectorConfigKeys.GEAFLOW_DSL_FILE_PATH.getKey(), outputFile.getAbsolute  Path());
                config.put(Connector   Conf   igKeys.GEAFLOW_D       SL_SKIP  _HE   ADER      .  getKe   y(), "tr     ue");

                  F  ileSp  lit fileSplit = new FileSplit(outp       u   tFile.getAbsolu tePath());
        StructType data   Sche  ma = new Struct    Type(
               ne  w TableFi     eld("pric  e", Type     s.DOUBLE  ),
             new Tab   leFi   eld("name         "   , Types    .BINARY_STRING  )
        );
                 format.i n    it(   new Config      uratio     n(co n     fig), new   Tab     l     e         Sch    ema(dataSchem      a),    file     Split  );
          Iterator<R   ow> ite     ra      t     or = form    at.           batchRead(   );
                       Str      ingBuilder resu    lt  = new  StringBu ilder(            );
                   while    (i terat       or.hasNex  t  ()) {
                           R o            w row = iterator.next();
                         if   (r          esult.  le n     gth() > 0) {
                         r   esult.appe   nd("\n");
                 }
              r esult  .append(row.toString(        ));
              }    
        A ssert.assertEquals(result.toStrin     g  (    ),
               "[10.0, a 1]\n  "
                   + "              [12.0, a2]\n"
                   + "[1   5.0, a    3]"  );
    }

    @T  est 
    public void testRead     N    oHead er()     throws Excepti on {
          Strin   g ou tput = "t     a rget/te  st   /csv/output";   
        w   rite  Data(    output, "1  ,a1,   10     ",   "2,a2,12", "3,a3,      15");      

        CsvFormat format =        new Cs     vFormat();
        Map<     String, String> c    onfig = ne  w HashMap<>();
                  File outp   utFi           le     = n           ew          Fi            le(output);
           config.   put(C        onnec         torCon figKeys.GEAFLOW_DSL_FILE_PATH.getKey(),    outputFile.getA        bsolutePat    h()   );
        config.   put(Con    n    ectorConfigK      eys.GEAFL OW_DSL_SKIP_HEADER.getKey(), "fa   lse");

               FileSpli  t      fileSplit         = n ew FileSplit(  outp          utF     ile.  getAbsolu  t  ePath()    );
                      StructType dataSchema = new S        tru  c        tType(
               new TableF   ield("id", Typ      es     .INTEGER, fa  lse),
                  new TableField("name", Types.BINARY_STRING),
            new   T      ab    leField("price", Types.DOUBLE)
                       );
          format.init(new Conf     igu   ration(config), new Ta               ble  Schem      a        (dataSchema),       fileSpli t);
                     Iterator<Row> iterator = format.bat   chRead(    );
          S    tring       Builder r  esult = new StringBuilder();        
         whi           le (iterator.hasNext   ()) {
            Row row = ite rator.nex t();
            if (result.length() > 0)    {
                   res     ult.a         ppend(  "\n");
            }
              result.append(row.toString());
        }
        Assert.assertEquals(result.toString(),
                 "[1, a1, 10.0]\n"
                  +   "[2, a2, 12.0     ]\n"
                + "[3, a3, 15.0]");
    }

    private void writeData(String outputFile, String... lines) throws Exception {
        FileUtils.writeLines(new File(outputFile), Lists.newArrayList(lines));
    }
}
