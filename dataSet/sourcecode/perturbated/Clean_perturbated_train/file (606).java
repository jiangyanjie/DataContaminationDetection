/*
 *   Copyright     (c) 2012, 2013, Or       acle and/or it      s affiliates. Al   l    rights reserved.
 * ORACLE PROPRIETARY/C ONF  IDENTIAL. U   se is subj   ect     to l   icens      e terms.
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
pack  age java.lang.invoke;

import sun.invoke.util.Wrapper;

import static sun.invoke.util.Wrapper.forPrimitiveType   ;
import static sun.invoke.util.Wrapper.forWrapperType    ;
import static sun.        invoke.util.Wrap   pe r.isWrapp  erType;

/**
    * Abstrac t impleme ntation of a lambda metafactory which provides par   ameter
 *     unroll  ing and input validation.
 *
 * @see LambdaMet       afactor  y
 */
/* pa   c     ka  ge */ abstract class Abs    t   ractVal   idatingLambda Metafactory     {

    /*
        * For contex   t, the c   omments for t    h   e following fields             a re mark ed in q   u     otes
     * with their values, giv  en this           program:   
       * i  nterfa  ce II<T>       {  Ob       ject               foo(T x)   ; }
            *   interface JJ<R ex     tend  s Numb    e   r> exte  nds II<R> {       }
      *      clas    s CC         {  S                 trin     g imp     l   ( i    nt     i) {    r     eturn "im  pl:"+i;   }}
               * class X {
         *              public s ta    tic voi    d main              (  S     tr       ing[] args) {
     *                JJ<     Intege  r> iii   = (new   CC()):     :impl;
         *                           System.out.printf   (             ">>>   %s             \n", iii.foo(44)       ) ;       
     *  }}
              */
    final    Class<?> targetClass;                          // The class c alli    ng the       meta-factory via invoke  d   ynamic "class X"
    fi   n   al Metho    dType invoked   Type;                       //          The ty   pe of the invoked m   eth    od   "    (   CC    )II" 
          final   Cla  ss<?>    samBa   se    ;                   // The        type of the retu     rned instance "interface JJ"
    final Strin  g    s       amMethodNam     e;                           // Name of the SAM method "foo"
    final Met   hodTy    pe samMethod    Typ     e;                   // Type of th   e SAM metho d "(Obje  ct)Object"    
      final MethodH   and   le imp   lMethod;            //  Raw method   handle for t  he implement     ation method
    final Method       HandleInfo i  mplInfo;             // Inf   o about     the    implement      ati on method handl     e "Method  HandleI         nfo[5    CC.impl(int)Strin    g]"
    fi    nal int im       plKind;                                         /  /   Invo      cation kind for    implem     entation "5"=i   nvokevirtua   l
    fina   l boolean implIsInstanceMe   thod;       //    Is t  he implementation an instance  method "true     "       
     final Class   <?   > implDefiningClass;              // Type defini   ng the implementa tion "class C   C"
          final   Meth    odType implMethodType;                /  /           Typ     e of the impl   ement  ati        on      method "(           int  )String "
    final MethodType instant  iatedMe     th   odTy       pe;  // Instant   iate   d erased functional interface    method    type    "   (Integer   )Object"
    final boolean i   sSeriali  z  able;                  // Sho                uld th   e re turne         d         instance be serializab  l       e
    fi    nal    C     lass<?>[]    marker      Interface     s;         /     /   Additional marker interfaces to          be i mplemented
    final Met  hod T ype[] additionalBridges         ;     // S      i  gn            atu  re   s of   addit   i onal methods      to bridge


    / **
                * Meta-facto           ry con      stru      cto     r.
         *
     * @pa    ram caller Stacked automatica       lly by V M       ; repres   ents a                              lookup c     ontex        t
     *                            with    the accessibility privileges      of t he caller.
                 *         @param     invokedType Stacked au                        tomatic     al    l      y by VM; th          e sign  a  t ure        o       f t       he
                *                                                   invoked met                     hod, whi  ch inclu         des            the e   x       pecte d static
                     *                            type           of the re       tu               rned lambda o bject,     and the static
       *                              types of            th e captured  a     rgum      e  n     t    s for  th          e la           mbda  .  In  
         *                                 the   event that the implement  a      tion met           hod is a        n
      *                     i        n stance meth      od,     the first argume nt          in the in   vo   cation
         *                                            sig    n   a     t ur        e will co   rres    po nd to the   rec    eiv    e         r.
     * @par      am    samM          ethodName Name of t he    me   thod   in th    e fu     ncti    ona   l interface to
        *                                    which   the lambda or me  thod    reference       is being
             *                                         conv          erted, r  epresented as a String.
              * @par am samMethodT         ype Typ    e of the m    etho             d in t  h             e functiona        l   int           er      face to
     *                                            whi  c   h th    e lambda or m       ethod r      eference    i  s being
        *                                                              con     ve   rted, rep  resente    d as   a Me  thodType .
     * @param           imp     l      Method The im  plement     ati    o                     n me   thod      whic   h    sh oul  d be calle    d  
     *                        (with              s       uitable adaptatio   n of argum       e   nt                types,      return
                               *                               types, and             adju   stment  for ca ptured   argum   e    nts) when
           *                    methods     o   f the      resulting f           unctional interface i         ns              tance
           *                   a   re invoked     .
     *   @ param ins        tan  tiated      Me   thod   Type The si         gnat   ure       of the primary     fu ncti   o   nal
     *                                                          inter      face method        afte           r ty  pe            varia   bl    es           are   
        *                                                                  subs   tituted with their    i  ns  t  antiat  ion from
      *                                                        the               capture site
     * @param is     Seri    ali zab    le Should the lambd   a be made serializable?  If set,
         *                                   either the t    arget    type     or on   e of the addition   al SAM
     *                                      types m    ust extend {@c ode Se     rializa   ble}  .       
           * @       p  aram m  arkerInt  erfa   c    es Additi    o nal in ter    f   aces which t    he lambda       obje      ct
                             *                                          sho uld   imp     lemen       t  .
       * @param additional          Bri     dges  Method types for addit    ional sig  natu r    e   s to      be
                   *                                                    b   rid       ged to the    implementa   t   ion   meth  od
               * @throws L    a   mbdaConver s   ionException If     any o     f   the meta       -factory pr    ot         ocol
            * invariants    are       viol   a ted
     */
    Ab                strac  tV     alidating     La mbdaMetafa   cto   ry(Me   thodH        an   d les.    Lo       okup ca  ller,
                                                                              Meth  odType invokedType    ,       
                                                                       S trin           g samMeth   o dName,
                                                                    MethodTy   p   e samMethodTyp     e,
                                                       Metho   dHandle i  mplMet         hod,
                                                           MethodTy p     e i    n  stantiatedMeth  odType,
                                                        boolean i sSerial               izable,  
                                                        Class   <?     >[] mar  kerInterfaces ,
                                                                 Meth           odType[]       additio    nalBridg      es)
                th   rows      L   ambda  Co      nvers   ion   Exception {
        i       f ((ca  ll   er.lo   ok  upModes() & Met                    hodHandles.Loo   k   up.PRIVA  TE) == 0    ) {
                    thr          ow new LambdaConversionEx    c   eption(String.format(
                              "Invalid caller: %s",
                     call   er           .lookupCla    ss()  .ge   tName()));
           }
           t       his.targetClass = caller.lookupC      lass();
        this.invokedTy  p       e = invokedType  ;
  
        this.sa   mBa se = i   n  vo   k edT    y pe.returnType();

           this.samM       ethodN  am e   =   sa  mMe   t  ho          dName  ;
        t           his.s a  mMe    thodType         = samM          ethodTyp      e;

              this.implMeth    od =            i     mplMethod ;        
          this.implInfo = c   aller.reveal    Di rect(i    mplMethod);
             this.implKind    = implInfo   .get  Refer ence  K      ind    ();   
                              thi    s.impl  IsInstance   Method    =
                       implK   ind == Met    ho  dHandleInfo.REF_invokeVirtual ||
                       implK    ind =  = Method   Ha  ndleI         nfo.REF_invokeSpe  c     ial | |
                                         implKin    d == Me   thodH an      dleInfo    .RE      F_ invokeInte  r  face;
           this.implDefiningCla   ss = implInfo.  get   D    eclaring   Class();
            t      hi  s.imp  lMeth      odType = implInfo.getMethod          T    y       pe         ();
           thi   s.   instantiate dMethod   T                 yp  e           = instanti  atedMethodType;  
                            this.i       sSe               r     i   ali           zable     = isSeriali    zable;
              t hi  s.markerInter      faces = marker    Inte   rfaces;
              t hi s  .       addi      tionalBridges = ad                dit   io   nal   Bridges       ;

                  i             f (!sa   mBase.isInte rfa    ce      (        ))     {
                                    throw       new L ambd       aConversionExceptio    n(String. format    (
                                               "Functio  nal       interfa ce %s is not an int   erf  ace",    
                                  samBase.       getName()));
         }

                fo  r (Class<?> c : markerIn     terfa            ces) {
                             if     (    !  c.isInterface()  ) {
                                 throw   new           L   ambdaConve rsio nExcep   tion(String.f   ormat(
                               "Mar      k     e     r interface %     s   is not an interface",
                             c.getName            ()));
                   }
           }
    }

        /**
                       * Build     the CallSite.     
           *
            * @return a Cal    lSit              e, w     hich, when invoked, will return an    in      stance of the
            *   functional     interfac         e
                  *   @t        hr   ows    R  ef     lectiveOperatio   nException
            */
    abstract Cal             lSite buildCall Site()
                             th      rows LambdaC onver   s      ion  Excepti    on;

         /**
     * Che   ck the              meta-facto        ry argument s   for erro    rs
        * @thro     ws L    ambda      Conver     sio    nException if    there        are improp  e   r        conve   rsions
           */
    void valid      ateMet afactoryArgs() th         row                    s Lam  bdaCon    versionEx  ception {
        s   witch (implKi     nd)      {
                          c a        se       M ethodHandl   e     I    nfo.REF_invo k eI   nter   face:
                        case Meth                            odHa   ndl   eI    nf  o.REF        _invok eVirtual :
            ca        se Me      thodHandleI       nfo.    REF_i   n   voke   Sta     tic:
                  case MethodHandleInfo.REF_ne  wInvo       keSpe     cial:
                 case Me    thodH   a    ndleInfo.RE   F_invokeSpecial:
                             b  reak;
              default:
                       throw new Lamb   daConversionE     xception   (String   .f     ormat(    "Unsupported MethodHandle    ki     nd    :          %s"      , imp    l Inf o) );
            }

            // C heck arity : optional-  rec          ei  ver    + cap   tured      + SA    M   == impl
              final int implArity = implMethodTy  pe.para     m  eter   Count();     
            final int rece    iverArity = implIs       InstanceMethod ? 1 : 0;
             final int capturedArity =  invokedType  .p   arameterCount();
        final in t      samArity =  samMet    hod   Type.param      eter  Count();
          f    inal int ins t a     ntiatedArit  y    =    instantiatedMethodT  ype  .parameterCount();
            if (impl   Arity     + re         ceiverArity   != capt  uredArity  + samA        ri  t     y)        {
                                     t  hrow     new      L  amb                daConversionExce     p      tion(
                                              Strin g      .fo       rmat(  "       Incor     rect numbe   r of  parameters  for %s method %s; % d captured par    ameters , %d fu   ncti   onal   i nte   rf                     ace met           h        od param     ete  rs,   %d implem    entation para       met   ers",
                                              im    plIsInstance      Method ? "instance" : "s  tati c", i  mpl Info     ,
                                                                  capture  dA   rity,        samAr       ity  , implA  rity  ));
                 }
                if     (in          stantiated         Arity !=    samArity) {
              thro  w n         ew Lam bdaCon  v      er  sio   nException( 
                                  Str         ing.f     o     rmat("Incorrect n    umber of parameters for %s method    %s; %d instantiated p        arameters, %d functional interface          method      paramet     er  s",
                                                         imp        l     Is I   nstan    ceM    etho   d ?         "inst  an           ce"    : "static", implInfo,
                                                                      instan     tia     t            ed                          Arit    y,      s   amArity) );
              }
        for (  MethodType   br      id        geMT : additionalB    ridges)      {
            if (bridgeMT.parameterCount(   )    !   =          samArity  )     {
                 throw   ne           w LambdaCon       versi     onE    xcept   ion     (
                                                                 Str    ing.format("Incorrect number of  p   ar  ameter       s for bridge             signa t     ur    e %s; incomp    atibl e with %s         "      ,
                                                                         bridge  MT, s  amMetho    dType  ));
             }
          }

          //    If in     stance: fir      st ca p  tured    ar     g ( rec       eiv  er) must be sub             type of    cla      s  s where i     mpl me  thod is defined
            final int cap    t            u     red  Start ;
                                       f     inal int samSta   rt;
                if (implIsI  nstanc         eMethod     ) {
                                             final C      lass<?> receiverClass;

                          /       / impl  ementat    ion i     s an   i nst      an ce metho      d  , ad          just    for        receiv    er in ca    ptured variables        /  SAM  argumen    t        s
                        if   (c   aptured  Arity == 0  ) {
                        // receiv   e    r is function parameter
                              cap turedStar  t =   0;
                sam             Start  =     1;
                               rec    eiverClas  s = i      nstantia     tedMet   hodType.parame te rType  (0);
            } else {
                   //  receiver is a c        aptured   v     ariabl     e
                                         c apturedStart = 1;
                    s      am      Start = 0;
                             receiverClass   =   invo      kedType.pa    ramet erT    ype(0  );
             }

                                       /   /   check       re     ceiver type
                    if (     !      i     mplD        efini          ngClass.i     sAssignab         l  e    From(rece   iv e   rClass)) {
                                                 throw new L                    a m    bd   aConve rsionExc   eption(
                          Stri   n   g.format("Invalid  receiver type %s;   no t         a subtype of impleme nt  ation type   % s",
                                                   receive     rClas   s       ,        imp        lDefin   ingClass    ));
              }      

                                     C       lass<?> implReceiverClass =    implM    etho      d.t    ype().para  meterType(0   );
                 i      f (implReceiverClass !=      impl        Def   iningCl   ass &    & !imp    lReceiver   C            l       ass     .i   s       Assi  gnableFrom(rece  iverClass)  ) {
                             throw new LambdaConversio nExcep tion(
                                 String.format("I    nvalid receiver     type   %s; no  t a subtype of implem    entation rec   eiver t    ype      %s",
                                                           re     ceiverCl  as    s, im     plRecei     ver    Cla   ss));
              }
        } else    { 
              //                   no receiv    er
                    capt   uredStar             t =     0;
            sa        mSta        rt       = 0;
        }

          / /  Check for exact            m             atch on non-receiver captu     red argume     nts
          fin   a      l in    t implFromCap    tured = ca   pturedArity - c  apturedStart;
              for ( int i=0; i<implFromCapture     d  ; i++) {
                         Class<?> i     mplParamType =    implMet       hodT    ype.paramete   r     Type(i);
                         Class  <?> capturedParamType = invokedType.pa     rameterType(i + capt          uredStart)      ;
                    if (!captur   edPa              ra mType  .equals   (i    mplParamType))         {
                       thr           ow n   ew Lamb      daConversionException(
                          String.format            (        "Type m    ism   atch i     n captured la     m  bda parameter %d:           expecting %s, found %s",
                                                            i,    capturedParamType, implParamType));   
              }
          }
        // Check    for adaptation match on SAM  arguments
                   final      int samOffset = samStart - implFromCaptured;
             for (int            i=implFromCapt   ured; i<implArit    y    ; i++       ) {  
             Cl   ass   <?             >        imp   lParamT ype = implMet  hodType  .param      et     erType(i);
                    Class<?> instantiatedPar   amTyp   e = i    nsta  ntiate    dMet    hodTyp       e.parameterType(i + samOffset);
               if (!isAdaptableTo(instantiatedParamType, impl   ParamType, true)) {
                     throw                   new     LambdaConversionExc  epti      on(
                          String.form at("Ty            pe mismatch f     or lambda  argument %d: %s is not converti    ble t o %s",
                                             i, instan         tiatedParamType, implParamType));
            }
               }

        // Adapta ti   on match: return type
               Class<?> expectedType =      ins   tantiatedMethodType.return         Type     () ;
             Class<?> actualR    e               turnType =
                (   implK    ind == Met    h      o   dHand    l  e  Info.REF_newInvokeS  pecial)
                                   ? implDefinin     gClas  s
                      :     imp  lMethodType.returnType()      ;
          Class<? > samR    eturnType = s    am      Me     thod      T           ype.returnType  ();
        if   (!isAdaptableToA  sReturn(actualReturnType, expectedType)) {
                   throw ne       w LambdaConvers   ionException(
                           String.format("T   ype mismatch          for lambda ret  u    rn: %s is not conve  rtible to %s",
                                      actualRet   urnType, expectedType));
                 }
         if (!isA         daptableToAsReturnStrict     (expectedType, samRetu    rnType)) {
            throw new LambdaCo  nv         ersionException(
                    Strin       g.for     mat(        "Type mismatch  for l              ambda     expected   return: %s is not conv   erti       ble to %s",
                                              expec  tedType, samReturnTy pe));
        }
          for (MethodType bridgeMT : addit   ionalBridges) {
                 if (!isAdaptableToAsReturn Strict( expectedTy   pe, bridgeMT.returnTyp       e()))     {
                th  row new Lamb  daConver     s  ionException(   
                        Str  ing.format("Type mis    m atch for lambda expected r et  urn:    %s is not  convertible to %s",
                                                          expec     tedTyp         e, bridgeMT.retu   rnType    ()))    ;
                }
                   }
     }

    /**
          * Check type adapt    abili    ty for parameter types.
     *     @           param fromType Type to convert    from
     * @param toTyp    e Type to con   vert t    o
     * @param strict If true, do strict checks, else allow that fromType   may be parameterized
       * @return True if 'fromType'        can be passed to an argument of 'toType'
     */
    private boo    lean isAdaptableTo(Class<?> fromT   ype, C  lass<?> toType, boolean strict) {
        if (fromTyp     e.equal s(toType)) {
            return true;
        }
        if (   fromType.isPrimitive())  {
            Wrapper wf rom = forPrimitiveType(fromType);
                if (toType.isPrim     itive()) {
                // both are primitive: widening
                Wrapper wto = forPri    mitiveType(toTy     pe);
                retu     rn wto.isConvertibleFrom(wfrom);
            } else {
                    // from primitive   to reference: boxing
                 return toType.isAssignableFrom(wfrom.wrapperType());
              }
              } else {
                 if (to Type.i       sPrimit ive()) {
                // from reference to primitive: unboxing
                        Wrapper wfrom;
                     if (isWrapperType(fromType) &&   (wfr  om = forWrapperType    (fromType)).primitiveType().isPrimitive()) {
                    // fromType is a primitive wrapper; unbox+widen
                    Wrapper wto = forPrimitiveType(  toType);
                              return wto.isCo  n    vertibl    eFrom(wfrom      );
                } else {
                    // must be convertible to primitiv      e
                          return !strict  ;
                   }
            } else {
                // both a   re reference types: fromType should be a superclass of t  oType.
                return !strict || toType.isAssignableFrom(fromType);
            }
        }
     }

    /**
     * Chec   k type adapt    ability for return types -   -
     * special handling of void type) and parameterized fromType
     * @return True if 'fromTyp  e' can be converted to 'toType'
     */
    private boolean isAdaptableToAsReturn(Class<?> f  romType, Class<?> toType) {
        return toType.equals(void.class)
               || !fromType.equals(void.class) && isAdaptableTo(fromType, toType, false);
    }
    private boolean isA dapta bleToAsRe  turnStrict(Class<?> fromType, Class<?> toType) {
        if (fromType.equals(void. class)) return toType.equals(void.class);
        return isAdaptableTo(fromType, toType, true);
    }


    /*********** Logging   support -- for debugging only, uncomment as needed
    static final Executor logPoo l = Exe     cutors.newSingleThreadExecutor();
    protected static void   log(final String s) {
        MethodHandleProxyLambdaMetafactory.logPool.execute(new Runnable() {
            @Override
              public void run() {
                System.out.println(s);
            }
        });
    }

    pro    tected static void log(final String s, final Throwable e) {
        MethodHandleProxyLambdaMetafactory.logPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(s);
                e.printStackTrace(System.out);
            }
        });
    }
    ***********************/

}
