package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeService;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteService;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.importacao.ImportacaoRepository;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoService;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.tabelaspreco.TabelaPrecoService;
import br.com.libertsolutions.libertvendas.app.domain.factory.CidadeFactory;
import br.com.libertsolutions.libertvendas.app.domain.factory.ClienteFactories;
import br.com.libertsolutions.libertvendas.app.domain.factory.FormaPagamentoFactory;
import br.com.libertsolutions.libertvendas.app.domain.factory.ProdutoFactories;
import br.com.libertsolutions.libertvendas.app.domain.factory.TabelaPrecoFactory;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.events.UsuarioLogadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ImportacaoResourcesRepository;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
class ImportacaoPresenter extends BasePresenter<ImportacaoContract.View>
        implements ImportacaoContract.Presenter {

    private final ImportacaoRepository mImportacaoRepository;

    private final FormaPagamentoService mFormaPagamentoService;

    private final Repository<FormaPagamento> mFormaPagamentoRepository;

    private final CidadeService mCidadeService;

    private final Repository<Cidade> mCidadeRepository;

    private final ProdutoService mProdutoService;

    private final Repository<Produto> mProdutoRepository;

    private final ClienteService mClienteService;

    private final Repository<Cliente> mClienteRepository;

    private final TabelaPrecoService mTabelaPrecoService;

    private final Repository<TabelaPreco> mTabelaPrecoRepository;

    private final ImportacaoResourcesRepository mResourcesRepository;

    private final SettingsRepository mSettingsRepository;

    private boolean mIsDoingInitialDataSync = false;

    private Vendedor mUsuarioLogado;

    private Empresa mEmpresaLogada;

    private Throwable mErrorMakingNetworkCall;

    ImportacaoPresenter(
            ImportacaoRepository pImportacaoRepository,
            FormaPagamentoService pFormaPagamentoService,
            Repository<FormaPagamento> pFormaPagamentoRepository,
            CidadeService pCidadeService, Repository<Cidade> pCidadeRepository,
            ProdutoService pProdutoService, Repository<Produto> pProdutoRepository,
            ClienteService pClienteService, Repository<Cliente> pClienteRepository,
            TabelaPrecoService pTabelaPrecoService,
            Repository<TabelaPreco> pTabelaPrecoRepository,
            ImportacaoResourcesRepository pResourcesRepository,
            SettingsRepository pSettingsRepository) {
        mImportacaoRepository = pImportacaoRepository;
        mFormaPagamentoService = pFormaPagamentoService;
        mFormaPagamentoRepository = pFormaPagamentoRepository;
        mCidadeService = pCidadeService;
        mCidadeRepository = pCidadeRepository;
        mProdutoService = pProdutoService;
        mProdutoRepository = pProdutoRepository;
        mClienteService = pClienteService;
        mClienteRepository = pClienteRepository;
        mTabelaPrecoService = pTabelaPrecoService;
        mTabelaPrecoRepository = pTabelaPrecoRepository;
        mResourcesRepository = pResourcesRepository;
        mSettingsRepository = pSettingsRepository;
    }

    @Override public void attachView(ImportacaoContract.View pView) {
        super.attachView(pView);
        if (mImportacaoRepository.isImportacaoInicialFeita()) {
            getView().navigateToMainActivity();
            getView().finishActivity();
        }
    }

    @Subscribe(threadMode = MAIN, sticky = true) public void onUsuarioLogadoEvent(
            UsuarioLogadoEvent pEvent) {
        mUsuarioLogado = pEvent.getVendedor();
        mEmpresaLogada = pEvent.getEmpresa();
    }

    @Override public void startSync(boolean pDeviceConnected) {
        if (pDeviceConnected) {
            getView().showLoading();
            requestImportacao();
        } else {
            getView().showDeviceNotConnectedError();
        }
    }

    private void requestImportacao() {
        mIsDoingInitialDataSync = true;

        if (mEmpresaLogada == null) {
            getView().showMessageDialog(
                    mResourcesRepository.obtainStringMessageVendedorSemEmpresaLogada());
            return;
        }

        final String cnpjEmpresa = mEmpresaLogada.getCnpj();

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

        Observable<List<Produto>> getProdutos = mProdutoService
                .get(cnpjEmpresa)
                .filter(list -> !list.isEmpty())
                .flatMap(data -> mProdutoRepository
                        .saveAll(ProdutoFactories.createListProduto(data)));

        Observable<List<Cliente>> getClientes = mClienteService
                .get(cnpjEmpresa)
                .filter(list -> !list.isEmpty())
                .flatMap(data -> mClienteRepository
                        .saveAll(ClienteFactories.createListCliente(data)));

        Observable<List<TabelaPreco>> getTabelasPreco = mTabelaPrecoService
                .get(cnpjEmpresa)
                .filter(list -> !list.isEmpty())
                .flatMap(data -> mTabelaPrecoRepository
                        .saveAll(TabelaPrecoFactory.createListTabelaPreco(data)));

        addSubscription(Observable
                .merge(
                        getFormasPagamento,
                        getCidades,
                        getProdutos,
                        getClientes,
                        getTabelasPreco)
                .observeOn(AndroidSchedulers.mainThread())
                .lastOrDefault(Collections.emptyList())
                .subscribe(
                        pResult -> {
                            mImportacaoRepository.setImportacaoInicialComoFeita();
                            getView().hideLoadingWithSuccess();
                        },

                        e -> {
                            Timber.e(e);
                            mErrorMakingNetworkCall = e;
                            getView().hideLoadingWithFail();
                        }
                ));
    }

    @Override public void handleClickDoneMenuItem() {
        if (mIsDoingInitialDataSync) {
            getView().navigateToMainActivity();
        } else {
            getView().finishActivity();
        }
    }

    @Override public boolean isSyncDone() {
        return mImportacaoRepository.isImportacaoInicialFeita();
    }

    @Override public void handleCancelOnSyncError() {
        getView().finishActivity();
    }

    @Override public void handleAnimationEnd(boolean pSuccess) {
        if (pSuccess) {
            getView().showSuccessMessage();
            getView().invalidateMenu();
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

}
