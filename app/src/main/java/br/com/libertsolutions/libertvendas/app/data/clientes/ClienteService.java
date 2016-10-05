package br.com.libertsolutions.libertvendas.app.data.clientes;

import java.util.List;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface ClienteService {
    Observable<List<Cliente>> get();
}
