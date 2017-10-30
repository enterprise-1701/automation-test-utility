package com.cubic.logutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.PropertyConfigurator;

import com.cubic.genericutils.FileUtil;
import com.cubic.genericutils.GenericConstants;

public abstract class Log4jUtil {
	
	/**
	 * Configures Log4j
	 * 
	 */
	public synchronized static void configureLog4j(String log4jConfigFilePath) {
		
		//Creates the "Log4j.properties" if it doesn't exists.
		createLog4JPropertiesFile(GenericConstants.LOG4J_FILEPATH);
		
		//Creates the log4j output root folder, if it doesn't exists.
		createLog4JFolders();
		
		PropertyConfigurator.configure(log4jConfigFilePath);
	}

	/**
	 * Creates the "Log4j.properties" if it doesn't exists.
	 * 
	 * @param log4jFilePath
	 */
	private synchronized static void createLog4JPropertiesFile(String log4jFilePath) {
		try {
			// If Log4j.properties doesn't exist, then create the default
			// Log4j.properties specific to project
			if (!FileUtil.verifyFileExists(GenericConstants.LOG4J_FILEPATH)) {
				FileUtil.createFileWithContent(GenericConstants.LOG4J_FILEPATH, getLog4JFileContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the default log4j content.
	 * 
	 * @return java.lang.String
	 */
	private synchronized static String getLog4JFileContent() {
		String  log4jContent = 
				"log4j.rootLogger=DEBUG, consoleAppender, fileAppender, htmlAppender\r\n" + 
				"\r\n" + 
				"logfile = "+GenericConstants.LOG4J_OUTPUT_FOLDER_LOGS+"\r\n" +
				"htmlfile = "+GenericConstants.LOG4J_OUTPUT_FOLDER_HTMLLOGS+"\r\n" +
				"\r\n" + 
				"# Define the consoleAppender\r\n" + 
				"log4j.appender.consoleAppender=org.apache.log4j.ConsoleAppender\r\n" + 
				"log4j.appender.consoleAppender.layout=org.apache.log4j.PatternLayout\r\n" + 
				"log4j.appender.consoleAppender.layout.ConversionPattern=%-5p [%t] %c %x - %m%n\r\n" + 
				"\r\n" + 
				"# Define the fileAppender\r\n" + 
				"log4j.appender.fileAppender=com.cubic.logutils.LogFileAppender\r\n" + 
				"log4j.appender.fileAppender.File=${logfile}/FrameworkLogs\r\n" + 
				"log4j.appender.fileAppender.layout=org.apache.log4j.PatternLayout\r\n" + 
				"log4j.appender.fileAppender.layout.ConversionPattern=%-5p [%t] %c %x - %m%n\r\n" + 
				"\r\n" + 
				"# Define the html appender\r\n" + 
				"log4j.appender.htmlAppender=com.cubic.logutils.HtmlFileAppender\r\n" + 
				"log4j.appender.htmlAppender.File=${htmlfile}/FrameworkLogs\r\n" + 
				"log4j.appender.htmlAppender.layout=org.apache.log4j.HTMLLayout\r\n" + 
				"log4j.appender.htmlAppender.layout.Title=HTML Layout Example\r\n" + 
				"log4j.appender.htmlAppender.layout.LocationInfo=true\r\n" + 
				"\r\n" + 
				"log4j.logger.org.apache.http=ERROR";
		
		return log4jContent;
	}
	
	/**
	 * Creates the log4j output root folder, if it doesn't exists.
	 */
	private synchronized static void createLog4JFolders() {
		try{
			FileUtil.createDirectoryWithPath(GenericConstants.LOG4J_OUTPUT_FOLDER_HTMLLOGS);	
			FileUtil.createDirectoryWithPath(GenericConstants.LOG4J_OUTPUT_FOLDER_LOGS);
		}catch (Exception e) {
			e.printStackTrace();
		} 		
	}
	
	/**
	 * Fetches the error stack trace.
	 * <pre>
	 * Ex: 
	 * try{
	 * .....
	 * }catch (Exception e) {
	 *		LOG.error(LogFileAppender.getStackTrace(e));
	 * }
	 * </pre>
	 * @param e : Throwable
	 * @return String : contains error stack trace
	 */
	public static String getStackTrace(Throwable e)
	{
	    StringWriter stringWriter = new StringWriter();
	    PrintWriter  printWriter  = new PrintWriter(stringWriter);
	    e.printStackTrace(printWriter);
	    printWriter.close();    //surprise no IO exception here
	    try {
	        stringWriter.close();
	    }
	    catch (IOException e1) {
	    }
	    return stringWriter.toString();		
	}	
}
