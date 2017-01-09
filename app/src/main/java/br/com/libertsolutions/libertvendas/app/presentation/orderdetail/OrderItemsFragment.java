package br.com.libertsolutions.libertvendas.app.presentation.orderdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;

/**
 * @author Filipe Bezerra
 */
public class OrderItemsFragment extends LibertVendasFragment {

    @BindView(R.id.recycler_view_orders) protected RecyclerView mRecyclerViewOrders;

    public static OrderItemsFragment create() {
        return new OrderItemsFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_order_items;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Pedido order = hostActivity().getIntent().getExtras()
                .getParcelable(OrderDetailActivity.EXTRA_ORDER_TO_DETAIL);

        mRecyclerViewOrders.setHasFixedSize(true);
        mRecyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewOrders.setAdapter(new OrderItemsAdapter(getActivity(), order.getItens()));
    }
}
