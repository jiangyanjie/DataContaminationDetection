/*
   * Copyright (c) 1999, 2006, Oracle and/  or   its affiliates. All    rights reserved.
 *   ORACLE PROPRIETARY/CONFIDENTIAL.   Use is subj       ect to license   terms.
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

import java.util.Vector;
import java.util.Locale;
import java.util.MissingResourceException;
    import java.util.ResourceBundle;

/**
    * <P>Class    Accessibl       e   Relation describe         s      a rel     ation between the
 * object that i   mp  lements t     he       AccessibleRelation and one or m  ore  other
 * ob  jects.  The actual relations that an ob   ject has with oth   e     r
 *       objects are defined as an   AccessibleRelationSet, wh   ich is a      composed
 * set   of AccessibleRela     tions.   
   * <p>T  h   e toDi    sp     layString method allows you to obtain     t  he lo    calized str    i ng
 * for a lo cale independent key from a predefined Resource      Bundle for the
       * keys defined in this class .
 * <p>Th   e     consta nts      in th   is cla   ss present   a   strong  l        y t     yped enumerati  on
 * o   f common    object r           oles. If   the constan ts in this class are not    suffici       ent
 * to describe      the role of a  n ob  j  ect,  a subclass should be  generated
 * from this cla    ss and it should provid   e constant     s in a  s  imi la      r manne r.        
 *
   * @author      Ly   nn Mon  santo
   * @since 1.3
     */
public class Acce         ssibleRelat   ion extends Acce  ssi    bleBundle    {

       /*
     * The         group    of objects that particip ate     in       the relation.
     * Th    e rel     ati      on m  ay be on   e -to-o   ne or one-to-many.  For
      * example,      in th e case of  a LABEL_FOR relat  ion   , the         target
     * vector would contai  n a   list   of ob   jects    l abele              d by t  he object
            * tha  t implements t   his    AccessibleRelation.  I     n  the cas e of a      
          * MEMBER_O  F     relati on, the t        arg   et vector would c         ontain all
     * of the compo   ne    nts that are members of   t     he sa    me      gro up as the
      * obj     e   ct that impleme     nts thi                s Accessible         Re      lation. 
     */
           private Objec        t [] target = new  Object[0     ];

     /**    
     *     I    nd   icates an   obje   ct is a lab    el      fo   r on  e        or more tar     get o    bjects.
             *
            * @see  #        getTar   get    
             * @see #C      ONTR      OLLER_   FOR
       * @se         e  #CONTROLLED_    BY
        * @   see #   L      ABELED_BY
            * @see   #MEMBER_OF
             */
    public  static final Stri    ng LABEL_FOR = new String("labelFor")       ;

    /     **
     * In     d          icates    an o bject  is labeled by  one    or more target objects.
     *
         *  @    see         #ge  t   Targe   t
                  * @ see #CONTROL  LER_FOR
             *            @see #CONTROLLED_BY
             * @see #LABEL_F   OR
     * @see #MEMBER  _OF
        */
    public s  tatic     final String LABELED_BY    = new String("labeledB    y");

    /**
       * Ind icates an o    b    ject is a member of a group of one      or mor               e
               * tar  get object    s       .
                                *
          * @se e #getTarg et
     * @s     ee #CONTROLLER_FOR
     * @see #CONTROLLED_BY
         * @s   ee #LABEL_FOR
       * @se  e     #LABELED_   BY
            */
    public     static fin      al     St ring MEMBER_OF = new String("m   emberOf");        
     
        /**
       * I ndicates     an object is    a control     l    e  r for o     n   e or m ore       target
           *  object      s.
     *
     * @s ee #getTarget
     *      @see #CONTROLLED_BY
       * @see #LABEL    _  F                OR
             * @see #L     ABE   LED_BY
            * @see #MEMBER_OF
       *     /
       public static fi                 nal String   C     ONT ROLLER_FOR =  n    ew St   ring("  controller For");

     /**
           * Indi                           cat es an object      i    s controlled by one o      r more      target
     *          objects.
     *
         * @see #   getTarg       et
     * @see #CONTROLLER_FOR
               * @see #LABE   L_        FOR
     * @        see #LABE   LED  _BY
     * @see #MEMB        ER_OF           
           */
    public st   atic f   i     nal     String CO       NTRO    LLED_BY        = new Str   ing("co nt      ro    lledBy ");    

