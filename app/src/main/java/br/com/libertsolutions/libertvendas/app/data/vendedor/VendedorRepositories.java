package br.com.libertsolutions.libertvendas.app.data.vendedor;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.util.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.EmpresaEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
public class VendedorRepositories {
    private VendedorRepositories() {/* No instances */}

    private static VendedorService sService = null;

    private static VendedorRepository sRepository = null;

    private static Mapper<Vendedor, VendedorEntity> sMapper = null;

    private static Mapper<Empresa, EmpresaEntity> sEmpresaEntityMapper = null;

    public static synchronized VendedorService getService(@NonNull Context pContext) {
        if (sService == null) {
            sService = ServiceFactory.createService(pContext, VendedorService.class);
        }
        return sService;
    }

    public static synchronized VendedorRepository getRepository() {
        if (sRepository == null) {
            sRepository = new VendedorRepository(getMapper());
        }
        return sRepository;
    }

    private static Mapper<Vendedor, VendedorEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new VendedorMapper(getEmpresaEntityMapper());
        }
        return sMapper;
    }

    private static Mapper<Empresa, EmpresaEntity> getEmpresaEntityMapper() {
        if (sEmpresaEntityMapper == null) {
            sEmpresaEntityMapper = new EmpresaMapper();
        }
        return sEmpresaEntityMapper;
    }
}
