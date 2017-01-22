package com.zsj.test;

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
         Date date2 = DateUtil.stringToDate("2017-01-22 15:20:00 ",DateUtil.YMD_DASH_WITH_TIME);
         int diff = DateUtil.secondDiff(date1,date2);
         System.out.println(diff);

    }
}
