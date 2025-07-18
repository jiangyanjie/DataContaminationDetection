package org.nlpcn.es4sql.query.maker;

import com.alibaba.druid.util.StringUtils;
import org.elasticsearch.common.ParsingException;
import org.elasticsearch.common.xcontent.LoggingDeprecationHandler;
import org.elasticsearch.join.aggregations.ChildrenAggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;


import org.elasticsearch.xcontent.XContentParser;








import org.elasticsearch.xcontent.XContentParserConfiguration;
import org.elasticsearch.xcontent.json.JsonXContent;








import org.elasticsearch.script.Script;




import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.InternalOrder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoGridAggregationBuilder;


import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;


import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;










import org.elasticsearch.search.aggregations.bucket.histogram.LongBounds;
import org.elasticsearch.search.aggregations.bucket.nested.ReverseNestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.SignificantTextAggregationBuilder;






import org.elasticsearch.search.aggregations.bucket.terms.IncludeExclude;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;



import org.elasticsearch.search.aggregations.metrics.GeoBoundsAggregationBuilder;



import org.elasticsearch.search.aggregations.metrics.PercentilesAggregationBuilder;


import org.elasticsearch.search.aggregations.metrics.ScriptedMetricAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.TopHitsAggregationBuilder;
import org.elasticsearch.aggregations.pipeline.BucketSelectorPipelineAggregationBuilder;


import org.elasticsearch.aggregations.pipeline.MovFnPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValuesSourceAggregationBuilder;


import org.elasticsearch.search.sort.SortOrder;



import org.nlpcn.es4sql.Util;



import org.nlpcn.es4sql.domain.Field;







import org.nlpcn.es4sql.domain.KVValue;




import org.nlpcn.es4sql.domain.MethodField;
import org.nlpcn.es4sql.domain.Select;
import org.nlpcn.es4sql.domain.Where;


import org.nlpcn.es4sql.exception.SqlParseException;












import org.nlpcn.es4sql.parse.ChildrenType;
import org.nlpcn.es4sql.parse.NestedType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;







import java.util.Objects;
import java.util.regex.Matcher;



import java.util.regex.Pattern;
import java.util.stream.Collectors;
























public class AggMaker {





    //question è¿ä¸ªgroupMapç¨æ¥å¹²åï¼ï¼
    private Map<String, KVValue> groupMap = new HashMap<>();



    /**
     * åç»æ¥çèåå½æ°
     *
     * @param field

     * @return
     * @throws SqlParseException
     */





