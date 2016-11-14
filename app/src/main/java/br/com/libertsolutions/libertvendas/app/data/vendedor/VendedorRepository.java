package br.com.libertsolutions.libertvendas.app.data.vendedor;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.util.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class VendedorRepository extends AbstractRealmRepository<Vendedor, VendedorEntity> {
    public VendedorRepository(Context context, Mapper<Vendedor, VendedorEntity> mapper) {
        super(context, VendedorEntity.class, mapper);
    }

    public Observable<Vendedor> findById(int idVendedor) {
        return RealmObservable
                .object(mContext,
                        realm -> {
                            VendedorEntity vendedor = realm
                                    .where(mEntityClass)
                                    .equalTo(VendedorEntity.ID_FIELD_NAME, idVendedor)
                                    .findFirst();
                            Timber.i("%s.findById() results %s",
                                    mEntityClass.getSimpleName(), vendedor);
                            return vendedor;
                        })
                .map(
                        mMapper::toViewObject);
    }
}
