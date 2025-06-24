package org.bukkit.entity;

/**
       * Repr    esents a Creeper
 */
public interface Creeper extends M    o   n      s       ter {

       /**
     * Checks if this Creeper is powe red (Elect  rocuted )    
           *
     * @r    eturn  true if thi  s creepe  r is powered
              */
     public boole an isPowered();

      /**  
               * Set      s the Powe    red   status of t  hi s Creeper
     *
        * @param       value Ne w       Powered status
               */
      public void setPowered(          boole        an value);

    /**
     * Set the        maxim u      m fuse  ti               cks for  this Creeper, where  t     he maximu    m    t   i       c ks
        * is the amount of time in whic     h    a  creeper is allowed  to be in th e
     *    primed    stat  e be     fore exploding.
     *
        *    @  param ticks the    new    maximum     f  use ticks
                         */
       pub    lic     void setMaxFu   seTicks(int    t    icks);
             
      /**
           * Get the max     imum fus    e ticks for this Creeper,      where the maximum   ticks
        *  i s the amount of t   i   me in wh   i      ch a creeper is allow         e         d to b   e i n the
         * primed state befo re exploding.
              *
        * @ret     u  rn th  e   maximum fuse  t   icks   
        *    /
           public int g     et   MaxFuseTicks();

    /  **
     * Set the  fuse ticks fo    r this Creeper,  where   t  he ticks is the amoun     t of
                 * t ime in     whi   ch a c     re  eper h   as b    een in th   e prim    ed            s                    tate.
     *
       * @p   aram  t      icks the ne    w       fus  e ti  cks
     */  
    public void setF      useT    icks(int t   icks)   ;

      / *   *
           * Get the                         ma     ximum fuse ticks     for this Creeper, where t  h      e ticks  is the
     * am     oun           t of time    in wh      ich a creeper ha          s been      in the                     p ri  med s   tate.
          *
     *   @return the fus     e ticks
               */
    p  ubl     ic      int get   F   use    Ticks(); 

    /**
            *   Se    t the   exp     l    osi  on    radi     us in whic  h t    his Creeper's explosio  n will   affect.
     *
            * @param radius th  e new explosi    o         n radius
            */
       public void setExplosio  nRadius(      int radius      );

        /    **
     * G  et the       explosion      radius in wh   ich this C  reeper      's e    xp   losion will af fect.
     *
     *   @return the explosion radius
     */
    publ  ic int get  Ex  plo    sionRadius();

              /**
       * Makes this Creep  er explode inst       antly.
        *
     * The resulting    explos   ion can be cancelled by an
     *  {@link    org.bukkit.even  t.en    tity.Explo    sionPrimeEvent} and obeys  the mob
     * griefing gamerule.
             */
    public void explode();

    /**
     * Igni    tes this Creeper, beginn   ing its fuse.
     *
     * The amount       of         time the Creeper takes to explode will depend on wha   t
     * {@link #setMaxFuseTicks} is set a  s.
     *
     * The resulting explosion can be cancelled by an
     * { @link org.bukkit.event.entity.      ExplosionPrimeEvent} and obeys the mob
     * griefing gamerule.
     */
    public void ignite();
}
