package br.com.libertsolutions.libertvendas.app.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;

import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_chave_autenticacao;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_endereco_servidor;

/**
 * @author Filipe Bezerra
 */
public class SharedPreferencesSettingsRepositoryImpl implements SettingsRepository {

    private static final String KEY_INITIAL_CONFIGURATION_DONE
            = "pref.initialConfigurationDone";

    private static final String KEY_USER_LOGGED_IN = "pref.userLoggedIn";
    private static final String KEY_ID_LOGGED_IN_USER = "pref.idLoggedInUser";

    private static final String KEY_INITIAL_DATA_IMPORTATION_DONE
            = "pref.initialDataImportationDone";

    private final Context mContext;

    private final SharedPreferences mSharedPreferences;

    public SharedPreferencesSettingsRepositoryImpl(final Context context) {
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override public void setInitialConfigurationDone() {
        putBooleanPreference(KEY_INITIAL_CONFIGURATION_DONE, true);
    }

    @Override public boolean isInitialConfigurationDone() {
        return getBooleanPreference(KEY_INITIAL_CONFIGURATION_DONE, false);
    }

    @Override public boolean hasRequiredSettings() {
        return mSharedPreferences.contains(mContext.getString(key_pref_endereco_servidor))
                && mSharedPreferences.contains(mContext.getString(key_pref_chave_autenticacao));
    }

    @Override public Settings loadSettings() {
        final String urlServidor = mSharedPreferences
                .getString(mContext.getString(key_pref_endereco_servidor), "");
        final String chaveAutenticacao = mSharedPreferences
                .getString(mContext.getString(key_pref_chave_autenticacao), "");

        return Settings.create(urlServidor, chaveAutenticacao);
    }

    @Override public boolean isUserLoggedIn() {
        return getBooleanPreference(KEY_USER_LOGGED_IN, false);
    }

    @Override public void setLoggedInUser(final int userId) {
        putIntegerPreference(KEY_ID_LOGGED_IN_USER, userId);
        putBooleanPreference(KEY_USER_LOGGED_IN , true);
    }

    @Override public int getLoggedInUser() {
        return getIntegerPreference(KEY_ID_LOGGED_IN_USER, -1);
    }

    @Override public void setInitialDataImportationDone() {
        putBooleanPreference(KEY_INITIAL_DATA_IMPORTATION_DONE, true);
    }

    @Override public boolean isInitialDataImportationDone() {
        return getBooleanPreference(KEY_INITIAL_DATA_IMPORTATION_DONE, false);
    }

    private void putBooleanPreference(final String key, final boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    private boolean getBooleanPreference(final String key, final boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    private void putIntegerPreference(final String key, final int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    private int getIntegerPreference(final String key, final int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    private void putStringPreference(final String key, final String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    private String getStringPreference(final String key, final String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }
}
