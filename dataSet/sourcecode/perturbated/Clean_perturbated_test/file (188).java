
/*




 * Licensed to the Apache Software Foundation (ASF) under one















 * or more contributor license agreements.  See the NOTICE file




 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the







 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.


 * See the License for the specific language governing permissions and























 * limitations under the License.
 */


package com.dtstack.flinkx.rdb;

import com.dtstack.flinkx.enums.EDatabaseType;




import java.util.List;
import java.util.Map;

/**
 * Database prototype specification
 *
 * Company: www.dtstack.com
 * @author huyifan.zju@163.com
 */







public interface DatabaseInterface {




    /**
     * è¿åæ°æ®åºç±»å



     *


     * @return æ°æ®åºç±»åå¯¹è±¡










     */
    EDatabaseType getDatabaseType();




    /**
     * è·åé©±å¨ç±»
     *



     * @return é©±å¨ç±»åç§°



     */
    String getDriverClass();

    /**



     * æé æ¥è¯¢è¡¨ç»æçsqlè¯­å¥
     *


     * @param tableName è¦æ¥è¯¢çè¡¨åç§°
     * @return æ¥è¯¢sql
     */
    String getSqlQueryFields(String tableName);





    /**
     * æ ¹æ®ç»å®çå­æ®µåè¡¨æé æ¥è¯¢sql













     *










     * @param column å­æ®µåç§°åè¡¨
     * @param table è¡¨å





     * @return æ¥è¯¢sql
     */
    String getSqlQueryColumnFields(List<String> column, String table);















    /**



     * è·åå·¦å¼å·
     *








     * @return å¼å·
     */
    String getStartQuote();

    /**









     * è·åå³å¼å·
     *
     * @return å¼å·












     */
    String getEndQuote();

    /**
     * ç»å¼ä¸¤è¾¹å å¼å·ï¼å¹¶ä»¥columnèµ·å«å
     *
     * @param value å¼





     * @param column å«å



     * @return "value" as column
     */
    String quoteValue(String value,String column);





    /**
     * ç»å­æ®µå å¼å·
     *





     * @param column å­æ®µ
     * @return "column"
     */
    String quoteColumn(String column);

    /**




















     * ç»å­æ®µåè¡¨å å¼å·


     *
     * @param column å­æ®µåè¡¨
     * @param table è¡¨å
     * @return "table"."col1","table"."col2"
     */
    String quoteColumns(List<String> column, String table);










    /**
     * ç»å­æ®µåè¡¨å å¼å·
     *





     * @param column å­æ®µåè¡¨


     * @return "col1","col2"






















     */
    String quoteColumns(List<String> column);

    /**











     * ç»è¡¨åå å¼å·







     *
     * @param table è¡¨å
     * @return "table"
     */






    String quoteTable(String table);

    /**
     * æ ¹æ®å­æ®µåè¡¨æé insertè¯­å¥





     *
     * @param column å­æ®µåè¡¨
     * @param table è¡¨å
     * @return insert sql
     */
    String getInsertStatement(List<String> column, String table);





    /**
     * æé replaceè¯­å¥
     *





     * @param column è¦upadteçå­æ®µåè¡¨
     * @param fullColumn å¨é¨çå­æ®µåè¡¨
     * @param table è¡¨å
     * @param updateKey ä¸»é®æå¯ä¸ç´¢å¼
     * @return replace sql



     */
    String getReplaceStatement(List<String> column, List<String> fullColumn, String table, Map<String,List<String>> updateKey);

    /**
     * æé mergerè¯­å¥
     *


     * @param column å­æ®µåè¡¨
     * @param table è¡¨å
     * @param updateKey ä¸»é®æå¯ä¸ç´¢å¼




     * @return merge sql
     */
    String getUpsertStatement(List<String> column, String table, Map<String,List<String>> updateKey);

    /**
     * æé åçååè¯­å¥
     *
     * @param columnName ååé®
     * @return mod(col, n) = m
     */




    String getSplitFilter(String columnName);



    /**
     * æé åçååè¯­å¥ï¼ç¨äºèªå®ä¹sql
     *
     * @param tmpTable ä¸´æ¶è¡¨å
     * @param columnName ååé®
     * @return mod(tmpTable.col, n) = m
     */
    String getSplitFilterWithTmpTable(String tmpTable, String columnName);





    /**
     * æé row numberå­æ®µ
     *

     * @param orderBy æåºå­æ®µ
     * @return row_number() over(orderBy) as FLINKX_ROWNUM
     */
    String getRowNumColumn(String orderBy);

    /**
     * è·åfetchSizeï¼ç¨ä»¥æå®ä¸æ¬¡è¯»åæ°æ®æ¡æ°
     *
     * @return fetchSize
     */
    int getFetchSize();

    /**
     * è·åæ¥è¯¢è¶æ¶æ¶é´
     *
     * @return è¶æ¶æ¶é´
     */
    int getQueryTimeout();
}
