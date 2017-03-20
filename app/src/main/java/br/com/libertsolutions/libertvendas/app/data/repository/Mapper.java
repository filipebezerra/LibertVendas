package br.com.libertsolutions.libertvendas.app.data.repository;

import java.util.List;

/**
 * @author Filipe Bezerra
 */
public interface Mapper<T, E> {

    E toEntity(T object);

    List<E> toEntities(List<T> objects);

    T toViewObject(E entity);

    List<T> toViewObjects(List<E> entities);
}
