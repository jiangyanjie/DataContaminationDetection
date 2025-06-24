package de.ar56te876mis.CraftMinecartControl.Util;

import de.ar56te876mis.MinecartControl.Utils.ConfigUtil;
import de.ar56te876mis.MinecartControl.Utils.SearchUtil;
import de.ar56te876mis.MinecartControl.Utils.VectorUtil;
import de.ar56te876mis.MinecartControl.Utils.StationUtil;
import de.ar56te876mis.MinecartControl.Utils.ItemTransferUtil;
import de.ar56te876mis.MinecartControl.Utils.OtherUtils;
import de.ar56te876mis.MinecartControl.Utils.SensorUtil;
import de.ar56te876mis.MinecartControl.Utils.Utils;
import de.ar56te876mis.MinecartControl.Utils.OneLocOneActionUtil;
import de.ar56te876mis.MinecartControl.Utils.MinecartLockUtil;
import de.ar56te876mis.MinecartControl.Utils.EntityPushUtil;
import de.ar56te876mis.MinecartControl.Utils.TeleportUtil;
import de.ar56te876mis.CraftMinecartControl.MinecartControl;

public class CraftUtils implements Utils{
    
    private final OneLocOneActionUtil oneLocOneActionUtil = new CraftOneLocOneActionUtil();
    private final SearchUtil searchUtil = new CraftSearchUtil();
    private final VectorUtil vectorUtil;
    private final OtherUtils otherUtils;
    private final MinecartLockUtil minecartLockUtil;
    private final EntityPushUtil EntityPushUtil;
    private final SensorUtil sensorUtil;
    private final StationUtil stationUtil = new CraftStationUtil();
    private final ConfigUtil configUtil;
    private final ItemTransferUtil itemTransferUtil = new CraftItemTransferUtil();
    private final TeleportUtil teleportUtil;

    private final MinecartControl mC;
    
    public CraftUtils(MinecartControl mC){
        this.mC = mC;
        sensorUtil = new CraftSensorUtil((MinecartControl) mC);
        configUtil = new CraftConfigUtil(mC);
        minecartLockUtil = new CraftMinecartLockUtil((MinecartControl) mC);
        EntityPushUtil = new CraftEntityPushUtil((MinecartControl) mC);
        teleportUtil = new CraftTeleportUtil(mC);
        vectorUtil = new CraftVectorUtil(mC);
        otherUtils = new CraftOtherUtils(mC);
    }
    
    

    @Override
    public OneLocOneActionUtil getOneLocOneActionUtil() {
        return oneLocOneActionUtil;
    }

    @Override
    public SearchUtil getSearchUtil() {
        return searchUtil;
    }
    
    @Override
    public VectorUtil getVectorUtil(){
        return vectorUtil;
    }

    @Override
    public OtherUtils getOtherUtils() {
        return otherUtils;
    }

    @Override
    public MinecartLockUtil getMinecartLockUtil() {
        return minecartLockUtil;
    }

    @Override
    public EntityPushUtil getEntityPushUtil() {
        return EntityPushUtil;
    }

    @Override
    public SensorUtil getSensorUtil() {
        return sensorUtil;
    }

    @Override
    public StationUtil getStationUtil() {
        return stationUtil;
    }

    @Override
    public ConfigUtil getConfigUtil() {
        return configUtil;
    }
    public ItemTransferUtil getItemTransferUtil() {
        return itemTransferUtil;
    }

    public TeleportUtil getTeleportUtil() {
        return teleportUtil;
    }

    
}
