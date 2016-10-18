package br.com.libertsolutions.libertvendas.app.data.formaspagamento;

import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public class MockFormaPagamentoServiceImpl implements FormaPagamentoService {
    private static List<FormaPagamento> sFormaPagamentoList;

    static {
        List<FormaPagamento> list = Arrays.asList(
                new FormaPagamento("Dinheiro"),
                new FormaPagamento("Cheque a prazo"),
                new FormaPagamento("Cheque a vista"),
                new FormaPagamento("Crédito"),
                new FormaPagamento("Débito"),
                new FormaPagamento("Paypal"),
                new FormaPagamento("PagSeguro")
        );
        sFormaPagamentoList = Collections.unmodifiableList(list);
    }
    @Override
    public Observable<List<FormaPagamento>> get() {
        return Observable.just(sFormaPagamentoList);
    }
}
