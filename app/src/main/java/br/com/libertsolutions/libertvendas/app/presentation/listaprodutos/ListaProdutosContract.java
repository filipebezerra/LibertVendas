package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface ListaProdutosContract {

    interface View extends MvpView {

        void showLoading();

        void hideLoading();

        void showProdutos(List<ProdutoVo> produtos, boolean isSelectionMode);

        void updateViewPedidoItem(int position);

        void showNoProductSelectMessage();
    }

    interface Presenter extends MvpPresenter<View> {

        void loadProdutos();

        boolean handleActionDoneVisibility();

        void handleQuantidadeItemAdicionada(int position);

        void handleQuantidadeItemRemovida(int position);

        void handleQuantidadeItemModificada(int position, float quantidade);

        void handleActionDone();

        void refreshProductList();
    }
}
