package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

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
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;

/**
 * @author Filipe Bezerra.
 */
public class ListaClientesFragment extends LibertVendasFragment
        implements ListaClientesContract.View {

    @BindView(R.id.recycler_view_clientes) protected RecyclerView mRecyclerViewClientes;

    private ClienteAdapter mClienteAdapter;

    private ListaClientesContract.Presenter mPresenter;

    public static ListaClientesFragment newInstance() {
        return new ListaClientesFragment();
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_lista_clientes;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ListaClientesPresenter(this,
                Injection.provideClienteRepository(getContext()));

        mRecyclerViewClientes.setHasFixedSize(true);
        mRecyclerViewClientes.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.loadListaClientes();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lista_clientes, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.hint_busca_cliente));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mClienteAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void showListaClientes(List<Cliente> pClienteList) {
        mRecyclerViewClientes.setAdapter(
                mClienteAdapter = new ClienteAdapter(getContext(), pClienteList));
    }
}
