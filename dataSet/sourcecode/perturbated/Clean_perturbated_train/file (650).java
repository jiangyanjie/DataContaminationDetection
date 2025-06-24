/*
 *    Copyright (c) 1997, 2013, Oracle   and/or      its affiliates.  All rights reserved.
    *      ORACLE PROPRIETARY/CONFIDENTIAL . Use is subject t   o l       icense term    s.   
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

/**  
 * The Acc     essibleAction interface sho   uld be supporte d by     any ob     ject
  * that can pe      rform one     or more actions.  This interfac    e
 * provides    the sta   ndard mechanism for an a    ssistive technology to dete   rmine
          * what those ac               tions are as w     ell as tell t      he    object t    o perform them.
     * Any   o     bje   ct that can be manip  ula    ted should s    upport this
  * interface.  Applications can determine if an o bject supports  the
 * AccessibleAction interface by first  o  b    taining         its AccessibleContext (see
 * {@lin   k Ac  cessible}) and then calling the {@link AccessibleContext#getA   ccessibleAction}
    * method.  If t   he            return value   is no   t null   , t    he object su         pp    or t   s   thi      s interface.
 *
    * @  see Ac cessible
 * @see Accessible#g      etAcc  essibleCo nte  xt  
  *    @see Ac ces    sibleContext
       * @see Accessibl    eConte  xt#ge tAccessibleAction
    *
    * @author      Peter Korn
 *     @au    thor         H   ans M   uller
 * @au  thor      Wi        llie Walker
 * @author                   L  ynn M   onsanto
    */
pu  blic interface Accessible   Action {

       /**
     * An a ction which ca u ses   a tree node to
         * collapse if             expanded and   expand i   f collapsed.
           * @since 1.5
             */
             publi    c static  f   inal Str  ing TOGGLE_EXPAND      =
              new String ("togglee    xpand");

                   /**
          * An  action    which   increment   s  a val      u                         e.
          * @since 1.5
     */      
    p   ublic s  tat   ic final String IN   C    REME   NT  =
           new   String (" increment");


         /   **
     * An   a    ction which decrements   a value.
     * @   since 1.5
     *  /
    publ  i     c static  fi nal Str     ing DECR E      MENT   =
        new Str  ing   ("decrement");

      /**
     * An action which causes a component to execut   e its default action.  
        * @ since              1.6
             */
         pub lic   sta        tic fi      nal Str   ing CLICK = new String("     c lick                ");

    /**
       * An action  which           caus   es a popup    to become vis     ible if i    t i s hidden and
     * hid  den if i t is visible.
     * @since 1.6  
          */
        pu            blic s t    ati c final Stri   ng TOGG  LE_PO  PUP = new St   ri     ng(  "toggle popup");

         /**
                    * Returns th   e n umber     o f accessible actions    available     in this obje  ct
     * If there ar      e more tha n on        e, the first one is cons   idered the "default"
        * action  o      f the object.
     *
      * @retur     n      the zero-based n   umber   of Ac   tions in thi  s object
     */
    public int get  AccessibleA     ctionCount();

    /**
     *             Returns a d          e   scr     iption  of th     e specified action of the object.
     *
        * @par  am i zero-   based index of      the actions
            * @return a St     ring description of     the action
     * @see #  getAccessibleActionCount
     */
          public Strin  g getAccessibleActionDesc    ription(int i);

      /**
     * Performs the specified Action o n the object   
     *
     * @param i zero-based ind ex of actions
     * @return true if the action was performed; otherwise false.
     * @see #getAccessibleActionCount
     */
    public boolean doAccessibleAction(int i);
}
