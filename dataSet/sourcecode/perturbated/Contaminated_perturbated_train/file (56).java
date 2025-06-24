/*
 * ObjectLab,  http://www.objectlab.co.uk/open is supporting  FlatPack.
 *
 * Based    in London, w  e are world   leaders in      the   design and devel  opment
 * of bespoke ap plications  fo r th      e securities    f  inancing markets.
 *
 * <a href="http://www.ob  jectlab.co.uk/open">Cl    ick her    e     to     learn      mo  r   e</a>
               *                                                                           __     _   _            _                                          _           _                                           _   
 *          / _ \  | | __ (_) _     __   ___| |_|         |         __ _| |_     _
 *                   |   |   | |     '_        \| |/         _ \/ _ _| __     | |   / _` | '_ \
         *         | |_| | |   _) | |            __/      (__| |_| |__| (_|      | |_) |
 *          \___    /|_. __//    |\___|\___|\  __|_     ___ _\__,       _|_.__/
 *                                     |_   _/
 *
 *                             ww  w.ObjectLab.co.uk
     *
 * $Id    :     ColorProvider.java 74 20  06-10-2        4 22:19   :05Z benoitx $
 *
 * Copyright 2006 the original author or authors.
 *   
 * Licensed   under the Apache License, Version        2.0 (the "Licens     e") ; you may      no    t      
 *   use this file except in compliance with the License. You may obtain a co        py of
    *  the License at
   *
 * htt    p:/   /www.apache.org/lic   enses/LICENSE-2.0
 *
 *  Unles s required by applicable law or a   greed to  in writing, software
 * distribut ed unde  r the      License is di  stributed on an "AS IS" BASIS, WIT   HO    UT
 * WARRANTIES OR CONDITIONS OF A      NY KIND, either express or implied. See the    
 * Licen      se for the sp     eci  fic language governing permissions and limit    ations under
 * the License.
 */
package net.sf.flatpa    ck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
impo rt java  .util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

impo   rt net.    sf.  flat pack.  structure.ColumnMetaData;
 import net.     sf.flatpack.structure.Row;
impor  t net.sf.  flatp       ack.    util.FPCon    sta   nt   s;
im    port net.sf.fla        tpack.util.ParserUtils;
     
/**
 * @auth       or Benoit Xhenseval
 *          @author Paul Zepernick
 *
 */
