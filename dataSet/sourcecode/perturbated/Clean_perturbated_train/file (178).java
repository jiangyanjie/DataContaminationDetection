/*
   *    Copyright (c) 1997, 2013, Ora    cle and/or its affi     liates. All rights res   erved.
 * ORACLE PROPRIETARY/CONFI  DENTIAL.   Use is      subject to       licens   e terms.
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
package java   x.swing.text;

import java.util.*;
import java.io.*;
import java.awt.font.TextAttribute;
imp ort java.text.Bidi;

import javax.swin     g.UIManager;
import javax.swing.undo.*;
import javax.swing.event.*;
import javax.swing.tree.TreeNode;

import sun.f    ont.BidiUtils;
            import sun    .swing.S     wingUtilities2;         

/**
 *       An implementati           on o         f the do    cument i     nterface to serve as a
 * b   asis  for implementing   various kinds of         d   ocuments.  At t    his     
 * le vel th    ere is very little policy, so    there is a corre   sponding    
 * in   crease in difficulty of use.
 *     <p>
 * This class impl   ements a lockin     g  mechanism  for the do          cum ent.  It
 * allows multiple readers or  o ne wri  ter, and writers must wait unt          il
 * a          ll      obs  erve rs       of the document have be  en   notified of a previous
 * ch  ange be  fore beginning anot     her    mutation to the docu   ment.  The
 * read lock is acquired    and release d us i  ng th e <code>render</cod  e>
 * method.  A write lock is acquired by the metho  ds t  hat mutate the
 * doc   ument, and are held for the   dur   ation of th  e meth   od ca      ll.
     * Not    ification   is done on th   e thread t hat pro          duced th e mutation,
 *        an       d   the thread has full rea   d access to the document for   the    
     * duration                 o   f the notification, but other r     eaders are      kept out
 * unti l the notifi      cation has f     inished.  The   notificati     on is a
 * beans event n  ot    ificat   ion which does no t    allow any further
 * mutatio  ns until all listeners have been notified.
 * <p>
 * Any models s        ubclassed f    rom t   his class and used in c onjun   ction
 *        with a text componen  t that has a l   ook and fe   el imp lementation
 * th          at is derived fro    m Basi       cTex   tUI may    be safely updated
 * asynchronousl y  , bec ause      all a    ccess   to the View hierarchy
 *                 is serialized         by BasicTextUI if the docume   nt is of typ   e
 * <code>AbstractDocument< / code>.  The locking ass     um   es that an
 *       independen  t thread will acce    ss the View hie   rarchy         only from
 * the DocumentListener methods, and tha    t there will   be on   ly
 * on    e e   ve nt thread acti     ve  a t a ti      me.
 *       <  p>
 * If concurrency     support is desired, there are the following
 * additional impl ications.     The co  de path for any DocumentListener
 * implemen  tation and any UndoListener implem    entation must be thr eadsa  fe,
 * and not  access the component loc k if tryin          g to  be safe from deadlocks.
 * Th   e <code>repain  t</code>              and <co    d   e>revalidate</code> methods
 * on JComponent are     saf  e.
 * <p  >   
 * A bstractDocu    m  ent m      odels a    n implied         break at the end o    f the document.
 * Among          other things thi     s allows you to      position the caret afte  r the last
 * character. A     s  a result of this    , <code>getLength</code> returns one less
 *      than the length  of the Content. If you cre   ate your own Content,         be
 * sure    and initialize it t     o have an a   dditional character.      Refer t  o
 * Str ingContent and GapContent for e   xamples of this. Anoth  er     implication
 * of this is that Elements that model th e implie  d end character will have
    * an e ndOffset == (   getLen  g       th() + 1). For example, in DefaultStyledDo    cument
 * <code>get Paragraph    Element(getLength()).getEnd         Offset() ==        ge         tLength() + 1
 * <   /    code>.
 * <p>
         * <strong>Warn  ing   :  </strong>
 *      Serialized objects of this    class will                 not be com   patible    with
 * futur     e Swing releases. The current s   erialization support is
 * appropriate        for sh    ort term    s          tora    ge or RMI bet  w  een appl  ications running      
 *   the     same ve        rsion of Swing.  As of 1.4, suppor  t f      or long  term storage   
        * of al l JavaBeans&t    r   ade;
 * has been   added to the <code>java.beans</code> package.
 * Please see   {@link  java.be   ans    .         XMLEncoder}.
 *
 * @author  Timothy Prinzing
 */
public abstract class A  bstractDocu    ment implements Document, Serializable    {

    /**   
     * Constr  u     cts a new <code>A   bs  tractDocumen t</code>, wrapped a  ro    und som   e
     * specified   cont    ent s torag     e mechani      sm.  
     *
     * @param data the content
     * /
    pr        otected    Abstract  Document(Content d   a  t  a)        {
            this(data, Style  Co  ntext.getD  e faultStyle   Context());
                 } 

      /**
     * Const     ructs  a new <code>Abst   ractDocument</code>, wrapped around    some
     *   specif  ied conte nt   storage mechani    sm  .
                          *
          *           @pa    ra m data the     cont                e nt
     * @            p  aram con               text          th  e  attrib        ute conte     xt
        */
    prote   cte                d A  bst ra  c  tDocument(C    on    tent         d                ata, At tributeCo     nte    x t context) {
        thi s.  data  =   data;
         this.context =    contex   t;
                  bidiRoot = new B  idiRootElement();  

                           if (de     faultI18NPrope  rty == null    )       {  
                  // determine def  ault s  ett    ing f or    i18n support
                S tri     ng o               = j         a va.sec  u         rity.Acces    sControll    er.doPrivil     eged(
                              new j             ava.s  ecurity.PrivilegedActio   n<St  r   ing>() {
                                      pub    lic Str   ing  ru  n()     {
                                     retur  n Syst     em.getPr   ope     rty(I         18NPr   oper              ty);
                            }
                           }
               );
               if (o   != null) {
                       de     fa    ultI18NP      rop  erty = Boolea    n.va  lueOf(o    );
              } else {
                   defaul      tI18NProperty = Bo olean .  FA      LSE;
            }
           }
                                         p  utProper          ty( I18NProperty, defaultI  18  NPr   operty);

                               //RE     MIND(bcb    )              This creates a  n initial bidi e    lement to acco unt for
           //the \n that ex      ists    by def  ault in       the            content.   Doing      it  this way
         //s        eems to expose     a             little too much knowledge of th        e  c     on        tent given
        //to us  by the sub-class.  Consider having the sub-clas         s' c    onst   ruct o   r
               //m    ake   an initi       al ca      ll            to insertUpda  te           .
        wri           teLock();    
          try   {
                   E                        lement[] p         = new       Element[1];
                p     [0] = new Bi  diElement( bidiRoot,    0, 1, 0 );
                                   bidiRoo   t.replace(0,0,p      )           ;
        } finally {
                           wr  ite  Unl       o       c  k();
           }
             }

    /**
     * Supports managi       ng a s    et   of properties. C    allers
     * can use      the <   code>documentPro  pe       r     ties     </code> dictio     nary
          * to annotate   th        e document with docume  nt-wide         propert  ies.
            *
                    * @r      etur   n a       non-<code>nu l  l<  /code> <code>D    ictionary</c     od   e  >
     * @see # set      DocumentPrope    rties
        */
    public Dictionary              <Object,Object>     getDocumentProperties()      {
          if   (documentP r  opertie        s == null) {
              documentPro  pertie s = new Hashtable<Object      , Object>(2);
          }   
               return documentP      roperties;
     }

    / **     
     * Replac      es the docu   ment properties dic        tio  n   ary for    this docume      nt.
         *
     * @param x the ne          w dictio  na   ry
             * @    see  #getD  ocumentPr    ope   r  ti               es
                  */
    public voi      d     setDocument     Properties(Di ctionary<Object,Object> x) {
                         do     cu      me  n   tPr   ope r  ties = x;
          }

    /**
     *         Notif ies all listeners that      have r     egistered      interest fo r
             * notific    ation on   t        his event   typ            e.        T he            event instan ce
     *      is    laz      ily     created us  in   g the pa    rameters pass  ed int               o
     * the fi        re me     th od      .
                 *
     *         @param e the e       vent  
         * @    se  e EventList    en   erList
       */
    pro    t    ec        ted                    void fire   In  ser              tUpdate(DocumentEvent  e)    { 
          not      if yingL  istener   s        = true;
                    try {
              // Guarantee   d to          return    a    non-n   ull array
                O    bje c t    []    l   ist    ene rs    =      li   ste  nerList.getListe   nerL     ist(   )  ;
            /       /    Process the     listener        s las    t to    first, notifyi ng
            /       / t hose     that      ar     e int      erested in      this                   ev   ent
                            for (int i  =      listene rs.l  eng  th-2;   i    >=0; i-=2) {   
                    if (listeners[i]=     =      DocumentListener.    class) {
                                       /        / Lazily create the   event :
                                        //   if (     e == null)
                                     // e = n ew ListS  elec    ti  onE    ve   nt(this, firstIndex, las      t      Index);
                                 ((     Docu    mentListener)listeners[i+1]).i nsert      Upd    ate (e);
                                 }
                }
         } fina    lly {
                        not   ifyingL istene      rs = fa   lse;
        }
                      } 

     /*     *
      * Notifies all       lis  teners that     have    registe    re  d interes         t for
     *        notification on this   e  vent type.  The ev        e     nt in      stance
     * is l     azily         create    d using       the p    arameter   s passed i    nto
          * t       he fire method.
     *
           * @param e    the event
                  * @s             ee E  ventListenerLi   st
                 */
    protected   voi    d     fireChangedUp  date    (Documen     t    Event e) {
               notifyingL  isteners   = true;
            try {
                           // Gu  aranteed to return a no      n-null arra    y
                    Object[  ] listen    ers    = liste n    erList.g   et    L       istene       rList();
                // Process the listene   r   s l      ast  to first, notify       ing
                                         //    th  ose that are inte   r          es    ted         i              n this e    vent
                          for (   int           i =                     listeners.l   engt    h   -2; i>=   0; i-=2)  {
                     if   (listene  rs[i]==Do  cumentLi      stener. c  l  as  s) {  
                                             // Lazil  y create the event:
                        // if  (e  ==        null)
                         // e = new ListSelectionEv  ent(t his,         f irstIndex, las      tIndex);
                            ((Doc  umen tListener)li    steners[i+1]) .changedUpd  ate(e       );
                              }
                       }
                     } finally {
            notify     i           ngLi          st         eners = false  ;
                  }
    }    

    /   **
        * Not  ifies all  l      isteners that           h        ave r     e            gistered    inte         rest           for
     * not   i    ficatio n on this       event typ  e .  The e          ve   nt inst  anc     e
      * is lazi  ly created using the        parameters  passed int   o   
           * th e f               ire metho d.
     *
     * @p    aram e      the event
     *        @         s     ee EventListenerList
           */
    pr otected v   o      id fireRemov   eUpdate(Docume     ntEvent     e  ) {
             noti   fyingList          ene      rs =       true;
        try {
                  // Guarantee   d to return a     non-n  ull array
                  Object[] listeners = l       isten erList.getListenerLis   t();   
            // Proce   ss the        listeners last                    to  first, no ti fying
                   // those that are int  er    ested in             this event
             for (int i = listeners.leng    th-2; i>=   0; i-=2) {
                             if     (lis  ten        ers[i]==Documen   t      L     isten     er  .class) {
                            //  Lazily create the event:
                                   // if (  e == null)
                            // e = new ListSel   ecti        onEvent(this,    f i            rstIndex, lastIndex)    ;
                            ((   Docu  men     tListener)listeners[i+1]).remove Updat        e(e);
                         }
                    }
             } fi  nally {
                      notifyi  ngList   eners = false;
        }     
       }

    /**
     *     Notifies all list en      ers that h  ave   re  gi       stered interest     fo         r
             *    notification on t    hi   s event type.          The eve nt instance  
     * is     lazily created usin    g   the par     a  met     ers pass          ed int         o
     *    the fire method  .
       *
                 * @p  aram e         the       event
     *   @see EventListenerList
      */
    protect  ed voi   d  fireUndoableEditUpd   ate(Un   doableEditEve         nt e) {
        // Gu     aranteed to return a n   on-null    ar       ray
            Ob    je   ct[] listeners =  lis     tenerList.ge  tLis tenerLis  t();
           // Proce  ss the    liste   ners last       to    first, notify ing
            //        th  ose tha           t are interes   ted in this  event
                     for (int i =       listeners.lengt h-      2;  i>=0; i-=2)  {
            if  (   listeners[i  ]==UndoableEditL        istener .cla     ss)   {
                                 // La     zily create the event:
                               // i f (e =      = null)    
                // e = new ListSelect  ionEve   nt(this    , firstIndex, las tI   n dex      );
                 (   (U    ndoableEditListener)lis   tener  s[i+1])     .undoab    l         eEditHappened(e);      
                  }
           }
    }

    /      **
     * Returns         an   array of all        the objec          ts c ur r    ently regis     tered
     *    as <code><em>Foo</em>Li st     ener</code>s
        *   upon this doc ument   .
        *   <code>      <e    m>F  oo</em     >Listener</code   >s are registered using the
           * <cod e>a   d   d<em>Foo</em>     L            istener</code> method.
     *
     * <          p>
     * Yo  u       c         an spe       cify   the    <co    de>listene   rType</co  de> argument
                 * w       ith      a class literal, such as
     * <code><em>Foo</em>Listene   r   .cl  ass</c     ode>.
              * For example    , you can q   uery a
             * docu  ment <c   ode>   d</code>       
     * for its do   cume  nt listene rs  with the fol            low ing     c      od    e:
     *
        * <pre>Doc     umentListen     er[] mls = (Docu    men    tLi    stene    r[     ])(d.getList       eners(D    o  cum entLi     s ten      er .  c      lass));</pre  >
          *
        * I  f no such listeners exist, this        me    th    od retur     ns        an  empty       array.
     *
              * @param   listenerType the type of  listener  s  re  que sted; this parameter
     *          should spe cify an interface that descend      s fr    om
               *            <code>java   .util.EventLi    st  ener</code> 
         * @return     an   array of al l objects registered as
         *          <code><em   >Foo</e    m>Listener </cod e>s on thi  s c omponent ,
      *            or an empty a           rray if no   suc h
     *              liste  ners    have been added
        *    @except   ion C      lassCa  stExce ption    if      <code>liste   nerType</code>
     *          doesn't specify a class or inter  fa  ce    th at implements
        *                <code>java.util.EventListe  ner</code>
          *
       * @see #ge tDocumentListeners
     * @see #getU   ndoabl   eEd  itListeners
     *
     * @since 1.3
       */
    publ       ic <T extends       EventList      ener> T[] getListeners(C      la    ss<T> li   stenerType  ) {
        r    eturn listenerList.getL      istene      rs(listene    rType);
    }
  
    /**
         *       Gets the   asynchronous           loading p    riority.  If     l   ess than zero,
     * the documen     t should n     ot be l  o aded asynchrono     usly.
     *
        * @return    the asynch    ro n      o    us        loading p   riority     , or <code>-1</cod    e>
     *   if t     he do   cument   should not be l      o    aded asynchr  onously
         */ 
      publi    c int getAsynchronous     LoadPriority()    {
          In   tege       r loa d   Pr         iority = (Integer)
                  ge   tProperty(Abst   ractDoc     um   ent.  AsyncLoadPriority);
            if   (loadPriority     !=    nul  l)    {
            r  eturn loadPrio    rity.intValue();
               }        
               retu       rn   -1;
    }

          /**
             * Sets th  e asyn  chronou             s l        oading pri         ority.   
     * @pa       ram      p   the new async         hronous load        ing priority; a valu   e
           *   less tha  n zero indicat  es tha  t         th  e d   ocument s    hould not be
     *   loaded asynchro    nou  sly
     */
    p    ublic voi     d setAsynchron  ousLoadPrior    ity(int p) {
        In te ger l     oadPri   ority   = (p >= 0)   ? I    nteger.val           ueOf(p) : null;
                              putP roperty(A     bstrac  tDocument.Async L oadPr     iority       , load     Pri ority)     ;
    }

