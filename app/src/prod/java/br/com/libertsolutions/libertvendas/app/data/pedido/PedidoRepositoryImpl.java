package br.com.libertsolutions.libertvendas.app.data.pedido;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmRepositoryImpl;
import br.com.libertsolutions.libertvendas.app.data.utils.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import io.realm.RealmResults;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class PedidoRepositoryImpl extends RealmRepositoryImpl<Pedido, String, PedidoEntity>
        implements PedidoRepository {

    public PedidoRepositoryImpl(final RealmMapper<Pedido, PedidoEntity> mapper) {
        super(PedidoEntity.class, mapper);
    }

    @Override protected String idFieldName() {
        return "id";
    }

    @Override public Observable<Pedido> findById(final String id) {
        return RealmObservable
                .object(realm -> {
                    PedidoEntity first = realm.where(mEntityClass)
                            .equalTo(idFieldName(), id)
                            .findFirst();
                    Timber.i("%s.findById() results %s", mEntityClass.getSimpleName(), first);
                    return first;
                })
                .map(mMapper::toViewObject);
    }

    @Override public Observable<List<Pedido>> findByStatus(final int status) {
        return RealmObservable
                .results(realm -> {
                    RealmResults<PedidoEntity> pedidos = realm
                            .where(mEntityClass)
                            .equalTo("status", status)
                            .findAll();
                    Timber.i("%s.findByStatus() results %s", mEntityClass.getSimpleName(),
                            pedidos.size());
                    return pedidos;
                })
                .map(mMapper::toViewObjectList);
    }
}
