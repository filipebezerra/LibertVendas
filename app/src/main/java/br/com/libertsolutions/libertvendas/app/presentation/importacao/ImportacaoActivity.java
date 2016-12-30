package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.DataInjection;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FeedbackHelper;
import butterknife.BindView;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

/**
 * @author Filipe Bezerra
 */
public class ImportacaoActivity extends LibertVendasActivity implements ImportacaoContract.View,
        AnimatedCircleLoadingView.AnimationListener {

    @BindView(R.id.loading_view) protected AnimatedCircleLoadingView mLoadingView;

    private ImportacaoContract.Presenter mPresenter;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_importacao;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        mPresenter = new ImportacaoPresenter(
                PresentationInjection.provideSettingsRepository(this),
                PresentationInjection.provideConnectivityServices(this),
                DataInjection.RemoteRepositories.provideFormaPagamentoService(this),
                DataInjection.LocalRepositories.provideFormaPagamentoRepository(),
                DataInjection.RemoteRepositories.provideCidadeService(this),
                DataInjection.LocalRepositories.provideCidadeRepository(),
                DataInjection.RemoteRepositories.provideClienteService(this),
                DataInjection.LocalRepositories.provideClienteRepository(),
                DataInjection.RemoteRepositories.provideTabelaService(this),
                DataInjection.LocalRepositories.provideTabelaRepository());
        super.onCreate(inState);
        setAsSubActivity();
        mLoadingView.setAnimationListener(this);
        mPresenter.attachView(this);
    }

    @Override public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_importacao, menu);
        return true;
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_done).setVisible(mPresenter.handleMenuVisibility());
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done: {
                mPresenter.handleActionDone();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onStart() {
        super.onStart();
        mPresenter.registerEventBus();
    }

    @Override public void onStop() {
        super.onStop();
        mPresenter.unregisterEventBus();
    }

    @Override protected void onResume() {
        super.onResume();
        mPresenter.startSync();
    }

    @Override public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.resetLoading();
        mLoadingView.startIndeterminate();
    }

    @Override public void showSuccessMessage() {
        FeedbackHelper.showMessageDialog(this, R.string.message_importacao_completada);
    }

    @Override public void showMenu() {
        supportInvalidateOptionsMenu();
    }

    @Override public void hideLoadingWithSuccess() {
        mLoadingView.stopOk();
    }

    @Override public void hideLoadingWithFail() {
        mLoadingView.stopFailure();
    }

    @Override public void showOfflineMessage() {
        FeedbackHelper.showOfflineMessage(this,
                (dialog, which) -> mPresenter.startSync(),
                (dialog, which) -> mPresenter.cancel()
        );
    }

    @Override public void showServerError() {
        FeedbackHelper.showRetryDialogMessage(this, R.string.message_server_error,
                (dialog, which) -> mPresenter.startSync(),
                (dialog, which) -> mPresenter.cancel());
    }

    @Override public void showNetworkError() {
        FeedbackHelper.showRetryDialogMessage(this, R.string.message_network_error,
                (dialog, which) -> mPresenter.startSync(),
                (dialog, which) -> mPresenter.cancel());
    }

    @Override public void showUnknownError() {
        FeedbackHelper.showRetryDialogMessage(this, R.string.message_unknown_error,
                (dialog, which) -> mPresenter.startSync(),
                (dialog, which) -> mPresenter.cancel());
    }

    @Override public void onAnimationEnd(final boolean success) {
        mPresenter.handleAnimationEnd(success);
    }

    @Override public void finalizeView() {
        finish();
    }

    @Override public void finalizeViewWithSuccess() {
        setResult(Navigator.RESULT_OK);
        finish();
    }

    @Override protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}
