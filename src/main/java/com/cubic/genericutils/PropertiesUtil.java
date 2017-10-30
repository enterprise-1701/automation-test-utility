package com.cubic.genericutils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Contains several useful wrapper methods related to java.util.Properties, It
 * cannot be instantiated.
 * 
 * @since 1.0
 */
public abstract class PropertiesUtil {

	/**
	 * Returns the data present in .properties file as Hashtable
	 * 
	 * @param propertiesFilePath
	 * @return Hashtable<String, String>
	 */
	public static synchronized Hashtable<String, String> getPropertysAsHashtable(String propertiesFilePath) {

		Hashtable<String, String> propertiesTable = null;
		try {
			Properties properties = new Properties();
			InputStream inputStream = new FileInputStream(propertiesFilePath);

			// Load the properties file.
			properties.load(inputStream);

			propertiesTable = new Hashtable<String, String>();
			for (final String key : properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				propertiesTable.put(key, value != null ? value.trim() : value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return propertiesTable;
	}

	/**
	 * Returns the property value based on key in the .properties file.
	 * 
	 * @param propertiesFilePath
	 * @param key
	 * @return
	 */
	public static synchronized String getProperty(String propertiesFilePath, String key) {
		String value = null;

		try {
			if (key == null) {
				throw new Exception("Key '" + key + "' should not be null, provide .properties key");
			}

			if (key.trim().length() == 0) {
				throw new Exception("Key '" + key + "' should not be blank, provide .properties key");
			}

			Properties properties = new Properties();
			InputStream inputStream = new FileInputStream(propertiesFilePath);

			// Load the properties file.
			properties.load(inputStream);

			value = properties.getProperty(key);
			value = value != null ? value.trim() : value;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

	
	/** 
	 * Updates the value for the key in the properties file
	 * 
	 * Note: if there is no key available in the properties, then the method adds key and its value to the property file.
	 * 
	 * @param propertiesFilePath
	 * @param key 
	 * @param value
	 * @return boolean 
	 */
	public static synchronized boolean setProperty(String propertiesFilePath, String key, String value) {

		try {
			if (key == null) {
				throw new Exception("Key '" + key + "' should not be null, provide .properties key");
			}

			if (key.trim().length() == 0) {
				throw new Exception("Key '" + key + "' should not be blank, provide .properties key");
			}
			
			value = value!=null ? value : "";
			
			PropertiesConfiguration properties = new PropertiesConfiguration(propertiesFilePath);
			properties.setProperty(key, value);
			properties.save();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}

	}
	
	/**
	 * Updates the batch of values for the keys, based on the keys and values provided in the Hashtable in the properties file. 
	 * 	
	 * Note: if there is no key available in the properties, then the method adds key and its value to the property file.
	 * 
	 * @param propertiesFilePath
	 * @param propertiesTable
	 * @return boolean
	 */
	public static synchronized boolean setProperties(String propertiesFilePath, Hashtable<String, String> propertiesTable) {

		try {
			PropertiesConfiguration properties = new PropertiesConfiguration(propertiesFilePath);

			for(String key : propertiesTable.keySet()){
				String value = propertiesTable.get(key);

				if (key == null) {
					throw new Exception("Key '" + key + "' should not be null, provide .properties key");
				}

				if (key.trim().length() == 0) {
					throw new Exception("Key '" + key + "' should not be blank, provide .properties key");
				}
				
				value = value!=null ? value : "";
				properties.setProperty(key, value);
			}
			
			properties.save();
		
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}

		
	}	
}
