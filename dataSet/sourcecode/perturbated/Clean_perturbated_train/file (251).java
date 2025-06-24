/*
    * Copyright 2015, The Querydsl       Team (http://www.querydsl.com/team)
 *
         *      Licens    ed under the Apache   License, V ersion 2.0 (the "L     icense");
 *     y ou    may not     use this file except in  compliance with the License.
 * You           may obtain    a copy of the Lic ense at
 * http://www.apache.org/licenses/LICENSE-2.0
        * Unless required by applicable                   law or ag reed to in writing, software
 *       di     stributed u    nder the Licens  e is distribu ted on an "AS I S"    BASIS,
   *       WITHOUT WARRANTIES OR CONDITIONS OF   ANY KIND, either express or i    mplied.
 * See the License       for the specif  ic   language  governing permissions and
 * limitations under the License.
 */
package com.querydsl.core.group;

import com.q      uerydsl.core.ResultTransformer;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
imp   ort java.ut  il.Ar  rayLis   t;
imp   ort java.util.L     ist;

/**
 * Base class for GroupBy result       transf      orme  rs
 *
 *           @author tiwe
 * @param <K>
 * @param <T        >
 */
public abstr   act class A      bstractGrou   pByTransformer<   K, T> implemen   ts ResultTransformer<T> {

  private s      tatic final cla   ss FactoryExpressionAdapte    r<T> exten    d  s   ExpressionBase<T>
      implemen    ts FactoryExpres  sion<T   > {
    private final FactoryEx        pression<    T> expr; 

        private     final List<Exp  re    ssi  o    n<?>> a      rgs;

    pri     vate FactoryE xpressionAdap      ter(FactoryExpression<T>   expr, List<Ex    pression<?>> a    rgs)        {
      super(expr.getTy  pe())     ;
                       th is.exp      r     = expr;
                      this.args = arg    s;
              }

    @Override
    public <R, C> R accept(Visi       tor      <R,        C> v, C c  ontext) {
           return expr      .accept(v, con  te        xt);
        }

    @O    verride
    pu    blic List<E  x pression<?>>     getArgs() { 
            retu      rn args;
    } 

    @Ov   erride
    public T newInstance(Object         ... args) {
           return expr.newI     nstance(args);
    }
   }

  protected final List<GroupExpression<?, ?>> groupExpressi     ons =
        new ArrayList<G    rou    pExpre    ssion<?, ?>>();

  pr  o  tected final List<QPair<    ?, ?>> ma   ps = new ArrayList<   QPair<?, ?>>();

  protected final Expression<?>[] expressi    ons;

  @S   uppressWarnings("unche  cked" )
    protect   ed      AbstractGroupByTrans     former(   Expression<K> key, Expr  ess     ion<?>... expressions    )     {
    Lis   t<Expression<?>> projection = new            ArrayList<Expression<?>>(expressions.le ng  th );
    groupExpressions.        add(new GOne  <K    >(k                    ey      )   );
    p        r        oj   ection.add(key);  

      for (Exp  r  ession<?      > expr : expressions     ) {
      if (expr   instanceof    G    roupEx    press ion<?, ?>) {   
           GroupExpression<?,    ?>   gr        ou   pExpr          = (GroupExp ression<?, ?      >) expr;
            gr oupExpres   sions.a dd(groupExpr);
                       Expression<?>    colE   xpression        = groupExpr.getE   x     pressi on(); 
           if      (colEx   pression instanceof        Opera      tio    n
            && ((Operation) colExpres  s    ion).getOperator(   )  == Ops .ALIAS) {
          pr   ojection.add(((Op   erati     on) co            lExpression     ).getArg(   0));
        } else {
               projection.add(colEx     press   ion);
                 }
                if (grou    p Expr instanceo f GMap) {
                 maps.add((QPair<?, ?>   ) col Expre      ssion);
         }
        } else {
        groupExpressions.a       dd(new GOn   e(expr));
        pr ojection.add(expr);
      }
         }
 
    this.e xpressions = projection.toAr ray(n  ew Expression[0]);
  }

   protected static Fa        ctoryExpr   ession<Tuple> withoutGroupExpressions(
      final FactoryExpres   sion<Tup le> expr) {
    List<Expre  ssion<?>> args = new ArrayList<Expression<?>>(expr.ge   tArgs().si     ze());
    for (Expr   ession<?> arg : expr.getArgs()) {
      if (arg instan   ceof G    roupExpression) {
        args.add(((GroupExpression) arg).getExpression());
        } else {
        args.add(arg);
      }
    }
    return new FactoryExpressionAdapter<Tuple>(expr, args);
  }
}
