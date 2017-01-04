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
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import br.com.libertsolutions.libertvendas.app.DataInjection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FeedbackHelper;
import butterknife.BindView;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ListaProdutosFragment extends LibertVendasFragment
        implements ListaProdutosContract.View, ListaProdutosAdapter.ListaProdutosCallbacks {

    public static final String TAG = ListaProdutosFragment.class.getName();

    private static final String ARG_EXTRA_IS_SELECTION_MODE = TAG + ".argExtraIsSelectionMode";
    private static final String ARG_EXTRA_ITENS_PEDIDO = TAG + ".argExtraItensPedido";

    @BindView(R.id.container_lista_produtos) protected FrameLayout mContainerListaProdutos;
    @BindView(R.id.recycler_view_produtos) protected RecyclerView mRecyclerViewProdutos;

    private ListaProdutosContract.Presenter mPresenter;

    private ListaProdutosAdapter mRecyclerViewAdapter;

    private MaterialDialog mProgressDialog;

    private OnGlobalLayoutListener sRecyclerViewLayoutListener = null;

    public static ListaProdutosFragment newInstance(
            boolean isSelectionMode,  @Nullable List<ItemPedido> itensPedido) {
        ListaProdutosFragment fragment = new ListaProdutosFragment();
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARG_EXTRA_IS_SELECTION_MODE, isSelectionMode);
        if (itensPedido != null && !itensPedido.isEmpty()) {
            arguments.putParcelableArrayList(ARG_EXTRA_ITENS_PEDIDO, new ArrayList<>(itensPedido));
        }
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_lista_produtos;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewProdutos.setHasFixedSize(true);
        mRecyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter = new ListaProdutosPresenter(
                getArguments().getBoolean(ARG_EXTRA_IS_SELECTION_MODE),
                getArguments().getParcelableArrayList(ARG_EXTRA_ITENS_PEDIDO),
                DataInjection.LocalRepositories.provideTabelaRepository());
        mPresenter.attachView(this);
        mPresenter.loadProdutos();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lista_produtos, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.hint_busca_produto));
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

    @Override public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_done).setVisible(mPresenter.handleActionDoneVisibility());
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            mPresenter.handleActionDone();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override public void showLoading() {
        mProgressDialog = new MaterialDialog.Builder(getContext())
                .content(getString(R.string.loading_produtos))
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    @Override public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override public void showProdutos(
            final List<ProdutoVo> produtos, final boolean isSelectionMode) {
        mRecyclerViewProdutos.setAdapter(
                mRecyclerViewAdapter = new ListaProdutosAdapter(
                        getContext(), this, produtos, isSelectionMode));
        mRecyclerViewProdutos
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        sRecyclerViewLayoutListener = this::onRecyclerViewFinishLoading);
    }

    private void onRecyclerViewFinishLoading() {
        mRecyclerViewProdutos
                .getViewTreeObserver()
                .removeOnGlobalLayoutListener(sRecyclerViewLayoutListener);
        sRecyclerViewLayoutListener = null;

        hideLoading();
    }

    @Override public void updateViewPedidoItem(final int position) {
        mRecyclerViewAdapter.notifyItemChanged(position);
    }

    @Override public void showNoProductSelectMessage() {
        FeedbackHelper.snackbar(mContainerListaProdutos,
                getString(R.string.message_nenhum_produto_foi_selecionado));
    }

    @Override public void onQuantidadeAdicionada(final int position) {
        mPresenter.handleQuantidadeItemAdicionada(position);
    }

    @Override public void onQuantidadeRemovida(final int position) {
        mPresenter.handleQuantidadeItemRemovida(position);
    }

    @Override public void onQuantidadeModificada(final int position, final float quantidade) {
        mPresenter.handleQuantidadeItemModificada(position, quantidade);
    }

    @Override public void onDestroyView() {
        mPresenter.detach();
        super.onDestroyView();
    }
}
