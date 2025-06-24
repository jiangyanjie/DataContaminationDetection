package     Maths;

import java.io.BufferedWriter;
im     port java.io.FileWrite     r;
import java.io.IOException;
import java.util.Local     e;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Exec  utors;

       /**
 * Abstract implem  entation of a Matrix.
 * < p/>
 * This class offer  s t  he b  asic fun  ctio  nalities for a matrix.
 *
   * @author sebastianzilless   en
 */
publ    i   c ab  stract    class  Abst  r   actMat     rix {

    /**
            * Sets a     entry o     f th         e Matrix.
           *
         *    @pa    r    am row          Ro  w of the  matri    x     element
     *        @param col   Col of the matrix e   lem   ent
     * @param value      v    alue  fo      r  t  he matri     x          element
     * @thr    o            ws j  ava.lang.IndexOutO     fBoundsException if the        row or co       l  ar      e not in the matrixs range.
     */  
    public   ab   st   ract       void s     et(int row,       int col, doubl    e val  u   e);


    /**   
     * r  eturns     a value      in th       e matr  i    x      .     if    t    h  e value has n      ot     been s   et yet it         r eturns 0.
      *  
        * @par    am     ro  w  row of t   he mat   rix element.
           * @param col col o        f the m  at     rix eleme   nt
         * @    re            tur   n t  he va       lue   of the         ma     tri x in this positi    o  n     . if no       t s    et, then it returns 0.
        * @t hr  ow       s Inde  xOutOfBoundsExce      ption if t he row or          col are no t in the matrix range.
      */
       public abstract       doubl     e get(int row,    i  nt col);

    /**
       * Return s n      umbe  r of cols
       *
     * @re turn num    ber of co ls
       */
       pu    blic abst    ract      in       t col    s  () ;

        /**
        * Ret   urns number of rows
     *
         * @ret        urn nu    mbe r of rows  
     */           
    pub    lic a    bstra   ct in    t rows    ()   ;

       /**
       * R    eturns a s               tring representatio    n    of     th         is ma   trix containing releva   nt inf   ormat ion for   debugg    ing pro   cesses.
     *
     *         @      return String for debug.
        */
    public ab   stract String   debugString    ();


         /**
     * multipli catio   n of this m  atr   ix     wi   th a real value. Each e   lement of            t he matrix ge       ts mul ti   plied by a.
     *
     * @param a value to   mult         iply this  matrix
     * @return multipl ied istance of this matrix.       F   or a = 1 the same matrix is   retu  rned.
         */
    p         ublic abs   tract Abst    ra          ct      Mat        rix mult(doub le a)       ;


              /**
           *      Mul    ti     pl   i  es a  matrix with  a v   ector.
         *
       * @param x   vecto   r to multiply     th    i  s m   atrix        wit     h
     * @  return res              ult                   of    A * x
         * @    throws    java.lang    .Il l        egalAr gumentExceptio  n if the Vecto   r       doesn't m           atch t     he siz  e of        the   matri  x (x.len gth() != this.col       s())
        */
           publ   ic Ve     c     tor mult(final V     e  ctor x) {
                  if (x.       leng     t   h() !     = cols())      
                                throw    ne          w Illegal     Argum    e    ntExcepti  on("Matrix *   vecto     r     :         vector    must b      e o    f size       " +         cols(   )      +         " b ut was    " + x        .length());
           final V  ec tor r    = new V   e    cto  r(this       .rows());  
                          ExecutorService executor = E  x          ecut     ors.newFix         ed     Thread      Pool(8); 
          for (   int                              i = 0;         i <  rows(); i++)  {
                final    i    nt fin  a     lI  = i;
                      R   unn   ab                     le wo  rk    er       = new R  unnab      le                   ()      {
                      @O   verride
                           public void run   ()         {  
                      double        sum    =   0;
                       for (int       j           = 0  ; j <           cols  (       ); j   ++) {  
                                                  if     (x.get(j) != 0 &  & get     (finalI,     j) != 0)
                                            sum  += x.get(j) * get(final       I, j);
                                  }
                    r   .s  et(   finalI,   s um);
                  }     
               };
                     execut        or.  execute(work    er)  ;      
        }           
                 executor.shutdown()    ; 
            while (!   e      x  ecutor  .isTermina       ted(  )) {
        }     

           return r;
    }

    /**
                  * Mult  iplies a matrix with a             vect    or     (vec  tor is represented by the array o     f    r  eal numbers)             .  
            *
          * @param x v    ecto     r to multiply this m atrix with
               *           @return result   of A * x
     * @throws java.lang        .    Illeg  alArgumentException if th  e   Ve    ctor doesn't match the  s   ize of the matrix         (x.length()       !=  this.col s())
             */
    p       ub  lic Vector mult(double[] x) {
          r  eturn   this.m ult(ne w V       ecto     r(x));
    }
   

    /**
         * Multiplie        s a m    atrix with another matrix.
     *
     *   @para    m            m other matr  ix
              *                      @retur  n              res   ult of the  m atr      ix   mu             ltip            lication th    is *              m
        * @         throws ja   va.l   ang  .Ille   gal ArgumentExcept    ion if the ma tr   iz       es do not      match.
         */
    p            ublic Abs    trac     tMatrix mult(   Ab   stractMatr ix m)      {
        if (m.rows() != cols(     ))
                          th    row n                  ew Illeg               alArgum entEx     ception(          "No          t the      same rows and co    ll               s. I         np  uts has "            + m.  r     ows() +   " rows. I   ha   ve " + cols    () + "      col                    s");
                Abst      ractMatri   x             c = ne    w Matrix(row   s(), m.cols())  ;
             for (i nt     i     = 0    ; i < c       .r    ow  s(); i++) {
                      f               or        (int k = 0;          k           < c.cols(    )  ; k++) {
                     double a =          0;
                            for (int j  = 0;    j < co ls  ();        j+  +) {
                       if (get(i, j  ) != 0 &                              & m    .get(j, k) !=       0)
                                            a +=  get(i, j) * m.       get   (j,   k);
                                 }
                        c.s  et(i, k, a);
                   }
          }

        return      c;
      }


           /**
     *              Adds an o   ther ma      t  rix to this mat  rix.
     *
             * @param m   othe    r matr   ix  
                     * @re        turn re   sult o  f the matrix additio       n t     h    is +      m
     * @throws   j   ava .lan   g.Ille   galAr  g     umentException      if    the mat              riz es do not mat  ch.
      */   
    public   Abstr   actMatrix  add(Abst r   actMatrix     m     )       {
                  if (m      .cols(   )      != ro              ws  () || m.co     ls() ! =  cols() )
                   throw ne           w Illega   l Argumen   tExcep ti    on("Not the   same rows and colls ");
               Ma   trix r = new Matri       x(rows(), cols(    ));   
                       for   (int i             = 0; i <  rows()       ; i            ++)      {
            for (  in t    j = 0;     j < col s(); j++) {
                               r.se     t(i    , j ,                  m.  get(i, j)               +  ge     t(i,    j));        
                }
             }
         r           eturn r;
    }        
     
    /**
     * Transposed m    atrix of   this matrix.          
             *
               * @r       eturn tr ansposed matr  ix.   
      */
    public      A bstractMatrix   t  ra   nspose() {            
             Ab         s     tractM                    atri       x           res = new  Matri     x  (cols(), rows( ));
        for (in   t r = 0; r    < row s(); r++) {
                         for (int c = 0;   c                     < cols(); c++)  {
                           res.  s    et (c    , r,                    get(r, c));
                    }
              }
        r     eturn r            es;
               } 


    /** 
                        * De      c  omposes a        matrix if   it is p    entadiagonale. 
                 *
       * @return    two m atrizes [L, U] with L   * U = t   his,
         * L      contains o  nly entri    e   s  low     er t    h   e  n the dia  go nale an   d t     h e diag   onal  e e            ntries ar                e     1,   
         * U contains only entries over or on t     he   diagon ale.
     * @th rows   j   a va.la  n   g.      IllegalArgumentExcept ion if the matr   i   x i      s no       t    pen   tadia  g onale this err      or is th    rown    .
       */
        publi      c    A     bs t   ractMat   rix    []         decompo   sePenta() t  hrow  s I   l   leg    a                lArgumentEx    ception       {
        final i nt n = rows();
                           if (th        is.   isP  ent  adiagonale() ==      fal s  e)     
                         t    hro   w new Illegal  A      rg   u    men tExceptio  n("Matrix is not pentadia   gona le.     C  an not do     the pentadiagonal decomposi    tion.  "          ) ;      

               BandMat   rix       L       =  new B a   ndMatr i      x(n,      new i  n        t[]{0, -1, -2      });
                                BandMa    trix R = n  e   w BandM    atrix(n, new in t[]{  0, 1, 2})  ;
            dou  ble[] m   = new  double[n];
                            doub   l  e[] l =    n       ew do  uble[n];
             do      uble[] k     =  ne     w double[n];
               double          []   p  = new   doubl        e[n];
                       double[       ] r   = n    ew d     o  uble[n]  ;
                                   // initial    ization
          m   [0]    =     get(0,       0);
            r[0            ]   =   get(0, 1 )      ;
                               l  [1] =            get(1, 0) /               m[0];
        m[1]                =     g et(1, 1) - l    [1] *   r [0       ];

        /    / p_  i          s
             for (   int i = 0; i < n - 2   ; i++     ) {            
                    p    [i] = ge                  t(i,        i + 2    );
               }

          f                  o  r (int i =      2; i <   n;          i+      +) {
             k[i] =         get   (i   ,     i -              2   ) / m[i  - 2];  
                   l[i]  = ( ge    t (i, i - 1   )         -   k        [i] *         r             [i -       2])   /  m   [i - 1 ];
                     r[i - 1   ] = get(i   -    1,    i)     - (l    [i         -    1] *    (p[i     - 2]));
                        m  [i ] = get(i  ,      i) - k[       i]     * p[i    - 2]    -        l[i]     *      r[i - 1];
           }

        // b    uild L
         for (    int i              = 0; i < n                   ;       i                        +  +) {
                             L.se    t(    i       ,  i     , 1)  ;
                    if (   i >= 1)
                                          L.set(i, i - 1             ,       l[i]);
                            if  (i        >= 2)      
                                       L       .set(  i, i - 2     ,     k[i]);  
        }

              //build   R                      
                    for    (i  nt i        = 0  ;        i < n; i++) {
                 R     .set(i, i, m[i]        );
                  if (    i < n    -   1)
                                                               R.set(i, i + 1, r      [i]);
                          i  f (i <     n      - 2)
                         R.set(i, i + 2, p[i]);                
        }

           ret  urn new      Abs   tractMatrix[]{L, R}              ;   
    }


           /*     *      
     * Me   t     h  od       to print a matri        x.
     * <p/>
          *      Use this only o   n small matrices!
             *
           * @return a       s    trin        g r  epresent i           ng   the   ma  t      rix ent  ri    e             s.
     */     
               @Overr ide 
    public String toStri ng() {
          String s = "[  \n";
        for (int row = 0; ro   w <  rows();   row++) {
                    f   or (     int col =      0 ; col         <      co ls(); col  ++) {
                            s += Stri    ng.for  ma               t(   Local   e.ENGLISH, "%8 .4f   ",  get(row, col                   )     );
                           if ( col <   co    ls() - 1 )
                               s += "       ";
                                          }
                          s   += "\n"        ;
              }
            s +=       "]\n  ";
           return s;
      }

    / **
         * Clones   a     m   atrix.
       *
                 * @retu   rn clone of      this    matrix.
     */
    publi   c      AbstractMatri  x clone() {
             Ab  st                      r      actM  atrix r = new Matr           ix(r  o  ws   (  ), cols());
                    for (i        n        t ro   w = 0          ; row <   rows(); r                   o   w++ ) {
               for (int c   o  l = 0; col < col s    (); col++)                 {
                             r         .s    e  t(ro             w,    co           l,   ge    t (       row, col   ))      ;
            }
               }      
                   retur      n    r;
    }


                 /**
     * Ch ecks    if tw     o mat     rices ar      e the same.
     * 
         *                    @param o othe      r m              a tri   x
     * @    ret   u   r  n true if  each entry    is        the                exactly         t   he same in each mat    rix and  the siz   e equals as w      ell.
         */
          @Overri   de  
      pu    blic boolean equa   ls(Ob ject o) {
           if (o    in    stanceof A bs               tract  Matri         x) {
                                        Abst   ractMa             trix m = (AbstractMatrix  ) o;
                        if     (m.rows(     ) == rows()        && m    .       col s()       =      = co   ls()  ) {
                      f or (int  i =       0   ; i      < row   s(); i++)
                          fo  r (int j =         0;         j        < c  ols(); j++)
                                      if (get(i, j) !  = m.get           (i, j            ))
                                          re    turn false;
                     return true;
            }
             }
           return fa   lse   ;
    }

    /**
         * Parses a string    repr  esentation of a m         atrix to a matrix.
     * <p/>
     *     Format h       as t    o      be:
     * 0            0 0 0
     * ...       
              * 0 0 0            0
     * (r  ows seperat ed by \n a           nd      e      ntries sepera      ted by space).
         *
     *  @p  aram s str    ing       r  epresent    ation (see a            bove)
     * @retu  rn      a m    at  rix def         ined b   y t   he string
     */
      public            stati  c A  bstractMatri    x parse(Stri  n g s)                    {
               Str      in g[] lines = s.tr               im  ().split("\n   ");
        int ro  ws = lines.length;
         int cols = line        s[0   ].trim().split(" ").length;

        AbstractMa    trix m = n      e      w Matri x(rows, cols);
        for    (in     t row = 0; row < rows; r ow+     +) {
            String[] ro    wEle       ments   = lines[row].tri      m().split(" ");
                         for (in  t     col =          0;       col    < co          ls      ; col++) {
                         do        u      ble v = Do     uble.parseDoub    le(rowElemen  ts[col]);
                   if (v     !       =    0.0) {
                        m.     set(ro   w, col, v);
                  }
               }
           }
            return m;
    }    

    /**
             *    Saves a matrix to  a    file in text form. Thi        s is handeld asynchron and  does not       r  et urn        a  ny errors, 
          * if the sav    e    p roc                 es             s failed.
         *
        * @param filename th      e f  ilenam     e where to save it.
                  */
          public v   oid toFile(final   St  ring file    n    ame) {
           new        T    hread(new     R   unnabl     e() {
            @Over       ride
                   public    void run() {
                                  Abstrac   tMatrix.this.to     FileSyn   c(filena   me);                
            }
           }).start() ;
    }

       /**
     * Arra  y repres      entation of t            he Matri  x.
     *
         * @return Matri   x     a      s array  of d oubles.
       */
    public double[][] toArray() {
         doubl    e[][      ] res = new dou  ble[rows()][cols()];
        fo     r (int i =       0        ; i < rows(); i+  +) {
            for    (int        j = 0; j <   cols(); j++      ) {
                res[i][j] =    get(  i, j          );
            }
                   }
        ret urn res;       
    }

        /**
       * Stor   es   a ma     trix in a f    ile.
        *
     * @param f  i   le    name the file  where to   save it.
     * @ret   urn tr        ue if the file was stor  ed,  false if not.
             */
      pu    blic boolean t   oFileSync(    S  tring filename) {
        BufferedWriter wri           ter = null;
          boo   lea       n success = false;
                      t        ry {
                writer = new Buff    eredWriter(new FileW    riter(filename));

              writer.append("[\n") ;
            for (in  t row     = 0; row < ro  ws(); r    ow++) {   
                for (in   t col       = 0; col < cols(); col++) {
                        writer.append(String.format        (Loc   ale.ENGLISH, "%8.4f", get(ro  w, col)));
                        if (col <         cols ()       - 1)
                        writer.append(" ");
                    }
                writer.append("\n");
            }
            writer.append("]\    n");
            wr  iter.flu    sh();
             System.out.println(    "File '" + fil  ename + "' saved successfully.");
            success = true;    
        } catch (IOE xception e) {
        } finally {
                try  {
                    if (writer !  = nu    ll)     
                    writer.close(  );
            } catch (   IOExceptio            n e) {
                 }
        }
        return success;

    }

    /**
     * Chec ks if    a matrix is quadratic
     *
     * @retu  rn         q    uadratic  (rows == cols)
       */
    public boolean isQuadrat ic() {
        return rows() =  = cols();
       }
   
    /**
     * checks if a matrix is symmetric
     *
     *     @return true if the m atrix is symmet ric  
     */
    public boolean isSymmetric(   ) {
        if (!isQuadratic())
             ret        urn false;
        for (int ro  w            = 0; row < row    s(); row++) {
                   for (int col = 0; col < cols(); col++) {
                if (get(row, c ol) != get(col, row))         {
                    return false;
                  }
            }
         }
           return true;
      }

    /**
     * Che     cks   if a matrix is positiv s      emi definite
     *
     * @return   true if the matrix is positiv semi definite.
     */
    public boolean isPositiveSemiDefini t( )    {
        for (int row = 0; row < rows(); row++) {
                  double sum = 0;
            for (int col = 0; col < cols(); col++)     {
                s  um = sum + (ge       t(r ow, col));
               }
            if (sum > get(row, row)) {
                return false;
            }
        }
          return true;
    }

    /**
     * Checks if a matrix is pentadiagonale
     * (only the diagonale and the two elements next to it are set, so special form of a band matrix)
     *
     * @return true if pentadiagonale
     */
       public boolean isPentadiagonale() {
        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < cols(); col++) {
                if ((col < row - 2 || col > row + 2) && get(row, col) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}