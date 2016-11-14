package br.com.libertsolutions.libertvendas.app.data.produtos;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.util.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class ProdutoRepository extends AbstractRealmRepository<Produto, ProdutoEntity> {
    public ProdutoRepository(Context context) {
        super(context, ProdutoEntity.class, ProdutoRepositories.getEntityMapper());
    }

    public Observable<Produto> findById(int idVendedor) {
        return RealmObservable
                .object(mContext,
                        realm -> {
                            ProdutoEntity produto = realm
                                    .where(mEntityClass)
                                    .equalTo(ProdutoEntity.ID_FIELD_NAME, idVendedor)
                                    .findFirst();
                            Timber.i("%s.findById() results %s",
                                    mEntityClass.getSimpleName(), produto);
                            return produto;
                        })
                .map(
                        mMapper::toViewObject);
    }
}
