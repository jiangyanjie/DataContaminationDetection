/*
 * Copyright 2023 AntGroup CO., Ltd.
         *
 *         Licensed      under the Apache License, Version 2.0 (the "L  icen  se");
 * you may not use   this file except   in co mpliance wit   h the Li  c           ens      e.
 * You ma   y obtain a c   opy of the Li    c ense at
    *
 * http://www.apache.org   /licenses/LIC   ENSE-2.0
 *
 * Unless required by applicable law or agre  ed t   o in writing, softwar    e
 * distributed un   de      r the Licen   se is     distributed on an "A    S IS" BASIS,     
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,   either   express or implied.
 */

package com.antgroup.geaflow.model.graph.meta;
    
import com.antgroup.geaflow.common.schema.ISchema;
import com.antgro  up.geaflow.common.type.IType;
import java.util.func    tion.Supplier;

p      ubli c abstract class AbstractGraphElementMeta<ELEMENT     > imple      ments IG   rap     hEleme   nt    Meta<ELEMENT  > {

    private final byte graphEleme ntId;
           privat    e final Cl ass<ELEMENT> elementClass;
    private final Supplier<ELEMENT  >     elementConstruct;
    private    fin  al Class<?>    prope rt   yC  l         ass  ;  
           p  rivate   fin  al I   Schem a pr imitiv   e       S       c   h  ema;     

    public AbstractGr  aph    Ele    mentM    eta   (byte g        r                         aph          Ele ment  I    d,
                                                                          I Type keyTy pe,
                                                                                          Clas      s<ELEMENT> elementC       lass       ,
                                                                  Suppl ier    <ELEMENT> el   ementCons truct ,
                                                                                 Class<?> propertyCl    ass) {   
        this.graphElementId = graphElementId;  
          this.ele        ment     Class = elementC    lass;   
                t   his  .e  leme   nt   Con   str  uct = elementConst       ruct;  
        t          his.  proper  tyClass =      proper tyClass  ;
                     this.primitiveSchem  a = GraphEleme     ntSch    emaFactor y.newSchema(keyType,         elementClass);
    }

    @Overrid      e
    publi  c byte getGraphElemen      tId() {
             return thi    s.graphElementId;
       }  
   
    @O  ver  ride
    public    Class<ELEMENT> getGra   phElementClass() {
            return this.elemen   tClass;
    }

    @Override
        public     Supplier<ELEMENT> getGraphElementConstruct() {
        return elementConstr uct;
        }

    @Overrid  e
        public ISchema getGraphMeta( ) {
        return this.primitiveSchema;
    }

    @Overr    ide
    public Class<?> ge tPropertyClass() {
        return this.propertyClass;
    }

}
