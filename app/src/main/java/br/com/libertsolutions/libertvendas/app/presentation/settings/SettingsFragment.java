package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.Preference.OnPreferenceClickListener;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.sync.SyncTaskService;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompatDividers;

import static android.text.TextUtils.isEmpty;
import static br.com.libertsolutions.libertvendas.app.R.string.settings_auth_key_preference_key;
import static br.com.libertsolutions.libertvendas.app.R.string.settings_automatically_sync_orders_preference_key;
import static br.com.libertsolutions.libertvendas.app.R.string.settings_server_address_preference_key;
import static br.com.libertsolutions.libertvendas.app.R.string.settings_sync_periodicity_preference_key;
import static br.com.libertsolutions.libertvendas.app.R.string.settings_sync_periodicity_preference_summary;
import static br.com.libertsolutions.libertvendas.app.R.xml.settings;
import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideEventBus;
import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideSettingsRepository;
import static br.com.libertsolutions.libertvendas.app.presentation.settings.CompletedSettingsEvent.newEvent;

/**
 * @author Filipe Bezerra
 */
public class SettingsFragment extends PreferenceFragmentCompatDividers {

    public static final String TAG = SettingsFragment.class.getName();

    private static final String ARG_IN_INITIAL_FLOW = TAG + "arg.inInitialFlow";

    public static final int RESULT_AUTO_SYNC_ORDERS_CHANGED = 2;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    public static SettingsFragment newInstanceForInitialFlow() {
        SettingsFragment fragment = newInstance();
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARG_IN_INITIAL_FLOW, true);
        fragment.setArguments(arguments);
        return fragment;
    }

    private OnPreferenceChangeListener mPreferenceChangeListener = this::handlePreferenceChanged;

    private OnPreferenceClickListener mPreferenceClickListener = this::handlePreferenceClicked;

    private Unbinder mUnbinder;

    @BindString(settings_server_address_preference_key) String mServerAddressPreferenceKey;
    @BindString(settings_auth_key_preference_key) String mAuthKeyPreferenceKey;
    @BindString(settings_sync_periodicity_preference_key) String mSyncPeriodicityPreferenceKey;
    @BindString(settings_automatically_sync_orders_preference_key) String mSyncOrdersAutomaticallyKey;

    @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(isOptionsMenuEnabled());
    }

    @Override public void onCreatePreferencesFix(
            @Nullable final Bundle inState, final String rootKey) {
        setPreferencesFromResource(settings, rootKey);
    }

    @Override public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
    }

    @Override public void onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.action_all_done)
                .setVisible(provideSettingsRepository().isAllSettingsPresent());
    }

    @Override public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_all_done) {
            provideEventBus().post(newEvent());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override public View onCreateView(
            LayoutInflater inflater, ViewGroup container, @Nullable Bundle inState) {
        try {
            View view = super.onCreateView(inflater, container, inState);
            mUnbinder = ButterKnife.bind(this, view);
            return view;
        } finally {
            setDividerPreferences(
                    DIVIDER_PADDING_CHILD | DIVIDER_CATEGORY_AFTER_LAST | DIVIDER_CATEGORY_BETWEEN);
        }
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeSettingValueToSummary(findPreference(mServerAddressPreferenceKey));
        initializeSettingValueToSummary(findPreference(mAuthKeyPreferenceKey));
        initializeSettingValueToSummary(findPreference(mSyncPeriodicityPreferenceKey));
        initializeSettingValueToSummary(findPreference(mSyncOrdersAutomaticallyKey));
    }

    @Override public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    private void initializeSettingValueToSummary(Preference preference) {
        preference.setOnPreferenceChangeListener(mPreferenceChangeListener);
        preference.setOnPreferenceClickListener(mPreferenceClickListener);
        bindSettingValueToSummary(preference);
    }

    private void bindSettingValueToSummary(Preference preference) {
        Object value;
        if (preference instanceof SwitchPreference) {
            value = PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                    .getBoolean(preference.getKey(), false);
        } else {
            value = PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                    .getString(preference.getKey(), "");
        }

        setPreferenceSummary(preference, value);
    }

    private boolean handlePreferenceChanged(Preference preference, Object newValue) {
        setPreferenceSummary(preference, newValue);

        if (preference.getKey().equals(mSyncPeriodicityPreferenceKey) &&
                provideSettingsRepository().isInitialFlowDone()) {
            SyncTaskService.schedule(getContext(), Integer.valueOf(newValue.toString()));
        }

        else if (preference.getKey().equals(mSyncOrdersAutomaticallyKey)) {
            getActivity().setResult(RESULT_AUTO_SYNC_ORDERS_CHANGED);
        }

        if (isOptionsMenuEnabled()) {
            getActivity().supportInvalidateOptionsMenu();
        }
        return true;
    }

    private void setPreferenceSummary(Preference preference, Object value) {
        if (preference instanceof EditTextPreference) {
            final String stringValue = value.toString();
            if (!isEmpty(stringValue)) {
                if (preference.getKey()
                        .equals(getString(settings_sync_periodicity_preference_key))) {
                    preference.setSummary(
                            getString(settings_sync_periodicity_preference_summary, stringValue));
                } else {
                    preference.setSummary(stringValue);
                }
            }
        }
    }

    private boolean handlePreferenceClicked(Preference preference) {
        if (!preference.isPersistent()) {
            preference.setPersistent(true);
        }
        return true;
    }

    private boolean isOptionsMenuEnabled() {
        return getArguments() != null &&
                getArguments().getBoolean(ARG_IN_INITIAL_FLOW, false);
    }
}
