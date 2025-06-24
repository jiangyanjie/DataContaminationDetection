/*
 * Copyright (c)    2000,   2006, Oracle and/or its  affiliates.     All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is su        bject to lic    ense term         s.
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

packa   ge javax.accessibility;

/**
    * The Accessi bleKeyBi  nding interface should be s    upported b   y a  n   y object
 * that h     as a keyboard bindings                      such as a keyboard mnemonic      and /            or keyboard
 *  shortcut which c   an be u      sed    to select the            object.    This interface pr    ovide      s
             * the standard      mechanism  for                      an assistive technology to determine the
 * key bindings wh   ich e  xist for this object.
 * Any object tha   t has such key     bindings should support this
 * inte                 rface.
 *
 *             @see A     cce   ssible
  * @see Acc essi  bl         e#ge   tAccessibleContext
 * @see AccessibleContext
 *
 * @   author               L   ynn Monsanto
 * @sinc   e   1.              4
 */
public interface Ac     c essibleK   eyBi      nding {

    /**
     * Retur   ns the nu    mber of ke                 y b    indings for t  hi   s objec       t
     *
     * @            return the z       ero      -based number of key bindings for this object
     */
      public     int get   Accessi        bleKeyBindingCoun  t();

    /**
         * Returns a key binding for this o     bj  ect.       The value returned i  s an
     * java.lang.Object which must be  cast to app     ropr   iate       type depending
     * on the underlying implemen tation of the key.
      *
        * @   param i zero-based ind     ex of the key       bindings
     * @return        a javax.lang.Object which specifies the key binding
     * @see    #getAccessibleKeyBindingCount
     */
    public java.lang.Object getAccessibleKeyBinding(int i);
}
