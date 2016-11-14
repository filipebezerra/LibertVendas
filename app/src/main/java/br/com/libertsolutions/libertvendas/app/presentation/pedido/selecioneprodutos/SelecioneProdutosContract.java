package br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos;

import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface SelecioneProdutosContract {
    interface View {
        void showListaProdutos(List<ProdutoVo> pProdutoList);

        void updateViewPedidoItem(int position);

        void showFeedbackMessage(String pMessage);
    }

    interface Presenter {
        void loadListaProdutos();

        void clickAdicionaQuantidadeItem(int pPosition);

        void clickRemoveQuantidadeItem(int pPosition);

        void handleQuantidadeModificada(int pPosition, float pQuantidade);

        void clickActionDone();
    }
}
