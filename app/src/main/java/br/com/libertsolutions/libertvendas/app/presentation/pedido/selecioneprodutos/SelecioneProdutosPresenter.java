package br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos;

import android.support.v4.util.Pair;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.factory.ProdutoFactories;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.resources.SelecioneProdutosResourcesRepository;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
class SelecioneProdutosPresenter implements SelecioneProdutosContract.Presenter {
    private final SelecioneProdutosContract.View mView;

    private final Repository<Produto> mProdutoRepository;

    private final SelecioneProdutosResourcesRepository mResourcesRepository;

    private final Repository<Vendedor> mVendedorRepository;

    private final Repository<TabelaPreco> mTabelaPrecoRepository;

    private List<Pair<Produto, ItemTabela>> mProdutoList = new ArrayList<>();

    private List<ProdutoVo> mProdutoVos;

    SelecioneProdutosPresenter(
            SelecioneProdutosContract.View pView, Repository<Produto> pProdutoRepository,
            SelecioneProdutosResourcesRepository pResourcesRepository,
            Repository<Vendedor> pVendedorRepository,
            Repository<TabelaPreco> pTabelaPrecoRepository) {
        mView = pView;
        mProdutoRepository = pProdutoRepository;
        mResourcesRepository = pResourcesRepository;
        mVendedorRepository = pVendedorRepository;
        mTabelaPrecoRepository = pTabelaPrecoRepository;
    }

    @Override
    public void loadListaProdutos() {
        mVendedorRepository
                .findById(3)
                .flatMap(
                        new Func1<Vendedor, Observable<TabelaPreco>>() {
                            @Override
                            public Observable<TabelaPreco> call(Vendedor pVendedor) {
                                return mTabelaPrecoRepository
                                        .findById(pVendedor.getIdTabela());
                            }
                        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<TabelaPreco>() {
                            @Override
                            public void onCompleted() {}

                            @Override
                            public void onError(Throwable e) {
                                Timber.e(e);
                            }

                            @Override
                            public void onNext(TabelaPreco pTabelaPreco) {
                                findProdutos(pTabelaPreco.getItensTabela());
                            }
                        }
                );
    }

    private void findProdutos(List<ItemTabela> pItemTabelas) {
        for (ItemTabela item : pItemTabelas) {
            mProdutoRepository
                    .findById(item.getIdProduto())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Subscriber<Produto>() {
                                @Override
                                public void onCompleted() {
                                    showListaProdutos();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Timber.e(e);
                                }

                                @Override
                                public void onNext(Produto pProduto) {
                                    mProdutoList.add(new Pair<>(pProduto, item));
                                }
                            }
                    );
        }
    }

    private void showListaProdutos() {
        mProdutoVos = ProdutoFactories.createListProdutoVo(mProdutoList);
        mView.showListaProdutos(mProdutoVos);
    }

    @Override
    public void clickAdicionaQuantidadeItem(int pPosition) {
        if (pPosition >= 0 && pPosition < mProdutoVos.size()) {
            ProdutoVo produtoVo = mProdutoVos.get(pPosition);
            produtoVo.addQuantidade();
            mView.updateViewPedidoItem(pPosition);
        }
    }

    @Override
    public void clickRemoveQuantidadeItem(int pPosition) {
        if (pPosition >= 0 && pPosition < mProdutoVos.size()) {
            ProdutoVo produtoVo = mProdutoVos.get(pPosition);
            if (produtoVo.removeQuantidade()) {
                mView.updateViewPedidoItem(pPosition);
            }
        }
    }

    @Override
    public void handleQuantidadeModificada(int pPosition, float pQuantidade) {
        if (pPosition >= 0 && pPosition < mProdutoVos.size()) {
            ProdutoVo produtoVo = mProdutoVos.get(pPosition);
            produtoVo.setQuantidade(pQuantidade);
            mView.updateViewPedidoItem(pPosition);
        }
    }

    @Override
    public void clickActionDone() {
        List<ProdutoVo> produtosSelecionados = new ArrayList<>();
        for (ProdutoVo vo : mProdutoVos) {
            if (vo.getQuantidadeAdicionada() > 0) {
                produtosSelecionados.add(vo);
            }
        }

        if (produtosSelecionados.isEmpty()) {
            mView.showFeedbackMessage(mResourcesRepository
                    .obtainStringMessageNenhumProdutoFoiSelecionado());
            return;
        }

        EventBus.getDefault().post(ProdutosSelecionadosEvent.newEvent(produtosSelecionados));
    }
}
