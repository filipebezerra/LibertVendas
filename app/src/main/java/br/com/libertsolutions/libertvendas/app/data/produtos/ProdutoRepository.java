package br.com.libertsolutions.libertvendas.app.data.produtos;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
public class ProdutoRepository extends AbstractRealmRepository<Produto, ProdutoEntity> {
    public ProdutoRepository(Context context) {
        super(context, ProdutoEntity.class, ProdutoRepositories.getMapper());
    }
}
