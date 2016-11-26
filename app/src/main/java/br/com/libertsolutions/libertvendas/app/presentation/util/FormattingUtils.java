package br.com.libertsolutions.libertvendas.app.presentation.util;

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

    private static final NumberFormat PT_BR_CURRENCY_FORMATTER =
            NumberFormat.getCurrencyInstance(PT_BR_DEFAULT_LOCALE);

    private static final NumberFormat PT_BR_NUMBER_FORMATTER =
            NumberFormat.getNumberInstance(PT_BR_DEFAULT_LOCALE);

    private static final DateFormat DATE_FORMATTER_AS_DD_MM_YYYY =
            new SimpleDateFormat("dd/MM/yyyy", PT_BR_DEFAULT_LOCALE);

    private static final PhoneNumberUtil PHONE_UTIL = PhoneNumberUtil.getInstance();

    private static final String BR_REGION_CODE = "BR";

    private static final DecimalFormat CNPJ_NFORMAT = new DecimalFormat("00000000000000");

    private static final DecimalFormat CPF_NFORMAT = new DecimalFormat("00000000000");

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

    public static String convertMillisecondsToDateAsString(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return DATE_FORMATTER_AS_DD_MM_YYYY.format(calendar.getTime());
    }

    public static String formatCPForCPNJ(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }

        final int valueSize = value.length();
        if (valueSize > 14) {
            return value;
        }

        boolean isCPF = valueSize < 12;
        DecimalFormat formatDecimal = isCPF ? CPF_NFORMAT : CNPJ_NFORMAT;

        final String stringNumber = formatDecimal.format(Long.valueOf(value));

        return isCPF ? stringNumber.replaceAll(
                "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1.$2.$3-$4")
                : stringNumber.replaceAll(
                        "([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})",
                        "$1.$2.$3/$4-$5");
    }
}
