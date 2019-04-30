/**
 * DateTimeConverter.java
 * com.jfly.core.view
 * Copyright (c) 2014.
*/

package com.dripop.core.util;

import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 日期转换
 * <p>
 * format [yyyy-MM-dd]
 * format [yyyy-MM-dd HH:mm:ss]
 * format [yyyy-MM-dd HH:mm:ss.SSS]
 * @author   yangjian1004
 * @Date     Aug 4, 2014     
 */
@SuppressWarnings({ "rawtypes" })
public class DateTimeConverter implements Converter {

    private static final Logger log = LoggerFactory.getLogger(DateTimeConverter.class);
 
    private static final String DATE      = "yyyy-MM-dd";
    private static final String DATETIME  = "yyyy-MM-dd HH:mm:ss";
    private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

    public Object convert(Class type, Object value) {
        return toDate(type, value);
    }

    public static Object toDate(Class type, Object value) {
        if (value == null || "".equals(value))
            return null;
        if (value instanceof String) {
            String dateValue = value.toString().trim();
            int length = dateValue.length();
            if (type.equals(java.util.Date.class)) {
                try {
                    DateFormat formatter = null;
                    if (length <= 10) {
                        formatter = new SimpleDateFormat(DATE, new DateFormatSymbols(Locale.CHINA));
                        return formatter.parse(dateValue);
                    }
                    if (length <= 19) {
                        formatter = new SimpleDateFormat(DATETIME, new DateFormatSymbols(Locale.CHINA));
                        return formatter.parse(dateValue);
                    }
                    if (length <= 23) {
                        formatter = new SimpleDateFormat(TIMESTAMP, new DateFormatSymbols(Locale.CHINA));
                        return formatter.parse(dateValue);
                    }
                } catch (Exception e) {
                    log.error("系统异常",e);
                }
            }
        }
        return value;
    }
}