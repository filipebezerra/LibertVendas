package br.com.libertsolutions.libertvendas.app.data.clientes;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
public class ClienteRepositories {
    private ClienteRepositories() {/* No instances */}

    private static Repository<Cliente> sRepository = null;

    public synchronized static Repository<Cliente> getRepository(
            @NonNull Context context) {
        if (sRepository == null) {
            sRepository = new ClienteRepository(context.getApplicationContext());
        }
        return sRepository;
    }
}
