package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface ListaProdutosContract {

    interface View extends MvpView {

        void showLoading();

        void hideLoading();

        void showListaProdutos(List<ProdutoVo> pProdutoVoList, boolean pListaSelecionavel);

        void showFeedbackMessage(String pMessage);

        void updateViewPedidoItem(int pPosition);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadListaProdutos();

        void clickAdicionaQuantidadeItem(int pPosition);

        void clickRemoveQuantidadeItem(int pPosition);

        void handleQuantidadeModificada(int pPosition, float pQuantidade);

        void handleActionDone();
    }
}
