package br.com.libertsolutions.libertvendas.app.data.repository;

import java.util.List;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface Repository<T> {
    String ID_FIELD_NAME = "id";

    Observable<T> save(T viewObject);

    Observable<List<T>> saveAll(List<T> viewObjects);

    Observable<List<T>> list();

    Observable<T> findById(int id);

    String idFieldName();
}
