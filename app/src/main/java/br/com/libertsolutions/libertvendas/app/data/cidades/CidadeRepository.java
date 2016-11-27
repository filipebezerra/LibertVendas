package br.com.libertsolutions.libertvendas.app.data.cidades;

import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.util.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import io.realm.RealmResults;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class CidadeRepository extends AbstractRealmRepository<Cidade, CidadeEntity> {
    public CidadeRepository(Mapper<Cidade, CidadeEntity> mapper) {
        super(CidadeEntity.class, mapper);
    }

    public Observable<List<Cidade>> list(int pIdEstado) {
        return RealmObservable
                .results(
                        realm -> {
                            RealmResults<CidadeEntity> list = realm
                                    .where(mEntityClass)
                                    .equalTo("estado.idEstado", pIdEstado)
                                    .findAll();
                            Timber.i("%s.list() results %s",
                                    mEntityClass.getSimpleName(), list.size());
                            return list;
                        })
                .map(
                        entities -> mMapper.toViewObjectList(entities));
    }
}
