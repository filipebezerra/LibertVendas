package br.com.libertsolutions.libertvendas.app.data.vendedor;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.NetworkInjection;

/**
 * @author Filipe Bezerra
 */
public final class VendedorRepositories {

    private VendedorRepositories() {/* No instances */}

    private static VendedorService sService = null;

    private static VendedorRepository sRepository = null;

    public synchronized static VendedorService getService(@NonNull Context context) {
        if (sService == null) {
            sService = NetworkInjection.provideRetrofit().create(VendedorService.class);
        }
        return sService;
    }

    public synchronized static VendedorRepository getRepository() {
        if (sRepository == null) {
            sRepository = new VendedorRepositoryImpl(new VendedorMapper(new EmpresaMapper()));
        }
        return sRepository;
    }
}
