package com.cubic.reportengine.bean;

import java.util.Date;
import java.util.LinkedHashMap;

public class CustomReportBean {

	private String hostName;
	private String osName;
	private int totalTestScriptsPassed;
	private int totalTestScriptsFailed;
	private Date suiteStartDateAndTime;
	private long overallExecutionTimeInMillis;
	private String browserName;
	private String resultsFolderBasePath;
	private String latestResultsFolderPath;
	private String detailedReportsFolderPath;
	private String screenshotsFolderPath;
	private String wsEvidenceFolderPath;
	private String logosFolderPath;
	private String summaryReportFilePath;
	private String suiteExecutionTime;

	private LinkedHashMap<String, DetailedReportBean> detailedReportMap;

	public int getTotalTestScriptsPassed() {
		return totalTestScriptsPassed;
	}

	public void setTotalTestScriptsPassed(int totalTestScriptsPassed) {
		this.totalTestScriptsPassed = totalTestScriptsPassed;
	}

	public int getTotalTestScriptsFailed() {
		return totalTestScriptsFailed;
	}

	public void setTotalTestScriptsFailed(int totalTestScriptsFailed) {
		this.totalTestScriptsFailed = totalTestScriptsFailed;
	}

	public Date getSuiteStartDateAndTime() {
		return suiteStartDateAndTime;
	}

	public void setSuiteStartDateAndTime(Date suiteStartDateAndTime) {
		this.suiteStartDateAndTime = suiteStartDateAndTime;
	}

	public long getOverallExecutionTimeInMillis() {
		return overallExecutionTimeInMillis;
	}

	public void setOverallExecutionTimeInMillis(long overallExecutionTimeInMillis) {
		this.overallExecutionTimeInMillis = overallExecutionTimeInMillis;
	}

	public LinkedHashMap<String, DetailedReportBean> getDetailedReportMap() {
		return detailedReportMap;
	}

	public void setDetailedReportMap(LinkedHashMap<String, DetailedReportBean> detailedReportMap) {
		this.detailedReportMap = detailedReportMap;
	}

	public String getResultsFolderBasePath() {
		return resultsFolderBasePath;
	}

	public void setResultsFolderBasePath(String resultsFolderBasePath) {
		this.resultsFolderBasePath = resultsFolderBasePath;
	}

	public String getDetailedReportsFolderPath() {
		return detailedReportsFolderPath;
	}

	public void setDetailedReportsFolderPath(String detailedReportsFolderPath) {
		this.detailedReportsFolderPath = detailedReportsFolderPath;
	}

	public String getScreenshotsFolderPath() {
		return screenshotsFolderPath;
	}

	public void setScreenshotsFolderPath(String screenshotsFolderPath) {
		this.screenshotsFolderPath = screenshotsFolderPath;
	}

	public String getWsEvidenceFolderPath() {
		return wsEvidenceFolderPath;
	}

	public void setWsEvidenceFolderPath(String wsEvidenceFolderPath) {
		this.wsEvidenceFolderPath = wsEvidenceFolderPath;
	}

	public String getLogosFolderPath() {
		return logosFolderPath;
	}

	public void setLogosFolderPath(String logosFolderPath) {
		this.logosFolderPath = logosFolderPath;
	}

	public String getSummaryReportFilePath() {
		return summaryReportFilePath;
	}

	public void setSummaryReportFilePath(String summaryReportFilePath) {
		this.summaryReportFilePath = summaryReportFilePath;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getLatestResultsFolderPath() {
		return latestResultsFolderPath;
	}

	public void setLatestResultsFolderPath(String latestResultsFolderPath) {
		this.latestResultsFolderPath = latestResultsFolderPath;
	}

	public String getSuiteExecutionTime() {
		return suiteExecutionTime;
	}

	public void setSuiteExecutionTime(String suiteExecutionTime) {
		this.suiteExecutionTime = suiteExecutionTime;
	}
	
}
