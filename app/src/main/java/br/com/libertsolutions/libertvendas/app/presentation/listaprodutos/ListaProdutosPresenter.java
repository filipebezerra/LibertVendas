package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Filipe Bezerra
 */

class ListaProdutosPresenter implements ListaProdutosContract.Presenter {
    private final ListaProdutosContract.View mView;

    private final Repository<Produto> mProdutoRepository;

    ListaProdutosPresenter(
            ListaProdutosContract.View pView, Repository<Produto> pProdutoRepository) {
        mView = pView;
        mProdutoRepository = pProdutoRepository;
    }

    @Override
    public void loadListaProdutos() {
        mProdutoRepository.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showListaProdutos);
    }
}
