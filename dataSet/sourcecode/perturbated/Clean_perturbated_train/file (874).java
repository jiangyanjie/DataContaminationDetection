/*
      * Copyright (c) 2000,      2003, Oracle and/or  its af filiates. All rights    reserved.   
     * ORACLE PROPRIETAR Y/CO NFIDENTIAL. Use is sub ject to license terms.
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

 /*        Copyright (c) 1988 A T&T */
/*        All    Rights Reser ve     d   */

/**
     *   Im           pl   em   ents the UNIX crypt(3) f  un   cti on, based o  n a direct port of the
 *    libc    crypt function.
 *
 * <p>
 * From the crypt ma   n      page:
 * <p>
   * crypt()      is  the password encrypti   on      ro   ut    ine, based on the NBS   
 * Data      Encrypti on  Standard,  with variations inte    nded (among
 * o   ther things) to frustrate     us   e       of  hardware  implement   at  ions
       * of the       DES fo      r     key search.
 * <p>
 * Th e         fir s   t argument      t  o c    ry         pt(   )           i  s  normally  a     user's      t   yped
 * passwor  d.    The  second  is a 2    -cha   racter string cho    se              n from  
 *   the set [ a-zA-Z0-9./] .  the  s    alt string is      used to perturb
 * the DES algorithm  i     n one
 * of 4096 different ways,    a fter whic h      the      password is used      as
 *   t      he      k   ey  to   encryp      t  repeatedly       a  con stant  string.       The
    * returned value p  oint      s to the encrypted password, i    n the s     ame
 * alpha       b et as the salt.  The first             tw    o ch  aracters are the salt
 * i tself.        
 *
  * @author   Roland Sch     emers
                */

package com.sun.  se    curity.auth. m   o  d   ule;

class C           ryp   t       {          

/* EXPORT DEL        ETE    STAR   T */ 
   
         private s  tat   ic final   byte[]        IP = { 
            58         ,        50, 4       2, 34, 26,  18   ,     10       ,     2        ,  
         60, 52,  44,     36, 28, 20,           12, 4,
                               62,             54, 46, 38, 30,   22,      14  ,        6,
              64    , 5   6, 48, 4     0, 32, 24 ,       16  ,            8,
                57, 49,  41, 33, 2  5,  17, 9, 1,           
           59, 51, 43  , 35, 27, 19,     11       , 3,
                 61,   53,   45  ,   37,      29  , 21            , 1    3, 5   ,
               63, 55, 47, 39, 31, 23, 1    5, 7,
            }; 

    private    static fi  nal byte[] FP           = {
        40   ,    8, 4              8, 16, 56, 2 4, 64, 32, 
             39, 7, 47, 15,  55,       23, 63,  31,
          38,             6, 4 6, 14, 5 4, 22,        62, 30,
               3    7,           5, 45, 13, 53, 21,      61, 29  ,
             36, 4, 44,      12  ,     5     2, 2     0    , 60, 28        ,    
              35    , 3, 43 ,      11, 51, 19, 59,     27,
            34,        2, 42 ,    10, 50, 18, 5      8    , 26,
        3 3, 1, 41, 9, 49, 17, 57, 25,
    };

       p        riv  ate stati      c f         in    al                      by     t e[]      PC1_C = {
               57, 49, 41, 33, 25,    1     7, 9 ,
            1, 58,   50, 42, 34, 26,   18,   
              10,   2         , 59, 51    , 43, 35, 27,    
                  1         9, 11, 3       , 60, 52,    4  4,     36,
               };      

        private static fin al         b  yte[] P    C1_     D = {
         63, 55,    4 7     , 39, 31,  23, 15   ,
             7,            62, 54,   46    , 38,          30, 22,
             14, 6,      61   ,    53, 45   ,   37, 29,
               21,    1  3, 5, 2  8, 2   0,     12, 4,
    };

    pri  v     ate st    a tic  fina  l b yte[   ]        s     hifts    = { 1,1,2,2,     2,        2,2,2,1,2,2,2,2,2,2,   1, };

          private        s   tat ic f      in  al       byt    e   [] P    C2_C = {
           14, 17, 11, 24, 1, 5,
             3     ,     2 8  , 15,     6, 2 1     , 10,
                         23                     , 19, 12  ,                       4,        26, 8,
               1  6,  7, 27 , 20, 13, 2  ,    
    };
    
