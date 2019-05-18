package com.project.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * 时间戳转YYYY-MM-DD HH:MI:SS
     * @return
     */
    public static  String timestamp2date(long timestamp){

        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp * 1000));
        return result;
    }



}
