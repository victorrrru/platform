package com.fww;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 范文武
 * @date 2018/04/27 15:36
 */
public class DateUtil implements Serializable {
    protected static final Log logger = LogFactory.getLog(DateUtil.class);
    public static final long HOUR = 3600000L;
    public static final int HOUR_SECOND = 3600;
    public static final int EIGHT_HOUR_SECOND = 28800;
    public static final int DAY_SECOND = 86400;
    public static final long DAY_MILLI_SECOND = 86400000L;
    public static final long EIGHT_HOUR_MILLI_SECOND = 28800000L;
    public static final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

    public DateUtil() {
    }

    public static void checkTimeZone() {
    }

    public static String date2String(Date date) {
        if(date == null) {
            return null;
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(date);
        }
    }

    public static Date defaultDate(Date date) {
        return date == null?new Date():date;
    }

    public static Date defaultDate(Date date, long defaultDate) {
        return date == null?new Date(defaultDate):date;
    }

    public static Date defaultDate(Date date, Date defaultDate) {
        return date == null?defaultDate:date;
    }

    public static String date2String(Date date, String formatString) {
        if(date == null) {
            return null;
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
            return simpleDateFormat.format(date);
        }
    }

    public static Date str2Date(String dateString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.parse(dateString);
        } catch (ParseException var2) {
            throw new RuntimeException("时间转化格式错误![dateString=" + dateString + "][FORMAT_STRING=" + "yyyy-MM-dd HH:mm:ss" + "]");
        }
    }

    public static Date str2Date(String dateString, Date defaultDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.parse(dateString);
        } catch (Exception var3) {
            return defaultDate;
        }
    }

    public static Date str2Date(String dateString, String formatDate, Date defaultDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
            return simpleDateFormat.parse(dateString);
        } catch (Exception var4) {
            return defaultDate;
        }
    }

    public static Date addTime(int minute) {
        long millis = System.currentTimeMillis();
        millis += (long)minute * 60L * 1000L;
        Date date = new Date(millis);
        return date;
    }

    public static Date getOnlyDate(Date date) {
        String dateStr = getDate(date);
        return toDate(dateStr + " 00:00:00");
    }

    public static Date addDate(Date date, int daynum) {
        int minute = daynum * 60 * 24;
        return addTime(date, minute);
    }

    public static Date addTime(Date startDate, int minute) {
        long millis = startDate.getTime();
        millis += (long)minute * 60L * 1000L;
        Date date = new Date(millis);
        return date;
    }

    public static Date getTime() {
        return new Date();
    }

    public static String getDate(Date date) {
        String time = getTime(date);
        return time == null?null:time.substring(0, 10);
    }

    public static String getTime(Date date) {
        if(date == null) {
            return null;
        } else {
            long millis = date.getTime();
            return DateTime.getTime(millis);
        }
    }

    public static String getTime(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date toDate(Long time) {
        return time != null && time.longValue() > 0L?new Date(time.longValue()):null;
    }

    public static long getTimestamp(Date date) {
        return date.getTime();
    }

    public static Date toDate(String datetime) {
        if(StringUtils.isEmpty(datetime)) {
            return null;
        } else {
            long time = DateTime.getTimestamp(datetime);
            if(time <= 0L) {
                (new Exception("非法日期:" + datetime)).printStackTrace();
            }

            return new Date(time);
        }
    }

    public static int getSeconds() {
        int seconds = (int)(System.currentTimeMillis() / 1000L);
        return seconds;
    }

    public static int getShortSeconds() {
        int seconds = getSeconds();
        return seconds - 1262275200;
    }

    public static int getShortSeconds(Date date) {
        int seconds = (int)(date.getTime() / 1000L);
        return seconds - 1262275200;
    }

    public static Date toLongDate(Double time) {
        return time == null?null:toLongDate(time.intValue());
    }

    public static Date toLongDate(int time) {
        if(time <= 0) {
            return null;
        } else {
            int seconds = time + 1262275200;
            return new Date((long)seconds * 1000L);
        }
    }

    public static int getSecond(Date date) {
        long time = date.getTime();
        return (int)(time / 1000L);
    }

    public static boolean isToday(Date date) {
        if(date == null) {
            return false;
        } else {
            String time = getTime(date);
            return DateTime.isToday(time);
        }
    }

    public static Date getBeforeSecond(Date date, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(13, second);
        return cal.getTime();
    }

    public static Date str2Date(String dateString, String formatString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
            return simpleDateFormat.parse(dateString);
        } catch (ParseException var3) {
            throw new RuntimeException("时间转化格式错误![dateString=" + dateString + "][FORMAT_STRING=" + formatString + "]");
        }
    }

    public static boolean before(Date date1, Date date2) {
        return !(date1 == null && date2 == null) && !(date1 == null) && (date2 == null || date1.before(date2));
    }

    public static boolean isEqualsDay(int second1, int second2) {
        int diffDayCount = getDiffDayCount(second1, second2);
        return diffDayCount == 0;
    }

    public static int getHour(int second) {
        int hour = (second + 28800) % 86400 / 3600;
        return hour;
    }

    public static int getDiffDayCount(int postSecond, int currentSecond) {
        int posttimeDay = (postSecond + 28800) / 86400;
        int currentDay = (currentSecond + 28800) / 86400;
        return currentDay - posttimeDay;
    }

    public static int getDays(String date1, String date2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;

        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception var7) {
            throw new RuntimeException(var7.getMessage(), var7);
        }

        long day = (date.getTime() - mydate.getTime()) / 86400000L;
        return (int)day;
    }

    public static int getDayCount(Date date1, Date date2) {
        int dayCount1 = DateTime.getDayCount(date1);
        int dayCount2 = DateTime.getDayCount(date2);
        return dayCount1 - dayCount2;
    }
}
