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
}



