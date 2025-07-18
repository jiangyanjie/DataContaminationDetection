


package com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.mapper.dynamic;













import static com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.mapper.dynamic.MetricDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;








import com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.domain.generate.Metric;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.DeleteProvider;





import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;



import org.apache.ibatis.annotations.Results;







import org.apache.ibatis.annotations.SelectKey;





import org.apache.ibatis.annotations.SelectProvider;


import org.apache.ibatis.annotations.UpdateProvider;



import org.apache.ibatis.type.JdbcType;



import org.mybatis.dynamic.sql.BasicColumn;


import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;

import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;




import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;





import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;














@Mapper
public interface MetricDynamicMapper {



    BasicColumn[] selectList = BasicColumn.columnList(id, aggregationType, aggregationField, metricType, alarmId, ruleId, dataSourceId, dataNameId, dataName, queryString, postData, properties, creator, createAt, bucketType, bucketField);





    @SelectProvider(type=SqlProviderAdapter.class, method="select")






    long count(SelectStatementProvider selectStatement);

    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);
















    @InsertProvider(type=SqlProviderAdapter.class, method="insert")

    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Long.class)
    int insert(InsertStatementProvider<Metric> insertStatement);









    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("MetricResult")







    Optional<Metric> selectOne(SelectStatementProvider selectStatement);




    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="MetricResult", value = {




        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="aggregation_type", property="aggregationType", jdbcType=JdbcType.VARCHAR),
        @Result(column="aggregation_field", property="aggregationField", jdbcType=JdbcType.VARCHAR),
        @Result(column="metric_type", property="metricType", jdbcType=JdbcType.VARCHAR),
        @Result(column="alarm_id", property="alarmId", jdbcType=JdbcType.BIGINT),








        @Result(column="rule_id", property="ruleId", jdbcType=JdbcType.BIGINT),






        @Result(column="data_source_id", property="dataSourceId", jdbcType=JdbcType.BIGINT),


        @Result(column="data_name_id", property="dataNameId", jdbcType=JdbcType.BIGINT),







        @Result(column="data_name", property="dataName", jdbcType=JdbcType.VARCHAR),


        @Result(column="query_string", property="queryString", jdbcType=JdbcType.VARCHAR),


        @Result(column="post_data", property="postData", jdbcType=JdbcType.VARCHAR),
        @Result(column="properties", property="properties", jdbcType=JdbcType.VARCHAR),
        @Result(column="creator", property="creator", jdbcType=JdbcType.VARCHAR),







        @Result(column="create_at", property="createAt", jdbcType=JdbcType.TIMESTAMP),




        @Result(column="bucket_type", property="bucketType", jdbcType=JdbcType.VARCHAR),




        @Result(column="bucket_field", property="bucketField", jdbcType=JdbcType.VARCHAR)


    })
    List<Metric> selectMany(SelectStatementProvider selectStatement);




    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    default long count(CountDSLCompleter completer) {


        return MyBatis3Utils.countFrom(this::count, metric, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, metric, completer);
    }








    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }





    default int insert(Metric record) {

        return MyBatis3Utils.insert(this::insert, record, metric, c ->



            c.map(aggregationType).toProperty("aggregationType")
            .map(aggregationField).toProperty("aggregationField")
            .map(metricType).toProperty("metricType")


            .map(alarmId).toProperty("alarmId")
            .map(ruleId).toProperty("ruleId")
            .map(dataSourceId).toProperty("dataSourceId")
            .map(dataNameId).toProperty("dataNameId")





            .map(dataName).toProperty("dataName")
            .map(queryString).toProperty("queryString")
            .map(postData).toProperty("postData")
            .map(properties).toProperty("properties")
            .map(creator).toProperty("creator")
            .map(createAt).toProperty("createAt")
            .map(bucketType).toProperty("bucketType")


            .map(bucketField).toProperty("bucketField")
        );
    }











    default int insertSelective(Metric record) {





        return MyBatis3Utils.insert(this::insert, record, metric, c ->
            c.map(aggregationType).toPropertyWhenPresent("aggregationType", record::getAggregationType)
            .map(aggregationField).toPropertyWhenPresent("aggregationField", record::getAggregationField)
            .map(metricType).toPropertyWhenPresent("metricType", record::getMetricType)
            .map(alarmId).toPropertyWhenPresent("alarmId", record::getAlarmId)







            .map(ruleId).toPropertyWhenPresent("ruleId", record::getRuleId)








            .map(dataSourceId).toPropertyWhenPresent("dataSourceId", record::getDataSourceId)
            .map(dataNameId).toPropertyWhenPresent("dataNameId", record::getDataNameId)
            .map(dataName).toPropertyWhenPresent("dataName", record::getDataName)

            .map(queryString).toPropertyWhenPresent("queryString", record::getQueryString)


            .map(postData).toPropertyWhenPresent("postData", record::getPostData)




            .map(properties).toPropertyWhenPresent("properties", record::getProperties)


            .map(creator).toPropertyWhenPresent("creator", record::getCreator)




            .map(createAt).toPropertyWhenPresent("createAt", record::getCreateAt)
            .map(bucketType).toPropertyWhenPresent("bucketType", record::getBucketType)
            .map(bucketField).toPropertyWhenPresent("bucketField", record::getBucketField)




        );
    }



    default Optional<Metric> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, metric, completer);
    }





    default List<Metric> select(SelectDSLCompleter completer) {


        return MyBatis3Utils.selectList(this::selectMany, selectList, metric, completer);
    }



    default List<Metric> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, metric, completer);
    }

    default Optional<Metric> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))




        );








    }







    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, metric, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(Metric record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(aggregationType).equalTo(record::getAggregationType)
                .set(aggregationField).equalTo(record::getAggregationField)
                .set(metricType).equalTo(record::getMetricType)
                .set(alarmId).equalTo(record::getAlarmId)








                .set(ruleId).equalTo(record::getRuleId)
                .set(dataSourceId).equalTo(record::getDataSourceId)
                .set(dataNameId).equalTo(record::getDataNameId)
                .set(dataName).equalTo(record::getDataName)
                .set(queryString).equalTo(record::getQueryString)





                .set(postData).equalTo(record::getPostData)



                .set(properties).equalTo(record::getProperties)
                .set(creator).equalTo(record::getCreator)







                .set(createAt).equalTo(record::getCreateAt)
                .set(bucketType).equalTo(record::getBucketType)
                .set(bucketField).equalTo(record::getBucketField);
    }



    static UpdateDSL<UpdateModel> updateSelectiveColumns(Metric record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(aggregationType).equalToWhenPresent(record::getAggregationType)
                .set(aggregationField).equalToWhenPresent(record::getAggregationField)
                .set(metricType).equalToWhenPresent(record::getMetricType)
                .set(alarmId).equalToWhenPresent(record::getAlarmId)
                .set(ruleId).equalToWhenPresent(record::getRuleId)
                .set(dataSourceId).equalToWhenPresent(record::getDataSourceId)
                .set(dataNameId).equalToWhenPresent(record::getDataNameId)


                .set(dataName).equalToWhenPresent(record::getDataName)
                .set(queryString).equalToWhenPresent(record::getQueryString)
                .set(postData).equalToWhenPresent(record::getPostData)
                .set(properties).equalToWhenPresent(record::getProperties)
                .set(creator).equalToWhenPresent(record::getCreator)




                .set(createAt).equalToWhenPresent(record::getCreateAt)
                .set(bucketType).equalToWhenPresent(record::getBucketType)
                .set(bucketField).equalToWhenPresent(record::getBucketField);
    }

    default int updateByPrimaryKey(Metric record) {
        return update(c ->
            c.set(aggregationType).equalTo(record::getAggregationType)
            .set(aggregationField).equalTo(record::getAggregationField)










            .set(metricType).equalTo(record::getMetricType)



            .set(alarmId).equalTo(record::getAlarmId)
            .set(ruleId).equalTo(record::getRuleId)
            .set(dataSourceId).equalTo(record::getDataSourceId)
            .set(dataNameId).equalTo(record::getDataNameId)
            .set(dataName).equalTo(record::getDataName)
            .set(queryString).equalTo(record::getQueryString)


            .set(postData).equalTo(record::getPostData)
            .set(properties).equalTo(record::getProperties)
            .set(creator).equalTo(record::getCreator)
            .set(createAt).equalTo(record::getCreateAt)
            .set(bucketType).equalTo(record::getBucketType)
            .set(bucketField).equalTo(record::getBucketField)
            .where(id, isEqualTo(record::getId))


        );
    }

    default int updateByPrimaryKeySelective(Metric record) {
        return update(c ->
            c.set(aggregationType).equalToWhenPresent(record::getAggregationType)
            .set(aggregationField).equalToWhenPresent(record::getAggregationField)
            .set(metricType).equalToWhenPresent(record::getMetricType)
            .set(alarmId).equalToWhenPresent(record::getAlarmId)
            .set(ruleId).equalToWhenPresent(record::getRuleId)
            .set(dataSourceId).equalToWhenPresent(record::getDataSourceId)
            .set(dataNameId).equalToWhenPresent(record::getDataNameId)
            .set(dataName).equalToWhenPresent(record::getDataName)
            .set(queryString).equalToWhenPresent(record::getQueryString)
            .set(postData).equalToWhenPresent(record::getPostData)
            .set(properties).equalToWhenPresent(record::getProperties)
            .set(creator).equalToWhenPresent(record::getCreator)
            .set(createAt).equalToWhenPresent(record::getCreateAt)
            .set(bucketType).equalToWhenPresent(record::getBucketType)
            .set(bucketField).equalToWhenPresent(record::getBucketField)
            .where(id, isEqualTo(record::getId))
        );
    }
}