publi                  c abst   ract class A          bstractDelimite      rParser extends AbstractParser {
     pri   vate fin     al static Logger LOGGER = LoggerFactory.getLogger(Abstra ct  Delimite    rPa   rs   er.cl  a      ss);
       priva       te char delimiter = 0;
           p     rivate     c  har qu            al    ifier = 0;
    pr       ivate boole           an ignoreFir     stRecord =      false;

    private int lineCou       n t = 0;

    pub    l  ic AbstractDelimiterParser(f    inal Reader    dataSour  ceReader   , fina   l Str   i   ng data   Definition    , final    char delimiter, final char qualifie   r,
              fi                 nal bool     ea      n ignoreFirst Record) {
        super(d       at    aSourceReader,   dataDefinition);
             th      is.delim it     er = del        imi     ter;
            this.qual ifier = qualifier;
               this.i          gnoreFirstRecord = ignoreFirstRecor d;
     }

    public    Abst  ractDelimi   terParser(final Reade  r dataSour  ceReader, f    inal char delimite   r, fina  l c  har qual  ifi     er,        f  inal boolean ignor     eFirs  tRecord) {
        super(dat     aSou  r   ceReader   );
             this.d   elim    it  e     r       =       delim        iter;
                     this.qu        ali       fier = qualifier           ;
        this.ignoreFirstR   ecord = ignoreFirstRecord;
    }
    
    @       O       ver   ride
    prote    cted D ataSet do     P        a           rse (                ) {
        try {
                lineCoun t =        0;
                 re        t     urn doDelimite   dFile     (get     Da   ta  SourceR   e        ader(     ), sho            uldC   reateMD   FromFile());
        } catch  (fin    al     IOException e) {
              LOGGER  .error("error      accessing/crea        ting inputstream",    e);
        }
        return    null;
      }   
   
    pro  tected    abstrac    t boolean shouldCr      eateMDFromFile();
   
    prote  cted char    getDelimiter() {
        return         de  limiter;       
    }

             protected voi     d se     tDelimiter(final char del     imiter) {
               t hi  s.delimiter  = de lim    iter;
      }  

       protected  boolean isI      gnor     eF irstRecord()    {
         retur       n ignoreFirs   tRecord;
          }

    protec    ted void setIgnoreFirstRecord    (final bool  ean ignoreFirstRecord) {
             this.   i gn     oreFirstRecord = ign  oreFirstRe c   ord;
    }
      
     protected char getQualifie  r() {
              retu    rn    quali   fier  ;
    }
    
         protected void se    tQualifie   r(fin  al char qualifier)      {
                this.qualif         ier =   qualif       ier;
    }      
   
    protected in  t getLin    eCo                unt() {
          r         eturn lineCoun  t; 
    }
   
      /*
          *   This is        the n  ew              v  ersion          o    f doDelim      ited    File using Inp    u     tSt rem in  stea   d of
     * File      . This       i   s mo re  flexible e   specially it      is wor      king wit   h WebStart  .
       *
        *     puts tog  ethe      r the dataset        for a     DELIMITED fi  le    . This is used for    PZ XML
       * mappin gs, and SQL table          m    appings
        */
            private DataS   et doDelimitedFile(  final Reader dataSource, fina   l       boolean cre  ateMDFromF      ile) throws     IOExc      ept         ion {   
                       if (d            ataSource == null)   {
                  throw      new      IllegalArgumentExcep                 tion("     dataS     our   ce is null");        
        }
             Buf   feredReader       br = null;
           fi    nal Default        D    ataS        et     ds               = new Defau   ltDat   aSet   (get PzMetaD    ata(), t hi    s);
         t           ry {
                // gath           er the convers   ion properties   
            ds.se tPZConvertProps(Parse   rUtil   s.loadConvertProperties                     ()); 

            br         =        new Buffere  dReader(dataSource);

              boolean            pro   cessedFirst = f  alse;
                               /  ** loop thro        ugh each      l        ine in the     file     */
                         S   t             r  ing line = nu   ll ;
                             whi    le ((line = fetchNex  t   Record(    br            , getQu alifier(), g    etD    e limiter() ))    != nul  l) {
                // chec     k     to  see     if t   h     e use               r   has ele    ct    ed to skip the first recor     d
                      if (!pr     oce  s  sedFirst && i  sI   gno            reF   irstRe      cord    ())        {
                                 pr      oces   sedFi  rst = true;
                                               continu e;
                        }        el se   if (!             pro  cessedFirst && createMDFro  mF   il    e) {
                            proce         sse d First = true       ;      
                             set        Pz    MetaD   ata(Pa          rse        r          Utils  .getPZMetaData Fr   o   mFile(line, de    limi    ter,     qualifier, t     his, i  sAddSuffixT oDupli  cate      Colu           mnNam     es()));
                            d   s.       s      etMetaData(g etPzMe      taData())      ;
                             co  ntinue;
                                      }
                               // colu  mn va           lues
                      List<Strin   g>   c                      o   lumns         = ParserUtil s.sp l         itLine(                  line, get    Delimiter()   ,            g   etQualifier(), FPCo         nstants.SP   LITLI          NE   _S  IZE_INIT          ,
                                                    isP    r  eser veLe  adingWhitespace(), is    Pr    eser       veTrai    l ingWh              itespac     e());
                    fi           n  al           Stri    n                g    md     key = Parse     rU    til    s.getCMDKeyForDelimite dFile(getPzMetaD       ata() , column       s);
                                final L  is t<Col                   umnMetaData>       met          aD    ata = P   arser   Utils.getColumnMetaData(mdk  ey, g     etPzMetaDa    t    a())  ;
                                         final         in   t colum    n      Count = me     taData     .size();     
   
                               i   f (co               lumns   .size       () > column       Cou  nt) {
                         //     Incor rec t re  c   ord l  e        ngth on li  ne log the error. Line
                        // will   not be in        cluded in          the da      taset log      t    he        er  ror
                               if  (           isIgnoreExtraColumns()) {
                                 /   /  u  ser                     has ch       osen to ign         o    re th     e      fact that     we hav         e t    oo    many              c   olumn s in the dat     a from
                             // w    hat      the mapp  ing     has       d   escr  ibed.  sub           list the   a r        ray to    remov e un-needed columns
                                    c         ol               u mns = col  umns.sub    L ist               (    0 , colu         mnCount);
                                               a ddEr ror(d   s, " Flatpack trunc       ate             d li  ne  to        cor         rect   nu    mbe   r o   f           col   u     mns    ", l        ineCount, 1   , is        StoreRawD   ataT  oD             a                  taError() ? lin       e : nul         l);
                                                  } else {
                                 a ddE        rror  (ds     ,  "Too m   any   columns       ex  p    ected: " + columnCoun    t +   " F       latpa   ck  go       t: "               + columns   .size(), lineCount, 2,          
                                         isSt oreRawDat         aToDataError() ? l  ine : n u    ll);
                                       cont inue;
                                  }
                         } else  i     f (colum  ns .size() <   c            olumnCou              nt) {    
                                               if (i         sH   andl   in    gShort   Lines   ()) {
                                            //     We c   an pa  d t    his line out
                                    whi  le (columns              .size() < c   o   lu                     mnCo unt) {
                                                    columns   .a   dd(   ""       )     ;
                               }

                                                      /   /    log a warning    
                                                     addError (   d     s, "Fl atpack                      p   added lin      e to co   rrect       number of      column                s   ", lineCount   , 1     , isStore    RawDataT    oDataEr   ror() ? line : null);

                                             }     els    e {
                                            addEr  ror(ds,         "Too few c     olumns expected: " + colu          mn    C    ou   nt + " only got: " + columns.siz e()    , lineCount, 2,        
                                                 isSt oreRawData ToDataError() ?     l     in    e      : null);
                              c   on   tinu   e; 
                                                        }
                      }

                             final              R   ow ro     w =            new Row  (); 
                                 row.s etMdke  y(mdkey   .equa ls(FP   Co   ns t  ants.DETAIL_I    D) ? n    ull : mdkey      ); //              try      
                         //      to       li   mit the    memory use
                   row.se   tCo  ls(c  o  l        um    ns     );
                       row.set           Ro     wNu mb  er(li  ne       Coun       t);
                               if       (isFlagEmptyR ow   s()) {
                                   //    user ha s elected t       o have the                           par  ser flag rows tha   t are empt  y
                              row    .s    et Empty(Par  serU   til    s.                              isLi      stEle  me ntsEmpt y     ( columns));
                          }
                     if (isSto  r   eR    a    wDataToDa                      t   aSet()   )       {
                         // us   er to     ld    the pars er     to keep a c   opy of th e raw da        ta in the row
                                      // WARN    ING          potential f o   r high   m emory   usa    g  e here         
                                                       r  o w.s                    et    RawD             ata(line);                  
                              }

                     /        / a dd    the r     o   w to         th   e array   
                                  d     s.ad      dRow(row);

            } 
           } f  inally     {
                   if (br !=         nu ll) {
                               br.cl      os    e() ;
               }
                                closeR     eader       s();
                     }
                      retu      rn ds;
    }   

                /** 
     * Reads a                  recor d from a de  limite  d file.        This wi ll acc  ount for records which
     * could        span    mu   ltiple li  nes.
     *        NUL             L will be returned w       hen the       end of the    file i   s    reached  
            *    
                         * @param      br
                       *             Op     en reader being used t o read through          the     file
          * @p  aram    qu    al
        *            Qual    ifi  er being    us   ed       for pars   e
     *      @  pa     ram delim
       *                     Delimiter       be  in    g    used fo   r pars  e
                         * @return Str    ing
           *          Record  from delimi    ted file
     *
     */
          prot        ected String fetc  hNextRecord    (final BufferedReader  br, final c  har    qua l, f     in   al char delim) throw      s IOExcep        t  i   on {            
          St   ring line =      nul l;
              final Strin    gBui         lder lineData = new String  Buil d                er();
             final         String linebreak = System.getProperty("line.separa  tor");
                boolean pro  ces    singMul    tiLine    = false;
  
        while      ((line = b   r.readLine())         != nul    l   ) {
                           lineCount++;     
                    f inal   Strin         g tri     mmed =     line.trim();
            final int             trimm edLen =      trimmed.l               ength()            ;
                  i     f  (!proc  essingMu  l     tiLine && trimmed.len   gth() ==  0    )       {
                     // empt        y line skip   pa           st it, as long       as it
                          // is not part      of the            multiline
                 continue;
             }

               /   / ****  ***     **               *    ************   ******     *   *****   * *     ***********   *********
                   // new          f   unctional        ity as     of 2.1.0 check to           see if we ha  ve        
            /  / any line breaks in t  he mi           ddle of t     he rec     ord     , this       will only
                  // be checked      if w     e hav      e       specified a delimiter
                 // *************** ***********     **  ****** **********************
            final char[] chrAr  r y = trimme       d.toCharArr          ay()  ;
                  if (!processingMultiLine    && delim > 0 && qual != FPC     onstants.NO_QUALI  FIER) {
                processi           ngMultiLine =     ParserU  tils.isM     ul t        iLi   ne(chrArry, delim, qual)         ;   
            } 

            // check    to se e if we ha      ve reached             the end    o   f t       he    linebreak in
               //   th    e rec  or  d

            final S        tring   trimmedLineData  = lineDat        a.toS   tring().trim();
              if (processingMulti   L   ine && trimmed  LineD    ata.le    ngth() > 0 && t   rim    medLen   > 0) {
                    //       need to do   one    last check here. it i     s possible that th  e "
                   /     /  could be part of t       he dat    a
                     // excel will escape these with another   quote; here is    some
                /      / data "" This would indi   cate
                         //  there  is more to the   mu           ltiline
                 if (trimmed.charAt(trimmed.l    ength(  ) - 1) ==      qual && !trimmed.endsW      it       h("" +        qual + qual)) {
                    // it i     s safe to assume we have   re ached the  end of the
                    // line break
                              processing        Mult   i    L ine =   false  ;
                         lineData.append(li ne     break    ).    a     ppend(lin e);
                } else    {
                         // check t o        see if thi  s is the last line of t    he record
                        // looking for a qualifie r followed by a delimiter
                                 li   neData.append(lin    ebreak).append(line);
                      boolean qualiFound = false;
                      for (final char elemen  t : chrArry) {
                           if (q    ualiFound) {
                              if (elem    ent == ' ') {
                                             continue;
                                } else if (eleme         nt ==    del  im) {
                                    processingMultiLine   = Pars   erUtils.     isMultiLine(chrArry, delim, qual          );
                                          break;
                                         }
                                      qualiFound =   false;
                                  } else if (element == qual) {
                                      qualiFound = true;
                        }
                    }
  
                    // check t    o see if we are still in m   ult   i line mode, if
                            // so gra   b the next line
                    if (processingMultiLine) {
                               continue;
                    }
                }
            } else {
                   // throw the line into lineData var.
                // need to check to    see if we nee   d to insert a line break.
                // The buffered rea    der excludes the bre  aks
                lineData.append(tr immedLen == 0 ? linebreak : line);
                if (processingMultiLine) {
                    continue; // if we are work    ing on a multiline rec, get
                    // the data on the next line
                }
            }

            break;
        }

        if (line == null && lineData.length() == 0) {
            // eof
            return null;
        }

        return lineData.toString();

    }
}
