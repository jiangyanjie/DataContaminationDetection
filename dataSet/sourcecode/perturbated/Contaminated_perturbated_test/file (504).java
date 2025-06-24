/* DBInterpreterTokenManager.java   */
/*      Generated    By:JavaCC: Do not edit   this  line. DBInterpreterToke   nManager.java */
package edu.purdue.cs448.DBMS;
impo     rt edu.purdue.cs448.DBMS.Structur   e.*;
import java.util.*;
import java.io.InputStream;
import java.io.IOException;

/**  Token Manager. */
@SuppressWarnings("unused   ")public class DBInterpreterTokenManager implements DBInterpreterConstants    {

  /** De    bug output. */
  public  java.io.PrintS   trea   m debugStrea              m = System.out;
  /**      Set debug output  . */
  public  void     setDebugStream(java.io.PrintSt     ream ds) { debugStream = ds; }
private final in    t jjStopStri    n      gLiter  alDfa  _0(int p     os, lo    ng active0){
   switch         (p os    )
             {
            c      ase 0:
                           if ((ac   t         i    ve0 & 0x30 00007ffff       ff80L  ) !     = 0   L              )    
                   {
                                jjmatc    hedK                  i         n  d = 50  ;              
                               ret      urn 4;    
                                }
                        return     -     1;
          case    1:
                  if ((act i              v   e0                    & 0x1         00     000   L) != 0L)
                      return 4;
                if ((a  c     tive0 &   0x   30 00     007ffeff f8   0L  ) != 0L)
                           { 
                             j  jmatchedKind =    5  0;
                 jjmatc         hedPos     = 1          ;
                                 r  et   ur     n 4       ;
                     }
         retur  n -1       ;
                   case            2:     
               if ((   active 0 & 0     x      2   08c            8000L) != 0L)
                         r       e    tu        rn 4;
                                    if ((        ac  tive0          & 0x300 0007df637f8      0L)    != 0L)
                                              {
                  if       (jjmatch  edPo    s != 2)    
                       {    
                     jjmat  chedKind = 50;
                             jjm        atc     hed                           Pos =         2    ;
                    }
                                retur   n 4;
            } 
                                r  e   turn    -1;
      case 3:
                  if ((act  ive0 & 0x                 3             00  0      0029400b0 0   0L)    != 0L)
                  retur       n 4     ;
                               if ((active0              & 0x54b  634f80   L)  !=            0L)
                        {
                        if (j   jmatched    Pos   !=      3   )
                     {
                              j     jm              atche dKind            =           5     0;
                  j  jmatc        hedPo     s =    3;
            }  
                            return             4                     ;
               }
                           retu    rn -1;
                       ca               se            4:
            if       ((act ive0 & 0x44b420   f8   0L)     != 0L) 
                   {
                                                  if    (j       jmatched   Po    s !=   4)
                      {
                                                 jjmatchedKin  d = 50;
                            j   j  ma   tchedPo   s =    4;
              }
                return 4;
                 }
         if          ((active     0    & 0x1   00214000L)   !=    0L   )
                        ret       urn 4;
                                       retu   rn    -1;
                    cas     e      5:
         if  ((act       i     v  e0   & 0x4      4b400000L) !=     0L)
           {       
                        jjm     atch    e  dKind =     50;
                                  jjmat  chedPos           =   5 ;
                retu                rn 4;          
           }
                if ((acti ve0   &     0x10  002  0f80L) != 0L)
                    retur n 4;
               retur n -1;    
         c   ase   6:
               if    ((active0  & 0x      40900000 0L) != 0           L)
         {
                     jjmatchedKin    d = 50;
                       jj    matche    dPos = 6;
                                              r      eturn 4 ;
             }     
                       if ((ac          tive0     &      0x42400000L ) !           = 0L)
                     re   turn 4  ;
           return - 1;
      case 7:
                    if          ((act           ive     0 & 0  x8000          000L) != 0L)
                                               return 4;
              if      ((a   ctive0 & 0         x   401000000L  ) !  =   0L)          
         {  
                             j  jmat    chedKind =              50;
                 jjmatc hedPos = 7;
                  return 4    ; 
            }
                                 r       eturn -1;
                              case 8  :
              if ((act  iv    e0 & 0 x4000  00      000L) != 0L)
            return 4;
              if ((a cti    ve     0 & 0x1000000L) != 0L)
                        {
                           jjmatc   hedKin     d    = 50;
                jjma    tche    d   Pos =  8;
                         return 4;
              }
         return -1;
      default :
                 return -     1;
   }
}
private   final int jjSta  rtNf        a_0(int p  os, long a  c   tive0){
   return            jjMoveNfa_0(jjSt      opSt    ringLiteralDfa _ 0(pos, active0), pos + 1)  ;
}
private int     jj   S topAtPos(int pos, int kind)
{
    j     j   m atchedKind =  k       ind;
         jj   matchedPos   =         pos;
   retur      n pos +      1;
}
private int jjMoveStrin    gL      iter   alDfa0 _0(){   
             switch(curCh     ar)   
   {  
          case 33:
         return jjM            ove      StringLiter   alD      fa1_0(0x200           000           00000L);
          case 3  7   :     
             return jj   StopAtPos(0,          48)  ; 
      case 40:
                return j       j S       topAtP    os(0,   35                 );
             case 41:
         r    etur n jjStopAtPos(0, 36);
      case 42:
         return jjSto   pAtPos(0, 46);
        case 43:
                          ret   urn jj     StopAtPos(0, 44);
          case 44:
                retu   rn j   jStopAtPos(0, 49);
                   cas e 45:
         return jjStopAtPos(0, 45);
      case     47:
                          return jjSt  op      AtPos(0, 47);
      ca  se   59:
                  r   etu   rn         jjStopAtPos(0, 37);
      cas   e    60:  
             jj   mat          ch   e dKind = 39  ;
               retur  n j   jMoveSt  ringL    iteralDf     a1_0(0x1   00000   00000   L) ;    
         case 61       :
             retu rn jjStop At    Pos(0            , 38);
      c    ase    62:
             j    jmatche dKi        nd = 42;
         r     eturn jjMoveStr      i   ngLi    teralDfa1_0(0x80000000000L  )       ;
      case 65: 
         ca   se 97:
          return jjM    oveSt     ringLiteralDfa1_0(0x80000      L)   ;
      case 67:
           case 99:
         ret   ur          n jjM        ov       eStringLite   ralDfa1_0(0x10200400L);
             case  68 :
         case 100:    
           return j     jMoveStrin    gLiteralDfa1_0(0x48001800L);
      case 70:
         ca  se 102:
                         retur    n          jjMove  StringLiteralD  fa1_0       (0x200    2000L);
          case 72:
               case 1    04:
          re    t  u  rn jjMove      S   tring Litera  lDfa1_0(0x4000000L);
      case      73:
      case 10   5:
               return jjMov    e          StringLiter al  Df    a  1_0(0x200081 00L);
            cas  e 75:
        case    107      :
             return jj     MoveStringLiteralDfa1_0(0x8      00   000L);
      case 79:
      case 111:
         return jj  Move   StringLiteralDfa1_        0(0x10         00        00L);
      c         ase   80:
      case  1   12     :
         return jjMoveStrin           gLiter alDfa1_0(0x400000L);
            case 81:
               cas e       113:
             return jj   MoveStri     ngL     it    eralDfa 1_0(0x800  00000L);       
      case 82:
      ca  se    114:
                return jjMoveStri  n  gLite   ralD    f   a1_0(0x   1000000  L);
       case 83:
        cas   e 115     :
            retur  n jjMoveStringLite  ra     lDfa1_0(0x400040080L);
       cas  e 84:   
       case 116:
              r    eturn jjM  oveStrin     gLitera  lDf    a1_0(0x100010000L);
          case 85:
      case      117:
         r      eturn jjMoveString         LiteralDfa1_0(0x3000002  0000020      0L);
            ca   se 86:
      c             as   e     118:
             r     et   urn jjM  oveStri             ngLiteralDfa1_0(0   x20000L); 
        c as   e 87:
      ca         se 11   9:
                        r  e            turn jj Mo  veStringLiteralDfa1_0(0x400    0L);
                 def      a ult  :
                 ret     ur    n jjMoveNfa_0(0, 0);
   }
}
private int jjMoveStri ngLi       te  r   a  lDfa1_0             (long a     ctive    0){
   try {    curChar =       input_stream.  readChar(); }
        c            atch(java.io .IOE  xception e  )  {
            jjStopStrin  g    LiteralD  f        a_0(0   , activ        e0)   ;
      return 1  ;
   }
   switch(cur            Char)
   {
              case  61:
                  if ((active0 &    0 x1000000000    0L) != 0L)
                              return jj     StopAtP o   s(1       , 40);
                else if ((active0          &     0x20000000000L)     != 0 L)
               return jjSto  pAtPos(1, 41)     ;
           else if ((active0 & 0x80000000000L      ) !=    0L)
            return j     jStopAtPos (1, 43);
            break;   
      case 65:
        cas   e 97:
         r eturn jjMoveStrin  gLiteralDfa2_   0(       active0, 0     x100030000L      );
      c  as         e   69:
           case 101:  
                    r         eturn jjMoveStrin      gLit   eralDfa2_0(    active0, 0     x4d8 408 80L )   ;
                   ca se 7    2:
       cas      e 104:
            ret    urn jjM    oveStr   ingLiteralDfa2_0(   a        ct    ive0   , 0x1020400     0L);
      ca        se      78:
      case 110:   
                   return j  jMove  StringLiteralDfa2_0(active0,      0x2008    8100L    );
      case 79:  
      case 111:             
          r  et   urn j jMoveStringLit    eralDfa2_0(active0  ,    0x200 0000L);
           c  ase 8     0:
      case 11    2:   
              retu   rn jjMo  veS       tringLiter alDfa2_      0(   active0, 0  x200L);
             case 82:
           c    ase     114:  
             if ((a     cti     ve0 &            0x10   0000L)         !=    0L)
                    ret         urn jjStartNfaWithStates_0(1, 20, 4    );
          return jjMoveStringLiteralDfa2_0(active0, 0x  403400L);
             ca     se 83:
        cas e 115:
            return jjMo veStri   ngLiter     alDf a2_0    (active0, 0x300000200      000000L);       
       case            85:
      cas       e  11 7   :
                                       r    eturn jjMoveStringLiteralDfa2_0(active0, 0x    480000000L); 
           defa ult :
         break;
   }
   return jjStartNfa_0(0, acti  ve   0);
}    
priva     te int     jjMoveStrin   gLiteral   Dfa 2_0 (              lo         ng ol        d0, l ong active0){
         if (((a ctive0 &= old0))    =       = 0L   )
      return   jjSta    rtNfa_0(0, old0)  ;
       try { curChar = input_stre  am.readC     har(); }
   catch(java.io.I   OE     xception e) {
      jjStopStrin  gLiteralD  fa_0(1    , acti    ve0);
      ret urn 2;
   }
   swi    tch(curChar )
   {
      case 65:
                      case    9   7          :
                    return jjMoveS     tri       ngLiteralDfa3_0(act   ive0, 0x10000    000L);
      case 66  :
      case    98:
         retur    n jjMov   eStringLiteralDf     a   3_0(     ac  tive0, 0x500010000L);
              case 67:
          case 99:
                   return jjMoveStringL          iteralDfa3_0(act    ive                               0, 0x 40    00000  0L);
            case 68      :     
                       case 100:
                    if ((act   ive0 & 0x         8   0000     L) != 0L)        
             return jj  StartNf  aWi    thState    s_0(2, 19, 4);
               return jjMoveStr   ingLiteralDfa3_0         (activ    e0, 0x20  0L     );
        cas  e 69:
      case 101:
               retu       rn jjMoveSt  rin     gLiteralDf  a3_0(a    ctive0,               0x300000200204400L);
                  case      70:
                cas   e  102: 
              retur     n jj MoveS      tringLit         era  lD  f    a3_0(active0         , 0x1000  0     00L);
       case 73:
      c   ase 105:       
                  return jjMoveStringLi ter  alDfa3_     0(active0,    0x8   04000       00       L)     ;
                  case           76:
      cas      e      108:
           return jj MoveStringLite   ral     Dfa3_0(acti ve     0,    0x       4020880L)   ;       
                 case 79:
         case      111:
            return jjMoveStrin gLi      teralDfa3_0(active0,        0x3000L);
         case   82:
             case 11       4:
                  re  turn jjM   oveStringLit     eralD    fa3_0(ac      tive0, 0x2000000L);
         case 83:
                 case 11   5:
                        retu r                    n jjMoveStr     ingLite   ralDf a3_0(a       ctive0, 0x80001 00   L);
                            case        84:
        c  ase 116:
                 if ((active0        & 0x40000L   ) !=     0L)
                  r   eturn          jjStartN    faWithStates_0(2 , 18, 4);     
         els  e  if ((acti   ve0 & 0x       2 0000000L) != 0    L)
              {
            jjmatch   edK     in d = 29;
                  j   jmatchedPos = 2;
             }
             return jjMoveStringLiteralDfa3_0(      a   ctive0, 0x8000L);
          case 89:
      case 121:
                  i    f (     (active0 & 0x800000L    ) !  =        0L)
              r    et    urn jjStar t      NfaW  ithStates   _0(2, 2  3, 4);
               br e     a   k;
           d  e   fault     :
         brea  k;
     }
   r eturn      jj    StartNfa_0(1,   active 0);
}   
private      int jjMoveStringLiteral   Dfa3_0(long old0, long          activ    e0              ){
   if    ((          (active0    &= old0)) == 0  L)
         r eturn j   jStartN   fa_0(1   , old0);
     t   r  y { cur   C           har = input_stream.     readChar()  ; }
   c   atch(j          ava.        io.IOE   xception       e) {
            jjStopSt   ri  ngLiteralDfa _0(2, active        0);
        return 3;
   }
   switch(curChar          )
       {
            cas           e 65:
      case 97:
                       return jjMoveString          Literal           D fa4_0(active0, 0x600L);
       case 67:
      cas         e 9     9   :
                                     return jjMoveS  tring          Lite   r      alD   fa4_0(active0, 0x8200  000L);
            case 69:
          case 101:
                         return jjMo   ve   StringLiteralD  fa 4_0(active0 , 0x  3000980L);
           case  73:
           case 10              5:   
             return jjMov    eString L     i   teralDf   a4_0(act iv     e0, 0x40000000L);
      c   ase 76:
           case 1  08:
                   return j       j   MoveString   LiteralDfa    4_0(activ       e0, 0x1    0   0  0100    00L);
             case   7  7:
      case 10   9:
         if ((a        ct i   v   e0 & 0x2000L) !     =    0L)
                 retu        rn jjStart    NfaWit    hSt          ate     s_0(3 , 13, 4);
         return jj Mo  veStr         ingL      itera  lDfa4_0(    act    ive0,  0x40000   0L);      
            case 79:
      case 111:
         if         ((active         0 &                   0 x8    000L) != 0L)
                        re   tu            rn jjStartNf       aWithS    tates   _  0(    3 ,     15, 4 );
         break;          
      case 8   0:
        case 11 2:
         if           ((active0      &      0x1000L)      != 0L)
                           return jj    St   artNfaWit      hS  t     ates_0( 3, 12, 4);    
         else i  f ((act ive0 & 0  x4000000L) != 0   L)
                          retu   rn jjStartNfaWit   hSt   ates_0(3,  26, 4);
                  break;
      case 82:        
               case 1         1 4:
             if ((active0 &       0x100     0      0000L) != 0L)
                                        return jjSt         artNf          aWi        t            hStates_0(3, 28, 4);      
         el se i        f ((active0 & 0x20 0000000L)      != 0L)
                {
               jjmatche  dKi   nd  = 33  ;
                                  jjmatchedPos = 3;
              }    
         r   eturn jjMoveStringLite ralDfa4_0(acti  ve0, 0x3000        00000004000L);
           case 8    3:
      case 1          15:
                  r   eturn jjM  ove    Str      ingLiteralDfa4_0(act    ive0, 0     x400000000L     );
      case     84:     
         case 116:
         if     ((active    0 & 0x80      000   000L)        != 0L)
                   ret  u     rn jjStart     NfaWi            thS tates_0(3  , 31, 4);
                    break;
          case 85:
      case 117:
             return j    jMo        veStringL           iteralDfa4_   0(ac    tive0, 0     x20000L           );
       d        efault :    
         break;
   }
   r    et    u  rn jjStartNfa_0( 2 , a    c     tiv  e0);   
}
private int           jjMoveStringLi te    ralDfa4_0(long old0,    long a                ctive  0){
   if (((active0 &= old0       ))    == 0  L)
        re    turn jjStartNfa_0(2, old0);
   t                  ry { curChar = in             put_   stre         a           m.readChar();     }      
     catch(java      .  io.IOException e) {
      jjStopStringLit eralDfa_0( 3    , active0);
      ret           urn 4;
      }
       switch(cu   rCh    ar)
   {
                          case 45:
         retu   rn    jj   Mo    veStr                ingLiteralDfa5_0(active0, 0x   3000    0000000000   0L);  
                   case 65:
      case     97:
          return    j    jMove  StringLiteralDfa   5_0(   ac    tive0, 0x40000  0L); 
      case 67   :
            case     99:
                      ret        ur   n jjMoveSt       ringLit    era    lDfa5_0(acti  v        e0, 0x4000   0        008    0    L);
                 case 69:
      ca    se 101:
                     i  f ((act      ive0 & 0x4000L) != 0       L)
                         return jjStar   tNfaWithStates_0(4, 14, 4);
             else i   f ((active0    &     0x1      0000 L)          != 0L    )
                 {
                   jjmatchedK ind      = 16;
                        jjmatch    edPos =        4    ;    
              }
            return jjMoveStringL i        tera   lDfa5_0(active     0, 0    x100020 000L);
          case 73:         
      case 105:
               return jjM   ov  eStringLitera   lD    fa5_0(active    0, 0x20   00      000L);
        cas       e 75:   
                   ca     se 107:   
           if ((a ctive0    & 0x20    0  00   0L) != 0L)
            re   turn jjSt  art          NfaWit hStates_0(4, 2  1, 4  );
         break;
          case              77    :
                  c     ase 109 :
                     ret   urn jjMoveStringLiteralDfa5_0(active0, 0x40000000L);
      case 82    :
      case 114   :
               return           jj   Mo      veStrin gL    iteralDfa5_0(active  0, 0x90001         00L);
         case 84:     
              case 116   :
                 ret  urn jjMoveString       Li    ter     alD  fa  5_     0(activ     e0   , 0xe00L  );
        defaul t :
                break;   
   }
      r                    eturn jjStartNfa_0(3, active0);
}
pr   ivate int  jj    Mov            eStringLiteralD fa5_0(l  o   ng old0, long act      i      ve  0){
      if (((active0 &=                   old0)) ==   0L)
         return jjStartNfa_0          (3, old0);
   try { curCh   ar =   input_st  ream.readChar();   }
                  catch(ja   v  a.io.IOEx    cepti   on     e)    {
                               jjS           topSt     ringLiteralDfa_0(4, active 0);
      r    et       urn 5;
                   }
         switc  h(curCha     r)
       {
          case 65:
                case   97:
             if ((    active 0         & 0x100000000000000   L)     != 0L)
                     retur    n   jjStopAtPos( 5, 56);
                retu       rn jjMoveStringLi    te  ral  D        fa6_0(acti     ve0,       0x40000000L);
                  ca               se     6   6:
        cas    e 9     8:
         if ((active       0 & 0x                20   00          000    00000000L) != 0L)
                           retur      n                 jjSto    p At      Pos    (5, 57); 
                br e      ak;
             case   69:
               case 10           1:
               i f ((active  0 & 0x200L   ) != 0L    )
               ret    ur   n jjS       tartNfaWi     thS tat   es_0(5,  9,    4);  
             els     e  if                ((ac   tive0 & 0   x400L) != 0L)
                       r         eturn j    jStart   NfaWith        States_0(5, 10, 4);
          else if ((ac  t        i       v    e0 & 0x800L ) !=       0L)
                 re  turn jjStartNfaW   ithStates_0(5, 1   1, 4);
         re turn jjMoveStri     n               gL  iteralDfa6_0(       active0, 0x100    0 000L)    ;
               case 71:
      case 103           :
         ret     urn jjMoveStri   ngLiteral    Dfa6_0(active0     ,     0x  20          00 0    00L);
             cas    e           72:
               case      104:
                    retur    n       jjMoveStringLite ralDf  a6_0(      active0, 0x4        0    00               0000  0L);
      case 7  3:
                 case 105:
            r         eturn j   jMoveStr        ingLiteralDfa6_0(active             0,  0x              800000          0L     );  
            cas e 82:
            c   ase 114:          
             ret   urn   jjMo    veStringL         i t      eralDfa6_0(active0, 0x400000L);
      cas  e          83:
                cas      e      115   :
          if ((   active0 &                0x  20000L) !   = 0 L)    
                       ret     ur  n jjStartN   faWithS         tates_0      (5,    17, 4);
                               e   lse if ((active0   &      0x100   0  00 0  00L)      !=  0L)
                                return jjStartNfaW       ithStates_0(5,  32      , 4);
                  br       eak;
      case 84:
      ca s     e 116:
                             i f           ((a  ctive0 & 0x80L)  !=       0 L)
                                 ret  u   r    n jjSt ar      t    NfaW      ith   States_0(    5,   7,             4     );
                                el                                 se   if          ((activ e0 &    0                     x100L) != 0L)    
            retu    rn                      jjStartN    faWithStates_0(5, 8, 4);  
         br       eak;    
                      d    efaul   t :         
                bre  ak;
    }
   r                      eturn jjStart Nfa_0(4,   active0);
}
privat   e int jjMov   eString    Li teralDfa6_0(long  old0,              long active0){
   if (((acti          ve0 &      =    old0)) == 0L)   
             r    eturn j     jStartNfa_  0(4, old0)   ;
         try { cu    rChar = input_s     tream.read        Char      (); }
   c     atch(j   ava.   io   . IOException e) {
           jjStopStringLiter  alDfa  _0(5,    ac        tive0);
                     ret    urn 6  ; 
     }
   sw   itch(cu     rChar)
      {   
                        case 66:   
      cas    e 98:
                     r eturn jjMoveStri ng  Li             tera lDfa7_0(ac t                ive         0   ,            0x800000   0   L);
                             case 69:
          ca     se 101:
                   r  etur                n jjM     oveStringLi        ter    al Dfa7   _0(activ   e0, 0            x40   0000000L);
      ca    se       76:
         ca    se 108:
                  i    f   ((a     cti ve  0 &  0x 4   0000      00   0L) != 0      L)
                    ret             urn jjStartNfaWit hState   s  _0         (6, 30   , 4);
            bre             ak;
                 c   a  se   78   :
         ca  se 110:
                   if ((active0 & 0 x200     0000L) != 0L)
                ret                 urn jjStartNfaWit         hState       s_0(6,       25,    4);
              re      tu  rn jjMoveStringL       iter    alDfa    7_ 0(a   ctive0, 0x1       0    00000L);
              case 8    9:
       ca    se    121            :
               if ((acti   ve     0     & 0         x40  0      000L)         !=    0L)
                       retur        n jj StartN  faWithSta te   s    _0 (6      , 22  ,   4      );
                br              eak;
            d     efaul    t :       
                  b   r  eak          ;
            }
   ret  ur    n jjSt  ar                 tNfa _0(5 , activ    e0);
}
private i   nt jj     MoveStringL  iteralDfa7_0(lon   g      ol       d 0  ,   lo  ng ac    tive0){    
        i        f (   ((ac      tive0 &= old0       )) ==  0L)
      r     etu    rn jj     S                  tartN   fa_0(5    , o ld0);
               try { curCha  r  =   i   n    put_                  stream.                 readChar(); }
   cat       ch(   ja      v a.io.I   OExce             ption e       ) {
               jjStop      StringLi       te  ralDf   a_0      (6, active0);
             ret     urn 7;
   }
       switc    h(cu      rC har)
              {  
         case 67:
                  ca         se 99 :
           retu   rn jj                               M  ove     S         tringL      iteralDf           a8 _0(  acti     ve0  , 0x10  0000           0           L    )   ;
                 c        ase 69  :
      c     as            e 101:         
                         if      ((ac  tive0 & 0x800   0  000L)               != 0L)
              re turn jjSt    art  NfaWithS ta   tes_0   (7, 27,  4);
                     break;
                    case 77:
          c ase 109       :
                     return jjMo    veS      t ringLite    ra  lD         fa8 _0    (a  ctiv    e0, 0          x   400000000L);
      defa   ult :  
            b       rea k;                   
         }
   retur           n jjStartN   f        a_0(  6, acti    ve0);
      }
pr   ivat e   int    jjMov  eS   t      ringLi   teralDfa        8_   0(    lon  g old0    , long           a       ctiv                              e     0)      {
      if (((a    ctive0 &= o      ld0)) =    =     0L)  
      r     e     turn jjSt   artNfa _ 0(            6,           old0);
      tr y    {    curCha                  r = i   npu t_stre   am.r   eadChar(); }
       ca           t     ch(j   ava        .  io.IOExc ep t  i   on e)           {
                j   jS        topSt   ringLiteralDf       a     _0(            7, active0);
      retu        rn  8  ;
   } 
   switch(c    urChar)
     {       
                       cas  e 6    5:
                         ca  se 97          :
             i       f ((active       0       & 0      x4000        0        0 0      00L) !=             0L)   
                                r  eturn jjS   tartN   fa                  W i      thStates_0(8,    3   4, 4)          ;
                b  reak;                  
          c   ase    69:
             case    101:
                  return  j   jMo veSt    ringLiteralDf    a9_0(a               ct   ive0,     0x   1    000000L)         ;
        default :
            br eak;
   }
            re     turn jjStartNfa_0             (7,              ac  tive0)                    ;
}
private              int jj        Move   St   r      ingL   itera         lDfa9_0(lon    g           old0     , long ac         tive     0){
      if (((active0 &  =        old           0   ))     == 0L)      
      return jjSt      a      rtNfa   _0         (7,  old0);                    
   tr        y { curCh    ar = input_stream.readChar(); }    
   catc   h(jav    a.io.IOE        xception e) {    
      jjSt             opString  Literal    Dfa   _0   (8   , a      cti  ve0);    
            re  turn                         9       ;
        }
   s  witch(curChar  )
      {                      
        case    83  :
           case               115:
          if ((a     c ti        ve0 & 0  x1   00     0000L            ) != 0L                  )
             retur n jjStar    tNfaW    it   hStates_0(9,             24, 4)  ;  
                           bre       ak;
      de        fault :
                     break  ;     
   }
   retu  rn jjS     t        ar  tNfa          _0      (8, act i          ve0)           ;
}
p      r         iv  ate int   j jS                     t          a   rtN      faWi   thStates_0(int pos, int kin  d,       int state)
                  {
                  jjm             at                 c                                    he       dKind = k     ind;
              jjm   a   tchedPo              s    =   pos;
   try { curC    h       a      r = input_    stream.readCh ar();          }
   c     atch   (java.io.I  OE       xcep    tio       n e) { ret    ur      n pos +       1;       }
        ret   urn      jjMoveN     fa           _0(s    t   at  e, pos + 1);
 }
s  t      ati   c    f   inal long[]    jj bit     Ve        c0         = {
   0xffffffffffff  fffeL, 0xff   f fff     ff          ffffff   ff   L    , 0xffffff       fffffffff    fL, 0x     ffffff   ffffffffffL
  };
s  t at    ic            fi   nal long[] jj bitVec2 = {   
          0x   0L, 0x    0L             , 0xffffff         ffff        fff        fffL     ,    0            x    ffff   ff          ffff    fffff    fL
};
privat e          int jjM            oveNfa      _0    (int     s ta    rtState,    int  cur      Pos)
{
   int           s   t  ar  ts        At = 0   ;
                        j          j   ne  wS    tat  e Cn     t = 32;
              int i = 1;
     jjstateSet[         0]     =    s    t     art          S      tate;        
   int kind    = 0x7fffffff;
   for (;   ;)
      {
      i                   f           (++jjround == 0x7f     ffffff)
                R eI   ni    tRou                   n     d    s( );
            if   (    cu    rChar < 64)
      {
            lon    g l = 1L << cu rCh            a r;
                do                
                                         {
                       swi           tch(jjstate   Se       t     [--i] )
                               {   
                                 cas         e    0:   
                           if              ((  0x         3ff000 00    000       0000L &  l)   !   =           0L)
                             {
                                          if           (ki    nd > 51)  
                                             kind                   =              51    ;
                                             { j           jChec          kNAdd Sta  tes(      0, 6);  }
                                           }
                                  e    ls    e if       (curChar == 3            9)
                                               {    jjCheckNAddStates(   7,       9 );   }    
                                  else if     (curChar     == 34)
                                      { jjC        hec   k   N AddTwo    Sta     tes(11, 1   2)       ; }
                            else if       (curChar        ==    46)
                                                                   { jjCheck        NAd  d(           6);      }       
                                 break;
                                             c         ase     1:    
                          { jjAddSta     tes(10,     11); }
                                      brea  k;
                          case 4:
                             if (           (0x3ff0000    0000000   0L &       l) ==    0   L)
                                                         break;
                                                   if (kin       d > 5  0)
                                                   kin     d =           50;
                                             jjstate      S   e               t[jjn ewS   t   a    teCnt++] = 4;
                             break;
                           ca s          e 5:   
                             if    (curCh      a        r == 46)
                                         { jjCheck            NAdd(6)  ; }
                                   br            ea  k;
                  case 6:
                            if (     (0x3ff     0000  000  000    00L & l) ==           0L)
                                                            break;   
                                                      i             f (ki           nd > 52)
                                                                    kind = 52;
                     { j     jCheckNA    d         dTwoSt   a  tes(  6,   7);   }
                                 br   ea      k;
                    case 8:
                       if (( 0      x2800000000       00            L & l ) != 0L)
                               { jj     Che  ckNAdd( 9 );    }
                                          break  ;
                                        case 9:
                             if     ( (0x3ff00            00        000  00000L    & l)       == 0L)
                                                             break;
                                        i   f       (ki      nd  > 52)
                          k      i       nd =     5      2   ;
                                         { jjCheckNA      d d      (9      ); }
                               break;
                        c      as  e 10:
                             if    (cur         C har ==      3    4)
                                    {       jjCh          eckNAddTw   oStates(1      1, 12); }
                           break;
                       c  ase 11:
                                       i      f ((0xf     ff  ffffbfff   fffffL & l       )  != 0L)
                               { jjCheckN    Ad   dTw oSt            ates(             11, 12); }
                                       bre    ak;
               case 12:
                        if        (cu     rCh          ar == 34 && kind           > 5   4)
                                             kin  d                         = 54;
                                                       break;
                             case 1       3:
                                               if       (                       curChar == 39)
                                   { j    jCheckNAddStat   es          (7, 9); }
                      break;
                            case 14  :
                  if ((0xffffff7fffff     fff       fL & l) != 0L)
                                          {      jjCh   eckNAd    dStates(7,  9); }
                                  break;
                         c ase 15:
                            if     (    curChar    =      =    39                    ) 
                                                     { jjCheckNAd dStates(    12 , 14); }
                                break;
                               case 16     :
                                  if (cur Char   =    = 39  )
                                 jjstateSe  t[jjnewSta  teCnt++     ] = 15;
                                        break;
                              case 17:
                                                     if (             (0xffffff7fffffffffL & l) != 0L)
                                                  { jjCheckN   Add    Stat     es(12, 14)     ;   }
                   break;        
                             c  as        e 1  8:
                  if (curCha  r == 39 &&   kind > 55)
                                    kin   d =        55;
                       br  eak;
                  case 19          :
                         if ((0   x3ff00000  00000   00             L & l) ==      0L)
                                       b         reak;
                     i   f       (k    i   n   d             >      51)
                              ki   nd =     51       ;
                                                 { jj  CheckNAd      d  States(0, 6); } 
                               br   eak;
                  case 20:
                                            if (      (0       x         3         ff00000000000        0          L & l) == 0L)
                               bre a        k;
                       if (       kind > 51)
                                              k    ind = 51;
                         { jjCheckNAdd(20); }
                  br  eak;
                          cas      e 21:
                               if ((0x3ff00000000 00       00L     & l)   != 0L)
                           {   jjCheckN   AddTwoStates(21 , 5); } 
                         br       e    ak;  
                         case 22:
                                i      f ((0x3ff00000000000      0L & l) !=     0L)
                                     {      j    jCheckNAddTwoStates(2  2, 23); }  
                           break;
                  ca   se 23:
                       if (   curChar != 46  )
                          b   reak;
                    if (kind > 52)
                           k ind   = 52;
                   {  jj        Che        ckNAddTwoSta    tes(24, 25     ); }
                            break; 
                   case    24:
                          if ((0x3ff0000000000 0 0L & l) ==    0L)
                       break;
                              if (kind         >          52)
                               kind = 52;
                      { jjCheck          N        AddTwoStat   es(2   4, 25); }
                                      b  r      eak;    
                    case 26:
                      if ((0x2800000000          00L & l) != 0L)
                               { jjCheckNAdd(27)             ; }
                  break;
                     cas  e 27:
                                              if ((0x3ff     000000000000  L     & l)     ==   0L)
                          break;
                               if (kind > 52)
                            kind =   52;
                        { jj    CheckN   Add(2     7) ; }
                     break;
               case  28:
                     if      ((0x3ff000000000   000L & l)           == 0L     )
                            break;
                         if (kind > 52)
                         kind = 52;
                               { jjCheckNAddTwo      Stat   es(28 , 29);    }
                  break;
               case 30:
                       if     ((0x280000000000L & l) != 0L)
                               { jjC   hec kNA    dd(31)  ; }
                                    bre  ak;
                              case 31:
                         if ((0x3ff000000 000000L &    l)   == 0L)
                     break;
                  if      (ki  nd > 52)
                     k    ind = 52;     
                  { jjCheckNAd d(31); }
                      break;
                   de  fau l    t : break;    
                }   
                 } wh ile(    i !=    start     sAt  );
      }
      else if (curChar < 12 8)
      {
           long l = 1L <<  (curChar &            077);
                  do
         {
            swi          tch(   jj       s    tateSet [--i])
             {
                        case 0:
                              if ((0x7fffffe87fffffeL & l)     !=   0L)
                      {
                                    if (kind   > 50)   
                                     kind = 5  0;
                            { jjChec   kNAdd(4); }
                       }
                   el  se if (curCh   a    r == 123)
                                { jjCheckNAddTwoSta    tes(1, 2); }
                         break;
                 case 1:
                     if ((    0xd7ffff     ffffffffffL & l) !=    0L)
                     { j   jCheckNAddTwoSta      tes(1, 2); }
                           break;
                   cas   e 2:
                           if (c   urChar =      = 125 && kind > 6)
                              kind = 6;
                         b reak;
                 case 3:
                     case 4:
                  if ((0x7fffffe87f    ffffeL & l) =      = 0  L)     
                     bre  ak;
                  if         (kind > 50)
                       kind = 50;
                  { j  jCheckNAd     d(4);           }
                       b       reak;
               case 7:
                  if     ((0     x2 000000020L & l) !=     0       L)
                            { jjAddStates (15, 16); }
                       break;
                    case 11:
                           { jjAddStates(17, 18); }
                            break         ;
                       case    14:
                  { jjCheck  NAddStates(7, 9);  }
                      break;
               c     ase 17:    
                  { j j  CheckNAddStates(12, 14); }
                                break;
                case 25:
                     if ((0x     20000000       20L & l) != 0L)
                     { jjAddSta    tes(19, 20); }
                  break;
               case 29:
                    if ((0x2000000020L & l) != 0L)
                                 { jjAdd            State   s(21, 22); }  
                     break;
                            default : break;
              }
                   } while(i != startsAt);
        }
          else
         {
         i  nt hiByte = (curChar >> 8);
          int i1 = hiByte >> 6;
         long l1 = 1L << (hiByte   & 077);
           int i2    = (curChar & 0xff) >> 6;
             long   l2 = 1L << (   curChar & 077);
             do
         {
            switch(jjstateSet[--i])
            {
                    case 1:
                  if     (jjCanMove_0(hiByte, i1, i2, l1, l2))
                     { jjAdd  States(10, 11); } 
                  break;   
                    case 11:
                  if (jjCanMove_0(hiByte,    i1, i   2, l1, l2)   )
                     { jjAddStates(17, 18); }
                  break;
                   case      14:
                    if (jjCanMove_0(hiByte, i1, i2,         l1, l2))
                     { j      jCheckNAddStates(7, 9); }
                  break;
                 case 17:
                  if (jjCanMove_0(hiByte, i1, i   2, l1, l2))
                           { jjCheckNAddStates(12, 14); }
                         b  reak;
                 default : if (i1 == 0 || l1 == 0 || i2 == 0 || l2 == 0) break; else break;
            }  
            } while(i != startsAt);
      }
      if     (kind != 0x7fffffff)
           {
         jjmatchedKind = kind;
               jjmatchedPos = curPos;
         kind  = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 32 -      (jjnewStateCnt = startsAt)))
         return curPos;
               t      ry { curChar = in  put_stre  am.readChar(); }
      catch(java.io.IOExcep    tion e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   20,    21, 5, 22, 23, 28, 29, 14, 16, 18, 1, 2, 16, 17, 18, 8, 
   9, 11, 12, 2    6, 27, 30, 31, 
};
private static final b oolean j  jCanMove_0(int hiByte, int i1, int i2, long l1, long l2)
{
   switch    (hiByte)
   {
      case 0:
           return ((jjb   itVec2[i2] &     l2) != 0L);
      default :
         if ((jjbitVec0[i1] & l1) != 0  L)
            return true;
         return false;
   }
}

/** Token literal values.  */
public static  fina l String[] jjstrLite ralImages = {
"", nul     l, null, nu      ll, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, nul     l, null, null, null,       null, null, 
null, null, null, null, null, null, n   ull, null, "\50",  "\51", "\73", "\75", "\74", 
"\74\75", "\41\75", "\76", "\76\75", "\53", "\55", " \52", "\57", "\   45", "\54", null,   
null, null, null, null, null, null, null, };
prot e   cted Token jjFillToken()
{
   final      Tok        en t;
   final String curTokenImage;
   final       int beginLine;
    final int endLine;
   final int beginColumn;
   fin  al int endColumn;
   String    im = jjstrLiteralImages[jjmat      chedKind];
   curTo    kenImage = (im == null) ? input_stream.GetImage() : im;
   beginLi   ne = input_stream.   getBeginLin    e();
   beginColumn = input_stream.  getBeginColumn();
      endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t   = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn =  endColumn;

   return     t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int   jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{    
  Token ma     tche       dToken;
  int cu  rPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
           }
   catch(java.io.IOException     e)
   {
      jjmatchedKind = 0;
       jjmatchedPos = -1;
      matchedTo      ken = jjFillToken();
      ret urn matchedToken;
   }

   try { input_stream.backup(0);
      while     (curChar <= 32 && (0x100003600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;    
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7ff     fffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.    backup(curPos     - jjmatchedPos      - 1);
      if ((jjtoToken[jjmatchedKind >> 6    ] & (1L << (jjmat     chedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      els e
      {
         continue EOFL oop;
      }
   }
   int error_line = input_stream. getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = cur    Pos <= 1 ? "" : inp    ut_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column+   +;
   }
   if (!EOFSeen) {
      input_stream  .backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   t    hrow new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int  end)
{
   do {
      jjstateSet[jjnewStateC nt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

    /** Constructor. */
    public DBInterpreterTo      kenManager(JavaCharStream stream){

      if (JavaCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  /** Constructor. */
  public DBInterpreterTokenManager (JavaCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  public void ReInit(JavaCharStream stream)
  {
    jjmatchedPos = jjnewStateCnt = 0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 32; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  public void ReInit(JavaCharStream stream, int lexState)
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new Toke   nMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0x3dfffffffffff81L, 
};
static final long[] jjtoSkip = {
   0x7eL, 
};
    protected JavaCharStream  input_stream;

    private final int[] jjrounds = new int[32];
    private final int[] jjstateSet = new int[2 * 32];

    
    protected char curChar;
}
