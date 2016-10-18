package br.com.libertsolutions.libertvendas.app.data.produtos;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
class MockProdutoServiceImpl implements ProdutoService {
    private static List<Produto> sProdutoList;

    static {
        List<Produto> list = Arrays.asList(
                new Produto("Achocolatado 200g", 10, 4.49),
                new Produto("Açúcar Extra Fino pt 1Kg", 4, 3.3),
                new Produto("Arroz Agulhinha T.1 pt (5Kg)", 100, 7.98),
                new Produto("Biscoito Recheado Duchen pt 135g", 55, 1.4),
                new Produto("Café Torrado e Moído Extra Forte pt 250g", 10, 8.14),
                new Produto("Leite em Pó (Comp. Lacteo) pt 200g", 11, 20.69),
                new Produto("Feijão Carioca T.1 pt 1kg", 12, 10.99),
                new Produto("Farinha de Mandioca Temperada 250g", 17, 3.55),
                new Produto("Fubá Tipo Mimoso pt 500g", 200, 1.85),
                new Produto("Macarrão Parafuso/Pene c/ Ovos pt 500g", 101, 4.90),
                new Produto("Óleo de Soja Refinado pet 900ml", 90, 3.69),
                new Produto("Extrato Tomate 140g", 85, 1.56),
                new Produto("Sal Refinado pt 1kg", 80, 2.19),
                new Produto("Tempero Completo 300g", 66, 2.99)
        );
        sProdutoList = Collections.unmodifiableList(list);
    }

    @Override
    public Observable<List<Produto>> get() {
        return Observable.just(sProdutoList);
    }
}
