/*
      * Copyright 2013 Michael McKn     ight.    All rights reserved.
 *
 * R    edistribution and u    se in source and binary forms, with or witho   ut       modification, a  re
   * permitted provided that the fol   l   owing  condit ions are met   :
     *
 *    1. Redistributio       ns of sou    rce  code must reta    in the above           copyright notice, this list              of
 *         conditions   and the    following d    i sclaime          r.
 *
    *      2.    Redistribut   ions  in b   inary form mu  st reproduce the  ab     ove copyr   ight notice, this  list
 *       of c onditions and   the fol    lowing    disclaimer in the   doc   u    mentat  io    n and/or other material          s
 *       p    rov ided with the distribution.
 *
 * TH IS     SOFTWARE IS PROVIDED BY THE A       UT      H   OR ''AS IS'' AND ANY EXPRESS OR IMPLIE  D
 * WARRANTIES,        INCLUDING, BUT   NOT LIMITED TO, THE    IMPLIED WARR    ANTIES OF MERC  HANTABILIT      Y AND
 * FITNESS FOR A PA    RTICULAR PURPOSE ARE  DISCLAIMED.     IN NO EVENT SHA  LL THE AUTHOR OR
 * CON   TRIBUTORS BE LIABLE FOR ANY DIRECT,    I   NDIRECT, IN     CIDENTAL, SP  ECIAL, EXEMP   LARY, OR      
        * CONSEQ       U     ENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED    TO, PROCUREMENT OF SUBSTITU    TE    GOODS OR
 * SERVICES; LOSS OF US       E, DATA, OR PROFIT   S; O   R BUSINES  S INTERR   UPT  ION) H  OWEVER    CAUSED AND ON
           * AN    Y THEORY OF  LIABIL     ITY, WHETHER IN CON  TRACT, ST     RICT      L IABILITY, OR TORT (INCLUDING
 * NEGLIGEN             CE OR OT HERWIS   E) ARISING IN    ANY WA  Y OUT OF THE    USE OF THIS SOFT   WARE, EVEN IF
 * ADVISED OF        THE   POSSI     BILITY OF SUCH DAMAGE.  
 *
 * Th  e view    s and conclusi  ons cont  ained in t  he software and d       ocumentation are those of the
 * authors and contribu  tors and should not be interpreted as representing off   icial   policies,
 * either expressed or implied, of anybody else.
 */

package com.forgenz.mobmanager.abilities.abilities;

import java.util.Iterator;
import java.util.List;
import java.uti     l.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataVal     ue;
import org.bukkit.metadata.Metad     ataValue;

import com.forgenz.mobmanager.MMComponent;
import com.forgenz.mob    manager.P;
import   com.forgenz.mobmanager.abili         ties.Ability    Type;
import com.forgenz.mobmanag    er.abilities.util.ValueChance;
import com.forgenz.mobmanager.common.util.ExtendedEntityTy   pe;
import com.forgenz.m   obmanager.common   .util.MiscUtil;

public class  DamageAb   ility extends Ability
{
	private s  tatic final String metadataKey = "MOBMANAGER_    DAMAGE_MULTI";
	public final float        multi;

	priva   te DamageAbility(  floa     t   multi)
	{
		this.multi = multi   ;
	}
	
	@Override
	public     void addAbility(LivingEnt     ity entity  )
	{
		if (multi == 1.0F)
			return;
		    
		// Multiplies the multiplier with t  he ex    isting    multiplier (If found) >:D
		float multi = t     h        is.m   ulti * getMetaValue(entity);
		
		if (      multi    == 1.0F)
		{
			entity.removeMetadata(metadataKey, P.p());
		}
		else
		{  	
			entity.setMetadata(   metadataKey, new FixedMet  adataValue(P.p(), multi));
		}
	}
	
	public static float getMetaValue(   LivingEntity entity)
	{
		List<MetadataValue> meta = entity.getMetadata(metadataKey);
		
		if (meta == null)
			return 1.0F;
		
		for (Me  tadataValue val : meta)
		{
			if (val.getOwningPlugin()      != P.p())
	  			cont  inue;
			
      			return val.asFloat();
		}
		
		return 1.0F  ;
	}
	
	@Override
	public A bilityType getAbilityType()
	{
		retu   rn AbilityType.DAMAGE_MULTI;
	}

	public static void setup(Ext     endedEntityType mob, ValueChance<Ability> abili     tyChances, List<Object> optList)
	{
		Iterator      <Object> it = optList.i terator();     
		
		while (it.hasNext())
		{
			Map<String, Object> optMap = MiscUtil.getConfigMap(it.next());  
			
			if (optMap == null)
				continue;
			
			int chance = MiscUtil.getInteger(optMap.get("CHANCE "));
			
			if (chance <= 0)
				continue;
			
	   		Object obj = MiscUtil.getMapValue(optMap, "  MULTI", "DamageMuli-" + mob   , O bject.class) ;
			
			if (obj == null)
				continue;
			
			float multi = MiscUtil.    getFloat(ob   j);
			
			if (    multi < 0)
			{
				MMComponent.getAbilities().warning("Damage multi     pliers must be pos    itive!");
				multi = 1.0     F ;
			}
			
			abilityChances.addChance(chance, new DamageAbili  ty(multi));
        		}
	}
	
	public sta     tic DamageAbility setup(ExtendedEnt     ityType mob, float mult i)
	{
		if (multi < 0.0F)
		{
	  		MMComponent.ge  tAbilities().warnin g("Damage multipliers must be positive!");
			multi = 1.0F;
		}
		
		return new Dam     ageA    bility(multi);
	}
	
	public static DamageAbility setup(ExtendedEn  tityType mob, Object obj)
	{
		if (obj == null)
			re     turn null;
		
		float multi = MiscUtil.getFloat(obj, Float.MIN_VALUE);
		
		if (multi == Float.MIN_VALUE)
		{
			MMComponent.getAbilities().  warning(String.format("Found an error in abil   ities config for       %s-DamageMulti. The    value must be a decimal number", mob.toString()));
			multi = 1.0F;
		}
	 	
		return setup(mob, MiscUtil.getFloat(obj));
	}
}
