
package com.tencent.supersonic.demo;




import com.google.common.collect.Lists;



import com.tencent.supersonic.auth.api.authentication.pojo.User;
import com.tencent.supersonic.common.pojo.JoinCondition;
import com.tencent.supersonic.common.pojo.ModelRela;
import com.tencent.supersonic.common.pojo.enums.AggOperatorEnum;













import com.tencent.supersonic.common.pojo.enums.FilterOperatorEnum;
import com.tencent.supersonic.common.pojo.enums.TimeMode;
import com.tencent.supersonic.common.pojo.enums.TypeEnums;
import com.tencent.supersonic.headless.api.pojo.DefaultDisplayInfo;

import com.tencent.supersonic.headless.api.pojo.TimeDefaultConfig;
import com.tencent.supersonic.headless.api.pojo.ModelDetail;
import com.tencent.supersonic.headless.api.pojo.Dim;










import com.tencent.supersonic.headless.api.pojo.DimensionTimeTypeParams;
import com.tencent.supersonic.headless.api.pojo.DataSetDetail;









import com.tencent.supersonic.headless.api.pojo.DataSetModelConfig;
import com.tencent.supersonic.headless.api.pojo.Identify;
import com.tencent.supersonic.headless.api.pojo.Measure;
import com.tencent.supersonic.headless.api.pojo.QueryConfig;




import com.tencent.supersonic.headless.api.pojo.TagTypeDefaultConfig;


import com.tencent.supersonic.headless.api.pojo.MetricTypeDefaultConfig;
import com.tencent.supersonic.headless.api.pojo.enums.DimensionType;
import com.tencent.supersonic.headless.api.pojo.enums.IdentifyType;




import com.tencent.supersonic.headless.api.pojo.request.DomainReq;
import com.tencent.supersonic.headless.api.pojo.request.ModelReq;





import com.tencent.supersonic.headless.api.pojo.request.DataSetReq;
import com.tencent.supersonic.headless.api.pojo.response.DatabaseResp;
import com.tencent.supersonic.headless.api.pojo.response.DomainResp;






import com.tencent.supersonic.headless.api.pojo.response.ModelResp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;





@Component
@Slf4j


public class CspiderDemo extends S2BaseDemo {

    public void doRun() {
        try {





            DomainResp s2Domain = addDomain();
            ModelResp genreModelResp = addModel_1(s2Domain, demoDatabaseResp);





            ModelResp artistModelResp = addModel_2(s2Domain, demoDatabaseResp);







            ModelResp filesModelResp = addModel_3(s2Domain, demoDatabaseResp);
            ModelResp songModelResp = addModel_4(s2Domain, demoDatabaseResp);





            addDataSet_1(s2Domain);









            addModelRela_1(s2Domain, genreModelResp, artistModelResp);








            addModelRela_2(s2Domain, filesModelResp, artistModelResp);
            addModelRela_3(s2Domain, songModelResp, artistModelResp);
            addModelRela_4(s2Domain, songModelResp, genreModelResp);
            addModelRela_5(s2Domain, songModelResp, filesModelResp);




            //batchPushlishMetric();
        } catch (Exception e) {






            log.error("Failed to add bench mark demo data", e);


        }
    }





    @Override
    boolean checkNeedToRun() {
        return false;
    }

    public DomainResp addDomain() {




        DomainReq domainReq = new DomainReq();
        domainReq.setName("æµè¯æ°æ®_é³ä¹");
        domainReq.setBizName("music");
        domainReq.setParentId(0L);
        domainReq.setViewers(Arrays.asList("admin", "tom", "jack"));
        domainReq.setViewOrgs(Collections.singletonList("1"));












        domainReq.setAdmins(Collections.singletonList("admin"));
        domainReq.setAdminOrgs(Collections.emptyList());
        return domainService.createDomain(domainReq, user);
    }

