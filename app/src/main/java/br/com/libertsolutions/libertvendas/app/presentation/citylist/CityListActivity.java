package br.com.libertsolutions.libertvendas.app.presentation.citylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.city.CitiesByStateSpecification;
import br.com.libertsolutions.libertvendas.app.data.city.CityRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.City;
import br.com.libertsolutions.libertvendas.app.presentation.addcustomer.SelectedStateEvent;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseActivity;
import br.com.libertsolutions.libertvendas.app.presentation.widget.recyclerview.OnItemClickListener;
import br.com.libertsolutions.libertvendas.app.presentation.widget.recyclerview.OnItemTouchListener;
import butterknife.BindView;
import butterknife.OnClick;
import com.arlib.floatingsearchview.FloatingSearchView;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCityRepository;
import static br.com.libertsolutions.libertvendas.app.presentation.citylist.SelectedCityEvent.newEvent;

/**
 * @author Filipe Bezerra
 */
public class CityListActivity extends BaseActivity implements OnRefreshListener,
        OnItemClickListener {

    private CityRepository mCityRepository;

    private CityListAdapter mCityListAdapter;

    private SelectedStateEvent mSelectedStateEvent;

    private Subscription mSubscription;

    private OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    private OnItemTouchListener mRecyclerViewItemTouchListener = null;

    @BindView(R.id.swipe_container_all_pull_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view_cities) RecyclerView mRecyclerViewCities;
    @BindView(R.id.floating_search_view) FloatingSearchView mSearchView;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_city_list;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        super.onCreate(inState);
        setUpSearchView();
        setUpSwipeRefreshLayout();

        mCityRepository = provideCityRepository();
    }

    @Override protected void onStart() {
        super.onStart();
        loadCities();
    }

    @OnClick(R.id.button_all_retry) void onButtonRetryClicked() {
        mLinearLayoutErrorState.setVisibility(View.GONE);
        loadCities();
    }

    @Override public void onRefresh() {
        loadCities();
    }

    @Override public void onSingleTapUp(final View view, final int position) {
        selectCity(position);
    }

    @Override public void onLongPress(final View view, final int position) {}

    private void setUpSearchView() {
        mSearchView.setOnHomeActionClickListener(this::finish);
        mSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            if (mCityListAdapter != null) {
                mCityListAdapter.getFilter().filter(newQuery);
            }
        });
    }

    private void setUpSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
    }

    private void loadCities() {
        mSelectedStateEvent = eventBus().getStickyEvent(SelectedStateEvent.class);

        if (mSelectedStateEvent != null) {
            mSubscription = queryCitiesAsObservable(mSelectedStateEvent)
                    .subscribe(createSubscriber());
        }
    }

    private Observable<List<City>> queryCitiesAsObservable(SelectedStateEvent event) {
        return mCityRepository
                .query(new CitiesByStateSpecification(event.getState().getStateId()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Subscriber<List<City>> createSubscriber() {
        return new Subscriber<List<City>>() {
            @Override public void onStart() {
                mRecyclerViewCities.setVisibility(View.GONE);
                mLinearLayoutEmptyState.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(true);
                mSearchView.showProgress();
            }

            @Override public void onError(final Throwable e) {
                Timber.e(e, "Could not load cities");
                mSearchView.hideProgress();
                mSwipeRefreshLayout.setRefreshing(false);
                mLinearLayoutErrorState.setVisibility(View.VISIBLE);
            }

            @Override public void onNext(final List<City> cities) {
                showCities(cities);
            }

            @Override public void onCompleted() {}
        };
    }

    private void showCities(List<City> cities) {
        if (!TextUtils.isEmpty(mSearchView.getQuery())) {
            mSearchView.clearQuery();
            mSearchView.clearSearchFocus();
        }

        if (!cities.isEmpty()) {
            mRecyclerViewCities.setVisibility(View.VISIBLE);
            mRecyclerViewCities.setHasFixedSize(true);
            mRecyclerViewCities.setAdapter(mCityListAdapter = new CityListAdapter(cities));
            mRecyclerViewCities
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            mRecyclerViewLayoutListener = this::onRecyclerViewFinishLoading);
        } else {
            mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void onRecyclerViewFinishLoading() {
        mRecyclerViewCities
                .getViewTreeObserver()
                .removeOnGlobalLayoutListener(mRecyclerViewLayoutListener);
        mRecyclerViewLayoutListener = null;

        if (mRecyclerViewItemTouchListener != null) {
            mRecyclerViewCities.removeOnItemTouchListener(mRecyclerViewItemTouchListener);
            mRecyclerViewItemTouchListener = null;
        }

        mRecyclerViewCities.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        mRecyclerViewCities.addOnItemTouchListener(
                mRecyclerViewItemTouchListener
                        = new OnItemTouchListener(this, mRecyclerViewCities, this));

        mSearchView.hideProgress();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void selectCity(int position) {
        final City citySelected = mCityListAdapter.getCity(position);
        if (citySelected != null) {
            eventBus().postSticky(newEvent(citySelected));
            finish();
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (mSelectedStateEvent != null) {
            eventBus().removeStickyEvent(mSelectedStateEvent);
        }
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
