package br.com.libertsolutions.libertvendas.app.presentation.importation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.city.CityRepository;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CompanyCustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.company.paymentmethod.CompanyPaymentMethodRepository;
import br.com.libertsolutions.libertvendas.app.data.company.pricetable.CompanyPriceTableRepository;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodRepository;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.City;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyCustomer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPaymentMethod;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPriceTable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PaymentMethod;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.login.CompletedLoginEvent;
import butterknife.BindView;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.CircularProgressButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCityRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCompanyCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCompanyPaymentMethodRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCompanyPriceTableRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providePaymentMethodRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providePriceTableRepository;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideCityApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideCustomerApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.providePaymentMethodApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.providePriceTableApi;
import static com.dd.CircularProgressButton.ERROR_STATE_PROGRESS;
import static com.dd.CircularProgressButton.IDLE_STATE_PROGRESS;
import static com.dd.CircularProgressButton.INDETERMINATE_STATE_PROGRESS;
import static com.dd.CircularProgressButton.SUCCESS_STATE_PROGRESS;

/**
 * @author Filipe Bezerra
 */
public class ImportationFragment extends BaseFragment {

    public static final String TAG = ImportationFragment.class.getName();

    private ImportationAdapter mRecyclerViewAdapter;

    private CityRepository mCityRepository;

    private PaymentMethodRepository mPaymentMethodRepository;

    private CompanyPaymentMethodRepository mCompanyPaymentMethodRepository;

    private CustomerRepository mCustomerRepository;

    private CompanyCustomerRepository mCompanyCustomerRepository;

    private PriceTableRepository mPriceTableRepository;

    private CompanyPriceTableRepository mCompanyPriceTableRepository;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private AtomicInteger mImportationTotalCounter = new AtomicInteger(1);

    private AtomicInteger mImportationCompletedCounter = new AtomicInteger();

    @BindView(R.id.recycler_view_importation_companies) RecyclerView mRecyclerView;

    @BindView(R.id.button_importation_states_and_cities) CircularProgressButton mProgressButtonStatesAndCities;

    @BindView(R.id.button_all_retry) Button mButtonRetryImportStatesAndCities;

    public static ImportationFragment newInstance() {
        return new ImportationFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_importation;
    }

    @Nullable @Override public View onCreateView(
            final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle inState) {
        View view = super.onCreateView(inflater, container, inState);

        mRecyclerView.setHasFixedSize(true);

        mProgressButtonStatesAndCities.setIndeterminateProgressMode(true);
        mProgressButtonStatesAndCities.setProgress(IDLE_STATE_PROGRESS);

        mCityRepository = provideCityRepository();
        mPaymentMethodRepository = providePaymentMethodRepository();
        mCompanyPaymentMethodRepository = provideCompanyPaymentMethodRepository();
        mCustomerRepository = provideCustomerRepository();
        mCompanyCustomerRepository = provideCompanyCustomerRepository();
        mPriceTableRepository = providePriceTableRepository();
        mCompanyPriceTableRepository = provideCompanyPriceTableRepository();

        eventBus().register(this);

        return view;
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        importCities();
        loadLoggedUser();
    }

    @Subscribe public void onRetryImport(RetryImportEvent event) {
        importForCompany(event.getCompany());
    }

    @OnClick(R.id.button_all_retry) void onButtonRetryImportStatesAndCitiesClicked() {
        mProgressButtonStatesAndCities.setProgress(IDLE_STATE_PROGRESS);
        importCities();
    }

