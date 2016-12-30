package br.com.libertsolutions.libertvendas.app.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

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
    private static final String KEY_NAME_LOGGED_IN_USER = "pref.nameLoggedInUser";
    private static final String KEY_CPF_LOGGED_IN_USER = "pref.cpfLoggedInUser";
    private static final String KEY_ID_LOGGED_IN_USER_COMPANY = "pref.idLoggedInUserCompany";
    private static final String KEY_NAME_LOGGED_IN_USER_COMPANY = "pref.nameLoggedInUserCompany";
    private static final String KEY_CNPJ_LOGGED_IN_USER_COMPANY = "pref.cnpjLoggedInUserCompany";

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

    @Override public void setLoggedInUser(final Vendedor vendedor, final Empresa empresa) {
        putIntegerPreference(KEY_ID_LOGGED_IN_USER, vendedor.getIdVendedor());
        putStringPreference(KEY_NAME_LOGGED_IN_USER, vendedor.getNome());
        putStringPreference(KEY_CPF_LOGGED_IN_USER, vendedor.getCpfCnpj());
        putIntegerPreference(KEY_ID_LOGGED_IN_USER_COMPANY, empresa.getIdEmpresa());
        putStringPreference(KEY_NAME_LOGGED_IN_USER_COMPANY, empresa.getNome());
        putStringPreference(KEY_CNPJ_LOGGED_IN_USER_COMPANY, empresa.getCnpj());
        putBooleanPreference(KEY_USER_LOGGED_IN , true);
    }

    @Override public LoggedUser getLoggedInUser() {
        if (!isUserLoggedIn()) {
            return null;
        }

        int idVendedor = getIntegerPreference(KEY_ID_LOGGED_IN_USER, -1);
        String nomeVendedor = getStringPreference(KEY_NAME_LOGGED_IN_USER, "");
        String cpfVendedor = getStringPreference(KEY_CPF_LOGGED_IN_USER, "");
        int idEmpresa = getIntegerPreference(KEY_ID_LOGGED_IN_USER_COMPANY, -1);
        String nomeEmpresa = getStringPreference(KEY_NAME_LOGGED_IN_USER_COMPANY, "");
        String cnpjEmpresa = getStringPreference(KEY_CNPJ_LOGGED_IN_USER_COMPANY, "");
        return LoggedUser.create(
                idVendedor, nomeVendedor, cpfVendedor, idEmpresa, nomeEmpresa, cnpjEmpresa);
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