        /**     
     * Sets the    <    code>DocumentFilter</co    de    >. The <c     ode>Docume    n  tFilter</c    ode>
     * is p  assed <co       de>insert</code> and <code>rem  ove</code> to conditionally
                       * allow ins  erti     ng/d   eleting o f the     text.  A <code>null</cod   e>     val    ue
       * indica     tes th  at no filtering will       occur.
      *
     * @param    f  ilter the <co        de>DocumentFi  lter</cod   e> used      to constrain te   xt
       * @see  #g  etD       ocum     entF    ilt  er
     * @since     1.4
     */
           public v    oid setDoc   um   e      n   tFilter(D  ocumentFilter f  ilter) {
                documentFilter = filter;
    }
      
    /**
          *  Retu      rns the <code>Docum  ent   Filte     r</co de> that     is resp   onsible for
     *    fil         t  er  i  ng of in   s   ertion        /removal. A <c   ode>null</code> return value
       *  i    mplies no     filtering is t    o    o  ccur   .
     *
     * @since 1.4
     *             @ see #se               tDocumen           tFilte    r
     * @retu  rn the Docume    ntFilter
       */  
     publi  c DocumentFilter getDo     cumen  t  Filter() {
                           return     docume         ntFilter;
    }

          // ---    Doc   ument method s -------------  --  -   ---------------- --------- 
 
    /    **
     * This allows t he  model  to be safely   rendered in the pre sen      ce
          * of currency,     i           f the      model supp   o rts        b        eing    u      pdated a synchronously.
            *  The given runnable will            be exe     cu     ted        in a wa     y that allows it
     * to safely re     ad the mode   l   with n    o ch  anges while t he ru nnable   
                                *                  i     s being executed.  The runn able itself     may <e     m>no    t<  /em>
      *  make any     mutations.
     *          <p>
             *     This is im     p     leme  n t  ed     to     acqu   ire a r ead lock for the duration
     * o      f   the    runna   bles e  xecut  ion.  Ther    e ma   y          be mul  tipl  e       runnables
     * executing at the   same time, and  al    l write    r    s will be blocked
     * while             ther   e are    active renderi    ng ru   n    na     bles.  If the runn         able
        * throws       a    n exception, its     l  ock will be safely re      leased.
     * The      re         is no   prot ection against a runnable tha      t never ex   its,
               * which    will e    ffectivel y   leave   the docume        nt lo   cked fo r    it 's
     * lifet   ime.
                * <p>
     *  If                      t     he given        runnable attempts to make   any   muta ti  ons in    
       * this im     p   lementation, a       deadlock will occ  ur   .  There i    s 
        * no tracki           n g of individ   ual rendering thr   eads to      e    na    ble
           *     detecting     t   his   situ   ation    , but a subclass co    uld i     nc     ur
     * the overhe          ad of   tracking them and      throwin  g  an e  rror.
           * <p>           
      * This     m ethod is      thread s afe, alt     hou  gh mos t Swing metho  ds
     * are    not. Ple   ase see
            * <A H      REF="https:  //do  cs.oracle     .com/javas   e/tutorial/uisw        ing/co  n  c     ur rency/index.html">Concurren  cy
     * in Swi    n       g< /A                > fo r m   ore      information.
     *
       * @param r      the r    ende  r     e      r to execute
     */
       public void rende   r(Runnab  le      r) {
                              rea     d      Lock(      );
             try {         
                   r.run();
                                     } finally {
                  readUnlock();
        }
     }

      /**         
     * Retu rns th    e length   of t   he data  .  This    is t         he nu  m   b    er of
       *    characte       rs         of   content that       rep  resents the use    rs data.
     *
     * @retur  n t  he length      &gt;= 0
              *   @s    ee Docum     ent#getLeng     t  h   
     */
    p       ubli   c   i  n t getLength(           ) {
             retur      n dat      a.length() - 1;
    }   

    /**
     * Adds     a document listener f    or not     ifica   tion of any c   hanges.
       *
     *      @  p  aram listene     r the    <code>Docume ntLi          ste ner      </ code    > to add
      *       @see               Docum   ent#addDocument     Listener
          */
    public void addDoc umentLi             st    en    er    (Docu men    tL  isten er listener) {
        li   stenerList.ad            d( D   ocum  en    tL    isten  er.class, listene  r);    
      }    

        /**
      * Rem          ov       es a document listener.
              *
       * @pa       ram list           ene    r the <code>Doc umentListene  r </code> to r emo   v        e
         *    @see Do     cu  ment#r   emov   e        Document      L    istener
     */
    pu   bli   c void rem  o  veDocumentL        istener(Docume   ntListener      listener) {
                 listenerLis t.remove(DocumentListe   ner.c  lass, listener)           ;
                  }

    /**     
     * Returns an array of all the do  cu men      t      l  isteners
     *      r      egistered on  this document.
     *
           *  @return all of this d oc      ume     nt's <code>Document       Listener</c         ode>s
        *                   or a   n     empty arra  y if no do    cument li stener    s are
     *               curre    ntly r  egistered
         *
     * @see     #ad  dDocum   entListener
           * @see         #removeDocu    men  t      Li  s te  ner  
         * @s   ince       1.4      
     */
         public DocumentListene      r[] getD    ocumentLi    steners() {
             re turn l     istenerList.ge tL is  teners(         Doc     umentList           ener.clas            s);
    }

    /**
              * Adds an     undo        li    stener for notif          ication of  a  ny         ch    anges.
     *        Undo  /R  edo operations p  erfo  rmed      on the       <c ode>Un   doableEdit     </code>
         *       will cause t        he app   ropr      iate  Doc     ument   E  v       ent to be     fired t  o keep
     *     the view(s)    in sync     with the model  .
                  *
             *        @param        lis    tener th        e <c  ode>Und oabl   eEditListener</code> to  add
         *      @see Document   #addUndoableEditListener
          */
    public void  addUn    doableEd  itLis tener(UndoableEditListener listener) {
                  list  enerList.a  dd(UndoableEditLi        stener.cl     a ss    , l           is           te  ner);
        }

    /**
          *           Removes  a  n un  d  o list      ener.
     *
     * @param l       i             st   ene   r the <code>Undo  able  EditListener</co    de> to r       emove
       * @see Document#removeDocumentList ener
      */
    publi   c void r         e   mo     veUndoableEditListe   n   er(    U                ndoableEditListener liste  ner) {
            l      istene   rLis t.re mov     e(UndoableEditListen    er.class, listener);     
    }

      /**  
      * Returns an       array of al     l        t he undoable edit listene            rs
     * re       gistered on this docume   nt.
     *
       * @re    t  urn all of this docume   n  t's <co         de>U   ndo      ab leEditLi   ste   ner</ code>s
     *            or a n empty array    if        no u  nd    oable edi  t     li  steners are
                *               cu  rrent     ly regist ered
     *
             * @see    #addUndoa  bleEd      itListe  ner
      * @see     #re moveUndoable     EditL   is         t ener
       *
          * @sin  ce 1.4       
             */
         pub       lic      Undoa b    leEditLis         ten           er[] getUnd oableE   dit   Listeners() {   
               return list  enerList   .getListene rs(UndoableEditLi  sten   er.cl         ass);
        }

    /**
           * A           co nvenienc     e me   thod for loo     ki   ng up a prope     rty v       alue.  It is 
     *           equivalent t o:    
     *   <pre>
        * getDocumentProperti  e   s().get(k ey);
              * </pr  e>
            * 
     * @param k  e         y the non-<code>null     </code>      prop    erty          k  ey
       * @r    etur   n th  e v   a    lue o                 f this property    or <     code>null</   cod  e>
     *   @see      #getD    ocumen  tProper  ties
            */
    p   ublic   final Obj  ect getProperty    (O   bj       ect k      ey) {
        return getD    ocumentPro     p    erties().get( key);
        }         


     /**         
     * A conve                  nience me   thod       for storin   g up       a prop      ert      y value.  It   is
              * e      q      uiva       lent to:
      *      <   pre>
     * getDocumentPro    p     er   ties ().put(key,     value);
             * </pre>
     * If <code>value</code> i        s <cod     e>nul       l</co     de> this met hod wi         ll
         * remove the p    roper            t   y    .
     *    
           * @param key the n o   n-<code>nul    l</code>        key
         *     @param v          alue the proper     ty val     ue
               * @see #get   DocumentPropertie     s
               */
             public      final void p        utProperty(Object ke      y, Obj       ect v            alu      e) {
           if (    value != null) {
                         getDocu      me n             tPrope r    ti      es().put(key, value);
           } else    {
            getDocumentP         ropert      ies().remove(ke   y);
        }     
                    if  ( key == TextAtt          ribut  e. RUN_DI   RECTION
               &&    Boolea   n.TRUE.equals(   g            etPr o    perty(I                 18NP       roperty  )) )
        {
                               //REMIND - this nee  d  s to flip   o   n   the i18n p   ro  perty if r              u    n   dir
              //is rtl and the i        1       8n prop          erty is no     t already o    n.
              w  riteLock()    ;
               tr  y {
                           DefaultDoc  u   mentE   vent e
                                =     new Def     aul       tDocum         entEv    ent(0, getLength(   ),
                                                                        DocumentEv       e    nt.Eve    ntTy    pe.INSERT);       
                     updateBidi(      e );
                   } finally   {
                  writeUnl     ock();
            }  
              }
    }

        /**    
     *   Re   mo   ves some c    onte  nt from t he document.
     * Removing cont e    nt causes a w    ri     te lo        ck to be held whi  le the
     *   actual c   hange    s          are taki      ng   place.       Obser      v  e       rs are notified
                         * o f the ch ange on the thread that called th   is met  hod.   
     * <p  >
     * Thi       s   me   t    hod  is    thread safe, alt  hough most Swing methods
     *    are not. Pl   ease see
                *    <A HR     EF="https://docs  .oracle.com/jav        ase/tutorial/uis      wing      /concurrenc    y/ind                 ex.  html">Concurrency
     *    in Swing</A>  for mo          re    information  .
         *
       * @param   offs the      start   ing offset            &gt;= 0     
      * @param len the    numb  er o f        chara      cte      rs  to rem      ove &    gt ;= 0
              * @exception  BadLocationException  the        given          remove     position i   s not a valid   
       *   po sition   w  i  t    hin  t   he d        ocum  en t      
          * @see Document#   remove
          */
    public      void remove(   int offs, int len)       throws Bad     Locatio     n    Exc      eption     {
        Doc   umentF  il  ter filter = getDocumentFilt   e          r();

          writ           eLock(       );    
              try {
                       i      f (f  ilter != null)  {
                                             fi  lter         .     remo  v e    (g  etFilt      erBypas s(),   o                 ffs,      l  en); 
                               }
                     else   {
                                             handleRemove  (offs,         len);
               }      
          }  fin   all         y {
               writ  eUnlo  ck()      ;
           }
    }

      /**
     *  P     erfo  rms       th   e      actual w    ork   of the remove. It is   a    ss  umed         the        caller
     *    will have obt     a    ined a  <         code>writeLock</c         ode > before invok    ing   th i       s.
              */
    void handleRemove(int  offs, int len) t  h  rows    BadLocationExce    ption     {
        i  f (len > 0) {
                  if (off s    < 0 ||   (offs + len) > getLe     n  gt h())  {
                       throw new Bad    LocationExc     e pt       i  on("Invalid remove   ",           
                                                                  getLe   ngth(   )          + 1);
                    }
                                 De faultDoc      umentEvent ch  ng =
                      ne  w Defaul  tDocumentEvent(offs, len, Doc        umentEven        t.Event  Type.    REM  OVE);     

                   bo   ol        ean isComp   o  sed   Tex   tElement;
                   /    / Check whethe r the  posi       t ion of    int   erest is the compo       sed text
             isC       om  posedTe         xtElement =   Ut       ilities.   isComp   os  edTextElement(this, of    fs);

            removeUpdate(ch  ng);
                Und  oableEdit u =    data.remove(offs,  len);
                    if (u != null) {
                      chng.a   ddEdit(u  );
                 }
                            post     R  emoveUpdate(c  hng)      ;
                              // Mark    the edit as don   e.
                 chng.     end  ()      ;
                fir         eRemov     eUpdat   e(chn  g);
                             /      / only  f     ire undo if C  o    ntent impl   ementat      io      n suppor         ts it
                 // u  ndo          for the              composed text is no  t         suppo  rted for now
            if ((u !   = null     ) && !isComposedTextEle    me  nt) {
                  fireUndoa bleEditUp dat       e(  new U  ndoableEditEvent(this, c     hng));
            }
        }
       }

    /    **
     * Del       etes the region of   tex t   from <code>offset</co    de       > to
     *     <c  ode>off set + leng th              </    code>, an    d replaces it wit   h <co   de>text    </code   >           .     
      * It is up to       the implementati on a     s t  o ho w    this is    implem ente  d, some
         * imp        le    mentations may       treat this     as two      dis           tinct    operat   i      on s: a re    move
     * f     ollo                  w     ed   by an insert           , others          ma      y t        reat the r  eplace     as o ne atom   ic
     * operati    on.
          *
              * @param offse      t i   ndex of child element
       * @pa         ram           len  gth len  gt    h    o           f text to delete, may be 0 in dicating      don't
               *                    delete anything                
                    * @par      am text text to insert, <code>null</code> i         ndicat                es no text      to i    nse    rt
        * @param attrs Attribute             Se  t indicat  ing   attributes of inse   rted t        ext,
         *                     <c               od        e>null</code>  
       *              is le         g        al, a nd        t        ypically treate   d a     s an    empty attributeset,    
        *                          but ex   act interpreta     t      io n is     le ft t o th    e s   u        bclass
                   * @exce     pti    on Ba     dLocati         onExcepti    on the        given position is not a valid
     *            positi  on w it   hin the document       
     * @since 1.4
     */
        p   ubl     ic            vo    id repl  ace(int of          fset, in        t  length, String te   xt,
                                            AttributeSe  t attrs) throws BadL    ocationExceptio   n   {
           if (leng        t  h =      = 0 && (text =  = nu  ll || te  xt     .length() == 0))  {
            ret  urn;
           }
                    Documen      tFilter fil  ter = getDocumentFilt   er();

             writeLock();
          try           {
                 if (fi     lter !       = null)           {      
                       fi   l ter.replace(getFilt    erBypass(),   offs    et, length,  t   ext   ,
                                     a ttrs);
                                      }
               else {
                   if (le n gth    > 0)    {
                    remove(off    se t, l         en     gth);
                                     }
                         if (text !=      nul           l && text.  l    ength    (        ) > 0)      {
                                in  se      rtString(o     ffset, text, at  t    rs);
                              }  
                }   
           } fin     ally {
            writeUnlock();
                  }
    }

    /**
     * Inserts     so   me content into t   he document.
       * In   serting c         o    n t   ent      causes a wr      i     te lock t  o be hel d w        hile      the
        *      ac      tual changes are      taking      place, followed by notifica          t       i  on
     * to th      e obse  rver  s on the thread th   at grabbed the w   rite   lock.
          * <p  >
       * This    method is thread safe,      a       ltho    ugh most S    wing metho   ds
            *         are n     ot. Please see
     *  <   A         H    REF="https:   //     doc        s.oracle.com/   jav     ase /tutorial/ u iswing          /concurrency/index.html">Concurr   e       n   cy
                * in Swing</A      > for mor       e i    nformation.
     *
         *   @param off    s  the star           ting       offset &gt;= 0
     *            @param s    tr the stri   ng to     insert; does n  othing    w           ith null    /empty strings
                 *   @pa   ra m a the attrib    utes       for the insert   ed content     
     * @exc   epti        o       n BadLocat ionE       xcep tion  th  e given insert po  si  tion is not a         valid
      *    po   sition  within the     document
       * @se      e Docume       nt#ins ertS   tring
                   */
       public void inse     r       tString(i   nt offs, String     str, Attrib   uteSet  a) t  hrows BadLocationExc  e ption {        
              if ((str        ==      null) || (str.  len  gth() == 0)) {
                   ret  urn;
           }
          Docum  entFilter filter =                  getDocumentFilter();

              writeLock()    ;

             try {
                   if (filt     er !=  null        ) {
                    filter  .insertStrin  g (getFilterBypass(), offs, s   tr, a)     ;   
            }                 else {
                       ha    nd   leInsertString(offs   ,  str, a        );
                         }
             }   finall     y   {
                    writeUnlo ck();
                  }
    }

