package com.cubic.genericutils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.cubic.logutils.Log4jUtil;

public class StringUtil {
	private static final Logger LOG = Logger.getLogger(FileUtil.class.getName());
	
	/**
	 * Replaces the regular expression values with client required values
	 * 
	 * @param text value of content
	 * @param pattern of type (String), regular expression of actual value
	 * @param replaceWith of (String), value to replace the actual
	 * @return : String value indicating expected text
	 */
	public static String replaceAll(String text, String pattern, String replaceWith) {
		String value = null;
		try {
			value = text.replaceAll(pattern, replaceWith);
		} catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
		return value;
	}

	/**
	 * Gets sub string of given actual content
	 * 
	 * @param text of type (String), Actual text
	 * @param startIndex of (int), Start index of sub string
	 * @param endIndex of (int), end index of sub string
	 * @return : String value indicating the expected substring of actual content
	 */
	public static String subString(String text, int startIndex, int endIndex) {
		String flag = null;
		try {
			flag = text.substring(startIndex, endIndex);
		} catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
		return flag;
	}

	/**
	 * getRandomAlphaNumeric, Get random String      //br
	 * @param numberOfCharacters of (int), length Number of characters specified
	 * @return String indicating the value of random string
	 */
	public static String getRandomAlphNumeircString(int numberOfCharacters) {
		String alphaNum = null;
		try {

			alphaNum = RandomStringUtils.randomAlphanumeric(numberOfCharacters);
		}
		catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
			return alphaNum;

		}
    /**
     * Gets randomString of given actual context //br
     * @param minNumberOfCharacters length of the string
     * @param maxNumberOfCharacters  length of the string
     * @return String indicating the random string
     */
    public static String getRandomString(int minNumberOfCharacters,int maxNumberOfCharacters) {
        String randomString = null;
        try {
            randomString = RandomStringUtils.randomAlphabetic(minNumberOfCharacters, maxNumberOfCharacters);
        } catch (Exception e) {
            LOG.error(Log4jUtil.getStackTrace(e));

        }
        return randomString;
    }
	/**
	 * Gets randomAlphanumeric string of given actual context //br
	 * @param minNumberOfCharacters length of the string
	 * @param maxNumberOfCharacters  length of the string
	 * @return String indicating the random string
	 */
    public static String getRandomAlphanumericString(int minNumberOfCharacters,int maxNumberOfCharacters) {
        String randomAlphaNumeric = null;
        try {
            randomAlphaNumeric = RandomStringUtils.randomAlphanumeric(minNumberOfCharacters, maxNumberOfCharacters);
        } catch (Exception e) {
            LOG.error(Log4jUtil.getStackTrace(e));

        }
        return randomAlphaNumeric;
    }

	/**
	 * getRandomString, Get random String
	 * @param noOfCharacters of (int), Number of characters to get randomly
	 * @return String indicating the value of random string
	 */
	public static String getRandomString(int noOfCharacters){
		String text = null;
		try{
			text =  RandomStringUtils.randomAlphabetic(noOfCharacters);
		}catch(Exception e){
			LOG.error(Log4jUtil.getStackTrace(e));
		}
		return text;
	}


	/**
	 * Gets the Value last digits (as user defined) of a String 
	 * 
	 * @param value of (int), indicates the actual string
	 * @param digitValue of (int), indicates number of digits from string
	 * @return String indicating the value of last digits(as user defined) of String
	 */
	public static String getLastDigitsFromString(String value,int digitValue){
		String opValue = null;
		try{
		int startindex = value.length() - digitValue;
		 opValue = value.substring(startindex);
		} catch(Exception e){
			 LOG.error(Log4jUtil.getStackTrace(e));
		 }
		return opValue;
	}

	/**
	 * Gets the Value first digits (as user defined) of a String 
	 * 
	 * @param value of (int), indicates the actual string
	 * @param digitValue of (int), indicates number of digits from string
	 * @return String indicating the value of first digits(as user defined) of String
	 * @throws Exception 
	 */
	public static String getFirstDigitsFromString(String value,int digitValue) throws Exception {
		String opValue = null;

		if (value.length() < digitValue) {
			throw new Exception("provided value '" + value + "', is less than the value length '" + value.length() + "'");
		}

		try {
			opValue = value.substring(0, digitValue);
		} catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
		return opValue;
	}
}
