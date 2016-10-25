package br.com.libertsolutions.libertvendas.app.data.repository;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.util.RealmObservable;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
@SuppressWarnings("Convert2MethodRef")
public abstract class AbstractRealmRepository<T, E extends RealmModel> implements Repository<T> {
    protected final Context mContext;

    protected final Class<E> mEntityClass;

    protected final Mapper<T, E> mMapper;

    public AbstractRealmRepository(Context context, Class<E> entityClass, Mapper<T, E> mapper) {
        mContext = context;
        mEntityClass = entityClass;
        mMapper = mapper;
    }

    @Override
    public Observable<T> save(T object) {
        return RealmObservable
                .object(mContext,
                        realm -> {
                            Timber.d("%s.save() with %s", mEntityClass.getSimpleName(), object);
                            E saved = realm.copyToRealmOrUpdate(mMapper.toEntity(object));
                            Timber.d("Realm.copyToRealmOrUpdate() results %s", saved);
                            return saved;
                        })
                .map(
                        entity -> mMapper.toViewObject(entity));
    }

    @Override
    public Observable<List<T>> saveAll(List<T> objects) {
        return RealmObservable
                .list(mContext,
                        realm -> {
                            Timber.d("%s.saveAll() with %d",
                                    mEntityClass.getSimpleName(), objects.size());
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
                .map(
                        entities -> mMapper.toViewObjectList(entities));
    }

    @Override
    public Observable<List<T>> list() {
        return RealmObservable
                .results(mContext,
                        realm -> {
                            RealmResults<E> list = realm.where(mEntityClass).findAll();
                            Timber.i("%s.list() results %s",
                                    mEntityClass.getSimpleName(), list.size());
                            return list;
                        })
                .map(
                        entities -> mMapper.toViewObjectList(entities));
    }

    @Override
    public Observable<T> findFirst() {
        return RealmObservable
                .object(mContext,
                        realm -> {
                            E first = realm.where(mEntityClass).findFirst();
                            Timber.i("%s.findFirst() results %s",
                                    mEntityClass.getSimpleName(), first);
                            return first;
                        })
                .map(
                        entity -> mMapper.toViewObject(entity));
    }
}
