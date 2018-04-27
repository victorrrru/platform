package com.fww;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 范文武
 * @date 2018/04/27 15:40
 */
public class DateTime implements Serializable {
    public static final long HOUR_MILLIS = 3600000L;
    public static final long DAY_MILLIS = 86400000L;
    public static final int EIGHT_HOUR_SECOND = 28800;
    public static final int DAY_SECOND = 86400;
    public static final long EIGHT_HOUR_MILLI_SECOND = 28800000L;
    private static final SimpleDateFormat GET_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat GET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String IS_DATE_REGEX = "^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}$";
    private static final String IS_TIME_REGEX = "^[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$";
    private static final String IS_DATETIME_REGEX = "^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}(\\.[0-9]{1,3})?$";
    private static final String[] CN_WEEK_NAMES = new String[]{"天", "一", "二", "三", "四", "五", "六"};
    private static final String[] EN_WEEK_NAMES = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final SimpleDateFormat GET_INT_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public DateTime() {
    }

    public static int getDayCount() {
        long daynum = (System.currentTimeMillis() + 28800000L) / 86400000L;
        return (int)daynum;
    }

    public static int getHourCount() {
        long daynum = System.currentTimeMillis() / 3600000L;
        return (int)daynum;
    }

    public static int getDayCount(Date date) {
        long daynum = (date.getTime() + 28800000L) / 86400000L;
        return (int)daynum;
    }

    public static int getDayCount(String datetime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date;
        try {
            date = format.parse(datetime);
        } catch (ParseException var5) {
            throw new IllegalArgumentException(var5.getMessage(), var5);
        }

        long daynum = (date.getTime() + 28800000L) / 86400000L;
        return (int)daynum;
    }

    public static int getDayCount(String date1, String date2) {
        int dayCount1 = getDayCount(date1);
        int dayCount2 = getDayCount(date2);
        return dayCount1 - dayCount2;
    }

    public static String getDate() {
        return getDate(System.currentTimeMillis());
    }

    public static String getDate(int daynum) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, daynum);
        return getDate(cal.getTimeInMillis());
    }

    public static String addDate(int daynum) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, daynum);
        return getDate(cal.getTimeInMillis());
    }

    public static String addDate(String date, int daynum) {
        int[] arr = parseDatetimeToArray(date);
        int year = arr[0];
        int month = arr[1];
        int day = arr[2];
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day + daynum);
        return getDate(cal.getTimeInMillis());
    }

    public static int[] parseDatetimeToArray(String datetime) {
        int year = Integer.parseInt(datetime.substring(0, 4));
        int month = Integer.parseInt(datetime.substring(5, 7));
        int day = Integer.parseInt(datetime.substring(8, 10));
        return new int[]{year, month, day};
    }

    public static synchronized String getDate(long millis) {
        Date date = new Date();
        if(millis > 0L) {
            date.setTime(millis);
        }

        return GET_DATE_FORMAT.format(date);
    }

    public static int getHour() {
        Calendar cld = Calendar.getInstance();
        return cld.get(11);
    }

    public static int getDay() {
        Calendar cld = Calendar.getInstance();
        return cld.get(5);
    }

    public static int getMonth() {
        Calendar cld = Calendar.getInstance();
        return cld.get(2);
    }

    public static int getMinute() {
        Calendar cld = Calendar.getInstance();
        return cld.get(12);
    }

    public static String getTime() {
        return getTime(0);
    }

    public static String addTime(int minute) {
        long millis = System.currentTimeMillis();
        millis += (long)minute * 60L * 1000L;
        return getTime(millis);
    }

    public static String addTime(String time, int minute) {
        long millis = getTimestamp(time);
        millis += (long)minute * 60L * 1000L;
        return getTime(millis);
    }

    public static String getTime(int second) {
        long millis = (long)second * 1000L;
        return getTime(millis);
    }

    public static String getTime(Date date) {
        long millis = date.getTime();
        return getTime(millis);
    }

    public static String getTime(String time) {
        return time == null?null:time.substring(0, 19);
    }

    public static synchronized String getTime(long millis) {
        Date date = new Date();
        if(millis != 0L) {
            date.setTime(millis);
        }

        return GET_TIME_FORMAT.format(date);
    }

    public static long getTimestamp(String datetime) {
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(datetime.substring(0, 4));
        int month = Integer.parseInt(datetime.substring(5, 7));
        int day = Integer.parseInt(datetime.substring(8, 10));
        int hour = Integer.parseInt(datetime.substring(11, 13));
        int minute = Integer.parseInt(datetime.substring(14, 16));
        int second = Integer.parseInt(datetime.substring(17, 19));
        cal.set(year, month - 1, day, hour, minute, second);
        if(datetime.length() > 19) {
            int mill = Integer.parseInt(datetime.substring(20));
            cal.set(14, mill);
        } else {
            cal.set(14, 0);
        }

        return cal.getTimeInMillis();
    }

    public static long getTimestamp() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    public static int getUnixTimestamp() {
        long timestamp = getTimestamp();
        return (int)(timestamp / 1000L);
    }

    public static int getUnixTimestamp(String datetime) {
        long timestamp = getTimestamp(datetime);
        return (int)(timestamp / 1000L);
    }

    public static boolean isDate(String date) {
        return date != null && date.matches("^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}$");
    }

    public static boolean isTime(String time) {
        return time != null && time.matches("^[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$");
    }

    public static boolean isDateTime(String datetime) {
        return (datetime != null && datetime.length() != 0) && datetime.matches("^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}(\\.[0-9]{1,3})?$");
    }

    public static int getSecond(String datetime) {
        long time = getTimestamp(datetime);
        return (int)(time / 1000L);
    }

    public static String getGMT(String time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimestamp(time));
        Date date = cal.getTime();
        return date.toGMTString();
    }

    public static String getWeekName() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(7) - 1;
        return CN_WEEK_NAMES[day];
    }

    public static String getWeekName(String datetime) {
        Calendar cld = Calendar.getInstance();
        cld.setTimeInMillis(getTimestamp(datetime));
        int num = cld.get(7) - 1;
        return EN_WEEK_NAMES[num];
    }

    public static int getDayCountOfMonth(int monthNum) {
        Calendar cal = Calendar.getInstance();
        cal.add(2, monthNum);
        cal.set(5, 1);
        int daynum = cal.getActualMaximum(5);
        return daynum;
    }

    public static String getFirstDayOfMonth(int monthNum) {
        Calendar cal = Calendar.getInstance();
        cal.add(2, monthNum);
        cal.set(5, 1);
        return getDate(cal.getTimeInMillis());
    }

    public static String getFirstDayOfMonth(String date, int monthNum) {
        int[] arr = parseDatetimeToArray(date);
        int year = arr[0];
        int month = arr[1];
        int day = arr[2];
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        cal.add(2, monthNum);
        cal.set(5, 1);
        return getDate(cal.getTimeInMillis());
    }

    public static String getMonday(String date) {
        int[] arr = parseDatetimeToArray(date);
        int year = arr[0];
        int month = arr[1];
        int day = arr[2];
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        cal.setFirstDayOfWeek(2);
        cal.set(7, 2);
        return getDate(cal.getTimeInMillis());
    }

    public static boolean isToday(String time) {
        if(time != null && time.length() >= 10) {
            time = time.substring(0, 10);
            return getDate().equals(time);
        } else {
            return false;
        }
    }

    public static synchronized String getIntTime() {
        Date date = new Date();
        return GET_INT_TIME_FORMAT.format(date);
    }
}
