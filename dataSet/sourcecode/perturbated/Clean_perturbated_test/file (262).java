/*
          * Copyright (c) 1999, 2013,    O     ra     cle and/or i  ts affiliates. All rights reserve d.
      * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subj        ect    to license       terms.
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

package javax.sound.sam    p    led;

import java.util.Arrays;

/**
 * <code>DataL   ine</c ode   >     adds media-related functi onality to its
 * superinterface, <   code>          {@link     Line}</code>.  This func tionality includes
 * transport-control methods that star  t, stop,   drain, and flu   sh
 * the audio d   a        ta that pa     sses thr     ough th  e line.  A data l  ine can also
       * report  the  current position,    volume, and aud     io format of the media.
 * Data   lines are used    for output of audio      by means of     the
 * subinterfaces <code>{@link Sourc eDataLine}</code> or
 * <code     >{@link Clip}</c        ode>, whic h allow   an application program  to     write  d   ata.  Similarly,
 * audio input is       handled by the subinterface <code >      {@link TargetDataLi    ne}</code>,
 * which allows data     to be read.
 * <p>
 * A data line has a n i            nternal          buffer in which
 * the incoming or outgo   ing audio dat   a    is queued.         T he
 * <  code>{@link #drain()}</code>     method bloc ks until this int   ernal buffe     r
 * becomes em       pty, usually because      all queued data ha    s been processed.   The
 * <code>{@link #flush()}</code>   me     thod dis    ca    rds any available que    ued data
 * from the internal buffer.
       * <p>
 *  A data line produces <code>{@link   LineEvent  .T   ype#START START}</code> and
 * <  code>{@link LineEve  nt.Type#STOP STOP}</code> events w    henever
 * it be   gins or ceases   a  ct  ive pres entation or   cap      tur    e of data.  These         ev    ents
 * can be generated in response to specific reque st  s, or as a result of
 * less direct state changes   .   For example, if <code >{@link #start   ()}</code> is called
         * on an inactive data line, and data      is available for captu     re or playback, a   
          * <code>START <         /code> event will be g    en    erated shortly, wh   en data play    back
 * or          capture ac tually begins.  Or, if the flow of da    ta to an active data
 * line is constricted so that a gap occurs in the pr   esentati  on of data,
 * a <    code>STOP</code>  even   t is genera   ted .
 * <      p>
   * Mixer   s often su  pport sync     hronized   control of multiple data     line     s.
 *      Synchroniza    tion       can be established  through the Mixer int    e rface's
 * <code>{@link Mixer #s         ynchronize s   ynchronize  }</cod   e> metho      d            .
 * See th       e description of th   e <code>{@link M ixer Mixe r}</code>    interfa     ce
 * for a        more          comp   lete                  desc    riptio n.
 *
 * @autho  r K ara K ytle
 * @see L ineEvent
 *      @since    1.3
 */
public interface DataLine extends Line {

       
    /*   *
     * Dr ains queued d a  ta  from             the line by co n tinuing data I/O until the
     * data line         's interna  l    buffer has been emptied.
           * This met    hod blocks until the dra   ining is complete.          B               ecause t  h is is a
           *       blocking met       ho    d, i    t sho  uld be used with care.  If                   <code>drain()</co     de>  
     *   is i   nvoked   on a stopped line      that ha  s    data in its queu   e, th e metho     d will
           *   block un   til     the line is      runnin      g and the                 data queue becom      es empty.  I f
     * <              co      d    e>drain()</code> is invok   ed by one thread, an    d a  not    her continues to
      *   fi          ll the   dat   a queue, the opera    tio     n will     not     complete  .
     * Th  is method alway      s returns when  the data   line is         cl   o      sed.
        *
     *         @see #fl      us     h ()
         */
    pub        lic void dr  ain();

