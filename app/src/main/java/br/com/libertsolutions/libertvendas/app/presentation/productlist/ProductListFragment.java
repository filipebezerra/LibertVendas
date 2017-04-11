package br.com.libertsolutions.libertvendas.app.presentation.productlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableByIdSpecification;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTableItem;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providePriceTableRepository;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class ProductListFragment extends BaseFragment implements OnRefreshListener {

    public static final String TAG = ProductListFragment.class.getName();

    private SearchView mSearchView;

    private PriceTableRepository mPriceTableRepository;

    private Subscription mCurrentSubscription;

    private ProductListAdapter mProductListAdapter;

    private OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    private LoggedUser mLoggedUser;

    @BindView(R.id.swipe_container_all_pull_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view_products) RecyclerView mRecyclerViewProducts;

    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_product_list;
    }

    @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable @Override public View onCreateView(
            final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle inState) {
        View view = super.onCreateView(inflater, container, inState);

        mRecyclerViewProducts.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        mPriceTableRepository = providePriceTableRepository();

        return view;
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProducts();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_all_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint(getString(R.string.product_list_search_hint));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override public boolean onQueryTextChange(String newText) {
                if (mProductListAdapter != null) {
                    mProductListAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    @Override public void onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.action_all_search)
                .setVisible(mProductListAdapter != null && !mProductListAdapter.isEmptyList());
    }

    @Override public void onRefresh() {
        loadProducts();
    }

    @OnClick(R.id.button_all_retry) void onButtonRetryClicked() {
        mLinearLayoutErrorState.setVisibility(View.GONE);
        loadProducts();
    }

    @Subscribe(sticky = true) public void onLoggedInUserEvent(LoggedInUserEvent event) {
        if (mLoggedUser != null && !mLoggedUser.equals(event.getUser())) {
            mLoggedUser = event.getUser();
            loadProducts();
        }
    }

    private void loadProducts() {
        mCurrentSubscription = mPriceTableRepository
                .findFirst(new PriceTableByIdSpecification(loadDefaultPriceTableId()))
                .map(PriceTable::getItems)
                .observeOn(mainThread())
                .doOnUnsubscribe(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(createPriceTableItemListSubscriber());
    }

    private int loadDefaultPriceTableId() {
        if (mLoggedUser == null) {
            mLoggedUser = eventBus().getStickyEvent(LoggedInUserEvent.class).getUser();
        }
        return mLoggedUser.getDefaultCompany().getPriceTableId();
    }

    private Subscriber<List<PriceTableItem>> createPriceTableItemListSubscriber() {
        return new Subscriber<List<PriceTableItem>>() {
            @Override public void onStart() {
                startLoadingProducts();
            }

            @Override public void onError(final Throwable e) {
                handleLoadProductsError(e);
            }

            @Override public void onNext(final List<PriceTableItem> priceTableItems) {
                showProducts(priceTableItems);
            }

            @Override public void onCompleted() {}
        };
    }

    private void startLoadingProducts() {
        mSwipeRefreshLayout.setRefreshing(true);
        mProductListAdapter = null;
        mRecyclerViewProducts.setAdapter(null);
        mRecyclerViewProducts.setVisibility(View.GONE);
        mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
        getActivity().invalidateOptionsMenu();
    }

    private void handleLoadProductsError(Throwable e) {
        Timber.e(e, "Could not load products");
        mSwipeRefreshLayout.setRefreshing(false);
        mLinearLayoutErrorState.setVisibility(View.VISIBLE);
        mLinearLayoutEmptyState.setVisibility(View.GONE);
    }

    private void showProducts(List<PriceTableItem> priceTableItems) {
        if (!priceTableItems.isEmpty()) {
            mRecyclerViewProducts.setVisibility(View.VISIBLE);
            mRecyclerViewProducts.setAdapter(
                    mProductListAdapter = new ProductListAdapter(priceTableItems));
            mRecyclerViewProducts
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            mRecyclerViewLayoutListener = this::onRecyclerViewFinishLoading);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void onRecyclerViewFinishLoading() {
        if (getView() != null) {
            mRecyclerViewProducts
                    .getViewTreeObserver()
                    .removeOnGlobalLayoutListener(mRecyclerViewLayoutListener);
            mRecyclerViewLayoutListener = null;

            getActivity().invalidateOptionsMenu();
            mSwipeRefreshLayout.setRefreshing(false);
            mLinearLayoutEmptyState.setVisibility(View.GONE);
            if (!eventBus().isRegistered(this)) {
                eventBus().register(this);
            }
        }
    }

    @Override public void onDestroyView() {
        if (mCurrentSubscription != null && !mCurrentSubscription.isUnsubscribed()) {
            mCurrentSubscription.unsubscribe();
        }
        eventBus().unregister(this);
        super.onDestroyView();
    }
}
