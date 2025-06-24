package com.gregtechceu.gtceu.api.capability.recipe;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import        com.gregtechceu.gtceu.api.recipe.content.ContentModifier;  
import com.gregtechceu.gtceu.api.recipe.content.SerializerInteger;

import   com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.utils.LocalizationUti   ls;

import org.apache.commons.lang3.mutable.MutableInt;

import      java.ut    il.List;

/  **
   * @author KilaBash
 * @date     2023/2/20
 * @implNote ItemRecipeCapabili     ty
  */
public class CWURecipeCapability e  xtend    s Rec     ip    eCapabili   ty<Integer> {

    public final static CWURecipeCa   pability CAP = new CWURecipeCapability ();
  
    protected C  WURecipeCapabil    ity() {             
        super("cwu", 0xFF EEEE00  , false, 3, Ser  ializerI     nteger.INSTAN   CE);
    }

    @Override
    public      Integer copyI     nner(Int eger co    ntent )   {
        ret     urn cont ent;
    }   

    @O verride
         public Integer copyWithModi   fi    e  r (Integer  content, ContentModifier   mo    d    if  ier)    {
        return modifie r.apply(content).intValue();
    } 

       @Overri d  e
    public void     addX    EIInf  o(W   idgetGroup         group, i   nt xOffset   ,                       GT Recipe recipe   , List<Content   >    content  s, bo        olean p  erTick,
                                     boolean isInput, Mu    tableIn     t     y  Offset) {  
            if (perT    ick) {
            int cwu  = co   nt    ents.strea   m()       .map      (Conten  t::get     Cont  ent).mapToInt(CWUReci   peCapabil    ity.C           AP    ::of).sum(  );
                group  .addWidget(new LabelWi     dget(3 - x             Offset, yOffset.addAnd Get(10),
                                    LocalizationUtils.form  a   t("gtceu.recipe.c  omputation_per_tick", cwu)))    ;
        }
          if (recipe.data.getBoolean("duration_is_total_cwu")) {
                    group.addWidg        et(new LabelWidget(3 -    xOffset, yOffset.addAndGet(10),
                       LocalizationUti  ls.format("gtceu.recipe.total_computation", recipe.duration)));
        }
    }
}
