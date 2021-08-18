package com.ar.comp.util;

public class Tools {

    public static String ltrim(String s) {
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            i++;
        }
        return s.substring(i);
    }

    public static String rtrim(String s) {
        int i = s.length()-1;
        while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }
        return s.substring(0,i+1);
    }

    public static String trimStringWith(String str, String startS) {
        String a1 = str;

        int bc = a1.indexOf(startS);
        if (bc > -1) {
            bc = bc + startS.length();
            a1 = a1.substring(bc);
        }
        return a1;
    }
}
