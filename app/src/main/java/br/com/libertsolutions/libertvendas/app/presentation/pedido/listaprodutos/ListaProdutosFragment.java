package br.com.libertsolutions.libertvendas.app.presentation.pedido.listaprodutos;

import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;

/**
 * @author Filipe Bezerra
 */
public class ListaProdutosFragment extends LibertVendasFragment {

    public static ListaProdutosFragment newInstance() {
        return new ListaProdutosFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_lista_produtos;
    }

}
