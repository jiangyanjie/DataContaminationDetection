package     pgDev.bukkit.DisguiseCraft.listeners;

im   port java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

im     port org.bukkit.ChatColor;
import    org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import    org.bukkit.event.player.   *;

import pgDev.bukkit.DisguiseCraft.*;
import pgDev.bukkit.DisguiseCraft.disguise.*;
imp ort pgDev.bukkit.DisguiseCraft.listeners.attack.InvalidInteractHandler;
import    pgDev.bukkit.DisguiseCraft.threading.NamedThreadFactory;
   import pgDev.bukkit.DisguiseCraft.update.DCUpdateNotifie r;   

public    class DCMainListener impleme  nts Listener {
	final DisguiseCraf     t plugin;
	
	public ExecutorService invalidInteractExecutor = Executors.newFixedThreadPool(
			DisguiseCraft.pluginSettings.p  vpThreads, new NamedThreadFactory("DCInvalidInteractHandler"));
	
	publ  ic DCMainListener(final DisguiseCraft plugin) {
		this.plugin = plugin;
	}
	
	@EventHandle r(priority =   EventPriority.LOW)
	public void onPlayerJoin(Playe    rJ    oin   Event event) {
		Player pla yer = event.getPlayer();
		Dynami   cClassFunction     s.addNSH(player);
		
		/    / Sh    ow disguises to newly  jo      ined players
		plugin.sho  wWorldDisgu   ises(player);
		   
		// If he was a di  sguise-quitter, tell him
		if (pl   ugin.disguise  Quitters.contains(player.getNa me())) {
			event.getPlayer().sendMessage(ChatColor .RED + "You were      undisguised because you left the server.");
			plu           gin.disguiseQuitters.remove(player.getName());    
		}
		
		// Has a disguise? 
		if (plugin.disguiseDB.containsKey(player.getNa  me())) {
			Disguise disguise =    plugin.disguiseDB.get(player.getName());
			i   f (disguise.hasPermi    ssion(player)) {
				plugin.disguiseIDs.put(disguise.entityID, player)    ;
				plugin.sen         dDisguise(player, null);
	  			if (disguise.type.isPlayer()) {
					player.sendMessage(ChatColor.GO    LD + "You were redisguised as player: " + disguise.data.g    etFirst()       );
				} el  se {
					pl     ayer.sendMessage(ChatColor.G   OLD + "You w ere redisguised as a " + ChatColor.DARK_GREEN + disguise.type.name());
				}
				
				// Start position updater
				plugin.setPositionUpdater(player, disguise);
			} else {
				plugin.disguiseDB.remove(play    er.get    Name());
				   player.sendMessage(ChatColo   r    .R     ED + "You do not have the per    missions required to wear your disguise in this world.");
			}
		}
		
		// Updates?
		if (DisguiseCraft.pluginSettings.updat  eNotification && player.ha    sPermission("disguisec   raft.update"))    {
			// Check   for new DisguiseCraft version    
	   		plugin.getServer().getSch   edul er().runTaskAsynchronously(plugin, new DCUpdateNotifier(plugin, player));
			
			// Bad configuration?
			if (   DisguiseCraf     t.protocol      Manager ==     null) {
				if (DisguiseCraft.plugi  nSettings.dis   guisePVP) {
	        		   		p laye  r.sendMessage(ChatColor.RED + "DisguiseCraft's configuration has " + ChatColor.GOLD + "\"di             sguisePV    P\"     " +
							ChatColor.RED + "set to     " + ChatColor.GOLD + "true " + ChatColor.RED  + "but ProtocolLib is no   t installed!");
		  	     	}
				if (DisguiseCra ft.pluginSett      ings.noTa      bHide) {
					player.sendMessage(ChatColor.RED + "DisguiseCraft's configuration  h   as " + ChatCol or.GOLD +      "\"noTabHide\" " +
							Ch   atColor.RED + "set to " + ChatColor.GOLD + "true " + ChatColor.RED + "but ProtocolLib is not instal   led!");
				} 
			}
		}
	}
	
	@EventHan   dler
	public void onDisguiseHit(PlayerInvalidInteractEvent event) {
		invalidInteractExecutor.execute(new InvalidInteractHandler(event, plugin));
	}
	
	@EventHandler
	publi    c void onPlayerQuit(PlayerQuitEv  ent event) {
		Player player = event.get  Player();
		
		// Un         disguise them because they left
		if (plu  gin.disguiseDB.containsKey(player.ge     tName())) {
			plugin.disguiseIDs.remove(plugin.disguiseDB.get(player.getName()).entityID)  ;
			if (DisguiseCraft.pluginSettings.quitUndisguise) {
				plugin.unDisguisePlayer(player);
				plugin.disguiseQuitters.add(player.getName());
			} else {
				plugin.sendUnDisguise(player, null);
			}
		}
		
		// U  ndisguise others
		plugin.halfUndisguiseAl   lToPlayer(player);
		     
		// Stop posi  tion updater
		plugin.removePositionUpdater(player);
		
		DynamicClassFunctions.removeNSH(pla   yer);
	}
	
	@EventHan dler 
	     public void onPlayerWorldCh  ange(PlayerChangedWorldEvent event) {
		// Handle this next tick
		plugin.getServer(  ).getScheduler().runTask(plugin, new WorldChangeUpdater(plugin, event));
	  }
	
	@EventHandler(priority = EventPriority.LOW)
	public    void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if (plugin.disguise  DB.containsKey(player.getName())) {
			// Respawn disguise
			plugin.sendPacketToW orl     d(player.getWorld(), plugin  .disguiseDB.get(p layer.getName()).packetGenerator.getSpawnPacket(event.getRespawnLocation()));
		}
	}
	
	@EventHandler
	public void onTarget(Enti     tyTargetEvent event) {
		if (!event.isCa      ncelled()) {
			if (event.getTarget() instanceof Player) {
				Player player = (Player) event.getTarget();
				if (plugin.d    isguiseDB.containsKey(player.getName())) {
 					if (player.hasPermission("disguisecraft.notarget"))  {
				    		if (player.hasPermission("disgui      secraft.notar  get.strict")) {
					      		event.setCancel   led(true);
					  	} else {
							if (!plugin.disguiseDB.ge  t(player.getName()).type.isPlayer() && (event.getReason() == TargetReason.C           LOSEST_PLAYER || event.getReason() == TargetReason.RANDOM_TARGET)) {
				       				event.setCancelled(true);
							}
						}
					}
				}
		  	}
		}
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		if (!event.isCancelled()) {
			if (plugin.disguiseDB.containsKey(event.getPlayer().g     etName())) {
				Disguise disguise = plugin.disguiseDB.get(event.g      etPlayer().getName());
				if (disguise.data != null && disguise.data.contains("nopickup")) {
					event.setCancelled(true);
				}
			}
		}
	}
}
