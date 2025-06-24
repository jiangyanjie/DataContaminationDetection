/*
 *      Copyright (c) 2012,      20     13, Oracle a      nd/or its affi li ates. All r            i  ghts reserved.
 *    ORACLE PROPR   IETARY/CONFID    ENTIAL.    U    se i   s subject to licens  e t  erms.    
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
package java.util.stream      ;

import java.util.Objects;
import java.util.Splitera tor;
import j    ava.util.function.IntFunction;
import java.u     til.fu     nctio   n.      Sup  plier;

/**
 * Abstract base c    lass for "pipeline"      classe   s,   which are the   core   
 * i  mplem enta ti      ons of t he Stream interface  a  nd its primitive specia       lizations.
 * Manages constr    u ction an  d evaluation o    f stream p    ipelines   .
 *
 *     <p>An {     @code AbstractP    ipelin  e} represents an      i  nitial portion of       a stream
         *   pipeline, encapsu lating a   stream source and zero o    r more intermediate
 * opera      t     ions.  Th  e individual {@code AbstractPipeline} objects are often
 * referr   ed to as <em>stages</em   >, w   here eac   h stage des  cribes either the stream      
        * source or an interm        ediate operatio     n .           
 *
 * <p>A concre   te intermedi    ate stage is    generally bu        ilt from an
 *     {@code         AbstractPipe     line}, a      sh  ape-spe     cific pipeline class which     extend    s it
 * (e.g., {@code IntPip    eline}) which is a  lso abstract, and an operation-specific
 * concret   e clas   s which          extends    that.  {@co       d      e Abs tr          a      ctPipeline} contains most     o    f
 * the mechan     ics of evaluati ng the pi    peline, and im   plements metho   ds that will      be
 * used    by the      ope         ration; the s  hape-specific cl      asses add helper methods for
 * dealin    g with collection of results into the appropriate sh    ape-specific
 * con   tainers.
 *
 * <p>After     chaining a new inte  rmediate operation, o r execu ting a term  in      al
 * operation, the stre   am is considered   to be consumed, and no more interm   e  di   ate
 * or    termi    nal operations are    permitted on this stream    instance.
       *
 * @implNote
 * <p>    For sequenti  al streams,     and parallel streams without
 * <a  href="package-summary.html#     Strea     mOps">stateful inter   medi    ate
 * o     perations</a>, parallel streams, pip   eline evalua   tion is done i      n a single
   * pass that "jams" a  ll the o     p  erations tog      ether.  For paralle    l    streams w      ith
 * s  tateful operations, execution is di       vide            d into segments, where eac h
 * state ful operat   ions marks     the end   of a segment, a nd each segment is
 * evalua  ted separately and    the result u sed as th     e input to the next
 * se   gment.  In all cases, the source da    ta is not      consu med until a terminal
 *       operation beg   ins.
   *
 * @param <E_    IN>  ty pe of input elements
  *     @param <E_OUT> type of output elements
 * @param <S> type of   the subclass i mplementing {@  c    ode BaseStrea m}
 * @since 1.8
 */
