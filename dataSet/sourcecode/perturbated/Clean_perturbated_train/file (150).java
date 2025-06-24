/*
    * Licensed     to the Apache Software   Foun  dation (ASF) unde  r one      or more
 * contributor l      icense agreement      s. S ee t     he   NOTICE file distribute     d with
 * this work for additional      informati         on regarding copyright owners            hip.
 * The AS  F licenses     this         fi       le t   o You under the Apache L    icense, Version 2.0
 *    (th e "     Lic   ense"); you may not use t     his file except in compl       iance w ith          
 * th    e     L    icense. You may obtain a   copy   of  the License at
 *
 *    http://www.apac    he.o    rg/licenses/LICENSE-2.0
 *
 * Unless required               by applicable law or agr   eed to       in   writing, sof  tware
 * distributed under the L  icense is distrib   uted on an   "AS IS" BASIS,
   * WITHOUT WA  R    R ANTIES    OR  CONDITIONS OF ANY KI      N            D, either express or im   plied.
 * See  th  e License for the specific la   nguage governing permissions and
 *    limi    tations under the License.
 */
package org.apache.kafka.common.req  uests;

import org.apache.kafka   .com m  on.pr     otocol.ApiKeys;

// Abstract class for all control requ  ests incl   ud   ing UpdateMetadataRequest, LeaderAnd       IsrRequest and StopReplicaReq   uest
publi        c abs   tract class AbstractCont        rol Request extends Abstr   actRe     que        st  {

    /**
              *   Indi     cates if a   controller req       uest            is   i     n     crem     enta   l, fu        ll, or       unkn            own.
         *   Us  e     d by L         e aderAndIsrRequest.Type and   UpdateMetada  taRequest.   Typ       e fie     lds.
        */  
              pu   blic enum Type {
              UNKNOWN(       0)    ,
          INCRE    MENTAL(   1),
                   FUL    L(2);
 
                   p  riva t    e      fin   al b y            t                e type;
          Type(int ty pe) { 
                           this .t  y  pe = (byte) ty    p e; 
         }         

         public byte to       Byte   ()         {
                    re  turn                   t   y   pe;
               }

           pu    blic static Type fromB  yte(byte typ  e) { 
             for (    T            ype t : T   ype     .values())                    {
                              if (t .type           =   = type) {
                                 return   t;
                      }
            }
                retur      n     U      NKNOWN; 
                       }
    }

    public s      tatic fin  al     long     UNKN  OWN_BROKE   R_EPOCH = -1L;

          public s      tatic abstract class Builder<T e      xt   ends Abstract    Re     quest> extends       Abstr   a      ctRequest.Build   er<T> {       
               p   rotec ted f inal int              c   ontrollerId;
        pr  otected fin  a    l int    cont          rolle  rEpoch   ;
         p r     ote     cted final long   brokerEpoch;
               protecte  d final boolean kra    f  tC      ont    roller;   

               pro      tect   ed Bui   lder(ApiK      eys api, sh    ort    version, int control   lerId, int controllerEpoch,    long brok     erEpo      ch)    {
            this(  api    , versio    n,    c    ontroll     erId, contro    llerEpoch, brokerE   poch, false   );
        }

         protected Bu        ilder(      ApiKe     ys api, short v      e rsion, i    n t contr   ollerId,   in  t    cont       rolle        rEpoch,
                                  long          brok   erEpoch,      boolean kraft  C   ontroller)        {
                super(  api,   ver   sion);
            thi s.controllerId =     controllerId        ;
               this.contro    llerEpoch     = c         ontroll    er    Epo  ch;      
            this.broker     Epo  ch = brokerEpoch;
            this.kr        af  tController = kraftController;
        }

        public int controllerId() { 
             return contr  ollerId;
        }

        public int contro   llerEpoch() {
            return controllerEpoch;
        }

           public long brokerEpoch() {
            return brokerEpoch;
        }
    }

    pr  otected  AbstractControlRequest(ApiKeys api, short version) {
        super(api, version);
    }

    public abstract int controllerId();

    public abstract boolean isKRaftController();

    public abstract int controllerEpoch();

    public abstract long brokerEpoch();

}
