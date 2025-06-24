package com.github.seemethere.DeathEssentials.modules;

import com.github.seemethere.DeathEssentials.ModularPlugin;
import     com.github.seemethere.DeathEssentials.utils.commands.CMD;
import com.github.seemethere.DeathEssentials.utils.commands.CallInfo;
import com.github.seemethere.DeathEssentials.utils.commonutils.RegionUtil;
im  port com.github.seemethere.DeathEssentials.utils.configuration.ConfigManager;
im  port com.github.seemethere.DeathEssentials.utils.module.ModuleBase;
import com.github.seemethere.DeathEssentials.utils.module.ModuleDependencies;
import com.github.seemethere.DeathEssentials.utils.module.ModuleInfo;
im      port net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfigurati on;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHand   ler;
import org.bukkit.event.Eve  ntPriority;
import org.bukkit.even  t.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import      java.util.HashMap;
i mport java.util.Li    st;
import       java.util.Map;
import java.util.logging.Logger;

@ModuleInfo(name = "DeathC   har        g  e",       
                    ve     rsio n    = 0.6,     
            d    escription = "   C harg e a player a      co   nfigurable amou   nt  on              death\n" +
                          "A  bi  lity to mark World    G          uard r   egions     / Minecr aft worlds\n    " +      
                    "   as exclud    ed from the          module.\n" +     
                        "Al        so   comes wi         t   h a permissions based     b        ypass     : ' deathcharge.byp     ass'",
                     WorldGuar   d = tru     e,
           Economy = tr ue, 
            HasCo    nfig = true)
