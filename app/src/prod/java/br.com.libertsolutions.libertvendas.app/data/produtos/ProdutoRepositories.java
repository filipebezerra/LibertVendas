package br.com.libertsolutions.libertvendas.app.data.produtos;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.util.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
public class ProdutoRepositories {
    private ProdutoRepositories() {/* No instances */}

    private static ProdutoService sService = null;

    private static ProdutoRepository sRepository = null;

    private static Mapper<Produto, ProdutoEntity> sMapper = null;

    public static synchronized ProdutoService getService(@NonNull Context pContext) {
        if (sService == null) {
            sService = ServiceFactory.createService(pContext, ProdutoService.class);
        }
        return sService;
    }

    public synchronized static ProdutoRepository getRepository() {
        if (sRepository == null) {
            sRepository = new ProdutoRepository();
        }
        return sRepository;
    }

    public synchronized static Mapper<Produto, ProdutoEntity> getEntityMapper() {
        if (sMapper == null) {
            sMapper = new ProdutoMapper();
        }
        return sMapper;
    }
}
