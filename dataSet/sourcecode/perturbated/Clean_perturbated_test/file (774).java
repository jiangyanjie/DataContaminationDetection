package   de.ar56te876mis.CraftMinecartControl.Util;

import de.ar56te876mis.MinecartControl.Utils.SearchUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import   org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public c     lass CraftSearchUtil implemen     ts SearchU       til {

    @Ov  erride
    public   Sign getSign(Locati     on   loc, int s  ig  nLine, St  ring  line) {  
             Location s  loc;
        List<BlockFace> checklis    t = new ArrayL  is       t<BlockFace>();
                   checklist.a       dd(BlockFace.NO      RTH     )    ;
           checklist.add(B      lo   ckFac    e.EAST );
        che  cklist.add       (Bl   ockFac  e.SOUTH);
        checklis t.add(     B    lockFace.WE     ST);

        l  o       c.setY(loc   .ge   t   BlockY()  - 2);
                if            (loc.get  Block().getSta                te() instanceof   Si   gn) {      
                       retur  n (Sign) loc.    getB       lo    c        k().ge              tS   tate();
          }
        
               loc.setY (l        oc.   get  BlockY() + 1);
           for (Bl    ock     Face bf : c  hecklist) {
                                    sloc = loc        .getBlo    ck().getRe  lative(bf).getLoc  ation();
                                  if (sloc.       ge  tBlock(). getState(    ) instan   ce     o     f Sign) {  
                             ret    urn (  Si           gn) sloc.getBloc    k().getState( );
                      }
                     }  
        loc. s  etY(loc.g    e     tBlock     Y  ()    + 1) ;  
              for (Bl ockFace bf : chec        klist) {
                  sloc =    lo       c.getB  l  ock().getRelative(           bf)       .get   Locatio    n();
               i   f (slo    c  .     g  etBlock().getState() instanceof Sign  ) {
                return        (Sign)      sloc.g     etBlo      c k   ()    .getState();
                     }    
        }
                      
             loc.setY(loc.getB   lockY() + 1);
              if (loc.getBlock().getState()       instanceof Sign) {
                 return              (Sign)   lo  c.g     et   Block( ).getState();
        }

                      return      null;
        }

        @Overrid   e
    public ArrayList<Sign  > getSigns(Locatio           n        loc    ) {
              Location sloc;
        List<BlockFace>      chec   k                  list =     new ArrayL   i     s     t   <    Bloc    kF     ace>();
                   checklis  t.add(Bl    oc  kFace.NORTH);
          checklist.ad   d       (BlockFace.EAST);
        ch  ec     klist.   add      (BlockFace.SOUTH  );
              checklist. add(BlockFace.WEST     );
            A      rr        ay  Lis      t<Sign> si  gn    List = ne  w ArrayList       <Sign>();

        loc.set Y(loc.getBl     ockY() - 2);
         if (loc.getBloc     k().ge tS   tate() instance of           Si  gn    ) {
            s ignLi s     t.a     dd((Si     gn) lo  c.getBlock().ge tSta        te());
         }
          
          l    oc.s                       etY(loc.ge tBlockY() + 1);   
                     f   or (BlockF      ace     b      f :       checkli   st) {
                    sloc      = loc.ge    tB    lock().getRelat ive  (bf).getLoca  tio             n();
            if (s      loc.getBlo  c k       ().ge   tSt ate()    instanceof Sig     n)  {
                signList.ad  d((Si       gn) sloc   .getBlock   ().getS tate());
             }
        }
            lo       c.    setY(l  oc.getBlockY     ()   + 1);
                for (B    lockFace b   f   : checklist) {
                     sl     oc = loc.getBlock()      .getRelative(bf).getLocati  on();
            i   f (slo         c.ge      tB  lock().   get   State(        ) instanceo       f Sign) {
                            si    gnList.      add((Sign) sl    oc.getBlo    ck().get    State     ());      
                }             
        }
           
        loc     .setY(loc.getBlockY() + 1);  
               if (loc.getBl      ock    ().getState   () instanceof Sign) {
            s     ignList.   add((Sign) loc.g     etBlock().getSt    ate());
        }

                   return    signList;
    }

    public BlockSt  a     te get  Blo      ckB    lockSta  te     (Class c, Location loc) {
        L  ist<BlockFace> checkl   ist = new ArrayList<BlockFace>();
               checkli   st.add(BlockFace.    NORTH );
        checklist.add(Bl  ock  Face.EAST);
        che    cklist.add(Bloc   kFace.SOUTH);
                  c    hecklist.add(BlockFace.WEST);
        checklist.add(Bloc  kFa   ce.DOWN);
        c     hecklist.add(BlockFace.UP);

           Location sloc;
        for (BlockFace bf : checklist) {
            sloc = loc.getBlock()     .   getRelative(bf).getLocation();
            if (c.isInstance(sloc.getBlock()   .getState())) {
                return sloc.getB       lock().getState();
            }
        }
        return null;
    }
}
