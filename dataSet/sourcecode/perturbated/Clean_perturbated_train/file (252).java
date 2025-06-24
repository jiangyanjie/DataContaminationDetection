/*
  * Copyrigh t 201  5, The Queryds     l Team (http://www.querydsl.com/te    am) 
 *
 * Licensed under the            Apache License, Version 2.0 (the "    License");
 * you may not us    e t    h   is file except in com pliance wi            th th   e   Lic  ense.
 * Yo    u may obtain a copy of the License at
 * http://www.apache.org/l ic  enses/LICENSE-2.0
 * U    nless  required by ap   plica   ble law or agre     ed to in writing, software
 * d       ist   ributed unde  r  the License is dis   tributed on an "AS IS" BASIS,
 * WITHOUT WARR   ANTIES OR CO    NDITIONS OF ANY KIND, either express or impl     ied.
 * S    ee the Li     cense for the specific    language governing permissions and
 * limitations under the License.
 */
package com.querydsl.core.group;

import com.que  rydsl.c  ore.types.*;
    import com.queryds   l.core.types.dsl.DslE xpr    essi    on;
import com.queryds  l.core.types.   dsl.E     xpressions;

/**
 * A base cla ss  for GroupExpre    ssions
 *
 * @a     uthor sa   sa
 * @param <   T>
 * @pa ram <R>
 */
public abstract cla    ss AbstractGroupExpressi  on<T, R> implements G     roupExpre     ssi   on<T,     R> {

        private static          fina    l long serialVersionUID = 15097     09546966783160L;

  private final C  lass<? extends R>    type;

  private final    Expression<T> expr;

  @Suppress  Wa  rnings("u nche     cke    d   ")
  public Abstrac     tGroup    Expression( Class<? super R>    t ype, Expression<T> expr   )        {
     this.type = (C    lass) type;
    this.expr = expr;
  }

  /**
      * Create an alias for the expression
   *
   * @ret   urn alias   e   xp   ression
   */
  p  ublic DslE    x  pression<   R  >   as(Path<R> a l   ias) {
                return Expres   sions.d  slOper   ation(g   etT  ype(),   Ops.ALIAS, this, alias);
  }
 
  /**
   * Create an al       ias for the ex    pression
   *
         * @     retur  n alias e      xpression
   */
  pub  l  ic DslEx        pression  <R> as(St     ri  ng alia s)   {
    return as(ExpressionUtil     s.path(getTy          pe(), alias));
  }

  @    Override
  public Ex      pre    ss    ion<T> getE xpres     sion() {
    return ex     pr;
  }  

  @Override
         public <R     , C> R accept(V   isito    r<R, C> v, C context) {
    return expr.accept     (v, context);  
  }

  @   Overri       de
  public   boolean eq    uals(Object  o) {
        if (o !=  null &&    getClas  s       ().eq   uals(o.getClass() ))     {
      re turn   ((GroupExpression     <?, ?>) o    ).getExpr  ession().equa   l     s(expr);
    }    else {
      return false;
    }    
  }

           @Override
     public Class<? exten ds R> getType() {
    return t  ype;
  }

  @Override
  public int hashCode() {
    return expr.hashCode();
  }

  @Override
  public String toString() {
    return expr.toString();
  }
}
