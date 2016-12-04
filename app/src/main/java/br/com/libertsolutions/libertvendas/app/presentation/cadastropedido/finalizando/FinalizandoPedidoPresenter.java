package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoRepository;
import br.com.libertsolutions.libertvendas.app.data.pedidos.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ProdutosSelecionadosEvent;
import br.com.libertsolutions.libertvendas.app.presentation.resources.FinalizandoPedidoResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.util.ObservableUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
class FinalizandoPedidoPresenter extends BasePresenter<FinalizandoPedidoContract.View>
        implements FinalizandoPedidoContract.Presenter {

    private final FormaPagamentoRepository mFormaPagamentoRepository;

    private final PedidoRepository mPedidoRepository;

    private final FinalizandoPedidoResourcesRepository mResourcesRepository;

    private final SettingsRepository mSettingsRepository;

    private Pedido mPedidoEmEdicao;

    private Calendar mDataEmissao = Calendar.getInstance();

    private Cliente mClienteSelecionado;

    private TabelaPreco mTabelaPrecoPadrao;

    private List<ProdutoVo> mProdutosSelecionados;

    private List<FormaPagamento> mFormasPagamentoList;

    FinalizandoPedidoPresenter(FinalizandoPedidoDependencyContainer pDependencyContainer) {
        mFormaPagamentoRepository = pDependencyContainer.getFormaPagamentoRepository();
        mPedidoRepository = pDependencyContainer.getPedidoRepository();
        mResourcesRepository = pDependencyContainer.getResourcesRepository();
        mSettingsRepository = pDependencyContainer.getSettingsRepository();
    }

    @Override public void initializeView(Pedido pPedidoFromExtra) {
        mPedidoEmEdicao = pPedidoFromExtra;
        initializeViewFields();
        initializeViewRequiredFields();
        loadFormasPagamento();
    }

    private void initializeViewFields() {
        List<Integer> viewIds = new ArrayList<>();
        viewIds.add(mResourcesRepository.obtainDataEmissaoViewId());
        viewIds.add(mResourcesRepository.obtainClienteViewId());
        viewIds.add(mResourcesRepository.obtainTotalProdutosViewId());
        viewIds.add(mResourcesRepository.obtainDescontoViewId());
        viewIds.add(mResourcesRepository.obtainFormaPagamentoViewId());
        viewIds.add(mResourcesRepository.obtainObservacaoViewId());
        getView().setViewFields(viewIds);
    }

    private void initializeViewRequiredFields() {
        List<Integer> requiredViewIds = new ArrayList<>();
        requiredViewIds.add(mResourcesRepository.obtainDataEmissaoViewId());
        requiredViewIds.add(mResourcesRepository.obtainClienteViewId());
        requiredViewIds.add(mResourcesRepository.obtainFormaPagamentoViewId());
        getView().setRequiredFields(requiredViewIds);
    }

    private void loadFormasPagamento() {
        Observable<List<FormaPagamento>> formasPagamentoFromMemoryCache = ObservableUtils
                .toObservable(mFormasPagamentoList);

        Observable<List<FormaPagamento>> formasPagamentoFromDiskCache = mFormaPagamentoRepository
                .list()
                .doOnNext(pFormasPagamentoList -> mFormasPagamentoList = pFormasPagamentoList);

        addSubscription(Observable
                .concat(formasPagamentoFromMemoryCache, formasPagamentoFromDiskCache)
                .firstOrDefault(Collections.emptyList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pFormasPagamentoList -> {
                            getView().displayFormasPagamento(mFormasPagamentoList);
                            initializeFormasPagamentoFieldIfEmEdicao();
                        },

                        Timber::e
                ));
    }

    private void initializeFormasPagamentoFieldIfEmEdicao() {
        if (isEditing()) {
            int indexOf = mFormasPagamentoList.indexOf(mPedidoEmEdicao.getFormaPagamento());
            getView().setViewValue(mResourcesRepository.obtainFormaPagamentoViewId(), indexOf);
        }
    }

    @Subscribe(sticky = true) public void onProdutosSelecionadosEvent(
            ProdutosSelecionadosEvent pEvent) {
        List<ProdutoVo> produtoVoList = pEvent.getProdutoVoList();
        TabelaPreco tabelaPreco = pEvent.getTabelaPreco();
        if ((produtoVoList != null && !produtoVoList.isEmpty()) && tabelaPreco != null) {
            mProdutosSelecionados = produtoVoList;
            mTabelaPrecoPadrao = tabelaPreco;
        }
    }

    @Subscribe(sticky = true) public void onClienteSelecionadoEvent(
            ClienteSelecionadoEvent pEvent) {
        Cliente cliente = pEvent.getCliente();
        if (cliente != null) {
            mClienteSelecionado = cliente;
        }
    }

    private boolean isEditing() {
        return mPedidoEmEdicao != null;
    }
}