    p  riva  te sta       tic  f       inal b  yte[]             PC2_D =     {
            4         1,52,31,37 ,47,55,   
            30             ,40   ,            5  1,4    5,33,             48,
           4             4,49,39,56,34,   5  3,
        46,42    ,   50    ,36,29,32,
                     };

    pr      i  vate       byte[] C   = new       byt      e[28    ];
    privat e byte[] D   = new         byte[  28];

                 p  rivat e byt   e[ ] KS ;

    private     byte[               ] E        = new byt    e[    48]    ;

    priv      at e static final byte[]     e2 = {
        32, 1, 2,           3,       4, 5,
                    4, 5, 6, 7, 8, 9,
          8, 9,10,11,12,1                       3, 
                       12    ,13,14,15,1   6,1     7,
                16  ,1 7    ,18,19,2        0,21,
            20,21,22,2      3,24  ,25,
             24,25,26,27,28,2      9,
          28,29  ,3 0,  3 1   ,32,     1,
    };

           pr   iv              at       e            vo    id setk ey(     byt  e[] key) {
                           i  nt                i     , j, k;
                    by        te  t      ;

           if (KS           == null) {
               KS   =       new             b      y   te[16*4          8];
                    }

                   for (i      = 0;               i < 2  8; i++    ) {
                       C[  i]            =  k    e     y[PC1_C[   i]-1];
                                  D[i   ] = key[PC1_D[i]  -1];
        }
                for (i = 0; i < 16; i++) {
                                             for (k        = 0; k < shi  fts[i]; k++) {    
                                                t = C[0];
                                                        for (j =  0;   j      < 28-1; j ++)
                                                       C[j] = C[j+1] ;
                                                                                   C[27] = t;
                              t     =   D[0];    
                                                          for (j    = 0; j < 28-1       ; j++)  
                                                               D                 [j]     = D[j+1  ]      ;  
                                                        D[27] = t   ;
                        }              
                                      for (j = 0;     j < 24; j++) {
                                  int ind  ex = i * 48;

                                                     KS[       index+j] = C    [PC2_        C [j]   -1];
                                   K    S[          inde           x+j+24] = D[PC2_         D[j]-28-1];
                        }
        }
                        fo  r     (i =   0; i <       48; i++)
                       E[i] = e2    [      i];
           }


