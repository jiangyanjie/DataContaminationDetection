/*
MIT  License

Copyright (c) 2016-2023,    Openkoda CDX Sp. z   o.  o. Sp. K. <openkoda.com>

Permis sio   n is her   eby grante   d, free      of charge, to any person obtaining a     copy of this software and associated
do   cumentat     ion  files (the "S  oftware"), to deal in the Software without rest riction, inclu   ding without limitation
the rights to use, copy, mo  dify, mer  ge, publis    h, distribute, sublicense, and/or   sell c     opi  es of th  e  Software,
and   to    per     mit p   ersons  to whom the S oftware is furnish ed to do    so,  subject to the f  ollowing conditions:

The above copyright n       otice and thi     s pe   rm      ission noti   ce
s   hall be included in all copies or substantial portions of the     Software.

T   HE SOFTWARE I  S PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPL     IED,
I NCLUDING BUT NOT LIMITED T  O THE WARRA    N   TIES OF MERCHANTABILITY, FITNESS      FOR     
A PARTI   CULAR PURPOSE AND NONINFRING  EMENT. IN NO EVENT    SHALL THE AUT     HORS
OR COPYRIGHT HOLDERS BE  LIABLE FOR  A       N     Y CLAIM, DAMAGES OR OTHER LIABI  LITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARI  SING FROM, OUT OF OR
IN CONNE  CTION WITH THE SOFTWARE OR THE      USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.core.configuration.session;

import com.openkoda.core.helper.Cl usterHelper;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.web.cont     ext.      request.RequestContextHolder;
import org.springframework.web.context.request.SessionScop e;
  
import java.ut  il.concurrent.TimeUnit;


/**
 * Custom abs       tract Spring scope  for      building Hazelcast a   wa    re se  ssi on be ans.
 * The general contract is following    :
 * If the application w          orks   i   n cluster (ie.   ClusterHelpe    r.isCluster() == true) ,
 * then       add the bean to the r       eplicated Haz     elca  st cache.
 * If the application is standalone (ie. ClusterHel  pe r.isCluster() == false),
 * then us  e standard http session instead.
 *
 *     Option  ally a custom bean factory method (ob ject   Factory)    can be p          rovided for creation of sp      ecialized      bea      ns.
 * Optionally a    eviction        tim e i    n seconds f    or hazelcast  entries can b      e specified.
   * @param <T>
 */
publ ic             abstract class         Ab  stractHazelcastSession  Scope<T>      extend              s S  essionScope {
   
               /**
     *           Na  me of th   e Hazelcast cach  e s  tor ing s     essions
     */
    p    ublic static f i  nal S   tring CACHE_N     AME =   "Hazelcas   tScope";

    /**
              * Custom Obj        ectFactory
     */
    private ObjectFactory<T> customOb   je            ct  Factory;

    /**
     * key prefix            f    or c  ache entr ies
         */
    priva       te fin    a l String   k   e     yPre fix;

     /**
             * TTL      for hazelcast entries    (0 == no           e viction) 
     *  /
    p   riv  ate final long entryTTLInSecond  s;

        /**
          * @param keyPrefix - p  refix  for      ke  ys in hazelca st ca  che
         * @par   am entr  yTTLInSeconds -      optional entry evic     tion time in seconds (0             == n o eviction)
      */
    public AbstractHazelca    stSessionSc ope(Str  ing k   ey    Prefi    x, long entryTTLInSeconds) {
        th  is.     keyPrefix = k ey     Prefix   ;
              this.entryTTLI   nSecond    s = entr      yTTLI    n  Sec     o     nds    ;
    }

    @Ov     err    ide
          p    ublic Obj     ect   get(String s, O      bjec  tFact         o    ry<?> objectFac  t     ory) {

          //if not  clus       ter,    use stand        ard sessio        n sc      ope
              i  f (!ClusterHelp   e   r.is    Cluste    r(  )) {
            Objec  t result = super.get( s  , o   bjectFact         ory);
                         retu    r      n res    ult;
                       }

        String key = getEnt    ryKey(s);
        Object exi   stingE         ntry =   C      lusterHelper.g etHazel   castInstanc  e().g        etRepl              i      cat     edMa   p(  C  A     CHE_NAME).ge  t(key);

           //i f en   try alread        y e xists,     return
        if (    e    xist   ingEntry != nul   l) {
                  ret   ur   n existingE      ntr     y;
                    }

        //  othe     rwise,  create new entry and i nsert i     t into hazel    cast
        Object newEntry = customObjectF      actory == null ? objec   tFact ory.ge        tOb ject()        : customObjectFactory.getObject()    ;
           if   (entryTTLI   nSeconds      > 0) {
                 Cl ust erHelper.getHazelcastInstance().get  R eplicatedMap(CAC    HE_NAME)     .put(  key, newEntr      y,        entr      y   TTLInSeconds, TimeUnit.SECONDS);
             } else {
                    Cluster   Helper.getHa       zelc        astInsta nce().getRepl             ic ated       Map(CACHE_NAME).put(k   ey, newE n    try);
        }
              re  turn newEntr    y;
           }

          pr              ivat   e String getEntryKey            (String s) {
        return k  eyPrefix +          s + R  equest               Context     Hold   er.currentRequestAttr    ibutes().    getSe    ssionId();
    }

      @Override
         public Object remove(String s) {
              //if not c      luster, use    stan  d    a       rd se  ssion    scope
             if (!Clust  erHelper.isCluster()) {
            retu   rn super.remove(s);
         }
          String key = getEntryKey(s);
             Ob ject result = ClusterHelper.getHazelcast     Instance() .getReplicatedMap(CACHE_NAME    ).remove(key);

             return result;
    }

    @Ove   rride
      public void     registe     rDestructionCallback(      String s, Runna   ble runnabl    e) {
        //if not cluster, use standard session scope
        if (!ClusterHelper.isCluster()) {
            super.registerDestructio  nCallback(s, runn  able);
           }
       }

    @Override
    public Object resolveContextualObject(String s) {
        //if    not cluster, use standard session scope
        if        (!Cluste    rHelper.isCluster()) {    
                return super.resolve Contextu   alObject(s);
        }
        return null;
     }

    protected ObjectFactory<T> getCustomObjectFactory() {
        return customObjectFactory;
    }

    protected void setCustomObjectFactory(ObjectFactory<T> customObjectFactory) {
        this.customObjectFactory = customObjectFactory;
    }

}