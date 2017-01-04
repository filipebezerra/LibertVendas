package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmRepositoryImpl;
import br.com.libertsolutions.libertvendas.app.data.utils.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
class VendedorRepositoryImpl extends RealmRepositoryImpl<Vendedor, Integer, VendedorEntity>
        implements VendedorRepository {

    VendedorRepositoryImpl(final RealmMapper<Vendedor, VendedorEntity> mapper) {
        super(VendedorEntity.class, mapper);
    }

    @Override protected String idFieldName() {
        return "idVendedor";
    }

    @Override public Observable<Vendedor> findById(final Integer id) {
        return RealmObservable
                .object(realm -> {
                    VendedorEntity first = realm.where(mEntityClass)
                            .equalTo(idFieldName(), id)
                            .findFirst();
                    Timber.i("%s.findById() results %s", mEntityClass.getSimpleName(), first);
                    return first;
                })
                .map(mMapper::toViewObject);
    }
}
