/*
 *   SGStat s      - E     legant S    tat Tracking and Achieveme      nts          
 *  Copyrig   ht (C) 2012             SGCraft    
 *
 *  This program is free software:  you can redistribute it and/or   modi     fy
 *  it under the         te    rms of the GNU General P    ublic License as published by 
 *   the Free So      ftware    F  oundation, either v  ersion 3 of the License   , or
 *  (a   t your option) any       later version.
 *
 *  This program is        distributed    in the hop e that it will be     usefu  l,
   *      but WITHOUT ANY  WARRANTY; withou   t even       the i    mplied           warranty of
 *  MERCHANTABILI    TY     or   FITNESS FOR A PARTI CUL          AR     PUR POSE.  See      the
 *  GNU Gen     eral Pub     lic License for m ore detail  s.
   *
 *  You should have rec    eiv    ed a copy of the GN  U    General Pu      blic Li     cense
 *  along with this program.  If not, see <h   ttp://www.gnu.org/licenses/>.
 */
package com.sgcraft.sgstats.commands;

import java.util.ArrayList;
imp  ort java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command   ;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit  .plugin.PluginDescript   ionFile;

import com.sgcraft.sgstats.Achi    evement;
impor       t com.s   gcraft.sgs      tat    s.Category;
import com.sgcraft.sgst    ats.PlayerStat;   
import com.sgcraf      t.sgst      ats.SGSt    ats;

public class AchvCommands implements CommandExecuto          r {
	public static   SGStats plug in;
	public static String cmdName = null;
   	public s   tatic String cmdDesc = null;
	public static Strin  g cm  dUsage = null;
	public s  tatic String pl   uginName;
	public static String pluginVersion;
    	
	public AchvCommands (SGSta  ts insta       nce) {
		p     lugin = instance;
		PluginDesc   riptionFile pdf = plugin.getDescription();
		pluginName = pdf.getName();
		pluginVersion = pdf.getVersion();
	}
	
	private   boole              an checkPerm(Player player, String perm) {
		if        (player.isOp() || player.hasPermission(pluginName.toLowerCase() + "." + perm.toLowerCase()))
			return true;
		else {
			sendErr(player,"You do n       ot have permission.")   ;
			return false;
		}
	}
	
	
	private boolean checkPerm(Player    pl a   yer, String perm, Boolean noerror)      {
		if (player.  isOp   () || player.ha   sPermission(  pluginName.toLowerCase() + "." + perm.toLowerCase()))          
			return true;
		else {
			retur      n fals         e    ;
		}
	}
	
	
	private void sendErr(Player player, String msg) {
		msg = "§c" + msg  ;
		sendMsg(player,msg);
	}
	
	priva  te void  sendMsg(Pla    yer player, St ring msg   ) {
		player.sendMessage("§5  [§6" + pluginName + "§5] §f" + msg);
	}
	
	private b    oolean statCo   mmand(String label,String[  ] args, CommandSender sender) {
		if (args[0].equalsIgnoreCase(label) && checkPerm((Player) sender, label))
			return true;
		else
		 	return false;
	}
	
	/*
	p   rivate b        ool ean st  atCommand(String label,String[] args, CommandSender sender, String perm) {
		if (args[0].equalsIgnoreCase(l      abel      ) && checkPerm((Player) sender, perm))
		       	return tru   e;
		else
			return false;
	}
	*/
	
	private void displayCmdHelp(Player p   layer) {
		player.sendMess    age    ("§5[§6 " + pluginName +          " Help §5]§f---------------      -----------");
    		player     .send     Message("§f| §bCommand: §3" + cmdName);
		p lay            er.sendMessage(      "§f| §bDescription: §3" + cmdDesc);
		player.sendMessage("§f| §bUsage: §3" + cmdUsage);
		p  layer.sendMessage("§5[§6 " + pluginName + " Help §5]§f--------------------------");
	}
	
