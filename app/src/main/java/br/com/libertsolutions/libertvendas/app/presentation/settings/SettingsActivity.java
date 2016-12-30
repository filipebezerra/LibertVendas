package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * @author Filipe Bezerra
 */
public class SettingsActivity extends LibertVendasActivity implements SettingsContract.View {

    private SettingsContract.Presenter mPresenter;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_settings;
    }

    @Override protected void onCreate(@Nullable Bundle inState) {
        mPresenter = new SettingsPresenter(
                PresentationInjection.provideSettingsRepository(this));
        super.onCreate(inState);
        setAsSubActivity();

        if (inState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment, SettingsFragment.newInstance())
                    .commit();
        }

        mPresenter.attachView(this);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.action_done).setVisible(mPresenter.handleMenuVisibility());
        return true;
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
        new MaterialDialog.Builder(this)
                .content(R.string.message_settings_required)
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override public void showInvalidUrlServidorMessage() {
        new MaterialDialog.Builder(this)
                .content(R.string.message_invalid_url_servidor)
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override public void showInvalidUrlServidorComPathApiMessage() {
        new MaterialDialog.Builder(this)
                .content(R.string.message_invalid_url_servidor_com_path_api)
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override public void showInvalidChaveAutenticaoMessage() {
        new MaterialDialog.Builder(this)
                .content(R.string.message_invalid_chave_autenticacao)
                .positiveText(android.R.string.ok)
                .show();
    }

    @Override public void finalizeViewWithSuccess() {
        setResult(Navigator.RESULT_OK);
        finish();
    }

    @Override protected void onStop() {
        mPresenter.detach();
        super.onStop();
    }
}
