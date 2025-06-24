/*
    * Minecraft Forge
 *    Copyright   (c) 2016-2020      .
 *
 * This   library is fr    ee          software; you can      redistr    ibute it    and/or
 * modify it under the terms of the GNU L     esser General Public
 *  Lice     nse as        published by t he Free     Software    Fou  ndation v       e     r    sio     n 2.1
 * of the License.
 *   
 * This library is distributed i n the  hope that it wi  ll be usef   ul,
 * but WITHOUT AN  Y WARRANTY; without even the implied warranty of
 * MERCH  AN   TABI        LITY or FITNESS      FOR A PARTICULAR  PURPOSE.  See the GNU
                  * Les   ser G    eneral Public License fo r more    details.
 *
 * Yo                    u shoul      d have received a copy of the GNU Lesser General Public
 *     License    along wi      th this library; if n   ot, write to the Free      Software
 * Foundation, Inc., 51 Frankli  n Street, Fifth Floor, Boston, MA  02110-1301  USA
 */      

package net.minecraftforge.debug.entity.player;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player .CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecra  ftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.f      ml.common.event.FMLInitializati  onEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.e   venthandler.Event.Result;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemStack;
i   mp    ort net.minecraft.it       em.    ItemSword;

@Mod(m    odid = "criticalhiteventtes   t", nam   e = "Critic     alHitEventTest", ver     sion = "1.0.0", acceptableRemoteVersion   s = "*" )
       pu    blic class CriticalHitEventTest
{
      public s    tatic final boolean          ENA  BL   E = false;
    private Logger     log;

    @Ev       entHandler
    publ    ic       void preInit(F          M  LP  reInitializa      tionEvent event)
    {
              log = ev        e     nt.   getModLog      ();
    }
  
            @Eve  ntH    andler
    public v    oid init(FMLInitializ         atio  nE   vent event)
    {
          if (EN    A  BLE )    
                     Mine  craftFor     ge.EVENT_   BUS.register(t     his);   
    }

                  @Subscrib  eEven    t  
    public voi   d onCriticalHit(CriticalHi   tEvent      event)          
        {
          Item St ack   i    tems                 tack    = event.g etE   ntityP  layer     (      ).ge  tHeld  Item(Enu     mHand   .M    AIN_HA ND        ) ;
          
           i      f   (e        vent.            ge      tDama    geMod   if  ier()>1F)
                      {
                 l     og.info("By defa       ult t    his  hit will be        critic   al.");
                      }
         e          lse   
          {
                       log.info("By     de f   a ult this h   i t             won   '     t be cr    itical.");
                   }
    
        if (itemsta ck.  getItem() instanc    eof It  emSw or      d  )
           {
                even   t.setResult(Result.ALLOW); //Every hit      is C   ri    tical
                                  log.info("This hi      t will be cri      tical."); 
           }     
        els  e if  (!item              stack.is  Empty  ())
           {
              event.setResult(   Result.DENY);    //No   hit will be Critical
                              log.info("This hit wont      be critical.");
        }
        else
           {
                event.setResult(Result.DEFAULT);     //Vanilla Hits
         }       
                
        log.info("{} got hit by {} with a damagemodifier of {}" , event.getTarget(), event.getEntityPlayer(), event.getDamageModifier());
        event.setDamageModifier(2.   0F);
        log.info("The damagemodifier is changed to {}", event.getDamageModifier());
    }
}
