/*
     *     Copyright          (c)     2000, 2    011      , Oracle and/or   its affili ates.  All r  ights reserved.
 * ORACLE PROPRIETARY/CONFID   E   NTIAL. Use is subject to          license te  rms.
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
package java.bea   ns;

import java.util.*;
import java.l an  g   .reflect.*;
import java.util.Obje     cts;
im  port sun.reflect.misc.*;


/   **
 * The <code>Defaul   tPersistenceDelegate</code> i                s a concrete implementation of
 *    the    abstract <code>Persistenc  eDelegate<    /code> cl    ass and
 * is the dele        gate used by          default for clas   ses about
 * which n  o   i  nformation is available. The   <code>DefaultPersistenceD      elegate</code>
    * provides, ver   sion resilien     t, public API-based persistenc            e for
 * c  lasses that follow the JavaBeans&trade; con  ventions wit   hou   t a   ny        c    lass specific
 * configuration   .
 * <p>
 *   The key a                ssu  mptio     ns are that  the class has a nullary co    nstr  uctor
 * an    d   that its state is ac      curately represented by matching pairs
 * of "setter"   and "getter" methods in the     order they are returne d
 *       by the Intr    ospector.
   * In addition to      provid     ing code-free   per sistence for JavaBeans,
 * the <co de>DefaultPersistenceDele        gate</code>        provi    des a convenient me    ans
 * to effect persistent storag     e      for classes t    hat have a      const   ruc tor
 * that   , while not nullary,   simply        requires some property   values
 * as arguments.
 *
 * @see   #DefaultPersis       tenceDelegate(String[])   
 * @see java.beans.Introspector
 *
 * @since  1.4
 *
 * @a   uthor P  hilip Milne
 */
    
