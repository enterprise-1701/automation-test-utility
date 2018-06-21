package com.cubic.genericutils;
import com.cubic.logutils.Log4jUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;


public class IntegerUtil {
    private static final Logger LOG = Logger.getLogger(FileUtil.class.getName());

    /**
     * Gets randomInteger with no parameter    //br
     *
     * @param
     * @return random integer value
     */
    public static int getRandomInteger() {
        int randomInt = 0;
        try {
            randomInt = RandomUtils.nextInt();
        } catch (Exception e) {
            LOG.error(Log4jUtil.getStackTrace(e));
        }
        return randomInt;
    }

    /**
     * Gets random integer of given actual value   //br
     *
     * @param minValue of (int),
     * @param maxValue of (int)
     * @return Integer value
     */
    public static int getRandomInteger(int minValue, int maxValue) {
        int randomInt = 0;
        try {
            randomInt = RandomUtils.nextInt(minValue, maxValue);
        } catch (Exception e) {
            LOG.error(Log4jUtil.getStackTrace(e));
        }
        return randomInt;
    }

    /**
     * Gets random integer of given actual context   //br
     *
     * @param minValue of (double),
     * @param maxValue of (double)
     * @return double value
     */
    public static double getRandomDouble(double minValue, double maxValue) {
        double randomDouble = 0.0;
        try {
            randomDouble = RandomUtils.nextDouble(minValue, maxValue);
        } catch (Exception e) {
            LOG.error(Log4jUtil.getStackTrace(e));
        }
        return randomDouble;
    }



    /**
     * Gets randomInteger (user defined)of given actual context   //br
     *
     * @param chrlen Number of characters
     * @return Integer value
     */

    public static int getRandomInteger(int chrlen) throws Exception {
        String minval = "1";
        String maxval = "9";
        int min = 0;
        int max = 0;
        int randomInt = 0;
        if (chrlen > 1) {
            int i;
            for(i=0; i<chrlen-1; i++) {
                minval = minval+'0';
                maxval = maxval+'9';
            }
            min = Integer.parseInt(minval);
            max = Integer.parseInt(maxval);
        }

        try {
            randomInt = RandomUtils.nextInt(min,max);
        }
        catch (Exception e) {
        LOG.error(Log4jUtil.getStackTrace(e));
        }
        return randomInt;
    }


    /**
     * Gets randomDouble(user defined)of given actual context   //br
     *
     * @param chrlen Number of characters
     * @return randomDouble value
     */

     public static Double getRandomDouble(int chrlen) throws Exception
     {
        String minval = "1";
        String maxval = "9";
        int min = 0;
        int max = 0;
        Double randomDouble = 0.0;
        double[] gaussians  = new double[10];
        if (chrlen >1){
             for (int i=0;i<chrlen-1;i++)
            {
           minval = minval+'0';
           maxval = maxval+'9';
             }
             min = Integer.parseInt(minval);
             max = Integer.parseInt(maxval);
              }
       try{
            randomDouble = RandomUtils.nextDouble(min,max);
       }
       catch (Exception e) {
           LOG.error(Log4jUtil.getStackTrace(e));
       }
       return randomDouble;
       }
    }




