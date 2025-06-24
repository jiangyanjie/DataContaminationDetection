/*
    * $Id:$
      * 
 * T     his software       is pr  ovided 'a      s-is',        without any e  xpress or implied
 *     w arranty. In         no      event w     ill the authors be held l  iable for any   damages  
 * arising fro  m t      he     use    of this software  .
 * 
 * Permission is grante    d to   anyone   to use thi    s software for a    ny purpose,
 * includi    ng      commercial applica  tions, and   to alter it and    redistribute it
 *  freely, s     u     bject to the    f   ollowing res      tric   tions:
 *    
 *  1. The orig   in   of this software must not be mis  represented;  y     ou     must   not
     *     claim that you wr  ote the    original software.                           If you use                  t  his software
     *          in    a product, an a   cknowledgment in the pr  oduc   t documen   tation wou   ld be         
 *     ap preciated but is not requi red.
 * 
     *  2. Altered source versions must be pla   i nly marked    as such, and    must     not be
 *     misrepresented as being    the origin  al  software.
 *          
 *   3. This notice may   not be remo     ved or altered from any sourc     e
 *     distribution.
 */
package oms3.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamRe    ader;
import java.io.PrintWriter;
      import java.io.Reader;
import j   ava.io.StringReader;
import     java.lang.reflect.Arra   y;
i   mport java.net.URL;
import java.t  ext.DateFormat;
import java.text.ParseException;
import ja        va.text.SimpleDateFormat;
import java.util.ArrayLi       st;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Li     nkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.   util   .rege    x.Matc    her;
import java.util.regex.Pattern;

imp ort jav   ax.swing.event.   Tabl    eModelListener;
import javax.swing.   tab    le.Abstract        TableModel;
imp          ort javax  .swing.table.  Tab            leMod  el;

import oms3.Conversions;

/** Data Inp ut/Ou    tp    ut ma   na   g ement.
 *
 * @    author        Olaf Da    vid
      */
