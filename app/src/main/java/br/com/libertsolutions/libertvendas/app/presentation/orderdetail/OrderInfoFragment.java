package br.com.libertsolutions.libertvendas.app.presentation.orderdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.widget.FormasPagamentoAdapter;
import butterknife.BindView;
import smtchahal.materialspinner.MaterialSpinner;

import static java.util.Collections.singletonList;

/**
 * @author Filipe Bezerra
 */
public class OrderInfoFragment extends LibertVendasFragment implements OrderInfoContract.View {

    @BindView(R.id.input_layout_data_emissao) protected TextInputLayout mDateIssueTextInputLayout;
    @BindView(R.id.input_layout_cliente) protected TextInputLayout mCustomerTextInputLayout;
    @BindView(R.id.input_layout_total_produtos) protected TextInputLayout mTotalProductsTextInputLayout;
    @BindView(R.id.input_layout_desconto) protected TextInputLayout mDiscountTextInputLayout;
    @BindView(R.id.spinner_forma_pagamento) protected MaterialSpinner mFormPaymentSpinner;
    @BindView(R.id.input_layout_observacao) protected TextInputLayout mNoteTextInputLayout;

    private OrderInfoContract.Presenter mPresenter;

    public static OrderInfoFragment create() {
        return new OrderInfoFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_order_info;
    }

    @Override public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new OrderInfoPresenter();
        mPresenter.attachView(this);

        final Pedido order = hostActivity().getIntent().getExtras()
                .getParcelable(OrderDetailActivity.EXTRA_ORDER_TO_DETAIL);
        mPresenter.initializeView(order);
    }

    @Override public void displayIssueDate(final String issueDate) {
        mDateIssueTextInputLayout.getEditText().setText(issueDate);
    }

    @Override public void displayCustomerName(final String customerName) {
        mCustomerTextInputLayout.getEditText().setText(customerName);
    }

    @Override public void displayTotalProducts(final String totalProducts) {
        mTotalProductsTextInputLayout.getEditText().setText(totalProducts);
    }

    @Override public void displayDiscount(final String discount) {
        mDiscountTextInputLayout.getEditText().setText(discount);
    }

    @Override public void displayFormPayment(final FormaPagamento formPayment) {
        mFormPaymentSpinner.setAdapter(new FormasPagamentoAdapter(getActivity(),
                singletonList(formPayment)));
        mFormPaymentSpinner.setSelection(1);
    }

    @Override public void displayNote(final String note) {
        mNoteTextInputLayout.getEditText().setText(note);
    }
}