      /**
       * Indicat              es      an    object      is logically c    on     tiguous with a s econd
       * obj       ect where            t he s    econd obj  ect occurs aft er           the o bject.
     * An exampl   e  is a               paragraph of text that runs t        o the end of
        * a  page and continues o n th   e next page with an int    erv  ening   
     * text footer an    d/o      r  text    header  .  Th     e two parts of
       *   the   par          agraph are separa  te text ele  ment   s b         ut are related
         * in      that the secon       d element  is a co n tinuation
         * of the first
     * element.  In oth              er w    ord      s, t    h  e first      el ement "flows to"  
                 * the second element.
     *
     * @since 1   .5
                     */ 
    public sta      tic fin al String F     LOWS_TO = "flowsTo";

    /**
               *       I n   dic    ates an objec     t   i              s log   i   cally co    ntiguous with a se co    nd
     * object where the    se    cond o bj    ect occurs before the object.
     * An  examp le is a para    graph of text th       at runs t        o     the end o      f
                * a page    and    continues o n the next page with an in         terven       ing
                    * text fo     oter and/ or text h         eader.      The two par          ts of
        *      t            he paragr   a  ph are   separate t    ex   t     elements but        are     r                 elated
      * in that the se    cond element is a conti       nuati     on of the f i   rst
         * elemen  t    .  In othe     r words,         the second element "flows f    rom"
                    * the s  econd element.
     *
     * @    since 1.5 
           */
    pu      blic static final   String  FL     OWS_FROM  = "flowsFrom";
    
    /**
     *    Indicates that an  object  is a subwindow of one or more
     * objects.
     *
        * @   since 1.5
        */
    publ   ic  static         f       inal    S   tring SUB   WINDOW_OF =      "subwi     ndowOf";

        /**
        * Indicates that an object is a parent win dow of one or more
         * objects.
     *
               * @since 1.5
     *    /
    public stat ic fi   nal String PARENT_W INDOW_OF = "pa  re       ntWindowOf";

    /**    
             * I             nd icates tha       t an object ha      s one  or more objects
     * embedded i  n it.
       *
        * @since         1. 5
      */
       pu  b    lic     static final     S    tring EMBEDS        = "embeds";
 
    /**
     *  Indicates that an    object is embe  dded in one or more
     * obj       ec       ts.
     *
     * @    si  nce    1.5
      */
            public   stat     ic final String EMBEDDED_BY = "embeddedBy";

    /**
         *   Indicat  es    that an object    is     a ch   ild n    od   e of one
     * or more o  bjects.
                     *
     * @since 1.5
              *    /
    public static      final    String CHILD_NODE_OF =   "childNodeOf"    ;

    /**
       * Ide  nti   fies that t     he target group for a label has ch an     ged
     */
    p   ublic s       tatic final Strin g LABEL_FOR_PRO    PERTY = "        labelForPro perty";

    /**
     * Identifies that the o  b jects that                 are doing    the labelin          g have cha  nged
     */
    public static final String LABELED_    BY_PRO  PERTY = "l      abele   d  By            Pro    perty"          ;

    /**
     * I dentifi     es that grou     p me    mber sh         ip has         changed.  
     */
    public s         tat   ic     final String MEMBER_OF_PROPE RTY = "memberOfPro       per  t     y";

       /**
     * Identifies       that       the controlle r f        or t     he                   target obje   ct has          c    ha     nged
           */
    pub    l   ic static   final Strin    g CONTROLL    ER_       FOR_PRO        PE   RTY =    "controlle     rForProp   erty"    ;

      /*   *
     * Identifies that the target ob  ject tha       t is doi    ng the co ntrolling has
     * changed
           */
    public static final Strin      g CON        TROLLED_BY_ P ROPERT    Y = "cont  r  olledByPr  oper    ty  ";

    /        *     *      
             * Indi   cates th    e FLOWS_TO relation           betw      een  two obj                   ects
     * has         changed.
             *
     * @s    in   ce             1.5
     */
           p      ublic static fi    nal S   tring FL OWS_TO_         P  RO  PERTY      = "fl owsToP  roperty";      

    /**       
     * Indic ates the FLOWS_FR  OM r          elation be      t    ween     two objects
     * has c     hanged.
       *
      * @s    ince        1.5
     */
    public st  at   ic  final          Str   ing FLOWS_FROM_PROPERTY = "flowsFr   omPro      perty";
 
    /**
     * I   n    dica   tes th     e    SUBWINDOW_O   F rel ation   bet   ween two  or more    ob   jects
             *    has chan    ged.
         *
     * @since 1.5
     */
       public     static fina    l String SUBWINDOW     _OF_PROPE        RTY =    "subwindowOfProperty"     ;

