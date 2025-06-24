/*
 * Copyright (c) 1998,   1999,                  Oracle and/or its aff  iliates. Al l righ   ts reserved.
 * ORACLE     PROPRIETARY/CONFIDENTI     AL. Use is subject to licens    e terms   .
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

package or g.omg .COR   BA;

/** Define    s th     e methods used to rea      d primitive data type  s      from in   put s  treams
*     for unmarsha   ling cu   stom            value types.  This interface is used by user
  * written custom unmarshaling code for custom value types.
*      @see org.omg.CORB  A.  DataOutputStream
*      @see org  .omg.CORB     A.Custo mMars   hal
*/
public interface DataInputStre  am extends org.omg.CORBA.portable.Va  lueBas        e
{
       /*    *      Re     ads an IDL <code>Any</code> value from the input stream.
    * @ret urn  the       <co  de>A   ny</   co   d  e> read          .
    * @throws <code>org.omg.   CORBA.MARSHAL</code>
    *  If a  n     inconsistency is d       et    ected, in  cluding not    h   avin   g  register  e d
           * a streaming polic      y,   then the s  tandar     d s    ystem exception MARSH   AL     is raised. 
    */   
    org.o   mg.CORBA.      Any read    _any ();        

    /**   Reads an IDL bo    olea           n value from the i  nput stream.
    * @return   the       boolean read.
       * @ throws <code>org.omg.COR  BA.MARSHAL</code>
    * If an in                 consistency is detected, i       ncluding not having re          gi  stered
      * a st   reaming       policy, then th    e sta   ndard sys    t  em exception MARSHAL      is raised     .
    */
        boolea  n rea     d_     boolean ();

        /** Rea  ds an IDL character value    from the input   stream.
    *      @return  the ch       aract   er read.
      *     @throws <code>org.omg.CO RBA    .MA  RSHA   L</c  ode>
       * If an       inco   nsistency i          s detected, including n   ot  having regi stered
    * a streamin        g policy, then the              standard sys    tem exception MARSH  AL is raised.   
    */
    char     read_char    ();

    /**      Reads a    n IDL wide character value f   rom   the input strea      m.
        * @return  the wide charact   er re    a d.
    * @th      rows <code>org.omg.CORBA.MARSHAL</code>
    * If a     n inc    onsistency  is   d   etected  ,    including not havin     g registered
        *   a streamin      g  policy,                then the stand ard system except  ion MARSHAL is raised.
    */
    char read_w  cha  r  (   )   ;

      /**   Read        s an IDL octet value from          t he input stre  am.
    * @re          turn  the oct    et val   ue rea      d   .
        * @throws <code>org     .omg.CORBA.MARSHAL</c       ode    >
    * If an inconsistency is detec ted, in    clu  ding not having registered
    *        a streaming         policy, then the standard system excep     tio    n MAR     SHAL is raised.
    */
        byte r       ead_ o    ctet ();

       /   ** Reads   an IDL short from the input str eam.
    * @return  the short r   ead.
    * @th   rows <co     de    >org.omg.            CORBA.MARSHAL   </code>
     * If an incon         sistency is detected    , incl u di       ng not    having regist     ered
    * a streami         ng           policy,   then th  e st  andard system exception        MARSHA  L is raised.
          */
           shor  t re   ad_short ();

             /** Re  ad  s an IDL unsigned short from th          e  input stre am.
    * @r        eturn      the unsigned short   read.
       * @th  rows     <code>org.omg.CORBA   .MARSHAL</    code      >
    *   If an inconsistency is detected, including      not having register   ed
        * a strea    ming policy, t     hen the standard system   except   ion     MARSH    AL is raised.
    */  
        short read_ushort ();

               /** Reads an IDL           long from the input stream.
        *      @return  the long rea     d.
       * @    thro         ws       <c    ode>org.omg.CORBA.MARSH A L</code>
    * I     f an     inc  onsist         ency is             detecte  d, including not having regi    stered
    * a stream     i   ng policy       ,     then the sta     nda   rd   syst      e m exc eption MARSHAL is rai        sed.
    */
    int  read_long ();

        /**  Reads an IDL unsigned long from the i     nput         s  tream.
        * @return  the unsigned long read.
      * @throws <code>org.om g.CORBA.   MARSHAL</code>
    * If an inconsist   ency       is det        ected, i       n     c ludin    g not   having re   gistered
             * a s     tr eaming pol icy, then the stan    d  a     rd system      ex    ception     MARSHAL is raised.
    */
    i     nt read_ulong    ();