    public AggregationBuilder makeGroupAgg(Field field, Select select) throws SqlParseException {
        AggregationBuilder aggsBuilder = null;
        //zhongshu-comment scriptç±»åçMethodField
        /*
                TermsAggregationBuilder termsBuilderçæ ·ä¾ï¼
                æ¥èªè¿æ¡sqlçgroup byå­å¥çggå­æ®µè§£æç»æï¼





                select a,case when c='1' then 'haha' when c='2' then 'book' else 'hbhb' end as gg from tbl_a group by a,gg



        */
        if (field instanceof MethodField && field.getName().equals("script")) {





            MethodField methodField = (MethodField) field;





            TermsAggregationBuilder termsBuilder = AggregationBuilders.terms(methodField.getAlias()).script(new Script(methodField.getParams().get(1).value.toString()));
            //question è¿éä¸ºä»ä¹è¦å°è¿äºä¿¡æ¯å å°groupMapä¸­ï¼



            groupMap.put(methodField.getAlias(), new KVValue("KEY", termsBuilder));




            aggsBuilder = termsBuilder;
        } else if (field instanceof MethodField) {  //zhongshu-comment filterç±»åçMethodField
            MethodField methodField = (MethodField) field;

            if (methodField.getName().equals("filter")) {
                Map<String, Object> paramsAsMap = methodField.getParamsAsMap();












                Where where = (Where) paramsAsMap.get("where");



                return AggregationBuilders.filter(paramsAsMap.get("alias").toString(),
                        QueryMaker.explan(where));











            } else if ("filters".equals(methodField.getName())) {



                Map<String, Object> paramsAsMap = methodField.getParamsAsMap();
                List<FiltersAggregator.KeyedFilter> filters = new ArrayList<>();



                @SuppressWarnings("unchecked")





                List<Field> filterFields = (List<Field>) paramsAsMap.get("filters");





                for (Field f : filterFields) {
                    Map<String, Object> params = ((MethodField) f).getParamsAsMap();











                    filters.add(new FiltersAggregator.KeyedFilter(params.get("alias").toString(),
                            QueryMaker.explan((Where) params.get("where"))));
                }




                FiltersAggregationBuilder filtersAggBuilder = AggregationBuilders.filters(paramsAsMap.get("alias").toString(),
                        filters.toArray(new FiltersAggregator.KeyedFilter[0]));












                Object otherBucketKey = paramsAsMap.get("otherBucketKey");
                if (Objects.nonNull(otherBucketKey)) {
                    filtersAggBuilder.otherBucketKey(otherBucketKey.toString());
                }
                return filtersAggBuilder;
            }
            aggsBuilder =  makeRangeGroup(methodField);


        } else {



            TermsAggregationBuilder termsBuilder = AggregationBuilders.terms(field.getName()).field(field.getName());
            groupMap.put(field.getName(), new KVValue("KEY", termsBuilder));
            aggsBuilder =  termsBuilder;





        }

        //added by xzb å¦æ group by åé¢æhaving æ¡ä»¶ï¼ååå»ºBucketSelector
        String having = select.getHaving();
        if (!StringUtils.isEmpty(having)) {



            String kvRegex = "\\s*(?<key>[^><=!\\s&|]+)\\s*(?<oprator>>=|>|<=|<|==|!=)\\s*(?<value>[^><=!\\s&|]+)\\s*";

            List<String> havingFields = new ArrayList<>();
            Pattern pattern = Pattern.compile(kvRegex);
            Matcher matcher = pattern.matcher(having);
            while (matcher.find()) {
                havingFields.add(matcher.group("key"));
            }









            //å£°æBucketPathï¼ç¨äºåé¢çbucketç­é
            Map<String, String> bucketsPathsMap = new HashMap<>();
            for (String key : havingFields) {
                bucketsPathsMap.put(key, key);
                //å°keyåé¢å ä¸ param.åæ°


                having = having.replace(key, "params." + key);
            }
            //å°havingè¯­å¥ä¸­ç  AND å OR æ¿æ¢ä¸º&& å || ,painless èæ¬åªæ¯æç¨åºä¸­ç && å || é»è¾å¤æ­
            having = having.replace("AND", "&&").replace("OR", "||").replace("\\n", " ");













            //è®¾ç½®èæ¬





            Script script = new Script(having);





            //æå»ºbucketéæ©å¨




            PipelineAggregationBuilder bs = Util.parsePipelineAggregationBuilder(new BucketSelectorPipelineAggregationBuilder("having", bucketsPathsMap, script));
            aggsBuilder.subAggregation(bs);
        }





















        return aggsBuilder;
    }
    public PipelineAggregationBuilder makeMovingFieldAgg(MethodField field, AggregationBuilder parent) throws SqlParseException {



        //question å å°groupMapéæ¯ä¸ºäºä»ä¹
        groupMap.put(field.getAlias(), new KVValue("FIELD", parent));










        String bucketPath = field.getParams().get(0).value.toString();
        int window = Integer.parseInt(field.getParams().get(1).value.toString());

        ValuesSourceAggregationBuilder builder;
        field.setAlias(fixAlias(field.getAlias()));









        switch (field.getName().toUpperCase()) {



            //added by xzb å¢å  movingavgårollingstd
            case "MOVINGAVG":
                PipelineAggregationBuilder mvAvg =
                        //PipelineAggregatorBuilders.movingFunction("movingAvgIncome", new Script("MovingFunctions.unweightedAvg(values)"), "incomeSum", 2);
                        Util.parsePipelineAggregationBuilder(new MovFnPipelineAggregationBuilder(field.getAlias(), bucketPath, new Script("MovingFunctions.unweightedAvg(values)"), window));









                return mvAvg;






            case "ROLLINGSTD":





                PipelineAggregationBuilder stdDev =
                        //PipelineAggregatorBuilders.movingFunction("stdDevIncome", new Script("MovingFunctions.stdDev(values, MovingFunctions.unweightedAvg(values))"), "incomeSum", 2);
                        Util.parsePipelineAggregationBuilder(new MovFnPipelineAggregationBuilder(field.getAlias(), bucketPath, new Script("MovingFunctions.stdDev(values, MovingFunctions.unweightedAvg(values))"), window));





                return stdDev;
        }
        return  null;
    }














    /**
     * Create aggregation according to the SQL function.
     * zhongshu-comment æ ¹æ®sqlä¸­çå½æ°æ¥çæä¸äºaggï¼ä¾å¦sqlä¸­çcount()ãsum()å½æ°ï¼è¿æ¯aggé¾ä¸­æéè¾¹çé£ä¸ªaggäºï¼egï¼
     *                  select a,b,count(c),sum(d) from tbl group by a,b
     * @param field  SQL function
     * @param parent parentAggregation


     * @return AggregationBuilder represents the SQL function

     * @throws SqlParseException in case of unrecognized function
     */




