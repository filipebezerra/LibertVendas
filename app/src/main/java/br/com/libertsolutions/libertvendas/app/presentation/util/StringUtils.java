package br.com.libertsolutions.libertvendas.app.presentation.util;

/**
 * @author Filipe Bezerra
 */
public class StringUtils {
    private StringUtils() {/* No constructor*/}

    //TODO: Refatorar nome do método para isNullOrEmpty()
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean equalsIgnoringNullOrWhitespace(String firstStr, String secondStr) {
        String firsStrIgnoring = firstStr == null ? "" : firstStr.trim();
        String secondStrIgnoring = secondStr == null ? "" : secondStr.trim();
        return firsStrIgnoring.equals(secondStrIgnoring);
    }
}