abstract   class AbstractPip e       line<E_IN, E     _OUT, S extends BaseStream    <E_OUT, S>>
                          extends PipelineHelper<E_OUT> implements BaseStream<E_O    UT, S> {
    private        static final Str    ing M      SG_STREAM_LINKED       = "stream has   already    bee n operated up    on   o    r closed";
    priv   ate static final String MSG  _CONSUMED = "source already  consum   ed     or     closed";
     
         /**
          * Back  link t      o the hea d    of the pipeli           ne c  hain  (se lf if this is the sourc e
     * stage).
          *        /
    @Sup  p          ressWar  n      ings("ra w      types")
           private fi  nal               AbstractPipeline sourceS  tag       e;

    /**
     * The "upstrea      m" pipeli    ne,   or nu    ll  if this is the so      urce stage.
        */
    @SuppressWarnings("raw  typ         es")
    private final AbstractPipel    ine previousStage   ;

      /**
           *      The operation fla  gs for th e i     n   terme  diate operation re   pres  ented by this
     * pipe         line object.
     */
         protec   ted final   int   sour    ceOrO           pFlags;
    
    /**
     * The next stage in the     pipelin      e  , or null if        this is the last  stage.
     *  Effect  ively        final a    t t      he point            of linking to the next pipeline      .
               */
     @     SuppressWa   rnings(       "rawtypes")
    privat    e      AbstractP    ipeline n  extStage;

      /**
      * T he   n  u    mber of intermediat e operations b   etw  een this pipe   l     ine ob    ject
     * a    nd the str    eam so   urce if sequential, or th      e   previous statefu    l if     parallel       .
       *     V    alid   at the point of pipeline prepa ration f   or ev    al uation.
     */
             private int depth;

    /**
     * Th  e combined source a        nd operati   on flags f     or the source and all   op   erations
       * up to and i      nclud  in   g th  e opera tion r  epresented by this pipeline ob       ject.
      * Valid   a t the poi    nt of pipeline preparation     for eval         uation.
     */
    priv  ate int combinedFlags  ;

    /**
         * Th  e s    o       ur        ce split           erator. Only valid   f           or the head pipeli ne.
     * Befo  re       the pipeline is consumed if no  n-nu        ll then       {@code sourceSupplier}
     * mus t       be null. After    the      pipeline        is consume d if non- null th         en is set to
         *      null    .
           */
    private Spliterator<   ?     > sou    r      ceSpliter     a   tor  ;

    /**
     * The sourc  e supplier. Onl     y valid  for the     head p ipel  i   n e. Before th e
     * pipeline is consumed if non   -null  then {   @c       od           e    sourceSpl iterator} must be
     * nu   ll.  After th      e    pipeline is   cons       umed if   n on-nu          l    l th      en  is set to null.
       */    
       private Supplier <? extends Split     er  ator<   ?>> s ourceSu   pplier;

            /**
     * True if this pipeline h    a s been link  ed    or consumed
       */
    private   b        oolean linked  OrConsumed;

      /**
     * True if         t    her  e are any stat  eful ops in the pipelin      e; on  ly valid f    or             the
     * source stage.
      */
    private     b  oolean sourceA               nyS        t  a           teful;

     private Ru        nnable sourceCloseAction;

    /**
        * True if pipeline is parallel, othe   rwise the pip  el  ine is      sequent   ial; o nly
     *   va     l            id for the     s   ource       stage.
     */  
       privat       e   boolean par      allel;

    /**
         *   Constructor for the he   ad of a stream pipe  l     ine.
     *
     * @para   m               sourc        e            {@code  Supplier<Spliterator>} describi    ng the            stream sou          rce   
     * @param sourceFlags The source     flags            for            the stream so       urc         e, des          cr ibed  i         n
     * {@l ink Stream  OpFlag}
     * @param pa     rallel    True       if   the pipeline is parallel  
        */
    A bstractPipe  line(Supplier<? e  x    tends   Spliterator<?>> source,
                                       int sourceFlags, bool     ean parallel) {
             this.p  r eviousStag   e = null;
        th  is.sourceS upplier = source;
           th    is.sourceStag          e =       this;
           this.sourceOrOpFl      ags = sourceFlags & S trea    m         OpF   lag.S TREAM_MASK;
        // The f   oll owing is an optimization of:
           // StreamOpFl   ag. combineOpFlags(sourceOrOpFlags, Stre   amOpFlag.INITIAL_OP    S_VALUE);
        this.com         binedFlags      = (~(s         ourceOrOpF l  ags << 1)) & StreamOpFl   ag  .INITIAL_O   PS_V          ALUE;
             this.depth = 0;
                this.parallel    = pa    ralle  l       ;
        }

    /*      *
     * Const  ructor for the   head   of      a str eam pipeli       ne.
     *   
       * @param     sour          ce {@c ode Splite        ra   tor} des  cribi ng t  he    stream source
     * @param sourceFlag  s the s    our   c e flags for the strea   m    source, described           in
        * {@link   StreamO  pFlag}
       *     @para      m paralle     l {  @code true}   if the           p          ipeline is parallel
     */
         Ab stractPipe lin    e(Spli   terator<?> source,
                           int sour    ceFlags, boolean parallel)   {
        this.pre viousStage =     n  ull;
        this.so urceSpliterat   or = source;
        t     his.sourceSta     g  e = this;
                      th    i s.sou    rceOrOpFlags = source  F     lags &   St      reamOpFlag.STREAM_   MAS    K;
        // The following is an op       timizat     ion of:
        // StreamOpF   lag.        combineOp    Flags(so      urceOrOp       Flags, St     reamOpF lag.INITIA  L_OPS_VALUE);
        this .combinedFlags              = (~(so      urceOrOpFlags              << 1)) & StreamO    pFlag.INITIA     L_   OPS_VALUE;
             thi         s.depth = 0     ;     
        this.parallel = parall   el;
    }

      /**
     * Const   ructo          r for appending an  int   ermed  ia     te oper  atio n stage onto an
     * existing p  ipeline.
     *
         *      @param previousStage         the upstream p      ipe line stage   
     * @      par  am opFlags the          oper  ation flags for t       he      new stage, de   s    cribed in
              * {@li       nk StreamOpFlag}    
     */
     A   bstra         ctPipel ine(AbstractPi    peline<?, E_  IN, ?> previousSta    ge, in      t op     Flags) {
          if (   previousS        tage.linke       dOrConsu  med)
            throw new IllegalState          Exce  ption(MSG_STREAM_  LINKED)         ;   
                previo usStage.linkedOrCon   sumed = true;
             previousStage.ne xtStage = this    ;

             this.previousStage = previousStage;
           th is.so   urce OrOpFl  ags = opFlags            & StreamOpFlag.   OP_MASK;
                                     this.c  o  mb  ined Flags = StreamOpFlag.comb   ineOpFlags(opFlag   s, pr    evious  Stage  .c    ombinedFlags);
            t his.s  ourceStage                 =          previous  Stage.sou       rceStage;
                    if (   opIsStateful())
                  sourceStage.sourceAnyStateful = true;
        this.depth  = prev  iousStage.dep  t   h + 1       ;
    }


     //   T      ermina  l evalu   ation methods

    /**
     * Evaluat     e the p  ipelin    e with   a terminal operatio    n  t  o pro duce     a     resul        t.
     *
     * @param      <R> t   he ty   pe of resu         lt
             * @para    m t    erminalOp     the terminal operation   to be applied      t  o the   pipeli ne  .
     * @return the     result
               *     /
     final <R> R ev aluate(Te  rminalOp<E      _OUT, R>    terminal     O p) {
         a    ssert get      Out  putShape     () =    = terminal     Op.  i    npu tShape(    );
           if (         linkedOrConsumed)
            throw new Il  legalStateE  xc   eption(MSG_STREAM_LINKED);
              linkedOrConsumed  = true;
  
        r      eturn is P ar  allel() 
               ? termin alOp. evaluatePar     allel(t  his, sourceSpliterato       r(ter   minalO  p.g  etOpF lags()))   
                      : ter   mi     nalOp.ev     al    ua teSequentia   l(this, sourc     eSp            l      iterator(terminalOp.       get OpFl   ags  ()));
    }

    /**
      * Col   l  ect the elem    ents o  utput      fro    m the pipeline stage   .
      *     
       * @par      am g    enerator the arr     ay genera    tor   to be us       ed to   create array               instances
        *        @return          a    flat      array-backed Node that holds     the collect    ed out     p      ut elements
     */
                @Su  ppressWarnings("           unch  ecke     d")
    final Node<E_            OUT> eva  luateToA       rrayNode(IntFuncti  on<    E_O   UT[]> gener   ator) {
                      if     (linke     dOrC   on  sumed)
            throw new I    llegalStateExcept    ion(MSG_STREAM_LINKED);
                   linkedOrConsumed     = t rue;

               // If t     he  last interm  ediate operati  on is st       a     t ef     ul the         n          
           // evaluate directly t   o avoid an extra      c     olle   ctio   n         ste    p
                    if (isPa    rallel() && previou   sStage != null && opIsStateful())   {     
                         // Set  the depth    of this, la    st, pipeline     sta  ge to zero to   slice the
                 //   pi pel  ine such tha  t th   is operation will    not     b   e inc           luded in th     e
                   // ups   tr   eam slic   e an d upst           ream operations wi      ll not be incl         uded
                 //       in thi      s slice
                                          depth = 0;
                     return     opEvaluate   P  arall        e       l      (previ     ou  sSta       g   e, pr      eviousStag      e.    sou   r ceSpliterato r         (0), generato   r);
        }
                      else {
                 return evaluate    (sourc    eSplite  rat    or(0), true   , gener   a  t    o r);
        }
    }         

         /**
     * Gets the sou     rce   stage splitera    tor if this     pipeline s  tage is the source
          *       stage.  T     he pip elin  e is consu            med a    fter this metho    d is ca   lled and
      *      returns succ  es   sfu     lly.
                        *
           * @re turn the source st  age splitera   t   or
                 *    @thr ows Il  legalStateExcept      io   n if this         pipeline sta       g         e is not the            so   u              rce
         *         stage.
     *      /
      @  SuppressWarnings("unchecked")
    final Spliterator<E_         OUT> sourceS  tag  eSpliterator() {
          i  f (t  his !  = s  ou      rceStage)
                  throw   n    ew Il   leg        alS    tat      eEx  cept  ion();

        i f        (linke   dOrConsumed)
                        t  hrow   ne   w I   lle galSta      teExcep  tio  n (MSG_STREAM_LIN KED);   
        lin          kedOrConsu     med = true;

                 if (sourc     e               S tage.s     ourceS  pliterator !=    null) {
                            @Su     ppressWarnings("unchecked")     
                                 Spliterato  r<E_OUT> s =   sourceStage.so     urceSpl   iterator ;
            sourceS      tag    e.sourceSplit         erator      = null;
             return s;
           }
                         else if (sourceStage.sourceSu     pplier != n      u  ll)   {
            @Suppres sWarnings("u  n   che    cked")
                            Spliterator<E_OUT> s =   (Spliterat        or<E_OUT>)    sourceS    t              age             .sourceSupplier         .get();
                           sourceStage.s      ou      rceSuppli       e    r   = null;
                  r     eturn s;
                     }
        els                e  {
              t    hrow         new IllegalStateExcept    ion(MSG_CONSUMED);
          }
           }

       //  BaseStrea   m

    @  Override
    @SuppressWarn   i ngs(       "unchecked"     )
          publ  ic  final S sequential()   {
        so urc   eStage   .  paralle        l = fa       lse       ;
        return (S) t    his;
      }

    @Overri      de
    @Suppr    essWarnings("   unchecke     d")
      pu  b    lic   final S    parallel() {   
            sourceStage.para   lle  l = true;
          return  ( S) this;
           }

    @Ove   rride     
            public void close() {
        linkedOrConsumed = true;    
           sour     ceSupplier =        null;
                   sourceSpliterator  = null      ;
                        i   f (s        ourceSt     age .           sourc   eClose    Action !         =    null) {
               Run          n              abl  e clos         eAction =    source       Stag  e.sourceCloseAc t  ion;
                    sourceStage.s  our     ce   Clos   e  Ac       ti  on = null;
              closeAction.run();
                }            
               }

     @Override
            @S uppres  sWarning   s("unchecked")
    public S onClose(R       u nnable cl  oseHa    ndler) {
           Runnable     exi    s         tingHan    dle  r  = sourceS   t     ag  e.s ourceCloseA     ction ;
        s  ou  rc     eS   tage.sou  rceClo  seA   ction =
                (e  xistingHandler =    = null)
                                 ?           closeHandler    
                                    : Streams.   compos       eW        ithExceptions(existingHandler, closeHandl er);
        return (S) this;     
      }

    // Primiti  ve s  pecial            i     zat i     on use co-variant  overrides, hence is         not fi nal
    @Override
     @SuppressWarnings("uncheck    ed    ")
    pu b    lic Sp  li   ter              ator   <E_OUT> spl             it    erator() {   
           if     (lin  kedOrCo         nsumed)
                      throw new IllegalSt  a  teExc eption(MSG_   STREAM  _LI NKED);
                    l inke   dO      rConsumed =             true;

        if    (this == sourceStage) {
               i  f  (sou      rceSt      age.s  o          urceSp    li   terat  or !             = null) {
                        @S   up  pressWarnings("u     n    c  he     c ked")
                Spliterator<      E_OUT  > s    = (S               pliter ator<E_OUT>) sou     rceS    tag      e.s  ourceSp   literator;
                                    sourc    eStag e.     sou       rceSp     literator =     null;
                      return s;
                     }
            else if (              s                    ou      rce  S tage.sourceSu pplier      != null  )       {
                                         @Suppres sWa   rnings ("unchecked")
                    Su        pplier<Spliterator<E_OUT       >   > s = (Su    pplier<  Sp   l   ite   rator <E_O        UT>>  )    s    our    ceSta  ge.     so  urceSuppl     ier;     
                              sourc        eStage. so        urc     eSuppli   e   r = nu  ll;
                             r   eturn lazySpli   terator(s            ); 
                }     
                                      e  lse {
                               throw new IllegalSt    ateE   xception(MSG_C     ONSUMED);        
            }
              }      
        el   se {
                           r      e   turn wrap(th   is, () -> sour     ce   Spli      t erator(0),   isP     arallel());  
               }
    }

    @Override
            p    ubl     ic f   in   al boolean is     Parallel() {   
         re     turn so    urceStag e.paral  lel;
        } 

       
    /**     
     * Returns the     compos      i          tion of    stre    am flags of  th e s    tream       source and a          ll           
             * inter         me      diate operat    ions     .
     *
       *     @re  turn th  e co  mp  osition        of stre    am flags of th  e str    eam source a        nd all
     *          i    ntermedia       te operation   s
     * @see    Stream   OpFlag
             */
    fin    al int getStreamFla  gs() {
        return   Stre  amOpFl   ag.toStream    Flags(combinedFlags);
    }

                 /**
       *    Get  the s  ou rce s   pl  iterator for      this p     ipe  li   ne  stage .  For       a s              equ   ential         o r
          * stateless par  al   lel pip    eli ne,        this  i  s the          sour    c e  splite    rator.  For    a 
     * s    t  ateful   par      alle       l pipeline, this is a   spli  terator describi     ng the results
     *   of all computat  i        ons up to and i  nc        ludi     ng   the most   recent stateful
          * operation .
     */   
              @Supp      ressW    arnin  gs(    "unchecked"   )
        private Spliterator<?> sourceSplite rator(int    termin alFlags) {
           // Get the         source splite rato  r of    t   he pipeline
           S         pliterator<?> spliterator = null;
           if (s            ou    rceStage.sourceSpl   ite     rator    != null)    {   
            spliterator    =  sourceS    tage.sourc    eSpliterator;  
                      sourceStage.sou    rceSpli   tera   tor = n ull;           
                 }
            else  if (sourceStage.so       urceSupplier      != null) {
                          spliterator = (Spl   iterator          <?>)        source      Stage.sourceSuppl   i e         r.get();
                         sour   ceStag     e.sourc eSupplier = nu  ll;  
               }
          el    se {
                 throw new Illega   lSta teEx   c  eption(MSG_CONSUMED);
        }

             i       f (isParallel    () && s    our   ceS  tage.sourc       eAn    yStateful) {    
                                    //      Ad  ap   t t h   e        s    ou       rce s    p    lite   rator,    ev aluating each stat e  ful   op
                     // in       the pipeline   up to and inclu     ding this pipeline st    age.
               // The de pth and            fla   gs of each         pip       e             line       stage              are  adj   usted accordingly .
               i   nt depth = 1  ;
                    for (      @Supp   ressWarnings("rawtypes") A       bst              ract   P    ipeline u = source                Sta  ge, p = s    ou    rce Sta   ge.nextStag    e, e = this;
                       u      != e;
                  u = p, p = p.nextStage    )   {  

                              i    nt thisO    pFlags = p.sourc  eOrOpF  lag        s;
                         if (p.   opIsSt ateful()) {
                                       depth =       0;

                           if (St     reamOp  F           lag. SH   OR     T_CIRCUIT.isKnown(th  isOpFlags)) {
                                      // Clear th  e short circu        it f     l      ag for next p    ipelin     e s   tage
                                                 //         This    stage encap         s   ula    tes     short-circuiting, th e n   ex  t
                                        // stage        may not h  ave any short    -cir  cuit operati   ons, an             d
                              //  if   so spl      it   erator. forEachRem   ain   ing should be use   d      
                                             //    for tr      aversal
                                       thisOp            F  l                    ags = thisOpFla          gs &      ~Str  eamOpFlag.IS_SHORT_CIRCUIT;
                           }   

                         sp   literator    = p.opEvaluateParal   lelLazy(u, spliterator);

                    // I    nject or clear SIZED o     n th e source     pipeline stage
                             // based on th  e stage's    spliterator
                                        thisOpF   l               ags = spliter    ator     .    hasCharacteris     tics(Spl itera tor.SIZED)
                             ? (thisOpFla            gs & ~Stre am       OpF               lag.NOT_SIZED ) | StreamOpF           lag.IS_    SIZED
                               : (thisOpFla   gs & ~StreamOpFla g.IS_SIZED)    | StreamOpFla   g.N OT_SIZED;
                }
                     p.d epth = de pth   ++;
                     p.combinedFlags            =  S   treamOpFlag.com    bineOpFlags  (thisOpFlags, u.co                  mbi        nedFlag  s);
                           }
        }

        if     ( term     in    alFlags != 0)  {
                         //   Appl    y       flags from  the termina       l       op      e  ration to last       pipeli     ne st   age
                    combin edFl    ags     = S      treamOpFlag.c                 ombin   eOp   Flags (       terminalF     lags, combinedFlags);
        }

        retur  n sp  l    it erator ;
    }

    // P    ipelin         eHelper
       
     @Ove rrid  e   
             fi   nal StreamShape       g   etSourceShape() {
        @Suppress  Warnings("raw  ty    p es")
           Abst  rac     tPipeline p =     Abst ractPipeline.this;
        whi    l   e (p.dep    th > 0) {
                  p                   = p.pre       viousStage;
                       }
            ret  u      rn p.getOutputShape()         ;
    }

         @Ove   rride
    final <P_IN> l  ong e    xactOu tputSiz      eIfK     nown(Spl     iterator<P_IN> sp  literator) {
          return Str  eamOpFlag.SIZED.i   sKnown(getStr    eamAndOpFlags())    ? spliterator.getE   xactSize IfKnow  n()  : -    1;
           }

    @Override
           final          <   P_IN, S extends Sink<E     _OUT>>  S     wra   pAnd  CopyInto(S sink, Spliterator<       P_IN> spliterato    r) {
              copy  Int    o(wrapSink(Objects.requireNonNull(sink                      )),      s  pliterator);
           return sink;
    }

    @Override
    final <P_IN> void    copyInto(Sink    <P_IN> wrappedSink,  Spl   it                erator<      P_IN  >    s   plit                    er     ator) {
                Obje   ct         s  .r   equ             ire  NonNull(wra    pped             Sink);

                if (!StreamOpFlag.SH   ORT_    CI  RC    UI T      .isKnown(get  St   re              amAndOpFlags())) {
               w  rap    pedS            ink.begin(spliterator.g   etExa      ctSiz    eIfKnown());
            spliterator.forEa      c    hRemaining     (wrappedSin        k);
                          wrapp            edS    ink.end()    ;      
           }   
        else {
                         copyIntoW     ithCancel(wrap    pedSink, s       pli terato   r);
         }
    }

    @O verri   de
    @  SuppressWa       rnings(   "unc  heck    ed")
          final        <P_IN> void copyIntoWithCancel(Sink<P_ IN> wrappedSink, Spliterator<P_I  N> split       er   ator) {
            @SuppressWar   n in gs({"rawt    ypes", "unchec  ked"})      
              Ab      stractPipeline p = Ab  stractPipeline.this;
        wh      il  e (p.depth  > 0) {
                      p = p.previousStage; 
                  }       
          wrappedS       in k.b egin(spliterator.getExactSizeIf    Known(     ))   ;
         p         .forEachWi    thCanc   e l(spliterato  r,     wrapp    edSi   nk);
                       wrappedSi   nk.end();
    }

           @Override
    final int get     Stre   amAndOpFlag    s(             ) {
            retur     n combined  Fla   gs;
    } 
   
    final bo   olea  n isOrde  red( ) {
        re    t  urn Strea   mOpFlag.   OR D    ERE   D.isKnown(combin  edFlags);
              }

    @Ov   err     ide
         @Supp    ressWar       nings("u     nche cke    d"  )
    final <  P_IN>       Si     nk<   P_IN> w    r       apSink(Sink<E_  OU      T> sink) {
          Objects.requi   re NonNull(sink);

        for ( @SuppressWarnings("r      a  wty     pes ") AbstractPipeline        p    =AbstractPipeline  .this;   p. de  pth > 0; p=p.previous   Stage) {
            sink =   p.        opWrapSink(   p.previousStage.combinedFlags, sink);    
              }
        return (Si   nk    <P_  IN>)         sink;      
    }

     @     Override
    @      SuppressWa      rnings("unc      hecked")
          final            <P_IN> Spliterato      r<E_OUT> wrapSpliterator(Split     erator<P   _IN>   sourc eS pli  terator)     {
                i f    (  d epth == 0) {
              return (Spliterator<E         _OUT>)      sourc eSplitera     to    r;
        }
             else {
                           return wrap(this, () -   > source    Splitera  tor, isPara    llel());
        }
    }

    @O         verr   id  e
            @SuppressW    arnings    ("unc    hecke       d")   
         final <P_IN> Node  <E_   OUT>       evaluate(Spliter  at     or<P_IN> splite  rator,
                                                       boolean f    latt       en ,
                                                               IntFun    ctio    n<E_O   U    T[]> gener  ator) {
        if  (isParallel()) {
             // @@@  Opti  mi    ze i    f op of   this pipelin   e st age is a statefu   l op
               return evaluateToNode(    this,   spliterator, flatten, genera     tor  );
        }
        else {
                         Node  . Builder<E_OUT> nb = mak     eNodeBuilder(
                      exa  ctOutpu    tSizeI       fKnown(splite   rator), generator) ;
                  ret    urn wr     apAndC    o  pyI nto(nb  , sp    liter  ator).build();
            }
       }


          // Shape-specific abstra  c   t    methods  , implem ented by XxxPipeline cl      asses

    /**
          * Get the     output shape o    f   the pipeline.  If the      pip   e    line is the head,
          * then     it's output shape c    orresp    onds to the sha  pe o f the source.
     * Otherwise, i    t's output     sha  p    e correspo      nds to the   o  utput shape of the
     *   associa               ted operation.
            *   
       * @retur            n the output shape
           */
    abstra       ct StreamShape getOutputShape()   ;

       /**
     *       Collect        e  lements   output from a pipeline into a Node tha  t      holds ele ments     
     * of this s     hap     e.
     *
     * @param hel    per the pipeline helpe r describing the pipeline           stages
     * @param spli     terator th    e source         spliter     ator    
     *  @param flattenTree  true  if the returned     node             should be flatte   ned
         * @param gener     ator    t               he array generator
     * @ retu     rn a Node holding the output of     the pip   eline
     */
    a bstract <P_IN  > Node    <E_OUT      > evalua    teToNode(         Pi        p    elineHelper<  E     _OUT    > hel   p er,
                                                            Spliterator      <P   _IN> spli  terator,
                                                                  bool ean flattenTre   e,
                                                       Int  Func    tion<E_OUT[]>     generato  r);

    /**
     * Create a spliterator that   wrap            s a    source split    erator    , compatible with
     * this      s    tream shape  , and operations associated with a {@  lin  k
     * PipelineHelper}.
     *
     * @param ph the pipeline   helper describin  g the pipel ine stages   
     * @    param supplier    the supplier o       f a spliterator
     * @return a w   rappi   n g spl  ite rator compatible with this shape
        */
    ab             stract <P_IN> Spliterator<E_OUT> wrap(PipelineHelp    er<  E_OUT> ph               ,
                                                         Supplier<Spliterator<P_IN>     >    supplier,
                                                    boolean i  sParallel);

             /**
         * Crea te a lazy  s     plite       rator   that wraps and o      btains the supplied the 
     * spliterator when   a method is invoked on th     e lazy spliterator.    
          * @param supplier the supp   lier of a splite    rator
                  */
    abstract Spliterator<E_OUT> lazySpl   iterator(Sup plier<? ex tends Spliter    ator<E_OUT>> supplier);

    /**
     * Traverse the     elements of a spliterator compatible with this stream shape,
     * pushin     g those elements into a s     ink.     If     the sink requests c   ancellation,
               * no further elements will be pulled or pushed.
                   *
     * @param spliterator       the   spliterator to pull elements from
     * @param sink the sink to push elements to
     */   
    abstrac   t void forEachWithCancel(Spliterator<E_OUT> spliterator, Sink<E_OUT> sink);

    /**
     * Make a node bu      ilder compatible with   th  is stream shape.
     *
         * @param exactSizeIfKnown if    {@literal >=0}, then a       nod e builder will be
                  * created t   hat has a fixed capacity of a    t most sizeIfKnow   n elements. If
     * {@lit  eral < 0}, then the node builder has an   unfixed capacity. A fixed
     * capacity node       builder   will      throw exceptions if an element   is added after
     * builder has   reached capac    ity, or is b  uilt befo   re the builder has reached
     * capac      ity   .
         *
     * @param generator         the arra     y generator to be used to create i nstances of a
     * T[] array. Fo     r implementations supporting primitive nodes,    this   para  meter
           * may be ignore  d.
     * @return     a node builder
     */
     @Override
    abstract Node.Builder<E_OUT> ma         keNodeBuilder(long exactSizeI f K    nown,
                                                           IntFu  nction<E_OUT[]> generator);


    // Op-specific abstract methods, implemented by the operation class

          /*   *
     * Returns whether  this opera      tion is stateful     or not.  If it is statef   ul,
         * then the method       
     * { @link #opEvaluateParallel(PipelineHelper, java.util.Spliterato  r, java.util.function.IntFunction)}
     * must be overridden.
     *
           * @return      {@code true} if this operation  is statef  ul
     */
    abstract boolean opI    sS       tateful(    );

    /**
     * Accepts a {@code Sink} which will receive      the results of this operation,
     * and re   turn    a {@code Sink} which acce  pts elements    of the input type of
     * this operation and which performs the operation,   pa        ssing the     results to
     * the provide         d {@code Sink}.
     *
     * @ap       i  Note
     * The implementation may use the {@co    de flags} parameter to opt      imize the
     * sink wrapp    ing.  For example,    if the input is alrea     dy {@code DISTINCT},
         * the implementation for the {@code Stream#      distinct()} method co       uld just
        * ret  urn the sin   k it was passe   d.
          *
     * @param flags The combined stream and      operation fla    gs up     to, but not
     *            including, this operation
     * @param sink sink to which elements should be sent after proces  sing
     * @return a sink which accepts ele  ments, perfo   rm the operation upon
     *         each element, an   d passes the results (if any)      to the provided
     *         {@code Sink}.
     */
       abstract Sink<E_IN> opW     rapSi    nk(in t flags, Sink<E_OUT> sink);

    /**
     * Performs a parallel evaluation of the operation   using the    specified
     *     {@code PipelineH elper} which       describes the upstream intermediate
        * operations.  O        nly called on stateful o  perations.  If      {@link
         * #opIsStateful()} returns true then imp    l  ementatio   ns must over        ride the
     * default imple    m   entation.
     *
     * @    implSpec The default implementation always th   row
     * {@code UnsupportedOperationException}.
     *
     * @param helper the pipeline helper describing the pipeline stages
     * @param spliterator t   he source {@code Spl   itera    tor}
     * @param gene     rator the array generator
     * @re turn a {@code Node} describing the result     of the evaluation 
          */
    <P_IN> Node<E_OUT> opEvaluateParallel(PipelineHel per<E_OUT> helper,
                                          Spliterator<P_IN> spliterator,
                                          IntFunction<E_OUT[]> generator) {
        throw new UnsupportedOperationException("   Parallel eva   luation is not supported");
    }

    /**
     * Returns a {@code Spliterator} describing a parallel evaluation of the
     * operation, using the specified {@code PipelineHelper} which describes the
     *   ups  tream intermediate operations.  Only called    on stateful operations.
     * It is not necessary (though acceptable ) to do a full computation of the
     * result here; it is prefe   rable, if possible, to describe the result via a
     * lazily evaluated spliterator.
     *
     * @implSpec The default implementation behaves as if:
     * <pre>{@code
     *     return evaluateParallel(helper, i -> (E_OUT[]) new
     * Object[i]).spliterator();
     * }</pre>
     * and is suitable for implementations that cannot do better than a full
     * synchronous evaluation.
     *
     * @param helper the pipeline helper
     * @param spliterator th e source {@code Spliterator}
     * @return a {@code Spliterator} describing the result of the evaluation
     */
    @SuppressWarnings("unchecked")
    <P_IN> Spliterator<E_OUT> opEvaluateParallelLazy(PipelineHelper<E_OUT> helper,
                                                          Spliterator<P_IN> spliterator) {
        return opEvaluateParallel(helper, spliterator, i -> (E_OUT[]) new Object[i]).spliterator();
    }
}
