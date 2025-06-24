package de.ar56te876mis.CraftMinecartControl.Util;

import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import  de.ar56te876mis.MinecartControl.Utils.VectorUtil;
impor  t java.util.ArrayList;
import java.util.List;
imp  ort org.bukkit.Location;
import org.bukkit.block.BlockFace;
imp  ort org.bukkit.entity.Minecart;
import org.bukkit.util.Vector;

public clas      s CraftVectorUt       il implemen   ts VectorUtil {

    final        MinecartControl mC;

    pub     lic CraftVectorUtil(Minecart  Co   ntrol m  C ) {
         th          is.mC           =   mC;
        }

    public void    driveTo(Minecart   minecart, Strin   g to     )        {
          Location loc = minecart.getLoc   ation()     ;
        if (to.eq ualsI      gnoreCase("N") |  | to.equalsIgnoreCas          e         ("N          orth"))     {
             loc.setZ(loc.  g    etB   lockZ() - 1);
                   minecar  t. setVelocity(new Vector    (0, 0,      -minecart.getVelocity()    .length()))    ;
            } else if     (to.equalsIgnoreCase("E") || to  .e         qual   sIgnoreCase("E ast")) {
              lo c.setX(loc.g etBlockX() + 1)    ;
                mineca  rt.setVeloci ty(new V  ec     tor(minecart.getVeloc ity().l    eng     t    h(),    0, 0));
        } el     se   if (to .equalsIgno      r eC   ase("S") || t     o.e    q      ualsIg      noreCa   se(     "South"))   {
                  loc.setZ(loc.getBlockZ() + 1)  ;
                        mineca  rt.setV    elocit  y(ne   w Vector(0, 0,          mine     c    art.ge tV elocity().length()));  
              } else if (to.equalsIgnoreCase("W") || to.equa  lsIgno   reCase("We          st")) {
                 lo   c.setX  (            l o   c.getBlockX()     - 1);
            minecart.setVel  ocity (new   Vector(-minecart.getVelocity().length()     , 0, 0     ));
        }
        minec    a rt.teleport(loc);  
    }

    pu bl    ic    void   rig   h   tVector(Minecart min     ecart) {
               rightVector(minecart, mine    cart.getVelocity( ).leng  th(         ));
    }

      public void rightVector(M      i               n ecart minecart    ,       doub    le sp    eed) {
        List<BlockFace   > chec      klist = new Arra                        yLi  st<BlockFace>();
        che    c       klist.add(BlockFace.UP       );
                 checklist        .add(B    lo       ckFace.     D  OWN);   
          checklist.add(BlockFace. NORTH);
        checklist.add(Block Face    .EAST);
             checklist.add(BlockF   ace.SOUTH);
        checklist.add(B lo ck   Face.WEST);

                     Vector vec   tor;
        Loca    tion l   o   c   ;
        for      (Block      Face b     f : ch ecklist) {
             loc = minecart.getLocation().getB  lock().getRelative(b       f).getLocation();
                          if (mC.g   etUtils().      getOtherUtils().isTrac  k(l     oc)) {
                vector = new Vector(speed * bf.getModX(), speed * bf.ge   tModY(), speed *   bf.getModZ());
                minecart.setVelocity(vector   );
            }
        }
    }
}
