package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.login.UsuarioLogadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.resources.FinalizaPedidoResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import rx.android.schedulers.AndroidSchedulers;

import static br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.FinalizandoPedidoContract.Presenter;
import static br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.FinalizandoPedidoContract.View;
import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
class FinalizandoPedidoPresenter extends BasePresenter<View> implements Presenter {

    private final Repository<FormaPagamento> mFormaPagamentoRepository;

    private final Repository<Pedido> mPedidoRepository;

    private final FinalizaPedidoResourcesRepository mResourcesRepository;

    private final SettingsRepository mSettingsRepository;

    private FinalizandoPedidoViewModel mViewModel;

    private Calendar mDataEmissao = Calendar.getInstance();

    private List<FormaPagamento> mFormaPagamentoList;

    private Cliente mClienteSelecionado;

    private TabelaPreco mTabelaPrecoPadrao;

    private List<ProdutoVo> mProdutosSelecionados;

    private Vendedor mVendedorLogado;

    private Empresa mEmpresaLogada;

    FinalizandoPedidoPresenter(
            Repository<FormaPagamento> pFormaPagamentoRepository,
            Repository<Pedido> pPedidoRepository,
            FinalizaPedidoResourcesRepository pResourcesRepository,
            SettingsRepository pSettingsRepository) {

        mFormaPagamentoRepository = pFormaPagamentoRepository;
        mPedidoRepository = pPedidoRepository;
        mResourcesRepository = pResourcesRepository;
        mSettingsRepository = pSettingsRepository;
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
    }

    @Subscribe(threadMode = MAIN, sticky = true) public void onUsuarioLogadoEvent(
            UsuarioLogadoEvent pEvent) {
        mVendedorLogado = pEvent.getVendedor();
        mEmpresaLogada = pEvent.getEmpresa();
    }

    @Override public void clickSelectDataEmissao() {
        getView().showCalendarPicker(mDataEmissao);
    }

    @Override public void changeDataEmissao(long pDataSelecionada) {
        mDataEmissao.setTimeInMillis(pDataSelecionada);
        mViewModel.dataEmissao(formatDataEmissao());
    }

    private String formatDataEmissao() {
        return FormattingUtils.convertMillisecondsToDateAsString(mDataEmissao.getTimeInMillis());
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

            final String cnpjEmpresa = mEmpresaLogada.getCnpj();
            final String cpfCnpjVendedor = mVendedorLogado.getCpfCnpj();

            Pedido novoPedido = Pedido.novoPedido(
                    mDataEmissao.getTimeInMillis(),
                    desconto,
                    formaPagamento.getPercentualDesconto(),
                    mViewModel.observacao(),
                    mClienteSelecionado,
                    formaPagamento,
                    mTabelaPrecoPadrao,
                    itensPedido,
                    cnpjEmpresa,
                    cpfCnpjVendedor
            );

            addSubscription(mPedidoRepository
                    .save(novoPedido)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(pPedido -> {
                        EventBus.getDefault().postSticky(NovoPedidoEvent.newEvent(pPedido));
                    }));
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
            if (mViewModel.hasDesconto()) {
                if (!mSettingsRepository.loadSettings().isPodeAplicarDesconto()) {
                    getView().displayValidationErrorForDesconto(
                            mResourcesRepository
                                    .obtainStringMessageVendedorNaoPermitidoAplicarDesconto());
                    return true;
                }

                final FormaPagamento formaPagamento = mViewModel.formaPagamento();
                final float percentualDesconto = formaPagamento.getPercentualDesconto();

                if (percentualDesconto == 0) {
                    getView().displayValidationErrorForDesconto(
                            mResourcesRepository
                                    .obtainStringMessageValorDescontoNaoPermitido());
                    return true;
                }

                final Double desconto = Double.valueOf(mViewModel.desconto());
                final Double totalProdutos = Double.valueOf(mViewModel.totalProdutos());

                final double percentualAplicadoVenda = desconto * 100 / totalProdutos;

                if (percentualAplicadoVenda > percentualDesconto) {
                    getView().displayValidationErrorForDesconto(
                            mResourcesRepository
                                    .obtainStringMessageValorDescontoNaoPermitido());
                    return true;
                }
            }

            return false;
        }
    }

    private void syncPedido() {

    }

}
