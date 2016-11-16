package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
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

    private FinalizaPedidoViewModel mViewModel;

    private List<FormaPagamento> mFormaPagamentoList;

    private Calendar mDataEmissao = Calendar.getInstance();

    private List<ProdutoVo> mProdutosSelecionados;

    private Cliente mClienteSelecionado;

    private Vendedor mUsuarioLogado;

    FinalizaPedidoPresenter(
            FinalizaPedidoContract.View pView,
            Repository<FormaPagamento> pFormaPagamentoRepository,
            Repository<Pedido> pPedidoRepository) {
        mView = pView;
        mFormaPagamentoRepository = pFormaPagamentoRepository;
        mPedidoRepository = pPedidoRepository;
    }

    @Override public void initializeView(FinalizaPedidoViewModel pViewModel,
            ProdutosSelecionadosArgumentExtractor pProdutosSelecionadosExtractor) {
        mViewModel = pViewModel;
        mViewModel.dataEmissao(formatDataEmissao());

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

        Pedido novoPedido = Pedido.novoPedido(
                mDataEmissao.getTimeInMillis(),
                0, //desconto
                0, //percentualDesconto
                "", // observacao
                mClienteSelecionado,
                null, //forma pagamento selecionada,
                null, // tabela
                itensPedido
        );

        mPedidoRepository
                .save(novoPedido)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::resultNovoPedido);
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
