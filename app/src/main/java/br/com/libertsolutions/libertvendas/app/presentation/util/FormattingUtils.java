package br.com.libertsolutions.libertvendas.app.presentation.util;

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
}
