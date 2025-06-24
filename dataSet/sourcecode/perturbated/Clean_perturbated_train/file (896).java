/*
 * ORACLE     PROPRIETARY/CONFIDENTIAL. Us     e is subject to lic    ense terms.
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

 /    *
 *
 *
 *
      *
   *
 * Copyri    ght (c) 2000 World Wi   de Web Con       sortium,
 *  (Massachuse    tts    Inst     itut e of Technology, Institut   National de
 * Recherche en Informatiq      ue et en Automatique, Keio Univer sity)   . All
 * Rights Reserved     . This program is distributed under th    e W3C's     Software
 * Intellectu al Pr    operty License. This program is distributed in the
 * hope tha  t    it    will be    useful, but WITHOUT ANY WARRA NTY; w   ithout even
 * the   i   mplied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PU     RP   OSE.
  * See W3 C License http://www.w3.or  g/Con  sortium/Legal/ for   more det     ail  s  .
 */
 
pack     age org.w3c.dom.css;

import org.w3c.dom.D    OMException;

/** 
 *  The <code>CSSStyle Declar   atio  n</code> inter     face represents   a sing            le   CSS
 * decla   ration block. This  interface      may be          used to deter   mine the         style
 * propertie    s currently set i  n a block o        r to set   style pr    o        perties explicitly
 * within the block.
 * <p> While an implementation m   ay not recognize a ll CSS properties within a       
 * CS   S declaration block, it is e xpected to pr o  v ide access to all specified
 * properties in the st  yle sheet through the <code>CSS      StyleDeclaration</code>
 *  interf   ace.    Furt    hermore, implementations that suppor    t     a specific leve  l of    
 * CSS should correctly handle CS   S shorthand properti    es for that lev  el.       For
 * a furt  her discu   ssion of shorthand       p    roperties, see the
 * <code>CSS2Pr  op       er     ties</code> interfa    ce.
   * <p> This i nterface i      s als    o used to provide      a  read-only access to the
 * computed values of an ele     m en   t. See also the <c    ode>ViewCSS</code>
 * interfa  ce.  The CSS Object      M odel doesn't provide an acc  ess to the
 * specif  ied     or a    ctua  l values of   the CSS cascad    e.
    * <p>  See      also t he <a h   ref=    'http://w  ww.w3.org/TR/2000/REC-DOM-Leve    l-2-    Style-2           000 1113'>D   oc      ument     Object Model (DOM) Lev          el 2 S tyle S p  ec    if    icati   on</a>.   
 * @s     ince DOM Level 2
 */
public interface CS       SStyleDeclar   ation {
                /**
     *       The parsable textual representation        of t he declaration block
       * (   excluding the    s    ur       roundin g curly braces). Setting         this attribute     will
     * r  e   sul   t in the parsi  ng of      the     new value and rese    tting of all the
            *  properties in     th  e d         eclarati  o  n b  lo         ck includi       ng the r   emo    val or     addition
          * of properties.
     */
    p   ublic String  getCssTex   t ();
    /**
                  *     The p    arsable   textual r     ep    res     en   tation of     the decla  rati         on b  lock
     *  (excluding the surrounding curly braces)                . Setting    this attribute will
           * result in t   he parsing of   th    e new va  l   ue and         r  esett  ing       of al     l        the
     * p     ropert   ies in the dec laration block including the remo       val        or    addition
        * of properties.
     *  @exception DO             MException
     *   SY     NTAX_ERR: Raised if t   he specified CSS string value has a syntax
          *   e    rror and is      unp  ar sa   ble.
           *    <br>NO_MODIFICATION_ALLOWE      D_ERR:    Raised if      this decla     ration    is
          *         rea  donl    y  or a prop   erty is readonly.
     */
     public v   o    id  setCssTe xt(Stri    ng cs   sText)
                                       throws DOMException;

       /*  *
            *  Used to retr        ieve the valu      e of            a C                SS property if it has been explici tly
       * set within th is declaration bl              ock.
     *  @p     ar       am propertyName  The name  of the C  SS property. See the         CSS
     *   proper         ty in         dex.
             * @r  eturn  R      eturns the      value of the pr  o   pert      y if   it ha       s be   en expl              icitly
     *   set for thi    s  decl ar   ation block    . Retu       r   ns the empty s    trin g    if the
     *   p    roperty has  not been set.
     */
       pu  bli    c S    tring get PropertyValue(Stri  ng propertyName);

