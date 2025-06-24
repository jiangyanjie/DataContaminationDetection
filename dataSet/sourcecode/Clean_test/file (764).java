package de.ar56te876mis.CraftMinecartControl.Util;

import de.ar56te876mis.CraftMinecartControl.Control.CraftControlBlock;
import de.ar56te876mis.CraftMinecartControl.MinecartControl;
import de.ar56te876mis.MinecartControl.Control.ControlBlock;
import de.ar56te876mis.MinecartControl.Utils.ConfigUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class CraftConfigUtil implements ConfigUtil {

    private final MinecartControl mC;
    private YamlConfiguration confighandle;
    private boolean usePermissions = true;
    private List<String> disableWorlds = new ArrayList<String>();

    public CraftConfigUtil(MinecartControl mC) {
        this.mC = mC;
    }

    @Override
    public void loadControlBlockConfig() {
        File blockConfig = new File("./plugins/MinecartControl/blockConfig.yml");
        if (blockConfig.exists()) {
            confighandle = YamlConfiguration.loadConfiguration(blockConfig);

            for (String blockName : confighandle.getKeys(false)) {
                ConfigurationSection path = confighandle.getConfigurationSection(blockName);
                if (path == null) {
                    continue;
                }

                int blockId = path.getInt("blockid", 0);
                String jobName = path.getString("jobName", "");
                String job = path.getString("job", "");

                if (blockId != 0) {
                    mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(blockId, blockName, jobName, job));
                }

            }

            if (mC.getBlockControlManager().getControlBlockList().isEmpty()) {
                setDefaultBlocks();
                saveControlBlockConfig();
            }
        } else {
            setDefaultBlocks();
            saveControlBlockConfig();
        }
    }

    @Override
    public void saveControlBlockConfig() {
        confighandle = new YamlConfiguration();
        File blockConfig = new File("./plugins/MinecartControl/blockConfig.yml");
        ArrayList<ControlBlock> controlBlockList = mC.getBlockControlManager().getControlBlockList();

        for (int x = 0; x < controlBlockList.size(); x++) {

            String blockName = controlBlockList.get(x).getName();

            confighandle.set(blockName + ".blockid", controlBlockList.get(x).getBlockId());
            confighandle.set(blockName + ".jobName", controlBlockList.get(x).getJobName());
            confighandle.set(blockName + ".job", controlBlockList.get(x).getJob());

        }

        try {
            confighandle.save(blockConfig);
        } catch (IOException e) {
            System.out.println("Error by saving");
        }

    }

    @Override
    public void saveConfig() {
        confighandle = new YamlConfiguration();
        File config = new File("./plugins/MinecartControl/config.yml");

        confighandle.set(".usepermissions", usePermissions);
        confighandle.set("entityPusher.enable", mC.getUtils().getEntityPushUtil().isEnable());
        confighandle.set("entityPusher.pushradius", mC.getUtils().getEntityPushUtil().getPushRadius());
        confighandle.set("entityPusher.minspeed", mC.getUtils().getEntityPushUtil().getMinSpeed());
        confighandle.set("entityPusher.allMinecartsAreEntityPusher", mC.getUtils().getEntityPushUtil().areAllMineartsEntityPusher());
        confighandle.set("version", "1");
        confighandle.set("disableworlds", disableWorlds);



        try {
            confighandle.save(config);
        } catch (IOException e) {
            System.out.println("Error by saving");
        }

    }

    @Override
    public void loadConfig() {
        File config = new File("./plugins/MinecartControl/config.yml");
        if (config.exists()) {
            confighandle = YamlConfiguration.loadConfiguration(config);

            ConfigurationSection path = confighandle;
            usePermissions = path.getBoolean("usepermissions", true);

            disableWorlds = (ArrayList<String>) path.getStringList("disableworlds");

            
            path = confighandle.getConfigurationSection("entityPusher");
            if (path == null) {
                saveConfig();
                path = confighandle.getConfigurationSection("entityPusher");
            }
            mC.getUtils().getEntityPushUtil().setEnable(path.getBoolean("enable", true));
            mC.getUtils().getEntityPushUtil().setPushRadius(path.getDouble("pushradius", 4.0D));
            mC.getUtils().getEntityPushUtil().setMinSpeed(path.getDouble("minspeed", 0.5D));
            mC.getUtils().getEntityPushUtil().setAllMinecartsAreEntityPusher(path.getBoolean("allMinecartsAreEntityPusher", false));

            updateConfig(confighandle.getString("version", ""));

        } else {
            saveConfig();
        }
    }

    @Override
    public boolean usePermissions() {
        return usePermissions;
    }

    @Override
    public List<String> getDisableWorlds() {
        return disableWorlds;
    }

    public void setDefaultBlocks() {
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(41, "BoostBlock", "Boost", "2"));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(88, "BreakBlock", "Boost", "0.4"));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(22, "ClearBlock", "Clear", ""));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(42, "EjectBlock", "Eject", ""));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(57, "ElevatorBlock", "Elevator", ""));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(35, "ReverseBlock", "Reverse", ""));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(15, "GrabBlock", "Grab", "3"));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(121, "HealBlock", "Heal", ""));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(48, "KillBlock", "Kill", ""));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(24, "JumpBlock", "Jump", "2"));
        mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(49, "StationBlock", "Station", ""));
    }

    private void updateConfig(String ver) {
        if (ver.isEmpty()) {
            mC.getBlockControlManager().registerControlBlock(new CraftControlBlock(49, "StationBlock", "Station", ""));
            saveConfig();
            saveControlBlockConfig();
        }
    }
}
