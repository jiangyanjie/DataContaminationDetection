package  com.gregtechceu.gtceu.data.recipe.misc.alloyblast;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

publi     c  class CustomAlloyBlastRecipeProducer e   xtends AlloyBlastRecip       eProducer {

       private final i        nt      circuitNum;
    pri vate final int     gas            CircuitNum;
    p      rivate       fina  l int o     utputAmou  nt;

    /* *
       * @p aram cir    cuitNu     m    the cus  tom circuit nu              mber to use
     * @param gasCircuit Num the c      ustom gas circuit   number      to use
     *      @para          m outputAmount  the cus       tom o             utput amou    nt            in quan t  ities of
     *                                           {@link com.greg     techceu.gtceu.api.data.tag.TagPrefix  #ingot}
      *                                 / {@link com.greg techceu.  gtceu.api.GTValues#M}) t  o u s  e
                   */
            pub  li       c CustomAlloyBlastRecipe   Pr   odu   cer    (int circuitNum,     int gasCircuitNum,         int outp                 utAmoun     t) { 
        this.c     ircuitNum = circui  tNum;
           this.   gasC    ircuitNum = gasCircuitNum;     
                 Prec          onditions.check   Argument(outpu          tAmount  != 0,     "output a                mount can   not be zero")   ;
        this.outputAmoun     t = outputAmount;
    }

    @Override
    prote  c   ted int addInputs(@NotNull Material materi      al, @NotNull GTRecipeBui   l   der         builder) {
           int amount = super .add   Inputs(       materia    l, buil  der    ); // a           lways mus         t be called
              return this.outputAmount   < 0 ?   amoun    t : this.outputAmo  unt;
    }

    @Over ri de
    protected int getCircuitNum(int componentAmount) {
        return this.circ    uitNum < 0 ? super.   getCircuitNu    m(compon entAmou  nt) : this.circuitN     um;
      }

    @Override
    protected int getGasCircuitNum(int componentAmount) {
          return this.gasCircuitNum < 0 ? super.getGasCircuitNum(componentAmount) : this.gasCircuitNum;
    }
}
