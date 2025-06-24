package net.unknown_degree.seer.Professions.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import net.unknown_degree.seer.Professions.Professions;
import net.unknown_degree.seer.Professions.data.DataRead;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DataWrite extends JavaPlugin implements Listener {
	
    private Professions plugin;
    
    public DataWrite(Professions plugin) {
        this.plugin = plugin;
    }
    
	public static void joinProf(CommandSender sender, String prof) {
		sender.sendMessage(prof);
	}
	
	/*
	 * This function writes the player data on block break events.
	 * TODO Write to player files...
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		Player p = evt.getPlayer();
		Block b = evt.getBlock();
		
		ConfigurationSection cProfs = plugin.getConfig().getConfigurationSection("profs");
		Set<String> profs = cProfs.getKeys(false);
        Iterator<String> i = profs.iterator();		

		while ( i.hasNext() ) {
		    
		    String v = (String)i.next();
            Integer i1;
            Integer i2;
            
		    //This is data from the config file!
		    ArrayList<ArrayList<ArrayList<String>>> j = DataRead.getProfData(v, plugin);
	        
	        for ( i1 = 0; i1 <j.size(); i1++ ) {
	            if ( j.get(i1).get(0).get(0) != null ) {
	                for ( i2 = 0; i2 < j.get(i1).get(0).size(); i2++ ) {
	                    Material material = Material.matchMaterial(j.get(i1).get(0).get(i2));
	                    if ( b.getTypeId() == material.getId() ) { 
	                        Collection<ItemStack> drops = b.getDrops(p.getItemInHand());
	                        if ( !drops.isEmpty() ) {
	                            p.sendMessage("You broke a block for your profession! :: " + material.getId());
	                        }
	                    }
	                }
	            }
	        }
	        
		}
	}
	
}
