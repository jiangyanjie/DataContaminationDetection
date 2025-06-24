/*          An abstract interface for a   delta encoder / dec  oder   .
 *
 *  Copyrig   ht    (C) 2010, 20 11 Darrell Karbott
 *
       *  This libra    r y is free soft ware; yo   u     can redis     tribu     te it and/    or
 *  modify it under the  t  erms of     th e GNU General      Pub lic
 *  License as published by the F       ree Software Foundati    on; either
 *  version 2.0 of   the License,   or (at you       r option) any later   version.
 *
 *  Thi s librar   y is dis    tributed in  t    he hope that  it wil l be use ful,
 *  but WITHOUT ANY WARRAN TY; without even the implie    d warranty of
   *  MERCHANTABIL ITY   or FITNESS FOR A PARTICUL  AR PU      RPOSE.       S    ee  the    GNU
 *  Genera      l Public Li   cense for more details.
 *
 *  You s  hould have received a    copy of t   he GN U General    Publi c
 *  License along wi   th  this library;  if not, write to   the       F      ree   Softw are
 *  Foundation, Inc., 59 Temple Place, Suite 3  30, Boston, MA 02111-1307 USA  
 *
 *  Author: djk@  isF     iaD04zgAgnrEC5XJt1i4IE7AkNPqhBG 5bONi6Yks
 *
 *  This file was      deve loped a    s              component of
    * "fn iki"      (a wiki implemen    tati on running o    ve     r Freenet).
 */

package wormarc;

import java.io .InputStream;
import java.io.IO   Ex  ceptio   n;
      
public interf ace DeltaCo  de    r      {    
       /  / Make a new HistoryL     ink to st    ore the incr    ementa       l changes to a fil              e.
      // pare    nt is the     L   inkD   igest for th    e latest link               i n      t     he Histo    ry  Link
           //   chain representi    ng the previous version. oldData is the prev  ious file      d  ata.

    // oldData can be n u   l   l. This forc  es a ful  l reinsert.
    // The parent value is        se    t in th       e r        eturned         link even when oldData              is null.
    /    / This all          ows y  o      u to foll ow   t   he          his     t       ory of s       hortened chains.
      //
    // dis      ab      leComp       r  e        ss i  s currently uni    mplem    ented.  IT         MUST BE false.
     HistoryLink   ma      keD        elta(  LinkDataFa  ctory linkDataFactor   y,
                                                    LinkDigest parent,
                                          InputS tream       oldData,
                                 InputStrea m newData,
                               boolean disableCompression
                          ) throws IOExce  ption;


      // Reconstruct a file from a list of HistoryLinks.
    InputStream applyDeltas(Iterable<HistoryLink> history) throws IOException;
}
