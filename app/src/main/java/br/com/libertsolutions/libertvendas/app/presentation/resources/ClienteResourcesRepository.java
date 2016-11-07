package br.com.libertsolutions.libertvendas.app.presentation.resources;

import java.util.List;

/**
 * @author Filipe Bezerra
 */

public interface ClienteResourcesRepository extends CommonResourcesRepository {
    List<String> loadTiposPessoa();
}
