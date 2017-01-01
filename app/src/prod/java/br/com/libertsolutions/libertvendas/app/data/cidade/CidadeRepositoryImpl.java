package br.com.libertsolutions.libertvendas.app.data.cidade;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmRepositoryImpl;
import br.com.libertsolutions.libertvendas.app.data.utils.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class CidadeRepositoryImpl extends RealmRepositoryImpl<Cidade, Integer, CidadeEntity> implements CidadeRepository {

    public CidadeRepositoryImpl(final RealmMapper<Cidade, CidadeEntity> mapper) {
        super(CidadeEntity.class, mapper);
    }

    @Override protected String idFieldName() {
        return "idCidade";
    }

    @Override public Observable<Cidade> findById(final Integer id) {
        return RealmObservable
                .object(realm -> {
                    CidadeEntity first = realm.where(mEntityClass)
                            .equalTo(idFieldName(), id)
                            .findFirst();
                    Timber.i("%s.findById() results %s", mEntityClass.getSimpleName(), first);
                    return first;
                })
                .map(mMapper::toViewObject);
    }
}