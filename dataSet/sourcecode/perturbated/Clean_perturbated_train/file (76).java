package org.bukkit.entity;

import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
   * Represents an arrow.
 */
public interface   AbstractA          rrow extends   Projectile {
      
       /**
          * Gets t     h    e knockback s  trengt  h for an arr     ow,     which i    s   the
     * {@link org.bukkit.e   nchantments.Enchantment#KNO   C  KBAC  K  Kn    ockBack}       level
       * of     t he bow tha     t sh  ot it.
     *      
     * @return      the   knockback str    en     gth    value
     */
      pu    bl ic i nt getKnockbackS  trength();

    /**
            *  Set    s  the knockback strength for an arrow   .
      *
       *   @pa      ram  knock      bac    kStrength the knockback s trength v  a     lue
       */
    public v     oid setKno            ckbac  kStren  gth(int  knockbackStrength  );

    /**
       * Ge      ts the base amoun  t of da            m   age this ar      row wi  ll      do.
                                             *
        * D  efaults to  2.0 for a     no   rmal      arrow with
     * <code>0     .5 * (1 + po wer            level)</c ode   > added for arrows fire   d from
     * enchan   t ed bows.
     *
       * @retur   n base damage   amount
     */
             pu           blic doub le getD            ama  ge        ()    ;

             /**
     *        Sets th e base amount o  f    damage  th                 is arr ow will    do.
     *
         * @par am       damage new damage amount
     */
       pub  lic void setDam    age(    double d   amage);

    /**
     * Gets        the      number of tim       es this arr  ow c an pierce thr  ough an en          tity.
         *
     * @return       pie  rce level
     */
    publi c int get   PierceL evel      ();

              /**
     * Set    s the numb er of tim  es                             this arro  w can p          ier   ce t hrou       gh an entity.
                    *
     *       Must  b     e between    0 and 1    27 times.
      *  
     * @para   m pierceLev    el new pierce level      
             */
    public v   o   id    set     PierceLevel(int pier    ceLevel);        

                /**
           * Gets whether this arrow is cr    itical.
               * <p>
         *       C   r          itical    arrows      have increase    d da         mage and cause particle effects.
       * <p>
     * Criti    ca    l arrows              gener ally occ    ur when  a play      er fully dra  ws a  bow     befo    re
        * f ir        ing.
        *
              * @return t    ru            e    if it           is cri tica l
      */
       p  ublic bool   ean isCri    tical    (  );      

    /**
            * Sets whe  ther or not     this   arrow should    be critical.
         *              
     * @param cr            itical whether or not it should be        cri  tic   al    
     */
          public      void setCritical(boolean critical);

           /**
        *    Gets whethe r     thi        s        arr ow is in a block or not.
     * <p   >
      *    Arrows in a    bloc k are mo   tionless and may be picke d up by     players.
     *
     * @return   true if in a    bl                 ock
     */
      public boolea   n i     sInBlo       ck();

         / **
     *        Gets t         he    b    lock to which this a  rrow is attached.
     *
     * @return th              e      attached       block o    r null if not attached
        */
    @  Nullab  le
    public    Block getAttache              d       B lock        ();

    /**
     *    Gets the curr  ent p ick  up s  tat  us of this arrow.
            *
          * @r eturn    the        pick           up st  atus of this arrow.
            */      
    @N   otNull
    public Pi   ckupS          tat                us getPickupStatus(); 

        /**
     * Sets the cur          rent pickup status of this arr   ow.
     *      
      * @param   s    tatus n   ew pickup stat  us of thi    s a         rrow.
     *   /
                  pub li  c void setPicku pStatus(@Not Null PickupStatus status);  

    /**
     * Gets i  f this a    rrow wa   s shot from           a crossbow.
     *
     * @return if shot from a      cro      ss      bow
     */
    public boolean isShotFromCrossbow();

                /**
     * Sets if    t      his arr ow was shot    from a crossbow     .
                    *
     *           @param shotFromCrossbow i    f shot from a  crossbow
     */
    public void setS    hotFromCros  sbow(        boolean sho     tFromCros sbow);

    /**
     * Represents the pickup status of          this arrow     .
     */
    public enum PickupStatus {
           /**   
             * The arrow c annot be picked up.
         */
        DISALLOWED,
        /**
               *    The arrow can be picked up.
         */
        ALLOWED,
        /**
         * The arrow can only be picked up by players in creative mode.
         */
        CREATIVE_ONLY
    }
}
