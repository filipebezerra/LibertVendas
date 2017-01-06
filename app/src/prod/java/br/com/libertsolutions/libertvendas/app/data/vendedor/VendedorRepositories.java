package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

import static br.com.libertsolutions.libertvendas.app.NetworkInjection.provideRetrofit;

/**
 * @author Filipe Bezerra
 */
public final class VendedorRepositories {

    private VendedorRepositories() {/* No instances */}

    private static VendedorService sService = null;

    private static VendedorRepository sRepository = null;

    private static RealmMapper<Vendedor, VendedorEntity> sMapper = null;

    public synchronized static VendedorService getService() {
        if (sService == null) {
            sService = provideRetrofit().create(VendedorService.class);
        }
        return sService;
    }

    public synchronized static VendedorRepository getRepository() {
        if (sRepository == null) {
            sRepository = new VendedorRepositoryImpl(getMapper());
        }
        return sRepository;
    }

    public synchronized static RealmMapper<Vendedor, VendedorEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new VendedorMapper(new EmpresaMapper());
        }
        return sMapper;
    }
}
