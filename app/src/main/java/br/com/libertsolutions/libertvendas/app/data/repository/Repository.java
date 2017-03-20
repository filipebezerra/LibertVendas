package br.com.libertsolutions.libertvendas.app.data.repository;

import java.util.List;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface Repository<T> {

    Observable<T> save(T object);

    Observable<List<T>> save(List<T> objects);

    Observable<T> findFirst(Specification specification);

    Observable<List<T>> findAll(String sortField, boolean ascending);

    Observable<List<T>> query(Specification specification);
}
