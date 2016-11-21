package br.com.libertsolutions.libertvendas.app.presentation.pedido;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface PedidoContract {

    interface View extends MvpView {
        void goToListaClientesStep();

        void goToFinalizandoPedidoStep(List<ProdutoVo> pProdutosSelecionados,
                TabelaPreco pTabelaPrecoPadrao, Cliente pClienteSelecionado);
    }

    interface Presenter extends MvpPresenter<View> {

    }

}
