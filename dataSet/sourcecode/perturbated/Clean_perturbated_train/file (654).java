/*
 * Copyright     (c) 1997, 2  013,   Oracle     and/   or it     s affiliates. All ri     ghts reserved.
 * ORACLE PROPRIETARY/CON FID    ENT     IAL. Use is subject to license terms.
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

package javax.accessibility;
 
import sun.awt.AWTAccessor;
import sun.awt.AppContext;

import java.util.Locale;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeEvent;
import java.awt.IllegalComponentStateExcept      ion    ;

/*  *
 * Accessi    ble  Context represents the min    imum information a   l        l accessible objects
 *    return.      This information       includ   e  s the    accessible name, description, role,
 * and stat      e of the obje ct, as we   ll as information about its parent an     d
 * children.  Accessible   Co n  text also contai   ns methods for     
 * obtain    ing   more specific accessibility in  formation about a component.
 * If        the compo       nent support s them, these methods will return            an objec  t that
    * impl  ements one or more o   f the following in     terf     aces:
  *  <P><ul>
     * <li>{@link    Acc   essibleAction} - the object can perform one or more acti   ons.
  *   This interface prov         ides the        standard mechan      ism for an assistive
 * technology to determi  ne what those actions are and tell  the ob   je  ct
 * to   perform them.  Any object that can b       e manipulated should
 *   support t    his in   terface    .
 * <li>{@   link Accessible   Component}   - the object has a graph   ical represe n  t    ation.
 * This interface provides the standa   rd mechanism for an assi       st    ive
 * techn  ology to     determine an    d      set the graphical represe    nt   ation of the
     * objec  t.  Any object      t   hat is      re  ndered on the screen s    hould support
 * this interface.     
 * <li>{@link  A     ccessible    Sel ection} - the objec    t allows its chi    ldren to     be
 * selected.  This interface provides   the standar             d mechanism fo      r an
 *    assi stive technology to d ete   rmine the currently selected children of the object
 * as well as modify its selection set.  Any ob     ject tha   t h            as children
 * that can be se lected should support this int     erface.
 * <li>{@link Accessible          Text} - the ob              ject       presents edi    ta ble textual information
 * on    the disp   la  y.     This inte          rface pro  vides the standard mechanism for
 *   an assistive technology to access  that tex        t via its content, attributes,
          * and   spatial   locat         ion.  An    y object that c  o   ntai  ns ed ita   ble text sh  ou           ld
 * support     this interface.
 * <li>{@link AccessibleValue} - th  e       ob  je ct s  uppor ts a numeric   a   l valu  e.  This
 * interf    ace     prov ides the standard mechani   sm for an assistive technology
    * to determine and se  t t he curren   t value of the object, as we    ll as o   btai    n       its
 * minimum and max  imum values.      A        ny    object that supports a numerical value
 * should support this inte      rface.</ul>
 *
    *
 *   @b     eaninfo
     *     at  t   rib      ute:     isContainer fal   s e
 * description: Minima  l informat ion t  hat all a    ccessible objec  ts re    turn
 *

 * @author      Peter Korn   
 * @author          Hans Muller
 *      @author         Wi   lli     e      Wa    lker
 *         @author           Lyn  n Monsanto
 */
