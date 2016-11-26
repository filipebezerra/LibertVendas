package br.com.libertsolutions.libertvendas.app.data.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Filipe Bezerra
 */
public class ApiUtils {
    private ApiUtils() {/* No constructor */}

    private static final DateFormat API_DEFAULT_DATE_FORMATTER =
            new SimpleDateFormat("YYYY-MM-DD'T'hh-mm-ss", Locale.US);

    public static String formatApiDateTime(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return API_DEFAULT_DATE_FORMATTER.format(calendar.getTime());
    }
}
