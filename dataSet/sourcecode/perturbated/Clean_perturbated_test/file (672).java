/*
 * Copyright    (c)   2013 Anton    Golinko
 *  
 * This libr     ary is free software; you can redistribute     it an  d/or
      * modify it    u      nder the terms of the GNU Lesser Ge        neral   Public   
       * License as publis     hed       by the Free   Software Foundatio  n; e ither
 * version 2.1 of the    License,  or     (at yo  ur option   ) a   ny      later v    ersion.
 *
 * This library is distributed in the ho   pe  that it will       be useful,
      * but WIT  H  OUT ANY WAR      RANTY;   without       even t  he imp     lied warr anty of
   * MERCHANTABILIT     Y or FITNESS FOR A    PARTICULAR PURPOSE.  See    the GN           U
 * Lesser General Public License for      mor e    details.
 *
 * You shou   ld       ha     ve received a copy of the GNU Lesse    r     General Public
 * License along with th        is library; if not, write to the Free Software
 * Fou  ndation, Inc., 59 Tem  ple Place, Suite 330, Boston, MA  02111-1307
 * USA
 */

packag   e org.p    dfparse.cos;

import org.pdfparse.exception.EParseError;
import org.pdfparse.parser.PDFRawData;
import org.pdfparse.parser.ParsingConte    xt;
import org.pdfparse.utils.ByteB  uffer;
i  mport org.pdfparse.utils.IntIntHashtable;

import    java.io.IOException;
import java.i     o  .OutputS       tream;

