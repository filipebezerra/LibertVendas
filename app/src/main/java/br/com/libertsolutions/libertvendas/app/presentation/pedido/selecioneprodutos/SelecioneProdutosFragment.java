package br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.util.FeedbackHelper;
import butterknife.BindView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class SelecioneProdutosFragment extends LibertVendasFragment
        implements SelecioneProdutosContract.View,
        ProdutoSelecionadoAdapter.ProdutoSelecionadoAdapterCallbacks {

    @BindView(R.id.container_selecione_produtos) protected FrameLayout mContainerSelecioneProdutos;
    @BindView(R.id.recycler_view_produtos) protected RecyclerView mRecyclerViewProdutos;

    private SelecioneProdutosContract.Presenter mPresenter;

    private ProdutoSelecionadoAdapter mProdutoSelecionadoAdapter;

    public static SelecioneProdutosFragment newInstance() {
        return new SelecioneProdutosFragment();
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SelecioneProdutosPresenter(this,
                Injection.provideProdutoRepository(getContext()),
                Injection.provideSelecioneProdutosResourcesRepository(getContext()));
        setHasOptionsMenu(true);
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_selecione_produtos;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewProdutos.setHasFixedSize(true);
        mRecyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.loadListaProdutos();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selecione_produtos, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            mPresenter.clickActionDone();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override public void showListaProdutos(List<ProdutoVo> pProdutoList) {
        mRecyclerViewProdutos.setAdapter(
                mProdutoSelecionadoAdapter =
                        new ProdutoSelecionadoAdapter(getContext(), this, pProdutoList));
    }

    @Override public void onQuantidadeAdicionada(int position) {
        mPresenter.clickAdicionaQuantidadeItem(position);
    }

    @Override public void onQuantidadeRemovida(int position) {
        mPresenter.clickRemoveQuantidadeItem(position);
    }

    @Override public void onQuantidadeModificada(int position, float quantidade) {
        mPresenter.handleQuantidadeModificada(position, quantidade);
    }

    @Override public void updateViewPedidoItem(int position) {
        mProdutoSelecionadoAdapter.notifyItemChanged(position);
    }

    @Override public void showFeedbackMessage(String pMessage) {
        if (!TextUtils.isEmpty(pMessage)) {
            FeedbackHelper.snackbar(mContainerSelecioneProdutos, pMessage);
        }
    }
}
