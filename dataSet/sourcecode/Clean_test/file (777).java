package de.ar56te876mis.CraftMinecartControl.Util;

import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Utils.VectorUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.util.Vector;

public class CraftVectorUtil implements VectorUtil {

    final MinecartControl mC;

    public CraftVectorUtil(MinecartControl mC) {
        this.mC = mC;
    }

    public void driveTo(Minecart minecart, String to) {
        Location loc = minecart.getLocation();
        if (to.equalsIgnoreCase("N") || to.equalsIgnoreCase("North")) {
            loc.setZ(loc.getBlockZ() - 1);
            minecart.setVelocity(new Vector(0, 0, -minecart.getVelocity().length()));
        } else if (to.equalsIgnoreCase("E") || to.equalsIgnoreCase("East")) {
            loc.setX(loc.getBlockX() + 1);
            minecart.setVelocity(new Vector(minecart.getVelocity().length(), 0, 0));
        } else if (to.equalsIgnoreCase("S") || to.equalsIgnoreCase("South")) {
            loc.setZ(loc.getBlockZ() + 1);
            minecart.setVelocity(new Vector(0, 0, minecart.getVelocity().length()));
        } else if (to.equalsIgnoreCase("W") || to.equalsIgnoreCase("West")) {
            loc.setX(loc.getBlockX() - 1);
            minecart.setVelocity(new Vector(-minecart.getVelocity().length(), 0, 0));
        }
        minecart.teleport(loc);
    }

    public void rightVector(Minecart minecart) {
        rightVector(minecart, minecart.getVelocity().length());
    }

    public void rightVector(Minecart minecart, double speed) {
        List<BlockFace> checklist = new ArrayList<BlockFace>();
        checklist.add(BlockFace.UP);
        checklist.add(BlockFace.DOWN);
        checklist.add(BlockFace.NORTH);
        checklist.add(BlockFace.EAST);
        checklist.add(BlockFace.SOUTH);
        checklist.add(BlockFace.WEST);

        Vector vector;
        Location loc;
        for (BlockFace bf : checklist) {
            loc = minecart.getLocation().getBlock().getRelative(bf).getLocation();
            if (mC.getUtils().getOtherUtils().isTrack(loc)) {
                vector = new Vector(speed * bf.getModX(), speed * bf.getModY(), speed * bf.getModZ());
                minecart.setVelocity(vector);
            }
        }
    }
}
