package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import com.afollestad.materialdialogs.MaterialDialog;
import org.greenrobot.eventbus.EventBus;

/**
 * @author Filipe Bezerra
 */
public class SettingsActivity extends LibertVendasActivity implements SettingsContract.View {
    public static final String EXTRA_FROM_LAUNCH
            = SettingsActivity.class.getSimpleName() + ".extraFromLaunch";

    private SettingsContract.Presenter mPresenter;

    @Override protected void onCreate(@Nullable Bundle inState) {
        super.onCreate(inState);
        setAsInitialFlowActivity();
        mPresenter = new SettingsPresenter(this,
                Injection.provideSettingsRepository(this),
                getIntent().getExtras().getBoolean(EXTRA_FROM_LAUNCH));

        if (inState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment, SettingsFragment.newInstance())
                    .commit();
        }
    }

    @Override protected int provideContentViewResource() {
        return R.layout.activity_settings;
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        final boolean canEnableOptionsMenu = mPresenter.canEnableOptionsMenu();
        menu.findItem(R.id.action_done).setEnabled(canEnableOptionsMenu);
        menu.findItem(R.id.action_done).setVisible(canEnableOptionsMenu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mPresenter.handleClickHomeMenuItem();
                return true;
            }
            case R.id.action_done: {
                mPresenter.handleClickDoneMenuItem();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override public void resultAsCancelled(int resultCode) {
        setResult(resultCode);
        finish();
    }

    @Override public void resultAsOk(int resultCode) {
        setResult(resultCode);
        navigate().toLogin();
    }

    @Override public void enableSettingTabelaPrecoPadrao() {
        EventBus.getDefault().post(
                SettingsEvent.create(getString(R.string.key_pref_tabela_preco_padrao)));
    }

    @Override public void showRequiredMessage() {
        new MaterialDialog.Builder(this)
                .content(getString(R.string.message_settings_required))
                .positiveText(android.R.string.ok)
                .show();
    }
}
