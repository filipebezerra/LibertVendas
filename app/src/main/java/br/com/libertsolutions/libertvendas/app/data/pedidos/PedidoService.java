package br.com.libertsolutions.libertvendas.app.data.pedidos;

import java.util.List;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface PedidoService {
    Observable<List<Pedido>> get();
}