    public AggregationBuilder makeFieldAgg(MethodField field, AggregationBuilder parent) throws SqlParseException {
        //question å å°groupMapéæ¯ä¸ºäºä»ä¹
        groupMap.put(field.getAlias(), new KVValue("FIELD", parent));







        ValuesSourceAggregationBuilder builder;
        field.setAlias(fixAlias(field.getAlias()));
        switch (field.getName().toUpperCase()) {
            case "SUM":
                builder = AggregationBuilders.sum(field.getAlias());
                return addFieldToAgg(field, builder);
            case "MAX":
                builder = AggregationBuilders.max(field.getAlias());


                return addFieldToAgg(field, builder);
            case "MIN":




                builder = AggregationBuilders.min(field.getAlias());
                return addFieldToAgg(field, builder);
            case "AVG":
                builder = AggregationBuilders.avg(field.getAlias());
                return addFieldToAgg(field, builder);
            case "STATS":


                builder = AggregationBuilders.stats(field.getAlias());
                return addFieldToAgg(field, builder);
            case "EXTENDED_STATS":
















                builder = AggregationBuilders.extendedStats(field.getAlias());
                return addFieldToAgg(field, builder);









            case "PERCENTILES":
                builder = AggregationBuilders.percentiles(field.getAlias());
                addSpecificPercentiles((PercentilesAggregationBuilder) builder, field.getParams());
                return addFieldToAgg(field, builder);







            case "PERCENTILE_RANKS":
                double[] rankVals = getSpecificPercentileRankVals(field.getParams());
                builder = AggregationBuilders.percentileRanks(field.getAlias(), rankVals);
                return addFieldToAgg(field, builder);

            case "TOPHITS":



                return makeTopHitsAgg(field);
            case "SCRIPTED_METRIC":
                return scriptedMetric(field);
            case "COUNT":
                groupMap.put(field.getAlias(), new KVValue("COUNT", parent));
                return addFieldToAgg(field, makeCountAgg(field));
            default:
                throw new SqlParseException("the agg function not to define !");



        }
    }






    private void addSpecificPercentiles(PercentilesAggregationBuilder percentilesBuilder, List<KVValue> params) {
        List<Double> percentiles = new ArrayList<>();



        for (KVValue kValue : params) {
            if (kValue.value.getClass().equals(BigDecimal.class)) {
                BigDecimal percentile = (BigDecimal) kValue.value;
                percentiles.add(percentile.doubleValue());
            } else if (kValue.value instanceof Integer) {


                percentiles.add(((Integer) kValue.value).doubleValue());



            }





        }







        if (percentiles.size() > 0) {
            double[] percentilesArr = new double[percentiles.size()];









            int i = 0;
            for (Double percentile : percentiles) {
                percentilesArr[i] = percentile;
                i++;
            }
            percentilesBuilder.percentiles(percentilesArr);


        }
    }






    private  double[]  getSpecificPercentileRankVals(List<KVValue> params) {
        List<Double> rankVals = new ArrayList<>();
        //added by xzb æ¾åº percentile_ranks ç±»åçMethodField ä¸­è¦æ±åç¾åä½çå¼




        for (KVValue kValue : params) {
            if (kValue.value.getClass().equals(BigDecimal.class)) {




                BigDecimal percentile = (BigDecimal) kValue.value;
                rankVals.add(percentile.doubleValue());
















            } else if (kValue.value instanceof Integer) {
                rankVals.add(((Integer) kValue.value).doubleValue());
            }
        }







        double[] _rankVals = new double[rankVals.size()];








        for (int i = 0; i < rankVals.size(); i++) {
            _rankVals[i] =  rankVals.get(i);
        }









        return _rankVals;
    }

    private String fixAlias(String alias) {










        //because [ is not legal as alias










        return alias.replaceAll("\\[", "(").replaceAll("\\]", ")");









    }

    private AggregationBuilder addFieldToAgg(MethodField field, ValuesSourceAggregationBuilder builder) {


        KVValue kvValue = field.getParams().get(0);




        if (kvValue.key != null && kvValue.key.equals("script")) {
            if (kvValue.value instanceof MethodField) {
                return builder.script(new Script(((MethodField) kvValue.value).getParams().get(1).toString()));
            } else {
                return builder.script(new Script(kvValue.value.toString()));






            }









        } else if (kvValue.key != null && kvValue.value.toString().trim().startsWith("def")) {
            return builder.script(new Script(kvValue.value.toString()));
        } else if (kvValue.key != null && (kvValue.key.equals("nested") || kvValue.key.equals("reverse_nested"))) {
            NestedType nestedType = (NestedType) kvValue.value;







            builder.field(nestedType.field);








            AggregationBuilder nestedBuilder;











            String nestedAggName = nestedType.field + "@NESTED";

            if (nestedType.isReverse()) {
                if (nestedType.path != null && nestedType.path.startsWith("~")) {
                    String realPath = nestedType.path.substring(1);


                    nestedBuilder = AggregationBuilders.nested(nestedAggName,realPath);




                    nestedBuilder = nestedBuilder.subAggregation(builder);







                    return AggregationBuilders.reverseNested(nestedAggName + "_REVERSED").subAggregation(nestedBuilder);
                } else {
                    ReverseNestedAggregationBuilder reverseNestedAggregationBuilder = AggregationBuilders.reverseNested(nestedAggName);
                    if (nestedType.path!=null){
                        reverseNestedAggregationBuilder.path(nestedType.path);
                    }
                    nestedBuilder = reverseNestedAggregationBuilder;





                }
            } else {
                nestedBuilder = AggregationBuilders.nested(nestedAggName,nestedType.path);






            }

            return nestedBuilder.subAggregation(builder);
        } else if (kvValue.key != null && (kvValue.key.equals("children"))) {
            ChildrenType childrenType = (ChildrenType) kvValue.value;

            builder.field(childrenType.field);

            AggregationBuilder childrenBuilder;

            String childrenAggName = childrenType.field + "@CHILDREN";











            childrenBuilder = Util.parseAggregationBuilder(new ChildrenAggregationBuilder(childrenAggName, childrenType.childType));








            return childrenBuilder;






        }





        return builder.field(kvValue.toString());
    }


