package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.resources.FinalizaPedidoResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Filipe Bezerra
 */
class FinalizaPedidoPresenter implements FinalizaPedidoContract.Presenter {
    private final FinalizaPedidoContract.View mView;

    private final Repository<FormaPagamento> mFormaPagamentoRepository;

    private final Repository<Pedido> mPedidoRepository;

    private final FinalizaPedidoResourcesRepository mResourcesRepository;

    private FinalizaPedidoViewModel mViewModel;

    private List<FormaPagamento> mFormaPagamentoList;

    private Calendar mDataEmissao = Calendar.getInstance();

    private List<ProdutoVo> mProdutosSelecionados;

    private TabelaPreco mTabelaPrecoPadrao;

    private Cliente mClienteSelecionado;

    private Vendedor mUsuarioLogado;

    FinalizaPedidoPresenter(
            FinalizaPedidoContract.View pView,
            Repository<FormaPagamento> pFormaPagamentoRepository,
            Repository<Pedido> pPedidoRepository,
            FinalizaPedidoResourcesRepository pResourcesRepository) {
        mView = pView;
        mFormaPagamentoRepository = pFormaPagamentoRepository;
        mPedidoRepository = pPedidoRepository;
        mResourcesRepository = pResourcesRepository;
    }

    @Override public void initializeView(
            FinalizaPedidoViewModel pViewModel,
            ProdutosSelecionadosArgumentExtractor pProdutosSelecionadosExtractor,
            TabelaPrecoPadraoArgumentExtractor pTabelaPrecoPadraoExtractor) {
        mViewModel = pViewModel;
        mViewModel.dataEmissao(formatDataEmissao());

        mTabelaPrecoPadrao = pTabelaPrecoPadraoExtractor.extractExtra();
        mProdutosSelecionados = pProdutosSelecionadosExtractor.extractExtra();
        double totalProdutos = 0;
        for (ProdutoVo vo : mProdutosSelecionados) {
            totalProdutos += vo.getTotalProdutos();
        }

        mViewModel.totalProdutos(FormattingUtils.formatAsQuantidade(totalProdutos));

        mFormaPagamentoRepository
                .list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pFormaPagamentos -> {
                            mFormaPagamentoList = pFormaPagamentos;
                            mViewModel.formasPagamento(pFormaPagamentos);
                        }
                );
    }

    @Override public void clickActionSave() {
        mView.hideRequiredMessages();

        if (!shownViewModelErrors()) {
            //TODO: Validar se vendedor pode aplicar desconto

            //TODO: Validar se valor do desconto é válido/permitido

            List<ItemPedido> itensPedido = new ArrayList<>();
            for (ProdutoVo vo : mProdutosSelecionados) {
                itensPedido.add(
                        ItemPedido.novoItem(
                                vo.getPreco(),
                                vo.getQuantidadeAdicionada(),
                                vo.getTotalProdutos(),
                                vo.getProduto()
                        )
                );
            }

            final double desconto = mViewModel.hasDesconto() ?
                    Double.valueOf(mViewModel.desconto()) : 0;

            final FormaPagamento formaPagamento = mViewModel.formaPagamento();

            Pedido novoPedido = Pedido.novoPedido(
                    mDataEmissao.getTimeInMillis(),
                    desconto,
                    formaPagamento.getPercentualDesconto(),
                    mViewModel.observacao(),
                    mClienteSelecionado,
                    formaPagamento,
                    mTabelaPrecoPadrao,
                    itensPedido
            );

            mPedidoRepository
                    .save(novoPedido)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mView::resultNovoPedido);
        }
    }

    private boolean shownViewModelErrors() {
        if (!mViewModel.hasRequiredValues()) {
            if (!mViewModel.hasDataEmissao()) {
                mView.displayRequiredMessageForDataEmissao();
            }

            if (!mViewModel.hasFormaPagamento()) {
                mView.displayRequiredMessageForFormaPagamento();
            }

            if (!mViewModel.hasCliente()) {
                mView.displayRequiredMessageForCliente();
            }

            mView.showFeedbackMessage(mResourcesRepository.obtainStringMessageFieldsRequired());
            return true;
        } else {
            return false;
        }
    }

    @Override public void clickSelectCliente() {
        mView.navigateToListaClientes();
    }

    @Override public void clickSelectDataEmissao() {
        mView.showCalendarPicker(mDataEmissao);
    }

    @Override public void setDataEmissao(int pYear, int pMonthOfYear, int pDayOfMonth) {
        mDataEmissao.set(Calendar.YEAR, pYear);
        mDataEmissao.set(Calendar.MONTH, pMonthOfYear);
        mDataEmissao.set(Calendar.DAY_OF_MONTH, pDayOfMonth);
        mViewModel.dataEmissao(formatDataEmissao());
    }

    @Override public void handleClienteSelecionadoEvent(Cliente pCliente) {
        mClienteSelecionado = pCliente;
        mViewModel.cliente(mClienteSelecionado.getNome());
    }

    @Override public void handleUsuarioLogadoEvent(Vendedor pVendedor) {
        mUsuarioLogado = pVendedor;
    }

    private String formatDataEmissao() {
        return FormattingUtils.convertMillisecondsToDateAsString(mDataEmissao.getTimeInMillis());
    }
}
