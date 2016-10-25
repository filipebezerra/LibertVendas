package br.com.libertsolutions.libertvendas.app.presentation.util;

/**
 * @author Filipe Bezerra
 */
public class StringUtils {
    private StringUtils() {/* No constructor*/}

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
