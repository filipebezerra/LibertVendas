package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoRepository;
import br.com.libertsolutions.libertvendas.app.data.pedido.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.data.sync.SyncTaskService;
import br.com.libertsolutions.libertvendas.app.data.utils.ApiUtils;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ProdutosSelecionadosEvent;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.resources.FinalizandoPedidoResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FormattingUtils;
import br.com.libertsolutions.libertvendas.app.presentation.utils.ObservableUtils;
import br.com.libertsolutions.libertvendas.app.presentation.utils.StringUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando.NovoPedidoEvent.newEvent;
import static java.util.Collections.emptyList;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class FinalizandoPedidoPresenter extends BasePresenter<FinalizandoPedidoContract.View>
        implements FinalizandoPedidoContract.Presenter {

    private final FormaPagamentoRepository mFormaPagamentoRepository;

    private final PedidoRepository mPedidoRepository;

    private final FinalizandoPedidoResourcesRepository mResourcesRepository;

    private Pedido mPedidoEmEdicao;

    private Calendar mDataEmissao = Calendar.getInstance();

    private List<FormaPagamento> mFormasPagamento;

    private List<ProdutoVo> mProdutosSelecionados;

    private Tabela mTabelaPadrao;

    private Vendedor mVendedorLogado;

    private Cliente mClienteSelecionado;

    FinalizandoPedidoPresenter(
            final FormaPagamentoRepository formaPagamentoRepository,
            final PedidoRepository pedidoRepository,
            final FinalizandoPedidoResourcesRepository resourcesRepository) {
        mFormaPagamentoRepository = formaPagamentoRepository;
        mPedidoRepository = pedidoRepository;
        mResourcesRepository = resourcesRepository;
    }

    @Override public void initializeView(final Pedido pedidoEmEdicao) {
        mPedidoEmEdicao = pedidoEmEdicao;
        initializeViewFields();
        initializeViewRequiredFields();
        displayDataEmissao();
        loadFormasPagamento();
        initializeFieldsIfEmEdicao();
        getLoggedUser();
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
                .toObservable(mFormasPagamento);

        Observable<List<FormaPagamento>> formasPagamentoFromDiskCache = mFormaPagamentoRepository
                .findAll()
                .doOnNext(pFormasPagamentoList -> mFormasPagamento = pFormasPagamentoList);

        addSubscription(Observable
                .concat(formasPagamentoFromMemoryCache, formasPagamentoFromDiskCache)
                .firstOrDefault(emptyList())
                .observeOn(mainThread())
                .subscribe(
                        list -> {
                            getView().displayFormasPagamento(mFormasPagamento);
                            initializeFormasPagamentoFieldIfEmEdicao();
                        },

                        Timber::e
                ));
    }

    private void initializeFormasPagamentoFieldIfEmEdicao() {
        if (isEditing()) {
            int indexOf = mFormasPagamento.indexOf(mPedidoEmEdicao.getFormaPagamento());
            getView().setViewValue(mResourcesRepository.obtainFormaPagamentoViewId(), indexOf);
        }
    }

    private void initializeFieldsIfEmEdicao() {
        if (isEditing()) {
            getView().changeTitle(mResourcesRepository.obtainStringTitleEditandoPedido());

            displayDataEmissao();

            getView().setViewValue(
                    mResourcesRepository.obtainClienteViewId(),
                    mPedidoEmEdicao.getCliente().getNome());

            displayTotalProdutos(calculateTotalProdutosPedido());

            getView().setViewValue(
                    mResourcesRepository.obtainDescontoViewId(),
                    String.valueOf(mPedidoEmEdicao.getDesconto()));

            if (!StringUtils.isNullOrEmpty(mPedidoEmEdicao.getObservacao())) {
                getView().setViewValue(
                        mResourcesRepository.obtainObservacaoViewId(),
                        mPedidoEmEdicao.getObservacao());
            }
        }
    }

    private void getLoggedUser() {
        LoggedUserEvent event = EventBus.getDefault().getStickyEvent(LoggedUserEvent.class);
        mVendedorLogado = event.getVendedor();
    }

    @Subscribe(sticky = true) public void onClienteSelecionadoEvent(ClienteSelecionadoEvent event) {
        mClienteSelecionado = event.getCliente();
        getView().setViewValue(mResourcesRepository.obtainClienteViewId(),
                mClienteSelecionado.getNome());
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Subscribe(sticky = true) public void onProdutosSelecionadosEvent(ProdutosSelecionadosEvent event) {
        mProdutosSelecionados = event.getProdutos();
        mTabelaPadrao = event.getTabela();
        displayTotalProdutos(calculateTotalProdutosSelecionados());
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override public void handleActionSave() {
        getView().hideRequiredMessages();

        if (!getView().hasEmptyRequiredFields()) {
            if (validateDesconto()) {
                addSubscription(mPedidoRepository.save(pedidoFromFields())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::notifyPedidoSalvo,

                                Timber::e
                        )
                );
            }
        } else {
            getView().displayRequiredFieldMessages();
        }
    }

    private boolean validateDesconto() {
        final String descontoStr = getView().getViewStringValue(
                mResourcesRepository.obtainDescontoViewId());

        if (StringUtils.isNullOrEmpty(descontoStr)) {
            return true;
        }

        final double desconto = Double.valueOf(descontoStr);

        if (desconto == 0) {
            return true;
        }

        if (!mVendedorLogado.isAplicaDesconto()) {
            getView().displayValidationErrorForDesconto(
                    mResourcesRepository.obtainStringMessageVendedorNaoPermitidoAplicarDesconto());
            return false;
        }

        int positionFormaPagamento = getView()
                .getViewPositionValue(mResourcesRepository.obtainFormaPagamentoViewId());

        final FormaPagamento formaPagamento = getItemFromList(
                mFormasPagamento, positionFormaPagamento);

        if (formaPagamento == null) {
            return true;
        }

        final float percentualDesconto = formaPagamento.getPercentualDesconto();

        if (percentualDesconto == 0) {
            getView().displayValidationErrorForDesconto(
                    mResourcesRepository.obtainStringMessageValorDescontoNaoPermitido());
            return false;
        }

        final double totalProdutos = calculateTotalProdutosSelecionados();
        final double percentualAplicadoVenda = desconto * 100 / totalProdutos;

        if (percentualAplicadoVenda > percentualDesconto) {
            getView().displayValidationErrorForDesconto(
                    mResourcesRepository.obtainStringMessageValorDescontoNaoPermitido());
            return false;
        }

        return true;
    }

    private double calculateTotalProdutosSelecionados() {
        double totalProdutos = 0;
        for (ProdutoVo vo : mProdutosSelecionados) {
            totalProdutos += vo.getTotalProdutos();
        }
        return totalProdutos;
    }

    private Pedido pedidoFromFields() {
        final long dataEmissao = mDataEmissao.getTimeInMillis();

        String descontoStr = getView().getViewStringValue(
                mResourcesRepository.obtainDescontoViewId());
        final double desconto
                = StringUtils.isNullOrEmpty(descontoStr) ? 0 : Double.valueOf(descontoStr);

        int positionFormaPagamento = getView().getViewPositionValue(
                mResourcesRepository.obtainFormaPagamentoViewId());
        final FormaPagamento formaPagamento
                = getItemFromList(mFormasPagamento, positionFormaPagamento);

        final String observacao = getView().getViewStringValue(
                mResourcesRepository.obtainObservacaoViewId());

        List<ItemPedido> itensPedido = new ArrayList<>();
        for (ProdutoVo vo : mProdutosSelecionados) {
            itensPedido.add(
                    ItemPedido.createNew(
                            vo.getPreco(),
                            vo.getQuantidadeAdicionada(),
                            vo.getTotalProdutos(),
                            vo.getProduto()
                    )
            );
        }

        if (isEditing()) {
            return Pedido.changed(
                    mPedidoEmEdicao,
                    dataEmissao,
                    desconto,
                    observacao,
                    mClienteSelecionado,
                    formaPagamento,
                    itensPedido,
                    ApiUtils.formatApiDateTime(System.currentTimeMillis()),
                    mVendedorLogado.getEmpresaSelecionada().getCnpj(),
                    mVendedorLogado.getCpfCnpj()
            );
        } else {
            return Pedido.createNew(
                    dataEmissao,
                    desconto,
                    formaPagamento,
                    observacao,
                    mClienteSelecionado,
                    mTabelaPadrao,
                    itensPedido,
                    mVendedorLogado.getEmpresaSelecionada().getCnpj(),
                    mVendedorLogado.getCpfCnpj()
            );
        }
    }

    private void notifyPedidoSalvo(final Pedido pPedido) {
        if (isEditing()) {
            getView().resultPedidoEditado(pPedido);
        } else {
            EventBus.getDefault().post(newEvent(pPedido));
            getView().finishView();
        }
        SyncTaskService.schedule(PresentationInjection.provideContext());
    }

    @Override public void handleDataEmissaoTouched() {
        getView().showCalendarPicker(mDataEmissao);
    }

    @Override public void handleDateSelected(final int year, final int month, final int day) {
        mDataEmissao.set(Calendar.YEAR, year);
        mDataEmissao.set(Calendar.MONTH, month);
        mDataEmissao.set(Calendar.DAY_OF_MONTH, day);
        displayDataEmissao();
    }

    private void displayDataEmissao() {
        long dataEmissao = isEditing() ?
                mPedidoEmEdicao.getDataEmissao() : mDataEmissao.getTimeInMillis();
        getView().setViewValue(mResourcesRepository.obtainDataEmissaoViewId(),
                FormattingUtils.convertMillisecondsToDateAsString(dataEmissao));
    }

    private double calculateTotalProdutosPedido() {
        double totalProdutosPedido = 0;
        if (isEditing()) {
            List<ItemPedido> itens = mPedidoEmEdicao.getItens();

            for (ItemPedido item : itens) {
                totalProdutosPedido += item.getSubTotal();
            }
        }
        return totalProdutosPedido;
    }

    private void displayTotalProdutos(double pTotalProdutos) {
        getView().setViewValue(mResourcesRepository.obtainTotalProdutosViewId(),
                FormattingUtils.formatAsDinheiro(pTotalProdutos));
    }

    private <T> T getItemFromList(List<T> list, int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    private boolean isEditing() {
        return mPedidoEmEdicao != null;
    }
}
