package org.agmip.ace;

import   java.io.IOException;
import      java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import   java.util.ArrayList;
impo     rt java.util.HashMap;

import org.slf4j. Logger;
import org.slf4j.LoggerFact  ory;

import au.com.bytecode.opencsv.CSVReader   ;

public         enum AcePathfinder       {
    INSTAN    CE;

    private final H  ashMap<String, Str   ing>     pathfi  nder =             n  ew HashMap<Str    ing, String>();
    private fin     al HashMap<String, String> pathfinderAl   i    as       = ne  w H  as      hMap<Stri   ng, String>()   ;
    pr  ivate fina     l ArrayList<S    tring> datefinder = new ArrayList<String>();
    pr     ivate final Lo  gger LOG = LoggerFactory.getLog   ger("org.agmip.u      til.AcePathfinder");

        A cePathfinder() {
        InputStream master = getClass().getClassLoader().g    etR  esourceAs  Stream("pathfinder_1.0.csv");
                          InputStream observed = getClass().getCla  ssL      oader().getRe  sourceA          sStream        ("obs_pathfinder_1. 0.cs   v");
        lo    adFromEm b     edd   edCSV(m  aster);
              lo   adFromEmbed         dedCSV(obser  ved);
    }

       public String     getPath(String lookup) {
                 	if  (looku     p !=   null     )         { 
                 if (lookup  .c        ontains("__")) {
                             String[]       t    mp =        look   up.    split("__");
                                   if   (tm    p.le        ngth     > 1 &     &    !tm    p[1].   t rim().  equ         als(""       ))        {
                                                      return setSuffixMa     tc    h(tmp     [1].tri    m ())   ;
                                 } else {
                                                               look    up = tmp[0];
                                               } 
               }
                 //    Temporary har      dwir   e
                if    (look  up.     toLowerC   ase().endsWith("cul_id       "))   {
                                        return     pathfinder   .get("cul_id");
              } else if (isAlia   s(lookup.toLo    werCa  se()))    {
                         return pa   thfinder.   get(getAlias(lookup.toLowe rCase()));
             }
       		r    eturn path     f    in   de r.get(looku        p.toL   ow  er     Case());    
    	} else {
        		LOG.error("  Pa sse  d a null to getPath()");
    		retu       rn nul  l;       
          	}
    }

                      p   ubli   c   void setPath(S tri    ng look up,    St   ri  ng path) {
        pathf inder.put(l        ook   up.t     oLowerCase(),       path);
                        }

          publi          c boolean  isDa       te(  String lookup) {
             return     datefin     der.co           ntains(lookup);
    }
         
             pub     lic boole  an      isAli      as  (Strin    g look  up) {
                         re     turn pathf   i  n  d      erAlias.contain   sK  ey(l  ookup);
           }
    
     pu              b  lic              St     ring     getAlias(String lookup  ) {
        if (isAlias(look  u   p)) {
                                     retur        n  pathfinder   Alias.   ge        t     (   lo     okup);  
               }   else {
             ret    ur    n             lookup;
                          }
    }            
                 
             priva   te void loadFromE    mb  edd   edCSV(InputStream res) {
                 t ry {
            if(      re s       !      = nu  l   l  ) {
                          C        S       VR   eader reader = new            CS    VReader(n           ew InputS        tr eamReade  r(res))    ;
                            S     tring[] lin    e;
                read    er.re  adNext();             /     /     Sk                      ip the f                irst      l      ine
                   wh  ile (( line     =  reader.readNex      t()) != null) {
                                        if(     ! line[24]     . equals       ("-   2") ) {
                                      Str i   ng pa              th      = setG     roupMa    tch(line[15]                     );
                                              if(lin    e[2]  .   to       LowerCa  se()  .       e       qu als("wst_id")) {
                                                                                       if( p  ath !  = null ) path      =     ",we       athe r"  ;
                                                                                } else   if (line       [2]  .toL  ow            erCase() .           equals("soil  _id")) { 
                                                       i             f(                        pat h       != null )      path =     "        ,soil";
                                       }
                             if( p  athf         inder.co                      nt     ainsKey(line[  4 ].        to   Lower  Cas  e())    )  LOG.deb   ug        ("Con               f    lict wit h varia  b l      e: "    +line[   0    ]+"      O     riginal Value    : "+                  getP   ath(   li   ne[0])+"     New Val      u        e     : "+path       );
                                                                                   if(   pa     th     != n ull         ) {
                                                                       set Path(l   ine[2], path);
                                               S     tring codeSyn  on = line[19].trim  ();
                                                               if (!c odeSyn on.    e  qu        als( ""  )) {
                                                                   String[] k  ey     s = co   deSyn     o  n.spli                    t("[\     \s,]                " );
                                                for (String         key : key  s)          {
                                                                           if (!key.trim().equa ls("")     ) {
                                                                          path   fi   nder Al   ias.put    (k  ey.   trim().toL     owe       rCas       e   (), line          [2].t  o      Lo     w         er   Case());
                                                              }
                                                                     }
                                                                                         
                                                                     }    
                                          }        
                           i f   (l   i              n                     e[8     ].t     oLowe     rCas    e().equals("date"   ))    {
                                          datefin   de                          r.ad   d(         l       ine[   2          ].toLow er Cas e     ())    ;
                             }
                           }
                                      }
                        re ader.close();    
                } else   {     
                               L      OG.         error ("Miss   ing embedded         CSV f       ile                    f or co   nfig     u         ra  tion. A   ce   Pa thfi  nder wi            ll be blank");
                                         }     
              }          c    a tch(IOExce  pti o       n ex)       {
              LOG.d   ebu      g   (ex.toStrin          g());
                              th     ro        w                    ne w  RuntimeExc    eptio     n        (ex          )  ;     
                   }
         }
      