       /** Reads an IDL long long   from   t he inpu  t s             tream.   
    * @ret   urn       the l ong long read.
                      * @throws <code>org.o  mg.C    ORBA.MARSHAL</code>
    * If an inconsiste    ncy      is   detec  ted, including not ha  vin  g      registered
                 * a stream      ing policy, then the standa   r    d system excep    tion M  ARSH      AL is raised.
    */
        l  ong       read_lon      g   long ();

               /** Read   s an u nsigned IDL long long f   rom the i nput stream.
       * @return  the unsig   ned long long read.
    * @t      hrows <  code    >org.      omg.CORBA.MARSHA  L</code>
    * If   an incons        istency is detected, including no  t having re  gis tered
     * a strea   ming     pol  icy, then     the    stand ard system exception MA    RSH     AL is r  aised.
       */
    long    read_ulonglong ();

    /** Reads an IDL f  l   oat from        t he input str           eam.
           * @ret      urn   the float read.
    *      @throws <code>org.omg.CORBA.MAR    SHAL</code>
      * If        an inco   nsistency is d  e   tected, including not having   registered
    * a streaming     policy, then      t            he standard syst em ex  ception MARS     HAL is raised.
    */
    float read_f       lo    at ();

    /** Reads an IDL double from the    i    nput stream.
    * @      return  th e             double read.
         * @  throws    <code>org.omg.CORBA.  MARSHAL</cod    e>
    * If an inconsi  stency is detected, in     clu   ding not havi     n         g re      g   istered
    *        a streaming p    olicy,     then      t h  e standard syste m excep   tion      MARSHA   L is rai    sed.
    */
      dou ble read_double   ()   ;
    //   read_lo    ngdouble not supp   orted by IDL/Jav     a mapping

    /** Reads    an IDL st    ring from the input stream.
    * @return  the s   tring rea d.
    * @throw s <code>org.omg.CORBA.MA   RSHAL</code>
    * If a  n    incon   sistency    is detected, includi  ng n  ot having reg istered
    * a streaming p    olicy,    then the standard system  exc   ept i    o    n MARSHAL is       rai            sed.
    */
    S  t ring rea   d  _            string    (     ) ;

    /** Reads an IDL wide string     from the input st     ream.
        * @r           e     turn  t        he w id  e string read.
       * @throws <code>  org.omg.CORBA.M      ARS     HAL</code>
    * If an inconsistency is dete   cted, including not having       registered
    * a streaming policy, t  hen the s tandard    system  excep   t  ion MAR SHAL is rai     sed.
      */
    String read_ws tri   ng ();

    /** Reads an IDL COR    BA::Ob    ject  from the         input   stream.
    * @r eturn  th e CORBA::Object re       a    d.
    * @throws   <   code>org.om   g.CORBA.MARSHAL</code>
    * If an incon   si   s    tenc  y is       detected, i   ncluding not having registered
    * a streami   ng policy, t hen the standard sys tem exception MAR   S      HAL i  s rai   sed.
    */
    org.omg.COR   BA.Object read_O   bject ();

    /** Re       ad    s an IDL   Abstr  act  interfa  ce from t  he  input st   ream.
    * @  r   eturn     the      Abstract int   er    fac      e read.      
         * @throws <code  >org.omg.C     OR BA.MAR SHAL</  code>
    * If an inconsis   tency i       s detect  ed, includi        ng not havi   ng reg      istered
    *   a strea  min g policy    , then the standa         rd syste m exception MARSHAL is       raised.
                   */
    j    ava.lang.Object read_Abst  ract ();

       /** Rea   ds   an IDL   va          lue type from the    input st  ream.
         * @return  the value type read.
    * @throws <code>org.      omg.CORBA.MARSHAL</code>
       * If an inconsistency is detected, including no   t having registered
    * a streami   ng policy   , then the standa   rd system e  xception MARSHA   L is raised.
    */
       java.io.Seriali zable read_Value ();

    /** Reads an ID   L     t      ypecode fr  om   the in      put stream.
    * @return  the ty    p     ecode read.
    * @throws <code>org.omg.CORBA.MARSH    AL</code>
      *    If an inc o nsistency  is d etect  ed, including not hav   ing regist  ered
                    * a streaming    policy, then the standard sy stem e             xcept  ion MARSHA     L is raised  .     
       */
    org.om    g.  CORBA.TypeCode read_TypeCode      ();
         
