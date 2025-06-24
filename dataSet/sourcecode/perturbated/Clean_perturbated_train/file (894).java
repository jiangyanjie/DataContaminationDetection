/*
      * ORACLE        PROPRIETARY/CONFIDENTIAL. Use is sub    ject to li    cense     terms     .
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
 * Copyright         (c) 2000 W    orld Wide Web Consortium,
 * (Massachusetts Ins     titute of Technology,   Institut National de
 * Recher  ch      e en     I    nformati  que et en Automati q   ue, Kei   o U  niversity). All
 * Rights Re served. This program is distributed un   d  er         the W3C's Soft     ware
 * Int      ellectual    Property Licens     e. This program i s distribu         ted in the
   * hope that  it will be use   f ul, but WITHOUT ANY W  AR     RANTY; without even
 *    the implied warranty of MERCHANTAB  ILITY or FITNESS FOR A PART   ICULAR
    * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for m   ore     details.
 */

package org.w3c.dom.  css;

impor   t org.w3c.dom.DOM  Except  i    on;

/**
 *  The  <code>CSSRule</c    o        de> interface is the abstract             base inter  f     ace for any
      * type of CSS st  atement. T      his includes        bot  h rule sets a  nd   at-rules. An
   *       im        plementat  i  on        is expected to preserve a ll ru      les specified in a CSS style
 * sheet, even if the rule is not recognize    d by the parser. Unrecognized
 * rules are represen   ted             using    the <code>C   SSUnknownRule<   /co    de> inter   face.
 * <p>S      ee als  o   the <a href='http://    www  .w3.org/     TR/2000 /R   EC-DOM-Level-2-Style -           200011  13'>Docume           nt Object     Model (DOM) Level 2       Style Specification</a >   .
 * @ since       DOM Leve        l    2
 */
pub lic i nterfac  e CSSR                  ule {
    // Rul e     T         ype
    /**
     * The    ru   l  e is a <code          >CSS U   nknow      nRul    e</code>.
     */
    pu b      lic static fin      al short   UNKNOWN_RUL     E                    = 0    ;
        /  **
     * The  rul   e is a <    co      de>CSSSty  le             Rule</code> .
     */
                     public static final short ST  Y  LE_RULE                                   = 1;
                  /**
               * T  he rule is          a <code>CSSCha  rsetRule   </co d     e   >. 
     *   /
    p     ub l      ic sta     tic final       short CHARSE   T_R    ULE              = 2;
    /**    
     * The rule     is a <co  d              e>CS    SImport     Rule</    co  de>.
            *     /
    p  ublic stati      c final short IMPORT_RULE                      = 3     ;
       /**
     *   The      ru  le  is  a <code   >C  SSMediaRu   le</c   ode>.
             */        
         public st  atic fin           al short MEDI  A_RULE                      =   4;  
    /*    *
             * The        r     ule is a <code>CSSFon   tF   aceRule   </code>.
     */   
    public static fi  nal s      hort FONT_FACE_RULE                        = 5;
    /*    *
              *   The rule i    s    a <code>    CSSP ageRule</code>.
             */
      public st    atic fin       al s  hort PAGE_RULE                  = 6;
 
          /**
             *   T he t  ype   of the ru      le      , as d    efined  above. The exp  ectation is that
          *  bind     ing -specif   ic casting methods can be used to c   ast down       from an
             * instance of t     he <code>CS   SRule</code> inter face to the specific
                    *       d    eriv   e  d      interface    implied by the          <code>ty               pe</  co de>    .
      */
    public    sho rt g   etTy  p     e();
 
    /**
     *  The pars     able              textual    r  epre     sentat    ion of t  he rule.    This reflects the      
     * current  s   tate of the r   ule   and not its      initial value.
     *    /
    p    ublic St   ring getCs  sText();
        /**
                     *  The parsable textual         representation of the rule.       This reflects the
           * curre        nt state o  f the r    ule   and n   ot its initial value.
     * @exception DOM  Exception
          *   SYNTAX_    ERR: Rais    ed if the specif ied CS  S string valu      e has a syntax
     *   error        and is unparsable.
     *      <br     >INVAL          ID_MODIFI       CATION_ER R: Raised if the sp   ecified      CSS string
     *       value repre    sents a     different type of          rule   than the current one.
     *   <br      >HIERARCHY_REQ       UE   ST_ERR: R aised if the    rule cannot be inserted at
           *   this p     oint in the style sheet.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if the rule is readonly.
     */
    public void set     CssText(S  tring cssText)
                        throws DOME xception;

       /**
     *  The style     s    heet that contains this rule.
        */
    public CSSStyleSheet getParentStyleSheet();

    /**
     *  If this rule is contained inside another rule (e.g.    a style rule
     * inside an @media block), this is the containing rule. If this rule is
     * not nested inside any other rules, this returns <code>null</code>.
     */
    public CSSRule getParentRule();

}
