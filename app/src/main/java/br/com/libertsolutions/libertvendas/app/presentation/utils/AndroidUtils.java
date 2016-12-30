package br.com.libertsolutions.libertvendas.app.presentation.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Filipe Bezerra
 */
public class AndroidUtils {

    private AndroidUtils() {/* No constructor */}

    public static void focusThenShowKeyboard(
            @NonNull final Context context, @NonNull final View view) {
        if (view.isShown()) {
            if (view.requestFocus()) {
                showKeyboard(context, view);
            }
        }
    }

    public static void showKeyboard(@NonNull final Context context, final View view) {
        view.postDelayed(() -> {
            InputMethodManager inputManager = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view.findFocus(), 0);
        }, 50);
    }

    public static void hideKeyboard(@NonNull Context context, View currentFocus) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }
}
