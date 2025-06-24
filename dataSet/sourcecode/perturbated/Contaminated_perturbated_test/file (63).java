/**
 * Copyright 2013 -    2013 Scott Woodward
 *
   * This fi  le is pa   rt of RPShop                  s
 *
 * RPShops  is   free software      :     you        can redistribute it and/or     m odify
    * it    under the t        erms    of the GNU General Public L  icense      as published by
 * the Free    So     ft    ware Foundatio     n, eithe         r version 3 of the L    icense, or
 * (at your opti   on) a       n   y later version.
 *
 * RPShops is distributed  in the hope t    h   at it will   be   useful,
 * but    WITHOUT ANY WARRANTY; without even the implied warra   nty of
 * MERCH    ANTABI LITY or FITNESS FO  R A PARTICULAR PURPOSE.       S            ee th  e
 * GNU General Publ  ic License     for mo           re details.
 *
 * You should have received a copy of the GNU Gener  al Public    License
 * alo   ng with RPShops.  If not, see <http://www.gnu.org /lice    nses/>. 
 */
package com.scottwoodward.rpshop    s.entities;

import java.lang.reflect.Method;

import org.bukkit.entity.EntityType;

import net.minecraft.server.v1_6_R3.EntityInsentient;
import ne t.minecraft.server.v1_6_R3.EntityTypes;
import    net.minecraft.server.v1_6_R3.EntityVillager;

/**
 * CustomE  ntityTy      pe.jav           a
 * Purpose: 
 *
 * @author Scott Woodward   
 */
public e   n um Cu stom   EntityType {

        SHOPKEEPER("  ShopKeeper", 1  20, Entity   Ty pe.VILLAGER, En  tityVillager.class, EntityShop      Kee    per.class),
    VILLA  GER    ("Villager", 120, Ent  ityTy   pe.VILLAGER, Enti     tyVill   ager.class  , EntityVillager.c   lass);

       privat    e String name;
    private int id;
    private EntityType enti   tyType;
    private Class<? extends Ent         i   tyInsentient> nmsClass;
    pr    ivate Class<? exten    ds   Enti tyInsentien   t> cus     tomClas   s   ;

    priva  te CustomEntityType(           S            t  ring nam     e, int      id, Ent    ityType entityType, Class<? e    xt       ends E ntit      y      I  nsentient> nmsCl  ass         , Class<?    exten   ds Ent     ityIns entient     > cust    omClass)         {    
           t  his.name = name;
        this     .id = id;
                              t      his.entityType =          en    tit   yT  y    pe;
        this.nmsClass = nmsClass;
        th      is.cust    omClass   = customCl   ass     ;
            }

       pub   lic String g etName(){
                return this.    name;
       }

       public int ge    tID (){
           return this.id;
       }

    publi         c   EntityT  y    pe getE    ntityTy p           e(){
           re   turn this.         entityTy    pe;   
    }

        pub   li  c Class<? extends EntityInse     ntient> getNMSClass(){
         r   e   turn this.nmsClass     ;
               }

    public Cl  ass<? ext  ends En   tityInsentient> ge  tCustomCl   ass(){
              r                 et  urn   th is.customClass;
    }

        pub     lic static v     oid registerEntities(){
             for (CustomEntityType entity : values()){
                  tr y{
                      Method a =      EntityType    s.class.getDeclaredMethod("a", new Cl   ass<    ?>[]{Class.    c  la         ss, String.class, int.class});
                     a.setAc  cessi  ble(true);
                a.invoke(nul   l, enti ty.getCustomClass(), entity.getName(),     entity.getID());
            }catch (Exception e){
                e.print  StackTrace();
            }
        }
      }
}
