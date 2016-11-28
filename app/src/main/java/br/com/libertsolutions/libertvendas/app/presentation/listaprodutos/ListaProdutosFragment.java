package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.util.FeedbackHelper;
import butterknife.BindView;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ListaProdutosDependencyContainer.createDependencyContainer;

/**
 * @author Filipe Bezerra
 */
public class ListaProdutosFragment extends LibertVendasFragment
        implements ListaProdutosContract.View,
        ListaProdutosAdapter.ProdutoSelecionadoAdapterCallbacks {

    private static final String ARG_EXTRA_TO_SELECT
            = ListaProdutosFragment.class.getSimpleName() + ".argExtraToSelect";

    @BindView(R.id.container_lista_produtos) protected FrameLayout mContainerListaProdutos;
    @BindView(R.id.recycler_view_produtos) protected RecyclerView mRecyclerViewProdutos;

    private ListaProdutosContract.Presenter mPresenter;

    private ListaProdutosAdapter mRecyclerViewAdapter;

    private MaterialDialog mProgressDialog;

    public static ListaProdutosFragment newInstance(boolean pToSelect) {
        ListaProdutosFragment fragment = new ListaProdutosFragment();
        Bundle arguments = new Bundle();
        arguments.putBoolean(ARG_EXTRA_TO_SELECT, pToSelect);
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

        final boolean toSelect = getArguments().getBoolean(ARG_EXTRA_TO_SELECT);

        mPresenter = new ListaProdutosPresenter(toSelect,
                createDependencyContainer(this));
        mPresenter.attachView(this);
        mPresenter.loadListaProdutos();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
                mRecyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
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

    @Override public void showListaProdutos(List<ProdutoVo> pProdutoVoList, boolean pToSelect) {
        mRecyclerViewProdutos.setAdapter(
                mRecyclerViewAdapter =
                        new ListaProdutosAdapter(getContext(), this, pProdutoVoList, pToSelect));
        mRecyclerViewProdutos
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mRecyclerViewProdutos
                                .getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                        hideLoading();
                    }
                });
    }

    @Override public void showFeedbackMessage(String pMessage) {
        if (!TextUtils.isEmpty(pMessage)) {
            FeedbackHelper.snackbar(mContainerListaProdutos, pMessage);
        }
    }

    @Override public void updateViewPedidoItem(int pPosition) {
        mRecyclerViewAdapter.notifyItemChanged(pPosition);
    }

    @Override public void onQuantidadeAdicionada(int pPosition) {
        mPresenter.clickAdicionaQuantidadeItem(pPosition);
    }

    @Override public void onQuantidadeRemovida(int pPosition) {
        mPresenter.clickRemoveQuantidadeItem(pPosition);
    }

    @Override public void onQuantidadeModificada(int pPosition, float pQuantidade) {
        mPresenter.handleQuantidadeModificada(pPosition, pQuantidade);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
    }
}
