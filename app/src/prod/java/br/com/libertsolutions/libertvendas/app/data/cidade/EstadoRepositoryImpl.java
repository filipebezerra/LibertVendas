package br.com.libertsolutions.libertvendas.app.data.cidade;

import br.com.libertsolutions.libertvendas.app.data.repository.MethodNotApplicableException;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmRepositoryImpl;
import br.com.libertsolutions.libertvendas.app.data.utils.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class EstadoRepositoryImpl extends RealmRepositoryImpl<Estado, Integer, EstadoEntity>
        implements EstadoRepository {

    public EstadoRepositoryImpl(final RealmMapper<Estado, EstadoEntity> mapper) {
        super(EstadoEntity.class, mapper);
    }

    @Override protected String idFieldName() {
        return "idEstado";
    }

    @Override public Observable<Estado> findById(final Integer id) {
        return RealmObservable
                .object(realm -> {
                    EstadoEntity first = realm.where(mEntityClass)
                            .equalTo(idFieldName(), id)
                            .findFirst();
                    Timber.i("%s.findById() results %s", mEntityClass.getSimpleName(), first);
                    return first;
                })
                .map(mMapper::toViewObject);
    }

    @Override public Observable<List<Estado>> findByVendedorAndEmpresa(
            final String cpfCnpjVendedor, final String cnpjEmpresa) {
        throw new MethodNotApplicableException("findByVendedorAndEmpresa()");
    }
}
