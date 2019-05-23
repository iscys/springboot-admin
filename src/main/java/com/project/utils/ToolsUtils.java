package com.project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolsUtils {

    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;

        }
        return flag;
    }


    public static void main(String[] args) {
        System.out.println(checkMobileNumber("wqeqweds1"));
    }

}
