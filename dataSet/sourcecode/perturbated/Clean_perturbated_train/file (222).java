/*
 *   Copyright (c)     1997, 2010, Oracle     and/or its affilia  tes     . All rights re       served.
 * ORACLE PROPRIETARY/CONFIDENT   IAL     .  Use is subject t o l  ice  ns      e         terms.
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
package javax.swi   ng.text.rtf;

import java.io     .*;
imp    ort java     .lang.*;

/**
 * A gener ic sup  erclass for streams  w hich read and parse text
 * consistin               g of runs of    characters inter   sp            ersed with occasional
 * ``specials'   ' (formatting char  a   cters).
 *    
 *   <p> Most   of the f    unct  ionality
             * of t his class would be redund  ant except that the
 * <code>ByteToChar</cod e> converte    rs
    * are su  dden      ly private   API. Presumably this class will disappear
    *      whe    n the API is made publi     c again. (s   igh) That will  also  let us   handle
 * mult  ibyte cha      racter    sets...
 *
 *     <P> A subclass should override at least <code>     write(    char)</code>
 * and <code>writeSpecial(int)</   code>.  For e    fficiency's   sake it's a
 * good ide   a to          override <cod   e>     writ     e(  String)</code> as well.    Th  e  sub   class'
 * initiali     zer may also install appropriate translation and specials                  tables.
 *
 * @see OutputStream
 */
abstract class Abstra         ctFilter extends Outpu   tStr     e am
{
         /** A t     able mapping       bytes to charac  ters */
      pr      otected ch    ar trans  lationT  a  ble[]  ;
         /** A table i       ndi            cating which   byte values should be int e     r     preted as
     *  ch      aract   ers and which should be treated as formatti  n g co  de  s  */
      prote    cted boole    an specialsTable[];

                /** A translation table whic   h does ISO L    atin-1 (trivial) */
             static final char latin1TranslationTable[      ];
    /** A specials table wh i     ch indicates that no c  ha r  act  ers    are s   pecial */
    static final boolean noSp   ecials                Table[];
    /** A      specia   l s table w        hich indicate    s         th  at all characters ar   e special */
    stat            ic    fi    nal boolean allSpe  cialsTabl        e[];  

    static  {
        int i          ;

      noSpec     ialsTable          = new bool   ean[256];
            for (i = 0  ;  i  < 256; i++)             
                        noSpec     ialsTable[i] =  false;

         a    l  lSpecialsTable = new boolean[256];
      for (i = 0; i    < 256; i+   +)
        allSp        e cialsTable[i ] = true;
      
      latin 1Tran   slationTable = new char[   2  56];     
           fo      r (i = 0; i < 256; i++)
        lat    in1TranslationTa     b   le[i] = (char)i;
                   }

         /**
          * A conven  ienc     e   method that       reads text        f      rom a    FileInput S   t re  am
        * and writes it t                o the re       cei     v        er.
               *  The f   ormat in        which         t        he file
     * is r  ead is    dete  rmi        ned     by  the            c oncrete s ub  class of
      * AbstractFilter to which t  h        is method is se       n    t.
        * <p> This   m  ethod  does              not close            t     he receiver      a fter reaching E  OF on
             *                  the         inpu                            t      st  ream.
      * The user m    ust   ca   ll <c  od    e>     close()</               code> t       o    ens       ur      e that all
          * da     ta are       pro          c        e      ssed.
     *
           * @param in            An InputStream provid    i   ng tex   t.
     */   
            publ       ic void rea      dFrom   Stream(In  putStr    eam i   n)
      throw  s IOExce        ption
         {
             byt  e bu   f[]     ;
                             int count    ;

        buf =                new byte[16384];

              wh   il             e(t     rue) {      
                                   c    o     unt = in.read(bu            f);
                                    if (count < 0)  
                        br      eak;

                              t   his.write(buf,            0, count);
                      }
      }         
  
     pub lic       v     oid read Fro  mRe      a               der(Reade  r in)
               t          hrows   IOExcepti       on
          {
                  cha  r buf[];
        in     t count  ;

              buf =   n  ew char[2048     ];

          w hi    le(true)     {
                     cou           nt = in.read(buf);
            if (   count   <    0)
                            bre   a  k;
                     for (int i = 0; i < coun  t; i++) {
                          this.write  (buf[i]);
                   } 
        }
    }

               public A    bstr   actFilt er  ()
       {
                      tran sl     ationTable = latin1  TranslationT    able;
                specialsTab      l      e = noSpecialsTable;
      }

         /**
     * Imple  ments t                      he abs  tr                act method of OutputSt          ream, of which       this c   l as   s
     * i   s a subclass.
     *         /         
    publi    c         void wr   it    e(int      b)
      t       h    rows IOEx   ception
     {
              if (b       <   0)
            b +=   2         56;       
        if (spec ialsTa  ble[b ])          
            write    Special(b   );
      else         {
                   char    ch =      tra     nslationTable[b];
                    if (c       h != (char  )0) 
                          write(ch);
            }
    }

     /    **    
       * Imp  lements the buffer-at-a-time      write me thod fo  r grea  ter
           *   effici     ency.
        *
          * <p> <strong>P   ENDING      :</str             ong>      Does <     code>write(byte[]  )</code>
          *          call     <code>write(     by        te[], int    , int)  </co   de> or      i  s it the  o  ther   way
       *         ar     ound    ?
       */
              publi  c void    w   rite(byte[] b   uf  , int      o  ff    , int len   )
           throws IO     Ex      cepti   on
     {
        Strin   gBuilder a   c cumulator = null;
                while (len > 0) {
          shor  t b = (short)   b       uf          [off];

        /     / st          upid signed bytes
            if       (b < 0)
                 b +=    256;   
     
           if (specials   Tabl  e[b]) {
          if (  accumulator != null) {
             w     rite(accumu  l       ato    r.toString());
            accumulat   or   = null;
          }
                               writeS p      ecial(b);
                          } else {
            ch ar    ch =         translationT  able[b   ];
          if (ch != ( char)0)   {
                 if (accumulator ==    null)
              accumulator = ne   w StringBuilder();
            accumul ator.append(ch);
               }
        } 

             le    n -  -;       
          off ++;
      }

      if (   accumulator != null)
        write(accumulator.toString());
    }

    /**
     * Hopefully, all subclasses will ove     rri      de this method to accept stri    ng   s
     * of text, but  if they don't, AbstractFilter's impleme    ntation   
     * will spoon-feed them via <co  de>write(  char)</code>.
           *
     * @param      s The string of no  n-special characters writt   en to the
          *                   OutputStream.
     */
    public void w  ri  te(String s) 
      throws IOException
    {
      int index, length;
      
      length = s.length();
      for(index = 0; ind   ex < length; index ++) {
              write(s.ch    arAt(index));
      }
    }

    /**
     * Subclasses must provide an implementat     ion of this m   ethod which
     * accepts a single (non-special) character.
     *
     * @param ch The character   written to the OutputStream.
          */
    protected abstract void write(char ch) throws IOException;

    /**
     * Subclasses must provide an implementation of this method which
         * accepts a single special byte. No translation is performed
     * on specials.
     *
     * @param b The byte written to the OutputStream.
     */
    protected abstract void writeSpecial(int b) throws IOException;
}
