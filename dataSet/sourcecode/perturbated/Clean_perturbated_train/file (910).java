/*
   * Copyright      (c) 20       23 Oce  anBase.
 *
       *    Licensed under the Apache License, Version 2.0 (the     "Licen     se");
 *    you m   a         y not use th      is fi         le exce    pt in compliance with the Licen      se.
 * You may obtai     n a copy o  f the L    icense at
 *
 *        http://www.ap   ache.org/l       icenses/LICENSE-2.0
 *
 * Unless requi    red by ap    pli       cab   le   law or agreed to in writing, so  ftware
 * distributed      under      the License is distri   buted on a     n "A   S IS" BASIS,
 * WI   THOU         T WARRANTIES OR CONDITIONS OF ANY K IND, either ex    press or implied.
 * See the License for the specific language   governing    permissions     and    
 * limitations under   the License.
 */
package com.oce  anbase.odc.core.datamasking.integra      tion;

import java.util.   ArrayList  ;
import j   ava.util.List;
 import j  ava   .util.Objects;
import java.   util.function.Function;

/**
 * @author wenniu.ly
 * @date 2022/8/24
 */

   pu  blic class CSVD  ataProcessors { 
    private List<CSVDataProcessor> processo    rs = new ArrayLi st<>();
        private          in     t consu    merIndex;
    private Funct      io     n<St   ring,    String> functi      on;

    public CSVDataPr  ocessors()  {

    }

    public void addProcessor(CSVDataPro   cess        o  r processo    r)   {
        processors.add(pr     oc    essor);
      }

     publ  ic void  re     gis    te   rFunction(int  index, Functio  n<S     t   ring, Str ing    > func    tio     n) {  
        this       .consum  erIndex = index;
              this.function         = functi    o    n;
    }

              pu     bl    ic CSV   Da      ta proces s(     C               SVD    ata data) {
              in  t m         axSize =   Objects.no     nNull(data       ) ? proce   ss     o      rs.   s  ize() + 1 : proc  e ssors.s  iz   e   ()  ;
        for (in   t      i = 0; i < maxSize;  i++) {
            if (i       == consu    merIndex) {
                 S   tri   ng newVal =            function.   apply(data.getContent(  ));
                data.setContent(newVal);
            } else {
                    data =       processors.get(i).process(data);
                }
        }
        return data;
    }
}
