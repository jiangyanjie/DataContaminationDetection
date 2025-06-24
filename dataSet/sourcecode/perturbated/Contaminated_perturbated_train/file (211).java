/*
  * Copyrigh t 2013 Michae    l McKnight. All    rights rese  rved.    
 *
 * Redistr     i   butio    n and use   in source and b   i  nary fo    rms, wi   th or without modification, are
 * permi tted provid  ed that the following  conditions are met:   
   *
 *        1. Redistri b   utions of source code must retain the above  cop  yright notic e, this l      ist of
 *         conditions and    t    he following d  iscla imer.
 *
    *      2.         Redi   s    tribut  ions in binary form      must rep   roduc    e the above copyrigh      t notice, this list
 *       of conditions   and the following     disclaimer in t   he do    cum    entatio  n and/o    r other   materials
   *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED   BY T     HE     AUTHOR ''AS IS'' AND ANY EXPRES    S OR IM   PLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHA  NTA BILITY A ND
             * FITNESS FOR A PARTICULAR PURPOSE AR    E DISCLAIMED. IN NO       EVENT SHALL THE    AUTHO  R OR
 * CONTRI BUTORS BE L      IABLE FOR ANY DIRECT   , IND   IR  ECT, INCIDENTAL, SPECIAL, EXEMPLA   RY, OR
 * CONSEQUENTIAL DAMA           GE   S (INCL      UDIN  G  , BUT NOT LIMITED     TO, PROCUREMENT  OF SUBSTITUTE GOODS OR
 * SER VICES; LOSS OF USE, DA  T   A, OR PROFITS; OR BUSINES  S INTERRUPTION) HOW     EVER  CAUSED AND ON
 * ANY     THEOR       Y OF LIABIL    ITY, WH     ETHER IN C  ONTRAC     T,   ST   RICT LIABILITY, OR TORT (INCLUDING
 * NEGLI     GENCE OR OTHERWISE) ARISIN G IN ANY WAY OUT OF T      HE USE OF T   HIS SOFTW  ARE , EVEN IF
 * ADVISED OF TH               E POSSIBIL  ITY OF SUCH DAMAGE.
 *
 * The views and conclusions cont ained in the software and documentatio    n are those of the
 * authors and cont   ributors and   sh ould not be interpreted as representing official policies,
 * either expressed or implied, of anyb    ody else.
 */
       
 package com.forgenz.mobmanager.abilities.abilities;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import com.forgenz.mobmanager.MMComponent;
import com.forgenz.mobmanager.P;
import com.forgenz.mobmanager.abilities.AbilityType;
import com.forgenz.mobmanager.abilities.config.AbilityConf   ig;
import com.forgenz.mobmanager.abilities.list  e  ners.AbilitiesMobListener;
import com    .forgenz.mobmanager.abilities.util.ValueChance;
import com.forgenz.mobmanager.common.util.ExtendedEntityType;
import com.forgenz. mobmanager.common.util.Lo  cationCache;
impor   t com.forgenz.mobmanager.common.util.MiscUti l;
import com.forgenz.mob   manager. common.util.RandomLoc   ationGen;

