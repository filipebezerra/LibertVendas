package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente.CadastroClienteActivity;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemClickListener;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemTouchListener;
import butterknife.BindView;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.DataInjection.LocalRepositories.provideClienteRepository;

/**
 * @author Filipe Bezerra
 */
public class ListaClientesFragment extends LibertVendasFragment
        implements ListaClientesContract.View, OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = ListaClientesFragment.class.getName();

    private static final String ARG_EXTRA_IS_SELECTION_MODE = TAG + ".argExtraIsSelectionMode";

    private static final String ARG_EXTRA_CLIENTE_PEDIDO_EM_EDICAO
            = TAG  + ".argExtraClientePedidoEmEdicao";

    @BindView(R.id.recycler_view_clientes) protected RecyclerView mRecyclerViewClientes;
    @BindView(R.id.swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;

    private ListaClientesContract.Presenter mPresenter;

    private ListaClientesAdapter mRecyclerViewAdapter;

    private OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    private OnItemTouchListener mRecyclerViewItemTouchListener = null;

    public static ListaClientesFragment newInstance(
            boolean isSelectionMode, @Nullable Cliente cliente) {
        ListaClientesFragment fragment = new ListaClientesFragment();
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARG_EXTRA_IS_SELECTION_MODE, isSelectionMode);
        if (cliente != null) {
            arguments.putParcelable(ARG_EXTRA_CLIENTE_PEDIDO_EM_EDICAO, cliente);
        }
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_lista_clientes;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewClientes.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        final boolean isSelectionMode = getArguments().getBoolean(ARG_EXTRA_IS_SELECTION_MODE);

        mSwipeRefreshLayout.setEnabled(!isSelectionMode);

        final Cliente clientePedidoEmEdicao
                = getArguments().getParcelable(ARG_EXTRA_CLIENTE_PEDIDO_EM_EDICAO);
        mPresenter = new ListaClientesPresenter(
                provideClienteRepository(), isSelectionMode, clientePedidoEmEdicao);
        mPresenter.attachView(this);
        mPresenter.registerEventBus();
        mPresenter.loadClientes();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lista_clientes, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.hint_busca_cliente));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override public boolean onQueryTextChange(String newText) {
                mRecyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override public void showClientes(final List<Cliente> clientes) {
        mRecyclerViewClientes.setAdapter(
                mRecyclerViewAdapter = new ListaClientesAdapter(getContext(), clientes));
        mRecyclerViewClientes
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        mRecyclerViewLayoutListener = this::onRecyclerViewFinishLoading);
    }

    private void onRecyclerViewFinishLoading() {
        mRecyclerViewClientes
                .getViewTreeObserver()
                .removeOnGlobalLayoutListener(mRecyclerViewLayoutListener);
        mRecyclerViewLayoutListener = null;

        if (mRecyclerViewItemTouchListener != null) {
            mRecyclerViewClientes.removeOnItemTouchListener(mRecyclerViewItemTouchListener);
            mRecyclerViewItemTouchListener = null;
        }

        mRecyclerViewClientes.addOnItemTouchListener(
                mRecyclerViewItemTouchListener
                        = new OnItemTouchListener(getContext(), mRecyclerViewClientes, this));

        hideLoading();
    }

    @Override public void navigateToCadastroCliente(final Cliente cliente) {
        hostActivity().navigate().toCadastroCliente(cliente);
    }

    @Override public void updateChangedItemAtPosition(final int position) {
        mRecyclerViewAdapter.notifyItemChanged(position);
        mRecyclerViewClientes.smoothScrollToPosition(position);
    }

    @Override public void updateInsertedItemAtPosition(final int position) {
        mRecyclerViewAdapter.notifyItemInserted(position);
        mRecyclerViewClientes.smoothScrollToPosition(position);
    }

    @Override public void onSingleTapUp(final View view, final int position) {
        mPresenter.handleItemSelected(position);
    }

    @Override public void onLongPress(final View view, final int position) {}

    @Override public void onActivityResult(
            final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == Navigator.REQUEST_EDITAR_CLIENTE && resultCode == Navigator.RESULT_OK) {
            mPresenter.handleResultClienteEditado(
                    data.getParcelableExtra(CadastroClienteActivity.RESULT_CLIENTE_EDITADO));
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void onRefresh() {
        mPresenter.refreshCustomerList();
    }

    @Override public void onDestroyView() {
        mPresenter.detach();
        super.onDestroyView();
    }
}
