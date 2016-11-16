package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.res.Resources;
import br.com.libertsolutions.libertvendas.app.R;

/**
 * @author Filipe Bezerra
 */
public class ImportacaoResourcesRepositoryImpl extends CommonResourcesRepositoryImpl
    implements ImportacaoResourcesRepository {
    public ImportacaoResourcesRepositoryImpl(Resources resources) {
        super(resources);
    }

    @Override public String obtainStringMessageVendedorSemEmpresasVinculadas() {
        return getResources().getString(R.string.message_vendedor_sem_empresas_vinculadas);
    }
}
