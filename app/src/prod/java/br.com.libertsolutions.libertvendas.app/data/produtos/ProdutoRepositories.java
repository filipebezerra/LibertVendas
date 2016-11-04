package br.com.libertsolutions.libertvendas.app.data.produtos;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.util.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
public class ProdutoRepositories {
    private ProdutoRepositories() {/* No instances */}

    private static ProdutoService sService = null;

    private static Repository<Produto> sRepository = null;

    private static Mapper<Produto, ProdutoEntity> sMapper = null;

    public static synchronized ProdutoService getService(@NonNull Context pContext) {
        if (sService == null) {
            sService = ServiceFactory.createService(pContext, ProdutoService.class);
        }
        return sService;
    }

    public synchronized static Repository<Produto> getRepository(@NonNull Context context) {
        if (sRepository == null) {
            sRepository = new ProdutoRepository(context.getApplicationContext());
        }
        return sRepository;
    }

    public synchronized static Mapper<Produto, ProdutoEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new ProdutoMapper();
        }
        return sMapper;
    }
}