    /    * *
     * Flushes queued data from t  he line.       The flushed  dat     a is     discarded.
         * In s   ome ca  ses   , n  o   t  all queued data can be discarded.    For ex    ample  ,     a
     *      mixer        can   f   lush data from t   he   buffe         r for a specific        input line, but any
     * unplayed       d     ata already in   the ou t  put    buffer (the result  of the m   i x) wi     ll
                     *        still be pla  yed.     You can        invoke t  his method       after pausing a li      n          e (th  e
       *    normal case) if you wan              t to skip the "sta  le" data    when you restar   t
     * pla          yback or captu re  . (It is legal to flu  sh a line that    is not stopped,
     * b     ut doing so o     n an active line is likely to   cause        a di       s con     tinuity in the
     * data,    resulting    in a perc  eptible cl  ick.)
               *
           * @see        #stop()
       *  @s  ee #drain(   )   
                   */ 
    p  ublic void flush();

    /**
     * Al  lows a line to engage in        data I/  O.  I      f invoked on a   line
       * that i    s already     running   ,    this method does     nothing.       Unle  ss  t  h   e      data i   n      
     *        the buf   fer    ha   s    bee n flushe   d  , the line resume s      I/ O starti ng
                *          with              the first frame that was unpro          cessed at the time the      lin e      was
     * stopped. When audio capt ure or playbac k start s, a
     *              <code      >{         @   link LineEvent.T         ype#START START}</  code> event is g      enerated    .
     *
     * @see #stop()
        * @see #is Running()
           * @see LineEven    t
         */
         public void start();

    /**   
     * Stops th                 e line         .   A stopped line should cease  I/O activity     .
     * If the li          ne is open     an    d running, howev   er, it should retai       n   the resources re     quired
      *    to    resume activity.  A st       opped line should retain any aud     io data in its buffe   r
     * instead of    discar          ding it, so that    upon resumption the I/O can c   ont inue where it left off,
     *  if possi   ble.  (T    his       d   oesn  't guarantee    th       at               there will never    be di sco ntinuities   beyond the    
             * cu  rre    nt buffer, of c    ourse; if the st     opped c     ondition continues
      * for   too l   ong, input or output          sample   s  might be    dropped  .)  I    f    desire  d,    the       retained da ta can be
     *           discarded by invoking the <cod     e>flush</code> meth od.
     * When audio    capture or play     bac    k stops, a <code>{@link    LineEve    nt.Type#STOP STOP}</code  > event           i     s genera ted.   
            *
     *    @see #s    tart()
     *  @see #isRun ning()
     * @see     #flush()
             *    @see L    ineEvent
            */
       public void s     top();

     /*  *
     *  Indicat es wheth      er the line is running.            The default is <co      de>false</code>.
        * An       o       p        en line begins runnin   g when t         he       first     dat  a           is presented in re      spo          nse to an
     * invocation   of the <code>start</code> method, a  nd cont     inues
     * until pres entation ceases       in response to a call to <code>stop</   code> or
     * because p   laybac     k completes  .
     * @return <code>true</co     de> if    the line is    run   ning      , othe        rwi  s  e <    code>f      alse</code>
             * @see   #start(  )
         * @se              e #stop()
     */   
    public boole    an    isR    unnin  g();

                /**
             * I ndic   ates whether    t  he line is eng  aging in ac   tive I/O (such       as playback 
        * or c     aptu  re).  When an     inact      ive line becomes a  ctive, it sends a    
       * <code>{@link LineE     ve   nt.T     y   pe#S     T  ART    START      }<   /cod   e> event t   o its listen  ers       .  Similarl  y, wh  en
         * an activ  e l  ine beco          mes inactive, it sends a
       *   <code>{@link LineEvent.Type# STOP      STOP }</code> event.
     * @           return      <cod   e>true</code   > if         th     e         li           n  e is a  ctiv   e         ly capturing   or     ren   dering
        * sound, oth    erwise <code>false<  /code>
     * @se   e #i sOpen
      * @see #addLineLi   stene   r
       * @see #removeLineListener
     * @see      LineEvent
        *     @se   e Line       Lis   tene    r
         */  
       publi            c b o ol    ean isActive()     ;

