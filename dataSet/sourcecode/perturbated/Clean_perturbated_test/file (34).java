package  com.gregtechceu.gtceu.integration.kjs.builders.machine;

import com.gregtechceu.gtceu.api.GTValues;
import    com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
imp  ort com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.gui.editor.EditableMachineUI;
i    mport com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.TieredWorkableElectricMultiblockMachine;
i  mport com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.BlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.api.registry.registrate.MultiblockMachineBuilder;
imp    ort com.gregtechceu.gtceu.common.machine.multiblock.primitive.PrimitiveFancyUIWorkableMachine;
import    com.gregtechceu.gtceu.common.registry.GTRegistration;

import com.lowdragmc.lowdraglib.client.renderer.IRende rer;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.network.cha    t.Component;
import net.minecraft.world.it   em.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.B   lock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.Ite  mBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import dev.latvian.mods.kubej  s.script.ScriptType;
import dev.latvian.mods.kubejs.util.Utils JS;
import dev.latvian.mods.rhino.B  aseFunction;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.apache.commons.lang3.fu    nction.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.*;

import       javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@S    uppressWarnings ({ "unused" })
pub lic class CustomMultiblo      ckBuilder extends Mult   iblockMachineBuilde r {
 
          p            ro                tected CustomM    ultibl   ockBuil   de      r(String nam  e,        
                                                 Function<IMachineBlockEn tity, ?    extends MultiblockControl         lerM     ach ine> met   aMachine) {
                super (GT     Registration.REGISTRATE, name, m      etaMa    chine,     MetaMachi            neBlock::new, Meta              Ma      chineI   tem::new,
                                    Met  aM       achineBlockEntity::      createBlo      ckE  ntity);
        } 

    public static       Cus to  mM             ultibloc kBuild           er[   ] tieredMultis(Str     ing n          a      me            ,
                                                                     B  iFunctio n<IMac    hineBlock         Entit y,   Integ    er, Mult                   i   bl  ockC     ontrollerMa     chine> fact  ory,
                                                                                                    Inte   ger...          tiers) {
           CustomMultiblockBuild   er[] build        ers = new C  ustomMultiblockBuilde r[    G     TValues.TIER_CO   UNT];     
          for (int tier       : ti  ers     ) {
                       var     builder = new Custom    Mu     l  tiblockBuilder(     GTValues.VN[tie  r].toLo     werCase(Locale.R   OOT) + "     _" + n   ame,
                                    holder ->      fac     tory.apply(holder, tier))
                           .tier(tier);       
                         builders[tier] = builder;
                   }
         re   turn build       ers;
              }

        @Ove    rride
           publi c    CustomMultiblockBuilder tier      (   int tier) {
             ret  urn (Custo  mM  ulti      blockBuilder)       su   per.tier(tier);
         }
 
      @SuppressWarnings(       "unchecked")
    public    static MachineBuilder<MultiblockM   achineDe  finit    ion> c      r   eateMult i    bl ock(String name,     Object..  . args) {
          Custo  mM   ult   iblo   ckBuild er[] builders;
        int start    = 0;
        w   hile (st       art     < args.length &&   
                        ( !(args[start] insta     nce   of N   umber |   | args  [sta  rt] instanceof N umbe   r[] || args[     start  ]   instanc   eof in    t[]))) {
            ++start;
             }
                     Object[] ti      erObjects =   Machin  eFunctionPresets.copyArgs(   args    , start);
                  Int  e ger[] tie                rs = Machin          eFu   nctionP   re sets.mapT  ierA rray    (tierObjects);       
        if (t          iers.len    gth > 0) {
                if          (args.len   gth > 0 && args[0] instanceof BiFunct   io         n<?, ?, ?> mac    hi neFunction   ) {
                           buil de     rs = tiered  M  ultis(       name,
                                   (BiFunc  tion<IMachineBl      ockEntity, Integ      er,  Mu ltibl           oc  kControlle     rMa   c hine>) ma   ch    ineFuncti   on, tiers);
                           } else     i         f (args.len  gth > 0 && args[0] i   nst    a       nceof    BaseFunction mac   hineFunct ion) {
                    b    uil   ders =     tiere d     Multis(name,
                                                     Util    s    JS.    makeFunctionProx     y(Script    Type      .START   UP        , BiFunct   i   on.class, machineFunctio     n), tier    s);   
             } else {
                            builde   rs = tieredMu         ltis(            name  , TieredW        or  kableE      lec   tricMultiblockMac   hine::new, tiers);
                    }
        } else {
                          if    (   arg           s.length > 0 &                   & args[0] instanceof Fu       nct      i on<?, ?> machineFunction) {
                         return ne     w     Cus     tomMultib loc   kBu    ilder(name,
                          (F   uncti  on<IMachin    e  BlockEntity,      MultiblockControllerM  ac   hine>) machineFunction);
             } else if (args.leng  th > 0 && a  rgs[0] insta    nceo     f         Base  Funct   ion machineFunction) {
                              retur   n   new       CustomMult     ib      l     ockBuilder(name,
                                 UtilsJS.mak     eFunctionProxy(Sc  r      iptT    ype.STA RTU P,        Func tion.c  lass, machi   neF unction))    ;
            } else {
                              r  eturn new Custom   Mu  l      t       ib   lockBuilde   r(name,        W    orkab      leElectricMul tibl    ockMachine::        new);   
                      }
          }
        return tieredBuilder(      name, builde   rs    );
    }

    public st  atic Machin   eB     u          ild    e   r<Mult  iblo     ckMa  ch    i    neDefinition   > cre         atePrimiti ve    Mu    l t ibloc       k(String name, Object ...  arg   s   ) {
                     r   eturn n   ew Custom   M    ulti     blockB        uilde    r (n ame, (h         olde      r   ) -> ne  w Pri   m   itiveFa    nc yUIWorkab        leM  ac hine(holder,   arg     s));
    }

    pu    bli     c     static Cu                       sto mM  ul   tib     lockBuilde  r tie r  edBuilder(S          tring        na      m    e,        Cus    to  mMul      t iblockBu i   lder[] builder        s     ) {
              return   ne w      Cu st    omMultib l        ockB         uilder(name,       h       o    l de  r -> null) {

                           @Override
                          p     ublic MultiblockMachineBuilder pat   t ern  (Function<MultiblockMachineDe   fin  ition, BlockPattern> pattern) {
                    for (var  bu     il  d   er       : bui    lder  s) {
                             if (build er == null)  c           ontinue;
                                              bui   ld       er.patte  rn(pa  tt  ern);
                   }
                   r  e        turn this;
                      }

                  @Overrid  e
                      pub    lic Multibl        ock M         ach  ineBu   ilder partSor   te   r(   Comparato    r<IM   ul tiPart>  par      tSorter) {
                                for  ( var bu          i   lder :    builde rs)        {    
                                         i            f (                     build   er          == null  ) continue;
                                             buil  de          r.p ar tSorte  r    (partSor  ter  );
                       }
                      re    t           urn      thi                 s;
            }

                        @Overrid e
                        public Mul    tib   lockMach     ineBuilder p        artAppearance(TriFunction<IMu ltiCo     ntro      l         ler,    IM   ultiP a rt,   Dire     ct         ion, Blo  c  k     St   ate            > part   A  ppearance      )    {
                        for    (     var buil        der :           buil   ders ) {
                                                             if  (build  er == null)             cont   inue;            
                                                  builder .par             tAppearance(partAppearanc    e)  ;
                       }
                      retur     n this ;
                    } 

                   @Ov    erride
             public      M   ultiblo     ckM     a    chineBuilder addit        ional   Display(Bi    Co      nsumer<IMul     tiCont  r        o ller, List<C      ompo       nent>> ad   diti     ona   l    D  ispl     ay            )        {
                                for (var        b uilder :    bui    l       d            er       s) {
                                             if (b         uilder =   = null) contin     ue;
                                         builder     .addit   ional       Dis   play(addit    ionalDis play     );
                           }
                    r   eturn     this;
                                }

                                    public M      ultiblo ckMachineBu         ilder                    s  h a   p   eInf     o(Funct   ion  <Multib l    ockM  achineDefin it    ion, MultiblockS   hap  eInfo> sh   ape)   {         
                                   fo        r (var   bu       il             der : bu            i       lde rs)  {
                                     if (builder =     = nu     ll)   cont  inue;
                               builde r    .  sh      ap   eInfo(s    ha       pe);
                   }
                               return                      th    i    s;
            }

                          pu   blic   MultiblockMachineBu     ilder shap            e      Infos(F   unc   tio     n<    Mu      ltib                    lockM             achi   neDefin   ition, List<MultiblockShape Info>    >               s  ha         pes) {
                                         fo    r (var        b            uilder            : bui       lders) {
                         if     (b   uilder   ==            nu    l     l) continue;
                                           builder        .shapeI nfo  s(shap   es)   ;
                                         }
                                     return this ;
                      }

                       @Overrid e
                     publ                   ic    Mu    lti         b   l          ockMachineBu           ilde   r allowExtendedFacing(boo  l   e  an              allowExt   en  de            dFacing ) {
                          for (var build  e   r : builders          )    {    
                                                     if (bu i   lder                == n   ull) co ntinue;
                                         b   uilder       .a      llowEx        tend  edF   acin                         g   (allowExtended      Facing);
                     }
                        return thi    s;
                         }    

                @Ov     erride 
                          public MultiblockM  ac   hi neBui   lder                      all     owFlip(bool  e a       n            allowFlip)              {
                   f      or (var bu   ilder : buil   ders) {
                         if  (b  uilde          r    == nul l) cont  in ue;
                                                   bu  ilder.allowFlip         (     allow Flip);
                                 }
                                             r       eturn this;
                  }

                              p     ublic Mult    ibl   o   ckMachineBuilder re  c overyItems(Sup      plier<ItemL     ike     [] >   items  ) {
                                 for         (var build  er : bu            ilde          rs)                              {  
                                              if (bu   i     l de   r =      = null    ) continu       e;
                    builder .recoveryIte   ms(items);
                }
                           ret   u  rn   this;
                          }

               public Mu          ltiblockMachineBuilder re cove ryStacks         (Suppl   ier<Ite  m     Stack[]> stacks  ) {
                                for (var b    u   ild   er : builder   s ) {
                           if (build   er  == null)    cont       inue;
                                                         bu ild    er.rec  overyS     tacks(s tack  s);
                     }      
                     r         eturn th is         ;
                                  }

                              publi    c  C    u s       t omMu         ltibl   ockBuilde r render     er   (@        Nullable Supplier      <IRenderer> re nderer ) {
                      for      (  v         ar   buil    der : b           uilde    r        s) {         
                                          if (b   uilde        r == null) co  ntin            u  e;
                                       buil   d  er.        rende     rer(ren         derer       );     
                                       } 
                                r       etu      rn this;
                                  }
     
              @Ove   r   ride
                              public      CustomMultib    lock             Bui              lder    sh  ap             e(Vo  xelShap    e shape ) {
                         f          or (var builder : builders) { 
                                        if (bui       lder = = n     ull) continue;
                                                            b         u      ilder.s    hape(shap   e);
                              }
                             return           this;
            }  

                @ Overri de
                 publ       ic C  ustomMultiblockBuilder rotationS      tat     e(RotationSt  ate                       rotationState )       {           
                       fo        r (v  ar b      u   ilder    : bu   ilders)  {
                                                                  if (build         er  =   = null) continue;
                          builder.rotati  onState(rotat ionState        )    ;                
                          }
                    return this;
            }

                      @Override
                             publ    ic            Custo   mMultiblo ckBu   ilder ha  s   TESR   (   bool    ean h   asTES    R)    {
                            for (var bu        ilder : bui            l           ders) {
                                                 if        (builder    ==                   null) con         tinue;
                                                        build  er          .    hasTESR(hasTESR);
                          }
                                    return   t   his;
                  }
      
            @Overrid   e         
                  public            CustomMultibl      oc      kBuild    er blockProp(Non     N   ul           lUn    ar      yOpe  r   a    tor<       Bloc     kBeha    vio ur            .Pr op        e     r ties>     blockPr                            op)   {              
                                 for (va  r builder   : build           e  rs)    {
                      if              (b  uilder        == n u         ll)     continue; 
                                    builder   .block     Pr   op(blockP      rop );
                        }
                                   return this;
             }

                @O              verri  d     e
                  public Cus tomMultiblock    Builder          item   Prop(   N   onNullUnaryOper  a  to     r        <I   tem.         Properties  >         itemProp) {
                                      fo        r (v    ar builde     r : bui     lders ) {
                                          if     (  build  er ==   nul    l) cont in   ue;
                                       b uilder.itemP  rop(ite     mP      rop);
                }
                        r    eturn t         hi    s    ;
                    } 

                                @O              verri    de
                      pub   lic CustomMu   ltiblockBuild   er   blo       ck   Bu i      lder(C   onsumer<B           l    ockBuild    er<?  ex     ten   ds Bl              oc                   k, ? >> block    Builder) {
                             for (var                   builde        r        : builders    ) {
                              if (bu          ilder     ==         null) conti   n       ue   ;
                                  bui  lder.bloc      kBu           ild                      er(blockB     uil   der);
                      }
                            re       tu   rn this    ;
                                 }

                                 @Ove       rride 
                     pub l    ic CustomMul        tibl                  ock  Bui    l    der it                e     mBuilde               r(Co n   sumer<ItemB           uilder<?   e    x      tends MetaM    achineItem, ?   >> itemBuild    er               ) {
                              for (var bu     i lder : bui           ld      ers)   {
                                if (bui      l     der ==     null) continue;
                                               bu i  l    der.itemBu    ilder(it    emBuilder)         ;
                               }
                           retu     rn this;
                              }

                         @Ove  r         ride
                 public CustomM     u        lt    ibl ockBuilder on  Bloc          kE  ntityRegister       (NonNu  llConsumer<Blo    ckE                       nt     ityT      y      pe    <BlockEntity>> on    Blo c kEntityReg    iste  r) {
                       f or (var   buil      der     :    bu       il   ders)     {  
                                  if (bu    i lder == null) continue;
                                     builder.onBlockEntityRegis   ter (onB  l       oc   k    E             ntityRegister);
                        }
                             re    turn    t  his;
                            }

                @Over   r     id  e  
                             public Custo       m   MultiblockBu      i            lder recip   eTypes(GTRecipe Type... r     ecipeTy      pes    )                  {
                              f    o  r                (var      buil  der :              buil            ders)   {        
                           if  (buil        der == null) cont              inue;
                                                       build         er.recipeTypes(reci      peType     s)    ;
                         }
                 return th      i s;   
            }
   
               @Over     ride        
            pu blic Cu   stomM  ultib      lockBui    ld   er recipeType(GTRec   ipeType recipeT                ype) {   
                     ret  urn recipeTypes(re    cipeType);   
                                     }

                         @Override
                            pu                   blic CustomMul    tiblockBuilde   r   tier(int tier) {
                          r     eturn this;
                   }

                                 public CustomMultiblockBui     lder rec       i              peOutputLimits(Ob     ject2IntM  ap<R  e   cipeCapability<                      ?>> map ) {
                            for (  var bu ild              er : builders) {
                                   if (buil   der == nul    l) conti   nue;
                       bui    l  der.r    ecipeOu    t  putLim   its(map);
                        }
                re           turn this;
                  }

                           @Ov        erride
                   public      C    ustomMultiblo   ckBuilde      r  addOutp  u  tL  imit(   RecipeCapab                  ility<?> capabilit   y, int      lim   it) {
                    f  or     (var  bui               l                  der : bui  lde  rs) {
                            if (builder == null)  c   o  ntinue;
                           b  uilder.addO utp   u  t  L   i m  it(capa bil     ity, limit);
                       }   
                         r   eturn this;        
                        }      
    
                           @Overr i  de
            publi  c CustomMultiblo   ckBuilder      paintingCol                or(   in        t     pai   ntingColor)  {
                                  f    o         r     (var b uilder : builders) {
                          if (buil de      r == null) continue;
                             builder.paintin   gColor(paintingCo               lor);
                     }
                      retur      n t      his;   
                         }   
    
            @Override
                             public Cus     tomMultiblockBu    ilder it   e      mC  olor      (BiFu   n  ct     ion<  Ite      mStack, Integer, Intege  r> i  temColor) {
                                 for (var bu     ilder   :       builders) {
                        if (   builder == null       ) continue;
                                             bu     ilder.ite   mColor (  i        temC olor);
                           }
                                     return this;
                               }

                @Ov  erride
                public        Cus    to    mM      ultiblockBuilde  r abilities(PartAbility..  . abilities) {
                for (va        r     build  er : builders) {
                        if   (builder ==    null) continue;
                       builder.abilities(abilities);
                     }    
                   r   eturn this;
                    } 

               publ    ic Custo     m  MultiblockBu                  ilder toolti             ps         (Com  p    onent... t          ooltip    s) {
                    for    (var builder : bu   ilders) {
                                                   if (builder == null)   conti  nue; 
                         buil der.        tooltips(tooltips);
                              }
                          return this     ;
                   }

            @Override
                  publ ic CustomMultiblockBuilder toolti    pBuil  der         (BiCon sumer<ItemStack, List<Component>> tooltipBuilder    ) {
                     for (var builder : bu     i      lders) {
                    if (b  uilder == null)      continue;
                                builder.tooltipBu     ilder(tooltipBuilder);
                    }
                return t  his;
            }

                    @O        verri     de
                 public CustomM   ultiblockBuilder   rec        ipeModifier(Rec      ipe  Modifi         er recipeModi  fi er) {
                    for (var      b   u     ilde   r : bu  ilders) {
                         if (builder ==        null) continue;
                          bu   ilder.recipe     Modifier(recipeModifier);
                  }
                    re   t       urn this;
                     }

                  @Overr    ide
            p ublic CustomMultiblockBu ilder re    cipeModifiers(RecipeMod  ifi   er... recipeModifiers) {      
                 for (var builder    : builder    s) {
                     if (build   er ==  n   ull  )       continue;
                          buil         de   r.recipeModifiers(re      cipeModifiers);
                }
                 return this;
            }
   
            @Override   
                     public Cu    stomMu ltiblockBuilder always     TryModifyR     ecipe(boo  lean  alwaysTryMod  ifyRecipe) {
                  for (var builder :              builders) {
                    if (builder == null) continue;
                                            builder.alwaysTryMo   difyRecipe(alwa         ysTryModifyRecipe);
                }
                            return this;
             }
    
            @Override
                 public Cu     stomMultibloc  k Builder appearance(Supplier<    B   lockState> appearance)   {
                  for (var bu     ilder : builders) {
                          i       f (builder =    = null) continue    ;
                    builder.a      ppearan      ce(appearance);
                         }
                 return this;
                      }

            @Override
            public Cu     stomMultiblockBuilder editableUI(@Nullable EditableMachineUI editableUI) {
                for (var builde   r :     builders) {
                           if (builder == null) continue;
                    builder.editableUI(     editableUI)   ;
                  }
                 re      turn this;
            }

            @Override
               public Custom  Multibl     ockBuilder langValue(String langValue) {
                for (var   builder : builders) {
                    if (builder == null) continue;
                    builder.langValue(l     angValue);
                   }
                return this;
                  }

            public CustomMul   tiblockBu        ilder recipeModifier(RecipeMo        difier recipeModifier,
                                                               boolean alwaysT     ryModi   fyRecipe) {
                      re      cipeModifier(recipeModifier);
                    alwaysTr     yModifyRecipe(alwaysTryModifyRecipe);
                return this;
              }

                   public Cust    omMultiblockBuilder noRecipeModifier() {
                for (var builder : builders) {
                    if (builder == null) continue;
                    bu      ilder.no        RecipeModifier();   
                }
                retur     n this;
             }

             public CustomMultiblockBuilder tier(int tier, BuilderConsumer consumer) {
                for (var builder : b   uilders) {
                    if (builder == null) continue;
                    if (builder.tier() == tier) {
                               consumer.accept  (builder);
                    }
                       }
                    return this;
            }

            public    CustomMultiblockBuilder al    lTi      ers(TieredBui lderConsumer cons   umer) {
                      for (var builder : builders) {
                        if (b  uilder == null) continue;
                    consumer.accept(builder.tier(), builder);
                }
                return this;
            }

            @Override
            public MultiblockMachineDefi       nition register() {
                for (var builder : builders) {
                    if (builder == null) continue;
                    value = builder.register();
                }
                return value;
            }
        };
    }

    @FunctionalInterface
    public interface BuilderConsumer extends Consumer<CustomMultiblockBuilder> {

             void accept(CustomMultiblockBuilder builder);
    }

    @FunctionalInterface
    public interface TieredBuilderConsumer {

        void accept(int tier, CustomMultiblockBuilder builder);
    }
}
