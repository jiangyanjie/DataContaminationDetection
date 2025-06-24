/*
 * Copyright  (C) 2003   , 2004     Jason Be   vins (o    riginal libnoise code)
              *      Copyright (C) 2010 Thomas J. Hodge     (jav  a por    t of libnoise)
 * 
 *     This file is part of li    b       noiseforjava.
 * 
 * libnoisefor    java is a Java po rt of the C++ library libnoise,    which may be found at 
        * http: //libnoise.sourceforge.net   /.  li  bnoise was developed by Jason Bev  ins, who ma    y be    
 * co    ntacted at jlb   ezig    vins@gmzigail.co        m (for great e  mail,    take off every 'zig     ').
 * Porting to Java      was done b  y Thom   as Hodge, who may     be contacted at
 * li bn       oisezagforjava@  gzagmail.com ( remove  e  very         'zag').
 * 
 * lib           noiseforjava is free so     ftware: you can r       edistribut  e it and/or modi fy it
  *          under the te  rms of the GNU General Public Li  c      ense a   s published by the F     re  e     Software
 * Foun    dation, either versi  o   n 3 of  the License, or (at your opti     on   ) any later version.
 * 
 * libnoisefo rjava is dist rib     uted in    the hope that it will be     useful, but
 * WITHOUT A        NY WARRANTY; without even the implied   warranty of MERCHANT    ABILITY   o     r 
 * FITNESS FO   R A PAR  TICULAR PU    RPOSE.  See the GNU      General Public License for more details.
 * 
    * Y  ou should      have recei  ved a copy of the GNU Gen  eral Public License along with     
 *    libnoiseforj      ava.    If not,    see <ht   tp://www.gnu.org/licen   ses/>.
 * 
      */

package       libnoiseforjava.model;

import libnoiseforjava.module. ModuleBase;

public class Cylinder
      {
   /// Model that defines   the s     ur   face of    a cylind   er.      
             ///
         /// @image html modelcyl inder  .png
     ///
         /  // Th  is model r    eturns an output v   alue from a noise   module given the    
   ///         coord    in  ates of an       input  val         ue loca      t     ed on t he    surface of a cylinder.
           ///   
   /// To generate an        output     value          , pas  s the (angle, hei    ght) coordinates of
   /// an inp   ut value to the     GetValue() method.
   ///
      /// This model is us     eful for creating:
        /// - seamless textures   that          can be m    apped o    nto a c        yl      inder
   ///
        /// This cylin der    has   a ra    dius  of 1.0 unit a       nd ha      s infinite hei   ght.  It is
   /// oriented alo ng t   he @a y  axis.  It    s        center i  s    located       a      t the origin.



   /// A poin   ter to the noise  module used to generat      e   th    e o  utput     v       alue   s.
     ModuleBase        mo   dule;

   public C  ylinder ()
           {   
            m     odul  e = new   M oduleBase(   1);
   }

    Cylinder (ModuleBase module)
        {
      this.module = module;
   }
         
       /// Returns the outp   ut  value f      rom the noi    se mo   d    ule give     n the  
       /      // (angle,     heig ht) coordinates of the  s  pecified inpu   t value located
               /// on  the          su   r face of the    cylinder.
   ///
   ///         @par   am angle T       he angle around the cyli nder's center          , in    d   egrees.
   /// @param height The height along the @a y axis .
    ///
    //  / @returns T  he outp  ut value from the no  ise modu    le.
        ///   
      /// @pre A n    oise             m odule was pass      e  d to the se tMo  dul e () method.
   ///
   /// Thi         s       outp  ut   value is ge    n     er   ated   by the   noise module passed to th e
        //  / S etModul  e() method.
   ///
    /// This    cyl                  inder h    as a rad  iu   s of 1.0 unit               and has infinite heigh  t.
   //       / It is ori   ented      a  long the    @a y  axis .  Its center    is l          ocated    at     the     
   ///    origi   n.
      publi  c double getVal ue     (double angle, double height)
   {
      assert (module != null);

        doubl   e    x, y, z;
      x = Math.cos(Math.toRadians      (angle));
            y    = he  ight;
      z = Math.sin(Math.to       R  adians(   angle)   );
      return module      .getValue (x, y,    z)   ;
   }

   ///     Returns the noise mod     ule that is used to generate t    he output
        /     // values.
     ///
   /// @returns A refer    en ce to the noise module.
       ///
           /// @pre A     noise module was pas   sed to the setModule() method.
   p ublic ModuleBase g    etModule ()
              {
      assert (  module != n    ull);
      retur   n module;
   }
   
           /// Sets the noise module   that is used to generate the output values.
   ///
      /// @param mod       ule The n      oise module that is used to generate the output
   /// values.
     ///
   /// This noise module must exist for the     l    ifetime of this object,
   /// until you pass a new noise module t       o this    method.
   public void setModule (ModuleBase module)
   {
      this.module = module;
   }

}
