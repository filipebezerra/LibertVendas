package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Filipe Bezerra
 */
public class AndroidUtils {

    private AndroidUtils() {}

    public static int dpToPx(final Context context, float dp) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(px);
    }

    public static float pxToDp(final Context context, float px) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return px * displayMetrics.density;
    }

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
