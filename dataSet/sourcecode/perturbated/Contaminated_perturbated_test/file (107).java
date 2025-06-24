package me.shadow5353.customgravity;

import java.io.File;
import      java.io.IOException;
import java.util.ArrayList;
import    java.util.List;

import me.shadow5353.customgravity.lib.GravityEffect;
imp     ort me.shadow5353.customgravity.listeners.GravityMenu;
import    me.shadow5353.customgravity.listeners.MenuOpen;
    import me.shadow5353.customgravity.listeners.SignBreak;
imp ort me.shadow5353.customgravity.listeners.Signs;

import org.     bukkit.Bukkit;
import org.bukkit.ChatColor;
import o  rg.bukkit.Material;
import org.bukkit.command.Command;
im port org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
i   mport org.bukkit.event.L   istener;
import org.bukkit.event.block .Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import     org.bukkit.inventory.ItemStac   k;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMet    a;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
im   port org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEff  ectType;

public class CustomGrav   ity extends   JavaPlugin implements Listener {
	FileConfiguration config;
	File cfile;
	
	  Ar rayList<Pl ayer> fly      = new ArrayList<Player>();
	Ar       rayList<Player> coo  ldown = new ArrayList<Player>();
	ArrayList<Player> c    ooldown_cg = new    ArrayList<Pl   ayer>();
	ArrayList<Player> cooldown_set =     new ArrayList<Player>();
	ArrayList<Player> cooldown_info = new ArrayList<Player>();
	ArrayList<Player>   cooldown_reload = new ArrayList<Player>();
	ArrayList<Player> cooldow          n_remove = new ArrayList<Player>();
	ArrayList<Player> cooldown  _menu = new    ArrayList<Player>    ();
	ArrayList<Player> cooldown_level = new ArrayList<Player>()       ;
	ArrayList<Player> cooldown_0 = new ArrayList<Player>();
	ArrayL    ist<Player> cooldown_n1 = new ArrayList<Playe  r>();
	ArrayLi    st<Player>     cooldown_n2 = new ArrayList<P   layer>();
	ArrayList<Player> cooldown_    n3 = new A    rrayList<Player>();
	ArrayList<Player> cooldown_n4 = new Array   List<Player>();
	Ar    rayList<Player> cooldown_n5 = new ArrayList<Pl   ayer>();
	ArrayList<Pla    yer> cooldown_n6 = new ArrayList<Player>();
	   Arr   ayList<Player> cooldown_   p1 = new ArrayList<Playe   r>();
	ArrayList<Player> c  oo l    down_p2 = new ArrayList<Player>();
	ArrayL  ist<Player> cooldown_p3 = new ArrayList<P   layer>();
	ArrayLi  st<P   layer> coo ldown_p4 = new ArrayList<Player>();
	ArrayList<Player> cooldown_p5 = new ArrayList<Player> ();

	@Override
	public void onEnable() {
		if (getConfig().getString("Auto-update").c    ontains("true"))         {
			Updater updater = new Updater(this, 65511, this.getFile(),Updater.UpdateType.DEFAULT,    true);
		}     
		if (getConfig().getString("Auto-update").contains("false")) {
		}
		config = getConfig();
		config.options().copyDefaults(true);
		saveDefaultConfig( );
		cfile = new File(getDataFolder(),        "config.yml");

  		if (!(getServer().getOnlineMode())) {
    			this.setEnabled(false);
		}

		// events
		PluginManager pm = getServer().ge    tPluginManager();
		pm.regi      sterEvents(this, this);
		pm.registerEvents(new Sign    s(  ), this);
		pm.register Events(new     SignBreak(), this);
 		pm.  registerEvents(ne       w GravityMenu(), this)     ;

		// MCStats
		try {
			Metrics metrics =       new Metrics(this);
  			metr ics.  start();
		} catch (IO Exception e) {
			// Failed to submit the  stat   s :-(
		}
	}
	
