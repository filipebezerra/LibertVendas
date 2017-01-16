package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.Preference.OnPreferenceClickListener;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import com.afollestad.materialdialogs.MaterialDialog;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompatDividers;

import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_chave_autenticacao;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_endereco_servidor;
import static br.com.libertsolutions.libertvendas.app.R.string.key_pref_tabela_preco_padrao;

/**
 * @author Filipe Bezerra
 */
public class SettingsFragment extends PreferenceFragmentCompatDividers
        implements SettingsContract.View {

    private LibertVendasActivity mLibertVendasActivity;

    private SettingsContract.Presenter mPresenter;

    private OnPreferenceChangeListener mPreferenceChangeListener = (preference, newValue) -> {
        if (preference instanceof EditTextPreference) {
            final String stringValue = newValue.toString();
            if (!TextUtils.isEmpty(stringValue)) {
                ((EditTextPreference) preference).setText(stringValue);

                if (preference.getKey().equals(getString(R.string.key_pref_sync_period))) {
                    preference.setSummary(getString(R.string.summary_pref_sync_period, stringValue));

                    if (mPresenter != null) {
                        mPresenter.handleSyncPeriodPreferenceChanged(stringValue);
                    }
                } else {
                    preference.setSummary(stringValue);
                }
            }
        }

        return false;
    };

    private OnPreferenceClickListener mPreferenceClickListener = preference -> {
        preference.setPersistent(true);
        return true;
    };

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mLibertVendasActivity = (LibertVendasActivity) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException(
                    "This fragment must be hosted by " + LibertVendasActivity.class.getName());
        }
    }

    @Override public void onCreatePreferencesFix(
            @Nullable final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        bindSettingValueToSummary(findPreference(getString(key_pref_endereco_servidor)));
        bindSettingValueToSummary(findPreference(getString(key_pref_chave_autenticacao)));
        bindSettingValueToSummary(findPreference(getString(key_pref_tabela_preco_padrao)));
        bindSettingValueToSummary(findPreference(getString(R.string.key_pref_sync_period)));
    }

    private void bindSettingValueToSummary(Preference preference) {
        preference.setOnPreferenceChangeListener(mPreferenceChangeListener);
        preference.setOnPreferenceClickListener(mPreferenceClickListener);

        mPreferenceChangeListener.onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            mPresenter = new SettingsPresenter(
                    PresentationInjection.provideSettingsRepository());
            return super.onCreateView(inflater, container, savedInstanceState);
        } finally {
            setDividerPreferences(
                    DIVIDER_PADDING_CHILD | DIVIDER_CATEGORY_AFTER_LAST | DIVIDER_CATEGORY_BETWEEN);
        }
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
    }

    @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_settings, menu);
    }

    @Override public void onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.action_done).setVisible(mPresenter.handleActionDoneVisibility());
    }

    @Override public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done: {
                mPresenter.handleActionDone();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override public void showSettingsRequiredMessage() {
        new MaterialDialog.Builder(getActivity())
                .content(R.string.message_settings_required)
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override public void showInvalidServerAddressMessage() {
        new MaterialDialog.Builder(getActivity())
                .content(R.string.message_invalid_url_servidor)
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override public void showInvalidServerAddressWithPathApiMessage() {
        new MaterialDialog.Builder(getActivity())
                .content(R.string.message_invalid_url_servidor_com_path_api)
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override public void showInvalidAuthenticationKeyMessage() {
        new MaterialDialog.Builder(getActivity())
                .content(R.string.message_invalid_chave_autenticacao)
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override public void navigateToHome() {
        mLibertVendasActivity.navigate().toHome();
    }

    @Override public void onDestroyView() {
        mPresenter.detach();
        super.onDestroyView();
    }
}