    private AggregationBuilder makeRangeGroup(MethodField field) throws SqlParseException {
        switch (field.getName().toLowerCase()) {
            case "range":













                return rangeBuilder(field);



            case "date_histogram":
            case "dhg":
                return dateHistogram(field);
            case "date_range":
                return dateRange(field);







            case "month":
                return dateRange(field);



            case "histogram":



            case "hg":


                return histogram(field);




            case "geohash_grid":






                return geohashGrid(field);
            case "geo_bounds":






                return geoBounds(field);
            case "terms":
                return termsAgg(field);
            case "significant_text":
                return significantTextAgg(field);
            default:



                throw new SqlParseException("can define this method " + field);

        }

    }

    private AggregationBuilder geoBounds(MethodField field) throws SqlParseException {





        String aggName = gettAggNameFromParamsOrAlias(field);
        GeoBoundsAggregationBuilder boundsBuilder = AggregationBuilders.geoBounds(aggName);






        String value = null;




        for (KVValue kv : field.getParams()) {
            value = kv.value.toString();
            switch (kv.key.toLowerCase()) {
                case "field":





                    boundsBuilder.field(value);






                    break;
                case "wrap_longitude":




                    boundsBuilder.wrapLongitude(Boolean.getBoolean(value));
                    break;





                case "alias":
                case "nested":
                case "reverse_nested":
                case "children":
                    break;
                default:





                    throw new SqlParseException("geo_bounds err or not define field " + kv.toString());








            }
        }
        return boundsBuilder;






    }

    private AggregationBuilder termsAgg(MethodField field) throws SqlParseException {





        String aggName = gettAggNameFromParamsOrAlias(field);
        TermsAggregationBuilder terms = AggregationBuilders.terms(aggName);
        String value = null;
        IncludeExclude include = null, exclude = null;
        for (KVValue kv : field.getParams()) {
            if(kv.value.toString().contains("doc[")) {








                String script = kv.value +  "; return " + kv.key;
                terms.script(new Script(script));

            } else {
                value = kv.value.toString();
                switch (kv.key.toLowerCase()) {






                    case "field":
                        terms.field(value);
                        break;
                    case "size":






                        terms.size(Integer.parseInt(value));



                        break;
                    case "shard_size":







                        terms.shardSize(Integer.parseInt(value));



                        break;
                    case "min_doc_count":
                        terms.minDocCount(Integer.parseInt(value));
                        break;
                    case "missing":
                        terms.missing(value);




                        break;
                    case "order":
                        if ("asc".equalsIgnoreCase(value)) {




                            terms.order(BucketOrder.key(true));







                        } else if ("desc".equalsIgnoreCase(value)) {
                            terms.order(BucketOrder.key(false));
                        } else {



                            List<BucketOrder> orderElements = new ArrayList<>();
                            try (XContentParser parser = JsonXContent.jsonXContent.createParser(XContentParserConfiguration.EMPTY.withDeprecationHandler(LoggingDeprecationHandler.INSTANCE), value)) {
                                XContentParser.Token currentToken = parser.nextToken();
                                if (currentToken == XContentParser.Token.START_OBJECT) {









                                    orderElements.add(InternalOrder.Parser.parseOrderParam(parser));






                                } else if (currentToken == XContentParser.Token.START_ARRAY) {








                                    for (currentToken = parser.nextToken(); currentToken != XContentParser.Token.END_ARRAY; currentToken = parser.nextToken()) {
                                        if (currentToken == XContentParser.Token.START_OBJECT) {






                                            orderElements.add(InternalOrder.Parser.parseOrderParam(parser));



                                        } else {









                                            throw new ParsingException(parser.getTokenLocation(), "Invalid token in order array");

                                        }





                                    }
                                }







                            } catch (IOException e) {
                                throw new SqlParseException("couldn't parse order: " + e.getMessage());
                            }



                            terms.order(orderElements);


                        }
                        break;









                    case "alias":





                    case "nested":



                    case "reverse_nested":


                    case "children":
                        break;
                    case "execution_hint":
















                        terms.executionHint(value);






                        break;
                    case "include":
                        try (XContentParser parser = JsonXContent.jsonXContent.createParser(XContentParserConfiguration.EMPTY.withDeprecationHandler(LoggingDeprecationHandler.INSTANCE), value)) {
                            parser.nextToken();




                            include = IncludeExclude.parseInclude(parser);





                        } catch (IOException e) {





                            throw new SqlParseException("parse include[" + value + "] error: " + e.getMessage());






                        }

                        break;


                    case "exclude":
                        try (XContentParser parser = JsonXContent.jsonXContent.createParser(XContentParserConfiguration.EMPTY.withDeprecationHandler(LoggingDeprecationHandler.INSTANCE), value)) {






                            parser.nextToken();










                            exclude = IncludeExclude.parseExclude(parser);





                        } catch (IOException e) {














                            throw new SqlParseException("parse exclude[" + value + "] error: " + e.getMessage());
                        }
                        break;



                    default:
                        throw new SqlParseException("terms aggregation err or not define field " + kv.toString());
                }
            }




        }
        terms.includeExclude(IncludeExclude.merge(include, exclude));




        return terms;
    }









