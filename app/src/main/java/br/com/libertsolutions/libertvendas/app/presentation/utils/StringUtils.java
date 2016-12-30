package br.com.libertsolutions.libertvendas.app.presentation.utils;

/**
 * @author Filipe Bezerra
 */
public class StringUtils  {

    private StringUtils() {/* No instances */}

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
