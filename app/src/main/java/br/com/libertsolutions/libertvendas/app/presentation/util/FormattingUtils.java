package br.com.libertsolutions.libertvendas.app.presentation.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Filipe Bezerra
 */

public class FormattingUtils {
    private FormattingUtils() {/* No constructor */}

    public static final Locale PT_BR_DEFAULT_LOCALE = new Locale("pt", "BR");

    private static final NumberFormat PT_BR_CURRENCY_FORMATTER =
            NumberFormat.getCurrencyInstance(PT_BR_DEFAULT_LOCALE);

    private static final NumberFormat PT_BR_NUMBER_FORMATTER =
            NumberFormat.getNumberInstance(PT_BR_DEFAULT_LOCALE);

    private static final DateFormat DATE_FORMATTER_AS_DD_MM_YYYY =
            new SimpleDateFormat("dd/MM/yyyy", PT_BR_DEFAULT_LOCALE);

    private static final PhoneNumberUtil PHONE_UTIL = PhoneNumberUtil.getInstance();

    private static final String BR_REGION_CODE = "BR";

    static {
        PT_BR_CURRENCY_FORMATTER.setMaximumFractionDigits(2);
        PT_BR_CURRENCY_FORMATTER.setMinimumFractionDigits(2);
        PT_BR_NUMBER_FORMATTER.setMaximumFractionDigits(2);
        PT_BR_NUMBER_FORMATTER.setMinimumFractionDigits(2);
    }

    public static String formatAsDinheiro(double value) {
        return PT_BR_CURRENCY_FORMATTER.format(value);
    }

    public static String formatAsQuantidade(double value) {
        return PT_BR_NUMBER_FORMATTER.format(value);
    }

    public static String formatMillisecondsToDateText(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return DATE_FORMATTER_AS_DD_MM_YYYY.format(calendar.getTime());
    }

    public static String formatPhoneNumber(String phoneNumberText) {
        try {
            Phonenumber.PhoneNumber phoneNumber
                    = PHONE_UTIL.parse(phoneNumberText, BR_REGION_CODE);
            return PHONE_UTIL.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        } catch (NumberParseException e) {
            return phoneNumberText;
        }
    }
}