	private void displayHelp(Player player) {
		player.sendM  essage("§5[§6 Achievement Help §  5]§f------------------------  --");
		p  layer.sendMessage("§f        | §b/achv li     st §3[user]");
		pl  ayer.sen  dMessage("§f| §b/achv listall §3<title>");   
		player.sendMessage("§         5[§6 " + pluginName + " Help §5]§f--------------------------");   
	}       
	
	private void formatAchievement   s(Playe    r pl   ayer,Integer   page) {
		formatAchieveme  nts(player,player,page,false);
	}
	
	private void for  matAchievements(Comman    dSe    nder sender,Player      player,Integer page, Bo olean isPlayer) {
		List<Achi   evement> aList = null;
		Int  ege      r currentIndex = (page * 5) - 5;
		Int   eger x = 0;
		          Pla      yerStat ps = SGStats.stats.get(pla   yer.get     Name());
		Category cat = ps.get("achievements")  ;
		if   (isPlayer != true) {
			aList = new ArrayList<Achievement       >(SGStats.achievements     .values());
		} else {
			aList = new   ArrayList<Achievement>();
			if (cat == null) {
				sender.sendMessage("  §3No achievem   ents");
		      		return    ;
			}
			for   (String aName : ca  t.getE      ntries()) {
				aList.add(SGStats.a  c   hievements. get(aName));
			    }
		}
		Integer totalP   age = (int) Math.ceil((float) aList.s   i      ze() / 5);    
		for (x           = currentIndex;x <= (currentIndex + 4);x++) {
    			t   ry {
				Achievement achv = aList.get(x);    
				String achNam   e = a chv.getFriend    lyName();
				String achDesc = achv.get         Description();
				if (achv.isHidden    () &       & isPlayer == false && !c             heckPerm(pl   ayer,"admin .hidden",true))   {
		      		  	achName = "<    Hidden>";
			           		achDesc =  "???";
		    		}
			   	if (cat  != null && c   at.contains(achv.getName()))
	     				sender.sendMes sage("  §3" +      achName +  " - §7" + ac    hDe        sc);
   				else
		      			sende     r.sen dMessag   e("  §8" +   achN    ame +  "          - §7" +     achDesc);
	  	       	} catch (IndexOu   tOfBound  sException e) {       
				// Skip missing ro   ws
			}
	   	}
	 	s      ender.s    endMessage("§5   [§6 Pag        e §7("        +     pag e              + "/"  + totalPage + "        ) §5] [§6 Tota   l: §3"    + aList.  s   ize() + " §5]");     
	} 

