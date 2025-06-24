package de.ar56te876mis.CraftMinecartControl.Util;

import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Utils.SensorUtil;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.InventoryHolder;

public class CraftSensorUtil implements SensorUtil{

    private final MinecartControl mC;

    public CraftSensorUtil(MinecartControl mC) {
        this.mC = mC;
    }

    @Override
    public boolean sensor(String line, Minecart minecart) {
        line = line.toLowerCase();

        if (line.equalsIgnoreCase("Standard")) {
            if (!(minecart instanceof StorageMinecart) && !(minecart instanceof PoweredMinecart)) {
                return true;
            }
        } else if (line.equalsIgnoreCase("Storage")) {
            if (minecart instanceof StorageMinecart) {
                return true;
            }
        } else if (line.equalsIgnoreCase("Powered")) {
            if (minecart instanceof PoweredMinecart) {
                return true;
            }
        } else if (line.equalsIgnoreCase("Hopper")) {
            if (minecart instanceof HopperMinecart) {
                return true;
            }
        } else if (line.equalsIgnoreCase("TNT") || line.equalsIgnoreCase("Explosive")) {
            if (minecart instanceof ExplosiveMinecart) {
                return true;
            }
        } else if (line.equalsIgnoreCase("Spawner")) {
            if (minecart instanceof SpawnerMinecart) {
                return true;
            }
        } else if (line.equalsIgnoreCase("Empty")) {
            if (minecart instanceof InventoryHolder) {
                InventoryHolder invMinecart = (InventoryHolder) minecart;
                for (int x = 0; x < invMinecart.getInventory().getSize(); x++) {
                    if (invMinecart.getInventory().getItem(x) != null) {
                        return false;
                    }
                }
                return true;
                
            } else if (minecart.isEmpty()) {
                return true;
            }
        } else if (line.equalsIgnoreCase("Player")) {
            if (minecart.getPassenger() instanceof Player) {
                return true;
            }
        } else if (line.equalsIgnoreCase("Mob")) {
            if (minecart.getPassenger() instanceof Monster || minecart.getPassenger() instanceof Animals) {
                return true;
            }
        } else if (line.equalsIgnoreCase("N")) {
            if (minecart.getVelocity().getZ() < 0) {
                return true;
            }
        } else if (line.equalsIgnoreCase("E")) {
            if (minecart.getVelocity().getX() > 0) {
                return true;
            }
        } else if (line.equalsIgnoreCase("S")) {
            if (minecart.getVelocity().getZ() > 0) {
                return true;
            }
        } else if (line.equalsIgnoreCase("W")) {
            if (minecart.getVelocity().getX() < 0) {
                return true;
            }
        } else if (line.startsWith("st_")) {
            if (minecart.getPassenger() instanceof Player) {
                String[] split = line.split("_");
                if (mC.getUtils().getStationUtil().getStationFromPlayer((Player) minecart.getPassenger()).toLowerCase().startsWith(split[1])) {
                    return true;
                }
            }
        } else if (line.startsWith("p_")) {
            if (minecart.getPassenger() instanceof Player) {
                String[] split = line.split("_");
                if (((Player) minecart.getPassenger()).getName().toLowerCase().startsWith(split[1])) {
                    return true;
                }
            }
        } else if (line.startsWith("i_")) {
            String[] split = line.split("_");
            if (split[1].contains("@")) {
                int id = 0;
                int amount = 0;
                String[] split1 = split[1].split("@");
                try {
                    amount = Integer.parseInt(split1[1]);
                    id = Integer.parseInt(split1[0]);
                } catch (NumberFormatException numberFormatException) {
                }
                if (id == 0 || amount == 0) {
                    return false;
                }
                if (minecart instanceof InventoryHolder) {
                    if (((InventoryHolder) minecart).getInventory().contains(id, amount)) {
                        return true;
                    }
                } else if (minecart.getPassenger() instanceof Player) {
                    if (((Player) minecart.getPassenger()).getInventory().contains(id, amount)) {
                        return true;
                    }
                }
            } else {
                int id = 0;
                try {
                    id = Integer.parseInt(split[1]);
                } catch (NumberFormatException numberFormatException) {
                }
                if (id == 0) {
                    return false;
                }
                if (minecart instanceof InventoryHolder) {
                    if (((InventoryHolder) minecart).getInventory().contains(id)) {
                        return true;
                    }
                } else if (minecart.getPassenger() instanceof Player) {
                    if (((Player) minecart.getPassenger()).getInventory().contains(id)) {
                        return true;
                    }
                }

            }
        } else if (line.startsWith("ih_")) {
            if (minecart.getPassenger() instanceof Player) {
                String[] split = line.split("_");
                if (split[1].contains("@")) {
                    String[] split1 = split[1].split("@");
                    int id = 0;
                    int amount = 0;
                    try {
                        id = Integer.parseInt(split1[0]);
                        amount = Integer.parseInt(split1[1]);
                    } catch (NumberFormatException numberFormatException) {
                    }
                    if (id == 0 || amount == 0) {
                        return false;
                    }

                    if (((Player) minecart.getPassenger()).getInventory().getItemInHand().getTypeId() == id && ((Player) minecart.getPassenger()).getInventory().getItemInHand().getAmount() >= amount) {
                        return true;
                    }
                } else {
                    int id = 0;
                    try {
                        id = Integer.parseInt(split[1]);
                    } catch (NumberFormatException numberFormatException) {
                    }
                    if (id == 0) {
                        return false;
                    }

                    if (((Player) minecart.getPassenger()).getInventory().getItemInHand().getTypeId() == id) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
