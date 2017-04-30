package br.com.libertsolutions.libertvendas.app.presentation.util;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;

import static br.com.libertsolutions.libertvendas.app.BuildConfig.DEBUG;

/**
 * @author Filipe Bezerra
 */
public class AnswersEvents {

    private AnswersEvents() {}

    public static final String LOGIN_METHOD_CPF_CNPJ = "CPF/CNPJ";

    public static void unsuccessfulLogin(final String method) {
        if (!DEBUG) {
            Answers.getInstance().logLogin(new LoginEvent()
                    .putMethod(method)
                    .putSuccess(false));
        }
    }

    public static void successfulLogin(final String method, final int numberOfProfiles) {
        if (!DEBUG) {
            Answers.getInstance().logLogin(new LoginEvent()
                    .putMethod(method)
                    .putSuccess(true)
                    .putCustomAttribute("Number of profiles", numberOfProfiles));
        }
    }
}
