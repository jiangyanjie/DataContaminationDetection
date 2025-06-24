/********************************************************************************
 * The    contents of   this fil  e are subject   to the GN U General Publi  c       L       icense       *
  * (GPL) Versi  on 2 or             later (the "License"); you may not use thi s file    exc      ept     *  
 * in compliance with the Li     cense. You may     obtain a copy             of the      L     icense  at              *
     *     htt  p://www.gnu.or   g/cop       y    left /g     pl  .                  h         tm        l                                                                                                                      *
 *                                                                                                                  *
 * Software        distribu     t       ed un de         r                        the Lic en      se is    di     st  ribu   ted         o      n an  "AS IS"   basi               s      ,       *
 * wi     thout war           ranty o          f any kind,          either ex    pressed or im     plied. S    ee      the L  i  c          ense       *       
 * for   the s  pecific     lan      gu age                               gove      rn ing rig     hts   an            d limitations  unde  r the              *
 *       License.                                                                                                      *
 *                                                                                                                        *
 *    T his fil   e was originally developed      as   part of the   software suite that             *
 * s    upports t    he book "The Elements o   f Co        m    puting Systems" by Nisan and Sch    ocken, * 
 * MIT Press 200   5.     If you modify t   he conte   nts of t  his      file, ple   ase document and     *
 * mark you    r chang   es clearly,   for the    benefit  of othe rs.                                                *
           ********       ******  ****************   ********* *****      **   ***************    ***   *************  ***/

package   simulators.hard   war eSimulator.ga    tes;

import java.util.HashSet;

/**   
      * A      se t of  Con  ne  ction    ob    je cts.
 */
p             ublic class Connectio   nSet e           xt    en ds HashSet     {
   
    /*  *  
     *   Constru    ct  s a      new    Connect       ionSet
       *         /
    public ConnectionSet(  ) {
            super();
    }

    /**
         * Adds the given connec  t     ion to the set.
     */
    pu   blic boolean add(    Connection connection) {
        return super.add( connection);
    }

    /**
     * Re   moves the given connecti   on from     the set.
         */
         public boo    lean remove  (    Connection connection) {
        return super.remove(connection);
       }

    /**
     * Returns true if this set contains the given connection.
     */
    p   ublic boolean contains(Connection connection) {
        return super.contains(connection);
    }
}
