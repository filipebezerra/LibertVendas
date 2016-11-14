package br.com.libertsolutions.libertvendas.app.data.importacao;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

/**
 * @author Filipe Bezerra
 */
public class SharedPreferencesImportacaoRepositoryImpl implements ImportacaoRepository {
    private static final String KEY_PREF_IMPORTACAO_INICIAL_FEITA = "importacao-inicial-feita";

    private final Context mContext;

    private final SharedPreferences mPreferences;

    public SharedPreferencesImportacaoRepositoryImpl(Context pContext) {
        mContext = pContext;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);;
    }

    @Override public boolean isImportacaoInicialFeita() {
        return PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .getBoolean(KEY_PREF_IMPORTACAO_INICIAL_FEITA, false);
    }

    @Override public void setImportacaoInicialComoFeita() {
        PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .edit()
                .putBoolean(KEY_PREF_IMPORTACAO_INICIAL_FEITA, true)
                .apply();
    }
}