publ   ic clas    s Def  aultPersistenceDel  egate extends Persistenc    eDeleg  ate {     
    p     rivate String[     ] con   structor;
    private Boolean    de  fine    sEquals;    

    /        **
       * Creates a persistence de         lega   te for     a   clas         s with a nulla   ry constructor.
               *
     * @see #  DefaultPersiste    nceDel   egate(java.lang  .S    t   r   ing[])
                */   
    public D   efault   Persis tenceDelegate(   ) {
                         this(n  ew S   tring [0]);
        }

           /**
        * Creates a defaul  t persistence delegate for                   a class with   a
     *      constructor whose a    rguments are the values of the  property
            * names as s       pecified by <code>c  o       nstructorPropert     yNa   mes</code>   .
           * The constructo       r ar    guments are created by
      * eva   luating the property  names i n the order they     are  su pplied.
         * To use this class    to specify a single preferred        cons   tructor for use
                       * in the     serializatio  n of        a   parti     cular type,   we state the
           * n  am              es of          the p         rope rties th  at make up the   cons    t    ructor's
     *      ar  gume  nt  s. For ex     ample, the <code>      Fo nt</co       de> class which
         * do   es not def      ine a nullary constructor can be ha  ndled
     * wi         th t  he           f        ollowing persistence delega   te:
     *
     * <pre>
           *           new   Default  Persisten c        eDelegate(new Stri    ng[]{    "name ", "s    t  yle", "s      ize"});
     *      </pr  e            >
     *
      * @param      con   str uctorPro  p      ertyNames     The property names      for  t he arguments of this   const         ructor.
     *    
        * @see #ins              tantiate
     */
              public Defau    ltP   ersist    enceDeleg ate           (String[]   construc          torPr            opertyN   ames      ) {
        this.con   struc  tor = con   s   truct   orP  ropertyNames;
           }

    private            s              ta     t   i   c   boo         lean   define       sEquals(Clas s type) {
                   tr   y {
                      return type          ==     type.g   etMethod(         "equals"    , Obje     ct.class).getD     eclaringCl  ass();
        }
             catch(No   SuchMet hodExc ept ion   e) {
            ret  urn  fals                          e;
        }
            }      

    private    boolea n definesEquals(Ob     ject inst   anc     e) {    
        if (d   efine     sEquals  !=      nul  l) {   
               r eturn (def      inesEqual            s == B   oolean.T  RUE);
           }
         else    {
             boolean result = de    f             inesEquals(i   nstance.ge     tCla  ss());    
                      definesEquals = result ? Bo olean.TRUE   : Boolea     n.FALSE;
                           return result;
        }
    }
   
      /**
     * If t  he numb   er of     arguments in       t  he specified        co     ns       tru ctor           is non-zero and
     * the cla  ss of       <code>o      ldI       nstanc   e</code    >     explic  itl   y d    ecla      res an "equals" method
     * this   m   ethod return   s th          e v       alue of <code>old      Insta            nce.equ  als(newIn  st  ance)</code>.
     * Otherwise, this met    ho    d uses the superclass'   s de  fi   nition which returns true if       the
     *     clas ses of the two instances a              re      e            qual.
     *
     *    @param oldInstance  The        instance to b e   cop   ied.
         * @param newInstance The inst    ance      t   hat is to    be modified.
     * @return True if an equ   ivalent           copy of <code>newInstance <   /c       ode> m       ay     b e
     *             created by applying a series      of mutat     i   ons to <code  >oldIns   tan    ce</         c ode>.
            *
     *         @s ee #Defau   l  tPersistenceD       e  l       ega   t   e(String    [])
      */
    protected boolean           muta  tesTo(Object     oldInst    ance, Object newInstance) {
            // As   s    um e the     instance is either    m utabl    e or a singleton
            //         if it  has a nullary constructor.
          ret           urn (      constructor.leng     th     =      = 0) ||   !defin   esEquals(oldIns      tance) ?
                 super.mutates    T  o(oldInst     anc   e, newInstance)       :
                      ol   dInstance .equals(newInstance);
    }

    /**
     * This def          ault imp  lementation     of t   he <code>i            nst    an   tiate</code>  metho      d re  turns
     * an     expression containing the    pred    e    fine    d me     thod     name "new" which       den    otes a
     * ca  ll to a constructor with the arguments as s   pecifie       d in   
         * the    <   code>DefaultPe   rsiste         nceDelegate</code>    's constructor.
     *
      *   @p ar am    oldInstance The instance to   b e instan        t  iated.
         * @param    out The code output     strea   m.
       * @ return An expression   whose   value      is <code>  old     Inst ance</code>    .
     *    
     * @th rows NullPointerExcep    tion         i  f {@code out} is {@code nul   l      }
      *
         * @s             e           e #DefaultPersiste nceDel      egat e(Str  ing[])
     *        /
    prote          cted   Expres              sion             instantiate(Obj  ect oldInsta nc       e, Enco    der out)    {     
                      int nArgs  = co   nstructor.len   gth;    
        Class type = old Insta     nc e.get       Class();
          Obj ect[] constructorArgs =     ne   w Object[   nAr   gs]; 
                 f  or          (in              t i = 0  ;   i < nArgs; i+ +)        {
                                           try {
                                    M    eth  od method =    fin dMeth       od(type,   t            his.co   nstructor[i]   );
                               con       str    ucto  rAr gs[i   ]    = M            e   t     hod Util.i        nvoke(met   hod,  oldInstance  ,    ne      w   Obj  e     ct[0]);
            }
                              catch (Ex    cep         tion e) {
                         out.getEx         ceptionListene   r().exceptio    nTh rown(e);
            }
        }         
        return new   Expre  ssion  (ol  dInst  ance, oldIns   tance.getClass()         , "       new", c   on    stru   cto    rArgs);
    }

    p  rivate Method f    ind Method(Clas     s type, S tring propert   y) {
         if (pro       perty == null   ) {  
            thr       ow new   Il  l       egalA     rgume    ntException("Prope     r t        y name i     s n  ull");  
                     }
        Pr     opertyDe   scr                   iptor pd = get   Prop  ertyDe        scri  ptor(t     ype, p     roperty)  ;
                     if      (pd =      = null) {
                 throw new Ill      eg    alStateE  xcep  ti   on("C  ould      not find pro   per       t    y by     the name " + property);
        }
          Met    hod met   hod =   p   d.getReadMethod();
         if    (   method =  = null) {
                          thro         w      n      e   w IllegalSt    ateException("Could n ot find ge   tt     er fo    r the        property       " + prope            rty           );   
           }
        re   turn             meth  od;
    }

      p         riv  ate void doProperty(Clas  s  type, Pro  pe   r     tyD      escripto r pd    ,                        Object oldIn  stance, Object newInstan ce    , Encoder out) th  rows  Exception {
                      Met     hod getter =   pd.g   etRe   adMetho   d    ();
        M e t    ho  d set   ter = pd.g  etWriteM   ethod();

                              if (gett e  r !      = null && setter !=   nul              l       )   {
                      Expressi    on oldGetE   xp = new      Expressi   on     (old       Instance, gett e          r.getNa      me() , n      ew Obje  ct[]{})    ;
                     Expressi   on newG etExp = new E  xpr  ession(newInsta      nce  ,         get         t      er.g   etNa  me()   , ne w Obj           ect[]{})   ;
                          Ob       ject oldValue   = o ldGetExp.getValue(); 
                          Ob  ject n    ewValue =    newGetExp.getValu  e(  )        ;
                out.  wr   iteExpre    s   sion(        oldGetExp);
                           if (!Object s.       e     q                       uals          (ne   wVal                ue, out.get (oldValue)  )) {
                    // Se  arch       for     a   static consta   nt   wi    t  h this   va   lue;
                Object            e    = (       Objec       t[]     )pd.getValue("enume   r  ationValues");    
                                                    if (  e ins     tan  ce           of Obje  ct[]   &           & Array.getLength(e   ) %     3       == 0)   {
                                                 O     bject[]  a  = (Obj   ect[]  )e;
                                        for(   int i = 0; i < a.le  n         gth; i = i + 3)     {   
                                       try {
                                    Field f = t     ype.getField((String)a[i]);
                                                       if (f.ge     t(null).equals(o  ldValu   e)) {     
                                                     out.remo   ve(oldValue)    ;
                                      out.   wr     iteE   x  pressi   o  n(new Expression       (o   ldV    al      ue       , f, "get", new Obje   ct   []{null}));
                                     }
                                         }
                                       catch   (Exception ex          ) {}
                                   }
                                  }
                invokeSt     ateme nt  (ol  dI  nstance,          setter.ge             tName(),         n e   w Object[]{oldValue}, out);
                          }
        }     
    }

    stat ic vo  id invoke         Stat  ement(Object instance, St  ring m     ethodName, O      b   ject[] a  rgs, En    c      o der    out) {
        out.writ eStatement(new S          tateme nt(instance    , methodName,   args)   ) ;
       }

                   /     / W           rite out t  he properties    of     this instan        ce.
                    priv   ate void initBe  an(Class type,            Object oldInstance, Ob        ject newIn        st               ance, Enco   de     r      out) {
        for       (Field     fie          ld :  type.ge t      Fi          elds()) {
                     int         mod        = field.   getM    odi      fiers    ();
                         if     ( Mo         dif  ier        .isFi   nal(mod    ) || Modif  ier.   isStat   ic       (    m       od) ||         Mo    difier.isT   ransient(m   o d)) {
                          c         ontinue;
                          }
                                       tr    y {
                    Ex pression oldGetExp = n  e     w Expression(fie ld, "get ", new Object[]   { oldIn       s           tanc  e });     
                Expr essi  on new         Ge   tExp =  new     Expres   sion(field, "ge    t",     n    ew  Object[] { newInstance   })         ;
                 Ob    ject old        Value =   o ldGetExp.get   Value();
                                        Object       ne wValue =        newGetExp.get Val      ue();
                out.wr               i teExpression(oldGet   Exp);
                if (!Obj     ects.     equals(newValue, o           ut.g   et(o ldVal   ue)           )) {      
                                 out.writ    eStatement(new Statemen      t(field, "set", ne  w Object[] { oldInstance  , oldVal    ue }));
                  }
                }
                    catch (  Exception exception) {
                out.getExceptio       nL    istener().ex    ceptionThr own(           e   xceptio     n   );      
                }
            }      
                    BeanInfo  info;
        try {
                    info =  I                ntr osp    ector.getBea  nI        nfo(type );
            }  catch (In  trospecti      onExcep    tion exception)        {
            return ;
         }
         // Pr    ope    rties
                 for     (PropertyDesc   riptor d :    info.get  P      ropertyDescriptor  s( )) {
             if    (d.isTransient()) {   
                con   tinue;
                         }       
                          tr    y {
                     doPro  perty        (    type    , d     ,  oldInstance, newIn    stance,    ou t);
            }
                 c     atch (Exc   ep      t   ion e) {
                 o ut.getExceptionListener().e     xceptionT                 hrown(e);
                   }
                         }

        // Listeners
               /*
                        Pending(    m                 ilne). Th       er   e is a general problem         with the a      rchival of
        list  eners which is unresolved as of 1 .4. Many  of t     h    e methods
        whi   ch install one obje          ct inside       another         (ty       p  ically "add" methods  
        or      setters) au     tomat   ically in      stall a li    stener  on the "   child" o    bject
                                               s  o that    its "pa rent"   may respond     to change     s t     hat are mad e t    o it.           
         For example the J  Ta  b   l   e:s               etModel() method autom   atically adds       a
                      TableMod        elListener (the       JTable itsel      f in this   case) to the supp   lied
        table mode        l.

           We     do not need to e  xplictly ad      d t  hese listeners to th  e mode l in         an    
        a           rchive as they w ill        b    e   a    dded       aut                  om           a  tically by, in the above case,
        the JTab  le's "setMod    el     "     method       . In so me cases, w         e must  specifically
             avoid t  r            ying   to do this since the listen         er may b   e an inne                  r class
              that cannot be instantiated using   public API.

         No genera      l    mechan    ism currently
                exists for differe   nt   iating    between these kind   of li   steners and
        those which were a      d              ded explic  itl   y   by the user. A  mech   anism must
        be creat  ed    t       o pro   vide a general means                 to diffe      rentiate these
                 spe  cial              c   a s    es      so as  t  o pro    v    ide             reli able per    sisten ce of listene   rs
                for the gen            eral c     ase.
          */
        if     (!java.awt.Component. class.isAssign   a      bleFro     m(t   ype)) {
             ret u  rn; // Just   handle the listeners of Components   for now   .
        }
            f   or (EventSet     Descriptor       d :       info.get     EventSetDescriptors())     {
                            if   (d     .is  Tr        a     nsient()) {
                continue     ;
                 }
            C       lass          list    en e  rType = d.    g et       L           i    ste  nerType();


              // The C       omponentListe  ner       is   a dded a utomatically,    whe n
                //      Contat  in  er      :add is calle  d on the        parent.    
                      if (  l        istener        Type == java.aw     t.e  ve       nt.Comp        onent         List          ener.clas    s) {
                             contin   ue;
                }

                        //        JMenuItems have a chang    e lis     tener    added to them i               n
                                  /  /      t heir "add"      me     thods     to                   enable acce  ssibilit y support -
                             // see the  add     method i   n JM          enu      Item for details. We ca       nnot
                   //    instantia     te       this instance as it i  s a private inner class
                     // and do not need to do     t     his     anyway    since it    will                be   created
                   // and      install  ed by the "add" m   e     thod. S    pecial case this     for now,
            /  / ignoring all change lis    tene   rs o   n JMenuItems.
                           if (liste   nerType ==     javax.swing.event.ChangeListener.class &&
                  type =       = j  avax.swing.       JMenuIte  m .class) {
                    conti  nu       e;
               }

            EventLis  ten er[]   o     ldL        = new   EventListener[0];
                             EventListener[] newL = new E  vent      Li  sten     er[0];
            try {       
                Method m  = d.getGetListen            e r        Method();
                  oldL = (  EventListene  r[])     Metho          dUtil.      in  voke(     m, o    ldInstance,   new Obje ct[]{}  )               ;
                    newL      = (Ev    entListener[])MethodUtil.invoke(m, newInstance, ne   w Object[]{});
                 }
                c a           tch (Exception    e2) {
                   try {
                                      Method m = type.     getMetho   d("    getListeners", new Class[]{Cl    ass.class}); 
                    oldL = (EventListener     [])   MethodUtil.invo   ke(m, oldIn     sta nce, new Object[]{listenerType});
                            newL = (E        ventListener[])Metho        dUtil.invo      ke(m, newIn     stanc  e, n     ew Object[]{listenerType});
                                   }   
                cat     c    h (   Exc   e ption e3) {
                      return;
                }
            }

            // Asssu   me the l  ist  eners a   re in t     he s ame order and that     ther        e are no  gap   s.
              // Event   ual    ly, this        may need to do true differenc    ing.
            String a   ddL is    tenerMeth   odName    = d.getA     dd  ListenerMethod().getName();
             for (int i =   ne wL.l      engt   h; i < oldL.length; i++)    {  
                        // System.out.print     ln("Adding       lis   tener      : " + addListenerMeth  odName + oldL[i]);
                      invokeStatement(oldInstan      ce, addL iste     nerMethodName, new Object[]{oldL[i]}, out);
            }

            S     tring removeListenerMet h odName = d.getRemo    veListenerMethod().getName();
              for (int i = old    L.length; i < newL.length; i++) {
                inv    o  keStatement(oldInstance, removeListenerMetho   dN   ame, new Object[]{newL[i]},     out);
            }
          }   
    }

    /**
        * This default implementation of the <code>initialize</code> met   hod assumes
     * all state held in objects of this type is expos ed    via th e
           * matching pairs of "setter" and "getter" methods in   the order
     * they are r   eturned by the I    ntrospector. I      f a property descriptor
     * defines a "transient" attribute with  a val      ue equal to
     * <code>Boolean.TRUE<  /code> the pr operty is ignored by this
         *     default implem e   nta           t     ion. Note that this use of   the word
     * "transient"        i   s quite independe  nt of the field modifier
     * th  at is use d      by the <code>ObjectOutputStream</code>.
        * <p>
     * For each non-t   ransient property  , an expression is created
     * in which the nullary "getter" method    is applied
     * to th   e   <code>oldInst   ance</code>. The value of          thi   s
     * ex  pression is th    e value o  f the  property in t  he instance that is
     * being  serialized. If the value of this     expression
     * in the cloned environment <code>mutatesTo</code> the
       * target value,  the new val    ue is    init    ialized to ma     ke it
     *    equivalent to the old    valu  e. In this case, because
     * the property value has not chan ged there is no need to
       * call the corresponding "setter" method and no s tatement
        * is emitted. If not ho      wever, the ex  pression for this value
     * is    replaced with another expression (normally          a constructor)
     * and the correspondin      g "setter" method is called to install
      * the new property value in the object. This scheme re     moves
     * default information from the output produced by streams
     * using this     delegate.
     * <p>
     * In passing these statements to the output stream, where they
     * will be executed,  side effects are made to the <    code>newInstance</code>.
        * In most cases this allows the problem of properties
     * whose values depend on each other to actually help the
     * serialization p rocess by making the n  umber of statements
     * that need to be wr   itten to the output smaller. In general,
     * the problem of handling interdependent properties is re   duced to
     * that of finding an or der for the properties in
     * a class such that no property value depends on the value of
     * a subsequent property.
     *
     * @param oldInstance The instance to be copied.
     * @param      newInstance The instan    c    e that is to be modified.
     * @param out The stream to which any initialization  statements shoul d be written.
     *
     * @throws NullPointe  rExc  eption if {@code out} is {@code null}     
     *
     * @see java.beans.Introspector#getBeanInfo
     * @see java.beans.PropertyDes   criptor
     */
    protected void initialize(Cla ss<?> type,
                              Object oldInstance, Object newInstance,
                                   Encoder out)
    {
           // System.out.println("DefulatPD:initializ e" + type);
        super.initialize(type, oldInstance, newInstance, out);
               if (oldInstance.getClass() == type    ) { // !type.isInterface()) {
            initBean(type, oldInstance, newIn   stance, o  ut);
        }
    }

    private static PropertyDescriptor getPropertyDescriptor(Class type, String property) {
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(type).getPropertyDescriptors()) {
                if (property.equals(pd.getName()))
                    return pd;
            }
        } catch (IntrospectionException exception) {
        }
        return null;
    }
}