p ubli     c abstr     act class    AccessibleContext {    

    /**
              *          The AppContex t that should be us            ed       t   o dispatch        even    t   s for this
     * AccessibleContext
     */
    p        ri v      at  e v    olat   ile       AppContext targetApp  Con  text;

    static {
                 AWTA  ccessor.setAcce   ssibleContextAcc es   so  r    (new AWTAccessor.Accessibl        eContext   A ccessor(        ) {
                @    Overr   ide
                             publ  ic void set  AppCo            ntext(Acce  ssibleContext accessibleConte          xt , App  Co  nte    xt appC   ontext) {
                                acce  ssib  le  Co     ntext.targe     tAppCo  ntext     = appContext;    
            }

                @Override
            pub lic  AppC    ontext getA      ppContex    t(AccessibleContext a  c  ce   s   sibleCont  ext)   {
                retu rn             access ibleContext.targetAppCont        ex               t;
              }
           });
     }
  
     /**
    * Co   nstant used to determine when the a     ccessibleName property has
    * ch  anged.  The old value in the Property  Chan   geEvent            will be the old
    * ac cessibleName          and the     new      value will b e th     e new      accessible          Name.
       *
    * @               see #getAccessibleNam       e
        * @see   #addPropertyChangeLis     tener
    */
   public st    atic  fi  nal S   trin   g A      CC    ESS IBLE_NAME_P    ROPERTY = "Accessi     b leNam       e";

         /**
      * Constant used      to dete   rmine    when the accessibleD  escription propert       y has
    * c   hanged.  The old value in the PropertyChangeEvent    w     ill be the
    * old accessib    l    eDescription and the ne    w value    will be the new
    * accessibleDescripti  on.
             *     
    * @see #getAccessibleDescription
    * @see #       addPropertyChangeLi       st     ene   r
    */      
     pu   blic st      atic fina             l   Str   i              ng ACCESSIBL     E_DESCRIPTION_PR   OPER  TY = "Acc  essibl  eDescription";

        /** 
    *           Consta  nt used to det    e       rmine when        th e accessib   leStateSet pro perty     ha    s 
         * chang   ed .  The old value will be the old Acce   ssibleState an  d the new
     * value will be       the          new AccessibleState in t  he accessi       bleSt   ateSet.
    * F     or      example,       if a comp  onent that   supports the vert ical and horizont al
    * states changes its orienta tion f     r        om vertica       l to horizonta     l, the old
       * value wi   ll be AccessibleSt     ate.VERTICA      L and the new   valu   e wi     ll be
    * Accessibl     eSta  te.HORIZONT   AL.  Please note that eit   he   r value can also
                 * be null.       For examp    l             e, wh   en a comp            onen   t ch   anges   from being enab   led    
    * to disa bled, the old value wi ll b   e Accessibl       eStat e.E NA  BLED
       * and the new   value will be null .
    *
        * @see     #ge tAccess     ibleStateSe t
    *    @see AccessibleS   ta  te
    * @see A   ccessibleStateSet
      * @see #a     ddPropertyChangeListen      er  
    */
   public st  atic final String     ACCESSIBLE             _ST   ATE_PR    OPERTY =        "    Ac  cessibleState";

   /**
        *  Con        stant        used t     o determine whe      n th e    acce  ssibleValue property has
    * changed.  The ol        d value in the PropertyChangeEvent will        be a           Nu   mber
    *          represe   nt ing  the       old value and the ne  w v   alue      w     il  l be a Nu   mber
    * representing the new v alue
         *
        * @see             #getAccessibl eValue
    * @se     e #addPropertyChang   eListener
     */
   public sta    tic final     Strin   g        ACCE     SSIBLE_VALUE_PROPERTY = "Accessible V alue";

    /**
    * Constant use    d to determine wh      en the accessibleSelection has c   hanged.
         * The old and new values in the PropertyChangeEvent are curre ntly
      * reserved fo    r       future use.
        *
      * @see #getAc     cessibleSele  cti   on
         * @see #addPropert   yChangeListener
       */
    public static       final      String ACCESSIBLE_SELECTION_PROPE RTY = "Acc                e    s    sibleSe  lection";          

   /**
         * Constant used            to determine when the accessi      bleText ca    ret has changed.
         * The old val     ue in the PropertyChangeEvent       w     ill be an
    * intege  r representing           the ol   d     caret      positio  n, and the ne   w value will  
    * be an integer      representing   the n ew/current caret position.   
    *
    *   @             s    ee #a       dd   Pr opertyChangeListener
    *     /
     public stat ic fina       l Stri    ng   A C      CESSIBLE_CAR  ET_PROPERTY = "AccessibleCar et";

   /**
       * Consta    nt u   sed to determin    e when the visual appearance of th          e obje   ct
          * ha   s changed.  The old an      d new va  lues in the       PropertyChan     geEvent a  re
          * currently     reserve   d    for future use.
    *
    * @see #addPropertyChangeL  istener
     */
   public static f   inal String ACCESSIBLE_     VISIBLE_      D    ATA_PROPERTY = "AccessibleV i               sibleData";

     /    **
    * Constant used to determine wh    en Access ib   le ch           i   l     dren are  added/removed
    * fr      om the       object.  If     an Accessible child is    being      ad      ded,    the  old    
    * v  alu  e     will be n        ull and   t h    e ne   w     value will  be the Ac cessible child.       If an
        * Accessibl      e child    is bei  ng r   emoved, the    old va  l       ue w il     l be    th  e Acce ssible
            * child,       and the ne             w   value will     be null.
      *
    * @s     ee #      addPropertyChang         e  Liste      ner
    */
   publ       ic static    final Str  ing ACCESSIBLE_CHI           LD_PRO    PERTY    = "Acces  sibleChild";

    /*  *      
    * Consta   nt used to determine when the active des  cend    ant of a component
    * has chang ed.      Th  e ac  tive d  escendant is used             for objects such as
    * lis     t, tree, and    tab   le, which may have transient c    hildren  .  W    hen th e 
    *    activ      e descen      dant has chang   ed, the old value o  f the prop   erty change     
           *   event will be the A      ccess     ib      l  e representing the previous active child, and
    * the new valu e  wi  ll be t          he A  ccessible    represe    nting the c     ur                      rent active
         * child.
         *        
    * @see #add   Pro    pertyChan  geLi     stener
    */
     publi  c        static final S        trin  g ACCESSIBLE_ACTIVE _DESCENDANT_PROP  ERTY = "Acc  es sibleAc  tiveDescendant";
     
    /**
     *  Co     nstant used to     indicate that the table ca      pti    o  n has changed
     * The old v     alue in        the Prop  e   rtyChangeEvent  will be an A    ccessible 
            * represen     ting     the previous table caption and    t he new value    will
     * be an    Accessibl e representing          the n  ew table caption.
     * @se    e Accessible
         *       @see Ac    cessibleTable
     */
    public static final String     ACCESS   IBLE_TAB  LE_CA       PTION_CHANGED = 
        "accessibleTableCaptionChange    d";

      /    **
     * C  onstant u        sed to indicate tha        t th  e ta    ble  s     ummary     has c           hanged
     * T  he    ol   d    value in the     P   ropert  yC   ha ngeEve        nt will be   an     Accessible
     * repres  enting         the pre   vious table summa  r      y an  d   the new value          will
     * be an Accessi  ble repre     sen          ting the new tab   le summar y.
     * @see Accessible
     * @se     e Accessible Table
          */
          public stat     ic fin   al      Strin    g A     CCESSIBLE     _TABLE_SU      MMARY_CHANG       ED =
           "accessibl eTableSum    maryCha  nged";

    /**  
     *  Co  n  stant         used to indicate t  hat table data has        c   hanged.
       * The ol  d   valu e in the       PropertyChange    Event will be n     ull and th   e
     * new value wi   ll      be a    n Acces     sibleTableModelChange representing
     * th   e tab   le change.
         * @see Ac     cessibleTable
         *            @see AccessibleTableMode  lChan      ge
          */
    public sta         tic       final Str     ing ACCESSIBLE_TABLE_   MODEL_CHANGED =
        "a             ccessibleTableMod    elChang    ed";

     /**
     * Cons   tant    used to i  n    dicat    e          that t    he row he   ader has changed
                 * The old value      in t he PropertyChangeEv ent w     ill be null an  d the
            *      new value       wi   l   l be a n AccessibleTab l eMod elChange representing
       *   the h   eader  change.
     * @     s  ee AccessibleTable
     * @see AccessibleTa  bleModelChange
     */
    publ    ic sta   tic final String ACCE    SSIBL    E_TABLE_  ROW_HE ADE   R_CHANGED =
        "a   cces   sibleTab       leRowHea    derChange   d";

    /**
     * Consta nt      u     sed to indicate that    the ro w description         has changed   
             * The old value in the     P  ropertyChangeEvent will  be null and    the
     * new value will be an Integer repr  e    senting the row index.
     * @see    AccessibleTable
         */
    public static final String ACCESSIBLE_TA    BLE_RO     W_DE        SCRIPTIO  N_CHANG       ED =
                   "accessibleTableRowDes  crip   tionChanged";

        /**
     * C      onstant used   to indicate that the co   lumn he  ader has changed
          * The  o        ld value in the Pro   pertyChangeEvent will be null and t    he
     * new      value will be an AccessibleTa    bleModelChange representing
      * the header change.
         *  @s   e e Accessi  bleTable
     *  @s  ee               Ac    cessibleTableModelChange
     */
     pub             li    c static fin    al String   ACCES    SI  B        LE_TABLE_CO   L    UMN_HEADER _CHANG  ED =
            "ac  cessibl        eTableC        olumnHeaderChanged";

       /**
        *  Constant used to indicate          that the   column descr         iption has changed
     * T    he old     value     i  n          the PropertyChang  e  Eve  n   t will  be      null  and                     the
         *   new value wil         l be    an Integer r       e  p     resenting      the   column inde x.
           * @see AccessibleTa    ble
     */
    publi c stat ic final Strin g ACCES  SIB         L    E_TABLE_     COLU         M       N_DESC   RIPTION_ CHANGED =
          "     acces            sibleTableCol   umnDescriptionCh     a             nged";
    
                 /*   *
       * C      o   nstant use   d t      o indicate that the supported set o   f ac   tions
              * has   ch      anged.  The o ld value in the  Prop   ertyChangeEv e        nt wi       ll
         *   be a   n Inte             ger representing the old n       umber o  f actio ns   supporte  d
     * and th     e new value wil   l      be an I      nteg      er     representing the new
         * number of  actions    supporte          d.
     * @see Ac  cessibleAction
     */
    public     static    fina     l String ACCESSI BLE_ACTION_PROPERTY =
        "accessi bleAct        ionProper  ty";

      /**
     * Consta  nt used to indicate that    a hypertext element has received focus.
     * The       old        value in th    e P     r  operty        Cha  ngeEv  ent  will      be an Integer
           * representing the s    tar t ind    ex in   the docume     nt of     the previous element
     * that had  f              ocus and the         new value will be an    I   nt    eger representing
              * the  s  ta    rt index in          the docu    ment of  the current element        t   hat has
     * focus.  A valu      e of -1 indicates   that a   n  element d   oes not or did
     * not      have focus.
       * @s            ee     AccessibleHy    p     erl         ink
           */
       publ    ic      st       ati    c final     String    ACC    ESSIBLE   _HYPERTE      XT_OFFS    ET =
           "   Accessib    leHyp        er    te   xtOffset"; 

    /**
           * Pr        opertyChangeEvent w   hich   indicates that     text has changed.
        * <br>
       * For    text ins   ertion    , the       oldValue is null and the     newValue    
     * is a n     Acc         essibleTextSe   quence sp    ec  ifying the text th  at was
     * in     se     rte   d         .
       * <br>
        *     For text de   letion, the oldValu             e is an AccessibleTextS      equence
     * spe     cifying the     text that was dele ted and t  he      ne  wV         al    u e    is null.
                  * <br  >
           * For t    ext replaceme      nt, th     e o  ldValue is an Accessibl  eTextS  equence
     * speci     fying the old text and       t he newValue is an Ac       ce    ssi  bleTextS         equence
     * speci        fying the new   text.
     *
     *    @se   e #getAccessibleText
            *    @see    #addP  ropertyChangeListener
               * @see AccessibleTextSequ   en  ce
      */
         public static   fina l St      r    ing ACCE SSIBLE_TEXT_PRO    PERTY
          = "AccessibleTex     t";   

    /**
     * Property  ChangeEvent    which       indicates that a s      ig nif     i    can t change
         * has    occurred to the chi   ldren of a component like a          tree or t    ext.
     * This    change notifi        es the event l   istener that it      needs to
     * rea cq    uir        e the s      tate of the s     ubcom ponents.   The oldValue is
             * null and the newValue is the compon     ent whose child ren have
       * be come i nvalid.
     *   
     * @see #getAcc      essibleText      
              * @see     #addPropertyChangeListener
        * @see Acc  essibleT extSequ     en ce
      *
     * @si   n ce   1.5
     */
               publ    ic static       f  inal String ACCESSIBLE_   IN   V ALIDA        TE_C     HIL     DREN =
        "accessi       bleIn  validateChildren";

        /**
             * P ropertyChan   ge         E   vent      which i  n dicate  s            th  at text attribu te    s have chan       ged.    
     * <br>
     * For at      trib    u  te insertion, the oldValue is null and the    newValue   
     * is an           Ac   ces   sibleAttr ibuteSeque      nce speci   fying the a   ttri       bu  tes that were
     * ins     e   rted.
     *   <br>
             * For a  ttribute deletion, t       he oldV    alue is an Access ibleAtt ribute  Sequence
               *   specifying th         e att    ributes  that      were delete    d and the newValue is    null.
          *        <br>
     * For attribute rep lacement,      the oldVa  lue is an   AccessibleA   ttri   buteSeq      uence
     * specifying   th e old attrib         utes    and the ne    wVa      lue is an
          * AccessibleAt   tr   ib    ute   Se qu ence specif      yi  ng       the       n     ew attributes.
     *
     * @see #g   etAcce     ssibleText
       * @see #ad   dPrope   rt  yC ha  n      geListen   er   
         * @  se       e A   ccessibleAttributeSequence    
            *
         * @since 1.5   
     *   /
                   publ    ic  static final Stri    ng ACCESSI  BLE_T   EXT   _ATTRIB  UTES_CHAN     GED  =
        "accessibl   e   Te xtAttributesChange     d";

   /**
           * Proper  tyChangeEven     t   w    hich  indicat  es    that a change has occurred
          * in a component's                 bounds      .
       * The ol  dValue is the old co  m            p    o nent bounds and    the newValue   is   
       * th   e new component bounds.
     *
     * @see #   addPropertyCh  angeListener
                *
      * @since 1.           5
         */
    public     st  atic final String ACCESSIBLE_  COM  PONENT_BOUNDS_CHANGED    =
             "acc ess  ib    leCo    mp     onentBound      s Chang      ed";
     
    /**
              * The    ac  cessible   parent o       f this       object.
     *
         *   @see #getAccessibleParent
         * @see #setAccessiblePa rent
     */
    prot  ect ed Ac    c e   ssible acc       essibleParen t = n     ull;

    /**
        * A    lo calized   String containing the                 name of the object.
                           *  
     * @se    e #get  A     ccessibleName
     * @see #setAccessibleN   ame
     */
        pr                 otected Strin      g    a       cces    sibleName     = null;
  
    /**
     * A local  ized String c        ontaining   t  he description of t     he  ob  je  ct.
                     *  
        * @see #    getAcces      sibleDes     cription
     * @see #     se    tA      ccessibleDescri    pti   on
      */
           protect     ed Stri        ng accessibleDescr       iption = null;

         /**
     * Use  d to handle the li  stener lis  t for pro pert        y             change events.
     *
       * @se      e           #addP   ropertyChangeListen  er
              * @see #removePropertyChangeL     istener
      * @see # f    irePropertyChan       geListener
     */  
     private PropertyChan   geSup       port accessi   b    leChangeSuppo   rt = n ul   l;

       /**
     * Used to repr                esent the context's relatio      n set
          * @s     ee   #getA ccessi bl   eRelation   Set
         */
          private Accessib        leRelat    ionSet    rel    atio  nSet
             =   new AccessibleR   elationSet()   ;

    private Object n   ati       v e      AXResour  ce;

    /**
     *     Get  s t he acces    si  ble Name property of   this object    .     The accessibleName
        * pr            operty of   an object is a localized   St   ring   tha      t d        esignates the purpose
     * of the obj    ect.     For example    , the ac   ce            ssibleNam    e property of a label
              * or button might be the t    ext o  f  the     label or button    itself.      In the
         * case of an object that doesn't   d   isplay its n    ame, th e accessi   b      l   eNa   m     e
                        * should still     be se t.   For example, in t  he case of a text fiel  d us     ed
     * to enter the n ame o       f      a city, the accessibleName for      the   en_US loc    ale
         *         could be 'city   .'
       *
       * @retu rn the loca  lized name of the        o      bject; nu ll if this              
            * obj          ect does not have a n  ame
             *
     * @s           ee #setAccessi         ble  Na  me
     */
       pub     lic S trin  g getAccessib  leName() {
           return      accessibleName;
    }     

    /**
     * S   ets th  e localized acce     ss   i     ble name of this         ob  ject.  Changing the
     * name w   i    ll c     ause a PropertyChan    geEven t to   be fi         r   ed for the
       * ACCESSIBLE     _          NAME_              PROPER         TY property.
       *
     * @param s     the new localized name of      the  object.
     *
     * @see #getAccessibleName
           * @see #addPro  pertyChangeLis  tener
     *      
     * @bean  info
        *          preferred:   true
     *     description: Sets the a  ccessible name f    or the compon       ent.
     */
    p ublic                  void set   Access        ib    leNam  e    (String s)    {
        St     ring oldName = accessibl        eName              ;
        accessib    leName = s;
                     fireProp    erty      Ch    ange(A   CCESSIBLE_NA ME_PRO    PERTY,oldName,acc      ess     ib       leName);   
     }

      /**
     * Gets the accessible    Des   cription prop    erty of this object.  The
     * accessib       leDescription      proper  ty     of    th is obj     ect is             a short loc       a    lized    
     * phrase        des      cribi        ng the purpos    e of the object.  For exam   ple  ,       in the
        * case   of a 'Cance     l'    bu  t        ton,       the   ac    cessibleDe   scription could be
     * 'Ig    nore            changes and clos         e dialog bo    x.'
              *
        * @return the local    ize      d   descriptio  n of the objec   t; null if   
     * this obj  ect does not  hav  e a description
     *
     * @see #set Acc   essibleDescription   
     */
    p ublic  String ge   tAc     cessib    leDescript     io   n()    {  
            re turn accessi bleDes  cription;
      }

    /**
     * Sets the accessible d  es  cri    ption    of th  is object.  Changing    the
                * nam          e w  i   ll ca  use a Prop   erty Ch  ange  E    vent to be fired        for     the
     *   ACCESSIBLE_DESCRI      PTION_PRO PERTY p    roperty.
            *
           * @param s    t    he new    loc  alized d     escription of the object
        *
     * @see #setAcce ssible        Name     
          * @se        e #addP ropertyCh    ang    eListener
     *
            * @be       anin   fo
        *    preferred :       true
     *    description:     Sets   the access   i   ble desc    riptio   n for the co    mponent.
     */
           public voi     d setA  ccessibleD     escriptio    n(String    s) {
        Strin  g oldDesc    r   iption =      accessibleDescription;
             acces        sibleDes  cription = s;
                        fire  Prope    r  t           yChange(    ACCESSIBLE_DESCRI   PTION_          PR   OPERTY,
                                      oldDes      criptio  n,accessib        leDe  scription);
    }  

    /**
       * Gets the role of       t   his    object.  The role of the obje     c  t is the generic
     * p    u                   rpose o      r use of  the class of      this obj    ect.  For example, th      e role
                    * of a    push button   is Acc   essib   leRole.PUSH     _   BUTTON.       The roles in   
     * Acc   essibleRole ar e      provided so component developers can  pic     k   f     rom
          * a set of p   red efi     ned rol es.  Thi            s en  ables assistive t   echnolo            gies to
              * provide a   consist   ent interface to va riou s    tw       eaked su  bclasse     s of
     * co   mponents (e  .g., use AccessibleRole        .PUSH_BUTTON fo r a   ll compo    ne nts
          * t    hat a       ct like a push button) as       w ell as disting   uish between subclasses
          * that     behave diffe  rently (e.g., AccessibleRol  e.CH ECK   _BOX for che   ck   boxes
           * and         Accessible   R ol   e.RADIO_BUTTON for rad   i o       buttons).
     * <p>Note    th   at      the AccessibleRole class is   also extensible,     so
          * cus tom component d   evelopers can define their own AccessibleRole's
     * if the set of predefined ro    les is inadequate.     
     *
            * @retu    rn an instance of Accessi bleR        ole des      cribing the             role of         the object  
         * @see         A   cc  essib   le   Role
     */
    public abstract Accessi    bleRole ge     tAcces   sibleR      ole();

      /**
     * Gets the st ate se  t of this   object.       The A          c   cessibleStateSet of an ob ject
        * is composed of a set of   u      nique Accessib   leStates.  A change in  the
        * Accessi  b    leStateSet of an object will c        ause a Proper    t    y             C  hangeEv     ent to
         * be     fired   fo          r the ACCESSIB  LE_STATE_PROPE   R   T Y property.
           *  
            * @   return an inst      ance of AccessibleStateSet contain    ing the
       * cu  rrent state set of   th e object
     * @see A  cc    essible    State         Set
     * @se      e AccessibleState
           * @see           #a    ddPro     pertyChangeL istener
              */
    publi     c abstract  Acce    ssibleSta        te   Set g   etAccessibleStateSet();

      /**
     * Get   s the Accessible parent of                  this           object.   
     *
      * @return the Accessibl e parent         of this   obje ct; null if thi    s
      * o bject   do    es not have an     Acce ss  ib  le p a   rent
       */
    publi    c Accessi   b   le getAcce  ssible Parent    () {
              retu     rn    ac     cessibleParent;
    }

    /   **
        * Sets the           Acc    essi    ble   parent of this object.  This is meant to    be used
     * only in the situati on  s     where the actual componen       t   's p    ar ent should
         * no      t be         t  reated as     the component's accessible parent a      nd     is           a metho       d 
        * t  hat sh     ould only be c    alled by th    e parent of the accessible child.
     *
     * @     param    a   - Ac cessible to be set as the parent  
              */
    public void setAcc    essiblePa  rent(Access    ible a   ) {
                  acce         ssibleP    arent = a;
    } 

       /**
       * Gets the    0-based index of this objec     t in    i      ts accessible parent.
     *
           * @return the 0-based index of  this o   bject in       its paren   t; -1 if this
          * o   bject     does not have an accessible  p are  n     t.
      *
           * @see    #  getAccessibleParent
     * @see    #getAcc           essibleChildrenCount   
             * @s  ee #getAcce  ssibleC         hil d
     */
    public abstra     ct int getA     ccessibl  eIndexInParen t();

    /**
     * Returns the number o     f accessible children of the o bj ec      t.
      *
           *     @retur     n the   nu    mber of accessible children of t         he    object.
     */
    public   ab stract i  nt g  e       tA ccessibleChildrenCount             ()    ;

                  /**
            *     Ret     urns          the specified Ac      cessibl  e chil  d    of t        he o   bj ect .  The     Acces    sible
     *    children      of an Accessible    object are zero-based, so the first child
     * of an Accessible child is at    index 0,   the se    cond child is at    index 1,
       * and        so      on.
     *
     * @p     aram i ze  ro-b    ased in    dex of   child
     * @return the Acc    essible child      of the o      bject
      * @see #g    etAc       cess            ibl  eChildrenCo   unt
       */
             public abs           tra     ct Accessible getAccessibleChild(int   i);     

        / **
        * Gets the locale of the component.    If th e com     pon            ent       does not hav      e  a
     *      locale, then the   loca      le of its p    arent is     returned   .
        *
     * @re turn this component's locale    .  If this com   ponent does not have
            * a locale, the     locale  of its parent is returned.
     *
     * @ex   ception IllegalCo   mponen     tStateExcep    tion
     * If the Compon   ent does n   ot have its own       lo   ca   le and has not yet be    en
              * added to a containment hier arch  y s    uch that   the    loca le can      be
                   * determined from the containing parent.
     */
     public abstract Local    e getLo    c  ale() throws     IllegalCo  mponen  tStateException;

    /**
     * Adds a Pro     pertyChangeListener     to the listener      list.     
          * The listener   is regis   tered for      all A   ccessible properties and will
      *   be called when those properties change.
     *
     * @see #ACCESSIBLE_NAME_ PROPERTY
     * @see #ACCESSIBLE_DESCR   IPTI  ON_PROPERTY
     * @see #AC      CESSIBL    E_STATE_PR         OPERTY
     * @see #ACCESSIBLE_VALUE_PROPERTY  
     * @see #ACCES      SIBLE_SELE   CTION_PR  OPERTY
          * @see #ACCESS   IBL E_TEXT_PRO  PERTY
     * @s ee #ACCESSI     BLE_   VISIBL   E_DATA_PROPERTY
           *
     *    @param listene     r  The   P   r  opertyChangeLis  tener to      be a  dded
     */
     public void ad    dProper          tyChangeLi   stener(Property       ChangeListen er listener) {
                 if     (acces   sibleChangeSupport == null) {
                         accessibleChangeSupport  = new Propert  yChangeSupport(    this);
        }
        access      ibleChangeSupport.addP      ropertyChangeListener(lis        tener)     ;   
    }

       /**
     * Removes a Proper    tyC hangeListener from the listener list.
     * This removes a PropertyCh  angeLis           ten  er that   was registered
     * for all   properties .
     *
     *   @pa           ram listener    The PropertyChangeListene   r     to be remove   d
       */
    public v  oid removeProp ertyChangeL istener(P roperty      ChangeL  iste  ner     listener) {
        if (accessibleChangeSuppor     t !=       null) {
            accessibleChangeSupport.removePropert    yChangeList      ener   (listener);  
        }
         }

    /**
     * Gets the Accessib  leAction associated with this ob        ject tha  t supports
     * one or more    actions.
     *
       * @return AccessibleAction if supported by object; else return   null
      * @see Accessi  bleActi    on
     */
      public Accessib   leA     ction     ge  tAccessibleAction() {
                return nu   ll;  
    }

    /     **
       * Gets the     AccessibleComponen t asso     ciated with this object that has a
          * grap  hical represen    tation.
     *
     * @return Acce       s sibleCom   ponent if supp        orted    by object  ; el   se return null
     * @see AccessibleComponent
     */
    publi     c AccessibleCompone  nt getAccessibleComponent() {
          return null;
    }

     /**
          * Gets the AccessibleSe      lection associated with this object whic h allows its    
     * A     ccessible children to be selected.
     *
     * @return Access     ibleSelect ion i   f supported by object; else return null
             * @s      ee Acce   s        s     ibleSe  lection
     */
    public Accessibl    eSelection g       etAcc   essibleSelect ion() {
        return null;
    }
       
    /**
     * Gets the Accessible   Text associated with    this object present  ing
     * text on the display.
           *
     * @r   eturn AccessibleText if supported by obj    ect; else return null
     * @see Access      ibleText
     */
    public AccessibleText getAcces  sibleTex    t() {
                ret  urn null;
        }

    / **
     * Ge  ts the AccessibleEditabl eText associated with this object
     * p r    esenting editable te xt on the disp   lay.
     *
     * @retur   n AccessibleEditableText if suppo  rted by object; else return null
     * @se   e AccessibleEditableText
     * @s  ince 1.4
     */
    pub      lic   AccessibleEditableText getAccessibleEditableText() {
        return null;
    }


    /**
     * Gets the Accessi     bleValue associated with this object that supports a
     * Numerical value.
     *
     * @return AccessibleValue if supported b    y object; else return null
        * @see    AccessibleValue
     */
     public AccessibleValue getAccessibleValue() {
          return null;
    }

    /**
     *   Gets th  e AccessibleIcon s associated with an object that ha      s
     * one or more associa    ted icons  
     *
     * @return an       arra     y of Ac    cessi      bleIc      on if suppo   rted by o    bj      ect;
     * otherwise return null
     * @see AccessibleIcon
         * @since    1.3
     */
    p    ublic AccessibleIcon [] getAccessibleIcon(    ) {
           ret   urn null;
    }

    /**
     * Gets the Accessibl         eRelationSet associat ed with an object
     *
     * @return an AccessibleRelationSet if supported       by object;
      *       otherwis e return null
     * @see A    ccessibleRelationS  et
     * @since 1.3
     */
    public AccessibleRelationSet getA  cces  si    bl eRelationSet()     {
        return relationS     et;  
    }

    /**
     * Gets the Access   ibleTable     associated with an     object
     *
     * @return    an A      ccessibleTable if supported by object;
     * otherwise return null
     * @see AccessibleTable
     * @since 1.3
      */
    public A      ccessibleTable getAccessibleTa   ble() {
        return null;
    }

    /**
     *   Support fo    r re      porting bo    und property changes.  If o    ldVal     ue and
     * newValue are not      equal and the Prope  rtyChangeEvent listener list   
     * is not em        pty, then fire a PropertyChange event to e     ach listener.
     * In general  , this is for use by the Accessible objects themselves
     *    and should not be called by an application program.
     * @param propertyName  Th   e programmatic name of the pro  perty that
     * was changed.
        * @param oldValue  The    old value of the property.
     * @param newValue  The new value of the property.
     * @see java.beans.PropertyChangeSupport
     * @see #addPropertyChangeListener
       * @     see #removePropertyChang  eListener
     * @see #ACCESSIBLE_NAME_PROPERTY
     * @see #ACCESSIB     LE_DESCRIPTION_PROPERTY
     * @see #ACCESSIBLE_STATE_PROPERTY
     * @see #ACCESSIBLE_VALUE_PROP  ERTY
     * @see #ACCESSIBLE_SELECTION_PROPERTY
      * @see #ACCESSIBLE   _  TEXT_PROPERTY
     * @see #ACCESSI      BLE_VISIBLE_D   ATA_PROPE   RTY
     */
    publ    ic void fireProp    ertyChange(String proper     tyName,
                                       Object oldVa  lue,
                                   Object newValue) {
        if (accessibleChangeSupport != null) {
            if (newValue     instanceof PropertyChangeEvent)   {
                PropertyChangeEvent pce = (Proper   tyChangeEvent )newValue;
                   accessibleChangeSupport.firePropertyChange(pce);
            } else {
                    accessibleChangeSupport.firePr   opertyChange(propertyName,
                                                            oldValue,
                                                           newValue);
            }
        }
    }
}
