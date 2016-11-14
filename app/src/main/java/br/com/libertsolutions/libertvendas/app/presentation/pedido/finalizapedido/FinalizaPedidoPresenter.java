package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.NavigateToNextEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Filipe Bezerra
 */
class FinalizaPedidoPresenter implements FinalizaPedidoContract.Presenter {
    private final FinalizaPedidoContract.View mView;

    private final Repository<FormaPagamento> mFormaPagamentoRepository;

    private FinalizaPedidoViewModel mViewModel;

    private List<FormaPagamento> mFormaPagamentoList;

    private Calendar mDataEmissao = Calendar.getInstance();

    private Cliente mClienteSelecionado;

    FinalizaPedidoPresenter(
            FinalizaPedidoContract.View pView,
            Repository<FormaPagamento> pFormaPagamentoRepository) {
        mView = pView;
        mFormaPagamentoRepository = pFormaPagamentoRepository;
    }

    @Override public void initializeView(FinalizaPedidoViewModel pViewModel,
            ProdutosSelecionadosArgumentExtractor pProdutosSelecionadosExtractor) {
        mViewModel = pViewModel;
        mViewModel.dataEmissao(formatDataEmissao());

        List<ProdutoVo> produtoVos = pProdutosSelecionadosExtractor.extractExtra();
        double totalProdutos = 0;
        for (ProdutoVo vo : produtoVos) {
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
        EventBus.getDefault().post(NavigateToNextEvent.notifyEvent());
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

    private String formatDataEmissao() {
        return FormattingUtils.convertMillisecondsToDateAsString(mDataEmissao.getTimeInMillis());
    }
}
