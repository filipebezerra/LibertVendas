package br.com.libertsolutions.libertvendas.app.data.cidade;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.NetworkInjection;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;

/**
 * @author Filipe Bezerra
 */
public class CidadeRepositories {

    private CidadeRepositories() {/* No instances */}

    private static CidadeService sService = null;

    private static RealmMapper<Cidade, CidadeEntity> sMapper = null;

    private static CidadeRepository sRepository = null;

    public synchronized static CidadeService getService(@NonNull Context context) {
        if (sService == null) {
            sService = NetworkInjection.provideRetrofit().create(CidadeService.class);
        }
        return sService;
    }

    public synchronized static RealmMapper<Cidade, CidadeEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new CidadeMapper(EstadoRepositories.getMapper());
        }
        return sMapper;
    }

    public synchronized static CidadeRepository getRepository() {
        if (sRepository == null) {
            sRepository = new CidadeRepositoryImpl(getMapper());
        }
        return sRepository;
    }

}