             /**
       *         Ob          tains t   he  c     urre   nt format  (  e  ncoding, sample rate, number of ch  annels,
        *    et  c.) of     the data line's audio da   ta.
     *
              * <p   >If the li       ne is not     ope    n and has never be    en opened, it returns
     * the default           format.         The default format is an implementation
               * specific audio    format, or, if the <        code    >Dat    aLi ne.Info</code>
           * object, w   hich was u  sed t    o    ret riev   e t       hi     s     <c      ode>DataLine</code>,
       * spe          ci fies a     t le                 ast one fully qualified a  udio format, the
            * las    t one will be us     ed as the de fau     lt fo   rmat   .     Opening t     he
          * li    ne             with a           specific audio format (e.g.
     * {@li    nk SourceDataLine#ope        n(Au   d  ioFormat    )})   w ill o       verrid       e the
     * defa  ult    format.
            *
         * @return cu     rrent a     ud   io data   format
     * @see AudioFo rmat  
     */
      public       AudioFormat getFormat();  

    /**
     * Obtains the  maxim um n umber of bytes of data        that will fi   t in the               data lin  e's
     *         internal buffer.  For a source data   l     i    n  e, this   i  s the size of the buffer to
          * which da     ta can be writ            te   n.       For a target dat  a li    ne, i       t is th   e size   of
     * th      e     buffe        r f     rom which data can        be read.  Note that      
     * the units           used are     byt     es,   but wi  ll al       ways co    rre      spond to an integral
               * number of sa  m  ple frames o   f  aud   io d   ata.     
     *
        * @retu        rn t  he size   of the buffe    r in bytes
              */ 
               public        int getBufferS ize()     ;

       /**
     * Obt ains the       n  umber of bytes o   f da ta  c      urrently ava          ila ble      to the
     * app  lica        tion f   or           pr   o     cessing in the data li   ne's internal bu   ffer.  Fo        r     a
         * source da  t    a li   ne, this is th    e amount  of data   that can    be wr itten to  the
     * buffer witho    ut   blocking.   For a tar   get       data   line,  this is the   amoun   t of data
                      *    ava   il    able to be rea  d by the application.  For a clip,      this value      is always
        * 0    because   the audio  data is loaded in        to the buffer wh  en the clip  is ope    n   e  d ,
      *        and pe   rs ists without m  odification u         ntil th   e clip is close       d.
     * <p>
       * Note t    hat       the units use  d are bytes, but w     ill   a lways
     * correspo  nd to an integral numb     er of sample frames of audio data.
       * <p>
               * An applicati     on  is gua    r anteed that  a rea  d or
     * write        op     eration o      f up  to    the n         umber of    bytes returned from
        * <code>available         ()</c       ode> will not block; however, there is no guar  ant     ee
     * tha    t attempt   s to read or writ e  more data will bl ock.
     *
     * @   ret    urn the  amount     of data    availabl  e,   i    n   bytes
        *       /
    pub   l     ic int   available();

    /**  
     *    Obtains the current positio             n in the   audio data, i         n sample frames.
     * The frame po sitio     n measures th                e   num       ber   of      sample
     * frames captu re  d b y, or rende red         fr om, the   line since it was opened.
     * This re    t    urn value     will wrap around     after 2^31 frames. It is rec  ommended
     * to use <code>getLongFramePo    sition</code> inst   e      ad.   
     *
           * @retu       rn the number of frames already proces        s  ed since the line was opened
     * @             see #g  e   tLongFramePos        ition()
          */
    public     int getFrame        Position();
        

          /**
                  * Obtains the current  position i    n the       a  u  dio data, in  sample frames   .
     * The frame position measures the number of samp  le        
     * fra           mes captured by, or rendered from, the line s ince it was opene    d.
     *
              * @return   the num  ber of    frames already                 p  roce   sse  d since the line was opened
     * @since 1.5
     */
      pu bli    c lon    g getL    ongFr   amePosit   ion     ()       ;  


