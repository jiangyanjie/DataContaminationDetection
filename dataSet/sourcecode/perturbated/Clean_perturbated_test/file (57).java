/*
   *  Minecr  aft Forge
   * Co pyr   ight (c) 201   6-2020.
 *
 * This   library is         free      softwar   e; you can redistribute it and   /or
 * modify it under the terms of the      GNU Lesser General      Public   
 * Licen    se       as     published      by the F  ree S     oftware Foundation version          2.1
 * o  f the Lice     nse.
 *
 * This   library is distribute   d     in        the hope              that it will    be useful,
     * but WI     THOUT         AN    Y   WARRANTY; with     out even the implied    warranty of
 * MERCHA   NTABILITY          o                 r FITNESS FOR    A PARTICULAR PURPOSE.  See the GNU
 * Less       er     Gen  eral   P  ublic License f or m ore detai        ls.
 *
 * You   sh    ould have receiv   ed a copy of the GNU Lesser General Public
 * License along with this lib     rary; if not, write to the Free Softwa    re
 * Foundation, Inc., 51 Fra  nklin Street, Fifth Floor, B  oston, MA  02110-1301  USA
 */

package ne      t.minecraftf     orge.debug.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creati       vetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelRegistryEve   nt;
impor t net.minecraft    forge.client.mod      el.ModelLoader;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.event.RegistryEvent;
import net.mine      craftforge.fml.common.Mod;
import     net.minecraftforge.fml.common.eventhandler.S  ubscribeEvent;
import n et.minecraftforge.fml.common.registry.Game Registry   ;
import net.minecraftforge.fml.relauncher.       Side;

@Mod.   EventBusSubscri   ber
@Mod(m     odid = Cus  tomRarityTest.MOD_     ID, name = "Custom r       ar   ity test mod", ver     sion = "1.0", acceptableRe      moteVersions = "*")
public class CustomRa    rityTest    
{
         st   a  tic final String   MOD_ID     = "cust   om_rarity_test";

      @ GameRegistry.ObjectHo  lder("test_it          em   ")
      publ     ic static fi na l   Ite     m    TEST_ITEM = null;

       @Subscr       ibe  Event
    public static void registerItem(         Re     gistryEvent.R   egister<Item> event)
      {
             event.g       e   t    Re  gistr          y().re         gister(new   TestItem()
                       .        set   Registr    yName(MO  D_ID, "      test      _item")
                  .se      tTra  nslat  io    nKey(MOD_ID + "    .test_item")
                  .s   etCreativeTab(C   reativeTab   s.MISC)
                );  
        }

    @Mod.E    ventBusSubscriber        (va    lue = Side.CLIENT, mod     id = MOD_  ID)
    p  ubli   c sta       tic cl  ass Client  EventH   andler
      {
            @Su  bscribeE    v        ent
         public static voi  d regis            te      r  Models(Mo  delRegistry   Even   t event    )
                        {
                          Mo    delL     oad  e     r.setCustomModelResourceLocati      on(TEST                _ITEM            ,             0,         ne   w Mo      delResourceLoc        atio  n   ("   minecraf   t:b     o    ok#inventor      y  ")   );
            }
     }

       static final    class Test       Item ex  tends Item
    {
          pr ivat         e stati       c fina     l IRarity   RA  RITY = new IRarity()
              {
            @Over        r   i   de
            public TextFormatting getColor(    )
                        {
                 return TextF      orma tting.RED;
                     }

                   @Override
            public String getName()
               {
                        retu     rn "Test";
            }
        };

         @Override
        public IRarity getForgeRarity(ItemStack stack)
        {
            return RARITY;
        }
    }
}
