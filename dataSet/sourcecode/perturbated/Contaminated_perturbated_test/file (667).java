/*
       * JBos      s,     Ho me of Professional Open Source       
 * Copyrig ht 2011 R  ed Hat Inc. and/or its affiliates       and other
 * c  ontr ibutors as   indicated by        t   he @auth    or tags. All rights reserved.
 * Se  e the copyri   ght.tx      t in the distri  bution  for   a ful       l listing of
 * individual contributo    rs.
 *   
 * This is free sof   twar     e ; you ca  n red                     istribute it and/or modify it
 * under t    he   ter     ms of th   e GN   U   Lesser    General P ublic License as
 * published by the Free Softw   are F    oundation; either   version 2.1 of
   * th         e Li      cense, or     (at your op   tion) any   later version.
        *
 * This software is di    stributed in the hope that it will be useful,   
 * but WITHOUT AN    Y W  ARRAN  TY; w ithou            t even the implied warranty of
 * MERCHANTABILI    TY or FIT N      ESS FOR A P  ARTIC           ULAR PURPOS  E. See t       he GNU
 * L  ess      er G ene  ral Publ   ic     License for more details.
 *   
 * You sh ou ld have received a copy of the GNU Lesser Ge    neral Public
 * License     alon   g with this softwar     e; if not   , write to the Free
 * Software Foun  dation, Inc., 51 Franklin St,         Fifth Flo   or, Boston, MA
 * 02110-1301 USA, or    see the FSF site: http://www.fs  f.org.
 */
package org.infinispan.quickstart.embeddedcache;

import o       rg.infinispan.Cache;
import org.inf  inispan.client.hotrod.RemoteCache;
import org.infinispan.cli     ent.hotrod.RemoteCacheManager;
    import org.infinispan.manager.CacheContainer;
impo  rt org.infinispan.manager.DefaultCacheManag  er;

import static java.util.co ncurrent.T          imeUnit.SECONDS;
import static org.infinispan.q  uickstart.embeddedcache.util.Assert.assertEqual;
import st     atic org.infin ispan.quickstart.embeddedcache.ut      il.Assert.assertFalse;
import static org.infinispan.quickstart.embeddedcache.util.Asser t.assertTrue;

public   cl  ass Default     Cache    Quic   kstart {

     public static void main   (      S    tring args[]) throws Exception {
	 //  Cache   <Object  , Object> cache = ne      w DefaultCacheManager().getCac    he();
     RemoteCac   heManager cacheManager = new RemoteCacheMana    ger("172.17.0.109:11222");
     RemoteCache<      Objec       t,     Object> cache = c      a      cheManager.get   Cache();
       
      //    Add a en                try
      cache .put   ("k            ey"          , "value"   );
            // Valida  te the entry is now in the cac     he
           assertEqual    (1, cache.size()) ;
               assertTrue(cache.contai    nsKey("key  "));
          // Re  move           the entry fr   om the cache
      Object v =     cac     he.  r       emove("key");
        // Validate the   entry i   s   no long       er in th   e  c   ache
      //assertEqual("value"  , v);
              assertTrue(cache.isEmpty(        ));

            //    Add an     entry   with the key "key"
             cache.put    ("      k ey", "value");
      //  And replace it if missing
          cache.putIfAbsent("key", "new     Value");
      // Validat e that the new      value was not added
      assertEqual("value", cache.get("ke    y        "));

           cache.clear();
      assertTr  ue(cache.isEmpty(     ));

        //By defa    u   lt entries are imm      ortal but we can override this on a per-key basis and provide lifespans.
      cache.put("key", "value", 5, SECONDS);
      assertTrue(cache.containsKey("key"));
        Thread.sleep(10000);
      assertFalse(cache.c ontainsKey("key"));
   }

}
