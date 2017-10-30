package com.cubic.logutils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;

/**
 * Adds additional capabilities(i.e. html appenders) to log4j
 * 
 * @since 1.0
 */
public class HtmlFileAppender extends FileAppender {
	@Override
	public void setFile(String file) {
		super.setFile(prependDateToLogFile(file));
	}

	/**
	 * Adds date format to the generated html log4j file.
	 * 
	 * @param filename
	 * @return java.lang.String
	 */
	private static String prependDateToLogFile(String filename) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_hh_mm_ss");
		return filename + "_" + dateFormat.format(new Date()) + ".html";
	}
}
