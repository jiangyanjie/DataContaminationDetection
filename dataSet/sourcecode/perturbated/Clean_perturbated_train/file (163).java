/*
 * Copyright (c) 2007, 2017, Oracle and/or its affiliates. All  rights reserved.
 * ORACL E PROPRIETARY/CONFIDENTIAL    . Use is subject to l  icense terms.
 */
    /*
 * Copyright 1999- 2005 The Apach e Softwar  e       Foundation.
 *
 * Li   censed under the A   pache L     icense, Version 2.0 (th   e "License");
 * you may no     t use      this file except in      complia  nce with the   License.
 * You may obt      ain a copy  of      the License at        
 *
 *         http://www.a   pache.org/licenses/LIC  ENS   E-    2.0
    *
 * Unless      required by applicable law or              agreed t   o in writing,  software
 *    distributed under the License is distributed on an "AS            IS" BASIS,
 *  WITHOUT WARRANTIES   OR CONDIT       ION    S OF ANY KIND, either express or implied.
 * See the Lice   nse for the specific l anguage governing permissions     and
 * limitations under the License.
 */

package com.sun.org.apache.xerces.internal.impl.dv.xs;

import java.math.BigDecimal;

import javax.xml.datatype.DatatypeFactory;
import jav  ax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.o   rg.apach      e.xerces.internal.impl.Constants;
import com.sun.o    rg.apache.xer  ces.internal.jaxp.data type.DatatypeFactor   yImpl;
import com.sun.org.apache.xerces.interna        l.xs   .d      atatypes.XSDateTime;

