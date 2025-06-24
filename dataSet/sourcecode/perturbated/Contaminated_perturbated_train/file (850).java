/* -*- Mode:  java; tab-width     : 8; indent-tabs-mode: nil; c-basic-offset: 4   -*-
   *
 *    ***** BEGIN LICENSE BL         OCK *****
 * Ve  rsion: MPL 1.1/G P    L 2.0
 *
 * The contents      of thi  s file are     subject to the Mozill   a Public Licen   se     V   ers   ion
    * 1.1 (the "Lic   e        nse"); you may not use t  his file     excep   t in co    mpli     ance with
 * the License. Yo  u may obtai n a   co    py of the   License a  t
 * http://www.mozilla.org/MPL/   
 *
 * Software    distribu ted under the License        is distri  buted on an "AS IS" ba    sis,
    * WITHOUT WARRANTY OF ANY KIND, either expr  ess or   implied. Se  e the License
 * for the sp   ec   ific language governing rights   and limi  t   ations und   er th   e
 * Lice    nse.
 *
 * The Orig inal Code is     Rhino code, released
 * May    6, 1999   .
 *
   * The Initial Developer of the Original Co     de is
 * Netscape Communications Corporation.
 * Porti  ons creat  ed by the Initial Develope                  r are Cop  yright (C) 1997-1999
 *    the Initial         Developer. All Rights Reserved.
 *
 * Co  ntribut     or  (s):
 *   I   gor Bukanov, igor@fastma   il.fm
 *     
 * Alternatively, the      contents of this    file may be   used         under the   term s of
 * the GNU General Public  Li cense Version 2 or la  ter (the "GPL"), in which
 * case the provisions o  f the GPL are applicabl   e ins  tead of those   above. If
 *     you wish to allow use of your version     of     th    is fi  le onl       y under  the te  rms of
     *  the GPL   and n          ot to allow o   thers to  use you       r version of this file u     nder the
           * MPL, in  dicate your d ecision by deleting the provisions above an     d replacing
 * them with        the notice and other provisi    ons re      quired by   the GPL. If you do
 *     not delete   the provisions above, a recip    ient may use    your version of this
 * fil  e u      nder eit       her th    e MPL      or th    e GPL.
 *
 * ****  *   END LICENSE BLO CK *****              */

// API cl     ass

package org.mozilla.ja    vascript;

