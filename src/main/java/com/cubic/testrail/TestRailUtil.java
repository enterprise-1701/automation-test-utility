package com.cubic.testrail;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.cubic.genericutils.FileUtil;
import com.cubic.genericutils.GenericConstants;
import com.cubic.logutils.Log4jUtil;

public class TestRailUtil {

    public static final String CLASS_NAME = "TestScript_Driver";
    private static final Logger LOG = Logger.getLogger(CLASS_NAME);
    
	public APIClient trclient=null;
	private static Hashtable<String , String> propTable = GenericConstants.GENERIC_FW_CONFIG_PROPERTIES;

	public TestRailUtil(String base_url,String userName, String password){		
		trclient=new APIClient(base_url);
		trclient.setUser(userName);
		trclient.setPassword(password);	
	}

	/**
	 * Get the Project ID mapped to the Project Name provided
	 * @param projectName : Project name
	 * @return String id : Project ID 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws APIException
	 * @author Cigniti
	 */

	private String getProjectID(String projectName) throws MalformedURLException, IOException, APIException {
		String id = null;
		JSONArray jsonArr= getAllProjectsofTestRail();
		JSONObject jsonObj=null;
		for(int i=0;i<jsonArr.size();i++){
			jsonObj=(JSONObject)jsonArr.get(i);
			if((jsonObj.get("name").toString().equalsIgnoreCase(projectName))){
				id=jsonObj.get("id").toString();
				break;
			}
		}		 
		return id;
	}



	/**
	 * Get the Suite ID mapped to the provided Suite Name in the Project
	 * @param suiteName
	 * @param projectID
	 * @return id : Suite ID
	 * @throws Throwable
	 * @author Cigniti
	 */
	private String getSuiteID(String suiteName,String projectID) throws Throwable{

		String id = null;
		JSONArray jsonArr= getAllSuitesOfProject(projectID);
		JSONObject jsonObj=null;
		for(int i=0;i<jsonArr.size();i++){
			jsonObj=(JSONObject)jsonArr.get(i);
			if((jsonObj.get("name").toString().equalsIgnoreCase(suiteName))){
				id=jsonObj.get("id").toString();
				break;
			}
		}
		return id;
	}

	/**
	 * Get Test Case ID's that are available under a suite of a given project in JSON Array format
	 * @param projectID
	 * @param suiteID
	 * @return
	 * @throws Throwable
	 * @author Cigniti
	 */
	private JSONArray getTestCaseIDs(String projectID,String suiteID) throws Throwable{
		JSONArray jsonArr= getAllTestCasesOfSuite(projectID, suiteID);
		JSONArray testcaseIDs = new JSONArray();
		JSONObject jsonObj=null;

		for(int i=0;i<jsonArr.size();i++){
			jsonObj=(JSONObject)jsonArr.get(i);
			//			 System.out.println("Test Case ID number  " +jsonObj.get("id").toString());
			testcaseIDs.add(jsonObj.get("id").toString());
		}
		return testcaseIDs;
	}

