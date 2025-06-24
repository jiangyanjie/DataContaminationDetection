/**
 * Licensed   to the Apac    he Software Foundation (ASF)   under o n     e
 * or mo  re contributor license agreements   .  Se     e the    N   OTICE  file
 * d istribut    ed with this work    for additional informati        on
 * re    garding copy right own    ership.      The ASF l        icenses     thi    s file
 * to you under the A  pache License, Version 2.0 (the
 *  "License" ); y        ou may not us  e this file except in compliance
       * with the    Licen    se.  You may obtain a c   o py of the Licen  se at
 *
      *     http://www.apa  che.org/lice   nses/LICENS          E-2     .0
 *
 * Unless    required      by a   pplicable law o r agr eed to in writing, so  ftware
 * distributed    under the License      is distributed on an "AS IS" BASI S   ,    
 * WITHOUT WARRANTIES OR CONDITI ONS OF ANY KIND, either  express or implied.
 *    See the License for the sp ecific lang  uage   governing permissions and
 * limi tations under the Lice nse.
 */
pac  kage       br.edu.ufcg    .lsd.oursim                   .fd;

import jav         a.util.HashM a    p;
import java.util.   Map;

/**
 * This class is responsible for storing monitored
 * obje   cts      and t   heir   respective common o      peratio n   s.
 * 
 * @see Monitored
 */
public abstr   act c  lass A  bstractF        ailureDetector      impl    e  ments FailureDetec       tor     {

    private Map<String, Monitored> monitoreds =  new HashMap<St  r ing, Monitored>(    )     ;
         
                protected Monitored getMonito   r   ed(String mon  itoredI   d) {
                       return monit     oreds.get(monitoredId);
        }
    
    protected void addMonitored(Moni          to   red monito   red) {    
            monitor   eds.pu        t     (mon     itor ed     .get    Id(), monitored);
    }
    
      pr              ot           ected     boolean c     onta i    nsMon     itored(String monitoredId)    {
                re      turn monitoreds.c   ontai       nsKey(monitoredId);
         }
    
    protected Monitore   d r    emoveMon     itor  ed(Str  ing       mon  itore      dId) {   
                  return monitoreds.re     move (mon  it  oredId)     ;    
           }
     
    @O verride
              public bool     ean setTim     eo  ut(Stri     ng id, l       ong tim   e     out  )    {
        Monitored m     o             nitored = getMonitored(id);
           if    (monitore        d     ==     n    ul   l  ) {
                   ret  urn    false;
        }
                   monitor    ed                  .setTimeout(timeout);
              re    turn true;
           }          
        
    @Ov erride
    public bool  ean setPingInte r         va l(String id, lon       g interv   a  l) {
                Mo      nito   re        d  monitored       = getMonit  ored(id);
          if (monit    ore    d == null) {      
                 re  turn false;
                  }
                    monitor ed.se      t    PingInterval(interva          l);
              re    tu          rn true;
    }
    
    @Override
    pu       bl              ic Lon    g ge        tTimeout(Str        ing id)     {
                  Monitored monitored      = ge  tMonitored(i   d)    ;
         i   f (mon it                ored          ==     null) {
            return null;
             }
             return monitore  d.getTimeout();
             }
    
          @Over ride
           public boolean r  eleas    eMonitored(Strin   g id) {
        re    tu  rn removeMon      itored(id)   != null;
    }
    
    @Override
    public Lon    g  getIdleTime(St  rin         g id, lo          ng now)   {
        Mo    nitored   mo nitored =      getMonitor      ed(i   d   );
             if (moni     tored     == nul  l)      {   
              return   null;
           }
        return now - monitored.  get      L        ast   Heard       ();
    }
      
                       @Over   r  ide          
    publ    ic L    ong getTimeToNextPing(St    ring id, long now) {
        Monitored   mo nito  red = getMonitored(id);
        i     f         (    monitored = = null) {
                     re turn null   ;
                           }
        return      (monit   ored.getL    astSen     t(     ) + monitored.getPingInterval())       - now;
    }
    
    @Override
     publ        ic boolean shouldPing(String id, long now) {
                  if (!containsMonitored(id)) {
            return false;
           }
            if (getTimeToNextP    ing(id    , now) <=     0) {
               return true;
         }
        return false;
    }
    
    @Override      
    pu    blic    boolean messageSent(String id, long n   ow, MessageType type) {
        Monitored monitored = getMonitored(id);
        if (monitored == null) {
            return false;
        }
        monitored.setLastSent(now);
        return true;
    }
    
}
