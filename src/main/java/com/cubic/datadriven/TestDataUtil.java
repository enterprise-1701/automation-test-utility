package com.cubic.datadriven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.cubic.genericutils.FileUtil;
import com.cubic.genericutils.GenericConstants;
import com.cubic.genericutils.JsonUtil;

import net.minidev.json.JSONArray;

public class TestDataUtil {
	private static final Logger LOG = Logger.getLogger(TestDataUtil.class.getName());

	/**
	 * Fetches the test data from excel file
	 * 
	 * @param testCase name of respective testcase
	 * @param filePath path of TestData File
	 * @param sheetName name of respective Sheet in .xlsx file
	 * @return Object[][] holds the testdata of respective testcase
	 */
	public synchronized static Object[][] getData(String testCase, String filePath, String sheetName) {
		Xls_Reader xls = new Xls_Reader(filePath);

		int testCaseStartRowNum = 0;
		// iterate through all rows from the sheet Test Data
		for (int rNum = 1; rNum <= xls.getRowCount(sheetName); rNum++) {
			// to identify testCase starting row number
			if (testCase.equals(xls.getCellData(sheetName, 0, rNum))) {
				testCaseStartRowNum = rNum;
				break;
			}
		}

		// total cols
		int colStartRowNum = testCaseStartRowNum + 1;
		int cols = 0;
		// Get the total number of columns for which test data is present
		while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
			cols++;
		}

		// rows
		int rowStartRowNum = testCaseStartRowNum + 2;
		int rows = 0;

		// Get the total number of rows for which test data is present
		while (!xls.getCellData(sheetName, 0, (rowStartRowNum + rows)).equals("")) {
			rows++;
		}

		Object[][] data = new Object[rows][1];
		Hashtable<String, String> table = null;
		// print the test data
		for (int rNum = rowStartRowNum; rNum < (rows + rowStartRowNum); rNum++) {
			table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < cols; cNum++) {
				table.put(xls.getCellData(sheetName, cNum, colStartRowNum), xls.getCellData(sheetName, cNum, rNum));

			}
			data[rNum - rowStartRowNum][0] = table;
		}
		return data;
	}
	


	/**
	 * Fetches the test data from the JSON file based on the parentElement
	 * 
	 * @param testDatafilePath jsonFilePath
	 * @param parentElement parentElement name in the Json
	 * @return Object[][] returns the test data
	 * @throws Throwable Handle Exception
	 */
	public static Object[][] getTestDataFromJson(String testDatafilePath, String parentElement) throws Throwable {

		return getTestDataFromJson(FileUtil.readFile(testDatafilePath),  "$."+parentElement, true);
	}	
	
	/**
	 * Fetches the test data from the JSON file based on the JsonPathExpression
	 * 
	 * @param testDatafilePath jsonFilePath
	 * @param jsonPathExpression JSON Path Expression
	 * @param trimTestData trimTestData=true trims the test data
	 *                     trimTestData=false does not trim the test data  
	 * @return Object[][] returns the test data
	 * @throws Throwable Handle Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized static Object[][] getTestDataFromJson(String testDatafilePath, String jsonPathExpression,
			boolean trimTestData) throws Throwable {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());

		Object[][] object = null;
		try {
			ArrayList<Hashtable<String, String>> dataList = null;

			JSONArray jsonArray = JsonUtil.getJsonArray(testDatafilePath, jsonPathExpression);
			if (jsonArray.size() > 0) {
				dataList = new ArrayList<Hashtable<String, String>>();
			} else {
				LOG.error("test data is not in proper format OR no test data to parse");
				throw new Exception("test data is not in proper format OR no test data to parse");
			}

			for (Object jsonObject : jsonArray) {
				HashMap<String, String> hm = ((HashMap) jsonObject);

				String runMode = hm.get(GenericConstants.RUN_MODE);
				runMode = runMode != null ? runMode.trim() : runMode;

				if (GenericConstants.RUN_MODE_YES.equalsIgnoreCase(runMode)) {
					if (trimTestData) {
						for (String key : hm.keySet()) {
							if (hm.get(key) != null) {
								hm.put(key, hm.get(key).trim());
							}
						}
					}
					dataList.add((new Hashtable<String, String>(hm)));
				}
			}

			int index = 0;
			object = new Object[dataList.size()][1];
			for (Hashtable<String, String> ht : dataList) {
				object[index][0] = ht;
				index++;
			}
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		}

		return object;
	}

	/**
	 * Gets the respective caller class name
	 * @return Gives the Respective ClassName of type (String)
	 */
	private static String getCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		return stElements[3].getClassName();
	}

	/**
	 * Gets the respective caller method name
	 * @return Gives the respective method name of type (String)
	 */
	private static String getCallerMethodName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		return stElements[3].getMethodName();
	}	
}