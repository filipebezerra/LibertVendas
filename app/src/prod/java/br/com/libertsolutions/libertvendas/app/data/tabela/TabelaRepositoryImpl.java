package br.com.libertsolutions.libertvendas.app.data.tabela;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmRepositoryImpl;
import br.com.libertsolutions.libertvendas.app.data.utils.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class TabelaRepositoryImpl extends RealmRepositoryImpl<Tabela, Integer, TabelaEntity>
        implements TabelaRepository {

    public TabelaRepositoryImpl(final RealmMapper<Tabela, TabelaEntity> mapper) {
        super(TabelaEntity.class, mapper);
    }

    @Override protected String idFieldName() {
        return "idTabela";
    }

    @Override public Observable<Tabela> findById(final Integer id) {
        return RealmObservable
                .object(realm -> {
                    TabelaEntity first = realm.where(mEntityClass)
                            .equalTo(idFieldName(), id)
                            .findFirst();
                    Timber.i("%s.findById() results %s", mEntityClass.getSimpleName(), first);
                    return first;
                })
                .map(mMapper::toViewObject);
    }
}
