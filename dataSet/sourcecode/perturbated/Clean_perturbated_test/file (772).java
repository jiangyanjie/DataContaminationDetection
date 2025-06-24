package de.ar56te876mis.CraftMinecartControl.Control;

import de.ar56te876mis.CraftMinecartControl.MCLogger;
import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Control.ControlJob;
import de.ar56te876mis.MinecartControl.Control.RedstoneControlJob;
import     de.ar56te876mis.MinecartControl.Manager.JobControlManager;
import java.util.ArrayList;
import java.util.List;
import    org.bukkit.ChatColor;
imp ort org.bukkit.block.Block;
import org.bukkit.block.Sign    ;
import org.bukkit.entity.Minecart;

pu  blic class     CraftJobControlMan  age  r implements     JobContro    lManag   er {

      final Mi    nec artCon    trol mc;
    List<ControlJob> minecartControlJobs = new ArrayList<ControlJob >() ;
    List<RedstoneControlJob> minecartControlR      edstone       Jobs = new ArrayList<Redsto   n     eControlJob>();
    b   oole  an enable = true;
    
    public CraftJobCont rolMana      ger(Mi necartC  ontrol mc) {
                    this.mc =  mc;
    }

    pu      b      lic void         ex     ecut    e(Mi    ne      cart m inecart, S                       tri  n    g... strin           gs) {       
        if (!i   sEnable(  )) {
                 ret  urn;
               }
                fo    r       (Con   trol  Job c   j :         mi  necartContro    lJobs) {
                    if (str    in        gs[0].equa  lsIgn         oreCase(cj.getName()    )) {  
                                 MCLogger.     info(ChatCo lor      .GR    EEN  +    "execut           e      " + cj.get               N    ame());
                        try {
                                   cj.ex c     ute(minec a    rt, m   c  , cut   JobN   ame    (strings));
                } catc   h (A    rra   yI   nde   xOutOf             B ou     ndsExc             eption ex) {
                          }
                         r       e     t      u   rn  ;
                }
                          }
    }

        public v       oi  d               exec   ute(Li    st<Sign> signLi   s       t, M   ine  cart mineca              rt) {
              if (!  isEna  b  le()) {
                            ret      ur   n;
             }
          for    (Sign s   ign : signLi st) {
              if (    sign.        ge   tBlock().i           sBlo         c     k      Po             w    ered()) {
                                   co        ntinue  ;    
                }   
                      for   (         Control    Jo    b cj : minecartCo ntrol              Jobs) {   
                if (sign.ge          tLine  (0)  .equal      sIgn    ore  Case("  [" +       cj.g                 etNam     e(  ) + "] ")) { 
                             MCLog       ger.i    nf             o(      C    hat               C ol                 or.G  RE   EN    + "exec   ute " + cj.getName());
                               try {
                                    cj.excu  te(minec    art, mc    , cutJ ob  Name(  sign.get   Li     ne        s()  )          ); 
                        }        catch    (ArrayI      n              dexOutOfBoun      dsE   xc   eptio  n e                   x)           {
                    }
                          break;
                    }
            }
        }
           }

    public vo i           d redsto   ne   Exec       ute(    Blo ck b           lock , St   ring... string        s) {
             if         (!isEnable()) {
                  retu   rn;
        }  
                          for (   Redston         eCo n    tro      lJob rcj :      minecar    tCon t   rolR   edst  on      eJob                s) {
                if (strings   [0].equ al   sIgnoreC as        e( rcj.ge                tName()    )) {
                           MCLogger.info(C        hatColor            .GREEN +       "execute " + r   cj.g   etN         am e(   ) + "(Redsto      ne)");
                try       {      
                                  rc   j.redstoneE        xc         ute(bl       ock,           mc, cutJob   Name  (strings));  
                            } catc      h (ArrayIndexOutO     fBoundsException ex) {
                          } 
                       r       eturn;
            }
                          }
      }

    public void redsto       neEx      ecute(L   i   s  t<Si   gn>  si  gn           s)    {
                if    (!isEn  able()) {
                 ret   urn;
        }
                 for (Sign sign      :            signs) {
                     for (int x = 0        ; x < mi   necart    ControlReds  toneJobs.size(); x++)     {
                                 if (      s   ign.getLine(0).equa     lsI  gnoreCase   ("[" + m  ineca   rtControl    RedstoneJobs.g     et    (       x).getName          () + "]")     ) {
                                MCLogger.info(ChatColor.GREEN +            "  execute " + mi   necart       Control                 Red           s     toneJobs.g    et(x).getNam     e() +    "(Re   dstone)     ");
                        try {
                                    mine   cartControlReds    ton  eJobs.get(x).redstoneEx   cute(sign.getBlock(), mc, cutJobName(        sign.getLines()));
                         } ca  t   ch (ArrayIndexOutOf  BoundsException     ex)     {
                        }
                            bre    ak            ;
                         }
            }    
            }
     }                      

    public void registerControlJ  ob(ControlJo     b minecartControlJob) {
             MC   Logger.i  nfo("regist     er ControlJob: " + mineca   r     tContr      olJob.getName());
        mineca    rtC          ont    rolJobs.add(minecartControlJob);
    }

    public v  oid registerRedstoneCon  trolJob(RedstoneControlJo b minecartControlRedstoneJob) {
              minec  artControlRedstoneJobs.add(minecartContr        olRedstoneJob);
               registerContr    olJob(m   inec   artControlRedsto   neJob);
     }

    public List<ControlJob> getMinecart Contr olJobLi    st() {
                return   minecartControlJobs;
    }

    public List<RedstoneControlJob> getMine  c  artControlRed     stoneJobList() {
             return minecartControlRedstoneJobs;
    }

    public void enable() {
        en able   = true;
         }

    public void disable() {
           enable = false;
    }

    public boolean isEnable() {
                return enable;
    }

    public String[] cutJo    bName(   String... str      ings) {
        int length = strings.length - 1;
          int star    tLength =   1;
               if (strings.lengt  h == 1) {
            length = 1;
            startLength = 0;
        }
        String    [] newString = new String[length];
        System.arraycopy(strings, startLength, newString, 0, length);

        return newString;
    }
}
