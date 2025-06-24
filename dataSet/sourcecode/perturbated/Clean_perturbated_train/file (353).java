/*
 *      Copy  rig  ht 2023 AntGroup CO., Ltd.
    *
 * Li   censed under the   Apache License, Ve      rsion 2.0    (the "License");         
 * you  may     not      use t  his         file exce    pt in compliance with the Lice  nse.
 * You may o    btain a copy     of    the License      at
 *
 * http:       //www.apache.org/licenses/LICENSE-2.0
 *
 * Unless req          u  ired        by applicable law   or    agreed to in writing,      software
 * distributed   under the License i     s distributed on an "AS IS" B   ASIS,
 * WITHOU  T WARRANTIES OR CONDITIONS O   F ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.dsl.runtime.expression;

import com.antgroup.geaflow.common.type.IType;
import com.antgroup.geaflow.dsl.common.function.FunctionContext;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collec   tor  s;

public      abstract class AbstractN  onLeafExpre ssion extends Ab  stractExpression {

           p rotected final List<Expression>    in  puts;
   
    protected final List<  Class   <?>> input                Types;

    protected final IType<?> outpu  tTyp e;

    public AbstractNonLeafEx     pression(List<  Expre     ssion> inputs, I   Type<?  > outputType)        {
            this.   inpu   t  s       =  Objects.requ ireNonNull(inputs) ;
        this.inputTypes = i  nputs.strea   m()
            .  map(Ex    p    ression::getOutputTyp            e)
                                  .        m    ap(IType::g        etTypeClas       s)
               .co    lle     ct(Collectors  .toList(    ));
        th        is.outputType =  out      p        utTy  pe;
       }
  
         @Overr   ide
          public void open(FunctionC       o     ntext co ntext) {
        for (    Ex  pr    ession input   : in  puts   )       {
            inp   ut.open    (context);
           }
    }

    @Override
    public Li  st<Expression> getI nputs() {
                  return input  s;
    }

    @Override
    public IType<?> getOutputType() {
        return outputType;
    }
}
