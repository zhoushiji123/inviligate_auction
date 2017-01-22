package com.zsj.test;

import com.sun.org.apache.bcel.internal.generic.DADD;
import com.zsj.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zsj on 2017/1/22.
 */
public class Test {

    public static void main(String[] args) {

         Calendar calendar = Calendar.getInstance();
//        Date d1 = DateUtil.stringToDate("2017-01-22 15:15:00",DateUtil.YMD_DASH_WITH_TIME);
//        Date d2 = DateUtil.stringToDate("2017-01-22 15:15:30",DateUtil.YMD_DASH_WITH_TIME);
//        long diff = d2.getTime() - d1.getTime();
//        System.out.println(diff/1000L);
         Date date1 = DateUtil.stringToDate("2017-01-22 15:30:00",DateUtil.YMD_DASH_WITH_TIME);
         String unix = DateUtil.dateToUnix(date1);
         System.out.println(unix);
         String dateStr = DateUtil.unixToDate(unix);
        System.out.println(dateStr);

    }
}
