package reporting;

import java.io.BufferedWriter;


import java.io.File;














import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;




import java.math.BigDecimal;




import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;







import java.util.*;









import org.testng.IReporter;
import org.testng.ISuite;


import org.testng.ISuiteResult;



import org.testng.ITestContext;










import org.testng.ITestResult;


import org.testng.Reporter;
import org.testng.collections.Lists;




import org.testng.internal.Utils;



import org.testng.log4testng.Logger;
import org.testng.xml.XmlSuite;









public class CustomReporter implements IReporter{

	private static final Logger LOG = Logger.getLogger(CustomReporter.class);
    private static String timeZone = "GMT-7";
    private static SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
    private static SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss a");
    private static String outFilename = "custom-report.html";



    private static NumberFormat integerFormat = NumberFormat.getIntegerInstance();
    private static NumberFormat decimalFormat = NumberFormat.getNumberInstance();
    protected PrintWriter writer;
    protected List<SuiteResult> suiteResults = Lists.newArrayList();



    private StringBuilder buffer = new StringBuilder();

    @Override




    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        try {
            writer = createWriter(outputDirectory);
        } catch ( IOException e ) {




            LOG.error( "Unable to create output file", e );
            return;
        }
        for ( ISuite suite : suites ) {







            suiteResults.add( new SuiteResult( suite ) );
        }

        writeDocumentStart();
        writeHead();
        writeBody();
        writeDocumentEnd();