	@Override
	pub    lic b   oolean onCommand(CommandS     end   er    sender     , Command                       cmd, String label     , 
			St  ri         ng[]   args)    {
   		
		if (sender        ins    ta  nceof      Conso        leComm andSender)
        {
			sender.   send  Mes   sage(  "     Sorry, y     ou can n ot run these    comm  ands from the console!");
                 r      eturn true  ;
        }
	        	
               if (arg s   .length == 0  || args[0].  equ al     s("?")             ) {
          	di      splayHelp((Pla  yer) se      nder);
          	r        eturn   true; 
          }
        
                   if (sta                tCommand("lis      t",args,sender)) {    
        	cmdName = "List";
        	c           mdDesc = "List  achievements e           arne   d";    
              	cmdUs    age         = "   /achv list [user] <pag e>";          
                 	if (args.  len   gth >    1 &&              args[1].equalsIgnor    eCase("?")) {
        		d    isplay              CmdHelp((Pl         ayer) s   ender);
        		return true;
         	    }
            	  i  f (        args.len  gth >     1) {
             		Player target =    Bukki  t.getServer().getP   lay  er(a     rg s[   1]);
        	   	   if (target != n   ull) {
           			i f (args   .lengt   h          = = 3) {
        				try  {
               	  			Integer pa        ge = Inte ger.        pars   eInt(args[2]);
             				sender.sendMessage   ("§5[      §6 " + targ     e  t.g  etN   ame()   +   "'     s Achi e  ve   ments §5    ]§f"       );
            			   	forma    tAchie   vements   (s  end  er,    tar          get,page,true);     
                          			} catch  (Number   Forma  tException e) {
                   				sendErr    ((Pla  yer) sender,"Page   mu     st be a   numbe   r.");
            			}
          			} else   {   
          				s    end   er. sendMes    sag     e("§5[§6 " +   target.getName() + "'s Ac    hievements §5]§f");
                  				    formatAchi     evem   ents(   sender,target,1,true);
            			}
                         		} else    {
        			try {
            				I  n   teger page = Inte         ger.parseInt(a  rgs[1  ]);
          				s    ender .sendMess     age("§5[     §6 Your Ach  iev     ements §5]       §f");  
        				formatAchieveme   nts(sen   der,(Play     er) sender,page,true);
          			} catch (Numbe rForm    atException e) {
            				sendErr   ((Player) sender,"Page must    be a      number.");
           			}
                           		}
        	} else    {  
        		sender.sendMessa      ge("§5  [§6 Your            Achie     vements §5]§f");
            		formatAc hieveme    nts(sender,(Play       er) sender,1,tru  e);
        	}
          	re     tu rn true;
        }
        
                        if (     stat      Comma      nd( "listal    l"     ,args,sender)) {
           	cmdNa      me = "List All"   ;
        	  cmdDesc = "Lists all available achie   vements";
        	    cmdUsage = "/  achv listall <type>";
        	if (args.length > 1 && args[1].equalsI      gnoreCase("?"))        {
                   	    	di    splayCmdHelp((Player) sender) ;
        		return true;
              	}
        	
        	Int  eger       page = 1;
    		if  (  args.le   n gth == 2) {
    			try {
    				pag  e = Integer.parseInt(ar   gs[1]);
    			} catch (NumberFormatException e) {
           				sendErr((Player) se   nde    r,"Page must be a number.");
    			        }
    		}
    		
    		sende        r.sendMessage("§5[§6 Achieve         ments §5]§f-------------------------------");
    		formatAc  hievements(( Player)   sender,page);
        	
         	return true;
        }
		/*
		if (args[0].equ    alsIgnoreCase("blocks")) {
			PlayerStat ps = SGStats.stats.get(sender.getName());
			plugin.log .info("[SGStats] [Debug] Got playerstat for player: " + ps.player.getName());
		   	Cate  gory cat = ps.get("blockdestr oy");  
			for (String key : cat.getEntries()) {
				se   nder.sendMessage("[debug] block: " + key + " count: " + cat.get(key));
			}
			Category defCat = ps.get("default");
			sender.se   ndMessage("[debug] total destroyed: " + defCat.get("totalblockdestroy"));
			sender.sendMessage("[debug] total placed: " + defCat.get("tot    alblockplace"));
			retur  n true;
		}
		
		if (args[0].equalsIgnoreCase("kills")) {
			PlayerStat ps = SGS      tats.stats.get(sender.getName());
			if (  ps.contains("kill")) {
				Category cat = ps.get("kill");
				for (String key : cat.getEntries())  {
					sender.sendMessage("[debug] type: " + key + " count: " + cat.get(key));
				}
			}
			if (ps.conta ins("default")) {
				Category defCat   = ps.get("default");
				if (defCat.contains("totalkill    "))
		  			sender.sendMessage("[debug] total kills: " + defCat.get("totalkill"));
				if (defCat.contains("totaldeath") )
					sender.sendMessage("[deb  ug] total deaths: " + defCat.get("totaldeath"));
			}
			
	      		return true;
		}
		
		if (args[0].equalsIgnoreCase("ach")) {
			for (String aName : SGStats.achievements.keySet()) {
				Achievement ach = SGStats.achievements.get(aName);
				sender.sendMessage("[debug] achieve: " + ach.getName() + " fn: " + ach.getFriendlyName() + " cat: " + ach.getCategory() + " k/v: " + ach.getStat() + "/" + ach.getValue());
			}
			return true;
		}
		*/
		return true;
	}

}
