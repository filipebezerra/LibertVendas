package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.CadastroPedidoActivity;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemClickListener;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemTouchListener;
import butterknife.BindView;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.DataInjection.LocalRepositories.providePedidoRepository;

/**
 * @author Filipe Bezerra
 */
public class ListaPedidosFragment extends LibertVendasFragment
        implements ListaPedidosContract.View, OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener  {

    private static final String ARG_LISTA_PEDIDOS_NAO_ENVIADOS
            = ListaPedidosFragment.class.getSimpleName() + ".argListaPedidosNaoEnviados";

    @BindView(R.id.recycler_view_pedidos) protected RecyclerView mRecyclerViewPedidos;
    @BindView(R.id.swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;

    private ListaPedidosContract.Presenter mPresenter;

    private ListaPedidosAdapter mRecyclerViewAdapter;

    private boolean mShowOrdersNotSent;

    private ViewTreeObserver.OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    private OnItemTouchListener mRecyclerViewItemTouchListener = null;

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_lista_pedidos;
    }

    public static ListaPedidosFragment newInstance(boolean showOrdersNotSent) {
        ListaPedidosFragment fragment = new ListaPedidosFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_LISTA_PEDIDOS_NAO_ENVIADOS, showOrdersNotSent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewPedidos.setHasFixedSize(true);
        mRecyclerViewPedidos.setLayoutManager(new LinearLayoutManager(getContext()));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_LISTA_PEDIDOS_NAO_ENVIADOS)) {
            mShowOrdersNotSent = arguments.getBoolean(ARG_LISTA_PEDIDOS_NAO_ENVIADOS);
        }

        mPresenter = new ListaPedidosPresenter(providePedidoRepository());
        mPresenter.attachView(this);
        mPresenter.loadPedidos(mShowOrdersNotSent);
    }

    @Override public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override public void showPedidos(List<Pedido> pedidos) {
        mRecyclerViewPedidos.setAdapter(
                mRecyclerViewAdapter = new ListaPedidosAdapter(
                        getContext(), !mShowOrdersNotSent, pedidos));
        mRecyclerViewPedidos
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        mRecyclerViewLayoutListener = this::recyclerViewFinishLoading);
    }

    private void recyclerViewFinishLoading() {
        mRecyclerViewPedidos
                .getViewTreeObserver()
                .removeOnGlobalLayoutListener(mRecyclerViewLayoutListener);
        mRecyclerViewLayoutListener = null;

        if (mRecyclerViewItemTouchListener != null) {
            mRecyclerViewPedidos.removeOnItemTouchListener(mRecyclerViewItemTouchListener);
            mRecyclerViewItemTouchListener = null;
        }

        mRecyclerViewPedidos.addOnItemTouchListener(
                mRecyclerViewItemTouchListener
                        = new OnItemTouchListener(getContext(), mRecyclerViewPedidos, this));

        mPresenter.registerEventBus();
        hideLoading();
    }

    @Override public void onSingleTapUp(final View view, final int position) {
        mPresenter.handleItemSelected(position);
    }

    @Override public void onLongPress(final View view, final int position) {

    }

    @Override public void navigateToCadastroPedido(Pedido pedido) {
        hostActivity().navigate().toCadastroPedido(pedido);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Navigator.REQUEST_EDITAR_PEDIDO && resultCode == Navigator.RESULT_OK) {
            mPresenter.handleResultPedidoEditado(
                    data.getParcelableExtra(CadastroPedidoActivity.RESULT_PEDIDO_EDITADO));
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void updateInsertedItemAtPosition(int position) {
        mRecyclerViewAdapter.notifyItemInserted(position);
        mRecyclerViewPedidos.smoothScrollToPosition(position);
    }

    @Override public void updateChangedItemAtPosition(int position) {
        mRecyclerViewAdapter.notifyItemChanged(position);
        mRecyclerViewPedidos.smoothScrollToPosition(position);
    }

    @Override public void navigateToOrderDetail(final Pedido order) {
        hostActivity().navigate().toOrderDetail(order);
    }

    @Override public void onRefresh() {
        mPresenter.refreshOrderList();
    }

    @Override public void onDestroyView() {
        mPresenter.unregisterEventBus();
        mPresenter.detach();
        super.onDestroyView();
    }
}
