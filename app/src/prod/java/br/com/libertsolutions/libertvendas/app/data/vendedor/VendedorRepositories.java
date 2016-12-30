package br.com.libertsolutions.libertvendas.app.data.vendedor;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.utils.ServiceFactory;

/**
 * @author Filipe Bezerra
 */
public class VendedorRepositories {

    private VendedorRepositories() {/* No instances */}

    private static VendedorService sService = null;

    private static VendedorRepository sRepository = null;

    public static synchronized VendedorService getService(@NonNull Context context) {
        if (sService == null) {
            sService = ServiceFactory.createService(context, VendedorService.class);
        }
        return sService;
    }

    public static synchronized VendedorRepository getRepository() {
        if (sRepository == null) {
            sRepository = new VendedorRepositoryImpl(new VendedorMapper(new EmpresaMapper()));
        }
        return sRepository;
    }
}
