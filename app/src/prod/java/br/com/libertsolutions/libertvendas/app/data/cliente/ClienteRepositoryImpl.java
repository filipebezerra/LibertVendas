package br.com.libertsolutions.libertvendas.app.data.cliente;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmRepositoryImpl;
import br.com.libertsolutions.libertvendas.app.data.utils.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class ClienteRepositoryImpl extends RealmRepositoryImpl<Cliente, Integer, ClienteEntity>
        implements ClienteRepository {

    public ClienteRepositoryImpl(final RealmMapper<Cliente, ClienteEntity> mapper) {
        super(ClienteEntity.class, mapper);
    }

    @Override protected String idFieldName() {
        return "id";
    }

    @Override public Observable<Cliente> findById(final Integer id) {
        return RealmObservable
                .object(realm -> {
                    ClienteEntity first = realm.where(mEntityClass)
                            .equalTo(idFieldName(), id)
                            .findFirst();
                    Timber.i("%s.findById() results %s", mEntityClass.getSimpleName(), first);
                    return first;
                })
                .map(mMapper::toViewObject);
    }
}