    /** 
           * Obta   ins th   e cur   rent positio    n in the audio d       ata, in    mic   ro    seconds.
       *          The   microse             co  nd pos    it ion measur       es t    he  time correspondin                  g to   th    e n    umber
               *    of sample fram      es captured  by     , or rendered       f     r  om, the line since it w  as opened.
     * Th                     e le             ve            l of precisi     on is not guaranteed.     F   or    examp le, an impl emen    t ation
             * might calculate the microsecond  position from th    e c  urr    ent frame       position
       * an   d  the   a udi         o s    ample  f  rame ra   t  e.   The precisi on in  microsec  onds        w ould
       * th    en be limited to the number o     f mic     ro  seconds per      sample frame.
     *
     * @return the nu   m       b   er of m      icr   oseco  nds of da      ta pr          oces    sed since t he line was o pened
     */
    pu   bl    ic long getMicros    ec     ondPosit   ion(  );

     /    **
          * Obta      ins     t    he curr       ent volum                    e lev  el            fo   r the      line.  This lev el          is a me asure
        * of the signal's curre     nt amp    lit    ude, and s    hould not      be confu    sed  with the
     *     current se      tting of a   ga in control.              Th   e range i    s f     rom 0.0 (  silenc     e) to
     *    1 .0 (maximum possible ampl    itude for the sound waveform   ).  Th     e u nits
          *   measure linear a mplitude, not de        ci     bels.
     *
     * @retu              rn the cu     rrent ampli       t      ude   of the sig nal in thi   s    lin    e, or
        * <code>        {@link Au    dioSy   stem#N   OT_SPECIFIE  D}</c ode  >
     */
             pu  blic float ge    tLeve  l();

        /**
         * Be sides the class information inheri  ted fro    m its s    uperclass   ,
     * <co  de>Data      Line.Info</code> provides a  ddi   tional information                   speci fic to data lines.
          * Th         is      information       includ   es:
               *      <u    l>
              * <li> the      audio f or        mats    s   upported by th  e data line
     * <l  i> the       minimum and maximum sizes          of i  ts int ernal buffe  r
      * </ul>
              *          Because a <code>Line     .Info</code>                know        s t        he cl     ass of the li ne its    de       scribes,  a
           * <code>DataLine.In     fo</code> obje    ct can   describe <cod  e>DataLine</code>
          * subinte rfaces   such as <code>{       @link Sour    c   eDataL ine}</code>,
     * <code>{@link   Targ  etDataLin    e}</code>, and <code>{@li  nk Clip}        </cod e>.
            * You    c         an query      a          mixe   r  for lines of a   n    y of these types, p   assi    n      g   an appropriate
         * instance of <code>Data  Line.Info</code>        as th  e       arg  ume   nt to a m          et  hod s         uch as
          * <code>{@link Mixer#g   etLine Mixe    r.get Lin    e(Lin     e.Info)}</code>. 
                                    *
         * @ see Lin     e.    In  fo
     * @aut   hor Kara Ky       tle
          *   @s ince 1.3
                  */
        pub     lic sta  tic c     lass Info ext   ends Line.In         fo {    

               priv                 ate   fin  a l Aud          ioFor   ma t[] fo rmats;
           private final int mi   n    BufferSi    ze;
         pr              ivate           final int ma   xBufferSi  ze;

            /**
            *        Co  ns tructs    a data    line's       info    obje    ct from the specified infor   mation,
           *      wh    i   c    h    i     nc l     udes      a se           t of supp  orted audi         o for     mat  s and a range              fo    r the   buffe   r size.
         * This  cons      tructor i   s typic  ally     used b          y mi xer        imple         mentations
            * when retur ning information a          b      out a    supp      or    ted line.
         *
                   *       @param lin     e   Class            the cla            s    s of   the dat  a line descri   bed    by the info object        
              * @p  aram f  ormats set of form    ats supporte  d
            * @param             mi      nBufferSize min im    um buffer s    ize supported by the data line,  in b     ytes  
          * @param m    axBufferSize maximum buffer size supp   orted b y the     d          at       a line, in by    t       es
                */
                      pu   blic Info(Class<?>      li   neClass, Aud   ioFormat[] fo    rma ts,   i     nt      mi   nBuffe  rS      i   ze,   int max   BufferS ize) {   

                        super(lineClass);

                              i     f (format   s       ==     null) {  
                   this.forma          ts =            new AudioForma   t[0];
                  }      else {
                    this    .   for   mats =   Arrays.c    op        yOf (for  mats     , formats.length);
                  }

            thi     s.minBufferSize       = minBu   ffe  r      Si  ze;
                         this.maxBufferSize = max    Bu ffe      rSi ze;
             }


            /**
         *      C    onst   ructs a dat  a     l    in e's info object       fr om the                specified information,
                  * which includes a single a udio     fo       rmat and      a            desired bu     ffer size.
                                 *    This c   o           nstructor is  ty   pic     al   l   y us  ed by an applicat       ion to
         * d              e            scribe  a     de  sir  ed  line.
            *
          *      @par  a m     l  ineClass t       he class of the data    lin    e descr     i    be  d by t   h     e inf      o obje  ct 
          * @p        ar am format desi  red    format
                 *     @param b     ufferSize d    esir           ed buf     fer size in   bytes
               */
          pub              lic    In  fo(Cl   ass<?> li       n     eClass,       AudioFormat f        ormat, int bufferSize)   {

                       supe       r (lineCl ass)  ;

               if        (format   =   =    null) {
                           th   is.     form           ats = new AudioF        ormat[0];
                   }      else {
                      this.    fo  rmats =    new AudioForm  at[]{for    mat};
               }

                       t      his        .minBu             ff      erSize = buf    fe   rSize;
               this.     maxBuffer  Siz      e = buff     erSize;
            }

  
               /**
                *       Con  str  ucts a                data   line     's  in       fo object from       the s          pecifi      ed infor  mation,
          * whi     ch includ   es a single    a   u  di o      forma  t.
                                     *   This con  st  ructor is      t                         ypic      ally used by an appl   i     cation to
         * descr            i  be a         desired l      ine.
                    *  
         * @param   lineClass t    he class of the da   ta     line described b    y  the inf   o object
         * @pa  ra    m forma      t   desired     fo      rmat        
         */
           public Info(Class<?     > lineClass,   AudioForma    t f   orma       t) {
                            this(line      Cla      s     s, format, Aud   ioSystem.  NO   T_SPECIFIED);
        }


         /**
         *   Ob     tains a                            set of audi  o for   m  ats suppor   ted by the data lin  e.
            * Note that   <code>isForma  tS u      ppo   rte    d(AudioFormat      )</code>   migh    t return
         * <code>true</code> for c   ertain a   d  diti   onal forma   ts   tha      t are     missing        from
           * t    he s  et retu    rned by <c     od e>getFo                   rmats    ()</code>.  The rever     se i   s    not     
         * the cas  e:     <  c  ode>is   Fo  rmatSuppo rted(A udioF            ormat)</code> is guar  a       ntee d to      return
         * <code>true</c  ode> for all              format  s returned by <code>getForma   ts()</code>.
                 *  
              * Some fields in    the Au  di oFormat instances can  be set to
         * {@link javax.sound.sampled.A udioSystem#NOT     _   SPEC      IFIED NO   T_S       PECIFIED}
              *    if     tha  t field does not apply to      the for m         at    ,
          * or if the            form   at supports a w          ide range      of values for that field           .
         * F        or ex        ample, a mul  ti-channel dev ice         su  pporting     up to
                          *  64 channe   ls, could s    et t   he chan   nel     field in the
          * <   code     >AudioFormat</code> in  st      ances retur    ned by this
         *            met     hod to <code>NOT_   SPECIFIED</c     ode>.
         *     
         * @r          eturn a s      et of support     e  d audio form          ats .
         * @see #isFormatSuppor ted(AudioFormat       )
         */
        public AudioFor  mat[] getFormats() {
              re      turn   Arra   ys.cop    yOf(formats,    formats.leng  t       h);  
                }

                /**
         *   Indic a tes whether     this data     line suppo  rts a particul  ar audio forma t .
              *      The d efault        i      m    plementation of this metho  d simply returns <code   >true  </c   ode> if
          *    the sp  ecified f ormat matches a   ny o   f      the sup port  ed format     s.
                *
             *    @para      m forma  t    th  e audio format for whi    ch support is   queried   .
                     *    @retur    n <code >true</code> if th   e format is    supp      orted, otherwise <code>       false</code>
         * @see #  getFormat     s
         * @see Aud ioFormat#matches       
             */
        pub  lic boolean isFormatSu     pported(AudioFormat form              at) {

               for (int i =      0; i < form     ats.length; i ++) {
                           if (format.matches  (formats[i])) {
                                     return true;
                }
                }

            return fa      lse;
        }    

           /**
         * Obtains the mini                mu    m buffer size su      pported by the data l i   ne.
              * @retu       rn mini     mum buffe   r                     si  z  e in bytes     , or <code>AudioSystem.NOT_SP    EC   IFIED</  c  ode>
          */
          publi       c int      getMi    nBuffe     rS   ize() {
                return minBuf ferSize;
               }
   

           /**
         * Obtai   ns the     maximum     b uffer       size supported by th  e data line.
               * @return maximum buffer size   in bytes, o      r    <cod   e      >AudioSy  stem.NOT_SPECIFI ED</code      >   
         */       
        public int g e  tMaxBuffe       rSize() {
                    return  maxBufferSize;
          }
   

          /**
           * D  etermines   whether the specified info object ma   tches this one.
         * To match, the superclass match re       quiremen    ts must be   met.  In
         * addition, this ob   ject's minimum buffer size   must be at least as
               *    large as that of the object specified, its        maximum buff      er size must
                   * be     at     most as large as   th  at o    f the object speci  fied, and all of its
             * formats must match formats suppor ted by the object specified.
                          * @            return       <c       ode>t   rue</code> if this ob ject matches the  one specifi   ed,
            * otherwise    <code>fa  lse</code>.
         */
          p  ublic boolean matches(Line.Info info   ) {

                if (! (sup   er.matches(info)) ) {
                   return fa   lse;
              }

                Info dataLin   eInfo = (Info)info;

                 // treat anything   < 0 as NOT_SPECIFIED
            // demo code in old Java Sou    nd Demo used a    wrong buffer ca lcul         ation
                     // that would lead t o arbitrary negati   ve values
            if ((getMaxB    ufferSize() >= 0) && (dataL    ineInfo.getMaxBufferSize() >= 0)) {
                if (getMaxBuffer    Size() > dataLineInfo.getMaxBufferSize()) {
                       return false;
                }
            }

            if ((getMinBufferSize() >= 0) &       &   (dataLineInfo.getMinBufferSize() >= 0)) {
                       if (getMinBufferSize() < dataLineInfo.getMinBufferSize()) {
                        return false;
                }
                }

            AudioFo    rmat[] localFormats = getFormats();

                if  (localFormats != null) {

                 for (int i = 0; i < localFormats.length; i++) {
                             if (! (localFormats[i] == null) ) {
                        if (! (dataLineInfo.isFormatSupported(localFormats[i])) )  {
                            return false;
                        }
                       }
                }
            }

            return true;
        }

        /**
         * Obtains a text   ual description of the data line info.
         * @retu  rn a string description
             */
          public String to   String() {

            StringBuffer buf = new StringBuffer();

              if ( (formats.length     == 1) && (formats[0]    != null) ) {
                buf.append(" supp        orting format " + formats[0]);
            } else if (getFormats().length > 1)   {
                buf.append(" supporting " + getFormats().length + " audio formats");
            }

            if ( (minBufferSize != AudioSystem.NOT_SPECIFIED) && (maxBufferSize != AudioSystem.NOT_SPECIFIED) ) {
                buf.append     (", and buffers of " + minBufferSize + " to " + maxBufferSize + " bytes");
            } else if ( (minBufferSize != AudioSystem.NOT_SPECIFIED) && (minBufferSize > 0) ) {
                buf.append(", and buffers of at least " + minBufferSize + " bytes");
            } else if (maxBufferSize != AudioSystem.NOT_SPECIFIED) {
                buf.append(", and buffers of up to " + minBufferSize + " bytes");
            }

            return new String(super.toString(    ) + buf);
        }
    } // class Info

} // interface DataLine