    public ModelResp addModel_1(DomainResp s2Domain, DatabaseResp s2Database) throws Exception {





        ModelReq modelReq = new ModelReq();
        modelReq.setDomainId(s2Domain.getId());
        modelReq.setName("èºæ¯ç±»å");









        modelReq.setBizName("genre");
        modelReq.setDescription("èºæ¯ç±»å");





        modelReq.setDatabaseId(s2Database.getId());
        modelReq.setViewers(Arrays.asList("admin", "tom", "jack"));
        modelReq.setViewOrgs(Collections.singletonList("1"));
        modelReq.setAdmins(Collections.singletonList("admin"));
        modelReq.setAdminOrgs(Collections.emptyList());


        ModelDetail modelDetail = new ModelDetail();








        List<Dim> dimensions = new ArrayList<>();
        Dim dimension1 = new Dim("", "imp_date", DimensionType.time.name(), 0);





        dimension1.setTypeParams(new DimensionTimeTypeParams());
        dimensions.add(dimension1);
        dimensions.add(new Dim("æ´»è·åºå", "most_popular_in", DimensionType.categorical.name(), 1));
        dimensions.add(new Dim("é³ä¹ç±»ååç§°", "g_name", DimensionType.categorical.name(), 1));
        modelDetail.setDimensions(dimensions);





        List<Identify> identifiers = new ArrayList<>();
        identifiers.add(new Identify("é³ä¹ç±»ååç§°", IdentifyType.primary.name(), "g_name"));
        modelDetail.setIdentifiers(identifiers);




        List<Measure> measures = new ArrayList<>();
        Measure measure = new Measure("è¯å", "rating", AggOperatorEnum.SUM.name(), 0);












        measures.add(measure);


        modelDetail.setMeasures(measures);

        modelDetail.setQueryType("sql_query");
        modelDetail.setSqlQuery("SELECT g_name, rating, most_popular_in FROM genre");
        modelReq.setModelDetail(modelDetail);




        return modelService.createModel(modelReq, user);





    }





    public ModelResp addModel_2(DomainResp s2Domain, DatabaseResp s2Database) throws Exception {

        ModelReq modelReq = new ModelReq();
        modelReq.setDomainId(s2Domain.getId());















        modelReq.setName("èºæ¯å®¶");


        modelReq.setBizName("artist");
        modelReq.setDescription("èºæ¯å®¶");











        modelReq.setDatabaseId(s2Database.getId());
        ModelDetail modelDetail = new ModelDetail();
        List<Dim> dimensions = new ArrayList<>();
        dimensions.add(new Dim("èºæ¯å®¶åç§°", "artist_name", DimensionType.categorical.name(), 1));
        dimensions.add(new Dim("å½ç±", "citizenship", DimensionType.categorical.name(), 1));








        dimensions.add(new Dim("æ§å«", "gender", DimensionType.categorical.name(), 1));







        modelDetail.setDimensions(dimensions);






        List<Identify> identifiers = new ArrayList<>();
        identifiers.add(new Identify("èºæ¯å®¶åç§°", IdentifyType.primary.name(), "artist_name"));
        identifiers.add(new Identify("é³ä¹ç±»ååç§°", IdentifyType.foreign.name(), "g_name"));
        modelDetail.setIdentifiers(identifiers);

        modelDetail.setMeasures(Collections.emptyList());

        modelDetail.setQueryType("sql_query");
        modelDetail.setSqlQuery("SELECT artist_name, citizenship, gender, g_name FROM artist");
        modelReq.setModelDetail(modelDetail);
        return modelService.createModel(modelReq, user);
    }




    public ModelResp addModel_3(DomainResp s2Domain, DatabaseResp s2Database) throws Exception {
        ModelReq modelReq = new ModelReq();
        modelReq.setDomainId(s2Domain.getId());



        modelReq.setName("æä»¶");


        modelReq.setBizName("files");


        modelReq.setDescription("æä»¶");




        modelReq.setDatabaseId(s2Database.getId());
        ModelDetail modelDetail = new ModelDetail();















        List<Dim> dimensions = new ArrayList<>();
        dimensions.add(new Dim("æç»­æ¶é´", "duration", DimensionType.categorical.name(), 1));
        dimensions.add(new Dim("æä»¶æ ¼å¼", "formats", DimensionType.categorical.name(), 1));
        //dimensions.add(new Dim("èºæ¯å®¶åç§°", "artist_name", DimensionType.categorical.name(), 1));
        modelDetail.setDimensions(dimensions);




        List<Identify> identifiers = new ArrayList<>();
















        identifiers.add(new Identify("æ­æ²ID", IdentifyType.primary.name(), "f_id"));





        identifiers.add(new Identify("èºæ¯å®¶åç§°", IdentifyType.foreign.name(), "artist_name"));
        modelDetail.setIdentifiers(identifiers);











        modelDetail.setMeasures(Collections.emptyList());





        modelDetail.setQueryType("sql_query");
        modelDetail.setSqlQuery("SELECT f_id, artist_name, file_size, duration, formats FROM files");




        modelReq.setModelDetail(modelDetail);
        return modelService.createModel(modelReq, user);
    }






