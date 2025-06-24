package com.gregtechceu.gtceu.common.machine.storage;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import  com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import      com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.transfer.fluid.InfiniteFluidTransferProxy;

import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.texture.ResourceBorderTexture;
   import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
    import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import  com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;
import com.lowdragmc.lowdraglib.side.fluid.IFluidTransfer; 
import com.lowdragmc.lowdraglib.syncdata.annotation.DropSaved;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.core.Direction;

import org.jetbrains.annotations.Nulla  ble;

     public cla   ss Cr   eativeTankMachine extends Quant    umTankMachine {   

    publ  ic stati    c final ManagedFieldHolder MANAGED_FIELD_HO LDER = ne   w ManagedFieldHolder(Cr ea  tiveTankMachin           e.class     ,
              QuantumTank  Machine. MANAGED_FIELD_HOLDER)   ;         

    @Persisted
           @DropSa     ve    d
          private int mBPerCycle = 1; 
                @Persisted
          @DropSa   ved
        private int ticks PerCycle = 1;

       pr   ivate final InfiniteFluidTransferProxy capab     ilityTransferProxy;

    publi    c CreativeTan   kMachine     (IMach       ineBlockEntity holder) {
        super(holder, GTValues.MAX, -1                      );

        capabilityTransferProx y = new InfiniteFluidTransferProxy(cac  he, true, tru    e);
        }

    @Nullable
    @Ove    rride
        p  ub   lic IFl  uidTransfe  r getFluidTr      ansferCap(@Nullable Direction side, boolea    n u  s     eCoverCapability) {
                     if (s       ide == null ||         (useCov   erCapability &&   cove   rCo  ntainer.ha    sCover(side)))
                        return super .getFluid   TransferCap(sid    e, use        CoverCapability);

           retur n capability   TransferPro        xy;
      }

    protected No         tifiableFluidTank crea   teCacheFl  uidHandler(Object... args)    {
        re turn new N   otifiableFluidTa       nk(this, 1,     1000, IO.BOTH, IO.NO         NE   );
         }
     
    @Override
      protected void updateAutoOutputS  ubs   cript      ion() {
        var    outputFacing = getOutpu  tFacingFluids();
                if ((i    s     AutoOut   putFlu      ids() && !cach     e.isEmpty())   && out   putFacing !=      null &&  FluidTr     a nsfe   rHelper
                .g   etFluidTransfer     (getLevel()    , getPos().relative(   outputFacing), outputFacing.     g   etO    ppos   i  te()) != null) {
                   autoOutputS    u bs = s     u bscribeServerTick(aut     oO utp   utS     ubs, th                 is::checkAu         toOutput);
        }    else if (a     utoOutputSubs != null) {
                 au   toOutputSub   s.unsubscribe(    );
                       au             toOutputSub  s = null;
        }    
    }

    pro t  ected void checkAu     toOut   put() {           
        if (  getOffsetTimer() %    ticksPerCycle       == 0) {
                  if        (isAutoOutputFluids() && getOu  tputFacingFluids()    !=   null      )  {
                                        updat  eFlui   dTick();
            }
                  updat    eAutoOutputSubscrip tio   n();
                 }
              }

        @        Override
    public WidgetG  roup   c  reate      UIWidget(   ) {
               var grou   p =       new WidgetGroup(0,   0, 176, 131);
            group.addWidget(new PhantomFluidWid    ge  t(this      .cache.getSto    rages()[  0], 0   , 36, 6,        18,  18     ,
                (   ) -> th      is. cache.getStora          ges()[0].getFluid(), (fluid) -> this.cache.g      etSto  r  ages()[0]  .setFluid( fluid    ))
                       .setShowAmount(false).setBackground(G  uiTex   tures         .F                   LUID_SLOT));
                  group.addWidget(new  Lab  elWidget(   7, 9       , "gtc  eu.creativ     e.tank.f  luid"))  ;
        g  roup.a    ddWidget(n    ew Image   Wid  get(7,    45, 154, 1             4, Gui  Textures   .     D ISPLAY )      );
          g     roup.addWidget(new     TextFieldW    idget(9, 4   7,    152    , 10, () ->   S      tring.valu e Of(mBPer  Cycle), v  alue -> {
            if (!value.isEmpty()  ) { 
                        mBPerC     ycle = Integer.parse           I     nt(  va    lue);
                 }    
        }   ).setMaxStringLength(11).setNumbersO   nly(1,      Integer.MAX_VALUE));
        g  roup.addWidge     t(n   ew L       abe lWidge      t(7, 28, "gtce u.  creative.tank.mbp                 c"));

         g  roup.  addWi    dge   t(new     Im    ageWid g       et(7, 82    , 154, 14, GuiTextures.D      ISPLAY));
             group.addWidget(ne   w     Text FieldWi     dget    (9,             84  , 152, 10       ,   () -> String.valu   eOf(tick sPer  Cycle ), va  lue ->   {
            if (!valu                  e.is  Empty()) {
                         ticksPerCycle =       Int   eg  er.pa       rseInt(val        ue);            
                  }
         }).s  etMaxStr             ing  Length      (11).setNumb  ersO     nly(1  ,      Integ       er.MAX_VALUE))  ;
                group.addWi   dge    t    (n   ew Label        Widget(    7,      65, "gtceu.creative.tan   k.tpc") );

                group.addWidget(             new Swit     c  hWidget(7, 101, 162, 20,   (clic    kData , value)    -> setWorkingEnab led(v    alue))
                      .se        tText    ure(
                        new   GuiTextureGroup(Resour    ce     Bo    rderText     ure.BUT        TON_COMMON,
                                         new TextTextur  e("gtceu.creative.activity.off")),
                                        n   ew GuiTextureGr   oup    (ResourceBorderTexture.BU      TT    ON_COMMON, 
                                                      new TextTextur      e(   "   gtceu.   creative.activity      .on    ")))       
                          .set     Pressed(isWorkingEnab  led ()));

            retu       rn group;
    }

    public void updat     eFluidTic  k() {
            if (ticksPerCycle == 0 || g       etOffsetTimer() % ticksP  erCyc    le != 0 ||
                  cache.get     Storages()[0].getF     luid().isEmpty() ||   getLevel().isClientSide   || !isWor   kingEnab led  ())
            retu   rn;

        IFluidTransfer trans   fer =   FluidTransferHelper.getFluidTransfer(getLevel(),
                getPos().relative(getOutputFac  ingFluids()), get   Ou     tputFacingFluids().getOpposite());
        if   (transfer != null) {
            FluidStack stack = cache.getStorages()[0].getFluid().copy();
            stack.setAmount(mBPerCycle);
            long canInsertAmo   unt = transfer.fill(stack, true);
            stack.setAmount(Math.min(mBPerCycle, canInsertAmount));

                tra  nsfer.fill(stack, false);
        }
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
