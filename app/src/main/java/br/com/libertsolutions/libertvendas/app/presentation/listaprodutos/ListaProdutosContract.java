package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import java.util.List;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */

interface ListaProdutosContract {
    interface View {
        void showListaProdutos(List<Produto> pProdutoList);
    }

    interface Presenter {
        void loadListaProdutos();
    }
}
