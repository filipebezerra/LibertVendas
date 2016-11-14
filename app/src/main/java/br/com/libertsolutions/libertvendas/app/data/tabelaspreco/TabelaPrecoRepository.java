package br.com.libertsolutions.libertvendas.app.data.tabelaspreco;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.util.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaPrecoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class TabelaPrecoRepository extends AbstractRealmRepository<TabelaPreco, TabelaPrecoEntity> {
    public TabelaPrecoRepository(Context context, Mapper<TabelaPreco, TabelaPrecoEntity> mapper) {
        super(context, TabelaPrecoEntity.class, mapper);
    }

    public Observable<TabelaPreco> findById(int idTabela) {
        return RealmObservable
                .object(mContext,
                        realm -> {
                            TabelaPrecoEntity tabelaPreco = realm
                                    .where(mEntityClass)
                                    .equalTo(TabelaPrecoEntity.ID_FIELD_NAME, idTabela)
                                    .findFirst();
                            Timber.i("%s.findById() results %s",
                                    mEntityClass.getSimpleName(), tabelaPreco);
                            return tabelaPreco;
                        })
                .map(
                        mMapper::toViewObject);
    }
}
