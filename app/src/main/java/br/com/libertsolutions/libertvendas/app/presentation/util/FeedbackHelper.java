package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.Snackbar.Callback;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import br.com.libertsolutions.libertvendas.app.R;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;

/**
 * @author Filipe Bezerra
 */
public class FeedbackHelper {
    private FeedbackHelper() {/* No constructor */}

    private static final Callback NO_SNACKBAR_CALLBACK = null;

    private static final SnackbarAction NO_SNACKBAR_ACTION = null;
    private static final String NO_SNACKBAR_ACTION_TEXT = null;
    private static final OnClickListener NO_SNACKBAR_ACTION_LISTENER = null;

    private static final int NO_DIALOG_TITLE = -1;
    private static final DialogCallback NO_DIALOG_CALLBACK = null;

    private static Context applicationContext(Context context) {
        return context.getApplicationContext();
    }

    private static String stringResource(@NonNull Context context, @StringRes int res) {
        return context.getString(res);
    }

    private static class SnackbarAction {
        private Pair<String, OnClickListener> mAction;

        private SnackbarAction(String actionText, OnClickListener actionListener) {
            mAction = Pair.create(actionText, actionListener);
        }

        public static SnackbarAction from(String actionText, OnClickListener actionListener) {
            return new SnackbarAction(actionText, actionListener);
        }

        public String actionText() {
            return mAction.first;
        }

        public OnClickListener actionListener() {
            return mAction.second;
        }
    }

    public static void snackbar(@NonNull View inView, @NonNull String message) {
        snackbar(inView, message, false);
    }

    public static void snackbar(
            @NonNull View inView, @NonNull String message, boolean showQuickly) {
        makeSnackbar(inView, message, showQuickly, NO_SNACKBAR_ACTION, NO_SNACKBAR_CALLBACK)
                .show();
    }

    public static void snackbar(@NonNull View inView, @NonNull String message,
            boolean showQuickly, @NonNull Callback callback) {
        makeSnackbar(inView, message, showQuickly, NO_SNACKBAR_ACTION, callback)
                .show();
    }

    public static void snackbar(
            @NonNull View inView, @NonNull String message, boolean showQuickly,
            @NonNull String actionText, @NonNull OnClickListener actionListener) {
        makeSnackbar(inView, message, showQuickly, snackbarAction(actionText, actionListener),
                NO_SNACKBAR_CALLBACK)
                .show();
    }

    private static SnackbarAction snackbarAction(String actionText,
            OnClickListener actionListener) {
        return SnackbarAction.from(actionText, actionListener);
    }

    private static Snackbar makeSnackbar(
            @NonNull View inView, @NonNull String message, boolean showQuickly,
            @Nullable SnackbarAction action, @Nullable Callback callback) {

        Snackbar snackbar = Snackbar.make(inView, message,
                showQuickly ? Snackbar.LENGTH_SHORT : Snackbar.LENGTH_LONG);
        final View snackbarView = snackbar.getView();

        snackbarView.setBackgroundColor(
                ContextCompat.getColor(inView.getContext(), R.color.color_accent));

        final TextView snackbarText = (TextView) snackbarView
                .findViewById(android.support.design.R.id.snackbar_text);

        snackbarText.setTextColor(
                ContextCompat.getColor(inView.getContext(), android.R.color.white));

        if (action != NO_SNACKBAR_ACTION) {
            snackbar.setAction(action.actionText(), action.actionListener());
        } else {
            snackbar.setAction(NO_SNACKBAR_ACTION_TEXT, NO_SNACKBAR_ACTION_LISTENER);
        }

        if (callback != NO_SNACKBAR_CALLBACK) {
            snackbar.setCallback(callback);
        } else {
            snackbar.setCallback(NO_SNACKBAR_CALLBACK);
        }

        return snackbar;
    }

    public static void toast(
            @NonNull Context inContext, @StringRes int message, boolean showQuickly) {
        toast(inContext, stringResource(inContext, message), showQuickly);
    }

    public static void toast(
            @NonNull Context inContext, @NonNull String message, boolean showQuickly) {
        makeToast(inContext, message, showQuickly)
                .show();
    }

