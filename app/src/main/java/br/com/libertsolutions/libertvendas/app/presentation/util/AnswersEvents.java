package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.support.annotation.StringDef;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.LoginEvent;
import java.lang.annotation.Retention;

import static br.com.libertsolutions.libertvendas.app.BuildConfig.DEBUG;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Filipe Bezerra
 */
public class AnswersEvents {

    private AnswersEvents() {}

    @Retention(SOURCE)
    @StringDef({ LOGIN_METHOD_CPF_CNPJ})
    public @interface LoginMethod {}
    public static final String LOGIN_METHOD_CPF_CNPJ = "CPF/CNPJ";

    @Retention(SOURCE)
    @StringDef({ ACTION_MANUAL_SYNC, ACTION_DUPLICATE_ORDER })
    public @interface AnswerAction {}
    public static final String ACTION_MANUAL_SYNC = "Manual Sync";
    public static final String ACTION_DUPLICATE_ORDER = "Duplicate Order";

    public static void unsuccessfulLogin(@LoginMethod final String method) {
        if (!DEBUG) {
            Answers.getInstance().logLogin(new LoginEvent()
                    .putMethod(method)
                    .putSuccess(false));
        }
    }

    public static void successfulLogin(@LoginMethod final String method, final int numberOfProfiles) {
        if (!DEBUG) {
            Answers.getInstance().logLogin(new LoginEvent()
                    .putMethod(method)
                    .putSuccess(true)
                    .putCustomAttribute("Number of profiles", numberOfProfiles));
        }
    }

    public static void action(@AnswerAction final String action) {
        if (!DEBUG) {
            Answers.getInstance().logCustom(new CustomEvent(action));
        }
    }
}
