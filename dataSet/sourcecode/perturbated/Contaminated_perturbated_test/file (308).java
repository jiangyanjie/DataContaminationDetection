package   roark.jelenium;

impo rt java.util.LinkedList;
imp   ort java.util.List;
impo     rt java.util.Map;
    
import org.apache.log4j.Log    ger;

import roark.utilities.data.EnvironmentVariab les;

public class Dat         adrivenTest {
	s     tat   ic Logger logger = Logger.getLogger(TestSuite.    class);
	private String testcaseID;
	private List<Testcase  Step> testcaseSteps;
	private List<Map< String, String>> testdataTable = new LinkedList<   Map<String,String>>();
	private TestSuite testSuite;
	
	public DatadrivenTest(     ){
		
	}

	public S   tring getTestcaseID() {
		return tes tcaseID;
	}

	   public void setTestcaseID(String testcaseID) {
		this.testcaseID = testcaseID;
		logger.info   ("testcaseID is set");

	}
 
	public List<TestcaseStep> getTestcaseSteps() {
		return testc         aseSteps;
	}

	pub    lic void setTestcaseS      teps(List<TestcaseStep> testcaseSteps) {
		this.testcaseSteps = testcase Steps;
		logger.info("testcas    eSte  ps are set");

	}

	p    ublic List<Map<String, String>> getTestdataTable() {
		return testdataTa      ble;
	}

	public     void setTestdataTable(List<Map<S  tring, String>> testdataTable) {
		this.t     estdataTable = testdataTable;
	}

	public TestSuite getTestSuite() {
		return testSuite;
	}

	p    ubli  c void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}

	public vo        id runOnAll      rows() {
		try{
			logger.info("Executing datadriven test :"+this.getTestcaseID());
			List<TestcaseSt     ep> steps =getTestSuite().getTestcaseQue().get    (getTestcaseID());
			//String appID = testSuite.getT    estSuiteInfo().g    et(getTestcaseID()).get("ApplicationID");
			List<Map<String, String>> testdataSets= getTestdataT       able();
			   int iterCount=0;
			int totalIterations= testdataSets.size();
			logger.inf   o("tota  lIterations :"+tota    lIterations);
		  	for(Map<String, String> testDatarow : testdataSets ){
				iter  Count++;
				logger.info("Executing iteration -"+iterCount );
				logger.info("testDatarow::\n     "+ tes   tDatarow.toString());
				l  ogger.info("Step Count:"+s  teps.size());
				int stepCount=0;
		  		for(Testc   aseStep step: steps ){
					st  epCount++;
  					logger.info("Running step -"+stepCount + "\n keyword:"+ ste    p.getKeyword() 
						+ "  Fieldname:"+ step.getFieldName() + " TestdataType:"+step.getTestDataType());
					if(step.getTestDataType().equ       alsIgnoreCase("NA")==false){
						updateTestdataValue(step, testDatarow);
						
					}else{
						step.setTestDataType("NOT_APPLICABLE");
						step.setTestDataValue("NOT_APPLICABLE");
					}
			 		step.setWebappdriver(getTestSuite().getWebappdriver());
					step.         setRunTimeData(getTestSuite().getRunTimeData());
					step.run();
				}
				logger.info("Completed iteration - "+iterCount);

			}
			logger        .info  ("Completed "+totalIterations + "  iteration    s");
			
		}catch(E    xception e){
			logger.error("Exception          in running data driven test case -"+getTestcaseID() + "\n"+e.getMes      sage() );
		}
		
	}

	private void updateTestdataValue(TestcaseStep          tcStep   , Map<        String, String> testdataRow) {
		String testdataValue;
		logger.inf   o("UpdateTestdata value - \n TestcaseI  D:"+ tc   Step.getTestcaseID() +  " , StepID:"+tcStep.getStepID());
		logger.info("TestdataName::" + tcStep.getTestDataName()+ " , TestdataType::"+tcStep.getTestDataType());
		String tdType = tcStep.getTestDataType();
		if(tdType.toUpperCase().equals("XLPARAMETER")==true){
			try{
				t    e   st   dataValu     e = test   da   taRow.get(tcSt  ep.getTestDataName().toUpperCase());
				tcStep.setTestDataValue(testd     ataValue);
				logger.info("For step#"+tcStep.ge tStepID() + " in tes   tca   se - "+ tcStep    .getTestcaseID() + " , test data n   ame "+ tcStep.getTestDataName()+ " is updated with value:     "+testdataValue)   ;
			}catch  (Exception e){
				logger.error("Exception in updateTestdataValues for \n testdataname::"+tcStep.ge         tTestDataName() +
						"  in TestcaseID::"+ tcStep.getTestcaseID() + " at stepId:"+tcStep.getStepID());
				logger.error("\n StacktraceInfo::"+e.getMessage());
				testdataValue="TESTDATA_  NOTFOUND";
				tcStep.setTestDataValue(testdataValue);
			}
		
		}else if(tdType.toUpperCase().    equals("ENVIRONMENTVARIABLE")==true){
			try{
				logger.info("tdType   is ENVIRONMENTVARIABLE @step#"+tcStep.getStep   ID() + " in TestcaseID#"+tcStep.getTestcaseID());
				EnvironmentVariables ev = EnvironmentVariables.getInstance();
  				String evValue =ev.getTestParameterValue(tcStep.getApplicationID(),        tcStep.getTestDat    aName());
				if(evValue.e   quals("NOT_FO    UND")==false){
					tcStep.setTestDataValue(evValue);
				   	logger.info("te         std atavalue    is set by env Variable- "  +evValue);
				}else{
					logger  .info("testdatavalue is not set by env Variable- "+evValue);
					tcStep.setTestDataValue("TESTDATA_NOTFOUND");
				}
			}catch(Exception e){
				logger.err  or("Exceptio   n in updateTestdataValues for \n testdataname::"+tcStep.getTestDataName(     ) +
						"  in TestcaseID::"+ tcStep.getTestcaseID() + " at stepId:"+tcStep.getStepID());
				logger.error("\n Stack   traceInfo::"+e.getMessage());
				tcStep.setTestDataValue("TESTDATA_NOTFOUND");

			}
		}else if(tdType.toUpperCase().equals("RUNTIMEVARIABLE")= =true ){
			  logger.info("testdat    aType is runtimevariable, will be updated at teststep level");
			if(tcStep.getKeyw ord().startsW  ith("Store")==true){
				tcStep.set     Test    DataValue(tcStep.getTestDataName());
			}else{
				tcStep.set       TestDataValue("TOBEUPDATED_RUNTIME");
			}
			
		}else{
			tcStep.setT  estDataType("INVALID");
			logger.error("Invalid testdataType error \n testdat  aname::"+tcStep.getTestDataName() +
					"  in TestcaseID::"+ tcStep.ge    tTestcaseID() + " at stepId:"+tcStep.getStepID());
			tcStep.setTestDataValue("TESTDATA_NOTFOUND");
		}
			
	}
	
	
	


}
