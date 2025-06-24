package de.ar56te876mis.CraftMinecartControl.Util;

import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Utils.ItemTransferUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftItemTransferUtil implements ItemTransferUtil {

    List<Integer> itemsWithD = Arrays.asList(new Integer[] {269, 270, 271, 290, 273, 274, 275, 291, 256, 257, 258, 292, 284, 285, 286, 294, 277, 278, 279, 293, 259, 346, 359, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 267, 268, 272, 276, 283, 261});
    public void startTransfer(MinecartControl mc, Inventory inventory1, Inventory inventory2, String... job) {

        for (String string1 : job) {
            String string2 = string1;
            if (string1.startsWith("+")) {
                string1 = string1.trim().replaceFirst("\\+", "");
            } else if (string1.startsWith("-")) {
                string1 = string1.trim().replaceFirst("-", "");
            } else if (string1.startsWith("~")) {
                string1 = string1.trim().replaceFirst("~", "");
            }

            if (string2.startsWith("+") || string2.startsWith("-")) {
                if (string2.startsWith("+allitems")) {
                    transferAllItems(inventory2, inventory1);
                    return;
                } else if (string2.startsWith("-allitems")) {
                    transferAllItems(inventory1, inventory2);
                    return;
                }
                for (ItemStack itemStack : getItemStacks(string1)) {
                    if (string2.startsWith("+")) {
                        if (itemStack.getAmount() != 0) {
                            transfer(inventory1, inventory2, itemStack);
                        } else {
                            transferAllformOneItems(inventory2, inventory1, itemStack.getTypeId(), itemStack.getDurability());
                        }
                    } else {
                        if (itemStack.getAmount() != 0) {
                            transfer(inventory2, inventory1, itemStack);
                        } else {
                            transferAllformOneItems(inventory1, inventory2, itemStack.getTypeId(), itemStack.getDurability());
                        }
                    }
                }
            } else if (string2.startsWith("~")) {
                if (inventory1.getHolder() instanceof Dispenser) {
                    Dispenser dispenser = (Dispenser) inventory1.getHolder();
                    int amount = getInt(string1);
                    if (amount == 0) {
                        amount++;
                    }
                    for (int y = 0; y < amount; y++) {
                        dispenser.dispense();
                    }
                }
            }
        }
    }

    private void transferAllformOneItems(Inventory inventory1, Inventory inventory2, int itemId, short durability) {
        for (int x = 0; x < inventory1.getSize(); x++) {
            ItemStack is = inventory1.getItem(x);
            if (is != null && is.getTypeId() == itemId && (is.getDurability() == durability || containsItemId(itemId))) {
                ItemStack addItem = inventory2.addItem(inventory1.getItem(x)).get(0);
                inventory1.setItem(x, null);
                if (addItem != null) {
                    inventory1.addItem(addItem);
                }
            }
        }

    }

    private void transferAllItems(Inventory inventory1, Inventory inventory2) {
        for (int x = 0; x < inventory1.getSize(); x++) {
            ItemStack is = inventory1.getItem(x);
            if (is != null) {
                ItemStack addItem = inventory2.addItem(inventory1.getItem(x)).get(0);
                inventory1.setItem(x, null);
                if (addItem != null) {
                    inventory1.addItem(addItem);
                }
            }
        }
    }

    private int getInt(String id) {
        int Int = 0;
        try {
            Int = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
        }
        return Int;
    }

    private short getShort(String id) {
        short b = 0;
        try {
            b = Short.parseShort(id);
        } catch (NumberFormatException ex) {
        }
        return b;
    }

    public HashSet<ItemStack> getItemStacks(String line) {
        String[] split = line.split(",");
        HashSet<ItemStack> itemStackSet = new HashSet<ItemStack>();
        HashSet<Integer> blackIds = new HashSet<Integer>();
        String numbers = "-0123456789";
        String lastOperator;
        StringBuilder idS;
        StringBuilder dataS;
        StringBuilder amountS;
        StringBuilder blackIdS;

        for (String splitPart : split) {
            lastOperator = "";
            idS = new StringBuilder();
            dataS = new StringBuilder();
            amountS = new StringBuilder();
            blackIdS = new StringBuilder();
            for (int x = 1; x <= splitPart.length(); x++) {
                String substring = splitPart.substring(x - 1, x);
                if (numbers.contains(substring)) {
                    if (lastOperator.length() == 0) {
                        idS.append(substring);
                    } else if (lastOperator.equals(":")) {
                        dataS.append(substring);
                    } else if (lastOperator.equals("@")) {
                        amountS.append(substring);
                    } else if (lastOperator.equals("!")) {
                        blackIdS.append(substring);
                    }
                } else {
                    lastOperator = substring;
                }
            }


            String data = dataS.toString();
            int amount = getInt(amountS.toString());
            if (amount < 0) {
                amount = 0;
            }
            int blackId = getInt(blackIdS.toString());
            String id = idS.toString();

            if (id.contains("-")) {
                String[] idSplit = id.split("-");
                int id1 = getInt(idSplit[0]);
                int id2 = getInt(idSplit[1]);

                for (int x = id1; x <= id2; x++) {
                    if (x != 0) {
                        if (data.contains("-")) {
                            String[] dataSplit = data.split("-");
                            Short data1 = getShort(dataSplit[0]);
                            Short data2 = getShort(dataSplit[1]);

                            for (Short y = data1; y <= data2; y++) {
                                itemStackSet.add(new ItemStack(x, amount, y));
                            }
                        } else {
                            Short dataId = getShort(data);
                            itemStackSet.add(new ItemStack(x, amount, dataId));
                        }
                    }
                }
            } else {
                if (getInt(id) != 0) {
                    if (data.contains("-")) {
                        String[] dataSplit = data.split("-");
                        Short data1 = getShort(dataSplit[0]);
                        Short data2 = getShort(dataSplit[1]);

                        for (Short y = data1; y <= data2; y++) {
                            itemStackSet.add(new ItemStack(getInt(id), amount, y));
                        }
                    } else {
                        Short dataId = getShort(data);
                        itemStackSet.add(new ItemStack(getInt(id), amount, dataId));
                    }
                }
            }

            if (blackId != 0) {
                blackIds.add(blackId);
            }
        }

        for (ItemStack itemStack : (HashSet<ItemStack>) itemStackSet.clone()) {
            if (blackIds.contains(itemStack.getTypeId())) {
                itemStackSet.remove(itemStack);
            }
        }
        return itemStackSet;
    }

    private void transfer(Inventory inventory1, Inventory inventory2, ItemStack itemStack) {
        ItemStack itemStackC;
        int amount = itemStack.getAmount();
        for (int x = 0; x < inventory2.getSize(); x++) {
            itemStackC = inventory2.getItem(x);
            if (itemStackC == null) {
                continue;
            }
            if ((itemStackC.getTypeId() == itemStack.getTypeId() && itemStackC.getDurability() == itemStack.getDurability()) || (itemStackC.getTypeId() == itemStack.getTypeId() && containsItemId(itemStack.getTypeId()))) {
                if (itemStackC.getAmount() <= amount) {
                    amount -= itemStackC.getAmount();
                    inventory2.setItem(x, null);
                    ItemStack itemStack1 = inventory1.addItem(itemStackC).get(0);
                    if (itemStack1 != null) {
                        inventory2.addItem(itemStack1);
                        return;
                    }
                } else {
                    ItemStack clone = itemStackC.clone();
                    clone.setAmount(amount);
                    itemStackC.setAmount(itemStackC.getAmount() - amount);
                    inventory2.setItem(x, itemStackC);
                    amount = 0;
                    ItemStack itemStack1 = inventory1.addItem(clone).get(0);
                    if (itemStack1 != null) {
                        inventory2.addItem(itemStack1);
                        return;
                    }
                }
                if (amount == 0) {
                    return;
                }
            }
        }
    }

    public boolean containsItemId(int id) {
        return itemsWithD.contains(id);
    }
}
