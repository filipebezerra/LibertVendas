package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.login.UsuarioLogadoEvent;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompatDividers;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_chave_autenticacao;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_endereco_servidor;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_tabela_preco_padrao;
import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
public class SettingsFragment extends PreferenceFragmentCompatDividers {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override public void onCreatePreferencesFix(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        bindSettingValueToSummary(
                findPreference(getString(key_pref_endereco_servidor)));
        bindSettingValueToSummary(
                findPreference(getString(key_pref_chave_autenticacao)));
        bindSettingValueToSummary(
                findPreference(getString(key_pref_tabela_preco_padrao)));
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        try {
            return super.onCreateView(inflater, container, savedInstanceState);
        } finally {
            setDividerPreferences(
                    DIVIDER_PADDING_CHILD | DIVIDER_CATEGORY_AFTER_LAST | DIVIDER_CATEGORY_BETWEEN);
        }
    }

    @Override public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = MAIN, sticky = true) public void onUsuarioLogadoEvent(
            UsuarioLogadoEvent pEvent) {
        ((SwitchPreference)findPreference(getString(R.string.key_pref_pode_aplicar_desconto)))
                .setChecked(pEvent.getVendedor().isAplicaDesconto());

        findPreference(getString(R.string.key_pref_tabela_preco_padrao))
                .setSummary(String.valueOf(pEvent.getVendedor().getIdTabela()));
    }

    private Preference.OnPreferenceChangeListener mOnPreferenceChangeListener
            = (preference, newValue) -> {
        if (preference instanceof EditTextPreference) {
            final String stringValue = newValue.toString();
            if (!TextUtils.isEmpty(stringValue)) {
                preference.setSummary(stringValue);
                ((EditTextPreference) preference).setText(stringValue);
            }
        }

        return false;
    };

    private Preference.OnPreferenceClickListener mOnPreferenceClickListener
            = preference -> {
        preference.setPersistent(true);
        return true;
    };

    private void bindSettingValueToSummary(Preference preference) {
        preference.setOnPreferenceChangeListener(mOnPreferenceChangeListener);
        preference.setOnPreferenceClickListener(mOnPreferenceClickListener);

        mOnPreferenceChangeListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
}
