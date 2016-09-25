package br.com.libertsolutions.libertvendas.app.data.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;

/**
 * @author Filipe Bezerra
 */
public class SharedPreferencesSettingsRepositoryImpl implements SettingsRepository {
    private static final String KEY_PREF_FIRST_TIME_SETTINGS_LAUNCH = "first-time-settings-launch";

    private final Context mContext;

    private final SharedPreferences mPreferences;

    public SharedPreferencesSettingsRepositoryImpl(Context context) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public boolean isFirstTimeSettingsLaunch() {
        return PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .getBoolean(KEY_PREF_FIRST_TIME_SETTINGS_LAUNCH, true);
    }

    @Override
    public void setFirstTimeSettingsLaunch() {
        PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .edit()
                .putBoolean(KEY_PREF_FIRST_TIME_SETTINGS_LAUNCH, false)
                .apply();
    }

    @Override
    public boolean hasAllSettingsFields() {
        return mPreferences.contains(mContext.getString(R.string.key_pref_endereco_servidor))
                && mPreferences.contains(mContext.getString(R.string.key_pref_sincronizar_pedido_automaticamente))
                && mPreferences.contains(mContext.getString(R.string.key_pref_pode_aplicar_desconto));
    }

    @Override
    public Settings loadSettings() {
        final String enderecoServidor = mPreferences.getString(
                mContext.getString(R.string.key_pref_endereco_servidor), null);
        final boolean sincronizarPedidoAutomaticamente = mPreferences.getBoolean(
                mContext.getString(R.string.key_pref_sincronizar_pedido_automaticamente), true);
        final boolean podeAplicatDesconto = mPreferences.getBoolean(
                mContext.getString(R.string.key_pref_pode_aplicar_desconto), false);

        return Settings.create(
                enderecoServidor, sincronizarPedidoAutomaticamente, podeAplicatDesconto);
    }
}
