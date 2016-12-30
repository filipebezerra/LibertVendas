package br.com.libertsolutions.libertvendas.app.data.repository;

import br.com.libertsolutions.libertvendas.app.data.utils.RealmObservable;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public abstract class RealmRepositoryImpl<T, ID extends Serializable, E extends Entity<ID> & RealmModel>
        implements Repository<T, ID> {

    protected final Class<E> mEntityClass;

    protected final RealmMapper<T, E> mMapper;

    public RealmRepositoryImpl(Class<E> entityClass, final RealmMapper<T, E> mapper) {
        mEntityClass = entityClass;
        mMapper = mapper;
    }

    @Override public Observable<T> save(final T object) {
        return RealmObservable
                .object(realm -> {
                    Timber.d("%s.save() with %s", mEntityClass.getSimpleName(), object);
                    E saved = realm.copyToRealmOrUpdate(mMapper.toEntity(object));
                    Timber.d("Realm.copyToRealmOrUpdate() results %s", saved);
                    return saved;
                })
                .map(mMapper::toViewObject);
    }

    @Override public Observable<List<T>> saveAll(final List<T> objects) {
        return RealmObservable
                .list(realm -> {
                    Timber.d("%s.saveAll() with %d", mEntityClass.getSimpleName(), objects.size());
                    List<E> newEntities = new ArrayList<>(objects.size());

                    for (T object : objects) {
                        Timber.d("saving %s as %s", object, mEntityClass.getSimpleName());
                        E entity = realm.copyToRealmOrUpdate(mMapper.toEntity(object));
                        Timber.d("Realm.copyToRealmOrUpdate() results %s", entity);
                        newEntities.add(entity);
                    }
                    Timber.i("%s.saveAll() results %s",
                            mEntityClass.getSimpleName(), newEntities.size());

                    RealmList<E> realmObjects = new RealmList<>();
                    realmObjects.addAll(newEntities);

                    return realmObjects;
                })
                .map(mMapper::toViewObjectList);
    }

    @Override public Observable<List<T>> findAll() {
        return RealmObservable
                .results(realm -> {
                    RealmResults<E> list = realm.where(mEntityClass).findAll();
                    Timber.i("%s.list() results %s", mEntityClass.getSimpleName(), list.size());
                    return list;
                })
                .map(mMapper::toViewObjectList);
    }

    protected abstract String idFieldName();
}
