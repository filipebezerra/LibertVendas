package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import android.os.Parcelable;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeRepository;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeService;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteService;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoRepository;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaRepository;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaService;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.utils.ConnectivityServices;
import java.io.IOException;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.domain.factory.CidadeFactory.toPojoList;
import static br.com.libertsolutions.libertvendas.app.domain.factory.ClienteFactory.toPojoList;
import static br.com.libertsolutions.libertvendas.app.domain.factory.FormaPagamentoFactory.toPojoList;
import static br.com.libertsolutions.libertvendas.app.domain.factory.TabelaFactory.toPojoList;
import static java.util.Collections.emptyList;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class ImportacaoPresenter extends BasePresenter<ImportacaoContract.View>
        implements ImportacaoContract.Presenter {

    private final SettingsRepository mSettingsRepository;

    private final ConnectivityServices mConnectivityServices;

    private final FormaPagamentoService mFormaPagamentoService;

    private final FormaPagamentoRepository mFormaPagamentoRepository;

    private final CidadeService mCidadeService;

    private final CidadeRepository mCidadeRepository;

    private final ClienteService mClienteService;

    private final ClienteRepository mClienteRepository;

    private final TabelaService mTabelaService;

    private final TabelaRepository mTabelaRepository;

    private Vendedor mLoggedUser;

    private int mSyncCompletedCounter;

    private Throwable mErrorMakingNetworkCall;

    ImportacaoPresenter(
            final SettingsRepository settingsRepository,
            final ConnectivityServices connectivityServices,
            final FormaPagamentoService formaPagamentoService,
            final FormaPagamentoRepository formaPagamentoRepository,
            final CidadeService cidadeService, final CidadeRepository cidadeRepository,
            final ClienteService clienteService, final ClienteRepository clienteRepository,
            final TabelaService tabelaService, final TabelaRepository tabelaRepository) {
        mSettingsRepository = settingsRepository;
        mConnectivityServices = connectivityServices;
        mFormaPagamentoService = formaPagamentoService;
        mFormaPagamentoRepository = formaPagamentoRepository;
        mCidadeService = cidadeService;
        mCidadeRepository = cidadeRepository;
        mClienteService = clienteService;
        mClienteRepository = clienteRepository;
        mTabelaService = tabelaService;
        mTabelaRepository = tabelaRepository;
    }

    @Override public void initializeView() {
        LoggedUserEvent event = EventBus.getDefault().getStickyEvent(LoggedUserEvent.class);
        mLoggedUser = event.getVendedor();
        getView().showSyncItems(mLoggedUser.getEmpresas());
    }

    @Override public boolean handleMenuVisibility() {
        return mSettingsRepository.isInitialDataImportationDone();
    }

    @Override public void handleActionDone() {
        getView().navigateToHome();
    }

    @Override public void startSync() {
        if (!mConnectivityServices.isOnline()) {
            getView().showOfflineMessage();
            return;
        }

        mSyncCompletedCounter = 0;
        syncCities();

        for (Empresa empresa : mLoggedUser.getEmpresas()) {
            syncItem(empresa);
        }
    }

    private void syncCities() {
        addSubscription(mCidadeService.get()
                .filter(dtoList -> !dtoList.isEmpty())
                .flatMap(dtoList -> mCidadeRepository.saveAll(toPojoList(dtoList)))
                .observeOn(mainThread())
                .subscribe(
                        new Subscriber<List<Cidade>>() {
                            @Override public void onStart() {
                                getView().showSyncCitiesStarted();
                            }

                            @Override public void onError(final Throwable e) {
                                Timber.e(e, "Could not sync cities");
                                getView().showSyncCitiesError();
                            }

                            @Override public void onNext(final List<Cidade> cidades) {}

                            @Override public void onCompleted() {
                                getView().showSyncCitiesDone();
                                mSyncCompletedCounter++;
                                checkAllSyncCompleted();
                            }
                        }
                ));
    }

    private void syncItem(Empresa empresa) {
        addSubscription(Observable
                .merge(getFormasPagamento(empresa), getClientes(empresa), getTabelas(empresa))
                .lastOrDefault(emptyList())
                .observeOn(mainThread())
                .subscribe(
                        new Subscriber<List<? extends Parcelable>>() {
                            @Override public void onError(final Throwable e) {
                                Timber.e(e, "Could not sync data from %s", empresa.getNome());
                                //mErrorMakingNetworkCall = e;
                                //getView().hideLoadingWithFail();
                                getView().showSyncError(empresa);
                            }

                            @Override public void onNext(
                                    final List<? extends Parcelable> parcelables) {}

                            @Override public void onCompleted() {
                                //getView().hideLoadingWithSuccess();
                                getView().showSyncDone(empresa);
                                mSyncCompletedCounter++;
                                checkAllSyncCompleted();
                            }
                        }
                ));
    }

    private Observable<List<FormaPagamento>> getFormasPagamento(Empresa empresa) {
       return  mFormaPagamentoService
               .get(empresa.getCnpj())
               .filter(dtoList -> !dtoList.isEmpty())
               .flatMap(dtoList ->
                       mFormaPagamentoRepository.saveAll(
                               toPojoList(dtoList, mLoggedUser.getCpfCnpj(), empresa.getCnpj())));
    }

    private Observable<List<Cliente>> getClientes(Empresa empresa) {
        return mClienteService
                .get(empresa.getCnpj())
                .filter(dtoList -> !dtoList.isEmpty())
                .flatMap(dtoList ->
                        mClienteRepository.saveAll(
                                toPojoList(dtoList, mLoggedUser.getCpfCnpj(), empresa.getCnpj())));
    }

    private Observable<List<Tabela>> getTabelas(Empresa empresa) {
        return mTabelaService
                .get(empresa.getCnpj())
                .filter(dtoList -> !dtoList.isEmpty())
                .flatMap(dtoList ->
                        mTabelaRepository.saveAll(
                                toPojoList(dtoList, mLoggedUser.getCpfCnpj(), empresa.getCnpj())));
    }

    private void checkAllSyncCompleted() {
        if (mSyncCompletedCounter == (mLoggedUser.getEmpresas().size() + 1)) {
            mSettingsRepository.setInitialDataImportationDone();
            getView().showMenu();
        }
    }

    @Override public void handleAnimationEnd(final boolean success) {
        if (success) {
            getView().showMenu();
            getView().showSuccessMessage();
        } else {
            showError();
        }
    }

    private void showError() {
        if (mErrorMakingNetworkCall instanceof HttpException) {
            getView().showServerError();
        } else if (mErrorMakingNetworkCall instanceof IOException) {
            getView().showNetworkError();
        } else {
            getView().showUnknownError();
        }
    }

    @Override public void cancel() {
        clearSubscriptions();
        getView().finalizeView();
    }
}
