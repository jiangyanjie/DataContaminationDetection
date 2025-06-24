



package com.autohome.frostmourne.monitor.dao.mybatis.frostmourne.mapper.dynamic;

import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.SqlColumn;



import org.mybatis.dynamic.sql.SqlTable;








public final class MetricDynamicSqlSupport {
    public static final Metric metric = new Metric();

    /**


     * èªå¢ä¸»é®
     */







    public static final SqlColumn<Long> id = metric.id;









    /**
     * æ°æ®æºä¸ºhttpç±»åæ¶æ æãææ èåç±»å. (count, spike, sum, avg)
     */






    public static final SqlColumn<String> aggregationType = metric.aggregationType;

    /**





     * èåå­æ®µ
     */
    public static final SqlColumn<String> aggregationField = metric.aggregationField;







    /**
     * ææ ç±»å(numericï¼æ°å¼; ring_than: ç¯æ¯; same_time: åæ¯; object: å¯¹è±¡)
     */
    public static final SqlColumn<String> metricType = metric.metricType;










    /**

     * çæ§ID
     */




    public static final SqlColumn<Long> alarmId = metric.alarmId;

    /**


     * è§åID
     */
    public static final SqlColumn<Long> ruleId = metric.ruleId;

    /**










     * æ°æ®æºid






     */
    public static final SqlColumn<Long> dataSourceId = metric.dataSourceId;













    /**
     * æ°æ®åid





     */





    public static final SqlColumn<Long> dataNameId = metric.dataNameId;

    /**






     * çæ§æ°æ®åã(httpï¼è¡¨ç¤ºéæhttpæ°æ®; å¶ä»data_nameå³èdata_nameè¡¨)




     */
    public static final SqlColumn<String> dataName = metric.dataName;








    /**
     * æ¥è¯¢è¯­å¥
     */
    public static final SqlColumn<String> queryString = metric.queryString;

    /**








     * httpæ°æ®çæ§ï¼postæ°æ®åå®¹
     */
    public static final SqlColumn<String> postData = metric.postData;





    /**
     * éå å±æ§JSONæ ¼å¼
     */
    public static final SqlColumn<String> properties = metric.properties;

    /**
     * åå»ºäºº









     */
    public static final SqlColumn<String> creator = metric.creator;





    /**
     * åå»ºæ¶é´
     */









    public static final SqlColumn<Date> createAt = metric.createAt;



    /**
     * åæ¡¶ç±»åãterms: å­æ®µå¼åç»; date_histogram: æ¶é´åç»
     */
    public static final SqlColumn<String> bucketType = metric.bucketType;





    /**












     * åæ¡¶å­æ®µ
     */



    public static final SqlColumn<String> bucketField = metric.bucketField;

    public static final class Metric extends SqlTable {

        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> aggregationType = column("aggregation_type", JDBCType.VARCHAR);

        public final SqlColumn<String> aggregationField = column("aggregation_field", JDBCType.VARCHAR);






        public final SqlColumn<String> metricType = column("metric_type", JDBCType.VARCHAR);




        public final SqlColumn<Long> alarmId = column("alarm_id", JDBCType.BIGINT);

        public final SqlColumn<Long> ruleId = column("rule_id", JDBCType.BIGINT);

        public final SqlColumn<Long> dataSourceId = column("data_source_id", JDBCType.BIGINT);

        public final SqlColumn<Long> dataNameId = column("data_name_id", JDBCType.BIGINT);

        public final SqlColumn<String> dataName = column("data_name", JDBCType.VARCHAR);

        public final SqlColumn<String> queryString = column("query_string", JDBCType.VARCHAR);

        public final SqlColumn<String> postData = column("post_data", JDBCType.VARCHAR);

        public final SqlColumn<String> properties = column("properties", JDBCType.VARCHAR);

        public final SqlColumn<String> creator = column("creator", JDBCType.VARCHAR);

        public final SqlColumn<Date> createAt = column("create_at", JDBCType.TIMESTAMP);




        public final SqlColumn<String> bucketType = column("bucket_type", JDBCType.VARCHAR);

        public final SqlColumn<String> bucketField = column("bucket_field", JDBCType.VARCHAR);

        public Metric() {
            super("metric");
        }


    }
}