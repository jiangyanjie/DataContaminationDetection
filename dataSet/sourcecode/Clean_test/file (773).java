package de.ar56te876mis.CraftMinecartControl.Util;

import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Utils.OtherUtils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.material.Lever;

public class CraftOtherUtils implements OtherUtils {

    final MinecartControl mC;

    public CraftOtherUtils(MinecartControl mC) {
        this.mC = mC;
    }

    @Override
    public boolean isTrack(Location loc) {
        if (loc.getBlock().getType() == Material.RAILS || loc.getBlock().getType() == Material.ACTIVATOR_RAIL || loc.getBlock().getType() == Material.POWERED_RAIL || loc.getBlock().getType() == Material.DETECTOR_RAIL) {
            return true;
        }
        return false;
    }

    @Override
    public Minecart spawnMinecartFromChest(Chest chest) {
        for (int x = 0; x < chest.getInventory().getSize(); x++) {
            BlockFace first = null;
            if (chest.getData() instanceof org.bukkit.material.Chest) {
                first = ((org.bukkit.material.Chest) chest.getData()).getFacing();
            }
            if (chest.getInventory().getItem(x) == null) {
                continue;
            }

            if (chest.getInventory().getItem(x).getType() == Material.MINECART) {
                chest.getInventory().setItem(x, null);
                return spawnMinecartAtRail(chest.getLocation(), "Standard", first);
            }
            if (chest.getInventory().getItem(x).getType() == Material.POWERED_MINECART) {
                chest.getInventory().setItem(x, null);
                return spawnMinecartAtRail(chest.getLocation(), "Powered", first);
            }
            if (chest.getInventory().getItem(x).getType() == Material.STORAGE_MINECART) {
                chest.getInventory().setItem(x, null);
                return spawnMinecartAtRail(chest.getLocation(), "storage", first);
            }
            if (chest.getInventory().getItem(x).getType() == Material.EXPLOSIVE_MINECART) {
                chest.getInventory().setItem(x, null);
                return spawnMinecartAtRail(chest.getLocation(), "tnt", first);
            }
            if (chest.getInventory().getItem(x).getType() == Material.HOPPER_MINECART) {
                chest.getInventory().setItem(x, null);
                return spawnMinecartAtRail(chest.getLocation(), "hopper", first);
            }
        }

        return null;

    }

    public Minecart spawnMinecartAtRail(Location loc, String name, BlockFace first) {
        List<BlockFace> checklist = new ArrayList<BlockFace>();
        if (first != null) {
            checklist.add(first);
        }
        checklist.add(BlockFace.SELF);
        if (!checklist.contains(BlockFace.NORTH)) {
            checklist.add(BlockFace.NORTH);
        }

        if (!checklist.contains(BlockFace.EAST)) {
            checklist.add(BlockFace.EAST);
        }

        if (!checklist.contains(BlockFace.SOUTH)) {
            checklist.add(BlockFace.SOUTH);
        }

        if (!checklist.contains(BlockFace.WEST)) {
            checklist.add(BlockFace.WEST);
        }

        Location spawnloc;
        for (BlockFace bf : checklist) {
            spawnloc = loc.getBlock().getRelative(bf).getLocation();
            if (isTrack(spawnloc)) {
                return spawnMinecart(spawnloc, name);
            }
        }
        return null;
    }

    @Override
    public Minecart spawnMinecart(Location loc, String minecartType) {
        Minecart spawn = null;
        if (minecartType.equalsIgnoreCase("standard")) {
            spawn = loc.getWorld().spawn(loc, Minecart.class);
        } else if (minecartType.equalsIgnoreCase("powered")) {
            spawn = loc.getWorld().spawn(loc, PoweredMinecart.class);
        } else if (minecartType.equalsIgnoreCase("storage")) {
            spawn = loc.getWorld().spawn(loc, StorageMinecart.class);
        } else if (minecartType.equalsIgnoreCase("hopper")) {
            spawn = loc.getWorld().spawn(loc, HopperMinecart.class);
        } else if (minecartType.equalsIgnoreCase("tnt") || minecartType.equalsIgnoreCase("explosive")) {
            spawn = loc.getWorld().spawn(loc, ExplosiveMinecart.class);
        } else if (minecartType.equalsIgnoreCase("Spawner")) {
            spawn = loc.getWorld().spawn(loc, SpawnerMinecart.class);
        }

        mC.getUtils().getVectorUtil().rightVector(spawn, 2);
        return spawn;
    }

    @Override
    public void pressNearbyLever(Location loc) {
        for (int x = loc.getBlockX() - 2; x <= loc.getBlockX() + 2; x++) {
            for (int y = loc.getBlockY() - 2; y <= loc.getBlockY() + 2; y++) {
                for (int z = loc.getBlockZ() - 2; z <= loc.getBlockZ() + 2; z++) {
                    Block block = new Location(loc.getWorld(), x, y, z).getBlock();
                    if (block.getType() == Material.LEVER) {
                        final BlockState leverBS = block.getState();
                        final Lever lever = (Lever) leverBS.getData();
                        new Thread() {
                            @Override
                            public void run() {
                                lever.setPowered(true);
                                leverBS.setData(lever);
                                leverBS.update();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                }
                                lever.setPowered(false);
                                leverBS.setData(lever);
                                leverBS.update();
                            }
                        }.start();
                        return;
                    }
                }
            }
        }
    }
}