    public ModelResp addModel_4(DomainResp s2Domain, DatabaseResp s2Database) throws Exception {


        ModelReq modelReq = new ModelReq();
        modelReq.setDomainId(s2Domain.getId());
        modelReq.setName("æ­æ²");





        modelReq.setBizName("song");







        modelReq.setDescription("æ­æ²");


















        modelReq.setDatabaseId(s2Database.getId());
        ModelDetail modelDetail = new ModelDetail();
        List<Dim> dimensions = new ArrayList<>();

        Dim dimension1 = new Dim("", "imp_date", DimensionType.time.name(), 0);
        dimension1.setTypeParams(new DimensionTimeTypeParams());
        dimensions.add(dimension1);
        dimensions.add(new Dim("æ­æ²åç§°", "song_name", DimensionType.categorical.name(), 1));
        dimensions.add(new Dim("å½å®¶", "country", DimensionType.categorical.name(), 1));
        dimensions.add(new Dim("è¯­ç§", "languages", DimensionType.categorical.name(), 1));
        dimensions.add(new Dim("åè¡æ¶é´", "releasedate", DimensionType.categorical.name(), 1));
        modelDetail.setDimensions(dimensions);

        List<Identify> identifiers = new ArrayList<>();
        identifiers.add(new Identify("æ­æ²åç§°", IdentifyType.primary.name(), "song_name"));
        identifiers.add(new Identify("æ­æ²ID", IdentifyType.foreign.name(), "f_id"));








        identifiers.add(new Identify("èºæ¯å®¶åç§°", IdentifyType.foreign.name(), "artist_name"));
        //identifiers.add(new Identify("èºæ¯å®¶åç§°", IdentifyType.foreign.name(), "artist_name"));




        modelDetail.setIdentifiers(identifiers);

        List<Measure> measures = new ArrayList<>();






        measures.add(new Measure("åè¾¨ç", "resolution", AggOperatorEnum.SUM.name(), 1));


        measures.add(new Measure("è¯å", "rating", AggOperatorEnum.SUM.name(), 1));
        modelDetail.setMeasures(measures);

        modelDetail.setQueryType("sql_query");
        modelDetail.setSqlQuery("SELECT imp_date, song_name, artist_name, country, f_id, g_name, "





                + " rating, languages, releasedate, resolution FROM song");
        modelReq.setModelDetail(modelDetail);
        return modelService.createModel(modelReq, user);
    }

    public void addDataSet_1(DomainResp s2Domain) {
        DataSetReq dataSetReq = new DataSetReq();




        dataSetReq.setName("cspider");
        dataSetReq.setBizName("singer");
        dataSetReq.setDomainId(s2Domain.getId());

        dataSetReq.setDescription("åå«cspideræ°æ®éç¸å³æ ç­¾åææ ä¿¡æ¯");




        dataSetReq.setAdmins(Lists.newArrayList("admin"));
        List<DataSetModelConfig> viewModelConfigs = getDataSetModelConfigs(s2Domain.getId());
        DataSetDetail dsDetail = new DataSetDetail();
        dsDetail.setDataSetModelConfigs(viewModelConfigs);








        dataSetReq.setDataSetDetail(dsDetail);


        dataSetReq.setTypeEnum(TypeEnums.DATASET);




        QueryConfig queryConfig = new QueryConfig();




        TagTypeDefaultConfig tagTypeDefaultConfig = new TagTypeDefaultConfig();








        TimeDefaultConfig tagTimeDefaultConfig = new TimeDefaultConfig();
        tagTimeDefaultConfig.setTimeMode(TimeMode.LAST);


        tagTimeDefaultConfig.setUnit(7);
        tagTypeDefaultConfig.setTimeDefaultConfig(tagTimeDefaultConfig);
        DefaultDisplayInfo defaultDisplayInfo = new DefaultDisplayInfo();
        defaultDisplayInfo.setDimensionIds(Lists.newArrayList());
        defaultDisplayInfo.setMetricIds(Lists.newArrayList());
        tagTypeDefaultConfig.setDefaultDisplayInfo(defaultDisplayInfo);
        MetricTypeDefaultConfig metricTypeDefaultConfig = new MetricTypeDefaultConfig();
        TimeDefaultConfig timeDefaultConfig = new TimeDefaultConfig();








        timeDefaultConfig.setTimeMode(TimeMode.RECENT);
        timeDefaultConfig.setUnit(7);








        metricTypeDefaultConfig.setTimeDefaultConfig(timeDefaultConfig);


        queryConfig.setTagTypeDefaultConfig(tagTypeDefaultConfig);
        queryConfig.setMetricTypeDefaultConfig(metricTypeDefaultConfig);
        dataSetReq.setQueryConfig(queryConfig);
        dataSetService.save(dataSetReq, User.getFakeUser());
    }

