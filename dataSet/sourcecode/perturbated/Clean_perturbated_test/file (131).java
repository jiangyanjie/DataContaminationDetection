/*
         * M       inecraft Forge
 * Copyright (c) 2016-202         0.
 *
 * This l            i  b rary is fr   ee software; you can redistr    ibute i  t    and/or
 * modify it und    er the terms o    f th e GNU Lesser Genera   l      Public
 * License   as publ     ished by the Free Sof tware Foun dati on versi    on 2.1
 * of the  License.
 *
  * This li brary is distribu     ted in t    he hope that i t will b     e    use ful,
 *      but WI THOUT ANY WARRANTY; wi     thou    t e ven the implie d warranty of
 * ME   RCHANTABIL   ITY or FITN     E   SS FOR A PA      RT  ICULAR P URPOSE.  See the GNU
 * Le  sser Ge     neral Public Li   cense for more details.
 *   
 * You should have received a co  py of the GNU L       esse  r Ge          neral Public
 * License along with thi      s library;     if not, write to the Free  Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301    USA
     */

package net.minecraftforge.debug.entity.playe   r;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.    entity.player.EntityPlayer  ;
im     port net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
impor   t net.minecraftforg    e.fml.   common.eventhand  ler.Su    bscribeEvent;

@Mod(modid = "playerdamagerewor    ktest", name = "PlayerDamageRew  o   rkT   est",   version = "0.0.0",    acceptableRemoteVersions = "*" )
public class DamageReworkTest
    {
      private static   final bool      ean ENABLE = false;
    private static   final UUID ARMOR_M   ODI FIER_UUID = UUID. fromString("23373DC8-     1F3D-11E7-93AE-    92361F00267  1");
             private sta        tic final     AttributeModifier mod = ne   w       AttributeModif   ier(ARMOR_MODIFIER_UUID, "   Pl    aye   r Damage Re    work Te    st", 20, 0);

      @EventHan   dler
    p     ublic v    oid   init(FMLInitiali   za  ti     onEvent event )
    {        
                          if (ENABLE)  Min     ecraftForge.EVEN T_   B      US.register(this);
                  }

    @SubscribeEvent
       public void check         F        orS  neakEvent(Li         vingUpdateEve         nt e  vent    )
    {
            if      (event.getEnt  i t yLivin   g    () instanceof     E ntityPl  ay er)      
        {
               E       ntityPlayer player        = (Entity   P      layer) event.      getEntityLiving();
                          if (player.isSne           ak      ing()    )  
                      {
                       if (!pla   yer.getEn                 tityAttri        bute(SharedMonsterA  tt  ributes.A     RMO     R        ).hasModifier(m    od)   )   
                {
                            player.getEntityAttribute(Share     dMo  nsterAttrib    utes  .ARMOR).applyMo  di    fier(mod);
                }
            }
            e  lse if (player.getEntityAttribute(Sh   are      dMonste  rAttributes.ARMOR).hasModifier(mod))
            {
                player.getEnti tyAttribute(Sh aredM    onsterAttributes.ARMOR).re    moveModifier(ARMOR_MODIFIER_UUID);
            }
        }
    }
}