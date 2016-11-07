package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.res.Resources;
import br.com.libertsolutions.libertvendas.app.R;
import java.util.Arrays;
import java.util.List;

/**
 * @author Filipe Bezerra
 */

public class ClienteResourcesRepositoryImpl extends CommonResourcesRepositoryImpl
        implements ClienteResourcesRepository {
    public ClienteResourcesRepositoryImpl(Resources resources) {
        super(resources);
    }

    @Override
    public List<String> loadTiposPessoa() {
        return Arrays.asList(getResources().getStringArray(R.array.tipos_pessoa));
    }
}