      /**
     *      Performs  the a     ct ual w  o rk      o f inserting the          te            xt;   i      t is assumed       the
                * c  alle    r    has o           btai  ned a     write l  ock    before invoking this.
     */
    p     rivate     voi   d handleIn   s  e    rtStri             ng(int o    ffs, String s tr,          AttributeS      et  a)   
              throws BadLocationException {
                    if ((          str          == null  )   ||    (str.length()  == 0     ))    { 
                   r  eturn   ;
           }
           Und oab  leEdit u = data.insertStrin   g(     o  ffs, str);
             Defaul  t    Documen           tEvent e =
               new        Default Do   cum entEvent(of   fs, str.length(),  Do  cu        mentEvent.EventType.    INSE   RT );
          if (u        != n   ull)    {
             e.addEdit     (u);
              }

        /  / see  if complex        gly     ph   layout support is    ne  eded
                           if(     getProperty(I18NProperty).     equ a     ls( Boolean.FA        LSE ) ) {
            // if a default di   rection  of right  -         to-left h  as been specified,
                               // we w   ant com      pl        ex layout even if       the    text is all left t      o righ    t.
                           Obje   ct d    = getProperty     (TextAttribute   .R   UN_DIRECTION  );
                i     f    ((d !=     n u   ll   ) && (d.    equals(TextA    ttribute.RU   N_DIRECTION_  RTL)))      {
                     putProperty   ( I18NProper ty,  Boolean.TRUE);
                          }       else {
                       char  []      chars = st   r .toCharArray();   
                           if (SwingUtilit    ies2.isComplexLay    out(c  hars, 0, chars.length)) {
                           putP       roperty( I  18NProperty,  B      oolea        n.TRUE)   ;
                }
                   }
        }   

               i   nsertUpdate(e      ,    a     )   ;   
        /       / Mark     the ed          it    as d one.  
                           e.end();
         fi       reInsertUpdate(e);
        // on     ly fire    und   o if Co  nt         ent                    imp  lementation supp    o rts it
           /     / und o f   or the composed      text is   not   sup    ported fo    r  n    o   w  
               if (u  != null && (a == null || !a  .i    sDefined (StyleCon stants.Compo  s edTextAttribute))) {
                    fire    Und   oableEdi      tU  pdate(new UndoableEdi    tEvent(t       his, e));
          }
                 }

                /**
     * Gets a  se        q  uence of text    from  the doc  umen  t.
            *
         * @      param offset the st   a        rtin   g o  ffset &g   t;= 0
                    * @pa       ram   len        gt    h the number of c      haracters to retrieve                 &gt     ;= 0
       * @return the   text
     * @exception BadLocati     on  Exce   pt     ion     t       he range giv    en i   ncludes a positi    o        n
     *   that i    s not a valid position w         ithi                n the       doc  ument
         * @see D  oc  ument   #ge  t Text
     */
    pu     blic  String ge   tText(int offset,   int length) throws BadLo   cationExce          ption {       
               if     (lengt  h < 0) {    
                 throw new BadLocationExceptio       n("Lengt      h must         be      positive", length);
           }
        String str = data.getS tring(offset, len    gth    );
        return s   tr;
    }

    /**
           * Fetches the text contained wi    thin the g  iven portion
                * of  the document.
                    *      <p>
     * If the    partialR  e   turn  prop  erty       on th    e txt para  meter    is false, the
          *   da     ta      ret      urned in the S     e   g  men     t w  i           l    l be th    e entire l ength requested             a   nd
           * m ay  or m      ay not  be    a c opy depending    upon ho  w the data         was store         d.
     *     If t       he partial           Re     turn property is true, onl    y the amount of     t   e    xt t    hat
     * can b      e ret  urn  ed withou    t creati  ng       a cop     y is re      turned  .  Using partial  
     * r      eturns wil  l give better  p       erformance for s    itua          ti   ons         where lar ge
           *   parts of   the d       ocument are being  s  ca n  ned.  The f ollowing   i      s    an  exampl   e       
           *     of using the partial   return        t  o    ac cess the entire    document:
     *
     * <pr   e>
               * &n      bsp; int nleft =             doc.getDocumentL    ength()                   ;
     * &nbsp; Seg       m   ent te      xt = n     e  w         Segment(   );
     * &n     bs   p                     ; i nt offs = 0;    
     * &nb     sp      ; text.set PartialReturn(true);
     * &nbsp  ; while (nleft &gt;     0) {
     * &        nbsp;                 doc.getText(offs     , nleft ,         text)           ;
     *       &nbsp  ;          // do      so      methin       g w   ith text
     * &nbsp;      n    left   -= te       x       t.count;
     *   &nbsp;           of fs += t   ext.co                  unt     ;
     * &n  bsp; }
     * </pre>
          *   
     * @par   a     m of   fset  th  e st   arti     ng of       fset   &g   t;= 0
     * @param      length   the    number of    cha   racters to retriev e  &gt;=   0
        * @p ara        m txt the Seg        ment object        to retrieve   the  text         i           nto
     * @excepti    o   n BadL            ocationEx      ception  the ra ng   e given in      cl          udes a p  osition
     *   t ha  t    is    not a valid    posi     tion wi     t   hin the d      o      cu     me nt
     */
        pub l   ic      void     getText(int of      fset, int le       ngth,   Segmen      t txt) throws BadLocati   onExcept   io     n {
              if (len      gth     < 0) {
                        thro w new BadLocat   ionExce          p      tion(             "Le   n gt h   mu     st be positi      v e",    lengt h);
        }              
            da  ta.getChars(off          set, lengt   h      ,     txt  );
          }

    /*  *
      * Returns a position    that will t rack change a    s t        he document
      * is altered.
     * <p>
          * This method    is th    read safe, although      most Swing method     s
                 * a      re     not. Plea   s                     e see
     * <A HREF=         "                     https ://do   cs.oracle.com/javas    e/    tuto rial/u   i    swi   ng/concurren cy/i       ndex.html"    >Co     ncurrency
       * in Swing      </A> for more inform    ation.
          *    
     *  @pa   ram off    s        the posi        tion      in th e mode  l &gt;= 0
        * @return the p   o  s iti           on
     * @exception   B adLocatio nException  if the g iven position   does not
        *   represent a vali      d loc  ation in t      he associa       t          ed       document
     * @see Document#createPosition
            */
    public        synchronized Positi        on cr     eat                 e        Position(int of fs) throws        BadLocationExcept     i   o     n {
                  retu    rn d  ata.createPo   sitio   n(o           ffs);
               }

    /**
           *    Ret      u   rns a posit    ion that    represent      s th        e sta      rt of the docum     ent.  The
         * posi   tion retu  rned can be counted on    to track c   hange and   stay
     * l   ocated a t the  beginnin    g    of the    docume                    nt.
     *
      * @re   turn               the p               osition
     */
        p     u  blic final Posit      ion g   etStartPos       itio      n() {
                   Po   sitio   n      p;
               try {
              p = c   reatePosition(0 );
                 } catch (B  adLoc  ationEx   cepti    on bl) {
              p = n    ull;
        }
           retu        rn p;
     }

    /**
     * Returns  a posit    ion  that represents the    end   of     the       docume    nt.  The
        *   pos     ition returne                 d ca     n be counted on to track   chan   ge    a  nd stay
     *   loca  ted   a        t t         he e       nd of    the do cume     nt.
                  *    
     * @retur n the pos       iti   on
        */
           public         fina l  Positio      n getEndPosi   t i   on() {
                 Position p;  
        try {
            p = createPosition       (data.len        gth())  ;
        }      c atc h   (B      adLocation  Exception      bl) {
                   p = null;
               }
               return p;
    }

                             /**
             *  Gets a                ll root elements defined.  Typical              ly, there
            * wi   ll on   ly be on  e s  o      the defa    ul   t imp    lementation
     * is to return the     defaul t root ele   me  nt.
                 *
         * @   return the root e  lement  
        */
    publ   ic Element   [] getRootElements() {
               Element[] elems = new Eleme     nt[2];
                   elems[     0]    = getDef     aultRootEl      ement();
             elems[1] =                   g     etB      idiRootEl     ement();
             return el  e ms;  
    }

    / **
     * Re turns the roo t elem  ent that vi    ews shou      ld b    e based     upon
        *         u nless s      ome ot her   me chani        sm for assign    ing views to el     ement     
     *    struc     tu  res i     s prov        ided.
     *
              *     @return the root  element  
                  * @see Document#g etDefau      ltRootElement
     *  /
    pub  l   ic abstract Element getDef    aul                   tR   ootElement();
        
    //     ---- local    method      s    ------   ------------   -- ---      ------------------    
 
    /**
               * Return  s th    e <code>     FilterBypass<  /code>. Th  is will crea  te one if one
                     * does not y          et exist    .
       */
             private Do  c   ume     nt    F     ilter.Fi        lterBypass getFilterBypass() {
           if (filt       erBypass    == null) {
                 filterBypass =   new DefaultFilterBypass(); 
            }
           return filterBypass;
    }  
    
            /*   *
           * Returns t   he       r    oot      element   of    the    bid irectional str  u cture for this
               * docum    ent.     Its children represent character ru    ns with a gi    ve       n
     * Unicode bi    d      i  level      .
           */
    public Ele   ment getBid     iRootEl  ement     () {
               return bidiRoot;
    }

    /*  *
                     *      Retur ns true if the text in the     range           <    code>    p    0<   /code>  to
          * <        code>p1</code> is le ft to right.     
        * /
    static boolean isLe  ftTo  Right(    Document   d   oc, int p0, int p1) {
        if (Boolea  n    .TRUE.equals(doc.get  Prop  erty(I18NProp          erty)                   )) {  
                 if   (   doc instan c                 eof   Abs    tr     actDocument) {
                     AbstractDocument adoc = (Ab   s  tra  ctDocumen    t) doc;
                         Ele    ment bid              iRoot = ad oc.   getBidiRootElement();
                         int in            d         ex    = bidiRo     ot.getElem     entIndex(  p0);
                Elem ent b    idiEl    em = bidiRoot.getEle     men  t(i  ndex);
                      if (bid      iEle     m.getE       ndOffset () >= p1) {
                             At    t  rib        uteSet            bidiAttrs = b i    diElem.ge   tAttri     b       ute        s(    );
                         retu  rn ((StyleConstants.getBidiLe               vel(bidiAtt         rs)   % 2) == 0    )   ;
                   }
            }
        }
                        r  etu         rn tru  e;
    }

          /**
     * Get the paragr     aph element containin g      the given position.   Sub         -cla     sses
     * mu       st define for t hemselves what e        xactly cons tit  u  tes          a pa r  agraph.      They
     *    should keep in         mind howe   ver that        a    paragraph    should at lea          st be the
      * unit of  text                 o    ver which to r   u    n the Unicode bidire       c  tional algorithm.
                                       *
                   * @param      p o        s t  he  starting of    f      set &gt;=     0
     *   @r  eturn   th e    ele    me   nt *               /
    p ubli  c abstract     El     eme  nt getPa   ragr      aphElem  ent(int pos);
  

                /**
     * F     etch     es th  e context for managing a          ttr  ibutes.  T  his
     * method effecti    ve       ly establishes th      e strat egy used
     * f  or compressing Attrib     u       teSet         in formation.  
            *
             * @return t    he c        ontex      t
     *  /
         prot  ected final       Att ributeConte    xt        get    AttributeContext() { 
               return context;
             }

    /**   
             *     Updat  es   do        cument structu  re as a r  e      sult of    text    insert    ion.  This
     * will happen withi  n a wri   te  lock.     If a s    u bclass of         
                      * this class rei     m   pl   ements  this m ethod, it sh    ould deleg    at  e t  o the
               * su      perc           las   s as well.   
     *
     * @par   am chng a descr   ipti on of the ch    an     ge
           *   @para         m  a  ttr the  at  trib utes f    or the change     
                  */
              protected void inser   tUpdat e(Def       aultDoc umentEvent chng, Att          ributeSet attr)      {
               if(     getProperty(I18NProper        ty)  .   equals( Boo  lean.TRUE ) )
                                 u       pda     teBid        i( chng    );   

                  // Check if a multi       byte      is encountere   d in the inserted text.
         if (ch  ng  .t         ype     ==     Doc  umentEve nt .Event Typ  e.INSERT &&
                            ch   ng.ge  t Length  ()             > 0 &&
                                                          !Boo      le   an.                      TRUE.equals     (g  etPro    perty(MultiByteP     ropert    y         )))       {
                                Segme            nt se    gme          nt = SegmentC  ache.ge    tShar     edSe       gment(     );
                      t    r  y             {       
                           ge             tTe  xt  (chng  .getO            ffset(), chng      .get           Length(), segm  ent   );            
                                     se   gment.first()   ;            
                     d  o   {
                                        if ((  int        )segment.c    urr  en   t() > 255)  {
                                p      utProperty(MultiBytePro      per        ty    , B   oolea      n    .TRUE);
                                break ;
                         }
                       } w                hile (seg      m    ent.ne xt( ) !=              Segment.DONE);
                       } catch     (B             adLo       c           ationExcepti          o      n     b      le)          {
                          // Should nev     er                   happe   n
                       }
                          Seg  mentCache   .rele   ase  SharedS  eg    ment(  segment);
                }
        }

         /**
                  *   Update       s              any doc      ument str  uctu           re as a result of text removal     .  This
           * method    is called       b efore th       e text is actual          ly remo ved f     rom the C      on        t   ent.
     *       This will hap pen wi  th  in a write     lock. If a subc lass
        * o  f            th   is class reimplements th  is metho   d   , it sho    uld delegate   to the      
           * superclass    as   wel  l.
         *
                 * @param      chng a descriptio    n  of the change
       */
         protected v   oid r                 emov   e   Update   (Def     aultDocumen   tE  ve            n t chng) {
       }

    /**      
           * Updates any    document structure as       a res    ult       of tex            t re   mova  l.              This
          *  meth   o d is called        after               th  e text has bee      n remov ed from the Co      ntent.
     *   This will happen within a  wri te   lock. If a     subclass
     * of this class reimplemen   t  s t         his method, it should delegate     to the
       * s        uperclas     s as we          ll. 
         *
           *      @  par      am                            chng a          description   of     the  chang         e
     */
    protected v oid postRemoveUpdate(DefaultDocumentEvent c    hn                g) {
            if   ( getProperty(I1  8NPrope       rty).equa                ls( Bo ole               an  .TRUE    ) )
                     updateBidi( c        h               ng );
    }


