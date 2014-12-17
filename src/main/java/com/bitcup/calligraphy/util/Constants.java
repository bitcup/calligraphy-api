package com.bitcup.calligraphy.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author bitcup
 */
public abstract class Constants {

    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSZ";
    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(DATE_FORMAT);

}
