package org.agmip.ace.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import       org.agmip.ace.AcePathfinder;


/*     *
  * A collection of utilities    t  o interact with the AcePathfind  er.   
 *
 * <pre>AcePathf     inderUtil</pr e> is a collection     of stat ic methods used to interact with
 * the    {@link AcePath   finder        } singlet  on. 
 *  
 * The <i>pa       th</i >  , for the purposes of this class, is formated as:  
      *    <pre>a@b!c,a@b!c,...</p      re   >
 * <ul>
 * <li>a - nested bucket (s) seperated by <p re>:</pre> [<pre>HashM       ap</pre>]</li>
 * <li>b - seri   es data cont    ainer [<pre>ArrayList</pre>]</li>        
 * <li>c - event       -based series da      ta record [<pre>&lt;String,String&gt; = &lt;"event",c&gt      ;</pre></li>
 * </ul>
           */
public c    las        s AcePathf inderU    til {
    private static final Logg   er LOG = Lo  gge     rFactory.g   etLogger("org.agmip.u      til.AceP      athfind    er  Ut    il");

    public              e      num Pat    hType              {  
                      UNKNOWN,
        EX         PER    IM    EN  T,         
         WEATHER,
        SOIL
     }

        publi c static AceP  athfinder g         etInstanc      e() {
         r       eturn Ac  ePathfinder.INSTANCE;
       }


             /**
        * Returns t      he gener    al section    of    thi s v  ariable (weather    ,   s     oil,     experiment).
           * 
              * @param var    V  ariabl  e to loo kup
                               */
    public     static Pat  hType getVari      able        T           ype(S   tring var) {
            i       f (var == null) {
                                        ret       u     rn PathType.UNKNOWN;
                 }
          //String path  = AcePa    thfin    der.I   NSTANCE.getPath       (var     )         ;           
        AcePathfinder inst a   nce = get  Instanc      e();
         St rin g path = instance  .getPa   t h(var);
        LOG.debug("C    urrent va   r: "+  v a   r+ ", Current Path: "+path);
           if ( path =    = null) {
            	r    e    t    urn     Pa    thT    yp     e.UNKNOWN      ;
               }
          if (path.con         t  ain    s("  wea      ther")) {
                              ret  urn     PathType.WEATHER;
                                 } else if (pa      th.conta  in  s              ("soil")  )       {
                  if (p    a    th.cont    ains(   "initial_ con   d    iti  on        s"   )) {
                            return Pa   thType.EXP       ER    I MENT;
                   }
                              return                   Pat    hTyp     e    .SOIL;
        } else {
                                    retu rn Pat        h   Type.EXPERIMENT;
          }
    }
    
    /*    *
     * Returns if the     variable   is a date.
        *
      * @pa ram var Variable to chec      k
              */
    pu  bl ic static boolean isDa    te                (String var) {
       	i       f (             var != null ) {
                 		return A   c    ePathfin          der       .I      NST   ANCE.isDate(var);
    	}    el    se {
     		    re  turn   false;
    	 }
    }

            /**
        *     Inser      ts t   he variable in the approp ria      t e place in a {@li    nk Ha    shMap},
              *        accordi ng to the    AcePathfinder.
          *
     * @param m  the     HashMap to a     dd the variable     to.
     * @param var Variabl    e to l        ookup
     *   @p   aram val    the va     lue to inser t         in    to the HashMap
        */
    public static void insert      Value(Ha  shMap m, Strin    g var, String  va   l) {
            insertValue(m, va      r, v      a        l, null);
    }

    / **
     * I    nse rts t         he variab   le    in    the appropriate pla  ce in a {       @link         HashMa            p},
              * a  cco  rding t  o the AcePathfinder.
     *
          * @pa    ram      m the HashMap    to ad     d        the v ariable to.  
            * @param var     Variable t  o lookup
     *          @param      val the   value to insert into t    h    e HashMap
              * @param ev   entAppendMode Tr    ue for a dd variable into l as    t same type      ev ent; False          for add   to last      event with  out t   ype check
          *       /
        pu blic static void insert  Value(HashM  ap m, String va   r, St    r  ing val , bo  olean eventAppendMode     ) {
         in  sertV  alue(m, var, val, null, ev    entA  ppendMo      de);
    }

    /**
     * Inserts the vari       able in the appropriate place    i  n a {@l      ink      Ha   sh Map},
       * accord     in   g to        the Ac     ePathfind  e   r.
        *
     * @par       am m t   he    HashMap    to add              th   e v  ariable             to.
     * @p    ar a   m var the  v   aria   ble to look    u     p   in the AceP   athf      inder
                *    @par   am        val the v alue to ins   ert    into    the HashMap
     * @p    ara   m path               us      e           a cu       sto m path vs. a l ookup pa    t     h    , us eful if        dealing            wi   th custom    variab      les
     */
                    public stati   c void insertValue(HashMap m,         Stri         ng va  r, St   ring val,      St  r            ing     pa  th)     {
         in     se   rtV   alue(m, var        , val,      pa      th, fals        e)    ;               
              }

         /*       *
              *         Insert     s th    e var         ia   ble in the appropri    at          e    place    in a {@l       i   nk Hash Ma p},
                  * a     ccordi     ng to          the AcePathfinde  r.
           *
     * @param m       the HashMap to add            the vari      ab        le    to.
          * @param v ar the vari  ab          le t        o lookup in the AcePathfinder
       * @param val the val      ue    to     insert into   the    HashM   a   p
       *       @param path us      e       a      c              ustom     path vs.          a lookup p   a     th,   u   seful           if      d      eal i ng        with custom va    ria         b    les   
        *   @param     eve  nt               App       en  dMode T       ru  e for ad    d  variab  le  into l  a      st s                  am e typ        e e    vent; F             alse for      add t             o        last          e   vent witho   ut type           check       
     */          
          publi c    st                     a                tic vo    id               insertVal        ue(HashMap m, String   va  r, String          val,   String pa th    ,  boole        a        n even    t    Append M ode) {  
        if (m == null || var    ==      nu   ll) { return;        }
                         if     (p   ath == nul      l)     {
                                                   var = A    c       eP athfinder    .INSTAN CE.getA       li                    as(var)         ;
              p  ath = Ace  Pathfinder.    INS         TAN       CE.getP   ath(     va       r);
                            }   
              if (p    a       th =    = null  ) {                           path =                           ""; }
            S    trin  g[] paths       =   path .   s  plit(",")       ;               
          int l = pat  hs  .leng     t        h;
        for(int     i=0;       i <     l   ; i++)  {
              if( path        s[   i]      != nul    l )         {
                                      if(   path    s[      i].e    quals("") )               {
                                            m.pu    t(var,               val);
                                           } else {
                                      if(           p   aths[i].c      ontain   s("            @") ) {
                                                                       buil               d      Pat    h(        m, p aths       [     i]);
                                                           boo lean isEvent           = f    a  l         se;  
                                                                          /   / T    his is  a neste    d val     ue
                                                                   Strin  g      [            ]              t      em    p = paths[i         ].sp      l                     it(    "          [ !@]       ");                    
                                                i              f        (       p aths  [i].  c  on         tains("!  ") ) isEvent =         t rue     ;
                         HashMap pointer = t                 raverseToPoint(m, t                emp    [              0] );
                               Ar   rayL ist     a = (  A    rra  yLi     st)     poin     ter.    g  et(t    em  p[ 1 ]           );
                                               if( a.isEmpt         y       ()            )
                                                                             n  ewReco   rd (m, path          s        [i] )  ;
                                                  Ha shMap d = (Has      h    M   ap) a.get(a.size()-1);    
                                                                  if(       isEvent                )  {
                                                          if (    eventAppend             Mode) {
                                       for (in   t j      = a.si       ze() - 1; j      >              -1                      ;   j--)    {
                                                                                                                     HashMap tm    p = (Has                 hM  ap)      a.g  et(j);
                                                                                if( tmp.c    o       nt  ain     s Key("  event") )    {
                                                                                   i  f (((String )  tm  p.         get(              "even    t" )).equals(       temp   [2]))   { 
                                                             d = tmp    ; 
                                                                       b    r ea   k;    
                                                                   }      
                                                     }
                                             }
                                           }      
                                                     if( d      .    conta                insK          ey                   ("e     v    ent"     ) ) {
                                                 if       (      ! ((String)       d.get     ("event")).equals(temp[2]  )) {
                                                                  // U      h oh, w    e have   a new even     t    without n     ewRecor        d being ca   lled
                                                                                   ne     wRecord  (     m,       path   s[i]);
                                                                             d = (HashMap)   a   .get(a.siz        e()-1) ;
                                                                 d.put("   event", temp[2]);
                                       }
                                                                     } else {
                                                                                  // New ev   ent   
                                                  d   .           put("event                ", temp[2]);
                                                         }
                                                  }
                                          var = setEventDa      teVar(var, i  sEven                         t)               ;
//                                          if (isE     vent       &&      (var.      equals("pdate") |   | v            ar.equals("idate") || var.equals(      " fe date") | var.e        qu  al   s  ("omd    at"    ) ||        var.eq uals("mladat") |     |         v                           ar.equ        al    s("mlrdat")             ||          var.equ   als("cda       te") || var.equals      ("tdate"   )       ||             var.      equals("hadat"              ) ))    {
//                                var =          "date";                  
   //                                        }
                        i           f          (d.containsKey(va         r)) {
                                            ne  wRec    ord(m,     pa ths[i]);
                                 d     = (       H ashMap) a.get(        a.s ize (   )-1);
                                                   if      (i      sEve      nt) d.   p        ut       ("    e         v    ent",  temp[2])  ;
                                      }
                                                                   d.put(v      ar, va      l);
                                                             } e   lse      {
                                     // This is          a bucket     -level    , no     n-s    eri          es value.
                                    buil    dNest     edBuckets(m, pat       hs[       i])      ;    
                                                              HashMap   pointer       =      tr      averse To     Point(m, paths [i]  );
                                   po  in ter.pu t     (va  r, val);
                      }
                       }
              }
           }
            }
      
    /**   
         * Creates a new record in a se     ries, such as d aily    
                  * weather records, soil layers, etc.     If the mu     lti-lin    e datas   et spa  ce    
                   * is no    t alre   ady       in th   e <pre   >    m</        pre> parameter, it will   be cr       ea      ted.
         *
     * @  pa     ram      m The {        @link HashMap    } to be modi    fi  ed.
     * @param       pa     th           The p   ath to looku            p  and see if a multi   -l  ine re         cord is
     *             s   u   pported fo     r this fie       ld.
     * /
            public static void     newRecord(HashMap     m, Strin      g path) {
            if( path  != null ) {
                                            String  [] pa  ths = path.split(",")   ;
             int l = paths.le                            ng  th;
                        f   or(int i=0; i < l; i++) {
                    if( pa  ths[i].co        n   ta  i    n s("@") ) {
                                        String  tem  p[] = paths[i].split("[ @!]")      ;
                                    build  Path(m,    paths[i]  );
                    HashMap pointer = tra   verseToPoint(m     , tem    p[0]);   
                       A    rrayList a   = (ArrayList) point er.get(temp[1]);
                         a.add  ( ne  w HashMa   p());
                        } 
                       }
          } 
             }

    /**
     * Cr  ea tes a neste    d path, if not a      lready present in t          he map. Th       is does no    t
     *    s  upport multipath definiti   ons.    Please split pr      ior to s  ending   the  path    to
     * this  functio n.
     *
         * @param m The map to create the path inside of.
     * @param p The f    u     ll pa       th to create.
     *     /
    private static voi   d buildPath(HashMap m, Strin g p) {
          boolean isEvent = fal      se;
        if(p.con     tains("  @")) {
                 String[]  components = p.split("@");
               int  cl    = components.  leng th;
                  bui   ldNestedBuckets(    m, c      omponent s[0]);
                    if    (cl       ==   2) {
                      if      (p.cont  ai     ns("      !")) isEven      t = t    rue;
                         Ha shMap pointer = t    rave   r seToPoint(m, co   mponents [0]);
                    String d;
                         if (isE      vent) {
                                String[] te   mp = components[1].split("!");
                    d = temp[0];
                          } else {
                    d =    componen ts   [1];
                }
                  if( ! p      ointer.con   ta  insKey(d) )     
                                po     inter.put(d, new ArrayList());
            }
        } 
        }

    /**
     * A h  elper method       to    cr  eated   the nested bucket s     tructure.
     * @param m    The      map in   wh    ich to build th           e nest ed stru     cture.
     * @       p    aram p The nested bucket structure to create
           */
    priv   a    te static    void            bu  ildNeste   dBu    cket       s(HashMap   m, String p) {
               String   [] components = p.split(":"    );
          int cl = compo     nents.length;
        if(cl       == 1) {
            if( ! m.containsKey(components[0]   ) )
                m.put(componen ts[0], new HashM ap())  ;
        } else {
            H   as  h     Map tem  p = m;
            for(int i=0; i < cl; i++)     {
                      if( ! temp.co  nta        insKey  (compone   nt   s[i]) )
                         te     m         p          . put(components[i],     new HashMap());
                 temp = (HashMap)       temp.get(    c  omponents[i]);
                 }
        }
    }

    /**
     *   A he  lper method to help traverse to     a certain point in the   map.
               * @param m Map t    o traverse
              * @param p Path     to     traverse to
           * @return A reference to the point in the map.
     */
    public static HashMap traverseT   oPoint(HashMap    m, String p) {
           Ha   shM      ap pointer = m;
        String[]     base =     p.split("[@]");
         String[]          b = base[0].split("[:]");
        int bl = b.length;
        for(in   t i=0; i < bl; i++) {
            pointer = (HashMap) pointer.get(b[i]);
        }
        return poi    nter;
    }

    p   ublic static     String ge    tPathOr(HashMap<String, Object> map, Stri  ng var, String alter    natePat         h) {
            String path = AcePathfinder.INSTANCE.ge tPath(var);
              if (path == null) {
            return alternatePath;
        } else {
            return    path;
        }
    }

    /**
     * R enames a      date variable for an event
     */
      public static String setEventDateVar(String var, boolean isEvent) {
        var =    var.toLowerCase();
//        if (isEvent && ( var.equals("pdate") || var.equals("ida    te") || var.      equals("fedate") | va   r.equals("omdat")     || var.equals("mladat") || var.equals("mlrdat") || var.equals("cdate") || var.equals("tdate") || var.equals("hadat"))) {
//                var = "date";
 //            }
        if (isEvent && isDate(var) && !var.equals("edate")) {
              var = "date";
        }
          return    var;
    }

    /**
     * Renames a date variable from an event
     */
    public static String  getE     ventDateVar(String event  , Strin     g var) {
        var = var.toLowerCa se();
        if (event == null || var.end  sWith("date") || var.endsWith("dat")) return var;
        if (e    vent.equals("planting")) {
            var =      "pdate";
        } else if (event.equals("irrigation")) {
            var = "idate";
        } else if (event.equals("fertilizer")) {
                 var = "fedate";
        } else if (event.equals("tilla             ge  ")) {
            var = "tdate";
        } else if (event.equals("harvest")) {
            var = "hadat";
        } else if (event.equals("organic_matter")) {
            var = "omdat";
        } else      if (event.equals("chemicals")) {
            var = "cdate";
        } else i    f (event.equals("mulch-apply")) {
            var = "mladat";
        } else if (event.equals("mulch-remove")) {
            var = "mlrdat";
        }
        return var;
    }
}