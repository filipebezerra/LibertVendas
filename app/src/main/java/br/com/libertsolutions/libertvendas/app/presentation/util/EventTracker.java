package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.support.annotation.StringDef;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.LoginEvent;
import com.crashlytics.android.answers.SearchEvent;
import java.lang.annotation.Retention;

import static br.com.libertsolutions.libertvendas.app.BuildConfig.DEBUG;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Filipe Bezerra
 */
public class EventTracker {

    private EventTracker() {}

    @Retention(SOURCE)
    @StringDef({ LOGIN_METHOD_CPF_CNPJ})
    public @interface LoginMethod {}
    public static final String LOGIN_METHOD_CPF_CNPJ = "CPF/CNPJ";

    @Retention(SOURCE)
    @StringDef({ 
            ACTION_MANUAL_SYNC, 
            ACTION_DUPLICATE_ORDER, 
            ACTION_FILTERED_GRAPH,
            ACTION_FILTERED_ORDERS,
            ACTION_CEP_SEARCHED,
            ACTION_CHANGED_PROFILE,
            ACTION_SAVED_CUSTOMER,
            ACTION_SAVED_ORDER })
    public @interface ActionName {}
    public static final String ACTION_MANUAL_SYNC = "Manual Sync";
    public static final String ACTION_DUPLICATE_ORDER = "Duplicate Order";
    public static final String ACTION_FILTERED_GRAPH = "Filtered Graph";
    public static final String ACTION_FILTERED_ORDERS = "Filtered Orders";
    public static final String ACTION_CEP_SEARCHED = "CEP Searched";
    public static final String ACTION_CHANGED_PROFILE = "Changed Profile";
    public static final String ACTION_SAVED_CUSTOMER = "Saved Customer";
    public static final String ACTION_SAVED_ORDER = "Saved Order";

    @Retention(SOURCE)
    @StringDef({ SEARCHED_PRODUCTS, SEARCHED_CUSTOMERS, SEARCHED_ORDERS })
    public @interface SearchScope{}
    public static final String SEARCHED_PRODUCTS = "Searched Products";
    public static final String SEARCHED_CUSTOMERS = "Searched Customers";
    public static final String SEARCHED_ORDERS = "Searched Orders";

    public static void unsuccessfulLogin(@LoginMethod final String method) {
        if (!DEBUG) {
            Answers.getInstance().logLogin(new LoginEvent()
                    .putMethod(method)
                    .putSuccess(false));
        }
    }

    public static void successfulLogin(
            @LoginMethod final String method, final int numberOfProfiles) {
        if (!DEBUG) {
            Answers.getInstance().logLogin(new LoginEvent()
                    .putMethod(method)
                    .putSuccess(true)
                    .putCustomAttribute("Number of profiles", numberOfProfiles));
        }
    }

    public static void action(@ActionName final String action) {
        if (!DEBUG) {
            Answers.getInstance().logCustom(new CustomEvent(action));
        }
    }

    public static void search(@SearchScope final String scope, final String query) {
        if (!DEBUG) {
            Answers.getInstance().logSearch(new SearchEvent()
                .putQuery(query)
                .putCustomAttribute("Scope", scope));
        }
    }
}