    private void importCities() {
        provideCityApi().get()
                .filter(cities -> !cities.isEmpty())
                .flatMap(mCityRepository::save)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<List<City>>() {
                            @Override public void onStart() {
                                mProgressButtonStatesAndCities.setProgress(INDETERMINATE_STATE_PROGRESS);
                                mButtonRetryImportStatesAndCities.setVisibility(View.GONE);
                            }

                            @Override public void onError(final Throwable error) {
                                Timber.e(error, "Error loading/saving cities");
                                mProgressButtonStatesAndCities.setProgress(ERROR_STATE_PROGRESS);
                                mButtonRetryImportStatesAndCities.setVisibility(View.VISIBLE);
                            }

                            @Override public void onNext(final List<City> cities) {}

                            @Override public void onCompleted() {
                                mProgressButtonStatesAndCities.setProgress(SUCCESS_STATE_PROGRESS);
                                incrementAndCheckImportantionCounter();
                            }
                        }
                );
    }

    private void loadLoggedUser() {
        CompletedLoginEvent completedLoginEvent
                = eventBus().getStickyEvent(CompletedLoginEvent.class);

        if (completedLoginEvent != null) {
            showCompaniesFromLoggedUser(completedLoginEvent.getUser());
            return;
        }

        Subscription subscription = settings().getLoggedUser()
                .subscribe(this::showCompaniesFromLoggedUser);
        mCompositeSubscription.add(subscription);
    }

    private void showCompaniesFromLoggedUser(final LoggedUser loggedUser) {
        final List<Company> companies = loggedUser.getSalesman().getCompanies();

        mRecyclerViewAdapter = new ImportationAdapter(companies);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        if (mImportationTotalCounter.get() == 1) {
            mImportationTotalCounter.addAndGet(companies.size());
        }
        for (Company company : companies) {
            importForCompany(company);
        }
    }

    private void importForCompany(Company company) {
        Subscription subscription = Observable
                .merge(
                        importPaymentMethods(company),
                        importCustomers(company),
                        importPriceTable(company)
                )
                .lastOrDefault(Collections.emptyList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<Object>() {
                            @Override public void onStart() {
                                mRecyclerViewAdapter
                                        .updateProgress(company, INDETERMINATE_STATE_PROGRESS);
                            }

                            @Override public void onError(final Throwable e) {
                                Timber.e(e);
                                mRecyclerViewAdapter
                                        .updateProgress(company, ERROR_STATE_PROGRESS);
                            }

                            @Override public void onNext(final Object objects) {
                            }

                            @Override public void onCompleted() {
                                mRecyclerViewAdapter.updateProgress(company, SUCCESS_STATE_PROGRESS);
                                incrementAndCheckImportantionCounter();
                            }
                        }
                );
        mCompositeSubscription.add(subscription);
    }

    private Observable<List<CompanyPaymentMethod>> importPaymentMethods(final Company company) {
        return providePaymentMethodApi().get(company.getCnpj())
                .filter(paymentMethods -> !paymentMethods.isEmpty())
                .flatMap(mPaymentMethodRepository::save)
                .flatMap(paymentMethods -> saveCompanyPaymentMethods(company, paymentMethods));
    }

    private Observable<List<CompanyPaymentMethod>> saveCompanyPaymentMethods(
            final Company company, final List<PaymentMethod> paymentMethods) {
        return mCompanyPaymentMethodRepository
                .save(toCompanyPaymentMethods(company, paymentMethods));
    }

    private List<CompanyPaymentMethod> toCompanyPaymentMethods(
            final Company company, final List<PaymentMethod> paymentMethods) {
        List<CompanyPaymentMethod> companyPaymentMethods = new ArrayList<>();

        for (PaymentMethod paymentMethod : paymentMethods) {
            companyPaymentMethods.add(CompanyPaymentMethod.from(company, paymentMethod));
        }
        return companyPaymentMethods;
    }

    private Observable<List<CompanyCustomer>> importCustomers(final Company company) {
        return provideCustomerApi().get(company.getCnpj())
                .filter(customers -> !customers.isEmpty())
                .flatMap(mCustomerRepository::save)
                .flatMap(customers -> saveCompanyCustomers(company, customers));
    }

    private Observable<List<CompanyCustomer>> saveCompanyCustomers(
            final Company company, final List<Customer> customers) {
        return mCompanyCustomerRepository.save(toCompanyCustomers(company, customers));
    }

    private List<CompanyCustomer> toCompanyCustomers(
            final Company company, final List<Customer> customers) {
        List<CompanyCustomer> companyCustomers = new ArrayList<>();

        for (Customer customer : customers) {
            companyCustomers.add(CompanyCustomer.from(company, customer));
        }
        return companyCustomers;
    }

    private Observable<List<CompanyPriceTable>> importPriceTable(final Company company) {
        return providePriceTableApi().get(company.getCnpj())
                .filter(priceTables -> !priceTables.isEmpty())
                .flatMap(mPriceTableRepository::save)
                .flatMap(priceTables -> saveCompanyPriceTables(company, priceTables));
    }

    private Observable<List<CompanyPriceTable>> saveCompanyPriceTables(
            final Company company, final List<PriceTable> priceTables) {
        return mCompanyPriceTableRepository.save(toCompanyPriceTables(company, priceTables));
    }

    private List<CompanyPriceTable> toCompanyPriceTables(
            final Company company, final List<PriceTable> priceTables) {
        List<CompanyPriceTable> companyPriceTables = new ArrayList<>();

        for (PriceTable priceTable : priceTables) {
            companyPriceTables.add(CompanyPriceTable.from(company, priceTable));
        }
        return companyPriceTables;
    }

    private void incrementAndCheckImportantionCounter() {
        if (mImportationCompletedCounter.incrementAndGet() == mImportationTotalCounter.get()) {
            settings().setInitialFlowDone();
            new MaterialDialog.Builder(getContext())
                    .title(R.string.importation_completed)
                    .content(R.string.importation_completed_message)
                    .positiveText(android.R.string.ok)
                    .onPositive((dialog, which) ->
                            eventBus().post(CompletedImportationEvent.newEvent()))
                    .show();
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        eventBus().unregister(this);
        mCompositeSubscription.unsubscribe();
    }
}
