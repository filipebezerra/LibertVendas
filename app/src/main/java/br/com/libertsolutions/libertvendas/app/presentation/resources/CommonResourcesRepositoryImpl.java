package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.res.Resources;
import br.com.libertsolutions.libertvendas.app.R;

/**
 * @author Filipe Bezerra
 */
public class CommonResourcesRepositoryImpl implements CommonResourcesRepository {

    private final Resources mResources;

    public CommonResourcesRepositoryImpl(Resources resources) {
        mResources = resources;
    }

    Resources getResources() {
        return mResources;
    }

    @Override public String obtainStringMessageVendedorSemEmpresasVinculadas() {
        return getResources().getString(R.string.message_vendedor_sem_empresas_vinculadas);
    }

    @Override public String obtainStringMessageVendedorSemEmpresaLogada() {
        return getResources().getString(R.string.message_vendedor_sem_empresa_logada);
    }

    @Override public String obtainStringMessageFieldsRequired() {
        return mResources.getString(R.string.message_campos_obrigatorios_nao_preenchidos);
    }

}
