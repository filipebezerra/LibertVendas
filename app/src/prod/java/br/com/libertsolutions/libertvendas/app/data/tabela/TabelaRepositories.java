package br.com.libertsolutions.libertvendas.app.data.tabela;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.NetworkInjection;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemTabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;

/**
 * @author Filipe Bezerra
 */
public class TabelaRepositories {

    private TabelaRepositories() {/* No instances */}

    private static TabelaService sService = null;

    private static RealmMapper<Tabela, TabelaEntity> sMapper = null;

    private static RealmMapper<ItemTabela, ItemTabelaEntity> sItemMapper;

    private static RealmMapper<Produto, ProdutoEntity> sProdutoMapper;

    private static TabelaRepository sRepository = null;

    public synchronized static TabelaService getService(@NonNull Context context) {
        if (sService == null) {
            sService = NetworkInjection.provideRetrofit().create(TabelaService.class);
        }
        return sService;
    }

    public synchronized static RealmMapper<Tabela, TabelaEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new TabelaMapper(getItemMapper());
        }
        return sMapper;
    }

    public synchronized static RealmMapper<Produto, ProdutoEntity> getProdutoMapper() {
        if (sProdutoMapper == null) {
            sProdutoMapper = new ProdutoMapper();
        }
        return sProdutoMapper;
    }

    public synchronized static TabelaRepository getRepository() {
        if (sRepository == null) {
            sRepository = new TabelaRepositoryImpl(getMapper());
        }
        return sRepository;
    }

    private static RealmMapper<ItemTabela, ItemTabelaEntity> getItemMapper() {
        if (sItemMapper == null) {
            sItemMapper = new ItemTabelaMapper(getProdutoMapper());
        }
        return sItemMapper;
    }
}
