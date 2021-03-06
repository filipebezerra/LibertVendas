package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderform;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.design.widget.Snackbar.Callback;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.company.paymentmethod.PaymentMethodsByCompanySpecification;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRepository;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PaymentMethod;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems.AddedOrderItemsEvent;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems.SelectedPriceTableEvent;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.customerlist.SelectedCustomerEvent;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker;
import br.com.libertsolutions.libertvendas.app.presentation.widget.MaterialSpinner;
import butterknife.BindView;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static android.text.TextUtils.isEmpty;
import static android.widget.AdapterView.INVALID_POSITION;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_discount_percentage;
import static br.com.libertsolutions.libertvendas.app.R.id.edit_text_observation;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_customer_name;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_discount_percentage;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_issue_date;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_observation;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_total_items;
import static br.com.libertsolutions.libertvendas.app.R.id.input_layout_total_order;
import static br.com.libertsolutions.libertvendas.app.R.id.spinner_payment_method;
import static br.com.libertsolutions.libertvendas.app.R.layout.fragment_order_form;
import static br.com.libertsolutions.libertvendas.app.R.string.all_correct_fields_error;
import static br.com.libertsolutions.libertvendas.app.R.string.all_correct_required_fields_error;
import static br.com.libertsolutions.libertvendas.app.R.string.all_please_wait;
import static br.com.libertsolutions.libertvendas.app.R.string.all_required_field_error;
import static br.com.libertsolutions.libertvendas.app.R.string.all_retry;
import static br.com.libertsolutions.libertvendas.app.R.string.all_saving_message;
import static br.com.libertsolutions.libertvendas.app.R.string.all_unknown_error_saving_form;
import static br.com.libertsolutions.libertvendas.app.R.string.order_form_discount_value_not_allowed;
import static br.com.libertsolutions.libertvendas.app.R.string.order_form_payment_method_with_no_discount;
import static br.com.libertsolutions.libertvendas.app.R.string.order_form_salesman_cant_apply_discount;
import static br.com.libertsolutions.libertvendas.app.R.string.order_form_saved_successfully;
import static br.com.libertsolutions.libertvendas.app.R.string.order_form_unknown_error_loading_payment_methods;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providePaymentMethodRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providerOrderRepository;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_CREATED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_MODIFIED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderType.ORDER_TYPE_NORMAL;
import static br.com.libertsolutions.libertvendas.app.presentation.util.CurrencyUtils.round;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.getCurrentDateTimeInMillis;
import static br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker.ACTION_SAVED_ORDER;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsDateTime;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsPercent;
import static br.com.libertsolutions.libertvendas.app.presentation.util.NumberUtils.toDouble;
import static br.com.libertsolutions.libertvendas.app.presentation.util.NumberUtils.toFloat;
import static br.com.libertsolutions.libertvendas.app.presentation.util.NumberUtils.withDefaultValue;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class OrderFormStepFragment extends BaseFragment implements BlockingStep {

    private PaymentMethodRepository mPaymentMethodRepository;

    private Subscription mCurrentSubscription;

    private PaymentMethodsAdapter mPaymentMethodsAdapter;

    private LoggedUser mLoggedUser;

    private Order mCurrentOrder;

    private OrderRepository mOrderRepository;

    private MaterialDialog mProgressDialog;

    @BindView(input_layout_issue_date) TextInputLayout mInputLayoutIssueDate;
    @BindView(input_layout_customer_name) TextInputLayout mInputLayoutCustomerName;
    @BindView(spinner_payment_method) MaterialSpinner mSpinnerPaymentMethods;
    @BindView(input_layout_total_items) TextInputLayout mInputLayoutTotalItems;
    @BindView(input_layout_discount_percentage) TextInputLayout mInputLayoutDiscountPercentage;
    @BindView(input_layout_total_order) TextInputLayout mInputLayoutTotalOrder;
    @BindView(input_layout_observation) TextInputLayout mInputLayoutObservation;

    public static OrderFormStepFragment newInstance() {
        return new OrderFormStepFragment();
    }

    @Override protected int provideContentViewResource() {
        return fragment_order_form;
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventBus().register(this);
        loadCurrentOrder();
        loadPaymentMethods();
    }

    @Subscribe(priority = 2) public void onSelectedCustomer(SelectedCustomerEvent event) {
        setCustomer(event.getCustomer());
    }

    @Subscribe public void onAddedOrderItems(AddedOrderItemsEvent event) {
        setOrderItems(event.getOrderItems());
        calculateDiscount();
    }

    @Subscribe public void onSelectedPriceTable(SelectedPriceTableEvent event) {
        setPriceTable(event.getPriceTable());
    }

    @OnTextChanged(edit_text_discount_percentage) void onEditTextDiscountChanged(CharSequence text) {
        setDiscountPercentage(text.toString());
        calculateDiscount();
    }

    @OnItemSelected(spinner_payment_method) void onSpinnerPaymentMethodItemSelected(int position) {
        if (position == INVALID_POSITION) {
            setPaymentMethod(null);
        } else {
            final PaymentMethod paymentMethod = mPaymentMethodsAdapter.getItem(position);
            if (paymentMethod != null) {
                setPaymentMethod(paymentMethod);
                setPaymentMethodFloatingLabelText(paymentMethod.getDiscountPercentage());
            }
        }
    }

    @OnTextChanged(edit_text_observation) void onEditTextObservationChanged(CharSequence text) {
        mCurrentOrder.withObservation(text.toString());
    }

    @Override public VerificationError verifyStep() {
        clearInputErrors();

        if (checkEmptyRequiredInput(mSpinnerPaymentMethods)) {
            return new VerificationError(getString(all_correct_required_fields_error));
        } else if (!checkDiscountValue()) {
            return new VerificationError(getString(all_correct_fields_error));
        }

        return null;
    }

    @Override public void onSelected() {}

    @Override public void onError(@NonNull final VerificationError error) {
        Snackbar.make(getView(), error.getErrorMessage(), LENGTH_SHORT).show();
    }

    @UiThread
    @Override public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {}

    @UiThread
    @Override public void onCompleteClicked(
            final StepperLayout.OnCompleteClickedCallback callback) {
        subscribeToSaveOrder(callback);
    }

    @UiThread
    @Override public void onBackClicked(final StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    private void subscribeToSaveOrder(final StepperLayout.OnCompleteClickedCallback callback) {
        if (mOrderRepository == null) {
            mOrderRepository = providerOrderRepository();
        }

        mCurrentSubscription = saveOrderAsObservable()
                .subscribe(createOrderSubscriber(callback));
    }

    private Observable<Order> saveOrderAsObservable() {
        return mOrderRepository.save(mCurrentOrder)
                .map(order -> mCurrentOrder = order)
                .observeOn(mainThread());
    }

    private Subscriber<Order> createOrderSubscriber(
            final StepperLayout.OnCompleteClickedCallback callback) {
        return new Subscriber<Order>() {
            @Override public void onStart() {
                showProgressDialog(all_saving_message);
            }

            @Override public void onError(final Throwable e) {
                handleSaveOrderError(e, callback);
            }

            @Override public void onNext(final Order order) {}

            @Override public void onCompleted() {
                showSuccessSavingOrder(callback);
            }
        };
    }

    private void handleSaveOrderError(final Throwable e,
            final StepperLayout.OnCompleteClickedCallback callback) {
        hideProgressDialog();
        Timber.e(e, "Could not save order");

        new MaterialDialog.Builder(getContext())
                .content(all_unknown_error_saving_form)
                .positiveText(all_retry)
                .onPositive((dialog, which) -> subscribeToSaveOrder(callback))
                .show();
    }

    private void showSuccessSavingOrder(final StepperLayout.OnCompleteClickedCallback callback) {
        EventTracker.action(ACTION_SAVED_ORDER);
        hideProgressDialog();
        Snackbar.make(getView(), order_form_saved_successfully, LENGTH_SHORT)
                .addCallback(new Callback() {
                    @Override public void onDismissed(final Snackbar snackbar, final int event) {
                        eventBus().postSticky(SavedOrderEvent.newEvent(mCurrentOrder));
                        callback.complete();
                    }
                })
                .show();
    }

    private void loadCurrentOrder() {
        SelectedOrderEvent event = eventBus().getStickyEvent(SelectedOrderEvent.class);

        if (event != null) {
            if (event.isDuplicateOrder()) {
                mCurrentOrder = event.getOrder().copy();
            } else {
                mCurrentOrder = event.getOrder().withStatus(STATUS_MODIFIED);
            }

            mInputLayoutIssueDate.getEditText()
                    .setText(formatAsDateTime(mCurrentOrder.getIssueDate()));
            mInputLayoutCustomerName.getEditText()
                    .setText(mCurrentOrder.getCustomer().getName());
            mInputLayoutTotalItems.getEditText()
                    .setText(formatAsCurrency(mCurrentOrder.getTotalItems()));
            mInputLayoutDiscountPercentage.getEditText()
                    .setText(String.valueOf(mCurrentOrder.getDiscountPercentage()));
            mInputLayoutTotalOrder.getEditText()
                    .setText(formatAsCurrency(mCurrentOrder.getTotalOrder()));

            if (!isEmpty(mCurrentOrder.getObservation())) {
                mInputLayoutObservation.getEditText()
                        .setText(mCurrentOrder.getObservation());
            }
        } else {
            mCurrentOrder = new Order()
                    .withType(ORDER_TYPE_NORMAL)
                    .withSalesmanId(getLoggedUser().getSalesman().getSalesmanId())
                    .withCompanyId(getLoggedUser().getDefaultCompany().getCompanyId())
                    .withStatus(STATUS_CREATED);
            setIssueDate(getCurrentDateTimeInMillis());
        }
    }

    private void loadPaymentMethods() {
        if (mPaymentMethodRepository == null) {
            mPaymentMethodRepository = providePaymentMethodRepository();
        }

        mCurrentSubscription = mPaymentMethodRepository
                .query(new PaymentMethodsByCompanySpecification(getSelectedCompanyId()))
                .observeOn(mainThread())
                .subscribe(this::showPaymentMethods, this::handleLoadPaymentMethodsError);
    }

    private LoggedUser getLoggedUser() {
        if (mLoggedUser == null) {
            mLoggedUser = eventBus().getStickyEvent(LoggedInUserEvent.class).getUser();
        }
        return mLoggedUser;
    }

    private int getSelectedCompanyId() {
        return getLoggedUser().getDefaultCompany().getCompanyId();
    }

    private void showPaymentMethods(List<PaymentMethod> paymentMethods) {
        mSpinnerPaymentMethods.setAdapter(
                mPaymentMethodsAdapter = new PaymentMethodsAdapter(getContext(), paymentMethods));

        if (mCurrentOrder != null) {
            int mSelectedPaymentMethod = mPaymentMethodsAdapter
                    .getPosition(mCurrentOrder.getPaymentMethod());
            if (mSelectedPaymentMethod != -1) {
                mSpinnerPaymentMethods.setSelection(mSelectedPaymentMethod + 1, true);
            }
        }
    }

    private void handleLoadPaymentMethodsError(Throwable e) {
        Timber.e(e, "Could not load payment methods");
        new MaterialDialog.Builder(getContext())
                .content(order_form_unknown_error_loading_payment_methods)
                .positiveText(all_retry)
                .onPositive((dialog, which) -> loadPaymentMethods())
                .show();
    }

    private void setIssueDate(final long issueDate) {
        mCurrentOrder.withIssueDate(issueDate);
        mInputLayoutIssueDate.getEditText().setText(formatAsDateTime(issueDate));
    }

    private void setCustomer(final Customer customer) {
        mCurrentOrder.withCustomer(customer);
        mInputLayoutCustomerName.getEditText().setText(customer.getName());
    }

    private void setOrderItems(final List<OrderItem> orderItems) {
        mCurrentOrder.withItems(orderItems);
        mInputLayoutTotalItems.getEditText()
                .setText(formatAsCurrency(mCurrentOrder.getTotalItems()));
    }

    private void setPriceTable(final PriceTable priceTable) {
        mCurrentOrder.withPriceTable(priceTable);
    }

    private void setDiscountPercentage(final String discountStr) {
        mCurrentOrder.withDiscountPercentage(toFloat(discountStr));
    }

    private void calculateDiscount() {
        final float discountPercentage = mCurrentOrder.getDiscountPercentage();
        final double discount = round(mCurrentOrder.getTotalItems() * discountPercentage / 100);

        mCurrentOrder.withDiscount(discount);
        mInputLayoutTotalOrder.getEditText()
                .setText(formatAsCurrency(mCurrentOrder.getTotalOrder()));
    }

    private void setPaymentMethod(final PaymentMethod paymentMethod) {
        mCurrentOrder.withPaymentMethod(paymentMethod);
    }

    private void setPaymentMethodFloatingLabelText(final Float discountPercentage) {
        if (withDefaultValue(discountPercentage, 0) == 0) {
            mSpinnerPaymentMethods.setFloatingLabelText(
                    getString(R.string.order_form_payment_method_label,
                            getString(R.string.order_form_no_discount)));
        } else {
            mSpinnerPaymentMethods.setFloatingLabelText(
                    getString(R.string.order_form_payment_method_label,
                            formatAsPercent(discountPercentage)));
        }
    }

    private void clearInputErrors() {
        mInputLayoutDiscountPercentage.setError(null);
        mSpinnerPaymentMethods.setError(null);
    }

    private boolean checkEmptyRequiredInput(MaterialSpinner spinner) {
        if (spinner.getSelectedItem() == null) {
            spinner.setError(getString(all_required_field_error));
            return true;
        }
        return false;
    }

    private boolean checkDiscountValue() {
        final String discountPercentageStr
                = mInputLayoutDiscountPercentage.getEditText().getText().toString();

        if (isEmpty(discountPercentageStr)) {
            return true;
        }

        final double discountPercentage = toDouble(discountPercentageStr);

        if (discountPercentage == 0) {
            return true;
        }

        if (!getLoggedUser().getSalesman().getCanApplyDiscount()) {
            mInputLayoutDiscountPercentage.setError(getString(order_form_salesman_cant_apply_discount));
            return false;
        }

        final int selectedPaymentMethodPosition = mSpinnerPaymentMethods.getSelectedItemPosition();

        if (selectedPaymentMethodPosition == INVALID_POSITION) {
            return true;
        }

        PaymentMethod paymentMethod = mPaymentMethodsAdapter.getItem(selectedPaymentMethodPosition);

        //noinspection ConstantConditions
        if (paymentMethod.getDiscountPercentage() == 0) {
            mInputLayoutDiscountPercentage.setError(getString(order_form_payment_method_with_no_discount));
            return false;
        }

        if (discountPercentage > paymentMethod.getDiscountPercentage()) {
            mInputLayoutDiscountPercentage.setError(getString(order_form_discount_value_not_allowed));
            return false;
        }

        return true;
    }

    private void showProgressDialog(int titleRes) {
        mProgressDialog = new MaterialDialog.Builder(getContext())
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .title(titleRes)
                .content(all_please_wait)
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

    @Override public void onDestroyView() {
        cancelCurrentSubscription();
        eventBus().removeStickyEvent(SelectedOrderEvent.class);
        eventBus().unregister(this);
        super.onDestroyView();
    }
}
