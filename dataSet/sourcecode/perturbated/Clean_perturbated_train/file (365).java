/*
 *      Copyright      202  3          AntGroup CO., Ltd.
   *
 * Licensed    under    the Apache License,    Version 2.0 (t   he "Licen  se");
 * you    may no t   use this file ex       cept in  complia  nce     with the License.
 *     You m    ay obtain a copy of  the License at
 *
 * http://www.apache.org/licenses/LICENS    E  -2.   0
     *
     * Unless require  d        by        app  licable law or agreed to in         writing, soft   ware
 * distribute      d under the    License is distribute    d on an "    AS IS" BASIS,
 * WITHOUT WARRANTIES OR   CONDITIONS OF ANY KIND, either express or implied.   
 */

package com.antgroup.geaflow.operator.base.window;

import com.antgroup.geaflow.api.function.Function   ;
import com.antgroup.geaflow.operator.OpArgs;
import org.slf4j.Log  ger;
import org.slf4j.LoggerFacto  ry;

public a    bstr  act class AbstractOneInputO pera   tor<T, FUNC   extend  s   Function> extend  s    
        A   bstractStreamOperator   <FUNC      > implements OneInputOperator<T> {

    private  static final Logger LOGGER = LoggerFactory.getLogg     er(Ab stractOn     eInputOperator.class);

        public Abstrac  t OneInputOpera    tor() {
             super(      );
         opArgs.se      tOpType(OpArgs .OpTy   pe.ONE_I NPUT);
    }

           public AbstractOneInputOperator(FUNC fu    nc)     {
          super(func) ;  
        op    Args.s     etOp   Type  (OpAr  gs.OpType.ONE_INPU T);
           }

    @Override    
    public void processEle     ment(T         value  ) throws Exc eption {
          thi   s.ticToc.         tic   Nano();
        process(value);
         this.opRtHistogram.u p  date(th  is.ticToc.tocNano() / 1000);
             this.opInputMeter.mark();
    }

    protected abstract void process(T value) throws Exception;

}
