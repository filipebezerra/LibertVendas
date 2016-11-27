package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.util.FeedbackHelper;
import butterknife.BindView;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import static br.com.libertsolutions.libertvendas.app.R.string.importacao;
import static br.com.libertsolutions.libertvendas.app.R.string.message_importacao_completada;
import static br.com.libertsolutions.libertvendas.app.R.string.message_network_error;
import static br.com.libertsolutions.libertvendas.app.R.string.message_unknown_error;
import static br.com.libertsolutions.libertvendas.app.presentation.util.AndroidUtils.isDeviceConnected;

/**
 * @author Filipe Bezerra
 */
public class ImportacaoActivity extends LibertVendasActivity
        implements ImportacaoContract.View, AnimatedCircleLoadingView.AnimationListener {

    @BindView(R.id.loading_view) protected AnimatedCircleLoadingView mLoadingView;

    private ImportacaoContract.Presenter mPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new ImportacaoPresenter(
                Injection.provideImportacaoRepository(this),
                Injection.provideFormaPagamentoService(this),
                Injection.provideFormaPagamentoRepository(),
                Injection.provideCidadeService(this),
                Injection.provideCidadeRepository(),
                Injection.provideProdutoService(this),
                Injection.provideProdutoRepository(),
                Injection.provideClienteService(this),
                Injection.provideClienteRepository(),
                Injection.provideTabelaPrecoService(this),
                Injection.provideTabelaPrecoRepository(),
                Injection.provideImportacaoResourcesRepository(this),
                Injection.provideSettingsRepository(this));
        super.onCreate(savedInstanceState);
        setAsSubActivity();
        mLoadingView.setAnimationListener(this);
        mPresenter.attachView(this);
    }

    @Override protected int provideContentViewResource() {
        return R.layout.activity_importacao;
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_importacao, menu);
        return true;
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        return mPresenter.isSyncDone();
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done: {
                mPresenter.handleClickDoneMenuItem();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onStart() {
        super.onStart();
        mPresenter.registerForEvents();
    }

    @Override public void onStop() {
        super.onStop();
        mPresenter.unregisterForEvents();
    }

    @Override protected void onResume() {
        super.onResume();
        requestSyncToPresenter();
    }

    @Override public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.resetLoading();
        mLoadingView.startIndeterminate();
    }

    @Override public void showSuccessMessage() {
        FeedbackHelper.showMessageDialog(this, message_importacao_completada);
    }

    @Override public void hideLoadingWithSuccess() {
        mLoadingView.stopOk();
    }

    @Override public void hideLoadingWithFail() {
        mLoadingView.stopFailure();
    }

    @Override public void showMessageDialog(String pMessage) {
        FeedbackHelper.showMessageDialog(this, pMessage);
    }

    @Override public void showDeviceNotConnectedError() {
        FeedbackHelper.showOfflineMessage(this,
                (dialog, which) -> requestSyncToPresenter(),
                (dialog, which) -> mPresenter.handleCancelOnSyncError());
    }

    @Override public void showServerError() {
        FeedbackHelper.showRetryDialogMessage(this,
                getString(R.string.message_server_error, getString(importacao)),
                (dialog, which) -> requestSyncToPresenter(),
                (dialog, which) -> mPresenter.handleCancelOnSyncError());
    }

    @Override public void showNetworkError() {
        FeedbackHelper.showRetryDialogMessage(this,
                getString(message_network_error, getString(importacao)),
                (dialog, which) -> requestSyncToPresenter(),
                (dialog, which) -> mPresenter.handleCancelOnSyncError());
    }

    @Override public void showUnknownError() {
        FeedbackHelper.showRetryDialogMessage(this,
                getString(message_unknown_error, getString(importacao)),
                (dialog, which) -> requestSyncToPresenter(),
                (dialog, which) -> mPresenter.handleCancelOnSyncError());
    }

    private void requestSyncToPresenter() {
        mPresenter.startSync(isDeviceConnected(this));
    }

    @Override public void invalidateMenu() {
        invalidateOptionsMenu();
    }

    @Override public void navigateToMainActivity() {
        navigate().toHome();
    }

    @Override public void finishActivity() {
        finish();
    }

    @Override public void onAnimationEnd(boolean success) {
        mPresenter.handleAnimationEnd(success);
    }

}
