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
public class SharedPreferencesSettingsRepositoryImpl implements SettingsRepository {
    private static final String KEY_PREF_FIRST_TIME_SETTINGS_LAUNCH = "first-time-settings-launch";
    private static final String KEY_PREF_USUARIO_LOGADO = "usuario-logado";

    private final Context mContext;

    private final SharedPreferences mPreferences;

    public SharedPreferencesSettingsRepositoryImpl(Context context) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override public boolean isFirstTimeSettingsLaunch() {
        return PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .getBoolean(KEY_PREF_FIRST_TIME_SETTINGS_LAUNCH, true);
    }

    @Override public void setFirstTimeSettingsLaunch() {
        PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .edit()
                .putBoolean(KEY_PREF_FIRST_TIME_SETTINGS_LAUNCH, false)
                .apply();
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
        PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .edit()
                .putInt(KEY_PREF_USUARIO_LOGADO, idVendedor)
                .apply();
    }

    @Override public int getUsuarioLogado() {
        return mPreferences.getInt(KEY_PREF_USUARIO_LOGADO, -1);
    }
}
