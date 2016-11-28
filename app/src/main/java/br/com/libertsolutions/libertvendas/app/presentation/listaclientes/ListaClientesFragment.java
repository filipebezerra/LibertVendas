package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import android.content.Intent;
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
import android.view.ViewTreeObserver;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente.CadastroClienteActivity;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemClickListener;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemTouchListener;
import butterknife.BindView;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ListaClientesFragment extends LibertVendasFragment
        implements ListaClientesContract.View, OnItemClickListener {

    private static final String ARG_EXTRA_TO_SELECT
            = ListaClientesFragment.class.getSimpleName() + ".argExtraToSelect";

    @BindView(R.id.recycler_view_clientes) protected RecyclerView mRecyclerViewClientes;

    private ListaClientesContract.Presenter mPresenter;

    private ListaClientesAdapter mRecyclerViewAdapter;

    private MaterialDialog mProgressDialog;

    public static ListaClientesFragment newInstance(boolean pToSelect) {
        ListaClientesFragment fragment = new ListaClientesFragment();
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARG_EXTRA_TO_SELECT, pToSelect);
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
        mRecyclerViewClientes.setLayoutManager(new LinearLayoutManager(getContext()));

        final boolean toSelect = getArguments().getBoolean(ARG_EXTRA_TO_SELECT);

        mPresenter = new ListaClientesPresenter(toSelect,
                Injection.provideClienteRepository());
        mPresenter.attachView(this);
        mPresenter.loadListaClientes();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
                mRecyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override public void showLoading() {
        mProgressDialog = new MaterialDialog.Builder(getContext())
                .content(getString(R.string.loading_clientes))
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    @Override public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override public void showListaClientes(List<Cliente> pClienteList) {
        mRecyclerViewClientes.setAdapter(
                mRecyclerViewAdapter = new ListaClientesAdapter(getContext(), pClienteList));
        mRecyclerViewClientes.addOnItemTouchListener(
                new OnItemTouchListener(getContext(), mRecyclerViewClientes, this));
        mRecyclerViewClientes
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mRecyclerViewClientes
                                .getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                        hideLoading();
                        mPresenter.registerForEvents();
                    }
                });
    }

    @Override public void updateChangedItemAtPosition(int pPosition) {
        mRecyclerViewAdapter.notifyItemChanged(pPosition);
        mRecyclerViewClientes.smoothScrollToPosition(pPosition);
    }

    @Override public void updateInsertedItemAtPosition(int pPosition) {
        mRecyclerViewAdapter.notifyItemInserted(pPosition);
        mRecyclerViewClientes.smoothScrollToPosition(pPosition);
    }

    @Override public void onSingleTapUp(View view, int position) {
        mPresenter.handleSingleTapUp(position);
    }

    @Override public void navigateToCliente(Cliente pCliente) {
        hostActivity().navigate().toCadastroCliente(pCliente);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Navigator.REQUEST_EDITAR_CLIENTE && resultCode == Navigator.RESULT_OK) {
            mPresenter.handleResultClienteEditado(
                    data.getParcelableExtra(CadastroClienteActivity.RESULT_CLIENTE_EDITADO));
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void onLongPress(View view, int position) {}

    @Override public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unregisterForEvents();
        mPresenter.detach();
    }
}