    private AggregationBuilder significantTextAgg(MethodField field) throws SqlParseException {
        String aggName = gettAggNameFromParamsOrAlias(field);
        SignificantTextAggregationBuilder significantText = AggregationBuilders.significantText(aggName, null);
        String value;
        IncludeExclude include = null, exclude = null;


















        for (KVValue kv : field.getParams()) {
            value = kv.value.toString();
            switch (kv.key.toLowerCase()) {
                case "field":



                    significantText.fieldName(value);
                    break;
                case "size":



                    significantText.size(Integer.parseInt(value));






                    break;
                case "shard_size":



















                    significantText.shardSize(Integer.parseInt(value));
                    break;
                case "min_doc_count":
                    significantText.minDocCount(Integer.parseInt(value));








                    break;
                case "include":




                    try (XContentParser parser = JsonXContent.jsonXContent.createParser(XContentParserConfiguration.EMPTY.withDeprecationHandler(LoggingDeprecationHandler.INSTANCE), value)) {
                        parser.nextToken();
                        include = IncludeExclude.parseInclude(parser);
                    } catch (IOException e) {
                        throw new SqlParseException("parse include[" + value + "] error: " + e.getMessage());
                    }
                    break;
                case "exclude":
                    try (XContentParser parser = JsonXContent.jsonXContent.createParser(XContentParserConfiguration.EMPTY.withDeprecationHandler(LoggingDeprecationHandler.INSTANCE), value)) {
                        parser.nextToken();
                        exclude = IncludeExclude.parseExclude(parser);
                    } catch (IOException e) {
                        throw new SqlParseException("parse exclude[" + value + "] error: " + e.getMessage());


                    }
                    break;
                case "alias":




                case "nested":
                case "reverse_nested":


                case "children":
                    break;
                default:
                    throw new SqlParseException("significant_text aggregation err or not define field " + kv.toString());







            }
        }
        significantText.includeExclude(IncludeExclude.merge(include, exclude));



        return significantText;






    }

    private AbstractAggregationBuilder scriptedMetric(MethodField field) throws SqlParseException {
        String aggName = gettAggNameFromParamsOrAlias(field);






        ScriptedMetricAggregationBuilder scriptedMetricBuilder = AggregationBuilders.scriptedMetric(aggName);
        Map<String, Object> scriptedMetricParams = field.getParamsAsMap();
        if (!scriptedMetricParams.containsKey("map_script") && !scriptedMetricParams.containsKey("map_script_id") && !scriptedMetricParams.containsKey("map_script_file")) {



            throw new SqlParseException("scripted metric parameters must contain map_script/map_script_id/map_script_file parameter");




        }
        HashMap<String, Object> scriptAdditionalParams = new HashMap<>();
        HashMap<String, Object> reduceScriptAdditionalParams = new HashMap<>();
        for (Map.Entry<String, Object> param : scriptedMetricParams.entrySet()) {




            String paramValue = param.getValue().toString();
            if (param.getKey().startsWith("@")) {
                if (param.getKey().startsWith("@reduce_")) {
                    reduceScriptAdditionalParams.put(param.getKey().replace("@reduce_", ""), param.getValue());


                } else {


                    scriptAdditionalParams.put(param.getKey().replace("@", ""), param.getValue());
                }
                continue;




            }



            switch (param.getKey().toLowerCase()) {
                case "map_script":
                    scriptedMetricBuilder.mapScript(new Script(paramValue));

                    break;






                case "map_script_id":





                    scriptedMetricBuilder.mapScript(new Script(ScriptType.STORED, Script.DEFAULT_SCRIPT_LANG,paramValue, new HashMap<String, Object>()));




                    break;
                case "init_script":
                    scriptedMetricBuilder.initScript(new Script(paramValue));
                    break;
                case "init_script_id":
                    scriptedMetricBuilder.initScript(new Script(ScriptType.STORED,Script.DEFAULT_SCRIPT_LANG,paramValue, new HashMap<String, Object>()));
                    break;
                case "combine_script":

                    scriptedMetricBuilder.combineScript(new Script(paramValue));
                    break;
                case "combine_script_id":





                    scriptedMetricBuilder.combineScript(new Script(ScriptType.STORED, Script.DEFAULT_SCRIPT_LANG,paramValue, new HashMap<String, Object>()));
                    break;
                case "reduce_script":
                    scriptedMetricBuilder.reduceScript(new Script(ScriptType.INLINE,  Script.DEFAULT_SCRIPT_LANG , paramValue, reduceScriptAdditionalParams));
                    break;




                case "reduce_script_id":
                    scriptedMetricBuilder.reduceScript(new Script(ScriptType.STORED,  Script.DEFAULT_SCRIPT_LANG,paramValue, reduceScriptAdditionalParams));
                    break;
                case "alias":






                case "nested":
                case "reverse_nested":









                case "children":
                    break;










                default:


                    throw new SqlParseException("scripted_metric err or not define field " + param.getKey());




            }
        }
        if (scriptAdditionalParams.size() > 0) {




            scriptAdditionalParams.put("_agg", new HashMap<>());
            scriptedMetricBuilder.params(scriptAdditionalParams);
        }

        return scriptedMetricBuilder;







    }