      /**   Reads   a        rray o f      IDL    Anys from offset for length    elements fr    om the
    * input stream.
    * @param seq The out parameter holder for the array to be read.
    * @par     am offset The index         into   seq of th e first e        lement to rea d from  the
    *  in    p ut stream     .       
    * @param l    e    n       g     t    h The nu  mb    er of elements     to read    from the input  stream.
     * @thr     ows <code>org.omg.COR    BA.MARSHAL</code     >
    * If an inconsis      te     ncy is detect  ed    ,       i   ncl        u  ding not hav   ing               registered
         *          a st     reamin g       policy, then   th      e st         anda   rd system   e    xception   MARSHAL is rai     sed.
    */
    void read_any_array         (org.om g.CORBA.AnySeqHolder se   q, int  offset, int length);

      /* * Reads a  rray of ID   L booleans from offse  t   for length   elements from   the
            *   input stre    am.
    * @param s   e      q       Th   e out parameter holder for the    a    rray    to be r     e   ad    .
       *      @     param of  fs et The i ndex into seq of the first element to read from the
    * inp  ut stream.
     * @p      aram     length The number of el     e   men   ts to rea  d from the inpu   t stream.
      * @throws <code>org.o      mg.CORBA.MAR SHAL</code>
    * If an inconsistency is det ecte d, including not     having registered
    *      a stream i  ng      policy,     then the s tandard system excep   tion MA   RSHAL is    raised.
                   */
          void r   ead_bo   ole         an_array (org.om   g.CORBA.Boo   leanSeqHolder seq, int off set, in    t length   );

        /** R ead  s array o f IDL characte  r  s fro     m offset for lengt h   elements from     the
            * input st      ream.
    * @param s      eq         The    out parame      t  er hol      de        r for the       array   to be read.
                *          @pa     ram offset The i  ndex into seq o    f the f    irst      element to read from the  
    * inp   ut strea      m.
    * @para     m length The number of el   ements to read f       ro     m the    input stream.
    * @throws      <        code>org.omg.                C        ORBA.MA  RSHA  L</code      >
    *      If an inc    onsistenc             y   is detect  ed, in c    luding not ha  ving regi    st ered
          * a     s     treaming pol  icy, then      the s tandard syst         em exception MARSHAL      is raised.
    */
    void        read_    ch      ar    _a rray       (org.omg.CORBA.Ch           arSeqHolder s   eq, int offset, int len     gth);

    /** Reads array      of IDL     wide characters from offset for length elements fr      om t   he
      *  inp     ut s    tream.
    * @para    m seq    The out parameter holder for the array to     be read.
    * @pa       ram of  f set T  he index into se     q of the first     el   ement to  read from   the
    * input s    tre     a   m.      
    * @param leng   th The nu  mber of elements to read f  r  om the          input stream.
    * @throws <code>o   r     g.om   g.  CORBA.M    ARSH   AL</code>
            * If an inconsis     tency i         s detected,  inclu      ding   not having  re  gister     ed
    * a st       reaming    policy, then the standar          d system e    xception MARSH   AL is ra   ised.    
          */
    void       read_wchar_array (org    .omg.CORBA.WCharSeqHolder seq,  int    offset,      int       leng  th   );

       /** Reads array        of I      DL octets from of     f    set fo   r le        ngth elements   from th  e
    * inp   u t strea m.
    * @   para     m seq The out p     a     rame  t     er ho   ld   e  r            for the array to be read.
    * @par   am offset The index       into seq     of t  he first element t   o      read from the
    *    input stream.
          * @para m length    The number of elements to read from the input stream.
    * @throws      <code>org.    omg.COR   BA.MARSHAL</code>
            * If an inconsistency is  detecte   d, including  not    having r     egistered
      *   a      stream  i   ng policy, then the standard     s        yste    m exception MARSHAL is rais   ed.
    */
    void read_octet_ar  ray (org.omg.CORBA.OctetSeqHolder se  q    , int o     ffset, i  nt length);

          /** Reads array of IDL shorts fro        m offset for leng th elements     from  the
    * input               stre am.
              * @param seq The o  ut parameter holder   for the    a    rra    y         to be read.
    * @     param      offset   The index into             seq of the first element t         o      read    from the
    * in  put stream.
        * @pa    ram length The   nu  mber of elem    ents to read fro      m the input stream.
    *      @throws <code>org.o     mg.CORBA.    MARSHAL</co  de>
         * If an          inconsisten  cy      is d  etected, i   ncluding not having                   r      egistered
    * a streaming poli     cy, then the sta          ndard sy  stem    exception MA    RSHAL is ra   ised.
       */
    v  oid read_short _    array (org.omg  .COR   BA.ShortSeq    Holder s  eq, int offset, int l    ength);

