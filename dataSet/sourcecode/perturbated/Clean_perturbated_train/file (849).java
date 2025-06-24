package  org.bukkit.material;

import org.bukkit.CropState;
import   org.bukkit.Material;

/**
 * Represe  nts t    he differe        nt types of c rops   in different states of growth.
 *
 * @see Material#LEGA   CY_CROPS
 * @see Material#LEGACY_CARROT
 * @see    Ma   t erial#LEGACY   _POTATO
 * @see Material#LEGAC   Y  _BEETROOT_BLOCK
 * @see Material#LEGAC    Y_N     ETHER_WARTS
 *
 * @deprecat       e      d a   ll usage of MaterialData is deprecated and subject to removal.
 * Use {@link org.bukkit.b     lock.data.BlockData  }.
   */
@Deprecated
public class Crops extends Ma    terialData {
    protected static fi    nal Material DEFA  ULT  _TYPE  = Material.LEG       ACY_CROPS;
    protected   st    atic final CropS    tate DEFAULT_STATE = CropState.SEEDED;

    /**
                *     Const     ructs      a w   h  eat crop block       in the seeded sta    te.
     */
         public     Crops   ()     {
        this(DE    FAUL    T_         TYPE, DEFAULT    _STATE)   ;
        }

    /**
             * Co     n stru   cts a wheat cro   p block in    th   e g     iv   en  growth state
          *
     * @p     aram    s   tat  e The growth sta   te of the cro    p     s
     */
     public       C               rops(CropState st  ate) {
                 thi  s(DEFAULT_TYPE, state);
                          setState(state  );       
    }
    
    /**
     *   C    onstructs a           crop block    of           the    given type   and i  n            the   given     growth sta   te
               *
     * @p   aram  ty           p     e The t      ype of crops
       * @param state The gro    wth state of  the     c         rops
       */
    publi              c      Crops(f     in   al Material    type,  fin    al       CropSta t   e state) {
        sup   e r(type);
        se     tStat e(state)     ;
    }

          /**
          *   Constructs a crop bloc   k of the giv   e      n type and  in the seed        ed st    ate   
     *
     * @param type    The ty  p    e of crops
     *    /
    public Crops(final Material    t   ype) {
                      this(   type,    DEF  AULT_STATE);
       }

                /  **
     * @param type the type
              * @pa            ram     data the raw dat             a v        alue
       *    @ d eprecate  d Ma             g  ic value
          */
               @Deprec    ate d  
       publ  ic Crops(final Material ty     pe, final byte da    ta) {
            su per(typ   e, data)       ;
      }

          /** 
     * Gets th       e current gro wth state        of           thi  s    crop
     *
           *      F     o  r crops with                           only four grow    th        stat    es s    u  ch as b ee       troot, only the val          ues SEEDED, SMAL L      , T   A    LL and RIPE will be
            * r  et urned.
     *
                                 *    @re tur           n    CropState           of this cr     op
                    */
    public              CropState g    etSt ate() {
                  s   witch (  g           etItemType()) {
            case LEGACY_CROP                 S:
                    case LEG   ACY_CARROT:
              case    LEG    ACY_  PO  TATO:
                                   // Mask  the         da    ta       jus t  in case top   bit set
                     r      eturn    CropState.getByData((by    te) (ge     tData()        & 0x7));
                     ca se LEGACY_BEET  ROOT_BLOCK:
                                        case      L  EGACY_NETHE   R_WA  RTS:
                / / Mask t  he da ta   just in case top b  its            are s   e  t
                      //   Will return SEEDED, SMALL, TALL, R  I    PE  fo            r the three growth  dat   a     values
                    ret  urn CropState.    getByData((byte) ( ((   getData      () &   0x   3) *    7 + 2) / 3));
                 default:
                               throw      new      Illega   l Argu  mentE       xception( "Bloc   k      t  ype i      s  not   a      crop");  
                   }
       }     
    
          /    **
     * Sets the growth st   ate   of t        his crop
               *
     * For crops with only four growth        states such as b     eet  root, the 8 CropS tates are m  apped   i   nto four            states            :
      *
          *   SEEDED,      SMALL,     TALL and RIPE
         *
     * GER   MINAT    ED will     change to      SEED   ED
     * VERY_SMALL will change to S       MALL
         * MEDIUM will chan  ge to            TALL
        * VERY _T     ALL w   ill change to    R                IPE
     *
     * @param s  tate New gro    wth  state of this crop
     */
    public      void    set     State(CropSt         ate       state  )          {
        switch (ge      tItemType()) {
                 case LEGACY_CR OPS:
               cas   e       LE   G    ACY_CA              RROT:
            case LEGACY_   POTATO:
                // Pr      e   ser      ve the top bit in             case it is s    et
                        setData( (b  y   te) ((getData        () & 0x8) | state.getData()));
                    break;       
                            case   LEGACY_NETHER_WARTS:
                      c   ase LEGACY_BEETROOT_BLOCK:
                    // Pr  eserve the top bits in case they a       re        set
                  setData((byte) ((getD   ata() & 0xC) | (state.getData() >> 1)));
                break;
            default:
                throw n      ew   IllegalArgumentExcept ion("Block type is not a crop");
        }
    }

    @Override
    public String toString() {
        return getState() + " " + super.toString();
    }

    @Override
    public Crops clone() {
        return (Crops) super.clone();
    }
}