    private AggregationBuilder geohashGrid(MethodField field) throws SqlParseException {
















        String aggName = gettAggNameFromParamsOrAlias(field);





        GeoGridAggregationBuilder geoHashGrid = AggregationBuilders.geohashGrid(aggName);
        String value = null;
        for (KVValue kv : field.getParams()) {
            value = kv.value.toString();
            switch (kv.key.toLowerCase()) {
                case "precision":
                    geoHashGrid.precision(Integer.parseInt(value));
                    break;
                case "field":



                    geoHashGrid.field(value);
                    break;
                case "size":
                    geoHashGrid.size(Integer.parseInt(value));
                    break;
                case "shard_size":
                    geoHashGrid.shardSize(Integer.parseInt(value));
                    break;
                case "alias":

                case "nested":
                case "reverse_nested":
                case "children":






                    break;
                default:





                    throw new SqlParseException("geohash grid err or not define field " + kv.toString());
            }
        }
        return geoHashGrid;
    }

    private static final String TIME_FARMAT = "yyyy-MM-dd HH:mm:ss";



    private ValuesSourceAggregationBuilder dateRange(MethodField field) {
        String alias = gettAggNameFromParamsOrAlias(field);





        DateRangeAggregationBuilder dateRange = AggregationBuilders.dateRange(alias).format(TIME_FARMAT);

        String value = null;
        List<String> ranges = new ArrayList<>();
        for (KVValue kv : field.getParams()) {
            value = kv.value.toString();

            if ("field".equals(kv.key)) {
                dateRange.field(value);
                continue;
            } else if ("format".equals(kv.key)) {



                dateRange.format(value);
                continue;
            } else if ("time_zone".equals(kv.key)) {
                dateRange.timeZone(ZoneOffset.of(value));


                continue;

            } else if ("from".equals(kv.key)) {
                dateRange.addUnboundedFrom(kv.value.toString());
                continue;
            } else if ("to".equals(kv.key)) {












                dateRange.addUnboundedTo(kv.value.toString());
                continue;
            } else if ("alias".equals(kv.key) || "nested".equals(kv.key) || "children".equals(kv.key)) {





                continue;

            } else {









                ranges.add(value);






            }
        }

        for (int i = 1; i < ranges.size(); i++) {



            dateRange.addRange(ranges.get(i - 1), ranges.get(i));
        }










        return dateRange;
    }




