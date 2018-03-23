package com.cubic.database;
 
import org.apache.log4j.Logger;

import com.cubic.genericutils.GenericConstants;
import com.cubic.logutils.Log4jUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Properties;
 
public class DataBaseUtil {
	
	private final Logger LOG = Logger.getLogger(this.getClass().getName());

	public Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
    private Hashtable<String , String> propTable = GenericConstants.GENERIC_FW_CONFIG_PROPERTIES;
    private static int openConnectionCount = 0;
    private static int totalConnections = 0;
    
   /** 
    *  Connects to the required database 
    *  
    *  @param dbName Name Of DataBase
    */ 
    private DataBaseUtil(String dbName){
    	connection = getDBConnection(dbName);
    }
     
    /**
     * Gets the connection Objects of Required DataBase
     * 
     * @param dbName Name Of DataBase (eg : mysql,msaccess,oracle and sqldeveloper)
	 * @return dataBaseUtil object which holds the connection object
     */
    public static DataBaseUtil getDatabaseConnection(String dbName){
    	DataBaseUtil dataBaseUtil = new DataBaseUtil(dbName);
    	if (dataBaseUtil.connection ==null) {
    		dataBaseUtil = null;
		}
    	return dataBaseUtil;
    }
     
    /**
     * Parameterized Constructor
     * @param dbName
     * @param dbHost
     * @param dbUsername
     * @param dbPassword
     * @param dbMsAccessPath
     */
     private DataBaseUtil(String dbName, String dbUrl, String dbUsername, String dbPassword, String dbMsAccessPath) {
            connection = getDBConnection(dbName, dbUrl, dbUsername, dbPassword, dbMsAccessPath);
       }
     
     /**
      * Overloaded Parameterized Constructor
      * @param dbName
      * @param dbUrl
      * @param dbUsername
      * @param dbPassword
      * @param dbRole
      * @param dbMsAccessPath
      */
     private DataBaseUtil(String dbName, String dbUrl, String dbUsername, String dbPassword, String dbRole, String dbMsAccessPath) {
         connection = getDBConnection(dbName, dbUrl, dbUsername, dbPassword, dbRole, dbMsAccessPath);
     }
     
     /**
      * OVERLOADED getDatabaseConnection()
      * Takes all connectivity parameters
      * @param dbName
      * @param dbHost
      * @param dbUsername
      * @param dbPassword
      * @param dbMsAccessPath
      * @return
      */
     public static DataBaseUtil getDatabaseConnection(String dbName, String dbUrl, String dbUsername, String dbPassword, String dbMsAccessPath){
         DataBaseUtil dataBaseUtil = new DataBaseUtil(dbName, dbUrl, dbUsername, dbPassword, dbMsAccessPath);
         return dataBaseUtil;
     }
     
     /**
      * OVERLOADED getDatabaseConnection() -- To include Database Role or internal_logon (e.g. SYSDBA)
      * @param dbName
      * @param dbUrl
      * @param dbUsername
      * @param dbPassword
      * @param dbRole
      * @param dbMsAccessPath
      * @return
      */
     public static DataBaseUtil getDatabaseConnection(String dbName, String dbUrl, String dbUsername, String dbPassword, String dbRole, String dbMsAccessPath){
         DataBaseUtil dataBaseUtil = new DataBaseUtil(dbName, dbUrl, dbUsername, dbPassword, dbRole, dbMsAccessPath);
         return dataBaseUtil;
     }
     
     /**
      * OVERLOADED getDBConnection()
      * @param dbName
      * @param dbHost
      * @param dbUsername
      * @param dbPassword
      * @param dbMsAccessPath
      * @return
      */
     private Connection getDBConnection(String dbName, String dbUrl, String dbUsername, String dbPassword, String dbRole, String dbMsAccessPath){
         LOG.info("+++++++++Inside getDB Connection+++++++++++++++++");
         try {
             Properties prop = null;
             
             if (dbName.equalsIgnoreCase(GenericConstants.MSACCESS)) {
                 connection = DriverManager.getConnection("jdbc:ucanaccess:" + dbMsAccessPath);
             } 
             else {
                 // Get driver
                 if (dbName.equalsIgnoreCase(GenericConstants.MYSQL)) {
                     Class.forName(propTable.get("mysql_driverName"));
                 }
                 else if (dbName.equalsIgnoreCase(GenericConstants.ORACLE)) {
                     Class.forName(propTable.get("oracle_driverName"));
                 }
                 else if (dbName.equalsIgnoreCase(GenericConstants.SQLDEVELOPER)) {
                     Class.forName(propTable.get("sqldeveloper_driverName"));
                 }
                 else if(dbName.equalsIgnoreCase(GenericConstants.SQLSERVER)){
                     Class.forName(propTable.get("sqlserver_driverName"));
                 }
                 else if(dbName.equalsIgnoreCase(GenericConstants.POSTGRESQL)){
                	 Class.forName(propTable.get("postgre_driverName"));
                 }
                 // Setup connection, but check for database Role (internal_logon) in case of ORACLE
                 LOG.info("DB URL = " + dbUrl);
                 if (dbName.equalsIgnoreCase(GenericConstants.ORACLE) && !(dbRole == null || dbRole.isEmpty())) {
                     // DB Role only applies to ORACLE
                     LOG.info("+++++++++ DB Role: " + dbRole + " +++++++++++++++++");
                     prop = new Properties();
                     
                     prop.put("user", dbUsername);
                     prop.put("password", dbPassword);
                     prop.put("internal_logon", dbRole);
                     connection = DriverManager.getConnection(dbUrl, prop);
                 }
                 else {
                     connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                 }
             }
                 
             ++openConnectionCount;     // Keep track of currently open connections and total connections
             ++totalConnections;
             
             LOG.info("+++++++++ DB Connection Established+++++++++++++++++");
             statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
             LOG.info("+++++++++ Statement Established+++++++++++++++++");
         }
         catch (Exception e) {
             LOG.error(Log4jUtil.getStackTrace(e));
             throw new RuntimeException(e);
         }
         
         return connection;
     }
     
