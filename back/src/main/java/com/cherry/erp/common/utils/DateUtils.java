package com.cherry.erp.common.utils;


import org.apache.commons.validator.routines.DateValidator;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;

public final class DateUtils {

    private DateUtils() {
    }

    public static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String FORMAT_DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
    public static final String FORMAT_DDMMYYYY = "ddMMyyyy";
    public static final String FORMAT_DDMMYY = "ddMMyy";
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String FORMAT_YYYYMMDD_AVEC_TIRET = "yyyy-MM-dd";
    public static final String FORMAT_YYYYMMDD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
    private static final Long DAY_IN_MILLISECOND = 86400000L;

    public static final String INCORRECT_DATE_FORMAT = "la Date {0} n'est pas dans le format correct.";

    public static String dateToStringByFormat(Date date, String format) {
        if (date == null) {
            return "";
        }
        final DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar xMLGregorianCalendar) {
        if (xMLGregorianCalendar != null) {
            return xMLGregorianCalendar.toGregorianCalendar().getTime();
        } else {
            return null;
        }
    }

    public static Date stringToDate(String dateString) throws ParseException {
        final DateFormat formatter = new SimpleDateFormat(FORMAT_YYYYMMDD_T_HH_MM_SS);
        return dateString != null ? formatter.parse(dateString) : null;
    }

    public static boolean isDDMMYYYYDateValid(String dateToValidate) {
        return (DateValidator.getInstance().validate(dateToValidate, FORMAT_DD_MM_YYYY) != null);
    }

    public static boolean isDDMMYYYYHHmmDateValid(String dateToValidate) {
        return (DateValidator.getInstance().validate(dateToValidate, FORMAT_DD_MM_YYYY_HH_MM) != null);
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        final DateFormat formatter = new SimpleDateFormat(FORMAT_DD_MM_YYYY);
        return formatter.format(date);
    }


    /**
     * Use it after validating the date using isDDMMYYYYDateValid
     *
     * @param date
     * @return date or null
     */
    public static Date stringToDateNoException(String date) {
        if (date == null) {
            return null;
        }
        final DateFormat formatter = new SimpleDateFormat(FORMAT_DD_MM_YYYY);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static Date stringToDateNoException(String date, String format) {
        if (date == null) {
            return null;
        }
        final DateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Integer getDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startDateCalendar = getInstance();
        startDateCalendar.setTime(startDate);

        Calendar endDateCalendar = getInstance();
        endDateCalendar.setTime(endDate);

        Long differenceInMilliSeconds = endDateCalendar.getTimeInMillis() - startDateCalendar.getTimeInMillis();

        return (int) (differenceInMilliSeconds / DAY_IN_MILLISECOND);
    }

    public static Date getDateWithoutTime(Date dateToConvert) {

        if (dateToConvert == null) {
            return null;
        }

        Calendar calendar = getInstance();
        calendar.setTime(dateToConvert);

        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);

        return calendar.getTime();
    }

    public static boolean isYYYYMMDDSansTiretDateValid(String dateToValidate) {
        return (DateValidator.getInstance().validate(dateToValidate, FORMAT_YYYYMMDD) != null);
    }

    public static boolean isYYYYMMDDAvecTiretDateValid(String dateToValidate) {
        return (DateValidator.getInstance().validate(dateToValidate, FORMAT_YYYYMMDD_AVEC_TIRET) != null);
    }

    public static Date stringToDate(String dateToConvert, String format) throws ParseException {
        final DateFormat formatter = new SimpleDateFormat(format);
        return dateToConvert != null ? formatter.parse(dateToConvert) : null;
    }

    public static String getSunday(Integer i) {
        Calendar c = Calendar.getInstance(Locale.FRANCE);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        DateFormat df = new SimpleDateFormat(FORMAT_YYYYMMDD_AVEC_TIRET);
        c.add(Calendar.DATE, i * Calendar.DAY_OF_WEEK);
        return df.format(c.getTime());
    }

    public static Date addDaysToDate(Date dateInitiale, int amount) {
        if (dateInitiale == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateInitiale);
        cal.add(Calendar.DAY_OF_YEAR, amount);
        return cal.getTime();
    }
}