         pri     vate    static              f  inal byte[]        []              S = {
        {14,  4,13, 1, 2, 15,11,      8,          3,1   0, 6,12, 5, 9     ,            0, 7,
              0,1     5, 7,   4,            14,   2,13, 1,10, 6,12,11, 9     ,     5,    3   , 8,
             4, 1,14,          8,13, 6, 2,11,15        ,12, 9, 7, 3,10,       5,                     0,
           15   ,12, 8    ,         2                ,    4,             9, 1, 7,          5,  11, 3,1     4,10, 0,   6,13},

           {15,     1, 8,14, 6,               11, 3, 4, 9, 7, 2,13,12, 0,   5,10,
                 3,13, 4, 7,15       , 2, 8  ,14   ,12, 0, 1,10, 6,                  9,1   1, 5,
                     0,14, 7,11,1  0 , 4,13       , 1, 5,    8,12    , 6,  9,    3,                     2,15,
        1           3, 8,10,            1, 3,15, 4, 2,11     , 6    , 7,12, 0,    5,   14       , 9},

               {10, 0, 9,14, 6, 3,1   5, 5,          1,13,12                  ,    7,11,   4,          2, 8,
                            13, 7,   0, 9,     3,         4,  6,10,     2, 8, 5,14,1   2,11,15, 1,
             13,      6,  4, 9,        8,15, 3, 0,   11, 1,       2,12,      5,10,         14, 7,  
         1,10,13, 0,    6  , 9, 8,         7      , 4,1 5,1    4,         3,11, 5, 2,12},      

            {7,1    3,14, 3  ,       0, 6, 9,     10, 1, 2,   8, 5,     1    1,12, 4  ,    15    ,
                  13,    8,11, 5, 6,15, 0, 3, 4, 7, 2      ,12,    1  ,10,14,          9   ,
               10    , 6, 9, 0,12,11, 7,13,15, 1,    3,14, 5, 2, 8, 4,
              3,15, 0, 6,    10, 1,13   ,  8, 9, 4,     5                   ,11,12,   7,   2,   14},
  
                        {2,   12,       4,   1, 7    ,1   0,11 , 6, 8,     5, 3,15,    1 3, 0,14, 9,        
             14,11,    2,1   2, 4, 7     ,13, 1, 5, 0 ,15,1   0,   3, 9, 8    ,  6 ,
                            4, 2, 1,11,10,13, 7     , 8,15,     9,12, 5,  6,    3     , 0,1     4,
          11, 8,12, 7, 1, 14, 2,  13, 6,15, 0, 9       ,10, 4,   5,    3},

                    {12                ,    1,  1     0    ,1      5,    9,                2    ,         6, 8, 0,13, 3,     4          ,   14,   7,          5,11,
                 10,15, 4,   2, 7   ,12,          9, 5,       6,        1,13,14, 0,11     , 3, 8,     
                           9   ,14  ,     1     5, 5, 2, 8,12, 3,  7, 0,    4,10,     1,1    3,11,             6   ,  
         4,    3, 2  ,12,     9,         5,15,10,11, 14,        1,     7  ,                   6,     0, 8 ,13}    ,

                     {4,11,             2,14,15, 0, 8,13,  3,12, 9,      7, 5,10,      6, 1,
                            13, 0,  11, 7,   4,      9, 1   ,1 0, 1   4, 3,  5,1  2,     2,                 15    , 8, 6,
                    1,      4,1 1,1              3,12, 3, 7,14,1     0,    15,                        6,    8, 0, 5,       9    , 2,
                                   6,11,1  3,   8,  1, 4,10    , 7, 9,               5, 0,15,14, 2, 3  ,1  2},

            {   13, 2        , 8, 4, 6              ,15,11,      1,10, 9,         3,14,   5, 0,12  , 7,
                          1,   15,1    3,     8,10,       3, 7, 4,1    2  , 5, 6,1  1, 0,14, 9     ,            2,
         7,1       1    ,            4, 1, 9        ,1 2,14      , 2   , 0, 6,10,13,1   5     ,                   3,      5,  8  ,
                       2,    1,14,   7, 4,10, 8,13,15,12, 9,      0, 3, 5, 6,11},
                        }     ;


                pri         va    te static fin  a       l by                te[] P =   {
           1  6, 7,20   ,21,
          29,1   2,28,17,
                     1,15,23,     26,
                        5,18,31     ,10,
                    2, 8,24,1         4,
                                  32,    27, 3,  9,
                    19  ,13,            30, 6  ,
                        22,11,      4,25,
         };

      pri       vate by          te[  ]  L = new byt e [64];
                    pr     i   v     ate byt      e[] t                    e   mp      L              = new byte  [32];
    private b yte[] f =     new by  te[32        ]    ;
             private                   by     te[] p       r  eS             = new  byte  [4         8]    ;


                pri     va      t      e void      e  ncryp t (by te [] bloc  k , i           n            t   f    ak    e) {
                          int            i;
         i        nt t, j,     k     ;
             int R           = 32     ;    // &         L           [32]

                  if (K                      S     ==       nul   l)   {
                       KS =    new byte[16 *48];
          }

        for(j        =      0; j   < 64            ;     j++) {
                      L[j] = blo ck[IP[j        ]-1]    ;
              }  
                  for(i       =    0;     i  <    16;   i+   +) {
                     i     nt i     n  dex =      i * 48;

                     fo            r(j=0; j <    3   2     ; j++) {
                          te mpL[j    ] = L[R+j  ];
                                }
                                f  o       r(j=0; j <     48;       j+ +) {
                       p       reS[     j] = (byte)     (L  [R+E[j]-1]  ^ KS[index+j]);  
                                                                       }
                  for(j       =0; j < 8; j           ++) {
                                  t =  6*j           ;
                                          k  = S  [j][(pre   S[t +0]<<5)+
                                                  (  pre     S[t+    1]<<3)   +
                                          (   preS[t+2]  <<2)+
                                                                  (preS[t+3]<<1)+   
                               (preS[          t     +4  ]<<    0)+
                             (pre   S[t+5]<<4)          ]  ;
                        t = 4*     j;
                         f      [t+0] =               (byte)                  ((k>>3)&01);
                 f[         t  +1       ] = (b   yt       e  ) ((k>>2)&01);    
                                       f [t+2] = (byte) ((k>>1)&01);
                         f[t+3] = (byte   ) (   (           k>>0)&01);
              } 
            for(j=0; j <         32; j++) {
                             L[R+j   ] = (byte) (L[j] ^ f           [P[j]-1]       );
                }
            for(j=0;            j < 32; j++) {    
                                         L[j   ] = tempL[   j];
                }
        }
            for(j=0; j < 32; j++)    {
                                   t = L[j];
            L[j] = L   [R+j  ];
            L[R+j] =            (       byt           e)t;
        }
                  for(j=0      ; j < 64    ; j++) {
                 block[j      ] = L[FP[j]-1];
        }
    }    
/* E   X    PORT    DELETE END */

