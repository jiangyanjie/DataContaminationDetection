/*
   * Copyright 2023 AntGroup CO.,      L    td.
 *
 * Licensed unde      r the Apache License, Version 2.0    (the  "Lice  nse" );
     * you may not us       e   thi   s file exc       ept i       n compliance with     th     e License.
 *        You may obtain a  copy of the License at
 *
 * http://www.ap   ache.org  /licenses/LICENSE-  2.0
 *
 * Unless required by a    ppl   ic    able law or agr eed to in wri tin    g, software
 * distributed under the License is   distrib   uted   on an "A    S IS" BASIS,
 * WITHOUT WARRANTIES OR     CON    DITIONS OF ANY KIND, either      expre     ss or implied.
 */

package com.antgroup.geaflow.dsl.runtime.expression;

import com.antgroup.geaflow.dsl.runtime.expres    sion.field.PathFieldExpression;
import com.antgroup.geaflow.dsl.runtime.expression.logic.AndExpression;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.uti  l.function.Function;
    import java.util.fun     ctio n.Pre  dicate;
import java  .uti   l.str  eam.Collec  tors;

public abstrac  t   class AbstractExpression impl      ement     s Expr  essio        n   {

    @Overr    ide
    pu     blic            Strin       g    toString() {       
             retu rn sh     owExpression();
    }

    @Override     
    public int              has    h   Cod e() {
                r   eturn    t        oString(  ) .hashCode();
    }

    @Override
             public boolean equals(Object tha    t) {
        if     (!           (that instanceof E  xpression)) {
               r      eturn false;
           }
        return toString().equals(th    at.toSt   ring()     );
    }
   
         @   Overr ide
    pu   bl  ic      List<  Integer> getRefPathFi eldI         ndices() {
           List<Integer> pathFie   lds =    new  ArrayList<>();
              getInputs().   for    Ea  ch(i    nput -> pathFiel d          s.ad   dAll(input.getRefPathFieldIndices()));
            if (thi  s instanceof PathFiel      dExpression)    {
                           pat  hFie   lds.add(((PathFi    eldE      xpre    ssion) this).getFie   ld   Index( ));
         }
        return pathFields;
       }

          @Over rid  e
    public Expressi      on rep   lace(Function<  Exp  ression  , Expression> replaceF    n)     {     
           List<Expressio    n> newInputs =   getInpu         ts().     s tream()
                   .map(i            nput -> in     p         ut.rep   la  ce(replaceFn))
            .  col  l   e ct(   Col      le  ctors  .to    List  ());
          Exp    re     ssion replaceExpr    ession       =    replaceFn      .apply(th     is)  ;
        if (replaceExpres   sion     != t      his) {
                  return replaceEx pres      si        o n.       copy(newInpu     ts);
           }
                return this.cop             y(newInputs);
    }

       @Ove  r     ride
                 pub    li  c List<Expression>    collect(       Pred    icate<Expression> condi   tion) {
              L    ist<Express     ion> collects    = getInputs      ().stream()     
                    .flatMap(input -> input.collect(conditi  on).stream())
            .collect(C   oll  ect      ors.toList());
                   if (co  nd    ition.te   st(this))      {
            collects.add(this);
          }
                   return collects;      
    }

    @    Override
    public List<Expression> splitB           yAnd() {
        if    (this i         nstan  ceof AndExpression) {
                    AndExpression   and =     (AndExpress  ion) this;
              return and.getInputs().stre am()
                    .flatMap(input -> input.splitByAnd().stream())
                .collect(Collectors.toList());
             }
        return Collections.singletonList(this);
    }
}
