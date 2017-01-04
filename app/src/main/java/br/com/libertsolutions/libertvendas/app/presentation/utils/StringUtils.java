package br.com.libertsolutions.libertvendas.app.presentation.utils;

/**
 * @author Filipe Bezerra
 */
public class StringUtils  {

    private StringUtils() {/* No instances */}

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean equalsIgnoringNullOrWhitespace(String firstStr, String secondStr) {
        String firsStrIgnoring = firstStr == null ? "" : firstStr.trim();
        String secondStrIgnoring = secondStr == null ? "" : secondStr.trim();
        return firsStrIgnoring.equals(secondStrIgnoring);
    }
}
