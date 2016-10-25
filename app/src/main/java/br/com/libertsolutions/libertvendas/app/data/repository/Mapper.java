package br.com.libertsolutions.libertvendas.app.data.repository;

import io.realm.RealmList;
import io.realm.RealmModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public abstract class Mapper<T, E extends RealmModel> {
    public abstract E toEntity(T object);

    public abstract T toViewObject(E entity);

    public List<T> toViewObjectList(List<E> entities) {
        final List<T> objects = new ArrayList<>();

        for (E entity : entities) {
            objects.add(toViewObject(entity));
        }

        return objects;
    }

    public RealmList<E> toEntityList(List<T> objects) {
        final RealmList<E> entities = new RealmList<>();

        for (T object : objects) {
            entities.add(toEntity(object));
        }

        return entities;
    }
}
