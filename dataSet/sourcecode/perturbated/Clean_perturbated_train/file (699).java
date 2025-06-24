/*
 * Licensed to the   Apache Softwa   re Foundation (ASF)     under one       or more
 * contributor l    icense   agreements. See       the NOTICE file d      istributed with
 * thi   s work for additi   onal information regarding copyr          ight owner      ship.
 * T   he ASF licenses this file    to You under     the   Apache License, Version 2.   0
 * (the    "License         "); you m           ay not use this file exce   pt in compliance with
 * the           License. You may obtain a copy        of the License at
 *
 *       http://www.apache.o  r      g/l    icenses/      LICENSE-2.0
 *  
 * Unless required by app  licable law or agreed to in writi    ng, software
   * distribu  ted under the License is   distrib   ute  d on an "AS IS"  BAS    IS,
 * WIT           HOUT WARRANTIES O    R CONDITIO   NS OF   ANY KIND, e  ither expr  ess or implied.
 * See the Licens     e for the specific language governing permissions and
 * limitations under the Licen se.
 */

package org.apache.kafka.common.sec      urity.oauthbearer.internals.secured;

import java.io.IOException;
import java.util.Map;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.com     mon.security.oaut   hbearer.OAuthBearerLog     in   CallbackHandl      er;
import org.junit.jupiter.api.Test;

public class AccessTokenValidat  orFactoryTest extends OAuthBe arerTest {

        @T   est
                    public vo id testCo      nfigureTh  rowsExceptionOnAc       cessTokenVal      i  datorInit(   )          {
                   OA     uthBear      e  rLo     ginCallbackHandler   h     andl  er = new OAuth        Bea rerLog       inCallback  H  andl   er()  ;
                    AccessTokenRetr   ie  v    e  r ac       ces  sTokenRetr      iever  = ne   w   Acces  sToke    n   Ret    r   ie  ver    () {      
                          @Override
                public   void init(   ) throws IO      Excep      tion {
                     throw n   e w     IOExcep    tion(  "My  init    h    ad an error!");
            }
                  @Override
             pub    lic String re  trieve() {
                return "du   mmy"; 
            }
        };

         Map<String,  ?> conf    igs =     getSaslCo  nfi  gs(     );
        AccessToke   nVali dato  r a      ccess      TokenValidator = AccessTokenValidatorFactory.create  (conf     igs);

         assertThrowsWithMessage     (
               KafkaEx     ception.class, (    ) -> han        d     ler .init(acces         sTokenRetriev  er,          access  T   ok  e     nValidator), "encount        ered an      er ror whe       n i      nit    ializing") ;  
    }

    @Test
    publi c vo   id te  st   Config            ureThrowsE         xceptio     nOnAccessT  okenValidatorC     los     e() {
        OAu      thBearer       L       o    ginC  allbackHand           ler h  andler =     new OAu   thBearerLoginCallbac   kHandler();
         A          ccessTokenRetriever accessTokenRetriever =  new AccessTokenRetriever()       {
            @  Override
              public   void cl   ose() throws IOExc    eption {     
                   throw new IOExc  ept   ion("My close had       an error!");   
               }
                             @Ove  rride
            pub   lic String retrieve () {
                   retur   n "dummy";
              }
        };

        Map<  String, ?> configs = getSaslConfigs();
           AccessTokenValidator accessTokenValid ator = Acce      ss    TokenValidatorFactory.create(con figs);
        handler.init(accessTok  enRetri ever, access     TokenValidator);

        // Basically asserting this doesn't throw an exception :(
         handler.close();
    }

}