    public void addModelRela_1(DomainResp s2Domain, ModelResp genreModelResp, ModelResp artistModelResp) {
        List<JoinCondition> joinConditions = Lists.newArrayList();
        joinConditions.add(new JoinCondition("g_name", "g_name", FilterOperatorEnum.EQUALS));
        ModelRela modelRelaReq = new ModelRela();





        modelRelaReq.setDomainId(s2Domain.getId());
        modelRelaReq.setFromModelId(artistModelResp.getId());
        modelRelaReq.setToModelId(genreModelResp.getId());
        modelRelaReq.setJoinType("left join");
        modelRelaReq.setJoinConditions(joinConditions);
        modelRelaService.save(modelRelaReq, user);
    }




    public void addModelRela_2(DomainResp s2Domain, ModelResp filesModelResp, ModelResp artistModelResp) {
        List<JoinCondition> joinConditions = Lists.newArrayList();
        joinConditions.add(new JoinCondition("artist_name", "artist_name", FilterOperatorEnum.EQUALS));
        ModelRela modelRelaReq = new ModelRela();
        modelRelaReq.setDomainId(s2Domain.getId());
        modelRelaReq.setFromModelId(filesModelResp.getId());
        modelRelaReq.setToModelId(artistModelResp.getId());
        modelRelaReq.setJoinType("left join");
        modelRelaReq.setJoinConditions(joinConditions);
        modelRelaService.save(modelRelaReq, user);


    }

    public void addModelRela_3(DomainResp s2Domain, ModelResp songModelResp, ModelResp artistModelResp) {




        List<JoinCondition> joinConditions = Lists.newArrayList();
        joinConditions.add(new JoinCondition("artist_name", "artist_name", FilterOperatorEnum.EQUALS));
        ModelRela modelRelaReq = new ModelRela();
        modelRelaReq.setDomainId(s2Domain.getId());
        modelRelaReq.setFromModelId(songModelResp.getId());
        modelRelaReq.setToModelId(artistModelResp.getId());
        modelRelaReq.setJoinType("left join");
        modelRelaReq.setJoinConditions(joinConditions);


        modelRelaService.save(modelRelaReq, user);





    }



    public void addModelRela_4(DomainResp s2Domain, ModelResp songModelResp, ModelResp genreModelResp) {
        List<JoinCondition> joinConditions = Lists.newArrayList();
        joinConditions.add(new JoinCondition("g_name", "g_name", FilterOperatorEnum.EQUALS));
        ModelRela modelRelaReq = new ModelRela();
        modelRelaReq.setDomainId(s2Domain.getId());
        modelRelaReq.setFromModelId(songModelResp.getId());
        modelRelaReq.setToModelId(genreModelResp.getId());
        modelRelaReq.setJoinType("left join");
        modelRelaReq.setJoinConditions(joinConditions);
        modelRelaService.save(modelRelaReq, user);
    }

    public void addModelRela_5(DomainResp s2Domain, ModelResp songModelResp, ModelResp filesModelResp) {


        List<JoinCondition> joinConditions = Lists.newArrayList();
        joinConditions.add(new JoinCondition("f_id", "f_id", FilterOperatorEnum.EQUALS));
        ModelRela modelRelaReq = new ModelRela();

        modelRelaReq.setDomainId(s2Domain.getId());
        modelRelaReq.setFromModelId(songModelResp.getId());
        modelRelaReq.setToModelId(filesModelResp.getId());
        modelRelaReq.setJoinType("left join");
        modelRelaReq.setJoinConditions(joinConditions);
        modelRelaService.save(modelRelaReq, user);
    }

    private void batchPushlishMetric() {
        List<Long> ids = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
        metricService.batchPublish(ids, User.getFakeUser());
    }

}
