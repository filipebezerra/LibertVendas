package br.com.libertsolutions.libertvendas.app.presentation.pedido;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.NovoPedidoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ProdutosSelecionadosEvent;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
class PedidoPresenter extends BasePresenter<PedidoContract.View>
        implements PedidoContract.Presenter {

    private List<ProdutoVo> mProdutosSelecionados;

    private TabelaPreco mTabelaPrecoPadrao;

    private Cliente mClienteSelecionado;

    @Override public void attachView(PedidoContract.View pView) {
        super.attachView(pView);
        registerForEvents();
    }

    @Subscribe public void onProdutosSelecionadosEvent(ProdutosSelecionadosEvent pEvent) {
        List<ProdutoVo> produtoVoList = pEvent.getProdutoVoList();
        TabelaPreco tabelaPreco = pEvent.getTabelaPreco();
        if ((produtoVoList != null && !produtoVoList.isEmpty()) && tabelaPreco != null) {
            mProdutosSelecionados = produtoVoList;
            mTabelaPrecoPadrao = tabelaPreco;
            getView().goToListaClientesStep();
        }
    }

    @Subscribe public void onClienteSelecionadoEvent(ClienteSelecionadoEvent pEvent) {
        Cliente cliente = pEvent.getCliente();
        if (cliente != null) {
            mClienteSelecionado = cliente;
            getView().goToFinalizandoPedidoStep(
                    mProdutosSelecionados, mTabelaPrecoPadrao, mClienteSelecionado);
        }
    }

    @Subscribe(threadMode = MAIN, sticky = true, priority = 1) public void onNovoPedidoEvent(
            NovoPedidoEvent pEvent) {
        getView().finishView();
    }

    @Override public void detach() {
        super.detach();
        unregisterForEvents();
    }
}