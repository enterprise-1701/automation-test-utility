package com.cubic.reportengine.report;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cubic.genericutils.DateUtil;
import com.cubic.genericutils.FileUtil;
import com.cubic.genericutils.GenericConstants;
import com.cubic.genericutils.TimeUtil;
import com.cubic.logutils.Log4jUtil;
import com.cubic.reportengine.bean.CustomReportBean;
import com.cubic.reportengine.bean.DetailedReportBean;

public class CustomReports {
	private final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	private SummaryReport summaryReport = null;
	private DetailedReport detailedReport = null;
	private Instant executionStartTime = null;
	private Instant executionEndTime = null;
	
	private LinkedHashMap<String, DetailedReportBean> detailedReportMap = null;

	private CustomReportBean customReportBean = null;
	
	private Hashtable<String , String> propTable = GenericConstants.GENERIC_FW_CONFIG_PROPERTIES;

	public CustomReports() {
		customReportBean = new CustomReportBean();
	}

	public CustomReportBean getCustomReportBean() {
		return customReportBean;
	}

	/**
	 * Creates the folder structure for custom report.
	 * 
	 * @throws Exception
	 */
	public void createFolderStructureForCustomReport() throws Exception {
		try {
			executionStartTime = TimeUtil.getCurrentInstant();
			
			summaryReport = new SummaryReport();

			// Form the file paths.
			String dynamicFolder = ""+new Date();
			dynamicFolder = dynamicFolder.replaceAll(":", "_");
			String resultsFolderPath = GenericConstants.CUSTOM_REPORTS_RESULTS;
			String dynamicResultsFolderPath = resultsFolderPath + dynamicFolder;
			
			String detailedReportsFolderPath = dynamicResultsFolderPath + "/"
					+ GenericConstants.CUSTOM_REPORTS_DETAILED_REPORTS;
			String screenshotsFolderPath = detailedReportsFolderPath + "/"
					+ GenericConstants.CUSTOM_REPORTS_SCREEN_SHOTS;
			String wsEvidenceFolderPath = detailedReportsFolderPath + "/" + GenericConstants.CUSTOM_REPORTS_WS_EVIDENCE;
			String logosFolderPath = detailedReportsFolderPath + "/" + GenericConstants.CUSTOM_REPORTS_LOGOS;
			String summaryReportFilePath = dynamicResultsFolderPath + "/" + GenericConstants.CUSTOM_SUMMARY_REPORT_NAME;
			
			//Test Rail integration code
			boolean testRailFlag=false;
			if(propTable.get("Test_Rail_Integration_Enable_Flag")==null){
				testRailFlag=false;
			}else if(propTable.get("Test_Rail_Integration_Enable_Flag").equalsIgnoreCase("true")){
				testRailFlag=true;
			}
			if(testRailFlag){				
				JSONObject tr=new JSONObject();
				JSONArray tresults = new JSONArray();
				
				tr.put("TestResults", tresults);
				FileUtil.createFileWithContent(resultsFolderPath+"/"+GenericConstants.TEST_RAIL_SUITE_RESULTS_JSON, tr.toJSONString());				
				
			}

			// Create the folders
			FileUtil.createDirectoryWithPath(dynamicResultsFolderPath);
			FileUtil.createDirectoryWithPath(detailedReportsFolderPath);
			FileUtil.createDirectoryWithPath(screenshotsFolderPath);
			FileUtil.createDirectoryWithPath(wsEvidenceFolderPath);
			FileUtil.createDirectoryWithPath(logosFolderPath);

			// Copy logos to logos folder
			FileUtil.copyAllFilesInDirectory(new File(GenericConstants.GENERIC_FW_CONFIG_PROPERTIES
					.get(GenericConstants.CUSTOM_REPORTS_LOGOS_FOLDER_PATH)), new File(logosFolderPath));

			// Store all the folder and files information in CustomReportBean.
			customReportBean.setResultsFolderBasePath(dynamicResultsFolderPath);
			customReportBean.setDetailedReportsFolderPath(detailedReportsFolderPath);
			customReportBean.setScreenshotsFolderPath(screenshotsFolderPath);
			customReportBean.setWsEvidenceFolderPath(wsEvidenceFolderPath);
			customReportBean.setLogosFolderPath(logosFolderPath);
			customReportBean.setSummaryReportFilePath(summaryReportFilePath);

			// Store environment details.
			customReportBean.setSuiteStartDateAndTime(new Date());
			customReportBean.setHostName(System.getProperty("user.name"));
			customReportBean.setOsName(System.getProperty("os.name"));

			// Create the detailedReportMap, holds the information about each
			// test case.
			LinkedHashMap<String, DetailedReportBean> detailedReportMap = new LinkedHashMap<String, DetailedReportBean>();
			customReportBean.setDetailedReportMap(detailedReportMap);
			
			//Stores the path of latest results folder.
			String latestResultsFolderPath = resultsFolderPath+"Reports";
			
			//Deletes the latest folder if it is already present
			FileUtil.deleteFileOrFolder(latestResultsFolderPath);
			customReportBean.setLatestResultsFolderPath(latestResultsFolderPath);
		} catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new Exception("Unable to create the folder structure for custom reports");
		}
	}
	
	/**
	 * Generates summary report.
	 * 
	 * @throws Exception
	 */
	public void generateSummaryReport() throws Exception {
		try {
			executionEndTime = TimeUtil.getCurrentInstant();
			
			//Stores the suite execution time for summary report
			customReportBean.setSuiteExecutionTime(TimeUtil.getTimeDifference(executionStartTime, executionEndTime));
			
			summaryReport.generateSummaryReport(customReportBean);
			
			//Places the results in latest results folder.
			FileUtil.copyAllFilesInDirectory(new File(customReportBean.getResultsFolderBasePath()),
					new File(customReportBean.getLatestResultsFolderPath()));
		} catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new Exception("Unable to create the summary report");
		}
	}

	/**
	 * Removes the special characters and spaces from file name.
	 * 
	 * @param fileName
	 * @return java.lang.String : file name after removing the special characters and spaces.
	 */
	protected String getFormatedFileName(String fileName) {
		
		if(fileName!=null && fileName.trim().length()>0){
			fileName = fileName.trim();
			for(String specialChar : GenericConstants.CUSTOME_REPORT_SPECIAL_CHARS){
				try{
					fileName = fileName.replaceAll(specialChar, "");
					fileName = fileName.replaceAll(" ", "_");
				}catch(Exception e){}	
			}
		}
		
		return fileName;
	}
	
	/**
	 * Initialises the detailed report.
	 * 
	 * @param testCaseName
	 * @throws Exception
	 */
	public void intializeDetailedReport(String testCaseName) throws Exception {
		try {
			LinkedHashMap<String, DetailedReportBean> detailedReportMap = customReportBean.getDetailedReportMap();
			if (detailedReportMap != null) {
				DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);
				if (summaryReport != null && detailedReportBean != null) {

					if (detailedReport == null) {
						detailedReport = new DetailedReport();
					}
					
					String[] testCaseDetails = testCaseName.split(GenericConstants.CUSTOM_REPORT_REPORT_DETAILS_SPLIT);
					String testCaseID = getFormatedFileName(testCaseDetails[0]);
					String testCaseDescriptionForSummaryReport = testCaseDetails[1];
					
					String detailedReportFilePath = customReportBean.getDetailedReportsFolderPath() + "/" + testCaseID
							+ ".html";
					detailedReportBean.setDetailedReportFilePath(detailedReportFilePath);

					//Set the detailedReportRelativeFilePath for use of summary report
					String detailedReportRelativePath = detailedReportFilePath.substring(detailedReportFilePath.indexOf(GenericConstants.CUSTOM_REPORTS_DETAILED_REPORTS), 
							detailedReportFilePath.length());
					detailedReportBean.setDetailedReportRelativeFilePath(detailedReportRelativePath);		
					
					detailedReportBean.setTestCaseID(testCaseID);
					detailedReportBean.setTestCaseDescriptionForSummaryReport(testCaseDescriptionForSummaryReport);
					
					detailedReport.intializeDetailedReport(detailedReportFilePath, testCaseName);
					detailedReportMap.put(testCaseName, detailedReportBean);

					this.detailedReportMap = detailedReportMap;
				}
			}

		} catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new Exception("Unable to intialze the detailed report for " + testCaseName);
		}

	}

	/**
	 * Adds the fail test step to the detailed report.
	 * - This will not add the path for ObjectEvidene(i.e screenshot/webservice response file path) in detailed report.
	 * 
	 * @param stepName
	 *            - test case step details
	 * @param description
	 *            - test case description
	 * @param testCaseName : test case id and description  
	 */
	public void failureReport(String stepName, String description, String testCaseName) {

		try {
			DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);
			detailedReportBean.setOverallStatus(GenericConstants.TEST_CASE_FAIL);
			System.out.println("failureReport OverallStatus"+detailedReportBean.getOverallStatus());
			
			String detailedReportFilePath = detailedReportBean.getDetailedReportFilePath();
			int stepNumber = detailedReportBean.getCurrentStepNumber() + 1;

			detailedReport.updateDetailedReport(detailedReportFilePath, GenericConstants.TEST_CASE_FAIL, stepName,
					description, stepNumber, null);
			detailedReportBean.setCurrentStepNumber(stepNumber);
		} catch (IOException e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
	}
			
	/**
	 * Adds the warning test step to the detailed report.
	 * - This will not add the path for ObjectEvidene(i.e screenshot/webservice response file path)  in detailed report.
	 * 
	 * @param stepName
	 *            - test case step details
	 * @param description
	 *            - test case description
	 * @param testCaseName : test case id and description  
	 */
	public void warningReport(String stepName, String description, String testCaseName) {

		try {
			DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);
			//detailedReportBean.setOverallStatus(GenericConstants.TEST_CASE_PASS);
			
			String testCaseStatus = detailedReportBean.getOverallStatus();
			testCaseStatus = testCaseStatus == null ? "" : testCaseStatus;			
			if (!testCaseStatus.equalsIgnoreCase(GenericConstants.TEST_CASE_FAIL)) {
				detailedReportBean.setOverallStatus(GenericConstants.TEST_CASE_PASS);
			}			

			String detailedReportFilePath = detailedReportBean.getDetailedReportFilePath();
			int stepNumber = detailedReportBean.getCurrentStepNumber() + 1;

			detailedReport.updateDetailedReport(detailedReportFilePath, GenericConstants.TEST_CASE_WARNING, stepName,
					description, stepNumber, null);
			detailedReportBean.setCurrentStepNumber(stepNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	/**
	 * Adds the pass test step to the detailed report.
	 *    - This will not add the path for ObjectEvidene(i.e screenshot/webservice response file path)  in detailed report.
	 * 
	 * @param stepName
	 *            - test case step details
	 * @param description
	 *            - test case description
	 * @param testCaseName : test case id and description              
	 */
	public void successReport(String stepName, String description, String testCaseName) {
		try {
			DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);

			String testCaseStatus = detailedReportBean.getOverallStatus();
			testCaseStatus = testCaseStatus == null ? "" : testCaseStatus;

			if (!testCaseStatus.equalsIgnoreCase(GenericConstants.TEST_CASE_FAIL)) {
				detailedReportBean.setOverallStatus(GenericConstants.TEST_CASE_PASS);
			}
			System.out.println("successReport OverallStatus"+detailedReportBean.getOverallStatus());
			
			String detailedReportFilePath = detailedReportBean.getDetailedReportFilePath();
			int stepNumber = detailedReportBean.getCurrentStepNumber() + 1;

			detailedReport.updateDetailedReport(detailedReportFilePath, GenericConstants.TEST_CASE_PASS, stepName,
					description, stepNumber, null);
			detailedReportBean.setCurrentStepNumber(stepNumber);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error(e);
		}
	}

	/**
	 * Adds the fail test step to the detailed report(for web).
	 * - This will ADD the path for ObjectEvidene(i.e screenshot/webservice response file path)  in detailed report.#
	 * 
	 * @param stepName : test step details
	 * @param description : test step description.
	 * @param screenshotFile : Screen shot
	 * @param testCaseName : test case id and description
	 */
	public void successReportForWeb(String stepName, String description, File screenshotFile, String testCaseName) {
		try {
			DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);

			String testCaseStatus = detailedReportBean.getOverallStatus();
			testCaseStatus = testCaseStatus == null ? "" : testCaseStatus;

			if (!testCaseStatus.equalsIgnoreCase(GenericConstants.TEST_CASE_FAIL)) {
				detailedReportBean.setOverallStatus(GenericConstants.TEST_CASE_PASS);
			}

			String detailedReportFilePath = detailedReportBean.getDetailedReportFilePath();
			int stepNumber = detailedReportBean.getCurrentStepNumber() + 1;

			//For relative path of the screenshot, this path is used for adding to the detailed report
			String screenshotFileName = GenericConstants.CUSTOM_REPORTS_SCREEN_SHOTS + "/" + detailedReportBean.getTestCaseID() + "_"
					+ DateUtil.getDateFormatForResults() + ".png";
			
			//Copy's the screenshot to the screenshots folder based on relative path.
			String screenshotFileNameToCopy = customReportBean.getDetailedReportsFolderPath()+"/"+screenshotFileName;
			FileUtils.copyFile(screenshotFile, new File(screenshotFileNameToCopy));			
			
			detailedReport.updateDetailedReport(detailedReportFilePath, GenericConstants.TEST_CASE_PASS, stepName,
					description, stepNumber, screenshotFileName);
			detailedReportBean.setCurrentStepNumber(stepNumber);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error(e);
		}
	}
	
	/**
	 * Adds the fail test step to the detailed report(for web).
	 * - This will ADD the path for ObjectEvidene(i.e screenshot/webservice response file path)  in detailed report.
	 * 
	 * @param stepName : test step details
	 * @param description : test step description.
	 * @param screenshotFile : Screen shot
	 * @param testCaseName : test case id and description
	 */
	public void failureReportWeb(String stepName, String description, File screenshotFile,
			String testCaseName) {

		try {
			DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);
			detailedReportBean.setOverallStatus(GenericConstants.TEST_CASE_FAIL);

			String detailedReportFilePath = detailedReportBean.getDetailedReportFilePath();
			int stepNumber = detailedReportBean.getCurrentStepNumber() + 1;

			//For relative path of the screenshot, this path is used for adding to the detailed report
			String screenshotFileName = GenericConstants.CUSTOM_REPORTS_SCREEN_SHOTS + "/" + detailedReportBean.getTestCaseID() + "_"
					+ DateUtil.getDateFormatForResults() + ".png";
			
			//Copy's the screenshot to the screenshots folder based on relative path.
			String screenshotFileNameToCopy = customReportBean.getDetailedReportsFolderPath()+"/"+screenshotFileName;
			FileUtils.copyFile(screenshotFile, new File(screenshotFileNameToCopy));

			detailedReport.updateDetailedReport(detailedReportFilePath, GenericConstants.TEST_CASE_FAIL, stepName,
					description, stepNumber, screenshotFileName);
			detailedReportBean.setCurrentStepNumber(stepNumber);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error(e);
		}
	}
	
	/**
	 * Updated the detailed report with pass test step details(for webservices).
	 * - This will ADD the path for ObjectEvidene(i.e. webservice response as .txt file path)  in detailed report.
	 * 
	 * - use successReportForJsonWebService() or successReportForXmlWebService(), since this method is deprecated.
	 * 
	 * @param stepName : test step details
	 * @param description : test step description.
	 * @param wsFileContent : wsResponse String
	 * @param testCaseName : test case id and description
	 */
	@Deprecated
	public void successReportForWebService(String stepName, String description, String wsFileContent, String testCaseName) {
		LOG.warn("Use successReportForJsonWebService() or successReportForXmlWebService(), since this method is deprecated.");
		successReportForWebService(stepName, description, wsFileContent, testCaseName, ".txt");
	}

	/**
	 * Updated the detailed report with pass test step details(for webservices as .json).
	 * - This will ADD the path for ObjectEvidene(i.e. webservice response as .json file path)  in detailed report.
	 * 
	 * @param stepName : test step details
	 * @param description : test step description.
	 * @param wsFileContent : wsResponse String
	 * @param testCaseName : test case id and description
	 */
	public void successReportForJsonWebService(String stepName, String description, String wsFileContent, String testCaseName) {
		successReportForWebService(stepName, description, wsFileContent, testCaseName, ".json");
	}

	/**
	 * Updated the detailed report with pass test step details(for webservices as .xml).
	 * - This will ADD the path for ObjectEvidene(i.e. webservice response as .xml file path)  in detailed report.
	 * 
	 * @param stepName : test step details
	 * @param description : test step description.
	 * @param wsFileContent : wsResponse String
	 * @param testCaseName : test case id and description
	 */
	
	public void successReportForXmlWebService(String stepName, String description, String wsFileContent, String testCaseName) {
		successReportForWebService(stepName, description, wsFileContent, testCaseName, ".xml");
	}
	
	/**
	 * Generic method for updating the detailed report with pass test step details(for webservices based on extension).
	 * - This will ADD the path for ObjectEvidene(i.e. webservice response file path)  in detailed report.
	 * 
	 * @param stepName : test step details
	 * @param description : test step description.
	 * @param wsFileContent : wsResponse String
	 * @param testCaseName : test case id and description
	 * @param extension : webservice response extension(i.e. .json/.xml)
	 */
	private void successReportForWebService(String stepName, String description, String wsFileContent, String testCaseName, String extension) {
		try {
			DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);

			String testCaseStatus = detailedReportBean.getOverallStatus();
			testCaseStatus = testCaseStatus == null ? "" : testCaseStatus;

			if (!testCaseStatus.equalsIgnoreCase(GenericConstants.TEST_CASE_FAIL)) {
				detailedReportBean.setOverallStatus(GenericConstants.TEST_CASE_PASS);
			}

			String detailedReportFilePath = detailedReportBean.getDetailedReportFilePath();
			int stepNumber = detailedReportBean.getCurrentStepNumber() + 1;

			String wsFileName = GenericConstants.CUSTOM_REPORTS_WS_EVIDENCE + "/" + detailedReportBean.getTestCaseID() + "_"
					+ DateUtil.getDateFormatForResults() + extension;
			
			//Copy's the wsFile to the WsEvidence folder based on relative path.
			String wsFileNamePath = customReportBean.getDetailedReportsFolderPath()+"/"+wsFileName;
			FileUtil.createFileWithContent(wsFileNamePath, wsFileContent);
			
			detailedReport.updateDetailedReport(detailedReportFilePath, GenericConstants.TEST_CASE_PASS, stepName,
					description, stepNumber, wsFileName);
			detailedReportBean.setCurrentStepNumber(stepNumber);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error(e);
		}
	}	
	
	/**
	 * Updated the detailed report with fail test step details(for webservices).
	 * - This will ADD the path for ObjectEvidene(i.e screenshot/webservice response file path)  in detailed report.
	 * 
	 * - use failureReportForJsonWebService() or failureReportForXmlWebService(), since this method is deprecated.
	 * 
	 * @param stepName
	 *            : test step details
	 * @param description
	 *            : test step description.
	 * @param wsFileContent
	 *            : wsResponse String
	 * @param testCaseName : test case id and description           
	 * @throws IOException
	 */
	@Deprecated
	public void failureReportWebService(String stepName, String description, String wsFileContent, String testCaseName)
			throws IOException {
		LOG.warn("Use failureReportForJsonWebService() or failureReportForXmlWebService(), since this method is deprecated.");
		failureReportWebService(stepName, description, wsFileContent, testCaseName, ".txt");

	}

	/**
	 * Updated the detailed report with fail test step details(for webservices as json).
	 * - This will ADD the path for ObjectEvidene(i.e webservice response as json file path)  in detailed report.
	 * 
	 * @param stepName
	 *            : test step details
	 * @param description
	 *            : test step description.
	 * @param wsFileContent
	 *            : wsResponse String
	 * @param testCaseName : test case id and description           
	 * @throws IOException
	 */
	public void failureReportForJsonWebService(String stepName, String description, String wsFileContent, String testCaseName)
			throws IOException {
		failureReportWebService(stepName, description, wsFileContent, testCaseName, ".json");

	}

	/**
	 * Updated the detailed report with fail test step details(for webservices as xml).
	 * - This will ADD the path for ObjectEvidene(i.e webservice response as xml file path)  in detailed report.
	 * 
	 * @param stepName
	 *            : test step details
	 * @param description
	 *            : test step description.
	 * @param wsFileContent
	 *            : wsResponse String
	 * @param testCaseName : test case id and description           
	 * @throws IOException
	 */
	public void failureReportForXmlWebService(String stepName, String description, String wsFileContent, String testCaseName)
			throws IOException {
		failureReportWebService(stepName, description, wsFileContent, testCaseName, ".xml");
	}

	/**
	 * Generic method for updating the detailed report with fail test step details(for webservices based on extension).
	 * - This will ADD the path for ObjectEvidene(i.e. webservice response file path)  in detailed report.
	 * 
	 * @param stepName
	 *            : test step details
	 * @param description
	 *            : test step description.
	 * @param wsFileContent
	 *            : wsResponse String
	 * @param testCaseName : test case id and description       
	 * @param extension : webservice response extension(i.e. .json/.xml)
	 * @throws IOException
	 */
	private void failureReportWebService(String stepName, String description, String wsFileContent, String testCaseName, String extension)
			throws IOException {
		try {
			DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);
			detailedReportBean.setOverallStatus(GenericConstants.TEST_CASE_FAIL);

			String detailedReportFilePath = detailedReportBean.getDetailedReportFilePath();
			int stepNumber = detailedReportBean.getCurrentStepNumber() + 1;

			String wsFileName = GenericConstants.CUSTOM_REPORTS_WS_EVIDENCE + "/" + detailedReportBean.getTestCaseID() + "_"
					+ DateUtil.getDateFormatForResults() + extension;
			
			//Copy's the wsFile to the WsEvidence folder based on relative path.
			String wsFileNamePath = customReportBean.getDetailedReportsFolderPath()+"/"+wsFileName;
			
			FileUtil.createFileWithContent(wsFileNamePath, wsFileContent);
			
			detailedReport.updateWsDetailedReport(detailedReportFilePath, GenericConstants.TEST_CASE_FAIL, stepName,
					description, stepNumber, wsFileName);
			detailedReportBean.setCurrentStepNumber(stepNumber);
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error(e);
		}

	}	 
	
}
