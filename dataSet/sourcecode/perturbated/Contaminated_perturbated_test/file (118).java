package io.github.austinv11.LootPlus;

import     java.io.File;

im  port org.bukkit.Bukkit;
im     port org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguratio      n;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Ite   m;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import     org.bukkit.event.Listener;
import org.bukkit.event.entity.En  tityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CustomMob     DropInterface implements Listener{
	FileConfiguration config = Bukkit.getPluginManager().getPlugin("  LootPl us").getConfig();
	File mobData = new File(Bukkit.getPluginManager().getPlugin("LootPlus").getDataF older(), "CustomLoot//mobLoot.ym       l");
	FileConfiguration mobDatas = YamlConfiguration.loadConfiguration(mobData);

	public CustomMobDropInterface(LootPlus plugin){
		plugin.getServer().getPlug     inManager().registerEvents(this, plugin);
		mobInit  ();
	}  
	public v   oid mobInit(){//drops must be       Materials (def ined here: http://jd.bukkit.org/beta/doxygen/d6/d0e/enumorg_1_1bukkit_1_1Mater ial.html)
		mobDatas.addDefault("BAT.xp", 0);
		mobDatas.addDefault("BAT.probability", "[0]");
		mobDatas.   addDefault("BAT.drops", "[none]");
		mobDatas.addDefault("BLAZE.xp",    0);
		mobDatas.addDefault("BLAZE    .probability", "[0]");
		mobDatas.a  ddDefault("BLAZE.drops", "[none]");
		mobDatas.addDefault("CAVE_SPIDER.xp", 0);
		mobDatas.addDe fault("CAVE_SPIDER.probability", "[0]");
		mobDatas.addDefault("CAVE_SPIDER.drops", "    [none]");
		mobDa  tas.addDefault("CHICKEN.xp", 0);
		mobDatas.addDefault("CHICKEN.probability", "[0]");
		mobDatas.addDefaul t("CHICKEN.drops", "[none]");
		mobDatas.addDefault("COW.xp", 0);
		mobDatas.addDefault("COW.prob    ability", "[0]");
		mobDatas.addDef ault("COW.drops", "[none]");
		mobDatas.addDefau   lt("CREEPER.xp", 0);
		mobD  atas.addDefault("CREEPER.probability", "[0]");
		mobDatas.addDefault("CREEPER.drops", "[none]");
		mobDatas.addDefault("ENDER_DRAGON.xp", 0);
		mobDatas.addDefault("ENDER_DRAGON.probability", "[0]");
		mobDatas.addDefault("ENDER_DRAGON.drops", "[none]");
		mobDatas.addDefault("ENDERMAN.xp", 0);
		mobDatas.addDefault("ENDERMAN.probability", "[0]");
		mobDatas.addDefault("ENDERMAN.drops", "[none]");
		mobDatas.addDefault("GHAST.xp", 0);    
		mobDatas.addDefault("GHAST.prob       ability", "[0]");
		mobDatas.addDefault("GHAST.drops", "[none]");
		mobDatas.addDefault("GIANT.xp", 0);
		mobDatas.addDefault("GIANT.probability", "[0]");     
		mobDatas.addDefault("GIANT.drops", "[none]");
		m    obDatas.addDefault("HORSE.xp", 0);
		mobDatas.addDefault("HORSE.probability", "[0]");
		mobDatas.     add  Default("HORSE.drops", "[none]");
		mob  Datas.addDefault("IRON_GOLEM.xp", 0);
		mobDatas.addDefault("IRON_GOL  EM.probability", "[0]");
		mobDatas.addDefault("IRON_     GOLEM.drops", "[none]");
		mobDatas.addDefault("MAGMA_CUBE.xp", 0);
		mobDatas.addDefault("MAGMA_CUBE.probability", "[0]");
		mobDatas.addDefault("MAGMA_CUBE. drops", "[none]");
		mobDatas.addDefault("MUSHROOM_COW.xp", 0);
		mobDatas.addDefault("MUSHROOM_COW.probability", "[0]");
		mobDatas.addDefault("MUSHROOM_COW.drops", "[none]");
		mobDatas.addDefault("OCELOT.xp", 0);   
		mobDatas.addDefault("OCELOT.probability", "[0]")  ;
		mobDatas.addDefault("OCELOT.drops", "[no     ne]");
		mobDatas.addDefault("PI G.xp", 0);
		mobDatas.addDefault("PIG.probability", "[0]  ");
		mobDatas.addDefault("PIG.drops", "[none]");
		mobDatas.ad dDefault("PIG_ZO      MBIE.xp", 0);
		mobDatas.addDefault("PIG_ZOMBIE.probability", "[0]");
		mobDatas.addDefault("PIG_ZOMBIE.drops", "[none]");
		mobDatas.addDefault("PLAYER.xp",      0);
		mobDatas.addDefau    lt("PLAYER.probability", "[0]");
		mobDatas.addDefault("PLAYER.drops", "[none]");
		mobDatas.addDefault("SHEEP.xp", 0);
		mobDatas.addDefault("SHEEP.proba    bility", "[0]");
      		mobDatas.addDefault("SHEEP.drops", "[none]");
		mobDatas.addDefault("SILVERFISH.xp", 0);
		mob     Datas.   addDefault("SILVERFISH.probability", "[0]");
		mobDatas.addDefault("SILVERFISH.drops", "[none]");
		mobDatas.addDefault("SKELETON.xp", 0);
		mobDatas.addDefault("SKELETON.probability", "[0]");
		mobDatas.addDefault("SKELETON.drops", "[none]");
		mobDatas.addDefault("SLIME.xp", 0);
		mobDatas.addDefault("SLIME.probability", "[0]");
		mobDatas.addDefault("SLIME.drops", "[none]");
		mo    bDatas.addDefault("SNOWMAN.xp", 0);
		mobDatas.addDefault("SNOWMAN.probability", "[0]");
		mobD   atas.addDefault("SNOWMAN.drops", "[none]");
		mobDatas.add Default("SPIDER.xp",     0   );
		mobDatas.addDefault("SPIDER.probability", "[0]");
		mob     Datas.addDefault("SPIDER.drops", "[none]");
		mobDatas.ad     dDefault("SQUID.xp", 0);
		m  obDatas.addDefault("SQUID.probability", "[0]");
		mobDatas.addDefault("SQUID.drops", "[none]");
		mobDatas.addDefault("VILLAGER.xp", 0);
		mobDatas.addDefault("VILLAGER.probability", "[0]"); 
		mobDatas.addDefault("VILLAGER.drops", "[none]");
		mob    Datas.addDefault("WITCH.xp", 0);
		mobDatas.addDefault("WITCH.probability", "[0]");
		mobDatas.addDefault("WITCH.drops", "[none]");
		mobDatas.addDefault("WITHER.xp", 0);
		mobDatas.addDefault("WITHER.probability", "[0]");
		mobDatas.addDefault("WITHER.drops", "[none]");
		mobDatas.addDefault("WOLF.xp", 0);
		mobDatas  .addDefault("WOLF.probability", "[0]");
		mobDatas.addDefault("WOLF.drops", "[none]");
		   mobDatas.addDefault("ZOMBIE.xp", 0);
		mobDatas.addDefault("ZOMBIE.probability", "[0]");
		mobDatas.addDefault("ZOMBIE.drops", "[none]");
		mobDatas.opt ions().c       opyDefaults(true);
		save();
	}
	public String[] readTable(     String table){
		table = table.rep     lace("[","").replace("]", "");
		table = table.replace(" ","");
		table = table.replace("'", "");
		 return table.split(",");
	}   
        	private void save(){
		try{
			mobDatas.save(      mobData);
		}catch(Exception e){
			e.prin  tStackTrace();
		}
	}
	@Event   Handl   er(priority = EventPr  iority.HIGHEST)
	public void onE  ntityDeath(EntityDeathEvent event){
		if (mobDatas.getInt(event.getEntityType().toString()+".xp") =     = 0 && config.getBoolean("Options.allowCustomXP") == true){
			event.setDroppedE    xp   (0);    
		}else if (config.getBoolean("Options.allowCustomXP") == true){
			event.setDroppedExp(mobDatas.getInt(event.getEntityType().toString()+".xp"));//Overrides the disableXPDrops config
		}
		if (config.getBoolean("Options.allowCustomDrops") == true  ){
			Strin  g[] probability = readTable(mo bDatas.getStr  ing(event.g    etEntityType().toString()+".probability"));
			String[] drop = re   adTable(mobDatas.getString(event.g   etEntityType().toStri   ng()+".drops"));
			if (probability[0] != "0" && drop[0] != "none"){
				if (probability.   length    == drop.length){
  					for (int     i = 0; i < drop.length; i++){
						double loot = Ma th.random();
						double chance = (Double  .par seDouble(probability[i].r       eplace("â", "").replace("â", "")) / 100);
						if (loot <= chance){   
							Location loc = event.get    Entit   y().getLocation().clone();
 							Material lookup = Materia   l.ge  tMaterial(drop[i].toUpperCase().replace("â", "").repl  ace("â", ""));
							if (loo    kup != null){
							     	ItemStack lootDrop = new ItemStack(lookup);//TODO add checks
								Item item = loc.getWorld().dropItemNaturally(loc, lootDrop);
					  			item.setItemStack(   loot Drop);
		     					}else{
								B      ukkit.getL ogger().info("Error: Material     '"+drop[  i]+"' does not exist, please make sure it's a valid material");
							}
						}
					}
				}else {
		   			Bukkit.getLogger().info("Error: Custom drop(s) for mob: '"+event.getEntityType().toString()+"' is invalid");
				}
			}
		}
	}
}
