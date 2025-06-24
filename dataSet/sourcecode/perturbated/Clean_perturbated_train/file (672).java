/*
    * Copyright (c)      2003, 2006, Oracle an  d/or its affiliates.       All rig    hts reserved.
   * ORACLE PROPRIETARY/CONFIDENTIA        L. Use is subje     ct to license     ter          ms.
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

import java.io.InputStream;
import java.  awt.datatransfer.Dat     aFlavor;

/**
     *     
 * The <code>Access ibleStreamable<       /code> interface should be implem  ented
 * by t he <code>AccessibleContext</code>    of a      ny compo    nent tha t presents t    he
 * raw str   eam   behind a component on th e  dis  play    screen.      Examples of such
 * comp  onents are HTML, bit  map images and MathML.  An obj    ect that implements
     *      <code>AccessibleStreamable</ code> provi    de  s two    thin  g  s  : a        lis    t of MIME
   * types supported by         the object and a streaming interf  ace for each MIME type to
 * ge t the data.
 *
 * @author Lynn Monsant    o
 * @aut hor Peter    K orn
   *
 *     @see ja     vax.acc essibil   ity.Accessibl   eContext
 * @s  in   ce 1     .5
 */
pub   lic      interface AccessibleStream    able {  
                          /**
      *    Returns an array of     Da taFlavo r   objects fo  r the M  IME types
         * this obj    ec      t supports.
      *
         * @return an array of   DataFlavor   ob  jects f      or the  MIME typ   e      s
      *     this object su      p      po  rts.
      */
     D    ataFlavor[] getMim  eTypes();

                  /**
                       * R       etur  ns a  n   InputStream for a DataFlavo    r
             *
      * @param flavor the DataFlavo     r
          *     @return an ImputStream if an ImputStream   for this DataFlavor exists.
      * Otherwise, null is returned.
      */
     InputStream getStream(DataFlavor flavor);
}
