package com.cubic.reportengine.report;

import java.io.IOException;

import com.cubic.genericutils.FileUtil;
import com.cubic.genericutils.GenericConstants;


public class DetailedReport {
	
	/**
	 * Initialises the detailed report
	 * 
	 * @param detailedReportFilePath
	 * @throws Exception
	 */
	void intializeDetailedReport(String detailedReportFilePath, String testCaseName) throws Exception {
		try {
			String detailedReport = ReportTemplates.DETAILED_REPORT;
			detailedReport = detailedReport.replace("<!--TestCaseName-->", testCaseName);
			FileUtil.createFileWithContent(detailedReportFilePath, detailedReport); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to intialize the detailed report");
		}
	}	
	
	/**
	 * Updated the detailed report with test step details.
	 * 
	 * @param detailedReportFilePath - detailed Report File Path
	 * @param status - adds detailed report with test status pass/fail
	 * @param stepName - test step details 
	 * @param description - test step description.
	 * @param stepNo - test step number
	 * @param screenshotFilePath - screenshot File Path
	 * @throws IOException
	 */
	void updateDetailedReport(String detailedReportFilePath, String status, String stepName, String description, int stepNo, String screenshotFilePath) throws IOException{
		
		try{
			String detailedReportRow = null;
			
			if(GenericConstants.TEST_CASE_PASS.equalsIgnoreCase(status) || GenericConstants.TEST_CASE_WARNING.equalsIgnoreCase(status)){
				detailedReportRow = ReportTemplates.DETAILED_REPORT_PASS;
				
				//If screenshot is present
				if(screenshotFilePath!=null){ 
					String tdRowWithScreenShot = ReportTemplates.DETAILED_REPORT_PASS_WITH_SCREENSHOT;
					detailedReportRow = detailedReportRow.replace("<!--Status TD-->", tdRowWithScreenShot);
					detailedReportRow = detailedReportRow.replace("<!--strErrorPath-->", screenshotFilePath);
					
				//If screenshot is not present	
				}else{
					String tdRowWithOutScreenshott = ReportTemplates.DETAILED_REPORT_PASS_WITHOUT_SCREENSHOT;
					detailedReportRow = detailedReportRow.replace("<!--Status TD-->", tdRowWithOutScreenshott);
				}				
				
			//For detailed report failed step	
			}else if(status.equalsIgnoreCase(GenericConstants.TEST_CASE_FAIL)){
				detailedReportRow = ReportTemplates.DETAILED_REPORT_FAIL;
				
				//If screenshot is present
				if(screenshotFilePath!=null){ 
					String tdRowWithScreenShot = ReportTemplates.DETAILED_REPORT_FAIL_WITH_SCREENSHOT;
					detailedReportRow = detailedReportRow.replace("<!--Status TD-->", tdRowWithScreenShot);
					detailedReportRow = detailedReportRow.replace("<!--strErrorPath-->", screenshotFilePath);
					
				//If screenshot is not present	
				}else{
					String tdRowWithOutScreenshott = ReportTemplates.DETAILED_REPORT_FAIL_WITHOUT_SCREENSHOT;
					detailedReportRow = detailedReportRow.replace("<!--Status TD-->", tdRowWithOutScreenshott);
				}
			}
			
			detailedReportRow = detailedReportRow.replace("<!--Step No-->", ""+stepNo);
			detailedReportRow = detailedReportRow.replace("<!--Step Name-->", stepName);
			detailedReportRow = detailedReportRow.replace("<!--Description-->", description);

			
			FileUtil.appendToFile(detailedReportFilePath, detailedReportRow);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	} 
		
	/**
	 * Updated the detailed report with test step details.
	 * 
	 * @param detailedReportFilePath - detailed Report File Path
	 * @param status - adds detailed report with test status pass/fail
	 * @param stepName - test step details 
	 * @param description - test step description.
	 * @param stepNo - test step number
	 * @param wsFilePath - ws File Path
	 * @throws IOException
	 */
	void updateWsDetailedReport(String detailedReportFilePath, String status, String stepName, String description, int stepNo, String wsFilePath) throws IOException{
		
		try{
			String detailedReportRow = null; 
			
			//TODO need move 'pass' to constants
			if(status.equalsIgnoreCase(GenericConstants.TEST_CASE_PASS)){
				detailedReportRow = ReportTemplates.DETAILED_REPORT_PASS;
			}else{
				detailedReportRow = ReportTemplates.DETAILED_REPORT_FAIL;
				
				//If wsFilePath is present 
				if(wsFilePath!=null){ 
					String tdRowWithWsFilePath = ReportTemplates.DETAILED_REPORT_FAIL_WITH_SCREENSHOT;
					detailedReportRow = detailedReportRow.replace("<!--Status TD-->", tdRowWithWsFilePath);
					detailedReportRow = detailedReportRow.replace("<!--strErrorPath-->", wsFilePath);
					
				//If wsFilePath is not present	
				}else{
					String tdRowWithOutScreenshott = ReportTemplates.DETAILED_REPORT_FAIL_WITHOUT_SCREENSHOT;
					detailedReportRow = detailedReportRow.replace("<!--Status TD-->", tdRowWithOutScreenshott);
				}				
			}
			
			detailedReportRow = detailedReportRow.replace("<!--Step No-->", ""+stepNo);
			detailedReportRow = detailedReportRow.replace("<!--Step Name-->", stepName);
			detailedReportRow = detailedReportRow.replace("<!--Description-->", description);
			
			
			if(wsFilePath!=null){ 
				detailedReportRow = detailedReportRow.replace("<!--strErrorPath-->", wsFilePath);
			}
			
			FileUtil.appendToFile(detailedReportFilePath, detailedReportRow);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
}
