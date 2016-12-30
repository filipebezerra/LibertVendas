package br.com.libertsolutions.libertvendas.app.data.formapagamento;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmRepositoryImpl;
import br.com.libertsolutions.libertvendas.app.data.utils.RealmObservable;
import br.com.libertsolutions.libertvendas.app.domain.entity.FormaPagamentoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
class FormaPagamentoRepositoryImpl
        extends RealmRepositoryImpl<FormaPagamento, Integer, FormaPagamentoEntity>
        implements FormaPagamentoRepository {

    FormaPagamentoRepositoryImpl(
            final RealmMapper<FormaPagamento, FormaPagamentoEntity> mapper) {
        super(FormaPagamentoEntity.class, mapper);
    }

    @Override protected String idFieldName() {
        return "idFormaPagamento";
    }

    @Override public Observable<FormaPagamento> findById(final Integer id) {
        return RealmObservable
                .object(realm -> {
                    FormaPagamentoEntity first = realm.where(mEntityClass)
                            .equalTo(idFieldName(), id)
                            .findFirst();
                    Timber.i("%s.findById() results %s", mEntityClass.getSimpleName(), first);
                    return first;
                })
                .map(mMapper::toViewObject);
    }
}
