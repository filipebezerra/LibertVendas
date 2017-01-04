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
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import br.com.libertsolutions.libertvendas.app.DataInjection;
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

    public static final String TAG = ListaClientesFragment.class.getName();

    private static final String ARG_EXTRA_IS_SELECTION_MODE = TAG + ".argExtraIsSelectionMode";

    @BindView(R.id.recycler_view_clientes) protected RecyclerView mRecyclerViewClientes;

    private ListaClientesContract.Presenter mPresenter;

    private ListaClientesAdapter mRecyclerViewAdapter;

    private MaterialDialog mProgressDialog;

    private OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    private OnItemTouchListener mRecyclerViewItemTouchListener = null;

    public static ListaClientesFragment newInstance(boolean isSelectionMode) {
        ListaClientesFragment fragment = new ListaClientesFragment();
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARG_EXTRA_IS_SELECTION_MODE, isSelectionMode);
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

        final boolean isSelectionMode = getArguments().getBoolean(ARG_EXTRA_IS_SELECTION_MODE);

        mPresenter = new ListaClientesPresenter(isSelectionMode,
                DataInjection.LocalRepositories.provideClienteRepository());
        mPresenter.attachView(this);
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

        mPresenter.registerEventBus();
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

    @Override public void onLongPress(final View view, final int position) {

    }

    @Override public void onActivityResult(
            final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == Navigator.REQUEST_EDITAR_CLIENTE && resultCode == Navigator.RESULT_OK) {
            mPresenter.handleResultClienteEditado(
                    data.getParcelableExtra(CadastroClienteActivity.RESULT_CLIENTE_EDITADO));
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void onDestroyView() {
        mPresenter.unregisterEventBus();
        mPresenter.detach();
        super.onDestroyView();
    }
}
