package com.cubic.genericutils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.log4j.Logger;

import com.cubic.logutils.Log4jUtil;


public abstract class DateUtil {
	
	/** The log. */
	private static final Logger LOG = Logger.getLogger(DateUtil.class);
	
	/**
	 * Gets the date time millis.
	 * 
	 * @return the date time millis
	 */
	public static synchronized long getDateTimeMillis() {
		long millis = System.currentTimeMillis();
		return millis;
	}

	/**
	 * Gets the date format for results.
	 * 
	 * @return the date format for results
	 */
	public static synchronized  String getDateFormatForResults() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss_SSS");
		String strDate = sdf.format(date);
		return strDate;
	}
	

	/**
	 * Gets required year e.g: 0-Current year, 1-Next year,
	 * 
	 * @param number to get year (e.g: -1,0,1 etc)
	 * @return integer value is indicating the value of year
	 * @throws Throwable the throwable
	 */
	public static int getYear(int number) throws Throwable {
		int year = 0;
		try{
			Calendar cal = Calendar.getInstance();
			year = cal.get(Calendar.YEAR) + number;
		}catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new RuntimeException(e);
		}
		
		return year;
	}

	/**
	 * Verifies date format.
	 * 
	 * @param actualDate of type (String) actual date e.g: 21-11-2015
	 * @param formatToVerify of type (String) format type e.g: dd-MM-yyyy
	 * @return boolean value indicating success of the operation
	 */
	public static boolean dateFormatVerification(String actualDate, String formatToVerify) {
		boolean flag = false;

		if (actualDate.toLowerCase().contains("am")) {
			flag = formatVerify(actualDate, formatToVerify);
		} else if (actualDate.toLowerCase().contains("pm")) {
			flag = formatVerify(actualDate, formatToVerify);
		} else if (!actualDate.toLowerCase().contains("am") || !actualDate.toLowerCase().contains("pm")) {
			flag = formatVerify(actualDate, formatToVerify);
		}
		return flag;
	}

	/**
	 * formatVerify: Reusable Function to verify date format by giving actualdate.
	 * 
	 * @param actualDate of type (String)e.g: 21-11-2015
	 * @param formatToVerify of type (String) type e.g: dd-MM-yyyy
	 * @return : boolean value indicating success of the operation
	 */
	public static boolean formatVerify(String actualDate, String formatToVerify) {
		boolean flag = false;
		try {
			SimpleDateFormat sdf;
			sdf = new SimpleDateFormat(formatToVerify);
			Date date = sdf.parse(actualDate);
			String formattedDate = sdf.format(date);
			if (actualDate.equals(formattedDate)) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * getCurrentDateTime, Function to get current time in client required format.
	 * 
	 * @param dateTimeFormat of type (String), format to get date and time (e.g: dd/mm/yyyy h:mm)
	 * @return : String value of date and time in require format
	 * @throws Throwable the throwable
	 */
	public static String getCurrentDateTime(String dateTimeFormat) throws Throwable {
		String afterDateFormat = null;
		try{
			DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
			Date date = new Date();
			afterDateFormat = dateFormat.format(date);
		}catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new RuntimeException(e);
		}
		
		return afterDateFormat;
	}
	
	/**
	 * getFutureDateTime: Function to get future or past date in client required format.
	 * 
	 * @param dateTimeFormat of (String), format to get date and time (e.g: MM/dd/yyyy)
	 * @param days of (int), number to get date E.g. 1:Tomorrow date, -1:Yesterday date
	 * @return : String value of past or future date and time in require format
	 * @throws Throwable the throwable
	 */
	public static String getFutureDateTime(String dateTimeFormat, int days) throws Throwable {
		String afterDateFormat = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, days);
			Date tomorrow = calendar.getTime();
			afterDateFormat = sdf.format(tomorrow);
		}catch (Exception e) {
			LOG.error(Log4jUtil.getStackTrace(e));
			throw new RuntimeException(e);
		}
		
		return afterDateFormat;
	}
	
	/**
	 * nextOccuranceOfDay, Returns the date of the next occurrence of that day
	 * <pre>
	 * ex: if nextOccuranceOfDay("dd/MM/YYYY, "tuesday") was called and today was Wednesday then the 
	 * date returned would be today plus 6 days
	 * format
	 * </pre>
	 * 
	 * @param dateTimeFormat of (String), format to get date and time (e.g: dd/MM/yyyy)
	 * @param dayOfWeek of (String), the day of the week to find the next occurrence of
	 * @return : String value of next occurrence day
	 * @throws Throwable the throwable
	 */
	   public static String nextOccurrenceOfDay(String dateTimeFormat, String dayOfWeek) throws Throwable {
	      int dayNum=0;
	      switch (dayOfWeek.toLowerCase()){
	         case "monday":{dayNum=1;break;}
	         case "tuesday":{dayNum=2;break;}
	         case "wednesday":{dayNum=3;break;}
	         case "thursday":{dayNum=4;break;}
	         case "friday":{dayNum=5;break;}
	         case "saturday":{dayNum=6;break;}
	         case "sunday":{dayNum=7;break;}
	      }
	      SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
	      Calendar cal = Calendar.getInstance();
	      int dow = cal.get(Calendar.DAY_OF_WEEK);
	      dow--; //This is to force Monday to be day 1 rather than Sunday
	      int numDays = 7 - ((dow - dayNum) % 7 + 7) % 7;
	      cal.add(Calendar.DAY_OF_YEAR, numDays);
	      return sdf.format(cal.getTime());
	   }
	   
	/**
   	 * Provides the system current time in kk:mm format.
   	 *
   	 * @return system time in kk:mm format of type String(eg:16:10)
   	 * @throws Throwable the throwable
   	 */
	   public static String getcurrentTime() throws Throwable {
			long timeInMillis = System.currentTimeMillis();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTimeInMillis(timeInMillis);
			SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm");
			String currenttime = dateFormat.format(cal1.getTime());
			return currenttime;
		}
		
	   /**
		 * description - it converts the date formats as given by the format types
		 * it takes inputs as current format like say if user passing current date format as  "dd-MMM-yyyy", and if user wanted to change to this format --> "dd/MM/yyyy" then we can use this function
		 * @throws ParseException 
		 */
		public static String changeDateFormat(String date, String existingFormat, String formatToChange){
			String outputFormatedDate = null;
			try {
				DateFormat df1 = new SimpleDateFormat(existingFormat); // for parsing input
				DateFormat df2 = new SimpleDateFormat(formatToChange);  // for formatting output
				String inputDate = date;
				Date d = df1.parse(inputDate);
				outputFormatedDate = df2.format(d);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return outputFormatedDate;
		}
		
		/**
		 * Give the difference between two dates in number of days
		 * 
		 * @param Stardate the stardate (eg :22/06/2017)
		 * @param enddate the enddate (eg : 25/06/2017)
		 * @param dateformat the dateformat (eg:dd/mm/yyyy)
		 * @return int value indicates no.of days difference between two dates
		 */
		public static int datedifferencemethod(String Stardate, String enddate, String dateformat) {

			String dateStart = Stardate;
			String dateStop = enddate;
			// HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat(dateformat);
			Date d1 = null;
			Date d2 = null;
			int diffDays = 0;
			try {
				d1 = format.parse(dateStart);
				d2 = format.parse(dateStop);

				// in milliseconds
				int diff = (int) (d2.getTime() - d1.getTime());

				diffDays = diff / (24 * 60 * 60 * 1000);

				System.out.print(diffDays + " day, ");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return diffDays;

		}
		
		/**
		 * Difference between two dates
		 * 
		 * @param date1 First Date of type String
		 * @param date2 second Date of type String
		 * @param dateFormat Giving Format(eg : dd/mm/yyyy)
		 * @return returns the time difference value of type (long)
		 * @throws Throwable Exception
		 */
		public static long differenceBetweenTwoDates(String date1, String date2, String dateFormat) throws Throwable {
			long diffDays = 0;
			try {
				SimpleDateFormat format = new SimpleDateFormat(dateFormat);
				Date d1 = format.parse(date1);
				Date d2 = format.parse(date2);
				long diff = d2.getTime() - d1.getTime();
				diffDays = diff / (24 * 60 * 60 * 1000) + 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return diffDays;
		}
		
		/**
		 * This function will return the proper date format in such a way if the user inputs the date as 4/1/2017 (4th Jan 2017) then it converts the given date  04/01/2017 and returns this value to the user 
		 * @param date
		 */
		public static String getProperDateFormat(String date){
			String formatedDate= "";
			try {
				DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy"); // for parsing input
				DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");  // for formatting output
				String inputDate = date;
				Date d = df1.parse(inputDate);
				formatedDate = df2.format(d); // => "03/30/2016"
			} catch (Exception e) {
				// TODO: handle exception
			}
			return formatedDate;
		}

	    /**createNowMillisec()
	     * Generates the millisecond time representation of "now"
         * @return long as milliseconds
         */
        public static long createNowMillisec() {
            return (new Date()).getTime();
        }
        
        /**
         * getFormattedDate()
         * Returns a give Date formated in a given Timezone and Date Format
         * @param date - Date object to format
         * @param dateFormat - Date Format to use on Date object
         * @param timezone - Timezone to format the Date object as
         * @return Formatted Date as a string
         */
        public static String getFormattedDate(long millisec, String dateFormat, String timezone) {
            String retVal = "";
            Date date = new Date(millisec);
            
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setTimeZone(TimeZone.getTimeZone(timezone));
            sdf.setLenient(false);
            retVal = sdf.format(date);
            LOG.info("Formatted Date in (" + dateFormat + ") format and (" + timezone + ") Timezone: " + retVal);
            
            return retVal;
        }
 
        /**
         * createNowZuluDTM()
         * Generates a UTC ("Zulu") timestamp of "now"
         * @return - "Now" timestamp in UTC
         */
        public static String createNowZuluDTM() {
            // Generate Today's timestamp (UTC/GMT)
            return getFormattedDate(createNowMillisec(), GenericConstants.DATE_FORMAT_ZULU, GenericConstants.TIMEZONE_UTC);
        }		
        
        /**
         * getPastFutureDateTime: Function to get future or past date from the current date in the required format.
         * 
         * @param dateTimeFormat of (String), format to get date and time (e.g: MM/dd/yyyy)
         * @param days of (int), number to get date E.g. 1:Tomorrow date, -1:Yesterday date
         * @return : String value of past or future date and time in require format
         * @throws Throwable the throwable
         */
         public static String getPastFutureMonthFromDate(String dateTimeFormat, int months) throws Throwable {
    	 	 	String dateAfterFormat = null;
        	 
        	 	try{
                    // set the appropriate dateTimeFormat
        	 		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
                    
                    // new instance of calendar object
                    Calendar calendar = Calendar.getInstance();
                    
                    // state the decrement / increment in months for your calendar object
                    calendar.add(Calendar.MONTH, months);
                    
                    // get the current time after time removed / added
                    Date pastFutureMonthDate = calendar.getTime();
        	 	
                    // return the new date formatted as defined by your specified format
                    dateAfterFormat = sdf.format(pastFutureMonthDate); 
        	 	}catch (Exception e) {
    				LOG.error(Log4jUtil.getStackTrace(e));
    				throw new RuntimeException(e);
        		}
               
                return dateAfterFormat;
         }

         /**
          * getPastFutureDayMonthFromDate: Function to get future or past date from the current date in the required format by specifying a day then month to go back or forward in time.
          * 
          * @param dateTimeFormat of (String), format to get date and time (e.g: dd/MM/yyyy)
          * @param days of (int), number to get date E.g. 1:Tomorrow date, -1:Yesterday date
          * @param months of (int), number to get date E.g. 1:Future Month date, -1:Last month date
          * @return : String value of past or future date and time in the required format
          * @throws Throwable the throwable
          */
          public static String getPastFutureDayMonthFromDate(String dateTimeFormat, int days, int months) throws Throwable {
        	  	String dateAfterFormat = null;

        	  	try{
                    // set the appropriate dateTimeFormat
                    SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
                   
                    // new instance of calendar object
                    Calendar calendar = Calendar.getInstance();
                    
                    // state the decrement / increment in days for your calendar object
                    calendar.add(Calendar.DATE, days);
                    calendar.add(Calendar.MONTH, months);
                    
                    // get the current time after time removed / added
                    Date pastFutureDayMonthDate = calendar.getTime();
                    
                    // return the new date formatted as defined by your specified format
                    dateAfterFormat =sdf.format(pastFutureDayMonthDate);
                    
        	  	}catch (Exception e) {
    				LOG.error(Log4jUtil.getStackTrace(e));
    				throw new RuntimeException(e);
        		}
        	  
                 return dateAfterFormat;
          }        
}
