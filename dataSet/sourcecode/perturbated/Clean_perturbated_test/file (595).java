package    Maths;

/**
 * Convolut    ion is       the code for applying    the   convolution o   perator.    
 *
 * @autho     r: Simon Horne, Sebasti  an Zille     ssen
 * @see: http://www.inf.ufpr.br/danielw/pos/ci724/20102/HIPR2/fl atjavasrc/Convol  ution.java
 */    
public class /**/Convolution {
      private        Con   vo   lutio    n()    {

        }

    /*    *
       * Returns       a      a  pproximation of a gaussian   kernel as stencil fo    r          the  1D.
     *  
                    * @  p aram radius R  adiu                s in pixels
     *    @param sigma  sigma for the gaus    sian      to use
     * @return
           */
    pub lic     static do     uble[] getGaus    sianKern      el1D(int radius, doub l  e  si  gma) {
         dou            ble[              ] r     es = new double[radius     * 2 + 1];    
          dou    ble norm =                 1.   0 /   (Math.sqrt( 2 * M ath.PI) *  sigma);    
           double coef    f       =    2 * si        gma * sigma;           
        do uble   total =  0;
             for (in    t       x  = -r     ad   i        us; x      <=    r       adius; x           ++)   {
                     doubl e g = norm * Math.exp(-x * x /   co  ef    f);
               res     [x + radius    ]              = g;
                      t   otal += g; 
                 }     
               for (int x = 0; x < res.le    ngth;       x++) {
                        res[  x   ] /=   total  ;
        }
        return res;
    }

            /**
     *             Returns a approxima    tion of a gaussian kernel as stencil    for   the 2D case.
     *
           * @param          radius Radi    us in pixels
          * @param si   gma    sigma for t             he gaussian   to use
     * @return
        */
    pu     blic static do  ub       le[][ ] getGauss   i  anK erne             l2D(int       ra dius, double            sig   ma) {
                     doubl  e[] gau  s = getGau ssianKer  nel1D(radius, sigm   a);
           double[][]     res = new dou     ble[g          a   us.  length][gaus.len gth];
                   for (int x = 0    ;   x <  g     aus  .length; x++) {
                            for (int y = 0; y <      gaus.len     gth; y++    )      {
                res[  x][y] = ga  us[x   ]    * g  aus[y]     ;
             }
        }
                retu      rn res;
                    }

   
    /**
           *    Tak             es a         n i  m         age     (grey-l     evel      s) and a kernel a          n   d   a p           osi tio               n,
       * appl   i es the    conv olutio   n   at        tha         t posi  tion and     returns th   e
     * ne    w pixel    value.
       *
             * @ para    m input The 2D double    array r  ep resenting the image .
                     *     @p    ara   m x         The x coordinate for the position of the con   volu   tion.
         * @param y     Th e    y co    ordinate            for th  e position o f     t     he con    v   olut   io      n.      
                   * @param k     The 2D arr ay r    epres        ent  ing t             he      k       erne         l.
            * @return The   new pixe  l value         a  fter   the    convoluti  on.
           */
    pub  li  c sta       t ic do  uble singl  e        P  ixel   C    onvolu  tion(    doub   l    e[   ][]      inp       ut,   
                                                                                                              int x, int y,
                                                                                                       do  uble[][        ]       k   ) {
               in    t k  ernelWidth =          k.le  ngth; 
           i                nt      ker      nelHeight = k[0].length;
        do  uble   outpu        t = 0;
                        f      o    r           (       int i = 0; i < kernelWidth   ; ++i)    {
            fo   r          (in  t j  = 0  ; j < ke    rnelHeigh    t; +    +j) {
                   if (x + i >= 0 && y + j   >=    0 && x + i < input.length & & y      +           j < input[0].length)
                    out     put += ( input[x +   i][y + j] * k[i][j]);
                        }
        }
            ret      urn ou    tput;   
      }  

        
         /**
     * Ta kes a 2D array of grey-le         vel   s and a kernel, applies the convolut          ion
         * over the area of the image specified by width and height and returns
       *     a part of the final image.     
     *
         *     @param inp  ut  the 2D d oub le array representing the     image
     * @par   am k ernel th e        2D array   rep resenti    ng the kernel
        * @return the 2D ar  ray re presenting the new image
       */
      p ublic static dou   ble[]      []     convolute(double[][] input   ,
                                                do    uble[][  ] kernel
    ) {
        double large[][] = new double[input.length][input[0].     length];
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[i].length; ++j) {
                       large[i][j] = singlePixelConvolution(inp   ut, i - kernel.length / 2,   j - kernel.length / 2, kernel);
            }
            }
        return large;
    }

}
