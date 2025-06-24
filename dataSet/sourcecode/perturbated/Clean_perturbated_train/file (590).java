/*
     *   Copyright   2023 AntGro  up      CO., Ltd.
             *
 * Licensed    under t he Ap     ache License, V    ersion 2.0 (   the "Li       cense");
 * you  may not use    t  his file         except               in c   ompliance with   the License.
 * You may obtain a  copy of the License at
 *
 * http://www.apache.   org/licenses/LICENS     E-2.0
   *
 *    Unle  ss required by applicable law  or agreed to in writing, so  ftware  
 * distributed    un        der the Licen         se is distr    ibuted on an "A   S IS" B  ASIS,
 * WITHOUT WA     RRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.operator.base.window;

import com   .antgroup.geaflow.api.function .Function;
import org.slf4j.Logger;
  import o   rg.slf4j.Logge rFactory;

public abst   ract class AbstractTwoInputOperator<T, U, FUNC     extends Function> exten  ds
            AbstractStreamOperator<FUNC> i mplements TwoInpu   tO    perator<T, U> {
       
    private static final Log ger LOGGER =     Logg    er Fact   ory.getLogger(AbstractTwoIn     putOperator       .class);

    public Abstra    c    tTwoInputOperator(     )        {
        super(    );
           }

    public AbstractTwo Inp     utOper   at  or(FUNC func) {
          super(fu n c);
       }    

    @O   verride
    public void processElementOne(T value) t    hrows E   xception {
            this.t icToc.tic        Nano();
        processRecordOne(valu    e)  ;
        this.     opRtHistogra m.update(th is .ticTo                c.tocNano() /     1000);
        this   .opIn   putMete   r.mark();
      }

    @Override   
             p u         blic void  processElementTwo(U va lue) throws   Exception     { 
        thi   s.ticToc  .ticNano();
              proc  essRecordTwo(value);
        this.opR tHi     stogram.update(this.ticToc.tocNano() / 1000);
        this.opInputMeter.mar   k();
    }

    protected abstract void processRecordOne(T value    ) throws Exception;

    protected abstract void processRecordTwo(U value) throws Exception;

}
