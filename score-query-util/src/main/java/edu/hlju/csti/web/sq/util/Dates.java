package edu.hlju.csti.web.sq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dates {
    public static String getDate(){
        Date date =new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}