	/**
	 * POST method to add test runs for test cases available under specified Suite in specified Project 
	 * @param projectID
	 * @param suiteID
	 * @return
	 * @throws Throwable
	 * @author Cigniti
	 */
	private JSONObject addTestRun(String projectID,String suiteID,Date currentTimeStamp) throws Throwable{
		JSONParser parser = new JSONParser();
		JSONArray testCases= getAllTestCasesOfSuite(projectID, suiteID);
		String testRunPostRequestJSON = FileUtil.readFile(GenericConstants.TEST_CASES_TO_BE_EXECUTED_JSON_FILE_PATH+GenericConstants.TEST_RAIL_TEST_RUN_TEMPLATE_JSON);
		String testRunPostRequestResetJSON = testRunPostRequestJSON;
		String testCaseListJSON = FileUtil.readFile(GenericConstants.TEST_CASES_TO_BE_EXECUTED_JSON_FILE_PATH+GenericConstants.TEST_CASES_TO_BE_EXECUTED_JSON);
		testRunPostRequestJSON=testRunPostRequestJSON.replaceAll("<suite_id>", suiteID);
		testRunPostRequestJSON=testRunPostRequestJSON.replaceAll("<rand>", ""+currentTimeStamp.toString().replaceAll(":", "_"));

		JSONObject testRuns = null;
		Object obj = parser.parse(testRunPostRequestJSON);
		Object testCaseObj = parser.parse(testCaseListJSON);
		Object testRunResetObj = parser.parse(testRunPostRequestResetJSON);
		JSONObject jsonObject = (JSONObject) obj;
		JSONObject testCaseJsonObj = (JSONObject) testCaseObj;
		JSONObject testCaseBlock = null;
		JSONObject testRunResetJSONObject = (JSONObject) testRunResetObj;
		JSONArray tcIDs = new JSONArray();
		JSONArray tcIDsRest = new JSONArray();
		if(((JSONArray)jsonObject.get("case_ids")).size()==0){

			JSONArray testCasesList = (JSONArray) testCaseJsonObj.get("TestCases");
			for(int i=0;i<testCases.size();i++){
				testCaseBlock=(JSONObject)testCases.get(i);
				//		 System.out.println("Test Case ID number  " +testCaseBlock.get("id").toString());
				for(int j=0;j<testCasesList.size();j++){			 
					if(testCaseBlock.get("title").toString().contains(testCasesList.get(j).toString())){
						tcIDs.add(testCaseBlock.get("id"));
						break;
					}
				}
			}
		}else{
			tcIDs=(JSONArray)jsonObject.get("case_ids");

		}

		jsonObject.put("case_ids", tcIDs);
		testRunResetJSONObject.put("case_ids", tcIDsRest);
		try (FileWriter file = new FileWriter(GenericConstants.TEST_CASES_TO_BE_EXECUTED_JSON_FILE_PATH+GenericConstants.TEST_RAIL_TEST_RUN_TEMPLATE_JSON)) {

			file.write(testRunResetJSONObject.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		testRuns= (JSONObject) trclient.sendPost("add_run/"+projectID, jsonObject);

		return testRuns;
	}

	/**
	 * Update status of all tests under the Test Run based on the execution results
	 * @param runID
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws APIException
	 * @throws ParseException
	 */
	private void updateResultsOfTestRun(String runID) throws MalformedURLException, IOException, APIException, ParseException{

		JSONParser parser = new JSONParser();
		JSONArray tests= getAllTestsUnderTestRun(runID);
		String results = FileUtil.readFile(GenericConstants.CUSTOM_REPORTS_RESULTS+"/"+GenericConstants.TEST_RAIL_SUITE_RESULTS_JSON);
		Object obj = parser.parse(results);
		JSONObject resultsJSON = (JSONObject)obj;
		JSONObject test = null;
		JSONObject testResult = null;
		JSONArray testResults = new JSONArray();
		testResults = (JSONArray)resultsJSON.get("TestResults");
		for(int j=0;j<testResults.size();j++){
			testResult=(JSONObject)testResults.get(j);
			String tcID=(String) testResult.get("TestCaseID");
			String tcStatus= (String) testResult.get("TestStatus");
			for(int i=0;i<tests.size();i++){
				test=(JSONObject)tests.get(i);
				String testTitle=(String)test.get("title");
				String test_Rail_Test_Case_ID =  String.valueOf(test.get("case_id"));
				if(testTitle.contains(tcID) || test_Rail_Test_Case_ID.equals(tcID)){
					updateResultOfTestID(test.get("id").toString(), tcStatus);
				}
			}
		}

	}


	private void updateResultOfTestRun(String runID,String testID,String status) throws MalformedURLException, IOException, APIException, ParseException{

		JSONParser parser = new JSONParser();
		JSONArray tests= getAllTestsUnderTestRun(runID);
		JSONObject test = null; 
		for(int i=0;i<tests.size();i++){
			test=(JSONObject)tests.get(i);
			String testTitle=(String)test.get("title");
			if(testTitle.contains(testID)){
				updateResultOfTestID(test.get("id").toString(), status);
			}
		}	
	}

	/**
	 * Update result of a particular test with specified status
	 * @param testID
	 * @param status
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws APIException
	 */
	private void updateResultOfTestID(String testID,String status) throws MalformedURLException, IOException, APIException{
		JSONObject testResult= new JSONObject();
		if(status.equalsIgnoreCase("pass")){
			testResult.put("status_id", TestStatus.PASS.getCode().toString());
		}else if(status.equalsIgnoreCase("fail")){
			testResult.put("status_id", TestStatus.FAIL.getCode().toString());
		}
		trclient.sendPost("add_result/"+testID, testResult);
	}
	/**
	 * Pull all the test caseID's under available under test suite
	 * Expected Test title should be "TC_SRS_999_001 : Test Scenario Description"
	 * TC_SRS_999_001 will be the test case ID
	 * @param projectID
	 * @param suiteID
	 */
	private void updateTestCaseList(String projectID,String suiteID){
		JSONArray tcIDs = new JSONArray();
		JSONObject tempTCID=new JSONObject();
		JSONObject tcIDList=new JSONObject();
		try {
			JSONArray testCases= getAllTestCasesOfSuite(projectID, suiteID);
			for(int i=0;i<testCases.size();i++){
				tempTCID=(JSONObject)testCases.get(i);
				String tCTitle=tempTCID.get("title").toString();
				String tcID=tCTitle.split(":")[0].trim();
				tcIDs.add(tcID);
			}
			tcIDList.put("TestCases", tcIDs);
			try (FileWriter file = new FileWriter(GenericConstants.TEST_CASES_TO_BE_EXECUTED_JSON_FILE_PATH+GenericConstants.TEST_CASES_TO_BE_EXECUTED_JSON)) {

				file.write(tcIDList.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Pull all the automation test script references under available under test suite
	 * 
	 * 
	 * @param projectID
	 * @param suiteID
	 */
	public static JSONObject updateTestClassList(String projectID,String suiteID){
		JSONArray tcIDs = new JSONArray();
		JSONArray testRailTestIDs = new JSONArray();
		JSONObject tempTCID=new JSONObject();
		JSONObject tcIDList=new JSONObject();
		String tCAutomationRef=null;
		String tCTestRailRef=null;
		TestRailUtil tr=new TestRailUtil(propTable.get("Test_Rail_Base_Url"),propTable.get("Test_Rail_UserName"),propTable.get("Test_Rail_Password"));
		try {
			if(projectID==null || suiteID==null || projectID=="${ProjectID}" || suiteID == "${SuiteID}" || suiteID == "${SuitID}"){
				throw new Exception("Project ID or Suite ID values are not provided"); 

			}
			JSONArray testCases= tr.getAllTestCasesOfSuite(projectID,suiteID);
			for(int i=0;i<testCases.size();i++){

				tempTCID=(JSONObject)testCases.get(i);
				//			System.out.println("Current Test is ::::::"+tempTCID.toString());
				if(tempTCID.get("custom_automation_reference")!=null){				
					tCAutomationRef=tempTCID.get("custom_automation_reference").toString();
					tCTestRailRef=tempTCID.get("id").toString();
					testRailTestIDs.add(tCTestRailRef);
					if(!tcIDs.toString().contains(tCAutomationRef))
					{					
						tcIDs.add(tCAutomationRef);					
					}
				}
			}
			tcIDList.put("TestClasses", tcIDs);
			tcIDList.put("TestRailCaseIDs", testRailTestIDs);
			try (FileWriter file = new FileWriter(GenericConstants.TEST_CASES_TO_BE_EXECUTED_JSON_FILE_PATH+GenericConstants.TEST_CLASSES_TO_BE_EXECUTED_JSON)) {

				file.write(tcIDList.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tcIDList;
	}

	/**
	 * Create Test run for the Test Suite provided
	 * @param ProjectID
	 * @param SuiteID
	 * @param currentTimeStamp
	 */
	//TODO TestRail Integration Code
	public static void generateTestRunsForTestCases(String ProjectID,String SuiteID,Date currentTimeStamp) {
		// TODO Auto-generated method stub
		TestRailUtil tr=new TestRailUtil(propTable.get("Test_Rail_Base_Url"),propTable.get("Test_Rail_UserName"),propTable.get("Test_Rail_Password"));
		try{		
			if(ProjectID==null || SuiteID==null){
				throw new Exception("Project ID or Suite ID values are not provided");
			}
			tr.updateTestCaseList(ProjectID,SuiteID);
		}catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject testRailObj = new JSONObject();
		JSONObject testRunBlock=new JSONObject();
		try {
			testRunBlock = tr.addTestRun(ProjectID, SuiteID,currentTimeStamp);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testRailObj.put("TestRunID", testRunBlock.get("id"));
		try (FileWriter file = new FileWriter(GenericConstants.CUSTOM_REPORTS_RESULTS+"/"+GenericConstants.TEST_RAIL_SUITE_RESULTS_JSON)) {
			file.write(testRailObj.toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates results in Test Run based on the Automation Test Execution results
	 */
	public static void updateTestResultsinTestRail() {
		// TODO Auto-generated method stub

		TestRailUtil tr=new TestRailUtil(propTable.get("Test_Rail_Base_Url"),propTable.get("Test_Rail_UserName"),propTable.get("Test_Rail_Password"));
		try{

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(GenericConstants.CUSTOM_REPORTS_RESULTS+"/"+GenericConstants.TEST_RAIL_SUITE_RESULTS_JSON));
			JSONObject testRun = (JSONObject) obj;
			tr.updateResultsOfTestRun(testRun.get("TestRunID").toString());

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Update test result of individual test under the Test Run in Test Tail
	 * @param testID
	 * @param Status
	 */
	public static void updateTestResultinTestRail(String testID,String Status) {
		// TODO Auto-generated method stub

		TestRailUtil tr=new TestRailUtil(propTable.get("Test_Rail_Base_Url"),propTable.get("Test_Rail_UserName"),propTable.get("Test_Rail_Password"));
		try{

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(GenericConstants.CUSTOM_REPORTS_RESULTS+"/"+GenericConstants.TEST_RAIL_SUITE_RESULTS_JSON));
			JSONObject testRun = (JSONObject) obj;
			tr.updateResultOfTestRun(testRun.get("TestRunID").toString(),testID,Status);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Get all test cases of given suite ID under Project
	 * @param projectID
	 * @param suiteID
	 * @return
	 */
	private JSONArray getAllTestCasesOfSuite(String projectID,String suiteID){
		JSONArray testCases=new JSONArray();
		try {
			testCases = (JSONArray)trclient.sendGet("get_cases/"+projectID+"&suite_id="+suiteID);
		} catch (IOException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testCases;
	}

	/**
	 * get all projects under the TestRail client
	 * @return
	 */
	private JSONArray getAllProjectsofTestRail(){
		JSONArray projectObjects=new JSONArray();
		try {
			projectObjects = (JSONArray)trclient.sendGet("get_projects");
		} catch (IOException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectObjects;
	}

	/**
	 * Get all suites under the Project
	 * @param projectID
	 * @return
	 */
	private JSONArray getAllSuitesOfProject(String projectID){
		JSONArray suiteObjects=new JSONArray();
		try {
			suiteObjects = (JSONArray)trclient.sendGet("get_suites/"+projectID);
		} catch (IOException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return suiteObjects;
	}

	/**
	 * Get all tests under the Test Run
	 * @param runID
	 * @return
	 */
	private JSONArray getAllTestsUnderTestRun(String runID){
		JSONArray tests=new JSONArray();
		try {
			tests = (JSONArray)trclient.sendGet("get_tests/"+runID);
		} catch (IOException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tests;
	}
	public void updateResultsOfCase(String runId, String caseID,String resultStatus, String comment){
		try {
			TestRailUtil tr=new TestRailUtil(propTable.get("Test_Rail_Base_Url"),propTable.get("Test_Rail_UserName"),propTable.get("Test_Rail_Password"));
			JSONObject testResult= new JSONObject();

			if(resultStatus.equalsIgnoreCase("pass")){
				testResult.put("status_id", TestStatus.PASS.getCode().toString());
				testResult.put("comment", comment);

			}else if(resultStatus.equalsIgnoreCase("fail")){
				testResult.put("status_id", TestStatus.FAIL.getCode().toString());
				testResult.put("comment", comment);
			}
			//trclient.sendPost("add_result/"+testID, testResult);
			trclient.sendPost("add_result_for_case/"+runId+"/"+caseID, testResult);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * Method to create a "reduced" JSONObject with test cases that are present in the given testClassSet HashSet, by
	 * matching the returned Automation References (i.e. Class names) from TestRail with that is in HashSet.  This is useful
	 * when running smaller TestNG suites.  Without this method and calling updateTestClassList() instead, ALL of the test 
	 * cases from TestRail that have an "Automation Reference" field populated will get run regardless of what is in the TestNG.xml
	 * suite.  The caller of this method is responsible for building/creating the testClassSet, which by definition has UNIQUE entries.
	 * @param testClassSet
	 * @param projectID
	 * @param suiteID
	 * @return
	 * @author romeroo
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject createTestClassListFromTestSet(HashSet<String> testClassSet, String projectID, String suiteID){
        JSONArray testRailAutomationRefNames = new JSONArray();
        JSONArray testRailTestIDs = new JSONArray();
        JSONObject tempTCID=new JSONObject();
        JSONObject tcIDList=new JSONObject();
        String tCAutomationRef=null;
        String tCTestRailRef=null;
        LOG.info("INSIDE updateTestClassList");
        
        // Get TestRail Credentials
        TestRailUtil tr = new TestRailUtil(propTable.get("Test_Rail_Base_Url"), propTable.get("Test_Rail_UserName"), propTable.get("Test_Rail_Password"));
        
        try {
            if (projectID == null || suiteID == null || projectID == "${ProjectID}" || suiteID == "${SuiteID}" || suiteID == "${SuitID}") {
                throw new Exception("Project ID or Suite ID values are not provided"); 
            }
            
            // Get all Test Cases in the TestRail Test Suite, then build JSONObject by matching with what is in testClassSet HashSet
            JSONArray testCases = tr.getAllTestCasesOfSuite(projectID, suiteID);
            
            // Cycle through each Test Case "object" in the Test Suite returned by TestRail
            for(int i = 0; i < testCases.size(); i++) {

                tempTCID = (JSONObject)testCases.get(i);
                
                // Check if Test Case in TestRail has the "Automation Reference" field populated
                if(tempTCID.get("custom_automation_reference") != null) {  
                    tCAutomationRef = tempTCID.get("custom_automation_reference").toString();
                    tCTestRailRef = tempTCID.get("id").toString();

                    // Check if Test Class (i.e. "Automation Reference") is contained in the provided HashSet
                    if (testClassSet.contains(tCAutomationRef)) {
                        testRailTestIDs.add(tCTestRailRef);
                        
                        // Check if Test Class (i.e. "Automation Reference") has already been added (shouldn't have to)
                        if(!testRailAutomationRefNames.toString().contains(tCAutomationRef))
                        {                   
                            testRailAutomationRefNames.add(tCAutomationRef);                 
                        }
                        else {
                            LOG.error("DUPLICATE Automation Reference: " + tCAutomationRef + ", with TestRail ID: " + tCTestRailRef + ". Please check TestRail");
                        }
                    }
                }
            }
            
            tcIDList.put("TestClasses", testRailAutomationRefNames);
            tcIDList.put("TestRailCaseIDs", testRailTestIDs);
            
            try (FileWriter file = new FileWriter(GenericConstants.TEST_CASES_TO_BE_EXECUTED_JSON_FILE_PATH+GenericConstants.TEST_CLASSES_TO_BE_EXECUTED_JSON)) {
                file.write(tcIDList.toJSONString());
                file.flush();
            } 
            catch (IOException e) {
                LOG.error(Log4jUtil.getStackTrace(e));
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            LOG.error(Log4jUtil.getStackTrace(e));
            throw new RuntimeException(e);
        }
        
        return tcIDList;
    }
}
