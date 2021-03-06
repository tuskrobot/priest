package com.tusk.priest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 代码操作工具
 *
 * @author alvin
 * @date 2019/3/26
 */
public class DateUtil {

    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");

    /**
     * 判断一个时间是否在另一个时间之前
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean before(String time1, String time2) {
        try {
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);

            if (dateTime1.before(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断一个时间是否在另一个时间之后
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean after(String time1, String time2) {
        try {
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);

            if (dateTime1.after(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算时间差值（单位为天）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 差值
     */
    public static int days(Date time1, Date time2) {
        try {
            int unit = 1000*60*60*24;
            int millisecond =(int)((time1.getTime() - time2.getTime())/unit);
            return millisecond < 0 ? 0 : millisecond;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 计算时间差值（单位为小时）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 差值
     */
    public static int hours(Date time1, Date time2) {
        try {
            int unit = 1000*60*60;
            int millisecond =(int)((time1.getTime() - time2.getTime())/unit);
            return millisecond < 0 ? 0 : millisecond;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算时间差值（单位为分钟）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 差值
     */
    public static int minutes(Date time1, Date time2) {
        try {
            int unit = 1000*60;
            int millisecond =(int)((time1.getTime() - time2.getTime())/unit);
            return millisecond < 0 ? 0 : millisecond;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 计算时间差值（单位为秒）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 差值
     */
    public static int seconds(Date time1, Date time2) {
        try {
            int millisecond =(int)((time1.getTime() - time2.getTime())/1000);
            return millisecond < 0 ? 0 : millisecond;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



    /**
     * 计算时间差值（单位为秒）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 差值
     */
    public static int minus(String time1, String time2) {
        try {
            Date datetime1 = TIME_FORMAT.parse(time1);
            Date datetime2 = TIME_FORMAT.parse(time2);

            long millisecond = datetime1.getTime() - datetime2.getTime();

            return Integer.valueOf(String.valueOf(millisecond / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



    /**
     * 获取年月日和小时
     *
     * @param datetime 时间（yyyy-MM-dd HH:mm:ss）
     * @return 结果
     */
    public static String getDateHour(String datetime) {
        String date = datetime.split(" ")[0];
        String hourMinuteSecond = datetime.split(" ")[1];
        String hour = hourMinuteSecond.split(":")[0];
        return date + "_" + hour;
    }

    /**
     * 获取当天日期（yyyy-MM-dd）
     *
     * @return 当天日期
     */
    public static String getTodayDate() {
        return DATE_FORMAT.format(new Date());
    }

    /**
     * 获取昨天的日期（yyyy-MM-dd）
     *
     * @return 昨天的日期
     */
    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -1);

        Date date = cal.getTime();

        return DATE_FORMAT.format(date);
    }

    /**
     * 格式化日期（yyyy-MM-dd）
     *
     * @param date Date对象
     * @return 格式化后的日期
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * 格式化时间（yyyy-MM-dd HH:mm:ss）
     *
     * @param date Date对象
     * @return 格式化后的时间
     */
    public static String formatTime(Date date) {
        return TIME_FORMAT.format(date);
    }


    /**
     * 格式化时间（yyyy-MM）
     *
     * @param date Date对象
     * @return 格式化后的时间
     */
    public static String formatMonth(Date date) {
        return MONTH_FORMAT.format(date);
    }

    /**
     * 格式化时间（yyyy）
     *
     * @return 格式化后的时间
     */
    public static String formatYear() {
        return formatYear(new Date());
    }

    public static String formatYear(Date date) {
        return YEAR_FORMAT.format(date);
    }


}
