package br.com.libertsolutions.libertvendas.app.presentation.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.joda.time.LocalDate;

import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.BR_REGION_CODE;
import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.DATE_FORMAT;
import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.PT_BR_DEFAULT_LOCALE;
import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
import static com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance;
import static java.text.NumberFormat.getCurrencyInstance;
import static java.text.NumberFormat.getNumberInstance;

/**
 * @author Filipe Bezerra
 */
public class FormattingUtils {

    private FormattingUtils() {/* No constructor */}

    private static final DecimalFormat sCnpjFormatter = new DecimalFormat("00000000000000");

    private static final DecimalFormat sCpfFormatter = new DecimalFormat("00000000000");

    private static final PhoneNumberUtil sPhoneFormatter = getInstance();

    private static final NumberFormat sCurrencyFormatter = getCurrencyInstance(PT_BR_DEFAULT_LOCALE);

    private static final NumberFormat sNumberFormatter = getNumberInstance(PT_BR_DEFAULT_LOCALE);

    private static final DateFormat sDateFormatter
            = new SimpleDateFormat(DATE_FORMAT, PT_BR_DEFAULT_LOCALE);

    static {
        sCurrencyFormatter.setMaximumFractionDigits(2);
        sCurrencyFormatter.setMinimumFractionDigits(2);
    }

    public static String formatCpforCnpj(String value) {
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

    public static String formatPhoneNumber(String phoneNumberText) {
        try {
            PhoneNumber phoneNumber = sPhoneFormatter.parse(phoneNumberText, BR_REGION_CODE);
            return sPhoneFormatter.format(phoneNumber, NATIONAL);
        } catch (NumberParseException e) {
            return phoneNumberText;
        }
    }

    public static String formatAsCurrency(double value) {
        return sCurrencyFormatter.format(value);
    }

    public static String formatAsNumber(double value) {
        return sNumberFormatter.format(value);
    }

    public static String formatAsDate(long dateInMillis) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(dateInMillis);
        return formatAsDate(date);
    }

    public static String formatAsDate(Calendar date) {
        return sDateFormatter.format(date.getTime());
    }

    public static String formatAsDate(LocalDate localDate) {
        return sDateFormatter.format(localDate.toDate());
    }
}
