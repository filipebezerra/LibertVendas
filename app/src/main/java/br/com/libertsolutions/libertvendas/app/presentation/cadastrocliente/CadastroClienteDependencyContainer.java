package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeRepository;
import br.com.libertsolutions.libertvendas.app.data.cidades.EstadoRepository;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.CadastroClienteResourcesRepository;

/**
 * @author Filipe Bezerra
 */
class CadastroClienteDependencyContainer {

    private final ClienteRepository mClienteRepository;

    private final CadastroClienteResourcesRepository mResourcesRepository;

    private final EstadoRepository mEstadoRepository;

    private final CidadeRepository mCidadeRepository;

    private CadastroClienteDependencyContainer(@NonNull CadastroClienteActivity pActivity) {
        mClienteRepository = Injection.provideClienteRepository();
        mResourcesRepository = Injection.provideClienteResourcesRepository(pActivity);
        mEstadoRepository = Injection.provideEstadoRepository();
        mCidadeRepository = Injection.provideCidadeRepository();
    }

    static CadastroClienteDependencyContainer createDependencyContainer(
            @NonNull CadastroClienteActivity pActivity) {
        return new CadastroClienteDependencyContainer(pActivity);
    }

    ClienteRepository clienteRepository() {
        return mClienteRepository;
    }

    CadastroClienteResourcesRepository resourcesRepository() {
        return mResourcesRepository;
    }

    EstadoRepository estadoRepository() {
        return mEstadoRepository;
    }

    CidadeRepository cidadeRepository() {
        return mCidadeRepository;
    }
}
