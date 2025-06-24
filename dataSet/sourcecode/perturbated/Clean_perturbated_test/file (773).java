package    de.ar56te876mis.CraftMinecartControl.Util;

imp        ort de.ar56te876mis.CraftMinecartControl.MinecartCont    rol;
import de.ar56te876mis.MinecartControl.Utils.OtherUtils;
import java.util.Ar    rayList;
import java.util.L    ist;
import org.bukkit.Locat  ion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.material.Lever;

public class C  raftOtherUtils implements Oth   e rU     tils {

    final MinecartControl mC;

      pu   blic CraftOtherUtils(Min   eca          rtCon       trol    mC)  {
         th               is.m  C =     mC;
    }

    @Override
    public boolean i       sTra     ck  (Loc  atio      n loc) {
        if (loc.getBlock().getType() == Ma    ter     ial.RAILS ||   loc. getBlock().getType() == Material.ACTIVATOR_RAIL   | |  loc.getBlock().getT ype() == Material.POW       ERED_RAI L |    |           loc.getBlock()          .getType                               () =  =     Material.      DETECTOR_RAIL) {
                  re   turn true;      
                  }
            return        fa  lse;
    }

    @Over  r ide
        pub   lic          Mi   neca   rt         spawnMinecartFro       mChest   (Chest chest)      {
           for (   int x   = 0; x       < chest.getI   nvent   ory(     ).get  Siz   e(); x++) {
                     B    lockFace first =       null;
                           if       (chest.  getData(   ) instanceof org.bukkit.material    .Chest) {       
                                       fi    rst = ((org.bukkit.material.Chest)   chest.get  Da      ta()).get Facing();
                                }       
                       if (chest.getInventor y().getIt    em(x) == null) {
                       continue;  
            }

            if    (chest.ge  tI           nventory().getItem(x).    getType() == Mate     rial.   MINECART) {
                                          chest.   getInve      ntory().setIt em(x,      null  );
                       return spawnM   ine          cart   AtRail(chest.getLocatio n(), "St andar   d"       , fir  st    );
               }
               if (che  st     . getInvent  ory().getItem(  x).   getType    ()     == Mat           e  rial.PO   WERED_MIN   E      CART) {
                  ches    t.getInvento       ry().s    etItem(x, null);
                   return spawn  MinecartA tRail(c      h      est.g   etLocation(), "P  owered  ", first);  
             }
                                          if     (chest.getInventory().get     Ite   m   (x).get   Type() = = Material.STORAGE_M  INECART) {
                                       chest.getInv  entory().                 se  tI            t   em(x, null);
                                 return spawnMinec artAtRail(chest.        getLocati           on(), "stor     age ", first);    
                 }
                             if (c  hest.getInventory().getItem  (x)           .getType()      == Ma  teri     al.EXPLOSIVE_MINECART)      {
                               chest.get         Inventory().setItem(x, n       ull  );
                     return spawnMinecartAtRail(c     h e    st.getLoc     ation ()  , "tn       t    ",            first);
                       }
            i          f  (chest.getInv       entory  ().      getI    tem(x).getType(  )      ==    Mate         rial      .HOPPER_MINECA    RT) {
                              chest.getInventor     y(          ).setItem(x, null);
                                 return spaw   nM    ineca   rtAtR        ail(chest.g   etLoc  ation( ), "hopper", first);
                           }
         }

           return     nu   ll;
   
                     }