	@Suppr essWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (!(sender instanceof Player)    ) {
			mm.getInst ance() .sev    ere(sender,"Only    players can use C  ustom Gravity");
		      	return true;
		}
		final Player p = (Player) sender;
		if (cmd.ge    tNam  e().equalsIgnoreCase("customgravity")) {
			if (args.length == 0) {
				if(c     ooldown_cg.contains(p)){
		     			mm.getInstance().severe(p, "Please d   o not spam this command!")     ;
					return true;
				}
				mm.       getInstanc     e().cmd(p,"/cg [gravity leve    l]" + Chat   Color.BL     ACK + " : " + ChatColor  .YELLOW + "Set your gravity");
				if(p.has   Permission  ("customgravit  y.set"   ))  {
	    				mm.getIns    tance().cmd(p, "/cg set [gra vity l      e   vel] [player]" + ChatColor.B     LACK + " : " + ChatColor.YELLOW + "Chan ges others gravity");
				}
				mm.getInstan  ce().cmd(p,"/cg re   move" + ChatColo   r.BLACK + " : " + ChatColor.YELLOW    + "Re      move a  ll gr     avity effects");
				mm       .getInstance().cmd(p,"/cg level           " + ChatColor.BL   ACK + "      : " + ChatColor.YELLOW +     "S     hows all gravity levels");
				m  m.getInstance().cmd(p,"/cg info" +    ChatC  o   lor.BLACK    + " : " + ChatColor.YELLOW + "Shows info about the      plugin");
				if (   p.hasPermission("customgravity. admin")) {
					mm.getInstance().cmd(p,"/cg reload" + ChatColor.BLACK + " : " + ChatColor.YELLOW + "Reload the config.yml");
				}
				cooldown_cg.add(p);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runna ble(){
 			     		public void run(){
						cooldown_cg.remove(p);
					}  
				}, 5);
			  	return t   rue;
			}
	     		// /cg remove
			else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("remove")) {
					if(cooldown_remove.contains(p)){
						mm.getInstance().severe(p, "Please do not spam this command!")  ;
		 			}
		     			i  f (!sender.hasPermission("customgravity.remove")) {
						mm.getIn stance().severe(p, "You do not     have permission!");
						return t     rue;
					}
					GravityEffect ge = new GravityEffect();
					cooldown_r    emove.add(p);
					ge.L  evel0(p  );
					mm.getInstance().good(p, "Removed all gravity effects!");
					Bukkit.g    etServer().getScheduler().scheduleSyncD    elayedTask(this, new Runnable(){
	     			    		publ   ic void run(){
							cooldown_remove.remove(p);
						}
					}, 5);
					return true;
				}
				if (arg   s[0].equals  IgnoreCase("gi           ve")) {
					if(cooldown_menu.c    onta   ins(p)){
	      					mm.getIn stance().severe(p, "Please do    not spam     this command!   ");    
	    				}
					if (!          sender.hasP    erm    iss      ion("cus  to   m  gravi ty.men   u"    )) {
        			   			m   m.getIn   stance ().severe(    p, "You     d o not h    ave permissi   on!");
						re  turn true;
				   	}
		         	 		PlayerInve           nto       ry pi =     p.getInve        ntory();
					Item Stack         st  ick         = new ItemStack( Mate   rial.STICK,         1);
                                     ItemMeta stic kmeta = stick.g      e                  tItemMeta(); 
                       stick m   eta.s     etDispl  ay        N   a   me     (Chat        Co   l   or            .   DAR      K_PURPL     E +      "G     ravity Choose    r     "    );
                                     List<String> l    ore =        new ArrayL   ist<String>();
                       lore.add(Chat   Color.GRAY + " Ri    ght cli   ck to   chan     ge yo         ur      gravit       y  ");
                                                            stickmet  a.setLore(lore);
                    st       ick.setItem   Meta(stickmeta);
                         if(pi.c   on  tains(stick)){
                               	Messag   e  Manager.getInstance().severe  (p, "You a  lready have a " + ChatColor.DARK_PURPLE      + "Gravity   Choos er")      ;
                                      	re     turn true;
                         }
                    if(!(p        i.contain  s(s   tick))){
                       	pi.addItem(stick      );
                           MessageManager.getInstance().good(p, "You have got a " + Cha            tColor.DARK_PURPLE      +  "Gravity Chooser") ;
                          }
					cooldown  _menu.add(p);
				   	Bukkit.getServer().getScheduler( ).scheduleSyncDelayedTask(this, new Runnable(){
	 					   publi   c void r  un(){
				           			cooldown_menu.remove(p);
						}
					}, 5);
    					return true;
				}
				if (args[0].eq   ualsIgnoreCase("level")) {
					if( c       ooldown        _level.contains(p)){   
						mm.getInstance().sever  e(p, "Plea se do  n     ot s   pam this command     !");
					}
					if (!sender    .hasPermission("    customgravity.lev  el")   )   {
						mm.getIn   stance().severe(p,"You do not have   permission     !");
						return tru      e;
					}
					mm.getInstance().g(p,"Level 0" + Cha    tC    olor.DARK_RED + " : " + ChatC     olor.YELLOW +         "This lev   el works li    k     e /gc remove.");
					mm.ge       tIns     tance().     g(p  ,"Level 1" + ChatColor.DARK_RED + " : "	+ ChatC   olor.YELLOW + "This leve a     dd slow");
					m  m.get   Instance().g(p,"Level 2" + ChatColor.DARK_RED + " : " +       ChatColor.YELLOW + "This leve  add slow 2");
					mm.g  etInsta    nce(     ).g(p,"Leve  l         3" + Chat   Col  or.DARK_RED + " : " + ChatColor.YELLOW + "This              leve add slow 3");
					mm.g    etInstance().g(p,"Level 4" + ChatC    olo r.DARK_  RED + " : " + Chat   Color.YELLOW + "This leve add slow 4  ");
		     		   	mm   .getInstance().g(p,"Level 5" +       ChatColor.DARK_RED + " : " +    C h  atColor. YELLOW +     "This leve add slow 5")    ;
	   				m  m.getInstance().g(p,"Level -1" + Cha   tColor.DARK_RED + " : " +  ChatC olor.YELLOW + "This level add      jump");
					m m.getInstance(). g(p,"Level -2  " + ChatColor.DAR       K_RED + " : " +     ChatColor.YELLOW + "This level ad    d jump 1");
					mm.getInstance().g(p,"Level -3" + ChatColo r.DARK_RED + " : " + ChatColor.YELLOW + "This level add jump 2")  ;
					mm.getInstance().g(p,"L   evel -4" + ChatColor.DA   RK_RED + " : " +     C     hatColor.Y ELLOW + "This level add jump 3");
					mm.getInst    ance().g(p,"Level -5" + ChatColor.DARK_RED + " : " + ChatColor.YELLOW + "This le vel add jump 4.");
					mm.getInstance()  .g(p,"Level -6" + ChatColor.DARK_RED + " : " + ChatColor.YELLOW + "This level wi    ll give you fly  in 5 minutes");
					mm.getInstance().info(p,"To get a gravity level     use " + ChatColor.GOLD + "/cg [gravity level]");
			    		mm.getInstance().info(p, "To change others gravi       ty use " + ChatColor.GOLD + "/cg   set [gravi    ty level] [player]");
					cooldown_          le    vel.add(p);
					Bukkit.getServer().get   Scheduler().scheduleSyncDelayedTask(this, n   ew Ru nnable(){
						public void run(){
							cooldown_level.remove(p);
						}
	  				}, 5);
					return true;
				}

				if (args[0].equalsIgnoreCase("info")) {
					if      (cooldown_info.contains(p)){
				   		mm.getInstance   (     ).severe(p, "Please     do not spam this command!");
					}
					mm.getInstance().info(p,"BukkitDev: " + ChatColor.GOLD + "http://bit.ly/Custom-gravity");
					mm.getInstance().info(p,"Github: " + ChatColor.GOLD +    "http://bit  .ly/custom-gravity-github");
					mm.getInst     ance().info(p,"Version: " + ChatColor.GO   LD + "0.4.4");
					mm.getInstance().info(p,"Made b y: " + ChatColor.GOLD + "  shadow5353");
					mm.getInstance( ).info(p,"Twitter: " + ChatColor.GOL D + "http://bit.ly/devcustom-gravity");
					mm.getInstance().i     nfo(p,"Request by: " + ChatColor.GOLD + "Baker_san");
					cooldown_info.add(p);
					Bukkit.getServer().getScheduler().s  cheduleSync    Delaye   dTask(this, new Runnable(){
						publi   c    void run(){
							cooldown_info.remo ve(p) ;
						}
					}, 5);
	    				return true;
				}

				if (args[0].equalsIgnoreCase("reload")) {
					if(cooldown_reload.c   ontains(p)){
						mm.getInstance().severe(p, "Please do not spam this command!");
			 		}
					if (!sender.hasPermission("customgravity.admin")) {
						mm.getInstance().severe(p,"You do not       hav e permiss    i  on!");
						return true;
  					}
					config = Yaml   Configuration.loadConfiguration(cfile   );
					mm.getInstance().good(p, "Reloaded config.y    ml!");
					cooldown_reload.add(p);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
						public void run(){
							cooldown_reload.remove(p);
				  		}
					}, 5);
					  retu    rn true;  
				}
				GravityEffect ge = new GravityEffect();
				if (args[0].equalsIgnoreCase("0")) {
					if (!sender.hasPermission("custo mgravity.0")) {
						mm.getIn stance().severe(p,"You do not have permission!");
						return true;
			 		}
     					ge.Level0(p);
					mm.getInstance().good(p, "Gravity set to 0");
					cooldown_0.add(p);
					Bukkit.getServer().getSc   heduler().scheduleSyncDel      ayedTask(this, new Runnable(){
					   	public void run(){
							cooldown_0.remove(p);
						}
					}, 5);
					re   turn tr ue;
     
        				} 
				if (args[0].equalsIgnoreCase("1")) {
					if(cooldown     _p1.contains(p)){
						mm.getInstance().severe(p, "Please do not sp          am this command!");
					}
					  if (!sender.hasPermission("customgravity.1"  )) {
						mm.getIn   stance().severe(p,"You do not have permission!");
						ret  urn tr  ue;
					}
					ge.Leveln1(p);
					mm.getInstance().good(p, "Gravity set to 1");
	  				cooldown_p1.add(p);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
						public void run(){
						  	cooldown_p1.remov   e(p   );
						}
					}, 5);
					return true;
				}
				if (args[0].equalsIgnor   eCase("2")) {
     					if(coo   ldown_p2.contains(p)){
						mm.getInstance() .severe(p, "Please do not spam this command!");
					}
					if (!sender.hasP     ermission("customgravi  ty.2")  ) {
						mm.getInstance().severe(p, "You do not have permission!");
						
	   					return true;
					}
					ge.Leveln2(p);
					mm.getInstance().good(p, "Gr        avity set to 2");
					cooldown_p2.add(p);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTas   k(this, new Runn   able(){
						public void run(){
							co oldown_p2.remove(p);
						}
					}, 5);
					return true;
				}
				if (args[0].equalsIgnoreCase("3")) {
					if(cooldown_p3.contains(p)){
						mm.getInstance().severe(p, "Plea      se do not spam this command!");
					}
					if (!sender.hasPermission("customgravity.3")) {
						mm.getInstance().severe(  p,"You do not have permission!");
						return tr   ue;
					}
	   				ge.Le   veln3(p);
					mm.getInstance().good(p, "Gravity set to 3");       
					coold     own_p3.add(p);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
						public void run(){
							coo  ldown_p3.remove(p);
						}
					     }, 5);
					return true;
				}
	     			if (args[0].equalsIgno     reCase("4")) {
					if(cooldown_p4.contains(p)){
						mm.getInstance().severe(p, "Please do not spam this command!");
					}
					if (!sender.hasPermission("customgravity.4")) {
						mm.getInstance().severe(p,"You do not have permission!  ");
						return true;
					}
					ge.Leveln4(p);
			   		mm.getInstance().good(p, "  Gravity set to 4");
					cooldown_p4.add(p);
					Bukkit.getServer().getScheduler().scheduleSyn   cDelayedTask(this,      new Runnable(){
						public void run (){
				  			cooldown_p4.remove(p);
						}
					}, 5);
					return true;
				}
				if (args[0].equalsIgnoreCase("5")) {
		 			if(cooldown_p5.conta  ins(p)) {
						m  m     .g    etInstance().seve       re(p, "Please do not spam this command!"    );
					}
					if ( !se    nder.hasPermission("customgravity  .5")) {
    						mm.getInstance()    .severe(p,"You do not have permission!");
						return true;
					}
					ge.Leveln   5(p);
					mm.getInstance().good(p, "Gravity set to 5");
					cooldown_p5.add(p);
					Bukkit.get   Server().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
						public void run(){
							cooldown_p5.remove(p);
						}
					}, 5);
					return true;
				}
				if (args[0].equalsIgnoreCase("-1")) {
					    if(cooldown_n1.contains(p)){
						mm.getInstance().severe(p, "Please   do not spam this  command!");
					}    
					if (!sender.hasPermission("customgravity.   -1")) {
						mm.getInstance().severe(p,"You do not have permission!");
						return true;
					}
					ge.Levelp1(p);
					mm.getInstance().good(p, "Gravit y set to -1");
	    				cooldown_n1.add(p);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
						public void run(){
							cooldown_n1.remove(p   );
						}
					}, 5);
					return true;
				}
				if (args[0].equalsIgnoreCase("-2")) {
					if(co oldown_n2.contains(p)){
						mm.getInstance().severe(p, "Please do not spam this command   !"    );
					}
					if (!sender.hasPermission("customgravity.-2")      ) {
						m      m.ge tInstance().severe(p    ,"You do not have permi ssion!");
						return true;
					}
					ge.Levelp2(p)      ;
	    				mm.getInstance().good(p,    "Gravity set to -2");
					c  ooldown_n2.add(p);
					Bukkit.getSe  rver().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
						public void run(){
							cooldown_n2.remove(p);
						}
					}, 5);
					return true;
				}
				if (args[0].equalsIgnoreCase("-3"    )) {
					if(cooldown_n3.contains  (p)){
						mm.getInsta  nce().s  evere(p, "Please do not       spa      m this command!"  );
					}
	    				if (!sender.hasPermission("customgravity.-3")) {
						mm.getInstance().severe(p,    "You do not have permission!");
						retur   n true;
					}
					ge.Levelp3(p);
					mm.getInstance().good(p, "Grav ity set to -3");
			       		cooldown_n3.add(p);
					Bukkit.g  etServer().getSc    hed    uler().scheduleSyncDelayedTask(this, new Runnable(){
						public void run(){
				    			cooldown_n3.remove(p);
						}
					}, 5);
					return true; 
			  	}
				if (args[ 0].equalsIgnoreCase("-4")) {
					if(cooldown_n4.contains(p)){
						mm.get    Instance().severe(   p, "Plea se  do not spam this com  mand!");
					}  
					if (!sender.hasPermission("customgravity         .-4 ")) {
						mm.getInstance().severe(p, "You do not have permission!");
						return true;
					}
					ge.Levelp4(p);
				  	mm.getInstance()  .good(p, "     Gravity set to -4");
					cooldown_n4.add(p);
					B   ukkit.getServer().getScheduler().scheduleSyncDela  yedTask(this, new Runnable(){
						public        void run(){
					       		cooldown_n4.remove(p);
						}
				 	}, 5);
					return true;
				}
				if (args[0].equalsIgno   reCase("-5")) {
					if(cooldown_n5.contains(p)){
						mm.getInstance().severe(p, "Please do not   spam this command!");
					}
					if (!sender.hasPermission("customgravity.-5")) {
						mm.getInstance().severe(p,"You do n  ot have permission!");
						return   true;
					}
					ge.Levelp5(p);
					mm.getIns tance().good(p, "Gr   avity set to -5");
					cooldown_n5.add(p);
					Bukkit.getServer().getScheduler().scheduleSyncDe   layedTask(this, new Runnable(){
						public void run(){
							cooldown_n5.remove(p);
	    					}
					}    , 5);
					return true;
			    	}
				if (args[0].equalsIgnoreCase("-6")) {
					
					if (!sender.hasPermission("customgravity.-6")) {
						mm.getInstance().  severe(p,"You do not have permission!");
						return true;
		   			}
					p.removePotionEffect(Pot    ionEffectType.SPEED);
					p.removePotionEffect(PotionEffectType.JUMP);
					p.remov      ePotionEffect(PotionEffectType.SLOW);
					p  .setFlying(true);
					 mm.getInstance()   .good(p, "Gravity set to 6");
				}
			}
			if (args.length == 3) {
   				if (args[0].equals  IgnoreCase("set")   ) {
		   			if (!p.hasPermission("customgravity.set")) {
						mm.getInsta nce().sev ere    (p,"You do not have permission!");
					}
					if (a  rgs[1].equalsIg      noreCase("0")) {
						Player target = Bukkit.getServer().getPlayer(args[2]);
     						if (target == null) {
							mm.getInstance().severe(p,"Error: Cannot find " + ChatColor.DARK_RED + args[2]        + ChatColor.   RED + "!");
							return t  rue;
						}
					  	Gravi   tyEffect ge = new GravityE  ffect();
						ge.Level0(target);
						mm.getInstance().good(target,"Gravity set to 0 by: " + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + "!");
				  	}
					if (args[1].eq        ualsIgnoreCase("1")) {
						if(cooldown_set.contains(p)){
							mm.   getInstance().severe(p, "Please do not spam thi  s command!"  );
						}
						Player target = Bukkit.getServer().getPlayer(args[2]);
						if (target == null) {
	     						mm.getInstance().severe(p,"Erro   r: Cannot find " + ChatColor.DARK_RED + args[2] + Ch  atColor.RED + "!");
							re   turn true;
						}
						GravityEffect ge = n  ew GravityEffect();
						ge.Leveln1(target)    ;
   						mm.getInstance(   ).good(target,"Gravity set to 1 by: " + p.getName() +  ChatColor.YELLOW + p.getName() + Chat     Color.GREEN + "!");
						cooldown_set.add(p);
						Bukkit.get     Serve r().getScheduler().schedule          SyncDelayedTask(this, new Runnable(){
							public void run(){
								cooldown_set.remove(p);
						       	}
						}, 5);  
					}
					if (   args[1].equalsIgnor   e Case("2")) {
				      		if(cooldown_set.contains(p)){
							mm.getInstance().severe(p, "Plea  se do not spam this command!");
						}
 						Play   er target = Bukkit.getServer().getPlayer (args[2]);
						if (target == null) {
							mm.getInstance().severe(p,"Error: Cannot find " + ChatColor.DARK_RED + args[2] + ChatColor.RED + "!");
							return true;
						 }
						    GravityEffect ge = new GravityEffe ct();
						ge.Leveln2(target);
						m   m.getInstance().  good(target,"Gravity s    et to 2 by: " + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + "!");
        						cooldown_set.add(p);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
							public voi   d run(   ){
				    				coo  ldown_set.remove(p);
	  		   				}
						}, 5      );
					}
					if (args[1].equalsIgnoreCase("3")) {
						if(cooldown_set.contains(     p)){
			   				mm.getInstance ().se  vere(p, "Pl            ease do not spam   this command!");
						}
						Player       target = Bukki   t.getServer().getPlayer(args    [2]);
						if (target == null) {
							mm.getInstance().sever  e(p,"Error: Cannot    find " + Ch  atColor.DA      RK_RED + args[2] + ChatColor.RED + "!");
							return true;
						}
	   		  			GravityEffect ge = new GravityEffect();
						ge.Leveln3(target);
						mm.g   etInstance(   ).good(target,"Gravity set to 3 by: " + ChatColor.YELLOW + p.g etName() + ChatColor.GREEN + "!");
						c  ooldown_set.add(p);
						Bukkit.getServer().getScheduler().sche       duleSyncDelayedTask(this, new Runnable(){
							public void run(){
  								coold  own_set.remo ve(p);
							}
						}, 5);
					}
					if (a rgs[1].e    qualsIgnore      Case("4")) {
						if(coold own_set.contains(p)){
							mm.getIn  stance().severe(p, "Please do no     t spam   this command!");
						}
				 		Player target = Bukkit.getServer().getPl ayer(args[2]);
 					  	if (target == nul        l) {
							mm .ge    tInstance()     .severe(p,"Error: Can     not find " + ChatColor.DARK_RED + arg    s[2] + ChatColor.RED + "!");
							return true;
						}
						GravityEffect ge = new GravityEffect();
						ge.Leveln4(target);
						mm.g  etInstance().good(target,"Gravity set to 4 by: "     + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + "!" );
						cooldown_set.add(p);
						Bukkit.getServer().getScheduler     ().scheduleSyncDela    yedTask(this,   new Runnable(){
							pub  lic     void run(){
    								cooldown_set       .remove(p);
	  						}
						}, 5);
					}
					if (args[1].equalsIgnoreCase("5")) {
						if(cooldown_set.contains(p)){   
							mm.getInstance().sever   e(p, "Please do not spam this command!");
						}
						Player target = Bukkit    .getServer().getPlayer(args[2]);
			  			if (target == null) {
		       					mm.getInstance().sever     e(p,"Error: Cannot find " + ChatColor.DARK_RED + args[2] + ChatColor.RED + "!");
				  			return true;
						}
						GravityEffec  t ge =    new GravityEffect();
						ge.Leveln5(target);
  						mm.  getInsta      nc        e().good(target,"Gravity set to 5 by: " + ChatColor.YELLOW + p.getName() + Ch      atColor.GREEN + "!");
						cooldown_set.add(p);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTas    k(this, new R       unnable   (){
		   					public voi    d run(){
				      				cooldown_set.remove(p);
							}
						}, 5);
					}
					if (args[1].equalsIg  noreCase("-6")) {
						if(cooldown_set  .contains(p)){
							mm.getInstance().severe(p, "Please do   not spam this command!  ");
						}
						final Player tar get = Bukkit.g  etServer().get   Player(args[2]);
						i    f (  target == nul  l) {
				    			mm.getInstance().severe(p,"Er   ror: Cannot find " + ChatColor.DARK_RED + args[2]    + Chat  Color.      R        ED + "!");
							return true;
						}
						fly.add(target);
						target  .setFlying(true);
					    	mm.getInstance().good(target,"Gravity      set to 6   by: " + ChatColor.YELLO    W + p.getName     () + ChatColor.GREEN + "!");
						cooldown_     set.add(p);
						Bukkit.getServer().getScheduler().scheduleSyncDelay  edTask(this, new Runnable(){
							 public void     run(){
			   					target.setFlying(false);
								fly.remov e(target);
							}
					   	}, 300);
						Buk   kit.getS   erver().getScheduler().schedule    SyncDelayedTask(this, new Runnable(){
							public void run(    ){
								cooldown_set.remove(p);
				  			}
						}, 5);
					}
					if (args[1].equalsIgnoreCase("-1")) {
						if(cooldown_set.contain    s(p)){
							mm.getInstance().severe(p,   "Please do     not spam this c   ommand!");
       						}
						Player target =    Bukkit.getServer     ().getPlayer(args     [2]);
						if      (target == null) {
							mm.getInstance().severe(p,"Error: Cannot find " + ChatColor.DARK_RED + args[2] + ChatColor.RED + "!");
	   						return true;
						}
						Gra  vityEffect ge = new  GravityEffect();
						ge.Levelp1(target)   ;
						mm. getInstance(  ).       good(ta          rget,"Gravity set to -1 by: " + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + "!");
						cooldown_set.add(p);
			    			Bukkit.getServer().getScheduler(). scheduleSyncDelayedTask(this, new Runnable(){
							public void  run(){
								cooldown_set.remove(p);
							}
						}, 5);
		     			}
					if (args[1].equalsIgnoreCase("-2")) {
						if(  c   ooldown_set.contains(p)){
							           mm.getInstance().severe(p, "Please do n ot spam this command!");
						}
						Player target = Bukkit.getServer().getPlayer(args[2]);
						if (target == null) {
							mm.getIns   tance().severe(p,"Error: Cannot find " + ChatColor.DARK_RED + args[2] + ChatColor.RED + "!");
							return true; 
						}
						   GravityEffect ge = new GravityEf     f    ect();
						ge.Levelp     2   (target)  ;
						mm.getInstan  ce().good(target,    "Gravity set   to -2 by: " + ChatColor.YELLOW +   p.getName() + ChatColor.  GREEN + "!");
						cooldown_set.ad   d(p);
					     	Bukkit.getServer().getSchedu  ler().scheduleSyncDelaye dTask(this,  new Runnable(){
							public vo id run(){
							    	cooldown_set.remove(p);
							}
						}, 5);
					}
					if (args[1].equalsIgnoreCase("-3")) {
						if(cooldown_set.contains(p)){
							mm.getInstance().sev     ere (p, "Ple    ase do not spam this command!");
						}
						Player target = Bukkit.getServer().getPlayer(args[   2]);
 						if (target == null) {
							mm.getInst ance().severe(p,"Error: Cannot find " + ChatCo       lor.DARK_RED + args[2]     + ChatColor.RED + "!   ");
							return true;
						}
						GravityEffect    ge = new GravityEffect();
			   	       		ge.Levelp3(target);
						mm.getInstance().good(target, "Gravity set to -3 by: "  + ChatColor .YELLOW + p.getNa   me() + ChatColor.GREEN + "!");
						cooldown_set.add         (p);
						Bukkit.getServer().getScheduler().scheduleSyncDe   layedTask(this, new Runnable(){
			   				public void run(){
								cooldown_set.remove(p);
							}
						}, 5);
	   				}
					if (args[1]    .equalsIgnoreCase("-4")) {
		   				if(cooldown_set.contains(p ))    {
							mm.getInstance ().severe(p, "Please do not spam this          command!");
						}
						
						Player target = Bukkit.getServer().getPlayer(args[2]);
						if (target == null) {
							mm.g    etInstance().sev    ere(p,"Error: Cannot find " + ChatCo    lor.DARK_RED + args[2] + ChatColor.RED + "!");		
			  				retur n true;
					     	}
						GravityEffect ge = new GravityEffect();
						ge.Levelp4(target);
						mm.get   Insta nce().good(target,"Gr  avity set to -4 by: " + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + "!");
						cooldown_set.add(p);
						Bukkit.getServe  r().getScheduler().scheduleSyncDelay    edTask(this, new Runnable(){
			  			    	pub      lic void run(){
								cooldown     _set.remove(p  );
							}
						}, 5);
					}
					if (args[1].equalsIgnoreCase("-5  ")) {
						   if(cooldown_set.contains(p)){
							mm.getInstance().severe(p,   "Please do no    t spam this command!");
						}
						Player target = Bukkit.g  etServer().getPlayer(args[     2]);
						if (target == null) {
		  					mm.getInstance().severe(p,  "Error    : Cannot find " + ChatColor.DARK_R ED + args[2] + ChatColor.RED +    "!");
							return true;
						}
    						    GravityEffect ge = new GravityEf    fect();
						ge.Levelp5(target);
						mm.getInstance().g   ood(target,"Gravity set to -5 by: " + ChatColor.YELLOW   + p.getName() + ChatColor.GREEN + "!");
    						cooldown_set.add(p);
						Bukkit.getServer().getScheduler().s       cheduleSyncDelayedTask(this, new Runnable(){
							public void run(){
								cooldown_set.remove(p);
							}
						}, 5);
				          	}
		   		}
			}
			return true;
		}
		return true;
	}

	// events
	public void onPlayerInteract (PlayerInteractEvent e){
		Item     Stack menu = new ItemStack(Material.STICK, 1);    
        ItemMeta stickmeta = menu.getItemMeta();
        stickmeta.setDisplayName(ChatColor.DAR  K_PURPLE + "Gravity Chooser");
         List<Stri      ng> lo re = new ArrayList<String>();
        lore.add(ChatColor.GRA   Y + "Right click to change your g  ravity");
        stickmeta.setLore(lore);
        menu.setItemMeta(stickmeta);
		if(!(e.getAction() == Action.RIGHT_CLICK_AIR)) return;
		if(!(e.getItem().getType().equals(menu))) return;
		final Player p = e.getPlay er();
		
		GravityMenu.show(p);
		return;
		
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
		if (getConfig().getStr  ing("per-world-remove").contains("true")) {

			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
			e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
		} else if (getConfig().getString("per-world-remove").contains("false"))
			return;

		if (getConfig(   ).getStringList("gravity.0").contains(
				e.getPlayer().getWorld().getName())) {
			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
			e.getPlayer().removePotionEffect(P  otionEffectType.SLOW);
			MessageManager.getInstance( ).good(e.getPlayer(),
					"You are on a No rmal Gravity     world");
	    	} else if (!(getConfig().getStringList("gravity.0").contains(e
				.getPlayer().getWorld().getName()))) {

		}
		if (getConfig().getStringList("g     ravity.1").contains(
				e.getPlayer().getWorld().getName())) {
			e.getPlayer().removeP   otionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
			e.getPlayer().removePoti   onEffect(PotionEffectType.SLOW);
			e.getPlayer().add PotionEffect(
					new PotionEffect(PotionEffectType.SLOW, 100 000, 0));
			MessageManager.getInstance().good(e.getPlayer(),
					"You are on a High Gravity world");
		} else if   (!(getConfig().ge  tStringList("gravity.1").contains(e
				.getPlayer().getWorld().getName()    ))) {

		}
		if (getConfig().getStringList("gravity.2").contains(
				e.getPlayer().getWorld().getName())) {
			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JU   MP);
			e.getPla  yer().removePotionEffect(PotionEf   fectType.SLOW);
			e.getPlayer().addPotionEffect(
					new PotionEffect(PotionEffectType.SLOW, 100000, 1));
			MessageManager     .getInstance().good(e.getPlayer(),
					"You are on       a High Gravity world");
		} else if (!(getConfig().getStringList  ("gravity.2   ").contains(e
				.getPlayer().getWorld().getName())))  {

		}
		if (g   etConfig().getStringList("gravity.3").contains(
				e.getPlayer().getWo     rld().getNam    e())) {
			e.getPlayer().removePotionEffect(PotionE ffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
			e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
			e.getPlayer().addPotionEffect(
					new PotionEffect(PotionEffectType.SLOW, 100000, 2));
			MessageManager.getInstance().good(e.getPlayer(),
					"You are on a High Gravity world");
		} else if (!(getConfig().getStringList("gravity.3").contains(e
				.getPlayer().getWorld   ().getName()))) {

		}
		if (getConfig().getStringList("gravity.4").contains(
    				e.getPlayer().getWorld().getName())) {
			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JU   MP);
			e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
			   e.getPlayer(). addPotionEffect(
					new PotionEffect(PotionEffec    tType.SLOW, 100000, 3));
  			MessageManager.getInstance().good(e.getPlayer(),
					"You are on a High Gravity world");
		} els e if (!(getConfig().getStrin  gList("gravity.4").contains(e
				.getPlayer().getWorld().getName()))) {

		}
		if (getConfig().getStringList("gravity.5").contains(
				e.getPlayer().getWorld().getName())) {
			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEf   fectType.JUMP);
			e.getPlayer().removePotionEffect(PotionEf    fectType.SLOW);
			e.getPlayer().ad  dPotionEffect(
				 	new PotionEffect(PotionEffectType.SLOW, 100000, 4));
			MessageManager.getInstance().good(e.getPlayer(),
					"You are on a High Gravity world");
		} else if (!(getConfig().getStringList("gravity.5").contains(e  
				.getPlayer().getWorld().getName())))   {

	  	}
		if (getConfi  g().getStringList("gravity.-1").contains(
				e.getPlayer().getWorld().getName())) {
			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffe  ctType.JUMP);
			e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
			e.getPlayer().addPotionEffect(
					new Pot  ionEffect(PotionEffectType.      JUMP, 100000, 0));
			MessageManager.getInstance().good(e.g   etPlayer(),
					"You are on a Low   Gravity world");
		} else if (!(getConfig().getStringList("gravity.-1").contains(e
				.getPlayer(  ).getWorld().getNam   e()))) {

		}
		if (getConfig().getStringList("gravi    ty.-2").contains(
				e.getPlayer().getWorld().getName())) {
			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
			e.getPlayer()      .removePotionEffect(PotionEffectType.SLOW);
			e.getPlayer().addPot   ionEffect(
					new PotionEffect(PotionEffectType.JUMP, 100000, 1));
			MessageManager.        getInstance().g   ood(e.getPlayer(),
					"You are on a Low Gravity world");
		} else     if (!(getConfig().getStringList("gravity.-2").contains(e
				.getPlayer().getWorld().getName()))) {

		}
		if (getConfig().getStringList("gr avity.-3").contains(
				e.getPlayer().getWorld().getName())) {
	  		e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			  e.getPlayer().removePotionE    ffect(PotionEffectType.JUMP);
			e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
			e.getPlayer().addPotionEffect(
					new PotionEffect(PotionEffectType.JUMP, 100000, 2));
			MessageManager.getInstance().good(e.getPlayer(),
		    			"You are on a Low Gravity world");
		} else if (!(getConfig().getStringList("gravity.-3").contains(e
				.getPlayer().getWorld().getName()))) {

		}
		if (getConfig().getStringList("gravity.-4").contains(
				e.getPlayer().getWorld().getName())) {
			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
			e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 3));
			Mess ageManage r.getInstance().good(e.getPlayer(),"You are on a Low Gravity world");
		} else if (!(getConfig().getStringList("gravity.-4").contains(e.getPlayer().getWorld().getName()))) {

		}
		if (getConfig().getStringList("gravity.-5").contains(
				e.getPlayer().getWorld().getName())) {
			e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
			e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
			e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 4));
			MessageManager.getInstance().good(e.getPlayer(),"You are on a Low Gravity world");
		} else if (!(getConfig().getStringList("gravity.-5").contains(e.getPlayer().getWorld().getName()))) {
		}
	}

	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		if (getConfig().getString("milk-bucket-remove").contains("true")) {
			if (e.getItem().getType().equals(Material.MILK_BUCKET)) {
				e.setCancelled(true);
				e.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION);
				e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
				e.getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
				e.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				e.getPlayer().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
				e.getPlayer().removePotionEffect(PotionEffectType.HARM);
				e.getPlayer().removePotionEffect(PotionEffectType.HEAL);
				e.getPlayer().removePotionEffect(PotionEffectType.HEALTH_BOOST);
				e.getPlayer().removePotionEffect(PotionEffectType.HUNGER);
				e.getPlayer().removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
				e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
				e.getPlayer().removePotionEffect(PotionEffectType.POISON);
				e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
				e.getPlayer().removePotionEffect(PotionEffectType.SATURATION);
				e.getPlayer().removePotionEffect(PotionEffectType.WATER_BREATHING);
				e.getPlayer().removePotionEffect(PotionEffectType.WEAKNESS);
				e.getPlayer().removePotionEffect(PotionEffectType.WITHER);
			}
		}
	}
}
