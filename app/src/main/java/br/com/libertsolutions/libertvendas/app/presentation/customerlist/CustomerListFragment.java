package br.com.libertsolutions.libertvendas.app.presentation.customerlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import br.com.libertsolutions.libertvendas.app.data.company.customer.CustomersByCompanySpecification;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.sync.CustomersSyncedEvent;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.presentation.addcustomer.SavedCustomerEvent;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.widget.recyclerview.OnItemClickListener;
import br.com.libertsolutions.libertvendas.app.presentation.widget.recyclerview.OnItemTouchListener;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.presentation.customerlist.SelectedCustomerEvent.selectCustomer;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class CustomerListFragment extends BaseFragment implements OnRefreshListener,
        OnItemClickListener {

    public static final String TAG = CustomerListFragment.class.getName();

    protected SearchView mSearchView;

    protected CustomerRepository mCustomerRepository;

    protected Subscription mCurrentSubscription;

    protected CustomerListAdapter mCustomerListAdapter;

    protected OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    protected OnItemTouchListener mRecyclerViewItemTouchListener = null;

    protected LoggedUser mLoggedUser;

    @BindView(R.id.swipe_container_all_pull_refresh) protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view_customers) protected RecyclerView mRecyclerViewCustomers;

    public static CustomerListFragment newInstance() {
        return new CustomerListFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_customer_list;
    }

    @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable @Override public View onCreateView(
            final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle inState) {
        View view = super.onCreateView(inflater, container, inState);

        mRecyclerViewCustomers.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        mCustomerRepository = provideCustomerRepository();

        return view;
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadCustomers();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_all_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint(getString(R.string.customer_list_search_hint));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override public boolean onQueryTextChange(String newText) {
                if (mCustomerListAdapter != null) {
                    mCustomerListAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    @Override public void onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.action_all_search)
                .setVisible(mCustomerListAdapter != null && !mCustomerListAdapter.isEmptyList());
    }

    @Override public void onRefresh() {
        loadCustomers();
    }

    @Override public void onSingleTapUp(final View view, final int position) {
        Customer selectedCustomer = mCustomerListAdapter.getCustomer(position);
        if (selectedCustomer != null) {
            onSelectedCustomerFromList(selectedCustomer);
        }
    }

    @Override public void onLongPress(final View view, final int position) {}

    @OnClick(R.id.button_all_retry) void onButtonRetryClicked() {
        mLinearLayoutErrorState.setVisibility(View.GONE);
        loadCustomers();
    }

    @Subscribe(sticky = true) public void onLoggedInUserEvent(LoggedInUserEvent event) {
        if (mLoggedUser != null && !mLoggedUser.equals(event.getUser())) {
            mLoggedUser = event.getUser();
            loadCustomers();
        }
    }

    @Subscribe(sticky = true) public void onSavedCustomer(SavedCustomerEvent event) {
        final Customer customer = event.getCustomer();
        eventBus().removeStickyEvent(SavedCustomerEvent.class);
        if (mCustomerListAdapter != null) {
            final int position = mCustomerListAdapter.updateCustomer(customer);
            if (position != NO_POSITION) {
                mRecyclerViewCustomers.scrollToPosition(position);
            }
        } else {
            showCustomers(new ArrayList<>(Collections.singleton(customer)));
        }
    }

    @Subscribe public void onCustomersSynced(CustomersSyncedEvent event) {
        getActivity().runOnUiThread(this::loadCustomers);
    }

    private void loadCustomers() {
        mCurrentSubscription = mCustomerRepository
                .query(new CustomersByCompanySpecification(loadSelectedCompanyId()))
                .observeOn(mainThread())
                .doOnUnsubscribe(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(createCustomerListSubscriber());
    }

    private int loadSelectedCompanyId() {
        if (mLoggedUser == null) {
            mLoggedUser = eventBus().getStickyEvent(LoggedInUserEvent.class).getUser();
        }
        return mLoggedUser.getDefaultCompany().getCompanyId();
    }

    private Subscriber<List<Customer>> createCustomerListSubscriber() {
        return new Subscriber<List<Customer>>() {
            @Override public void onStart() {
                startLoadingCustomers();
            }

            @Override public void onError(final Throwable e) {
                handleLoadCustomersError(e);
            }

            @Override public void onNext(final List<Customer> customers) {
                showCustomers(customers);
            }

            @Override public void onCompleted() {}
        };
    }

    private void startLoadingCustomers() {
        mSwipeRefreshLayout.setRefreshing(true);
        mCustomerListAdapter = null;
        mRecyclerViewCustomers.setAdapter(null);
        mRecyclerViewCustomers.setVisibility(View.GONE);
        mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
        getActivity().invalidateOptionsMenu();
    }

    private void handleLoadCustomersError(Throwable e) {
        Timber.e(e, "Could not load customers");
        mSwipeRefreshLayout.setRefreshing(false);
        mLinearLayoutErrorState.setVisibility(View.VISIBLE);
        mLinearLayoutEmptyState.setVisibility(View.GONE);
    }

    private void showCustomers(List<Customer> customers) {
        if (!customers.isEmpty()) {
            mRecyclerViewCustomers.setVisibility(View.VISIBLE);
            mRecyclerViewCustomers.setAdapter(
                    mCustomerListAdapter = new CustomerListAdapter(customers));
            mRecyclerViewCustomers
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            mRecyclerViewLayoutListener = this::onRecyclerViewFinishLoading);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    protected void onRecyclerViewFinishLoading() {
        if (getView() != null) {
            mRecyclerViewCustomers
                    .getViewTreeObserver()
                    .removeOnGlobalLayoutListener(mRecyclerViewLayoutListener);
            mRecyclerViewLayoutListener = null;

            if (mRecyclerViewItemTouchListener != null) {
                mRecyclerViewCustomers.removeOnItemTouchListener(mRecyclerViewItemTouchListener);
                mRecyclerViewItemTouchListener = null;
            }

            mRecyclerViewCustomers.addOnItemTouchListener(
                    mRecyclerViewItemTouchListener
                            = new OnItemTouchListener(getContext(), mRecyclerViewCustomers, this));

            getActivity().invalidateOptionsMenu();
            mSwipeRefreshLayout.setRefreshing(false);
            mLinearLayoutEmptyState.setVisibility(View.GONE);
            if (!eventBus().isRegistered(this)) {
                eventBus().register(this);
            }
        }
    }

    protected void onSelectedCustomerFromList(@NonNull Customer selectedCustomer) {
        eventBus().postSticky(selectCustomer(selectedCustomer));
        navigate().toAddCustomer();
    }

    @Override public void onDestroyView() {
        if (mCurrentSubscription != null && !mCurrentSubscription.isUnsubscribed()) {
            mCurrentSubscription.unsubscribe();
        }
        eventBus().unregister(this);
        super.onDestroyView();
    }
}
