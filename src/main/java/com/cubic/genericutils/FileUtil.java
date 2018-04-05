package com.cubic.genericutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.cubic.logutils.Log4jUtil;

/**
 * Contains several useful methods to perform operations on files and folders,
 * It cannot be instantiated.
 * 
 * @since 1.0
 */
public abstract class FileUtil {
	private static final Logger LOG = Logger.getLogger(FileUtil.class.getName());
	
	/**
	 * Reads the file
	 * 
	 * @param path of the respective file
	 * @return String value indicating the content of the file
	 * @throws IOException Should be handled
	 */
	public synchronized static String readFile(String path) throws IOException {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		String fileContent = null;
		try{
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			fileContent = new String(encoded, Charset.defaultCharset());
		}catch (Exception e) {
		    LOG.error(Log4jUtil.getStackTrace(e));
            throw new RuntimeException(e);
		}
		
		return fileContent;
	}  

	/**
	 * Verifies file exists or not in respective path
	 * 
	 * @param path of the file 
	 * @return boolean value indicating success of the operation
	 * @throws Exception Should be handled
	 */
	public synchronized static boolean verifyFileExists(String path) throws Exception {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		boolean flag = false;
		
		try{
			flag = new File(path).exists() ? true : false;
		}catch (Exception e) {
		    LOG.error(Log4jUtil.getStackTrace(e));
            throw new RuntimeException(e);
		}
		return flag;
	}
	
