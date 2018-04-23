package com.cubic.reportengine.report;

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser;

import com.cubic.genericutils.FileUtil;
import com.cubic.genericutils.GenericConstants;
import com.cubic.genericutils.TimeUtil;
import com.cubic.reportengine.bean.CustomReportBean;
import com.cubic.reportengine.bean.DetailedReportBean;

public class SummaryReport {

	private Hashtable<String , String> propTable = GenericConstants.GENERIC_FW_CONFIG_PROPERTIES;
	/**
	 * Generates the Summary Report.
	 * 
	 * @param customReportBean
	 * @throws Exception
	 */
	void generateSummaryReport(CustomReportBean customReportBean,boolean testRailFlag) throws Exception {
		try {
			String summaryReportTemplate = ReportTemplates.SUMMARY_REPORT;

			summaryReportTemplate = summaryReportTemplate.replaceAll("<!--Total No Of Test Scripts-->",
					"" + (customReportBean.getTotalTestScriptsPassed() + customReportBean.getTotalTestScriptsFailed()));
			summaryReportTemplate = summaryReportTemplate.replaceAll("<!--Total No Of Passed Scripts-->",
					"" + customReportBean.getTotalTestScriptsPassed());
			summaryReportTemplate = summaryReportTemplate.replaceAll("<!--Total No Of Failed Scripts-->",
					"" + customReportBean.getTotalTestScriptsFailed());
			/*summaryReportTemplate = summaryReportTemplate.replace("<!--Overall Execution Time-->",
					"" + TimeUtil.convertMilliSecondsToTimeFormat(customReportBean.getOverallExecutionTimeInMillis()));
*/
			summaryReportTemplate = summaryReportTemplate.replace("<!--Overall Execution Time-->",
						customReportBean.getSuiteExecutionTime());
			
			summaryReportTemplate = summaryReportTemplate.replace("<!--Date & Time-->",
					"" + customReportBean.getSuiteStartDateAndTime());
			summaryReportTemplate = summaryReportTemplate.replace("<!--Host Name-->", customReportBean.getHostName());
			summaryReportTemplate = summaryReportTemplate.replace("<!--OS_Name-->", customReportBean.getOsName());

			String browserName = customReportBean.getBrowserName();
			browserName = browserName!=null ? browserName : "";
			summaryReportTemplate = summaryReportTemplate.replace("<!--Browser Name-->",
					browserName);			

			String summaryResultsFilePath = customReportBean.getSummaryReportFilePath();
			FileUtil.createFileWithContent(summaryResultsFilePath, summaryReportTemplate);

			LinkedHashMap<String, DetailedReportBean> detailedReportMap = customReportBean.getDetailedReportMap();
			int sNo = 1;
			JSONObject testRailObj = new JSONObject();
			JSONArray testRailResultArray = new JSONArray();
			for (String testCaseName : detailedReportMap.keySet()) { 
				updateSummaryReport(sNo, testCaseName, detailedReportMap, summaryResultsFilePath); 
				JSONObject testResultObj = new JSONObject();
				testResultObj.put("TestStatus", returnTestCaseStatus(testCaseName, detailedReportMap));
				testResultObj.put("TestCaseID", returnTestCaseID(testCaseName, detailedReportMap));
				testResultObj.put("TestComment", returnTestCaseComment(testCaseName, detailedReportMap));
				testRailResultArray.add(testResultObj);
				sNo = sNo + 1;
			}
			if(testRailFlag){
			testRailObj.put("TestResults", testRailResultArray);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(GenericConstants.CUSTOM_REPORTS_RESULTS+"/"+GenericConstants.TEST_RAIL_SUITE_RESULTS_JSON));
			JSONObject testRun = (JSONObject) obj;
			testRailObj.put("TestRunID", testRun.get("TestRunID"));
			FileUtil.createFileWithContent(GenericConstants.CUSTOM_REPORTS_RESULTS+"/"+GenericConstants.TEST_RAIL_SUITE_RESULTS_JSON, testRailObj.toJSONString());			
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to create the summary report.");
		}
	}

	/**
	 * Add the test case details to the summary report.
	 * 
	 * @param sNo : Writes the serial number to summary report.
	 * @param testCaseName : use test case name to fetch test case details
	 * @param detailedReportMap : holds information related to test case
	 * @param summaryResultsFilePath : summary report file path
	 * @throws IOException
	 */
	private void updateSummaryReport(int sNo, String testCaseName, 
			LinkedHashMap<String, DetailedReportBean> detailedReportMap, String summaryResultsFilePath) throws IOException {
		try {
			DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);
			String time = detailedReportBean.getTotalTimeForTestCase();
			String testCaseStatus = detailedReportBean.getOverallStatus();
			String detailedReportFilePath = detailedReportBean.getDetailedReportRelativeFilePath();
			String testCaseId = detailedReportBean.getTestCaseID();
			String testCaseDescription = detailedReportBean.getTestCaseDescriptionForSummaryReport();
			
			String summaryReportRow = null;
			if (GenericConstants.TEST_CASE_PASS.equalsIgnoreCase(testCaseStatus)) {
				summaryReportRow = ReportTemplates.SUMMARY_REPORT_PASS;
			} else {
				summaryReportRow = ReportTemplates.SUMMARY_REPORT_FAIL;
			}

			summaryReportRow = summaryReportRow.replace("<!--Sno-->", "" + sNo);
			summaryReportRow = summaryReportRow.replace("<!--TestCase ID-->", testCaseId);
			summaryReportRow = summaryReportRow.replace("<!--TestScriptDescription-->", testCaseDescription);
			summaryReportRow = summaryReportRow.replace("<!--Time-->", time);
			summaryReportRow = summaryReportRow.replace("<!--ResultsDetailedPath-->", detailedReportFilePath);

			FileUtil.appendToFile(summaryResultsFilePath, summaryReportRow);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * Methods implemented for testrail
	 */
	private String returnTestCaseID(String testCaseName, LinkedHashMap<String, DetailedReportBean> detailedReportMap) {
		DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);
		return detailedReportBean.getTestCaseID();
	}

	private String returnTestCaseStatus(String testCaseName,
			LinkedHashMap<String, DetailedReportBean> detailedReportMap) {
		DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);
		return detailedReportBean.getOverallStatus();
	}
	
	private String returnTestCaseComment(String testCaseName,
			LinkedHashMap<String, DetailedReportBean> detailedReportMap) {
		DetailedReportBean detailedReportBean = detailedReportMap.get(testCaseName);
		if(detailedReportBean.getFailStepDescription()==null)
			return "";
		else
			return detailedReportBean.getFailStepDescription();
	}

	
}