    private static Toast makeToast(
            @NonNull Context inContext, @NonNull String message, boolean showQuickly) {
        return Toast.makeText(applicationContext(inContext), message,
                showQuickly ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
    }

    public static void showRetryDialogMessage(
            @NonNull Context context, @StringRes int messageRes,
            @Nullable SingleButtonCallback cancelCallback,
            @Nullable SingleButtonCallback retryCallback) {
        makeDialog(context, NO_DIALOG_TITLE, stringResource(context, messageRes),
                DialogCallback.withoutCallback(),
                DialogCallback.cancelCallback(cancelCallback),
                DialogCallback.retryCallback(retryCallback))
                .show();
    }

    public static void showOfflineMessage(@NonNull Context context,
            @Nullable SingleButtonCallback cancelCallback,
            @Nullable SingleButtonCallback retryCallback) {
        makeDialog(context, R.string.offline_message_title,
                stringResource(context, R.string.offline_message),
                DialogCallback.withoutCallback(),
                DialogCallback.cancelCallback(cancelCallback),
                DialogCallback.retryCallback(retryCallback))
                .show();
    }

    public static void showQuestionDialog(
            @NonNull Context context, @StringRes int questionRes,
            @NonNull SingleButtonCallback yesCallback,
            @Nullable SingleButtonCallback noCallback) {
        makeDialog(context, NO_DIALOG_TITLE, stringResource(context, questionRes),
                DialogCallback.yesCallback(yesCallback),
                DialogCallback.noCallback(noCallback),
                DialogCallback.withoutCallback())
                .show();
    }

    public static void showMessageDialog(@NonNull Context context, @StringRes int questionRes) {
        makeDialog(context, NO_DIALOG_TITLE, stringResource(context, questionRes),
                DialogCallback.withoutCallback(),
                DialogCallback.withoutCallback(),
                DialogCallback.withoutCallback())
                .show();
    }

    public static void showMessageDialog(@NonNull Context context, @NonNull String questionRes) {
        makeDialog(context, NO_DIALOG_TITLE, questionRes,
                DialogCallback.withoutCallback(),
                DialogCallback.withoutCallback(),
                DialogCallback.withoutCallback())
                .show();
    }

    private static class DialogCallback {
        private Pair<Integer, SingleButtonCallback> mCallback;

        private DialogCallback(int buttonText, SingleButtonCallback buttonCallback) {
            mCallback = Pair.create(buttonText, buttonCallback);
        }

        public static DialogCallback withoutCallback() {
            return NO_DIALOG_CALLBACK;
        }

        public static DialogCallback cancelCallback(SingleButtonCallback cancelCallback) {
            return from(android.R.string.cancel, cancelCallback);
        }

        public static DialogCallback retryCallback(SingleButtonCallback retryCallback) {
            return from(R.string.retry, retryCallback);
        }

        public static DialogCallback yesCallback(SingleButtonCallback yesCallback) {
            return from(android.R.string.yes, yesCallback);
        }

        public static DialogCallback noCallback(SingleButtonCallback noCallback) {
            return from(android.R.string.no, noCallback);
        }

        public static DialogCallback from(
                @StringRes int buttonText, SingleButtonCallback buttonCallback) {
            return new DialogCallback(buttonText, buttonCallback);
        }

        @StringRes
        public int buttonText() {
            return mCallback.first;
        }

        public SingleButtonCallback buttonCallback() {
            return mCallback.second;
        }
    }

    private static MaterialDialog makeDialog(
            @NonNull Context context, @StringRes int titleRes, @NonNull String message,
            @Nullable DialogCallback positiveCallback,
            @Nullable DialogCallback negativeCallback,
            @Nullable DialogCallback neutralCallback) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(message)
                .positiveText(android.R.string.ok);

        if (titleRes != NO_DIALOG_TITLE) {
            builder.title(titleRes);
        }

        if (positiveCallback != NO_DIALOG_CALLBACK) {
            builder
                    .positiveText(positiveCallback.buttonText())
                    .onPositive(positiveCallback.buttonCallback());
        }

        if (negativeCallback != NO_DIALOG_CALLBACK) {
            builder
                    .negativeText(negativeCallback.buttonText())
                    .onNegative(negativeCallback.buttonCallback());
        }

        if (neutralCallback != NO_DIALOG_CALLBACK) {
            builder
                    .neutralText(neutralCallback.buttonText())
                    .onNeutral(neutralCallback.buttonCallback());
        }

        return builder.build();
    }
}
