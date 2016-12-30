package br.com.libertsolutions.libertvendas.app.data.repository;

/**
 * @author Filipe Bezerra
 */
public interface Mapper<T, E extends Entity> {

    E toEntity(T object);

    T toViewObject(E entity);
}
