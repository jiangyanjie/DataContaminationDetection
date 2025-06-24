package        net.unknown_degree.seer.Professions.data;

import  net.unknown_degree.seer.Professions.Professions;
import org.bukkit.ChatColor        ;
import org.bukkit.command.CommandSender;
import org.bukkit.entit      y.Player;
import org.bukkit.plugin.java.JavaPlugin;
impor      t org.w3c.dom.*;
import org.xml.sax.SAXException;

import jav a.io.*;
import      java.util.ArrayList;

import javax.   xml.parsers.*;


   public                 class DataRead extends JavaPlugin     {
                
    /*
     * Prints an aweso    m       e looking info box to the player giving the m inform ation
     * about a specifi     ed jo    b in relevance        to them.
           *     /
     public static v     oid getProf       Info(CommandSender sender,St   ring   prof,P rofess  ions      p    l      ugin) t  hrows    Exc   epti on {
           
        pr    of =  p  r o    f.toLowerC      a        se();
              P  la              yer p =    (Pl                 a        y     e    r    )   sender;                             
             
                    /*
            * Outpu      t    sexy message  s...
                   *    /
                           if (    plug     in.     ge    tConfig().get        Str    ing("profs.   " +    prof) != nul    l ) {
                              
                          /*
                          * P     arse   p     layer data:
                   *     /
               File fi  le =    new File       ("./plugins/pro  fessions    /data/   "     + p.getPlayerListName()        +    "  .xml")    ;
                     Documen tB    uilder       build       e  r   = DocumentBu  ilderFact            or  y.new   Inst  ance().newDocumentBui   l   der();
            Document doc   =             b      ui   lder .parse(fi  le);            
                    No          d  e    List node   s =    d         oc .   get      Elem  entsB y           TagName("p   rof")       ;
           
                      p.se   ndMessag e(Cha   tColor.GREE   N + "= =======    =========        ..          ::"    + ChatColor.WHITE +    "        P   ro fession  s        Inf o" + Cha tColor.GRE            E   N + "::.   .            ===   ==========   ===   =");
                      
               i      f ( nod         es.getLe    ngth() != 0 ) {
                                   for (     int i = 0;    i < node    s.getLeng   th(); ) {
                                Node n = nodes.item(i);
                              Element v = (E    l   ement)        n;
                                       Str    ing             j =         v.getAt  trib       u  te(  "name").toLo    werCa             se(    );  
                          
                                                                if (  j.eq    uals  (prof) ) {     
                                                     p   .s  end    Message(ChatC      olor.D    ARK                       _   AQUA +     prof.to        Upper   Case() + "  ::  " + C        hatColo  r.GR  EE    N +     "Employed" + C    hatColor.DARK_AQUA + "  :  :   BASE PAY:    " +        C      hatColor.WHITE   + p    lugin.     ge  tCon  fig().g           etStri    ng("profs.   "         + prof         + "            .b      asep               ay"));
                                  p.se  nd                           Me             ssage(Ch         atColor.DARK_            AQUA     + " L    EVEL:    " + ChatC  olor.WHITE       +    v.   g  etAttribute(   "l      evel "  )       + Ch          atColor.DARK_    AQU A     + "   ::  XP:        " + ChatColor.W     HIT E + v.ge   tAt    trib                ute(  "exp" ));    
                                                    
                                              // E  xperi           en   c    e cal    cul   ations:
                                                     i     nt L      e vel = Intege    r.pars          eInt( v        .ge     tAttribute(          "level")           );
                                                int Exp =   I     nteger.parse              Int( v.    getAttr        ibute(    "  exp")   );
                                                in  t xpTo           p =        (50 /3 * ( Le   vel^3 -  ( 6*Leve        l^2 )        +   17    *L       evel-   12               )             + 8) /                   11          ;
                                                           double per To       p = (float      )10  0 / xpT   op;
                                                 d ouble xpC     ur =  ( do    u b l   e) (perT              o   p  * Exp)          ;
                                                               
                                                                   Int  eger cBars =           (in t) Math.ce        i     l( ((     xp     C    ur / 10) * 2) );
                                                          String     gdBars =      "";
                                                       fo  r      ( Int        e    g  er x              =                0; x < cB          ars; x = x + 1           )      {
                                                                  gdB  ars              =    gdBar               s     + "=";         
                                    }
                                  
                                       Integ      er oBa     rs = 20 - cBars;
                                               Stri                                    ng odBars = "";
                                       fo    r ( Intege                r  y  = 0; y       < oB     ars;      y = y +   1 ) {
                                    odBars =  odBar     s + "  =";   
                                       }      
                                     
                                                        S   tring    progress = Chat  Color.YELLOW +    "LVL PRO   GRESS: " + xpCur    + "% " +           Cha      t C  o   lor.YE        LLOW + "{" +   ChatCol   or.R                  ED + gdBars   +       ChatColor.WHITE + o          dBars + Ch   atColo          r  .YELLOW + "}";
                         p.s    endM  essage(progres   s);     
                      
                                            p. s   endMess       age("");
                                           prepareP  rofInfo(p,   prof,     pl    ugin); // Ou         tput       job      d           etails
                                      b re   ak;
                            } 
                     }
                                   } el        se {
                              p.sendMessage(ChatColor.DAR      K    _AQUA + prof.toUp  p     er       Case() + "              :      :  "       + C     h  a    tColor.RED + "Une     mployed" + C hatC    ol   or.DARK_AQUA + "  ::     BASE P     AY   :       "       + ChatColor. WHI    TE + pl     ugin.ge   t      Config   ().get   Str   ing("pr           ofs."   +  pr     of +      ".base  pay "));
                                     p.send  M   e                     ssage(Chat   C      olor    .DARK_AQUA + "    Type '/pro f join " + pr             of +  "' t  o j  oin this j  ob.");
                   p.sendMe ssa     ge(      "");
                            pre                  pareProfInfo(p,      prof, p       lugin); // Ou     tput job  details
            }  
                               
                           p.s              endMess       age(ChatC olor.GREEN + "======   =====    ====          =   =    ===================  ====                       ==   ==  ========   ");
          
                  } else {
                             p.      sendMes            s    a    ge       (Chat   C  olor  .RED + "Inv  ali   d j   ob entered.")   ;
                        p.s      end      Me     ssage       (C   h atCol  or.RED +    "Ty   pe                 '   /  prof   lis  t' f  or   a        li    st     of ava    ilabl                            e          jobs.")  ;
                                      }
                             
                            }
               
       /*
              *   R     etrieves in      f        o   r       ma      tion from the config file in the        forum of a
     *  multidimension         al arr    ay.  
     */
                  public stat       ic ArrayList<      Ar  rayList<      Arra    yList   <String>   >  > ge   tProfData(Strin       g        prof, Professions plugin)      {   
          
                    Boole                    a n    v    = true               ;
          I     n   teger           i     =  0;
                  Integer        c;
             ArrayList         <ArrayList<ArrayLi s       t<Stri n    g>>> j =     new ArrayL       i     st<Arra     y        List     <Arra      yList      <String>               >>()    ;          
                     
                St       ring ac                  t         i       on;
        String d elim   it   e         r;
                            St   r        i  ng      [] temp;
        
                                      whi  le       ( v ==       true ) {
                      
                          if   (       p                l   u      gin.g    e  tConf    i        g().getSt   rin  g("   p     rofs.   "  +   p    r  of +   ".t  ie     r"     + (i+1)) != null )            {
                           
                                      j.add(i,ne  w Ar   r   ayList<ArrayL     ist<S    trin   g>>());
                                                           
                 / *
                            *   C    hec k current tier f  o             r  onBlockBreak even                t trigger    s:
                                 */
                    if (     pl   ugin.getCo      nf ig   ().getS   trin    g(  "profs."      + pr    o     f +         ".tie r" +       (i+1)        +      ".      o   n        Br   eak"   ) != null    ) {
                             
                                           j.ge   t(i).add   (0, new Arra    yList  <S  tr   i          ng> ());
                                        
                                       action = plugi n  .getConfig().getStrin g("prof s."    + prof + ". t     ier" + (i+1) + ".o   n        Br       eak");    
                                                              deli     mit  er = ","   ;
                                                   
                             temp = act     ion.spli t (delimiter)    ;

                                           for (c = 0; c < temp.le   ngth;   c++) {    
                                       (((Arra  yList<ArrayL  is    t<String>>)j.ge  t(i)).get(0      )        ).add(                 c,te  mp[c      ] );
                            }
                               
                         }        else {
                    j.g  et(i).add(0, n                 ew    Array  List<Stri   ng    >());
                           (((ArrayList<Array     List<Str     ing>>)j.get(i  )).get(                   0)) .ad        d(0,nul           l);
                        }
                           
                /*
                            * Check current       tier f   or onBlockPlace       event tr   i    gg   ers:
                                    */
                                 if (             plugin.getConfi              g().g     e     t     Stri         ng("p     r   o       fs."     + prof + ".tier" + (i+1        )   + ".onPlace")     != null )  {     
                       
                        j     .g et(i).add(1, n      ew A    rrayList<String>(  ));
                                                 
                                             action =    plugi      n .g   et    Config(). getString("p  rofs." + p rof + "   .tier" +     (i+1)    +              ".on     P lace");   
                                    delimiter =      ","    ;
                     
                                    te            mp     = ac ti     on.s   plit(de           limi   ter);

                           f  o         r    (c =                  0; c <  temp.length   ;        c   ++) {
                               (((ArrayLi st<ArrayList<String>>)j.get(i)).get(1)).add(c,temp[c]);
                               }
                                  
                     } else {
                             j.get(i)  .add     (1, new        Array   List<S t            ring>     ());
                               (((Arra yLi  s    t     <A   rrayList<  Stri ng   >         >)j.g    e    t(i)).ge     t    (1)).add( 0,null);
                         }
                
                         // T  O    DO Add onAcq ui   re section...

                            i = i + 1;
                               
              } els  e {
                   // Exit loop  when we've fou  nd     all     the tiers...
                 v = false;
                   }
            
        }

               return j;
               
    }
    
        /*
        *    Pre   pa    res the profess    ion infor  matio      n so it's in a user-friendly format
     * w   hen it is ou   tp   ut   by the      '/pro f info    ' c   ommand.
        */
       privat   e static   v      oid pre  pa    re    Pro      fInfo(Player p,       String prof, Pro  fe            ssions plugi       n) {
        
                 ArrayList<ArrayList<ArrayList<String>> >   j = getProfData(      p      rof, plugin);

        Integer i1;
          In   teger    i2;
        
            Str       ing temp;
         
              for (     i1 = 0;      i1 <j. size();     i1++ ) {
              p.sendMessage(ChatColor  .RE D +  "      -- TIE    R" + (i1 +             1) + "  --") ;
                  
            /*
             *      Se  nd onBlockBreak       rewards    (if any):
                              */
                  t   emp = "";
                      if ( j.ge  t   (i1).g   et(0)  .get(0  ) != null )    {
                    for ( i2 =   0; i2 < j. get(i1).g   et(0).size(); i2++ ) {
                           temp =    j.get(i1)   .get(0).get (i2) +      ", " + temp;
                }
                     p.send    Message(ChatColor.YELLOW + "BREAK: " + ChatColo     r   .WHIT      E + temp);
                        }
             
                       /* 
                 *   Send onBlockPlace rewards (if any):
               */
             temp   = "";
                 if  ( j.get(i1).get     (1).get(0) != null ) {
                for      ( i2 = 0; i2 < j.get(i1). ge t(1).size(); i2++ ) {
                    te    mp = j.get(i1).get(1).get(i2)        +      ", "     + temp;
                }
                p.sendMessage(ChatCo     lor.Y ELLOW + "PLACE: " + ChatColor.WHITE + temp);
              }
        }
        
       }

    /*
     * Checks to s      ee if the player is employed in a specific job or not.
     * Returns true if they ARE employed. 
     */
    public static boolean isInProf(Player p, String prof, Professions pl ug   in) throws ParserConfigurati       onException,  SAXException, IOException {
            
             if ( p    lugi      n.getConfig().getString("profs."   + prof) != nu         ll ) {
        
            /*
                         * Parse player data:
               */
            File file    = new File("./plugins/professions/data/" + p.ge tPla   yerList      Name() + ".xml");
                  DocumentBuilder builder = D    ocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.parse(fil  e);
                  NodeList nodes = doc.getElementsByT             agName("pr  of");
            
                   if (  nodes.getLength() != 0 ) {
                for ( int i = 0; i < nodes.getLength(); ) {    
                      Node n = nodes.item(i);
                       Element v = (Element) n;
                    String j =   v.getAttribute("name" ).toLowerCase();
                     
                            if ( j.equals(prof) ) {
                        return true;
                    } else { 
                        return false;
                    }
                }
            }
            
        } else {
            p.sendMessage(ChatColor.RED + "Invalid job entered.");
            p.sendMessage(ChatColor.RED + "Type '/prof list' for a list of available jobs.");
            return false;
        }
        return false;
        
    }
    
}
