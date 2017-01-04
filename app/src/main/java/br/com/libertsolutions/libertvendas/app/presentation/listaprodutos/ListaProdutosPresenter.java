package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaRepository;
import br.com.libertsolutions.libertvendas.app.domain.factory.ProdutoFactories;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ProdutosSelecionadosEvent.newEvent;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class ListaProdutosPresenter extends BasePresenter<ListaProdutosContract.View>
        implements ListaProdutosContract.Presenter {

    private final boolean mIsSelectionMode;

    private final List<ItemPedido> mItens;

    private final TabelaRepository mTabelaRepository;

    private List<ProdutoVo> mProdutos;

    private Tabela mTabelaPadrao;

    ListaProdutosPresenter(
            final boolean isSelectionMode, List<ItemPedido> itens,
            final TabelaRepository tabelaRepository) {
        mIsSelectionMode = isSelectionMode;
        mItens = itens;
        mTabelaRepository = tabelaRepository;
    }

    @Override public void loadProdutos() {
        LoggedUserEvent event = EventBus.getDefault().getStickyEvent(LoggedUserEvent.class);
        int idTabela = event.getVendedor().getIdTabela();
        addSubscription(mTabelaRepository.findById(idTabela)
                .observeOn(mainThread())
                .flatMap(new Func1<Tabela, Observable<List<ItemTabela>>>() {
                    @Override public Observable<List<ItemTabela>> call(final Tabela tabela) {
                        mTabelaPadrao = tabela;
                        return Observable.just(tabela.getItensTabela());
                    }
                })
                .flatMapIterable(item -> item)
                .flatMap(new Func1<ItemTabela, Observable<ProdutoVo>>() {
                    @Override public Observable<ProdutoVo> call(final ItemTabela itemTabela) {
                        return Observable.just(ProdutoFactories.createProdutoVo(itemTabela));
                    }
                })
                .subscribe(new Subscriber<ProdutoVo>() {
                    @Override public void onStart() {
                        getView().showLoading();
                        mProdutos = new ArrayList<>();
                    }

                    @Override public void onError(final Throwable e) {
                        Timber.e(e);
                        getView().hideLoading();
                    }

                    @Override public void onNext(final ProdutoVo produtoVo) {
                        mProdutos.add(produtoVo);
                    }

                    @Override public void onCompleted() {
                        showProdutos();
                    }
                }));
    }

    private void showProdutos() {
        if (mItens != null && !mItens.isEmpty()) {
            for (ItemPedido item : mItens) {
                for (ProdutoVo vo : mProdutos) {
                    if (item.getProduto().equals(vo.getProduto())) {
                            vo.setQuantidade(item.getQuantidade());
                    }
                }
            }
        }
        getView().showProdutos(mProdutos, mIsSelectionMode);
    }

    @Override public boolean handleActionDoneVisibility() {
        return mIsSelectionMode;
    }

    @Override public void handleQuantidadeItemAdicionada(final int position) {
        if (position >= 0 && position < mProdutos.size()) {
            ProdutoVo produtoVo = mProdutos.get(position);
            produtoVo.addQuantidade();
            getView().updateViewPedidoItem(position);
        }
    }

    @Override public void handleQuantidadeItemRemovida(final int position) {
        if (position >= 0 && position < mProdutos.size()) {
            ProdutoVo produtoVo = mProdutos.get(position);
            if (produtoVo.removeQuantidade()) {
                getView().updateViewPedidoItem(position);
            }
        }
    }

    @Override public void handleQuantidadeItemModificada(
            final int position, final float quantidade) {
        if (position >= 0 && position < mProdutos.size()) {
            ProdutoVo produtoVo = mProdutos.get(position);
            produtoVo.setQuantidade(quantidade);
            getView().updateViewPedidoItem(position);
        }
    }

    @Override public void handleActionDone() {
        List<ProdutoVo> produtosSelecionados = new ArrayList<>();
        for (ProdutoVo vo : mProdutos) {
            if (vo.getQuantidadeAdicionada() > 0) {
                produtosSelecionados.add(vo);
            }
        }

        if (produtosSelecionados.isEmpty()) {
            getView().showNoProductSelectMessage();
            return;
        }

        EventBus.getDefault().postSticky(newEvent(produtosSelecionados, mTabelaPadrao));
    }
}
