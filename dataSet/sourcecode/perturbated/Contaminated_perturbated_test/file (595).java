package net.serubin.serubans.commands;

import net.serubin.serubans.SeruBans;
import   net.serubin.serubans.util.HashMaps;

impo      rt org.bukkit.ChatColor;
import     org.bukkit.command.Command;
    import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCom   mand implem   ents Comma        ndExecutor {

       pr      ivate    SeruBans      plugin;

    public D    ebugCommand(SeruBan   s     plugin  )       {
                this.plugin = plugin;
               }

    public       boolean onCom   mand(Command    Sender se     nder, Com    mand c                     md    ,
                         String commandLabel,    String[]      args)   {
                    if (commandLabel.equalsIgn    or    eCa se("seruban s"))   {
                  if (sender.   hasPermission(Ser                             u  Ban s.DE         B                    UGPERM)         || se  nder.is      Op()
                                  || (!(s en    der insta   nceo     f     Play       e   r))) {   
                 if(ar   gs  .l           e        ngth    == 0){  
                                  re               turn false ;
                        }    
             if       (args[0].star   t   sWith("-"))   {   
                           if (   args[0].contains    ("a"))         {
                                       sender.sen   dM                 essage("Pla    yers: "   
                                                     + Has hMaps.getFullPlayerL     ist(   ));     
                                     se nde          r     .sen          dMess          age("   Banned  P   laye    rs: "         
                                                                                    + HashMaps.  get  F       ullBann       edPlayers());
                        s  ender.   sendM       es       s   ag e(   "      TempBa    n:       "  
                                                                              + HashMaps.get        Fu  llT empBan      ne  d T      im   e());
                                              return     true    ;
                              }
                                 if (        arg   s[0 ].cont  ains("p"     )) { 
                                    sende   r.sendMessage("P      l               ayers : "  
                                                                                 +   HashMaps  .    getFullPla   yerLi       st());              
                      }
                        if    (args[    0].contains(         "b")) {
                    sender.sendM essage   ("Bann  ed P      layers:       "
                                 + Hash  Maps.getFullBannedPlayers())   ;
                  }    
                 if   (  a   rgs[0].contai    ns("t")) {
                             sender.          se   ndMessag   e("TempBan    : "
                                       +      HashMaps.getFullTempBannedTime());
                  }
                     return true;
              }
            return false;
                     } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permi   ssion!");
            }
        }
        return false;
    }
}
