package com.cubic.reportengine.bean;

import java.time.Instant;

public class DetailedReportBean {

	private int currentStepNumber;
	private String overallStatus;
	private String detailedReportFilePath;
	private String testCaseName;
	private Instant testCaseStartTime;
	private Instant testCaseEndTime;
	private String totalTimeForTestCase;
	private String detailedReportRelativeFilePath;
	private String testCaseID;
	private String testCaseDescriptionForSummaryReport;
	private String failStepDescription;
	
	public int getCurrentStepNumber() {
		return currentStepNumber;
	}

	public void setCurrentStepNumber(int currentStepNumber) {
		this.currentStepNumber = currentStepNumber;
	}

	public String getOverallStatus() {
		return overallStatus;
	}

	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	public String getDetailedReportFilePath() {
		return detailedReportFilePath;
	}

	public void setDetailedReportFilePath(String detailedReportFilePath) {
		this.detailedReportFilePath = detailedReportFilePath;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public Instant getTestCaseStartTime() {
		return testCaseStartTime;
	}

	public void setTestCaseStartTime(Instant testCaseStartTime) {
		this.testCaseStartTime = testCaseStartTime;
	}

	public Instant getTestCaseEndTime() {
		return testCaseEndTime;
	}

	public void setTestCaseEndTime(Instant testCaseEndTime) {
		this.testCaseEndTime = testCaseEndTime;
	}

	public String getTotalTimeForTestCase() {
		return totalTimeForTestCase;
	}

	public void setTotalTimeForTestCase(String totalTimeForTestCase) {
		this.totalTimeForTestCase = totalTimeForTestCase;
	}

	public String getDetailedReportRelativeFilePath() {
		return detailedReportRelativeFilePath;
	}

	public void setDetailedReportRelativeFilePath(String detailedReportRelativeFilePath) {
		this.detailedReportRelativeFilePath = detailedReportRelativeFilePath;
	}

	public String getTestCaseID() {
		return testCaseID;
	}

	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}

	public String getTestCaseDescriptionForSummaryReport() {
		return testCaseDescriptionForSummaryReport;
	}

	public void setTestCaseDescriptionForSummaryReport(String testCaseDescriptionForSummaryReport) {
		this.testCaseDescriptionForSummaryReport = testCaseDescriptionForSummaryReport;
	}

	public String getFailStepDescription() {
		return failStepDescription;
	}

	public void setFailStepDescription(String failStepDescription) {
		this.failStepDescription = failStepDescription;
	}

}
