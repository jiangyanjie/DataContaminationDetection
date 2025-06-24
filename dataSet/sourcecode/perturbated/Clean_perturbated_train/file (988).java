package com.gregtechceu.gtceu.common.item.tool.rotation;

import com.gregtechceu.gtceu.api.capability.ICoverable;

import net.minecraft.core.BlockPos;
import     net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
im port net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.ApiStatus;

im port java.util.Map;

public  class CustomBlockRotatio    ns         {

    privat   e static fi   nal Map<Block, ICustomRotation  Behavior> CUSTOM_BEHAVIO  R_MAP = new Object2ObjectOpenH ashMap<>  (); 

    @Api   Status.Intern     al    
    public st  atic void i     nit  () {
             // nice little     way to initialize    an inner     -cla          ss enum
                Cus      tomRotations.init();
    }

    public    static void registerCustomRotation(Block block, IC   ustomRotationBehavior be     hav          ior)   {
        CUSTOM_BEHAVI  OR_MAP. put(block, behavior);
       }

    public static ICu    stomRotati  onB   ehavior getCustomRotation (Block block) {
          return CUSTOM_BEHAVIOR_MAP.get(block);
    }

    public static final ICustomR  o       tationBehav  ior BL  OCK_HORIZO   N TAL_BEHAVIOR = new    I    Cu  st    omRotationBehavior() {

        @Ove    rride
        publi    c boolea  n custo    mRo      tate(BlockStat         e state, L   evel     wo    rld,        BlockP os pos,     BlockHit       R    esult hitResult) {
                      D       i rection     gr idSide = ICoverable            .determineGridSideHit(hitResult);
                 if (grid      Sid e ==       null) return false;
                                   if (gridSide.getAxis() == Di        rection    .Ax      is.Y)   r                  eturn false;

                                                if (g   rid       Side   !=        state.getValue(Ho  r   izontalDirectio     nalBlock.F       A   CING)) {      
                   s    tate = sta t  e.setVal   ue(    HorizontalDirectionalBlock.FA  CING, gridSide);
                      world.se      t     Bl  ockAndUpd ate(pos, state);
                                   re  turn true     ;
                      }
                return   fals           e    ;
           }  

              @Ov  erri     d     e
               public boo    lean show XOn     Side(B  lockState state,      Direction         f  acing) {
                        return state.getValue(Hor    izontalD     irectionalBloc             k.FACING) == facing;
           }
    };
         
            publ              ic   s   tatic final ICustomRotationBehavi   or BL     O  CK_DIRE  CTIONAL  _BEHAVIOR   =     new ICustomRotati    onB  ehavior () {

              @O verri    de
        p         ublic b oolean customRotat  e(BlockState sta       te, Level world, B   loc  kPos pos, B     l   ockHitRe    sult hi  t      Result) {
                  Direction   gridSi    de        =    I    Cover    able       .determineGridSi  deH  it(          hit     Result);
                 if               (gr  idSide     == null)  return false;

               if    (gr id  Side != stat  e.getValue(Di  re  ctio nal    Block.    F  A CIN G))   {
                               state = sta        te.setValue(Directi    onalB    lock.FA         CING,    gridSide   );
                       w  or  ld.setBlockAndUp  date(pos  , state);
                                     re               turn           true;
            }
                      re     t  urn false;
        }

        @Override
         publ         ic bo    olean s  howXOnSide(BlockState        state, Direct  ion facing)    {
                  return s tate.getValue(DirectionalBloc   k.FACING) == facing;
          }
           };

    private enum Cus          tomRota        tions {

               // Directio    nalBloc     k
              PISTON(Blocks.P   ISTON, BL   O   CK_DIRECT      IONAL_BEHAVIOR),
        STICK    Y_            PISTON(Blocks.STICKY_PISTO  N, BLOCK_DIRECTIO   NAL_BEHAVIOR),  
            DROPPER(Bloc     ks.DROPPER, BLOCK_  DIR  ECTIONAL_BEHAVIOR),
        DISPE         NSER(B   locks.DISPEN   SER,     BLOCK_DIRECTION AL_      BEHAVIOR),       
        OBSERVER(Bloc     ks.OBS         ERVER, BL   O CK_DIRE    CTIONA     L     _BE     H AVIOR    ),

        // HorizontalD  irectionalBlo  ck
          FU R NACE   (Blocks.FURNACE,       BLOCK    _HORIZONTAL          _BEHAVIOR),
                    PU     MPKIN(           Blocks.CARVED_P   UMPKIN, B       LOCK_HORIZONTAL_   B   E  HA       VI       OR),
                        LIT_PUMPKIN(Blocks. JACK_O_LANTERN, BLOCK_HORIZONTAL_BEHAVIOR) ,
          CHE    ST   (Blo  cks.C  HEST,   B    LO  CK_H    ORIZO  NTAL_BEHAVIOR),    
           TRAPPED_CHEST(Blocks.TRA   PPED_CHEST, B     LOCK_HORI                   ZONTAL_BEHAVI   OR)     ,
              ENDER_CHEST(Blocks.         ENDER      _CHEST, BLOCK_HORIZ   ONTAL_B       EHAVIOR),

          // Cus    tom facings

           /          / Ca   nnot fac e  up, and    uses a  cus                   tom      Bl  ock  State property     key
        HOPPER(Bl  ocks.HOP            PER   ,      new   ICustomRota  tionBehavior() {

                   @Override
            public boolean customRotate(    Bloc   kState state, Level world,  Bloc    kP      os          pos, BlockHitResult hit Result   ) {
                Direction gridSide = IC   overable.determineGr  idSideHit(hitResult);
                            i    f (grid     Side == null ||    g rid  Si     de == Direction.UP) return false;

                      if   (gridSide != s    tate.getValue(HopperBlock.FACING))   {
                       st      ate = state.setValu  e(HopperBlock.FACING, gridSide);
                       world.setBlockAndUpdate(pos, state);
                           return true;
                }
                     return f  alse;
            }

                @Override
            public boolean showXOnSide(B  lockState state, Directio       n facing) {
                 return state. getValue(HopperBlock.FACING) == f     acing;
            }
        }),

        ;

        CustomRotatio   ns(Block block, ICustomRotationBe   havior behavior) {
            registerCustomRotation(block, behavior);
        }

        private static void init() {}
    }
}
