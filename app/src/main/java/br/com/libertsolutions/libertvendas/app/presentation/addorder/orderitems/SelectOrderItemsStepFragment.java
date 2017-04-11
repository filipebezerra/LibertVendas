package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableByIdSpecification;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTableItem;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.customerlist.SelectedCustomerEvent;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent;
import butterknife.BindView;
import butterknife.OnClick;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static android.R.color.white;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static br.com.libertsolutions.libertvendas.app.R.color.colorPrimary;
import static br.com.libertsolutions.libertvendas.app.R.id.button_all_retry;
import static br.com.libertsolutions.libertvendas.app.R.id.recycler_view_order_items;
import static br.com.libertsolutions.libertvendas.app.R.id.swipe_container_all_pull_refresh;
import static br.com.libertsolutions.libertvendas.app.R.layout.fragment_select_order_items;
import static br.com.libertsolutions.libertvendas.app.R.string.select_order_items_invalid_quantity;
import static br.com.libertsolutions.libertvendas.app.R.string.select_order_items_no_order_items;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providePriceTableRepository;
import static br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems.AddedOrderItemsEvent.newEvent;
import static java.lang.Integer.valueOf;
import static java.util.Collections.emptyList;
import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

/**
 * @author Filipe Bezerra
 */
public class SelectOrderItemsStepFragment extends BaseFragment implements Step,
        SelectOrderItemsCallbacks {

    private PriceTableRepository mPriceTableRepository;

    private Subscription mCurrentSubscription;

    private SelectOrderItemsAdapter mSelectOrderItemsAdapter;

    private OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    private LoggedUser mLoggedUser;

    private Integer mCustomerDefaultPriceTable;

    private List<OrderItem> mOrderItems;

    private Order mSelectedOrder;

    @BindView(swipe_container_all_pull_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(recycler_view_order_items) RecyclerView mRecyclerViewOrderItems;

    public static SelectOrderItemsStepFragment newInstance() {
        return new SelectOrderItemsStepFragment();
    }

    @Override protected int provideContentViewResource() {
        return fragment_select_order_items;
    }

    @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SelectedOrderEvent event = eventBus().getStickyEvent(SelectedOrderEvent.class);
        if (event != null) {
            mSelectedOrder = event.getOrder();
        }
    }

    @Nullable @Override public View onCreateView(final LayoutInflater inflater,
            @Nullable final ViewGroup container, @Nullable final Bundle inState) {
        final View view = super.onCreateView(inflater, container, inState);

        mRecyclerViewOrderItems.setHasFixedSize(true);

        mSwipeRefreshLayout.setColorSchemeResources(colorPrimary);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(white);

        mPriceTableRepository = providePriceTableRepository();

        return view;
    }

    @Override public void onViewCreated(final View view,
            @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProducts();
    }

    @OnClick(button_all_retry) void onButtonRetryClicked() {
        mLinearLayoutErrorState.setVisibility(GONE);
        loadProducts();
    }

    @Subscribe(sticky = true) public void onLoggedInUser(LoggedInUserEvent event) {
        if (mLoggedUser != null && !mLoggedUser.equals(event.getUser())) {
            mLoggedUser = event.getUser();
            loadProducts();
        }
    }

    @Subscribe(priority = 1) public void onSelectedCustomer(SelectedCustomerEvent event) {
        Customer customer = event.getCustomer();
        if (!isEmpty(customer.getDefaultPriceTable())) {
            mCustomerDefaultPriceTable = valueOf(customer.getDefaultPriceTable());
            loadProducts();
        }
    }

    @Override public void onAddOrderItemRequested(final OrderItem orderItem, final int position) {
        final float quantity = orderItem.getQuantity() + 1;
        if (checkQuantity(quantity)) {
            orderItem.addQuantity(1);
            mSelectOrderItemsAdapter.notifyItemChanged(position);
        }
    }

    @Override public void onRemoveOrderItemRequested(
            final OrderItem orderItem, final int position) {
        final Float quantity = orderItem.getQuantity();
        if (quantity != null && quantity >= 1) {
            orderItem.removeOneFromQuantity();
            mSelectOrderItemsAdapter.notifyItemChanged(position);
        }
    }

    @Override public void onChangeOrderItemQuantityRequested(
            final OrderItem orderItem, final float quantity, final int position) {
        if (checkQuantity(quantity)) {
            orderItem.withQuantity(quantity);
            mSelectOrderItemsAdapter.notifyItemChanged(position);
        }
    }

    @Override public VerificationError verifyStep() {
        List<OrderItem> addedOrderItems = getAddedOrderItems();
        if (!addedOrderItems.isEmpty()) {
            eventBus().post(newEvent(addedOrderItems));
            return null;
        } else {
            return new VerificationError(getString(select_order_items_no_order_items));
        }
    }

    @Override public void onSelected() {}

    @Override public void onError(@NonNull final VerificationError error) {
        Snackbar.make(getView(), error.getErrorMessage(), LENGTH_SHORT).show();
    }

    private void loadProducts() {
        mCurrentSubscription = mPriceTableRepository
                .findFirst(new PriceTableByIdSpecification(loadDefaultPriceTableId()))
                .observeOn(mainThread())
                .doOnNext(priceTable ->
                        eventBus().post(SelectedPriceTableEvent.newEvent(priceTable)))
                .observeOn(io())
                .map(PriceTable::getItems)
                .flatMapIterable(item -> item)
                .map(this::createOrderItem)
                .observeOn(mainThread())
                .doOnUnsubscribe(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(createPriceTableItemSubscriber());
    }

    private int loadDefaultPriceTableId() {
        if (mLoggedUser == null) {
            mLoggedUser = eventBus().getStickyEvent(LoggedInUserEvent.class).getUser();
        }

        if (mCustomerDefaultPriceTable != null) {
            return mCustomerDefaultPriceTable;
        }

        return mLoggedUser.getDefaultCompany().getPriceTableId();
    }

    private OrderItem createOrderItem(final PriceTableItem priceTableItem) {
        OrderItem orderItem = null;
        if (mSelectedOrder != null) {
            for (OrderItem item : mSelectedOrder.getItems()) {
                if (item.getItem().equals(priceTableItem)) {
                    orderItem = new OrderItem()
                            .withItem(priceTableItem)
                            .withQuantity(item.getQuantity());
                    break;
                }
            }
        }

        if (orderItem == null) {
            orderItem = new OrderItem()
                    .withItem(priceTableItem)
                    .withQuantity(0.0f)
                    .withSubTotal(0.0d);
        }

        return orderItem;
    }

    private Subscriber<OrderItem> createPriceTableItemSubscriber() {
        return new Subscriber<OrderItem>() {
            @Override public void onStart() {
                startLoadingProducts();
            }

            @Override public void onError(final Throwable e) {
                handleLoadProductsError(e);
            }

            @Override public void onNext(final OrderItem orderItem) {
                mOrderItems.add(orderItem);
            }

            @Override public void onCompleted() {
                showOrderItems();
            }
        };
    }

    private void startLoadingProducts() {
        if (mOrderItems == null) {
            mOrderItems = new ArrayList<>();
        } else {
            mOrderItems.clear();
        }
        mSwipeRefreshLayout.setRefreshing(true);
        mSelectOrderItemsAdapter = null;
        mRecyclerViewOrderItems.setAdapter(null);
        mRecyclerViewOrderItems.setVisibility(GONE);
        mLinearLayoutEmptyState.setVisibility(VISIBLE);
        getActivity().invalidateOptionsMenu();
    }

    private void handleLoadProductsError(Throwable e) {
        Timber.e(e, "Could not load products");
        mSwipeRefreshLayout.setRefreshing(false);
        mLinearLayoutErrorState.setVisibility(VISIBLE);
        mLinearLayoutEmptyState.setVisibility(GONE);
    }

    private void showOrderItems() {
        if (!mOrderItems.isEmpty()) {
            mRecyclerViewOrderItems.setVisibility(VISIBLE);
            mRecyclerViewOrderItems.setAdapter(
                    mSelectOrderItemsAdapter = new SelectOrderItemsAdapter(mOrderItems, this));
            mRecyclerViewOrderItems
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            mRecyclerViewLayoutListener = this::onRecyclerViewFinishLoading);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void onRecyclerViewFinishLoading() {
        if (getView() != null) {
            mRecyclerViewOrderItems
                    .getViewTreeObserver()
                    .removeOnGlobalLayoutListener(mRecyclerViewLayoutListener);
            mRecyclerViewLayoutListener = null;

            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
            mLinearLayoutEmptyState.setVisibility(GONE);
            if (!eventBus().isRegistered(this)) {
                eventBus().register(this);
            }
        }
    }

    private boolean checkQuantity(final float quantity) {
        if (quantity < 0) {
            Snackbar.make(getView(), select_order_items_invalid_quantity, LENGTH_SHORT)
                    .show();
            return false;
        }

        return true;
    }

    private List<OrderItem> getAddedOrderItems() {
        if (mOrderItems == null || mOrderItems.isEmpty()) {
            return emptyList();
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItem item : mOrderItems) {
            if (item.getQuantity() > 0) {
                orderItems.add(item);
            }
        }
        return orderItems;
    }

    @Override public void onDestroyView() {
        if (mCurrentSubscription != null && !mCurrentSubscription.isUnsubscribed()) {
            mCurrentSubscription.unsubscribe();
        }
        eventBus().unregister(this);
        super.onDestroyView();
    }
}
