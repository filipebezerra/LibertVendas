package br.com.libertsolutions.libertvendas.app.data.clientes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */

class MockClienteServiceImpl implements ClienteService {
    private static List<Cliente> sClienteList;

    static {
        List<Cliente> list = Arrays.asList(
                new Cliente("Gustavo Costa Alves", "(79) 8558-9351", "GustavoCcostaAlves@jourrapide.com"),
                new Cliente("Sarah Costa Rodrigues", "(16) 6262-2691", "SarahCostaRodrigues@dayrep.com"),
                new Cliente("Rafaela Alves Almeida", "(19) 9588-2453", "RafaelaAlvesAlmeida@jourrapide.com"),
                new Cliente("Guilherme Souza Cardoso", "(84) 4498-8677", "GuilhermeSouzaCardoso@rhyta.com"),
                new Cliente("Luis Almeida Cunha", "(11) 5863-7194", "LuisAlmeidaCunha@rhyta.com"),
                new Cliente("Brenda Melo Fernandes", "(61) 9015-9484", "BrendaMeloFernandes@jourrapide.com"),
                new Cliente("Samuel Goncalves Rodrigues", "(38) 7986-8128", "SamuelGoncalvesRodrigues@rhyta.com"),
                new Cliente("Leonor Alves Melo", "(41) 9474-7639", "LeonorAlvesMelo@rhyta.com"),
                new Cliente("José Gomes Fernandes", "(61) 4734-2948", "JoseGomesFernandes@armyspy.com"),
                new Cliente("André Alves Correia", "(11) 5431-9271", "AndreAlvesCorreia@dayrep.com"),
                new Cliente("Raissa Oliveira Almeida", "(63) 6913-9343", "RaissaOliveiraAlmeida@teleworm.us"),
                new Cliente("Guilherme Castro Carvalho", "(11) 6845-7071", "GuilhermeCastroCarvalho@armyspy.com"),
                new Cliente("Fernanda Alves Pinto", "(15) 5303-3024", "FernandaAlvesPinto@rhyta.com"),
                new Cliente("Samuel Fernandes Carvalho", "(11) 4195-6231", "SamuelFernandesCarvalho@dayrep.com"),
                new Cliente("Amanda Cavalcanti Santos", "(11) 2430-9493", "AmandaCavalcantiSantos@teleworm.us")
        );
        sClienteList = Collections.unmodifiableList(list);
    }

    @Override
    public Observable<List<Cliente>> get() {
        return Observable.just(sClienteList);
    }
}
