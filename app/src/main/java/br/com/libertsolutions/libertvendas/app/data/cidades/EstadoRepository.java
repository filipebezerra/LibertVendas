package br.com.libertsolutions.libertvendas.app.data.cidades;

import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.util.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import io.realm.RealmResults;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity.FIELD_NAME_NOME;

/**
 * @author Filipe Bezerra
 */
public class EstadoRepository extends AbstractRealmRepository<Estado, EstadoEntity> {
    public EstadoRepository(Mapper<Estado, EstadoEntity> mapper) {
        super(EstadoEntity.class, mapper);
    }

    @Override
    public Observable<List<Estado>> list() {
        return RealmObservable
                .results(
                        realm -> {
                            RealmResults<EstadoEntity> list = realm
                                    .where(mEntityClass)
                                    .distinct(FIELD_NAME_NOME)
                                    .sort(FIELD_NAME_NOME);
                            Timber.i("%s.list() results %s",
                                    mEntityClass.getSimpleName(), list.size());
                            return list;
                        })
                .map(
                        mMapper::toViewObjectList);
    }
}
