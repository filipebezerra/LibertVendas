package br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.factory.ProdutoFactories;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.resources.SelecioneProdutosResourcesRepository;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Filipe Bezerra
 */
class SelecioneProdutosPresenter implements SelecioneProdutosContract.Presenter {
    private final SelecioneProdutosContract.View mView;

    private final Repository<Produto> mProdutoRepository;

    private final SelecioneProdutosResourcesRepository mResourcesRepository;

    private List<Produto> mProdutoList;

    private List<ProdutoVo> mProdutoVos;

    SelecioneProdutosPresenter(
            SelecioneProdutosContract.View pView, Repository<Produto> pProdutoRepository,
            SelecioneProdutosResourcesRepository pResourcesRepository) {
        mView = pView;
        mProdutoRepository = pProdutoRepository;
        mResourcesRepository = pResourcesRepository;
    }

    @Override public void loadListaProdutos() {
        mProdutoRepository.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::transformThenShowListaProdutos);
    }

    private void transformThenShowListaProdutos(List<Produto> pProdutoList) {
        mProdutoList = pProdutoList;
        mProdutoVos = ProdutoFactories.createListProdutoVo(mProdutoList);
        mView.showListaProdutos(mProdutoVos);
    }

    @Override public void clickAdicionaQuantidadeItem(int pPosition) {
        if (pPosition >= 0 && pPosition < mProdutoVos.size()) {
            ProdutoVo produtoVo = mProdutoVos.get(pPosition);
            produtoVo.addQuantidade();
            mView.updateViewPedidoItem(pPosition);
        }
    }

    @Override public void clickRemoveQuantidadeItem(int pPosition) {
        if (pPosition >= 0 && pPosition < mProdutoVos.size()) {
            ProdutoVo produtoVo = mProdutoVos.get(pPosition);
            if (produtoVo.removeQuantidade()) {
                mView.updateViewPedidoItem(pPosition);
            }
        }
    }

    @Override public void handleQuantidadeModificada(int pPosition, float pQuantidade) {
        if (pPosition >= 0 && pPosition < mProdutoVos.size()) {
            ProdutoVo produtoVo = mProdutoVos.get(pPosition);
            produtoVo.setQuantidade(pQuantidade);
            mView.updateViewPedidoItem(pPosition);
        }
    }

    @Override public void clickActionDone() {
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
