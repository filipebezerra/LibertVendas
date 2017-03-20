package br.com.libertsolutions.libertvendas.app.presentation.initialflow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseActivity;
import br.com.libertsolutions.libertvendas.app.presentation.importation.CompletedImportationEvent;
import br.com.libertsolutions.libertvendas.app.presentation.importation.ImportationFragment;
import br.com.libertsolutions.libertvendas.app.presentation.login.CompletedLoginEvent;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoginFragment;
import br.com.libertsolutions.libertvendas.app.presentation.settings.CompletedSettingsEvent;
import br.com.libertsolutions.libertvendas.app.presentation.settings.SettingsFragment;
import butterknife.BindView;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Filipe Bezerra
 */
public class InitialFlowActivity extends BaseActivity {

    @BindView(R.id.coordinator_layout_container) CoordinatorLayout mCoordinatorLayout;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_initial_flow;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        super.onCreate(inState);
        setAsInitialFlowActivity();

        if (!settings().isAllSettingsPresent()) {
            navigateToSettings();
        } else if (!settings().isUserLoggedIn()) {
            navigateToLogin();
        } else {
            navigateToImportation();
        }
    }

    @Override protected void onStart() {
        super.onStart();
        eventBus().register(this);
    }

    @Override protected void onStop() {
        eventBus().unregister(this);
        super.onStop();
    }

    @Subscribe public void onCompletedSettings(CompletedSettingsEvent event) {
        navigateToLogin();
    }

    @Subscribe public void onCompletedLogin(CompletedLoginEvent event) {
        navigateToImportation();
    }

    @Subscribe public void onCompletedImportation(CompletedImportationEvent event) {
        finishFlow();
    }

    private void navigateToSettings() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout_all_fragment_container,
                        SettingsFragment.newInstanceForInitialFlow(), SettingsFragment.TAG)
                .commit();
    }

    private void navigateToLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        LoginFragment.newInstance(), LoginFragment.TAG)
                .commitNow();
        setTitle(R.string.initial_flow_login_title);
    }

    private void navigateToImportation() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        ImportationFragment.newInstance(), ImportationFragment.TAG)
                .commitNow();
        setTitle(R.string.initial_flow_importation_title);
    }

    private void finishFlow() {
        setResult(RESULT_OK);
        finish();
    }
}