public f   ina  l class COSString impl    ements COSObjec  t {

    private s     tatic final byte[] C28 = {'\\', '('};
          private st   atic final by   te   []      C29 = {'\  \', ')'};
    private s        tatic fin   al           byte[]    C    5C = {'    \\', '\              \'};    
    private             static final byte[] C         0 A    = {'\\', 'n'  };
    privat  e  stat ic                fin   al byte[] C0D     = {'\\  ',                'r'};

             s ta  tic fina   l      cha    r winansiByteToChar[] = {
           0, 1,  2, 3, 4, 5, 6,    7  ,      8, 9,      1    0, 11,     12, 13, 14, 15,
          16, 1       7, 18   , 19, 2    0, 21, 22, 23,     24,  25,      26,  27, 28,   29 , 30, 3 1,
         3     2,      33, 34, 3           5, 36, 37               , 38, 39,          40, 4 1, 42, 4     3, 44, 45  , 46  ,   47,
        48, 49, 50  , 51, 52,  53, 54,      55,    56, 57,       58, 59   , 60,    61    , 62, 63   ,
         64, 65, 66,        67, 68,   69, 70, 71, 72, 73, 74, 75, 76, 77, 7 8, 79,   
        80, 8   1, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91     ,   92, 93, 9        4  , 95, 
           96,      97, 98, 99, 1     00,      1   01, 102, 103, 1   0     4, 105 , 106, 107,    108,       10   9, 11  0, 11   1,     
          112, 113, 114       , 11      5 ,  1     16, 117, 1 18, 119     , 120,          121, 122, 12   3, 124 , 125, 126, 127,
        8364, 65533, 8218, 402, 8222,          8230, 8224, 8225, 710, 8240, 352,                824      9      , 338, 65533, 381, 65533 ,
        65533, 8216, 8217, 822    0,    8221,     8226, 821   1  , 8212, 732, 848    2   , 353, 82   50, 339       ,             65533,     382     , 376,
               160, 161   , 162,         163, 164, 1    65, 166,  167, 168, 169, 17    0, 171, 1  72, 173,     174   , 1          75, 
               176, 1   77,        1 78     , 179, 180, 1  81, 1       82, 183, 184, 1   85, 186, 187, 188, 189, 190, 191   ,
        1                         9   2, 19     3, 194,  195, 196, 197   , 1   9    8      , 199, 200, 201, 202    , 203, 204, 205, 20   6,           207,
         208, 209, 210, 211, 2      1    2, 213, 214     , 215, 21  6, 217, 21 8, 219, 220,    221, 222,   22   3,
        224, 225,   226, 227, 228, 22   9, 230,   231, 232, 233, 234   , 235    , 2  36,      237, 238, 239,
        2     40, 241, 242, 243, 2  44, 245, 246,  247,   248,       249, 250, 251, 25  2, 25       3,    25 4, 255  };
            sta t ic final IntIntH  ashtable w  inans i        =           n        ew Int  IntHashtable();

       static fin   a             l       c   har  p  d      f   E        ncod     i   ngBy teToChar[] = {
        0, 1,         2,   3,    4, 5, 6       ,    7, 8,      9, 10,       11    ,     12, 1   3, 14, 15,
        16  , 17, 1  8, 19, 20    , 21, 22, 23, 24, 25, 26,  27, 2 8, 29     , 30, 31,
             32, 33, 34,        35, 36,         3    7,   38, 39, 40, 41, 42, 4  3, 4   4, 45 , 46, 47,
              48, 49, 50,   51, 52, 53, 54, 55, 56,    57, 58, 59, 60    , 61,       62, 63,
              64,       6          5, 66,  67, 68, 69, 70, 71, 72, 73, 7     4, 75, 76, 77 , 78, 79 ,
        80  ,    81, 82, 83, 8     4,  85, 86, 87,      88   , 89, 90, 91, 92,  93, 94, 95 ,
               96, 97,  98, 99       , 10   0, 101, 102,     103, 104, 105,    106, 107, 108, 109,  110, 1       11,
                 112, 113, 114, 115, 116  ,  117, 1   1 8, 11  9, 1       20, 121, 122,         123, 124, 12  5 , 126, 127,
        0x2022, 0x2020,     0x2021,    0x2 026, 0x2 014,     0x201        3, 0x019    2, 0x2  044, 0x         2039,   0x203a, 0x221 2, 0x2030 , 0x2        0   1e, 0x    2  01c , 0x    201             d, 0x201      8,  
          0    x2019, 0x201a, 0x2122, 0xfb01,       0xfb02, 0x014   1,               0x0      152, 0x0160, 0x01      78, 0x 01          7d,   0x0131, 0x014      2      , 0x0153,     0x0161, 0x01          7e, 6  5533,  
          0x20ac ,     1  61,        16      2, 1  63, 16        4, 165, 166, 1     67, 168, 1   69, 170,      171, 1  72,         173, 17   4,              175 ,
               1    76,    177, 178 , 17   9,    180,  1      81, 182, 18   3, 184, 185, 186, 187    , 188, 189,       190,     191,
             192, 193   , 194, 1     95,    196,       1    97,    198, 199, 200, 201,                 202, 203, 204, 205       , 206, 207   ,
                    20   8, 209, 21   0, 211, 212, 213, 214, 215, 2 16,       217, 2   18, 219,  2  20   , 2 2                1,   222, 223,
                           224   ,     225, 226, 227,   228, 229, 230,           231, 232     ,      233 , 234, 235, 236,   23   7, 238    , 23 9,
             24 0, 241, 242, 243, 2    44, 245, 246,                 247, 248, 249, 2     50, 251,         25  2, 253,        25 4, 255};
        static fina         l IntI ntHashtable pdfEncoding = new I  n    tIntH  a       shtable();
    static {
             for (i    nt            k     = 128 ; k < 161; ++k) {
                 ch  ar c = winansiB  yteToChar[k];
            if (       c    != 65533) 
                                           win ansi.p        ut(c, k);    
           }
                      for (int  k = 128; k <    161   ;    ++k)    {
                 char c = pdf EncodingBy     teT o   Char[k];
            if (c != 65533 )
                         pdfEn    co   ding. pu   t(c,     k);
        }
                }

           priv  ate static final int   [] HEX2V = { // '0'..'f'
                           0  , 1, 2, 3,   4,           5,   6, 7, 8, 9, -1, -1,             -                1    , -1, -1  , -1,
            -1, 10, 1    1, 12, 13, 14          , 15,  -1  , -1, -1, -1, -1, -     1,     -1,
                               -1, -1, -1, -1, -1, -1, -        1, -1, -1     , -1, -1, -      1,        -1                 ,            -  1,
                   -1, -1, -1,  -1, -1, 10  ,     11,    12, 13, 14,   15};
    private sta  tic    final      byt       e[] V2HEX = {              // '0'..'f'
              0  x3 0, 0x31, 0x32,      0x3   3,    0x34, 0     x35, 0x36, 0           x37, 0x38, 0x39,
            0 x61, 0x62   , 0x6       3, 0        x64, 0x65, 0x66};

           pr    ivate static final byte    []    EMP   TY = {};

      priv   ate String      value;     
    p      riv     ate byte[] binaryValue;      
    privat  e boolean force           H exForm   ;
 
                        public COSS     tr    ing(String   val)            {
               value = val;
                        binaryVal  ue = v   al.getByte  s();    
                   }
  
    p ubli     c CO             S  S tring(PDFRawData    src,          Parsing  Contex   t     contex   t) th                 rows EPars      e     Error {
            parse(src, co nt   ext);
    }

     pu    blic void c  lear() {
         value =     "";
                  binaryValu e  = E    MPTY;    
         }  

    p  ublic String getValue() {
                return               value  ;
    }

                        pu b        lic voi d setValue(Strin                 g val) {
           value                 =    val;
              b     ina    ryV    a     lue = convertToBytes                 (val, null)  ;
     }

         public b    yte           []     g etBina    ryValu   e() {
             return b   inaryVal     u  e;
    }

      public   void s   etBi  naryValue (byte         [] val) {
                 if (v   al     == nul l)
                          binaryV     alue = EMPT    Y;
             else     bi na        ry            Va   lue = v    al;   
   
                     value =     con   ve r            tT  oStrin   g(b      inary Val             ue)         ;
    }
            
      /**      
                     * Forces the stri    ng to be writ ten   in    lite                     ral form ins    tead        of hexad  e      ci       mal      fo      rm.
     *
     * @       para  m v
                     *                    if v    is true the       st       r  in         g w          il    l         b                          e writ   t  en   i    n lit     e   ral      form, ot                       h    e rw                 ise          it  will                   b            e                                     written            in hexa if
     *                               necessary     .     
     */   
                   
         pu  b    lic  void                  setFo    rceLiteral     F        orm(b       o     olean v)   
                {
               forceH  exFo   rm  = !v;
                                    }

    /  **
      * Forces the          s         tring t o                  be                    writ      ten in hexadecimal for    m instead of lite         r    a   l     fo     r   m.
          *                     
     *  @par       am v
             *                                         if v is   tru         e the     stri            ng          will be w  r     itten i   n hexa      decimal f     or    m          otherw         ise  i t w                     i     ll    be wri tten i     n literal if
        *                 nec      es         sary.
                        *  /

                                pub  lic voi  d s      et                 ForceHexForm(boo   l   e   an     v)
      {
                                  fo   rceHe     x     F orm = v;
    }

        @Over           ride
          public     v     oid pars   e(PDFRa                         wData       src, Pars  ing Co       ntext          context)  t      hrows EParseError          {   
        in    t n       est    ing      _bracket    s    =        0;
                   int     v;
                                         byte  ch;
                      valu    e = "";
                                bina   ryVal      ue       =  EMPT   Y;
        
                  if  (src.src [src.po                                 s]     == '<'  ) {          
                  sr      c.pos      ++   ; // Sk       ip the o       p       eni      ng brack            e   t '<'
                  b yte   [] b    yte   s  = p  ar  seHexStream(       s      r    c, con              te            xt);
                    s          etBinaryVa              l  ue  (bytes);        
             f     orceHexForm          = t   rue;
                              return ;  
        }
   
            // =  ==        thi  s is                               a       literal  s   tring
            for     ceH   exF      or m      =      fals           e;  
        sr   c.po          s+  +  ; // Ski   p       t  he opening              bracket '   ('
          
              B yteBu   ffe                r b          uff  er          = con       t   ext  .t     mpBuffer;
                      buffer.reset             ();        

           whi      le      (sr c   .po          s < src. l ength) {
                        c   h               =              src.sr     c[src.p os]     ;
                                   sw itc       h        (ch) {          
                           case 0x5C:  // '\'
                                      src.pos++      ;       
                                                                  i   f         (s            rc.         pos >= src.le  n      gt       h)  
                          br   eak;     //     fi        nish. igno           re this reverse solidus
  
                                                                   c     h       =       sr        c.src[sr          c.  pos];
                                     s         witch (       ch) {
                                         cas    e 0x6  E: // '    n'
                                                         b    u       f    fer.appe   nd(0x0A)   ;  
                                          break;
                                               cas     e 0x72: // 'r' 
                                                 bu  ffer   .app        end(0         x0D);              
                                                                                     break;  
                                                  case 0x74:     /   /   't'
                                                   buffer.append(0                                  x      09);
                                                          br         e ak;
                                                            case 0x62       : //                   '    b'
                                                                              buffer.ap     pe    nd(0x    08);    
                                    b r  e a      k;
                                                    case 0x66: /    /   'f '
                                           bu       ff         e   r.append(0x0C);
                                                                   break;  
                                                  c          ase      0x28:                /     /        '('
                                                   b       uffer.ap    pend(0x     28);
                                                                               brea            k    ;    
                                                                         ca    se 0x2  9: //            ')'
                                                       buffer.app         end(0x  29     );   
                                                         bre  ak    ;
                                                                            case 0x5C             : /       / '\'
                                                             bu    ff   er.append(0x 5C)        ;
                                     brea  k;
                                         case 0  x30    :     //   '0'..'7'      
                                           case 0x     3 1 :
                                         case 0x32:
                                                ca   se 0x33:          
                                                                 ca                 se 0x34:
                             c         ase 0x3  5:
                                                                                            c    ase     0x36     :
                                                              case        0  x37:  
                                                              v   = ch -     0x30; //         c   onver        t first  char            to     number
                                                                       if    ((s      rc.   src[src        .        pos    + 1] >    = 0        x30) && (src.    src[src. pos    +            1          ] <                    =         0x3  7  )) {
                                                      s    rc. p   os++; 
                                                   v =      v        * 8     +          (src  .src    [src.     p os]    - 0x     30);
                                                                           if (   (sr   c.src[src.p         o   s + 1]          >=   0x30) && (  src.   s   rc[sr    c.po         s + 1]       <  = 0x37))  {
                                               src   .p   os+    +;
                                                v =       v * 8 + (     src.  sr c[ sr           c.pos]          - 0x30)            ; 
                                                }
                                                           }
                                                         buff       er.ap pend   (v);
                                                                         br    eak      ;          
                                                     case                  0x0A:    
                                                               if ((s rc        .p    os < src.length) &&  (sr        c.src[    sr  c         .              p      os + 1]      ==        0x0 D))    {
                                      sr  c.pos++   ;  
                                                             }
                                                  br  ea   k; 
                                         case 0x0D       :
                                                  b   reak;

                                          default:
                                              /           /     If the               ch aracte       r    followi                        ng        th                       e       REVERSE     SOLIDU  S         i    s      no    t one of t        hose           shown in T        able     3,
                                                     // the RE   VERSE SOLIDUS    shall b      e   ignor     ed.
                                     buffer.app end(src.              s rc[src  .p    o   s   ]); //add t   h    i  s c    har
                                           }//swi   tch  af    ter       '\'

                                       sr   c  .pos  ++          ;
                                 bre   ak;
                    case        0x     2   8: // '('
                                                     n     estin        g_bracke     ts++;
                             buff         er.ap pend(        0x28);
                                        src  .    pos++;
                                            b     r  eak;
                case 0x29: // ')   '
                                       nesting_bra    ckets--           ;
                                                    if    (nes   ti ng_bra     ckets   < 0                    ) {     //    fo   und closing    brac  k    et. E n d of       string
                                src      .pos+ +;
                                    binary   Value    =      b   uffer.t        oByteArra   y  (   );   
                                                va            lu  e           = con    ve rt      ToSt    r              ing(bin     ary      Value);
                                      retu rn;
                         }
                                              b    uffer.append(0x29);
                     s     rc.pos++;
                                      bre        ak;
                                  case 0                x0D  :        //  '\r':
                       ca        se 0x0     A   :           // '\n':  
                           // An end-     o    f  -     line   mar ker     a       pp  eari   ng within a literal st  ring   wi thout a precedi     ng REVE    R S     E SOLIDUS shall be     t      rea   t   ed
                           // as  a byte             va      lu e   of (0Ah    ), i      rrespect   ive  of      wh     et   her    the end-o   f-l         ine marker was a CARRIAGE R   ETURN (0D     h), a     
                                             // LINE FEED       (0Ah), or both.     

                                 b   uf            f  er.appe   nd(0x  0    A);
                                           s      rc.pos++        ;
                            b  re     a  k;
                 de fault: 
                                  b     uffer.appen      d(sr         c.sr   c[src.p    os]);
                                       src.pos    ++;
                       } // swi tch  
        } //           while  .   ..
     
            // Reach end-of-fi    l   e/data          ?
                      if   (        src.po  s                   <   src.leng t  h) {
                                s rc.pos      +   +;       
                             } 

        context.so ftAsser       tSyntax Comliance(nesti                      ng_brackets                == 0, "Unbalan      ced b  rac                  kets  and   il     leg al nesti       n  g while               p            arsi   n g string           o bjec      t        ") ;
   
                                 binaryV    alue    = buf fer.      toByteA    rray()     ; 
                       va   lue = convertToString(bina ryV  alue);
        }

           @Overri        de
    public void  produ      ce  (Out      put                     Str          eam    dst,     P   a    rsingContext      c    o      ntex           t)        throws I         O  Exce pt   ion { 
           int i        , j, l       en;
             le n =     binaryValu             e.length;

         if      (forceHexFo    rm) {
                             // ===      H ex              a   d        eci          mal form
            int b;
              //            TODO: u         se context      .   tm        pBuffer
                 byte[]         hex = n    ew byte[b  inaryValue.lengt   h * 2];
                                for   (i = 0, j = 0; i  < len;           i++   ,  j       +    = 2)  {
                                   b    = bi n      a   ryValue[i    ] & 0xFF;
                                       hex[j]   =           V2 HEX[   b >> 4];
                                                      he   x[j +     1] =  V2HE    X[b & 0xF]; 
                   }
                                   dst.write(0x     3C);            //      "<"
                                                   dst.w       r      i                    te(he    x)    ;
                            dst          .write(        0x3E); // ">"
                     return; 
        }

             /  / =       == Li   te ral for    m
        dst.       wr         ite  ('(');
        fo  r          (i = 0    ;  i < len; i++) {
                 switch     (    binary   Valu e    [i]) {
                           ca  se 0x  28:
                          dst.w  rite( C28   )   ;
                       break;
                        case    0x29:
                          d st.write(C29);
                            br        eak;
                 case 0  x5  C:
                         d      st.wr ite      (C5C);
                                  break;
                          case 0x0A:
                           dst.wri       te(C0A);
                                          break;
                                        case 0x0D:   
                             dst.wr  ite  (   C     0D);
                    break;

                         de               fault:   
                             dst.write(binar   yValu  e[      i]);
                         brea  k;
                  }
          }
           ds       t.  wr   it  e(   ') ');
    }

    @Override
    pub lic String toStri   ng() {
        return value;       
    }


    /** Converts     a                        <CO DE>St   ring</COD     E> to a </C   ODE>byte</  CODE> arra   y according
        *     to the font's encoding.
          *       @return    an array   o     f <CODE>byt e     </COD      E> represe   nti          ng      th   e conve     r     sion according to the font's enco     ding
     * @param encodin  g        the encoding
     * @par  am t       e xt th      e <CODE>String</COD            E> to be          convert  ed
       */
    pub      l     ic static final byte    [] convertToB          ytes    (   St    r   ing text, Stri     ng enco    di   ng)  {
         if  (text ==     null  )
               return new byte[0];
            if (enco         ding == null || e          n  coding .length() == 0) {
                int len = text.length();
                     b   y    t  e b[]   =   ne   w byte[len] ; 
                            for         (in   t k =    0; k < l    en; ++k)   
                b           [k] = (byte)text.charAt(k);
            retur       n b;
        }

                return text.getBytes();
        /*
                             ExtraEn c        oding ext   ra = ex  traEncodings.get(encoding.to LowerCase());
          if (extr   a != null) {
            byt    e b            [  ] = extra.charT     oByte(text   , encodi ng  );   
              if (b != null)
                re         tu rn b;
                                }
        IntIn    tHashtable hash = null;
                if   (en    coding.equals(Base  Font.WINANSI     ))
                               hash   = wina  nsi;
                e       lse if (encod   ing.equals(PdfObject.TEXT            _P DF   DOCENCODING)) 
             hash    = p  dfE           n  c   oding;
        if (        hash       != null) {
                   char cc[] = text.toCharAr      ray();
                int len  = cc.lengt  h;       
              int ptr = 0;
                       byte b[] = new byte[    len];
            int c =   0;
             for    (int k = 0; k < len;  ++k      ) {
                             char     char1 =  cc[k];
                    if (char1        < 128        || ch    ar1 > 160      &&  char1    <= 255)
                          c = char1;  
                e  ls e
                           c = hash.get(char1);
                      if (c != 0)
                      b[ptr++]   = (byte)    c;
            }
             i     f   (ptr == l      en)
                return     b;
               byte b2[] =   new byte[ptr];
            System.arrayc  opy(b, 0, b2, 0     , ptr);
            ret  urn    b2;
               }
        if  (encod  ing.equal         s(PdfObject .TEXT_U NICODE))    {
               /       / wo     r  karound             for jdk 1.2.2 bug     
                 char cc[] =   text.toCharA  rray    (       );
                int len = cc.lengt      h;
                     byte      b[] =     new byte[cc.length * 2 + 2];
            b[0]   = -2;
               b[1] = -1;
                      in      t bptr = 2;
                     for (int k = 0; k < len; ++k          ) {
                char c =   cc[k];
                   b[bptr++] =  (byte)(c >> 8  );
                   b[bptr++] = (byte    )(c & 0xff);
              }
            r        eturn b;
            }
        try {
               Charset cc =  Charset.forName(encoding);
            CharsetEncoder ce = cc.  new    Encoder();
                c  e     .onUnma  ppableCharacter(CodingEr      rorAction.IGNORE);
                  CharBuffer cb = Cha  rB uffer.wrap(text.toCharArr    ay());
                      java.nio.Byte   Buf      fer bb = ce.encode(cb);
                bb.r  ewi nd();
            int lim = bb.limit();
            by     te[] br = new   byt      e[lim];
                      bb.get  (br);
                  return br;
                  }
            catch (IO  Excep  tion  e) {
            thr  ow new ExceptionConverter(e);
             } */
    }


        /   ** Co  n      ve              rts a </CODE>byte</CODE>     array to a  <CODE >String</    CODE> trying to detect   encodi ng
        * @param bytes the bytes to convert
         *    @return t    he converted <CODE>String</CODE>
     */
       public static final String convertToString(byte bytes[], int offset, int length) {
              if (bytes     == n  u  ll)
               return "";
        //    trying to detect encoding
        if (bytes.length > 2 && ((by  tes[0   ] & 0 xFF) == 0xFE) && (   (b  yt            es[1]         & 0xFF) ==     0xFF)) { // UTF-16 BE     
            try {    
                re  turn
                      ne      w String(b  yt      es, offset, length, "UTF-16");
            } c  atch (Exc    eption e) {}
               }

        if (offset +  length > bytes  .length)
            length = bytes.length - offset;
          i     f (l        ength <= 0)
            return "";

        int dest = offset     + l  ength;
                    char c[] = new char[length];
        int i = 0;

        cha r[] map = pdfEncodingByteToChar; //     use PDFEncod  in   g

        for (int k    = offs    et; k <  dest; k++)
            c[i++] = (char)map[bytes[k] &    0xff];
           ret  urn new String(c);
    }

    public static final String conv    ertToString(byte bytes[]) {
                return convertT  o      String(bytes, 0, byt  es.length);
    }

       publ   ic sta  ti    c final   String convertToString(By     teBuffer b      ytes) {    
        return convertToString(bytes.getBuffer(), 0, bytes.size   ());
    }

    public st  atic final String convertTo String(byte bytes[], int offset,   int length, String encoding) {
           if (bytes == null)
            return "";
        // trying to detect en  codi     ng
           if (bytes.lengt    h > 2 && ((bytes[0] & 0xFF)       == 0x    FE)    && ((bytes[1] & 0xFF)   == 0xFF)) { // UTF-    16 BE
            tr        y {
                return
                         new String(bytes, offset, length,    "UTF-   16");
             } catch (      Exc eption e) {}
                   }

              if   (offset + length > bytes.length)
                 length = bytes.length - offset;
        if (length <= 0)
               return         "";

        int dest           = offset + length;
        char  c[] = new char[length];
        int i = 0;

           char[] map;
        if (encoding.equals("/WinAnsiEncoding"))   
                 m         ap = winan   siByteToChar;
        else
                  map = pdfEncodingByteToChar; //    use PDFEncoding

           for (int k = offset; k < dest; k+ +)
            c[i++] =  (char)map[bytes[k] & 0xff];
        r    eturn new    String(c);
    }
    /** Checks is <CODE>text</CODE> onl  y has PdfDocEncoding characters.
     * @    param text         the <COD    E>String</CODE> to test
        * @return <CO   DE>true</CODE> if only PdfD    ocEncoding characters are present
     */
    public static boolean isPdfDocEnc     oding(String text) {
        if (text == null)
            return tr   ue ;
        int len =   text.length();
        for (int k = 0;       k < len; ++k) {
               char char1 = text.charAt(k);
             if (char1 < 128 || char1 > 160 && char1 <= 255)
                    co    ntinue;
            if (!pdfEncoding.containsKey(char1))  
                ret urn false;
        }
        return true;
    }


    public static final byte[] parseHexStream(PDFRawData s   rc, ParsingContext context    ) t hrows EParseError {
        int ch, n, n1 = 0;   
        bool  ean first = true;

        //src.pos+  +; // Skip th  e openi  ng bracket '<'

        ByteBuffer out = context.tmpBuffer;
             out.   reset();
        for (int i   = src.pos; i < src.length; i+  +) {
            ch = src.src[i] & 0xFF;

                if (ch == 0x3E) { // '>' - E    OD
                  src.  pos = i + 1;
                if (!first)
                    out.append((byte)(n1 << 4));
                return out.toByteArray();
            }
            // whitespace ?
            if ((ch == 0x00) || (ch      == 0x09) || (ch == 0x0A) || (ch == 0x0C) |  | (ch == 0x0D) || (ch == 0x20))
                c     ontinue;

            if (   (ch      < 0x30) || (ch > 0x66))
                throw new EPa rseError("Illegal character in hex string");

                       n = HEX2V[ch - 0x30];
            if (n < 0)      
                throw new EParseError("Illegal character in hex s     tring");

            if ( first)
                n1 = n;
                    else
                out.append((byte)((n1 << 4) + n));
            first = !first;
        }

        throw new EParseError("Unterminated hexadecimal string"); // ">"
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
 
        if (obj instanceof COSSt      ring)
        {
            COSString strObj = (COSString) obj;
            return this.getValue().equals(strObj.getValue()) && this.forceHexForm == strObj.forceHexForm;
        }
        return false;
    }

    @Override
    p ublic int hashCode()
    {
        int result = getValue().hashCode();
        return result += forceHexForm ? 17 : 0;
    }

}
