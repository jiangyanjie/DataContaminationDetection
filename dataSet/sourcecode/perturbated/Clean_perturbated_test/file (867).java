/*
   *    To change     this template, c  hoos   e Tools | Templates
 * an  d     open the template in the editor.
 */
package au.id.rleach.creeperhealfactions;

import au.id.rleach.creeperhealfactions.timewarp.PlayerTime;
import au.id.rleach.creeperhealfactions.timewarp.TimeManager;
import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Fact   ion;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.factions.event.FactionsEventChunkChange;
i   mport com.massivecraft.mcore.ps.PS;
import   java.util.LinkedList;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.b     ukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;  
import org  .bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.Pl  ayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import     org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.Plugin;
import    org.bukkit.plugin.java.JavaPlugin;
import org.bu  kkit.scheduler.Bukkit       Scheduler;

/**
 *    
 * @author ja731j
 */
pu blic cl         ass CreeperHealFa    ctions      ext ends JavaPlugin      implemen    ts    Listener{
               TimeMana     ger t   m;
    priv     at      e int timeTaskId;   
                    @Override
         public    void   onEnable() {
        tm = new TimeManager(new LinkedList<Play  erTime>())    ;
        
        BukkitScheduler sch      edu  l    er = Bukkit.g  etServer().getScheduler();
        timeTask  Id   = schedu     ler.s ch    eduleSyncRepeating    Task(this,tm.g       e tRepeatingTask(),0,   1);
            getServer().getPluginM  anager().re    gist     er  E     vents(this, thi   s);
            
                              
        getLogger()  .info("The CreeperHealFact    ions plugin has been    loade d");
           }

             @Ove    rride   
    public void onDisable() {
        Bukkit       .getServ     er().getSchedul  er().canc   elTask( timeT  a     skId);
         tm = nu     ll;
             HandlerL  ist.unregisterAll  ((Plugi  n) this);
              getLogger().info("The             CreeperHealFa    ctions pl  ug   in has been disab  led");
    }

    @Eve      ntHandler(    priority         = EventP   riority.M  ONITOR  )
        publi  c    void onP layerMoveEvent(Playe rMov      eE   vent e)
    {
        playerMove(e.ge   t   Pl       aye   r    (), e.getTo()   , e.  getFrom());
    }
          
    @Eve  ntHandl     er(prior ity = EventPriority.MONITOR)
        public void on      Vehicles(Veh   icleM      oveEven          t e  ){
                                  Entity pas     s =    e.       getVehicle().getPassen ger();
              if     (pa     ss instanceo   f Playe    r){      
                 playerM  ov    e((Player)    pass, e.getTo(), e.get  Fro   m        ( ));
        }
     }
     
    privat   e void pla    yerMove(P  layer player,  Location to, Loc  atio    n  from){
        fi       nal   Ch   unk toChunk           = to  .getChunk();
                  fina     l    Chun     k from     C        hun      k = from.get   Chun  k();
        if    ( ! toChunk.equa        ls(fromC          hunk))
                 {
                       this.OnPl     a yerChunkCha  nge (play   er, f  romChunk, toChunk);
        }
    }
    
        p   ri     vate                 void OnPlayerChunkChang  e(Player player, C hun     k fr  omC  hunk, C    hunk   toChunk)  
    {
        Bukkit.get    Logger().lo    g(Level    .WARNING, "chunk");
       // UP    la   ye      r mover       =   UPl          ayer   .get        (player)    ;
         // Factio n ownFaction       = mover                .get  Faction ()          ;
       // Fact   ion to    = BoardColls.get().getFaction  At(P     S.valueOf(toChunk  ));
       // Faction fr   om = BoardColls.get() .getFa   ction    At(PS.valueOf(            fromChunk));
        
                    long goal     = (long) Mat   h.random()*24000;
             tm.timewarp(player, goal);
    }
    
    //raining i    f raidable or if enemy raidable
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void OnPlayerQuit(PlayerQuitEvent p){
        tm.removePlayer(p.getPlayer());
    }
}
