package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FeedbackHelper;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemClickListener;
import br.com.libertsolutions.libertvendas.app.presentation.view.recyclerview.OnItemTouchListener;
import butterknife.BindView;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.DataInjection.LocalRepositories.providePedidoRepository;
import static br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.CadastroPedidoActivity.RESULT_PEDIDO_EDITADO;

/**
 * @author Filipe Bezerra
 */
public class OrderListFragment extends LibertVendasFragment implements OrderListContract.View,
        SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

    private static final String ARG_SHOW_ONLY_PENDING_ORDERS
            = OrderListFragment.class.getSimpleName() + ".argShowOnlyPendingOrders";

    @BindView(R.id.recycler_view_orders) protected RecyclerView mRecyclerViewOrders;
    @BindView(R.id.swipe_container) protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_state) protected LinearLayout mEmptyView;

    private OrderListContract.Presenter mPresenter;

    private OrderListAdapter mRecyclerViewAdapter;

    public static OrderListFragment newInstance(boolean showOnlyPendingOrders) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_ONLY_PENDING_ORDERS, showOnlyPendingOrders);
        fragment.setArguments(args);
        return fragment;
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_orders_list;
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewOrders.setHasFixedSize(true);
        mRecyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override public void onLayoutCompleted(final RecyclerView.State state) {
                super.onLayoutCompleted(state);
                mPresenter.handleDisplayingOrderListDone();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        Bundle arguments = getArguments();
        boolean showOnlyPendingOrders
                = arguments != null && arguments.getBoolean(ARG_SHOW_ONLY_PENDING_ORDERS, false);

        if (showOnlyPendingOrders) {
            mPresenter = new PendingOrderListPresenter(providePedidoRepository());
        } else {
            mPresenter = new OrderListPresenter(providePedidoRepository());
        }
        mPresenter.attachView(this);
        mPresenter.registerEventBus();
        mPresenter.loadOrderList();
    }

    @Override public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override public void showErrorLoadingOrderList() {
        FeedbackHelper.showRetryDialogMessage(getContext(), R.string.error_loading_order_list,
                (dialog, which) -> mPresenter.loadOrderList(), null);
    }

    @Override public boolean isDisplayingEmptyView() {
        return mEmptyView.getVisibility() == View.VISIBLE &&
                mRecyclerViewOrders.getVisibility() == View.GONE;
    }

    @Override public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mRecyclerViewOrders.setVisibility(View.GONE);
    }

    @Override public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
        mRecyclerViewOrders.setVisibility(View.VISIBLE);
    }

    @Override public void displayOrderList(
            final List<Pedido> orderList, final boolean statusIndicatorVisible) {
        mRecyclerViewOrders
                .setAdapter(mRecyclerViewAdapter =
                        new OrderListAdapter(getContext(), statusIndicatorVisible, orderList));
        mRecyclerViewOrders
                .addOnItemTouchListener(
                        new OnItemTouchListener(getContext(), mRecyclerViewOrders, this));
    }

    @Override public void refreshOrderList(final List<Pedido> orderList) {
        mRecyclerViewAdapter.swapItems(orderList);
    }

    @Override public void onRefresh() {
        mPresenter.loadOrderList();
    }

    @Override public void onSingleTapUp(final View view, final int position) {
        mPresenter.viewOrder(position);
    }

    @Override public void navigateToOrderEditor(final Pedido selectedOrder) {
        hostActivity().navigate().toCadastroPedido(selectedOrder);
    }

    @Override public void navigateToOrderDetail(final Pedido selectedOrder) {
        hostActivity().navigate().toOrderDetail(selectedOrder);
    }

    @Override public void updateEditedOrderAtPosition(final int position) {
        mRecyclerViewAdapter.notifyItemChanged(position);
        mRecyclerViewOrders.smoothScrollToPosition(position);
    }

    @Override public boolean isDisplayingOrderList() {
        return mRecyclerViewOrders.getAdapter() != null &&
                mRecyclerViewAdapter != null;
    }

    @Override public void updateAddedOrderAtPosition(final int position) {
        mRecyclerViewAdapter.notifyItemInserted(position);
        mRecyclerViewOrders.smoothScrollToPosition(position);
    }

    @Override public void onLongPress(final View view, final int position) {

    }

    @Override public void onActivityResult(
            final int requestCode, final int resultCode, final Intent data) {
        if (data != null) {
            mPresenter.handleResultFromOrderEditor(requestCode, resultCode,
                    data.getParcelableExtra(RESULT_PEDIDO_EDITADO));
        }
    }

    @Override public void onDestroyView() {
        mPresenter.detach();
        super.onDestroyView();
    }
}
