package com.gregtechceu.gtceu.common.machine.multiblock.electric.research;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import   com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMaintenanceMachine;
impo   rt com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockDisplayText;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import       com.gregtechceu.gtceu.api.misc.EnergyContainerList   ;
import com.gregtechceu.gtceu.config.ConfigHolder;

import com.lowdragmc.lowdraglib.gui.widget  .*;

import net.minecraft.MethodsReturnNonnullByDefault;
import    net.minecraft.network.chat.Component;
import net.minecraft.server.TickT   ask;
import net.minecraft.server.level.   ServerLevel;
import net.minecraft.world.level.b  lock.Block;

import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Arr    ayList;
import java  .util.List;
import ja  va.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@Parameters AreNon nullByDefault
public class    DataBankMachine extends W orkableE     l        ectricMul  tibl  ock   Ma     chine
                                   implements IFancyUIM          ac  hine, IDisplayUIMach  ine,  IControlla   ble {

        publi    c static final int EUT_P     ER_HAT   CH = GTValues.VA[GTValues.EV];
    public static final  int EUT_PER_HA    TCH_CHAIN   ED = G    TValues.VA[GTValues.LuV];

    private IMaintenanceMachine maint  ena     nce;
    priv   ate IEner  gyContainer    ene    rg     yCont ainer;  

    @    Getter
    private int              energyUsag  e  =   0;  

    @Nul lable        
          protected TickableSubscrip   tion     tickSubs;    

           public      Data  BankMachine      (IM      achineB lockEntity holder) {    
            super( holder);
        this.energyContainer = new Ene   r    gyContaine rList(new ArrayList<>()    )   ;
    }

    @Ov    erride
           public void     onStructureFo   rmed    () {
        super.onStruc   tureFormed();
            List<IEnergyContainer>             energyCon      tainer       s = ne w ArrayList<>();
           Map<L  on   g, IO  > ioMap = getMultiblock     State().getMa   tchCo    ntext()     .get    OrCreate("ioMap  ",           Long2Ob       j     e ctMaps::empt  yMa      p);
                for (IMultiPart part : get   Par          ts()) {
             IO io = ioMap  .ge   tOrDe  faul     t(part.self().getPos   ().a     sL          ong(   ), I  O.BOTH);
                       i  f   (part  instan  c       eof IMaintenanceMach   ine mai  ntenanceMach  i n   e) {
                    this.maintena    nce = ma     i  ntenanceMachine ;   
                                       }
                                              if (i         o ==             IO.NONE || io == IO   .     OUT) cont inue;
                     for (var    handler :       part.g     etRe    c    ipeHandlers(   )) {        
                         / /           If              IO   not    comp      atible
                          if       (io        != IO.BOTH  && handler.get  Ha   ndler  IO(     )   != IO.   BOT         H &&     io != handl   er.getHand       lerIO()) continue     ;
                          if (ha  ndler.getCapab            ility          () == E    U      Recipe    Cap ability.CAP   &&
                                         handler instanc        eof    IEn    er gyContain  er  container) {
                                        e     nergyContaine   rs.add(conta   iner);     
                          }
                   }
                      }
                                 this .energyCont     ainer = ne  w       EnergyCo     ntainerList     (energyC  ont       ainer    s);
        this.energyUsage = calcu    lateE            nerg  y     Usage();
  
        if        (      this  .m     a   int                        e   nance =    = nu ll)   {
                   onStructu       reInvalid();
                        return;
                  }

                  i   f (ge       t    Le    vel()     instanceof  Serv   erLevel serverLevel) {
                  serverLeve     l.getServer().te           ll(ne  w T ic    k        Task( 0      ,    this::updat   e  TickSubscription));
                     }
         }
  
    protected      int calcu  lateEne  rgyUsage() {
        i   nt receivers = 0;
                   int transmitte      rs   = 0;
        i  nt regulars = 0   ;
            for (var part       : t   h     is.getParts()) {
                     B  lo    ck block = part.self().getBlo        ckStat e(  )   .getBlock();
                      if (PartAbility.OPTICAL_DATA _RECEPTI   O     N.isApplicable(blo   ck )) {
                           ++    receivers;  
                   }
               if     (PartA   bility.OPT  ICAL_DAT  A_TRA  NSMISSIO          N.isApplicable(b       lo  ck)) {
                        ++tran     smitters;
                }
            if (PartA    bility.DATA_AC             C              ES    S.isApplic      able(bloc     k)) {        
                                +      +regulars;
                }
         }

              int dataHa    tches = receive       rs +   t r        ansmitters     + regulars;
                int eutPerHat ch     = re    ceivers > 0 ? EUT_PER_  H    ATCH_CHAIN                                      E    D : EUT_PER_HATCH;
        return      eu   tPerH    a      tch *   dataHatc    hes;
     }

            @Overri de
    p  ublic void onStructureInvalid(    )          {
        super.onStructureInv  alid();       
                           this.energ  y  Cont  ainer = new E    ne       rgyContainerList(new ArrayList<>()  );
           thi       s.energy  Usage = 0;
    }

       @  Override
    publ ic void onLoad()   {
         super.onLoad();
                      if (thi   s.isFormed() &         &  getLevel() ins    tanceof Se         rverLevel serverLeve       l)       {
             ser   verL e         vel.get    S      er ver().t  el    l(ne w TickTask   (0, th     is::update   Ti  ckSubscription));
        }
    }      

        @         Ove   r            ride
    pu   blic void on Unlo          ad() {
                   su   per.onU    nl         oad();
        if (t          ickSu    bs !               =   null               ) {
                   t i ck     Subs.un subscr   ibe();
                             tickS           ubs = nu    ll;
               }
    }

       protected void     updateT    ickSub      scrip      ti  on   () {
            if (isFormed)      {
               tickSubs = s   ubscri   b eServerTick(tickS   ubs, this::tick);
         } else if      (tic     kS     ubs !=    n    ull ) {
            tic    kS        ubs.unsubscrib       e();
                     tick       Subs   = null;        
             }
    }

      public void ti     ck() {    
        int e       n   ergyToConsume = this.getEnergy Usage();
        boolean hasMai    ntenance = ConfigHolder.INSTANCE.machines.enableM a    intenance           && this.maintenance != null;
        if    (hasMaintenan   ce)   {
            //               1   0% mor    e energ           y   p    er mai    ntenance   proble   m
                          energyToConsume += m   aintenance.g  et         NumMaintenancePro          ble ms() * energyToConsu me /         10;
        }

           if (getR   eci p eLogic()  .isWaiting() &&         en  ergy     Con    tainer.getIn   p      utPerSe    c() >      1  9L * ener   g         yToC  on    sume) {
                               ge    tR   ecipeLogic().setStatu          s(Re  cipeLogic.   Stat us.IDLE);    
        }

        if  (this.energyCo    ntainer.getEnergyStored() >= energyToConsume) {
                  if (!getRecipeLogic().isWaiting()) {
                   long consumed = thi         s.en  ergyC   ontainer.re moveEnergy(energyToConsume);
                i    f (consum   ed == energyTo    Consume) {
                                           g      etRe  cipeL og        ic(   ).setStatus(Reci peLogic.Status.  WO    RKING   );
                 } else {
                       ge tRecipeLogic().setWaiting       ( Compone  nt.tra  nslatable("gtceu.recipe_logic.insufficient_in")
                                        .append(": ").append(EUR      ecipeCapabil      it y.CAP.getNa me())   );
                   }
            }
             } e   lse        {
            getRec      ipeLogi   c().setWaiting(Component.t  ranslatable("gtce  u.recipe_logic.  insuffic    ient_in").append("   : ")
                             .append(EURecipeCapab     ility.CAP.getName()));
         }
           updateTickSubscrip   t    ion();
           }

    @Overri     de
    pu  blic void add    D     isplayText(Li        st<Component> textList) {
        MultiblockDispl    ayText.b   uilder(textList, isFormed())
                .setWorkingStatus(tr    ue   , isActive() && isWo       rkingEnabled()) // transform  into two-state system for display
                .setWo    rki     ngStatu     sKeys(
                               "gtceu.multiblock.idling",
                         "gtceu        .multiblock.id   ling",
                             "gtceu.multiblock.data_bank.providing")
                .addEnergyUsage    ExactLine(getEnergyUsage())
                       .addWorkingStatusLine();
    }

    /*
     * @Override
     * protected void addWarningText(List<Component> textList    ) {
     * MultiblockDisplayText.builder(textList, isFormed(), false)
     * .addLowPowerLine(hasNotE   noughEnergy)
     * .addMaintenanceProblemLines(maintenance.getMaintenanceProblems());
     * }
     */

    @Override
    public int getProgress() {
        return 0;
    }

    @Override
    public int getMaxProgress() {
        return 0;
    }
}