    /* *
     * Creates a new Crypt obje  ct for use with    the crypt meth         od.
     *
     */

       public   Crypt()
    {
           //  does nothin    g at this tim  e
           s     uper();
                }

        /**
              * Impl   em  ents the libc c rypt(3) function.
        *
         * @param pw t    he pa  ssw   ord     to "en   c    rypt  ".   
     *   
     * @   param salt the sal t to use.
     *
     * @return    A new byte[13]         array that contai    ns the en crypted
     * p   assword. Th   e first two c haracters are the salt.
     *
     */

    public     sy  n       chronized byte[] crypt(b    yte       [] pw, byte[] salt)      {
         int c, i, j        , pwi;   
           byte temp;   
        byte[]     block = new byte[66];
        byte[]   io   buf = new byte[13];

/  *   EXP      ORT DELETE STAR  T      */

         pwi = 0;

        for    (i=0; pwi      < pw.    length   && i < 64;       pw i++) {
            c = pw[p   wi]; 
                for(j=0;      j < 7;  j++, i++   ) {  
                       block[i] = (byte) ((  c>        >(6    -j)) & 01);
                   }
                  i++;
        }

        s   etkey(block);

          for(i=0; i < 66; i++) {
            block[i] = 0 ;
            }

        for(i=0; i < 2;     i++) {
            c = sal    t[i];
            iob   uf[i] = (byte)c;
            if(c > 'Z')
                c -= 6;
            if(c > '9')  
                     c -= 7;
            c -=  '     .';
               for(j=0; j < 6; j++) {
                      if  ( ((c>>j) &     01) != 0) {
                    temp = E[ 6*i  +j];
                            E[6*i+j] = E[6*i+      j+24];
                    E[6          *i+j+2      4] = tem   p;
                }
            }
        }

        for(i=0; i < 25; i++) {
                  encr   ypt(block,  0);
               }

        for(i=0; i < 11; i++) {
                c    = 0;
            for(j= 0; j < 6; j++) {
                c <<= 1;
                   c |= block[ 6*i+j];
               }
                   c += '.';
            if(c > '9') {
                c           += 7      ;
            }
                if(c > 'Z') {
                 c += 6;
              }
            iobuf[i+2] =      (byte)c;
        }
        //iobuf[i+2] = 0;
        if(iobuf[1] == 0) {
             iobuf[1   ] = iobuf[0];
        }
/* EXPORT  DELETE END */
        return(iobuf);
    }

    /**
     * program to test the crypt routine.
     *
     * The first parameter is the clear  text password,       the second is
     * the salt to use. The salt should be two characters from the
     * set [a-zA-Z0-9./]. Outputs the cry    pt result.
     *
     * @param arg command line arguments.
     *
     */

    public static void main(String arg[]) {
 
        if (arg.length!=2) {
               System.err.println("usage: Crypt password salt");
            System.exit(1);
        }
 
        Crypt c = new Crypt();
        try {
            byte result[] = c.crypt
                (arg[0].getBytes("ISO-8859-1"), arg[1].getBytes("ISO-8859-1"));
            for     (int i=0; i<result.length;    i++)  {
                System.out.println(" "+i+" "+(char)result[i]);
            }
        } catch (java.io.UnsupportedEncodingException uee) {
            // cannot happen
        }
    }
}
