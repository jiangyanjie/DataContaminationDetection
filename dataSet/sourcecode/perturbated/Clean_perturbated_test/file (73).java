/*
    *     Minecraft Forge
 * Copyright (c) 2016-20 20.
      *
       *    This     library is free           software    ; you can r   edistribute       it and/ or
 * modify it under th  e terms of the GNU Lesser General Public
 * Licens           e as published by the Free Softwar    e Foundation v     ersi  o   n 2.1
      * of t    he License.
 *
 * This library is distributed in t  he hope that it   will be us eful,
 * but WITHOUT ANY W  ARRANTY; without even th   e implied warranty        o    f
 * MERCHAN TAB    I   LITY or      F     ITNESS FOR A PARTICULAR    PURPOSE.        See the GNU
 *         Lesser General Public Licens  e for more details.
 *
 * You shou   ld have rec    eived a copy of the GNU Less    er Ge   neral Public    
     * License along with this library;               if       not, write   to the Free Sof   tware
 * Foundatio  n, Inc., 51 Franklin Street,       Fifth Floor, Bosto   n, MA  02110-1301  USA
 */
    
package net.minecraftforge.debug.entity.living;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Enti  tySpawnPlacementRegistry;
import net.minecraft.entity.monster.EntityStray;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml .common.event.FML  PreInitializationEven   t;

@Mod(modid = CustomSpa  wnPlace   mentTest.MOD_ID, name =     "Custom SpawnPlacementT   ype te   st mod", version     = "1.0", acceptableRemot      eVersions = "*")
public class Cus   tomSpawnPlacementT  est
{  
    s      tatic final String MOD_ID  = "custom_spawn_placement_test";
    static final boolean ENABL    ED    = false;
    static final     Enti      tyLiv   ing .SpawnPlacement    Type CUSTOM    =    EnumHelper.     addSpawnPlacem   entType("CUSTOM", (world, pos) -> world.getBlock        State(pos.down                  ()).getMaterial()          == Material.ICE    )  ;

     @Mod.EventHandl   er
    p ub  li                c void preInit(   FMLPr           e I      nitiali   zationEvent even      t)       
       {
        if (ENABLED)
          {
              /      / needs   edit to EntitySpawnPlacementRegistr   y to work
            EntitySpawnPlacementRegistry.set    PlacementType(EntityStray.class, CUSTOM);
        }
    }
}
