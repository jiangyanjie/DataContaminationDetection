/*
      *     Copyright  (C)    2003, 2004 J    ason    Bevins (origina   l libnois    e code)
 * Copyright     (C) 2010 Thomas J. Hodge (java         port of libno   ise)
 * 
 *          This    fil    e is p     art of    libnoisefo rjava.
 * 
 *     libnois eforjava is a Java port of     the    C++ library libnoise, which may      be found at 
 * http://l  ibnoise.sour    ceforge  .net/.         l   ibnoise was deve       loped by Jason Be   vins, who      may be 
 * contacted at jlbezigvins@gmzigail.   com (for    great email, take off ever  y    'zig').
 * Porting to Ja     va was done by Thomas Hod  ge, who may be co          ntacted   at
 *    libnois  ezagfo    rjava@gzagmail.com (remove eve ry '    z     a    g').
 * 
 * libnoiseforjava is free  software: you can redistribute it     and/or    m   odify it
 * under the terms of th  e GNU General Public Lic      ense as published by t  h      e  Free  So    ft ware
 * Fo   und       a    tion,    either vers  ion 3 of    the L icense, or (at your option)     any later version.
 * 
 *    lib    noiseforjava i  s     distributed in t  he hope that it w   ill be useful,   but
    * WIT H  OU   T ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
    * FITN   ESS FOR A P   ARTICULAR PURP OSE.  See the GNU General Public License f    or more details.
 *   
 * You should have r     eceived    a copy of the GNU Gen    eral Public     License     along with
 * lib noiseforjava.  If not, see   <http://www.gnu.  org/licens  es/>.
 * 
 */
    
package libnoiseforjava.module;

publ   ic cl   ass C  ylinders extends         ModuleBase
{
   ///     No ise m   odule that outp        uts co   ncentri       c cylinders.
        ///
   /// This      noise    mod ule outp   uts      con  c   entric cylinder   s center  ed on    the    or     igin.
   /// These cyl  inde  rs are      oriented along the @a y axis  similar to the
   /// co ncen  tric ring          s         of a tree.  Each cylin  d       er   extends infinitely al ong
   /// the @a         y axis.
   ///
   ///   The     fi     rst cy   linder has a radius o         f 1.0.      Ea    ch subsequent cylinder     ha   s
        /// a radius that  is 1.0 unit l  a           rger than the previous        cylinder.
   ///
   /// Th  e output value from  this noi             s e module          is de  termined by the distance
   /// b  etween t    he input value     and th  e      the nearest cyli nder surfac    e.  The
   /// input v       alues   tha   t are locat   ed o  n a cyli  n        der surface are g           iven the    
   ///     output value 1.0 and the         input values that a   re equidistant from t wo
   /// cyli      nder   surfaces are giv en the output va     lue -1.0.
        ///
   /// An     application       can c   hange the frequ ency of the concentric cylinders.   
   /   // Inc    rea        sin g th e fr              equency reduces the distance    s betwe     e   n       cylinde   r s.       T   o
   /// specify the f  re quency, call the setFrequ    ency() m   ethod.
   /// 
   ///    This noise     m     odule, modified      with some low-frequency, low    -power
    ///   turbulence, is u   seful for generating wood  -    like textures.
       ///
   /// This noise module do   es not require any s    ource   m      odules.

       //      / Defaul   t frequenc y valu    e       for the   Cylind   ers noi  se             modul     e.
   static final      d   ou ble DEFAULT_C    YLIN         DERS_FR  E       QUE     NCY = 1.0;       

   /// Frequency of the conce  ntric cylinde rs  .
   dou  ble frequency;

     pub lic  Cylin        ders ()
   {
          super(0);
      frequency    = DEFAULT_CYLINDER    S_FREQUENCY;
      }

   public    d   ouble getValue (double      x,  doubl     e y    , double z)
   {
      x *= frequency;
        z *= frequency;

      dou     ble dis       tFromC  enter = Ma   th  .sqrt(x *   x + z * z     );
      double distFromSmallerSp       here     = distFromC        enter - Math.floor(distFromCen          t  er);
       doubl e di   stFromLargerSphe   r         e = 1.0 - distFromSmallerSphere;
      double nearestDist =  Math.min(distFromSmallerSph er  e, distFr om La rgerSp here);

      // Pu ts   it in   the -  1.0 t  o +1.0 range.
             r etu  rn 1.0 - (   nea   re   stDis  t * 4.0);
   }     

   /// Returns     the frequency of the concentric cylinders.
   ///
   /// @retu  rn s      The    frequency  of the concentric    cylind  ers.
   ///
   /// Increasing the frequency incr  eases the d     e   nsity of the concen           tric
   /// cyl    inders, reducing the distances between them.
   public   double getFrequency() 
   {
      retur  n frequency;
   }

   /// Sets the frequency of the concentric cylinders.
   ///
   /// @param frequency The frequency of the concentric cylinders.
   ///
   /// Increasing the frequency increases the density of the concentric
   /// cylinders, reducing the    distances between them.
   public void setFrequency (double frequency)
   {
      this.frequency = frequency;
   }

}
