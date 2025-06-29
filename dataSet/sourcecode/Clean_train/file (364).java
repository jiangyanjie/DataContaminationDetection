package com.besscroft.diyfile.storage.service.base;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.besscroft.diyfile.cache.DiyCache;
import com.besscroft.diyfile.common.constant.CacheConstants;
import com.besscroft.diyfile.common.constant.storage.OneDriveConstants;
import com.besscroft.diyfile.common.enums.StorageTypeEnum;
import com.besscroft.diyfile.common.exception.DiyFileException;
import com.besscroft.diyfile.common.param.storage.init.OneDriveParam;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.OkHttps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Description OneDrive 基础服务
 * @Author Bess Croft
 * @Date 2023/2/15 10:40
 */
@Slf4j
public abstract class AbstractOneDriveBaseService<T extends OneDriveParam> extends AbstractFileBaseService<T> {

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
        refreshAccessToken();
        initialized = true;
    }

    @Override
    public Integer getStorageType() {
        return StorageTypeEnum.ONE_DRIVE.getValue();
    }

    /**
     * 获取 OneDrive 上载会话
     * @param folderPath 文件路径
     * @return 会话
     */
    @Override
    public abstract String getUploadSession(String folderPath);

    /**
     * 在驱动器内移动项目，可能是文件或文件夹
     * @param startPath 开始路径
     * @param endPath 结束路径
     */
    @Override
    public abstract void moveItem(String startPath, String endPath);

    /**
     * 获取项目 ItemId
     * @param path 路径
     * @return 项目 ItemId
     */
    protected String getItemId(String path) {
        String itemUrl = OneDriveConstants.ITEM_URL.replace("{path}", path);
        JSONObject result = JSONUtil.parseObj(OkHttps.sync(itemUrl)
                .addHeader("Authorization", getAccessToken())
                .get().getBody().toString());
        String itemId = result.getStr("id");
        log.info("获取 OneDrive 项目 id：{}", itemId);
        return itemId;
    }

    /**
     * 获取 OneDrive 驱动 id
     * @return OneDrive 驱动 id
     */
    protected String getDriveId() {
        return Optional.ofNullable(DiyCache.getDiyKey(CacheConstants.ONEDRIVE_DRIVE_ID + storageId))
                .orElseGet(this::getDriveIdRest).toString();
    }

    /**
     * 通过 Rest 接口获取 OneDrive 驱动 id
     * @return OneDrive 驱动 id
     */
    private String getDriveIdRest() {
        String driveRootUrl = OneDriveConstants.DRIVE_ID_URL;
        JSONObject result = JSONUtil.parseObj(OkHttps.sync(driveRootUrl)
                .addHeader("Authorization", getAccessToken())
                .get().getBody().toString());
        try {
            Map map = objectMapper.readValue(result.getStr("parentReference"), Map.class);
            String driveId = map.get("driveId").toString();
            DiyCache.putDiyKey(CacheConstants.ONEDRIVE_DRIVE_ID + storageId, driveId);
            return driveId;
        } catch (JsonProcessingException e) {
            log.error("获取 OneDrive 驱动 id 失败！");
            return "";
        }
    }

    /**
     * 刷新 token 并返回新的 token
     * @return 新的 token
     */
    protected String getAccessToken() {
        // 先从缓存中获取 token，如果没有则从调用 REST API 获取
        return Optional.ofNullable(DiyCache.getDiyKey(CacheConstants.ONEDRIVE_TOKEN + storageId))
                .orElseGet(this::refreshAccessToken).toString();
    }

    /**
     * 刷新 accessToken
     * @return 新的 accessToken
     */
    protected String refreshAccessToken() {
        OneDriveParam param = getInitParam();
        Map<String, String> map = new HashMap<>();
        map.put("client_id", param.getClientId());
        map.put("scope", "user.read files.read.all offline_access");
        map.put("refresh_token", param.getRefreshToken());
        map.put("grant_type", "refresh_token");
        map.put("client_secret", param.getClientSecret());
        try {
            HttpResult result = OkHttps.sync(OneDriveConstants.AUTHENTICATE_URL)
                    .setBodyPara(map)
                    .post();
            Map tokenResult = objectMapper.readValue(result.getBody().toString(), Map.class);
            String accessToken = tokenResult.get("access_token").toString();
            DiyCache.putDiyKey(CacheConstants.ONEDRIVE_TOKEN + getStorageId(), accessToken);
            log.info("accessToken 刷新成功:{}", accessToken);
            return accessToken;
        } catch (Exception e) {
            throw new DiyFileException(e.getMessage());
        }
    }

}
