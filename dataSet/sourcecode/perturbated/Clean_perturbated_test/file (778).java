package LZMA;

import   LZMA.CRangeDecoder;
impo  rt java.io.IOException;
import java.io.InputStream;

class CRangeDecoder
    {
  static   final int kNumTopBits = 24;
  static      final i nt kTo pValu e = 16    777216;
  sta                tic final int kTo   pValueMask = -16777216;
  static final int k      NumBitM  odelTot      a       lBit           s = 11;
  sta     tic    final int kBitModelTotal = 2048;
         static fin   al int kNu mMoveBits = 5;
    I  nputStream inSt    ream;
     i  nt Range        ;
  int Code;
  byte[] buffer;
  int buffer_si    ze;
  int buffer_ind;
  static final int kNumP   osB  itsMax = 4;
    static final int kNumPosState       sMax = 16;
  static final int kLenNumLowBi  ts = 3;
  static final int kL  e              nNumLowSymbols =   8;
    static final int kLenNu mMidB      its = 3;
  static final   int kLenNumMidS     y    mbols = 8;
    static  final     int kLenNumHig      hBits =        8;
      static fina  l i  nt kLenNumHighSymbols      = 2 56;
  static     final int Len            Choice   = 0;
  static final int LenCh   oice2 =    1;  
  s   t    atic final int LenLow = 2;
    static      final int       LenM   id = 130;
     stat          i   c final int        LenHigh = 25   8;
  st   atic final  in t kNumLenPr  obs = 514;

    CRang        eD    eco   d  er(I                np     u tStr e       am paramInputStrea   m)
    throws IOExc    eption
  {
    b   uffer = new byte[16384];  
    inS      tre   am = paramIn putStre   am;
        Code =   0;
    Range =       -1;
                       f  or (int  i =      0;     i < 5;            i++)
      Co  de   =    (Code << 8 | R          ead  byte());
  }
      
  int Rea     dbyte() throws IOE    xceptio       n {
                       if (   b    uf     fer_size     == buffer_ i        nd)     {
                        buffer_      si  ze = inS   t    ream.read(buffer);
      buffer_ind = 0;

         if (buffe      r_size < 1)
               throw new L       zma  Exception("L   ZMA :    Data Error");
      }
        re     tu rn buffer[(buff    er_ind++)] &    0xFF; 
  }

  int Dec ode     DirectB  its    (int       paramI               nt)                 throws   IOException {
        int i = 0;
                 for (int j = paramIn  t;      j > 0; j--) {
           Range >>>= 1;
                  int k      = Co  de         - Rang     e >>> 31;
      Code -= (Range  & k - 1);
           i = i << 1 | 1 - k;
 
        i  f (Range >= 1677   72       16)        
        c      ontinue;
      Code =   (Code  <  <    8 | Read   byte()   );
              Range <<= 8;
    }

                          return   i;
  }

  int BitD ecode(int[] p                    aramArra   yOfInt, int param Int) throws IOExc ept            ion {  
    int     i = (Ran  ge        >>>         11    ) * pa        r   amArr    ayO     fInt[pa  ram                Int];
    if  ((Code &  0  xFFFFFFFF)    < (i & 0x  F    FFF  FFFF))
    {
      Rang e =                i;
      paramArrayOfIn    t[par     amIn  t] +=   (     2048 - pa   ramArrayOfInt[pa    ramInt] >>> 5);

      if ((R     ange & 0           xFF000000) ==      0) {
        Code = (Code           << 8 |    Read    byte())   ;
        Range   <<= 8;
      }
          retu      rn 0;
    }
         Range -= i   ; 
           Co   de -= i;
           par      amArrayOfIn       t[p  aramInt] -= (paramArrayOfInt[param  Int] >>> 5);  

           if ((Ra     nge & 0xFF000000)  =    =    0)  {
      Code = (C       ode <<   8 | Rea      dbyte());
      R   ange <<= 8;   
    }
    retu  rn    1;
  }

  int BitTr     e        eDecod   e(   int[] paramArray O fInt, int paramInt     1, int     param   Int2) throws   IOException
  {
        int i =    1;
    for        (int    j     = paramInt2; j  >    0;  j--) {
                i = i + i +                  BitDecode(par amAr   rayO  fInt, paramInt1 + i);
    }   
        ret      urn  i  -   (   1     <<   pa  ramIn          t2     );
        }

  int   ReverseBi   tT   reeDecode     (int[] p   a   ramArray    OfInt, int paramInt1, in    t p    aramInt2) t    hr    ows IOException         {
    int i = 1     ;  
    int j   = 0;  

        for (int k     = 0; k < paramInt2; k++) {
      int m = BitD         ecode(paramArrayOfIn  t, par  amInt1 + i)    ;
               i    = i + i + m;   
      j |= m << k;
      }
        return j;
  }             

    byte L     zmaLiter   a     lDecode(int[] p aram Ar   ray         OfInt  , int paramInt) throws IO          Exce     ption {
               int i = 1    ;
    do
               i = i + i | BitDe      code(paramArrayOfInt, p  aramInt + i);
    while (i < 256    );

    return (byte)i  ;
  }

     byte LzmaLit   eral   Decode    Match       (i nt[] paramArrayOfI  nt   , i nt paramInt, byte paramByte) throws IOE   xc  eption        {
    int i = 1;
     do {
         int j = p   aramBy      te >> 7 & 0x1;
       paramByte = (byte)(  paramByte << 1       );
      int    k = BitDecode(para         mArrayOfInt, paramInt + (1 + j   << 8) + i);   
                 i = i << 1 | k;

           if (j != k) {
        while (i < 256) {
          i = i + i | BitDecode(paramArrayOfInt, par  a  mInt + i);
        }
      }
    }
    while (i < 256);

    return (by  te)i;
  }

  int LzmaLenD  ecode(int[] paramArrayOfInt , int paramInt1, int par amInt2)
    throws IOException      
  {
    if (BitDecode(paramArrayOfInt, paramInt1 + 0) == 0) {
      return BitTreeDecode(paramArrayOfInt, paramInt1     + 2 + (paramInt2 << 3), 3);
    }

    if (BitDecode(paramAr    rayOfInt, paramInt1 + 1) == 0) {
      return 8 + BitTreeDecode(paramArrayOfInt, paramInt1 + 130 + (paramInt2 << 3), 3);
    }

    return 16 + BitTreeDecode(paramArrayOfInt, paramInt1 + 258, 8);
  }
}