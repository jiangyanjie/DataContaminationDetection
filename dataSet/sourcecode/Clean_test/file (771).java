package de.ar56te876mis.CraftMinecartControl.Jobs;

import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Control.ControlJob;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class CraftJob implements ControlJob {

    String name = "Craft";

    public void excute(Minecart minecart, MinecartControl mc, String... job) {

        if (!(minecart instanceof InventoryHolder) || minecart instanceof PoweredMinecart) {
            return;
        }

        InventoryHolder invMinecart = (InventoryHolder) minecart;

        HashSet<ItemStack> itemStacks = mc.getUtils().getItemTransferUtil().getItemStacks(job[0]);

        for (ItemStack itemStack : itemStacks) {

            if (itemStack.getAmount() == 0) {
                return;
            }
            for (Iterator<Recipe> irt = Bukkit.recipeIterator(); irt.hasNext();) {
                Recipe r = irt.next();
                if (r instanceof ShapedRecipe) {
                    ShapedRecipe recipe = ((ShapedRecipe) r);
                    if (recipe.getResult().getType() == itemStack.getType() && recipe.getResult().getDurability() == itemStack.getDurability()) {
                    System.out.println(recipe.getResult().getType().name());
                        int y = 0;
                        while (y < itemStack.getAmount()) {
                            crafting(recipe, invMinecart);
                            y += recipe.getResult().getAmount();
                        }
                    }
                }
            }
        }
    }

    private void crafting(ShapedRecipe recipe, InventoryHolder invMinecart) {

        HashSet<ItemStack> removedItems = new HashSet<ItemStack>();
        for (Iterator<Entry<Character, ItemStack>> irt2 = recipe.getIngredientMap().entrySet().iterator(); irt2.hasNext();) {
            Entry<Character, ItemStack> entry = irt2.next();
            ItemStack is = entry.getValue();

            if (is == null) {
                continue;
            }
            ItemStack remove = invMinecart.getInventory().removeItem(is).get(0);
            if (remove == null) {
                removedItems.add(is);
            } else {
                invMinecart.getInventory().addItem(remove);
                for (ItemStack backItem : removedItems) {
                    invMinecart.getInventory().addItem(backItem);
                }
                return;
            }
        }
        invMinecart.getInventory().addItem(recipe.getResult());

    }

    public String getName() {
        return name;
    }
}
