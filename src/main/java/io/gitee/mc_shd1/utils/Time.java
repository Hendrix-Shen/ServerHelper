package io.gitee.mc_shd1.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Time {
    public static String GetTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        return dateFormat.format(date);
    }
    public static Map Difftime(String StartTime, String EndTime) throws ParseException {
        SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        Date FromTime = simpleDate.parse(StartTime);
        long Start_Time = FromTime.getTime();
        Date ToTime = simpleDate.parse(EndTime);
        long End_Time = ToTime.getTime();
        Map<String, Integer> TimeDiff = Maps.newHashMap();
        TimeDiff.put("Total_Days",(int) ((End_Time - Start_Time) / (1000 * 60 * 60 * 24)));
        TimeDiff.put("Total_Hours",(int) ((End_Time - Start_Time) / (1000 * 60 * 60)));
        TimeDiff.put("Total_Minutes",(int) ((End_Time - Start_Time) / (1000 * 60)));

        return TimeDiff;
    }
}
