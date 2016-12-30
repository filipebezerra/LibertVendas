package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import android.os.Bundle;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;

/**
 * @author Filipe Bezerra
 */
public class ListaProdutosFragment extends LibertVendasFragment {

    public static final String TAG = ListaProdutosFragment.class.getName();

    private static final String ARG_EXTRA_LISTA_SELECIONAVEL = TAG + ".argExtraListaSelecionavel";

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_lista_produtos;
    }

    public static ListaProdutosFragment newInstance(boolean pListaSelecionavel) {
        ListaProdutosFragment fragment = new ListaProdutosFragment();

        Bundle arguments = new Bundle();
        arguments.putBoolean(ARG_EXTRA_LISTA_SELECIONAVEL, pListaSelecionavel);
        fragment.setArguments(arguments);

        return fragment;
    }
}