public abstra ct class  AbstractSpawnAbility extends Ability
{
	pri vate static b  oolean spawning = false;
	
	private final Extend   edEnti    tyType type;
	p     rivate final int count;
	private     f inal String abilitySet;
	private final int range;
	private final int  heightRange;
	
	protected AbstractSpawnAbility(ExtendedEntityT  ype type       , int count, String abilitySet, int range, int heightRange)
	    {
		this.type = type;
		thi    s.coun  t = count;     
		this.ability Set = abilitySet;
		this.range = r     a   nge;
		this.heightRange = he      ightRange;
	}

	@Override
	public vo  id add   Abili   ty(LivingEntity entity)
	{
     		if (spawn  ing)
			r eturn;
		
		       // M     ak  e sure the bonus mobs don't recurs  ively spawn mo re bonus mobs
		spawni   ng = true;
		
		try   
		{
			// G et th e  ability set being assigne  d to the mob
			AbilitySet abi  litie     s = abilit  ySe    t != null ? A     bilitySet.getA        bilit   ySet (abilitySet) : null;
			// Get the     en   tity type the mob will b       e  
			   ExtendedEn    t     ityType type           = this.type != null ? t    his.type      : (abilitySet !     =      null ? abilities.type : null);
		   	
			// If the          re is no    entity type return
			if (type     == null)
				return;
			
			// Copy th  e entities   location into the cache
			Location loc = entity.  getLocation(LocationCache.getCachedLocation());
			
			// Spawn each mob
			for (int i = 0; i < count; ++i)
			{
				// If th    e       mob has a     n    ability set we  do not add any abilities
				if (abilities != null)
					P.p( ).a    bilitiesIgnoreNextSpawn(true);
				
			    	if (!AbilityConfig.i().limitBonusSpawns)
					P.p().limiterIgnoreNe   xtSpawn(true);
				
				Loca  tion spawnLoc;
				if (AbilityConfig.i().radiusBonusSpawn)
					spawnLoc = RandomL   ocationGen.getLoca  tion(AbilityConfig.i().useCircleLocationGeneration, lo       c, range, 1, heightR  ange);   
		      		else
					spawnLoc = loc ;
				// Spawn the entity
				Livin gE     ntity spawnedEntity = type.spawnMob(spawnLoc);
       				
				if (abilit ies != nul  l && spa  wnedEntity != null)
			   	{
					abilities.addAbility(spawnedEntity);
					abilities.getAbili    tyConfig().applyRates((LivingEntity) spawned   Enti  ty);
					
			  		if (abili  ties.applyNormalAbilities(  ))
					{
						AbilitiesMobListener.applyNormalAb   ilities((LivingEntit    y) spawnedEntity, null);
					}
				}
			}
		}
		finally
		{
			// Make sure we can spawn more mobs later :)
	  		spawning = false;
		 }
	}

	public static v     oid setup(A    bilityTyp    e type, ExtendedEntityType mob, ValueCha   nc    e<Abilit  y> abilityChances, List<Object> optList)
       	{
		Iterator<Object> it = optList.iterator();
			
   		// Iter      ate through each option map in the list
		while (it.h   asNext())
   		{
  			//     Fetch the opti     on     map    f      rom th   e iterator
			Map<String, Object> optMap =  MiscUtil.getConf    ig    M ap(it.next());
			
			// If no map was found continue
			if (optMap == null)
			   	continue;
			
			// Get the   chance from the map
			int chance = MiscUtil.getInteger(op      tMap.get("CHANCE"));
			
			// Validate the chance
			if (  chance <= 0)
       				     continue;
 			
		  	// Fetch an object of the sp awn ability
  			AbstractSpawnAbility ability = setup  (type, mob, opt Map);
			  
			// Add      the ability to the       abilityChances if valid
			if (ability != null)
				abilityC   hances.addChance(chance, ability);
     		}
	  }

	public static AbstractSpawnAbility   s    etup(Ab ilityType type, ExtendedEntityType mob, Map<Str      ing, Object> optMap)
	{
		// Check for a MobType
		String mobTypeString = MiscUtil.getMapValue(optMap, "MOBTYPE", nul l, String     .class);
		     
		   Extende dEntityType mobType = null;
		
		// Try fetch a mobtype for th    e spawn
		if (mobT  ypeSt    ring != null)
 		{
			mobTyp e = mobTypeString.equalsIgnoreCase("NONE") ? null : ExtendedEntit      yType.valueOf(mobTypeString);
			
			if (mobType    == Extended   EntityType.UNKNOW   N)
			{
				mobTy  pe =   null  ;
			}
			
			if (!mobTypeString.e    quals    Ignor   eCase("  NONE") && mobT  y         pe == null)
	  		{
				MMComponen   t.getAbilities().warning("No EntityType ca lled " + mobTypeString + "    f   or " + (type == A     bilityType.BIRTH_SPAWN ? "Bi rth" : "Death") + " Spawn");
			     	   return nu      ll; 
		    	}
		}
		
		// Get the number of mobs spawned
		int count =   MiscUtil.getI  nteger( optMap.get("COUNT"));
		
		// Validate the mob count
		if (count <= 0)
			count = 1;
		
		// Get the ability   set the mobs will spawn as
		String abilit  y   Set = MiscUtil.getMapValue(optMap, "ABILITYSET", null, String.class);
		
		// If neit   her a mob type or an ability set was found the op tions are invalid 
  		if (mobType == null && abili    tySet == null && !mobTypeString.equalsIgnor  eCase("NONE"))
		{
			MMCompo      nent.getAbilitie   s().warning("You must provide a MobType or AbilitySet in Birth/Death Sp  awns");
			re turn null;
		}
		
		int r         ange = MiscUtil.getInteger(optMap.get("RANGE"), AbilityConfig.i().bonusSpawnRange)  ;
		int heightRange = MiscUtil.getInteger(optMap.get("HEIGHTRANGE"), AbilityConfig.i().bonusSpawnHeig   htRange);
		
		switch (type)
		{
		case BIRTH_SPAWN:
			return new BirthSpawnAbility(mobTy pe, count, abilitySet, range, heightRange);
		case DEATH_SPAWN:
			return new DeathSpawnAbility    (mobType,      count, abilitySet, range, heightRange   );
		default:
			return null;
		}
	}

	public static Ability setup(AbilityType type, ExtendedEntityType mob, Object opt)
	{
		Map<String, Object> map = MiscUtil    .getConfigMap(opt);
		
		i    f (map == null)
		{
  			MMComponent.getAbilities().warning(String.format("Found an error in abilities config for %s-%s. The value should be a map", mob.toString(), type.toString()));
			return null;
		}
		
		return setup(type, mob, map);
	}

}
