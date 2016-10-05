package br.com.libertsolutions.libertvendas.app.data.pedidos;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
class MockPedidoServiceImpl implements PedidoService {
    private static List<Pedido> sPedidoList;

    static {
        List<Pedido> list = Arrays.asList(
                new Pedido(1, new Cliente("Gustavo Costa Alves", "(79) 8558-9351", "GustavoCcostaAlves@jourrapide.com"), 1100, 1475706128000L),
                new Pedido(2, new Cliente("Sarah Costa Rodrigues", "(16) 6262-2691", "SarahCostaRodrigues@dayrep.com"), 3200, 1475706128000L),
                new Pedido(3, new Cliente("Sarah Costa Rodrigues", "(16) 6262-2691", "SarahCostaRodrigues@dayrep.com"), 2500, 1475619728000L),
                new Pedido(4, new Cliente("Rafaela Alves Almeida", "(19) 9588-2453", "RafaelaAlvesAlmeida@jourrapide.com"), 500, 1475446928000L),
                new Pedido(5, new Cliente("Guilherme Souza Cardoso", "(84) 4498-8677", "GuilhermeSouzaCardoso@rhyta.com"), 5650, 1475446928000L),
                new Pedido(6, new Cliente("Luis Almeida Cunha", "(11) 5863-7194", "LuisAlmeidaCunha@rhyta.com"), 2320, 1475446928000L),
                new Pedido(7, new Cliente("Luis Almeida Cunha", "(11) 5863-7194", "LuisAlmeidaCunha@rhyta.com"), 9900, 1475360528000L)
        );
        sPedidoList = Collections.unmodifiableList(list);
    }

    @Override
    public Observable<List<Pedido>> get() {
        return Observable.just(sPedidoList);
    }
}
