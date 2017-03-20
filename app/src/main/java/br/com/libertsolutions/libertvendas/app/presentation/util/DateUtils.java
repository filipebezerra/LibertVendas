package br.com.libertsolutions.libertvendas.app.presentation.util;

import java.util.Calendar;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * @author Filipe Bezerra
 */
public class DateUtils {

    private DateUtils() {}

    public static long getCurrentDateInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static int getYear(long dateInMillis) {
        return calendar(dateInMillis).get(YEAR);
    }

    public static int getMonth(long dateInMillis) {
        return calendar(dateInMillis).get(MONTH);
    }

    public static int getDay(long dateInMillis) {
        return calendar(dateInMillis).get(Calendar.DAY_OF_MONTH);
    }

    private static Calendar calendar(final long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar;
    }
}
