package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoRepository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.tabelaspreco.TabelaPrecoRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.SelecioneProdutosResourcesRepository;

/**
 * @author Filipe Bezerra
 */
class ListaProdutosDependencyContainer {

    private final ProdutoRepository mProdutoRepository;

    private final SelecioneProdutosResourcesRepository mResourcesRepository;

    private final VendedorRepository mVendedorRepository;

    private final TabelaPrecoRepository mTabelaPrecoRepository;

    private final SettingsRepository mSettingsRepository;

    private ListaProdutosDependencyContainer(@NonNull ListaProdutosFragment pFragment) {
        mProdutoRepository = Injection.provideProdutoRepository();
        mResourcesRepository = Injection
                .provideSelecioneProdutosResourcesRepository(pFragment.getContext());
        mVendedorRepository = Injection.provideVendedorRepository();
        mTabelaPrecoRepository = Injection.provideTabelaPrecoRepository();
        mSettingsRepository = Injection.provideSettingsRepository(pFragment.getContext());
    }

    static ListaProdutosDependencyContainer createDependencyContainer(
            @NonNull ListaProdutosFragment pFragment) {
        return new ListaProdutosDependencyContainer(pFragment);
    }

    public ProdutoRepository getProdutoRepository() {
        return mProdutoRepository;
    }

    public SelecioneProdutosResourcesRepository getResourcesRepository() {
        return mResourcesRepository;
    }

    public VendedorRepository getVendedorRepository() {
        return mVendedorRepository;
    }

    public TabelaPrecoRepository getTabelaPrecoRepository() {
        return mTabelaPrecoRepository;
    }

    public SettingsRepository getSettingsRepository() {
        return mSettingsRepository;
    }
}
