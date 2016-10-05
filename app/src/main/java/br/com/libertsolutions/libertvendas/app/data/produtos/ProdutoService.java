package br.com.libertsolutions.libertvendas.app.data.produtos;

import java.util.List;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */

public interface ProdutoService {
    Observable<List<Produto>> get();
}
