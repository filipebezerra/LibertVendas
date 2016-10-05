package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Filipe Bezerra
 */

class ListaProdutosPresenter implements ListaProdutosContract.Presenter {
    private final ListaProdutosContract.View mView;
    private final ProdutoService mProdutoService;

    ListaProdutosPresenter(
            ListaProdutosContract.View pView, ProdutoService pProdutoService) {
        mView = pView;
        mProdutoService = pProdutoService;
    }

    @Override
    public void loadListaProdutos() {
        mProdutoService.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showListaProdutos);
    }
}
