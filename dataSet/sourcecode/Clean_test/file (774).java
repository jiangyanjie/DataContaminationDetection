package de.ar56te876mis.CraftMinecartControl.Util;

import de.ar56te876mis.MinecartControl.Utils.SearchUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class CraftSearchUtil implements SearchUtil {

    @Override
    public Sign getSign(Location loc, int signLine, String line) {
        Location sloc;
        List<BlockFace> checklist = new ArrayList<BlockFace>();
        checklist.add(BlockFace.NORTH);
        checklist.add(BlockFace.EAST);
        checklist.add(BlockFace.SOUTH);
        checklist.add(BlockFace.WEST);

        loc.setY(loc.getBlockY() - 2);
        if (loc.getBlock().getState() instanceof Sign) {
            return (Sign) loc.getBlock().getState();
        }
        
        loc.setY(loc.getBlockY() + 1);
        for (BlockFace bf : checklist) {
            sloc = loc.getBlock().getRelative(bf).getLocation();
            if (sloc.getBlock().getState() instanceof Sign) {
                return (Sign) sloc.getBlock().getState();
            }
        }
        loc.setY(loc.getBlockY() + 1);
        for (BlockFace bf : checklist) {
            sloc = loc.getBlock().getRelative(bf).getLocation();
            if (sloc.getBlock().getState() instanceof Sign) {
                return (Sign) sloc.getBlock().getState();
            }
        }
        
        loc.setY(loc.getBlockY() + 1);
        if (loc.getBlock().getState() instanceof Sign) {
            return (Sign) loc.getBlock().getState();
        }

        return null;
    }

    @Override
    public ArrayList<Sign> getSigns(Location loc) {
        Location sloc;
        List<BlockFace> checklist = new ArrayList<BlockFace>();
        checklist.add(BlockFace.NORTH);
        checklist.add(BlockFace.EAST);
        checklist.add(BlockFace.SOUTH);
        checklist.add(BlockFace.WEST);
        ArrayList<Sign> signList = new ArrayList<Sign>();

        loc.setY(loc.getBlockY() - 2);
        if (loc.getBlock().getState() instanceof Sign) {
            signList.add((Sign) loc.getBlock().getState());
        }
        
        loc.setY(loc.getBlockY() + 1);
        for (BlockFace bf : checklist) {
            sloc = loc.getBlock().getRelative(bf).getLocation();
            if (sloc.getBlock().getState() instanceof Sign) {
                signList.add((Sign) sloc.getBlock().getState());
            }
        }
        loc.setY(loc.getBlockY() + 1);
        for (BlockFace bf : checklist) {
            sloc = loc.getBlock().getRelative(bf).getLocation();
            if (sloc.getBlock().getState() instanceof Sign) {
                signList.add((Sign) sloc.getBlock().getState());
            }
        }
        
        loc.setY(loc.getBlockY() + 1);
        if (loc.getBlock().getState() instanceof Sign) {
            signList.add((Sign) loc.getBlock().getState());
        }

        return signList;
    }

    public BlockState getBlockBlockState(Class c, Location loc) {
        List<BlockFace> checklist = new ArrayList<BlockFace>();
        checklist.add(BlockFace.NORTH);
        checklist.add(BlockFace.EAST);
        checklist.add(BlockFace.SOUTH);
        checklist.add(BlockFace.WEST);
        checklist.add(BlockFace.DOWN);
        checklist.add(BlockFace.UP);

        Location sloc;
        for (BlockFace bf : checklist) {
            sloc = loc.getBlock().getRelative(bf).getLocation();
            if (c.isInstance(sloc.getBlock().getState())) {
                return sloc.getBlock().getState();
            }
        }
        return null;
    }
}
