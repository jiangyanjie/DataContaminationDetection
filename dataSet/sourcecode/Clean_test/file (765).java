package de.ar56te876mis.CraftMinecartControl.Control;

import de.ar56te876mis.CraftMinecartControl.MCLogger;
import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Control.ControlBlock;
import de.ar56te876mis.MinecartControl.Manager.BlockControlManager;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class CraftControlBlockManager implements BlockControlManager {

    ArrayList<ControlBlock> controlBlockList = new ArrayList<ControlBlock>();
    boolean enable = true;
    private final MinecartControl mc;

    public CraftControlBlockManager(MinecartControl mc) {
        this.mc = mc;
    }
    
    

    public void execute(Block block, Minecart minecart) {
        for (int x = 0; x < controlBlockList.size(); x++) {
            if (block.getTypeId() == controlBlockList.get(x).getBlockId()) {
                MCLogger.info(ChatColor.GREEN + "execute " + controlBlockList.get(x).getName());
                mc.getJobControlManager().execute(minecart, (controlBlockList.get(x).getJobName() + " " + controlBlockList.get(x).getJob()).split(" "));
            }
        }
    }

    public void redstoneExecute(Block block) {
        for (int x = 0; x < controlBlockList.size(); x++) {
            if (block.getTypeId() == controlBlockList.get(x).getBlockId()) {
                MCLogger.info(ChatColor.GREEN + "execute " + controlBlockList.get(x).getName() + "(Redstone)");
                mc.getJobControlManager().redstoneExecute(block, (controlBlockList.get(x).getJobName() + " " + controlBlockList.get(x).getJob()).split(" "));
            }
        }
    }

    public void registerControlBlock(ControlBlock controlBlock) {
        MCLogger.info("register ControlBlock: " + controlBlock.getName());
        controlBlockList.add(controlBlock);
    }

    public ArrayList<ControlBlock> getControlBlockList() {
        return controlBlockList;
    }

    public void enable() {
        enable = true;
    }

    public void disable() {
        enable = false;
    }

    public boolean isEnable() {
        return enable;
    }
}
