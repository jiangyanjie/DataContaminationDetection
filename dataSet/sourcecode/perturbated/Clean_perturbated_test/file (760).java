/*   
   *     AP(r) Computer Science G ridWorld Case       Study:
 * Copyright     (c)     2005-2006 Cay S. Horstmann (ht     tp://   horstmann.com)
      *
   *     This code is    free software; you can      redistrib  ute it and/      o   r m    odify
 * it    und      er the  terms of the        GNU Ge   neral Public License as publishe  d by
 * the       Free Software Foundation.
 *
 *   This       code is    distribu   ted in the ho     p   e that it will     be usef   ul,
 * but WITHOUT   ANY WARRANTY; with      out even the implied         wa    r   ranty of
 * MERCHANTABILITY or    FITNESS FOR   A PA   RTIC ULAR PURPOSE.     Se e      the
 * GNU General Public    License for m       ore d      etails.
   * 
 *   @author C    hris Nevison
 * @author Ba   rbara Cloud Wells
 * @author Cay   Horstmann
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
im      port info.gridworl  d.gr         id.Grid;
import info.gridwor    ld.grid      .Location;

import java.awt.Col  or;
import ja va.util.ArrayList;

/**
 * A <cod      e   >CrabCritter</code> loo        ks at   a limite   d set        of ne     ighbors wh      en       it eats and mov   es.
 * <br />
 * Th is class is not tested on     the AP CS A        and AB ex   ams.
 */
publ  ic c      las   s C           rabCr    it  t  e  r exten     ds Critter
    {
    public CrabCritter()
         {        
            se  tColor(Color.RED);  
                }
   
         /         **
     * A crab ge    ts    th e actors   in the thre  e locations immediat   ely in      front, to its 
     *     fr    ont-right and to its front-left
     * @return a list of     actors oc  cup         ying thes           e l       oc    ations
           */
      public   Array Lis   t<Ac  tor> getAc    t  ors()
         {
        Arra     yL    ist<      A        ctor> a   cto  rs =        new ArrayList<Actor>();
               i   nt[] dirs =
              { Lo   cation.A      HEAD,      Location.HALF_LEFT      , L      oca        tion.HALF_RI       G    HT };
          for (Locati  on    loc       :   ge            tLocati  onsInD  irections(d   irs))
        {
                           Actor a =  ge   t       Grid().g e  t(lo   c  ) ;
                            if (a !   =       null)
                 ac         tors.    add(a);
                  }

                    r  eturn act  or      s;
       }

      /**
     * @return list of empty   locatio   ns immedi   atel  y to the right and    to the left
                    */
       p    ublic   Array  List  <L   ocation> getMov  eLo     cations()
    {
                          ArrayLi  s t<Loc      ation> locs   = new ArrayList<L oc   ation     >();
                int[] dirs =
                             { L  oc     ation.LEFT, Loc        ation.RIG HT };
               fo  r        (L       oca       t     ion loc : ge      tLocation           sInD      ire   ctions(dir  s     ))
                               if (g etGrid  ().get(loc) == null)
                              locs       .add(loc);

                      retu   rn locs;
             }        

    /**  
     * If the c   rab critte   r doesn't  m    o      ve,   it    ra   ndoml           y tu         rns l   eft  o  r right.
        */
    public void     make    M  ove(Lo  cation loc)
    {
        if (loc.e           quals(    getLoca     tion()))     
            {
              doub     le r         = Math.rando    m();
                int a   ngle;
                    if (r < 0.5)
                   angle = Locat      ion  .LEF     T;
                         else
                     angle =    Location.   RIGH   T;
            setDirection(getD   irecti   o     n () +     angl        e);
            }
         e      ls e
            super .mak     eM      ove(loc);
    }
    
      /**   
       * Find       s    t      he valid    adjacent locations of this critter in diff  erent
     * directions.
      * @param      directions - an arr    a  y           of direc      ti    ons (which are relativ  e to th      e
     * current directi     on)
     * @retur n a set of v           alid locat     ions that are neighbors of the current  
     * location in the given directions
     */
    public Array L  ist <Location            > getLocationsInDirections    (int[] directi ons)
    {
        ArrayList<Location> locs = new ArrayList<Lo   cation>(    );
        Grid gr =     getGrid();
        Locat  ion loc = getLocation();
    
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getD     irection() + d);
            if (gr.isValid(neighborLoc))
                locs.add(neighborLoc);
        }
        return locs;
    }    
}
