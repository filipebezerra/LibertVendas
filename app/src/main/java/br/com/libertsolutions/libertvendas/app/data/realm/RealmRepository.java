package br.com.libertsolutions.libertvendas.app.data.realm;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.repository.Specification;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

import static io.realm.Sort.ASCENDING;
import static io.realm.Sort.DESCENDING;

/**
 * @author Filipe Bezerra
 */
public abstract class RealmRepository<T, E extends RealmModel> implements Repository<T> {

    private final Class<E> mEntityClass;

    private final Mapper<T, E> mRealmMapper;

    protected RealmRepository(final Class<E> entityClass, final Mapper<T, E> realmMapper) {
        mEntityClass = entityClass;
        mRealmMapper = realmMapper;
    }

    @Override public Observable<T> save(final T object) {
        return RealmObservable
                .object(realm -> {
                    Timber.d("%s.save(%s)", mEntityClass.getSimpleName(), object);
                    final E entity = realm.copyToRealmOrUpdate(mRealmMapper.toEntity(object));
                    Timber.d("%s saved %s", mEntityClass.getSimpleName(), entity);
                    return entity;
                })
                .map(mRealmMapper::toViewObject);
    }

    @Override public Observable<List<T>> save(final List<T> objects) {
        return RealmObservable
                .list(realm -> {
                    Timber.d("%s.save(list of %d)", mEntityClass.getSimpleName(), objects.size());
                    List<E> entities = realm.copyToRealmOrUpdate(mRealmMapper.toEntities(objects));
                    Timber.d("%s saved %d", mEntityClass.getSimpleName(), entities.size());

                    RealmList<E> result = new RealmList<>();
                    result.addAll(entities);
                    return result;
                })
                .map(mRealmMapper::toViewObjects);
    }

    @Override public Observable<T> findFirst(final Specification specification) {
        RealmSingleSpecification<E> spec = (RealmSingleSpecification<E>) specification;
        return RealmObservable
                .object(realm -> {
                    Timber.d("%s.findFirst()", mEntityClass.getSimpleName());
                    E entity = spec.toSingle(realm);
                    Timber.i("%s found %s", mEntityClass.getSimpleName(), entity);
                    return entity;
                })
                .map(mRealmMapper::toViewObject);
    }

    @Override public Observable<List<T>> findAll(final String sortField, final boolean ascending) {
        return RealmObservable
                .results(realm -> {
                    Timber.d("%s.findAll()", mEntityClass.getSimpleName());
                    RealmResults<E> entities = realm.where(mEntityClass)
                            .findAllSorted(sortField, ascending ? ASCENDING : DESCENDING);
                    Timber.i("%s found %d", mEntityClass.getSimpleName(), entities.size());
                    return entities;
                })
                .map(mRealmMapper::toViewObjects);
    }

    @Override public Observable<List<T>> query(final Specification specification) {
        RealmResultsSpecification<E> spec = (RealmResultsSpecification<E>) specification;
        return RealmObservable
                .results(realm -> {
                    Timber.d("%s.query()", mEntityClass.getSimpleName());
                    RealmResults<E> entities = spec.toRealmResults(realm);
                    Timber.i("%s found %d", mEntityClass.getSimpleName(), entities.size());
                    return entities;
                })
                .map(mRealmMapper::toViewObjects);
    }
}
