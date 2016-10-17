package br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class SelecioneProdutosFragment extends LibertVendasFragment
        implements SelecioneProdutosContract.View,
        ProdutoSelecionadoAdapter.ProdutoSelecionadoAdapterCallbacks {

    @BindView(R.id.recycler_view_selecione_produtos)
    protected RecyclerView mRecyclerViewSelecioneProdutos;

    private SelecioneProdutosContract.Presenter mPresenter;

    private ProdutoSelecionadoAdapter mProdutoSelecionadoAdapter;

    public static SelecioneProdutosFragment newInstance() {
        return new SelecioneProdutosFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SelecioneProdutosPresenter(this,
                Injection.provideProdutoService(getContext()));
        setHasOptionsMenu(true);
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_selecione_produtos;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewSelecioneProdutos.setHasFixedSize(true);
        mRecyclerViewSelecioneProdutos.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.loadListaProdutos();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selecione_produtos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showListaProdutos(List<ProdutoVo> pProdutoList) {
        mRecyclerViewSelecioneProdutos.setAdapter(
                mProdutoSelecionadoAdapter =
                        new ProdutoSelecionadoAdapter(getContext(), this, pProdutoList));
    }

    @Override
    public void onQuantidadeAdicionada(int position) {
        mPresenter.clickAdicionaQuantidadeItem(position);
    }

    @Override
    public void onQuantidadeRemovida(int position) {
        mPresenter.clickRemoveQuantidadeItem(position);
    }

    @Override
    public void updateViewPedidoItem(int position) {
        mProdutoSelecionadoAdapter.notifyItemChanged(position);
    }
}