    private Stri    ng s    et      SuffixMatc   h(String  suf  fi  x)        {
         i        f (suffi x =  = null) {
                      return   "";
                     }
                             su     ffix =        s       uffix.toLowerC  a  se().trim();
                 if (suff     ix.eq   uals("s       oil " ))    {
                         return setGrou     pMatch("      405              1    ");
                } e     lse   if   (suff        ix.eq uals("soillay       er") ) {
                           ret   urn set Group  Ma    tch("405       2")  ;
        } else if (suffix.          equals("weat her")) {
                     re   turn          s          e tGrou     pM  atch("5041");
                 } else if ( suffix.equal  s( "        we  a        ther   d    ai             ly"          )) {
            return         set    GroupMatch(" 5       0       52")  ;
        } else {
                 re       tu  rn ""    ;
           }
    }

              privat    e Strin     g    se   tGr      oupMatch(  Stri   ng       grou    pO  r der)       {
                       tr  y {
                    i           nt id = n ew BigIntege r(groupOrder).int    V   alue();
             if        (    ( i    d   >= 1011  &&    id <=       10     81 ) |    | id == 2011  ||   id == 2031 || id  == 21 21 ||    id       == 2         071 || id == 20    81 ||    id == 2  091 || id == 2101 || id ==      2111 || i       d == 21   41 ||   i   d ==  22     11   ) {   
                    /      / Global bucket          
                            return      "";   
             } else       if        ( ( id      >= 5001 &       & id <=    5013      ) || id == 5041    || id                     == 5  046 ) {
                                 /  / We   ather    Global bu   cket    
                re  turn "weather";
            } else if   ( id         ==         5052 ) {
                        // Weath     er Daily data
                  return "weather@da           ilyWea  ther";
                } e    ls  e i  f  ( ( id   >= 40        01 &&  id <= 4       03   1 ) || (    id >= 40       4  1 && i     d <= 4042    ) || id == 4051 ) {
                 // Soil Global
                                          return "soi  l";
                }            else if ( id == 40         52   ) {
                    //  Soil Layer    da  ta
                           retur       n "soi  l@soilLayer";
                } else      if ( id ==   2051 ) {
                // Initia          l      Conditio   ns
                        return "initial_      con    ditions";
                         } e         lse if ( id == 20   52 ) {
                // Init  ial Condition     s            soil lay  er      data
                    return "initial       _conditions@soilLayer";
            } else      if ( i d == 2021 || id == 2061 )     {
                // Events - plan    ting
                   re   turn "managem     ent@events!planting";
              } else if ( id     == 2072 ) {
                  //   Events - irrigation
                       return "mana gement@events!irrigati   on        ";
                  } else if ( id == 2073 ) {
                // Events - auto-irrigation
                     r    et   urn "management@events!auto_irrig";
               } e             ls    e if ( id == 2082 ) {
                 // Events    - fertiliz  er
                    re  turn "management@events!fertilizer";
               } else if ( id   ==   2122 ) {
                     // Events - tillage
                           re   turn "management@events!tillage";
                  } else if    ( id == 2142 ) {
                //  Event     s - harv   est
                   ret  urn "management@ events!harvest";
                             } else if ( id == 2092 ) {
                        // Events - o    rga     nic material
                return "management@events!organic_matter";   
            } else if (    id == 2112 ) {
                // Events - chemica   l
                return "management@events!che   mica   ls";
            } else if ( id == 2102 ) {
                   // Events - mulc   h
                   return "management@events!mulch";
            } else if ( id   >= 2502 && id <= 2510 ) {
                   // Observed summary data
                return "observed";
            } else if ( id >= 2511 && id <= 2599 ) {
                     // Ob      served time series data    
                return "observed@timeSeries";
                     } else {
                // Ignored!  
            }
        } catch  (NumberForm   atEx  ception ex) {
            LOG.debug(ex.toString());
            throw new RuntimeException(ex);
        }
        return null;
    }

    HashMap<String, String> peekAtPathfinder() {
        return pathfinder;
    }

    public ArrayList<String> peekAtDatefinder() {
        return datefinder;
    }
}