     /**
      * OVERLOADED getDBConnection()
      * @param dbName
      * @param dbHost
      * @param dbUsername
      * @param dbPassword
      * @param dbMsAccessPath
      * @return
      */
     private Connection getDBConnection(String dbName, String dbUrl, String dbUsername, String dbPassword, String dbMsAccessPath){
         return getDBConnection(dbName, dbUrl, dbUsername, dbPassword, null, dbMsAccessPath);
     }     
     
    /**
     * Provides DB connection to a specific database
     * 
     * @param dbName indicates the name of database (eg : mysql,msaccess,oracle and sqldeveloper)
     * @return connection holds the required DB connection
     */
    private Connection getDBConnection(String dbName){
    	LOG.info("+++++++++Inside getDB Connection+++++++++++++++++");
		try{
			
			if(dbName.equalsIgnoreCase(GenericConstants.MSACCESS)){
				connection=DriverManager.getConnection("jdbc:ucanaccess:"+propTable.get("msaccess_dbLocationPath"));
			}else if(dbName.equalsIgnoreCase(GenericConstants.MYSQL)){
				Class.forName(propTable.get("mysql_driverName"));
				connection=DriverManager.getConnection(propTable.get("mysql_dbURL"), propTable.get("mysql_username"), propTable.get("mysql_password"));
			}else if(dbName.equalsIgnoreCase(GenericConstants.ORACLE)){
				Class.forName(propTable.get("oracle_driverName"));
				connection=DriverManager.getConnection(propTable.get("oracle_dbURL"), propTable.get("oracle_username"), propTable.get("oracle_Password"));
			}else if(dbName.equalsIgnoreCase(GenericConstants.SQLDEVELOPER)){
				Class.forName(propTable.get("sqldeveloper_driverName"));
				connection=DriverManager.getConnection(propTable.get("sqldeveloper_dbURL"), propTable.get("sqldeveloper_username"), propTable.get("sqldeveloper_password"));
			}
			else if(dbName.equalsIgnoreCase(GenericConstants.SQLSERVER)){
				Class.forName(propTable.get("sqlserver_driverName"));
				connection=DriverManager.getConnection(propTable.get("sqlserver_dbURL"), propTable.get("sqlserver_username"), propTable.get("sqlserver_password"));
			}
			else if(dbName.equalsIgnoreCase(GenericConstants.POSTGRESQL)){
				Class.forName(propTable.get("postgre_driverName"));
				connection = DriverManager.getConnection(propTable.get("postgre_dbURL"), propTable.get("postgre_username"), propTable.get("postgre_password"));
			}
			
			++openConnectionCount;   // Keep track of currently open connections and total connections
			++totalConnections;
			
			LOG.info("+++++++++ DB Connection Established+++++++++++++++++");
			statement = connection.createStatement();
			LOG.info("+++++++++ Statement Established+++++++++++++++++");
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new RuntimeException(e);
		}
		return connection;
	}
    
    /**
     * Executes the given SQL statement (only SELECT ) 
     * 
     * @param query indicates select query Of Type String
     * @return ResultSet holds the table data
     */
	public ResultSet retrieveData(String query) {
		try{
			LOG.info("Query: " + query);
			resultSet = statement.executeQuery(query);
	    }catch(Exception e){
	    	LOG.error(Log4jUtil.getStackTrace(e));
	    	throw new RuntimeException(e);
		}
		return resultSet;
	}
	
