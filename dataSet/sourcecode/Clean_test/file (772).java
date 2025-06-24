package de.ar56te876mis.CraftMinecartControl.Control;

import de.ar56te876mis.CraftMinecartControl.MCLogger;
import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Control.ControlJob;
import de.ar56te876mis.MinecartControl.Control.RedstoneControlJob;
import de.ar56te876mis.MinecartControl.Manager.JobControlManager;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;

public class CraftJobControlManager implements JobControlManager {

    final MinecartControl mc;
    List<ControlJob> minecartControlJobs = new ArrayList<ControlJob>();
    List<RedstoneControlJob> minecartControlRedstoneJobs = new ArrayList<RedstoneControlJob>();
    boolean enable = true;

    public CraftJobControlManager(MinecartControl mc) {
        this.mc = mc;
    }

    public void execute(Minecart minecart, String... strings) {
        if (!isEnable()) {
            return;
        }
        for (ControlJob cj : minecartControlJobs) {
            if (strings[0].equalsIgnoreCase(cj.getName())) {
                MCLogger.info(ChatColor.GREEN + "execute " + cj.getName());
                try {
                    cj.excute(minecart, mc, cutJobName(strings));
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                return;
            }
        }
    }

    public void execute(List<Sign> signList, Minecart minecart) {
        if (!isEnable()) {
            return;
        }
        for (Sign sign : signList) {
            if (sign.getBlock().isBlockPowered()) {
                continue;
            }
            for (ControlJob cj : minecartControlJobs) {
                if (sign.getLine(0).equalsIgnoreCase("[" + cj.getName() + "]")) {
                    MCLogger.info(ChatColor.GREEN + "execute " + cj.getName());
                    try {
                        cj.excute(minecart, mc, cutJobName(sign.getLines()));
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
                    break;
                }
            }
        }
    }

    public void redstoneExecute(Block block, String... strings) {
        if (!isEnable()) {
            return;
        }
        for (RedstoneControlJob rcj : minecartControlRedstoneJobs) {
            if (strings[0].equalsIgnoreCase(rcj.getName())) {
                MCLogger.info(ChatColor.GREEN + "execute " + rcj.getName() + "(Redstone)");
                try {
                    rcj.redstoneExcute(block, mc, cutJobName(strings));
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                return;
            }
        }
    }

    public void redstoneExecute(List<Sign> signs) {
        if (!isEnable()) {
            return;
        }
        for (Sign sign : signs) {
            for (int x = 0; x < minecartControlRedstoneJobs.size(); x++) {
                if (sign.getLine(0).equalsIgnoreCase("[" + minecartControlRedstoneJobs.get(x).getName() + "]")) {
                    MCLogger.info(ChatColor.GREEN + "execute " + minecartControlRedstoneJobs.get(x).getName() + "(Redstone)");
                    try {
                        minecartControlRedstoneJobs.get(x).redstoneExcute(sign.getBlock(), mc, cutJobName(sign.getLines()));
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
                    break;
                }
            }
        }
    }

    public void registerControlJob(ControlJob minecartControlJob) {
        MCLogger.info("register ControlJob: " + minecartControlJob.getName());
        minecartControlJobs.add(minecartControlJob);
    }

    public void registerRedstoneControlJob(RedstoneControlJob minecartControlRedstoneJob) {
        minecartControlRedstoneJobs.add(minecartControlRedstoneJob);
        registerControlJob(minecartControlRedstoneJob);
    }

    public List<ControlJob> getMinecartControlJobList() {
        return minecartControlJobs;
    }

    public List<RedstoneControlJob> getMinecartControlRedstoneJobList() {
        return minecartControlRedstoneJobs;
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

    public String[] cutJobName(String... strings) {
        int length = strings.length - 1;
        int startLength = 1;
        if (strings.length == 1) {
            length = 1;
            startLength = 0;
        }
        String[] newString = new String[length];
        System.arraycopy(strings, startLength, newString, 0, length);

        return newString;
    }
}