    pub  lic Mi  necart spaw    nMineca   rtAtRail(     Location loc, St  ri     ng name,  BlockFa    ce first)    { 
        List<BlockFace>       ch       ecklis    t = n    ew ArrayList<Bl          ockFa    ce    >();
        if (first != null   ) {
                            checkli  s    t  .   add(first);    
               }  
                     checklist.add(B  lock   Face.SELF);
             if     (!check    list.con   tai   ns(BlockFace   .NORTH)) {
                   ch        ecklist.ad     d(   BlockFace    .NORT         H);
            }
  
              i   f        (!chec      k     list.contains(Blo   ckFace.EAST)) {
             check           list.add (BlockF     ace.EAST);
        }

                         i    f (!che  ckl  ist.cont  ains(B         lo  ck   F      ace.SOUTH)) {
              checklist.add(BlockFace.SOUTH)   ;
        }

        i   f     (!che    cklist.c            ontains(Bl    ockFace     .WES      T)) {
                      checkl  i st.ad       d(BlockF  ace.WEST);
        }

        Location spa wnloc;
               for    (Block Face bf : checklis t       ) {
              spawn     loc =   loc.getB    lock(    )       .g etRelative(   bf).get   Locatio  n();
            if (isTra      ck(spawnloc)) {
                      return        spa   wnMinecart(spaw   n      loc, name);
                   }
        }
           return     null;
              }

           @Ov      er           r       ide
    public Minec   art  spawnMinecart(Location loc, String minecartType) {
               Minecart spawn = null;
        if (m i   necartT     ype.equalsIgnoreCase("standard")) {
              sp  aw  n = loc.getWorld()   .spawn(loc,       Minecart.cl      ass)               ;
           }   else      if (minecartType.equalsIgnore Cas e("  power   ed")) {
               spa wn = l   oc.ge   tWorld().sp   a wn(lo    c,         Power        edMineca    rt.   class)          ;
        }          else      i     f (mineca     rtT    ype   .equa lsIgnor eCase("s   torag     e   "))                                 {
                    spawn =      loc.getWorld().s  paw  n(lo  c, Stor        ag eMi               ne                car                t.cla     s  s);
           }       e lse i f (mi  neca    rtT    ype  .eq  ualsIgno              r        eCase("hoppe r")) {
                       spawn  = loc.g             etWorld().s  paw     n(loc, Hopper  M  inecar  t.c    lass)   ;
           }    else   i   f    (minecar   tType.eq   ualsIgn    or eCase("   tnt")     || mine   c      artType.equals    Igno   re     Case(          "ex  plos i                        v e"   )) {
                      s pawn =         loc.getW   orld()      .spawn(   l    oc, Exp        losiveMin ec       art.       class);
        } els                e if (minecartT     yp    e.equals IgnoreC   ase        ("  Spaw  ner              ")) {
                       s pawn =     loc              .getWor ld().spaw             n(loc,    SpawnerMinec      art.c l           a  ss); 
        }

        mC.ge   tUtils()     .g               et        Ve                                        ct    orUtil().      rig    htVecto    r(s          pa  wn  , 2); 
          ret    urn    sp       awn;
    }     

              @O    v       errid   e
                 public void pr  essNea      rbyLever(Loca tion loc)      {
                                           f         or (int x = loc.getBlockX() - 2;  x <= loc    .getBlockX   () +   2        ; x++      ) {
                   for (int y = loc.getBlockY()       -        2; y       <= loc.getBlockY   (       ) + 2; y++) {
                       for (int z    = loc.getBlockZ(   ) -   2; z <=         l       oc.getBlock     Z(    ) +   2; z++) { 
                           Block b         lock = new Location(lo     c   .ge tWorld(),   x,   y, z). getBloc    k();
                             if (block.getTy   pe()    ==            Material.LEVE    R) {
                              final BlockState    lev   erB      S = block.getState() ;
                            final Lev      er lever = (Lever) leverBS.ge       tD ata();
                                             n  ew Thread() {
                                                     @Override
                                    public void run(  ) {
                                        lever.setPowered(true  );
                                        l   everBS  .setData(lever     );
                                            leverBS.u     pdate();
                                      try {
                                      Thread.sl     eep(1000);
                                  } catch (Inter  ruptedException ex  ) {
                                      }
                                    lever.setPo     wer    ed(false);
                                       leverBS.setData(lever);
                                   leverBS.update();
                            }
                        }.start();
                        return;
                    }
                }
            }
        }
    }
}
