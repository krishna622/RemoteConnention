package com.kmd.remoterdp.utils;

/**
 * Created by FARHAN on 2/1/2015.
 */
public class StringUtil {
    public static boolean isNullOrEmpty(final String pStr) {
        return pStr == null || pStr.trim().length() == 0 || pStr.trim().equalsIgnoreCase("null");
    }
}
