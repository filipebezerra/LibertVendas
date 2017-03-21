package br.com.libertsolutions.libertvendas.app.presentation.util;

import org.joda.time.LocalDate;

/**
 * @author Filipe Bezerra
 */
public class DateUtils {

    private DateUtils() {}

    /**
     * Gets the current local date-time in milliseconds.
     *
     * @return the milliseconds
     */
    public static long getCurrentDateInMillis() {
        return LocalDate.now().toDate().getTime();
    }

    /**
     * Gets the year from milliseconds.
     *
     * @param dateInMillis the milliseconds
     * @return the year
     */
    public static int getYear(long dateInMillis) {
        return getYear(new LocalDate(dateInMillis));
    }

    /**
     * Gets the month of the from milliseconds.
     * Important: Month value is 0-based. e.g., 0 for January
     *
     * @param dateInMillis the milliseconds
     * @return the month of the year
     */
    public static int getMonth(long dateInMillis) {
        return getMonth(new LocalDate(dateInMillis));
    }

    /**
     * Gets the day of the month from milliseconds.
     *
     * @param dateInMillis the milliseconds
     * @return the day of the month
     */
    public static int getDay(long dateInMillis) {
        return getDay(new LocalDate(dateInMillis));
    }

    /**
     * Gets the milliseconds of the date without time zone {@link LocalDate}.
     *
     * @param year the year
     * @param month the month of the year
     * @param day the day of the month
     * @return the milliseconds
     */
    public static long dateToMillis(final int year, final int month, final int day) {
        return toLocalDate(year, month, day).toDate().getTime();
    }

    /**
     * Gets the year from {@link LocalDate)}.
     *
     * @param localDate Date without time zone
     * @return the year
     */
    public static int getYear(final LocalDate localDate) {
        return localDate.getYear();
    }

    /**
     * Gets the month of the year from {@link LocalDate)}.
     * Important: Month value is 0-based. e.g., 0 for January
     *
     * @param localDate Date without time zone
     * @return the month of the year
     */
    public static int getMonth(final LocalDate localDate) {
        return localDate.getMonthOfYear() - 1;
    }

    /**
     * Gets the day of year from {@link LocalDate)}.
     *
     * @param localDate Date without time zone
     * @return the day of year
     */
    public static int getDay(final LocalDate localDate) {
        return localDate.getDayOfMonth();
    }

    /**
     * Creates a new {@link LocalDate} from year, month and day.
     *
     * @param year the year
     * @param month the month of the year
     * @param day the day of the month
     * @return the new date without time zone
     */
    public static LocalDate toLocalDate(final int year, final int month, final int day) {
        return new LocalDate(year, month + 1, day);
    }
}