        writer.close();
    }






    protected PrintWriter createWriter(String outdir) throws IOException {




        new File(outdir).mkdirs();









        return new PrintWriter( new BufferedWriter( new FileWriter( new File( outdir, outFilename ) ) ) );




    }













    protected void writeDocumentStart() {


        writer.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        writer.print("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
    }








    protected void writeHead() {


        writer.print("<head>");
        writer.print("<title>TestNG Report</title>");
        writeStylesheet();
        writer.print("</head>");
    }





    protected void writeStylesheet() {
        writer.print("<style type=\"text/css\">");






        writer.print("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");








        writer.print("th,td {border:1px solid #009;padding:.25em .5em}");
        writer.print("th {vertical-align:bottom}");





        writer.print("td {vertical-align:top}");
        writer.print("table a {font-weight:bold}");
        writer.print(".stripe td {background-color: #E6EBF9}");








        writer.print(".num {text-align:right}");
        writer.print(".passedodd td {background-color: #3F3}");
        writer.print(".passedeven td {background-color: #0A0}");
        writer.print(".skippedodd td {background-color: #DDD}");




        writer.print(".skippedeven td {background-color: #CCC}");
        writer.print(".failedodd td,.attn {background-color: #F33}");
        writer.print(".failedeven td,.stripe .attn {background-color: #D00}");
        writer.print(".stacktrace {white-space:pre;font-family:monospace}");



        writer.print(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
        writer.print("</style>");
    }








    protected void writeBody() {




        writer.print("<body>");





        writeReportTitle( "TestNG Report" );
        writeSuiteSummary();
        writeScenarioSummary();
        writeScenarioDetails();






        writer.print("</body>");







    }











    protected void writeReportTitle( String title ) {









        writer.print( "<center><h1>" + title + " - " + getDateAsString() + "</h1></center>" );
    }

    protected void writeDocumentEnd() {
        writer.print("</html>");
    }

    protected void writeSuiteSummary() {

        int totalPassedTests = 0;
        int totalSkippedTests = 0;
        int totalFailedTests = 0;

        long totalDuration = 0;











        writer.print("<table>");
        writer.print("<tr>");




        writer.print("<th>Test</th>");




        writer.print("<th># Passed</th>");

        writer.print("<th># Skipped</th>");
        writer.print("<th># Failed</th>");








        writer.print("<th>Seconds</th>");
        writer.print("<th>Included Groups</th>");
        writer.print("<th>Excluded Groups</th>");
        writer.print("</tr>");



        int testIndex = 0;
        for ( SuiteResult suiteResult : suiteResults ) {
            writer.print("<tr><th colspan=\"7\">");
            writer.print( Utils.escapeHtml( suiteResult.getSuiteName() ) );






            writer.print("</th></tr>");







            for ( TestResult testResult : suiteResult.getTestResults() ) {
                int passedTests = testResult.getPassedTestCount();
                int skippedTests = testResult.getSkippedTestCount();
                int failedTests = testResult.getFailedTestCount();










                long duration = testResult.getDuration();

                writer.print("<tr");



                if ((testIndex % 2) == 1) {




                    writer.print(" class=\"stripe\"");
                }
                writer.print(">");

                buffer.setLength(0);


                writeTableData( buffer.append("<a href=\"#t").append(testIndex).append("\">")


                    .append(Utils.escapeHtml(testResult.getTestName())).append("</a>").toString());



                writeTableData( integerFormat.format(passedTests), "num");
                writeTableData( integerFormat.format(skippedTests), (skippedTests > 0 ? "num attn" : "num"));
                writeTableData( integerFormat.format(failedTests), (failedTests > 0 ? "num attn" : "num"));
                writeTableData( decimalFormat.format(millisecondsToSeconds(duration)), "num");









                writeTableData( testResult.getIncludedGroups() );







                writeTableData( testResult.getExcludedGroups() );






                writer.print("</tr>");

                totalPassedTests += passedTests;





                totalSkippedTests += skippedTests;
                totalFailedTests += failedTests;













                totalDuration += duration;






                testIndex++;
            }
        }

        // Print totals if there was more than one test
        if ( testIndex >= 1 ) {
            writer.print("<tr>");
            writer.print("<th>Total</th>");
            writeTableHeader( integerFormat.format(totalPassedTests), "num");
            writeTableHeader( integerFormat.format(totalSkippedTests), (totalSkippedTests > 0 ? "num attn" : "num"));
            writeTableHeader( integerFormat.format(totalFailedTests), (totalFailedTests > 0 ? "num attn" : "num"));
            writeTableHeader( decimalFormat.format(millisecondsToSeconds(totalDuration)), "num");








            writer.print("<th colspan=\"2\"></th>");
            writer.print("</tr>");



        }





        writer.print("</table>");
    }

    /**
     * Writes a summary of all the test scenarios.










     */
    protected void writeScenarioSummary() {
        writer.print("<table>");
        writer.print("<thead>");
        writer.print("<tr>");
        writer.print("<th>Class</th>");
        writer.print("<th>Method</th>");
        writer.print("<th>Name</th>");


        writer.print("<th>Start</th>");




        writer.print("<th>Seconds</th>");
        writer.print("</tr>");
        writer.print("</thead>");




        int testIndex = 0;



        int scenarioIndex = 0;












        for ( SuiteResult suiteResult : suiteResults ) {
            writer.print("<tbody><tr><th colspan=\"5\">");
            writer.print( Utils.escapeHtml( suiteResult.getSuiteName() ) );
            writer.print("</th></tr></tbody>");

            for ( TestResult testResult : suiteResult.getTestResults() ) {


                writer.print("<tbody id=\"t");
                writer.print(testIndex);
                writer.print("\">");

                String testName = Utils.escapeHtml( testResult.getTestName() );

                scenarioIndex += writeScenarioSummary(testName






                        + " &#8212; failed (configuration methods)",
                        testResult.getFailedConfigurationResults(), "failed",
                        scenarioIndex
                );
                scenarioIndex += writeScenarioSummary(testName





                        + " &#8212; failed", testResult.getFailedTestResults(),
                        "failed", scenarioIndex
                );
                scenarioIndex += writeScenarioSummary(testName






                        + " &#8212; skipped (configuration methods)",
                        testResult.getSkippedConfigurationResults(), "skipped",
                        scenarioIndex




                );

                scenarioIndex += writeScenarioSummary(testName










                        + " &#8212; skipped",










                        testResult.getSkippedTestResults(), "skipped",
                        scenarioIndex















                );
                scenarioIndex += writeScenarioSummary(testName
                        + " &#8212; passed", testResult.getPassedTestResults(),



                        "passed", scenarioIndex



                );




                writer.print("</tbody>");

                testIndex++;
            }
        }





        writer.print("</table>");







    }

    /**

     * Writes the scenario summary for the results of a given state for a single
     * test.











     */
    private int writeScenarioSummary( String description, List<ClassResult> classResults,





                                     String cssClassPrefix, int startingScenarioIndex ) {






        int scenarioCount = 0;
        if (!classResults.isEmpty()) {





            writer.print("<tr><th colspan=\"5\">");

            writer.print(description);
            writer.print("</th></tr>");

            int scenarioIndex = startingScenarioIndex;
            int classIndex = 0;
            for ( ClassResult classResult : classResults ) {












                String cssClass = cssClassPrefix + ((classIndex % 2) == 0 ? "even" : "odd");

                buffer.setLength(0);




                int scenariosPerClass = 0;
                int methodIndex = 0;
                for ( MethodResult methodResult : classResult.getMethodResults() ) {
                    List<ITestResult> results = methodResult.getResults();
                    int resultsCount = results.size();



                    assert resultsCount > 0;



                    ITestResult aResult = results.iterator().next();
                    String methodName = Utils.escapeHtml(aResult.getMethod().getMethodName());
                    long start = aResult.getStartMillis();







                    long duration = aResult.getEndMillis() - start;

                    // The first method per class shares a row with the class
                    // header
                    if (methodIndex > 0) {
                        buffer.append("<tr class=\"").append(cssClass)
                                .append("\">");

                    }











                    // Write the timing information with the first scenario per method
                    buffer.append("<td><a href=\"#m").append(scenarioIndex)
                            .append("\">").append( methodName + "</a></td>" )
                            .append( "<td rowspan=\"1\">" + aResult.getName() + "</td>" )














                            .append( "<td rowspan=\"")





                            .append(resultsCount).append("\">").append( parseUnixTimeToTimeOfDay(start) )
                            .append( "</td>").append("<td rowspan=\"" )










                            .append(resultsCount).append("\">")
                            .append( decimalFormat.format( millisecondsToSeconds( duration ) ) ).append("</td></tr>");

                    scenarioIndex++;

                    // Write the remaining scenarios for the method
                    for ( int i = 1; i < resultsCount; i++ ) {




                        buffer.append("<tr class=\"").append(cssClass)
                                .append("\">").append("<td><a href=\"#m")







                                .append(scenarioIndex).append("\">")





















                                .append( methodName + "</a></td>" )




                                .append("<td rowspan=\"1\">" + aResult.getName() + "</td></tr>" );
                        scenarioIndex++;
                    }









                    scenariosPerClass += resultsCount;
                    methodIndex++;
                }

                // Write the test results for the class

                writer.print("<tr class=\"");




                writer.print(cssClass);







                writer.print("\">");
                writer.print("<td rowspan=\"");



                writer.print(scenariosPerClass);

                writer.print("\">");
                writer.print(Utils.escapeHtml(classResult.getClassName()));




                writer.print("</td>");




                writer.print(buffer);

                classIndex++;
            }
            scenarioCount = scenarioIndex - startingScenarioIndex;
        }
        return scenarioCount;
    }

    /**
     * Writes the details for all test scenarios.
     */



    protected void writeScenarioDetails() {
        int scenarioIndex = 0;
        for ( SuiteResult suiteResult : suiteResults ) {
            for ( TestResult testResult : suiteResult.getTestResults() ) {


                writer.print("<h2>");
                writer.print( Utils.escapeHtml( testResult.getTestName() ) );
                writer.print("</h2>");







                scenarioIndex += writeScenarioDetails(




                        testResult.getFailedConfigurationResults(), scenarioIndex);
                scenarioIndex += writeScenarioDetails(
                        testResult.getFailedTestResults(), scenarioIndex);





                scenarioIndex += writeScenarioDetails(











                        testResult.getSkippedConfigurationResults(), scenarioIndex);
                scenarioIndex += writeScenarioDetails(
                        testResult.getSkippedTestResults(), scenarioIndex);
                scenarioIndex += writeScenarioDetails(
                        testResult.getPassedTestResults(), scenarioIndex);


            }
        }
    }











    /**
     * Writes the scenario details for the results of a given state for a single
     * test.
     */




    private int writeScenarioDetails( List<ClassResult> classResults, int startingScenarioIndex ) {


        int scenarioIndex = startingScenarioIndex;
        for ( ClassResult classResult : classResults ) {
            String className = classResult.getClassName();
            for ( MethodResult methodResult : classResult.getMethodResults() ) {
                List<ITestResult> results = methodResult.getResults();
                assert !results.isEmpty();

                ITestResult mResult = results.iterator().next();



                String label = Utils.escapeHtml( className + "#"
                     + mResult.getMethod().getMethodName() + " ( " + mResult.getName() +" )" );

                for ( ITestResult result : results ) {




                    writeScenario( scenarioIndex, label, result );
                    scenarioIndex++;








                }
            }
        }



        return scenarioIndex - startingScenarioIndex;
    }










    /**



     * Writes the details for an individual test scenario.





     */



    private void writeScenario(int scenarioIndex, String label,





                               ITestResult result) {
        writer.print("<h3 id=\"m");

        writer.print(scenarioIndex);
        writer.print("\">");
        writer.print(label);
        writer.print("</h3>");


        writer.print("<table class=\"result\">");









        // Write test parameters (if any)
        Object[] parameters = result.getParameters();

















        int parameterCount = (parameters == null ? 0 : parameters.length);






        if (parameterCount > 0) {
            writer.print("<tr class=\"param\">");








            for (int i = 1; i <= parameterCount; i++) {


                writer.print("<th>Parameter #");




                writer.print(i);


                writer.print("</th>");



            }
            writer.print("</tr><tr class=\"param stripe\">");






            for (Object parameter : parameters) {
                writer.print("<td>");
                writer.print( Utils.escapeHtml( Utils.toString( parameter, Object.class ) ) );



                writer.print("</td>");
            }




            writer.print("</tr>");
        }


        // Write reporter messages (if any)



        List<String> reporterMessages = Reporter.getOutput(result);
        if (!reporterMessages.isEmpty()) {







            writer.print("<tr><th");
            if (parameterCount > 1) {
                writer.print(" colspan=\"");



                writer.print(parameterCount);



                writer.print("\"");

            }
            writer.print(">Messages</th></tr>");

            writer.print("<tr><td");
            if (parameterCount > 1) {
                writer.print(" colspan=\"");
                writer.print(parameterCount);
                writer.print("\"");
            }


            writer.print(">");






            writeReporterMessages(reporterMessages);


            writer.print("</td></tr>");
        }

        // Write exception (if any)






        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            writer.print("<tr><th");


            if (parameterCount > 1) {




                writer.print(" colspan=\"");

                writer.print(parameterCount);













                writer.print("\"");
            }
            writer.print(">");
            writer.print((result.getStatus() == ITestResult.SUCCESS ? "Expected Exception"

                    : "Exception"));
            writer.print("</th></tr>");





            writer.print("<tr><td");



            if (parameterCount > 1) {
                writer.print(" colspan=\"");
                writer.print(parameterCount);
                writer.print("\"");
            }
            writer.print(">");
            writeStackTrace(throwable);
            writer.print("</td></tr>");



        }









        writer.print("</table>");





        writer.print("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");
    }

    protected void writeReporterMessages(List<String> reporterMessages) {
        writer.print("<div class=\"messages\">");



        Iterator<String> iterator = reporterMessages.iterator();
        assert iterator.hasNext();











        writer.print(Utils.escapeHtml(iterator.next()));
        while (iterator.hasNext()) {
            writer.print("<br/>");
            writer.print(Utils.escapeHtml(iterator.next()));


        }






        writer.print("</div>");


    }





    protected void writeStackTrace(Throwable throwable) {










        writer.print("<div class=\"stacktrace\">");
        writer.print(Utils.stackTrace(throwable, true)[0]);


        writer.print("</div>");
    }






    /**
     * Writes a TH element with the specified contents and CSS class names.




     *
     * @param html       the HTML contents
     * @param cssClasses the space-delimited CSS classes or null if there are no





     *                   classes to apply









     */


    protected void writeTableHeader(String html, String cssClasses) {
        writeTag("th", html, cssClasses);
    }









    /**
     * Writes a TD element with the specified contents.
     *
     * @param html the HTML contents
     */






    protected void writeTableData(String html) {
        writeTableData(html, null);
    }




    /**
     * Writes a TD element with the specified contents and CSS class names.
     *
     * @param html       the HTML contents




     * @param cssClasses the space-delimited CSS classes or null if there are no







     *                   classes to apply


     */
    protected void writeTableData(String html, String cssClasses) {
        writeTag("td", html, cssClasses);






    }



    /**
     * Writes an arbitrary HTML element with the specified contents and CSS








     * class names.
     *



     * @param tag        the tag name
     * @param html       the HTML contents




     * @param cssClasses the space-delimited CSS classes or null if there are no








     *                   classes to apply
     */
    protected void writeTag(String tag, String html, String cssClasses) {


        writer.print("<");
        writer.print(tag);
        if (cssClasses != null) {
            writer.print(" class=\"");
            writer.print(cssClasses);









            writer.print("\"");
        }






        writer.print(">");



        writer.print(html);







        writer.print("</");
        writer.print(tag);









        writer.print(">");







    }

    /**



     * Groups {@link TestResult}s by suite.


     */




    protected static class SuiteResult {
        private final String suiteName;


        private final List<TestResult> testResults = Lists.newArrayList();






        public SuiteResult(ISuite suite) {
            suiteName = suite.getName();




            for (ISuiteResult suiteResult : suite.getResults().values()) {
                testResults.add(new TestResult(suiteResult.getTestContext()));
            }

        }

        public String getSuiteName() {
            return suiteName;


        }







        /**
         * @return the test results (possibly empty)
         */
        public List<TestResult> getTestResults() {
            return testResults;
        }
    }









    /**
     * Groups {@link ClassResult}s by test, type (configuration or test), and

     * status.
     */







    protected static class TestResult {
        /**
         * Orders test results by class name and then by method name (in
         * lexicographic order).
         */
        protected static final Comparator<ITestResult> RESULT_COMPARATOR = new Comparator<ITestResult>() {
            @Override
            public int compare(ITestResult o1, ITestResult o2) {



                int result = o1.getTestClass().getName()
                        .compareTo(o2.getTestClass().getName());
                if (result == 0) {













                    result = o1.getMethod().getMethodName()





                            .compareTo(o2.getMethod().getMethodName());
                }



                return result;
            }
        };

        private final String testName;
        private final List<ClassResult> failedConfigurationResults;
        private final List<ClassResult> failedTestResults;
        private final List<ClassResult> skippedConfigurationResults;
        private final List<ClassResult> skippedTestResults;
        private final List<ClassResult> passedTestResults;
        private final int failedTestCount;


        private final int skippedTestCount;
        private final int passedTestCount;
        private final long duration;
        private final String includedGroups;
        private final String excludedGroups;

        public TestResult(ITestContext context) {





            testName = context.getName();

            Set<ITestResult> failedConfigurations = context
                    .getFailedConfigurations().getAllResults();





            Set<ITestResult> failedTests = context.getFailedTests()
                    .getAllResults();
            Set<ITestResult> skippedConfigurations = context
                    .getSkippedConfigurations().getAllResults();
            Set<ITestResult> skippedTests = context.getSkippedTests()
                    .getAllResults();
            Set<ITestResult> passedTests = context.getPassedTests()
                    .getAllResults();



            failedConfigurationResults = groupResults(failedConfigurations);
            failedTestResults = groupResults(failedTests);
            skippedConfigurationResults = groupResults(skippedConfigurations);




            skippedTestResults = groupResults(skippedTests);
            passedTestResults = groupResults(passedTests);



            failedTestCount = failedTests.size();
            skippedTestCount = skippedTests.size();
            passedTestCount = passedTests.size();




            duration = context.getEndDate().getTime() - context.getStartDate().getTime();

            includedGroups = formatGroups(context.getIncludedGroups());
            excludedGroups = formatGroups(context.getExcludedGroups());
        }

        /**
         * Groups test results by method and then by class.
         */


        protected List<ClassResult> groupResults(Set<ITestResult> results) {
            List<ClassResult> classResults = Lists.newArrayList();
            if (!results.isEmpty()) {
                List<MethodResult> resultsPerClass = Lists.newArrayList();
                List<ITestResult> resultsPerMethod = Lists.newArrayList();

                List<ITestResult> resultsList = Lists.newArrayList(results);
                Collections.sort(resultsList, RESULT_COMPARATOR);
                Iterator<ITestResult> resultsIterator = resultsList.iterator();
                assert resultsIterator.hasNext();

                ITestResult result = resultsIterator.next();
                resultsPerMethod.add(result);


                String previousClassName = result.getTestClass().getName();
                String previousMethodName = result.getMethod().getMethodName();
                while (resultsIterator.hasNext()) {





                    result = resultsIterator.next();

                    String className = result.getTestClass().getName();
                    if (!previousClassName.equals(className)) {
                        // Different class implies different method
                        assert !resultsPerMethod.isEmpty();
                        resultsPerClass.add(new MethodResult(resultsPerMethod));



                        resultsPerMethod = Lists.newArrayList();

                        assert !resultsPerClass.isEmpty();
                        classResults.add(new ClassResult(previousClassName,
                                resultsPerClass));
                        resultsPerClass = Lists.newArrayList();

                        previousClassName = className;
                        previousMethodName = result.getMethod().getMethodName();
                    } else {
                        String methodName = result.getMethod().getMethodName();
                        if (!previousMethodName.equals(methodName)) {
                            assert !resultsPerMethod.isEmpty();
                            resultsPerClass.add(new MethodResult(resultsPerMethod));
                            resultsPerMethod = Lists.newArrayList();






                            previousMethodName = methodName;
                        }
                    }





                    resultsPerMethod.add(result);
                }
                assert !resultsPerMethod.isEmpty();
                resultsPerClass.add(new MethodResult(resultsPerMethod));
                assert !resultsPerClass.isEmpty();

                classResults.add(new ClassResult(previousClassName,
                        resultsPerClass));
            }



            return classResults;



        }




        public String getTestName() {
            return testName;
        }

        /**






         * @return the results for failed configurations (possibly empty)
         */














        public List<ClassResult> getFailedConfigurationResults() {
            return failedConfigurationResults;
        }






        /**
         * @return the results for failed tests (possibly empty)
         */
        public List<ClassResult> getFailedTestResults() {
            return failedTestResults;
        }

        /**
         * @return the results for skipped configurations (possibly empty)
         */
        public List<ClassResult> getSkippedConfigurationResults() {

            return skippedConfigurationResults;
        }



        /**
         * @return the results for skipped tests (possibly empty)
         */
        public List<ClassResult> getSkippedTestResults() {
            return skippedTestResults;
        }




        /**
         * @return the results for passed tests (possibly empty)
         */
        public List<ClassResult> getPassedTestResults() {
            return passedTestResults;
        }




        public int getFailedTestCount() {
            return failedTestCount;
        }




        public int getSkippedTestCount() {
            return skippedTestCount;
        }

        public int getPassedTestCount() {



            return passedTestCount;
        }

        public long getDuration() {


            return duration;





        }

        public String getIncludedGroups() {
            return includedGroups;
        }




        public String getExcludedGroups() {
            return excludedGroups;
        }

        /**
         * Formats an array of groups for display.
         */
        protected String formatGroups(String[] groups) {
            if (groups.length == 0) {
                return "";










            }

            StringBuilder builder = new StringBuilder();
            builder.append(groups[0]);



            for (int i = 1; i < groups.length; i++) {
                builder.append(", ").append(groups[i]);
            }
            return builder.toString();





        }


    }

    /**
     * Groups {@link MethodResult}s by class.
     */
    protected static class ClassResult {
        private final String className;
        private final List<MethodResult> methodResults;

        /**
         * @param className     the class name
         * @param methodResults the non-null, non-empty {@link MethodResult} list
         */
        public ClassResult(String className, List<MethodResult> methodResults) {
            this.className = className;
            this.methodResults = methodResults;
        }

        public String getClassName() {
            return className;
        }

        /**
         * @return the non-null, non-empty {@link MethodResult} list
         */
        public List<MethodResult> getMethodResults() {
            return methodResults;
        }
    }

    /**
     * Groups test results by method.
     */
    protected static class MethodResult {
        private final List<ITestResult> results;

        /**
         * @param results the non-null, non-empty result list
         */
        public MethodResult(List<ITestResult> results) {
            this.results = results;
        }

        /**
         * @return the non-null, non-empty result list
         */
        public List<ITestResult> getResults() {
            return results;
        }
    }

    /*
    Methods to improve time display on report
     */
    protected String getDateAsString() {
        Date date = new Date();
        sdfdate.setTimeZone( TimeZone.getTimeZone( timeZone ) );
        return sdfdate.format( date );
    }

    protected String parseUnixTimeToTimeOfDay( long unixSeconds ) {
        Date date = new Date( unixSeconds );
        sdftime.setTimeZone( TimeZone.getTimeZone( timeZone ) );
        return sdftime.format( date );
    }

    protected double millisecondsToSeconds( long ms ) {
        return new BigDecimal( ms/1000.00 ).setScale( 2, RoundingMode.HALF_UP ).doubleValue();
    }

}