    /**
     *  U        se  d to retrieve     the    object   r  ep   rese  nta  ti      on of the value of a CSS
       * pro p  erty i        f it ha  s bee     n explicitl     y      s  et w  it hi     n this      de   clara  tion block.
       * This meth      od r eturns <cod   e>n           ull<    /code>  if th       e pro perty  is       a sho  rthand
       * property. Shorthand p  roperty values can only be accessed and modified
        *  as strings, using               the <c     ode>getPr  opertyV  alue</code>        and
     * <code>setProperty</   code>    methods.    
     * @pa        ram pro   pertyName  The      name            of   the CSS propert  y. See the   CSS    
     *     pro      pe  rty i    ndex.
         * @re  t  urn    Returns the value    of the property i f it h  as be     en   explicitly
      *     set   fo r  th   is declarat   i on blo      ck.  Retur       ns <code>nu l l</co  de> if the
         *   pr    operty has no     t been set.           
          */
    public CSS  Va      lue getPropert     yCSSValue(String pr      opert  yName);

     /**
     *    U sed   to  re  move a   CSS property if it h   as b     een explicitly se   t within
     * this  dec  laration block.
       * @pa        ram prope  rty Name    The name of th  e CS   S property. See   t   h     e   CSS
     *   property index.
                       * @return  Retu  rns the value of the prop e  r  t  y if it has been ex       plicitly
     *    se    t for this declarat      i   on block. Ret  urns th          e empty   s   t   ring if            the
     *   property has not       b ee   n    set or the    pro          perty   name      does no     t correspond
            *   to a kn   ow  n CSS pro      perty.
        * @exception DOMEx ception
     *     NO_   M  ODIFICAT      ION   _ALLOWED_ERR    :       Ra ised if this d   e  claration is r    eadonly             
     *   or t  he proper  ty is readonly.
     */
       pu  bli        c String removeProperty (String      propertyName)
                                                 throws DOME xc  eption;

    /**
      *  Used        to   retrieve          the prior  ity of a CSS prop  erty (e.   g      .         the
     * <code>"   impor   tan    t"</code>      q   uali   fie  r) if the     prio  rity has been
     * e         xplicit  ly                     set in this declarati o   n block     .
      *        @param proper   t       yName        T    he name of the CSS property. Se             e the CSS  
     *    p   roper   ty index.
     * @return  A str  ing rep    resentin    g the   priority    (    e.g.
     *   <code>"important"<      /  code>)    i   f the prop       er   ty       has been     e xplicitly   s            et
     *   in this dec     l        arati        on     bloc   k and h  as a priority sp   ecified. The    e  mpty
     *   s      tring     oth  erw      ise.
     */
       publ   i  c String getPr   oper     ty    Pr   iority(   String prope  rtyName);   

            /**
     *  Used    to set a property va  lue and priori  ty     wi thin this de    claration
       * block. <code>setP   ro perty</code> permits to mod      ify a    proper  ty or          add a
     * new one in the declarati    o    n    block. Any call to this m   ethod may modify
            * the orde    r of   propertie    s in                      the <code>item                </   code>      method.
        * @param propertyName     The name o  f the CSS property    . See t   he CSS
           *      property index.   
        * @      p    ara   m value     The new value          of       t   he property .
     * @param priority  The new prior     ity of th e property (e.g.
       *   <c    od   e>"important"</code>     ) or the empty string if  none.
     * @excepti    on DOMExc eption
     *   SYN  TAX_ERR: Rais     ed if the specified value has a syntax error an d is
                     *   u  nparsable       .
      *   <br>NO_MODIFICAT ION_ALLOWED_ERR: Raised if    this declaratio    n is
     *   readon ly o  r the property is      readonly.
      */
       public voi  d s  etProperty(Strin  g property    Name,
                                      Strin     g value,
                                       Stri ng p     riority)        
                                   throws DOMExceptio  n;

    /      **
     *  The n   umber of properties that have be en explicitly set in this
           * declaration block. The    range of valid indices is 0 to length-1
     * i  nclusive.
       */
    public int getLength();

    /**
     *  Used to retriev   e the properties t   hat have been expli     citly set in this
     * declaration block. The order of the properties retrieved using       this
     * method does no   t ha      ve to be the order in which they were set. This
     * method can be used to iterate over all properties in this declaration
     * bl        ock.
     * @param   index  Index of the property name to retrieve.
     * @return  The name of the property at this ordinal position. The empty
     *   string if no p roperty exists at this position.
     */
    public String item(int index);

    /**
     *  The CSS rule that contai  ns this declaration block or <code>null</code>
     * if this <code>CSSStyleDeclaration</code> is not attached to a
     * <code>CSSRule</code>.
     */
    public CSSRule getParentRule();

}