    /**
          *   Updat  e the bidi element structur e    as         a result of      the gi     ven change
          * to         the docum       ent.  The give n chang    e           w   ill be updated    to re f    lect the   
     * changes m ad  e to the bidi      s tru      ct     ure.   
     *     
       * Thi  s method     assumes th            at e   very off      set  in       the model     is contained in
     * exac  tly one  para   g   raph.  Thi     s me     t ho   d       also    as    sumes that it  is called
       * aft er the       c    h   a                n       ge      is          made to the defa ult    element structure.
      */
               void updat   eBidi( DefaultDocumentE            vent chng ) {

        // Calculate      the range       of paragraphs   affected    by   the change    .
               int fir  stPStart ;
                      int   las    tPEnd;
        if( chng.type ==    DocumentEvent.EventTyp    e.INSERT
                                      |   | chng.   t    ype == Docum  en    tEvent.EventType.CHA NGE )
        {
              in         t   ch   ngSta rt =  chng.getOffset();
                   i     nt ch   ngEnd =  chngStar        t     +                          chng.ge         tLen gth();
                       fi rstPS   tart   =     getP    ar  agraphElement(chngStart   )    .ge   t         StartO   ffset()         ;
                       lastPEnd =     getParagraph     Element(c hngE   nd)    .getEndOffset();
          }      e    lse i  f( chn  g.type == DocumentE   vent.EventType.RE     MOVE ) {
                                E    lement      par     agr      aph = getParag     r  ap       h    Elem  ent( chng.getOffset(  )        );            
                        firstPStart = paragrap   h.getS tar t  Offse  t();
                         lastPEnd       = paragraph.getEndO     ffset();
         } else {
                      throw new        Error("Int   ernal error:              unkno   wn event type.");
        }
        //System.out.println("up  dateBidi: firstPStart = " + f     irst    PStart + " lastPEnd = " + lastPEnd   );   
   
 
                             /           / Ca   l   c          ula          te  the    bidi leve   ls for the  aff  e    ct   ed ran ge of paragraphs.  The
        /     / levels      array               will con   t   a     in a bidi level for each               charact     er in the
        // aff ect  ed text.
                  byte l   e  vels[] = cal   c   ulateBidiLe   v  el     s( firstPStart, las   tPEnd );
  

        Vector< Eleme nt> n  ewElemen   ts = new V    ector<Element>();
   
          // C    a  lc ulate                 the          first span of    c       h     aracters i   n the af  f  e  cted range with
           // the     s    ame bidi level.  If this       level    is the    same as the le   vel    of  the
            //   previ            ous bi  di ele               ment (the      existing bi   di el ement c         ontain     i                  ng  
        /     / first  PStart   -      1),    t he  n    mer     ge in the pre   vio us    e lemen  t.  If not, b  ut
                     /      / the previous element overlaps the affected  r   a   nge, truncate the
                        // pr evious              element at firstPStart    .
        int firstSp anSt        a  rt = firstPStar  t;
                 int removeF   r   o   mInde    x = 0;
               if( firs     tSpanStart > 0 )    {      
            int    prevEl  emIndex = bi  di  Ro         ot.g  etElement Index(      fir  st PStart-1      );
                    r           em  ov           eFromIndex = prevE       lemIndex;
                    Elemen     t     prev  Elem =  bidiRoot.getEl     ement(prevElemIndex)     ;      
               int pre   vLevel=StyleConsta  nt   s             .get      B  idiLevel(pre    v    El  em.getA     ttr ibutes()   );    
                   /    /System.ou  t.prin  tln("createbidiElements: pre   vElem= " +    prevEl e   m    +     " prevLevel=    " +   prevLe v    e        l   + "level[0] = " +     levels[0]);
                         if  (    pre    vLe  vel=    =leve   ls[0] ) {
                       firstSpanStart   = p      re vElem.ge   t       S  tartOffset();
                         } els  e if( p     revEl   em.getEndOffset() >  firs    tPStart ) {
                       newE         lements.add   Element(n          e   w Bi        diElemen       t(bidiRoot,
                                                                                     prevE      le      m.ge     t     StartOffset(),
                                                                                             firs tPSta rt, prevLevel));
                     }      e  lse {
                                      removeFrom  In   dex+     +;     
                   }
        }

        i  nt f   ir  s      tSpanEnd = 0;
            wh           ile((firs  tSpanEnd   <leve  ls.length)     && (leve  ls[fi    rstSpanEnd]==level  s[0]))
              firs tSp     anEnd++;
   
   
            // Calculate the last  span of cha   racters in the affected range with 
                  // t  he   s        a  me bidi level   .  If                this level is the   same as the level of the
         // next     bidi ele me   nt (the             existing bid i       element con    tainin    g   las  tP   End),
           // then mer    ge         in   the next element         .  If      n     o t,               but the nex  t             element
                          // over   la   ps   the aff    ected range, a      djust the next element  to    s   tart   at
        // lastP      End.
          int lastSpanEnd     = lastP            End;
        Element newNe  xtElem =     n   ull ;
         i   nt remove      T   oIndex = bidiRo    ot.   getElement       Count()     - 1;
         if(        las    t        SpanEn    d <= ge   tLengt        h() ) {
                  int      nextElemIndex = bidiRo   ot.getElementInde x    ( lastPEnd );
                         removeToInde  x          =  nextElemInd      ex   ;
               El     ement next   Elem = bid iRoot. g  etElement( nextElemInd      ex );
                 int nextLev    el = S tyle   Con    stants    .get                 BidiLevel(ne     xtElem.   getAttributes());
                      i    f( nextLevel =    = levels[l    e   vels.length-  1] ) {
                 lastSpanEn d = ne xtElem.getEndOff  se     t();
                      } else if( nextEle  m.getStartO  ff   set() < lastPEn d ) {       
                             newNextElem = new BidiElemen  t(         b         idiRo ot, la  stPEnd,  
                                                                                        nex  tEle     m.getEndOffset(),
                                                                       nextLevel);
            } else {
                              r  emo  v  e    ToI  ndex--;
                                  }
           }

          i  nt la st     Span                       S  tart     = level    s.l   ength;
           while  ( (las      tSpanStart>firstSpanEnd)    
               && (       leve      ls    [la   st Span   St      art-1]==level      s[levels.length-1] ) )
                   lastSpanSta    rt--;


        //      If    the first and last s     pans    are co   ntiguous and ha   v   e the   same le   vel,
        /   / merg    e           t    he             m a      nd      crea te a sing              le new       e      le  ment     fo r the entire s   pan.
           // Ot h    erwise, create eleme          nts for th     e first and    last   spa     ns as wel                 l       as
                     // any       spa  ns in be twe   en   .
        if    (   (firs    t  Sp   anEnd==lastSpanSt         art)&&(levels[0]==l     e vels[levels       .length-1])){
                      n       ew       Elements.addE             lement(new    B       idiEleme       nt(bidiRoot, firstSpa      nStart,              
                                                                                      lastSpanEnd, levels[      0]));
          } else {
            //     Creat    e an element f or the first sp   an .
               newElements     .addEleme       nt(new    BidiE   lem  en    t(bidi   Roo         t,               firstSpanStart,
                                                                            firstSpanEnd+firs tPStart,
                                                                             lev   els[0]   ));                  
            // Crea       t     e        ele    ments for th  e sp   a    ns in betwee    n the fir st     and la   st
                 for( int i=firstSpanEnd; i<las  tSp anStar   t; ) {
                      //S      ystem.out.p   r   intln("exec  uted li  ne 87   2");
                                   i  nt j;
                                  for( j=i   ;  (j< levels     .  length)        &&      (levels[j] ==      levels[i]); j++      );
                                    newE  lements.addE    lement(new BidiElemen    t(    bidiRoot, fi           rstPStart+i,
                                                                             fi        rstPStar    t+j,
                                                                         (int)leve  ls[i  ]))    ;
                        i=j;
                   }
                     // Cre    ate an elem                      ent for the las   t span.
            newEl       ements.addElement(new BidiElement(bi   diRo ot, 
                                                                        lastSpanStart+    firs          tPS  tart       ,
                                                                                             la   s            tSpanEnd       ,
                                                                                            leve   ls[levels.length-1]        ))   ;
                 }

                 if( newNextElem     !  = null        )
                      ne         wE    lem  e  nts.ad   dElement( newNext  Ele m );

 
                 // C alculate the set   of  existing    bidi eleme   nts wh     ic  h must b e
              //         r  e           move    d.
                                     int   remov  edElemCount = 0;
        if( b   idiR      oot.get   Eleme  ntCoun   t()   >     0 ) {
              remov         edElemCount   = r  emoveToIndex -     removeFr   omInd      ex   + 1;
               }
             E   lem  ent[     ]     removedElems =   new El  ement[removedElemC              ount];      
              for( int     i= 0; i<removedElemCoun      t; i+  + ) {
                    remove  dElems[i] = bidiR oo             t.get  Elemen         t   ( remove  From       In                                     d ex        +i);
                 }

        Element    [] addedElems =             new             El   eme  nt[ n      ewElements  .size()    ];
           newElements.copyInto( addedElems );
   
             /      / Update the c     hange record.
                  E   le      ment   Edit    ee = n   ew E     lementEdit( bidiRoot,   remove FromIndex,
                                                                  remo       ve   dEl         ems        , addedElems    )     ;
           chng.addE   di t( ee )  ;

