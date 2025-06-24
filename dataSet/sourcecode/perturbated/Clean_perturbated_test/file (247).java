package      com.gregtechceu.gtceu.data.forge;

i     mport com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.registrate.CompassNode;
import com.gregtechceu.gtceu.api.registry.registrate.CompassSecti   on;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
i mport com.gregtechceu.gtceu.common.data.GTConfiguredFe     atures;
import com.gregtechceu.gtceu.common.data.GTDamageTypes;
import com.gregtechceu.gtceu.common.data.G TPlacements;
import com.gregtechceu.gtceu.common.data.GTWorldgen;
import     com.gregtechceu.gtceu.common.data.forge.GTBiomeModifiers;
import com.gregtechceu.gtceu.data.tags.BiomeTagsLoader;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.regis tries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraf      t.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.Exi     stingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import   net.minecraftforge.eventbus.api.Su   bscribeEvent;
import net.minecraftforge.fml.common.Mod;
import   net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubs    criber.Bus.MOD   )    
public   c   lass DataGenerators {
   
    @    SubscribeE   vent
      pub       lic static void gather     Data(GatherDataEvent       eve   n     t) {
           DataGenera       tor gener      ator =   ev      ent.getGene      rat or();
              PackOutput      packOutp       ut = generator.getPackOutput();
        ExistingFileHelp     er exi   stin  gFi  leHelper = eve  nt.getExistin    gFileHelper();
                var r   egistries       = event.getLookupProv    id  er() ;
        if (event.includeC     lie    nt()) {   
            generator.addProvi    der(true, new S   oundEntryBuilder.SoundEntryPr ovi  der(packOutput, GTCE     u.MOD_ID));
                generator.addProvide  r(true, new Co  mpassSection.Com        p  assSec tionProvider(p  ackOut   p   ut, existi ngFileH   elper));
              generator.addProvider(true, new       Com passNode.CompassNodePr     ovider   (pac     kOutput, exis    ting      FileHelper));
        }
        if   (event.inclu        deServer      ()) {
                            var     se        t = Set.  of(GT     CEu.MOD_ID)          ;
                   gener a   tor.addProvider(true, n  e w Biom  eTagsL  oader(  pa   ckOutput,        registri es,     exis                tingFi   le   H   elper));
                  generator.      addProv   ider(true, n           ew Datapack      BuiltinEntrie sProvide    r(
                        packOut   p         u     t , registries,  ne      w       R          egistrySe         tBuilder   ()
                                                  .add(  Regist ries.DAMAGE_TYPE,    GT     D     amageTypes::bo otstrap)

                                                       .add(Re   g  istries.CONFIGUR       ED  _     FEATURE, GTConfiguredFeatu   res::  bootstrap)
                                  .add(Registries.PLACED_FEATURE, GTPlacements::bootstrap)
                             .add(Registri   es.DENSITY_F  UNCTION, GTWorldgen::   bootstrap    DensityFunctions)
                                     .add(ForgeRe gistries.Ke    ys.BIOME_MODIFIERS, GTBiomeModifiers::bootstrap),
                    set));
           }
    }
}
