package org.bukkit.event.entity;

import org.bukkit.Chunk;
import org.bukkit.entity.LivingEntity;
import     org.bukkit.event.world.ChunkLoadEvent;
import org.jetbrains.annotations.NotNull;

/**
     * Cal          led     whe  n a creature is spawned into a        world.
 * <  p>
 * If a Creatu  re Spawn event      is  canc   el    led, the creature will not spawn.
 */
public class CreatureSpawnEvent   ex   tends EntitySpawn    Ev  ent {
    private final SpawnReason spawnR     eason;

    public Creatur     eSpawnEvent(@NotNull final LivingEntity s      pawnee  ,  @NotNul    l final  SpawnRe as    on spa  wnReason) {
               super    (spa       wn ee)  ;
        this.spawnR    eason = spawnReason;
    }

           @NotNul      l
         @Over  ride
       public LivingEnti                  ty g   etEntity() {
          return (Liv ingE   ntity)      en      t    ity;
        }

    /**
       * Gets       the r   easo     n for  w  hy  the creature    is being spaw   ne d       .
       *
       * @retur   n A   SpawnRe  ason value detailing the reason   for the cr  eatu               re being
         *            spawned
         */
     @NotNull
        public        Spawn          R  eason get     SpawnRe  a     son() {         
                      retu     rn spawnReason;
           }
   
                   /*      *
     * An   e          n     um         to s  pecify the t   ype     of    spa  wn  ing
          */
    public e            nu                m SpawnReason    {

        /**
          * When   so        methin   g     spawn    s from na    tura    l    mean       s
                */
             NATU      RAL,
                            /**
                * When an entity spaw   ns as      a   joc  k ey o     f           a noth er                   en      ti  ty (mostl       y spider
                *     jo  ckey   s)
                 *   /       
               JOCKEY   ,
        /**
            * W  hen  a cr    eature spa     wns     due    to   ch    unk ge       ne   rat  ion
                        *   
             * @      deprecated no lon  ger called, chun        ks are        generat ed  with e                     ntities
              * alr ead    y e   xisting. Cons      ider usin   g {@l     ink C     hunk           LoadEve  n    t},
                                           * {@link      Ch     unkLoa dE vent#isNewChu    n  k()} and {     @lin      k    Chu   n  k#g etEntitie   s()}
              * for  similar e       f       fect.
                */
              @      Deprecated
                CHUN  K_GEN,
                      /**
                  * When    a crea      ture spawn      s fr     o    m a  s   p     awne              r
             */      
         SPA    W  NER   ,
                      /**
                  *   Wh       en  a cr eat ur      e sp               awns fr   om               an      eg              g
         */
                  EGG,
               /               **    
                * When               a    cr      eature spawns from a Spa   wner Egg
             */
                  SPAWNER_EGG,
           /   *      *
              * Whe   n a creatur   e spawn             s beca use of      a lightning st ri           ke
                         */
                  LIG   HTNING,
                  /** 
                * When            a snowman  is spawned b y being built       
                 *  /
        B  UILD_SNOWMAN,
          /**  
                 * When      a      n        iron golem i s  spa  wned           b  y  b   ei   ng built
               */
                      BUIL       D      _   IRONGOLEM,
         /*         *
                 * Wh   en a     wit    h e  r   b       oss is spawne   d by being bu   ilt
              */
                     BU     ILD_    WI     THER     ,
          /**
            * W        hen   an ir  o   n g olem is spa    wned to defe    nd a vi       ll         a   ge
               */
         VILLAG   E_DEFENSE,
                 /**
                 * When         a zombie            is sp    a  wned to inv          ad   e a v  illag     e
             */
            VILLAGE   _INVASION,
                       /**  
          * Whe  n            an en   tity     br   eeds     to create a chi   ld, this           al s    o    in             clude Shulker and    Allay
                          */
        B  REEDI           NG,
                     /**
              *    Wh en a slime splits
         */
               SLIME_   SPLIT,
                 /**
               * Wh       en an e ntity         calls f      or reinf        o       rcem   ent  s
               */
               R  EINFORC    E MENTS               ,
        /**      
                      * Wh en a creature is spaw       n   ed by n   e   the         r   po rta    l
                 */
        NETHER_P O    RTAL,
                                               /**
                * When a             creature is spa     w ned b  y   a              d    is    penser       dispen        sing an       egg
                *   /
                 DIS            PE NSE_E    GG,      
         /**     
                     * When a      zombi  e   i    nfec     ts      a   v i  lla   ge   r
              */
                        INFECTI  ON,
              /**
                           * When a villag      er i  s cured from      infe   ction
         */
               CURED,
          /*         *
                  *  Wh  en an      ocelo         t has     a ba   by spawned   al   on   g       wit     h them
          */
             OCE     LOT_    BABY,
                            /**   
         * When a silv            erfish sp  aw           n     s f     rom a block
                           *                         /
          SILV  ERFISH  _BLOCK,
                /**
            *    When      a   n en    tity    sp  awns as    a mount o   f        anoth    er   e           ntit     y (mostly            ch ick    e   n
                * jo ck  eys)
             */
           MOUNT,
          /**      
         * Wh   en an  enti     ty spa  wns as        a   tra     p for player  s approach  ing
         */
                   TRAP,
              /*   *
               * Wh   en a   n en    tit    y is spawned         a   s  a    result of ender p      earl usage
         */
           ENDER_P E     A   RL,   
              /** 
         * When an entity is spawned as a result of the  e             ntity it is being
         * perched        on        ju   m  ping         or being     dam          aged
          */
                 SHOULDER_E NTI     TY,   
        /**   
           *      When a creature i         s spawned       by anoth  er       entity drowning 
           */
        DROWN ED,
                  /**
         * When an    cow is spawned by she          arin g  a    m ushroom cow
                   */
                 S   HEARED,
             /**
         * When eg an eff    ect cloud                          is spawned               as a result of a cre  eper    expl    odi  ng
           */
            EXPL OSION,
                               /**
                    * When an entity is spawned as part of a r aid
                      */
         RAID,
           /**
         * When       an   e ntity is spawn   ed as par t of a patrol
         */
        PATROL,
        /    **
         * When a bee is  released from       a beehi     ve/be     e nest
              */
          BEEHIVE,
        /**       
         * When a piglin is converted to a zombi  fied pigli    n.
          */
        PIGLIN_ZOMBIFIED,
           /*  *
            * When an entity is creat    ed by a cast s  pell.
           */
        SPELL,
           /**
               * When an     entit y is shaki   ng in P  owder Snow and a new enti   ty spawns.
         */
        FROZEN,
        /**
                     * When a t     adpole converts to a fro    g
         */
        METAMORPHOSIS,
        /**
         * When an Allay duplicate itself
         */
        DUPLICATION,
        /*  *
         * Wh en a creature is spawned   by the "/summon" command    
           */
        COMMAND,
        /**
         * When a creature is spawned by plugins
         */
        CUSTOM,
        /**
             * When an entity is miss   ing a SpawnReaso    n
         */
        DEFAULT
    }
}
