package br.com.libertsolutions.libertvendas.app.data.tabelaspreco;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.util.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemTabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaPrecoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;

/**
 * @author Filipe Bezerra
 */
public class TabelaPrecoRepositories {
    private TabelaPrecoRepositories() {/* No instances */}

    private static TabelaPrecoService sService = null;

    private static TabelaPrecoRepository sRepository = null;

    private static Mapper<TabelaPreco, TabelaPrecoEntity> sMapper = null;

    private static Mapper<ItemTabela, ItemTabelaEntity> sItemTabelaMapper = null;

    public static synchronized TabelaPrecoService getService(@NonNull Context pContext) {
        if (sService == null) {
            sService = ServiceFactory.createService(pContext, TabelaPrecoService.class);
        }
        return sService;
    }

    public static synchronized TabelaPrecoRepository getRepository() {
        if (sRepository == null) {
            sRepository = new TabelaPrecoRepository(getEntityMapper());
        }
        return sRepository;
    }

    public static Mapper<TabelaPreco, TabelaPrecoEntity> getEntityMapper() {
        if (sMapper == null) {
            sMapper = new TabelaPrecoMapper(getItemTabelaMapper());
        }
        return sMapper;
    }

    private static Mapper<ItemTabela, ItemTabelaEntity> getItemTabelaMapper() {
        if (sItemTabelaMapper == null) {
            sItemTabelaMapper = new ItemTabelaMapper();
        }
        return sItemTabelaMapper;
    }
}
