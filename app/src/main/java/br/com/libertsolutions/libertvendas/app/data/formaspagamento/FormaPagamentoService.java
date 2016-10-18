package br.com.libertsolutions.libertvendas.app.data.formaspagamento;

import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.util.List;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface FormaPagamentoService {
    Observable<List<FormaPagamento>> get();
}