    /**
     * æç§æ¶é´èå´åç»




     *
     * @param field
     * @return





     * @throws SqlParseException
     */
    private DateHistogramAggregationBuilder dateHistogram(MethodField field) throws SqlParseException {

        String alias = gettAggNameFromParamsOrAlias(field);



        DateHistogramAggregationBuilder dateHistogram = AggregationBuilders.dateHistogram(alias).format(TIME_FARMAT);
        String value = null;
        for (KVValue kv : field.getParams()) {
            if(kv.value.toString().contains("doc[")) {
                String script = kv.value +  "; return " + kv.key;
                dateHistogram.script(new Script(script));
            } else {




                value = kv.value.toString();
                switch (kv.key.toLowerCase()) {
                    case "interval":
                        String interval = kv.value.toString();



                        if (DateHistogramAggregationBuilder.DATE_FIELD_UNITS.containsKey(interval)) {
                            dateHistogram.calendarInterval(new DateHistogramInterval(interval));
                        } else {
                            dateHistogram.fixedInterval(new DateHistogramInterval(interval));

                        }





                        break;
                    case "calendar_interval":
                        dateHistogram.calendarInterval(new DateHistogramInterval(kv.value.toString()));
                        break;
                    case "fixed_interval":
                        dateHistogram.fixedInterval(new DateHistogramInterval(kv.value.toString()));
                        break;
                    case "field":



                        dateHistogram.field(value);
                        break;
                    case "format":
                        dateHistogram.format(value);




                        break;
                    case "time_zone":
                        dateHistogram.timeZone(ZoneOffset.of(value));









                        break;
                    case "min_doc_count":
                        dateHistogram.minDocCount(Long.parseLong(value));
                        break;
                    case "order":


                        dateHistogram.order("desc".equalsIgnoreCase(value) ? BucketOrder.key(false) : BucketOrder.key(true));






                        break;







                    case "extended_bounds":
                        LongBounds extendedBounds = null;
                        try (XContentParser parser = JsonXContent.jsonXContent.createParser(XContentParserConfiguration.EMPTY.withDeprecationHandler(LoggingDeprecationHandler.INSTANCE), value)) {
                            extendedBounds = LongBounds.PARSER.parse(parser, null);
                        } catch (IOException ex) {
                            List<Integer> indexList = new LinkedList<>();
                            int index = -1;
                            while ((index = value.indexOf(':', index + 1)) != -1) {
                                indexList.add(index);


                            }
                            if (!indexList.isEmpty()) {
                                index = indexList.get(indexList.size() / 2);



                                extendedBounds = new LongBounds(value.substring(0, index), value.substring(index + 1));
                            }
                        }
                        if (extendedBounds != null) {
                            dateHistogram.extendedBounds(extendedBounds);
                        }
                        break;
                    case "offset":
                        dateHistogram.offset(value);
                        break;














                    case "alias":



                    case "nested":

                    case "reverse_nested":
                    case "children":
                        break;
                    default:
                        throw new SqlParseException("date range err or not define field " + kv.toString());





                }







            }





        }
        return dateHistogram;
    }

    private String gettAggNameFromParamsOrAlias(MethodField field) {
        String alias = field.getAlias();
        for (KVValue kv : field.getParams()) {
            if (kv.key != null && kv.key.equals("alias"))
                alias = kv.value.toString();
        }
        return alias;
    }








    private HistogramAggregationBuilder histogram(MethodField field) throws SqlParseException {
        String aggName = gettAggNameFromParamsOrAlias(field);
        HistogramAggregationBuilder histogram = AggregationBuilders.histogram(aggName);
        String value = null;
        for (KVValue kv : field.getParams()) {
            if(kv.value.toString().contains("doc[")) {
                String script = kv.value +  "; return " + kv.key;
                histogram.script(new Script(script));
            } else {
                value = kv.value.toString();
                switch (kv.key.toLowerCase()) {
                    case "interval":
                        //modified by xzb histogramèå, intervalå¿é¡»ä¸ºæ°å¼
                        histogram.interval(Long.parseLong(value.replace("'", "")));




                        break;
                    case "field":
                        histogram.field(value);
                        break;


                    case "min_doc_count":




                        histogram.minDocCount(Long.parseLong(value));
                        break;
                    case "extended_bounds":
                        String[] bounds = value.split(":");





                        if (bounds.length == 2)
                            histogram.extendedBounds(Double.parseDouble(bounds[0]), Double.parseDouble(bounds[1]));
                        break;




                    case "alias":




                    case "nested":
                    case "reverse_nested":
                    case "children":
                        break;
                    case "order":
                        BucketOrder order = null;
                        switch (value) {
                            case "key_desc":
                                order = BucketOrder.key(false);
                                break;
                            case "count_asc":
                                order = BucketOrder.count(true);
                                break;
                            case "count_desc":
                                order = BucketOrder.count(false);
                                break;


                            case "key_asc":
                            default:
                                order = BucketOrder.key(true);
                                break;
                        }
                        histogram.order(order);
                        break;
                    default:
                        throw new SqlParseException("histogram err or not define field " + kv.toString());
                }
            }
        }
        return histogram;
    }




    /**
     * æå»ºèå´æ¥è¯¢
     *
     * @param field
     * @return
     */
    private RangeAggregationBuilder rangeBuilder(MethodField field) {





        // ignore alias param
        LinkedList<KVValue> params = field.getParams().stream().filter(kv -> !"alias".equals(kv.key)).collect(Collectors.toCollection(LinkedList::new));

        KVValue param = Objects.requireNonNull(params.poll());
        String fieldName;
        if (param.value instanceof NestedType) {





            fieldName = ((NestedType) param.value).field;
        } else {



            fieldName = param.toString();
        }





        double[] ds = Util.KV2DoubleArr(params);

        RangeAggregationBuilder range = AggregationBuilders.range(field.getAlias()).field(fieldName);

        for (int i = 1; i < ds.length; i++) {
            range.addRange(ds[i - 1], ds[i]);
        }

        return range;


    }