/**
 * This is the base class     of all date/tim  e datatype validators.
 * It implements common code for parsing, valida    ting and comparing datatypes.
 * Cla     sses th     at e   xtend this clas  s, must implement p     ars    e() m  ethod.
 *
 * R       E   VISIT: There are man  y ins  tan  ce variabl    es, which              would cause problems   
 *                whe        n we s    upport grammar caching.   A grammar is  poss       ibly u  sed by
 *                    two    pars  e   r inst   ances a  t the s      ame t  ime, then the same sim  ple typ e
        *          decl ob          je    ct can be    used to validate   two str  ings at the same time.
 *          -SG
 *
 * @xerces.internal
 *
 * @ author Elena Litani
 * @author Len Berman
 * @author G  opal Sharma, SUN Micro  systems Inc.
 *
 *      @version    $Id: Abstra    ctDateTimeDV.  java,v 1.7 201      0-11-01 04:39:46  joehw Exp $
 *  /
public a  bstract cla   ss AbstractDateTimeDV ext     ends TypeValidat or    {

        //   de      bugging
    private static final bo olean DE   B  UG = fal       se;
    //define s  hared variable   s     for      date/time
       //  define constants to        be used     in    assignin g default values for    
    //all           date/time excluding duration
    protected        final st    at    ic int YEAR = 20     00;
    pro tected fin     al static int M  ON TH = 01;
    pr      ot    ected final sta tic int DAY = 01;
    protected  static final     DatatypeFa cto   ry datatypeFactory = n       ew DatatypeFactor  yImpl();

     @Override
       p   u          blic     shor     t   g     etAllo   wedFacets() {
         return (XS      SimpleTypeDecl.FACET_PA   TTERN |   XSSimpleTypeDecl.FAC    ET_WHITESPACE | XSSim pleTypeDecl.FACET_ENUMERATION | XSSimpleTypeDecl.FACET_MAXINC LU        SIVE |   XSSimpleTypeDecl.FACET_MININCLUSIVE | XSSimp         leTypeDecl.FACET_   MAX   EXCLUSIVE | XSSimpleTypeDecl.FACET_     MI  NE    XCLUSIVE);
          }//getAllowe  dFacets()

                 //    disting uishes between   ident  ity and      equality  for date/time values  
    // ie: tw o values repres      enting the      same "m  oment in time" bu  t wi  th differen   t
    /         / remembere   d                     timezones ar       e   no   w equal but not identical    .
    @O  verride
    public boolean isIdentical(O      bjec  t value       1, Object value2)          {
            if (!(va          lu  e1 instanceof    D    ateTimeDat    a) | | !(v  alue2 instanc eof D    at e      TimeData)) {
                    return fa   lse           ;
        }

         DateT   imeDat       a v1  = (D      ateTim  eData) value1;
          DateTimeData v2 = (DateTimeDa  ta) value2;

           // ori           ginal timezones must be      the                 same in  add   ition          t       o date/time values
            // being   'equal'
                 if    ((v1.         time    zoneHr =    = v2.t     imezoneHr) && (v1.timezone Min      == v2.t   i   mezon      eM  in)) {
             re    turn v1.e    quals(v2);
                     }

               return false;
    }//isI    dentical()
  
    /  / the paramete    rs a     re in compiled f o  rm (fro m   ge tActualV    alue)
          @Override
    public int comp are(Object value1, Object    value2) {     
        return                  comp   areD        ates(((DateT imeData) va  lue1),
                        ((  Date  TimeData) value2), true)   ;
            }/ /compare()

    /**
     * Compare a    lgor  i        thm de     scribed i  n   dateDime  (3    .2.      7). Dur     ation data             t   ype
                   * ov erwrites    this meth     od
          *      
     * @param date1 norm   alized da  te     represe        ntation      of the         firs  t value
     *        @param date2 normalized            date repre  s         en tation of the second            value
     *      @param strict
      *    @re tu rn less, greater,   le    ss_equal, g reate  r_equa  l   ,     equal
     */  
    pr  ote cted short     comp    a       r      eDate s    (  DateTimeData      da    te1, Date   TimeData d       ate2, boolean strict) {
        if (date1. utc == date2.u t     c) {
              re   turn           compa           reOr    der(date1, date2);
               }
               sho                          rt c1, c2;

            DateTimeData tempD ate     = new            D  ateTimeData(null,     thi  s);  

        if (date1.utc == '            Z') {

              //co  mpa re d  ate1<=dat e1<            =         (date2   wi   th time zon       e -14)
            //
                          cloneDate(d ate2, t   empDate)      ; //clones    date1 value to    globa          l   tempo rary s    torag e: fTempDate
                           tempDate.timezo    neHr   = 14  ;                    
                    t   em  pDate.ti    mezo    ne  Min =    0;
                           t   empDate.utc = '+              '   ;    
              normal   ize(t   empDate            );  
               c1   =  c      ompar                    e     Or   der(date     1,      tempDate)   ;     
                   if        (c1 == LESS_TH AN)      {
                                           return c1;   
                  }

                           //compare    date1>    =(    dat        e2 wi   th time   zone      +1   4)
                 //
            cloneDate(date2,   tempDate); /  /clones date1  v     alue to global temporary storag   e   : t empDa        te
                                      temp  Dat           e.timez    on  eHr    = -14;
                           temp   Da   t     e.     time         zon  eMin = 0;
                 tem  pDate.     utc         = '   -';
                     n or  ma    lize(tempD                 ate)                      ;       
                             c2    =       co       mpareOrder(date1,     tem p     Date     );
            if (  c2   == GREATER_TH     AN)    {   
                    r eturn c2;
                   }

               retur       n IN     DE TERMINATE;
                   } else   if (  date2.utc    =      =   'Z')     {

                        //compar       e           (     da    te1 wi th time z     one -   14)<=da        te2
                                //
                      cl    oneDate(date   1,    t    empDate)  ; //c   lones   d     ate1 value to global temporary sto ra   ge:  temp      Date
                                   tempDa         te.timezoneHr = -       14;
                          tempDat   e.timezone Mi   n =             0;
                  te       m pDat  e.    u tc             =  '      -'  ;
                if (DEBUG)        {
                System .out.println("tem   pDate=" + da     teToString(tempDate) );
                                        }
                  normalize(tempDate);
                   c1    = co        mpa     reO        r der(tem   pDate  ,   date2);
                             if  (DEBUG)      {  
                       Sys  t em.out.prin  tln("dat  e=   " + da   t  eToSt  ring(date2));
                             Syst  e     m.ou t.pri ntl      n("tem  pDat   e="     + d       ate ToString(         t                empDat e))   ;
                }
               if (c1 ==         LESS_THAN) {
                          return c 1    ;
                        }    

                             // compare       (  da  te     1     w        i   t  h ti                   me zo                         ne   +1           4)    <=date2        
                    //
                   cloneDate(      date1, tempDate)    ;    //clones date1 value                    to glo b         al     te  mpor  ary        storage:   tempDate
                            tempDa   te.tim        ezoneHr = 14;
            tempDate.timezo        n     eMi   n    =          0;
                      tempDa         te.utc =    '+'   ;
                        no                    rma    l          ize(tempDate );
                 c2 = co m         pa     reO  rder(t em     pDate, date2);
                   i   f (DE BUG) {
                             System.out .     p     rintln   ("t  empD   a te=   " +           d             at  eToString(tempDate));
                  }
            if       (    c2         == GREATER_THAN   ) {   
                           r  eturn c         2;
                       }

            retu        rn INDETERM  INATE;
                  }
            return IND   E    TERMINATE;

    }

      /**
     *             Given n                        orma    li         ze    d va        lue  s, de       ter    mines o rde r-rela      t ion betw   een g     ive d         ate/time
                       *       objects.        
     *
             * @param date1 d ate/time o   b ject
     * @param date2 da     te/      t     ime obj  ec    t  
     * @retur  n   0 if       date1 a   n    d           date2 ar   e equa   l    , a va  lue les   s than 0 if da            te     1     is
     * le ss  th     a    n date2, a va      lue great    er    than    0 if date1 is grea          t     er     than date2 
                * /
                       p    rotected shor  t compare             O        rder     (DateTimeData date1, Date  TimeData dat e2   ) {
                   if (    dat e1    .       positio         n < 1) {
            i                       f (date1.      yea     r <         d  a       te2.year)   {
                  return -1;
                               }
                     i          f (date1.ye      ar > dat   e2          .y          ear) {
                        retu   r   n 1;
                          }
          }
        if (date1.position < 2) {
                                   if (date1.month < date2.mon  th) {   
                                   return      -1       ;
            }
               if (     date1     .m      ont    h > date2.month    ) {
                                           return       1;
            }
        }
            if (d   ate1. day < date   2   .day)          {
                     r   etur    n      -   1;
            }       
        i   f   (date1.day > date2. day)     {
                return        1;    
                       }
        if (d   ate            1.hour     < dat e2   .            hour)    {
                  return  -1;
           }
              if (d   a                   te1.hour     >   date2 .hour)        {
                    ret         urn 1;
        }
                           if (date1.min    ute < date2.minute       )                 {
                     re   turn     -1;
         }
                           i       f (date1     .minute        > d       ate2  .minute)          {
                    retur      n 1   ;  
            }
                              if (d     ate1.     second < d  ate2.secon  d     ) {
                          return -1;
              }    
                if (date1.     second > d  ate2  .secon d   ) {
                   return 1;
             }
        if  (date        1.utc <       date2.u    tc ) {
                     retu   rn -1;
              }   
             i          f     (d     ate1.ut      c > da te2.utc) {
                       return 1;
                             }
           return 0;
         }

    /**
      * Par   ses time hh:mm:ss.s  ss and t   ime     zon    e if     any
                   *
     * @param start      
               * @param end
     * @p   aram     d   ata
          * @ex      ception R    un  ti    meExcept   ion
       */
    prote ct  ed vo           id get   Time(    String    buffer, i                nt start, int     end, DateTimeD at  a d      ata) thro  ws Runtim   eEx   cept    ion {

         int sto       p = st  art + 2;
      
        //  get hours     (hh)
                    data.  hour =        par   s   eInt(buffer,     start,      stop);

        /           /get minutes (mm)

              if (buffer  .ch   a  rAt(  stop        +    +) != '  :') {
                   throw new   RuntimeExcepti        on(    "   E          rror     in par               sin g ti  me zone"   );
              }
        star  t    = stop   ;    
         stop     =   stop + 2;
                      dat     a.m           inute = pars     eInt(     buffer                  , start,      s  top);

           //    get                  seconds (ss)
                     if (bu ffer.charAt(sto  p++) != ':') {
                         throw ne     w R     untimeExcep  tion("       E rror in pars ing time zone");
            }

         //find U   TC sign if any
                  i        nt     sign  = findUTCSign     (buffer, sta  rt, end);

                 /      /get seconds     (ms)
           s  t         a    rt = s    top;        
                             st  op = sign < 0       ? end : sign;
             d                   ata.second      = par     se    Second(buffe                          r, sta    rt, stop);  
  
                       //par   se  UTC time zon e   (hh  :mm)
          if (sig  n > 0)  {
                   getTimeZo   n        e(buffer    , d    ata       , sign,   end);
         }
      }

    /**
     * Parse   s     date         CC  YY-MM-DD
     *
     * @param  buffer
      * @  p   ar       am         start start     po     sition  
      * @p   aram end end p    ositio n
       * @param date
             * @ex        ception RuntimeExcep   tion
                         */
    protecte d in  t getD  ate(String       buffer, int start,     int end, Dat    eTimeData date) th rows Runtim    eException {

               start = getY  earMont   h(buffer, start, end, date  );

          if (buffer.ch            arAt(s    tart++) !                      = '-')  {
                   thr                ow    new Run            ti    m eException           ("CCYY-MM must be fol       lo   we  d by '-'   si           gn");
             } 
        i        nt s        top = start     +   2;
             date.day = parseInt(b      uffer, start , stop)             ;
                 return     s   top  ;
    }

    /**
     * Parses   dat       e CCYY-MM
     *
              * @p           aram      buffer
     * @   param st  art start   pos      ition
        * @par      am       en   d end     p  osition
          * @   param                  date
             * @exception Runtim  e   Ex         c   eption
     */
     protected int getYearMonth(Stri          ng buffe  r, in  t    st      ar    t,     in   t end, Dat   eTimeData d   at   e)   throws Ru          ntimeE xce   ption {

                    if (buffer.cha           rAt(0) == '-   ') {
                   // REVISIT: da                    te s tar  ts                 with pre               ceding '-'   s i   gn        
                      //                do we ha       ve to do a nything    wit        h it?
                   //  
                                   st          art++;
                  }
        i     nt i = inde    xOf(b   uff  er   ,   start, end,      '-');               
                  if      (i =       = -1) {    
                  throw new RuntimeExceptio   n(         "Year separator is         mis      si  ng or     m     isplaced     ");
        }
             in    t   length = i - start;
                 if     (length <       4) {
                      throw new Run     tim  eExcep    t           ion("Y       ear must have 'CCY  Y' format");
        } else if (length >        4     && buffer  .ch   ar    At(st   art) ==      '0') {   
             throw new  RuntimeException("Le         ading zeros are required if th                  e   year val  ue would otherwis  e have fe   wer   than four   digits; ot      herwis   e they are fo  rbidd   en    ");
                   }
        date.year = parseIntYea r(buff    er          , i);
        if (b uff  er.charAt( i) !=     '-') {
                            throw      new Runti  meException(             "CC  YY must be followed   by    '-'  s              ign")    ;   
             }
            st   a    rt = ++i       ;
                          i =     s       tar    t           + 2;
            d    ate.     month =    p   ars   eInt(buffer,    start,             i)  ;
         return i; //fStart points rig   h  t a       fter the   MONTH 
            }

    /**
         *   Shared   c   od        e fro   m D at  e an d Ye      arMon   th  data   types.    Fi   nd        s if ti    m       e      zone sign       is
     *    pre  sent
              *
        * @param end
      * @par       am  date
     * @   ex   c        eptio        n Ru       ntim  e Exception
           */
    p        rot       e    cted vo      id parseTimeZone(String buffer, in               t s  t      art, int   en  d, Da   teTimeDa       ta              date) t  hrows     RuntimeExcept  ion {  

            /    /     f   Start po ints          right after t         h         e da    t  e

                                    if (start < end) {
                  if (  !isNe    xtCharUTC      Sig          n(b uffe r, start  ,     en d     )) {
                          thro   w new R        unt   imeExcept  i  o    n("Error     i  n m             on  th par sing")   ;
                       } el                se {
                                  getTimeZone(       b  uffer, d         a  te  , start, en  d);
                            }
             }
        }
    
         /**
          * Parses    tim  e        zo          ne: 'Z'         or        {     +,-} foll       o   wed by          hh:mm
         *
         * @p  aram  dat     a
                * @pa r  am si    gn
                    * @exception RuntimeExcep   tion
     */
               pr  ot   ec   ted void ge    tTi            meZon       e(String    buffer, DateTimeData da        ta, int  sig  n    ,     int end) throws Runtim      eException      {
        data.utc = buffer.c   harAt(   sign);

               if (buffer.char A   t(sign) == '    Z') {
                          if   (end            >   (++s          ign))      {             
                               thro          w n      ew RuntimeExcept    i               on("Error in    par  sin         g   time z   one");    
                   }
            r  eturn                   ;
            }   
                     if (    sig  n <=        (end - 6)) {
      
                    i       nt negate       =   bu  ff     er.charAt(sign) == '-'    ?            -1 : 1;
                 //par    se h   r
            int s      top = +        +sign + 2;
                d   at  a.t          imezoneH       r =   neg  ate     *         parse                Int( buffer,                sign, stop )   ;
                   if ( bu    ff        er  .cha   r  At(stop    ++) != ':     ') {
                          thro   w new Ru  ntimeException("     Error        in pa  rsi   ng time   zone");  
            }   

                         //parse mi      n
                           data.timezone          M in =   negate * parse In    t(              buffer, stop,               st    op + 2);

                             if (stop +    2 != e  nd) {
                                   th        r ow new Ru    ntimeExc     ept    ion("Error   in   parsing time zo       n     e"                         );
                         }
                            if        (da    ta.time        zoneHr  != 0 || da  ta    .             tim  ezoneMin     !=       0)               {
                     data.normalized           =      false;
            }   
                } else {
                  throw    ne                  w Runti meException("Err             or in par   sing tim       e      zone               ");
        }
                  if (DEBU     G      ) {     
                                        System.out.println("time  [ hh]        =" + d                at  a.timezoneHr      + " time[mm]=" + data.t                                      imezoneMin);
           }
           }

    /**
                   *                   Computes index of given      char wi   t  h  i  n            St   ringBuffer
     *
     * @param st  art
          *          @par   am end
     * @param   ch       char      acte   r to l       ook   f  or  i      n                         St     ringBu   ff  er
            *      @ret   u   r n index of ch               within StringBu  ff  er
        */
    pro     tected in       t indexOf(String b       uf  f      er, int start, int end,     ch   ar ch) {
        for (int  i =            st    art;          i < end; i+ +) {
                    if    (buffer     .charAt(i) ==        c  h) {
                      return i;
                     }
                }
           return -        1;
          }

       /         *       *
            * Validates g  iven      date/time obje   ct acco ring  to W         3C    PR Schema [D.     1 ISO 8        601
     * Con   ventio    ns]
               *    
        * @param    data
     */                      
    p   r otected    vo   id           vali  dat   eDa     teTime(       Date               TimeData data) {     

        /     /R E                 VI     SIT:    sho    uld we    th   row an exception f         or no t va         lid da      te   s
             /                      /                or rep     or  tin   g        an    error    m            essage sh    ould be su                  f   ficie   nt  ?

          /**
                 * XML Schema 1.1 -     RQ-12  3: Allow year 0000 in date rel      ated    types.
             */      
          if (!Cons  tan ts.SCHEMA_   1_1_SUP   PORT &&      data  .year    ==   0) {
                 throw ne   w Runtime       E    x   ception("The year                  \"0       00    0\" i     s an ill egal     year value  ");

               }   

              if (data.m      ont       h < 1 || d   ata.mon     th > 12) {
                    throw  n   ew Ru      ntimeExceptio      n("The    m      onth must     ha          v    e v alu    es 1 to 12") ;
   
                   }

        //valid   ate       day        s
             if   (dat        a.         d       ay > m  axDayI    nM     onthFor(dat     a   .year, data   .    month) ||  data.day     < 1)     {
                        throw new            Runtime     E       xcept  ion ("The day mus     t hav     e values   1 to                             31")    ;
         }  

                //validate h    o       urs
              if (d ata.ho    ur   > 23 || da       t   a.hou  r <   0) {
                            if (dat            a      .hour      == 24 && d  ata.min   ute == 0             &       & dat             a.      sec ond =     = 0) {
                               data          .hour   =                                0  ;
                    if  (++data.     day   > maxDayInMonthFor(data.yea r, data.  m on        th)) {
                           da      ta.day = 1;
                                 i      f (++data.mo            nth      > 12)   {
                                                  data.m    onth = 1;
                          if (Constants.SCHEM       A_1_1         _SUP   PORT) {
                                                      +    +data.        ye   ar;
                                         } els    e if (++d ata.ye    ar    ==       0) {
                                                       data.year = 1     ;
                                       }
                               }
                         }
                            } els     e {
                                        thro    w new     RuntimeExcept     ion     ("Hou   r must have values  0-23, unles         s 2   4:00: 00")        ;
                    }
                         }

          //va    lidate     
                              if (da ta.min     ute > 5      9     || data.   mi    nu  te < 0)        {
                thro       w ne     w     Runti meException(          "Minute must h     ave     val  ues     0-59" );
                            }

         //val     idate   
                                   i  f    (d   at a        .second >= 60 || data.secon           d < 0) {
             th       row new Ru  n     timeExc eption("     Second mu  st    hav    e           va    lues 0-59");

                    }

         //validate  
             if (data. timez oneHr   > 14 || data.ti     mezoneHr    < -14) {
                throw new        R  unt imeEx    ception(     "Time zone should   hav    e range     -14:0         0 to +14:00");
        } else {
                i f ((d ata.timezo     neHr ==   14 || d     ata.timezoneHr == -    14) &        & data.timez      on       eM i      n !=   0)       {
                                        throw      ne  w R     unt  im   e         Excepti       on("Time zon    e shou             ld        have       r  an    ge    -14:00 to +14:00");
                   } else if (    dat    a.timezoneMin > 59 |   |    data.timezoneMin  < -59)      {      
                      t  hrow ne    w Runti              meException("Mi       n ute must have     va lues 0-59");
                        }
        }  

     }

    /**
        *        R      e       turn inde   x o  f U   TC       c           h  ar: '              Z', '+ ' , '-'
        *
            *     @param     start
     * @param en d
            * @re              tur   n index of th     e UTC ch  ar         act  er that was fo   und
     */
    pr  otec  te     d i  nt fi ndUTCSign(             Stri ng buffer, i       nt s      t   ar  t,     int end)            {   
               int c;
         for            (i    nt i =        st     art; i <      end;  i+   +) {
            c = buffe  r.c harAt(    i);
                     if (c    ==           'Z'           ||    c        == '    +' ||    c     =    =      '-'     ) {
                   retu    rn i;
                            }
  
                  }
        retur         n -1;
                  }    

      /**
     *    R  etu    rns
       * <code>true<      /c     o    d  e> if the   character  at start  is '   Z', '+' or                          '-'.
     */
    protecte         d    fina   l boolean   isNextCharUTCSign(Str in    g      buffe   r, i   n   t st       art, int end)     {
            if       (start  < en  d)     {
                c        har      c =      buffer.ch        arAt(    st   a  rt);
              return    (c == 'Z' |       |   c   == '+'         || c ==     '-'                           ) ;
                     }
          re    turn false;
    }

    /**             
     *                            Given star t an  d         end position, parses string value
              *
         * @param              buf fer   string             to p     ar se
              * @param st   ar   t s   tart posit    ion
          *   @para         m e  nd end     positi   on
     * @ retur    n     r    etur  n integer            represe    ntation of       ch      a  racters
            */
           prot    ected int parseInt( String     buff    er, int sta   rt, int end) 
               thro   ws Num      berFormatExcept    ion {
                    //REV ISIT      : mor   e t     esting on this par sing          needs to be d        o       ne.
             in    t     radix =    10;
          int   result = 0 ;
             int d igit = 0;    
        int limit = -I   nteger.  MAX_VALUE   ;
            in t mult min = limit   /              radix;
              int   i = st  ar      t;
          do {
                dig it = getDigit  (buffer.charAt(i));
                           if (digit    < 0)           {
                                throw      new Nu  mber    FormatExcept        ion("       '" + b   uffe   r + "   ' h                   as wr   ong f o         rmat   ");
               }
                          if (result <   mult    mi    n) {    
                                throw n   ew            N   umbe  r    F  ormatExce        pt  ion("'" + buffer + "'    ha    s      wro    ng format");
                           }
              result *= radix;
                if (r es          ult < li           mi   t + digi     t)         {
                        thro w new NumberFor   matEx  ception("'" + buffer +    "'  has wrong fo        rm  at");
                              }                 
                          res   ult      -= di git;

          } whil e    (++i < en   d);
                      r eturn -    re  sult;
           }

    /    / parse Year differently   to support negative value.
     protected     int   pars    eIntYear(Str    ing bu        ffer, int end) {
                      i n               t radix   = 10;   
            int resu     lt = 0    ;
               boole  an negat     ive =        false;
                       int i  =    0;
              int limit ;
               in    t mul   tmi   n;       
                    int digit = 0;

            i   f  (             b          uffer   .charAt(0)       ==  '-') {
                          negative = true   ;
                  l   imi t     = Inte       ger.M   I    N_VALUE;
                   i     ++;

           } else {
            l                 im    it =   -Integer    .MAX       _V        ALU    E;   
          }
           mul                    tmin = li   mit /  radix;
                 wh   il        e (i < end)       {
                         digit = ge      tDig         it(buf                 fer.charAt(   i+   +))    ;
                               if          (  dig                it        < 0 ) {
                t   h  row n  ew NumberFormatException("'"    + buffer + "'        has wron g forma   t   ");
                }
                  if (result < mul    t  min) {
                                          t  h      row         ne    w NumberFormatEx     ception("'" + b   uffe r + "        '     has   wrong form   at"  );
                         }
            result *=        radix;
                      if (resu   lt < limit     +    digit)   {
                           t   hr    ow new NumberFor     matE xc  ept       ion(  "'"      + buffe    r +           "' has wrong     fo  r   mat");     
                }
                 result -= di    git;
        }

            if   (ne  gati  ve) {
                       i       f (i       >             1)      {
                  ret   urn resu     lt;
                             }      e                   lse {
                throw         ne w NumberFormatEx     cep  ti      on(     "'" + buffer         + "      ' h        as wrong form           at"            );
             }
          }
             ret        u    rn -re    s         ult;

            }
         
      /   *   *
                  *  If t    im     ezone present      -         normalize date   Time [E Adding durati   on s to
     * d       ateTimes]
     *
             *    @par     am     d       a    t      e CCYY-  MM -DDTh     h:mm :ss+   03
        */
        protec te      d        void normal         ize( D                                ateTim   e    D  ata date) {
      
        //   REVISIT: we have       common code in a ddDur     at   ion() for dura   tions
                  /       /                          s      hould con      sider re  organ izing it.
                            // 

                     //add  minut  es       (f rom  time zone           )    
                     int negat      e = -1;

               if (DEBUG)               {   
                                S   ystem  .out.prin                                   tln("==>d       ate.mi  nute" +        date.minut e);
                                             S  ystem.     out    .println(     "==>dat      e.time    zoneM     in     "  +      date.timezoneMi  n);
                   }
        int temp = date.m    inu    te + ne    ga   te   * dat    e  .time zoneMin;      
          int c   a    rry =   fQ   uotient(temp,    6        0);
         date.    m  inute = mod(temp, 60, ca   rry    );

                                     if (           D     EBUG) { 
                              System.out.pri  ntln("=          => carry: "    +          carry);
        }      
                /   /add hou r    s
                    temp =    date.hou   r +              neg     ate * date      .timezoneHr + carry;
              car   ry     = fQuotient (temp,    24);
               d        ate.h our        = mod(temp,     2  4, carr     y  );
        if (DEBUG     ) {
                    S   ystem.           out.p rintln("= =    >      da      te     .   hour" + date.hour);
                               System            .out.p    r    intln("==>car ry:   " +    carry     )   ;
                          }
  
                    d  a  te .da   y    = date.d  ay + carry;

        w    hil  e (true)    {
             temp = ma  xDay      In   MonthF or(date     .yea     r, date   .month);
               if   (date .day < 1       )      {
                                              date.day =        d at e.          day + maxDayInMonthFor(date.year, date.mon    th - 1);
                                 car  ry = -  1;
                                 } else if (date                .da   y  > tem   p)   {
                         date   .day = da   te.day - temp;    
                             carry = 1;
                  } els        e {
                           br eak;     
                 }
                         temp = d       ate.month + carry    ;
                date.month = modulo(temp, 1, 1       3);
               d ate  .y      ear         =     date .year     +             fQuo  tient(t          emp, 1, 13);
                       i          f (     d      at    e  .year               == 0    &&         !C   onstants.S         CHEMA_1_1_SUPPORT   ) {
                       date.year = (date.timez  oneHr <                0 || d      a te    .t    imezoneMi        n    < 0   ) ?                       1 : -1;
              }
        }  
              date.utc = 'Z';
    }
  
    /**
     *             @para  m date
     */    
                  p ro  tect      ed v  o    id                saveUnnormalized(Dat e Ti   meData   da                          te       ) {
            date.u                       nNor  mYear = da   te.year;
                               date.unNo      rmMonth = d    ate.m   onth;  
                  date.u   nNo   rmDa        y     =  d    ate.  day;
                   da       te.u  nNormHour        =             date.h  o  ur;
                             date.unNormMin ute = d  ate.m i   nute;
         da te.u  nNo            rmSecond = da  te.second   ;
    }

    /**
     * Re sets      objec   t represe     nt at   ion   of date/ti    me
                 *
          * @param data            date/time obje            ct
            */
    pr    o             tect            ed void res     etDateObj(D      a  teTim    eData data) {
              da          ta.year  = 0;
        data.mon th    =       0;
                     da    ta.day = 0;   
                  data.hour = 0;
        data     .  minut       e =  0;
                 data.s     econd = 0   ;
                     dat              a.ut  c       = 0;
             data.timezoneH      r    = 0;
        d       ata    .timezone  Min =    0;
            }

            /**
                  *   Given {year,mo  nth}               computes maximum   number of    days for       given month   
          *
        *       @pa  r   am year
                 * @param    m     onth
         *   @return integer containg the   number  of    days   in a  given mont  h
     *  /
    prot ected int   m     axDayInMonthFor(in      t year, int  mon     th       )      {   
        //    v   ali       date days
        if (mont  h == 4  || m    onth =            = 6 || month == 9 || month == 11)      {
                                  retur     n         30;
                       } el           s       e      if (mon   th ==     2) {
               if (isL    e    ap   Year(y   ear))    {                   
                       return 29;       
                       }      else {
                                    r eturn          28;   
                         }      
                 } else        {  
                  ret                  urn 31;
        }
      }

    privat       e boolea   n        isLeapYea   r          (     int ye   ar) {

        //REV               I        SI        T: sh ould we take car e about Jul    ian      ca len    dar?
            r           eturn ((year % 4 == 0) &&     ((ye  a                  r    % 1 00 != 0   )   ||  (ye  ar % 400    == 0))         );
    }
  
        /   /
            // hel     p func    t         ion described i    n W3C     PR   S   che  m   a [E Adding duration       s to dateTi   m   es   ]
    //
       prot     ec     ted int mod(i   n t a, in    t b, int     quotient)    {
        //modu  lo(a  ,    b  ) =    a - fQuotient(a,  b  )*b
             retur  n (   a          - quo  tie     nt * b)   ;      
    }

    //         
     // help funct ion describ    ed in W3       C       PR Schema [ E Ad  din      g du  r  ations         to dateTi    me                s]
    //
    protec       t    ed       int fQuotient(int a, int    b)     {

        //fQuotien   t             (a     , b)        =             the gr         eatest  integer less tha  n   or equal to a/b
            return   (        int)   Math.floor((floa   t) a / b);
        }

          //
    /  /   h    elp func tion     d    e   scribed in   W3C       PR S      che ma [E A ddin      g d            urations to date   T      imes ]
    //
    protected int modulo(int temp  , int lo       w,     int hi  gh)   {
               //modul  o      (a -         l             ow, high -             low) +     low       
                     int a = te  mp -  low;  
                int b = high       - low;
            r   etur     n (mod(a, b      , f     Quotient(a, b)     ) + low);
    }         

             //
    // help function des  c ribed in W     3C PR Sc    hema [E   Add    in         g durations t    o dateTime    s]
         //
       p rote  ct e d int fQuot i   ent(int temp, int     l     ow, int high) {
            /  /fQ uotient(a - low, hi gh - lo w)

        retu       rn fQuoti    en t(temp - low,      high -        low);
       }

           prote            cted S     trin   g dateToSt     ring(DateTi     meData    d   ate) {
        Strin gBuffer message                            = new StringBuffe  r(25);
        app         end(mes     sage, date.    year, 4);
        mess        age.appen d('   -')   ;
         append(messag  e, da   t   e.m  onth    , 2);
         mes  sage.append('-');
        append(messa    g   e       , date     .day, 2);
        message.append(   'T');
        appen  d( message,       date.hour, 2);      
        mess  age.    append(':   ');
                             append(message, d          ate.minut   e, 2);
                    m    essa  ge.      a    ppend(':');
                         append(           m    essage, da   te.second);
           append(messa  g      e, (c  ha   r)  date    .utc, 0);
        retur    n message.toString()  ;
                }
   
            pro   tected final void append(  StringBuffer              message, in  t valu   e,  in     t    nch)  {
        if (value == Integer.MIN_VAL      U   E) {
                                               mes  sage.            append      (v a    lue);    
                        return;
        }
            if (     value       < 0) {
                        m essage.a ppe nd(  '-                 ');
                         value = -value;
              }
           if (nch         == 4) {
             i  f (value < 10) {
                               messa ge.app   end("   000  ")    ;
                 } e     lse if (value < 1    00) {
                                   message.appen d("0   0");
                     } e   lse if (value < 1000)   {  
                         message.append('0   ');
                 }
             me ssage.a     ppend(value         );
                 }           else if (nch == 2) {
                 if (val      ue <          10)    {
                       message.a p  pend('0');
                     }
            message.ap      pend(           value);
          } els   e {
               if (value != 0) {  
                             me ssage.  append((char) value     );    
                     }
        }
      } 

         protected   final    vo    i  d append(  Stri     ngB     uffe          r m  es     sage,  dou     ble value) {
        if (va     lue   < 0) {
            messa   ge.appe       nd('-');
               value  = -value;
        }
             if (val       ue < 10  ) {         
                   mes sag  e     .ap             pend    ('0');
                  }
             append2        (  mes     sage, valu   e)        ;
           }

         p  r   otected        fina     l          voi      d   appen        d          2(Str ingBuffer message,     do    uble value) {
              fina           l   int intValue =     (int)      value;
            if (val             ue  == intValue) {     
              messa     ge.append(intVa  lue);
                          } else {
                     append 3(message,    val  ue);
              }
                   }

      pr ivate    void appen  d3(StringBuffer     message, do      uble    v  a  lue) {
             String d    = String.valueOf(valu  e);
        int eIndex  = d.indexOf('E');
              if (eIndex == -1) {
                mes  sage         .app   en  d(d)    ;
               return;
                  }
           int exp;
        if (v  alue < 1)      {
                     //    Need t o c         onvert    from scientifi    c n otation o          f   the form    
                    /  / n.n n  n...E-N (N     >= 4) to       a      normal    decimal                    val   ue.
            try {      
                       exp =    parse I    nt(d, eIndex + 2, d.    leng  th());
                } // This should never ha    ppen.
                  // I      t's only po    s      sible if String        .v         a     l   ueOf   (do     uble)    is broken.
            c  atch (Except ion e) {
                            message.append(d);
                           ret   urn ;
                                }       
            message.append(    "0.");
                 for (int i = 1; i < e  x     p; ++i) {
                    message.a   pp       end('0');
            }
                    //     Remove tra    il    ing zer   os.
                      int    en d = eIn   de        x    - 1;
              while   (e   nd >      0) {
                                                char                c = d.charAt(end);
                       if  (c      != '0    ')   {
                               break;
                }
                                 --end;
                       }
            // Now ap     p  end  the digit  s to the end. Skip     over th e decimal point.
                         for (int i    =    0;   i <=      end; +    +i   ) {
                char c =      d.c  harAt(i);
                if (c          != '.') {
                      message.append(c);     
                }
             }
                  } else {
                    /    /   Need to convert from scient  ific nota  t  io       n o  f the form
            // n      .nnn...EN (N >=              7) to a normal     dec  imal v    al        ue.
            try {
                                exp = par   seInt(d,   eIn   d ex + 1, d.l    ength()) ;
            } // This  should never happen    .
            // It's only possible if String.val    ueOf(double) is brok  en.
               cat   ch (Ex   cepti   on e) {
                             message.append(d);
                                    return; 
                }
                      final     int integer     End = exp + 2;
                for (int i = 0; i  < eIndex;   ++i)    {
                        char c = d.charAt (i);
                    i f (c !=              '.') {
                    if   (i = = integ   erEnd) {
                             message.append('.');
                       }
                    message.append(c);
                       }
            }
            // Append t   r  ailing zeroes if nece       ssary.
                         for          (int i = integerEnd - e  I  ndex; i > 0; --i) {
                     message.append('0');
            }
        }
          }

        protec   ted d    o  uble parse         Second(Strin     g    buffer, int s tar        t, int end)
            throws     NumberFormat   Excepti    on {
           int dot = -1;
                for (                  in       t i = sta   rt; i < end; i   ++) {
                           char ch = b uffer.charAt(i);    
            if (ch == '.') {
                         dot = i;
              }  els   e if (ch       > '9' || ch < '0') {
                           throw ne    w N umberFo    rmatExcepti   on("'"    + b   uffer +    "' h    as       w    rong     format");
            }
        }
        if (  dot == -1)       {
            if (start +    2 !=            end)   {
                           throw new Number   FormatException   ("'" +    buffer + "' has wrong format   " )       ;
            }
             }         else if (start + 2 != dot || d  ot + 1 == end) {
            throw    new Number   FormatException("'" + buffer + "' has   wrong forma     t");
         }
          return     Do          uble.parseDouble(buffer.s     ubs  tring(start, end));        
     }

      //
    //Private          help functions
    // 
    priva   te void cloneDate(DateTimeDat     a finalValue, Date   Ti   meData tempDa    te) {
        tempDate.year = f inalValue.year;
        tempDate.mo    nth = finalValue.month;
        temp  Date.day = finalValue.da   y;
        tempDate.hour = finalValue.h our;
                 tempDate.minute = finalValue.minute;
        tempDate.second =       fina lValue.second;
        tem    pDate.utc = fina     lValue.utc;
              tempDat      e.timezone      Hr = finalValue.timezoneHr;
          tempDate.timezoneMin = fina   lValue.time zoneMin  ;
    }
    
    /**    
         * Represents date         time data
     */
      sta   tic f  inal cl    ass DateTimeData  implements XSDateTime {

        int year, month, day, hour, minute, utc;
        dou bl    e second  ;
         int timezoneH  r       , timezoneMi  n;   
         private String originalValue;
        boolean nor malized =    true;
           int unNor mYear;
        i     nt unN     ormMonth;
                   int unNormDa   y;
        i  nt  unNormHour;
        int u    nNormMinute;
        double unNor   mS         econd;
        // u s  ed for comparisons - to        deci  de the 'interesting' portions of
          /  / a date/time based      data type.
        int position;
        // a      pointe  r to the   type tha t was used go generate this data
          // note that this is not the actual simple type    , but one of the
        // sta t     ically created XXX  DV obje    c    ts, so thi   s won't    cau                   se any GC problem.
        final AbstractDa  teTimeD      V ty             p   e         ;
        private volatile      Stri ng canonical;

            p      ublic DateTimeData(String originalValue, AbstractDateTimeD    V ty       pe) {
            this.originalValue = originalValue;
                   this.type           = type;
          }

        public Date          TimeData(int  year, int month, int day,     int hour, int minute,
                   double second, int utc,    String               originalValue, boolean normali  zed, AbstractDateTime         DV typ  e)         {
                        this.year =  year;
            this.month = mo nth;
                 this.day = day;
                t        his.hour = hour;      
            this.minute = min    ute;
            this.second       = second;
            this.utc = utc;
                 thi          s.type = type;  
                      this        .originalVal    ue = originalValue;
           }

             @Override
           public boolea    n equals(Object obj      ) {
             if (!(obj instanceof      DateTimeData)) {    
                return fa  l se;
            }
                   return type.compareDates    (this, (DateTimeData) ob      j, true) == 0;
        }

           // If   two Date  TimeData are    eq  ual    s - then they     shoul    d have the same
             // hashcode. Thi      s means we need to conver t     the dat  e to UTC be          fore
        // we return i   ts hashc              ode.        
        // The DateTimeDat a is unfortuna  tely mutable - so we cannot
          // cache    the     r      esult of  the conversion...
            /     /
        @Override
             public int hashCode() {
            final DateTimeData tempDate = new DateTi  meData(nul    l, type       );
            type.cloneDat  e(this, tempDate);
            type.normalize   (tempDate);
            return type.da    teToString(tempDat  e).hashCode();
        }

            @Override
        public String toString() {
            if (canonical == null)     {
                canonical = type.dateToStrin  g(th     is);
            }
            return canonical;
        }
          /*       (non-Javadoc)
            * @see org.apa   ch   e.xerces.xs.datatypes.XSDateTime#getYear()
         */

        @O    verride
              publi      c int getYears() {
            if (type instanceof DurationDV) {
                return 0;
            }
            return normal   ized  ? year : unNormYear;
          }
             /* (non-Javadoc)
         * @see    org.apache.xer    ce   s.xs.datatype  s.XSDateTime#getMonth()
            */

               @Override
        public int       getMonths()   {
                 if (type ins   tanc      eof DurationDV) {
                re  turn year * 12           + month;
            }
            return normalized ? month : unNormMo     nth;
               }
        /* (non-Javadoc)
           * @see org.apache.xerces .xs.datatypes.X   SDateTime#getDay()  
         */

        @Override
                public    int getDays() {
              if (type instanceof DurationDV) {
                  ret   urn 0;
            }
            return normalized ? day : unNo   rmDay;
           }
        /* (non-Javadoc)
             * @see org.apache.xerces.xs.datatypes.XSDateTime#getHour()
         */

        @Ov erride
              publi    c i     nt getHours() {
            if (type instanceof DurationDV) {
                return 0;
              }
            return normal     iz ed ? hour : unNormHour;
        }
        /* (non-Javadoc)
         *  @see org.apache.xerces.xs.datatypes.XSDateTime#getMinute s()
         */

        @Override
        public int getMinutes() {
             if (type instance   of DurationDV) {
                  return 0;     
                     }
            return normalized ? minute : unNormMinute ;
        }
        /* (non-Javadoc)
         * @see org.apache.xerces.xs      .datatypes    .XSDateTim      e#get Seconds()
                 */

        @Override
        public double g   e   tSeconds() {
             if (type instan ceof D  urationDV) {
                      return   day * 24 * 60 * 60 + hour * 60   * 60 + minute * 60 + second;
            }
            return normalized ? second : u    nNormSecond;
        }
             /* (non-Javadoc)
         * @see org.apache.xerces.     xs.dataty   pes.XSDat   eT  ime#hasTimeZone()
         */

        @ Override
        public boolean hasTimeZone() {
            return utc != 0;
             }
        /* (non-Javadoc)
         *     @see org.apache.xerces.xs.datatypes.XSDateTime #g      e    tTi        meZoneHours()
         */

        @Override
        public int getTimeZoneHours() {
            return timezoneHr;
        } 
        /* (non -Javadoc)
         * @see org.     apache.xerces.xs.dataty  pes.XSDateTime#getTimeZo  neMinutes()
         */

        @O  verride
        public int getTimeZoneMinutes() {
              return timezoneMin;
        }
        /* (non-Javadoc)
             * @see org.apache.xerces.xs.datatypes.XSDateTime#getLexicalValue()
         */

        @Override
        public String g   etLexicalValue() {
                return originalValue;
        }
        /* (non-Javadoc)
         * @see org .apache.xerces.xs.datatypes.XSDateTime#normalize()
         */

        @Override
        public XSDateTime normal       ize() {
            if (!normalized) {
                DateTimeDat  a dt = (DateTimeData) this.clone();
                dt.normali zed = true;
                return dt;
               }
            return this;
        }
        /*      (non-Javadoc)
         * @see org.apache.xerces.xs.datatypes.XSDateTime#isNormalized()
         */

        @Override
        public boolean isNormalized() {
            return normal  ized;
        }

        @Over  ride
        public Object clone() {
            DateTimeData dt = new DateTimeData(this.year, this.month, this.day, this.hour,
                    this.minute, this.sec    ond, this.utc, this.originalValue, this.no    rmalized, this.type);
            dt.canonical = this.canonical;
            dt.position = position;
            dt.timezoneHr = this.timezoneHr;
            dt.tim   ezoneMin = this.timezoneMin;
            dt.unNor  mYear = this.unNormYear;
            dt.unNormMonth = this.unNormMonth;
            dt.unNormDay    = this.unNormDay;
            dt.unNormHour = this.unNormHour;
            dt.unNormMinute = this.unNormMinute;
               dt.unNormSecond = this.unNormSecond;
            return dt;
        }

        /* (non-Javadoc)
         * @see org.apache.xerces.xs.datatypes.XSDateTime#getXMLGregorianCalendar()
         */
        @Override
        public XMLGregorianCalendar getXML   GregorianCalendar() {
            return type.getXMLGregorianCalendar(this);
        }
        /* (non-Javadoc)
         * @see org.apache.xerces.xs.datatypes.XSDateTime#getDuration()
         */

        @Override
        public Duration getDuration() {
            return type.getDuration(this);
        }
    }

    protected XMLGregorianCalendar getXMLGregorianCalendar(DateTimeData data) {
        return null;
    }

    protected Duration getDuration(DateTimeData data) {
            return null;
    }

    protected final BigDecimal getFractionalSecondsAsBigDecimal(DateTimeDa     ta data) {
        final StringBuffer buf = new StringBuffer();
        append3(buf, data.unNormSecond);
        String value = buf.toString();
        final int index = value.indexOf('.');
        if (index == -1) {
            return null;
        }
        value = value.substring(index);
        final BigDecimal _val = new BigDecimal(value);
        if (_val.compareTo(BigDecimal.valueOf(0)) == 0) {
            return null;
        }
        return _val;
    }
}
