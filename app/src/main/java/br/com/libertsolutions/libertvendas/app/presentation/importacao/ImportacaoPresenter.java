package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeRepository;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeService;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteService;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoRepository;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaRepository;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaService;
import br.com.libertsolutions.libertvendas.app.domain.factory.CidadeFactory;
import br.com.libertsolutions.libertvendas.app.domain.factory.ClienteFactory;
import br.com.libertsolutions.libertvendas.app.domain.factory.FormaPagamentoFactory;
import br.com.libertsolutions.libertvendas.app.domain.factory.TabelaFactory;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.utils.ConnectivityServices;
import java.io.IOException;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import timber.log.Timber;

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

        getView().showLoading();

        LoggedUserEvent event = EventBus.getDefault().getStickyEvent(LoggedUserEvent.class);
        final String cnpjEmpresa = event.getVendedor().getEmpresaSelecionada().getCnpj();

        Observable<List<FormaPagamento>> getFormasPagamento = mFormaPagamentoService
                .get(cnpjEmpresa)
                .filter(list -> !list.isEmpty())
                .flatMap(data -> mFormaPagamentoRepository
                        .saveAll(FormaPagamentoFactory.createListFormaPagamento(data)));

        Observable<List<Cidade>> getCidades = mCidadeService
                .get()
                .filter(list -> !list.isEmpty())
                .flatMap(data -> mCidadeRepository
                        .saveAll(CidadeFactory.createListCidade(data)));

        Observable<List<Cliente>> getClientes = mClienteService
                .get(cnpjEmpresa)
                .filter(list -> !list.isEmpty())
                .flatMap(data -> mClienteRepository
                        .saveAll(ClienteFactory.createListCliente(data)));

        Observable<List<Tabela>> getTabelas = mTabelaService
                .get(cnpjEmpresa)
                .filter(list -> !list.isEmpty())
                .flatMap(data -> mTabelaRepository
                        .saveAll(TabelaFactory.createListTabelaPreco(data)));

        addSubscription(Observable
                .merge(getFormasPagamento, getCidades, getClientes, getTabelas)
                .observeOn(mainThread())
                .lastOrDefault(emptyList())
                .subscribe(
                        pResult -> {
                            mSettingsRepository.setInitialDataImportationDone();
                            getView().hideLoadingWithSuccess();
                        },

                        e -> {
                            Timber.e(e);
                            mErrorMakingNetworkCall = e;
                            getView().hideLoadingWithFail();
                        }
                ));
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