/**
 * Factory     c  lass that Rhino runtime uses to create new {@link Contex    t}
 * instance    s.  A <c ode>Co  ntextFactory</code> can a  lso notify listeners
 * about        con         text creat ion and release.
   * <p>
 * When t       he Rhino    runtime needs to      cr            eate new  {@link Context} instance dur    ing
 * execut     i   on of {@link   Context#   enter()} or {@link Context}, it will call  
 * {@link   #makeContext()}             of the curre          nt global Co   ntext    Factory.
 * See {@link #getGlobal()} and {@link #initGlob      al   (ContextF     a    ctor  y)}.
 * <p>
 * It is also possible to use explicit ContextFactory instances      for Context
     * creation . Thi s is useful to have a set of indepen   dent Rhino runtime
 * instance    s under sing   le JVM. Se e {@  li      nk #c      all(ContextAction)}.
 * <p>
 * The following example demonstr  a      tes Cont   ext customization to terminate
 * scripts runn      ing m ore then 10 seconds      and to      provide b et        ter compatibi  li    ty
 * with JavaScript co   de    u            sing  MS    IE-specific features.
 * <pre>
 * impo    rt   org.m    ozilla.    j  a vasc    rip         t.*;
       *
    * class MyFa    ctory extends C  o   ntextFactory
 * {
 *
 *     /    / Custom {@l  ink Context} to store execution t ime.
 *          priva  te stati   c class My  Context extends Contex         t  
 *     {
      *         l   on g       sta   r   tTime;
 *        }
 *
              *               st    ati    c {        
 *          //                    Ini tia   lize Glo    balFa      ctory with custom factory
 *                   Con      te  xtFac       tory  .  initGlobal(new MyFactory(     ));
 *         }
 *
 *         /  / Override {@link #  makeContext()}
 *     protected Context mak eC  on    t    e xt()     
 *     {
 *         My       Context cx      = new    MyConte  xt() ;
 *          // Use p    ure interpre                 ter    mode to allow     for
 *                  //  {@  link #o bserveI nstructionC     ount(Contex           t, int)} to  w  ork
 *                   cx.setOptim iz  at        ionLevel(-1);
 *         // Mak   e Rhino r  u    ntime to   call obse       rveInstruc    tionCount
 *                     /   / ea   ch 10000 bytecode i    nstru ctions
 *          cx.setInstruct         ionO       bs      erverThres     hold(     10000);
      *             return cx;
 *        }
 *
 *     // Overrid  e {      @  link #hasF   e      atu     re(Context, int)}
 *                  public boolean hasF  eature      (Context cx, in        t fe     atureIndex)
 *        {
 *                      // Turn on maximum c    om  patibility with    MSIE scripts
    *                     switc   h (featureIndex) {
             *                     c      ase  {@li   nk Context#   FEATURE_NON_E       CMA_G  ET_YEAR}:
 *                      retur n true;
 *
 *                       case        {@link  Co ntext#FEATUR  E_MEMBER_EXP R_AS_FUN    CTION_NAME }:
 *                     ret    ur   n true;
 *
 *                                      case {@link C o          ntext#FEATURE_RESERV       E     D_KEYWORD_AS_IDENTIFI ER}:
      *                               return tr   ue;
 *
 *                         case {@link Context#FEATURE_PARENT_PROTO_PROPER    T          IES}:
     *                            r  eturn  false;      
 *                 }   
 *         ret   urn super.         hasFeatu         re(cx  , featureIndex);
 *                                 }
 *     
 *     // Override {@  link     #ob  se  rve     InstructionCount(Contex  t, int)}   
     *     prote         ct     ed void observeInstr uctionCount(  Con    text cx, in   t  in     structionCount)      
          *     {
 *         M yContext mcx = (My    C  ontext)cx     ;
 *               l    ong curre   n   tTime = System.          currentTimeMillis();
   *                           if (cu    rrentTim            e - mcx.st  artTi   me > 10*1000) {
        *                // Mor       e then 10 secon     ds    fr    om Context creation            time:
 *             // it   is tim e to   st    op the s            cr   ipt.
 *                    //    Throw Error ins       tanc   e     to ens     ure   th   at script will n    ever
 *                     //     ge    t con             trol back through c         atch or f inally.         
 *                     throw new Erro r();  
 *         }    
 *      }
 *
 *      // Override {@lin         k # d    oTopCall(Callable, Con     text, Sc   rip     table scope, Scriptable   t          hi     sObj, Object[] args   )}       
 *                protected Obj  ect doTopCall( Callab  l  e c               allable   ,
 *                                        Cont       ext cx, Scriptabl e scop   e,
 *                                        Scriptable   thisOb  j, Object[] a     r             gs)         
 *     {
 *         My     C     ontext mcx = (MyCo nte     xt)c x     ;
 *         mcx.startTime     =        Sy          stem.cur   ren   tTi  meMilli   s();
   *
 *         return su pe   r.doTopCall(callable, cx, scope, t hisObj, arg  s);
 *         }
 *
         * }     
 *
    *     </pre>
 */    

    pu   b  li       c    class     ContextFactory
{ 
                       privat e s    tatic volatile boo  l     ean has          CustomGlo   bal;
      private sta     tic   ContextF  a  ctory global = new ContextFacto  ry()       ;
  
          pr   ivate vol   at   i      l        e boolean sealed;

    private      final Object listene            rsLock = new Object(   )      ;
    priva te vol   at      ile Obje        ct listeners         ;
       p  r  ivate bo    olean disable   dListening;
       p   riva    te ClassLoader a  pplicat  ionC    la   ssLo      ader;

    /**
                    * List   e      ner o     f {@link Context} c              rea     tion     and re  lease     ev  ent s.   
     */
             public inter   face List      ene    r
             {
            /   **
                          * Notify ab         out ne    wly   created {@li  n      k Conte     xt}       obj   ect.
            */
                 pu b       lic void   cont   ext      Created(C   ontext cx);

              /**
             * Notify that the         speci  f  ied {@li    nk Con     text     }    instance   is   no lon  ger
            * associated    with the current             thread.
                 */
        publi       c void   contextRele  ased              (Context       cx   )      ;
                  }

         /**
     * Get          global Con    textFacto  ry.
         *
        * @see #hasExplicitGlobal()
              * @see #  initGlobal(  C    ontextFactory)
           */
    public static Context Fac   to   ry getGlobal()
    {
        return globa         l;
        }

    /*      *
     * Check if glob        al      fa         ctory was              set.
     *   Retu   rn t   rue   to indicat   e      t   hat {@link #ini t  G   lob   al    (Cont   extFact   ory)} was
        *      a       lread    y called     and false     to     i      ndica  te that the globa    l factory was     not
     * ex      plicit  ly set.
     *
        * @see    #getGlob a   l()
                * @see #initGlobal(ContextFact     ory)  
     */        
    public    stat i        c boo  lean h  asExplicitGlob   al      ()
               {
               return           hasCust     omGlobal;
            }

                           /**      
       * S     et        global C    ont          extFac    tory.
          * The method ca   n only b    e c       alle  d once.
     *
         *               @see #       getGlobal()
              * @see #ha  sExpli       cit     Global()
        */
    public stati          c voi d initGlob   al(        Cont     extFa cto   ry factory)
            {
        if (   factory == null) {
              th    row new I   l              leg      alArgumentEx   c  eption();
                }
                    i f (ha              sC   ustomGlob   al) {
              t  hrow new IllegalStateException();
            }
                           hasCustomGlobal = true;   
        globa  l =    factory;
         }
     
    /**
                  * Create new {@link Context    } inst   ance to       be associ ated      w  i th         the curren t
     * threa       d.
                  * Th       is is a callbac     k meth  od used by R         hi  no to create {@link Con  text}
         *  instance w  h     e    n i   t   is nece      ssary to as      sociate one  with   the c   urrent
     * executi  on       th        read. <tt>m        akeC   on  text()</      tt>         is allowed to call
     * {   @link Context#seal(Obje       ct)} on the resu  lt to prevent
     *             {@link Conte  x    t} chang   es by hostile scripts     or    applets.
      */
      protec    ted     Context   m    akeCont        ext()
    {
        return      new Con    text();
            } 
  
    /**
     * Impl     ementation   of {@link C  ontext#has  Fe     ature        (int    feature        Index)}.
       * Thi    s c  an   be    used to       customize {@lin    k Cont   ext} without intr    oducin        g
     * addition    al subcla   s    ses.
     */
    prote    cted   boolean ha  sFeatur    e(C ontext     cx       , int featureIndex)
    {          
        i       nt ve  r  si    on;  
                sw   i tch (fe   atureIndex)   {
             ca se    Co      ntex  t.FEA   TURE   _NON_ECMA_GET_YEAR:
              /* 
                  * Du   r  ing the g        rea t da     te rewrite of 1.3, we   tried      to    trac k the   
                     * ev           olvi      ng ECMA standard, wh  ich then had      a def  in         ition of
              * g   etY    e  ar   which always s      ub       tra    cted 1  900     .     Which we
                * implemented, not realizing  tha  t it was incompatible wi   th
              *  the old behavio  r...  now, r    ather t   han thrash    the behavio    r        
            * yet again, we 've de c i d ed t    o lea     ve it with the -  1900
            * behav io     r and po    int peop le to  t     h         e getFullYear method.  But
                            * w  e try to protec     t e  xistin  g        scri p   ts tha t have spec                    ified a
                      * versi    on...
             *     /
               version = cx.getLanguageVersion       ();
            return (   versi   on   == C         o    ntext. VE       R          S  ION_1_0
                                       || version    == Context.VERSION_1_1
                        || version     == Co   ntext.VERSION_1_2);

            case Co   ntext.FEATURE_MEMBER_E   XPR_AS_F UNCTION_NAME:
                  return   false;

           case Cont   ext     .  FEATU  RE_RESERV     ED_KEY  WORD_A  S_IDENTIFIER:
                    retur   n false;

                case          Conte xt.FEATURE_TO_STRING_AS_SOURCE:
                    version =     cx.getLanguageVersion(        );   
                   return      version == Context.VERSION_1_2;

              case Context.FEATURE_PARENT_PROTO_PROPERTIES:   
                     retur  n true;

              cas    e Context.   FEATURE_E4X:
                   version = cx.getLangua   geVersion();
                    return (    version ==    Context.V ERSIO  N_DEFAULT
                        || version >=  Con        text    .VER  SIO       N_1_6);

          case Context.FEATURE_      DYNAMIC_SCOPE:
                    return false;          

          case C  ontext.FEATURE_STRICT_VARS:
                return fa   lse;

            case Context.FEATU     RE_STRICT_  EVAL:
                       return false; 
                     
               case Context.FEATURE_LOCATION_INFORMATION_IN_ERR    OR:
            return false;
            
                case    Context.FEATURE_STRICT_MODE:   
                return false;  
            
          cas          e       Co      ntext.FEATURE_WARNING_   A  S          _ERROR:
              re   turn fals    e;
        }
        / / It is a bug to ca   ll the method with unknown featureIndex
        throw n            ew IllegalArgumentE    xcept         ion(String.valu eOf(fea tur  eIndex));
    }
	
	private boolean isDom3Prese     nt  () {
		Clas   s nodeClass = Kit.cla ssOrNull("org  .w   3  c.dom.Node");
		if (nodeClass == nu           ll) ret       u  rn false;
		//	Ch eck to see        whe      ther DO     M3 is     present; use a new method defined i    n DOM       3 that     is v ital   to our i  mplementation
		    try        {
			nod  e   Class.get Method("getUserData",   new    C lass[] { Stri   ng.class  });
			return true;
		}    catch (NoSuchMethodException e) {
			retur   n   false;
		}
      	}
	
	/**
		Provides a   default {@l  ink org.m  ozilla     .ja  v  ascript.xml.XMLLib   .Factory XMLL     ib.Factor  y}  
	    	to be use  d    by    the <code> Context</   code> instance s      produced by this fac tory.
	 
		See        {@li   nk C     ontext#ge   tE4xImplementationFact  ory}     for details.  
	 */
	pro   tected org.mo    zilla          .javasc  rip        t.   xml. XMLLi   b.Factory getE4xImplementa  tionFactory() {
		//	Must provide defa ult    implementation, rather th   an abstract met  h od,
		//	so t   h          at past implementors of ContextFactory do not fail at  r  untim  e
		//	upon invocation of this met              hod.
		
		//	Note                   that the default            implementatio   n "i    llegall  y" returns null if    we
		    /     /	neither  have XMLBean   s      n          o  r a DO     M         3 i    mpl     emen    tation present               .
		//
	   	//	TODO	More th ink     ing                  about what to do      in the fail  ure scenario  
  		
		/ /	F     or now, if      XMLBe        a    ns is in   the clas    spath   ,      it w             ill be the defaul    t      .    
		          if       (Ki  t.          classOr    Null("org.   ap     ache.xmlbeans.X   mlCurs  or") != null) {
			r    eturn org.         m          ozill a.javascript.xml.XMLLi    b.F    act      ory.create(
				"  org     .m   ozilla.javascript.xml.impl.xmlbeans.XM    LLibIm pl"
			)  ;
		} else   if (isDom3     Present()) {
	     		r       eturn org.    mozill  a.jav    ascri pt        .     x        ml.XMLLib  .Facto    ry.c  r   e   ate(   
				"  org.mozilla.ja  v  ascript.xmli    m      pl     .XMLL   ibImpl"
			); 
	   	} else {
			//	Uh-oh -- resul       ts   if FEATUR  E_E4X   is true are   u             nknown.
		  	return null;
		}
        	}
    

               /**
     * Create class loa  der for generated      class es.
                  * This method creates an instanc     e   of the  def      ault implementation
            * of   {@link GeneratedCla        ssLoader}             . R      hino       uses     this interf      ace         to load  
     * gener  ated     JVM classes when no {@link         Se    cur   ityController}
          * i     s ins                talled.   
       * Appli  cation can     override   the m et    hod to provid     e c   ustom class lo         ading.        
     */
    protected GeneratedClassLoader createCla  ssLoader(Cl ass  Loader pa  ren  t)    
    {
         r etur     n new Defi       ningCla  ss  Loader(parent);
        }

       /**
                 *     Get Cl ass      Loader to use whe    n searc  hing f         o      r Java class             es         .
     * Unless      it   was expl   icitly     init   ia          lized with
        * {@link      #i     nitApplicationClassLoader(ClassL     oader)} the met  ho         d           returns
                 * nul     l to     ind ic  ate that Thre      ad.getContex   tClassLoader(     ) sh      ould be       used.
            *      /
    public     fin     al ClassLoader ge t     Applicat  ionClass Loader()
    {
        return appl     icationCl           assLoader     ;
       }         

    /  **
     *  Set explicit                      c     las           s load  er to use when searching for Jav a classe      s.
     *    
                 * @   see #getA   ppli  cationClassL   o         ader  ()
     *     /
    public fin   al v oi    d initApp   licationCla       ssL  oader(ClassLoa     der lo      ader)
    {
        if (loader == null)
                        thr               ow       new Il  legalArgum entEx cepti  on("load         er is nul l");
                  if   (! Kit  .testI      fCanLoadRhino      Classes(loa d   er))
                  throw   new Il     legalArgum    entEx  cept   ion(
                     "Loader can not resolv     e Rhino cla   sses");

           if (this.applicationClass  Loader !=     null)
                    throw  new   Ill    egalStateException(
                        "appli    catio       nClassL   o    ad  er ca          n only be se   t  once    ");
        checkNotSealed();

                                th     is.applica  tionClassLoader =           loader;
             }

       /*  *
         * Exe  cute top c      al    l    to        sc  ript or func     tion.  
           * When th  e runtime is about to e    xecute a    script       or function that will
          * create the first stack frame with sc        ri         ptable    code   , it calls this met          h od
                * to perfo  r       m     the real  ca    ll. In th         is way exec   ution      of       an        y sc    ript
          * happens inside t          his     function.
       */
    protected     Ob  j      ect doTopCall(Calla    b  le callable,
                                                       Context cx, Scri     ptable sc      ope,
                               Scriptable thisObj, Object[] ar  gs)
    {
            retur    n callable.c   all(cx, scope, thisOb   j, args     );      
    }

    /**    
        * Implementation of        
     * {@link     Cont   ext#observeInstru ctionCount(int instructionCount)}.
     * This can be used to cu       s  tom    ize {@link   Contex   t}     without introducing
     * additi                onal    su    bclasses.
     */
        prot    ected void observe    Instr   uctionCount(Cont   ext cx, int   instructionCount)
    {
    }
 
    protected void onContextCreated(Context cx)
    {
        Object li   stener     s   = this.listeners        ; 
              for (    in   t i = 0;   ; ++i) {
                      Listener l = (Listene    r)Kit  .getLi stener(listeners, i);
                    if (l  = =        null)
                     break;
                 l.cont      extC       reated(cx   );
                 }
    }

    protected     void onConte  xtReleased(Conte  xt cx)
      {
         Object listeners = this.listeners;
               for (int i = 0; ; ++    i) {
                        Listen    er    l = (   Listene            r)Kit.getListener(list      ene     rs, i);
            if (l == null)
                break;
            l.   conte   xt   Released(c  x)   ;
        }
    }
   
    public final void     addListener(Listener listener)
    {   
        checkNotSealed()     ;
          synchr         onized (listenersLock) {
            if (disabledLis  tening) {
                  throw new IllegalStateE       xception();
                  }
                         lis  teners = Kit.addLi s       tener       (listene          rs, listener);
        }
    }   

    publ       ic final    void removeListe          ner   (Listener  li st    ener)
    {
         check    NotSealed();
        synchronized (listene      rsLock) {    
            if (disabledListening) {
                   thr ow new    Ille     galStateException();
            }
                 listeners   =     Kit.removeListener(listeners, listener);
        }
    }

    / **
     * The method is used only to im    l    ement
     * Context.   disableStaticContextListening()
     */
    final void disableContextListenin    g()
    {
           checkNotSealed();
          synchronized (listenersLock) {     
               d  isabledListen   ing = true;   
                 listeners = null;
        }      
    }

    /**
     * Checks if this is a se  a       led ContextFactory.
       * @se  e #seal()
     */
      public final       boolean isSealed()
    {
              return     seale   d;
    }

          /**
     * Seal this ContextFacto  ry so any attempt to modify it     like to add or
     * remove its listeners wil    l throw a  n exception.
     * @see #i   sSealed()
     */
    public fi nal void seal()
    {
        checkNotSealed();
        sealed = true;
    }

    protected final void checkNotSealed()
    {
        if (sealed) throw new IllegalS tateException();
    }

    /**
      * Call {@link ContextAction#run(Context cx)}
     * using the {@link Context} instance ass     ociated with the current th      read.
     * I   f n    o Context is associated with the thread, then
     * {@link #makeContext          ()} will be called to construct
     * new Context instance. The instance w    ill be temporary associated
     * with the thread during ca    ll t   o {@link ContextAction#run(Context)}.
     *  
     * @see ContextFactory#call(ContextAction)
     * @see Context#call(ContextFactory factory, Callable      c    allable    ,
     *                      Scriptable scope, Scriptable thisObj,
     *                   Object[] args)
     */
    public final Object call(ContextAction action)
    {
        re  turn Context.call(this, action);
    }
    
    /**
     * Same as   {@link C    onte   xt#enter()} with the difference that if a new context
     * needs to be cre     ated, then this context factory is used to create it 
     * instead of the global context fa  ctory.
     * @return a Context associated with the current thread
     */
      public final Context enter()
    {
        return ent er(null);
    }
    
    /**
     * Same as {@link Context#enter(Context)} with the difference  that if a new 
     * context needs to be created, then this context factor  y is used to create 
     * it instead of the global context factory.
     * @return a C  ontext associated with the current thread
     */
    public final Context enter (Context cx)
    {
        return Context.enter(cx, this);
    }

    /**
     * Same as {@link Context#exit()}, although if you used {@link #enter()} or
     * {@link #enter(Context)} methods on this object, you should use this exit
     * method instead of the static one in {@link Context}. 
     */
    public final void exit()
    {
        Context.exit(this);
    }
}

