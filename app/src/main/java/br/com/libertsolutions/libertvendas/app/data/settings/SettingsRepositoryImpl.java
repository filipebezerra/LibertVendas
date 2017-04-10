package br.com.libertsolutions.libertvendas.app.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.Pair;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Salesman;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import com.google.gson.Gson;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;
import static br.com.libertsolutions.libertvendas.app.R.string.settings_auth_key_preference_key;
import static br.com.libertsolutions.libertvendas.app.R.string.settings_automatically_sync_orders_preference_key;
import static br.com.libertsolutions.libertvendas.app.R.string.settings_server_address_preference_key;
import static br.com.libertsolutions.libertvendas.app.R.string.settings_sync_periodicity_preference_key;

/**
 * @author Filipe Bezerra
 */
public class SettingsRepositoryImpl implements SettingsRepository {

    private static final String KEY_INITIAL_FLOW = "pref.initialFlow";
    private static final String KEY_LOGGED_USER = "pref.loggedUser";
    private static final String KEY_DEFAULT_COMPANY = "pref.defaultCompany";
    private static final String KEY_RUNNING_SYNC_PERIOD = "pref.runningSyncPeriod";
    private static final String KEY_LAST_SYNC_TIME = "pref.lastSyncTime";

    private final Context mContext;

    private final SharedPreferences mPreferences;

    private final Gson mGson;

    public SettingsRepositoryImpl(final Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mContext = context;
        mGson = new Gson();
    }

    @Override public boolean isInitialFlowDone() {
        return mPreferences.getBoolean(KEY_INITIAL_FLOW, false);
    }

    @Override public void setInitialFlowDone() {
        mPreferences.edit()
                .putBoolean(KEY_INITIAL_FLOW, true)
                .apply();
    }

    @Override public boolean isAllSettingsPresent() {
        return !(isEmpty(getServerAddress())) && !(isEmpty(getAuthKey())) &&
                !(isEmpty(getSyncPeriodicity()));
    }

    @Override public boolean isUserLoggedIn() {
        return mPreferences.contains(KEY_LOGGED_USER);
    }

    @Override public void setLoggedUser(final Salesman user, final Company defaultCompany) {
        Observable.zip(getUserAsObservable(user), getDefaultCompanyAsObservable(defaultCompany),
                (salesman, company) -> {
                    final String userJson = mGson.toJson(salesman, Salesman.class);
                    final String companyJson = mGson.toJson(company, Company.class);

                    return Pair.create(userJson, companyJson);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pairJson -> mPreferences.edit()
                                .putString(KEY_LOGGED_USER, pairJson.first)
                                .putString(KEY_DEFAULT_COMPANY, pairJson.second)
                                .apply()
                );
    }

    @Override public Observable<LoggedUser> getLoggedUser() {
        final String loggedUserJson = mPreferences.getString(KEY_LOGGED_USER, null);
        final String defaultCompanyJson = mPreferences.getString(KEY_DEFAULT_COMPANY, null);

        if (StringUtils.isNullOrEmpty(loggedUserJson) ||
                StringUtils.isNullOrEmpty(defaultCompanyJson)) {
            return Observable.empty();
        }

        return Observable.zip(
                getStringAsObservable(loggedUserJson), getStringAsObservable(defaultCompanyJson),
                (userJson, companyJson) -> {
                    Salesman user = mGson.fromJson(userJson, Salesman.class);
                    Company defaultCompany = mGson.fromJson(companyJson, Company.class);

                    return LoggedUser.create(user, defaultCompany);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override public Settings getSettings() {
        return Settings.create(getServerAddress(), getAuthKey(), getAutomaticallySyncOrders(),
                getSyncPeriodicityAsInt());
    }

    @Override public void setAutoSyncOrders(final boolean isEnabled) {
        mPreferences.edit()
                .putBoolean(mContext.getString(
                        R.string.settings_automatically_sync_orders_preference_key), isEnabled)
                .apply();
    }

    @Override public boolean isRunningSyncWith(final long period) {
        return mPreferences.getLong(KEY_RUNNING_SYNC_PERIOD, 0) == period;
    }

    @Override public void setRunningSyncWith(final long syncPeriod) {
        mPreferences.edit()
                .putLong(KEY_RUNNING_SYNC_PERIOD, syncPeriod)
                .apply();
    }

    @Override public void setLastSyncTime(final String lastSyncTime) {
        mPreferences.edit()
                .putString(KEY_LAST_SYNC_TIME, lastSyncTime)
                .apply();
    }

    @Override public String getLastSyncTime() {
        return mPreferences.getString(KEY_LAST_SYNC_TIME, null);
    }

    private Observable<String> getStringAsObservable(String value) {
        return Observable.defer(() -> Observable.just(value));
    }

    private Observable<Salesman> getUserAsObservable(Salesman user) {
        return Observable.defer(() -> Observable.just(user));
    }

    private Observable<Company> getDefaultCompanyAsObservable(Company defaultCompany) {
        return Observable.defer(() -> Observable.just(defaultCompany));
    }

    private String getServerAddress() {
        return mPreferences.getString(mContext.getString(
                settings_server_address_preference_key), "");
    }

    private String getAuthKey() {
        return mPreferences.getString(mContext.getString(
                settings_auth_key_preference_key), "");
    }

    private boolean getAutomaticallySyncOrders() {
        return mPreferences.getBoolean(mContext.getString(
                settings_automatically_sync_orders_preference_key), true);
    }

    private int getSyncPeriodicityAsInt() {
        final String syncPeriodicity = getSyncPeriodicity();
        return TextUtils.isEmpty(syncPeriodicity) ? 0 : Integer.valueOf(syncPeriodicity);
    }

    private String getSyncPeriodicity() {
        return mPreferences.getString(mContext.getString(
                settings_sync_periodicity_preference_key), "");
    }
}
