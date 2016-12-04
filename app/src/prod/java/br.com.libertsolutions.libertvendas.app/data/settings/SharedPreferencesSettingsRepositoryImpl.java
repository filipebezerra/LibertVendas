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


    private static final String KEY_PREF_FIRST_TIME_SETTINGS_LAUNCH
            = "first-time-settings-launch";
    private static final String KEY_PREF_USUARIO_LOGADO = "usuario-logado";
    private static final String KEY_PREF_EMPRESA_LOGADA = "empresa-logada";
    private static final String KEY_PREF_FIRST_TIME_FEATURED_MENU_SHOWN
            = "first-time-featured-menu-shown";

    private final Context mContext;

    private final SharedPreferences mPreferences;

    SharedPreferencesSettingsRepositoryImpl(Context context) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override public boolean isInitialDataImportationFlowDone() {
        return mPreferences.getBoolean(PREFERENCE_KEY_IS_INITIAL_DATA_IMPORTATION_FLOW_DONE, false);
    }

    @Override public void doneInitialDataImportationFlow() {
        putBooleanPreference(PREFERENCE_KEY_IS_INITIAL_DATA_IMPORTATION_FLOW_DONE, true);
    }

    private void putBooleanPreference(final String preferenceKey, final boolean value) {
        PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .edit()
                .putBoolean(preferenceKey, value)
                .apply();
    }


    //region deprecated
    @Override public boolean isFirstTimeSettingsLaunch() {
        return getBooleanPreference(KEY_PREF_FIRST_TIME_SETTINGS_LAUNCH, true);
    }

    @Override public void setFirstTimeSettingsLaunch() {
        putBooleanPreference(KEY_PREF_FIRST_TIME_SETTINGS_LAUNCH, false);
    }

    @Override public boolean isFirstTimeFeaturedMenuShown() {
        return getBooleanPreference(KEY_PREF_FIRST_TIME_FEATURED_MENU_SHOWN, false);
    }

    @Override public void setFirstTimeFeaturedMenuShown() {
        putBooleanPreference(KEY_PREF_FIRST_TIME_FEATURED_MENU_SHOWN, true);
    }

    private boolean getBooleanPreference(final String preferenceKey, final boolean defaultValue) {
        return mPreferences.getBoolean(preferenceKey, defaultValue);
    }



    @Override public boolean hasAllSettingsFields() {
        return mPreferences.contains(mContext.getString(key_pref_endereco_servidor))
                && mPreferences.contains(mContext.getString(key_pref_chave_autenticacao))
                && mPreferences.contains(mContext.getString(key_pref_sincronizar_pedido_automaticamente))
                && mPreferences.contains(mContext.getString(key_pref_pode_aplicar_desconto));
    }

    @Override public Settings loadSettings() {
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

    @Override public boolean hasUsuarioLogado() {
        return mPreferences.contains(KEY_PREF_USUARIO_LOGADO);
    }

    @Override public void setUsuarioLogado(int idVendedor) {
        putIntPreference(KEY_PREF_USUARIO_LOGADO, idVendedor);
    }

    @Override public int getUsuarioLogado() {
        return getIntPreference(KEY_PREF_USUARIO_LOGADO, -1);
    }

    @Override public void setEmpresaLogada(int idEmpresa) {
        putIntPreference(KEY_PREF_EMPRESA_LOGADA, idEmpresa);
    }

    @Override public int getEmpresaLogada() {
        return getIntPreference(KEY_PREF_EMPRESA_LOGADA, -1);
    }

    private int getIntPreference(final String preferenceKey, final int defaultValue) {
        return mPreferences.getInt(preferenceKey, defaultValue);
    }

    private void putIntPreference(final String preferenceKey, final int value) {
        PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .edit()
                .putInt(preferenceKey, value)
                .apply();
    }
    //endregion
}
