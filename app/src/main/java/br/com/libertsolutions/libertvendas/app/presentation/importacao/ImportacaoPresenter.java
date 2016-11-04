package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.factory.FormaPagamentoFactory;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.io.IOException;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
class ImportacaoPresenter implements ImportacaoContract.Presenter {
    private final ImportacaoContract.View mView;

    private final FormaPagamentoService mFormaPagamentoService;

    private final Repository<FormaPagamento> mFormaPagamentoRepository;

    private boolean mIsDoingInitialDataSync = false;

    private Throwable mErrorMakingNetworkCall;

    ImportacaoPresenter(
            ImportacaoContract.View pView,
            FormaPagamentoService pFormaPagamentoService,
            Repository<FormaPagamento> pFormaPagamentoRepository) {
        mView = pView;
        mFormaPagamentoService = pFormaPagamentoService;
        mFormaPagamentoRepository = pFormaPagamentoRepository;
    }

    @Override public void startSync(boolean deviceConnected) {
        if (deviceConnected) {
            mView.showLoading();
            requestImportacao();
        } else {
            mView.showDeviceNotConnectedError();
        }
    }

    private void requestImportacao() {
        mIsDoingInitialDataSync = true;

        mFormaPagamentoService
                .get("18285835000109")
                .filter(list -> !list.isEmpty())
                .flatMap(data -> mFormaPagamentoRepository
                        .saveAll(FormaPagamentoFactory.createListFormaPagamento(data)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pResult -> {
                            mView.hideLoadingWithSuccess();
                        },

                        e -> {
                            Timber.e(e);
                            mErrorMakingNetworkCall = e;
                            mView.hideLoadingWithFail();
                        }
                );
    }

    @Override public void handleClickDoneMenuItem() {
        if (mIsDoingInitialDataSync) {
            mView.navigateToMainActivity();
        } else {
            mView.finishActivity();
        }
    }

    @Override public boolean isSyncDone() {
        return true;
        //return mDataSyncRepository.isInitialDataSynced();
    }

    @Override public void handleCancelOnSyncError() {
        mView.finishActivity();
    }

    @Override public void handleAnimationEnd(boolean success) {
        if (success) {
            mView.showSuccessMessage();
            mView.invalidateMenu();
        } else {
            showError();
        }
    }

    private void showError() {
        if (mErrorMakingNetworkCall instanceof HttpException) {
            mView.showServerError();
        } else if (mErrorMakingNetworkCall instanceof IOException) {
            mView.showNetworkError();
        } else {
            mView.showUnknownError();
        }
    }
}
