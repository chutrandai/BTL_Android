package com.example.btl_pro;

import java.util.ArrayList;

public class commonUtil {
    public static String userName;
    public static boolean isNullOrEmpTy(String str) {
        if (str == null || str == "") {
            return true;
        }
        return false;
    }
    public static String regexStr = "^[0-9]$";
    public static boolean validateNumber(String number) {
        if (number.matches(regexStr) == false) {
            return false;
        }
        return true;
    }
}
