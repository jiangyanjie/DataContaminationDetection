package de.ar56te876mis.CraftMinecartControl.Util;













import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Utils.EntityPushUtil;


import org.bukkit.Location;



import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class CraftEntityPushUtil implements EntityPushUtil {

    final MinecartControl mC;


    boolean enable = true;
    private double pushRadius = 4.0D;
    private double minSpeed = 0.5D;





    private boolean allEntityPusher = true;






    public CraftEntityPushUtil(MinecartControl mC) {
        this.mC = mC;
    }









    @Override










    public void execute(Minecart minecart) {
        if (isMinecartEntityPusher(minecart)) {
            if (minecart.getNearbyEntities(pushRadius, 0, pushRadius) != null) {












                Vector vector = minecart.getVelocity();
                if (vector.length() > minSpeed) {
                    for (Entity entity : minecart.getNearbyEntities(pushRadius, 0, pushRadius)) {


                        Location loc = entity.getLocation();

                        if (entity == minecart.getPassenger()) {








                            continue;
                        }

                        if (vector.getX() > 0) {
                            loc.setX(loc.getX() - (pushRadius + 0.1D));



                        } else if (vector.getX() < 0) {
                            loc.setX(loc.getX() + (pushRadius + 0.1D));
                        } else if (vector.getZ() > 0) {
                            loc.setZ(loc.getZ() - (pushRadius + 0.1D));

                        } else if (vector.getZ() < 0) {
                            loc.setZ(loc.getZ() + (pushRadius + 0.1D));


                        }




                        entity.teleport(loc);
                    }
                }



            }
        }



    }









    @Override



    public boolean isEnable() {


        return enable;





    }







    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override



    public double getPushRadius() {
        return pushRadius;
    }

    @Override







    public void setPushRadius(double pushRadius) {








        this.pushRadius = pushRadius;
    }








    @Override




    public double getMinSpeed() {


        return minSpeed;




    }

    @Override
    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }

    @Override
    public void setAllMinecartsAreEntityPusher(boolean allEntityPusher) {
        this.allEntityPusher = allEntityPusher;






    }


    @Override
    public boolean areAllMineartsEntityPusher() {
        return allEntityPusher;





    }

    @Override
    public boolean isMinecartEntityPusher(Minecart minecart) {
        if (allEntityPusher) {
            return true;







        }

        if (minecart.getMetadata("minecartcontrol.isEntityPusher").size() > 0 ? minecart.getMetadata("minecartcontrol.isEntityPusher").get(0).asBoolean() : false) {
            return true;
        }
        return false;
    }

    @Override
    public void setMinecartEntityPusher(Minecart minecart, boolean nowEntityPusher) {
        minecart.setMetadata("minecartcontrol.isEntityPusher", new FixedMetadataValue((MinecartControl) mC, nowEntityPusher));
    }
}
