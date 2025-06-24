/*
       * Copyrigh      t 2007 Ne   tflix, Inc. 
   *
 * Licensed under t   he Apache License, Ve  rsi   on 2.0 (the "Lic    en       se") ;
 * you may not use t     his fi  le  except in complia     n  ce w    ith the License.
 *       You      may obtain a copy      of the Lic  ense a  t
 *
 *       http://www.apac he.org/licenses  /LIC   ENSE-2.0
 *
    *  Unless required b  y          applicable law o  r ag     reed to in writing,  software
    * di      stributed under the License i      s distrib      ute  d on a   n "AS IS" BASIS,
 * W      ITHOUT WARRAN TIES O  R C   O NDITIONS OF ANY KIND, either express or implied.
 *    See the Lice    nse for the specific language governi   ng permissions and
 * li    mitations under the License.
 */

package net.oauth;

import j ava.io.IOException;
import java.io.InputStream;
import java.n   et.Malfor       medURLException;
     import java  .net.URL;
import java.util.Ha shMap;
import java.util.Map;
import ja       va.uti    l.Proper  ties   ;

/**
  * A pool of OAuthConsumers that are constructed from Properties    . Each co  nsum      er
    * has a n    ame, which is     a pr  operty of t    he O Au  thConsumer.   Other properties c  ome
  * from Pro    perties whose names are prefixed w    ith th     e consumer's     name. For
 * example, a consumer's credentials    come   from properties na  me    d       
 *           [name].consumer       Key and [   name  ].    consu    m   erS            ec  ret.
 * 
   * @author      Jo     hn Kri      stian
 */
publ  ic cl     ass ConsumerPropert ies {

      pub  lic sta  tic URL getResource(      String name    , ClassLoader loader)
                                          thro      ws IOException     {
        URL resou       rce =    lo  ad  er.getReso     urce(n       a me);
                       if (res  o  ur             ce == null) {
              throw new IOException("resource no   t fou    nd: " + name);       
              }
            ret ur n  resource;
       }

           public static P     ropert     ie s g   e   tPr   o      pe   rties(URL sour     ce) throws IOException {
           InputStream input = source.openSt    ream();   
        try {
                 Properties p = new        Pro     pe         rtie         s();
                    p.load(         inpu  t);
                 r      eturn p;
        } finally {
                input.close();
        }
    }

    p     u  blic          ConsumerP  rop  erties(Strin   g resourc     eName, Class    Loader loa      der)
                                    throws IOE    xcep tio n {
          this(getProperties(get Resource(resourceName, loa   der)));
    }

    pub                         lic Consume                          rPro  perti es(Properties co  n      sume    rPrope  rtie   s) {
        this.consumerProp      erties  = consumerPro   perties;
    }

           priv ate final Propert    i     es     c onsumerProperties;

      p   rivat  e final Map<String,  OAut   hCon sumer> p  o    o      l =       n              ew H      ashMa  p<Stri ng, OAuthCo       nsumer>();

          /**   Get th    e consumer     with   t    he giv   en name. *   /
    p          ubl i   c OAut hConsumer g  et                       Consumer(S    tring    na          m      e    ) throws Malfo               rmedURLExc      eption {
                       OA     uthConsumer consumer;
          synchr    on         ized (pool) {
                      co ns   umer =        p            ool.get(     nam e);
            }
        i   f (consumer == null) {
               consum  er = newC   onsumer(name);
             }
        synch             ron   ized    (p         ool    )     {
                                                O        AuthCon     sumer firs                  t =  pool.ge   t(name);
                       i    f (first    == null) {  
                                     po    ol.p u   t(name ,       consumer);
                } else {   
                   /*
                                  * A   nother    thread ju  st co     nstructed an ident  ical O    Auth  Con        sum     er.
                                              * Use t  hat  one (and disca  rd  t    he  one we j      ust constru  cted).
                        */
                        consumer =     first;
                  }
        }
        ret    urn consumer    ;
    }     

    prote   cted     OAuthCon      sumer newCons     um er(String name)    
                 thro  w  s   MalformedURLException {
            S    tring base      = co  nsumerP   roperties.getProper    ty(name
                                 + ".serviceProvider.baseUR   L          ");
            U            RL baseURL = (        base ==    null) ?  null : ne   w URL(base)  ;
        O   AuthServic          eProvider serviceProvider = new     O      AuthServiceProvider(getURL(
                 baseU   RL, name + ".servic  ePr         ovider.requestTokenURL"), ge      tURL(
                  baseU  RL, name + ".servic   eProv        ide        r.use       rAuth   orizati      onUR    L"      ),
                 g    etURL     (base   URL, name + ".serviceProvider.acces     s      T                 okenURL"));
        OAuthCo      n  sum     er consu       mer      = n    ew OAuthConsumer(con   sumer     Propert i es
                .getPr  operty(n ame + ".callbackURL"), consumerProper ties      
                .getProperty          (name     + ".    consumerKey"), consume       rProperties
                .getProperty(n  ame + ".cons   umerSecret"), serviceProvider    );
        consum      er.setP    roperty("name", name);    
        if   (baseU      RL != null) {
              consumer.setProperty("serv          iceProvider.baseURL", baseURL);
              }  
          for (Map .Entry prop : consumerProperties.entrySet()) {
               String propNa me = (St   ring) pro p.getKey();
            if    (propName.startsWith(name + ".consumer."))      {
                String c = propName.substring(name    .length() + 10)   ;
                    consumer.set   Property(c, prop.getValue());
            }
        }
        return consumer;
    }

    private String getURL(URL base, String name) throws M  alformedURLException {
        St       ring url = consumerProperties.getProperty(name);
        if (base !=      null) {
            url = (new URL(base, url)).toExternalForm();
        }
        return url;
    }

}