       /       **
     *     Indicates t          h  e PAREN    T  _WIN   DOW   _O   F relati   on        be      t         we   en   two    or mo  re objects
       * has c   hanged.
                *
     *    @si   nce            1.5
             */       
    public static              fin al S     tring PARENT_WIND  OW_OF_PROPERTY = "pa     rentWindowOfProperty";

           /**
             * In  dicates the  EMBEDS relat   ion   be                 tween two or   more obj   ects
       * has     changed.
     *
               * @since    1.5
       */
    public stat ic   fina l String EMBEDS_P   ROPERTY = "embedsPrope     r    ty";

    /**
     * Indica     tes the EMBEDDED_BY relat  ion    b   e                t     ween two or mo   re    obj  ec         t       s 
     *      has           ch  anged.
     *
            * @  since 1.5
            */
    pu         b   lic     s    tatic fi    n    a   l String EMBEDD  ED_B  Y_PROPER   TY = "emb  edded   ByProper    t    y";

    /**
     *  In    dicates the CHILD_NOD   E_OF     relation betw  een two or more objects
     * has c    hanged.
          *
         * @since 1.5
     */
       public static final   S  trin g C    HILD_NODE_OF_PROPERTY    = "c     h    il    dNodeOfPrope  rty";

       /**
          *   Cre    ate a       new A c  cessibleRelation using the given   locale independent key.
     * The k  ey String should be      a locale independent key for the relation.
          * It is not inte   nded t   o  be used       as the actual String to di  splay
     * to the user    .  To g  et t    h e localized string, use toDisplayString.
     *
         * @par  a     m key the loc   a       le independ   ent name of the relation.
     * @s     e e       AccessibleBundle#toDisplayStr   i    ng
       */
    public Ac    cess           ibleRelati    on( String key)         {
                 this.   key      = k     ey;
           this.target = nu  ll;
    }

    /**   
     * Creates a new    Accessib     leRelation us ing the given local  e in     depe    n dent key.
     * The key Stri  ng should                 be a locale   independent key fo     r the relation.
     * It is not intended to       be used as the actual String to display
     *  to the user.  To    get the lo   calized string, use toDisplayString.
         *
     * @param key the loc    ale      independent name    of         the relat ion     .
     * @para  m target   the     t  arget obje  ct for th   is relation
     * @see Acces        sibleBundle#    toDisplayString  
     */
    public Accessibl     eRelati     o     n(String key, Object             targe             t) {       
        this.key = ke    y;
        this.ta   rget = new    Object[1];      
          this.target[0] = tar   get;
           }

    /**
     * Creates            a new Accessib  leRelation using the given  locale independent key  .
        * T he key Str   ing should be a locale i  nde pendent key for the rela t    ion.
     * It is     not i ntended to be used as the actual String to display
     * to t    he user.  To ge  t th   e localiz  ed   string, use toDisplayString.
     *
     * @param k ey the locale independent name of the relation.
     * @param targ    et the target object(s) for t         his       relation
      * @   see AccessibleBundle#toDisplayStrin   g
     */
    public Acc   essibleRelation(S    tring key, Object [] target) {
         this.k    ey = ke      y;
            this.targ   et   = target;  
    }

    /**
     * Returns the key for th    is r   elation
     *
     * @return the key for this relation
     *
     * @see #CONT   ROLLER_F  OR
     *      @see #CONTROLLED_BY
        * @see #LABEL_FOR
     * @see #L ABELED_BY
     *     @see #        MEMBER_OF
            */
     publi c String getKey() {
          return this.ke   y;
    }

    /**
        * Returns the target objects for this    relation
      *  
        * @return an array containing the target objects fo r this re  lation
     */
    public Object [] getTarget() {
        if (tar   get == null) {
            target = new Object[0];
        }
               Object [] retval = new Obje ct[target.length];
        for (int i = 0; i < target.length    ; i++) {
            retval[i] = target[i];    
        }
        return retval;
    }

    /**
     * Sets the target object for this relation
     *
     * @param target the target object for this relation
     */
    public void setTarget(Object target) {
        this.target = new Object[1];
        this.target[0] = target;
    }

    /**
     * Sets the target objects for this relation
     *
     * @param target an array containing the target objects for this relation
     */
    public void setTarget(Object [] target) {
        this.target = target;
    }
}
