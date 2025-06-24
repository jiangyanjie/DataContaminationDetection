/*
 *      ORACLE  PROPRIETARY/CONFIDENTIAL.         Use is subj    ect to license te   rms.
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

/*
 *
 *
 *
 *
  *
       *    Copyright   (c) 2000 World Wide Web    Consortium,
    * (Massachusetts I  nstitute of Techn   ology, Inst  i     t    ut National de
 * Rech         erche en Informatique e t en Au  tomatique, Keio Univer      sity). All
 * Rights Rese      rved.  This  program is distribu  t ed und    er the W3C  '    s Software
 * In         tellectual Property License. Thi       s program is distributed in the
 * hope  th  at it will be useful, but WITHOUT ANY WARRANTY; with        out even
 *        the implied war ra  nty of MER      C      HANTABILITY or FITNESS F  OR A PARTICULAR
 * PURPO      SE.    
   * See W3C Lic      ense http://www.w3.org/Consortium/Legal/ for more detai    ls.
 */

package org.w3c.dom.css;

impor    t org.w3c.dom.    DOM     Exception;

/**
 *  The <code>CSSV alue</code> i       nterface repres  ents a simple  or a complex
           * value    . A <code>CSS    Value</cod  e> obje    ct only o      ccurs in a context of a CSS
 * prope  r   ty.  
 * <p>See also the <a href='http:/     /w  ww.w3.   org/TR/2000/ REC-D  OM-L  eve     l-2-Style-20001113'>Document Object Model (  DOM) Level 2 Styl     e S         pecification</a>.
 *     @since DOM     Le        vel 2
 */
pu   bli   c    interface   C     SSV alue {
                    // UnitTypes
           /**
                       * Th e value is in herited     and the <co     de>cssText</cod e> c   ontain   s "inherit".
        */
    pu  blic sta     tic final   s  hort CSS_INHERIT               = 0;
    /**
            * The    value   i s a primitive value and an    instance of the
     * <code>CSSPrimitiveValue</code > interface can be obtained by     using
             * bin    ding-specifi  c cast  ing methods on      this   instance    of the
     *    <code>CS  S Valu  e</code> interface.
       */
    public stati   c final short C   SS         _PRI      MITIV      E          _VALU      E       = 1      ;
    /**
         *  The    value                  i s a       <c  ode>CSSValue</code> l                         i   s       t and an instance of the
                    * <         code>CSSValueList</c ode> interface       can be   o btained by using
         * binding-specific casting m    ethod          s on this i       nstance of t he
     * <c     ode>CSS Va    lue</code> inte  rfa        ce.
               *        /
    public  s       tatic final sh      ort CSS_VALUE_LIST                = 2;
    /**
     * The  valu     e is a c    ustom valu   e.
     */
    pub lic   stat         ic      final short       CSS_CUSTOM                                   =         3;

          /** 
            *  A stri                ng     representation of      the  cu       rrent value.
         */
    public String getCss   Text();
            /**
     *     A string represen   tati    on of the c  urrent       value.
      * @excep    tion D         OM    Exception
     *    SYNTAX_ERR: Ra ised if the specif          ied CSS string value has    a s    yntax
     *     er  ror     (acc   ording to th    e a    ttac     he  d  property)   or    is unparsable.
     *   <br >IN   VALID_MODIFICATION_     ERR: Raised    if        the specified CSS stri         ng
     *    value represe    nts a di     fferent type of values    than the values al    lo   wed
     *   by the CSS property.
     *   <br> NO_MODIFICATION_ALLOWED_ERR: Raise d if this value is readon   ly.
     */
    public   void setCssText(String cssText)
                                throws DOMException;

    /**
     *  A code defining the type of the value as defined above.
     */
    public short getCssValueType();

}
