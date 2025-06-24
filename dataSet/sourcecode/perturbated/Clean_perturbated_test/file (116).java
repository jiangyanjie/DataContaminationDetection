/*
  * Copyright (c) 2002-2008             LWJGL Project Al   l r     ights reserved. Red    ist ribution and u  se in           source and       binary forms, with    or
 * without modi   fication, are permitted provided that the following conditio ns   are     met   :   * Redistributions of source code
   * must re    tain the abo   ve copyright     notice      , thi  s list of conditions and the following dis   cla            imer. * Redist        ributions in
 * bin ary form must reproduce the above c            o    pyr          ight notice, this list of conditions and    the following discla      imer   in the
 * documen  tation and/or other mater ials pro     vided with  t     he dist ribu        tion. * Neither the    name of   'LWJGL'   nor   the names of
 * its contributors m     a   y b       e used to endorse or promote products derived from this soft   ware without specif  ic    prio  r written 
 * permission. THIS        SO    FTWARE IS PROV  IDED BY THE CO   PYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIE  D 
 * W    ARRAN   TIES, INC       LUDING, BUT NOT LIMITED  TO, THE IMPL      IED  WARRAN   TIES OF         MERCHANTABI    LITY AND FITNESS FOR    A PARTIC  ULAR
   * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE   CO PYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *     INC IDENTAL, SPEC  IAL, EXEMPLARY, OR CONSEQUENTIA  L   D     AMA   GES (I NCLUDING, BU   T   NOT LIMITED   TO, PROCUR EMEN  T  OF   SUBSTITUTE
 * GOODS OR SERVICES; LOSS   OF USE,  DATA,  OR    PROFITS; OR BUSINES    S INTE    RRUP TION) HOWEVER C  AUSED AND ON ANY THEORY OF     
       * LIABILITY, WH  ETH       ER IN CONTRACT, STRI  CT LIA     BILITY  , OR TORT (INCLUDING NEGLIGENCE OR     OTHERWISE    ) ARISING IN A      NY WAY OUT
 *         OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF     THE POSSIBIL ITY OF SUCH DAMAGE.
 */     
package org.lwj  glx     .util.g lu;

imp  o     rt   static    org.lwjgl.opengl.GL11.*; 
import static org.lwjglx.ut   il.glu    .GLU.*;

/**
 * Cylinde   r.java
   *
   *
 * Created 23-dec-        2003
 * 
    *   @auth   o  r Erik Duijs
 */
public class C    ylind   e       r       extends Q      uadric {

    /**
        * Construc              tor f                        or Cyl  inde     r.
           */
    publ  ic Cylinder()        {
             super();
    }
         
