package br.com.libertsolutions.libertvendas.app.presentation.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.BR_REGION_CODE;
import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.CURRENCY_SCALE;
import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.DATE_FORMAT;
import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.DATE_TIME_FORMAT;
import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.PERCENT_SCALE;
import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.PT_BR_DEFAULT_LOCALE;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.dateToMillis;
import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
import static com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance;
import static java.text.NumberFormat.getCurrencyInstance;
import static java.text.NumberFormat.getNumberInstance;
import static java.text.NumberFormat.getPercentInstance;

/**
 * @author Filipe Bezerra
 */
public class FormattingUtils {

    private FormattingUtils() {/* No constructor */}

    private static final DecimalFormat sCnpjFormatter = new DecimalFormat("00000000000000");

    private static final DecimalFormat sCpfFormatter = new DecimalFormat("00000000000");

    private static final PhoneNumberUtil sPhoneFormatter = getInstance();

    private static final NumberFormat sCurrencyFormatter
            = getCurrencyInstance(PT_BR_DEFAULT_LOCALE);

    private static final NumberFormat sNumberFormatter
            = getNumberInstance(PT_BR_DEFAULT_LOCALE);

    private static final NumberFormat percentFormatter
            = getPercentInstance(PT_BR_DEFAULT_LOCALE);

    private static final DateTimeFormatter sDateTimeFormatter
            = DateTimeFormat.forPattern(DATE_TIME_FORMAT);

    private static final DateTimeFormatter sDateFormatter
            = DateTimeFormat.forPattern(DATE_FORMAT);

    static {
        sCurrencyFormatter.setMinimumFractionDigits(CURRENCY_SCALE);
        sCurrencyFormatter.setMaximumFractionDigits(CURRENCY_SCALE);
        percentFormatter.setMinimumFractionDigits(PERCENT_SCALE);
        percentFormatter.setMaximumFractionDigits(PERCENT_SCALE);
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

    public static String formatAsDateTime(long dateTimeInMillis) {
        return sDateTimeFormatter.print(dateTimeInMillis);
    }

    public static String formatAsDateTime(Calendar date) {
        return formatAsDateTime(date.getTimeInMillis());
    }

    public static String formatAsDateTime(LocalDate localDate) {
        return formatAsDateTime(dateToMillis(localDate));
    }

    public static String formatAsDate(LocalDate localDate) {
        return sDateFormatter.print(dateToMillis(localDate));
    }

    public static String formatAsDate(DateTime dateTime) {
        return sDateFormatter.print(dateTime.getMillis());
    }

    public static String formatAsISODateTime(long dateTimeInMillis) {
        return ISODateTimeFormat.dateTime().print(dateTimeInMillis);
    }

    /**
     * Formata um valor numérico que não esteja como porcentual em um valor representativo de porcentagem.
     *
     * Este método não é apropriado para valores que estejam como porcentual, por exemplo: 0.45.
     *
     * @param notInPercentValue valor numérico
     * @return valor representativo de porcentagem
     */
    public static String formatAsPercent(double notInPercentValue) {
        return percentFormatter.format(notInPercentValue / 100);
    }
}