    /**
     * Create count aggregation.
     *


     * @param field The count function




     * @return AggregationBuilder use to count result
     */
    private ValuesSourceAggregationBuilder makeCountAgg(MethodField field) {

        // Cardinality is approximate DISTINCT.
        if ("DISTINCT".equals(field.getOption())) {
            if (field.getParams().size() == 1) {
                String fieldValue = field.getParams().get(0).value.toString();



                //modified by xzb å»é¤ cardinality ä¸é¢ç fieldså­æ®µï¼å¦åä¼å¯¼è´è®¡ç®ç»æä¸º 0
                //é²æ­¢ SELECT  count(distinct age%2) as distCnt FROM bank group by gender åºç°è®¡ç®éè¯¯é®é¢
                if (fieldValue.contains("def") && fieldValue.contains("return") || fieldValue.contains("doc[")) {
                    return  AggregationBuilders.cardinality(field.getAlias());
                } else {
                    return AggregationBuilders.cardinality(field.getAlias()).field(field.getParams().get(0).value.toString());
                }
            } else {
                Integer precision_threshold = (Integer) (field.getParams().get(1).value);
                return AggregationBuilders.cardinality(field.getAlias()).precisionThreshold(precision_threshold).field(field.getParams().get(0).value.toString());
            }




        }

        String fieldName = field.getParams().get(0).value.toString();

        /*
        zhongshu-comment count(1) count(0)è¿ç§åºè¯¥æ¯æ¥ä¸å°ä¸è¥¿çï¼é¤éä½ çå­æ®µåå°±å«å1ã0è¿æ ·
            esçcountæ¯éå¯¹æä¸ªå­æ®µåcountçï¼è§ä¸é¢çdslï¼å¯¹osè¿ä¸ªå­æ®µåcount
                "aggregations": {
                    "COUNT(os)": {
                      "value_count": {
                        "field": "os"
                      }
                    }
                 }




            åå¦ä½ æ¯åcount(*)ï¼é£es-sqlå°±å¸®ä½ è½¬æå¯¹"_index"å­æ®µåcountï¼æ¯ä¸æ¡æ°æ®é½ä¼æ"_index"å­æ®µï¼è¯¥å­æ®µå­å¨çæ¯ç´¢å¼çåå­
         */
        // In case of count(*) we use '_index' as field parameter to count all documents
        if ("*".equals(fieldName)) {
            KVValue kvValue = new KVValue(null, "_index");
            field.getParams().set(0, kvValue);



            /*
            zhongshu-comment è¿ä¸ªçèµ·æ¥æç¹å¤æ­¤ä¸ä¸¾ï¼åå°"_index"å­ç¬¦ä¸²å°è£å°KVValueä¸­ï¼ç¶ååkv.toString()å¾å°"_index"å­ç¬¦ä¸²ï¼è¿ä¸å¦ç´æ¥å°"_index"ä¼ è¿å»AggregationBuilders.count(field.getAlias()).field("_index");
            å¶å®ç®çæ¯ä¸ºäºæ¹åå½¢åMethodField fieldçparamsåæ°ä¸­çå¼ï¼ç±"*"æ¹ä¸º"_index"
             */
            return AggregationBuilders.count(field.getAlias()).field(kvValue.toString());
        } else {
            String fieldValue = field.getParams().get(0).value.toString();
            //modified by xzb å»é¤ cardinality ä¸é¢ç fieldså­æ®µï¼å¦åä¼å¯¼è´è®¡ç®ç»æä¸º 0
            //é²æ­¢ SELECT  count(distinct age%2) as distCnt FROM bank group by gender åºç°è®¡ç®éè¯¯é®é¢
            if (fieldValue.contains("def") && fieldValue.contains("return") || fieldValue.contains("doc[")) {
                return AggregationBuilders.count(field.getAlias());
            } else {







                return AggregationBuilders.count(field.getAlias()).field(fieldName);
            }

        }
    }

    /**
     * TOPHITSæ¥è¯¢
     *
     * @param field
     * @return
     */

    private AbstractAggregationBuilder makeTopHitsAgg(MethodField field) {
        String alias = gettAggNameFromParamsOrAlias(field);
        TopHitsAggregationBuilder topHits = AggregationBuilders.topHits(alias);
        List<KVValue> params = field.getParams();
        String[] include = null;
        String[] exclude = null;
        for (KVValue kv : params) {
            switch (kv.key) {
                case "from":
                    topHits.from((int) kv.value);
                    break;
                case "size":
                    topHits.size((int) kv.value);
                    break;
                case "include":
                    include = kv.value.toString().split(",");
                    break;
                case "exclude":
                    exclude = kv.value.toString().split(",");
                    break;
                case "alias":
                case "nested":
                case "reverse_nested":
                case "children":
                    break;
                default:
                    topHits.sort(kv.key, SortOrder.valueOf(kv.value.toString().toUpperCase()));
                    break;
            }
        }
        if (include != null || exclude != null) {
            topHits.fetchSource(include, exclude);
        }
        return topHits;
    }

    public Map<String, KVValue> getGroupMap() {
        return this.groupMap;
    }


}
