package com.cubic.genericutils;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.cubic.logutils.Log4jUtil;

public abstract class XmlUtil {
	private static final Logger LOG = Logger.getLogger(XmlUtil.class.getName());
	
	/**
	 * Returns value from xmlData based on xmlPathExpression
	 * 
	 * @param xmlData : input xmlData as java.lang.String
	 * @param xmlPathExpression : xmlPathExpression as java.lang.String
	 * @return actualVal : returns string value from jsonData based on jsonPathExpression
	 * 
	 */
	public synchronized static String getXmlElement(String xmlData, String xmlPathExpression){
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());

		String actualVal = null;
		try{
			Document xmlDocument = getXMLDocumentObject(xmlData);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			actualVal = xpath.compile(xmlPathExpression).evaluate(xmlDocument);
		}catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
	
		return actualVal;
	}	
	
	/**
	 * Converts the inputXml(i.e. String xml) into org.w3c.dom.Document and returns the org.w3c.dom.Document object.
	 * 
	 * @param inputXml 
	 * @return org.w3c.dom.Document 
	 */
	public synchronized static Document getXMLDocumentObject(String inputXml) {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		Document document = null;

		try {
			if (inputXml == null) {
				throw new Exception("inputXml should not be null");
			}

			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(inputXml));

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);

			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(inputSource);
		} catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}

		return document;
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
}
