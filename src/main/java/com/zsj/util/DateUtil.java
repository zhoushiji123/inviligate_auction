package com.zsj.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zsj on 2017/1/22.
 */
public class DateUtil {


    public static final String YMD = "yyyyMMdd";
    public static final String YMD_SLASH = "yyyy/MM/dd";

    public static final String YMD_DASH_WITH_TIME_SIMPLE = "yyyyMMddHHmmss";
    public static final String YDM_SLASH = "yyyy/dd/MM";

    public static final String YM_DASH = "yyyy年MM月";

    public static final String HM = "HHmm";
    public static final String HM_COLON = "HH:mm";
    public static final String HMS = "HHmmss";
    public static final String HMS_COLON = "HH:mm:ss";

    public static final String US_DATE = "EEE MMM dd HH:mm:ss Z yyyy";
    public static final long DAY = 24 * 60 * 60 * 1000L;

    private static final Map<String, DateFormat> DFS = new HashMap<String, DateFormat>();


    public static final String YMD_DASH_WITH_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_DASH = "yyyy-MM-dd";


    /**
     * string转date
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date stringToDate (String dateStr,String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null ;
        try {
            date = simpleDateFormat.parse(dateStr);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return  date;
    }


    /**
     * date转string
     * @param date
     * @param pattern
     * @return
     */
    public static  String dateToString (Date date,String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }


    /**
     * 获得今天日期
     * @return
     */
    public static String getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YMD_DASH);
        return simpleDateFormat.format(date);
    }


    /**
     * 获得当前时间
     * @return
     */
    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YMD_DASH_WITH_TIME);
        return simpleDateFormat.format(date);
    }


    /**
     * 日期年数加减
     * @param date
     * @param value
     * @return
     */
    public static Date addYear(Date date,int value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,value);
        return  calendar.getTime();
    }


    /**
     * 日期月份加减
     * @param date
     * @param value
     * @return
     */
    public static Date addMonth(Date date,int value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,value);
        return  calendar.getTime();
    }


    /**
     * 日期天数加减
     * @param date
     * @param value
     * @return
     */
    public static Date addDay(Date date,int value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,value);
        return  calendar.getTime();
    }


    /**
     * 日期小时加减
     * @param date
     * @param value
     * @return
     */
    public static Date addHour(Date date,int value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,value);
        return  calendar.getTime();
    }

    /**
     * 分钟加减
     * @param date
     * @param value
     * @return
     */
    public static Date addMinute(Date date,int value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,value);
        return  calendar.getTime();
    }


    /**
     * 秒数加减
     * @param date
     * @param value
     * @return
     */
    public static Date addSecond(Date date,int value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND,value);
        return  calendar.getTime();
    }




    /**
     * 计算两个日期的相差天数
     * @param date1
     * @param date2
     * @return
     */
    public static int daysDiff(Date date1,Date date2){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return calendar1.get(Calendar.DAY_OF_YEAR) - calendar2.get(Calendar.DAY_OF_YEAR);
    }



    /**
     * 计算相差小时数
     * @param date1
     * @param date2
     * @return
     */
    public static int hourDiff(Date date1 , Date date2){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        Long diffSecond =  (date1.getTime() - date2.getTime())/1000L ;
        Long diffHour = diffSecond/3600 ;
        return diffHour.intValue();
    }


    /**
     * 计算相差分钟数
     * @param date1
     * @param date2
     * @return
     */
    public static int minuteDiff(Date date1 , Date date2){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        Long diffSecond =  (date1.getTime() - date2.getTime())/1000L ;
        Long diffMinute = diffSecond/60 ;
        return diffMinute.intValue();
    }


    /**
     * 计算相差秒数
     * @param date1
     * @param date2
     * @return
     */
    public static int secondDiff(Date date1 , Date date2){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        Long diffSecond =  (date1.getTime() - date2.getTime())/1000L ;
        return diffSecond.intValue();
    }


    /**
     * 普通日期转unix时间搓
     * @param date
     * @return
     */
    public static String dateToUnix(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long mills = calendar.getTimeInMillis()/1000L;
        return String.valueOf(mills);
    }


    /**
     * unix时间搓转普通日期
     * @param unixTime
     * @return
     */
    public static String unixToDate(String unixTime){
        long mills = Long.parseLong(unixTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills*1000L);
        Date date = calendar.getTime();
        return DateUtil.dateToString(date,YMD_DASH_WITH_TIME);
    }

}
