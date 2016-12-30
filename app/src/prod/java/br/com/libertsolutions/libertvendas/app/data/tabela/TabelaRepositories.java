package br.com.libertsolutions.libertvendas.app.data.tabela;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.utils.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;

/**
 * @author Filipe Bezerra
 */
public class TabelaRepositories {

    private TabelaRepositories() {/* No instances */}

    private static TabelaService sService = null;

    private static RealmMapper<Tabela, TabelaEntity> sMapper = null;

    private static TabelaRepository sRepository = null;

    public static synchronized TabelaService getService(@NonNull Context context) {
        if (sService == null) {
            sService = ServiceFactory.createService(context, TabelaService.class);
        }
        return sService;
    }

    public static RealmMapper<Tabela, TabelaEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new TabelaMapper(new ItemTabelaMapper(new ProdutoMapper()));
        }
        return sMapper;
    }

    public static synchronized TabelaRepository getRepository() {
        if (sRepository == null) {
            sRepository = new TabelaRepositoryImpl(getMapper());
        }
        return sRepository;
    }
}
