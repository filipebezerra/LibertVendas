package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido.NovoPedidoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.resources.FinalizaPedidoResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.android.schedulers.AndroidSchedulers;

import static br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.FinalizandoPedidoContract.Presenter;
import static br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.FinalizandoPedidoContract.View;

/**
 * @author Filipe Bezerra
 */
class FinalizandoPedidoPresenter extends BasePresenter<View> implements Presenter {

    private final Repository<FormaPagamento> mFormaPagamentoRepository;

    private final Repository<Pedido> mPedidoRepository;

    private final FinalizaPedidoResourcesRepository mResourcesRepository;

    private FinalizandoPedidoViewModel mViewModel;

    private Calendar mDataEmissao = Calendar.getInstance();

    private List<FormaPagamento> mFormaPagamentoList;

    private Cliente mClienteSelecionado;

    private TabelaPreco mTabelaPrecoPadrao;

    private List<ProdutoVo> mProdutosSelecionados;

    FinalizandoPedidoPresenter(
            Repository<FormaPagamento> pFormaPagamentoRepository,
            Repository<Pedido> pPedidoRepository,
            FinalizaPedidoResourcesRepository pResourcesRepository) {

        mFormaPagamentoRepository = pFormaPagamentoRepository;
        mPedidoRepository = pPedidoRepository;
        mResourcesRepository = pResourcesRepository;
    }

    @Override public void attachViewModel(
            FinalizandoPedidoViewModel pViewModel,
            FinalizandoPedidoExtrasExtractor pExtrasExtractor) {

        mViewModel = pViewModel;
        mViewModel.dataEmissao(formatDataEmissao());

        mClienteSelecionado = pExtrasExtractor.extractThirdExtra();
        mViewModel.cliente(mClienteSelecionado.getNome());

        mTabelaPrecoPadrao = pExtrasExtractor.extractSecondExtra();
        mProdutosSelecionados = pExtrasExtractor.extractFirstExtra();
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

        //registerForEvents();
    }

    private String formatDataEmissao() {
        return FormattingUtils.convertMillisecondsToDateAsString(mDataEmissao.getTimeInMillis());
    }

    @Override public void clickSelectDataEmissao() {
        getView().showCalendarPicker(mDataEmissao);
    }

    @Override public void changeDataEmissao(long pDataSelecionada) {
        mDataEmissao.setTimeInMillis(pDataSelecionada);
        mViewModel.dataEmissao(formatDataEmissao());
    }

    @Override public void clickActionSave() {
        getView().hideRequiredMessages();

        if (!shownViewModelErrors()) {
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
                    .subscribe(pPedido -> {
                        EventBus.getDefault().postSticky(NovoPedidoEvent.newEvent(pPedido));
                    });

            unregisterForEvents();
        }
    }

    private boolean shownViewModelErrors() {
        if (!mViewModel.hasRequiredValues()) {
            if (!mViewModel.hasDataEmissao()) {
                getView().displayRequiredMessageForDataEmissao();
            }

            if (!mViewModel.hasFormaPagamento()) {
                getView().displayRequiredMessageForFormaPagamento();
            }

            if (!mViewModel.hasCliente()) {
                getView().displayRequiredMessageForCliente();
            }

            getView().showFeedbackMessage(mResourcesRepository.obtainStringMessageFieldsRequired());
            return true;
        } else {
            return false;
        }
    }

}
