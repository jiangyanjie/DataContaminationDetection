/********************************************************************************
 *   The      c   onten    ts of this file are s    ubject   to the GNU    Gener al    Public   Li      cense      *
    * (GP   L) Version 2 or     later (t        he "Li     cense"); you m    ay not use this fi  le  exc    ept   *        
              * in compliance with the   L  icen se  .   You may obtain      a copy of the License at                        *
 * h       tt   p://www  .       g   nu.org /               cop        yleft/gp    l.      h           tml                                                                                               *     
   *                                                                                                                                                                         *
 * Softw   are d istr  ibu t  ed under the L     icense is d            istribute   d on    a       n "AS          IS    "     basis,    *
 *  witho  u  t w     ar    ranty             of       a n   y         k    in   d   ,        eith    er              expressed     o       r impl    ied. See        t      he Licens   e   *
 * for  the specifi   c l  anguage govern   ing rights and li    m             it     at        ions und er   the                *
   *   Li       c      ense.                                                                                             *
 *                                                                                                                                                                         *
 * This file was originally develop    ed      as part of the softw      ar   e suite that        *
 * support  s  the book "The     Ele ments of Computing   Syste      ms " by    Nisa  n and S  chocken    , *
 * MIT        Press 20          05.   If you modify the     c   ontents of this file,      please docum    ent              and *
 * mark y  our  changes clearly,        for the b      enefi   t of others.                                 *
         *******  *******   *******  *****   ******* * ***   **      **  **   **************** *********************    /

packag  e     com      mon  ;               

/**
 * Format conversion uti   lities
    * /
   public class          Con ver    si    ons {
  
    // A hel  per string    of zeros  
    pri   vate static       final String   ZEROS = "00000 0 00000 0      00000000000000000      0        000000    0000";

     //    A help  er arr ay of powers of two
        private stati c fin   al  int[]          po   wersOf2 = {1,2,      4,8,16,3     2,64  ,128,256,512,                 1024,2        048,409   6,
                                                                 81  92,16384 ,327   68,65536,13                      1072,    2621  44,524 28     8,
                                                                          10485   76  ,2    097152,419430   4,83886     0  8,16777216,
                                                                                33554432,67     1  08864, 13421  7728,2     6    8435  456,  536870   912      ,
                                                                           10      73   7418    24,-214748364     8};
         
    /    /    A helper array of p owers of 16
           priv  ate static   fin    al int[]   pow   e       rsO  f1         6 = {1    ,16   ,2   56,4096,65    536,1048576,16777216,268435456}   ;

    /**
         * If t    he       give                n    s    trin   g    starts with %X, %B or %D translat        e           s     it to a normal decimal form.
         * If the       given st  r              ing is a d    ecimal num    ber, translate  s    it int      o       a normal decimal for m     .
                * Oth  e   rwi  se, return th     e g        iven string as is.
         */
      public stat  ic String       toDecima     lForm               (S   tring value) {
        if (value.star tsW      ith("%B"     )            )
                        value = S   trin g.valueOf(bi     nar   yToInt(valu e.substring(2)));
        else if (v   alue.startsW    ith("%    X")) {
               if    (value.le    ng      th() == 6)
                             value = String.valueOf(hex4ToInt(value  .substring(2  )));
                 e  ls  e
                    value   = Strin   g .v       alue      Of(h               exToInt  (value.substring(2)    ));   
           }
        el  se i f (value.   st     artsWith("%D"     )   )
                        valu   e =  value   .substrin g(2);
               else {
                        try {
                            i  nt int     Val     ue = I     nteger.par    seI   nt(v   al   ue    );
                        valu e = Str  i   n    g  .valueOf(intValue)    ;  
                               } catch     (     N    umberFormatEx      c e      ption      nfe)      {
                      }
                }

              ret   urn value;
    }

    /**
      * Returns t    he   decimal int rep    r  esenta  tio    n of th    e   g  iven b      inar  y value.
        * Th e    bin      ary value i   s             giv en as a   string of 0's and 1's. If any other char    acter   
        * appe      ars    in  the g   iven st    ring, a               NumberFormatException is thrown.
         */
       public  sta           tic int  binaryToInt(String value) throws NumberFormatEx   ce     ption {
           int result = 0;

           f     or                                        (i nt i = valu  e.    len    gth() - 1, mask  = 1;   i   >= 0 ; i--, mas   k = mas    k   <   < 1)                          {
            char      bi      t = value.c harA    t(  i);
                 if (bit =  = '1 ')
                                   result = (shor            t )(resu        lt | m  ask);
                     els      e if (bit != '0')
                 throw   new NumberFormatExcepti  on()    ;
        }
    
        return resu              lt;
    }

    /**
     * Return   s the decimal int repre   sen  tation of          th e give n hexadecimal v      alue.
       * The he      xadecimal va         l    ue     is given as a stri      ng        of   0-9,   a-f. If an        y other ch      a    ra      cter
     *   appea   rs in t  he giv  en s        tr     in  g, a NumberFormat  Ex  c e pt    i   on is thrown.  
                  */
               public     st   at   ic int h exToInt(Strin          g   v         alue) t hrows N     umbe    rFo     rmatExcepti  on {
            int r    esult    = 0;
                          i    nt multiplier      = 1;

        for (i     nt i    = val     u     e.length() - 1; i >= 0;   i--, multiplie    r                  *= 16) {
            cha  r digit    = value.charAt(i);
                    if (   digit          >= '0' && di         git <= '    9')
                 resul   t += (digi   t - '  0') * multiplier   ;
                    else if    (di   g it >=   'a              ' && digit <= 'f  ')
                        result += (digit - 'a' + 10) * multiplier;
            else if (di       git >= 'A' && di    git <= 'F')  
                      result +=       (digit - 'A' + 1  0) * mult iplier;
             else
                             thr  ow new Num    berFormatExceptio    n();
              }

            r   eturn result;
       }

    /**
     * Returns the    decimal int repres  entation of the given        4-digit hexadecimal value     .
        * The hexad    ecimal va    l   ue    is given as a string of 0-9, a-f. If an    y other character
     * appears in the giv     en string, a Numb    e  rFormatE       xception is t    hrown.
           * The given valu   e is assumed to have exactly 4 digits.
         */
    public static int hex4ToI    nt(String        va  lue) thr       ows NumberFormatExceptio        n {
        in      t result = hexT        oInt(value);

                if (result > 32767)
                   resu  lt -= 65536;

        return result;

    }

    /**
     * Returns the binar  y stri  ng representation o    f the given int value, adding
     *  pr  eceeding zeros if the result contains less digits than the given amount of digits.
        */
    public static   String decimalToBinary(int value, int numOf   Digits) {
        v   alue = value & (p  owersOf2[numOfDigits] - 1);
              String result   = Integer.toBinaryString(value);
        if (result.length() <      numOfD  igits)
            result = ZEROS.substring(0, numOfDigits - result.length()) + result;
        return result;
     }

    /**
     * Returns the hexadeimal string representation of the given int value, adding
     * preceeding zeros if the result contains less digits than the given amount of digits.
     */
    public static String decimalToHex  (int value, int numOfDigits) {
        value = value & (powersOf16[numOf  Digits] - 1);
        String result = Integer.toHexString(value);
        if (result.length() < numOfDigits)
            result = ZEROS.substring(0, numOfDigits - result.length()) + result;
        return result;
    }
}
