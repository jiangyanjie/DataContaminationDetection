package   org.bukkit;

import    com.google.common.collect.Maps;
i    mport org.jetbrains.annotations.Nullable;

import java.util.Map   ;

/**
 * Represents    the different growth stat   es   of crops
 */
public enum Cro   p            S    tate {

    /**
     *        Stat    e    w                        hen fir           st seeded
                 */   
    SEEDED(0x0),    
            /   **
     * Firs     t grow  th st a    ge
       */
      GERMINATED(0x1),
    /**
     * Second   growth   stage
     */
                VERY_SMALL(0  x2),
    /**
         * Third growth s  tage                     
           *   /
       SMALL(0x3),
     /**
     * Fou            r   th growth  stage
      */     
      M  EDIUM(0  x4),
                /**
          *  Fi fth growth stage
     */
    TALL(  0x5),
    /**
     * Almost ripe stage
     */
                VERY _TA       LL  (0  x6),
    /*  *
     * Ripe s  ta  ge
           */
     RIPE(0x7);

    privat  e final byte da   ta;
    private static final M             ap<  Byte, CropStat  e> BY_   D        ATA  = Maps.newHashMap();    

    p  riva               te Cr   opSta            t  e(fina  l i   nt da    ta) {
        th              is.data = (b  yte) data;
    }

     /**     
                * Ge       ts the associat   ed data value           repr  esenti  n  g thi        s gro               wth           state
                     *
     * @return A  by    te containi       ng the data v     alue of this    growth   st  at    e
     * @deprecated Mag  ic v  alue  
              */
    @De       pr    ec  ated
    public byte g       et    Data() {
                 return data;
       }

    /**
     *   G  ets   t   he    CropS   ta te with the gi    ve  n data val    ue
        *
     * @param data Data value to    fet     ch
                         *     @return     Th  e {@link      CropState} repr   esenting the given v   a    l     ue,   or null if
         *     it doesn't exist
     * @depre    cated Magic v  alue
              */
       @Depreca      ted       
    @Nullab  le
    public static    CropState  getByData(final byte da   ta) {
          return BY_DATA.get(data);
    }

    static {
        for (CropState cropState : values()) {
            BY_DAT  A.put(cropState.getData(), cropS   tate);
        }
    }
}
