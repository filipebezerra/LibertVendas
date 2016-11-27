package br.com.libertsolutions.libertvendas.app.data.produtos;

import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
public class ProdutoRepository extends AbstractRealmRepository<Produto, ProdutoEntity> {
    public ProdutoRepository() {
        super(ProdutoEntity.class, ProdutoRepositories.getEntityMapper());
    }

    @Override
    public String idFieldName() {
        return ProdutoEntity.ID_FIELD_NAME;
    }
}
