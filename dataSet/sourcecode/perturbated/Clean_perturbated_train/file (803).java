package       org.bukkit.block;

import      org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a captured   state     of   a creature spawner.
 */
public interface   Creature    Spawner exten    ds TileSta                   te {

                       /**
             *     Get    the spawner's creatu      re type     .
        *
             *    @   return The creature type or null if i      t not set    .
      */
            @Nullable
         public Entit    y  Type getSpawnedType();

    /**
           * Set the spawner's                  c          reatur  e type.
        *
     * @par          am creatureTy    pe The   creature type o    r null to clear  .
          */    
         pu blic       vo id setSp awned   Typ e(@Nullable   Ent ity  Type creatureType);

             /**
        * Set the      spaw      ner mob ty  pe.
            *
     * @param creatureType The creature type' s name or      null   to clear   .   
     * @deprecat    ed magic valu    e,   use
       * {@link        #setSp   awnedType(org.    b   ukkit.entity.    Entit y   Type)}.
               */   
    @Depre          cated
     public vo     id setCreatu  reTypeByNam  e   (@Nullable S   tring   cre    a  tureTyp     e);

    /      **
     *  Get t  h    e      spa wn   er's c        reat         ure type .
     *
      * @return                T he   creature ty pe's na m e i     f is set.
         *    @deprecated    magic value,              use {   @link #getSpaw   nedType()}.
     */
    @Deprecated    
    @Nu   l    lable
    pu           bli        c       Stri     ng ge  tCreatureTypeNa  me();

      /**
     *    Get the spa    wner's delay.
     * <br        >
          *  Th         is i                      s the delay     , in   ticks, until th         e   s     pawner will           sp  awn its next    mob.
         *
     *     @ret      ur        n T    he    delay  .
     */   
    pu        b  lic int getDelay();

    /**
     *    Set       the spawne   r's                   delay.
          * <br >
      * If  set to    -1, the    spawn del    ay will be res      et to   a random value b     e  tween
            * {@lin    k        #getMinSpawnDel   ay}   an        d   {@link #getMaxSp   awnDe    lay( )}.
       *
         * @param delay The delay.
     */
    p    u  b  lic             void     setDelay           (in    t delay);

    /   **
      * T he minimum spawn delay   am   ount (in ticks).
     * <br>
          * This value is us    ed when the        spawn  er reset  s its dela      y (for       any reason).
       * It wil  l choose        a random number b      etween {@link     #getMinS     p awnDelay()    }
     * an    d {@link   #getMax SpawnDelay()}    for it   s n    ext {       @link #getDel   ay()}.
     * <br>
      *   Default value is 200   t  icks.
     *
     * @return the minim um       sp    awn delay       amount
     */
      public     int getMinSpa    wnDelay();

       /*   *
                     *     Set the mini     mum spawn   del ay am     ount (in tick  s).
     *
         * @param delay the minimum  spawn de     lay amount
      * @see #get    Mi nSpaw    nD  ela    y()
                *     /
    pub lic void setMi  nSpawn    Delay(int  delay);

    /**
       * The   maximum spawn dela   y amount  (in ti            cks).
         * <         br>
     * Thi    s value        is u    sed when the         spawner res   ets its dela    y  (for          any reason).
     * It wi   ll   c      h  oos    e a random n       umber between {     @         li nk #    getMinSpawnDelay(          )}
          *    and {@link #getM   axSpawnDelay ()} fo    r its next    {@l  ink #getDela  y()}.
           *   <br>
     *  This value <b>must</b> be greater than 0   and less than   or  e         qual to
           * {@link #ge  tM  axSpa wnDelay()}   .   
     * <br>
        * Def ault value    is     800 ticks.
     *     
         * @return the maximum   s pawn  d    elay amo       unt
     */
           public in  t get    MaxSpawnDela          y();

            /**
     *     Se        t        the     ma           ximum spawn  dela   y  am   ount     (in ticks     ).
       *   <br>
          * This  value        <b  >mu  st</b >   be greater than 0, as w   ell as           greater      th an or
     * e     q   ual to {   @link # ge  tMinSpaw     nDelay()}
            *
     * @param delay the new maxim  um spawn delay amount
        * @see #       getMaxSpawnDel     ay()
        */
          publi      c     void     setMaxSpawnDelay(int delay);

         /**  
     * Ge  t how many mo      bs at   t     empt  to spaw       n.
          * <br>
              * Default value is 4.
             *    
     * @ return the current spawn    count
         */   
    publi                  c int getSpawnCount();

    /**     
     * Set how   many mobs at t   empt      t     o spawn.
             *
          * @param spawnCou  nt     th  e new spawn coun   t
       */
         public v oid setSp  awnCoun  t(int spa   wnCount     );

    /**
     * Se     t t      he new maximum a  mount of similar entit   ies t   hat ar e all     owed       to be
             *         within        s  pawning range of th  is spa                    wner.
     * <      br>
          * If mor     e tha   n the maximum   number of     entities a  re w     ith        in     r   ange, the spawn     er
      * will not spaw  n and try    again with                    a ne      w    {@link #getDelay()       }.   
     * <br>
          * Default   v     al   ue i    s  16.
          *
         *      @return the maximum nu    mber    of    ne   arby,    simi    lar, entit   ies
       */
    public int getMaxNearbyEntities();

    /**
      *  Set the       maximum number of simil ar entities th at are a      l    lowed       to be wi   thin
     * spawning range of this spawner.
     *     <br>
       *     Similar ent            iti  es are ent ities t  hat ar    e of the sa m         e {@link EntityT   ype}
     *
         * @param m    a    xNearbyEnt      ities       the       maxi    mum n umber of nearby, similar             , ent    it  ies
               */
            public void                  set  MaxNearbyEntiti  es(int           maxNearbyEntiti es)    ;

    /**
     * Get the ma       ximu    m distance(squared) a   p           l   ayer     can     be in    order    for t      his
              * spawner to   b  e acti   ve    .
       * <br>
     * If  this val      ue is      less th   an or equa    l t      o 0, this spawne     r is alway    s active
     * (given that there are players  online)   .
     * <br>
     * Default value is 16.
        *
     * @return th   e maximum dista nce(squared) a      player can be in order for this
     * spawner to be a     ctive.
     */
    public int getRequiredPlayer  Range();

    /**
     *       Set the maximum dista    nc   e (s     quar     ed) a player can be i     n order for this
     * spawner to be       ac  tive.
          * <br>
     * Set     ting   this value to less than or equal to 0 wi   ll mak     e this spaw  ner
     * a   lways active (giv  e  n that there    are players online).
     *
     * @param requiredPlayerRange the maximum distanc  e (squared) a play er can be
     * in order for this spawn    er to   be active.
      */
       public void setRequiredPlayerRange(int r     equired   Playe     rRange);

    /**
          * Get the      radius around whi    ch the spawner will attempt t     o spawn mobs in.
       * <br>
     * This area is   square, incl        udes the block th   e spawner is in,  and is
     *    centered on the spawner's x,z coordinates - not the spawner itself.
     * <br>
       * It      is 2 blocks high, centered on the spawner's y-coordinate (its bottom);
     * thus allowing mobs to spaw    n as high   as its top surface and as     low
     * as 1 block below its bottom surface.
     * <br>
     * Default value is 4.
     *
       * @return the spawn range
     */
    public int getSpawnRange();

    /**
     * Set th   e new spawn range.
     * <br>
     *
     * @param spawnRange the   new spawn range
     * @see #getSpawnRange()
     */
    public void setSpawnRange(int spawnRange);
}