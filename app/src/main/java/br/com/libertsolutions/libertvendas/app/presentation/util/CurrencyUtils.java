package br.com.libertsolutions.libertvendas.app.presentation.util;

import java.math.BigDecimal;

import static br.com.libertsolutions.libertvendas.app.presentation.util.Constants.CURRENCY_SCALE;
import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * @author Filipe Bezerra
 */
public class CurrencyUtils {

    private CurrencyUtils() {}

    public static double round(final double value) {
        BigDecimal bigValue = new BigDecimal(value);
        BigDecimal scaledValue = bigValue.setScale(CURRENCY_SCALE, ROUND_HALF_UP);
        return scaledValue.doubleValue();
    }
}
