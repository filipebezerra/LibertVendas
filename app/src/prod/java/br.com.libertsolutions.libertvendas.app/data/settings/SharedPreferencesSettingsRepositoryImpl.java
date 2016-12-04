package br.com.libertsolutions.libertvendas.app.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;

import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_chave_autenticacao;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_endereco_servidor;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_pode_aplicar_desconto;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_sincronizar_pedido_automaticamente;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_tabela_preco_padrao;

/**
 * @author Filipe Bezerra
 */
class SharedPreferencesSettingsRepositoryImpl implements SettingsRepository {

    private static final String PREFERENCE_KEY_IS_INITIAL_DATA_IMPORTATION_FLOW_DONE
            = "is-initial-data-importation-flow-done";

    private static final String PREFERENCE_KEY_LOGGED_IN_USER = "logged-in-user";

    private static final String PREFERENCE_KEY_LOGGED_IN_USER_COMPANY = "logged-in-user-company";

    private static final String PREFERENCE_KEY_USER_LEARNED_DRAWER = "user-learned-drawer";

    private final Context mContext;

    private final SharedPreferences mPreferences;

    SharedPreferencesSettingsRepositoryImpl(Context context) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override public boolean isInitialDataImportationFlowDone() {
        return getBooleanPreference(PREFERENCE_KEY_IS_INITIAL_DATA_IMPORTATION_FLOW_DONE, false);
    }

    @Override public void doneInitialDataImportationFlow() {
        putBooleanPreference(PREFERENCE_KEY_IS_INITIAL_DATA_IMPORTATION_FLOW_DONE, true);
    }

    @Override public boolean isRequiredSettingsFieldsSet() {
        return mPreferences.contains(mContext.getString(key_pref_endereco_servidor))
                && mPreferences.contains(mContext.getString(key_pref_chave_autenticacao))
                && mPreferences.contains(mContext.getString(key_pref_sincronizar_pedido_automaticamente))
                && mPreferences.contains(mContext.getString(key_pref_pode_aplicar_desconto));
    }

    @Override public Settings getSettings() {
        final String urlServidor = mPreferences
                .getString(mContext.getString(key_pref_endereco_servidor), "");
        final String chaveAutenticacao = mPreferences
                .getString(mContext.getString(key_pref_chave_autenticacao), "");
        final boolean sincronizaPedidoAutomaticamente = mPreferences
                .getBoolean(mContext.getString(key_pref_sincronizar_pedido_automaticamente), true);
        final boolean podeAplicarDesconto = mPreferences
                .getBoolean(mContext.getString(key_pref_pode_aplicar_desconto), false);
        final int tabelaPrecoPadrao = mPreferences
                .getInt(mContext.getString(key_pref_tabela_preco_padrao), 0);

        return Settings.create(
                urlServidor, chaveAutenticacao, sincronizaPedidoAutomaticamente,
                podeAplicarDesconto, tabelaPrecoPadrao
        );
    }

    @Override public boolean isUserLoggedIn() {
        return mPreferences.contains(PREFERENCE_KEY_LOGGED_IN_USER);
    }

    @Override public int getLoggedInUser() {
        return getIntegerPreference(PREFERENCE_KEY_LOGGED_IN_USER, -1);
    }

    @Override public int getLoggedInUserCompany() {
        return getIntegerPreference(PREFERENCE_KEY_LOGGED_IN_USER_COMPANY, -1);
    }

    @Override public void setLoggedInUser(int idVendedor, int idEmpresa) {
        putIntegerPreference(PREFERENCE_KEY_LOGGED_IN_USER, idVendedor);
        putIntegerPreference(PREFERENCE_KEY_LOGGED_IN_USER_COMPANY, idEmpresa);
    }

    @Override public boolean isUserLearnedDrawer() {
        return getBooleanPreference(PREFERENCE_KEY_USER_LEARNED_DRAWER, false);
    }

    @Override public void doneUserLearnedDrawer() {
        putBooleanPreference(PREFERENCE_KEY_USER_LEARNED_DRAWER, true);
    }

    private boolean getBooleanPreference(final String preferenceKey, final boolean defaultValue) {
        return mPreferences.getBoolean(preferenceKey, defaultValue);
    }

    private void putBooleanPreference(final String preferenceKey, final boolean value) {
        PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .edit()
                .putBoolean(preferenceKey, value)
                .apply();
    }

    private int getIntegerPreference(final String preferenceKey, final int defaultValue) {
        return mPreferences.getInt(preferenceKey, defaultValue);
    }

    private void putIntegerPreference(final String preferenceKey, final int value) {
        PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .edit()
                .putInt(preferenceKey, value)
                .apply();
    }
}
