package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.util.AndroidUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import java.util.Calendar;
import java.util.List;
import smtchahal.materialspinner.MaterialSpinner;

import static br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando.FinalizandoPedidoDependencyContainer.createDependencyContainer;

/**
 * @author Filipe Bezerra
 */
public class FinalizandoPedidoFragment extends LibertVendasFragment
        implements FinalizandoPedidoContract.View {

    public static final String EXTRA_PEDIDO_EDICAO
            = FinalizandoPedidoFragment.class.getSimpleName() + ".argExtraPedidoEdicao";

    @BindView(R.id.spinner_forma_pagamento) protected MaterialSpinner mSpinnerFormaPagamento;

    private FinalizandoPedidoContract.Presenter mPresenter;

    private SparseArray<ViewGroup> mViewFields = new SparseArray<>();

    private SparseArray<ViewGroup> mRequiredFields = new SparseArray<>();

    public static FinalizandoPedidoFragment newInstance(Pedido pPedidoEmEdicao) {
        FinalizandoPedidoFragment fragment = new FinalizandoPedidoFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(EXTRA_PEDIDO_EDICAO, pPedidoEmEdicao);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_finalizando_pedido;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new FinalizandoPedidoPresenter(createDependencyContainer(this));
        mPresenter.attachView(this);
        mPresenter.initializeView(getArguments().getParcelable(EXTRA_PEDIDO_EDICAO));
        mPresenter.registerForEvents();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_finalizando_pedido, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            mPresenter.handleActionSave();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override public void displayFormasPagamento(List<FormaPagamento> pFormasPagamentoList) {
        mSpinnerFormaPagamento.setAdapter(
                new FormasPagamentoAdapter(getContext(), pFormasPagamentoList));
    }

    @Override public void setViewFields(List<Integer> pViewIds) {
        mViewFields.clear();
        for (Integer viewId : pViewIds) {
            mViewFields.put(viewId, ButterKnife.findById(getView(), viewId));
        }
    }

    @Override public void setRequiredFields(List<Integer> pRequiredViewIds) {
        mRequiredFields.clear();
        for (Integer viewId : pRequiredViewIds) {
            mRequiredFields.put(viewId, mViewFields.get(viewId));
        }
    }

    @Override public void setViewValue(int pViewId, int pPosition) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof MaterialSpinner) {
            ((MaterialSpinner) field).setSelection(pPosition + 1, true);
        }
    }

    @Override public void setViewValue(int pViewId, String pViewValue) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof TextInputLayout) {
            ((TextInputLayout) field).getEditText().setText(pViewValue);
        }
    }

    @Override public void showCalendarPicker(Calendar pDataPreSelecionada) {
        new CalendarDatePickerDialogFragment()
                .setOnDateSetListener((dialog, year, monthOfYear, dayOfMonth) ->
                        mPresenter.handleDateSelected(year, monthOfYear, dayOfMonth))
                .setPreselectedDate(
                        pDataPreSelecionada.get(Calendar.YEAR),
                        pDataPreSelecionada.get(Calendar.MONTH),
                        pDataPreSelecionada.get(Calendar.DAY_OF_MONTH)
                )
                .setThemeCustom(R.style.Widget_Libert_BetterPickersDialogs)
                .show(getChildFragmentManager(), "DatePickerDialog");
    }

    @Override public void hideRequiredMessages() {
        for (int i = 0; i < mRequiredFields.size(); i++) {
            ViewGroup requiredField = mRequiredFields.valueAt(i);
            if (requiredField instanceof MaterialSpinner) {
                ((MaterialSpinner) requiredField).setError(null);
            } else if (requiredField instanceof TextInputLayout) {
                ((TextInputLayout) requiredField).setError(null);
            }
        }
    }

    @Override public boolean hasEmptyRequiredFields() {
        for (int i = 0; i < mRequiredFields.size(); i++) {
            ViewGroup requiredField = mRequiredFields.valueAt(i);

            boolean isEmpty = false;
            if (requiredField instanceof MaterialSpinner) {
                isEmpty = ((MaterialSpinner) requiredField).getSelectedItem() == null;
            } else if (requiredField instanceof TextInputLayout) {
                isEmpty = TextUtils
                        .isEmpty(((TextInputLayout) requiredField).getEditText().getText());
            }

            if (isEmpty) {
                return true;
            }
        }
        return false;
    }

    @Override public void displayRequiredFieldMessages() {
        ViewGroup firstRequiredField = null;

        for (int i = 0; i < mRequiredFields.size(); i++) {
            ViewGroup requiredField = mRequiredFields.valueAt(i);

            if (requiredField instanceof MaterialSpinner) {
                if (((MaterialSpinner) requiredField).getSelectedItem() == null) {
                    ((MaterialSpinner) requiredField)
                            .setError(R.string.error_campo_requerido);

                    if (firstRequiredField == null) {
                        firstRequiredField = requiredField;
                    }
                }
            } else if (requiredField instanceof TextInputLayout) {
                if (TextUtils.isEmpty(((TextInputLayout) requiredField).getEditText().getText())) {
                    ((TextInputLayout) requiredField)
                            .setError(getString(R.string.error_campo_requerido));

                    if (firstRequiredField == null) {
                        firstRequiredField = requiredField;
                    }
                }
            }
        }

        if (firstRequiredField != null) {
            if (firstRequiredField instanceof MaterialSpinner) {
                firstRequiredField.requestFocusFromTouch();
                firstRequiredField.performClick();
                AndroidUtils.hideKeyboard(getActivity(), getActivity().getCurrentFocus());
            } else {
                AndroidUtils.focusThenShowKeyboard(getActivity(), firstRequiredField);
            }
        }
    }

    @Override public String getViewStringValue(int pViewId) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof TextInputLayout) {
            return ((TextInputLayout) field).getEditText().getText().toString();
        }
        return "";
    }

    @Override public int getViewPositionValue(int pViewId) {
        final ViewGroup field = mViewFields.get(pViewId);
        if (field != null && field instanceof MaterialSpinner) {
            return ((MaterialSpinner) field).getSelectedItemPosition();
        }
        return -1;
    }

    @Override public void displayValidationErrorForDesconto(String pValidationMessage) {
        mSpinnerFormaPagamento.setError(pValidationMessage);
    }

    @OnTouch(R.id.edit_text_data_emissao) boolean onEditTextDataEmissaoTouched(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            mPresenter.handleDataEmissaoTouched();
            return true;
        }
        return false;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unregisterForEvents();
        mPresenter.detach();
    }
}