    /** Read   s arr   ay of  IDL unsigned  shorts from offset for length elemen ts  from th          e             
         * input   stream.
    * @param s   eq The   out p   aramet   er holder   for the arr   ay     to     be          re  ad.
    * @param of   f   set The index into seq of the first                          element to r   e    ad       from th    e
    * i   nput stream.
    * @param l ength The number of el     ements to read from th  e input strea   m.
       * @           throw  s <c       ode>org.omg.    CORBA.MARSHA   L</c  ode>
       * If an in   c  onsistenc     y is detected, inc  l   uding not having      r     egister   ed
      * a str    eam   ing     policy, the   n the standard sy   stem   exception MARSHAL is ra  ised.
    */
    void     read_us   h    or         t_array      (org .   om   g.CORBA.UShortS    eqHolder seq, int off        set, int     length)  ;
     
    /** Reads arra      y of IDL longs from offset            for length elements   from the
     * input     stream.
    * @      par      am seq The o   ut parameter holder for the array     to be read.
    * @param offset   The        index into s     eq   of the f    irst elem     en   t t   o      read fr   om the
    * input stream.
    * @para     m    length The    number   of elements     to read from the input stream.
    * @throws <code>org.omg.CORBA.MA   RSHAL</code>
    * If       an  inconsistency is detected, including n    ot having registered
    * a strea  ming pol    ic   y, then the standard system exception            MARSHAL is raised.
           */
        void read_long_array (org.omg.CORBA.Lo     ngS eqHolder seq, int    offset, int length);

    /   ** Reads array of IDL  unsign     ed long   s f   rom offset f   or length elements fro  m the
    * input stream.
    * @param  seq The out           parameter holder fo       r the array to be read.
    * @p   a   ram offset The index into seq of the first element to   read from        the  
    * input stream.
    * @param len       gth      The number of elements t  o read from the input strea   m.
          * @throws <code>org.omg.CORBA.M    ARSHAL</code>
       * If an inconsistency is detected, including not        hav   in g regi  stered
    * a streaming policy, then the standard system exception MARSH AL is raised.
    */
       void read_ulong_array (org.omg.CORB A.ULongSeqHolder seq, int   offset,          int          length);

         /** Reads array     of IDL    unsi    gned        long longs from offset for length elements from the
    * input stream.
    * @par    am seq Th    e o ut paramet    er holde      r for the array to b   e read.
    * @param offset The index into s   e     q o    f the f     irst element    to rea        d from th     e
    * input stream.
    * @param length    The number of elements to read from the input stream.      
            * @thro   ws <c  ode>   org.om   g.CORBA.MARSHAL</code> 
    * If    an inconsistency is de     tected, includ   ing not having re   gistered
       * a streaming policy, then    the standard system exception MARSHAL is raised.
     */
    void read_ulonglong_array (org.om     g.CORBA.U     Lo     ngLongS eqHolder seq, int offset, int length);

    /** Reads array of IDL long longs             from offset for length elements from the
    * input s   tream .
    * @param seq The out parameter holder for the array   to     be rea  d.
    * @param offset The in   dex into seq of the f irst element to read from the
    * input stream.
    * @param length T  he number of elements to read from the input stream.
    * @throws <code>org.omg.CORBA.MARSHAL</code>
    * If an inconsistency is detected, inclu     ding not ha          ving regis    tered
    * a streaming policy, then the standard syst em exception MARSHAL is raised.
    */
    void read_lo    nglong_array (org.omg.CORBA.LongLongSeqHolder seq, int offset, int length);

    /** Reads array of IDL floats from offset for lengt   h elements from the
    * input stream.
    * @param seq T  he out   parameter holder for the array to be read    .
    * @param offset The in  dex into seq of the first element to read from the
    * input s   tream.
    *  @param length T   he number of elements to read f  rom the input stream.
       * @throws <cod e>org.omg.CORBA.MARSHAL</code>
    * If an incon   sistency is detected, inclu      ding not having registered
        * a streaming policy, then     the standard system exception MARSHAL is raised.
    */
    void read_float_array (org.omg.CORBA.FloatSeqHolder seq, int offset, int length);

    /** Rea  ds array of IDL doubles from offset for length elements from the
        * input stream.
    * @param seq The out parameter holder fo       r the array to be read.
    * @par am offset The index into seq of the first element to read from the
    * input stream.
    * @param length The number of elements to read from the input stream.
    * @throws <code>org.omg.CORBA.MARSHAL</code>
    * If an inconsistency is detected, including not hav    ing registered
    * a streaming policy, then the standard system exception MARSHAL is raised.
    */
    void read_double_array (org.omg.CORBA.DoubleSeqHolder seq, int offset, int length);
} // interface DataInputStream
