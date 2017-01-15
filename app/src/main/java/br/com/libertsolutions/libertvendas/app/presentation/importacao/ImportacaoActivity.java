package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import br.com.libertsolutions.libertvendas.app.DataInjection;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FeedbackHelper;
import butterknife.BindView;
import com.dd.CircularProgressButton;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ImportacaoActivity extends LibertVendasActivity implements ImportacaoContract.View {

    @BindView(R.id.button_sync_cities_state) protected CircularProgressButton mButtonSyncCitiesState;
    @BindView(R.id.recycler_view_sync_items) protected RecyclerView mRecyclerViewSyncItems;

    private SyncItemsAdapter mRecyclerViewAdapter;

    private ImportacaoContract.Presenter mPresenter;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_importacao;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        mPresenter = new ImportacaoPresenter(
                PresentationInjection.provideSettingsRepository(),
                PresentationInjection.provideConnectivityServices(),
                DataInjection.RemoteRepositories.provideFormaPagamentoService(),
                DataInjection.LocalRepositories.provideFormaPagamentoRepository(),
                DataInjection.RemoteRepositories.provideCidadeService(),
                DataInjection.LocalRepositories.provideCidadeRepository(),
                DataInjection.RemoteRepositories.provideClienteService(),
                DataInjection.LocalRepositories.provideClienteRepository(),
                DataInjection.RemoteRepositories.provideTabelaService(),
                DataInjection.LocalRepositories.provideTabelaRepository());
        super.onCreate(inState);
        setAsSubActivity();
        mPresenter.attachView(this);
        mPresenter.initializeView();
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

    @Override protected void onResume() {
        super.onResume();
        mPresenter.startSync();
    }

    @Override public void showSyncItems(final List<Empresa> syncItems) {
        mRecyclerViewAdapter = new SyncItemsAdapter(this, syncItems);
        mRecyclerViewSyncItems.setHasFixedSize(true);
        mRecyclerViewSyncItems.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewSyncItems.setAdapter(mRecyclerViewAdapter);
    }

    @Override public void showSyncCitiesStarted() {
        mButtonSyncCitiesState.setIndeterminateProgressMode(true);
        mButtonSyncCitiesState.setProgress(50);
    }

    @Override public void showSyncCitiesDone() {
        mButtonSyncCitiesState.setProgress(100);
    }

    @Override public void showSyncDone(final Empresa syncItem) {
        mRecyclerViewAdapter.showDone(syncItem);
    }

    @Override public void showSyncCitiesError() {
        mButtonSyncCitiesState.setProgress(-1);
    }

    @Override public void showSyncError(final Empresa syncItem) {
        mRecyclerViewAdapter.showError(syncItem);
    }

    @Override public void showSuccessMessage() {
        FeedbackHelper.showMessageDialog(this, R.string.message_importacao_completada);
    }

    @Override public void showMenu() {
        supportInvalidateOptionsMenu();
    }

    @Override public void hideLoadingWithSuccess() {

    }

    @Override public void hideLoadingWithFail() {

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

    @Override public void finalizeView() {
        finish();
    }

    @Override public void navigateToHome() {
        navigate().toHome();
    }

    @Override protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}