publi   c class         Da    taIO { 

    priv  ate stat  ic fin a    l String     P = "@";
    public static    final     String TABLE =      P + "T"   ;
    p   ublic static final String        HEADER = P + "H";   
    public static final String PROPERTIE       S   = P + "S";  
       public     stat ic final Stri  ng PROPERTY = P + "       P";
      public      static      final Strin  g    TABLE1 = P +  "Table";
    public static final Strin  g HEADER1 =       P        + "Hea der";
    public static final    String PROPERTIES1  = P + "   Properties";
    public       static final String PROPERTY1      = P +   "    Property";
    / /
    //
    public s   tatic fi nal Stri     ng       CSPROPERTIES_EXT = "csp";
    public stati           c fi           nal String CST  ABLE_EXT   = "cst";
    //
        pr    ivate  sta    tic       final Strin     g RO OT_ANN = "___root___";
    priv    at       e stati c final String      COMMENT = "#";
        pri vate static final Map<String, String> NOINFO = Collections     .unmo       difiableMa       p  (new    HashMap<S  tring, S  tri   ng    >());    
    priv   a  te static final     Pa  tter n    v   arPattern = Pattern.c   ompi  le("\\$    \\{([^$}]+)\\}");
    /*       some static he    lpers, might have    to go somewhere e       lse */
    private sta       tic final String ISO8   601 = "yyyy-     M            M-   dd   'T'hh:mm:      ss";
         //
    // all meta data keys
    publi     c static final S    tring KEY_CON  VERTED_    FROM = "conve  rted_from";
                   public st     atic final String DATE_FORMAT = "d     ate_format";
    public             static fin al Strin g    DATE_START =    "date_sta     rt";
    public stat    i  c final String     DATE_END =   "date_end";
       public s     tatic final String KEY_CREATED_AT = "created_at";
    p   ublic static    final      String KEY_CREATE  D_BY = "created_by";
    public static         fi nal String KEY_ UNIT = "    unit";
    public static final St   r ing KEY_FORMAT  = "format";
           p ublic static  final Str         ing KEY_   TYPE = "type";
    public s          tatic f inal Strin g KEY_NAME = "name";
        pub     lic sta    tic final String KEY_MISSING_V A  L = "mi      ss         ing_value";  
    public static final St ring KEY_FC_START =    "forecast_start";
    public static final String KEY_FC_DAYS     =    "fo  recas  t_days";
                publ   ic sta   tic        fin  al         Strin   g   KEY_  HIST_YEAR = "historical_year           ";
    publ      ic static final St    ring KE     Y_DI GES  T = "digest";
    public st   atic fina    l Strin g VAL_DA TE = "Date";
          //TimeStep Enu   me     r ations
    public st    at    ic f inal int DAILY      = 0             ;
    public static final int MEAN_MONTHLY = 1;
    public stati     c   fin    al     int MONTHLY_MEA N = 2;
    publ      ic                static final in  t ANNUAL_MEAN = 3;
    public sta   tic final int PERIOD_MEAN = 4;
    publ   ic static final    int  PERIOD  _MEDIAN = 5;
             public     static final int PERIOD_STANDARD_DEV       I  ATION =     6;
    public static final int PER    IOD_M   IN = 7;
      public st  atic fina    l int PERIOD_MAX = 8;
           
    public static doub       le[] getColumnDoubleValuesInterval(Date     start, Date    end, CSTable t, S     t   ring c       olum     nName, int timeSte   p) {
           //  Uses calendar y             ear         with a ll mont     h       s as defau  lt.
        boo     lean[]              periodMask    =    {true,tr      ue, true, true, true, true, tru     e,    true,   t    rue, t rue, tr    ue, true};
                 boolean    [] subDi                         videMask=null;
        return ge tColumnDo      ubleValu e      sInterval(start , e  nd, t,    colu     mnName, timeStep, 0, periodMask     , subDivide    Mask  );
         }
       
       p    ublic static   double[]     getColum   nDoubleValue   sIn       terval(Date start, Da          te end, CSTable t       , Str  ing c olumnName,                     int timeSt   ep            , boolean[]    periodMask)       {
              // Us            e     s c    a     lendar     year      with   a    ll months as de        faul t.
            boo   l       ea     n[]       sub     DivideMask =nul   l;
            retu   rn ge          tColumnDo         u                      bleValuesInt erval(      sta    rt, end, t         ,   columnNam  e      ,      timeStep ,    0, periodMask, subDi  videM     as   k)       ;
    } 
            
                    
         public static do     uble[]     ge   tColumnDoubleV    alue   sIn     ter          va     l(  Date start    , Date end,                  C   STable t,       String      col    umnName  , int    time      St  e  p,                i       nt s    tartMonth, 
                            bo         olean[] perio  dMas              k,    bo   ol       ea  n [] subDivid  eMask)       {

             int col  = columnIn        dex(t, columnNa                   me);
                         if (co   l    == -1) {
                      throw new   IllegalA rgum    en          tE               xce     ption   (              "No   such   c      olu     mn:      " + colum nName  )    ;
          }

        if (p     eriod  Mask             ! =          null)  {
                         boolean   pmCh eck = f      a   l   se     ;    
                                    for (int i =0; i<pe riodMas    k.length; i++) {        
                        pmCheck = pmChec    k | period             Mask[i];
                                }
                           if    (pmC  h    ec         k == fals                      e)    {
                                           thr  o  w n  ew Il legalArg     u     me      n    tE    xception(                    "PeriodMask            (i.e. Mask of m        o       nths t       o             include   in       data              anal ysis) is      all f     al        se,       so      no data would be u  sed         .");
                      }
             }
         Date    Format        fmt =     lookupD          at            eFor   m    at(               t, 1);
                    boolea    n  h         as   Year =        DateForm    at     HasY          ea        r   (t,1)   ;

   
             
                                    swit      ch   (ti          me      St  e        p   ) {
                           /    *
                                  case          DAILY:
                                {
                                       L     ist<Double> l         = new Ar   r  ayLis     t<Doub          le>();
                                        do     uble  sum = 0       ; 
                                         fo          r     (   String[] row : t. rows()) {
                                                                  try {
                                                                      Date d   = fmt.parse(ro  w[1]);           
                                              if ((d.       eq            uals(st   ar                           t)                          ||       d.a  fter   (st           art))      &   & (     d.e quals(  end) || d.bef o                                              re(end)    ))        {
                                                                                             int mon       th = d            .      getMont        h  ();
                                                int year = d.g  e   tYear(      )         ;
                                                          int      day     =       d.ge tDate    ()          ;
                                                                     double     data        =    D          o    ub                           le           .parse   Do          ubl     e(  row     [col]);
                                                                            
                                                     bo      olean  p eriod  Val  id =     (       peri             odM      ask[mon    th    ]    ==       t    ru  e           )  ;       
                                               
                                                                                     if (p  er  io          dVa  lid  ) {
                                                               l      .add(                  da  ta  );             
                                                          }
                                                                                }
                          } catch              (       P  ar    seE  xc       epti      on               ex ) {
                                                               t   h     row               new Runtime    Except   i  on     (ex);
                           }
  
                             }     
                              
                               // Copy   th    e         L       ist   to the outpu  t       a  r       r ay.
                      do    uble   [             ] arr =     new         doub       l    e[     l.s  ize()];   
                                                                    f   or (i        nt i =                     0;   i < arr.le   n                             gth   ; i++)  {  
                                 arr         [i]    = l.g           et(i);
                       }

                                 return a       rr;
                                   //     bre  ak;   
               }
                              * /
                                         c  ase       DAILY:
                  c  ase MO                 NTHLY  _MEA   N:     
                  {

                          int previous       Month = -1;     
                                                int pr              eviousY   ea   r = -1;
                    int prev     iousDay     = -1;  
                                                bo       o  lean previo   usVal      id           = fal  se;
    
                           boolean useY    ear   =  (     time    Step =   = DAILY        ) || (   ti  meStep ==      M       ON    THL     Y       _MEAN) ||   (t     i    me              Step    =     =    ANNUAL_   MEAN);  
                                               bo        o             lean                          u            se Month = (t      i   meSt     ep =   = DAILY)       |  | (time    Step ==   MON    THLY_MEAN)       ;
                           boo  lean useDay        =              (t     i m              eSte   p = = DA         IL Y             );

                                                       L   ist<D     o                uble>           l =     n  ew Ar rayLi                st<Doubl       e>();
                                            double sum = 0     ; 
                      int count    = 0;
                                        int sd   i n       dx    = 0;           
       
                                   for   (String                                   [] r  ow : t        .rows())   {     
                                                      try                              {
                                                         Date  d =   fmt.par      se      (row[1]);
                                            i   f   ((      d. eq  uals( start) || d.af                      ter(star  t))  &&              (d.  equ         a    ls(e      nd)      || d.before(     end))) {
                                                                                     int month =     d.getMo  nth()  ;
                                      in  t   year =   d.   ge   tY   ea   r(   );
                                             int day = d.get         Date       ();
                                                           doubl                                     e data = Doub     l              e.parseDouble(row[  col]);
                 
                                                                             b  oo  l     ean newYear         = (ye a  r != previ ous         Year);
                                                                                                               
                                                                             boolean ne  wEn  t   ry          =    (previous      Valid &&       
                                                                ((useYe    ar &                    &       newY    e ar)     ||
                                                                                                                 (useM   onth   &   & (month != p    r evio       usMon th)  ) ||
                                                                                   (useD ay && (day !    = pre     viousDa   y))
                                                                                     )  
                                                                );                                      
     
                                                         if (ne wEntr       y &&         (c            ount!    =  0                      )) {
                                                                                                               l.add(s     um   / co      un   t)      ;
                                                             sum = 0;
                                                         cou  n   t            = 0;
                                          }

                                                                     b   oolean               pe    r     iodValid         =      (per   iodMas            k== null) || ( p                       eriodMask[m  o   nth   ] ==                               t   rue);   
                                                                                           boolean  subDivideVali d = (sub                  D  ivide            Ma   sk               == null  ) || sub            D           ivideMask[   sdindx++];
                                                           if (   p       er   iodV                al id &           &       s     ubD  ivi       deV        alid) {                                                                                    
                                                                                     sum    += data;      
                                                                     count++;
                                                                               }
                                                                                      
                                                          pre v iousValid  = t    rue  ;
                                                                 pre          viousDay = day;
                                                 prev i      ousMon  th   =          mo     nth;
                                                pre        viousYe      ar = yea r;
                                              }
                                            } catch (Pa      rse Exc  ep  tio  n ex) {
                                    thr   ow new   RuntimeException(ex);
                                                  }   

                           }
                                   if    (cou nt!=0)      {l    .add  (sum / co     un  t);   }         // add th   e fi       nal entry    which    wasn      't y        et a          dd  e d
                                        //       s  ince   it neve   r     hit a newEnt   ry.
     
                             // Co     p  y the Li  st to   the ou tput array        .
                                                       d      ouble[]      arr =          new     doubl         e       [l.size( )  ];
                                         fo   r   (int i =  0; i             <   a       rr.  l             e                                ngt   h;  i++   )               {     
                                               arr[i] =   l.get(i  );
                                        }

                             retu    r     n arr ;    
                    // break;    
             }
         
              case MEAN  _M ONT    H      LY: {
                                  
                                                        doubl     e[]   arr   = new d                   ou            ble[    1    2]; // 1              p          er month

                                              in     t[] count     =                       new int[12];
                                           i           nt sd                  indx=0;  
                  
                         for             (int     i       =   0; i               < 1   2; i++) {
                                           arr[i] =        0  ; //    init    ialize          data to    0
                          c   o  unt[i]          = 0;       
                           }

                                     // C          r   eate month i    f we don't hav  e  a d ate       . Ass  umes      1 mo              nt      h/r     ow.
                          fo   r (St      ring[] row : t.ro    ws()         ) { 
                                                               try    {
                                               Date d =     fmt .par            se(row[1        ]);
                                
                                       if     (!has  Y     ea  r    || ((d.equals(sta     rt            ) ||    d       .aft    er(sta        r     t)) &&        (   d.equals( end)   ||   d.befo  re  (end)  ))   ) {
                                        int month = d.getMo   nth()    ;
                                                                    if             (mont  h > 11) {
                                                 throw n         ew R     u     ntim   eExc         ept     ion(      "M      ont  h  > 11 = " + mont    h);
                                                    }
                                      d                                 ouble da    t     a = Doub        le.p    arseD    oubl       e    (r                          ow[col            ]);
                                                        
                                                            boolean  p      er   iodV    ali   d = (period    Ma     sk==null) ||     (pe   riodMask[    m         o n  th   ] = = true)                                            ;
                                                    b       o     ole an s        u       bDivideValid =  (subDivideMask == n  u ll)        ||       subDivideM ask[s     dindx++];
                                                        if    (      periodValid          && subD      ivideVali d) {
                                                            a rr[mo     nth   ]        = arr         [mo nth]       +  data   ;
                                                                     c       ount[month] = cou      nt[month] + 1;
                                                 }
                                            }
                          } catch (Pars    eEx    ception ex)           {
                                   throw ne  w Ru        ntim  e E   xce            ption(    e x)     ;
                                     } 
                                 }

                                                            for (int    i = 0; i       <    12;       i           ++) {
                                                            arr        [    i]    =     (c      ount[  i  ]  ==0) ? 0 : (   arr[i] /   count[i]);
                                           //S y s              tem.      out.println("Arr["+   i+"]   = " + arr[i           ]   );
                    }
                          
                            return a     rr;
                        // break     ;  
                                        }          
 
                                c        ase ANNUAL_MEAN:    
                           case     PERIOD_M          EAN:
                case P         ERIOD_MIN:   
            cas               e            PERIOD _MAX:
                   case                               PER        IOD_MEDIAN:
                      case PERIOD             _S   TANDAR              D_DE   VI      A                    TION    :     
                {
                               int previous       Mo         n   t      h = -1;
                                           int prev    io usYear = -1;
                 int prev   iousD    ay =   -1;
                           boole  an     previous  Valid = fals   e;

                       L    i   st<Double>               l = new    Array   Lis    t<Doub    le>();
                           Li  st<Dou bl  e> l      _medi    an            = new A       rra    yLis        t<Do   ub le>          ()         ;
                       
                                                                      do   uble  sum   = 0;
                     double s         q_sum = 0;
                        int   count =  0;
                          doub  le m       in = D   o           u  b     le.MAX_VALUE;
                double max =    D       oub      le           .MIN_               VA      LUE;   
                            int s                 dindx = 0;
                                 
                       f             or (String[]           row :   t  .rows())      {
                                         try {
                          D ate        d = fm       t.pa  rs e(row [1]);
                                                                           if ((d.equ als(st art) ||                   d.         aft   er( s      tart) ) &&         (d   .equals   (  end)     |  |      d.    be  for   e(en       d))      )                       {
                                        i       nt month   = d.    g  etM   on    th();
                                                                  int year     = d.getY     ear();
                                      int day =  d    .getDa    te();
                                                       do       uble                          da   t    a                          =     Do    uble  .p   arseD             ouble(r    o   w[  col]);         
                                           
                                       boolean                new    Y   e     ar =                          (m onth != p    revious    Month) && (m   o       nth ==                s      t          artMonth)      ;
                              boole     an new    Entry       =     (pr   eviousValid      &&  new   Year) ;          

                                                                  i                  f     (newEn                  try && co  un    t!   =0)             {                         
                                                  //    Ad                     d new da   t         a    t    o list   
                                                              l    .add   (selectResu       lt     (ti  meStep, sum                ,  sq_sum,  count, min, max,           l_m      edia  n));
                                                                  // Rese     t s    tats f      o  r n   ew ye     ar 
                                                                         s  um   =    0;  
                                                                  sq_sum = 0   ;
                                                           c   oun  t   = 0;        
                                                              m    in = Do     uble             .MAX_   VAL   UE;
                                              ma     x    = Double      .M IN_VA LUE;
                                                                    l_median.clea      r(); 
                                                           }

                                                boolean     pe      riodVa   lid = (periodM       ask==null) || (period    Mask    [mo      nth]       == tru   e);
                                              boolean su    b           Div      id eVal     i d = (s     ubDiv      ide   Mask == null) ||         s      ubDivid     eMa  sk[  sdindx  ++]  ;   
                                                     if (period    Valid   &&   subDivi  deV        al   id)     {
                                                  sum += data;     
                                                                 sq_su   m +  = (    data  * data)     ;
                                                           count ++;
                                                if    (data<   min) min = dat     a;
                                             if (data>  max)    ma    x = data;
                                     l_m ed       ian.add(da   ta);
                                           }
                                                
                                                                                 pre viousValid = true;
                                                 previousDay = d ay;
                                    pr   e        viousMonth = mont  h;
                                                                p  reviousYear    = year;
                                              }
                             } c   atch (ParseExcep    tion  ex           ) {
                                th ro   w ne  w R   u     n    tim    eException(ex);
                            }

                          }
                i  f (count !=0) {
                                   d    o               ubl   e    selVal =    selec    tR   es      ult(   time   St       e       p, sum, sq_sum, c o      u   nt, min, max, l   _m       edi  an);
                             l .   add(se    lVal);
                     }   //   add   t   he fina  l en  tr       y               whic  h wasn't   yet added
                    // since   it       never hit a  new      E            ntry  .
        
                                  // Co         py           the     List to   the   output      array.
                  double[] arr =     new dou    ble[l.      size()];
                     fo   r (i    nt i = 0; i      <    arr     .length; i++) {
                    a rr  [i]   = l.   g     et    (i   );
                            }

                re   turn arr;
                           // break ;  
                   }            
 
 
        default: {
                     thr     ow new IllegalAr    gum       en   tEx   ce    ption("t    imeStep " +      timeSte p + "n  ot supporte    d.");        
                    }
        }
       }

    
    p   rivate stat    ic do u       ble sele        ctResult (i  nt time     Step, double su   m, do      uble sq_sum  ,    int     count,  d    ouble  m                        in     , do     uble m      ax  ,  List<Double>   l) {   
                    if ((timeStep ==      ANNUA     L_MEAN) || (timeS       te  p            == PE        RIOD_MEAN)       )
                     return (cou     nt=  =0) ? 0 : (sum/count);
                                else   if (tim    eSt  ep     == PERI OD_MIN)         
                         return       m in;    
          else if ( timeSt      ep =  = PER  IOD  _MAX)    
               r    eturn max;
                 el     s   e       i    f   (timeStep ==      PERIOD_MEDIAN)    {
                          //Co   py to a   rray of dou         ble
                           int lSize    = l.s       i    ze();
                if (lSize == 0) {
                                    thr  ow    new  Runt  imeException(   "No         dat    a i      n fi  le matched the sp    eci          fied    peri            od ");
                        }   
                  do   ubl   e[] arr                 =                         new double[lSiz    e]    ;
                                     for (in t  i = 0; i < arr.le     ng  th; i++) {
                    arr[i]   =     l   .get(    i);
                               }

                       // Sort t  he Ar       ra     y  
                                    Arrays.    sort(arr);
                                     double      m   edi      an  ;               

                        //   Pull ou   t t                    h    e            Median          
                                      if (lSize % 2 ==   1    ) {
                         median = arr[(lSize       + 1)  /   2 - 1  ];
                                   }  else {
                             dou   ble lower   =           a   rr    [(l S   ize                    / 2 ) - 1];
                          double up  per =   arr[lSiz  e / 2         ];
                        me   dian = (lower         + u pper)     / 2.0  ;
                   }
                                    return median      ;
                }
                        els  e     if (timeSt  e     p == PERIOD_STA  ND  ARD_DEVIATION)         {
                           double    mea    n = (c              ount==     0) ? 0 : (sum      / count      );
                            double va     r i    an             ce = sq_sum   / count - (   mean * me       an);
                          double  st anda rd   Dev    iation   = Math.sqrt(  vari     ance);
                               return standa    rdDeviation;
         }    
                         e            ls   e
                          thr    ow new RuntimeException("TimeStep " + tim                e    S    tep +           "         not supported h  er       e.   ");
                                                              
            }     
    
   
           
        
      
         publ  ic  static Sim    pl    eDate    F  o       rm            a t looku       pDateFormat(CS   T   a    ble tab   le,   int co  l) {
           if (    co             l < 0 |           | col     > t             able.getC  ol    umnCou   nt())     {
            th   row ne   w Illega lArg  umentE  xcept   ion("invalid column:       "      + col);
          }  
            St    ri ng format   = table.ge  tColumnI    nfo(col).get(K  EY_FO RMAT);
             if (   format == null)    {
                         forma    t = tabl      e.getInfo().get(      DAT  E_    FORMAT);
        }  
                    if (for  m       at == null)      {
                   format   =      Conv    ersions.     I     SO().toPatte  rn();
                   }
             retur      n new Simple     D   ateForm            at(format);
       }

     pu      bl   ic  stati        c bool       e   an   Date    For  matHa sY       ear(CSTable table, int col)          {    
                      if (col   < 0 || col >           table    .get    C          olumnCou    nt())   {
                  t        h    row      n    ew Ille    galArgu        men     tEx        ception("inva lid colum n: " + c    ol);  
        }
        St    ring        f            o     rmat = table.       getColum     nIn    fo(col).get(       KEY_F           OR MAT)     ;
        if (format ==    null    ) {
                 form       at = t         able.getInfo(  ).get  (DATE  _FORMAT);
            }
        if (forma  t ==     nu    ll) {
                 f ormat =     Co   nversions.ISO().toPatt    ern();
               }
        retu     rn (              format.c     o       ntains    ("YY") ||   format.contains("y   y"));
    }
     
       public  stati    c         in t f    indRowB       yDate   (Date    date    , int dateColumn             ,   CST      able table ) {
              Str   ing type  = ta ble.getC olumnInfo(   d   a   t  eColumn)  .get(KEY    _TYPE)  ;
              i   f ((ty      pe == n     ull) || !typ e.equalsIgno re      Ca            se(VAL_DATE)) {
               throw ne   w IllegalArgumen tEx  ce   ption()    ;
                     }        

               DateFormat fmt = lo             okupDateForm    at(table, dat eColumn);

          in  t        ro       wNo = 0;
                      f or (String[   ]  ro       w : table.row      s(      ))            {
                     tr        y  {
                                                                       Da   te d = fmt     .parse(      row[dateC   olu mn  ]);
                        if (d.e    quals(date))   {
                                                       retu rn ro wNo;
                                     }
                                 ro    wN  o++;
                }       catch (P    ar     s         eException ex      )    {
                                      thr ow new Run  timeExc   e   ption(ex);
                }
        }
                               throw n ew IllegalAr    gum               entExcep  ti      on(date.toString());
          }

      p        ubli    c static  CST       ab       le synthESPInput(CSTable    table, Date in                   iStart, Da   te iniEnd,      i  nt fc    D      a     ys, in      t  year ) {

               int dateC   olumn     = 1;

        DateF    ormat hfmt =    lookupDateFormat(     t  able          ,         dateColumn);        

                 // Fore             cast      sta   r          t = end of initi  al zation + 1 day
                          Cale n       dar f   cStartC   al = new Gr egorianCalendar       ();
                  fcS   t       art  Cal .se     tTime(i  niE  nd);
               fc StartC     al.add(Ca    len    da                  r.D    A    T  E   ,      1);
           Date fcStart = fcS         tartCal.          ge      tTim   e();

          // get th e ini   t ializ  ation perio        d  
                Memor  yTable t = new MemoryTable(    table);    
                           int iniS  tart Ro     w =      fi   ndRowByDa    te(iniS    tart, dateColumn,   t);
                   int iniEn      dR ow     = f indRowByD   ate  (iniEnd, dateCo lumn, t );
          List<      S              tr    ing[]> iniRo  ws = t.getRo             ws(in   iStartRow, ini End       R ow);

            // set                   the his    t      or        ical dat      e     to the f     or    cast   dat    e, but use the
                      // h      istorical    ye   ar.
        Calend  ar             his   t  St   art    =    ne                w Gr    egor    ianCa lendar   ()           ;
            h     ist       St art.    se      t  Time(fcSta          rt);   
              histS   tart.s             e  t(C            ale           ndar    .YEA   R, yea  r);      
          
           // get the histor ical   d           ata     
           int       his   tStartRow    =     findRowByDate(histStart.      get T i      me(),    da     te  C   olumn,   t);
               in      t histEndRow = histStartRow + (fcDay        s -         1);
        List<St r           ing[]>  histRow         s = t.getRows(       histStar    tRo  w, his tEndRow);
   
                      /  /             c reate       th         e     new Table.
               MemoryTable es      pTable = new MemoryTable    (tabl               e);
                es pTable.      g  etInfo().      put  (DATE_                 START, hfmt.format(in        iStar          t));
             espT    able.ge        tIn   fo   (    ).   put(KEY_      FC_ST  ART, hfmt.format(fc Start));
             espTable.  getInfo      ().    pu    t(KEY_FC_ DAYS, In               teg     er.toS      tri            ng(fcDays         ))   ;
               espTable.ge   tI nfo()   .p   ut(KEY _HIST_  YEAR, In  teger.toString(       year));
              espT     able.c    learRow       s();
          espTable   .addRows(i    niRo ws);
        esp    Table    .addRows(    histRows);

        // historical d          ate    -> forecas    t da te.
                   Cal    en  da    r              fcCurren    t = new GregorianC      alen     dar(  );
                                fc     Curre   nt          .   setTime(fcS                       t  art);

                    List<St   ring                      []>       esp                Rows =         es  p        Table. getRows();
              in     t star       t              = iniRow s.     s         ize();       
          for   (i  nt i =         s  tart; i <=     start + (  fcDays - 1); i++) {
                   espRows.get(i    )[1     ] =  hfmt.fo        rmat(fc   Curr   ent               .getTime())     ;
            fc   Cu  rrent   .a     dd(Calendar.DA TE, 1);
           }
           fc  Current.ad               d     (                    Calen dar.D    ATE, -1)        ;
            e    s        pTable.getInfo()      .p  ut(DATE_END, hfmt.fo  rmat(fcCurren   t.g   etTime()));

         return e   spTable;
        }

    /    **     Get   a slice of r     o   ws o     ut of      the            tabl               e matching the tim    e wi    nd     ow
                     *
     *    @p    aram t        able
     * @para m ti      meCol
           * @para  m start       
     * @param end
       * @ret   ur  n    th         e first and last ro w t   hat ma   tches the tim    e window start-     >end    
     */
    public    stat  ic int[] sliceByTime(CSTa         bl                 e table,   int      timeCol,   Da         te start,   D   a  te       end) { 
              if     (e    n                d.b    efor   e(    star      t)) {
              throw new Ill    e         gal     Argumen       tException("end  < st   art"); 
          }
          if (timeCol < 0)     {
                     th   row               n ew Illegal    Arg   umentExcep           tion("tim      eCol :" + timeCol);
                     }
               i  n    t s = -1;    
              int e    = -1;  
        int i   = -1;
                   f   or (St   rin  g[] col : table.        ro        ws  (        ))   {
                   i++;   
                Date                       d = Conversi     ons.c                o   n  vert(col[time   Co  l]   , Da                   te.class);            
            i             f (  s ==         -1 && (s  tart.    be   fo r       e(d) || start  .e  qu         als(d))) {
                           s = i;
                                 }
                     i             f (   e      == -1 && (      end.b             efore(d) |                | end.equ      als( d   )))      {
                                        e =     i          ;
                                                 brea   k;
            }
         }
                                re   tu    rn n ew int[]           {s, e           };
    }
   
      /** C  rea te a r/o     da             ta tablemode    l
       * 
     * @param src
      * @return   a tab  le   model to th                   e CST able
     */
            p  u                          bli    c static               T     a   bleModel crea   teTableM    odel(fina       l    CST    able    src) {        
            final      List<Stri      ng []>    rows = new     ArrayList<String     [   ]>();
         for      (String[]   row :      src.rows()) {
                     rows.   add   (r          ow        );
               }

           return ne w Table   M   odel() {

              @Ov    err   ide
                 p    ub    lic int get ColumnCount(    )     {
                      re     t   urn     sr c.     getColum     nCount();
              }

                 @Overri      d      e
                        p    ub  lic       Stri    ng getColumnName   (int column)  {
                                      r  et         urn    src    .  ge          tColumnName(column)    ;
                            }

                      @Ov     erride
                          p  ublic int get Row        Coun                t() {
                             return row s.size    ();
                }

                           @Ove rride
                 public Class<?>               getCol       u  mn  Cla          ss(int    co      lumn  Ind ex) {
                                     return St    ring.class;
              }

                           @Overr ide   
                 pub  lic boolean isCellEditab      le(    int   rowIndex, in  t columnIndex)    {   
                         ret         urn false;
                }      
     
                              @Overri de
                    public Object  getVa              l     u      eAt(i   nt r owInd     ex,      in           t c         o       lumnI ndex) {
                        return      row   s.get(ro    wIndex)   [col   u  m  nInde  x];      
                 }

                  @Overr                ide
            pub  lic voi  d setV          alueAt(Ob   ject   a        V  alue, int rowInd  ex, i  nt c  o    l  umnInd    e   x)         {
//                  r ows.get(rowIn   dex)[columnInd  ex] = (Str    ing) aValue;
                             }

            @Ov   erri      de
              public      void addT        ableMo    del     Listener     (TableModel   Listener        l) {   
            }    

            @Override
                                                             public vo     id remo     ve  Table    ModelListener(T   ableModelLi   stener          l) {
                             }
            };
     }

     /**
             * Ge  t            the KVP as   table.
     * @ param p    
           * @   ret    u    rn             an    AbstractTable Mo         del fo r    p    ro   per      ti      es  (KVP)
            */
    publ   ic stat     ic Abstra     c      t   Ta               bleModel ge       t    Properties(fi    n                  al CSProp   erties p    ) {

            return new   A bstr    ac  tTableModel()   {

                @Ove      rride
            publ ic in                 t  getRowCo       unt(   ) {
                     ret  urn     p.keySet() .    si  ze();
                             }

                       @Over    ri    de
            public         int getColu          mnCoun                t       () {
                                          r     eturn   2;
                   }
       
                              @Override
                                              p  ublic Object  getValueAt(int r          o    wIndex,     int co   lumnIndex) {
                 if (col    umnInde  x      == 0      ) {     
                            re                turn      "     " +            p.   keySe    t().toAr r      ay(       )[  rowIndex  ];
                         } else {  
                              r       e        tu    rn          p.va     lues(). toA   rray()[row         Index];
                       }
                }

                @O verride       
                                    public            boo         l    ean isCel   lEd  it  able(int row       Index,      int       c    olumnI ndex )    {
                                         return columnI        nd    ex      ==        1;
            }

            @Overri   de
                          publ           ic v o     id set           V         alueA         t(     Object a  Val     ue, in           t ro    wInd   ex, int columnIndex)    {
                  i   f (columnIndex  ==    1) {
                         String[] keys = p.keySet       ().  toArr   ay     (new  String[   0]);
                                p.put(ke      ys[r   o   wInde  x]    , aValu   e.toStri ng());
                                            }
              }
  
                   @Override
             pub     lic String getColumnName(int col            u  mn)            {
                        retu   rn co            lu  mn == 0 ?   "Name"       : "Value";
              }

                 @Override
                      publ  ic Class<?>   get    ColumnClass(in     t co   lu        mnIndex) {   
                retu rn              String.class;
                 }
           };
    }

        publ   ic   static           Abstract        TableModel get    2DBounde    d(final CS     P        rope      rties   p, f  inal Stri ng pn     am    e) throws ParseExcept   ion          {

         St            ring m = p.getInfo(pn        ame    ) .g      et         (    "b    o  und"        );

           S  tring[] dims = m            .s    plit(",");
                 f       inal int rows = Data   I O.get   Int(p, dims[0]    .  trim());
                  f                  i    nal int co      ls      = DataIO.getInt(p,     dim     s[1].   trim   ());

        r                    et  urn new A        bstractTa     bleMode           l()      {
  
                 @Override
                            publi           c i  nt ge tRowC           ount()       {
                return rows;   
                }

                              @Ov e     rride
                      pu  blic int getCol  um                  nCou  nt()     {
                return            cols            ;
                       }

            @Overri    d           e
            public boole     an isC    ellEdit   able(int r      owInd      ex, in t column  Inde    x)    {
                           r         e        tu       rn t    rue;
                   }

              @Ove            r   ride
                         pu   b        li   c Obj     ec     t get        ValueAt          (          int row   , int col) {
                       Stri   ng[][]    d = Conversi     ons.conver t(    p.get(pname), String[][].cl  a    ss);
                        retur    n d[row]     [col]. trim();
                                }  

                       @O           ver            ride
                   publi     c void setVa           lueAt(Object  aValue, int ro   w    Ind   ex, in   t col  umnInde  x     ) {  
                             String[][ ]            d = Convers ions.convert(p.get(pna   me), String[][].clas  s);
                            d     [rowIndex][columnIndex]    = a Valu  e.toString().t        rim();
                                      S                                        tri   ng         s =  toArrayString(d                               );
                          p .pu t(    pname, s);
                     }

                   @Ov    e      rride
             public   S        tring     getCol umnName(int c    olu       mn) {        
                       retu    rn Integ  er   .toSt   r    ing(colum  n);
                 }

                      @ Override
                           public Class<    ?     >        getColumn Cl       ass  (int colum nI  ndex   ) {
                               return Str   in         g.   class;
               }
        };
       }
      
         stat  ic publ            ic boolean pl     aysR   ole     (f      inal CSPr  operties p, String            key, String   ro       le) {   
             Strin  g r = p    .   g  e        tIn  fo(key).get("ro le");
                    if             (r    == nu    l       l)   {
                      return   fals    e;
                                    }   
           re     turn   r.contains     (role ) ;
      }

        stat        ic   public boolean  isBou            nd(f       in  al CSProper  ties p,  Str ing key,    i   nt  dim)              {   
                    String bound = p.getInfo(key).get     ("boun        d");
          if   (bound == null)      {
                    return false;
        }
                S         tringToke  nizer         t =           new StringTo kenizer  (bo    und,    ",");
            if (t.c                ountToke n       s() == d   im   )      {
                         re    t   urn    tr ue;
        }
           return false;
    }

            p        ublic s        tatic CSTable getTa   bl     e(       fin      al CSP   roperties p,    S          tr     ing     boundName) {
        Memory  Table     m =       new Memor    yTable();
        List<String  > arr = k     eysByM  et    a(p, "b  ound"      , b        oundName)     ;
             fo    r (S     tring a : ar     r) {
        }
          return m;
           }

           // 1   D arra    ys
    public stat       ic A   bstra      ctTableMod  el ge     t       BoundPro  pert ies(fin   al CSProper      ties p     , String boundNa  m e) th rows    Pars   eException {  


             final int r    ows = Da                    taIO.get    Int(p,              boun dNam      e);
             fina   l L  ist<Str        ing> arr                           = k          eysByMeta(p,   "b  ou  nd", bou        nd   Name)  ;

        return new Abst   ract     TableModel() {

              @Overr      ide
                         publ ic   int g      e      tR   owCount() {
                 return  r   o     w     s;   
               }

              @Override
                      pub  lic int get Co    lumnC   oun     t() {
                  return a rr   .siz e();
                         }    

                    @Overr                i  de         
             public Object getVal    u  eAt(        i        nt      r  owIn de      x, int c   olumnIndex) {
                    Str    in  g        colname    = arr.ge      t(columnInde  x);  
                        St ring[    ] d = Conversions.conv    e                    r t(p.g       et(colname), String[].class);
                                   retu      rn d[rowIndex].      tr  im()    ;  
                 }
    
                          @Override  
                            public b    oolean isCe            ll   Editable(int           rowIndex   , in          t columnIndex) {
                    re    turn true;
                 } 
 
              @Overrid   e
            pu blic                               vo     i  d       setValueAt(Obje   ct aV a    lue, int r  owInd   ex, int columnIndex) {   
                Str           ing co        lnam  e = a    rr.ge t(col   um       nIndex);
                         String[        ] d =      C   onve    r si   ons.co                      n         vert(p.get(col      nam          e)   ,        String[]             .class) ;
                              d[rowInde  x] =         aV     alue.toStri ng()   .trim(   );
                  Stri    ng s =    toArrayS    tring(d);
                                  p.put(co  lna me, s);
            }
  
                                  @O       verrid     e
             public S       t  ring  ge    tC     olumnName    (int    column)   {
                  return arr.get(col  umn);
               }

                         @Ove rride
                    pub     lic Class<    ?> getColumnClass(int columnIndex) {
                                    return Strin     g.clas  s;
                        }
        };
    }
    
     // u  nbound
    publi   c stati c Abstr   a      ctTableMode l ge    tUn Boun  dP  roperties(final       CS    Properties p)      throws      Parse              Exception {


         final Lis     t    <String       > arr = keysB yNo      tM  eta(p, " bound");
    
        ret  urn   ne  w A  bstractT ableModel (   ) {

                  @O verr    id          e
            publi     c    int ge   tRowCo   unt()  {
                                        r           eturn arr.size();
                                }    

                        @Override
                public int getCo  lumnCount() {
                  return 2;
              }

                                          @Ove    rride
            public O  bject getVal     ue  At(int rowIndex, int colu     m     nIndex)     {
                           if    (co lumnIndex ==        0) {
                                          re    turn arr.get   (rowIndex);
                       }                    else {
                                ret  u   rn p.g    et(arr.get(rowIndex));
                    }
                         }

                                       @                     Overr   ide
                         publ    ic   b          ool    ea  n isC     ellEd   i        t  able(int                         r   owInd  ex, int      co  lumnInde          x)     {
                      r   etu         rn    columnIndex     ==  1;
                      }

            @Over     r ide
                    publi c voi        d   setVal   ueAt       (Ob   ject aV   alue, int ro wInd   ex, int  columnI    ndex) {
                            p.pu         t(arr.get (row   I                  n   dex    ), aVa           l ue.toSt  rin     g());
                      }

                       @               O      verri    d e
            public String    ge        tCo   lumnN   ame(         i nt colu mn) {
                          ret   urn     (c  o  lumn == 0)      ?       "K    ey" : "Value";
             }

                    @Over   ride      
                         p ublic Clas    s<?> getCol   umnC    la  ss(int    column   Ind   ex)     {
                      return              S    tring.c     las s;
                        }   
            };
    }     

       /**
                   * Creat          e  array     string.
     *              
     * @      par      a  m arr
     * @retu   rn an    array Str ing.
     */      
           public st  ati c Strin         g t oArr ayStri   ng(  S   tring    [      ] arr)                      {
           StringBuilder           b = new St      ringBu    ilde   r();
                               b.append('     {');
                for (i     nt i = 0; i < arr.l      ength; i++        ) {
            b.ap  pend(a rr[  i])      ;
                 if  (i     < a    rr.length - 1)    {
                                        b.append(     ',  ')  ;
              }
                    }
             b.   appen   d('}'      );
            r   etur    n b.t   oStri   ng();
           }      

                                     public static   String toArrayString(S t        ring[                      ] [] arr) {
               Str  ingBuild           er b = new StringBuil      der(  );
           b                 .append('{');   
        for (   in     t   i = 0 ; i < arr.leng       th;    i++) {
                       b.a     p           pe    nd('  {' );
                                                                        fo r (int j =    0; j <   arr[i].le      ng     th; j++) {       
                          b.append(   ar   r[i][j]);
                      if   (       j < arr[i].    lengt   h -  1)            {
                                b.ap  p     end(',');
                       }
                         }
                    b.append('  }');
                if   (i < arr.le ngt    h - 1) {
                 b.append(',');       
            }
            }
              b.ap  pen d('}  ');     
            return b.t     oString();
    }           

      /**     Ret    ur  ns     a r/o  table     fro       m       a CSP file
        *
       * @       pa        ram p
                         * @p   aram        di m
     *   @r          et     urn a   table model for p roperties with dimension.
     */
       p    ubli     c             sta    tic TableModel fromCSP(CSPr    operti   e      s p, final int        d   im) {    
                 List<String>   dims      = ke ysBy       Me    ta(p, "ro         le" , "dimension");  
          if (d i      ms.is  Empty()) {
                  return null;
                  }
               f   or (String d :    dims) {      
                   if (Integer.par se      I nt(p.ge      t(d).    toString())    == d  i m) {    
                               f    inal List<   St     ring> bounds = ke    y  sByMeta(p,          "bound",     d);
                           final    List    <Ob   j               ect> c  olumns = n ew ArrayLi   st<Object>(      bounds.size(   )) ;
                                  f    or (St    ring bound         : b ou  nds)  {
                    col     umns.add(C    onversion     s.convert(p     .g   et             (bou  nd)   , dou   ble[].c    lass));
                       }

                r          e   tur   n    new Ab        st      ractTableModel()   {

                        @Ov        e  rride
                                          pu            blic int getRowCo  unt() {
                                   return     dim;  
                          }

                        @Over              ride
                                  public in t ge  tColumnCount () {
                                             ret  u                  r  n bounds.   s  ize() ;
                              }

                       @Overr ide
                            public Obje       ct       getV    alueAt(in t              r         owInd   ex, int colum    nInde  x) {
                                                    return Array.get(columns    .get(columnIndex), r  owIndex);
                             }         

                                           @Ov    erride
                                                  public St r    i ng getC    olumnName(      int column) {
                                                 return bounds.get(c   olu  mn);
                                  }

                      @Ove rride
                                   public Cla            ss<?> ge    tCo   lumnClass (int columnI  ndex) {
                                                re      turn   Double .clas      s  ;  
                                              }
                                         };
                  }
            }
            return nul l;
    }

    / **
     *   
         * @para      m cs       p
     * @param m   key
       *     @param  mval
     * @r    e    t urn the lis     t of property key     s that    have a     meta d at a    val   ue.
     *   /
    public        static List<S      tring> keysByM   et    a(CS   Properti  es cs   p, String m  key       , St   ring m  va          l)    {
                  List<String> l = new ArrayLis   t<S  tring>();             
                            for          (String key : cs        p.  keyS et         ()) {
                      if (cs        p.   ge   tInf o(ke            y).ke   yS et().contains(mke  y))   {
                         String role = csp.getI   nfo(key).get(   mkey);
                        if   (role.  e     qua    ls(mval)) {
                           l.add       (key);   
                    }
                }
           }
               retu   rn l;
    }

        public st           a         tic       List<Str  ing> keysForBo  unds(CSProperties       csp    ,      i                 n        t boundCou   nt) {
           List<    Strin g> l = n      ew                ArrayList     <St            ri    ng>();     
          for             (  String k   ey : csp.keyS    et()) {
              if (        csp.getInfo(key).     k  eySet().contains   ("b      o  und "   ))    {
                                   String boun          d = c sp.g         etI n   fo(key).ge  t("b ou          nd"                 );
                                         Strin   gTokenizer t  = new Stri  ng  Tokenize   r(boun    d, ",");
                          if (t.      coun   tTo   k     e   ns() ==     boundCount) {
                                         l              .add(          k  ey);
                      }    
                            }
          } 
        return l;
    }

    p   ubl      ic      stati    c           Li   st          <String> key  s B   yNotMeta(CSPr      operties             c s p, Str   ing   mk  ey) {
                List<String> l     = new Arr   ayLis       t    <Strin      g     >();
           for (        String key : csp.keySe              t()) {
               if (!csp.get   Info(key).key    Set()      .co     ntains(mkey    )) {
                   l.ad  d (key  )   ;
              }
         }
        r  eturn l;
             }     

                   
            publi     c static Date[] getC     olumnD    ateValues(CS  Tab     l    e t, S tring colum nName) {
           int col   =   columnI                 n             dex(t, columnName);
                              if (col == -1) {
            throw new I    l  legalArgum entE   xcept         ion("  N    o such col     umn: " + c   olumnN      ame          );  
                             }   

        Co    n  versions.Params p = ne w     Conversion   s.Pa      r   am     s();
                                     p.add(St             ring.class            , Date.cl       ass,      loo   kup Da            teF      ormat(  t,    co       l   ));

           List  <        Date> l    = new Array List<Date  >()     ;
                          for   (Str         ing[]    s     :       t.rows()) {
                               l.  add(Con          versions  .co nve       r   t(s[        col   ] ,          Date.class,   p))     ;
           }
        re      tu    rn l.toArr  ay     (new Date[0]);
     }

                           /*   *
     * G et a co   lumn as                  a     n int array.
       *  
          * @pa  r a     m t  
     * @param col          u  mn Name
       * @re  t     urn     the c   olumn data as dou      bles    .
     */
     public        s     tati      c Double[]       getColum         nDoubl    eValues       (CS   Table       t, St  r   ing colum         nName)  {
                 int     co l =   columnIn dex(t,      co  lu    mnName);
              if (col ==  - 1)   {
                                  thro  w n     ew IllegalArg umentExce   ptio     n("No     suc           h c      olumn:       " + column     Na me       );
                   }
        Li st<           Doub   l         e> l = n             ew Arr  ayList<Double>();
        for ( Str  ing[] s : t.     rows(  )) {
                            l.add(new D     ouble(s[col]));
                      }
        retur n l.toArray(new   Dou  ble[0]);
     }

    /**
     * G   et a value as date.
              *    
     *               @param p
               * @param key
           * @       re   t           urn      a property as Date
                  * @throws     java.te   xt.ParseExcep                     tion
     */
                publ      ic           static Date getDate(CS   Pr   operties p, String key) throws ParseExcept        io  n {
                          String    v   al =           p.g et(key).toS   trin  g();    
           if (val  == n   u     ll) {
               throw new    Illega   lArgu m                 en         tE  xcepti        on(key);
                   }
                  String f   = p.getInfo (ke   y).get   (KEY_F ORMAT);
            DateFormat fmt =         ne     w     SimpleDateFor  mat(f =   = null ?      ISO 86 0       1 : f);
                     retu rn f    mt.p   arse           (val);
    }

              /*    *
     * Get a   value          as         integer      .  
         * @  param      p
     *  @par am ke   y
       * @return a            prop    er            ty v   alue as integer   .               
              * @th row   s java.text.     Pa  rseExcept            ion
        *  /
    public     static int get   Int(CS   Pr   oper ties   p,    String     key) t  hrow      s P  arseEx    cept    io   n {
                      Str   ing val  = p.g    et(key   ).t   oString      ();
             i   f (val = = nu  ll) {
                                       throw ne  w Illega  lAr             gumentE xception(k                    ey)   ;
            }
                   retur  n I nteger.parseInt(val);
         }
      
    pu    blic static           void save      (CSProperties c   s        p, File f, Stri n   g       title)  {
             Pri   ntWriter w =                        null    ;
           t  ry {
                    if (   csp instanceof    BasicCSPropert       ies) {
                        Basi  c   CS  Propertie       s c = (B    asi     cCSP      rop     e    rti es) csp;
                          c.setName(  titl   e);
             }
             w  = new  Pri  ntW   riter(f   );
                    D  ataIO.pri nt(csp   , w);
                     w.        cl  os e();
           } ca  tch (FileN  otFoundExceptio   n       e         x)    {
                e  x.printStackTr            a    ce();
                         } finally {
                             i      f (w      !=   null   ) {
                     w.clo   se();   
                 }
        }
                  }

         /** 
                 *           Print CSProper          t   ies.    
     * @par             a m props the Properties to print
       * @        param o  ut      t   he output writer                 to print to.
                   *    / 
    public static     void print(CSPr      opert      ies prop       s,             P   rintWriter o       ut) {
            out.   pri     ntln(P             ROPERTIES + "        ,"       +     CSVPars   er       .p    r    in   tLin   e(props        .getName())); 
              for (String k ey    : pr  ops.getInfo().keySet()) {
               out.p  ri ntln(" " + CSVPar    ser   .pr intLine(key,   props.g       etInf     o           ().ge   t(key)));     
                      }
        out.println()  ;
            for      (S    t  ring         key        : prop   s.keyS    et()) {   
                  out.println(PR   OP     ERTY + "," + CSVPar      ser.printLine(key, props.get(key).to                S  trin g()));
                     for               (St    ri   ng key1 : props.    getInfo(    ke    y).keySet(     )) {
                             out.p    rintl   n("        " + CSVParse  r.printLine(key1  , props.getInfo(key).get(  key1)));
                       }
                 o   ut.println(    );
                }
                ou    t.println();
                 out.flush    ()  ;
            }

                 publi c static vo    id         print(Map <   String, Object> pr  ops   , Stri  n     g header,        P    rintWriter   ou      t) {
        out.println( PRO         PERTIES + "," + heade r)     ;
          out.printl  n();
        for (String       key : props.keySe         t())          {
                 out.p  rintln(PROPERTY + "," + CSVP   a     rser. pri  ntLine(key, props.g    et(key).toString()));
                     }
              out.println();
        out.   flu     s   h();      
    }

       /**
              *     Prin         t a CSTa           b   le      to a Pr  intWriter
     * 
       * @pa r     a   m ta  bl e      the ta         ble to pr   int
           *         @pa  ra   m out t   he    writ    er to write to
     */
    public static void print  (CST able ta        ble, PrintWr     iter             out) {
         out.println(TABLE      + "," + C     SVParser.printLine(   table.getName()));
               for (String k ey :       table           .g  etInf  o().keySe   t())   {
                 out.pri    ntln(      CSVPa rs   er.printL   i      ne(   key, table.getInfo().get(key)))      ;  
        }
           if (table   .getColumn     Count() < 1) {
                      out.flush();
                 r            et          urn;
              }
        out.pr  i     nt(H     EADE  R)     ;
              for (int  i     = 1; i <  = ta    ble.getCo lumnCount()     ;        i++) {
                         out.         pri          nt(" ," + t       able.getColumnN  ame(i) );
                          }
        out      .println();
                     Map<String, S   tring>   m = table.getCol      umnInfo(1);
                fo   r (Strin  g k    ey :     m.keySet()     ) {
             out.pri nt    (key)    ;
                      for (int i = 1;    i   <= table.getC  olumnCount();      i+      +) {
                out.print("," + table.getCo  lumnI       nfo(i).ge              t(key)  );
                   }
                   o   u  t.println();
                   }
        fo r               (String[] row   : ta  ble.rows()) {
            for                            (i          nt i = 1; i < row.le    ngth; i++) {
                              out.    print("  ,"   +    row[i]  );   
               }
                  out.  println(                          );
                  }
                  out.pr  intln();
         out.f   lus   h();
    }

    /*    * Sa    ves a  table  to a file.
     * 
       *      @par am table the table to save
                * @param file the f   ile    t    o     store it in     (over   writt en,   if ex       ist  s)
       *   @thr  ows       I     OException 
       */
    public      st    ati c vo id        save(CSTable table,     File file) throws IOException {          
        Pri     ntWriter w = new PrintW  rite     r       (file);
                     print(table, w);
           w.cl      o     se();
                  }
   
        /   **
                   * Parse pr   oper   ties from a reader     
     *  
     *    @p a       ram r        the Re         ader
     * @param       name the name  of t     he proper        ties
     * @r   etur  n p  ropertie      s from       a file .
         * @thr    ows jav    a.  io           .IO  Exception
            */
    public stat           ic CSProperti   es prop   e  rt   ies(Reade r r      , S  tring n    ame) th  ro  ws IOException   {
            return new CSVP roperties(r,    name);
      }
    
    
    pu blic stati   c CS   Properties proper ties(File r, Strin g name) throws IOException {
          return      new CSVPro   perties(   new F    ileReader                     (r), name);
    }
    

      / *  *
     *     Cre ate  a    C   SP        r   opert y   fr            o        m an ar         ray      of read er.
        * @para        m r
             *   @par   am  name
         * @re tur   n merged          prope     rties.
          * @throws java.io.IOException
     * /
    public static CS  P    ro pert   ies properties(Reader[] r, String name) th       row    s IOEx    cepti    on {
                       CSVP  r   opertie  s p = new CSVPropertie        s(r[0], nam    e   );
                     for (in         t i = 1; i <    r.l    ength; i++ ) {
                      CSVPar   ser csv =   new   CSV      Pars  er(r[i], CSVStrategy.DE        FAULT_     S TRATEGY  );
            l ocate(csv,  name,      PROPE R   TIES, PROPERTIES1);
                                    p.readPr            ops(c   sv);
            r[i].c    lose();
                }
                ret urn    p;
    }  

         /**
     * M        erges       two P     rop ert  ies, res pec   ts permissions
      *    
           * @param        base
     *     @           param   o  ve           rlay
       * /
    publi   c static void           merge(CS  Properti     es base, CSProper   ties overl ay) {
               for (String key : overl ay   .keySe               t())  {
            if (base.getInfo(key).cont   ainsKey("       pu          blic")) {
                             b   ase.pu         t(  k    ey, overl   ay.get  (key  ));
                 } el   se {        
                     throw new IllegalA  rgume    nt    Except      ion("Not  public: " + key);
                     }
            }
    }

          /   **    
                           * Convert CSP    rop      er ties i  nto Properti                 es
     * @param p
     *     @  return the Proper   ties. 
      *    /
        public  static Prope  rties pr    op       erties(CSProperties p) {             
           Pr            operties pr   = new     Prop   ertie                                s();
                 pr     .putAll(p);
                   re    turn  pr;
         }

    /**     
         * Con  ver   t Prop   er   ties to CSPro      perties.
         * @p  aram p     th     e                Propert      ie   s
          * @     retur   n   CSPro  perties
               */
           public static CSP  rop      erties p       roperties(Propert            ies     p)        {
        return new     BasicCSProperti  es(p);
          }

       /** Con  ver    t     f    rom a Map  to properti      es.
     * 
             *        @param p          the sour     ce       map
      *    @return      CS         Prope  rti   e   s
       */
    public stat  ic CSP   rope  rt  ies pro perties(Map<String, Obj        ect>      p) {
           ret   urn new BasicCSPr   operties(p);    
    }    

    /** Create Empty propert  ies
     * @re    tur   n get some     empty                 propert        ies.
     */
    public s    ta   ti    c C SPrope     r  ties prop  e         rties() {
             return new BasicCSPr   opert   ies()   ;
        }

       /*  *     Parse th     e        first table from a   file
     *      
     * @param file th    e fi      le t o p        a    rse   
          * @re turn the CSTable
       * @throws IOException 
     */
    p ublic static CSTable      tab   le       (File file   ) throws IOExcepti     o     n    {
        return     tab    le(file,                      null);
    }

    /**   Pa     rse a t    a    ble from     a given Fil    e.
          * @param     file
     * @p  aram                      n   ame
       * @re         t    ur    n a CSTable.    
     * @throws java.     i       o.IO Ex      ception
     */
        publi   c static CSTable   table(File file, String name) throws IOException {
                   return new Fi   leTab          le(   file , na      me);  
    }

       /** Pa      rse a ta     ble          from a Re        ader. Find  the firs   t table
              * 
     * @param s t   he Read er to read from
                * @retur     n the CSTable   
     * @throws IOE   xc    e    ption  
     *          /
                public s     tati    c CS  Table ta     ble   (String   s) throws IOException {
        re    turn t     able   (s, null)   ;
     }

           /  ** Parse a t      a     b        le from a Reader.
     * 
     * @param         s the   Read er  to read f rom
            * @  par    am name the name of the table
        * @     return   t   he CSTable
     *  @thr ows IO   Exceptio         n   
       */
             pu  bl   ic st  atic C   STabl   e table(String s, Str ing       nam    e) throws IO   Except    ion {
        return new StringTable(s                      , name);
    }

      /* * Open s the fi             rst          table found at the URL
     * 
     * @  param url the URL
         * @ret urn   the  CS   Table
        * @throws      IOException 
     */    
    pu   b        lic sta   tic     CSTable table(U      RL      url)     throws      IOExce    ption {
             re       turn         tabl       e(url, null)      ;    
      }

    /** Cre   ate a CSTa     b          le    from a  URL source.      
        *         
             *     @param url t    he ta  ble URL
     * @  param name the nam           e of t  he table
                  * @return a ne       w CS   Tabl       e
     *     @throws   IOExcepti       on 
         */
    publi  c          static CSTable table( U      RL   ur               l,  St  ring name) thr ows IOExcepti      on {
        return new      URL   Tab        le(url, name);
      }

    /*   * Check if a column ex          is  t i     n table.
            * 
     * @par  am   table the table     to check
             * @param          n   ame the name  o  f the c    olumn
     * @return     
     */
    p        ublic      sta    tic boolea     n        columnExist(CSTabl    e table, String name) {
        for   (int i = 1;   i      <= table.ge tC      o    lumnCount(); i++) {
            if (ta  ble.getColu   mn  Nam  e(i)  .        star tsWith(name)) {
                    return tru   e     ;
                }
         }
               return     false;
    }

    /**   Gets  a colu  mn   index b   y na          me
          * 
           *     @param table The table to check
     *    @param name      th  e colu  mn name
      * @return the ind     e     x of the     column
     */
      public         static i  nt columnInd     ex(CSTabl  e   table, Strin  g name) {
        for (int i   = 1;     i <= table.getColu  mnCount(     ); i+ +) {
              if (tab          le.ge      tColumn Nam     e      (i).equals(name)) {
                                r    eturn      i;
                 }
         }
        retu      rn -1;
            }
    

     /** Get the      col  umn indexes for   a given          column na    me.
     *  (e.     g.       use t    min     to fetc  h tmin[0], tmin[1]       ...)
     * @param t  able
          * @par        am    name
               * @re     turn 
                */ 
       public static int       [] columnIn    dexes(CSTabl  e   table      , String name) {
               List<Int  eger> l = new Array     List<Int   eger>(     )   ;
        f                o   r (int      i = 1; i <= table.get   Column      Count(); i    ++) {  
              if (table.getColumnNam        e    (i).startsWith                 (name)) {
                l.add(i);
            }
        }
        if (   l.isEmpty()) {
                return null;     
                  }
           int[] i       dx =  new int[l.size()];  
        for             (int i = 0; i < idx  .length; i++)        {
                idx[i] = l.get(i)     ;
        }
                return idx;
    }

    publ         ic static List   <Stri                    ng> col umnNames(CSTable           table, String name) {
        List<Strin    g>   l = new ArrayList<String         >();
        for      (int i = 1; i <= table.getColumnC  oun   t(); i++) {
            if (table.getColumnName(i).startsWith(    name))    {
                l.      add(tab    le.g   etColum          nName (i    ));
            }
        }
                    if (l.isEmpty()) {
            throw n  e    w IllegalArg  ume     n  tException(      "  No colum  n(s) '" + name + "' in table: "  + table.getName());
        }

        return l;   
    }    

           public static    void r  owStringValues (Str   ing      row[], int[] idx, Str ing[] v  als) {
        for (int i   =     0   ; i < va            l       s.   length; i++) {    
               val      s[i] =      row[      idx[i]];
                       }
    }

         p      ublic static do         ubl     e[] r  owDoubleV alues(String row[],     int  [] idx, dou       ble[] vals)    {
        for (int i =    0;    i   < vals.length; i  ++   )   {
                vals[i] = Double.p   ar  seDouble(    row[idx[i]    ]);
            }
        return vals;
    }

         public static double[] rowDo    ubleValue     s(String row[], int[] idx)  {
           double[] vals      = n   ew double   [idx.lengt       h];
            r     e     turn rowDoub  leVa  lues(row,     id   x, vals);      
    }

     /** Extr         a          ct the columns and create a      nother   t able.
     * 
     * @   param table the table 
       * @pa        ram colNa  me the names     of th               e colum     ns      to extract.
     * @return A ne w Table with th    e Columns.
               */  
         public static CSTable extractColumns(CSTabl            e table,      String... colNames    ) {
        in   t[] idx  = {    };

              for (String      name : c olName   s) {
            idx = add(idx, columnIndexes(table, name));            
           }

                     if (idx     .length == 0) {      
            thr   ow  new IllegalAr     gument      E    x         ce   pti on("No such column names:   " + Arra      ys.toS       tring(  colNa mes));
              }

                   List<String> cols =      new A rr         ayList<Str      ing>();  
           for (String name : colNames) {
                    co  ls.addAll      (columnNames(table, name));
            }
 
                     MemoryTable   t = new MemoryTab     le();
            t.setNam  e(t     able.getName());
                t.    getIn  fo().putA    ll(table.get      Info());

                  // header
                       t.setColumns(co    l s.toArray(new String[0]));
        for (int i       =     0 ; i < idx.le n   gt    h;  i++) { 
               t.    g  etColumnInfo(  i   + 1).putAll  (  t   able.getC     o   l  umnInfo(idx[i]));
          }

                             Stri   ng  [] r = ne    w          Str   ing[idx.le  ngth];
        for   (String[] row : table     .ro   ws())  {
            row     StringVa     lues(row, idx,  r);
            t.addRow((Obje    ct[   ]) r);
             }

             return t    ;
    }

    public st  atic String di ff(double[] o, d   ouble    [] p) {
        Stri  ng status = "ok.";   
         i    f (o.length != p.  length) {
             status = "o.length !=      p  .len   gth";
          }    else {
            for (int i =  0  ; i < o.len    gth  ; i++) {    
                     if (o[i] !=  p[i])  {
                                          s       tatus   += "erro   r";
                      }
            }
                 }
        return status;
    }

    pub  lic   static CSTa ble asTable(CSProperties p, String dim)  {
        List  <Str    ing> arrays = DataIO.keysByM  eta(p,  "bound", d im);
                if (a   rray     s.isEmpty()) {
               / / nothing is bound to this
               return null;
         }  
                 int len = 0;
        List  <String[]> m = new Ar     rayList<St   ring[]>();
        for    (String     arr :      arrays) {
            String[   ] d = Conv    ersions.co   nv    ert(p.get(arr),    String[].class);
            len      = d.   length;
                        m.ad   d(d);
        }
              MemoryTable table   =   new Memory     Tab    le();
          table.getInfo().put("info", "Parameter bound b y " + dim);
             table.setName(di   m)   ;
        table.setColumn s(arrays.   toA          rra    y(new String[     m.size()]));
               String row    []     = new String[m.size()];
        for (in  t i =           0; i < len; i++) {
                for (int j  = 0;      j < m        .size(); j++) {   
                 row[j] = m.     get(j)   [i].trim();
              }
            table.add  Row((Object       []  )     ro   w);
        }
         retu      rn   table;
       }
   
    public static CSProperties fromTable    (CSTable       t) {
        BasicCSProperties   p = new BasicCSProperties();

        Map<In                t    ege    r, List  <String>> table = new HashMap<Integer  , List<String> >();
             for   (int i = 1; i <= t.ge    tColumnC            ount(); i++) {
            table.put (i, ne w  ArrayLis    t<St      ring>());
        }

          for       (String[] ro  w : t.rows())    {
               for (int i = 1; i < row.length     ; i++) {
                table.get(    i).add(row[i]);
            }
        }
              
        Map<String, String> m = n e        w HashMap<String, St   ri ng>();
             m.put("bound", t.getName(       ))   ;

        for (int i =     1;    i <= t.g etColumnCo   unt(); i++)   {
              String     name = t.getC  olumnName(i);  
            p.put(name, table.ge   t(i).toStr   ing      ().       repla   ce ('[', '{'   ).      replace (']', '   }    '));
              p.setInfo(name, m)  ;
        }  
        return p;
    }

    /** Find    all tabl  e names     in    a file.      
        * 
     * @param f the file to sear    ch in
     * @return a lis      t of table       names found in that fil e.
     */
    public static Li   st<St   ring> table   s(File f   ) t    hr ows IOExc      eption {
        r   eturn f    indCSVElements(f, "@T")    ;
    }  

    /** Find all         properties section names in a       file       .
      * 
         * @param f the file to     sea rch in
      *    @return  a list of sec tion names fo  und in th    at file.
     */
    p ublic static List<    String> pro      pe  rties   (Fi le f) throws IOException {
        return fin    dCSVElements  (f, "@S");
    }

      static List<String      > findCSVElements(Fil          e f, String tag) throws IOExce      pt  ion {
         List   <String> l     = ne w ArrayList<String>();
        Reader r = new Fil    eRea        der(f);
        CSVParser csv = new CSVPar  se r(r,       CSVStrateg      y.DEFAUL    T_STRAT  EGY    );
         String [] li    ne = null;
        while ((lin    e  = csv.ge        tLine()) != nul        l) {
            if    (   li      ne.length    == 2 && line[     0].   equals(tag))         { 
                                l.add(line[1])    ;
            }
        }
          r.cl   ose();
        return   l;
    }

    /////////   //            ///     //////      ////////////////////// ///////////////////////////////  ////
    /// private 
    private stat      i   c int[] add(  int[   ]    a,    int[] b) {
        int[]      c = new int[a.leng     th + b.length];
               System.arraycopy(    a, 0, c,   0, a.length);
        Syst   em.arra  yco py(b, 0   ,      c, a.le ngth    , b.length);
        return c;       
    }

    private   s         tatic String locate(CSVParser      csv, S     tring name, Strin    g. .. type) throws    IO Exception {      
           if     (name == null     ) {
              // match anything
                      n   ame = "          .+";
             }
        Patt e   rn p = Pattern.co    mpile(name)         ;
                   String[] line = null ;
          while ((line = csv.getLine  ()) != null)          {
               i   f (line[0].sta  r    tsWit  h(COMMENT) || !line[0].   star        tsWith(P)) {
//                if (line.l    ength != 2 || lin      e[0].startsWith(COMME   NT) || !l     ine[0].startsWi    th(P  )) {
                             continue;
                   }
                  for (Strin  g s :     t   ype)    {
                             if (line[0].eq  ualsIgnoreCase(s)   &&     p.mat        cher(line[1].trim()).matches()) {
                             return line[1];
                }  
            }
        }
        thr      ow new IllegalA  rgumentEx   ceptio n("Not found : " +   type + ", " + name);
    }

    @S   uppressWarnings("serial")
    private static class BasicCSProperties exte n    ds LinkedHashMap <S   tring, Object> implements CSProperties {
  
             String name = "";
                    Map<String, Map<String,    String>> info =   new HashMap<String,    Map<String, String  >>();  

           BasicCSPrope rties(Prope         rties         p) {
                 this();
            for    (Object key        :     p.keySet()) {
                put(key.toString(), p     .getProperty(key .     t    oString()));
                  }
        }    

         BasicCSProperties(Map<S      tring, Objec    t> p) {
                this();      
            for (String key : p.keySet()) {
                put(key, p. get(key));
              }
          }    

        BasicCSPropertie s() {
            info.put(ROOT_ANN, new H  ashMap<String,     String>())  ;
         }

            @Ove    rride
        public voi   d putAll(   C SProperties p) {
            super.putAll(p);
               for (String s : p.keySet())    {
                        Map<St   ring, String> m = p.getInfo  (s);
                      setInfo(s, m);
            }
            getInfo().pu   tAll(p.g    etInfo(  )   );
        }

            @Ov  er  ri  de
             public Str ing getName(  ) {
              return name;
           }

        @Override
        publ    i   c voi   d s      etName(Str ing n     ame)   {
                this.name =    n     ame;
        }

        @Override
            public Map<String, String> getInfo(Stri ng p    roperty             ) {
            Map<String    , String> im = info.get(property);
            return (im == null) ? NO    INFO :      im;
        }

            @Override
        public Map<String, String> getI   nfo()   {
                             re              turn getI nfo(RO  OT_ANN);
        }

          @Override
        public void setInfo(String pro   pe rtyname, Map<String, St   ring> inf) {
            info.put(prop ertyname, inf);
          }

        @O   verri  d          e
          public String get(Object key)     {  
               Object val = super. get(key.toString(    ));
            return reso  lve(val != nu      ll ? v   al.toString() : null);
         }

          /**
         * Resolve variable  subst              itution.
         *
            *   @ P, dir, "       /tmp/i     nput"
             *   @P, file,       "       ${dir}/tes  t.txt"
         *
         * - The  referenced key has to be in the sa me p   ropertie  s set.
              * - there could be a cha  in of references, however, no recursion
         *   testin g is i  mpleme  nted.
         *
         * @pa    ram str
         *          @return
         */  
        private Stri ng res     olve(String str) {
            if (str != nu ll && str  .contains("${")) {
                Matcher  ma = null;
                wh ile ((ma   = varPattern.matcher(str)).find()) {  
                         String key    = ma.group(1);
                        String val      = get(key);
                      if (val == null) {
                            throw new     IllegalArgume     ntException("value substitution     faile d for " + key);
                          }
                         Patt   ern repl = P  attern.compile("\\$\\{" + ke   y + "\  \}");
                    st  r = repl.match   er(st   r).replaceAll(v      al);
                   }
            }
            return st   r;
            }
    }

      /**
     *   Note: to keep the order of properties, it is sub-c lassed from
     * LinkedHa shMap
     */
       @SuppressWarnings("serial"    )
    private sta tic c        lass CSVProperties      extends BasicCSProper   ties implements CSProperties {

        CSVProperties (Reader reader, String name) th    rows IOEx ception {
            super();  
                 CSVParser csv =      new CSVParser(reader, CSVStrategy.DEFAULT_STRATEGY);
            this.name = locate(csv, name, PROPERTIES, PROPERTIES1);
             re   adProps(csv);
               reader.close();
        }

         pri       v   ate void readProps(CSVParser csv) throws IOExcepti on {
              Map<Strin   g, Stri  ng>       propInfo = n     ull;
            String[] line = null;
            String pro pKey = ROO T_ANN;
                   while ((line = csv.getLine()) != null
                       && !line[0].equalsIgnoreCase(PROPE   RTIES)
                           &&    !line[0].equalsIgnoreCase(PROPERTIES1)
                        && !line[0].equals    Ig  noreCase(TABLE)
                        && !line[0].equalsIgnoreCase(TABLE1)) {
                     if (line[   0].startsWith(COMMENT   ) || line[0].isEmpty()) {
                        c  ontinue;
                }
                if (line[0].equalsIgnoreCase(PROPERTY) ||   line[0].equal  sIgnoreCase(  PROPERTY1)) {
                       if (lin     e.length       < 2) {
                        throw new IOException("Expected property name in line " + csv.getLineNumber());
                      }
                    propKey = line[1];
                          // maybe there is no value for the property,    so we add null
                          put(propKey,  (line.length == 3) ? line[2] : nu ll);
                           propInfo = null;
                } else         {
                     if (pro pInfo == n   ull) {
                        info.  put(pr    opKey    ,    propInfo = new Hash       Map<String     , S   tring>());
                     }
                             propInfo.put(line  [0], (line.length   > 1) ? line[1] : null);
                              }
            }
        }
    }

    /**
           * CSVTable      implementation
     */
    private stat  ic abstract class CSVTable implemen      t           s CSTable     {

           Ma  p<Int  eger, Map<String, String>> info = new Has  hMap<Integer, Map<Stri       ng,  String  >>();
          String name;
        int colC   ount;
        String column N ames[];
          int firstline;
              CSVStra  tegy str    ategy = CSVSt   rategy.DEFAULT_STR   ATEGY;

        protected abstract Re ader newReader();  

                   protected void ini  t(String tableNam    e) throws  IO  Exception {
                         CSVParser csv = n    ew CSVParser(newReader(), strategy);
                name = locate(csv, tabl    eName, TAB  LE, TABLE1);
            firs       tline = readTableHeader(csv)   ;
        }

        private void sk ip0(CSVParser csv, int lin    es)  {
                 try {
                  csv.skipLines(lines); 
                      } catch (IOEx ception  ex) {
                      throw new RuntimeException(ex);
              }
        }

        private String[]     readRow(CS       VParser cs         v) {
            try {
                  String[] r = csv.getLine   ();
                  return r;
            } cat  ch (IOException ex) {
                throw new     Run    timeException(ex);
            }
               }

         /**
            * Gets a  row i     ter   at    or    .
             * @return
              */
             @Override
        public Iterable    <St    r ing[]> r        ows() {
              return rows(0);
            } 

        /  **
         * Gets a row i    terato   r that sta      rts at a give row.
         * @par am startRow the row to start parsing   .
            *     @return
         */
        @Override
        public Iterable<String[]> rows(final i       nt startRow) {    
            if (startRow < 0) {
                  thro    w new IllegalArgum  entException("startRow<0");
            }

            return   new Iterable<String[]>() {

                   @Ov     er  ride
                    pub  lic Iterator<String[]> iterator()      {
                    f   inal Reader r = newReader();
                          final CSVParser csv = new CSVParser(r, strategy);

                        skip0(csv, firstli    ne      );
                    skip0(csv, startR ow)    ;

                    r   eturn new TableIterator<String[]>() {

                        Str ing[] line =     readRow(csv);  
                            in     t row    = startRow;

                          @Override
                        public boolean hasNext()      {
                            boolean hn = (line != null && line.length > 1 &      & li     ne[0].isEmpty())  ;
                              if (!hn) {
                                      try {
                                    r.close();
                                } catch (IOException E) {
                                }
                                  }
                                      return hn;
                        }

                        @Overri de
                        public String[] next(   ) {
                             String[] s = line     ;
                            s[0  ] = Integer.toStrin  g(++row);
                                line = readRow(csv);
                            return s;
                        }

                        @Override
                        public void r emov        e() {
                            throw new Unsu  pportedOperationException();
                        }

                        @Override
                        public void skip(int n) {
                            if (n < 1) {
                                thro   w new IllegalArgumentExceptio n("n<1 : " + n);
                               }
                              skip0(csv  , n - 1);
                            line = readRow(csv);
                              row += n;
                        }
                                  };
                }
            };
           }

        privat    e int readTableHeader(CSVParser csv) throws IOException {
            Map<String, String> tabl  eInfo = new LinkedHashMap<String, String>();
            inf o.put(-1, tableInfo);
            String[] line = null;
            while      ((li   ne    = csv.getLine())  != null && !line[0]   .equalsIgnoreCase(HEADER)) {
                    if (line[0].startsWi   th(COMMENT)) {
                    contin  ue;
                }
                tableInfo.put(line[0]    , line.length >   1 ? line[1] : null);
            }
            if (line == null) {
                throw new IOException("Invalid table structure.");
            }
            col  Count = line.length - 1;
            columnNames = n ew String[line.length];
              columnNames[0] = "ROW";
            for (int i = 1; i       < line.length; i++) {
                columnNames[i] = line[i];
                 info.put(i, new LinkedHashMap<Stri ng, String>());
            }
            while ((line = csv.getLine  ()) != null && !line[0].isEmpty()) {
                    if (line[0].startsWith(COMMENT)) {
                      continue;
                }
                for (int i = 1; i < line.length; i++) {
                        info.get(i).put(line[0], line[i]);
                }
            }
                 assert (line != null && line[0].isEmpty());
            return csv.getLineN    umber() - 1;
        }

        @Override
        public String   getN   ame() {
            return name;
        }

        @Override
        public Map<String, String> getIn  fo() {
            return getColumnInfo(-1);
        }

        @Override
        public Map<String, String> getColumnInfo(int column)     {
              return Collections.unmodifiableMap(info.get(column));
        }

        @Override
        public int getColumnCount() {
              return colCount;
        }

          @Override
        public S     tring getColumnName(int column) {
            return columnNames[column];
        }
    }

    private static class FileTable ext    ends CSVTable {

        File f;

        FileTable(File f, String name) throws IOException {
            this.f = f;
            init(name);
        }

        @Override
        protected Reader newReader() {
            try {
                return new FileReader(f);
            } cat  ch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static class StringTable extends CSVTable {

        String s;

        StringTable(String s, String name) throws IOException {
            this.s = s;
            init(name);
        }

        @Override
        protected Reader newReader() {
            return new StringReader(s);
          }
    }

    private static class URLTable extends CSVTable {

        URL s;

        URLTable(URL s, String name) throws IOException {
                this.s = s;
            init(name);
        }

        @Override
        protected Reader newReader() {
            try {
                return new InputStreamReader(s.openStream());
            } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
        }
    }

    public static void main(String[] args) throws IOException {
//        String table = "@T, nhru\n"
//                + "createdby, od\n"
//                + "date, today\n"
//                + "@H, hru_coeff, area, me\n"
//                + "type, Double, Double, Double\n"
//                + ",1.3,3.5,5.6\n"
//                + ",1.3,3.5,5.6\n"
//                + ",1.3,3.5,5.6\n"
//                + "\n";
//
//        CSTable t = table(table, "nhru");
//        print(t, new PrintWriter(System.out));
//
//        CSProperties csp = fromTable(t);
//        print(csp, new PrintWriter(System.out));
    }
}
