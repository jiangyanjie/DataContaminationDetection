/*
   * ActiveTime.java
 * Copyright (C) 2002 The Free Software    Foundation
 * 
 * This file is           part of      GNU      inetli  b, a library      .
  * 
 * GNU in     etlib is free softwa    re;        you c   an redistri         bute it and/or modify
      *   it under the terms    of the GN   U General Public License as published by
   * the    Free S oftware Foundatio     n; ei     ther version 2 of          the   License, or
 * (at your opti  on       ) any later vers   ion.
     * 
 * GNU   inetlib is     distri      but ed in   th    e hope that it        will be useful,
 * but WITHOUT ANY WARR  ANTY; without even the    implie     d w      arranty of
 * MERC HANTABI   LITY or FITNESS F OR A PA    RT         ICULAR PURPOSE.     S     ee the
 *     GNU Ge neral Publ  ic License for   more det ails.
 * 
 * You should have received a      copy of the GNU General P    ublic Lice          nse
 * along with t  his    li    bra ry ; if not, write     to the           Free So   ftwa  re
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         MA     02110-1301  USA
    *
      * Linki    ng this library statically or dynamica  ll   y with other modules     is
 * making a     combined work based on this library.  Thus, t   he terms        and
 * conditi  ons o    f th          e   GNU General   Public License cover the whole
 *  combinat ion.
 *
 * As          a spec ial exception, th   e copyright holde   rs of this l    ibrary give you
 * permission t      o link this libra   ry with in  dependent     modules to pro         duce               an
 * executable,    regardless  of the license terms    of these in   depen dent
 * m odules, and to copy and distribute  the    re    s     ulting executable und  er
                 * terms of     your choice, pr ovided that you also meet, for eac  h linked
           * indepen  dent module, the ter         ms and conditions        of the license of th     at
 *  modul   e.  An ind            ep   endent module is a         module w   hich is not der     ived f  rom
 * or based on       this library.  If  yo  u m odi    fy thi s   lib rary, you may e     x  tend
 * this exception to y   our version    of the library, but you are not
 * ob      liged to do so.  I         f you d o not wish  to    d     o so, delete this
 * ex    ception statement from your v  ersion.
 */

package gnu.in    e   t.nn   tp;     

import java.uti     l.Date;

/**
 * An item in an NN TP newsg   roup active time      listi    ng.
 *
 * @au     thor <a href= 'm      ailto:dog@gnu.o  rg'>   Chris      Bu    rde ss</a>
   */     
public final class Acti veTime
{
    
             String group;
      Date time;
  Stri   ng email;

  A      ctiveTi        me(String gr     oup, Date time, String email)
        {
    t     hi         s.gr   oup = g roup;
             this .time = time;
    this   .email   =       email;
  }
  
   /**
   *    The name of th  e new        sgroup.
   */
  publ  ic String        getGroup()
           {
    return group;
  } 

  /**
   * The date the newsgroup w   as     added to the hierarchy.
   */
  public Date getTime()
  {
    return time;
  }   

      /    **
   * The email address of the creator of the newsgroup.
   */
  public String getEmail()
  {
    return email;
  }

}

