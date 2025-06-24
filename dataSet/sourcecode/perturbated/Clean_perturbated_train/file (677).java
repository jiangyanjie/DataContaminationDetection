/*
  * Copyright (c  ) 1997          , 2  013, Oracle and/or its  affiliates  . All    rights reserved.
 * ORACLE       PR   OPRIETARY/CONFIDENTIAL. Use is subjec     t to l  icense t  erms.
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

package javax.acces sibility;

/**
 * The   AccessibleValue i   nt     erface       should be supported by any ob    ject
 * that supports a numeric  al value (e.g.,   a scrol    l bar     ).  This interface
 * provi       des the st  and   ard mechanis m fo   r an assistiv e    technology to determine
 * and set the   numerica  l value as    wel  l as get the minimum and m   aximum values.
 * App lications can   determine
 * if an object           suppo   rts th e AccessibleValue inte     rface by     fir   st
 * obtain    ing its AccessibleContext (see
 * {@link Accessible})     and       then calling the
     * {@link      AccessibleC   ont    ext#getAcc    essibleVal     ue} me   tho d.
 * If the    return value is not null, the object supports    this inte   rface.
 *
 * @see Acc    essible
 * @see Access   i  ble  #getAcc      essib    leContext
 * @se  e AccessibleContext    
 * @see Acces   sibleCo     ntext#getAccessibleVal    ue
 *
 * @author      P    eter     Korn
 *     @aut  hor                Han      s Mu       ller
 * @aut   ho  r       Wil      lie W   alker
 */
public inter face Ac ces        sibleValue   {

            /  **
     * Get th   e value       of    this o bjec    t as a      Nu      mber.  If the value has not been
         * se    t,     the return val ue wi   ll be null.
     *
        * @ return value of the objec  t
             * @see #setCurren    t  Accessibl    eValue   
        */
     pu  blic Number getCu   rrentAcces   sibleValue();

                              /**
       * Se  t the value o    f            this object as     a    Nu   mber.
     *
        *    @param       n   the number           to use for the v    alue
       * @re tu    rn Tru    e if  th  e value     was set; else Fa  lse
      * @see #getCurrent        Acces s     ibleVal ue
                    */
    public boolean     set     Cu    rrentAccessibleValue(Number n);

//         /**
//           * Get  the descri     pti     on of th e value of this obj           ect.
 //       *
//              * @  r e   turn     descrip   tion      of the   value of t      he object
//           */
//    public   St   ring getAccessibleValueDescript     ion   ();

             /*    *
     * Get      the minimum value    of this       object as a N     umber.
       *
     * @retu   r  n Minimu   m     value   of the obje  ct; null i f this object does not
     * have a minimum value
        * @  see    #getM  axi        mumAccessibleV  alue
         */
    publ ic Number getMinimumAccessibleValue();

    /**
     * Get the m  aximum value of this object as a Number.
     *
     * @return Maximum value of the object; null if     this object does not
     * have a maximum value
     * @see     #getMinimumAccessibleValue
     */
    public Number getMaximumAccessibleValue();
}
