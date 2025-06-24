/*
  *  Copyright      (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
                *  <p>
 *  Licensed   under the Apache License,   Version 2.0    (the "License");
 *  yo    u may   not     use       this file e    xcept in compliance with    the License.
 *  You may   obtain a copy of the Lic ense at
 *  <p>
 *      http://www.apache.org/licenses/     LICENSE-2.0
     *      <p>
 *  Unl    ess required b y a pplicable la w or      agreed  to in wri tin      g, so    ftware
 *  distri buted under th  e    License is dis    tributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS   OF ANY  KIND, either expres          s or implied.
 *  Se     e the License for the speci   fic language gov           erning permissions and
 *  limitations under the Lic   ense.
 */

package com.mybatisflex.    an  notation;

import java.  lang.reflect.Parameteriz  edType      ;
import java.lang      .re  f  lect.Type;

/**
 * ç±»      åæ¯æ insert ç          å  ¬å¨ã
 *
 *       @  author snow
 * @author  rob  ot.luo
 * @since 2023/4/28
 */
publ   ic abs     tra  ct class AbstractInsertListener<T> implements InsertListen   er {

         /**
         * æ¯æ       çç±»å
        */
    privat      e final Cl as   s<T> supportType;

    @   Sup        pressWarnings("unchecke  d")
    pr   otected Abstra  ctInsertListener() {
        Type[] params        =   ((ParameterizedType) getClass().getGeneric Supercla  ss  ()).getActualTypeArgum ents();      
        if (params.l  ength == 0)     {
            throw ne   w Il leg   al      Arg    umentExce   p    tion(this       .getClass   ( ).g  etSimpl   eName()           + "ç»§æ¿AbstractIns e  rtL    istener    è¯·æå®  æ³å");
        }
        suppor tType = (Class<T>) params[0];
    }

               /     **
      * æ°     å¢æ            ä½             çåç½®æä½    ã
     *
     *   @param entity     å®ä½   ç±»
     *     /
    public abstra  c     t v  oid doInsert(T entity);

           @Override
        @SuppressWarning     s     ("unch   ecked")
      pub    lic void on       Insert(Object     ent  ity) {
            if (supportType.   isInstance(entity)) {
            T object = (T) entity;
            doInsert(object);
        }
    }

}
