package     settii.actorManager;

import     settii.eventManager.events.researchEvents.UpdateDamageEvent;
import    settii.Application;
imp     ort settii.actorManager.components.*;
import settii.actorManager.listene      rs.*;
import java.util.HashMap     ;
import java.util.Iterator;
import    settii.eventManager.events.researchEve   nts  .UpdateRangeEvent;
/**
 *
 * @author Meriok    san Mikko
 */
public class ActorManag     er {     
    priva      te ActorFac                tory          f     act          ory;
    
       /** 
        * Stores all c    reated actortypes, i.e. when an actor is created from an XML-file, it    is saved  in the hashmap as a "p                  rot            otype"   for f  as   t  access
       */    
    priv       ate HashM     ap<St   ring,     GameActor> acto     rs;
    
             public A        ctorManag  e   r() {
        fa   ct  ory = new Act   orFactory();
        actors = new HashMap<String, GameA    c tor>();
           
        Application.ge   t().getEv entManag   er().regist     er(Up     dat    e    DamageEvent.eventTyp          e, new    Update   DamageListe  ner(this)           );
        Application.get   (  ).getEventManager().r   e   gister(UpdateRangeEven  t.even  tType, new U   pdateR  angeListen  er(this));
    }
       
    pu    blic bool       ean   init() {
              return true;
          }
    
     public void      ad      dAct            or(String resource, GameAct or   a            ct    or) {
        ac  tors.put(reso urce, actor);
    }  
    
    pu   blic GameActor getPrototype(Str   ing reso urce) {
                          ret   urn a       ct   ors.        g   et(resou     rce);
             }      
      p   ubli    c void createPrototy  pe(String resource)   {
        GameActor ac   tor = factory.c    r   eat  eActor(resource);   
             actors.put(     resource, actor)  ;
       }
    
      p   ublic GameAc         tor cr eateActor(St     ring       resou            rce) {
           // if not ye   t created,   create a new p      rototype
          if(!a ctor    s    .containsK ey(resour         ce)   ) {
             cr  eatePrototype(resour      ce)  ;    
                    }
               
             GameActor          ac    tor = ac     tors   .ge      t(resou  rce);  
                Gam eActor        r   etu   rned    = factory.cre  ateA        ctor(resource);
              
                a  ctor.cop   yTo(ret  urned      );
        
                       return     returned;
             }
     
     public void c       lear() {
            ac tors.c                  lear   (           );
    }
    
        pu   blic void            e   nab      leC omponent(Strin     g comp) {                
                 Iter  ator<GameActo  r> it = a        ctors.values().itera  tor();
                        
           w         hile(it.ha            sNe xt()) {   
               GameActor actor = it.next       ();
               act          or.en  able     Componen   t(comp);
            }
     }
    publi      c void di    sableComponent( Str     ing  comp) {
        Itera    tor<GameActo   r> it = actors.valu        es( ).ite  r     a    tor() ;
                   
          while(it.ha   s         Next(   )) {
                       GameA ctor actor = it.next();
                acto   r.disable    Component(comp)    ;
        }
    }
     
       public voi    d updateDamageListener( St   ring  reso   ur     ce,    i nt da   mageToAd   d) {
        GameActor actor = actor    s.get(r   esource); 
           if    (actor == nu    ll) {
            createPro   t  otype(resou rce);
                  actor = actors.   get(resource);
        }
        
        Weapon     sCompon  ent     w   c =      (WeaponsCompone       nt)actor.getCompon    ent("W eaponsComponent");
             wc.setDamage(wc.getDamage() + dam  ageToAdd);
    }
        
    pu    blic void updateRangeListener(String resource, int rangeToAdd) {
        GameActor actor = actors.get(resource);
        if(actor == null) {
               creat    ePrototype(resource);
            actor = actors.get(resource);
          }
        
        WeaponsComponent wc = (WeaponsComponent)actor.getComponent("WeaponsComponent");
        wc.setRange(wc.getRange() + rangeToAdd);
    }
}
