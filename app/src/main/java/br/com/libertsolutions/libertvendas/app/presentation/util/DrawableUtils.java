package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import static android.support.v4.content.ContextCompat.getColor;

/**
 * @author Filipe Bezerra
 */
public class DrawableUtils {

    private DrawableUtils() {}

    public static void changeDrawableBackground(
            @NonNull final Context context, @NonNull Drawable drawable, @ColorRes int color) {
        drawable = drawable.mutate();
        if (drawable instanceof ShapeDrawable) {
            ((ShapeDrawable)drawable).getPaint().setColor(getColor(context, color));
        } else if (drawable instanceof GradientDrawable) {
            ((GradientDrawable)drawable).setColor(getColor(context, color));
        } else if (drawable instanceof ColorDrawable) {
            ((ColorDrawable)drawable).setColor(getColor(context, color));
        }
    }
}
