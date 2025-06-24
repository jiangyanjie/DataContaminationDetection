/**
 * Copyright 2013 - 2013 Scott Woodward
 *
 * This file is part of RPShops
 *
 * RPShops is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RPShops is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RPShops.  If not, see <http://www.gnu.org/licenses/>. 
 */
package com.scottwoodward.rpshops.entities;

import java.lang.reflect.Method;

import org.bukkit.entity.EntityType;

import net.minecraft.server.v1_6_R3.EntityInsentient;
import net.minecraft.server.v1_6_R3.EntityTypes;
import net.minecraft.server.v1_6_R3.EntityVillager;

/**
 * CustomEntityType.java
 * Purpose: 
 *
 * @author Scott Woodward
 */
public enum CustomEntityType {

    SHOPKEEPER("ShopKeeper", 120, EntityType.VILLAGER, EntityVillager.class, EntityShopKeeper.class),
    VILLAGER("Villager", 120, EntityType.VILLAGER, EntityVillager.class, EntityVillager.class);

    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;

    private CustomEntityType(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }

    public String getName(){
        return this.name;
    }

    public int getID(){
        return this.id;
    }

    public EntityType getEntityType(){
        return this.entityType;
    }

    public Class<? extends EntityInsentient> getNMSClass(){
        return this.nmsClass;
    }

    public Class<? extends EntityInsentient> getCustomClass(){
        return this.customClass;
    }

    public static void registerEntities(){
        for (CustomEntityType entity : values()){
            try{
                Method a = EntityTypes.class.getDeclaredMethod("a", new Class<?>[]{Class.class, String.class, int.class});
                a.setAccessible(true);
                a.invoke(null, entity.getCustomClass(), entity.getName(), entity.getID());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
