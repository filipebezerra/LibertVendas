package br.com.libertsolutions.libertvendas.app.presentation.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import java.text.DateFormat;
import java.text.DecimalFormat;
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

    private static final PhoneNumberUtil sPhoneFormatter = PhoneNumberUtil.getInstance();

    private static final String BR_REGION_CODE = "BR";

    private static final DecimalFormat sCnpjFormatter = new DecimalFormat("00000000000000");

    private static final DecimalFormat sCpfFormatter = new DecimalFormat("00000000000");

    private static final NumberFormat sCurrencyFormatter =
            NumberFormat.getCurrencyInstance(PT_BR_DEFAULT_LOCALE);

    private static final DateFormat sDateFormatter
            = new SimpleDateFormat("dd/MM/yyyy", PT_BR_DEFAULT_LOCALE);

    static {
        sCurrencyFormatter.setMaximumFractionDigits(2);
        sCurrencyFormatter.setMinimumFractionDigits(2);
    }

    public static String formatPhoneNumber(String phoneNumberText) {
        try {
            Phonenumber.PhoneNumber phoneNumber
                    = sPhoneFormatter.parse(phoneNumberText, BR_REGION_CODE);
            return sPhoneFormatter.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        } catch (NumberParseException e) {
            return phoneNumberText;
        }
    }

    public static String formatCPForCPNJ(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return value;
        }

        final int valueSize = value.length();
        if (valueSize > 14) {
            return value;
        }

        boolean isCPF = valueSize < 12;
        DecimalFormat formatDecimal = isCPF ? sCpfFormatter : sCnpjFormatter;

        final String stringNumber = formatDecimal.format(Long.valueOf(value));

        return isCPF ? stringNumber.replaceAll(
                "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1.$2.$3-$4")
                : stringNumber.replaceAll(
                        "([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})",
                        "$1.$2.$3/$4-$5");
    }

    public static String formatAsDinheiro(double value) {
        return sCurrencyFormatter.format(value);
    }

    public static String convertMillisecondsToDateAsString(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return sDateFormatter.format(calendar.getTime());
    }

    public static String formatMillisecondsToDateText(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return sDateFormatter.format(calendar.getTime());
    }
}
