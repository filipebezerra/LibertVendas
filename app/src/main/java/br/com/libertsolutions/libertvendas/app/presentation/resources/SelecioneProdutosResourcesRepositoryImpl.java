package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.res.Resources;
import br.com.libertsolutions.libertvendas.app.R;

/**
 * @author Filipe Bezerra
 */
public class SelecioneProdutosResourcesRepositoryImpl extends CommonResourcesRepositoryImpl
        implements SelecioneProdutosResourcesRepository {
    public SelecioneProdutosResourcesRepositoryImpl(Resources resources) {
        super(resources);
    }

    @Override public String obtainStringMessageNenhumProdutoFoiSelecionado() {
        return getResources().getString(R.string.message_nenhum_produto_foi_selecionado);
    }
}
