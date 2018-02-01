package com.cubic.genericutils;

import java.util.Hashtable;

/**
 * All Constants related to generic utils are strored.
 * 
 * @since 1.0
 */
public interface GenericConstants {

	// Below are the constants for reading the data from
	// GenericFrameworkConfig.properties
	String PROPERTIES_FILE_LOCATION = System.getProperty("user.dir")
			+ "/generic_framework_resources/GenericFrameworkConfig.properties";
	Hashtable<String, String> GENERIC_FW_CONFIG_PROPERTIES = PropertiesUtil
			.getPropertysAsHashtable(PROPERTIES_FILE_LOCATION);
		
	// Below are the constant for report
	String CUSTOM_SUMMARY_REPORT_NAME = "SummaryReport.html";
	String CUSTOM_REPORTS_LOGOS_FOLDER_PATH = "customreports_logos_folder_path";
	String CUSTOM_REPORTS_DETAILED_REPORTS = "DetailedReports";
	String CUSTOM_REPORTS_SCREEN_SHOTS = "Screenshots";
	String CUSTOM_REPORTS_WS_EVIDENCE = "WsEvidence";
	String CUSTOM_REPORTS_LOGOS = "logos";
	String[] CUSTOME_REPORT_SPECIAL_CHARS = new String[]{"\\", "/", ":", "*", "?", ">", "<", "|", "\""};
	String CUSTOM_REPORT_REPORT_DETAILS_SPLIT = ":"; 
	
	//customreports_results_folder_path
	//String CUSTOM_REPORTS_RESULTS = "Results";
	String CUSTOM_REPORTS_RESULTS = (GENERIC_FW_CONFIG_PROPERTIES.get("customreports_results_folder_path") == null) ?  
									 System.getProperty("user.dir")+"//Results//" : 
									 System.getProperty("user.dir")+"//"+GENERIC_FW_CONFIG_PROPERTIES.get("customreports_results_folder_path")+"//";
	
	String TEST_CASE_PASS = "PASS";
	String TEST_CASE_FAIL = "FAIL";
	String TEST_CASE_WARNING = "WARNING";

	// Below are the constants for database
	String MYSQL = "mysql";
	String MSACCESS = "msaccess";
	String ORACLE = "oracle";
	String SQLDEVELOPER = "sqldeveloper";
	String SQLSERVER = "sqlserver";
	String POSTGRESQL = "postgresql";
	
	//Below are the constants for Log4j
	String LOG4J_FILEPATH = GENERIC_FW_CONFIG_PROPERTIES.get("log4j_filepath");
	String LOG4J_OUTPUT_ROOTFOLDER_FILEPATH = GENERIC_FW_CONFIG_PROPERTIES.get("log4j_output_rootfolder_filepath");
	String LOG4J_OUTPUT_FOLDER_HTMLLOGS = LOG4J_OUTPUT_ROOTFOLDER_FILEPATH + "/htmllogs";
	String LOG4J_OUTPUT_FOLDER_LOGS = LOG4J_OUTPUT_ROOTFOLDER_FILEPATH + "/logs";
	
	//Below are the constants for data provider
	String RUN_MODE = "RunMode";
	String RUN_MODE_YES = "Y";
	
	//Below are the constants used for TestRail Integreation
	String TEST_RAIL_SUITE_RESULTS_JSON = "testResults.json";
	String TEST_RAIL_CASE_RUN_ID_MAP_JSON = "testRunMap.json";
	String TEST_RAIL_TEST_RUN_TEMPLATE_JSON = "testRun.json";
	String TEST_CASES_TO_BE_EXECUTED_JSON = "testCase.json";
	String TEST_CLASSES_TO_BE_EXECUTED_JSON = "testClasses.json";
	String TEST_CASES_TO_BE_EXECUTED_JSON_FILE_PATH = System.getProperty("user.dir")+"//TestData//";
	
    // ##### TIMEZONES
    String TIMEZONE_UTC = "GMT";
    
    // ##### DATE FORMATS
    String DATE_FORMAT_ZULU = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    String REGEX_ZULU_DTM = "\\d{4}-\\d{2}-\\d{2}T\\d{2}\\:\\d{2}\\:\\d{2}Z";
    
    // ##### GENERIC VALUES
    long SECS_IN_A_MIN = 60;
    long MINS_IN_A_HR = 60;
    long HRS_IN_A_DAY = 24;
    long SECS_IN_A_HR = SECS_IN_A_MIN * MINS_IN_A_HR;
    long SECS_IN_A_DAY = HRS_IN_A_DAY * SECS_IN_A_HR;	
}
