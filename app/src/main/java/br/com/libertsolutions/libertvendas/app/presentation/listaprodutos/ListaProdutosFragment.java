package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;

/**
 * @author Filipe Bezerra
 */
public class ListaProdutosFragment extends LibertVendasFragment
        implements ListaProdutosContract.View {

    @BindView(R.id.recycler_view_produtos) protected RecyclerView mRecyclerViewProdutos;

    private ProdutoAdapter mProdutoAdapter;

    private ListaProdutosContract.Presenter mPresenter;

    public static ListaProdutosFragment newInstance() {
        return new ListaProdutosFragment();
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_lista_produtos;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ListaProdutosPresenter(this,
                Injection.provideProdutoRepository());

        mRecyclerViewProdutos.setHasFixedSize(true);
        mRecyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.loadListaProdutos();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lista_produtos, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.hint_busca_produto));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mProdutoAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void showListaProdutos(List<Produto> pProdutoList) {
        mRecyclerViewProdutos.setAdapter(
                mProdutoAdapter = new ProdutoAdapter(getContext(), pProdutoList));
    }
}