	 /**
     * Retrieves an int value from the given query's result set, column and row number
     * @param dbQuery
     * @param colName
     * @param rowNum
     * @return
     */
    public int getIntQuery(String dbQuery, String colName, int rowNum) {
        // #### Send Query and Extract Value
        ResultSet rs = retrieveData(dbQuery);
        int retVal = 0;
        int origRowNum = rowNum;
        
        try {
            // Get to the row desired
            while (rowNum > 0) {
                rs.next();
                --rowNum;
            }
            
            retVal = rs.getInt(colName);
            LOG.info("row: " + origRowNum + ", column: " + colName + ", value: " + retVal + "\n");
            
            // "Rewind" - Set SQL ResultSet cursor back to the beginning for subsequent value fetching (by row)
            rs.beforeFirst();
        }
        catch (SQLException e) {
            LOG.error(Log4jUtil.getStackTrace(e));
            closeConnection();      // Close only on exception
            throw new RuntimeException(e);
        }
        
        return retVal;
    }
    
    /**
     * Retrieves a long value from the given query's result set, column and row number
     * @param dbQuery
     * @param colName
     * @param rowNum
     * @return
     */
    public long getLongQuery(String dbQuery, String colName, int rowNum) {
        // #### Send Query and Extract Value
        ResultSet rs = retrieveData(dbQuery);
        long retVal = 0;
        int origRowNum = rowNum;
        
        try {
            // Get to the row desired
            while (rowNum > 0) {
                rs.next();
                --rowNum;
            }
            
            retVal = rs.getLong(colName);
            LOG.info("row: " + origRowNum + ", column: " + colName + ", value: " + retVal + "\n");
            
            // "Rewind" - Set SQL ResultSet cursor back to the beginning for subsequent value fetching (by row)
            rs.beforeFirst();
        }
        catch (SQLException e) {
            LOG.error(Log4jUtil.getStackTrace(e));
            closeConnection();      // Close only on exception
            throw new RuntimeException(e);
        }
        
        return retVal;
    }
    
    /**
     * Retrieves a String value from the given query's result set, column and row number
     * @param dbQuery
     * @param colName
     * @param rowNum
     * @return
     */
    public String getStringQuery(String dbQuery, String colName, int rowNum) {
        // #### Send Query and Extract Value
        ResultSet rs = retrieveData(dbQuery);
        String retVal = "";
        int origRowNum = rowNum;
        
        try {
            // Get to the row desired
            while (rowNum > 0) {
                rs.next();
                --rowNum;
            }
            
            retVal = rs.getString(colName);
            LOG.info("row: " + origRowNum + ", column: " + colName + ", value: " + retVal + "\n");
            
            // "Rewind" - Set SQL ResultSet cursor back to the beginning for subsequent value fetching (by row)
            rs.beforeFirst();
        }
        catch (SQLException e) {
            LOG.error(Log4jUtil.getStackTrace(e));
            closeConnection();      // Close only on exception
            throw new RuntimeException(e);
        }
        
        return retVal;
    }    
    
	/**
	 * Executes the given SQL statement (only INSERT, UPDATE, or DELETE ) 
	 * 
	 * @param query DBQuery of type String (Only Create,Update and Delete)
	 * @return int value states the success of operation
	 */
	public int selectQuery(String query) {
		int value=0;
		try{
			 LOG.info("Query: " + query);
			 value = statement.executeUpdate(query);			
		}catch(Exception e){
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new RuntimeException(e);
		}
		return value;
	}
	
    /**
     * Checks if given result set is empty or not
     * @param rs - Result Set
     * @return
     */
    public boolean isEmptyResultSet(ResultSet rs) {
        boolean retVal = true;
        
        try {
            retVal = !rs.isBeforeFirst();           // Check for "empty"
            LOG.info("Result Set empty: " + retVal + "\n");
        }
        catch (Exception e) {
            LOG.error(Log4jUtil.getStackTrace(e));
            closeConnection();
            throw new RuntimeException(e);
        }
        
        return retVal;
    }
    
    /**
     * Closes the Database Connection
     */
	public void closeConnection(){
		try{
		    LOG.info("++++++++ CLOSING DB Connection +++++++++++++++++");
		    
			if(statement!=null){
			    LOG.info("++++++++ statement NOT NULL, CLOSING... +++++++++++++++++");
				statement.close();
				statement = null;
			}
			if(resultSet!=null){
			    LOG.info("++++++++ resultSet NOT NULL, CLOSING... +++++++++++++++++");
				resultSet.close();
			}
			if(connection!=null){
			    LOG.info("++++++++ connection NOT NULL, CLOSING... +++++++++++++++++\n");
				connection.close();
				connection = null;
				--openConnectionCount;      // Decrement every time we close the DB connection
			}
		}catch(SQLException e){
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Utility to return the currently open connection count
	 * @return
	 */
	public int getOpenConnectionCount() {
	    LOG.info("++++++++ CURRENT Open Connection Count: " + openConnectionCount + " -- Total DB Connections: " + totalConnections);
	    return openConnectionCount;
	}
	
	/**
     * Utility to return the total connections opened so far
     * @return
     */
    public int getTotalConnectionCount() {
        LOG.info("++++++++ TOTAL DB Connections: " + totalConnections + " -- Current Connection Count: " + openConnectionCount);
        return totalConnections;
    }
}