        // Update the bi  di eleme  nt structure.
        bidi   Root.replace( removeFromIndex,   removedElems.length, ad    de  dElems       );
    }
  

    /**      
     *     Calcula        te the level      s  a rray f    or      a ran  ge        of pa  rag  raphs.
        */
    private b yte[] cal   cul    at    eBidiLe    vels( int firs     tPSt    ar   t, int lastPEnd        ) {

            byte     le vels[] = new byte[ lastPE   nd -         f    irstPStart ];
        in      t  levelsEn    d =   0;
              B  o olean de      f aultD    irection = nu ll;
            Object d = g  etPr         operty   (TextAttribute.RUN_DIRECTION  );    
           if                 (d ins   ta      nceof Boole an     ) {
                                    defaul    tD    irecti on   =    (Boolea    n)   d;
                }         

         //     Fo  r each parag     raph        in the g   iven  range of paragra  p    hs,      g   et   its
        // l  evels array     and add it to the     levels arra         y for     the en  tire span.
        for       (int o=firs         tPSta rt; o<lastPEnd; ) {
                Element    p =  g  etPa ragra phEle     m    ent(   o     );   
                             i       nt pStart =      p.g etStartOffset();
                      int pEnd = p   .ge   tE   ndO       ffse   t()   ;

            //           default ru   n directio    n      f    or t   he paragrap      h.  Th    is will be
                    // null     if there is           no d    irection ove          rr      i de specified (i.e.         
            // th   e direction will be   determined f  rom the          content).      
                     Boolean d irection = de    fa ult       D     irection  ;
             d = p.     getAttributes().ge  tAttribute( TextA    ttribute.RUN   _DI  RECTION)     ;
            i       f     (d    instanceo f Boolea          n) {
                             di                 rection = (Bool   ean      )     d;
                 }

                               //System.out.println("u  pdat  eB    idi: par agr        aph sta rt = " +      pStart +   " parag   raph end = " + pE    nd    );

            // Create   a     Bidi  over t   h       i  s paragraph then        get the   leve l  
            // array       .
                Segment seg =      Segme   ntCache.ge   tShar    edSegment();
                 try {
                getText(  pSta    rt, pEnd-pStart, seg);
                }    catch (BadLocationEx     ception     e ) {
                                 throw ne w        Err  or       ("Internal error: "         +     e.toStri      ng(    ));
                }
                          //      REMIND(bcb) we      should real  ly be using a     Segment    here.
                    Bidi bid  iA    nal    yzer;
                    i              nt bidiflag   = Bidi.DIREC    TION_DEFA     ULT_LE   FT_TO_RIGHT;
               if   (direc       tio  n    != null) {   
                if  (TextAt trib  ut   e  .RUN_DIRECTION_ LTR.e   quals(direction)) {
                                 bidi   flag = B       i      di.DIRE  CTION_LEFT_T       O_RIGHT;
                  } else {
                                                b   id           iflag = Bi          di.DIR   ECTION_RIGHT_TO   _LEF T;
                      }    
               }
                  bidiAnal    y ze   r = new Bidi(seg.a         r  ray,  seg.of fset, null, 0, s   e     g .     cou       n t,
                                         bidiflag);
             BidiUtils.getLevels(bidiAnalyze r,     levels   , l          ev      el    sE    nd   )       ;
                       leve    lsEnd += bidiAnal     yzer.g   etLe       ngth();   

                  o =  p.getEndOffset  ();
                  Segmen             t Cache.releas   eS   haredSeg      men   t(seg);
              }

            // R    EMIND(bcb               ) remov     e this   co de   when deb ugg    ing     is  do   ne.
               if(   leve     lsEnd != levels.leng    th )    
                    throw new Error("lev  els    E   nd         assertion failed.");

         retu      rn levels;
    }

         /**
         * Gives a di   agn    ostic   dump.
     *        
          * @p   ar am out t   he outpu     t stream
     *    /
        publ       ic void dump( Pr int   St  rea    m ou  t)   {
          Element ro     ot = getDefaultRootEle  me   nt()  ;     
        if (root inst    anceo      f Abstr   actEleme          nt) {
              (   (Abs tract    Element)root).dump (out, 0);
                }
                      bidiRoot.dump(out,0);
            }

     /**
            * Gets t             he content for    the document.
       *
                      * @return the co    n      tent
     *  /      
    prote cted final C ontent getContent() {
          retur  n da  ta;
       }

          /**
     *       Cre    ate  s     a document leaf ele   me        nt.
       * Hook through whic h      elements ar  e created     to repres         ent the
              *         d o   cume   nt stru    cture   .  Beca  u       se this  i         mpl   ementation keeps
      * s  tr  ucture a     nd    co nte          nt sepa  rate, elements grow automat    ically   
     *      when co             ntent is extend    e     d s  o spl   its    of existing elemen   ts
     *            follow.      The docu     men   t   i ts  elf gets to deci            de how to ge   nerate
           *  ele   ments to give f      lexibil    ity       in the type   of el  em  ent   s  us     ed.
        *
         * @pa    ram p   a  ren    t the paren t elem   e nt
     * @param a the at          tributes for the eleme    nt
                   *                  @param p  0 the beginning       of the r  an  ge    &gt;=         0
           *  @param       p1 the  e n    d of       th    e range     &gt;= p0
      *    @ret  u   rn  the new element
                     */
    protected Elem      ent cre   a      teL  ea     fEleme     nt  (E    lem  ent paren        t,    AttributeSet    a,     in            t p0, int       p1)      {
          re       turn new L        eafE  lement(parent, a, p0,    p1);
    }

           /**
         *       C  reates a     docume  nt br a  nch elem  ent, that can contai     n other elemen   t         s.
          *         
          * @para  m par    ent the paren  t element
     *       @param a the a    ttri          b    ute     s   
     * @    re  tur     n t   he   elem         ent
           */
    protecte d Elemen   t cre at eBranchEleme      nt(Element parent,      AttributeSet a)   {
             return new BranchEle ment(parent, a);
      }

                    // ---       D  ocu  ment      locking -------  --          --------- ----------------

    /**
             * Fetches    the current writing thread if there is one.
                      * This can be used     t      o distinguish whethe r a      method   i    s
          * be                    ing called  as part of an existin        g   modification    or
     *        i     f a l  ock needs to be ac qui   red and    a new       tr           an   saction
                  * start ed   .
        *
     *    @return the     thread actively   modifying     the document
     *          or <code>n       ull</code>                          if th    ere are n       o modifications              in progr    es       s      
                 */
    protected synchroni        zed final Th     read getCurr  ent  Wr  iter() {
        retu rn cur     rWrit    er;
      }

      /**
        * A   cquires a    lock    to     begin       mut      ating the d     ocumen         t this        lock     
       *         pr   ote c    ts  .           T     here can   be    no wri       t     ing, no   tific   ation of changes  , or
        *       readin   g going    o         n in     o      rder to ga   in the lock.  Additional      ly a    th  read i s
     * all  owed to        ga    in   mor e than on      e   <   cod       e  >   writeLo        ck</code>,
             * as  long   as it doesn't attempt            to ga  in     additio   nal <cod    e>writeLock   </code  >s
     * from within document n  otification.         Atte   mpting t   o gain    a
           * <code>write           L  ock</c         ode> from   wit     h         in            a DocumentList   e   ner   no   tifi      cation will            
     * r   esult i   n an <code>I llegalStateException</cod  e>.  The abi  lit y
            * to        o            bta in m     o    re than     one <co    de  >w   riteLock</c  ode> per thread allows
     * subclasses   t     o    gain a wr  iteLo   ck, per     form a       number of     op   erations, then
         * rele             ase the lock.
      * <p>        
     *        Calls to <c    ode>    w  ri    teLock</code                    >
     *  must be   bal         anced        with call  s to   <co          de>wr   iteUnlock</code>,   e      lse the
               * <code>Docum    ent</code> will be left in a locked state s    o that n     o
         *  reading o  r w r        iting can be   done.
     *   
       * @          exc    eption IllegalS  tate           E   xcept     ion thr     own                on illega   l       lock      
       *         attemp   t.     If th e doc   ument      is imple  mented       properly, this can      
            *      only ha       ppen i    f             a document l     isten   er at    tem pts  to muta   t    e th      e
     *  document.  This       s  itua tio  n v i  olates the bean event model
     *  where         order of   de           livery is not g       u    ara       nteed and all liste   ners
      *  s     h    o            uld    be notified before f  urthe          r mutation  s ar       e  al  low ed.
     */
        protect   ed synchronized f   ina    l vo  id    writeLock() {
        try             {
              while ((numReaders >           0)    || (cu   rr     Writ     er != nul  l)) {
                   if                   (Th read.current        Th read() =   = currWri ter) {
                                    if (notifyingLi  st     ener   s)   {
                                                 // Assuming one          doesn'   t  do    something wr  ong  in a
                                                       // subclass t   his s   h    ould only              happen i                 f a
                                  // D ocumentL        i sten   er   tries        to mu       tate the do              cument.
                                          t   hrow        new  IllegalStateE          xcept    ion(
                                                  "Attem pt                 to mut    ate in   notifi          cation") ;
                                          }
                                              numW     r   iters++;     
                           r   eturn;
                      }
                                                         wait();
                  }        
                    cur rWri   ter = Thread.currentThre   a     d(); 
                 nu         mW     riters         =   1;  
               } catch (Interrupted  Exc   eptio n e  ) {    
                   throw new   Erro    r("I        nt             e   rrup ted    attempt to acquire      writ  e lock     ");
           }   
    }     
  
             /**
     * Releases a    wr              ite l    oc        k p   reviousl     y o   bta    ined via <cod   e>      writeLo ck</code>.
        * Aft er d   ecrement     ing  the  lock count if the   r  e a re no outstan   ding    l   ocks
                   * th    is wil    l a              llow       a new writer, or rea    d   ers.
     *
               * @see #wr    iteLo     ck
        */
    p  rot e cted syn   chronized final voi d w   riteUnlock() {
                             i    f (--nu  mWrit     ers   <=         0     ) {
                 n    umWriters =           0;
                      curr  Writer     = nu     l   l;
              notifyAll  ()  ;
          }
    }

    /**
     * Acq  uire  s a  l     ock to begin reading s  o    me stat       e from the
                           * document.  Th    ere can be       mul              ti    pl     e reade        rs at the  sa   me time.    
          * Writ   ing blo cks the  readers until notification o     f the change   
     *     to t     he  l iste    ners     has been co    mpleted.   This method sho    uld 
       *     be used very ca    refu   lly to   avoid unintended compromis  e
     * of the     document.   I      t shou ld always be balanced with a
     * <code>readU nlock</ code    >.    
        *
       * @see #r   eadUnlo           ck
           *         /
       public     s  ynchronized f    inal vo  id    readLo  ck    () {
          try {
                     whil   e    (currWrite  r !   = n ul       l) {
                                   if (currWrite      r =       = Thre    ad            .cu           r        re   ntTh   re  ad()) {
                                               //    w  riter has full        rea  d    access...     . may  try to acquire
                               //    lock in n           otification
                      return;    
                         }
                        wait();
                   }
              num  Re   aders += 1;  
                   } catc          h     (    Interrup              tedExcepti  on e     ) {
                     thr   ow new Err     or("Interrup       te   d attempt to acquire read lock");
                 }
      }

              /**
          * Does a read un   lo    ck.       This signals that     one
        * of the readers is do         ne.        If      there are n    o more readers
         * then  writi   n  g can begi      n   aga  in.      T      his         should be balanced
     * with a readLock   ,    and should occur in a finally    stat     eme      nt  
         * so that  the   ba   lance       i     s guara      nteed    .  The following is an
     * example.
         *  <pre><code        >
                  * &      nbsp       ;   r      eadL      ock();
        * &nbsp    ;   t ry {
     * &nbsp;       // d                 o somethi ng
              * &nbsp;            } fina      l     l    y   {
     * &    nb       sp   ;       readUnlock      (   );
       * &nbsp;   }  
         * </code></pr           e>
           *
     * @see    #  readL  ock    
            */
    public sy   nchr    on            ize   d     final    void re adUnl ock()     {
          if (currWri          ter = = Thread.cu    rrentThre    ad   ()) {
                             // wr     iter has    full re     ad access....    may try     to acq   uire     
                            //        loc   k in notification
            re    tu rn;
                           }
            if (numRe           ade        rs  <= 0)     {
                                t  hr      ow new     State   Inva  r   ian tError(BAD_LOC    K_STATE);
        }
                  numReaders -=    1    ;
                notify();  
    }

                   // --- seri    a  lization ---   ------------     --- -  ---    ------  - ---  -------------

            pri  v   ate void readObje   ct(Objec   tInputStream         s) 
      thr        ows    ClassNotFoundException,  IO  Exception              
    {
        s.def  aultReadObject();
               l      isten    e           rList =      new      Even    tListenerList();

                              // R   estore b        i          di structu   re      
             //  REM     I    ND(bcb) This cr     ea    tes an i    n   iti  a  l bi       d  i element to    account f     or
                     //the \  n  that      exist     s      by               default in th    e content.
                 bidiRo  ot = new B     idi  RootElement();
               try {
                              writeLo        ck()          ;
                        El    emen       t[  ] p = n      ew Element[1];
                p[0  ]    = new BidiElemen      t(           b       idiRo    o   t, 0, 1, 0     );
               bidiRo ot.   replace(0,0,p)                                       ;
           }    finally {
             wr iteUnl              ock();
          }
                    // At this poin     t bidi                   root i         s       only    partially corr  ect           . To fully  
           //        restore i t   w    e need a            c     cess      to getDefa   ultRootEle   me             nt. But, th        is
                        /   / is cr     ea      ted by      th e s ubclass  and at this point will be null. We
         // thus u   se r             egi   ster         Val  id   ation.
             s.re  giste         rValidation(new ObjectInputVali dati    on()          {
                             pu    blic void va       lida  teOb  ject()     {
                try {
                                               writeLock();
                            D    efaultDocu  mentEvent e = new Defa  ultD   ocumentE   vent
                                                                           (     0, ge  tLen gth       (),
                                                             D  ocum           entEve         nt.Even  tTy    pe.IN   SERT);
                          upd       ateBidi( e );
                     }
                                          fi           nally {
                           wr    iteUnloc     k()     ;
                                }
                  }
            },       0);    
       }

       // --    ---   member va  riables -   ----------    ---  -----    -       -----    -     --           -----   --------  -

        private        transient int numR            ead    ers;
      priva  te tr              ansient Threa                 d   currWriter;  
      /**
         *        The     numbe   r o   f   writers, all ob  tained from <code>c   urr    Wr   ite           r<    /c  od   e>.   
     */
      private transie      nt int           numWr     ite  rs;   
    / **
     * Tr        ue will noti   fyin    g l   i    st   ene      rs.     
     */
            private tran              s    ient b o  o lean notif        yi  ngListe     ners;

    private static Boolean defa         ultI18NPro     perty;

    / **
         * Storage for      d      ocume      nt-wide proper  t         ies  .    
               */
        priva   te Dictionar  y<Ob ject,Ob    j    ect>      documentProperties = n    ull;

    /**
     * The       e      vent listener list for    the document.
     */
    pro        tected E  ventL  is     tenerList    li        stenerL  is                 t = new EventL istenerLi     st();

          /      **
            *  Where   the text is a     ctual   ly stored,     and a set of marks
           * t   hat     tr  ack    change as the doc    ument is e      dited are     m          anaged.
       */
    p     r    ivat   e Content data;

                   /**
                  *      Fact       ory for         th      e     a t      tri   but       es.      This   i           s the   strategy for
               * attrib      ute         compr   essi    o       n a     nd             control  of the lifetime    of
        *   a set o     f attribut  es as a collect   ion.  This    may be sha        red
        * with other documen  ts.
           */
                         priv  ate Attribute Con  text con     text;

           /**
         *     The  roo                 t of the    bidirecti on al      structure fo     r this     d  ocument.          Its c hildren
      *     represe             nt    character r          u    ns with the      sa  me Un      icode bidi le     vel.
     */
    pri   vate transie    nt Branc                hElement bidiR   o    ot;

    /**
     * Filte  r fo r    i        n serting  /removing of text.
     */
     priv           ate     Do cumen   tFilte    r documentFilter;

    /**     
            * Us   ed by     DocumentFilter    to do actual ins   ert  /remov e.
     *    /  
    pr       ivate transie  nt Do        cumentFilter.FilterBypas                              s filterByp       ass;
   
      private         static    final String           BAD_LOCK_STAT                 E = "document   lock fail   ure";

    /*       *
     * Error    mess    age    to indicat  e a        bad     l    oc       at             io n.
     */
     protected static f     inal      String BA D_LOCATION = "    d    ocum  ent     location     failure";

    /**
        * Name of  el        ements u  se  d to represent paragr  aphs
              */    
    p u  blic static final              String ParagraphElemen       tName = "parag   raph";
   
    /            **
     *   Name o        f elem         e nts used      to           represent cont   ent
         */
             public static final St  r  i     ng C    on    tentE  le    mentNam      e = "   content" ;

     /**
         * N    ame of elements used to   hold section  s (l   in         es/para           graphs).          
     */
     public static final S   tring   Secti on      E  l    e    ment    Nam       e = "sectio     n"     ;

     /**
     * Name of elements   used to hold a unidirectiona  l run
         */
    public sta         tic final       String B     idiElementName = "   bidi level";

      /**
      *      Name of the att     ribute       u     sed t   o  specify elem  ent
     * names.
         */
    pu    b  lic stat     ic final  String    El ement  Nam   eAttr   ib        ute = "$ename";
   
    /      **
            * Document     pr  operty t   hat indicat   es wh    e    ther i      nternation               ali za   ti o   n
     * functi      ons such as te          xt   reorde ring or res         hap          i   ng should     be
     *     per   fo  rm  e    d.   This proper     ty    should  not   be pub    licly       exp    osed,
            * since   it  is used for implementation conven  ience onl                     y.  As a 
              *       sid    e e ffect,     copies of this property may b  e in its subc              lasses
          * th at liv e in       dif        f   erent p ackages (e.g.         H   T MLDocument as of n     o      w),        
      * so those co   p      ie                                s should als  o be   taken ca            re of     when this          property
       * needs to  be    mo  difi  ed.
     */       
      st        ati c    fin    a l String     I18NPr          opert y = "i18n";

    /**
     * Docu ment p roper  ty that indicates if a c  haracter   has       be       en in  ser  ted
     * into   the     docum   e  nt    that       i      s m       ore     than on e b     yte long.           Glyph         Vie  w use  s
              * this to de termine if     i    t shoul   d use Bre       akIt      erat   or.
        */  
       static   final Objec         t Mu    ltiBytePr        operty = "multiByte   ";         
  
               /**      
         * Doc  umen   t prope rty that  ind     icat      es              asynch   rono  us load        ing i    s
           *     desired, wit  h   the thr  ead pr                  iority given as the value.    
                */   
      stati         c    fi  nal String Asy        n  cLoad Priority    =           "load     priority    ";

                        /**
     * Int    erface t    o descr   ibe a s  e    qu            ence of ch    aract   er content t   hat
              *    ca    n b    e edite     d.  Imp lementations   m  ay or     may not   suppor      t a
     * history      mec                     hanis     m w              hich wi      ll          be refle  cted by whether or not
        * mutations r      eturn an   Un        d     oableEdit im  ple    mentation.
      * @see Abst  ra   c     t          Docu    men    t
              */
    pub lic  int   e    rf   a ce Content {
      
              /**
         * Cre ates a position within the    co   ntent t  hat      will
                    *    track ch  an    ge as the        content is        muta    ted.    
         *    
         * @pa   ra    m  off  s   et   the    o    f          fset   in the   co ntent &gt;= 0
                 * @return a Positi   on
              * @e      xception               BadLocati on               Exceptio   n for a   n invalid of   fse   t
         */
            public  Posit      ion createPositio       n(   i    nt     off   set      ) th    rows BadL  ocat ionExcep     tion;    

        /**
           * Current leng    t              h   of the seq u e          n    c e  of       c      haracter   content.    
         *
             * @ret       urn th e length &gt;=          0
               */
                    public int l          en     gth          ();     

        /**
                      * Inse  rt              s           a string           of      characte    rs            into the sequence.
                 *
          * @par          a     m where   of   fset into   the sequ  ence to ma  ke    the inse  rt        ion &gt;= 0   
         *      @par            am str          string to in     sert
                   * @return  if t                  he imp               lementa     ti on         supp       ort   s a histo ry mech  anism,
          *          a r      eferen      ce to an <co   de>Edit     <    /cod  e> implement    ation    will be returned,
                               *          otherw     is           e retur   ns <co          de>n   ull</code>
         * @exceptio n Bad   Location    Exc      eption  throw  n i   f the   area covere  d    b           y
            *   the      arg     ume      n   ts is   not   contai  ne    d in the cha   r  act    er seque  nce
                  */
               public    Undoa   bleEdit inse     rtStr     ing(   int where, String str) thro    ws BadLocation   E xcept               i       on     ;

                                /**
              * R  e      moves so             me p ortion of t           he sequence.
          *
           *    @pa    r     a      m where    The       off   se                 t into th            e se qu   ence to make the
                            *               in  s    ertion &     gt;=   0.
             *   @param ni tems         T he            n   umber of items  in the sequ         ence to remove   &  g  t;             =     0.
           * @return          If the implem   enta  tion supports  a history          mechanism,
                  *    a referen          ce to an    Edit       impl      ementatio       n will be  r  e                turn          ed,
           *    otherw     ise null.
                  * @exception       BadLocationException  Thrown           if t h    e    area covered by
                     *   th                 e arg  u      ments is     no   t contained i n the character sequ          e nce  .
         */
                public Undoa  bleEdit remove(int where, int                    nite   ms) thr    ows BadLoc         ationException;

        /**
         *        Fet ches a st         ring of charact  er    s con    tai       n         ed in t he s           equen      ce.
                               *
                                  * @p ar     a  m    whe       re           Offset    in    to             the seq  uenc e to fe   tch &gt       ;=   0.
         *  @p  ar    am len            n  u    mb    er   o          f    c               haracters to   copy &gt;= 0.
                            * @r     eturn th           e   string
         *    @excep ti  on Ba   dLoc   ationExcep     t   i o     n   Thro   wn      if the     area cove  re    d by
                         *       the    argumen     ts is not     cont   ained i     n the  c hara   cte        r sequence.
              */
            publi     c St  ring     getStrin    g(i    nt         wh       e               re, int   len) throws BadLocatio   nExcep      tion;

        /**
           *   Gets a     sequenc e o f chara  cters and co      pies them into a Seg  ment.
            *
                       * @pa  ram         wher    e the starting  offse t &gt;= 0
                         *      @par       a    m      len the n  umber   o f characters &gt;=  0
                 * @param txt the  targe  t        location to     cop   y into
          *    @    exc  epti  on BadLocationE   xcep     tion  Thrown if the        are  a covered   by
                         *                the arg    uments is not contained in   the character s  equence.  
             */    
                          p                 ub      l   ic voi       d    getChars(int wher       e, in     t l              en, Se   gment   txt)                 throws BadLoca  ti       onE     xc             e ptio   n;
    }

                 /**
          * A n inte    rface that c an b  e   u    sed to allow MutableAt    tribut         eSet
     * im plementations    to use plu  gg   abl     e att   ribute compress   ion
     * techn   iques.     Each mutati                      on   of            the     a   ttr   ibute      se   t can be  
       * u     sed to exchan  g                         e a              prev        ious AttributeSet i   nstan   ce        wit   h
              * a  n         other, pres          ervi       ng the p  oss    ibili                   ty of the At   t    ributeSet
      *                rema     ining      i                 mmutable.    An implementat   ion is provided by   
     * the StyleCo     ntext clas   s.    
     *
       * The Element implem    en   tations         provided by this class       use
     *    th   i  s inter  face to provide   their Muta bleAttr    ibut      eSet
     *       i  m    ple    menta     tions, so that differ                  e  nt Attribute    Set compression
     * techniques  c          an      be e      mp       loy    ed.  Th           e method
     * <cod       e>get            Attr   i  but     e  Conte      xt</co        de >      sh ould be            imple   m e   n  ted     to    
             *                      return the object   responsible          fo    r   impleme        nting    t      he     desired
            *     compre     ssion tec  hnique.
     *
       *      @see StyleCo   nt     ext
                      */ 
    public interfac     e        Attr    ibut  eContext         {
 
        /**
             * Adds a    n attribute to t   he    g  iven se            t   , and ret   urns
         * the new representat         ive s   et.
                           *
               * @param old      the        old attribut   e set
                            *   @p  ar  am na  me the non-null attribute       n ame
                            *    @param                  value           the      attrib     u        te      value
         *   @re tur    n th   e upd            ated attr      ibute set
         *    @see MutableAttr     ibuteSe       t#add      Attribute
         *              /
              public Attri  buteSet addAttribu te(At tr ib   uteSet old  , O   bj  ect name, O    bject va    lue   );

                       /**
                        * Adds a      s       et    of  attri      butes to       the elem  en   t.
                          *
         * @ param o     ld the old   attribute set    
                      *        @para    m attr      the attri        b                   u     te     s  to   add 
           * @return the updated  a  ttri     but  e set
              * @see Mutable      At         t      ributeSet#addAttri bute 
                    */
        pu   bl       ic     A  ttributeSet addAttributes(At     tri   bu    te  Set old, Attrib   uteSet attr);

        /**
         * Removes an    a                       ttribute fr    om          t   he  set.
                  *
                 * @p              a   ram old the old   attr  ibute         set
                     * @para m nam     e the n   on     -null att       ribute n   ame
         * @r        e  turn the updated att    ribu   te set
               *            @   see M    utableAtt          rib                      u      teSet#remov        eAttribute
                   */
           pub    lic Attribut    eSet remov        eAt                     t     ri               b   ute(  AttributeSet o               l  d  ,          Objec   t          na   me);

               /**
                * Removes     a set of    a  ttri                 b  ut    es for the   element .      
             *
                     *  @ pa r      am old th    e old att ribute se t       
                    * @param na        mes the attribute  names
                 * @re   t       urn the upda   ted att             rib     ute set
         * @s ee MutableA    t tr  ibuteSet#re      mo      v      e   Attr   ib  u          tes      
                    */
            publ    ic    AttributeSet removeAttributes (Attribut         eSet o    ld,          En     umer  atio n<?> names);

                           /    *          *
          *         Removes a set of attr      ibute          s for th   e eleme       nt.     
         *
             * @param old th    e   old  attri   bute s      e   t
              * @pa                     r       a    m att        rs     the     attributes
                      * @       return the upda   te    d   attribu           te set
                     * @see MutableAttri      b        uteSet#re   moveAttributes
                   */
                       public AttributeSe  t rem    oveA   ttrib   ut     es(Att  ributeSet old, At      tributeS  et  a     ttrs   );

        /**
                           * Fetch  es an e mpty Attrib    uteSet.  
          *
                            *  @retu    r  n   the attribute s et
                */
              publ   ic Attr    ibut  eSet g  etEmpt ySet();

        /    **
                   * Reclaim                        s an attribute set.
          * T       h  is is a way               for a  MutableAttri  but       e   Set to mark that it   no
                                * long     er ne     ed                a particular immutabl   e set.  T his is only necessary        
           * in 1.1 whe    re t         here are no w     ea k referen  ce          s.             A      1.   1       impl    ementatio n
                 * w ould call      t  his in i    ts          finali  z    e metho    d.
                  *
           * @param a the attribute set to reclaim
         */
                   publ  ic   void recl     aim(AttributeSet a);
    }

              / **
     *   Impl   emen      ts   the abst ract   p  a    rt of an element  .  B   y   d       efau   lt e   lements
              * support attrib utes by      having a fi          eld t hat represents th e i     mmutab    l  e
                    *    pa  rt of              the     c   urrent     a  ttr  ibu   t           e se        t  for   th                     e element         .  The ele    ment itself  
     * implements M         uta    bleAtt        rib    u   teSet w hich can    be use    d            to                mod                i   fy the set
      * by      fetchi      ng a n     ew immu   tabl   e set.              The           i        mmutabl    e set s a          re pro  v       ided
     *          by  the At    tributeC       ontext associated wi     th the doc    u ment.
        *  <p>
     *  <st       rong>Warn  ing:< /st rong>
                * Se   r      ia    lize       d ob j      ect      s of this    class        will       n      ot  be compati     ble wit    h
     * futur          e Swing        re    le   ases     . The c     urren   t    seri        alizat    ion suppor  t is
              * appropriate f        or short term stora   ge or RMI     be   tween appli       cations               running    
     * th     e same versi      on o   f Sw    in   g            .  A s of        1.4        , support     for long term sto   rage
     * of all JavaBeans &trade;
     *   has been   adde      d to the <code>java.b      eans</co    d        e> packag  e    .
     * Pleas  e    see {@   link       java.b ean    s.XMLEncod           er}.
        */
    public abstract clas  s Abstr      actElement imple  ments  Element, Muta    bl     eAt      t     rib        uteSet , S  eria    lizable  , TreeNod         e {  
    
             /**     
                *               C     reates   a new   Abstra   ctElement.  
              *
                                   * @  param    pa     ren t the parent   elemen   t          
             *               @param a th  e              att    ribut   es           f       or the element
                         * @si  nc     e 1.     4
         */
            pub    lic A     bstractE   lement(Element par   e    nt,           AttributeSet a) {   
                this.           parent   =    parent;
                             attributes = ge        tAttrib             ute         Con     text().    get   EmptySet(         );
                        i    f  (a          !      = n    ull)     {
                       add  Attrib    utes(a);
            }
                  }

              private final void i ndent(PrintW     ri   ter out,       int n) {
            for (int i = 0; i       < n; i++     )   {
                  ou t.print("  ");
                }
        }

                                /**
                 * Dumps a d      eb ugging r ep     r   esentati   on of the    ele ment    hierar    ch y.
                    *
            * @param ps  Out t   h      e outpu                t           stream
                    * @pa   ram i   ndentAmou  nt t    he in    dent  atio  n l    evel  &gt;=                0          
         */
         pu     bl     ic vo  id dump   (  PrintStream psOut, int i   nd      ent       Amount) {
               PrintWriter        out       ;
                           t   ry {
                ou   t = new Prin    tWriter(ne            w Outpu    tSt   r    eamWriter   (ps   Out,"JavaEs c"),
                                                                   true)   ;
                  } ca    tch (    U      ns       upp    ortedEncod   ingE      xception e){
                                          out            = new   PrintWriter          (psOut,tr  ue       );
                            }
                 ind     ent(ou    t  , indentAmo   unt)                        ;    
            i     f (getName(     )                    == null)     {
                                   out.print("<?   ?"  );
              }       e   lse {
                                                     out .            print("<" +     getName());
                             }
               if (g etAt tribute Co  u    n        t()      > 0) {  
                                                   out.printl  n("    ")     ;
                              //  dump      the           att ributes
                      Enumeratio   n names = attributes.getAttribut      eN am es();
                                whil    e (names.hasMoreE lemen   ts()) {
                                     Object      nam                        e         = name    s.ne  x   tEl  ement();
                                           indent(o    ut, in  dentAmoun        t +            1  );
                                ou   t .printl n(          name           + "=" +     getAt tribute (   n   ame));
                                    }
                                indent  (out, ind        ent Am  ount); 
                                           }
                out      .prin           tln(">");

                        if (isLeaf()) {
                        i             n  dent(out, inde       ntAmou      nt +1)            ;     
                                    out.prin t("                [" + getSta   rtOffset() + "," +         getEndO ffset() +            "]");
                               Con     t e    nt c = getConte  nt();  
                                  try                {
                        Stri n  g co    nte     ntStr = c.       getStr          ing(getStart  Offse      t(),
                                                                                                      get  E  ndOffset()    - get   St    artOffset())/ *.trim()*/;               
                             if  (conte    ntStr.l   en   gt   h() > 4     0) {
                                        contentStr    = co    n          te   ntStr   .substring  (0, 40   )  + "       ..  .";
                                 }
                                   out.p  ri ntln("["+  contentStr+"]");
                                  }     cat   ch (BadL  ocat  ion     Ex  ception e     ) {
                             }
      
            } e    l se {
                      i      nt n = getElementCount(      )  ;
                               for (int i = 0; i < n;               i++)        {
                               AbstractE         lement e = (Abstra      ctElement    ) getElement(        i);
                           e                       .dump(psOut, indentAmou    nt+1  ); 
                             }         
                  }
         }

        // ---       Attr  ibuteS      et ---                --   ----    ---     ------------    --        --
          //      delegated     to the immut    able field " attrib     utes"

             /**
                    * Ge        ts the number of attri  b  utes that     ar  e defined.
          *
             *        @return the n        um    ber of  a   ttributes    &gt;= 0       
                   *      @see  Attr      ibut   eSet#g    etAttributeCount     
              */     
           publi          c    int getAttribut eCo   unt() {
                           retur      n attribute    s.getAttribut      eCount()       ;
                           } 

                /**  
              * Che   ck s whether        a      given attribut    e is defined.     
             *
                                *    @par     am att         rName th e n on-n ul     l   at tribute name        
                 * @return t                rue if th  e att         ribute is  defined
                   * @s     e      e Att        ribute Set#is       De   fi   ned
           *          /
           public     b        oole    an isDefi         n      ed     (Object at               trN  ame) {
                 return attribut     es.isDefined(attrName);
                     }

           /**
                 * Che    cks w   hether        tw  o attrib ute     se  ts       are equal.
         *
             * @param attr     t   he a  t   tribute s e     t to chec   k ag           ainst
            * @retu      r     n tr    ue if the same
         * @see       A      ttribute       Set#isEqual   
             */
            public     bool     ean    isEqu   al(  Att      ribute           Set at  tr        ) {
                          return attributes.isEqual(attr)     ;
                  }

              /**
         * Co     pies      a set          of     a   ttributes       .
           *
                            * @return t  he cop y
                                * @see Attri      buteSe   t#copyAttributes
                    */
                  p   ublic A  ttributeS   et copy         A     ttribute   s() { 
            return  a    ttributes  .copyAttribute               s();    
            }

             /**
            *     Gets t  he value of an     a          t     tribute     .
                          *
                                    * @p aram   attrName the no     n-null a    t     t ribute name
                     * @r   etu   rn the attribu        te v a                lu   e
           *         @s ee AttributeSet          #   getAt    tribut e
                         */
        pu      bli     c      Objec   t getAttrib     ute(Obje      ct attrName)      {
                     Ob   ject               val          u  e = attribu     t     e s.get Attr    ibute(attrName);
               if (value     == null) {
                              //      The delegate n   or it's resolv e   rs had               a     match,
                               // so we'll try to resolve th     rough the pare    nt
                      // element.   
                            AttributeS  et   a =    (parent != n ull) ? parent.g  et     A  ttri      butes()       : nu ll;
                 i  f (a !   =  null) {
                    value    = a.                       getAt     tribute(at     tr     N    ame);
                }
                   }
               return                          va   lue;
             }

                 /**
                      * Get  s         the     names of all   at  trib     utes.
              *
                  * @r   eturn th    e a     t tribute names a  s           an enumerat  ion
         *    @see AttributeSet#getAtt  ributeN     a mes
                  */
              publ   ic Enumeration<?> getAttribut    eNam      es() {
                  retu   rn attributes.getA  ttributeNam  es(    );
                }
  
                  /*    *      
         *      Checks whether a g     iven attr              ibute name/value is d  e   fined.
           *
             * @param     name the non-null attri  b ute name     
                    * @param val  ue the     att  ribu  t            e value
         * @return    t    rue if the n    am    e/v   a        lue  i   s       def ined    
            * @    see At t    ributeS            et#contains  Attribute
                 */
         p    ublic   bo   ole an cont     ainsAttribute(Object name, O  bjec      t  valu       e         )                     {
                  r    eturn attri          b       ut  es. c         onta   ins    Att  rib   u te(name,    value);                    
        }


         /**
           * Checks whet                 he  r the element contain       s all      t    he              attributes.   
                 *
         *             @           param attr     s            t    he attribut    es to    check
                   * @ ret  urn true if the ele      ment cont  ai    ns         all the att  ributes
              *     @see Attr  ibut   eSet#con  tainsAttributes
                   *   /
            public    boolean co        n  tainsAttributes(A   tt     r      ibuteSet at      trs) {
             retur  n attributes.  con   tai nsAttribu   tes (at    tr        s);
                  }            

        /**
           * Ge ts the r      esolvin g parent.
               *  If not                    overridden, t  he       resol   ving parent d efaults to
          * t      h   e p   arent eleme      nt.
                 *
         *   @retur   n   the    attr      ibutes from the par ent,          <code>n   ull</code> if none
          * @see A ttribu   teSet#getR       esolveParent
          */
            p                 ub          lic Attri   buteS                         e       t get         ResolveP        a   rent()           {
               Attr   ibuteSet a = a   ttrib  utes.  getResol   ve            Parent()    ;
                 if ((  a == null)    &&    (p      arent !    =   null)) {
                                   a = par            ent.ge   tAttributes()    ;  
                      }
                                retur n a   ;
             }

               //     ---       M     u  tableAttributeS  et -------------    -----   --   --------------
            // s    hou       ld fetch a new immutable r        ecord           for    the field
                      /      / "attributes     ".

            /** 
         * Adds      an att      rib    ute          to the element.
              *
                            * @param na   m  e the non     -nul    l attribute           name
              * @pa    ra m      v      alue the      att    rib ute v     alue
                     *         @see Mu   t         a   bl eAttri    buteSet#addAttribute
                   */
        p      ub   lic     vo   id addAttribute(Object       n          ame  , Obj ect v alue)              {
                        checkForIllegalCa  st();       
            At  tribut  eConte  xt context = get     Attribu      te  C    ontext(); 
               attribu  te   s = context.addAttribute(attrib  utes, name, v  al   ue);
             }

        /**   
         * Ad    ds      a set of  at    tributes         t      o         the   e lement.
               *
               * @par am attr t               he attributes to ad              d         
             * @see Muta   bleAtt ri     b   uteSet#add    Attribute
                     */
                     public v   oid ad   dAttrib  utes(A      tt    ributeSet        attr) {
                    checkForI       llegal Cast();
                                   AttributeCo    ntext co   n text      = getAttri buteContext(  );
                                attrib  utes  =       conte    xt.  addAttrib   ute      s(attributes,         attr);
           } 

               /**
             * Remov es an attribu    te f       r            om the set .
         *
           * @para  m    name t he non    -null   attri     bu  te na    me
                       * @se   e Mutab   l eAttrib    u    teS et#remov eAttribute
             */
             publi    c      void re       moveAttribute(Object             n  ame) {     
                              checkForIllega    lCast();
                             Attri    bu te Cont  ext context        = get          Attri   bute   Co       n   te xt();
              at      tributes =   cont    ext.r   emoveAt   tribute(at  tr  ibut    es,  na    me   );
         }

        /**
         * Re                     moves          a     set of   attributes              for th        e elemen  t.
                   *
           * @par am names     the attribute      nam   e          s
             *        @see MutableA     t    tr ibuteSet     #removeAttr  ib  u       tes
                    */
             publi    c     void     r     emoveA   ttributes   (Enum      e   ra       tion<  ?> nam     es) {
                 checkForIllegalCast();
                       AttributeConte xt context =       get Attrib uteContext()  ;
             attr     ibu   tes = context.rem      oveAttributes(att     ri    butes, names);
            }

                 /**
         * R   emoves             a se          t of attri     bu        tes for t            he element.
                     *
         * @pa        ram a ttr          s the   attributes
          *     @ see    MutableA  t             tri     but e  Set#removeAttributes 
          */   
         public vo   id re    mo     veAttri  butes(Attrib             uteSet at   t  rs) {       
                               che   ck   Fo  r   IllegalCa  st   ();
              Attribu  teContext context = ge    tAttr  ibuteContext();
                          if (attrs = =    this) {
                           attrib utes = con  text.get   E mptySet   ();  
                    }   else {   
                              a ttributes =   conte xt.remo      v  eAttributes(attri   butes, attrs);
            }
           } 
     
            /**
           * Sets the resolving p      aren       t.
                 *
         *  @par       am pa    rent the parent, nu  ll       i       f     non   e       
                         * @see Mut   able  At    trib  uteSet#set  Resolv   eParent
                                      */
         publ    ic  void       setReso  lv       ePare  n     t(Att      rib       uteSet p  arent) {
              chec  kForIllegalCast( ) ;
                      Att  ri    buteCo  n  t     ext    con text =       getAttributeCon    text()   ;
            if (parent !=         null) {
                      attributes       =
                                 conte             xt.addAttribute(att   ributes, StyleConst   ants.ResolveAttribute,
                                               par  ent);
                        }  else {    
                                    attributes =
                                         context.        removeAttribute (attributes,     Style    Constants.Resolve  Attri    bu te);
                 }
          }

                         private            final      void checkForIllegalCast()          {
                        Thread t       =    ge   t       CurrentW      r     iter(      );
            if ((t == n    ull      ) || (t != Thread.currentTh  read()))    {
                throw new S           tate  Invar    iantE    rror  (    "    Illega       l c    a   st to MutableA  ttributeSe     t");
                    }
                }

                // --- Ele  ment methods -----           ----  --  ----------------  ------     ----   

           /**
               *  Retriev   es the        und      erlying model.
         *
                             * @       return the            model
                               */
         public D       ocument getDoc      ument() {         
                              retu             rn   A    bstr      actDocument.this;
        }

          /**
                           * G et                         s the parent of        the            eleme     nt.
                *
                 * @return the  parent
         */
        public Element getParen       tEle          me                          nt() {
                      r    eturn                parent;
        }          
   
                      /**
            * Gets the att    ri butes    for                   th  e e  l       e           ment.
         *
                        *   @return the   attribut     e set
           *   /
                            public A  ttributeSet ge      tAtt  ributes()    {
                               return this;
        }

            /**
                       *  Gets the     na     me of the     el   ement.
          *
                * @return th     e n    ame    ,   null if none  
             * /
           p    ublic String getName() {
                  if (attr      ibutes.isDefined(ElementN    a   meAtt     ribute))  {
                               retur    n (St   ring) a ttributes.get     Attribut     e(Eleme n  tN                   ameAttribut  e);
               }
            retu  rn nul    l;
                      }

        /**
                * Gets the     star ti   ng offs       et in  th  e model for the element.
         *
             * @retu rn            the offset &gt;= 0
                */
              public         abs    tract int getStartOffs et();

                   /**     
         *        Gets the ending offse     t          in the model     for the e  lement.
                                *
         * @    return the off   set &g     t;     = 0
            */
        public abstr   act     int      getEn     dOffse  t();

           /**
                     *  Gets   a chil                d eleme       nt.
           *
         *   @param  i   ndex      th    e ch      ild index, &gt;= 0 &amp;&amp; &lt; getEleme ntCount()
         * @return the chi    l     d      element
         */
        p    u   blic a     bstract Element getElement   (i          nt index);
               
           /**
               * Gets the n    um  ber of children for the element.
         * 
         * @return the               n    umber of children      &gt;= 0
             */
        public a  bstract int getElementCoun  t(   );

        /   **
         * Ge ts the ch              ild element              i  ndex     closes    t t  o the given model offset.   
         *
                    * @param o   ffs     et the offset &gt;= 0
                   *    @return t   he element index &gt ;= 0
               *   /
            public abstract int getElem     entIndex(int   o ffset);           

        /*        *
                   * Ch ecks  whether t he          element is a leaf.   
            *   
             *     @return true i    f   a l  eaf
             */
            pu  blic a bst       ract boo   le  an isLeaf();  

        // --- TreeNode methods        -    ---------------------  ---------------

           /**
            *      Returns the     chi  ld <c ode>TreeNode</code>     at index
         *  <code>childIndex</c  o   de>.
         */
        p         u   blic Tr  eeNod  e    ge     tCh       i ldAt(int chil       dInd         ex) {
              ret    urn        (TreeNod    e)get   Elem       ent(         ch  ildIndex);     
                    }

                    /**
                         *  Returns the n   umber       of children       <c  ode>TreeNode</    code>'s
                      * re    ceiver  contains.
                  *          @retu    rn th e n    umber of childr  en <code     >    TreeNodews</co   de>'  s  
                    * recei  ver cont  ains
              */
          public int getChildCount() {
               return getElementCount  ();
               }

         /   **
         *     Re   t    urns the parent <c    ode>Tre    e  Node   </code> of    t       he   receiver.
                * @return the par   ent <code>TreeNode</code> of the receive r
          *    /
          public   Tr          ee      Node ge tParent   ()    {
             return ( TreeNod   e)getP   are    ntElement();
        }      

           /**  
              * Returns the ind  ex of <code>node</code> i      n the           receivers children.
                           * If the receive   r does not contain      <code>node</code>, -1 will be
              * returned  .
          * @pa  r   a  m no   de the       location of in  t   erest   
            * @return the index of <code>node</code> in the receiver's
         * children,  or -1    if ab     sent
         */
              p     ublic int getIndex(T      ree   Node node) {
              for(i    nt counter = getChildCo         unt() - 1;      coun   ter >= 0      ; counter-   -)
                         if(getChildAt    (counter) == node)
                                     return cou     nter;
                 return -1;
          }   

          /**
         * Return     s true if the receive       r allows children.
          * @return     tr       u  e   if th    e receiver a      llows children, othe  rwise false
              */
                               publ ic abstract boolean getAllowsChildren();


            /**
         * R        eturns the children o   f t   he receiver as  an
                  * <code>Enumerati     on</code>.
                        * @return the children of  th  e receive   r as a  n <code>En     umeration</c  ode>
         * /
        public a  bstract Enumeration children();


           //      --- seriali zati    on     -----   ----------------------------------   ------

               private voi    d wr     i      teObject(ObjectOutputStr   eam s) throws IOExce pti              on {   
                       s.defau ltWri     te     Object();
                  StyleCo  ntext.wr   i     teAttr     ibuteSet(s, attribut     e    s);
                   }

        p rivat e void readObject(ObjectInputStream s)
               throws ClassNo     t   FoundException, IOExce         pt       io    n
         {   
            s.defaultR   eadObject();  
               Muta    bleAttri buteSet att       r = new Simp  l    eAttr  ib     uteSet();
                   S tyleContext.readAt  t    ribu   teS    et(s, at        tr);   
              Att ributeCo    ntext c ontext  = getAttributeContext(     );
                      attrib    u   tes = context.addAttributes(Simp    l    eAttributeSet   .EMPTY, attr);
        }    

        // ---- variab    les ----------       --  ---- - ----      --  ------     ---------    ----  ------ ---        --

           private El   e      ment pa   re  nt; 
             private     tra nsient Att    r          ibuteSet  attributes;

           }

     /**
     *                Implements a composite el  em   ent          that con   tai   ns other eleme      nts.
     * <      p>
     * <strong>Wa  rn   ing:</strong>
     * Serialized objects of thi   s    class will    not     be compatible with
     * future S       wing           releases. The c  urrent ser ialization      sup      p  ort is
          *       appro    priate for sho    rt term stora ge or RMI   between    applic  ations    running   
     * th                  e same versi    on of S      wi       ng.          As of 1.4   ,    support for long term storage
     * of all JavaBeans&trad       e ;     
      * has been added to t       he <   code     >java          .beans</code> pack  a ge.
                   * Please s       ee {  @link ja     va.     beans.XMLEncoder}      .
        */
    pu  blic  cl  ass Bran    ch  Element       exte    n d              s Abst ract    Element {

                                    /**
         * Construc   ts a composit             e ele     ment that initially     contains
         * no chi            ldren.
         *
         *      @para      m parent        The par   ent e    lement
                  * @p    a     ra m a      the att rib            ute    s fo  r the e lement
                * @since 1.4
                 */
        public B   ranchEle   m   ent(E    lem    ent parent, At  tributeSet a) {     
                 s uper(pa          rent,    a);
                          children = ne     w   Abstra          ctEle   ment[   1];
                     n       chi       ld  ren =    0   ;
              lastIndex             = -1;
           }

                  /**
                * Gets th  e    c  hild element that contains
                *      the gi   ve  n m    o       del  p   osition.
         *
         * @param pos th     e position &gt;= 0     
               * @ret  urn t     he element  , nu   ll if none
          *   /
        public Eleme    nt p   ositionToElement    (int pos) {
                int index = getElement   Index(pos);
            Ele   ment chi  l    d = chi   ldren[index];
                  int p0 = c    hild.   g  e   tSt   artOff          set(      );
              int p1 = chi    ld.g  etEndOffse   t()      ; 
            if (       (pos >= p0) && (pos <   p1)) {      
                       re  turn child;
                    }
                 return null;
          }

        /**
                * Replaces content with   a new set      of ele m  ents      .  
            *
               * @param offset th  e starting offse  t &gt;=  0
                          * @param  length      the length to   rep  lac  e &gt;= 0
               *     @par am elems the new elements
               */
        public void repla   ce(int offset, in t len      gth,   Element[   ] ele   m   s) {
               int delta = elems.leng  th - length    ;
                    i           nt src = offset + l      ength     ;
                int nmove = n children - src;
                       i  nt dest = src + delta;
            if ((nchildren + delt     a) >= c          hildren.length) {
                // need to grow           th   e array
                     in     t newLength = Math.max(2*       children.leng                              t  h, nchildren + de   lta);
                                      A  b   stractEl ement[] newCh ildren = new A   bstrac      t        Element[    ne  wLe   ngth];
                         System. arraycopy(children, 0,   newChildren, 0, offset);
                                    Syste   m.array     copy(elems, 0, newChi     ld    ren   , off  set, elems.length);
                     System.arraycop            y(childr     en, src,    n   ewChildre  n, dest, nmove   );
                       childr en = newC         hildren;
             } else    {         
                  // p  atch               t        he existing arra y
                  Syst     em.      arraycopy (chi  ldr   en, src, children, dest, nmove);
                System.arrayco          py(elems, 0, children, offset, ele  ms.   length       );
                      }
            nchildren = nchil  dre    n     +          de lta;
        }

             /**
           *  Converts the elemen     t to a string       .
            *
          * @return the string
            */
                  public String toString() {
            r      eturn      "BranchE  lement( " + getName() + ") " +   ge    tStar   tOffs   et() + ","                   +
                      getEndOffs      et() +  "\n";
        }

        // --- El  e ment met    hods -----------------------------------

        /**
            * Ge    ts the       e  leme   nt     name.
         *
               * @return the elem ent      name
           */
         public       String get Name() {
              S  tring nm = sup    er.get  Name ();
                    if (nm == nul   l) {
                                       n            m = P       aragraphElemen   tName;
            }
            return n     m;
        }     

        /**
             * Gets t he       st art  ing offset in      the model f  or the element.
           *
                * @re  t      u      rn the offset   &gt;= 0
                *      /
        publ   ic int getS  tartOffset() {
                    return children[0].    getStartOffset();
        }

                  /**    
         * Gets th    e e    nding o    ffset in the mo  de  l         f or th     e element.
             * @thr     ows NullPointerExc ept io      n    if      this element has n     o chi       l dren
             *
               * @return th        e offset &gt;= 0
                   */
             public int getEndOff       set(   ) {
                 Elemen        t child =
                          (nc  hild                 ren > 0)        ?               childr  en[nchildre   n - 1]       : child ren[0];    
                   return   child.    ge     tEndO ffset();
        }

        /*  *
                * Gets a c   hild element. 
              *
         *  @param index th  e child index, &gt;=  0  &amp;&amp; &lt; getElementCoun  t()
         * @retur    n     the chil      d e lement, null if none
         */ 
             p   ublic Element getElem     ent(   int index)       {
                   if (index <   nchildren      )    {
                return children[index];
                     }
                    return null;
        }
   
          /**
          * Gets the nu       mbe    r of children for t       he element.
              *
                   * @retu   r   n        the number of child   ren &gt;=    0
          */
           public int ge  tEl           ementCount()  {  
            return n    child   r  en;
         }
 
                    /**
              * Gets the child  element index closest          to the given model       offset.
            *
         * @param off set the offset &  gt;   =    0
         * @re   turn the element index &g    t;= 0
           */
        public int     getElemen tIndex(int offset) {        
                in  t in  dex;
                 int lower = 0                  ;
                int up  per  = n   children   - 1;
                int mid =    0;
              int        p0 = g etStartOf      fset()     ;
             i       nt p1;

                        if (      nchildre  n == 0) {
                                return 0;      
                 }
                        if (offset >= get   EndOff set()   )     {
                return   nchild ren - 1;
               }
   
               // see if   the last index can be used.
                  if ((lastIndex >= lower) && (lastIndex <= up  per)) {
                       Element    lastHit = chi  ldren    [la    stIndex];
                             p0 = lastHit.getStartOffs   et();
                    p1 = lastHit.   getE     ndOffse    t();
                        if ((offset >= p     0) &&        (offset < p1)) {
                             return lastIndex;
                }

                   // la     st index w  asn't a hit, but it      do     es    give useful        info about
                     // where a h       it (if any)  woul          d be.
                      if (offs et < p      0) {
                          upper = lastInd   ex;
                 } e     lse  {
                       lower     = las           tIndex;
                   }
             }

            wh  ile (lo  wer     <= up     per) {              
                                 m              id    = lower + ((    upp er - low   er)  / 2);
                Element  ele   m = c      hildren[mid];
                                  p0 = elem.getStartOffset();
                                         p1 = elem.      getE   ndOffset    ();
                               if    ((off set    >= p0) &&       (    o   ff  set < p1)) {    
                      // found the l  ocation
                            index = mid;
                                 lastIndex = i    ndex;
                          retu    rn index;
                } else if (of fset <    p0) {
                           upp   er = mid      -  1;
                    } els    e     {
                    low   er    = mid + 1;
                }
            }

            // didn't find it, but    we     indicate the in     dex o    f where it wo uld bel   ong
                if (offset < p0) {
                      index = mid;
                      } else   {
                inde            x = mid + 1;
              }
            lastIndex = index;  
              re  turn      index;
             }
  
        /**
             *  C        hecks wheth      er the el      ement is a leaf.
                  *
           * @return true if a     l    eaf
         */
        publi    c boolean isLeaf() {
            return fa   lse;  
               }
 

            //    ------ TreeN ode ----   ----------------------   ------------------   --           

        /* *   
         * Returns tr     ue if       the receiver allows c hildren.
         * @ret   urn      true if the re   ceiver allows childre   n, other wise fa lse
             */
        public boolean getAllowsCh     il    dren() {
                 r     eturn true;
            }


               /    **
         * Returns th  e   ch       ildren of the   receiver   as an
                    * <code>Enumeration</code>.
         *    @return the children of the receiver
         */
        public Enume ration children() {
             if(nc       hildren == 0)
                return n   ull;

                     Ve c  tor<Abst      ractElement> tempVector = new Vector<Abstra  ctElemen   t  >(nchildren);

            for(int counter = 0; counte r < nchildren; counter++)
                    tempVector.addElement(children[counter]);
                 return tempV  ector.e                 lements();
        }
 
            // ------ mem     bers ------ --       --------------------------------------

        private AbstractEl          em     ent[] children;
        private int    nchildren; 
        private int lastIndex;
    }

    /**
         * Im   plements an el    ement that di    rec  t    ly rep  resents content     of
     * some    kind.
     * <    p>
        * <strong>Warning:</strong>
     * Serialized o   bj ects o     f this class will not be compatible wi   th
     * future S  wing releases     . The current seria      l        ization support is
     * appropriate  for short term       storage or RMI betwee n applications running
     * the same version of Swing.  As of 1.4, support for long term stora      ge
     * of    all JavaBeans&trade;
        * h   as been added to the <co    de>j     av  a.beans</code> package.
     * Please   see {@l   ink java.beans.X          MLEn      coder}.
     *
     *               @se     e     Element
         */
        public class LeafElement   extends AbstractElement    {

        /**
            *  Con str   ucts an eleme    nt that represe  nts conten          t w   ithin th     e
         *    docume nt (has no    childr  en).
          *
         *  @param parent  The pare      nt element
               * @para       m a           The element attributes
         * @         param o  f  f  s  0   Th    e start offse   t       &gt;= 0
         * @param   offs1        The end offset &gt;= off  s0
         *         @since 1.4
             *  /
        public LeafE  le      m  ent(Element paren t, AttributeSet a, int offs0, int offs1) {
               super(paren   t       ,       a);      
            t   ry {
                p0    = createPosition(offs0);       
                  p1 = crea       tePosition(off      s1);
                   } catch (BadLocationExcep      tion e) {
                  p0 = nul  l;
                p1 = nu  ll    ;
                   throw       new StateInvar   ian tErro      r ("Can't crea      te Position refer    ences");
            }
        }

        /**
         * Con    verts the ele        ment   to a string.      
           *
               *  @return the string
            */
        public String   toStri    ng() {
                return "LeafElement(    "   + ge    tName() + ")    "     + p0 +  ",    " +  p1 + "\n";
        }

          // --- Ele     ment met  hods -  --------------------    ------------------------

           /    **     
         * G  ets the s   tar   ting    offset in the model for the   element.
           *
         * @return th  e offset &gt;= 0
         */
        pub l         ic   int getStart      Offset() {
             return p0.getOffse   t();
        }

               / **
          * Ge     ts                the en  ding offs        et in the model fo   r the element.
             *
         * @return the offset &gt ;= 0
         */
        public int getEndOffset() {
              return p1.getOffset();
        }

           /**
                     * Gets the element name.
         *
         * @return the name
              */
        publi  c Str  ing getName() {
                String        nm = super.getN     a me();      
                         if (nm     == null) {
                nm = ContentElementName;
            }
            return nm;
        }

        /**
            *      Gets t he c hild  ele    ment in     dex    closest to the giv   en m   od    el offset.
         *
           * @pa   ram pos the off     se t &gt;= 0
         * @return the element in  de   x    &    gt;= 0
             */
          public int getElementIndex(int pos) {
                 ret   urn -1;
        }

        /** 
         *  G  ets     a c     h      ild elem   ent.
         *
         * @param index       the child index,    &gt;= 0 &amp;&amp; & lt  ; getElem    entCount()
                   * @return the child element
                 */
        public Element getElem   ent(int index) {
            retu     rn nul l;
           }

        /**
              *  Returns the number of child eleme     nt  s.
         *
             * @return th e number      of     children &gt;= 0
            */
              pu   blic int getElementCount(     )  {
            return 0;
            }

        /**
         * C   hecks whether t  he element is a leaf.
         *
         * @return t   rue if a leaf   
            */    
        pub lic bo olean isLeaf()  {
            re  turn true;
           }

        // ------ TreeNode ---------  -----    -----   -    ------ --- ------------   -----

        /**
         * Returns true      i   f    the     receiver allows children.
         *      @return tru e i  f t   he receiver allows children, otherwise false
         */
        public boolean getAllowsChildren() {
            return false;
        }


        /**
         * Returns the children of the     receiver as an
         * <code>Enumeration</code>.
            * @return the children of t    he r eceiver
         */
        public Enu     meration children() {
            return null;
        }

        // --   - serialization --------------    -------------------------------

          private void write  Object(ObjectOutputStream s) throws IOExce     ption   {
                s.def   aultWriteObjec    t();
               s.writeInt(p0.getOffset());
            s.writ eInt(p1.getOffset());
           }

        private void re      adObj    ect(Obje   ctInputStream s)
            throws ClassNotFou     ndException, IOE  xception 
        {
            s.defaul  tReadObject();

            // set the range with   positions that track   change
                int off0 = s    .readInt();
              int off1 = s.readInt();
            try {
                p0 = createPosition(of      f0); 
                p1 = createPosit   ion(o    ff1);
            }        catch (B  adLocationExcep    tion e) {
                        p0 = nul       l;
                p1 = null;
                     throw new IOException("Can't res tore Position references");
            }
                 }

        // -   --- members --------------------------         -------------------    --------

        private transie   nt Po     sition p0;
        pr    ivate transient Position p1;
    }

       /**
     *     Represents the root element of the bidirectional eleme   nt structure.
     * The root element is the only eleme n     t in the bidi el    ement structure
     * which contains children.
     */
    class BidiRoot  Element extends BranchElement {

        BidiRootElement() {
              super( nu   ll, null );
        }

         /**
                * Gets the name of the element.
             * @ret      urn the name
         */
        public String getName() {
            return " bidi root";     
        }
    }

    /**
     * Represents an element of the b     idirectional element s         t   r  ucture.
     */
       clas   s BidiElement extends LeafElement {

        /**
               * Creates a new BidiElement.
         */
          Bi   diElement(Element parent , int start, int end, int level   ) {
             super(parent, ne   w S   impleAttributeSet(), start, end);
            addAttr ibute(Style     Constants.BidiLevel, Integer.valueOf(leve      l));
               //System.out.println ("BidiElement: start = " + start
            //                      + " end = " + end + " level = " + level );  
         }
    
          /**   
            * Gets the name of the element.
         * @r  eturn the name
                 */
        public Str  ing getName() {
            return BidiElementName;
        }

        int g      etLevel() {
               Int   eger o = (Integer) getAttribute(StyleConstants.BidiLevel);
            if (o != null) {
                return o.intValue();
            }
             return 0;  // L  evel 0 is base level (non-embedded) left-to-right
        }

          boolean isLeftToRi   ght() {
            r  eturn ((getLevel() % 2) == 0   );
        }
    }

    /**
     * St  ores document changes as t  he documen      t is being
         * modified.  Can subsequently be used for change notificatio  n
     * when done with t he document modification transaction.
     * Th    is is used by the Abstrac tDocument class and its extensions
       * for b roadcasting change    information to the document listen er  s.
     */
         public class DefaultDocumentEvent    e   xtend   s CompoundEdit implement    s DocumentEvent {

        /**
         * Constructs a change rec  ord.
          *
         * @param offs the offse  t into the document o  f the ch  ange    &       gt;= 0
           * @param len  the length of the change &gt;=  0
         * @param type the type of event (DocumentEvent.Even        tType)
         * @since 1.4
                 */
        public DefaultDocumentEvent(int offs, int l      en, Docu  mentEvent.EventType type) {  
            super();
            offset = offs;
            lengt     h = len;
               this.type = type;
        }

          /**
         * Returns a string description of the change event.
         *
           * @return a st  ring
          */
        public String toString() {
            return edit  s.toStr   ing();
             }

             // --- CompoundEdit methods -------------   -------- -----

        /**
         * Adds   a doc     ument edit.  If   t he     number o    f edits crosses
           * a thre  shold,    this swi    tch es on a hashtable lookup for
         * ElementCh      ange implem en  tations since    acc  ess    of these
           * needs to be relatively quick.
         *
           * @param anEdit a document edit record
           * @return true if the edit was added
             *      /
        public b   oolean addEdit(UndoableEdit anEdit) {
             // if the    n  umber of changes        gets too great, star    t using
            // a hasht  able for to locate th   e change for a given element.
            if ((changeLookup == null) && (edi     ts. size() > 10)) {
                         c    hangeLoo  ku       p    = new Ha   shtable     <Element, Ele    mentChange>();
                int n = edits.size();
                  for (int i = 0; i < n; i++) {
                        Object o = ed  its.elementAt(i);
                    if ( o instanceof DocumentEve    nt.ElementChange) {
                             DocumentE     vent.Ele         mentChange ec = (DocumentE   vent.ElementChange)  o;
                        cha    ngeLookup.put(ec.getElement(), ec);
                       }
                }
                 }

            // if we ha ve a hashtable... add the entry    if it's
              // an ElementChange.
            if ((changeLookup ! = null) && (anEdit inst  anceof DocumentEvent.E  lementChange)) {
                Documen   tEvent.ElementChange ec = (DocumentEvent.ElementChange) anEdit;
                     changeLookup.put(ec.getElement(), ec);
                }
                 retur    n super.addEdit(anEdit);
        }

        /*  *
            *     R edoes a change.
                 *
         * @exception CannotRedoException if the change cannot be redone
               */
               public       void redo()      throws CannotRedoExc   eption {
               writeLock     ();
            try {
                    // change the state
                super.redo();
                // fire a  DocumentEvent to notify the     view(s)
                         UndoRedo   DocumentEvent ev = new Undo   RedoDocumentEvent(this, false);
                      if (type == Docu      mentEvent.EventType.INSERT) {
                    fireInsertUpdate(ev);
                          } else if (type == DocumentEvent.EventType.REMOVE) {
                     fireRem     oveUpdate(ev);
                }        e     l      se {
                         fireChange dUpdate(ev);
                }
              } finally {
                writeU   n  lock();
              }
        }

         /**
         * Undoes a change.
          *
         * @exception CannotUndoException if the change cannot be undone
         */
        p  ubli    c v     oid u     ndo() throws Cannot       UndoException {      
            writeLock();
            try {
                // change the state
                  super.undo();
                // fire a DocumentEvent to notify the view(s)
                  U   nd  oRedoDocumentEven   t ev = n ew UndoRedoDocumentEv     ent(t         his, true);    
                if (typ e     =  = Docume    ntEvent.EventType.REMOVE) {
                     fireInsertUpdate(ev);
                } els   e if    (type == DocumentEvent.Even    tT  ype.INSERT) {
                      fireRemoveUpdate(ev   );
                } else {
                    fi     reChangedUpdate(e   v);      
                }
                } finally {
                writeUnlock();
              }
        }

           /**
         * DefaultDocument events a     re significant.  If you wish to aggregate
         * DefaultDocumentEvents to present them a  s a singl e ed    it to the user
         *    pla  ce them into a Compo      undEdit.
         *
         * @retur  n whether the event is sig        nificant for edit undo purposes
         */
        pu blic boolean isSignificant() {
            return true     ;
        }


        /**
         * Provi d      es a localized, human readable    des  cription of this edit
         * suitable f    or use in, say, a change log.
         *
         * @    return the description
         */
            publi   c String getP     resentationName() {
            DocumentEvent.Even      tTy   pe type = getType();
            if(type == DocumentEvent.EventTyp  e.INSERT)
                return UIManager.g etString("AbstractDocument.additionText");
            if( type == DocumentEvent.Ev    entT ype.REMOVE)
                  return UIManager    .getStri ng("AbstractDocument.deletionTe  xt");
             return UIManager.getString("    AbstractD ocument      .styleC   ha     ngeText");
        }

        /**
         * Provides a localized, human reada  ble description of the     undoable
         * form of this edit, e.g.   for use a      s an Und  o menu item. Typically    
         * derived from getDescripti     on();
         *
         * @return the description
           */
        public String getUndoPresentation  Name() {
            return UIManag     er.getString("Abstrac   tDocument.    undoText") + " " +
                     getPresentat   ionName();
        }

        /**
         * Provides a localized, h     uman readable description of the redoable
         * form of this edit, e.g. for use as a Redo menu item. Typically
         * derived from getPresentationName();
         *
         * @return the description
         */
        public String getRedoPresentationName() {
            retur   n UIManager.getString("AbstractDocument.redoText")    + " "    +
                getPresentationName();
              }

        // --- DocumentEvent methods --------------------------

        /**
                * R    eturns th   e type of event.
             *
         * @return   the event type as a    Docu   mentEvent.EventType
         * @see DocumentEvent#getType
            */
        public Doc  umentE  ve    n  t.EventType getType() {
            retur  n type     ;
        }

         /**
              * Return s the offset within the document of the s     tart of the c   hange.
         *
               * @return the offset &gt;= 0
         * @ see DocumentEvent#getOffset
         */
             public int getOffset() {
            return offset;
        }

        /**
         * Returns the length of the change.
         *
         * @return the length &gt;= 0
          * @see DocumentEvent#getLength
         */
        public int getLength() {
              return length;
        }

        /**
         * Gets    the document that sourced the change event.
          *
          * @return the document
         * @see       DocumentEvent#getDocument
           */
        publ     ic Document getDo      cument() {
            return AbstractDocument.this;
        }

        /**
         * Gets the changes for an element.
         *
              * @param    elem the element
         * @return the changes
         */
        public DocumentEven    t.ElementChange getChange(Element ele  m) {
            if (changeLookup != null) {
                  return chan     geLookup.get(elem) ;
            }
            int n = edits.size();
            for (int i = 0; i < n; i++) {
                Object o = edits.elementAt(i);
                  if (o instanceof   DocumentEvent.ElementChange) {
                    DocumentEvent.   ElementChange c = (   DocumentEvent.ElementChange) o;
                    if    (elem.equals(c.getElement())) {
                        return c;
                    }
                }
              }
                 return null;
        }

        // --- member variables -----------------------   -------------  

        private int offset;
        priv  ate i nt length;
        private Hashtable<Element, ElementChange> changeLookup;
        private DocumentEvent.EventType type;

    }

    /**
     * This event used when firing document changes while Undo/Redo
           * operations. It just wraps DefaultDocumentEvent and delegates
     * all calls to it except getType() which depends on o     peration
     * (Undo or Redo).
     */
    class UndoRedoDocumentEvent implements DocumentEvent {
        private DefaultDocumentEvent src = null;
        private EventT  ype type = null;

               public UndoRedoDocumentEvent(DefaultDocumentEvent src, boolean isUndo) {
            this.src = src;
            if(isUndo) {
                if(src.getType().equals(Eve   ntType.INSERT)) {
                          type = EventType.REMOVE;
                 } else if(src.get       Type().equals(EventType.REMOVE)) {
                         type = EventType.INSERT;
                   } else {
                    type =   src.getType();
                   }
            } else {
                type = src.getType();
            }
        }

              public DefaultDocumen  tEvent getSource() {
                   return   src;
        }

        // DocumentE  ven  t me   thods delegated to DefaultDocumentEvent source
        // except getType() which depends on operation (U    ndo or Redo).
        public int getOffset() {
            return src.getOffset();
        }

        public int getLength() {
            return src.getLength();
        }

        public Document getDocument() {
            retur   n src.getDocument();
        }

        public DocumentEvent.EventType getType() {
            return type;
          }

        public DocumentE    vent.ElementChan    ge  getChange(Element elem) {
             return src.getChange(elem);
          }
    }

    /**
     * An implementation of ElementChange that can be added to the document
     * event       .
     */
    public static class ElementEdit extends AbstractUndoableEdit implements DocumentEvent.ElementChange {

        /**
         * Constructs an e   dit record.  This does not modify th   e element
         * so it can safely be used to <em>catch up</em> a view to the
         * current model state for views that just attached to a model.
         *
         * @param e the element
         * @param index the index into the mode    l &gt;= 0
         * @p  aram removed a set of elements that were removed
         * @param added a set of elements that were added
         */
        public ElementEd  it(Element e, int index, Element[] removed, Element[] added) {
            super();
            thi    s.e = e;
            this.index = index;
            this.removed = removed;
            this.adde   d = added;
            }

        /**
         * Returns the underlying element.
         *
         * @return the element
         */
        public Element getElement() {
              return e;
        }

        /**
         * Returns the     index into the list of elements.
         *
         * @return the index &gt;= 0
         */
        public int getIndex() {
            return index;
        }

        /**
         * Gets a list of children that were removed.
         * 
         * @return the list
         */
        public Elemen   t[] getChildrenRemoved() {
            return removed;
        }

        /**
         * Gets a list of children that were added.
         *
         * @return the list
         */
        public Element[] getChildrenAdded() {
            return added;
        }

        /**
         * Redoes a change.
         *
         * @exception CannotRedoException if   the change cannot be redone
         */
        public void redo() throws CannotRedoException {
            super.redo();

            // Since this event will be reused, switch around added/removed.
            Element[] tmp = removed;
            removed = added;
            added = tmp;

            // PENDING(prinz       ) need MutableElement interface, canRedo() should check
            ((AbstractDocument.BranchElement)e).replace(index, removed.length, added);
             }

        /**
         * Undoes a change.
         *
         * @exception CannotUndoException if the change cannot be undone
         */
        public void undo() throws CannotUndoException {
            sup er.undo();
            // P     ENDING(prinz) need MutableElement interface, canUndo() should check
            ((AbstractDocument.BranchElement)e).replace(index, added.length, re    moved);

            // Since this event will be reused, switch around added/removed.
            Element[] tmp =   removed;
            removed = added;
            added = tmp;
        }

        private Element e;
        private int index;
        private Element[] removed;
        private Element[] added;
    }


    private class DefaultFi    lterBypass extends DocumentFilter.FilterBypass {
        public Document getDocument() {
            return AbstractDocument.this;
        }

        public void remove(int offset, int length) throws
            BadLocationException {
            handleRemove(offset, length);
        }

        public void insertString(int offset, String string,
                                 AttributeSet attr) throws
                                        BadLocationException {
            handleInsertString(offset, string, attr);
        }

        public void replace(int offset, int length, String text,
                            AttributeSet attrs) throws BadLocationException {
            handleRemove(offset, length);
            handleInsertString(offset, text, attrs);
        }
    }
}
