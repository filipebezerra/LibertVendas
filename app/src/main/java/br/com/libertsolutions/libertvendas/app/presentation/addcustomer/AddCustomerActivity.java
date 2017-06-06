package br.com.libertsolutions.libertvendas.app.presentation.addcustomer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.city.CityByNameSpecification;
import br.com.libertsolutions.libertvendas.app.data.city.CityRepository;
import br.com.libertsolutions.libertvendas.app.data.city.StateRepository;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CompanyCustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRepository;
import br.com.libertsolutions.libertvendas.app.domain.enumeration.TypeOfPerson;
import br.com.libertsolutions.libertvendas.app.domain.pojo.City;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyCustomer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PostalCode;
import br.com.libertsolutions.libertvendas.app.domain.pojo.State;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseActivity;
import br.com.libertsolutions.libertvendas.app.presentation.citylist.SelectedCityEvent;
import br.com.libertsolutions.libertvendas.app.presentation.customerlist.SelectedCustomerEvent;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker;
import br.com.libertsolutions.libertvendas.app.presentation.widget.MaterialSpinner;
import butterknife.BindView;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import com.afollestad.materialdialogs.MaterialDialog;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_FLAG_SIGNED;
import static android.text.TextUtils.isEmpty;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_address;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_address_complement;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_address_number;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_cpf_or_cnpj;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_customer_name;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_district;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_email;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_fantasy_name;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_main_phone;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_other_phone;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_postal_code;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_address;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_address_complement;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_address_number;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_city;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_cpf_or_cnpj;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_customer_name;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_district;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_email;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_fantasy_name;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_main_phone;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_other_phone;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_postal_code;
import static br.com.libertsolutions.libertvendas.app.R.id.spinner_state;
import static br.com.libertsolutions.libertvendas.app.R.id.spinner_type_of_person;
import static br.com.libertsolutions.libertvendas.app.R.string.add_customer_invalid_cnpj;
import static br.com.libertsolutions.libertvendas.app.R.string.add_customer_invalid_cpf;
import static br.com.libertsolutions.libertvendas.app.R.string.add_customer_invalid_email;
import static br.com.libertsolutions.libertvendas.app.R.string.add_customer_invalid_phone_number;
import static br.com.libertsolutions.libertvendas.app.R.string.add_customer_invalid_postal_code;
import static br.com.libertsolutions.libertvendas.app.R.string.add_customer_saved_successfully;
import static br.com.libertsolutions.libertvendas.app.R.string.add_customer_searching_postal_code_message;
import static br.com.libertsolutions.libertvendas.app.R.string.add_customer_unknown_error_loading_city;
import static br.com.libertsolutions.libertvendas.app.R.string.add_customer_unknown_error_loading_states;
import static br.com.libertsolutions.libertvendas.app.R.string.all_required_field_error;
import static br.com.libertsolutions.libertvendas.app.R.string.all_saving_message;
import static br.com.libertsolutions.libertvendas.app.R.string.all_unknown_error_saving_form;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCityRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCompanyCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideStateRepository;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.providePostalCodeApi;
import static br.com.libertsolutions.libertvendas.app.domain.entity.StateEntity.Fields.NAME;
import static br.com.libertsolutions.libertvendas.app.domain.enumeration.TypeOfPerson.LEGAL_ENTITY;
import static br.com.libertsolutions.libertvendas.app.domain.enumeration.TypeOfPerson.PRIVATE_INDIVIDUAL;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyCustomer.from;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus.STATUS_CREATED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus.STATUS_MODIFIED;
import static br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker.ACTION_CEP_SEARCHED;
import static br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker.ACTION_SAVED_CUSTOMER;
import static br.com.libertsolutions.libertvendas.app.presentation.util.ValidationUtils.isValidEmail;
import static br.com.libertsolutions.libertvendas.app.presentation.util.ValidationUtils.isValidPhoneNumber;
import static br.com.libertsolutions.libertvendas.app.presentation.util.ValidationUtils.isValidPostalCode;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class AddCustomerActivity extends BaseActivity {

    private TypeOfPersonAdapter mTypeOfPersonAdapter;

    private StatesAdapter mStatesAdapter;

    private StateRepository mStateRepository;

    private CityRepository mCityRepository;

    private Customer mCurrentCustomer;

    private Subscription mCurrentSubscription;

    private CustomerRepository mCustomerRepository;

    private CompanyCustomerRepository mCompanyCustomerRepository;

    private MaterialDialog mProgressDialog;

    private boolean mIsEdition;

    private LoggedUser mLoggedUser;

    @BindView(spinner_type_of_person) MaterialSpinner mSpinnerTypeOfPerson;
    @BindView(input_layout_cpf_or_cnpj) TextInputLayout mInputLayoutCpfOrCnpj;
    @BindView(input_layout_customer_name) TextInputLayout mInputLayoutSocialName;
    @BindView(input_layout_fantasy_name) TextInputLayout mInputLayoutFantasyName;
    @BindView(input_layout_email) TextInputLayout mInputLayoutEmail;
    @BindView(input_layout_address) TextInputLayout mInputLayoutAddress;
    @BindView(input_layout_address_number) TextInputLayout mInputLayoutAddressNumber;
    @BindView(input_layout_district) TextInputLayout mInputLayoutDistrict;
    @BindView(spinner_state) MaterialSpinner mSpinnerState;
    @BindView(input_layout_city) TextInputLayout mInputLayoutCity;
    @BindView(input_layout_postal_code) TextInputLayout mInputLayoutPostalCode;
    @BindView(input_layout_address_complement) TextInputLayout mInputLayoutAddressComplement;
    @BindView(input_layout_main_phone) TextInputLayout mInputLayoutMainPhone;
    @BindView(input_layout_other_phone) TextInputLayout mInputLayoutOtherPhone;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_add_customer;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        super.onCreate(inState);
        setAsInitialFlowActivity();
        setUpViews();
        loadCurrentCustomer();
    }

    @Override protected void onStart() {
        super.onStart();
        eventBus().register(this);
        loadStates();
    }

    @Override protected void onStop() {
        super.onStop();
        eventBus().unregister(this);
    }

    @Override public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_customer, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all_done: {
                saveCustomer();
                return true;
            }
            case R.id.action_add_customer_search: {
                searchPostalCode();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnItemSelected(spinner_type_of_person) void onSpinnerTypeOfPersonItemSelected(int position) {
        if (position == MaterialSpinner.INVALID_POSITION) {
            mCurrentCustomer.withType(null);
        } else {
            final TypeOfPerson typeOfPerson = mTypeOfPersonAdapter.getItem(position);
            if (typeOfPerson != null) {
                mCurrentCustomer.withType(typeOfPerson.getOrdinalType());
            }
        }
    }

    @OnTextChanged(edit_text_cpf_or_cnpj) void onEditTextCpfOrCnpjChanged(CharSequence text) {
        mCurrentCustomer.withCpfOrCnpj(text.toString());
    }

    @OnTextChanged(edit_text_customer_name) void onEditTextCustomerNameChanged(CharSequence text) {
        mCurrentCustomer.withName(text.toString());
    }

    @OnTextChanged(edit_text_fantasy_name) void onEditTextFantasyNameChanged(CharSequence text) {
        mCurrentCustomer.withFantasyName(text.toString());
    }

    @OnTextChanged(edit_text_email) void onEditTextEmailChanged(CharSequence text) {
        mCurrentCustomer.withEmail(text.toString());
    }

    @OnTextChanged(edit_text_address) void onEditTextAddressChanged(CharSequence text) {
        mCurrentCustomer.withAddress(text.toString());
    }

    @OnTextChanged(edit_text_address_number) void onEditTextAddressNumberChanged(CharSequence text) {
        mCurrentCustomer.withAddressNumber(text.toString());
    }

    @OnTextChanged(edit_text_district) void onEditTextDistrictChanged(CharSequence text) {
        mCurrentCustomer.withDistrict(text.toString());
    }

    @OnItemSelected(spinner_state) void onSpinnerStateItemSelected(int position) {
        if (position == MaterialSpinner.INVALID_POSITION) {
            mCurrentCustomer.withCity(null);
            mInputLayoutCity.getEditText().setText("");
            return;
        }

        if (mCurrentCustomer.getCity() != null) {
            final State stateSelected = mStatesAdapter.getItem(position);
            if (stateSelected != null) {
                if (!stateSelected.equals(mCurrentCustomer.getCity().getState())) {
                    mCurrentCustomer.withCity(null);
                    mInputLayoutCity.getEditText().setText("");
                }
            }
        }
    }

    @OnTouch(R.id.edit_text_city) boolean onEditTextCityTouched(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mSpinnerState.getSelectedItem() == null) {
                mSpinnerState.setError(R.string.add_customer_select_state_error);
            } else {
                selectState(mSpinnerState.getSelectedItemPosition());
            }
            return true;
        }
        return false;
    }

    @Subscribe(sticky = true) public void onSelectedCity(SelectedCityEvent event) {
        mCurrentCustomer.withCity(event.getCity());
        mInputLayoutCity.getEditText().setText(event.getCity().getName());
        eventBus().removeStickyEvent(SelectedCityEvent.class);
    }

    @OnTextChanged(R.id.edit_text_city) void onEditTextCityChanged(CharSequence text) {
        mInputLayoutCity.setHint(isEmpty(text) ?
                getString(R.string.add_customer_select_city_hint) :
                getString(R.string.add_customer_city_selected_hint));
    }

    @OnTextChanged(edit_text_postal_code) void onEditTextPostalCodeChanged(CharSequence text) {
        mCurrentCustomer.withPostalCode(text.toString());
    }

    @OnTextChanged(edit_text_address_complement) void onEditTextAddressComplementChanged(CharSequence text) {
        mCurrentCustomer.withAddressComplement(text.toString());
    }

    @OnTextChanged(edit_text_main_phone) void onEditTextMainPhoneChanged(CharSequence text) {
        mCurrentCustomer.withMainPhone(text.toString());
    }

    @OnTextChanged(edit_text_other_phone) void onEditTextOtherPhoneChanged(CharSequence text) {
        mCurrentCustomer.withSecondaryPhone(text.toString());
    }

    private void setUpViews() {
        mSpinnerTypeOfPerson.setAdapter(mTypeOfPersonAdapter =
                new TypeOfPersonAdapter(this, Arrays.asList(TypeOfPerson.values())));
    }

    private void loadCurrentCustomer() {
        SelectedCustomerEvent event = eventBus().getStickyEvent(SelectedCustomerEvent.class);

        if (event != null) {
            mCurrentCustomer = event.getCustomer().withStatus(STATUS_MODIFIED);
            mIsEdition = true;

            getSupportActionBar().setTitle(R.string.add_customer_editing_title);

            mInputLayoutCpfOrCnpj.getEditText().setText(mCurrentCustomer.getCpfOrCnpj());
            mInputLayoutSocialName.getEditText().setText(mCurrentCustomer.getName());

            if (!isEmpty(mCurrentCustomer.getFantasyName())) {
                mInputLayoutFantasyName.getEditText().setText(mCurrentCustomer.getFantasyName());
            }

            if (!isEmpty(mCurrentCustomer.getEmail())) {
                mInputLayoutEmail.getEditText().setText(mCurrentCustomer.getEmail());
            }

            if (!isEmpty(mCurrentCustomer.getAddress())) {
                mInputLayoutAddress.getEditText().setText(mCurrentCustomer.getAddress());
            }

            if (!isEmpty(mCurrentCustomer.getAddressNumber())) {
                mInputLayoutAddressNumber.getEditText()
                        .setText(mCurrentCustomer.getAddressNumber());
            }

            if (!isEmpty(mCurrentCustomer.getDistrict())) {
                mInputLayoutDistrict.getEditText().setText(mCurrentCustomer.getDistrict());
            }

            mInputLayoutCity.getEditText().setText(mCurrentCustomer.getCity().getName());

            if (!isEmpty(mCurrentCustomer.getPostalCode())) {
                mInputLayoutPostalCode.getEditText().setText(mCurrentCustomer.getPostalCode());
            }

            if (!isEmpty(mCurrentCustomer.getAddressComplement())) {
                mInputLayoutAddressComplement.getEditText()
                        .setText(mCurrentCustomer.getAddressComplement());
            }

            if (!isEmpty(mCurrentCustomer.getMainPhone())) {
                mInputLayoutMainPhone.getEditText().setText(mCurrentCustomer.getMainPhone());
            }

            if (!isEmpty(mCurrentCustomer.getSecondaryPhone())) {
                mInputLayoutOtherPhone.getEditText().setText(mCurrentCustomer.getSecondaryPhone());
            }

            TypeOfPerson typeOfPerson = TypeOfPerson.valueOf(mCurrentCustomer.getType());
            if (typeOfPerson != null) {
                int position = mTypeOfPersonAdapter.getPosition(typeOfPerson);

                if (position != -1) {
                    mSpinnerTypeOfPerson.setSelection(position + 1, true);
                }
            }
        } else {
            mCurrentCustomer = new Customer().withStatus(STATUS_CREATED);
            mIsEdition = false;
        }
    }

    private void loadStates() {
        if (mStateRepository == null) {
            mStateRepository = provideStateRepository();
        }
        if (mStatesAdapter == null) {
            subscribeToLoadStates();
        }
    }

    private void subscribeToLoadStates() {
        mCurrentSubscription = mStateRepository.findAll(NAME, true)
                .observeOn(mainThread())
                .subscribe(this::showStates, this::handleLoadStatesError);
    }

    private void showStates(List<State> states) {
        mSpinnerState.setAdapter(mStatesAdapter = new StatesAdapter(this, states));

        if (mCurrentCustomer.getCity() != null) {
            int position = mStatesAdapter.getPosition(mCurrentCustomer.getCity().getState());
            if (position != -1) {
                mSpinnerState.setSelection(position + 1, true);
            }
        }
    }

    private void handleLoadStatesError(Throwable e) {
        Timber.e(e, "Could not load states");
        new MaterialDialog.Builder(this)
                .content(add_customer_unknown_error_loading_states)
                .positiveText(R.string.all_retry)
                .onPositive((dialog, which) -> loadStates())
                .show();
    }

    private void searchPostalCode() {
        if (!connectivity().isOnline()) {
            new MaterialDialog.Builder(this)
                    .title(R.string.all_offline_message_title)
                    .content(R.string.all_offline_message)
                    .positiveText(R.string.all_retry)
                    .onPositive((dialog, which) -> searchPostalCode())
                    .neutralText(android.R.string.ok)
                    .show();
            return;
        }

        new MaterialDialog.Builder(this)
                .title(R.string.add_customer_zip_code_search_title)
                .inputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_SIGNED)
                .inputRange(8, 8)
                .input(R.string.add_customer_zip_code_search_hint, 0, false,
                        (dialog, input) -> subscribeToSearchPostalCode(input.toString()))
                .show();
    }

    private void subscribeToSearchPostalCode(String value) {
        EventTracker.action(ACTION_CEP_SEARCHED);
        mCurrentSubscription = providePostalCodeApi().get(value)
                .observeOn(mainThread())
                .subscribe(createPostalCodeSubscriber());
    }

    private Subscriber<PostalCode> createPostalCodeSubscriber() {
        return new Subscriber<PostalCode>() {
            @Override public void onStart() {
                showProgressDialog(add_customer_searching_postal_code_message);
            }

            @Override public void onError(final Throwable e) {
                handleSearchPostalCodeError(e);
            }

            @Override public void onNext(final PostalCode postalCode) {
                showPostalCodeInfo(postalCode);
            }

            @Override public void onCompleted() {}
        };
    }

    private void handleSearchPostalCodeError(final Throwable e) {
        hideProgressDialog();
        Timber.e(e, "Could not search postal code");

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .neutralText(android.R.string.ok);

        if (e instanceof HttpException) {
            builder.content(R.string.all_server_error_message);
        } else if (e instanceof IOException) {
            builder.content(R.string.all_network_error_message);
        } else {
            builder.content(R.string.all_unknown_error_message);
        }

        builder
                .positiveText(R.string.all_retry)
                .onPositive((dialog, which) -> searchPostalCode())
                .show();
    }

    private void showPostalCodeInfo(PostalCode postalCode) {
        hideProgressDialog();
        mInputLayoutDistrict.getEditText().setText(postalCode.district);
        mInputLayoutAddress.getEditText().setText(postalCode.address);
        mInputLayoutPostalCode.getEditText().setText(postalCode.postalCode);

        for (int i = 0; i < mStatesAdapter.getCount(); i++) {
            if (mStatesAdapter.getItem(i).getName().equalsIgnoreCase(postalCode.stateInfo.name)) {
                mSpinnerState.setSelection(i + 1, true);
                loadCityByName(postalCode.cityName);
            }
        }
    }

    private void loadCityByName(String name) {
        if (mCityRepository == null) {
            mCityRepository = provideCityRepository();
        }
        subscribeToLoadCityByName(name);
    }

    private void subscribeToLoadCityByName(String name) {
        mCurrentSubscription = mCityRepository.findFirst(new CityByNameSpecification(name))
                .observeOn(mainThread())
                .subscribe(this::showCity, (e) -> handleLoadCityByNameError(e, name));
    }

    private void showCity(final City city) {
        mCurrentCustomer.withCity(city);
        mInputLayoutCity.getEditText().setText(city.getName());
    }

    private void handleLoadCityByNameError(Throwable e, final String name) {
        Timber.e(e, "Could not load city by name");
        new MaterialDialog.Builder(this)
                .content(add_customer_unknown_error_loading_city)
                .positiveText(R.string.all_retry)
                .onPositive((dialog, which) -> loadCityByName(name))
                .show();
    }

    private void saveCustomer() {
        clearInputErrors();

        final boolean emptyRequiredInputs = checkEmptyRequiredInputs();
        final boolean validInputs = checkValidInputs();

        if (!emptyRequiredInputs && validInputs) {
            subscribeToSaveCustomer();
        }
    }

    private void subscribeToSaveCustomer() {
        if (mCustomerRepository == null) {
            mCustomerRepository = provideCustomerRepository();
        }

        if (mIsEdition) {
            mCurrentSubscription = saveCustomerAsObservable()
                    .subscribe(createCustomerSubscriber());
        } else {
            mCurrentSubscription = saveCompanyCustomerAsObservable()
                    .subscribe(createCompanyCustomerSubscriber());
        }
    }

    private Observable<Customer> saveCustomerAsObservable() {
        return mCustomerRepository.save(mCurrentCustomer)
                .map(customer -> mCurrentCustomer = customer)
                .observeOn(mainThread());
    }

    private Subscriber<Customer> createCustomerSubscriber() {
        return new Subscriber<Customer>() {
            @Override public void onStart() {
                showProgressDialog(all_saving_message);
            }

            @Override public void onError(final Throwable e) {
                handleSaveCustomerError(e);
            }

            @Override public void onNext(final Customer customer) {
                showSuccessSavingCustomer();
            }

            @Override public void onCompleted() {}
        };
    }

    private Observable<CompanyCustomer> saveCompanyCustomerAsObservable() {
        return mCustomerRepository.save(mCurrentCustomer)
                .map(customer -> mCurrentCustomer = customer)
                .flatMap(customer ->
                        saveCompanyCustomer(loadSelectedCompany(), customer))
                .observeOn(mainThread());
    }

    private Company loadSelectedCompany() {
        if (mLoggedUser == null) {
            mLoggedUser = eventBus().getStickyEvent(LoggedInUserEvent.class).getUser();
        }
        return mLoggedUser.getDefaultCompany();
    }

    private Observable<CompanyCustomer> saveCompanyCustomer(
            final Company company, final Customer customer) {
        if (mCompanyCustomerRepository == null) {
            mCompanyCustomerRepository = provideCompanyCustomerRepository();
        }
        return mCompanyCustomerRepository.save(from(company, customer));
    }

    private Subscriber<CompanyCustomer> createCompanyCustomerSubscriber() {
        return new Subscriber<CompanyCustomer>() {
            @Override public void onStart() {
                showProgressDialog(all_saving_message);
            }

            @Override public void onError(final Throwable e) {
                handleSaveCustomerError(e);
            }

            @Override public void onNext(final CompanyCustomer companyCustomer) {
                showSuccessSavingCustomer();
            }

            @Override public void onCompleted() {}
        };
    }

    private void handleSaveCustomerError(final Throwable e) {
        hideProgressDialog();
        Timber.e(e, "Could not save customer");

        new MaterialDialog.Builder(this)
                .content(all_unknown_error_saving_form)
                .positiveText(R.string.all_retry)
                .onPositive((dialog, which) -> saveCustomer())
                .show();
    }

    private void showSuccessSavingCustomer() {
        EventTracker.action(ACTION_SAVED_CUSTOMER);
        hideProgressDialog();
        Snackbar.make(mCoordinatorLayoutContainer, add_customer_saved_successfully, LENGTH_SHORT)
                .addCallback(new Snackbar.Callback() {
                    @Override public void onDismissed(final Snackbar snackbar, final int event) {
                        eventBus().postSticky(SavedCustomerEvent.newEvent(mCurrentCustomer));
                        finish();
                    }
                })
                .show();
    }

    private void clearInputErrors() {
        mSpinnerTypeOfPerson.setError(null);
        mInputLayoutCpfOrCnpj.setError(null);
        mInputLayoutSocialName.setError(null);
        mInputLayoutEmail.setError(null);
        mSpinnerState.setError(null);
        mInputLayoutCity.setError(null);
        mInputLayoutPostalCode.setError(null);
        mInputLayoutMainPhone.setError(null);
        mInputLayoutOtherPhone.setError(null);
    }
    
    private boolean checkEmptyRequiredInputs() {
        final boolean emptyTypeOfPerson = checkEmptyRequiredInput(mSpinnerTypeOfPerson);
        final boolean emptyCpfOrCnpj = checkEmptyRequiredInput(mInputLayoutCpfOrCnpj);
        final boolean emptySocialName = checkEmptyRequiredInput(mInputLayoutSocialName);
        final boolean emptyState = checkEmptyRequiredInput(mSpinnerState);
        final boolean emptyCity = checkEmptyRequiredInput(mInputLayoutCity);

        return emptyTypeOfPerson || emptyCpfOrCnpj || emptySocialName || emptyState || emptyCity;
    }
    
    private boolean checkEmptyRequiredInput(MaterialSpinner spinner) {
        if (spinner.getSelectedItem() == null) {
            spinner.setError(getString(all_required_field_error));
            return true;
        }
        return false;
    }
    
    private boolean checkEmptyRequiredInput(TextInputLayout inputLayout) {
        if (isEmpty(inputLayout.getEditText().getText())) {
            inputLayout.setError(getString(all_required_field_error));
            return true;
        }
        return false;
    }

    private boolean checkValidInputs() {
        final boolean validCpfOrCnpj = checkValidCpfOrCnpjInput();
        final boolean validEmail = checkValidEmailInput();
        final boolean validPostalCode = checkValidPostalCodeInput();
        final boolean validMainPhone = checkValidMainPhoneInput();
        final boolean validOtherPhone = checkValidOtherPhoneInput();

        return validCpfOrCnpj && validEmail && validPostalCode && validMainPhone && validOtherPhone;
    }
    
    private boolean checkValidCpfOrCnpjInput() {
        final String cpfOrCnpj = mCurrentCustomer.getCpfOrCnpj();
        if ((mCurrentCustomer.getType() != null) && !isEmpty(cpfOrCnpj)) {
            TypeOfPerson typeOfPerson = mTypeOfPersonAdapter
                    .getItem(mSpinnerTypeOfPerson.getSelectedItemPosition());

            if (typeOfPerson.equals(PRIVATE_INDIVIDUAL) && (cpfOrCnpj.length() != 11)) {
                mInputLayoutCpfOrCnpj.setError(getString(add_customer_invalid_cpf));
                return false;
            } else if (typeOfPerson.equals(LEGAL_ENTITY) && (cpfOrCnpj.length() != 14)) {
                mInputLayoutCpfOrCnpj.setError(getString(add_customer_invalid_cnpj));
                return false;
            }
        }
        return true;
    }

    private boolean checkValidEmailInput() {
        if (!isEmpty(mCurrentCustomer.getEmail())) {
            if (!isValidEmail(mCurrentCustomer.getEmail())) {
                mInputLayoutEmail.setError(getString(add_customer_invalid_email));
                return false;
            }
        }
        return true;
    }

    private boolean checkValidPostalCodeInput() {
        if (!isEmpty(mCurrentCustomer.getPostalCode())) {
            if (!isValidPostalCode(mCurrentCustomer.getPostalCode())) {
                mInputLayoutPostalCode.setError(getString(add_customer_invalid_postal_code));
                return false;
            }
        }
        return true;
    }

    private boolean checkValidMainPhoneInput() {
        if (!isEmpty(mCurrentCustomer.getMainPhone())) {
            if (!isValidPhoneNumber(mCurrentCustomer.getMainPhone())) {
                mInputLayoutMainPhone.setError(getString(add_customer_invalid_phone_number));
                return false;
            }
        }
        return true;
    }

    private boolean checkValidOtherPhoneInput() {
        if (!isEmpty(mCurrentCustomer.getSecondaryPhone())) {
            if (!isValidPhoneNumber(mCurrentCustomer.getSecondaryPhone())) {
                mInputLayoutOtherPhone.setError(getString(add_customer_invalid_phone_number));
                return false;
            }
        }
        return true;
    }

    private void selectState(int position) {
        final State stateSelected = mStatesAdapter.getItem(position);
        if (stateSelected != null) {
            eventBus().postSticky(SelectedStateEvent.newEvent(stateSelected));
            navigate().toCityList();
        }
    }

    private void showProgressDialog(int titleRes) {
        mProgressDialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .title(titleRes)
                .content(R.string.all_please_wait)
                .cancelable(true)
                .canceledOnTouchOutside(false)
                .cancelListener(dialog -> cancelCurrentSubscription())
                .show();
    }

    private void cancelCurrentSubscription() {
        if (mCurrentSubscription != null && mCurrentSubscription.isUnsubscribed()) {
            mCurrentSubscription.unsubscribe();
            mCurrentSubscription = null;
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override protected void onDestroy() {
        eventBus().removeStickyEvent(SelectedCustomerEvent.class);
        cancelCurrentSubscription();
        super.onDestroy();
    }
}
