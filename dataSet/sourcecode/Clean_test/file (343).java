/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apicamonitor;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import apicamonitor.ApicaCommunicator;

import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import com.singularity.ee.agent.systemagent.api.TaskExecutionContext;
import com.singularity.ee.agent.systemagent.api.TaskOutput;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.logging.Level;

public class ApicaMonitor extends AManagedMonitor {

    // When this is true, every call to the API will look at the checks, save the timestamps
    // and either save it to file or mem depending on the variable: useFilebasedDiffCache.
    // Set to false to disable caching entirely.
    private Boolean useTimestampDiffCache = false;
    // If using Periodic execution-style, this could be set to true in order to persist
    // time-stamp comparing to/from files.
    // Continuous execution keeps the program running indef. so then it's better to use
    // in-memory comparing (set this to false)
    private Boolean useFilebasedDiffCache = false;
    // Used when execution-style is expected to be continuous, since that signifies a loop that run
    // every 60th second.
    private Boolean useLoopingProcess = false;
    // Write stuff to StdOut so that debug is easier.
    private Boolean useOutputDebugStuff = false;
    // Metrics will be fetched and put heere.
    private Map<String, Integer> metrics;
    private Logger logger;
    // The variable containing timestamnps for when checks had new data lastly. 
    // Timestamps comes from the API. This is in order to see what data is new.
    HashMap<Integer, String> checkResultTimeStamps = new HashMap<>();
    
    // Set from an argument to the program.
    private String username = "";
    // Set from an argument to the program.
    private String password = "";
    // Set from an argument to the program.
    private String baseApiUrl = "";
    // Set from an argument to the program.
    private String metricPath = "Custom Metrics|Apica|";
    // Does the work of communicating with Apica's API.
    ApicaCommunicator apicaCommunicator;

