package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.Preference.OnPreferenceClickListener;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompatDividers;

import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_chave_autenticacao;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_endereco_servidor;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_tabela_preco_padrao;

/**
 * @author Filipe Bezerra
 */
public class SettingsFragment extends PreferenceFragmentCompatDividers {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    private OnPreferenceChangeListener mPreferenceChangeListener = (preference, newValue) -> {
        if (preference instanceof EditTextPreference) {
            final String stringValue = newValue.toString();
            if (!TextUtils.isEmpty(stringValue)) {
                preference.setSummary(stringValue);
                ((EditTextPreference) preference).setText(stringValue);
            }
        }

        return false;
    };

    private OnPreferenceClickListener mPreferenceClickListener = preference -> {
        preference.setPersistent(true);
        return true;
    };

    @Override public void onCreatePreferencesFix(
            @Nullable final Bundle inState, final String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        bindSettingValueToSummary(findPreference(getString(key_pref_endereco_servidor)));
        bindSettingValueToSummary(findPreference(getString(key_pref_chave_autenticacao)));
        bindSettingValueToSummary(findPreference(getString(key_pref_tabela_preco_padrao)));
    }

    @Override public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            return super.onCreateView(inflater, container, savedInstanceState);
        } finally {
            setDividerPreferences(
                    DIVIDER_PADDING_CHILD | DIVIDER_CATEGORY_AFTER_LAST | DIVIDER_CATEGORY_BETWEEN);
        }
    }

    private void bindSettingValueToSummary(Preference preference) {
        preference.setOnPreferenceChangeListener(mPreferenceChangeListener);
        preference.setOnPreferenceClickListener(mPreferenceClickListener);

        mPreferenceChangeListener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
}
