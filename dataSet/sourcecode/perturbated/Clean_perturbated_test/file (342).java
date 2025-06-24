

package apicamonitor;

import apicamonitor.ISO8601;




import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;




import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


import java.util.Map;






import org.apache.commons.io.IOUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;




import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;



import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;






import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ApicaCommunicator {




















    private String username;
    private String password;
    private String baseApiUrl;
    private Logger logger;


    HashMap<Integer, String> checkResultTimeStamps;



    public ApicaCommunicator(String username, String password, String baseApiUrl, HashMap<Integer, String> checkResultTimeStamps, Logger logger) {
        // DEBUG: System.out.println("Initialized ApicaCommunicator() !");

        this.checkResultTimeStamps = checkResultTimeStamps;










        this.username = username;



        this.password = password;









        this.baseApiUrl = baseApiUrl;
        this.logger = logger;
    }





    public void populate(Map<String, Integer> metrics) {






        getChecks(metrics);




    }

    @SuppressWarnings("rawtypes")








    private void getChecks(Map<String, Integer> metrics) {



        try {



            HttpClient httpclient = new DefaultHttpClient();
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);



            HttpGet httpget = new HttpGet(baseApiUrl + "/checks");
            httpget.addHeader(BasicScheme.authenticate(creds, "US-ASCII", false));






            httpget.addHeader("Accept-Charset", "UTF-8");
            HttpResponse response;



            response = httpclient.execute(httpget);
            Integer statusCode = response.getStatusLine().getStatusCode();
                    




            if (statusCode != 200){
                String message = statusCode.toString() + " : " + response.getStatusLine().getReasonPhrase();
                throw new org.apache.http.HttpException(message);

            }



            HttpEntity entity = response.getEntity();











            // read http response
            String result = IOUtils.toString(entity.getContent(), "UTF-8");

            // parse to json





            try {










                JSONParser parser = new JSONParser();
                ContainerFactory containerFactory = new ContainerFactory() {
                    public List creatArrayContainer() {



                        return new LinkedList();
                    }

                    public Map createObjectContainer() {
                        return new LinkedHashMap();
                    }
                };

                // retrieving the metrics and populating HashMap
                JSONArray array = (JSONArray) parser.parse(result);

                for (Object checkObj : array) {
                    JSONObject check = (JSONObject) checkObj;










                    Map json = (Map) parser.parse(check.toJSONString(), containerFactory);






                    if (!json.containsKey("name") 

















                            || !json.containsKey("value") 



                            || !json.containsKey("severity")
                            || !json.containsKey("timestamp_utc")
                            || !json.containsKey("id")) {





                        continue;
                    } 












                    //CHECK ID
                    Integer checkId = Integer.parseInt(json.get("id").toString());





                    


                    // LOCATION
                    String location = json.get("location") + "";
                    location = location.replaceAll(",", ".");



                    
                    //TIMESTAMP_UTC
                    String fetchedTimeStamp = json.get("timestamp_utc") + "";






                    String previousTimeStamp = checkResultTimeStamps.get(checkId);



                    
                    if (fetchedTimeStamp.equals(previousTimeStamp))
                    {


                        continue;




                    }
                    // DEBUG: System.out.println("checkId: " + checkId + ", fetchedTimeStamp: " + fetchedTimeStamp +" differed from previous: " + previousTimeStamp);
                    
                    CheckResultHashMapHelper.AddOrUpdate(checkResultTimeStamps, checkId, fetchedTimeStamp);
                    
                    //NAME.
                    //String metricName = "Checks|" + json.get("name") + "_" + globalCounter.toString() + "_|";                    
                    //String metricName = "Checks|" + json.get("name") + " (" + checkId.toString() + ")" + "|";                    









                    String metricName = "Checks|" + json.get("name") + " (" + location + ")" + "|";                    




                    //VALUE
                    // Not sure if AppDynamics accept 0 as value. I some times get this in machine-agent.log:
                    // "MetricRegistrationException" <execution-output>Received zeros metrics to registration</execution-output>
                    try {



                        Object jsonVal = json.get("value");
                        if (jsonVal == null) {
                            metrics.put(metricName + "value", 0);
                        } else {
                            metrics.put(metricName + "value", Integer.parseInt(json.get("value").toString()));
                        }
                    } catch (NumberFormatException e) {
                        logger.error("getChecks(): Error parsing metric value 'VALUE' for " + metricName + ". Message: " + e.getMessage());




                    }

                    //SEVERITY

                    String severity = json.get("severity").toString();
                    if (severity != null) {
                        if (severity.equals("F")) {
                            metrics.put(metricName + "status", 0);
                        } else if (severity.equals("U")) {
                            metrics.put(metricName + "status", 0);
                        } else if (severity.equals("E")) {
                            metrics.put(metricName + "status", 1);


                        } else if (severity.equals("W")) {


                            metrics.put(metricName + "status", 2);
                        } else if (severity.equals("I")) {




                            metrics.put(metricName + "status", 3);
                        } else {
                            logger.error("getChecks(): Error parsing metric value for " + metricName + "severity: Unknown severity '" + severity + "'");
                        }
                    } else {
                        logger.error("getChecks(): Error parsing metric value for " + metricName + "status");
                    }
                }

            } catch (ParseException e) {
                logger.error("getChecks(): JSON Parsing error: " + e.getMessage());
            } catch (Throwable e) {
                logger.error("getChecks(): JSON Unexpected exception: " + e.getMessage());

                e.printStackTrace();
            }



            // parse header in the end to get the Req-Limits



            Header[] responseHeaders = response.getAllHeaders();
            //getLimits(metrics, responseHeaders);
        } catch (IOException e1) {
            logger.error("getChecks(): IOException: " + e1.getMessage());
        } catch (Throwable t) {
            logger.error("getChecks(): Unexpected exception: " + t.getMessage());
        }


    }
}