    /**
     * For testing purpose is Main() executed. To see if connection works, 
     * run this java file alone with arguments: Username, Password, URI.
     *
     * Prints all metric names and their value from one round of REST calls to
     * StdOut.
     */
    public static void main(String[] args) throws ParseException {

        if (args.length != 3) {
            System.err.println("3 arguments needed: username, password, baseApiUrl");
            return;
        }

        String username = args[0];
        String password = args[1];
        String baseApiUrl = args[2];

        ApicaMonitor pm = new ApicaMonitor();
        pm.logger = Logger.getLogger(ApicaMonitor.class);
        pm.username = username;
        pm.password = password;
        pm.baseApiUrl = baseApiUrl;
        pm.checkResultTimeStamps = new HashMap<>();

        // Debug Option 1. Start the actual thread that will do the loop for us
        // In this case you probably want to write to stdout, so remove those comments.
        Map<String, String> taskArgs;
        taskArgs = new HashMap<String, String>();
        taskArgs.put("Username", username);
        taskArgs.put("Password", password);
        taskArgs.put("BaseApiUrl", baseApiUrl);
        try {
            pm.execute(taskArgs, null);
        } catch (TaskExecutionException ex) {
            java.util.logging.Logger.getLogger(ApicaMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Debug Option 2. Run the communicator and output to SdtOut.
        /*
         ApicaCommunicator apicaCommunicator = new ApicaCommunicator(username, password, baseApiUrl, Logger.getLogger(ApicaMonitor.class));
         Map<String, Integer> metrics = new HashMap<String, Integer>();
         apicaCommunicator.populate(metrics);

         for (String key : metrics.keySet()) {
         System.out.println(key + " ==> " + metrics.get(key));
         }
         */

    }

    @Override
    public TaskOutput execute(Map<String, String> taskArguments,
            TaskExecutionContext taskContext) throws TaskExecutionException {

        logger = Logger.getLogger(ApicaMonitor.class);
        metrics = new HashMap<String, Integer>();

        if (!taskArguments.containsKey("Username") || !taskArguments.containsKey("Password")) {
            logger.error("monitor.xml must contain task arguments 'Username', 'Password' and 'BaseApiUrl'"
                    + " Terminating Monitor.");
            return null;
        }

        username = taskArguments.get("Username");
        password = taskArguments.get("Password");
        baseApiUrl = taskArguments.get("BaseApiUrl");

        // setting the custom metric path, if there is one in monitor.xml
        if (taskArguments.containsKey("Metric-Path") && taskArguments.get("Metric-Path") != "") {
            metricPath = taskArguments.get("Metric-Path");
            if (!metricPath.endsWith("|")) {
                metricPath += "|";
            }
        }

        // Try get the diffcache from file
        if (useTimestampDiffCache && useFilebasedDiffCache) {
            checkResultTimeStamps = LoadTimestampDiffsFromFile();
        }
        else {
            
        }
            

        apicaCommunicator = new ApicaCommunicator(username, password, baseApiUrl, checkResultTimeStamps, logger);

        if (!useLoopingProcess) {
            if (!useTimestampDiffCache){
                checkResultTimeStamps.clear();
            }
            
            apicaCommunicator.populate(metrics);
            DumpToStdOut(metrics);
            writeAllMetrics();
            if (useTimestampDiffCache && useFilebasedDiffCache) {
                SaveTimestampDiffsToFile(checkResultTimeStamps);
            }
            metrics.clear();
        } else {
            // Using a sepaarate thread in a loop:
            while (true) {
                (new PrintMetricsClearHashmapThread()).start();
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    logger.error("Apica Monitor interrupted. Quitting Apica Monitor: " + e.getMessage());
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Write all metrics to the AppDyn Controller.
     */
    private void writeAllMetrics() {
        for (String key : metrics.keySet()) {
            // See: http://docs.appdynamics.com/display/PRO13S/Build+a+Monitoring+Extension+Using+Java#BuildaMonitoringExtensionUsingJava-YourMonitoringExtensionClass
            MetricWriter metricWriter = getMetricWriter(metricPath + key,
                    MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION, // Last reported value
                    MetricWriter.METRIC_TIME_ROLLUP_TYPE_AVERAGE, // I changed from Sum to Average.
                    MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_INDIVIDUAL); // No idea what this is

            // Below line might crash system when deployed
            //System.out.println("PRINTING: " + metricPath + key);
            metricWriter.printMetric(String.valueOf(metrics.get(key)));
        }
    }

    private void SaveTimestampDiffsToFile(HashMap<Integer, String> hashMap) {
        try (
                OutputStream file = new FileOutputStream("timestamps.ser");
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);) {
            output.writeObject(hashMap);
        } catch (IOException ex) {
            logger.error("Cannot serialize object: " + ex.getMessage());
        }
    }

    private HashMap<Integer, String> LoadTimestampDiffsFromFile() {
        try (
                InputStream file = new FileInputStream("timestamps.ser");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);) {
            //deserialize the List
            HashMap<Integer, String> hashMap = (HashMap<Integer, String>) input.readObject();
            return hashMap;

        } catch (ClassNotFoundException ex) {
            logger.error("Cannot deserialize object. Class not found." + ex.getMessage());
        } catch (IOException ex) {
            logger.error("Cannot deserialize object: " + ex.getMessage());
        }
        return new HashMap<>();
    }

    private void DumpToStdOut(Map<String, Integer> someMap) {
        if (!useOutputDebugStuff){
            return;
        }
        
        for (String key : someMap.keySet()) {
            String val = someMap.get(key).toString();
            System.out.println(key + " ==> " + val);
        }
        System.out.println("run() completed with: [" + someMap.size() + "] outputted metrics. ThreadId: " + Thread.currentThread().getId());
    }

    private class PrintMetricsClearHashmapThread extends Thread {

        public void run() {
            // The following line nullifies timestamp-diff-cache.
            if (!useTimestampDiffCache){
                apicaCommunicator.checkResultTimeStamps.clear();
            }
            
            apicaCommunicator.populate(metrics);
            DumpToStdOut(metrics);
            writeAllMetrics();
            metrics.clear();
        }
    }
}