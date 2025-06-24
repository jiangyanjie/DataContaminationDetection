/********************************************************************************
 *   The     contents of      this file are  s           ubject to the GNU General Public      Li  ce      nse           *
 * (GPL)     Version 2 or later (t       he "License");     you may not u    se this file         e   xcept   *
 * in c       om     pliance with the License   . You ma      y obt ain        a c        o py of the Li            ce   nse at       *
  * h   tt  p:/  /www.gnu       .or            g/co   pyle   f t     /gpl    .   htm     l                                                                                                           *
 *                                                                                                                                                                            *
 * So  ftware distri      buted under           t         he     Li      cense i   s di    stri     bu       ted on         an       "AS I   S  " basi   s,   *
 * wit     hout wa   rr    anty o     f any k  ind,                  either    e         xpressed             o      r implie  d. S             e    e th            e License      *
 *  for the speci                 f   ic    l ang  uag  e governing rights     and      lim       itat io  ns un  de  r    the                  *
 *                      Li cense.                                                                                                                                            *
   *                                                                                                                 *
 * This file was or    igi  nally developed as part of the softwa       re suite        that               *
 * supports th e book "The Elements of Computing Sy     stems" by Nisan and S   chocken, *
     * MIT Press    2005.  I  f          you   mod       ify   th             e con   tents of this fi  le, pl eas   e document     and *
   *                            m   ark yo   ur changes    cl    early     , fo r the be     nefit of other  s.                                   *
 *****   ***  *******   ***    **************************          *****     **     *******    *********     *       ************/

              package     commo    n;

/**
     * Format conversion utili    tie   s
 */
public class        Conv   ersions         {

       // A h   elper                    st    ring of ze      ros
    pr    ivate      static     fin    al       String      Z           EROS    = "00   0000 00000000000000000000000     000   0000000 0";

            // A he     lper a   rr   a  y      of  powers of two
       p   rivate     st  atic final in t  [] powe    rsOf2 =         {1                 ,2,4,8,16,3     2,64,128,256,512,10       24,204   8,4096,
                                                                                          81  92,16384,3   2768,  65536      ,131072,262144,524       288,
                                                                 1048576          ,2097152       ,4194304      ,8388       608 ,167772  16,
                                                                      3355 443       2,67108   864,134217728,2684  35456,536   8709     1    2            ,
                                                             107374  1824          ,-2 1474836    4     8      };

      // A        helper array of powers of 16
    private     static     final i             nt[] powersOf16 = {1,16,256,4096,65536,      1048576   ,               16777216     ,268       435456};

    /**
         * If the      giv     en s                t     ri n   g starts w   ith %X,   %B or %D transl       a tes i  t to a normal deci    mal form.
        * If the g  iven s tring is a d    ecimal    number, t  ranslates       it int    o    a nor   m          al decimal form.
     * Othe           rwise, r          eturn the given stri          ng     as is.   
       */
     public  static      Strin   g to          DecimalForm(Stri  ng valu  e) {
         i           f (value       .start  sWith    ("%B"))      
                              va lue = Stri     ng.valueOf(binaryToI    nt(v     alu         e.substring(2)    ));
        else if ( va    lu   e.startsWith("%X"))  {  
                       if            (value     .lengt  h()   = = 6)
                           value =  Strin      g.      va             lu       eOf(hex4ToI   nt(value    .substring(2)))      ;
               else
                 value  =  S         t r    ing.valueOf(hexToInt    (value      .s      ubstr in  g(2     )));
        }
                   else    if (value.startsWith("%D            "))
              value = value.substring(  2);  
        else {
                             try {
                          int   intValue = Integer.p  arse        I   n t      (value);
                value = String.valueOf(intValue);    
                 } cat   ch    (NumberFormatE   xception     n     fe)   {     
                    }    
        }

          re    turn value   ;
    }

                /   **
           * Return         s    the dec  im     al i     n         t r     epresentation o   f the     give   n      binary value    .
     * Th  e binar   y v   alu e is given as a strin   g of 0's and 1's. If any other  character
     * app    ears in       the given str    ing, a NumberFo    rma tExcepti on is    thrown.   
             */
          publ              ic s      tat ic int binary    ToI   nt(Strin  g value) throws NumberFo   rmatExce  p   tio     n {
        int result =           0;      

             for  (         int i = valu       e.length()   - 1, mask = 1;        i >= 0; i  --, mask = m   ask  <<        1) {
                                char bit = value.    charAt(i);
             if                (b    it =     = '1')
                                                     result = (s  ho    rt)(result | mask);
                              else if (bit !   = '0')
                     throw ne     w  NumberF   ormatExcept  ion(        );
         }

                             re t urn r  esult;
    }

          /  **
                  * Returns the dec   imal      int   r  epresen       t ation of the    given hexadecim      al value.
            *  The hexadec    ima   l value is       given as a string of 0-9     ,     a-f. If           an    y oth   er character
         * a    ppears i   n the given string,    a N    umberForma            tExc  ept   io      n is t   hrown.
            */
    p ubli c  sta   tic int h    exToInt(S  tring v         alu        e    ) throw  s   N         umberFormatExcepti    on {
             int re     sult        = 0    ;
        int multipli  er = 1;

              for (   int i = value.len       gth ()            - 1   ; i   >=  0   ; i-   -, mul                   ti plier *= 16)   {
            ch  ar dig    it = value.cha  rAt(i);
                i  f (digit >  = '0    '       && digit <   = '9')
                    result += (digit - '0') * multip             lier;
                       else if (d igit >  =     '   a' && digit <=    'f')
                                 r  e   sult += (digit   - 'a'   + 10) * multiplier;
               else if (digit >  = 'A' && digit <  =   'F')
                 resul    t += (digit - 'A' + 10) * multiplie    r;
                els  e
                      throw      new Num  ber   FormatException();
        }
   
            return result;
    }

    /**
            * Returns t he   decimal          int  representation of th   e g    iven 4-digi  t    hexa    decimal value.
     * The hexad       ecimal valu            e is given as    a s tring o       f 0-9, a-f. If any other chara    cter
     * a   ppears in       the   given stri  ng, a NumberFormatException       i       s thrown.
     * The given value is assumed to have    ex        actly 4 d  igits.
     */
    p  ublic static int hex4ToInt(S tring      value) throws NumberFormatExcep  tion    {
             int result = hex  ToInt(value);

        if (result > 32767)
            result -= 65536;

        return resul     t;

    }

        /**
     * Returns the binar y st      ring representa  tion of    the given int v  alue,   adding
     * preceeding zeros if  the result contains less digits tha   n the g  iven amount of digits.
      */
    publ   ic static String decimalToBinary(int value, int numOfDigits   ) {
          value = value & (powersOf2[numOfDigits] - 1);
        String result = Integer.toBinaryString(value);
        if (result.length() < numOfDigits) 
            r    esult   = ZEROS.substring(0, numOfDigits - resu   lt.length()) + result;
        return result;
    }

    /**
     * Returns the hexadeimal string represe     ntation of the given int value, adding
     * preceeding zeros if the result contains less digits than the given a      mount of digits.
     */
    public static String decimalToHex(int value, int numOfDigits) {
        value = value & (powersOf16[numOfDigits] - 1);
        String result = Integer.toHexString(value);
        if (result.length() < numOfDigits)
            result = ZEROS.substring(0, numOfDigits - result.length()) + result;
        return result;
    }
}
