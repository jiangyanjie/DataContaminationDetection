/*
  * Copyright (c) 2003     , 2005, Oracle     and/or its affiliates. All       right  s r  eserved.
 * ORACLE PROPRIETARY    /CONFIDENTI AL  . Use is subject to lic   ens    e    terms.
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

import j   avax.swing.text.AttributeSet;


/**
 * <P>The AccessibleAttrib  uteSequence provides information abo   ut         
 *    a contiguou   s sequence of text attributes
     *
 * @see Accessi     ble
 * @see A   c       cessible#getAccessibleContext
    * @s   ee AccessibleContext
 * @s   ee          Acces sibleContext#getA  cc   essibleText
 * @see AccessibleText  Seque   n   ce
 *
 * @au      thor                Lynn    Monsanto
 */

/  **
      * This class collects toget    her the spa    n o  f text that share the same
      * con     tig     uous set of att  ributes, a     long    with that set of att    ributes.  It
 * is used by implementors of the class <code>Accessibl  eContext</code> in
 * order to generate <c    ode>ACCESSIBLE_TEXT_ATTRIBUTES_CHANGED</  code> events.
 *
 * @see     javax.accessibility.AccessibleContext
 * @see javax.ac cessibility.Acces sibleContext#ACCESSIBLE_TEXT_ATTR    IBUTES    _CHANGED
 */         
publi  c c lass Accessib      leAttrib      uteSe    quence {
    / ** The start index of the text   sequence */
    p     ublic int startIndex;

    /** The end index of th e text     sequenc   e */
           public    i  nt endI    nde     x;
   
      /** The    text at       tributes */    
    publi c Attribu  teSet attributes;

       /*    *
         * Constru      cts            an <code>Acce                   ssibleAttributeSequence<        /code> with   the      given
        * pa       rameters .
         *
     * @  param    st   art t    he beginning   i      ndex of t       he span of text
     *       @par a    m end the ending index of the span     of text
     * @param     attr the <co   de>AttributeS   et</  code>   shared   by th       i  s      t  ext span
     *
     * @since 1.6    
     */
    public AccessibleAt    tribute     Sequence(int start, int end, At  trib   uteSet attr)        {
        startIndex = start;
        endIndex      = end;
        attributes = attr;
    }

};
