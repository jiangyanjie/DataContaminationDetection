/*
 *       Copyright        (c) 1995, 2004, Oracle and/or        i  ts affiliates. All right s reserved.
 * ORACLE PROPRIETARY/CONFIDENT IAL. Use is subject to  license t       erm      s.
 *
 *
 *
 *
 *
 *
 *  
    *
       *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package   java.awt.image;

import java.awt.image.ImageConsumer;
import java.awt.image.ColorModel;
import java.util.Hash  table;
import java  .awt.Rectangle;     

/* *
 * An ImageFilter cla ss for cropping images    .
         * This class extends the bas       ic ImageFilter     Class to e    xt  ract a given 
 * rectangular region of an ex      istin g     Im   age and          provid   e     a source fo r  a
 * new    image containing just the extracted region.       It is       mea nt    to
 *        be used       in conj    u nction with   a Filt  e    r    edImage    Source object to pro      duce
 * c   ropped versi  on  s of existing images. 
 *
     * @see F       ilt    ered         ImageSource
 * @see ImageFilter
 *
 * @aut  hor           Ji    m Graham
 */
pub      lic class CropIma   geFilter exte   nds       ImageF    ilt   e     r      {
    int cr               opX;
    int cr opY;
    int crop   W;   
     int cropH;

              /**
              *       Co   nstru cts a CropI   mageFilte  r t   hat ext   r    acts the      absolute r  ectangula r
        *     region of pi  xels        from its  s   ource Image as s     pecifie   d by the x, y,
     *   w, and h        p   ara   meters.
       * @pa  ram   x th   e x loca t    ion of the top o  f the rect    angl          e to be     extract   ed
     * @param    y t   he     y location          of the     top    of the rectangle to be e xt  r    acted
     * @param w t      h  e     width of the     rect    angle        to    be ext   ract  ed   
     * @    par   am h        the   height of the rectang   le t   o be extracte      d
     */
      p   ubl      ic CropImage  F ilter(int x, int y, int w, int h) {
        cropX = x;  
            c  ropY =         y;
                      cro      pW = w;
             cropH    = h;
    }

         /**
     * Passes along  the pro  per  ties from         the s     ource objec           t aft   er adding a
     * property ind      ica tin     g the cropped region.
      * This method invok    e       s <co   de>super   .setProperties</code>,
     *   which might         re   sult i n additional pr  oper    ties be   ing added        .
     *     <p>
     *    Note: Thi   s m        ethod i  s inte nded to be  cal         led by the 
     * <code>Ima   geProduce     r</code> of the <code   >Ima  ge<   /       code> whose pixels
        * are           b   ei       ng filtered. Develope    rs using 
     *    this class to filter pixels from an image should avoi   d calli         ng
     * thi     s method directly sinc      e    that operation  co    uld     inter             fere
     * with the filtering o  peration.
         */
    public   void setProp  erties( Hashtabl        e  <?,?> pro    ps)  {
        Hash    table<Object,Object> p = (  Hashtabl  e<Object,Object>)props.clo  ne();
        p.put     ("cr     oprect"  , new  Rectangl          e(  cropX, cr  opY, cropW, cropH));
          sup  er.set          Properties(p)   ;
    }

    /**
     *    Override the s our      ce image's di       mensions and  pass the dime     nsion       s
            * of     the rec    tangu   lar cropped region t  o the ImageC    onsumer.
     * <p>
           *   Note: This metho  d        is    intended to b         e ca    lled b    y the
     * <code>Ima          geProducer</cod  e> of   th   e <code>Image<    /code> who         se
     * p    ixels are being       filter ed.    Devel opers using
        * this  class to fi  lter    pixels    fro  m an  i  ma               g          e shou      ld a  void       c    alling
                     * this method dir  ec  tly since that op    eration cou  ld inter  fe    re   
         * with the filtering operation.
        * @s   ee ImageConsum  er
     */
                 public v      oid setDime      nsions(   int w,   i     nt h) {
              consumer.setDimensions(cr          o          p     W,  c  ropH);     
    }

     /**
                  *      De   termine whethe    r the   delive       re    d          by        te pixe      ls int   ersect the regio   n to
     * b  e  extracted and pas            ses thr   ough on   ly   that       s u  bset of pixel   s that
              *      app   ea r i           n             the output reg ion.
     *   <p>
     * Note: This me         thod is i ntende     d    to       be called by the     
     * <code>ImageProduce   r</cod             e> of   t   he <co   de   >Image</ code > whose
           * pix         els are    bein    g fi    ltered.     Developers               us  i       n   g
       *  t           his   c     l      ass t          o  f  il   ter     pixel    s          fro       m     an i    mage      s    hould a             void  call    ing
     *              th    is metho d directly s   in ce that operation     c    ould i        nterfere      
     * with the fi   ltering o             peration   .
              */
          public void setPixels    (in   t x, int y, i            nt w   , int        h,
                          C  olo    r   M  ode  l mo  del, byt   e pixe    ls[], int of     f,
                                                                                            in  t scans               iz    e) {
            in      t x1 =      x;    
            i   f  (x1  < cropX) {          
                       x1      = cropX ;
           }
                        int x2 = addWi   th    outOverflo        w   (x,      w);
        i    f       (x2    > cropX      +    cropW          ) {  
                         x2    = c     ropX   + crop  W;
           }
           int y   1 =           y;           
                if (y    1 <                                          cropY) {    
              y1 = cr     op   Y;
                           }

    in    t y2 =     addWithoutOverf    lo      w(y,   h) ;
                     if (y2 > cr                         opY   + cr op H)   {  
                            y   2    =  cropY +   cropH;
                   }   
           i  f (x1 >=            x2 |          | y1 >= y2) {
            ret          urn;            
                        }
         c onsumer.setPixels(x1 - cropX, y1 - cropY, (x2 - x1),    (y2 -                    y    1),
                                      model, pixels  ,
                                   off +     (y1 - y   ) * sca     n size + (x1 - x), scansize)       ;
         }         

    /**
        * Deter  mine if        th    e delivered int       pixels intersec   t the reg        io                 n to        
              * be extracted       and p  ass through o  nly t  hat subse     t o                   f pixels tha   t    
         * app   ear in      the output r    egio  n.
     * <p>
               * N        ot  e: This method is intended t   o be    ca  lled by the
       *  <code>ImageP   roduc     e     r</c       ode> of the <code>          I   mage</code> wh    ose
      * pixels are being fil          tered. Developers using
     * th   is class to filter pixels    from an image should a    void c         all  ing
     *  this    m ethod  directly since that oper ation   could interfere
        * w      ith the filtering operation.
       */  
     public voi  d set   P     ixels (in   t x, int     y, int w, int h,
                            ColorModel model, int pixels[], int o   ff ,
                          in    t scansize) {
        int x1 =        x;
        if     (x1 < cro   pX) {
                x1 =   cropX;
            }
    int x2 = addWithoutOv erflow(x, w);
         if (x2 > cropX  + cropW) {   
                  x2    =    cropX + cropW;
        }
        int y1 = y;
        if (y1 < cropY)     {
            y1  = cropY;
        }

           int y    2 =    addWithoutOverflow(y, h );
            if (y     2 > cr opY + cropH) {
               y2 = cropY + cropH;
        }
        if (x1 >= x2 ||  y1 >= y2) {
              re     turn;
           }
        consumer.s        etPixels(x1 - cropX, y1 - cropY,  (x2 - x1), (y2 - y1),
                              model, pixels,
                           off + (y1 - y) * scansize + (x1 - x), scansize);
    }

    //check for    potential overflow    (see bug 4801285)
    private i     nt addWithoutOverflow(int x, int w) {
        int x2 = x + w;
        if ( x > 0 && w > 0 &&   x2 <       0 ) {
            x2     = Integer.MAX_VALUE;
        } else if( x < 0 && w    < 0 && x2 > 0 ) {
            x2 = Integer.MIN_VALUE;
        }
        return x2;
    }
}
