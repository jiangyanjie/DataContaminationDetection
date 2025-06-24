package com.akto.data_actor;

import com.akto.dto.*;
import com.akto.dto.billing.Organization;
import com.akto.dto.runtime_filters.RuntimeFilter;
import com.akto.dto.traffic.SampleData;
import com.akto.dto.traffic.TrafficInfo;
import com.akto.dto.type.SingleTypeInfo;
import com.mongodb.client.model.WriteModel;

import java.util.List;

public abstract class DataActor {

    public abstract AccountSettings fetchAccountSettings();

    public abstract long fetchEstimatedDocCount();

    public abstract void updateCidrList(List<String> cidrList);

    public abstract void updateApiCollectionNameForVxlan(int vxlanId, String name);

    public abstract APIConfig fetchApiConfig(String configName);

    public abstract void bulkWriteSingleTypeInfo(List<Object> writesForApiInfo);

    public abstract void bulkWriteSensitiveParamInfo(List<Object> writesForSensitiveParamInfo);

    public abstract void bulkWriteSampleData(List<Object> writesForSampleData);

    public abstract void bulkWriteSensitiveSampleData(List<Object> writesForSensitiveSampleData);

    public abstract void bulkWriteTrafficInfo(List<Object> writesForTrafficInfo);

    public abstract void bulkWriteTrafficMetrics(List<Object> writesForTrafficInfo);

    public abstract List<SingleTypeInfo> fetchStiOfCollections(int batchCount, int lastStiFetchTs);

    public abstract List<SingleTypeInfo> fetchAllStis(int batchCount, int lastStiFetchTs);

    public abstract List<SensitiveParamInfo> getUnsavedSensitiveParamInfos();

    public abstract List<CustomDataType> fetchCustomDataTypes();

    public abstract List<AktoDataType> fetchAktoDataTypes();

    public abstract List<CustomAuthType> fetchCustomAuthTypes();

    public abstract List<ApiInfo> fetchApiInfos();

    public abstract List<ApiInfo> fetchNonTrafficApiInfos();

    public abstract void bulkWriteApiInfo(List<ApiInfo> apiInfoList);

    public abstract List<RuntimeFilter> fetchRuntimeFilters();

    public abstract void updateRuntimeVersion(String fieldName, String version);

    public abstract Account fetchActiveAccount();

    public abstract void updateKafkaIp(String currentInstanceIp);

    public abstract List<ApiInfo.ApiInfoKey> fetchEndpointsInCollection();

    public abstract List<ApiCollection> fetchApiCollections();

    public abstract void createCollectionSimple(int vxlanId);

    public abstract void createCollectionForHost(String host, int colId);

    public abstract AccountSettings fetchAccountSettingsForAccount(int accountId);
    
    public abstract void insertRuntimeLog(Log log);

    public abstract void insertAnalyserLog(Log log);

    public abstract void modifyHybridSaasSetting(boolean isHybridSaas);

    public abstract Setup fetchSetup();

    public abstract Organization fetchOrganization(int accountId);

}
