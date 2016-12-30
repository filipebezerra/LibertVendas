package br.com.libertsolutions.libertvendas.app.presentation.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

    private static final DecimalFormat CNPJ_FORMATTER = new DecimalFormat("00000000000000");

    private static final DecimalFormat CPF_FORMATTER = new DecimalFormat("00000000000");

    private static final String BR_REGION_CODE = "BR";

    private static final PhoneNumberUtil PHONE_FORMATTER = PhoneNumberUtil.getInstance();

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

    public static String formatCPForCPNJ(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return value;
        }

        final int valueSize = value.length();
        if (valueSize > 14) {
            return value;
        }

        boolean isCPF = valueSize < 12;
        DecimalFormat formatDecimal = isCPF ? CPF_FORMATTER : CNPJ_FORMATTER;

        final String stringNumber = formatDecimal.format(Long.valueOf(value));

        return isCPF ? stringNumber.replaceAll(
                "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1.$2.$3-$4")
                : stringNumber.replaceAll(
                        "([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})",
                        "$1.$2.$3/$4-$5");
    }

    public static String formatPhoneNumber(String phoneNumberText) {
        try {
            Phonenumber.PhoneNumber phoneNumber
                    = PHONE_FORMATTER.parse(phoneNumberText, BR_REGION_CODE);
            return PHONE_FORMATTER.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        } catch (NumberParseException e) {
            return phoneNumberText;
        }
    }
}
