package com.cubic.genericutils;

import org.apache.log4j.Logger;

import com.cubic.logutils.Log4jUtil;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minidev.json.JSONArray;

public class JsonUtil {
	private static final Logger LOG = Logger.getLogger(JsonUtil.class.getName());
	
	/**
	 * Returns value from jsonData based on jsonPathExpression
	 * 
	 * @param jsonData input jsonData as java.lang.String
	 * @param jsonPathExpression jsonPathExpression as java.lang.String 
	 * @return actualVal returns string value from jsonData based on jsonPathExpression
	 * @throws Throwable java.lang.Throwable
	 */
	public synchronized static String getJsonElement(String jsonData, String jsonPathExpression) throws Throwable {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());

		JSONArray jsonArray = null;
		String actualVal = null;
		try {
			Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonData);

			Object object = JsonPath.read(document, jsonPathExpression);

			if (object instanceof JSONArray) {
				jsonArray = (JSONArray) object;
				actualVal = jsonArray.get(0).toString();
			} else {
				actualVal = object.toString();
			}

		} 
		catch (NullPointerException e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw e;
		}
		catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
		return actualVal;
	}
	
	public synchronized static Object getJsonObject(String jsonData, String jsonPathExpression) throws Throwable {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());

		JSONArray jsonArray = null;
		String actualVal = null;
		Object object = null;
		try {
			Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonData);

			object = JsonPath.read(document, jsonPathExpression);

		} 
		catch (NullPointerException e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw e;
		}
		catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
		return object;
	}
	
	public synchronized static String getJsonObjectElement(Object jsonObject, String jsonPathExpression) throws Throwable {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());

		JSONArray jsonArray = null;
		String actualVal = null;
		try {

			Object object = JsonPath.read(jsonObject, jsonPathExpression);

			if (object instanceof JSONArray) {
				jsonArray = (JSONArray) object;
				actualVal = jsonArray.get(0).toString();
			} else {
				actualVal = object.toString();
			}

		} 
		catch (NullPointerException e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw e;
		}
		catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
		return actualVal;
	}
	
	/**
	 * Returns JSONArray from jsonData based on jsonPathExpression
	 * 
	 * @param jsonData input jsonData as java.lang.String
	 * @param jsonPathExpression jsonPathExpression as java.lang.String 
	 * @return jsonArray returns net.minidev.json.JSONArray based on jsonPathExpression
	 * @throws Throwable java.lang.Throwable
	 */
	public synchronized static JSONArray getJsonArray(String jsonData, String jsonPathExpression) throws Throwable {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		
		JSONArray jsonArray = null;
		try {
			Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonData);
			Object object = JsonPath.read(document, jsonPathExpression);

			if (object instanceof JSONArray) {
				jsonArray = (JSONArray) object;
			}

		} catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
		return jsonArray;
	}	
	
	/**
	 * getCallerClassName
	 * 
	 * @return String
	 */
	private static String getCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		return stElements[3].getClassName();
	}

	/**
	 * getCallerMethodName
	 * 
	 * @return String
	 */
	private static String getCallerMethodName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		return stElements[3].getMethodName();
	}	
	
	/**
	 * Returns length from jsonData based on jsonPathExpression
	 * 
	 * @param jsonData input jsonData as java.lang.String
	 * @param jsonPathExpression jsonPathExpression as java.lang.String 
	 * @return size returns size of the element from jsonData based on jsonPathExpression
	 * @throws Throwable java.lang.Throwable
	 */
	public synchronized static int getJsonElementSize(String jsonData, String jsonPathExpression) throws Throwable {
		LOG.info("Class name" + getCallerClassName() + ", Method name : " + getCallerMethodName());

		JSONArray jsonArray = null;
		int size = 0;
		try {
			Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonData);

			Object object = JsonPath.read(document, jsonPathExpression);

			if (object instanceof JSONArray) {
				jsonArray = (JSONArray) object;
				size = jsonArray.size();
			} else {
				size=1;
			}

		} catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
		return size;
	}

    public synchronized static List<HashMap> getJsonArrayToHashMap(String jsonData, String jsonPathExpression) throws Throwable {
        LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());

        JSONArray jsonArray = null;
        List<HashMap> results = new ArrayList<>();

        try {
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonData);
            Object object = JsonPath.read(document, jsonPathExpression);

            if (object instanceof JSONArray) {
                jsonArray = (JSONArray) object;
            }
            org.json.JSONArray jsonResults = null;
            if (jsonArray != null) {
                jsonResults = new org.json.JSONArray(jsonArray.toJSONString());
            }

            if (jsonResults != null) {
                for (int i = 0; i < jsonResults.length(); i++) {
                    org.json.JSONObject childJsonArray = jsonResults.getJSONObject(i);
                    Iterator<?> keys = childJsonArray.keys();
                    HashMap<String, String> row = new HashMap<>();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        row.put(key, childJsonArray.get(key).toString());
                    }
                    results.add(row);
                }
            }

        } catch (Exception e) {
            LOG.error(Log4jUtil.getStackTrace(e));
        }

        return results;
    }
}
