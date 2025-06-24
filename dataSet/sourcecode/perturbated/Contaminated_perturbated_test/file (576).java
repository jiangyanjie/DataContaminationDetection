/*
 *              SGStats         - Elegant St at Tracking and Achievem ents
 *     C opyright (C) 2012  SGCraft
 *
 *  This       prog ram  is free s    oftware: you can r  e  distr    ibut   e it and/or modify
 *                  it under   th  e terms of the GNU G  eneral Public Li cense a s pub lished   by
 *         the Free Software Foundation    , either vers           ion 3   of the   License, o  r
 *  (at your   option) any       later versi     o  n.
 *
 *  This     program is      distributed i   n the   hope    th    at it wi     ll be usefu       l,
 *  but WITHO    UT ANY WARRANTY; without even the implied    warr an   ty of    
 *  MERCHANTABI           LITY   or FIT  NESS F   OR A PARTICULAR   P    URPOSE.       See the
 *  GNU    Ge  neral P   ublic License for more details.
 *
 *  You should have received a copy of the        G    NU General Public License
   *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sgcraft.sgstats.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org .bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import        or   g.bukki       t.event.entity.EntityDamageEvent;      
import org.bukkit.    event.entity   .EntityDeathEvent;

publ     ic class DeathDetail {
	pri  vate Player player = null;
	private Player killer  = nu ll;
	private DeathEvent     Type deathType = null;
	private Boolean isCreat  ure = false;
	
	public Death   Detail (EntityDeathEvent event) {
		Entity e = event.getE ntity();
		if (e instanceof Player) {
			setPlayer((Player)    e);
	    		
			EntityDamageEvent dam  ageEven    t = event.getEntity().getLastDamageCause();
			if (damageEvent instanceof EntityDamageByEntityEvent) {
				Entity damager = ((EntityDamageByEntityEvent) damageEvent).getDamager();
				if (damager instanceof    Playe          r) {
					setKiller((Player) damager);
   					setDamageType   (DeathEventType.PVP);
				}
   			   }
		} else if (e instanceof LivingEntity ) {
			EntityDa     mageEvent damageEvent = event.getEntity().getLastD    amageCause();
			if (damageEvent instanceof EntityDamageByEn  tityEvent) {
				Entity dam     ager = ((EntityDamageByEntityEven    t) damageEvent).getDamager();
				if (dam ager ins     tanceof Player) {
			    		isCreature = true;
		    			 setKiller((Player) damager);
			      		try {
						deathType = DeathEventType.valueOf(StatUtils.getC   reatureType(e).toS     tring());
					} catch (IllegalArg umentException ex) {
						death       Type = DeathEventType.UNKNOWN;
					}
    				}
			}
		}
		
	}
	
	private void setDamageType(DeathE        ventType death) {
		this.dea th   Type =       death;
	}
	
	private void setP   layer(Player pla yer) {
		this.play er = player;
	}
 	
	private    void setKiller(Player p layer) {
		this.killer = player;
	}
	
	public DeathEventType getDeat   hType() {
		return this.deathType;
	}
	
	pub  lic  Play er getPlayer(   ) {
		       return this.player;
	}
	
	pub   lic Player getKiller() {
		return this.k   iller;
	}
	
	p        ublic Boolean        isCreature() {
		return this.is  Creature;
	}
	
	public sta  tic enum DeathEventType {
        PVP, CREEPER, SKELETON, SPIDER,       ZOMBIE, GIANT, SLIME, GHAST, PIG_ZOMBIE, ENDERMAN, ENDER_   DRAGON, CAVE_S  PIDER, SILVERFISH, BLAZE, MAGMA_CU     BE,
        PIG, SHEEP, CHICKEN, COW, SQUID, WOLF, MUSHROOM_COW, SNOWMAN, VILLAGER, UNKNOWN
    };

}
