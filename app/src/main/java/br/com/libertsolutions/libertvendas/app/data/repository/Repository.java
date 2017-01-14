package br.com.libertsolutions.libertvendas.app.data.repository;

import java.io.Serializable;
import java.util.List;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface Repository<T, ID extends Serializable> {

    Observable<T> save(T object);

    Observable<List<T>> saveAll(List<T> objects);

    Observable<List<T>> findAll();

    Observable<T> findById(ID id);

    Observable<List<T>> findByVendedorAndEmpresa(String cpfCnpjVendedor, String cnpjEmpresa);
}
