package com.orasi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
       public static boolean match(String regex, String value){
              Pattern pattern = Pattern.compile(regex);
              Matcher matcher = pattern.matcher(value);
              return matcher.matches();
       }
       
       public static boolean matchCaseInsensitive(String regex, String value){
              Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
              Matcher matcher = pattern.matcher(value);
              return matcher.matches();
       }
}