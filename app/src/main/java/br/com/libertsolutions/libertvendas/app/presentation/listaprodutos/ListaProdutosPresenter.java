package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import android.support.v4.util.Pair;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.factory.ProdutoFactories;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
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
class ListaProdutosPresenter extends BasePresenter<ListaProdutosContract.View>
        implements ListaProdutosContract.Presenter {

    private final Repository<Produto> mProdutoRepository;

    private final SelecioneProdutosResourcesRepository mResourcesRepository;

    private final Repository<Vendedor> mVendedorRepository;

    private final Repository<TabelaPreco> mTabelaPrecoRepository;

    private final SettingsRepository mSettingsRepository;

    private final boolean mListaSelecionavel;

    private final List<ItemPedido> mItensPedido;

    private List<Pair<Produto, ItemTabela>> mProdutoList = new ArrayList<>();

    private List<ProdutoVo> mProdutoVos;

    private TabelaPreco mTabelaPrecoPadrao;

    ListaProdutosPresenter(
            ListaProdutosDependencyContainer pDependencyContainer,
            boolean pListaSelecionavel, List<ItemPedido> pItensPedido) {
        mProdutoRepository = pDependencyContainer.getProdutoRepository();
        mResourcesRepository = pDependencyContainer.getResourcesRepository();
        mVendedorRepository = pDependencyContainer.getVendedorRepository();
        mTabelaPrecoRepository = pDependencyContainer.getTabelaPrecoRepository();
        mSettingsRepository = pDependencyContainer.getSettingsRepository();
        mListaSelecionavel = pListaSelecionavel;
        mItensPedido = pItensPedido;
    }

    @Override public void loadListaProdutos() {
        getView().showLoading();
        mVendedorRepository
                .findById(mSettingsRepository.getLoggedInUser())
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
                        pTabelaPreco -> {
                            mTabelaPrecoPadrao = pTabelaPreco;
                            findProdutos(pTabelaPreco.getItensTabela());
                        },

                        pError -> {
                            Timber.e(pError);
                            getView().hideLoading();
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
                                    getView().hideLoading();
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

        //TODO: Validar quando houver divergência de preço do produto do item do pedido para um possível produto atualizado
        if (mItensPedido != null) {
            for(ItemPedido item : mItensPedido) {
                for (Pair<Produto, ItemTabela> itemTabela : mProdutoList) {
                    if (item.getProduto().equals(itemTabela.first)) {
                        ProdutoVo vo = new ProdutoVo(item.getProduto(), itemTabela.second);
                        vo.setQuantidade(item.getQuantidade());
                        mProdutoVos.add(vo);
                    }
                }
            }
        }

        getView().showListaProdutos(mProdutoVos, mListaSelecionavel);
    }

    @Override public void clickAdicionaQuantidadeItem(int pPosition) {
        if (pPosition >= 0 && pPosition < mProdutoVos.size()) {
            ProdutoVo produtoVo = mProdutoVos.get(pPosition);
            produtoVo.addQuantidade();
            getView().updateViewPedidoItem(pPosition);
        }
    }

    @Override public void clickRemoveQuantidadeItem(int pPosition) {
        if (pPosition >= 0 && pPosition < mProdutoVos.size()) {
            ProdutoVo produtoVo = mProdutoVos.get(pPosition);
            if (produtoVo.removeQuantidade()) {
                getView().updateViewPedidoItem(pPosition);
            }
        }
    }

    @Override public void handleQuantidadeModificada(int pPosition, float pQuantidade) {
        if (pPosition >= 0 && pPosition < mProdutoVos.size()) {
            ProdutoVo produtoVo = mProdutoVos.get(pPosition);
            produtoVo.setQuantidade(pQuantidade);
            getView().updateViewPedidoItem(pPosition);
        }
    }

    @Override public void handleActionDone() {
        List<ProdutoVo> produtosSelecionados = new ArrayList<>();
        for (ProdutoVo vo : mProdutoVos) {
            if (vo.getQuantidadeAdicionada() > 0) {
                produtosSelecionados.add(vo);
            }
        }

        if (produtosSelecionados.isEmpty()) {
            getView().showFeedbackMessage(mResourcesRepository
                    .obtainStringMessageNenhumProdutoFoiSelecionado());
            return;
        }

        EventBus.getDefault().postSticky(
                ProdutosSelecionadosEvent.newEvent(produtosSelecionados, mTabelaPrecoPadrao));
    }
}