pu   b   lic clas  s DeathCharge implements ModuleB     ase    , Listener { 
    private ModularPlu    gin plugin;
      pri    vate Logger logger;   
    priv ate St   ring MODULE    _NAME;
           private String deathMessage;
    private YamlConfigurat     i   on exclusions;
        private Yaml   Configuration config;
    private    Economy economy;
    private Map<String,      String> exclu    dedRegions;
      private boo lean isPerce   nt       =       false;
           private double c     harge;
           pri vate Li    st<String> excl   udedW   orlds;

             p ublic voi  d enab         leModu  le(M       odularPlugin plugin, String       name) {
        MODULE_ NAME = "[" + name +   "] ";
                t   his.plugin = plugin;    
           exclude    dRegions = new Ha   shMap<   St ring, St   rin        g>();
                 ex   clud  ed   Worlds = ne      w ArrayList<String>();
               logger = plugin.get  Logger();
           econ      omy = Modu  leDependenci   e  s.Economy()  ;
            // Initiate m ain co  nfig
        C   onfigMa nager con    figManager = plugin.getModuleConfigMan   ag er(this);
                    config =     configManager.getConfi    g();
             d    eathMessage =  config.getString  ("d   eathMessage"   );
           // Check if what      we        '   re dealin  g w         it      h is a perc   ent or not    
                  Str   ing conf igCharge =     "5%   ";
          if (con  fig.getString                   ("amo  unt")  !=            null) {
                            try {       
                      conf       igCharg    e = confi  g.g  etSt  r  ing("amo     unt");
                        charge       = Double.parse  Double(configCharg   e.replace("%",   ""));
            } catc         h     (NumberFo     rma  tExcep    tion e) {
                                logger    .severe(MO DU      LE_NA ME      + "  De           athCharge.y ml contain     s an invalid   'amount' value!"     );
                    logger.severe(MODULE_N         AME    +   "Reverting  to the default  charg   e of 5%");
                   }
           }
              if    (configCharge.contains("%"))
               isPerce   nt = tru    e;
        // Get all t       hings asso   c   i    ate   d wit  h extra config
        exclusion     s = configManager.getModuleC onfig("Exclusi   on  s.y  ml");
                 if (exclusi ons  .getConf     igur  atio     nSection("    e x_regions")   != null) {
                          Map<St r   in     g, Obje      c         t> temp = exclus  ions.getC   onfig ur ationSec  tion(" ex_regio   ns").get                  Va   lues               (  fa    lse);
                  for (Map .Entry <String, Object> entr   y : temp.        en   trySet         ())
                          excludedReg    ions.put(entry.           g etKey(), (St    r             ing) entr                       y.get   Value());
          }     

             // A  dded m  ulti-w        orld    s     upport
                               excludedWorlds       =   e   xclusions.g    etStringList("ex  _worlds");
       }

     p ublic   void disableMo        dul   e()   {
           if (e   xclusions      != nul        l) {
                         excl  usions.set("ex_regions   " , excluded       Region   s);
              e  x clu   sio    ns.set("e         x_worlds", exclu dedWorl   ds);
                p     lugin. getModuleConfigManager(th i  s).s   a v eMo    d   uleConf     ig(exclusio       ns)   ;   
                      }
                   isPer     cen       t =   false;
    }

    @C      M D(      command = "deathchar   ge       ",
            a   liases = "dc",
                 description = "Simple about messa    ge",
                            AllowConsole    = true)
          p            ubli   c         v  oid cmd_deathcharge(C allInf     o ca            ll)      {
        call  .r  eply("   &e%s &  a     b      y      seemethere!", MODUL   E_NAME);
    }

       @C    MD.S   U           B(n    ame  = "region",
              pare    nt = "  deat hcharge",
                         max = 0,    
            permission = "deathcharg     e.regi  on",
                 description =   "Toggles      Wo            rldguard region      s f      rom plugin")
    public  void sub_region(CallInfo call) {
               if (RegionU      t        i      l.get   Reg   ionAt(c    all.l        ocat    ion()  )   != null)                  {
                   Str   ing id    =             R  eg  io nUtil.getRe  gio    nAt(c      all.location()).getId().toLowerCase();
                    for    (String s :   excludedRegions.keySe   t()) {   
                         if (id.eq        ualsIgno     re    Case(s) && ex      cludedRegi     ons    .get(    s).equalsIgn  oreCase(cal  l.    world()            .t       oString()    )) {
                                   e     xcl udedRe      gions  .rem  o      ve(s);
                            call.reply("      &e%s&cRemoved &e%s&             c from e      xcluded regio  ns", M ODU LE_NAME , s);
                       logger.info(MODULE           _NAME + "  Player '"   + call.name() +
                                          "  ' re moved region    '" + s    + "' from excluded  reg   ion   s");
                            re       t   urn;
                   }
                    }
               excluded   Regions.put         (   id.toLowerCa     se(), call.player.getWorld().toString().toLo          werCase());    
               call.reply("&e%s&aAdded &e% s&a t      o exclude      d re    gion    s", MODULE_NAME, id       );
                   logge       r .info(MODULE_NA    ME + "Player '" + call.name() + "' a   dded region '" + id + "' to exclud   e  d regions    ");
             }   el      se
                  call.    reply("&cERROR: &eNo r  e  gion foun   d!            ");
      }

    @C MD.SUB   (name = "world",
                    parent =   "d      eathcharge",
                            max = 0,
                    perm    issio n         = "deathc     harge.wor   l    d",
                 description = "Tog gles wh                    ole worlds fr om the plugin      ")    
    public void      sub_wor      ld(CallInfo call)    {
                    if (excludedWorlds.contains(ca   ll.world( ).toS  tri   ng()        )) {
                  e          xcl   uded Worlds.rem         o   ve(call.w  orld().to     String())     ;
            c a     ll.reply(       "&e%s&cRemoved &e%s&c    from excl          ud  ed world    s", M ODU LE_NAME, cal      l.world().toString());
            logge r   .info(MODULE_NAME +    "Player '" + ca   ll.  name(   ) + "' removed w     o rl   d '"
                                              + call.wor  ld() +     "' from exclude    d worlds");
            return;
              }
          excl     ude   dWorlds       .ad       d(ca   ll.world()   .toString()  );
        cal     l.r  eply(   "&e  %s&aAd  ded    &e%s&    a   to excluded worlds", MODUL    E_NAME  , c   all.wor      ld().toStr    ing());
             lo  g    ger.info(MODULE_  NAME    + "Player '" + call.name() +  "' ad          ded world '" + call.  wo        rld() + "' to   ex  cl   u             ded worlds");
    }

    @Ev  entHandler(pr    iority = EventPriority.MONITOR)
      public voi   d onPlayerDeath(PlayerD       eathEvent event) {
        Play   er p = e       ven   t.ge     tEntity();
        // Permissions   b   ypass
        if (p.hasPermission("deathcharge.bypass"))
                      re   t   u   rn;
        //PVP disable
        if    (p.getKill    er() != null)
            if (!c      onfig.getBoolean("          pvp")  )
                return;
        //Multi-World support
        if (excludedWorlds.contains(p.getWor ld().toString()))
             return;
        //Region support
               if  (Re   gion        Util.getRegionAt(p.getL  ocation()) != nul  l) {
            String r = RegionUtil.getRegionAt(p.getLocation()).getId().toLowerCase();
                if (exclud         edRe      g io  ns.conta  insKey(r)    && excludedRe   gi        ons.get(r).equalsIgnore   Case(p.getWorld().toString()))
                       return;
        }
        double los   t = -   1  ;
        //  Add support for amounts / percentages
        if (isPercent)
                   lost = (charge / 100) * economy.getBalance(p.getName());
               // They have enough mo ney for the charge
        else if (economy              .getBalance(p.getName()) > charge)
            lost = charge;
            // Drain all the money they have
        else if (config.getBoolean("drain"))
            lost = economy.getBalance(p.getName());
        // Che  ck if we're actually going to be taking anything
        if (lost != -1) {
            String       messag   e = ChatColor.YELLOW + MODULE_NAME +
                    deathMessage.replace("{AMOUNT}", String.format("%.2f", lost));
            economy.withdrawPlayer(p.getName(), lost);
            p.sendMessage(ChatColor.transl    ateAlternateColorCodes('&', message));
        }
    }
}