	/**
	 * Creates the directory
	 * 
	 * @param path place where to create the directory
	 * @throws Exception Should be handled
	 */
	public synchronized static void createDirectory(String path) throws Exception {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdir()) {
				LOG.info("Directory is created! "+file);
			} else {
				LOG.error("Failed to create the file '" + path + "'.");
				throw new Exception("Failed to create the file '" + path + "'.");
			}
		}
	}

	/**
	 * Creates multiple directories with in the path.
	 * 
	 * <pre>
	 * ex: "c:/sample/test" is the directory path. if 'sample' and 'test'
	 * folders are not present in the path, then it creates the 'sample' folder
	 * and creates 'test' folder with in the sample folder.
	 * </pre>
	 * @param path place where to create the directory
	 * @throws Exception Should be handled 
	 */
	public synchronized static void createDirectoryWithPath(String path) throws Exception {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdirs()) {
				LOG.info("Directory is created! "+file);
			} else {
				LOG.error("Failed to create the directory '" + path + "'.");
				throw new Exception("Failed to create the directory '" + path + "'.");
			}
		}
	}

	/**
	 * Copies the file from source to destination
	 * 
	 * @param srcPath indicating source path 
	 * @param destPath indicating destination path 
	 * @throws IOException Should handle IOException
	 */
	public synchronized static void copyFile(String srcPath, String destPath) throws IOException {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		try{
			Path src = Paths.get(srcPath);
			Path dest = Paths.get(destPath);
			Files.copy(src, dest);
		}catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new IOException("Failed to copy the files.");
		}
	}

	/**
	 * Copies all files present in source location to target location.
	 * 
	 * @param sourceLocation indicating source path
	 * @param targetLocation indicating destination path 
	 * @throws IOException Should handle IOException
	 */
	public synchronized static void copyAllFilesInDirectory(File sourceLocation, File targetLocation)
			throws IOException {

		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		try{
			if (sourceLocation.isDirectory()) {
				if (!targetLocation.exists()) {
					targetLocation.mkdir();
				}
				String[] children = sourceLocation.list();
				for (int i = 0; i < children.length; i++) {
					copyAllFilesInDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
				}
			} else {
	
				InputStream in = new FileInputStream(sourceLocation);
				OutputStream out = new FileOutputStream(targetLocation);
	
				// Copy the bits from instream to outstream
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new IOException("Unable to copy all files in directory");
		}
	}

	/**
	 * Appends the content of type string to the existing file.
	 * 
	 * @param srcFileName indicating the path of Source file
	 * @param contentToAppend content to append to the file 
	 */
	public synchronized static void appendToFile(String srcFileName, String contentToAppend) {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		
		BufferedWriter bufferWriter = null;
		FileWriter fileWriter = null;
		try {

			File file = new File(srcFileName);
			// true = append file
			fileWriter = new FileWriter(file.getAbsoluteFile(), true);
			bufferWriter = new BufferedWriter(fileWriter);
			bufferWriter.write(contentToAppend);
		} catch (IOException e) {
		    LOG.error(Log4jUtil.getStackTrace(e));
            throw new RuntimeException(e);
		} finally {
			try {
				if (bufferWriter != null)
					bufferWriter.close();
				if (fileWriter != null)
					fileWriter.close();
			} catch (IOException ex) {
			    LOG.error(Log4jUtil.getStackTrace(ex));
	            throw new RuntimeException(ex);
			}
		}

	}

	/**
	 * Creates the file with content of type string
	 * 
	 * @param filePath indicating the path of destination
	 * @param data value of content should be paced in the file
	 * @throws IOException Should be handled IOException
	 */
	public synchronized static void createFileWithContent(String filePath, String data) throws IOException {
		LOG.info("createFileWithContent -- Caller Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		
		try {
		    LOG.info("FILEPATH: " + filePath);
		    File fileToCreate = new File(filePath);
		    
		    // Check Directory first, if non-existent, create it
		    if (!fileToCreate.getParentFile().exists()) {
		        LOG.info("Directory DOES NOT exist, CREATING...");
		        fileToCreate.getParentFile().mkdirs();
		    }
		        
		    // Check file
            if (!fileToCreate.exists()) {
                LOG.info("File DOES NOT exist, CREATING...");
                fileToCreate.createNewFile();
            }

			FileOutputStream out = new FileOutputStream(fileToCreate, false);
			out.write(data.getBytes());
			out.close();
		}catch (Exception e) {
		    LOG.error(Log4jUtil.getStackTrace(e));
			throw new RuntimeException(e);
 		}
	}
	
	/**
	 * Deletes the File or Folder from respective path 
	 * 
	 * @param fileOrFolderPath indicating path of target folder or file
	 */
	public synchronized static void deleteFileOrFolder(String fileOrFolderPath) {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		File directory = new File(fileOrFolderPath);
		// make sure directory or file exists
		if (!directory.exists()) {
			LOG.info("Directory or File does not exist. "+directory);
		} else {
			try {
				delete(directory);
				} catch (IOException e) {
				    LOG.error(Log4jUtil.getStackTrace(e));
		            throw new RuntimeException(e);
				}
			}
		}

	/**
	 * Deletes the File from respective path 
	 * 
	 * @param filePath indicating the path of destination
	 * @throws IOException Should be handled IOException
	 */
	private synchronized static void delete(File file) throws IOException {
		LOG.info("Class name : " + getCallerClassName() + "Method name : " + getCallerMethodName());
		try{
		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
				LOG.info("Directory or file is deleted : " + file.getAbsolutePath());
			} else {
				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					LOG.info("Directory of is deleted : " + file.getAbsolutePath());
				}
			}
		} else {
			// if file, then delete it
			file.delete();
			LOG.info("File is deleted : " + file.getAbsolutePath());
			}
		}catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
		}
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
	 * deleteDirectory: Function to delete directory from local machine
	 * @param directoryPath of type (String), path for the directory to delete
	 * @return void
	 */
	public void deleteDirectory(String directoryPath) throws IOException {
		FileUtils.deleteDirectory(new File(directoryPath));
	}

	
	/**
	 * getRandomNumeric: Get random Numeric
	 * @param noOfCharacters of type (int), Number of characters to get randomly
	 * @return String
	 * @throws IOException
	 */
	public static String getRandomNumeric(int noOfCharacters) throws IOException {
		return RandomStringUtils.randomNumeric(noOfCharacters);
	}
	
  /**
	 * deleteSpecificFile: Function to delete the specified file from local machine path
	 * @param filepath of type (String)
	 * @return : void
	 */

	public static void deleteSpecificFile(String fileName) throws InterruptedException {
		try {
			File file = new File(fileName);
			if (file.delete()) {
				LOG.info(file.getName() + " is deleted!");
			} else {
				LOG.info("Delete operation is failed.");
			}
		} catch (Exception e) {
		    LOG.error(Log4jUtil.getStackTrace(e));
            throw new RuntimeException(e);
		}
	}
		
		/**
		 * Gets the latest file from specified path
		 * @param filePath indicating the path of destination
		 * @return latest modified file name
		 */
		public static File getLatestFilefromDir(String dirPath){
		    File dir = new File(dirPath);
		    File[] files = dir.listFiles();
		    if (files == null || files.length == 0) {
		        return null;
		    }
		
		    File lastModifiedFile = files[0];
		    for (int i = 1; i < files.length; i++) {
		       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
		           lastModifiedFile = files[i];
		       }
		    }
		    return lastModifiedFile;
		}
		
		/**
		 * getTextFromPDF : Read the pdf file and provide the content as string
		 * @param pdfFilePath indicating the path of destination
		 * return String value indicating the content of file 
		 */
		public static String getTextFromPDF(String pdfFilePath) throws Throwable {
			String parsedText = null;
			try{
					File pdfFile = new File(pdfFilePath);
					PDFParser parser = new PDFParser(new FileInputStream(pdfFile));
					parser.parse();
					COSDocument cosDoc = parser.getDocument();
					PDDocument pdDoc = new PDDocument(cosDoc);
					PDFTextStripper pdfStripper = new PDFTextStripper();
					parsedText = pdfStripper.getText(pdDoc);
					parser.getPDDocument().close();
				}catch(Exception e){
				    LOG.error(Log4jUtil.getStackTrace(e));
		            throw new RuntimeException(e);
				}
			return parsedText;
			}
		

			/**
			 * Generates random number in the given range
			 * @param Max - takes maximum number as a parameter
			 * @param Min - takes minimum number as a parameter
			 * @return once the random number is generated in the given range it returns the number
			 */
			public static int generateRandomNumber(int Max, int Min){
				int randomNum=0;
				try {
				Random rn = new Random();
				int j = rn.nextInt((Max - Min) + 1);
				randomNum =  Min + j;

				} catch (Exception e) {
				    LOG.error(Log4jUtil.getStackTrace(e));
				    throw new RuntimeException(e);
				}
				return randomNum;
			}
			
			/**
			 * This function is used to read the data from csv file with a comma separated and stores them into an array list
			 * @param file
			 * @return
			 * @throws Throwable
			 * @return returns list of array values of string data
			 */
			public static  ArrayList<String> readDataFromCSV(String filePath) throws Throwable {

				String line = "";
				String csvSplitBy = ",";
				File file = new File(filePath);

				ArrayList<String> columnvalues = new ArrayList<String>();
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {

					while ((line = br.readLine()) != null) {

						String[] data = line.split(csvSplitBy);
						for (int i = 0; i < data.length; i++) {
								columnvalues.add(data[i]);

						}

					}

				} catch (IOException e) {
				    LOG.error(Log4jUtil.getStackTrace(e));
		            throw new RuntimeException(e);
				}
				return columnvalues;

	} 
	
	/**
     * Processes a CSV file (not necessarily separated by commas) into an ArrayList of Hashtables
     * @param filepath
     * @return
     */
    public static ArrayList<Hashtable<String, String>> processCSV(String filepath, String csvSeparator) {
        BufferedReader br = null;
        String line = "";
        String[] colNames = null;
        ArrayList<String> colNameList = null;
        ArrayList<String> runValuesList = null;
        Hashtable<String, String> runValues = null;
        ArrayList<Hashtable<String, String>> testData = null;
        String colName = "";
        String value = "";
        int runCount = 0;

        try {
            LOG.info("##### OPEN INPUT FILE FOR READING: " + filepath + "\n");
            br = new BufferedReader(new InputStreamReader(FileUtil.class.getClass().getResourceAsStream(filepath)));
            
            LOG.info("##### READ FIRST LINE CONTAINING COLUMN NAMES\n");
            line = br.readLine();
            if (line != null) {
                colNames = line.split(csvSeparator, -1);
                colNameList = new ArrayList<String>(Arrays.asList(line.split(csvSeparator, -1)));
                LOG.info("colNames length: " + colNames.length);
                LOG.info("colNameList size: " + colNameList.size());
                LOG.info("Line found: " + line);
            }
            
            LOG.info("##### CREATE HASHTABLE\n");
            testData = new ArrayList<Hashtable<String, String>>();
            
            while ((line = br.readLine()) != null) {
                LOG.info("##### LINE #" + ++runCount + " FOUND: " + line + "\n");
                
                LOG.info("##### SPLIT VALUES");
                runValuesList = new ArrayList<String>(Arrays.asList(line.split(csvSeparator, -1)));
                runValues = new Hashtable<String, String>();
                
                LOG.info("##### LOOP THROUGH EACH COLUMN NAME TO BUILD HASHTABLE FOR ROW");
                for (int i = 0; i < colNameList.size(); ++i) {
                    colName = colNameList.get(i);
                    value = runValuesList.get(i);
                    
                    LOG.info("##### COLUMN NUMBER: " + (i + 1) + ", COLUMN NAME: " + colName + ", VALUE: " + value);
                    runValues.put(colName, value);
                }
                
                LOG.info("##### runValues Hashtable size: " + runValues.size() + "\n");
                testData.add(runValues);
            }
            
            if (br != null) {
                br.close();     // Clean-up
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        return testData;
    }
}