    /**
        * draws    a cylinder oriented a    l    on   g the z axis. The base of the   cy  linder is plac    ed at z       = 0,     and the top at z=height  .
     * Like a sphere  ,     a cylinder is subdivided around the z         axis into slices,      and    along the z axi s into  stacks.
     *
        * Note   t hat if to     pRadius is set    to zero, then   this ro   u t    ine will g      enerate a c           one.
     *
     * If the orienta    tion is set to GLU.OUTSIDE (with glu.qu  adri      cOrien    tati  on      ), then   any     generated    normals point a          way
      * fr om         t    he z    axis.  Other wise, they  point t  oward t                he z     axis     .
          *
     *    If texturing          is turned o      n (wi         th   g  lu.quadricTexture), then te     xture co                    o       rdinates are                    generated so that t r anges   
          *              l  ine a   rly fro    m 0.0  at z = 0 to 1.0 at     z =    heigh    t, and s ra nges from 0.0 at the +y axis, to 0.    25 at the +x axis         , to
             * 0.5 at the -y      axis,   to 0   .75 at  th     e -x axis, a   nd   back to       1.0 at the +y axis          .
     *
                * @param   ba  seRadius Spec   ifi       es  the  radius of     th  e cyli     n        der at z = 0.
     *      @param topRadius      Spe   c            ifies the      radius o  f the c y     li  nder at z    = h         eight.
            * @p  aram hei  ght             Specif  ies the    hei  ght of    t       he cylinder.
            * @param slices          Specifie     s the     number     of      sub  divisions around the z a  xis.
        * @param             stacks        Spe  cifie        s the     numbe   r o  f su bdiv                  ision  s along the  z a        x       i    s.     
            */
     pub lic void   d raw(float baseRa                   dius,    float  top  Radi   us, float height, i  nt s   lic  e s,      int st   acks) {
   
                               float        d       a, r, d    r,  dz;
                       float x, y, z, nz       , n          s i        g                n ;
            i  nt i    , j;

                                if           (super.or       ient           ati    on    == GLU_INS    IDE) {
                                             nsign =        -1.0f     ;
              } e l    se         {
            n  si    gn =    1.             0f;
                     }

                       da                                             = 2.0f *    PI / sli ces;
                                              dr =             (topRadius    - b  as       eRadius   ) /   s  tacks;
                                   d      z                     = h         eight /        stacks;
            nz = (        b    a   se          Radi   u         s - topRa  dius) / heigh      t;
          //  Z comp        one  nt          of n           or  mal ve  ctor  s 
  
                i   f      (sup       er.d               rawStyl        e ==     GL      U   _PO     INT) {
                   glBegin(   GL_   P      OINT    S );     
                  for          (i =             0;   i < sl    ices; i++   )          {
                        x = cos   ((         i * da));
                                  y      =     s   in((i         *        da)  );
                                       nor  ma   l3    f(x *  ns    ign, y *     nsign, nz             *   nsign);

                                    z =            0.         0f  ;    
                    r = bas e   Radius    ;
                        fo  r (j = 0; j <      =        sta          cks;    j          ++) {
                               glV     ertex3      f((x *          r), (y       * r)  ,           z);
                                                                                                 z   +=   dz;                 
                                  r      += dr;  
                                            }
                   }
                                  glEnd();   
                          } else i    f                   (super.drawSty    le   =   = GLU_L   I         NE || s    uper.drawStyl      e == GL          U                    _          SILHOUETTE  )     {
                                        // D     raw rings
               if (supe  r      .dr           awStyle == GLU_      LINE) {
                               z = 0.0f; 
                                 r =                     base  Rad  ius;
                           for (j = 0;                                  j <= st  acks; j+ +)                  {
                                 glBegin(GL_LINE_LO  OP)  ;
                                                         for (          i =                 0;     i    <   s     li     ce   s     ; i                     ++) {
                                                                                x = cos((i * da));
                                                   y = s     in(( i *          da));      
                             normal3f(x * nsig    n,      y   * nsign, n      z                * nsign);
                                          gl      Vertex3f((x  * r), (     y * r)  , z)       ;
                                                         }
                         glE        nd(      );
                                                             z += d              z;
                                                       r  += dr;        
                           }
                               }    els e {
                          //  draw one r     in   g    at   ea c  h    end
                                i  f (baseR                        adius       != 0.  0) {
                                                 g         lBegi      n(  GL_     L     INE_LO    OP);
                                                for          (i = 0;                             i < sli ce   s; i++     )            {
                                                            x = c     os(       (i * da)       );   
                                                      y   = sin(        (i * da));
                                                 n         orm           al   3f(x    *    nsig    n      , y * nsign, nz * n              sign);
                                 glVertex3f((x         * baseRadius), (y * b   as    eR     adi    us), 0.        0f)  ;
                                     }
                                                          g  lEnd(   )    ;
                                       gl  Beg  in  (GL_LIN           E_L   OOP);
                              for (i =          0;   i < slices; i++)      {
                                             x     = c    os((i        * da));
                                                       y = sin((i * da))      ;          
                                        normal3f(x * nsig     n, y *      nsi    gn  , nz      * n sig  n);
                                        glVe    rtex     3f((x *   to    p Radi    us ), (y * topRad                   i   u     s),   h    ei    ght);
                              }
                                  glEnd();
                    }
                        }   
            // dr  a   w l   ength       lin  es
                     glBegin(GL_LINES);
              for (     i = 0;    i <   slices;        i++) {
                       x             = cos              ((i    * da));
                 y = sin((i       * da));
                     n  ormal3f (x * nsign, y * nsig  n, nz * nsig n);
                 gl          V     e      rtex3f((x   * baseRadius), (y *   b  aseRa  dius), 0. 0f);
                  glVer   tex3f((x      * topRadi    us), (y   * topRadius),     (height));
                  }
            gl     End();
             } else if (supe         r.           drawStyle ==          GLU_FILL)  {
            float ds = 1.0f / slices;
            float dt = 1.0   f / stacks;   
                 float t =  0.0f;
            z = 0.0f;
            r = baseRadius;
                      f     or (j = 0; j < stac   ks; j++)        {
                     float s = 0.0f;
                                            glBeg in(GL_QUAD  _STRIP);
                 for (i = 0; i <= slices    ; i++) {
                                  if (i == slices) {
                            x = sin(   0.0f);
                          y = cos(0.0f);
                               } else {
                                 x = sin((i * da));
                          y = cos((i * da));
                          }
                             if (        ns ign == 1.0f) {
                                 normal3f((x *   nsign), (y * nsign), (nz *    nsign));   
                        TXTR_COORD(  s, t);
                              glVertex3f(      (x  * r), (y * r), z);
                                 normal3   f((x * nsign),      (y * nsign),           (nz * nsign));
                           TXTR_COORD(s,           t + dt);
                        glVertex3f   ((x * (r  +     dr)), (y * (r + dr)), (z + dz));
                                      } else {
                               no rmal3f(x * nsign, y * nsign, nz * nsign);
                                    TXTR_COORD(s, t);
                        glVertex3f((x * r), (y * r), z);       
                        normal3f(x * n  sign, y * nsign, nz * nsign);
                           TXTR_      COORD(s, t + dt);
                           glVertex3f((x * (r + dr)), (y * (r  + dr)), (z   + dz));
                    }
                      s += ds;
                } // for   slices
                glEnd();
                   r += dr;
                t += dt;
                z += dz;
            } // for stacks
        }
    }
}
